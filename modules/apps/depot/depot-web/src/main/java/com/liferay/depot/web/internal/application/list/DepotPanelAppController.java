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

package com.liferay.depot.web.internal.application.list;

import com.liferay.application.list.GroupProvider;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.depot.web.internal.application.controller.DepotApplicationController;
import com.liferay.depot.web.internal.constants.DepotPortletKeys;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.io.IOException;

import java.util.Dictionary;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = DepotPanelAppController.class)
public class DepotPanelAppController {

	public boolean isShow(PanelApp panelApp, long groupId) {
		String portletId = panelApp.getPortletId();

		if (_isAlwaysShow(portletId)) {
			return true;
		}

		return _depotApplicationController.isEnabled(portletId, groupId);
	}

	public boolean isShow(String portletId) {
		if (_isAlwaysShow(portletId)) {
			return true;
		}

		return _depotApplicationController.isEnabled(portletId);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_panelCategoryHelper = new PanelCategoryHelper(
			_panelAppRegistry, _panelCategoryRegistry);

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext,
			"(&(objectClass=com.liferay.application.list.PanelApp)" +
				"(!(depot.panel.app.wrapper=*)))",
			new DepotPanelAppServiceTrackerCustomizer(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private boolean _isAlwaysShow(String portletId) {
		if (portletId.equals(DepotPortletKeys.DEPOT_ADMIN) ||
			portletId.equals(DepotPortletKeys.DEPOT_SETTINGS) ||
			_panelCategoryHelper.isControlPanelApp(portletId) ||
			_panelCategoryHelper.isApplicationsMenuApp(portletId)) {

			return true;
		}

		return false;
	}

	@Reference
	private DepotApplicationController _depotApplicationController;

	@Reference
	private PanelAppRegistry _panelAppRegistry;

	private PanelCategoryHelper _panelCategoryHelper;

	@Reference
	private PanelCategoryRegistry _panelCategoryRegistry;

	private ServiceTracker<?, ?> _serviceTracker;

	private class DepotPanelAppServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<PanelApp, ServiceRegistration<?>> {

		@Override
		public ServiceRegistration<?> addingService(
			ServiceReference<PanelApp> serviceReference) {

			PanelApp panelApp = _bundleContext.getService(serviceReference);

			Dictionary<String, Object> panelAppProperties =
				new HashMapDictionary<>();

			for (String key : serviceReference.getPropertyKeys()) {
				panelAppProperties.put(key, serviceReference.getProperty(key));
			}

			panelAppProperties.put("depot.panel.app.wrapper", Boolean.TRUE);

			Integer panelAppOrder = (Integer)serviceReference.getProperty(
				"panel.app.order");

			if (panelAppOrder == null) {
				panelAppOrder = 0;
			}

			panelAppProperties.put("panel.app.order", panelAppOrder - 1);

			return _bundleContext.registerService(
				PanelApp.class, new PanelAppWrapper(panelApp),
				panelAppProperties);
		}

		@Override
		public void modifiedService(
			ServiceReference<PanelApp> serviceReference,
			ServiceRegistration<?> serviceRegistration) {

			removedService(serviceReference, serviceRegistration);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<PanelApp> serviceReference,
			ServiceRegistration<?> serviceRegistration) {

			serviceRegistration.unregister();
		}

		private DepotPanelAppServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		private final BundleContext _bundleContext;

	}

	private class PanelAppWrapper implements PanelApp {

		@Override
		public String getKey() {
			return _panelApp.getKey();
		}

		@Override
		public String getLabel(Locale locale) {
			return _panelApp.getLabel(locale);
		}

		@Override
		public int getNotificationsCount(User user) {
			return _panelApp.getNotificationsCount(user);
		}

		@Override
		public Portlet getPortlet() {
			return _panelApp.getPortlet();
		}

		@Override
		public String getPortletId() {
			return _panelApp.getPortletId();
		}

		@Override
		public PortletURL getPortletURL(HttpServletRequest httpServletRequest)
			throws PortalException {

			return _panelApp.getPortletURL(httpServletRequest);
		}

		@Override
		public boolean include(
				HttpServletRequest httpServletRequest,
				HttpServletResponse httpServletResponse)
			throws IOException {

			return _panelApp.include(httpServletRequest, httpServletResponse);
		}

		@Override
		public boolean isShow(PermissionChecker permissionChecker, Group group)
			throws PortalException {

			if (group.isDepot() &&
				!DepotPanelAppController.this.isShow(
					_panelApp, group.getGroupId())) {

				return false;
			}

			return _panelApp.isShow(permissionChecker, group);
		}

		@Override
		public void setGroupProvider(GroupProvider groupProvider) {
			_panelApp.setGroupProvider(groupProvider);
		}

		private PanelAppWrapper(PanelApp panelApp) {
			_panelApp = panelApp;
		}

		private final PanelApp _panelApp;

	}

}