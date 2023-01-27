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

}