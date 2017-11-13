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

package com.liferay.application.list.my.account.permissions.internal;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.PortletCategoryKeys;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = PanelAppMyAccountPermissions.class)
public class PanelAppMyAccountPermissions {

	public void initPermissions(Portlet portlet) {
		String category = portlet.getControlPanelEntryCategory();

		if ((category == null) ||
			!category.equals(PortletCategoryKeys.USER_MY_ACCOUNT)) {

			return;
		}

		try {
			_initPermissions(portlet);
		}
		catch (Exception e) {
			_log.error(
				StringBundler.concat(
					"Unable to initialize my account permissions for portlet ",
					portlet.getPortletId(), " in company ",
					String.valueOf(portlet.getCompanyId())),
				e);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		String filter = StringBundler.concat(
			"(&(objectClass=", PanelApp.class.getName(), ")",
			"(panel.category.key=", PanelCategoryKeys.USER_MY_ACCOUNT, "*))");

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, filter, new PanelAppServiceTrackerCustomizer());
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private void _initPermissions(Portlet portlet) throws Exception {
		long companyId = portlet.getCompanyId();
		String portletId = portlet.getPortletId();

		PortletPreferences portletPreferences =
			_portletPreferencesFactory.getLayoutPortletSetup(
				companyId, companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY,
				LayoutConstants.DEFAULT_PLID, portletId,
				PortletConstants.DEFAULT_PREFERENCES);

		if (_prefsProps.getBoolean(
				portletPreferences,
				"myAccountAccessInControlPanelPermissionsInitialized")) {

			return;
		}

		Role userRole = _roleLocalService.getRole(
			companyId, RoleConstants.USER);

		List<String> actionIds = ResourceActionsUtil.getPortletResourceActions(
			portlet.getRootPortletId());

		String actionId = ActionKeys.ACCESS_IN_CONTROL_PANEL;

		if (actionIds.contains(actionId)) {
			_resourcePermissionLocalService.addResourcePermission(
				companyId, portlet.getRootPortletId(),
				ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
				userRole.getRoleId(), actionId);
		}

		portletPreferences.setValue(
			"myAccountAccessInControlPanelPermissionsInitialized",
			StringPool.TRUE);

		portletPreferences.store();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PanelAppMyAccountPermissions.class);

	private BundleContext _bundleContext;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesFactory _portletPreferencesFactory;

	@Reference
	private PrefsProps _prefsProps;

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

				_initPermissions(portlet);
			}
			catch (Throwable t) {
				_bundleContext.ungetService(reference);

				if (_log.isDebugEnabled()) {
					_log.debug(t.getMessage());
				}
			}

			return panelApp;
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