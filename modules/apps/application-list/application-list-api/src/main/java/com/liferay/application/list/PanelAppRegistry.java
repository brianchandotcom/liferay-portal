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

import com.liferay.osgi.service.tracker.collections.ServiceReferenceServiceTuple;
import com.liferay.osgi.service.tracker.collections.ServiceTrackerMapBuilder;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerBucket;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerBucketFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapListener;
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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
			catch (PortalException portalException) {
				_log.error(portalException);
			}
		}

		return null;
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
					return panelApp.isShow(permissionChecker, group);
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

		for (PanelApp panelApp : getPanelApps(parentPanelCategoryKey)) {
			int notificationsCount = panelApp.getNotificationsCount(user);

			try {
				if ((notificationsCount > 0) &&
					panelApp.isShow(permissionChecker, group)) {

					count += notificationsCount;
				}
			}
			catch (PortalException portalException) {
				_log.error(portalException);
			}
		}

		return count;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap =
			ServiceTrackerMapBuilder.SelectorFactory.newSelector(
				bundleContext, PanelApp.class
			).map(
				"panel.category.key"
			).collect(
				new PanelAppsServiceTrackerBucketFactory()
			).newCollector(
				new PanelAppsServiceTrackerMapListener()
			).build();
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

	private class PanelAppsServiceTrackerBucketFactory
		implements ServiceTrackerBucketFactory
			<PanelApp, PanelApp, List<PanelApp>> {

		@Override
		public ServiceTrackerBucket<PanelApp, PanelApp, List<PanelApp>>
			create() {

			return new PanelCategoryServiceTrackerBucket();
		}

		private class PanelCategoryServiceTrackerBucket
			implements ServiceTrackerBucket
				<PanelApp, PanelApp, List<PanelApp>> {

			@Override
			public List<PanelApp> getContent() {
				return _services;
			}

			@Override
			public synchronized boolean isDisposable() {
				return _serviceReferenceServiceTuples.isEmpty();
			}

			@Override
			public synchronized void remove(
				ServiceReferenceServiceTuple<PanelApp, PanelApp>
					serviceReferenceServiceTuple) {

				_serviceReferenceServiceTuples.remove(
					serviceReferenceServiceTuple);

				_rebuild();
			}

			@Override
			public synchronized void store(
				ServiceReferenceServiceTuple<PanelApp, PanelApp>
					serviceReferenceServiceTuple) {

				int index = Collections.binarySearch(
					_serviceReferenceServiceTuples,
					serviceReferenceServiceTuple, _comparator);

				if (index < 0) {
					index = -index - 1;
				}

				_serviceReferenceServiceTuples.add(
					index, serviceReferenceServiceTuple);

				_rebuild();
			}

			private void _rebuild() {
				if (_serviceReferenceServiceTuples.isEmpty()) {
					_services = Collections.emptyList();

					return;
				}

				if (_serviceReferenceServiceTuples.size() == 1) {
					ServiceReferenceServiceTuple<PanelApp, PanelApp>
						serviceReferenceServiceTuple =
							_serviceReferenceServiceTuples.get(0);

					_services = Arrays.asList(
						serviceReferenceServiceTuple.getService());

					return;
				}

				Map<String, PanelApp> panelApps = new LinkedHashMap<>();

				_serviceReferenceServiceTuples.forEach(
					erviceReferenceServiceTuple -> {
						PanelApp panelApp =
							erviceReferenceServiceTuple.getService();

						panelApps.putIfAbsent(panelApp.getKey(), panelApp);
					});

				_services = new ArrayList<>(panelApps.values());
			}

			private final Comparator
				<ServiceReferenceServiceTuple<PanelApp, PanelApp>> _comparator =
					Comparator.comparing(
						ServiceReferenceServiceTuple::getServiceReference,
						new PanelAppOrderComparator());
			private final List<ServiceReferenceServiceTuple<PanelApp, PanelApp>>
				_serviceReferenceServiceTuples = new ArrayList<>();
			private List<PanelApp> _services = new ArrayList<>();

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

			if (panelApp instanceof BasePanelApp) {
				BasePanelApp basePanelApp = (BasePanelApp)panelApp;

				basePanelApp.setPortletLocalService(_portletLocalService);
			}

			Portlet portlet = panelApp.getPortlet();

			portlet.setControlPanelEntryCategory(panelCategoryKey);
		}

		@Override
		public void keyRemoved(
			ServiceTrackerMap<String, List<PanelApp>> serviceTrackerMap,
			String panelCategoryKey, PanelApp panelApp,
			List<PanelApp> panelApps) {
		}

	}

}