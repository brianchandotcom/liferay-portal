/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.util.structure;

import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Víctor Galán
 */
public abstract class LayoutStructureItem {

	public static LayoutStructureItem of(JSONObject jsonObject) {
		String parentId = jsonObject.getString("parentId");
		String type = jsonObject.getString("type");

		LayoutStructureItem layoutStructureItem =
			LayoutStructureItemUtil.create(type, parentId);

		if (layoutStructureItem == null) {
			return null;
		}

		List<String> childrenItemIds = new ArrayList<>();

		JSONUtil.addToStringCollection(
			childrenItemIds, jsonObject.getJSONArray("children"));

		layoutStructureItem.setChildrenItemIds(childrenItemIds);

		layoutStructureItem.setItemId(jsonObject.getString("itemId"));
		layoutStructureItem.setParentItemId(parentId);

		layoutStructureItem.updateItemConfig(
			jsonObject.getJSONObject("config"));

		return layoutStructureItem;
	}

	public LayoutStructureItem() {
		_childrenItemIds = new ArrayList<>();
	}

	public LayoutStructureItem(String parentItemId) {
		_parentItemId = parentItemId;

		_itemId = PortalUUIDUtil.generate();
		_childrenItemIds = new ArrayList<>();
	}

	public void addChildrenItem(int position, String itemId) {
		_childrenItemIds.add(position, itemId);
	}

	public void addChildrenItem(String itemId) {
		_childrenItemIds.add(itemId);
	}

	public void deleteChildrenItem(String itemId) {
		_childrenItemIds.remove(itemId);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LayoutStructureItem)) {
			return false;
		}

		LayoutStructureItem layoutStructureItem = (LayoutStructureItem)object;

		if (Objects.equals(
				_childrenItemIds, layoutStructureItem._childrenItemIds) &&
			Objects.equals(_itemId, layoutStructureItem._itemId) &&
			Objects.equals(_parentItemId, layoutStructureItem._parentItemId)) {

			return true;
		}

		return false;
	}

	public List<String> getChildrenItemIds() {
		return _childrenItemIds;
	}

	public abstract JSONObject getItemConfigJSONObject();

	public String getItemId() {
		return _itemId;
	}

	public abstract String getItemType();

	public String getParentItemId() {
		return _parentItemId;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, getItemId());
	}

	public void setChildrenItemIds(List<String> childrenItemIds) {
		_childrenItemIds = childrenItemIds;
	}

	public void setItemId(String itemId) {
		_itemId = itemId;
	}

	public void setParentItemId(String parentItemId) {
		_parentItemId = parentItemId;
	}

	public JSONObject toJSONObject() {
		return JSONUtil.put(
			"children", JSONFactoryUtil.createJSONArray(getChildrenItemIds())
		).put(
			"config", getItemConfigJSONObject()
		).put(
			"itemId", getItemId()
		).put(
			"parentId", getParentItemId()
		).put(
			"type", getItemType()
		);
	}

	@Override
	public String toString() {
		JSONObject jsonObject = toJSONObject();

		return jsonObject.toString();
	}

	public abstract void updateItemConfig(JSONObject itemConfigJSONObject);

	private List<String> _childrenItemIds;
	private String _itemId;
	private String _parentItemId;

}