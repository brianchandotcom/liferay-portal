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

import com.liferay.analytics.settings.rest.dto.v1_0.DataSource;
import com.liferay.analytics.settings.rest.internal.client.model.AnalyticsDataSource;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Riccardo Ferrari
 */
@Component(
	property = "dto.class.name=AnalyticsDataSource",
	service = {DataSourceDTOConverter.class, DTOConverter.class}
)
public class DataSourceDTOConverter
	implements DTOConverter<AnalyticsDataSource, DataSource> {

	@Override
	public String getContentType() {
		return DataSource.class.getSimpleName();
	}

	@Override
	public DataSource toDTO(
			DTOConverterContext dtoConverterContext,
			AnalyticsDataSource analyticsDataSource)
		throws Exception {

		return new DataSource() {
			{
				commerceChannelIds =
					analyticsDataSource.getCommerceChannelIds();
				dataSourceId = String.valueOf(analyticsDataSource.getId());
				siteIds = analyticsDataSource.getSiteIds();
			}
		};
	}

}