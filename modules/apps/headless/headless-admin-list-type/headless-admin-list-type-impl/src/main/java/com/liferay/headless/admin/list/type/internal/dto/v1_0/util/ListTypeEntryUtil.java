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
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Locale;
import java.util.Map;

/**
 * @author Gabriel Albuquerque
 */
public class ListTypeEntryUtil {

	public static com.liferay.list.type.model.ListTypeEntry toListTypeEntry(
		ListTypeEntry listTypeEntry,
		ListTypeEntryLocalService listTypeEntryLocalService) {

		com.liferay.list.type.model.ListTypeEntry serviceBuilderListTypeEntry =
			listTypeEntryLocalService.createListTypeEntry(0L);

		serviceBuilderListTypeEntry.setExternalReferenceCode(
			listTypeEntry.getExternalReferenceCode());
		serviceBuilderListTypeEntry.setKey(listTypeEntry.getKey());
		serviceBuilderListTypeEntry.setNameMap(
			LocalizedMapUtil.getLocalizedMap(listTypeEntry.getName_i18n()));

		return serviceBuilderListTypeEntry;
	}

	public static ListTypeEntry toListTypeEntry(
		Map<String, Map<String, String>> actions, Locale locale,
		com.liferay.list.type.model.ListTypeEntry serviceBuilderListTypeEntry) {

		ListTypeEntry listTypeEntry = new ListTypeEntry() {
			{
				dateCreated = serviceBuilderListTypeEntry.getCreateDate();
				dateModified = serviceBuilderListTypeEntry.getModifiedDate();

				if (GetterUtil.getBoolean(
						PropsUtil.get("feature.flag.LPS-168886"))) {

					externalReferenceCode =
						serviceBuilderListTypeEntry.getExternalReferenceCode();
				}

				id = serviceBuilderListTypeEntry.getListTypeEntryId();
				key = serviceBuilderListTypeEntry.getKey();
				name = serviceBuilderListTypeEntry.getName(locale);
				name_i18n = LocalizedMapUtil.getI18nMap(
					serviceBuilderListTypeEntry.getNameMap());
				type = serviceBuilderListTypeEntry.getType();
			}
		};

		listTypeEntry.setActions(actions);

		return listTypeEntry;
	}

}