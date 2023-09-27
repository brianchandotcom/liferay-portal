/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.list.type.entry.util;

import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalServiceUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Locale;
import java.util.Map;

/**
 * @author Murilo Stodolni
 */
public class ListTypeEntryUtil {

	public static ListTypeEntry createListTypeEntry(String key) {
		return createListTypeEntry(
			null, key, LocalizedMapUtil.getLocalizedMap(key));
	}

	public static ListTypeEntry createListTypeEntry(
		String key, Map<Locale, String> nameMap) {

		return createListTypeEntry(null, key, nameMap);
	}

	public static ListTypeEntry createListTypeEntry(String key, String name) {
		return createListTypeEntry(
			null, key, LocalizedMapUtil.getLocalizedMap(name));
	}

	public static ListTypeEntry createListTypeEntry(
		String externalReferenceCode, String key, Map<Locale, String> nameMap) {

		ListTypeEntry listTypeEntry =
			ListTypeEntryLocalServiceUtil.createListTypeEntry(0L);

		listTypeEntry.setExternalReferenceCode(externalReferenceCode);
		listTypeEntry.setKey(key);
		listTypeEntry.setNameMap(nameMap);

		return listTypeEntry;
	}

	public static String getListTypeEntryExternalReferenceCode(
		long listTypeDefinitionId, String listTypeEntryKey) {

		ListTypeEntry listTypeEntry =
			ListTypeEntryLocalServiceUtil.fetchListTypeEntry(
				listTypeDefinitionId, listTypeEntryKey);

		if (listTypeEntry == null) {
			return null;
		}

		return listTypeEntry.getExternalReferenceCode();
	}

}