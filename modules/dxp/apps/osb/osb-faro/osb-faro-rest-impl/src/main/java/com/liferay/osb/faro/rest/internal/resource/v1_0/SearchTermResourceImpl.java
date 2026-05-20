/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.resource.v1_0;

import com.liferay.osb.faro.rest.dto.v1_0.SearchTerm;
import com.liferay.osb.faro.rest.internal.dto.v1_0.converter.FaroDTOConverterContext;
import com.liferay.osb.faro.rest.internal.dto.v1_0.util.FaroPaginationUtil;
import com.liferay.osb.faro.rest.internal.graphql.client.FaroGraphQLClient;
import com.liferay.osb.faro.rest.internal.graphql.dto.GetWorkspaceGroupChannelSearchTermsPageResponse;
import com.liferay.osb.faro.rest.resource.v1_0.SearchTermResource;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Leslie Wong
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/search-term.properties",
	scope = ServiceScope.PROTOTYPE, service = SearchTermResource.class
)
public class SearchTermResourceImpl extends BaseSearchTermResourceImpl {

	@Override
	public Page<SearchTerm> getWorkspaceGroupChannelSearchTermsPage(
			Long groupId, String channelId, String rangeEnd, String rangeKey,
			String rangeStart, Pagination pagination)
		throws Exception {

		int cur = FaroPaginationUtil.getCur(pagination);
		int delta = FaroPaginationUtil.getDelta(pagination);

		GetWorkspaceGroupChannelSearchTermsPageResponse
			getWorkspaceGroupChannelSearchTermsPageResponse =
				_faroGraphQLClient.execute(
					GetWorkspaceGroupChannelSearchTermsPageResponse.class,
					_faroProjectLocalService.getFaroProjectByGroupId(groupId),
					"getWorkspaceGroupChannelSearchTermsPage",
					HashMapBuilder.<String, Object>put(
						"channelId", channelId
					).put(
						"rangeEnd", rangeEnd
					).put(
						"rangeKey", TimeRange.getRangeKey(rangeKey)
					).put(
						"rangeStart", rangeStart
					).put(
						"size", delta
					).put(
						"start", (cur - 1) * delta
					).build());

		GetWorkspaceGroupChannelSearchTermsPageResponse.CompositionBag
			compositionBag =
				getWorkspaceGroupChannelSearchTermsPageResponse.
					getCompositionBag();

		if (compositionBag == null) {
			return Page.of(Collections.emptyList(), pagination, 0);
		}

		Integer total = compositionBag.getTotal();

		if (total == null) {
			total = 0;
		}

		return Page.of(
			transform(
				compositionBag.getCompositions(),
				composition -> _searchTermDTOConverter.toDTO(
					new FaroDTOConverterContext(
						contextAcceptLanguage.isAcceptAllLanguages(), null,
						contextAcceptLanguage.getPreferredLocale()),
					composition)),
			pagination, total);
	}

	@Reference
	private FaroGraphQLClient _faroGraphQLClient;

	@Reference
	private FaroProjectLocalService _faroProjectLocalService;

	@Reference(
		target = "(component.name=com.liferay.osb.faro.rest.internal.dto.v1_0.converter.SearchTermDTOConverter)"
	)
	private DTOConverter
		<GetWorkspaceGroupChannelSearchTermsPageResponse.Composition,
		 SearchTerm> _searchTermDTOConverter;

}

// LIFERAY-REST-BUILDER-HASH:1916387633