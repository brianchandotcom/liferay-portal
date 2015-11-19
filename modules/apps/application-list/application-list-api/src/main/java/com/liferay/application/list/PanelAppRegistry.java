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
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.PortletLocalService;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = PanelAppRegistry.class)
public class PanelAppRegistry {

	public PanelApp getFirstPanelApp(
		PanelCategory parentPanelCategory, PermissionChecker permissionChecker,
		Group group) {

		List<PanelApp> panelApps = getPanelApps(parentPanelCategory);

		for (PanelApp panelApp : panelApps) {
			try {
				if (panelApp.hasAccessPermission(permissionChecker, group)) {
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

		List<PanelApp> panelApps = getPanelApps(parentPanelCategory);

		if (panelApps.isEmpty()) {
			return panelApps;
		}

		return ListUtil.filter(
			panelApps,
			new PredicateFilter<PanelApp>() {

				@Override
				public boolean filter(PanelApp panelApp) {
					try {
						return panelApp.hasAccessPermission(
							permissionChecker, group);
					}
					catch (PortalException pe) {
						_log.error(pe, pe);
					}

					return false;
				}

			});
	}

	public List<PanelApp> getPanelApps(String parentPanelCategoryKey) {
		List<PanelApp> panelApps = _serviceTrackerMap.getService(
			parentPanelCategoryKey);

		if (panelApps == null) {
			return Collections.emptyList();
		}

		return panelApps;
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, PanelApp.class, "(panel.category.key=*)",
			new PanelCategoryServiceReferenceMapper(),
			new ServiceRankingPropertyServiceReferenceComparator(),
			new PanelAppsServiceTrackerMapListener());
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		this.portletLocalService = portletLocalService;
	}

	protected PortletLocalService portletLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		PanelAppRegistry.class);

	private ServiceTrackerMap<String, List<PanelApp>> _serviceTrackerMap;

	private static class ServiceRankingPropertyServiceReferenceComparator
		extends PropertyServiceReferenceComparator<PanelApp> {

		public ServiceRankingPropertyServiceReferenceComparator() {
			super("service.ranking");
		}

		@Override
		public int compare(
			ServiceReference<PanelApp> serviceReference1,
			ServiceReference<PanelApp> serviceReference2) {

			return -(super.compare(serviceReference1, serviceReference2));
		}

	}

	private class PanelAppsServiceTrackerMapListener
		implements ServiceTrackerMapListener<String, PanelApp, List<PanelApp>> {

		@Override
		public void keyEmitted(
			ServiceTrackerMap<String, List<PanelApp>> serviceTrackerMap,
			String panelCategoryKey, PanelApp panelApp,
			List<PanelApp> panelApps) {

			Portlet portlet = portletLocalService.getPortletById(
				panelApp.getPortletId());

			if (portlet != null) {
				portlet.setControlPanelEntryCategory(panelCategoryKey);

				panelApp.setPortlet(portlet);
			}
			else if (_log.isDebugEnabled()) {
				_log.debug("Unable to get portlet " + panelApp.getPortletId());
			}
		}

	}

}