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

package com.liferay.knowledge.base.internal.security.permission.resource;

import com.liferay.knowledge.base.constants.KBConstants;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;

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
public class KBArticleModelResourcePermissionRegistrar {

	@Activate
	protected void activate(BundleContext bundleContext) {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("model.class.name", KBArticle.class.getName());

		_serviceRegistration = bundleContext.registerService(
			ModelResourcePermission.class,
			ModelResourcePermissionFactory.create(
				KBArticle.class, KBArticle::getRootResourcePrimKey,
				classPK -> {
					KBArticle kbArticle =
						_kbArticleLocalService.fetchLatestKBArticle(
							classPK, WorkflowConstants.STATUS_ANY);

					if (kbArticle == null) {
						kbArticle = _kbArticleLocalService.getKBArticle(
							classPK);
					}

					return kbArticle;
				},
				_portletResourcePermission,
				(modelResourcePermission, consumer) -> {
					if (PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {
						consumer.accept(
							new KBArticleDynamicInheritanceModelResourcePermissionLogic(
								modelResourcePermission));
					}
				}),
			properties);
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	@Reference
	private KBArticleLocalService _kbArticleLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.knowledge.base.model.KBFolder)"
	)
	private ModelResourcePermission<KBFolder> _kbFolderModelResourcePermission;

	@Reference(
		target = "(resource.name=" + KBConstants.RESOURCE_NAME_ADMIN + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	private ServiceRegistration<ModelResourcePermission> _serviceRegistration;

	private class KBArticleDynamicInheritanceModelResourcePermissionLogic
		implements ModelResourcePermissionLogic<KBArticle> {

		@Override
		public Boolean contains(
				PermissionChecker permissionChecker, String name,
				KBArticle kbArticle, String actionId)
			throws PortalException {

			if (!ActionKeys.VIEW.equals(actionId)) {
				return null;
			}

			long parentResourcePrimKey = kbArticle.getParentResourcePrimKey();

			if (parentResourcePrimKey ==
					KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				return null;
			}

			long parentResourceClassNameId =
				kbArticle.getParentResourceClassNameId();

			long kbFolderClassNameId = PortalUtil.getClassNameId(
				KBFolderConstants.getClassName());

			if (parentResourceClassNameId == kbFolderClassNameId) {
				if (!_kbFolderModelResourcePermission.contains(
						permissionChecker, parentResourcePrimKey, actionId)) {

					return false;
				}
			}
			else {
				KBArticle parentKBArticle =
					_kbArticleLocalService.getLatestKBArticle(
						parentResourcePrimKey, WorkflowConstants.STATUS_ANY);

				if (!_kbArticleModelResourcePermission.contains(
						permissionChecker, parentKBArticle, actionId)) {

					return false;
				}
			}

			return null;
		}

		private KBArticleDynamicInheritanceModelResourcePermissionLogic(
			ModelResourcePermission<KBArticle> modelResourcePermission) {

			_kbArticleModelResourcePermission = modelResourcePermission;
		}

		private final ModelResourcePermission<KBArticle>
			_kbArticleModelResourcePermission;

	}

}