/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.resource.v1_0;

import com.liferay.osb.faro.rest.dto.v1_0.PageMetric;
import com.liferay.osb.faro.rest.internal.dto.v1_0.converter.FaroDTOConverterContext;
import com.liferay.osb.faro.rest.internal.dto.v1_0.util.FaroPaginationUtil;
import com.liferay.osb.faro.rest.internal.graphql.client.FaroGraphQLClient;
import com.liferay.osb.faro.rest.internal.graphql.dto.GetWorkspaceGroupPagesPageResponse;
import com.liferay.osb.faro.rest.resource.v1_0.PageMetricResource;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.portal.kernel.search.Sort;
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
	properties = "OSGI-INF/liferay/rest/v1_0/page-metric.properties",
	scope = ServiceScope.PROTOTYPE, service = PageMetricResource.class
)
public class PageMetricResourceImpl extends BasePageMetricResourceImpl {

	@Override
	public Page<PageMetric> getWorkspaceGroupPagesPage(
			Long groupId, String channelId, String dataSourceId,
			String rangeEnd, Integer rangeKey, String rangeStart, String search,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		int cur = FaroPaginationUtil.getCur(pagination);
		int delta = FaroPaginationUtil.getDelta(pagination);

		GetWorkspaceGroupPagesPageResponse getWorkspaceGroupPagesPageResponse =
			_faroGraphQLClient.execute(
				GetWorkspaceGroupPagesPageResponse.class,
				_faroProjectLocalService.getFaroProjectByGroupId(groupId),
				"getWorkspaceGroupPagesPage",
				HashMapBuilder.<String, Object>put(
					"channelId", channelId
				).put(
					"dataSourceId", dataSourceId
				).put(
					"keywords", search
				).put(
					"rangeEnd", rangeEnd
				).put(
					"rangeKey", rangeKey
				).put(
					"rangeStart", rangeStart
				).put(
					"size", delta
				).put(
					"sort", FaroPaginationUtil.toGraphQLSort(sorts)
				).put(
					"start", (cur - 1) * delta
				).build());

		GetWorkspaceGroupPagesPageResponse.PageMetricBag pageMetricBag =
			getWorkspaceGroupPagesPageResponse.getPageMetricBag();

		if (pageMetricBag == null) {
			return Page.of(Collections.emptyList(), pagination, 0);
		}

		Integer total = pageMetricBag.getTotal();

		if (total == null) {
			total = 0;
		}

		return Page.of(
			transform(
				pageMetricBag.getPageMetrics(),
				pageMetric -> _pageMetricDTOConverter.toDTO(
					new FaroDTOConverterContext(
						contextAcceptLanguage.isAcceptAllLanguages(),
						pageMetric.getAssetId(),
						contextAcceptLanguage.getPreferredLocale()),
					pageMetric)),
			pagination, total);
	}

	@Reference
	private FaroGraphQLClient _faroGraphQLClient;

	@Reference
	private FaroProjectLocalService _faroProjectLocalService;

	@Reference(
		target = "(component.name=com.liferay.osb.faro.rest.internal.dto.v1_0.converter.PageMetricDTOConverter)"
	)
	private DTOConverter
		<GetWorkspaceGroupPagesPageResponse.PageMetric, PageMetric>
			_pageMetricDTOConverter;

}