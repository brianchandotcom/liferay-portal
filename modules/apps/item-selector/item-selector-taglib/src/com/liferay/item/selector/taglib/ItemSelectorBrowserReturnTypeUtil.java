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

package com.liferay.item.selector.taglib;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.Base64ItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.util.Set;

/**
 * @author Sergio González
 */
public class ItemSelectorBrowserReturnTypeUtil
	implements ItemSelectorReturnType {

	public static ItemSelectorReturnType
		getFirstAvailableItemSelectorReturnType(
			Set<ItemSelectorReturnType> desiredItemSelectorReturnTypes) {

		for (ItemSelectorReturnType desiredItemSelectorReturnType :
				desiredItemSelectorReturnTypes) {

			Class<? extends ItemSelectorReturnType>
				desiredItemSelectorReturnTypeClass =
					desiredItemSelectorReturnType.getClass();

			if (desiredItemSelectorReturnTypeClass.getName().equals(
					_base64Clazz.getName()) ||
				desiredItemSelectorReturnTypeClass.getName().equals(
					_fileEntryClazz.getName()) ||
				desiredItemSelectorReturnTypeClass.getName().equals(
					_urlClazz.getName())) {

				return desiredItemSelectorReturnType;
			}
		}

		return null;
	}

	public static String getValue(
			ItemSelectorReturnType itemSelectorReturnType, FileEntry fileEntry,
			ThemeDisplay themeDisplay)
		throws Exception {

		Class<? extends ItemSelectorReturnType> itemSelectorReturnTypeClass =
			itemSelectorReturnType.getClass();

		if (_base64Clazz.getName().equals(
				itemSelectorReturnTypeClass.getName())) {

			return StringPool.BLANK;
		}
		else if (_fileEntryClazz.getName().equals(
					itemSelectorReturnTypeClass.getName())) {

			return getFileEntryValue(fileEntry, themeDisplay);
		}
		else if (_urlClazz.getName().equals(
					itemSelectorReturnTypeClass.getName())) {

			return getURLValue(fileEntry, themeDisplay);
		}

		return StringPool.BLANK;
	}

	protected static String getFileEntryValue(
			FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		JSONObject fileEntryJSONObject = JSONFactoryUtil.createJSONObject();

		fileEntryJSONObject.put("fileEntryId", fileEntry.getFileEntryId());
		fileEntryJSONObject.put("groupId", fileEntry.getGroupId());
		fileEntryJSONObject.put("title", fileEntry.getTitle());
		fileEntryJSONObject.put(
			"url", DLUtil.getImagePreviewURL(fileEntry, themeDisplay));
		fileEntryJSONObject.put("uuid", fileEntry.getUuid());

		return fileEntryJSONObject.toString();
	}

	protected static String getURLValue(
			FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		return DLUtil.getImagePreviewURL(fileEntry, themeDisplay);
	}

	private static final Class<?> _base64Clazz =
		Base64ItemSelectorReturnType.class;
	private static final Class<?> _fileEntryClazz =
		Base64ItemSelectorReturnType.class;
	private static final Class<?> _urlClazz = URLItemSelectorReturnType.class;

}