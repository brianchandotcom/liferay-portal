/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.info.display.internal.asset.display.page.portlet;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Pavel Savinov
 */
public class AssetDisplayPageFriendlyURLProviderUtil {

	public static AssetDisplayPageFriendlyURLProvider
		getAssetDisplayPageFriendlyURLProvider() {

		return _serviceTracker.getService();
	}

	private static final ServiceTracker
		<AssetDisplayPageFriendlyURLProvider,
		 AssetDisplayPageFriendlyURLProvider> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AssetDisplayPageFriendlyURLProvider.class);

		ServiceTracker
			<AssetDisplayPageFriendlyURLProvider,
			 AssetDisplayPageFriendlyURLProvider> serviceTracker =
				new ServiceTracker<>(
					bundle.getBundleContext(),
					AssetDisplayPageFriendlyURLProvider.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}