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

package com.liferay.asset.info.display.url.provider.util;

import com.liferay.asset.info.display.url.provider.AssetInfoEditURLProvider;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Jürgen Kappler
 */
public class AssetInfoEditURLProviderUtil {

	public static String getURL(
		String className, long classPK, HttpServletRequest httpServletRequest) {

		AssetInfoEditURLProvider assetInfoEditURLProvider =
			_serviceTracker.getService();

		return assetInfoEditURLProvider.getURL(
			className, classPK, httpServletRequest);
	}

	private static final ServiceTracker
		<AssetInfoEditURLProvider, AssetInfoEditURLProvider> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AssetInfoEditURLProvider.class);

		ServiceTracker<AssetInfoEditURLProvider, AssetInfoEditURLProvider>
			serviceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), AssetInfoEditURLProvider.class,
				null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}