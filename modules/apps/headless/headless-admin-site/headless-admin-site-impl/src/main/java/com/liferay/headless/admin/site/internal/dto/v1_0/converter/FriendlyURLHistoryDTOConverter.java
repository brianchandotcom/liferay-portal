/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.headless.admin.site.dto.v1_0.FriendlyUrlHistory;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "dto.class.name=com.liferay.friendly.url.model.FriendlyURLEntry",
	service = DTOConverter.class
)
public class FriendlyURLHistoryDTOConverter
	implements DTOConverter<FriendlyURLEntry, FriendlyUrlHistory> {

	@Override
	public String getContentType() {
		return FriendlyUrlHistory.class.getSimpleName();
	}

	@Override
	public FriendlyUrlHistory toDTO(
			DTOConverterContext dtoConverterContext,
			FriendlyURLEntry friendlyURLEntry)
		throws Exception {

		return new FriendlyUrlHistory() {
			{
				setFriendlyUrlPath_i18n(
					friendlyURLEntry::getLanguageIdToUrlTitleMap);
			}
		};
	}

}