/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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