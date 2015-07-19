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

package com.liferay.portal.kernel.search.hits;

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

/**
 * @author Michael C. Han
 */
public class HitsProcessorRegistryUtil {

	public static HitsProcessorRegistry getHitsProcessorRegistry() {
		HitsProcessorRegistry hitsProcessorRegistry =
			_instance._serviceTracker.getService();

		if (hitsProcessorRegistry == null) {
			throw new IllegalStateException(
				"HitsProcessorRegistry not properly initialized");
		}

		return hitsProcessorRegistry;
	}

	public static boolean process(SearchContext searchContext, Hits hits)
		throws SearchException {

		return getHitsProcessorRegistry().process(searchContext, hits);
	}

	private HitsProcessorRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(HitsProcessorRegistry.class);

		_serviceTracker.open();
	}

	private static final HitsProcessorRegistryUtil _instance =
		new HitsProcessorRegistryUtil();

	private final ServiceTracker<HitsProcessorRegistry, HitsProcessorRegistry>
		_serviceTracker;

}