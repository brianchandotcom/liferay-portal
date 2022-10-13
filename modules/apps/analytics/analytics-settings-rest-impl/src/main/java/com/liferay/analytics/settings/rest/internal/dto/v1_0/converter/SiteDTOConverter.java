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

import com.liferay.analytics.settings.rest.dto.v1_0.Site;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Riccardo Ferrari
 */
@Component(
	property = "dto.class.name=com.liferay.portal.kernel.model.Group",
	service = {DTOConverter.class, SiteDTOConverter.class}
)
public class SiteDTOConverter implements DTOConverter<Group, Site> {

	@Override
	public String getContentType() {
		return Site.class.getSimpleName();
	}

	@Override
	public Site toDTO(DTOConverterContext dtoConverterContext, Group group)
		throws Exception {

		SiteDTOConverterContext siteDTOConverterContext =
			(SiteDTOConverterContext)dtoConverterContext;

		UnicodeProperties typeSettingsUnicodeProperties =
			group.getTypeSettingsProperties();

		return new Site() {
			{
				setChannelName(
					() -> {
						String analyticsChannelId =
							typeSettingsUnicodeProperties.getProperty(
								"analyticsChannelId", null);

						return siteDTOConverterContext.getChannelName(
							GetterUtil.getLong(analyticsChannelId));
					});

				friendlyURL = group.getFriendlyURL();
				id = group.getGroupId();
				name = group.getDescriptiveName();
			}
		};
	}

}