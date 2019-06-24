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

package com.liferay.wiki.internal.security.permission.resource;

import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.StagedModelPermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.WorkflowedModelPermissionLogic;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermission;
import com.liferay.portal.util.PropsValues;
import com.liferay.wiki.constants.WikiConstants;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalService;
import com.liferay.wiki.service.WikiPageLocalService;

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
public class WikiPageModelResourcePermissionRegistrar {

	@Activate
	public void activate(BundleContext bundleContext) {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("model.class.name", WikiPage.class.getName());

		_serviceRegistration = bundleContext.registerService(
			ModelResourcePermission.class,
			ModelResourcePermissionFactory.create(
				WikiPage.class, WikiPage::getResourcePrimKey,
				(Long resourcePrimKey) -> {
					WikiPage page = _wikiPageLocalService.fetchPage(
						resourcePrimKey);

					if (page == null) {
						return _wikiPageLocalService.getPageByPageId(
							resourcePrimKey);
					}

					return page;
				},
				_portletResourcePermission,
				(modelResourcePermission, consumer) -> {
					consumer.accept(
						new StagedModelPermissionLogic<>(
							_stagingPermission, WikiPortletKeys.WIKI,
							WikiPage::getResourcePrimKey));
					consumer.accept(
						new WorkflowedModelPermissionLogic<>(
							_workflowPermission, modelResourcePermission,
							_groupLocalService, WikiPage::getResourcePrimKey));
					consumer.accept(
						(permissionChecker, name, page, actionId) -> {

							// LPS-11086

							if (page.isDraft() &&
								actionId.equals(ActionKeys.DELETE) &&
								(page.getStatusByUserId() ==
									permissionChecker.getUserId())) {

								return true;
							}

							return null;
						});
					consumer.accept(
						new RedirectPageDynamicInheritanceModelResourcePermissionLogic());
				}),
			properties);
	}

	@Deactivate
	public void deactivate() {
		_serviceRegistration.unregister();
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(target = "(resource.name=" + WikiConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

	private ServiceRegistration<ModelResourcePermission> _serviceRegistration;

	@Reference
	private StagingPermission _stagingPermission;

	@Reference
	private WikiNodeLocalService _wikiNodeLocalService;

	@Reference(target = "(model.class.name=com.liferay.wiki.model.WikiNode)")
	private ModelResourcePermission<WikiNode> _wikiNodeModelResourcePermission;

	@Reference
	private WikiPageLocalService _wikiPageLocalService;

	@Reference
	private WorkflowPermission _workflowPermission;

	private class RedirectPageDynamicInheritanceModelResourcePermissionLogic
		implements ModelResourcePermissionLogic<WikiPage> {

		@Override
		public Boolean contains(
				PermissionChecker permissionChecker, String name, WikiPage page,
				String actionId)
			throws PortalException {

			if (!actionId.equals(ActionKeys.VIEW)) {
				return null;
			}

			// LPS-12130

			WikiPage redirectPage = page.fetchRedirectPage();

			if (redirectPage != null) {
				page = redirectPage;
			}

			if (PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {
				if (!_wikiNodeModelResourcePermission.contains(
						permissionChecker, page.getNode(), ActionKeys.VIEW)) {

					return false;
				}

				while (page != null) {
					if (!_hasPermission(
							permissionChecker, page, ActionKeys.VIEW)) {

						return false;
					}

					page = page.fetchParentPage();
				}

				return true;
			}

			return _hasPermission(permissionChecker, page, ActionKeys.VIEW);
		}

		private boolean _hasPermission(
			PermissionChecker permissionChecker, WikiPage page,
			String actionId) {

			if (permissionChecker.hasOwnerPermission(
					page.getCompanyId(), WikiPage.class.getName(),
					page.getResourcePrimKey(), page.getUserId(), actionId) ||
				permissionChecker.hasPermission(
					page.getGroupId(), WikiPage.class.getName(),
					page.getResourcePrimKey(), actionId)) {

				return true;
			}

			return false;
		}

	}

}