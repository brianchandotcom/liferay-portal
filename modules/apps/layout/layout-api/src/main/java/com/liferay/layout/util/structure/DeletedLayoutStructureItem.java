/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.util.structure;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Víctor Galán
 */
public class DeletedLayoutStructureItem {

	public static DeletedLayoutStructureItem of(JSONObject jsonObject) {
		if (jsonObject == null) {
			return new DeletedLayoutStructureItem(
				StringPool.BLANK, Collections.emptyList(), 0,
				Collections.emptySet());
		}

		return new DeletedLayoutStructureItem(
			jsonObject.getString("itemId"),
			JSONUtil.toStringList(jsonObject.getJSONArray("portletIds")),
			jsonObject.getInt("position"),
			JSONUtil.toStringSet(jsonObject.getJSONArray("childrenItemIds")));
	}

	public DeletedLayoutStructureItem(
		String itemId, List<String> portletIds, int position,
		Set<String> childrenItemIds) {

		_itemId = itemId;
		_portletIds = portletIds;
		_position = position;
		_childrenItemIds = childrenItemIds;
	}

	public Set<String> getChildrenItemIds() {
		return _childrenItemIds;
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
			"childrenItemIds", _childrenItemIds
		).put(
			"itemId", _itemId
		).put(
			"portletIds", _portletIds
		).put(
			"position", _position
		);
	}

	private final Set<String> _childrenItemIds;
	private final String _itemId;
	private final List<String> _portletIds;
	private final int _position;

}