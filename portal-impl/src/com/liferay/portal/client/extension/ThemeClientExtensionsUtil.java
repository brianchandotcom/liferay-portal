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

package com.liferay.portal.client.extension;

import com.liferay.portal.kernel.client.extension.ThemeClientExtensions;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
public class ThemeClientExtensionsUtil {

	public static ThemeClientExtensions getThemeClientExtensions() {
		return _themeClientExtensions;
	}

	private static final ServiceTracker
		<ThemeClientExtensions, ThemeClientExtensions> _serviceTracker;
	private static volatile ThemeClientExtensions _themeClientExtensions;

	static {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_serviceTracker = new ServiceTracker<>(
			bundleContext, ThemeClientExtensions.class,
			new ServiceTrackerCustomizer
				<ThemeClientExtensions, ThemeClientExtensions>() {

				@Override
				public ThemeClientExtensions addingService(
					ServiceReference<ThemeClientExtensions> serviceReference) {

					_themeClientExtensions = bundleContext.getService(
						serviceReference);

					return _themeClientExtensions;
				}

				@Override
				public void modifiedService(
					ServiceReference<ThemeClientExtensions> serviceReference,
					ThemeClientExtensions themeClientExtensions) {
				}

				@Override
				public void removedService(
					ServiceReference<ThemeClientExtensions> serviceReference,
					ThemeClientExtensions themeClientExtensions) {

					_themeClientExtensions = null;

					bundleContext.ungetService(serviceReference);
				}

			});

		_serviceTracker.open();
	}

}