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

package com.liferay.headless.admin.list.type.internal.dto.v1_0.util;

import com.liferay.headless.admin.list.type.dto.v1_0.ListTypeEntry;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Locale;

/**
 * @author Gabriel Albuquerque
 */
public class ListTypeEntryUtil {

	public static ListTypeEntry toListTypeEntry(
		Locale locale,
		com.liferay.list.type.model.ListTypeEntry serviceBuilderListTypeEntry) {

		return new ListTypeEntry() {
			{
				dateCreated = serviceBuilderListTypeEntry.getCreateDate();
				dateModified = serviceBuilderListTypeEntry.getModifiedDate();
				id = serviceBuilderListTypeEntry.getListTypeEntryId();
				key = serviceBuilderListTypeEntry.getKey();
				name = serviceBuilderListTypeEntry.getName(locale);
				name_i18n = LocalizedMapUtil.getI18nMap(
					serviceBuilderListTypeEntry.getNameMap());
				type = serviceBuilderListTypeEntry.getType();
			}
		};
	}

}