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
import com.liferay.portal.kernel.util.ProxyFactory;

/**
 * @author Michael C. Han
 */
public class HitsProcessorRegistryUtil {

	/**
	 * @deprecated As of 7.1.0, with no direct replacement
	 */
	@Deprecated
	public static HitsProcessorRegistry getHitsProcessorRegistry() {
		return _hitsProcessorRegistry;
	}

	public static boolean process(SearchContext searchContext, Hits hits)
		throws SearchException {

		return _hitsProcessorRegistry.process(searchContext, hits);
	}

	private static volatile HitsProcessorRegistry _hitsProcessorRegistry =
		ProxyFactory.newServiceTrackedInstance(
			HitsProcessorRegistry.class, HitsProcessorRegistryUtil.class,
			"_hitsProcessorRegistry");

}