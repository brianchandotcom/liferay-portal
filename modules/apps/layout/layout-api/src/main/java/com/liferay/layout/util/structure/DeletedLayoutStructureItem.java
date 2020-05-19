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

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * @author Víctor Galán
 */
public class DeletedLayoutStructureItem {

	public static DeletedLayoutStructureItem of(JSONObject jsonObject) {
		if (jsonObject == null) {
			return new DeletedLayoutStructureItem(
				StringPool.BLANK, Collections.emptySet());
		}

		return new DeletedLayoutStructureItem(
			jsonObject.getString("itemId"),
			JSONUtil.toStringSet(jsonObject.getJSONArray("portletIds")));
	}

	public DeletedLayoutStructureItem(String itemId, Set<String> portletIds) {
		_itemId = itemId;
		_portletIds = portletIds;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DeletedLayoutStructureItem)) {
			return false;
		}

		DeletedLayoutStructureItem deletedLayoutStructureItem =
			(DeletedLayoutStructureItem)obj;

		if (Objects.equals(_itemId, deletedLayoutStructureItem._itemId)) {
			return true;
		}

		return false;
	}

	public String getItemId() {
		return _itemId;
	}

	public Set<String> getPortletIds() {
		return _portletIds;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, _itemId);
	}

	public JSONObject toJSONObject() {
		return JSONUtil.put(
			"itemId", _itemId
		).put(
			"portletIds", _portletIds
		);
	}

	private final String _itemId;
	private final Set<String> _portletIds;

}