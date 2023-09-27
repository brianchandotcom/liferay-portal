/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.document.library.internal.item.display.context;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Cristina González
 */
public class ContentDashboardFileExtensionItemSelectorViewDisplayContext {

	public ContentDashboardFileExtensionItemSelectorViewDisplayContext(
		JSONArray fileExtensionGroupsJSONArray, String itemSelectedEventName) {

		_fileExtensionGroupsJSONArray = fileExtensionGroupsJSONArray;
		_itemSelectedEventName = itemSelectedEventName;
	}

	public Map<String, Object> getData() {
		return HashMapBuilder.<String, Object>put(
			"fileExtensionGroups", _fileExtensionGroupsJSONArray
		).put(
			"itemSelectorSaveEvent", _itemSelectedEventName
		).build();
	}

	private final JSONArray _fileExtensionGroupsJSONArray;
	private final String _itemSelectedEventName;

}