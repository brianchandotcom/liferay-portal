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

package com.liferay.asset.publisher.web.util;

import com.liferay.asset.publisher.util.AssetPublisherHelper;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author  Jürgen Kappler
 */
public class AssetPublisherHelperUtil {

	public static AssetPublisherHelper getAssetPublisherHelper() {
		return _serviceTracker.getService();
	}

	private static final ServiceTracker
		<AssetPublisherHelper, AssetPublisherHelper> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AssetPublisherHelper.class);

		ServiceTracker<AssetPublisherHelper, AssetPublisherHelper>
			serviceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), AssetPublisherHelper.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}