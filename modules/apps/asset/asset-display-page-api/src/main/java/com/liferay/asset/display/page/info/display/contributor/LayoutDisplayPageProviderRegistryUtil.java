/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.display.page.info.display.contributor;

import com.liferay.layout.display.page.LayoutDisplayPageProviderRegistry;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Jürgen Kappler
 */
public class LayoutDisplayPageProviderRegistryUtil {

	public static LayoutDisplayPageProviderRegistry
		getLayoutDisplayPageProviderRegistry() {

		return _serviceTracker.getService();
	}

	private static final ServiceTracker
		<LayoutDisplayPageProviderRegistry, LayoutDisplayPageProviderRegistry>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			LayoutDisplayPageProviderRegistry.class);

		ServiceTracker
			<LayoutDisplayPageProviderRegistry,
			 LayoutDisplayPageProviderRegistry> serviceTracker =
				new ServiceTracker<>(
					bundle.getBundleContext(),
					LayoutDisplayPageProviderRegistry.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}