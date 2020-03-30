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

import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class CollectionLayoutStructureItem extends LayoutStructureItem {

	public static final String LIST_FORMAT_GRID = "grid";

	public static final String LIST_FORMAT_STACKED = "stacked";

	public CollectionLayoutStructureItem(String parentItemId) {
		super(parentItemId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CollectionLayoutStructureItem)) {
			return false;
		}

		CollectionLayoutStructureItem collectionLayoutStructureItem =
			(CollectionLayoutStructureItem)obj;

		if (!Objects.equals(
				_collectionJSONObject,
				collectionLayoutStructureItem._collectionJSONObject) ||
			!Objects.equals(
				_listFormat, collectionLayoutStructureItem._listFormat) ||
			!Objects.equals(
				_numberOfColumns,
				collectionLayoutStructureItem._numberOfColumns) ||
			!Objects.equals(
				_numberOfItems, collectionLayoutStructureItem._numberOfItems)) {

			return false;
		}

		return super.equals(obj);
	}

	public JSONObject getCollectionJSONObject() {
		return _collectionJSONObject;
	}

	@Override
	public JSONObject getItemConfigJSONObject() {
		return JSONUtil.put(
			"collection", _collectionJSONObject
		).put(
			"listFormat", _listFormat
		).put(
			"numberOfColumns", _numberOfColumns
		).put(
			"numberOfItems", _numberOfItems
		);
	}

	@Override
	public String getItemType() {
		return LayoutDataItemTypeConstants.TYPE_COLLECTION;
	}

	public String getListFormat() {
		return _listFormat;
	}

	public int getNumberOfColumns() {
		return _numberOfColumns;
	}

	public int getNumberOfItems() {
		return _numberOfItems;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, getItemId());
	}

	public void setCollectionJSONObject(JSONObject collectionJSONObject) {
		_collectionJSONObject = collectionJSONObject;
	}

	public void setListFormat(String listFormat) {
		_listFormat = listFormat;
	}

	public void setNumberOfColumns(int numberOfColumns) {
		_numberOfColumns = numberOfColumns;
	}

	public void setNumberOfItems(int numberOfItems) {
		_numberOfItems = numberOfItems;
	}

	@Override
	public void updateItemConfig(JSONObject itemConfigJSONObject) {
		if (itemConfigJSONObject.has("collection")) {
			setCollectionJSONObject(
				itemConfigJSONObject.getJSONObject("collection"));
		}

		if (itemConfigJSONObject.has("listFormat")) {
			setListFormat(itemConfigJSONObject.getString("listFormat"));
		}

		if (itemConfigJSONObject.has("numberOfColumns")) {
			setNumberOfColumns(itemConfigJSONObject.getInt("numberOfColumns"));
		}

		if (itemConfigJSONObject.has("numberOfItems")) {
			setNumberOfItems(itemConfigJSONObject.getInt("numberOfItems"));
		}
	}

	private JSONObject _collectionJSONObject;
	private String _listFormat = LIST_FORMAT_STACKED;
	private int _numberOfColumns = 3;
	private int _numberOfItems = 3;

}