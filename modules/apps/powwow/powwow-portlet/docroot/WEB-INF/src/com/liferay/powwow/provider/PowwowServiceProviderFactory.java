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

package com.liferay.powwow.provider;

import com.liferay.powwow.provider.bbb.BBBPowwowServiceProvider;
import com.liferay.powwow.provider.zoom.ZoomPowwowServiceProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marco Calderon
 */
public class PowwowServiceProviderFactory {

	public static PowwowServiceProvider getPowwowServiceProvider(
		String providerType) {

		if (!_powwowServiceProviders.containsKey(providerType)) {
			throw new IllegalArgumentException("Invalid provider type");
		}

		return _powwowServiceProviders.get(providerType);
	}

	private static final Map<String, PowwowServiceProvider>
		_powwowServiceProviders = new HashMap<String, PowwowServiceProvider>() {
			{
				PowwowServiceProvider bbbPowwowServiceProvider =
					new BBBPowwowServiceProvider();

				put(
					bbbPowwowServiceProvider.getPowwowServiceProviderKey(),
					bbbPowwowServiceProvider);

				PowwowServiceProvider zoomPowwowServiceProvider =
					new ZoomPowwowServiceProvider();

				put(
					zoomPowwowServiceProvider.getPowwowServiceProviderKey(),
					zoomPowwowServiceProvider);
			}
		};

}