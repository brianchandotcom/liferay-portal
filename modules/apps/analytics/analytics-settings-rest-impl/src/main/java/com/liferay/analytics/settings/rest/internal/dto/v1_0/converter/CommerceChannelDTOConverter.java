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

import com.liferay.analytics.settings.rest.dto.v1_0.CommerceChannel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	property = "dto.class.name=com.liferay.portal.kernel.model.Group",
	service = {CommerceChannelDTOConverter.class, DTOConverter.class}
)
public class CommerceChannelDTOConverter
	implements DTOConverter<Group, CommerceChannel> {

	@Override
	public String getContentType() {
		return CommerceChannel.class.getSimpleName();
	}

	@Override
	public CommerceChannel toDTO(
			DTOConverterContext dtoConverterContext, Group group)
		throws Exception {

		CommerceChannelDTOConverterContext commerceChannelDTOConverterContext =
			(CommerceChannelDTOConverterContext)dtoConverterContext;

		UnicodeProperties typeSettingsUnicodeProperties =
			group.getTypeSettingsProperties();

		return new CommerceChannel() {
			{
				id = group.getClassPK();
				name = group.getDescriptiveName();

				setChannelName(
					() -> {
						String analyticsChannelId =
							typeSettingsUnicodeProperties.getProperty(
								"analyticsChannelId", null);

						return commerceChannelDTOConverterContext.
							getChannelName(
								GetterUtil.getLong(analyticsChannelId));
					});
				setSiteName(
					() -> {
						String siteGroupId =
							typeSettingsUnicodeProperties.getProperty(
								"siteGroupId", null);

						if (siteGroupId == null) {
							return null;
						}

						Group siteGroup = _groupLocalService.getGroup(
							GetterUtil.getLong(siteGroupId));

						return siteGroup.getDescriptiveName();
					});
			}
		};
	}

	@Reference
	private GroupLocalService _groupLocalService;

}