/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.journal.internal.security.permission.resource;

import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.constants.JournalConstants;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.DynamicInheritancePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.StagedModelPermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.WorkflowedModelPermissionLogic;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermission;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class JournalArticleModelResourcePermissionRegistrar {

	@Activate
	protected void activate(BundleContext bundleContext) {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("model.class.name", JournalArticle.class.getName());

		_serviceRegistration = bundleContext.registerService(
			ModelResourcePermission.class,
			ModelResourcePermissionFactory.create(
				JournalArticle.class, JournalArticle::getResourcePrimKey,
				classPK -> {
					JournalArticle article =
						_journalArticleLocalService.fetchLatestArticle(classPK);

					if (article != null) {
						return article;
					}

					return _journalArticleLocalService.getArticle(classPK);
				},
				_portletResourcePermission,
				(modelResourcePermission, consumer) -> {
					consumer.accept(
						new StagedModelPermissionLogic<JournalArticle>(
							_stagingPermission, JournalPortletKeys.JOURNAL,
							JournalArticle::getResourcePrimKey) {

							@Override
							public Boolean contains(
								PermissionChecker permissionChecker,
								String name, JournalArticle journalArticle,
								String actionId) {

								if (actionId.equals(ActionKeys.SUBSCRIBE)) {
									return null;
								}

								return super.contains(
									permissionChecker, name, journalArticle,
									actionId);
							}

						});
					consumer.accept(
						new WorkflowedModelPermissionLogic<>(
							_workflowPermission, modelResourcePermission,
							_groupLocalService, JournalArticle::getId));
					consumer.accept(
						new JournalArticleConfigurationModelResourcePermissionLogic());
					consumer.accept(
						new DynamicInheritancePermissionLogic<>(
							_journalFolderModelResourcePermission,
							_getFetchParentFunction(), true));
				}),
			properties);
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	private UnsafeFunction<JournalArticle, JournalFolder, PortalException>
		_getFetchParentFunction() {

		return article -> {
			long folderId = article.getFolderId();

			if (JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID == folderId) {
				return null;
			}

			if (article.isInTrash()) {
				return _journalFolderLocalService.fetchFolder(folderId);
			}

			return _journalFolderLocalService.getFolder(folderId);
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleModelResourcePermissionRegistrar.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalFolderLocalService _journalFolderLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.journal.model.JournalFolder)"
	)
	private ModelResourcePermission<JournalFolder>
		_journalFolderModelResourcePermission;

	@Reference(
		target = "(resource.name=" + JournalConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	private ServiceRegistration<ModelResourcePermission> _serviceRegistration;

	@Reference
	private StagingPermission _stagingPermission;

	@Reference
	private WorkflowPermission _workflowPermission;

	private class JournalArticleConfigurationModelResourcePermissionLogic
		implements ModelResourcePermissionLogic<JournalArticle> {

		@Override
		public Boolean contains(
				PermissionChecker permissionChecker, String name,
				JournalArticle article, String actionId)
			throws PortalException {

			if (!actionId.equals(ActionKeys.VIEW)) {
				return null;
			}

			try {
				JournalServiceConfiguration journalServiceConfiguration =
					_configurationProvider.getCompanyConfiguration(
						JournalServiceConfiguration.class,
						permissionChecker.getCompanyId());

				if (!journalServiceConfiguration.
						articleViewPermissionsCheckEnabled()) {

					return true;
				}
			}
			catch (ConfigurationException ce) {
				_log.error(
					"Unable to get journal service configuration for company " +
						permissionChecker.getCompanyId(),
					ce);

				return false;
			}

			return null;
		}

	}

}