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

package com.liferay.layout.util.structure;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author Víctor Galán
 */
public class DeletedLayoutStructureItem {

	public static DeletedLayoutStructureItem of(JSONObject jsonObject) {
		if (jsonObject == null) {
			return new DeletedLayoutStructureItem(
				StringPool.BLANK, Collections.emptyList());
		}

		return new DeletedLayoutStructureItem(
			jsonObject.getString("itemId"),
			JSONUtil.toStringList(jsonObject.getJSONArray("portletIds")),
			jsonObject.getInt("position"));
	}

	public DeletedLayoutStructureItem(String itemId, List<String> portletIds) {
		this(itemId, portletIds, 0);
	}

	public DeletedLayoutStructureItem(
		String itemId, List<String> portletIds, int position) {

		_itemId = itemId;
		_portletIds = portletIds;
		_position = position;
	}

	public boolean contains(String portletId) {
		if (_portletIds.contains(portletId)) {
			return true;
		}

		return false;
	}

	public String getItemId() {
		return _itemId;
	}

	public List<String> getPortletIds() {
		return _portletIds;
	}

	public int getPosition() {
		return _position;
	}

	public JSONObject toJSONObject() {
		return JSONUtil.put(
			"itemId", _itemId
		).put(
			"portletIds", _portletIds
		).put(
			"position", _position
		);
	}

	private final String _itemId;
	private final List<String> _portletIds;
	private final int _position;

}