/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.dto.v1_0.util;

import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalServiceUtil;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

/**
 * @author Carolina Barbosa
 */
public class ListEntryUtil {

	public static ListEntry toListEntry(
		DTOConverterContext dtoConverterContext, String key,
		long listTypeDefinitionId) {

		if (StringUtil.equals(key, StringPool.BLANK)) {
			return new ListEntry() {
				{
					setKey(() -> StringPool.BLANK);
					setName(() -> StringPool.BLANK);
				}
			};
		}

		ListTypeEntry listTypeEntry =
			ListTypeEntryLocalServiceUtil.fetchListTypeEntry(
				listTypeDefinitionId, key);

		if (listTypeEntry == null) {
			return null;
		}

		return new ListEntry() {
			{
				setKey(listTypeEntry::getKey);
				setName(
					() -> listTypeEntry.getName(
						dtoConverterContext.getLocale()));
				setName_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						dtoConverterContext.isAcceptAllLanguages(),
						listTypeEntry.getNameMap()));
			}
		};
	}

}