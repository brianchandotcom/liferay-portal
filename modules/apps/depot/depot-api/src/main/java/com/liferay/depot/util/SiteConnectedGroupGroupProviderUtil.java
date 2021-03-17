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

package com.liferay.depot.util;

import com.liferay.depot.group.provider.SiteConnectedGroupGroupProvider;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Adolfo Pérez
 */
public class SiteConnectedGroupGroupProviderUtil {

	public static long[] getAncestorSiteAndDepotGroupIds(long groupId)
		throws PortalException {

		SiteConnectedGroupGroupProvider siteConnectedGroupGroupProvider =
			_siteConnectedGroupGroupProviderUtil.
				_getSiteConnectedGroupGroupProvider();

		return siteConnectedGroupGroupProvider.getAncestorSiteAndDepotGroupIds(
			groupId);
	}

	public static long[] getAncestorSiteAndDepotGroupIds(
			long groupId, boolean ddmStructuresAvailable)
		throws PortalException {

		SiteConnectedGroupGroupProvider siteConnectedGroupGroupProvider =
			_siteConnectedGroupGroupProviderUtil.
				_getSiteConnectedGroupGroupProvider();

		return siteConnectedGroupGroupProvider.getAncestorSiteAndDepotGroupIds(
			groupId, ddmStructuresAvailable);
	}

	public static long[] getCurrentAndAncestorSiteAndDepotGroupIds(long groupId)
		throws PortalException {

		SiteConnectedGroupGroupProvider siteConnectedGroupGroupProvider =
			_siteConnectedGroupGroupProviderUtil.
				_getSiteConnectedGroupGroupProvider();

		return siteConnectedGroupGroupProvider.
			getCurrentAndAncestorSiteAndDepotGroupIds(groupId);
	}

	public static long[] getCurrentAndAncestorSiteAndDepotGroupIds(
			long groupId, boolean ddmStructuresAvailable)
		throws PortalException {

		SiteConnectedGroupGroupProvider siteConnectedGroupGroupProvider =
			_siteConnectedGroupGroupProviderUtil.
				_getSiteConnectedGroupGroupProvider();

		return siteConnectedGroupGroupProvider.
			getCurrentAndAncestorSiteAndDepotGroupIds(
				groupId, ddmStructuresAvailable);
	}

	public static long[] getCurrentAndAncestorSiteAndDepotGroupIds(
			long[] groupIds)
		throws PortalException {

		SiteConnectedGroupGroupProvider siteConnectedGroupGroupProvider =
			_siteConnectedGroupGroupProviderUtil.
				_getSiteConnectedGroupGroupProvider();

		return siteConnectedGroupGroupProvider.
			getCurrentAndAncestorSiteAndDepotGroupIds(groupIds);
	}

	private SiteConnectedGroupGroupProviderUtil() {
		Bundle bundle = FrameworkUtil.getBundle(
			SiteConnectedGroupGroupProviderUtil.class);

		_serviceTracker = new ServiceTracker<>(
			bundle.getBundleContext(), SiteConnectedGroupGroupProvider.class,
			null);

		_serviceTracker.open();
	}

	private SiteConnectedGroupGroupProvider
		_getSiteConnectedGroupGroupProvider() {

		return _serviceTracker.getService();
	}

	private static final SiteConnectedGroupGroupProviderUtil
		_siteConnectedGroupGroupProviderUtil =
			new SiteConnectedGroupGroupProviderUtil();

	private final ServiceTracker
		<SiteConnectedGroupGroupProvider, SiteConnectedGroupGroupProvider>
			_serviceTracker;

}