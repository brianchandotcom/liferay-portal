/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.hits;

import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;

/**
 * @author Michael C. Han
 */
public class HitsProcessorRegistryUtil {

	public static boolean process(SearchContext searchContext, Hits hits)
		throws SearchException {

		HitsProcessorRegistry hitsProcessorRegistry =
			_hitsProcessorRegistrySnapshot.get();

		return hitsProcessorRegistry.process(searchContext, hits);
	}

	private static final Snapshot<HitsProcessorRegistry>
		_hitsProcessorRegistrySnapshot = new Snapshot<>(
			HitsProcessorRegistryUtil.class, HitsProcessorRegistry.class);

}