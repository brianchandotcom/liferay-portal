/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.hits;

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Michael C. Han
 */
public class HitsProcessorRegistryUtil {

	public static boolean process(SearchContext searchContext, Hits hits)
		throws SearchException {

		return _hitsProcessorRegistry.process(searchContext, hits);
	}

	private static volatile HitsProcessorRegistry _hitsProcessorRegistry =
		ServiceProxyFactory.newServiceTrackedInstance(
			HitsProcessorRegistry.class, HitsProcessorRegistryUtil.class,
			"_hitsProcessorRegistry", false);

}