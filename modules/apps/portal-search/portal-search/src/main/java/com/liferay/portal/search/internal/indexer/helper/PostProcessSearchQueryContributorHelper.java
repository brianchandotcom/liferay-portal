/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.indexer.helper;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;

import java.util.Collection;

/**
 * @author André de Oliveira
 */
public interface PostProcessSearchQueryContributorHelper {

	public void contribute(
		BooleanQuery booleanQuery, BooleanFilter booleanFilter,
		Collection<Indexer<?>> indexers, SearchContext searchContext);

}