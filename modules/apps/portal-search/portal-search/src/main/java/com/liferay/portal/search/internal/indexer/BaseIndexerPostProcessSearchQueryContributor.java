/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.indexer;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.PostProcessSearchQueryContributor;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.search.internal.indexer.helper.PostProcessSearchQueryContributorHelper;

import java.util.Collection;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = PostProcessSearchQueryContributor.class)
public class BaseIndexerPostProcessSearchQueryContributor
	implements PostProcessSearchQueryContributor {

	@Override
	public void contribute(
		BooleanQuery booleanQuery, BooleanFilter booleanFilter,
		Collection<Indexer<?>> indexers, SearchContext searchContext) {

		postProcessSearchQueryContributorHelper.contribute(
			booleanQuery, booleanFilter, indexers, searchContext);
	}

	@Reference
	protected PostProcessSearchQueryContributorHelper
		postProcessSearchQueryContributorHelper;

}