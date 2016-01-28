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

package com.liferay.application.list.user.personal.site.permissions;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.PortletLocalService;
import com.liferay.portal.service.ResourcePermissionLocalService;
import com.liferay.portal.service.RoleLocalService;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Tomas Polesovsky
 */
@Component(immediate = true)
public class UserPersonalSitePermissions {

	public void initPermissions(List<Company> companies, Portlet portlet) {
		String rootPortletId = portlet.getRootPortletId();

		for (Company company : companies) {
			long companyId = company.getCompanyId();

			Role powerUserRole = getPowerUserRole(companyId);

			if (powerUserRole == null) {
				continue;
			}

			Group userPersonalSite = getPersonalSiteGroup(companyId);

			if (userPersonalSite == null) {
				continue;
			}

			try {
				initPermissions(
					companyId, powerUserRole.getRoleId(), rootPortletId,
					userPersonalSite.getGroupId());
			}
			catch (PortalException e) {
				_log.error(
					"Unable to initialize user personal site permissions" +
						" for portlet " + portlet.getPortletId() +
						" in company " + companyId
					, e);
			}
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_bundleContext = bundleContext;

		Filter filter = bundleContext.createFilter(
			"(&(objectClass=" + PanelApp.class.getName() + ")" +
				"(panel.category.key=" + PanelCategoryKeys.SITE_ADMINISTRATION +
					"*))");

		_serviceTracker = new ServiceTracker<>(
			bundleContext, filter, new PanelAppServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	protected void deactivated() {
		_serviceTracker.close();
	}

	protected Group getPersonalSiteGroup(long companyId) {
		try {
			return _groupLocalService.getUserPersonalSiteGroup(companyId);
		}
		catch (PortalException e) {
			_log.error(
				"Unable to obtain personal site in company " + companyId, e);
		}

		return null;
	}

	protected Role getPowerUserRole(long companyId) {
		try {
			return _roleLocalService.getRole(
				companyId, RoleConstants.POWER_USER);
		}
		catch (PortalException e) {
			_log.error(
				"Unable to obtain power user role in company " + companyId, e);
		}

		return null;
	}

	protected void initPermissions(
			long companyId, long powerUserRoleId, String rootPortletId,
			long userPersonalSiteGroupId)
		throws PortalException {

		String primaryKey = String.valueOf(userPersonalSiteGroupId);

		if (_resourcePermissionLocalService.getResourcePermissionsCount(
				companyId, rootPortletId, ResourceConstants.SCOPE_GROUP,
				primaryKey) == 0) {

			List<String> portletActionIds =
				ResourceActionsUtil.getPortletResourceActions(rootPortletId);

			_resourcePermissionLocalService.setResourcePermissions(
				companyId, rootPortletId, ResourceConstants.SCOPE_GROUP,
				String.valueOf(userPersonalSiteGroupId), powerUserRoleId,
				portletActionIds.toArray(new String[0]));
		}

		String rootModelName = ResourceActionsUtil.getPortletRootModelResource(
			rootPortletId);

		if (Validator.isBlank(rootModelName)) {
			return;
		}

		if (_resourcePermissionLocalService.getResourcePermissionsCount(
				companyId, rootModelName, ResourceConstants.SCOPE_GROUP,
				primaryKey) == 0) {

			List<String> modelActionIds =
				ResourceActionsUtil.getModelResourceActions(rootModelName);

			_resourcePermissionLocalService.setResourcePermissions(
				companyId, rootModelName, ResourceConstants.SCOPE_GROUP,
				String.valueOf(userPersonalSiteGroupId), powerUserRoleId,
				modelActionIds.toArray(new String[0]));
		}
	}

	@Reference(unbind = "-")
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		this._companyLocalService = companyLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		this._groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		this._portletLocalService = portletLocalService;
	}

	@Reference(unbind = "-")
	protected void setResourcePermissionLocalService(
		ResourcePermissionLocalService resourcePermissionLocalService) {

		this._resourcePermissionLocalService = resourcePermissionLocalService;
	}

	@Reference(unbind = "-")
	protected void setRoleLocalService(RoleLocalService roleLocalService) {
		this._roleLocalService = roleLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserPersonalSitePermissions.class);

	private BundleContext _bundleContext;
	private CompanyLocalService _companyLocalService;
	private GroupLocalService _groupLocalService;
	private PortletLocalService _portletLocalService;
	private ResourcePermissionLocalService _resourcePermissionLocalService;
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
					Class panelAppClass = panelApp.getClass();
					_log.error(
						"Unable to obtain portlet " + panelApp.getPortletId() +
							" for PanelApp " + panelAppClass.getName() +
							". Please register PanelApp to OSGi with " +
							"satisfied reference to the portlet.");

					return panelApp;
				}

				initPermissions(_companyLocalService.getCompanies(), portlet);

				return panelApp;
			}
			catch (Throwable e) {
				_bundleContext.ungetService(reference);

				throw e;
			}
		}

		@Override
		public void modifiedService(
			ServiceReference<PanelApp> reference, PanelApp service) {

			removedService(reference, service);
			addingService(reference);
		}

		@Override
		public void removedService(
			ServiceReference<PanelApp> reference, PanelApp service) {

			_bundleContext.ungetService(reference);
		}

	}

}