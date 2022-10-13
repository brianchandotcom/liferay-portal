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
import com.liferay.analytics.settings.rest.resource.v1_0.CommerceChannelResource;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	public Page<CommerceChannel> getCommerceChannelsPage(Pagination pagination)
		throws Exception {

		com.liferay.analytics.settings.rest.internal.client.pagination.Page
			<AnalyticsChannel> analyticsChannelsPage =
				_analyticsCloudClient.getAnalyticsChannelsPage(
					contextCompany.getCompanyId(), null, 0, 100);

		Collection<AnalyticsChannel> analyticsChannels =
			analyticsChannelsPage.getItems();

		Stream<AnalyticsChannel> stream = analyticsChannels.stream();

		Map<Long, String> analyticsChannelsMap = stream.collect(
			Collectors.toMap(
				AnalyticsChannel::getId, AnalyticsChannel::getName));

		List<Group> groups = _groupService.search(
			contextCompany.getCompanyId(), _CLASS_NAME_IDS, null,
			_getGroupParams(), pagination.getStartPosition(),
			pagination.getEndPosition(), null);

		int count = _groupService.searchCount(
			contextCompany.getCompanyId(), _CLASS_NAME_IDS, null,
			_getGroupParams());

		return Page.of(
			transform(
				groups,
				group -> _commerceChannelDTOConverter.toDTO(
					new CommerceChannelDTOConverterContext(
						group.getGroupId(),
						contextAcceptLanguage.getPreferredLocale(),
						analyticsChannelsMap),
					group)),
			pagination, count);
	}

	private LinkedHashMap<String, Object> _getGroupParams() {
		return LinkedHashMapBuilder.<String, Object>put(
			"active", Boolean.TRUE
		).build();
	}

	private static final long[] _CLASS_NAME_IDS;

	static {
		_CLASS_NAME_IDS = new long[] {
			PortalUtil.getClassNameId(
				"com.liferay.commerce.product.model.CommerceChannel")
		};
	}

	@Reference
	private AnalyticsCloudClient _analyticsCloudClient;

	@Reference
	private CommerceChannelDTOConverter _commerceChannelDTOConverter;

	@Reference
	private GroupService _groupService;

}