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

import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * Provides methods for retrieving application instances defined by {@link
 * PanelApp} implementations. The Applications Registry is an OSGi component.
 * Applications used within the registry should also be OSGi components in order
 * to be registered.
 *
 * @author Adolfo Pérez
 */
@Component(service = PanelAppRegistry.class)
public class PanelAppRegistry {

	public PanelApp getFirstPanelApp(
		String parentPanelCategoryKey, PermissionChecker permissionChecker,
		Group group) {

		List<PanelApp> panelApps = getPanelApps(
			parentPanelCategoryKey, permissionChecker, group);

		if (panelApps.isEmpty()) {
			return null;
		}

		return panelApps.get(0);
	}

	public List<PanelApp> getPanelApps(PanelCategory parentPanelCategory) {
		return getPanelApps(parentPanelCategory.getKey());
	}

	public List<PanelApp> getPanelApps(
		PanelCategory parentPanelCategory, PermissionChecker permissionChecker,
		Group group) {

		return getPanelApps(
			parentPanelCategory.getKey(), permissionChecker, group);
	}

	public List<PanelApp> getPanelApps(String parentPanelCategoryKey) {
		List<PanelApp> panelApps = _serviceTrackerMap.getService(
			parentPanelCategoryKey);

		if (panelApps == null) {
			return Collections.emptyList();
		}

		long companyId = CompanyThreadLocal.getCompanyId();

		return ListUtil.filter(
			panelApps,
			panelApp -> {
				Portlet portlet = panelApp.getPortlet();

				if (portlet == null) {
					return false;
				}

				long portletCompanyId = portlet.getCompanyId();

				if ((portletCompanyId != CompanyConstants.SYSTEM) &&
					(portletCompanyId != companyId)) {

					return false;
				}

				return true;
			});
	}

	public List<PanelApp> getPanelApps(
		String parentPanelCategoryKey, PermissionChecker permissionChecker,
		Group group) {

		List<PanelApp> panelApps = getPanelApps(parentPanelCategoryKey);

		if (panelApps.isEmpty()) {
			return panelApps;
		}

		return ListUtil.filter(
			panelApps,
			panelApp -> {
				try {
					PanelAppShowFilter panelAppShowFilter = _panelAppShowFilter;

					if (panelAppShowFilter == null) {
						return panelApp.isShow(permissionChecker, group);
					}

					return panelAppShowFilter.isShow(
						panelApp, permissionChecker, group);
				}
				catch (PortalException portalException) {
					_log.error(portalException);
				}

				return false;
			});
	}

	public int getPanelAppsNotificationsCount(
		String parentPanelCategoryKey, PermissionChecker permissionChecker,
		Group group, User user) {

		int count = 0;

		for (PanelApp panelApp :
				getPanelApps(
					parentPanelCategoryKey, permissionChecker, group)) {

			count += panelApp.getNotificationsCount(user);
		}

		return count;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, PanelApp.class, "(panel.category.key=*)",
			new PropertyServiceReferenceMapper<>("panel.category.key"),
			new ServiceTrackerCustomizer<PanelApp, PanelApp>() {

				@Override
				public PanelApp addingService(
					ServiceReference<PanelApp> serviceReference) {

					PanelApp panelApp = bundleContext.getService(
						serviceReference);

					panelApp.setGroupProvider(_groupProvider);

					Portlet portlet = _portletLocalService.getPortletById(
						panelApp.getPortletId());

					if (portlet != null) {
						portlet.setControlPanelEntryCategory(
							String.valueOf(
								serviceReference.getProperty(
									"panel.category.key")));
					}
					else if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to get portlet " + panelApp.getPortletId());
					}

					if (panelApp instanceof BasePanelApp) {
						BasePanelApp basePanelApp = (BasePanelApp)panelApp;

						basePanelApp.setPortletLocalService(
							_portletLocalService);
					}

					return panelApp;
				}

				@Override
				public void modifiedService(
					ServiceReference<PanelApp> serviceReference,
					PanelApp panelApp) {
				}

				@Override
				public void removedService(
					ServiceReference<PanelApp> serviceReference,
					PanelApp panelApp) {

					bundleContext.ungetService(serviceReference);
				}

			},
			Collections.reverseOrder(
				new PropertyServiceReferenceComparator<>("panel.app.order")));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PanelAppRegistry.class);

	@Reference
	private GroupProvider _groupProvider;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile PanelAppShowFilter _panelAppShowFilter;

	@Reference
	private PortletLocalService _portletLocalService;

	private ServiceTrackerMap<String, List<PanelApp>> _serviceTrackerMap;

}