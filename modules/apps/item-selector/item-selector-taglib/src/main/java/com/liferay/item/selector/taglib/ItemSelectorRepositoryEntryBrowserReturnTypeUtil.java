/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector.taglib;

import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.ListUtil;

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
			desiredItemSelectorReturnTypes);
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

		Group group = GroupLocalServiceUtil.getGroup(fileEntry.getGroupId());

		return JSONUtil.put(
			"externalReferenceCode", fileEntry.getExternalReferenceCode()
		).put(
			"fileEntryId", fileEntry.getFileEntryId()
		).put(
			"groupExternalReferenceCode", group.getExternalReferenceCode()
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
		).toString();
	}

	protected static ItemSelectorReturnType
		getFirstAvailableItemSelectorReturnType(
			List<ItemSelectorReturnType> desiredItemSelectorReturnTypes) {

		if (ListUtil.isEmpty(desiredItemSelectorReturnTypes)) {
			return null;
		}

		for (ItemSelectorReturnType itemSelectorReturnType :
				desiredItemSelectorReturnTypes) {

			if (_existingFileEntryReturnTypeNames.contains(
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