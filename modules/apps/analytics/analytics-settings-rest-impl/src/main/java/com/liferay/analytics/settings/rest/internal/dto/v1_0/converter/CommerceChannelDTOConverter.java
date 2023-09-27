/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	service = DTOConverter.class
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

						Group siteGroup = _groupLocalService.fetchGroup(
							GetterUtil.getLong(siteGroupId));

						if (siteGroup == null) {
							return null;
						}

						return siteGroup.getDescriptiveName();
					});
			}
		};
	}

	@Reference
	private GroupLocalService _groupLocalService;

}