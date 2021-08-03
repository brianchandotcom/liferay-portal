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

package com.liferay.content.dashboard.web.internal.display.context;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Cristina González
 */
public class ContentDashboardItemSubtypeItemSelectorViewDisplayContext {

	public ContentDashboardItemSubtypeItemSelectorViewDisplayContext(
		JSONArray contentDashboardItemTypesJSONArray,
		String itemSelectedEventName) {

		_contentDashboardItemTypesJSONArray =
			contentDashboardItemTypesJSONArray;
		_itemSelectedEventName = itemSelectedEventName;
	}

	public Map<String, Object> getData() {
		return HashMapBuilder.<String, Object>put(
			"contentDashboardItemTypes", _contentDashboardItemTypesJSONArray
		).put(
			"itemSelectorSaveEvent", _itemSelectedEventName
		).build();
	}

	private final JSONArray _contentDashboardItemTypesJSONArray;
	private final String _itemSelectedEventName;

}