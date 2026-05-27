/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.dto.v1_0.converter;

import com.liferay.osb.faro.rest.dto.v1_0.PageMetric;
import com.liferay.osb.faro.rest.internal.graphql.dto.GetWorkspaceGroupChannelPagesPageResponse;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leslie Wong
 */
@Component(
	property = "dto.class.name=com.liferay.osb.faro.rest.internal.graphql.dto.GetWorkspaceGroupChannelPagesPageResponse$PageMetric",
	service = DTOConverter.class
)
public class PageMetricDTOConverter
	implements DTOConverter
		<GetWorkspaceGroupChannelPagesPageResponse.PageMetric, PageMetric> {

	@Override
	public String getContentType() {
		return PageMetric.class.getSimpleName();
	}

	@Override
	public PageMetric toDTO(
		DTOConverterContext dtoConverterContext,
		GetWorkspaceGroupChannelPagesPageResponse.PageMetric pageMetric) {

		if (pageMetric == null) {
			return null;
		}

		return new PageMetric() {
			{
				setAssetId(pageMetric::getAssetId);
				setAssetTitle(pageMetric::getAssetTitle);
				setAvgTimeOnPage(
					() -> _value(pageMetric.getAvgTimeOnPageMetric()));
				setBounceRate(() -> _value(pageMetric.getBounceRateMetric()));
				setDataSourceId(pageMetric::getDataSourceId);
				setDirectAccess(
					() -> _value(pageMetric.getDirectAccessMetric()));
				setEntrances(() -> _value(pageMetric.getEntrancesMetric()));
				setExitRate(() -> _value(pageMetric.getExitRateMetric()));
				setIndirectAccess(
					() -> _value(pageMetric.getIndirectAccessMetric()));
				setUrls(() -> _toUrlsArray(pageMetric.getUrls()));
				setViews(() -> _value(pageMetric.getViewsMetric()));
				setViewsTrendPercentage(
					() -> _trendPercentage(pageMetric.getViewsMetric()));
				setVisitors(() -> _value(pageMetric.getVisitorsMetric()));
				setVisitorsTrendPercentage(
					() -> _trendPercentage(pageMetric.getVisitorsMetric()));
			}
		};
	}

	private String[] _toUrlsArray(List<String> urls) {
		if (ListUtil.isEmpty(urls)) {
			return null;
		}

		return urls.toArray(new String[0]);
	}

	private Double _trendPercentage(
		GetWorkspaceGroupChannelPagesPageResponse.Metric metric) {

		if ((metric == null) || (metric.getTrend() == null)) {
			return null;
		}

		return metric.getTrend(
		).getPercentage();
	}

	private Double _value(
		GetWorkspaceGroupChannelPagesPageResponse.Metric metric) {

		if (metric == null) {
			return null;
		}

		return metric.getValue();
	}

}