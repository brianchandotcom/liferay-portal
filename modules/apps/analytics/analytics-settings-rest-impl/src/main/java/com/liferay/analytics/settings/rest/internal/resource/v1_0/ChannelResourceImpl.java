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
import com.liferay.analytics.settings.rest.internal.dto.v1_0.converter.ChannelDTOConverterContext;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.analytics.settings.rest.resource.v1_0.ChannelResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Objects;

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
			String keywords, Pagination pagination, Sort[] sorts)
		throws Exception {

		com.liferay.analytics.settings.rest.internal.client.pagination.Page
			<AnalyticsChannel> analyticsChannelsPage =
				_analyticsCloudClient.getAnalyticsChannelsPage(
					contextCompany.getCompanyId(), keywords,
					pagination.getPage() - 1, pagination.getPageSize(), sorts);
		AnalyticsConfiguration analyticsConfiguration =
			_analyticsSettingsManager.getAnalyticsConfiguration(
				contextCompany.getCompanyId());

		return Page.of(
			transform(
				analyticsChannelsPage.getItems(),
				analyticsChannel -> _channelDTOConverter.toDTO(
					new ChannelDTOConverterContext(
						analyticsChannel.getId(),
						contextAcceptLanguage.getPreferredLocale(),
						analyticsConfiguration.commerceSyncEnabledChannelIds()),
					analyticsChannel)),
			pagination, analyticsChannelsPage.getTotalCount());
	}

	@Override
	public Channel patchChannel(Channel channel) throws Exception {
		AnalyticsConfiguration analyticsConfiguration =
			_analyticsSettingsManager.getAnalyticsConfiguration(
				contextCompany.getCompanyId());

		String[] commerceSyncEnabledChannelIds =
			analyticsConfiguration.commerceSyncEnabledChannelIds();

		if (channel.getCommerceSyncEnabled() != null) {
			boolean commerceSyncEnabled = ArrayUtil.contains(
				commerceSyncEnabledChannelIds, channel.getChannelId());

			if (channel.getCommerceSyncEnabled() && !commerceSyncEnabled) {
				commerceSyncEnabledChannelIds = ArrayUtil.append(
					commerceSyncEnabledChannelIds, channel.getChannelId());

				_analyticsSettingsManager.updateCompanyConfiguration(
					contextCompany.getCompanyId(),
					HashMapBuilder.<String, Object>put(
						"commerceSyncEnabledChannelIds",
						commerceSyncEnabledChannelIds
					).build());
			}

			if (!channel.getCommerceSyncEnabled() && commerceSyncEnabled) {
				commerceSyncEnabledChannelIds = ArrayUtil.remove(
					commerceSyncEnabledChannelIds, channel.getChannelId());

				_analyticsSettingsManager.updateCompanyConfiguration(
					contextCompany.getCompanyId(),
					HashMapBuilder.<String, Object>put(
						"commerceSyncEnabledChannelIds",
						commerceSyncEnabledChannelIds
					).build());
			}
		}

		DataSource[] dataSources = channel.getDataSources();

		if (ArrayUtil.isEmpty(dataSources)) {
			return _channelDTOConverter.toDTO(
				new ChannelDTOConverterContext(
					channel.getChannelId(),
					contextAcceptLanguage.getPreferredLocale(),
					commerceSyncEnabledChannelIds),
				_analyticsCloudClient.updateAnalyticsChannel(
					channel.getChannelId(),
					_analyticsSettingsManager.getCommerceChannelIds(
						channel.getChannelId(), contextUser.getCompanyId()),
					contextUser.getCompanyId(),
					analyticsConfiguration.liferayAnalyticsDataSourceId(),
					contextAcceptLanguage.getPreferredLocale(),
					_analyticsSettingsManager.getSiteIds(
						channel.getChannelId(),
						contextCompany.getCompanyId())));
		}

		if (dataSources.length > 1) {
			throw new PortalException("Unable to update multiple data sources");
		}

		DataSource dataSource = dataSources[0];

		if (!Objects.equals(
				dataSource.getDataSourceId(),
				analyticsConfiguration.liferayAnalyticsDataSourceId())) {

			throw new PortalException("Invalid data source ID");
		}

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

		_analyticsSettingsManager.updateCompanyConfiguration(
			contextUser.getCompanyId(),
			HashMapBuilder.<String, Object>put(
				"syncedCommerceChannelIds",
				_analyticsSettingsManager.updateCommerceChannelIds(
					channel.getChannelId(), contextCompany.getCompanyId(),
					analyticsDataSource.getCommerceChannelIds())
			).put(
				"syncedGroupIds",
				_analyticsSettingsManager.updateSiteIds(
					channel.getChannelId(), contextCompany.getCompanyId(),
					analyticsDataSource.getSiteIds())
			).build());

		return _channelDTOConverter.toDTO(
			new ChannelDTOConverterContext(
				channel.getChannelId(),
				contextAcceptLanguage.getPreferredLocale(),
				commerceSyncEnabledChannelIds),
			analyticsChannel);
	}

	@Override
	public Channel postChannel(Channel channel) throws Exception {
		AnalyticsConfiguration analyticsConfiguration =
			_analyticsSettingsManager.getAnalyticsConfiguration(
				contextCompany.getCompanyId());

		return _channelDTOConverter.toDTO(
			new ChannelDTOConverterContext(
				channel.getChannelId(),
				contextAcceptLanguage.getPreferredLocale(),
				analyticsConfiguration.commerceSyncEnabledChannelIds()),
			_analyticsCloudClient.addAnalyticsChannel(
				contextCompany.getCompanyId(), channel.getName()));
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

	@Reference
	private AnalyticsCloudClient _analyticsCloudClient;

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	@Reference
	private ChannelDTOConverter _channelDTOConverter;

}