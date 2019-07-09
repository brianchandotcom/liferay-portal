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

package com.liferay.application.list;

import com.liferay.application.list.util.PanelCategoryServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PrefsProps;

import java.io.Serializable;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides methods for retrieving application instances defined by {@link
 * PanelApp} implementations. The Applications Registry is an OSGi component.
 * Applications used within the registry should also be OSGi components in order
 * to be registered.
 *
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = PanelAppRegistry.class)
public class PanelAppRegistry {

	public PanelApp getFirstPanelApp(
		String parentPanelCategoryKey, PermissionChecker permissionChecker,
		Group group) {

		List<PanelApp> panelApps = getPanelApps(parentPanelCategoryKey);

		for (PanelApp panelApp : panelApps) {
			try {
				if (panelApp.isShow(permissionChecker, group)) {
					return panelApp;
				}
			}
			catch (PortalException pe) {
				_log.error(pe, pe);
			}
		}

		return null;
	}

	public List<PanelApp> getPanelApps(PanelCategory parentPanelCategory) {
		return getPanelApps(parentPanelCategory.getKey());
	}

	public List<PanelApp> getPanelApps(
		PanelCategory parentPanelCategory,
		final PermissionChecker permissionChecker, final Group group) {

		return getPanelApps(
			parentPanelCategory.getKey(), permissionChecker, group);
	}

	public List<PanelApp> getPanelApps(String parentPanelCategoryKey) {
		List<PanelApp> panelApps = _serviceTrackerMap.getService(
			parentPanelCategoryKey);

		if (panelApps == null) {
			return Collections.emptyList();
		}

		return panelApps;
	}

	public List<PanelApp> getPanelApps(
		String parentPanelCategoryKey,
		final PermissionChecker permissionChecker, final Group group) {

		List<PanelApp> panelApps = getPanelApps(parentPanelCategoryKey);

		if (panelApps.isEmpty()) {
			return panelApps;
		}

		return ListUtil.filter(
			panelApps,
			panelApp -> {
				try {
					return panelApp.isShow(permissionChecker, group);
				}
				catch (PortalException pe) {
					_log.error(pe, pe);
				}

				return false;
			});
	}

	public int getPanelAppsNotificationsCount(
		String parentPanelCategoryKey, PermissionChecker permissionChecker,
		Group group, User user) {

		int count = 0;

		for (PanelApp panelApp : getPanelApps(parentPanelCategoryKey)) {
			int notificationsCount = panelApp.getNotificationsCount(user);

			try {
				if ((notificationsCount > 0) &&
					panelApp.isShow(permissionChecker, group)) {

					count += notificationsCount;
				}
			}
			catch (PortalException pe) {
				_log.error(pe, pe);
			}
		}

		return count;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, PanelApp.class, "(panel.category.key=*)",
			new PanelCategoryServiceReferenceMapper(),
			new PanelAppOrderComparator(),
			new PanelAppsServiceTrackerMapListener());
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PanelAppRegistry.class);

	@Reference
	private GroupProvider _groupProvider;

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

	private ServiceTrackerMap<String, List<PanelApp>> _serviceTrackerMap;

	private class PanelAppOrderComparator
		implements Comparator<ServiceReference<PanelApp>>, Serializable {

		@Override
		public int compare(
			ServiceReference serviceReference1,
			ServiceReference serviceReference2) {

			if (serviceReference1 == null) {
				if (serviceReference2 == null) {
					return 0;
				}

				return 1;
			}
			else if (serviceReference2 == null) {
				return -1;
			}

			Object propertyValue1 = serviceReference1.getProperty(
				"panel.app.order");
			Object propertyValue2 = serviceReference2.getProperty(
				"panel.app.order");

			if (propertyValue1 == null) {
				if (propertyValue2 == null) {
					return 0;
				}

				return 1;
			}
			else if (propertyValue2 == null) {
				return -1;
			}

			if (!(propertyValue2 instanceof Comparable)) {
				return -serviceReference2.compareTo(serviceReference1);
			}

			Comparable<Object> propertyValueComparable2 =
				(Comparable<Object>)propertyValue2;

			return -propertyValueComparable2.compareTo(propertyValue1);
		}

	}

	private class PanelAppsServiceTrackerMapListener
		implements ServiceTrackerMapListener<String, PanelApp, List<PanelApp>> {

		@Override
		public void keyEmitted(
			ServiceTrackerMap<String, List<PanelApp>> serviceTrackerMap,
			String panelCategoryKey, PanelApp panelApp,
			List<PanelApp> panelApps) {

			panelApp.setGroupProvider(_groupProvider);

			Portlet portlet = _portletLocalService.getPortletById(
				panelApp.getPortletId());

			if (portlet != null) {
				portlet.setControlPanelEntryCategory(panelCategoryKey);

				panelApp.setPortlet(portlet);
			}
			else if (_log.isDebugEnabled()) {
				_log.debug("Unable to get portlet " + panelApp.getPortletId());
			}

			if (panelApp instanceof BasePanelApp) {
				BasePanelApp basePanelApp = (BasePanelApp)panelApp;

				basePanelApp.setPortletLocalService(_portletLocalService);
			}
		}

		@Override
		public void keyRemoved(
			ServiceTrackerMap<String, List<PanelApp>> serviceTrackerMap,
			String panelCategoryKey, PanelApp panelApp,
			List<PanelApp> panelApps) {
		}

	}

}