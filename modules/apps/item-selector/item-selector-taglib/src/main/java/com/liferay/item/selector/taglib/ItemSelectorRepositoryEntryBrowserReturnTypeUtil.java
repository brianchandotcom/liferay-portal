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

import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @author     Sergio González
 * @author     Roberto Díaz
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class ItemSelectorRepositoryEntryBrowserReturnTypeUtil
	implements ItemSelectorReturnType {

	public static ItemSelectorReturnType
		getFirstAvailableExistingFileEntryReturnType(
			List<ItemSelectorReturnType> desiredItemSelectorReturnTypes) {

		return getFirstAvailableItemSelectorReturnType(
			desiredItemSelectorReturnTypes, _existingFileEntryReturnTypeNames);
	}

	public static String getValue(
			ItemSelectorReturnType itemSelectorReturnType, FileEntry fileEntry,
			ThemeDisplay themeDisplay)
		throws Exception {

		String className = ClassUtil.getClassName(itemSelectorReturnType);

		if (className.equals(FileEntryItemSelectorReturnType.class.getName())) {
			return getFileEntryValue(fileEntry, themeDisplay);
		}
		else if (className.equals(URLItemSelectorReturnType.class.getName())) {
			return DLUtil.getPreviewURL(
				fileEntry, fileEntry.getFileVersion(), themeDisplay,
				StringPool.BLANK, false, false);
		}

		return StringPool.BLANK;
	}

	protected static String getFileEntryValue(
			FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		JSONObject fileEntryJSONObject = JSONUtil.put(
			"fileEntryId", fileEntry.getFileEntryId()
		).put(
			"groupId", fileEntry.getGroupId()
		).put(
			"title", fileEntry.getTitle()
		).put(
			"type", "document"
		).put(
			"url",
			DLUtil.getPreviewURL(
				fileEntry, fileEntry.getFileVersion(), themeDisplay,
				StringPool.BLANK, false, false)
		).put(
			"uuid", fileEntry.getUuid()
		);

		return fileEntryJSONObject.toString();
	}

	protected static ItemSelectorReturnType
		getFirstAvailableItemSelectorReturnType(
			List<ItemSelectorReturnType> desiredItemSelectorReturnTypes,
			List<String> itemSelectorReturnTypeTypes) {

		Iterator<ItemSelectorReturnType> iterator =
			desiredItemSelectorReturnTypes.iterator();

		while (iterator.hasNext()) {
			ItemSelectorReturnType itemSelectorReturnType = iterator.next();

			if (itemSelectorReturnTypeTypes.contains(
					ClassUtil.getClassName(itemSelectorReturnType))) {

				return itemSelectorReturnType;
			}
		}

		return null;
	}

	private static final List<String> _existingFileEntryReturnTypeNames =
		ListUtil.fromArray(
			ClassUtil.getClassName(new FileEntryItemSelectorReturnType()),
			ClassUtil.getClassName(new URLItemSelectorReturnType()));

}