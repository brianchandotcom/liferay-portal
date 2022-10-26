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

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.dto.v1_0.Channel;
import com.liferay.analytics.settings.rest.dto.v1_0.DataSource;
import com.liferay.analytics.settings.rest.internal.client.AnalyticsCloudClient;
import com.liferay.analytics.settings.rest.internal.client.model.AnalyticsChannel;
import com.liferay.analytics.settings.rest.internal.client.model.AnalyticsDataSource;
import com.liferay.analytics.settings.rest.internal.dto.v1_0.converter.ChannelDTOConverter;
import com.liferay.analytics.settings.rest.internal.manager.AnalyticsSettingsManager;
import com.liferay.analytics.settings.rest.resource.v1_0.ChannelResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;
import java.util.function.Function;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Riccardo Ferrari
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/channel.properties",
	scope = ServiceScope.PROTOTYPE, service = ChannelResource.class
)
public class ChannelResourceImpl extends BaseChannelResourceImpl {

	@Override
	public Page<Channel> getChannelsPage(
			String keywords, Filter filter, Pagination pagination)
		throws Exception {

		com.liferay.analytics.settings.rest.internal.client.pagination.Page
			<AnalyticsChannel> analyticsChannelsPage =
				_analyticsCloudClient.getAnalyticsChannelsPage(
					contextCompany.getCompanyId(), keywords,
					pagination.getPage() - 1, pagination.getPageSize());

		return Page.of(
			transform(
				analyticsChannelsPage.getItems(),
				analyticsChannel -> _channelDTOConverter.toDTO(
					new DefaultDTOConverterContext(
						false, null, dtoConverterRegistry, null,
						contextUser.getLocale(), null, contextUser),
					analyticsChannel)),
			pagination, analyticsChannelsPage.getTotalCount());
	}

	@Override
	public Channel patchChannel(Channel channel) throws Exception {
		DataSource[] dataSources = channel.getDataSources();

		if (dataSources.length > 1) {
			throw new PortalException("Unable to update multiple data sources");
		}

		DataSource dataSource = dataSources[0];

		AnalyticsChannel analyticsChannel =
			_analyticsCloudClient.updateAnalyticsChannel(
				channel.getChannelId(), dataSource.getCommerceChannelIds(),
				contextUser.getCompanyId(), dataSource.getDataSourceId(),
				contextAcceptLanguage.getPreferredLocale(),
				dataSource.getSiteIds());

		AnalyticsDataSource analyticsDataSource = _getAnalyticsDataSource(
			GetterUtil.getLong(dataSource.getDataSourceId()),
			analyticsChannel.getAnalyticsDataSources());

		_analyticsCloudClient.updateAnalyticsDataSourceDetails(
			null, contextCompany.getCompanyId(),
			ArrayUtil.isNotEmpty(analyticsDataSource.getCommerceChannelIds()),
			null, ArrayUtil.isNotEmpty(analyticsDataSource.getSiteIds()));

		AnalyticsConfiguration analyticsConfiguration =
			_analyticsSettingsManager.getAnalyticsConfiguration(
				contextUser.getCompanyId());

		_updateCommerceChannelGroups(
			analyticsConfiguration.syncedCommerceChannelIds(),
			channel.getChannelId(), contextCompany.getCompanyId(),
			analyticsDataSource.getCommerceChannelIds());

		_updateGroups(
			analyticsConfiguration.syncedGroupIds(), channel.getChannelId(),
			analyticsDataSource.getSiteIds());

		_analyticsSettingsManager.updateCompanyConfiguration(
			contextUser.getCompanyId(),
			HashMapBuilder.<String, Object>put(
				"syncedCommerceChannelIds",
				analyticsDataSource.getCommerceChannelIds()
			).put(
				"syncedGroupIds", analyticsDataSource.getSiteIds()
			).build());

		return _channelDTOConverter.toDTO(analyticsChannel);
	}

	@Override
	public Channel postChannel(Channel channel) throws Exception {
		return _channelDTOConverter.toDTO(
			_analyticsCloudClient.addAnalyticsChannel(
				contextCompany.getCompanyId(), channel.getName()));
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_commerceChannelClassNameId = _portal.getClassNameId(
			"com.liferay.commerce.product.model.CommerceChannel");
	}

	@Reference
	protected DTOConverterRegistry dtoConverterRegistry;

	private AnalyticsDataSource _getAnalyticsDataSource(
		long analyticsDataSourceId,
		AnalyticsDataSource[] analyticsDataSources) {

		for (AnalyticsDataSource analyticsDataSource : analyticsDataSources) {
			if (analyticsDataSource.getId() == analyticsDataSourceId) {
				return analyticsDataSource;
			}
		}

		throw new RuntimeException("Unable to get analytics data source");
	}

	private void _updateCommerceChannelGroups(
			String[] analyticsConfigurationCommerceChannelIds, String channelId,
			long companyId, Long[] dataSourceCommerceChannelIds)
		throws Exception {

		_updateTypeSetting(
			channelId,
			commerceChannelId -> _groupLocalService.fetchGroup(
				companyId, _commerceChannelClassNameId, commerceChannelId),
			ArrayUtil.filter(
				dataSourceCommerceChannelIds,
				commerceChannelId -> !ArrayUtil.contains(
					analyticsConfigurationCommerceChannelIds,
					String.valueOf(commerceChannelId))),
			false);

		_updateTypeSetting(
			channelId,
			commerceChannelId -> _groupLocalService.fetchGroup(
				companyId, _commerceChannelClassNameId, commerceChannelId),
			ArrayUtil.filter(
				analyticsConfigurationCommerceChannelIds,
				commerceChannelId -> !ArrayUtil.contains(
					dataSourceCommerceChannelIds,
					Long.valueOf(commerceChannelId))),
			true);
	}

	private void _updateGroups(
			String[] analyticsConfigurationGroupIds, String channelId,
			Long[] dataSourceGroupIds)
		throws Exception {

		_updateTypeSetting(
			channelId, groupId -> _groupLocalService.fetchGroup(groupId),
			ArrayUtil.filter(
				dataSourceGroupIds,
				groupId -> !ArrayUtil.contains(
					analyticsConfigurationGroupIds, String.valueOf(groupId))),
			false);

		_updateTypeSetting(
			channelId, groupId -> _groupLocalService.fetchGroup(groupId),
			ArrayUtil.filter(
				analyticsConfigurationGroupIds,
				groupId -> !ArrayUtil.contains(
					dataSourceGroupIds, Long.valueOf(groupId))),
			true);
	}

	private <T> void _updateTypeSetting(
			String channelId, Function<Long, Group> fetchGroupFunction,
			T[] groupIds, boolean remove)
		throws Exception {

		for (T groupId : groupIds) {
			Group group = fetchGroupFunction.apply(GetterUtil.getLong(groupId));

			if (group == null) {
				continue;
			}

			UnicodeProperties typeSettingsUnicodeProperties =
				group.getTypeSettingsProperties();

			if (remove) {
				String analyticsChannelId =
					typeSettingsUnicodeProperties.remove("analyticsChannelId");

				if ((analyticsChannelId != null) &&
					!channelId.equals(analyticsChannelId)) {

					throw new IllegalArgumentException("Invalid channel ID");
				}
			}
			else {
				typeSettingsUnicodeProperties.setProperty(
					"analyticsChannelId", channelId);
			}

			_groupLocalService.updateGroup(group);
		}
	}

	@Reference
	private AnalyticsCloudClient _analyticsCloudClient;

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	@Reference
	private ChannelDTOConverter _channelDTOConverter;

	private long _commerceChannelClassNameId;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

}