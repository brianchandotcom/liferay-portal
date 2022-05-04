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

package com.liferay.headless.admin.address.internal.dto.v1_0.converter;

import com.liferay.headless.admin.address.dto.v1_0.Region;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "dto.class.name=com.liferay.portal.kernel.model.Region",
	service = {DTOConverter.class, RegionResourceDTOConverter.class}
)
public class RegionResourceDTOConverter
	implements DTOConverter<com.liferay.portal.kernel.model.Region, Region> {

	@Override
	public String getContentType() {
		return Region.class.getSimpleName();
	}

	@Override
	public Region toDTO(
			DTOConverterContext dtoConverterContext,
			com.liferay.portal.kernel.model.Region serviceBuilderRegion)
		throws Exception {

		return new Region() {
			{
				active = serviceBuilderRegion.getActive();
				countryId = serviceBuilderRegion.getCountryId();
				id = serviceBuilderRegion.getRegionId();
				name = serviceBuilderRegion.getName();
				position = serviceBuilderRegion.getPosition();
				regionCode = serviceBuilderRegion.getRegionCode();
				title_i18n = serviceBuilderRegion.getLanguageIdToTitleMap();
			}
		};
	}

	@Reference
	private RegionService _regionService;

}