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

package com.liferay.analytics.settings.rest.internal.resource.v1_0;

import com.liferay.analytics.settings.rest.dto.v1_0.CommerceChannel;
import com.liferay.analytics.settings.rest.internal.client.AnalyticsCloudClient;
import com.liferay.analytics.settings.rest.internal.client.model.AnalyticsChannel;
import com.liferay.analytics.settings.rest.internal.dto.v1_0.converter.CommerceChannelDTOConverter;
import com.liferay.analytics.settings.rest.internal.dto.v1_0.converter.CommerceChannelDTOConverterContext;
import com.liferay.analytics.settings.rest.internal.util.SortUtil;
import com.liferay.analytics.settings.rest.resource.v1_0.CommerceChannelResource;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Riccardo Ferrari
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/commerce-channel.properties",
	scope = ServiceScope.PROTOTYPE, service = CommerceChannelResource.class
)
public class CommerceChannelResourceImpl
	extends BaseCommerceChannelResourceImpl {

	@Override
	public Page<CommerceChannel> getCommerceChannelsPage(
			String keywords, Pagination pagination, Sort[] sorts)
		throws Exception {

		com.liferay.analytics.settings.rest.internal.client.pagination.Page
			<AnalyticsChannel> analyticsChannelsPage =
				_analyticsCloudClient.getAnalyticsChannelsPage(
					contextCompany.getCompanyId(), null, 0, 100, null);

		Collection<AnalyticsChannel> analyticsChannels =
			analyticsChannelsPage.getItems();

		Stream<AnalyticsChannel> stream = analyticsChannels.stream();

		Map<Long, String> analyticsChannelsMap = stream.collect(
			Collectors.toMap(
				AnalyticsChannel::getId, AnalyticsChannel::getName));

		return Page.of(
			transform(
				_groupService.search(
					contextCompany.getCompanyId(), _classNameIds, keywords,
					_getParams(), pagination.getStartPosition(),
					pagination.getEndPosition(),
					SortUtil.getOrderByComparator(
						GroupTable.INSTANCE.getTableName(), sorts)),
				group -> _commerceChannelDTOConverter.toDTO(
					new CommerceChannelDTOConverterContext(
						group.getGroupId(),
						contextAcceptLanguage.getPreferredLocale(),
						analyticsChannelsMap),
					group)),
			pagination,
			_groupService.searchCount(
				contextCompany.getCompanyId(), _classNameIds, keywords,
				_getParams()));
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_classNameIds = new long[] {
			_portal.getClassNameId(
				"com.liferay.commerce.product.model.CommerceChannel")
		};
	}

	private LinkedHashMap<String, Object> _getParams() {
		return LinkedHashMapBuilder.<String, Object>put(
			"active", Boolean.TRUE
		).build();
	}

	@Reference
	private AnalyticsCloudClient _analyticsCloudClient;

	private long[] _classNameIds;

	@Reference
	private CommerceChannelDTOConverter _commerceChannelDTOConverter;

	@Reference
	private GroupService _groupService;

	@Reference
	private Portal _portal;

}