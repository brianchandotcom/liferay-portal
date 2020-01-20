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
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.depot.web.internal.application.controller.DepotApplicationController;
import com.liferay.depot.web.internal.constants.DepotPortletKeys;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.io.IOException;

import java.util.Dictionary;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

		if (portletId.equals(DepotPortletKeys.DEPOT_ADMIN) ||
			portletId.equals(DepotPortletKeys.DEPOT_SETTINGS) ||
			_panelCategoryHelper.containsPortlet(
				portletId, PanelCategoryKeys.CONTROL_PANEL)) {

			return true;
		}

		return _depotApplicationController.isEnabled(portletId, groupId);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_panelCategoryHelper = new PanelCategoryHelper(
			_panelAppRegistry, _panelCategoryRegistry);

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, PanelApp.class,
			new DepotPanelAppServiceTrackerCustomizer(
				bundleContext, _serviceRegistrations));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		for (ServiceRegistration<PanelApp> serviceRegistration :
				_serviceRegistrations.values()) {

			try {
				serviceRegistration.unregister();
			}
			catch (IllegalStateException illegalStateException) {
				_log.error(illegalStateException, illegalStateException);
			}
		}

		_serviceRegistrations.clear();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DepotPanelAppController.class);

	@Reference
	private DepotApplicationController _depotApplicationController;

	@Reference
	private PanelAppRegistry _panelAppRegistry;

	private PanelCategoryHelper _panelCategoryHelper;

	@Reference
	private PanelCategoryRegistry _panelCategoryRegistry;

	private final Map<ServiceReference<PanelApp>, ServiceRegistration<PanelApp>>
		_serviceRegistrations = new ConcurrentHashMap<>();
	private ServiceTracker<PanelApp, PanelApp> _serviceTracker;

	private class DepotPanelAppServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<PanelApp, PanelApp> {

		public DepotPanelAppServiceTrackerCustomizer(
			BundleContext bundleContext,
			Map<ServiceReference<PanelApp>, ServiceRegistration<PanelApp>>
				serviceRegistrations) {

			_bundleContext = bundleContext;
			_serviceRegistrations = serviceRegistrations;
		}

		@Override
		public PanelApp addingService(
			ServiceReference<PanelApp> serviceReference) {

			PanelApp panelApp = _bundleContext.getService(serviceReference);

			if (panelApp instanceof PanelAppWrapper) {
				return panelApp;
			}

			Dictionary<String, Object> panelAppProperties =
				new HashMapDictionary<>();

			for (String key : serviceReference.getPropertyKeys()) {
				panelAppProperties.put(key, serviceReference.getProperty(key));
			}

			panelAppProperties.put(
				"panel.app.order", _getPanelAppOrder(serviceReference) - 1);

			PanelApp wrappedPanelApp = new PanelAppWrapper(panelApp);

			ServiceRegistration<PanelApp> serviceRegistration =
				_bundleContext.registerService(
					PanelApp.class, wrappedPanelApp, panelAppProperties);

			_serviceRegistrations.put(serviceReference, serviceRegistration);

			return wrappedPanelApp;
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

			ServiceRegistration<PanelApp> serviceRegistration =
				_serviceRegistrations.remove(serviceReference);

			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}
		}

		private Integer _getPanelAppOrder(
			ServiceReference<PanelApp> serviceReference) {

			Integer panelAppOrder = (Integer)serviceReference.getProperty(
				"panel.app.order");

			if (panelAppOrder != null) {
				return panelAppOrder;
			}

			return 0;
		}

		private final BundleContext _bundleContext;
		private final Map
			<ServiceReference<PanelApp>, ServiceRegistration<PanelApp>>
				_serviceRegistrations;

	}

	private class PanelAppWrapper implements PanelApp {

		public PanelAppWrapper(PanelApp panelApp) {
			_panelApp = panelApp;
		}

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

			if ((group.getType() == GroupConstants.TYPE_DEPOT) &&
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

		@Override
		public void setPortlet(Portlet portlet) {
			_panelApp.setPortlet(portlet);
		}

		private final PanelApp _panelApp;

	}

}