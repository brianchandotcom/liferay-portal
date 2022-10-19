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

package com.liferay.analytics.settings.rest.internal.dto.v1_0.converter;

import com.liferay.analytics.settings.rest.dto.v1_0.Channel;
import com.liferay.analytics.settings.rest.dto.v1_0.DataSource;
import com.liferay.analytics.settings.rest.internal.client.model.AnalyticsChannel;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	property = "dto.class.name=AnalyticsChannel",
	service = {ChannelDTOConverter.class, DTOConverter.class}
)
public class ChannelDTOConverter
	implements DTOConverter<AnalyticsChannel, Channel> {

	@Override
	public String getContentType() {
		return Channel.class.getSimpleName();
	}

	@Override
	public Channel toDTO(
			DTOConverterContext dtoConverterContext,
			AnalyticsChannel analyticsChannel)
		throws Exception {

		return new Channel() {
			{
				channelId = String.valueOf(analyticsChannel.getId());
				dataSources = TransformUtil.transform(
					analyticsChannel.getAnalyticsDataSources(),
					analyticsDataSource -> _dataSourceDTOConverter.toDTO(
						analyticsDataSource),
					DataSource.class);
				name = analyticsChannel.getName();
			}
		};
	}

	@Reference
	private DataSourceDTOConverter _dataSourceDTOConverter;

}