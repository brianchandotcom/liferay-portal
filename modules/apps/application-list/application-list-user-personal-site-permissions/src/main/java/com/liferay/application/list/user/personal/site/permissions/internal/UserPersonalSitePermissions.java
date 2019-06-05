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

package com.liferay.application.list.user.personal.site.permissions.internal;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Tomas Polesovsky
 */
@Component(immediate = true, service = UserPersonalSitePermissions.class)
public class UserPersonalSitePermissions {

	public void initPermissions(List<Company> companies, Portlet portlet) {
		String rootPortletId = portlet.getRootPortletId();

		for (Company company : companies) {
			long companyId = company.getCompanyId();

			Role powerUserRole = getPowerUserRole(companyId);

			if (powerUserRole == null) {
				continue;
			}

			Group userPersonalSiteGroup = getUserPersonalSiteGroup(companyId);

			if (userPersonalSiteGroup == null) {
				continue;
			}

			try {
				initPermissions(
					companyId, powerUserRole.getRoleId(), rootPortletId,
					userPersonalSiteGroup.getGroupId());
			}
			catch (PortalException pe) {
				_log.error(
					StringBundler.concat(
						"Unable to initialize user personal site permissions ",
						"for portlet ", portlet.getPortletId(), " in company ",
						companyId),
					pe);
			}
		}
	}

	public void initPermissions(long companyId, List<Portlet> portlets) {
		Role powerUserRole = getPowerUserRole(companyId);

		if (powerUserRole == null) {
			return;
		}

		Group userPersonalSiteGroup = getUserPersonalSiteGroup(companyId);

		if (userPersonalSiteGroup == null) {
			return;
		}

		for (Portlet portlet : portlets) {
			try {
				initPermissions(
					companyId, powerUserRole.getRoleId(),
					portlet.getRootPortletId(),
					userPersonalSiteGroup.getGroupId());
			}
			catch (PortalException pe) {
				_log.error(
					StringBundler.concat(
						"Unable to initialize user personal site permissions ",
						"for portlet ", portlet.getPortletId(), " in company ",
						companyId),
					pe);
			}
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		String filter = StringBundler.concat(
			"(&(objectClass=", PanelApp.class.getName(), ")",
			"(panel.category.key=", PanelCategoryKeys.SITE_ADMINISTRATION,
			"*))");

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, filter, new PanelAppServiceTrackerCustomizer());
	}

	protected void deactivated() {
		_serviceTracker.close();
	}

	protected Role getPowerUserRole(long companyId) {
		try {
			return _roleLocalService.getRole(
				companyId, RoleConstants.POWER_USER);
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to get power user role in company " + companyId, pe);
		}

		return null;
	}

	protected Group getUserPersonalSiteGroup(long companyId) {
		try {
			return _groupLocalService.getUserPersonalSiteGroup(companyId);
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to get user personal site group in company " +
					companyId,
				pe);
		}

		return null;
	}

	protected void initPermissions(
			long companyId, long powerUserRoleId, String rootPortletId,
			long userPersonalSiteGroupId)
		throws PortalException {

		String primaryKey = String.valueOf(userPersonalSiteGroupId);

		int count = _resourcePermissionLocalService.getResourcePermissionsCount(
			companyId, rootPortletId, ResourceConstants.SCOPE_GROUP,
			primaryKey);

		if (count == 0) {
			List<String> portletActionIds =
				ResourceActionsUtil.getPortletResourceActions(rootPortletId);

			_resourcePermissionLocalService.setResourcePermissions(
				companyId, rootPortletId, ResourceConstants.SCOPE_GROUP,
				String.valueOf(userPersonalSiteGroupId), powerUserRoleId,
				portletActionIds.toArray(new String[0]));
		}

		String modelName = ResourceActionsUtil.getPortletRootModelResource(
			rootPortletId);

		if (Validator.isBlank(modelName)) {
			return;
		}

		count = _resourcePermissionLocalService.getResourcePermissionsCount(
			companyId, modelName, ResourceConstants.SCOPE_GROUP, primaryKey);

		if (count == 0) {
			List<String> modelActionIds =
				ResourceActionsUtil.getModelResourceActions(modelName);

			_resourcePermissionLocalService.setResourcePermissions(
				companyId, modelName, ResourceConstants.SCOPE_GROUP,
				String.valueOf(userPersonalSiteGroupId), powerUserRoleId,
				modelActionIds.toArray(new String[0]));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserPersonalSitePermissions.class);

	private BundleContext _bundleContext;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	private ServiceTracker<PanelApp, PanelApp> _serviceTracker;

	private class PanelAppServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<PanelApp, PanelApp> {

		@Override
		public PanelApp addingService(ServiceReference<PanelApp> reference) {
			PanelApp panelApp = _bundleContext.getService(reference);

			try {
				Portlet portlet = panelApp.getPortlet();

				if (portlet == null) {
					portlet = _portletLocalService.getPortletById(
						panelApp.getPortletId());
				}

				if (portlet == null) {
					Class<?> panelAppClass = panelApp.getClass();

					_log.error(
						StringBundler.concat(
							"Unable to get portlet ", panelApp.getPortletId(),
							" for panel app ", panelAppClass.getName()));

					return panelApp;
				}

				initPermissions(_companyLocalService.getCompanies(), portlet);

				return panelApp;
			}
			catch (Throwable t) {
				_bundleContext.ungetService(reference);

				throw t;
			}
		}

		@Override
		public void modifiedService(
			ServiceReference<PanelApp> serviceReference, PanelApp panelApp) {

			removedService(serviceReference, panelApp);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<PanelApp> serviceReference, PanelApp panelApp) {

			_bundleContext.ungetService(serviceReference);
		}

	}

}