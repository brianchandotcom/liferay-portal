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
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.UploadableFileReturnType;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
public class ItemSelectorRepositoryEntryBrowserReturnTypeUtil
	implements ItemSelectorReturnType {

	public static ItemSelectorReturnType
		getFirstAvailableDraggableFileReturnType(
			List<ItemSelectorReturnType> desiredItemSelectorReturnTypes) {

		return getFirstAvailableItemSelectorReturnType(
			desiredItemSelectorReturnTypes, _draggableFileReturnTypeNames);
	}

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

	protected static ItemSelectorReturnType
		getFirstAvailableItemSelectorReturnType(
			List<ItemSelectorReturnType> desiredItemSelectorReturnTypes,
			List<String> itemSelectorReturnTypeTypes) {

		Iterator<ItemSelectorReturnType> iterator =
			desiredItemSelectorReturnTypes.iterator();

		while (iterator.hasNext()) {
			ItemSelectorReturnType itemSelectorReturnType = iterator.next();

			String className = ClassUtil.getClassName(itemSelectorReturnType);

			if (itemSelectorReturnTypeTypes.contains(className)) {
				return itemSelectorReturnType;
			}
		}

		return null;
	}

	protected static String getURLValue(
			FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		return DLUtil.getImagePreviewURL(fileEntry, themeDisplay);
	}

	private static final List<String> _draggableFileReturnTypeNames =
		ListUtil.fromArray(
			new String[] {
				ClassUtil.getClassName(new Base64ItemSelectorReturnType()),
				ClassUtil.getClassName(new UploadableFileReturnType())
			});
	private static final List<String> _existingFileEntryReturnTypeNames =
		ListUtil.fromArray(
			new String[] {
				ClassUtil.getClassName(new FileEntryItemSelectorReturnType()),
				ClassUtil.getClassName(new URLItemSelectorReturnType())
			});

}