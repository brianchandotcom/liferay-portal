/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.search.spi.searcher;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.spi.searcher.SearchRequestContributor;

import java.util.function.Function;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gustavo Lima
 */
@Component(
	property = "search.request.contributor.id=com.liferay.depot",
	service = SearchRequestContributor.class
)
public class DepotSearchRequestContributor implements SearchRequestContributor {

	@Override
	public SearchRequest contribute(SearchRequest searchRequest) {
		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(searchRequest);

		SearchContext searchContext = searchRequestBuilder.withSearchContextGet(
			Function.identity());

		long[] groupIds = searchContext.getGroupIds();

		if (ArrayUtil.isEmpty(groupIds)) {
			return searchRequest;
		}

		for (long groupId : groupIds) {
			searchContext.setGroupIds(
				ArrayUtil.append(
					searchContext.getGroupIds(),
					TransformUtil.transformToLongArray(
						_depotEntryGroupRelLocalService.
							getSearchableDepotEntryGroupRels(
								groupId, 0,
								_depotEntryGroupRelLocalService.
									getSearchableDepotEntryGroupRelsCount(
										groupId)),
						depotEntryGroupRel -> {
							DepotEntry depotEntry =
								_depotEntryLocalService.fetchDepotEntry(
									depotEntryGroupRel.getDepotEntryId());

							return depotEntry.getGroupId();
						})));
		}

		return searchRequest;
	}

	@Reference
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}