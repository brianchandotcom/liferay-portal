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

import com.liferay.layout.helper.CollectionPaginationHelper;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class CollectionStyledLayoutStructureItem
	extends StyledLayoutStructureItem {

	public CollectionStyledLayoutStructureItem(String parentItemId) {
		super(parentItemId);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CollectionStyledLayoutStructureItem)) {
			return false;
		}

		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem =
				(CollectionStyledLayoutStructureItem)object;

		if (!Objects.equals(
				_collectionJSONObject,
				collectionStyledLayoutStructureItem._collectionJSONObject) ||
			!Objects.equals(
				_displayAllItems,
				collectionStyledLayoutStructureItem._displayAllItems) ||
			!Objects.equals(
				_displayAllPages,
				collectionStyledLayoutStructureItem._displayAllPages) ||
			!Objects.equals(
				_listStyle, collectionStyledLayoutStructureItem._listStyle) ||
			!Objects.equals(
				_numberOfColumns,
				collectionStyledLayoutStructureItem._numberOfColumns) ||
			!Objects.equals(
				_numberOfItems,
				collectionStyledLayoutStructureItem._numberOfItems) ||
			!Objects.equals(
				_numberOfItemsPerPage,
				collectionStyledLayoutStructureItem._numberOfItemsPerPage) ||
			!Objects.equals(
				_numberOfPages,
				collectionStyledLayoutStructureItem._numberOfPages) ||
			!Objects.equals(
				_paginationType,
				collectionStyledLayoutStructureItem._paginationType) ||
			!Objects.equals(
				_showAllItems,
				collectionStyledLayoutStructureItem._showAllItems)) {

			return false;
		}

		return super.equals(object);
	}

	public JSONObject getCollectionJSONObject() {
		return _collectionJSONObject;
	}

	@Override
	public JSONObject getItemConfigJSONObject() {
		JSONObject jsonObject = super.getItemConfigJSONObject();

		return jsonObject.put(
			"collection", _collectionJSONObject
		).put(
			"displayAllItems", _displayAllItems
		).put(
			"displayAllPages", _displayAllPages
		).put(
			"listItemStyle", _listItemStyle
		).put(
			"listStyle", _listStyle
		).put(
			"numberOfColumns", _numberOfColumns
		).put(
			"numberOfItems", _numberOfItems
		).put(
			"numberOfItemsPerPage", _numberOfItemsPerPage
		).put(
			"numberOfPages", _numberOfPages
		).put(
			"paginationType", _paginationType
		).put(
			"showAllItems", _showAllItems
		).put(
			"templateKey", _templateKey
		);
	}

	@Override
	public String getItemType() {
		return LayoutDataItemTypeConstants.TYPE_COLLECTION;
	}

	public String getListItemStyle() {
		return _listItemStyle;
	}

	public String getListStyle() {
		return _listStyle;
	}

	public int getNumberOfColumns() {
		return _numberOfColumns;
	}

	public int getNumberOfItems() {
		return _numberOfItems;
	}

	public int getNumberOfItemsPerPage() {
		return _numberOfItemsPerPage;
	}

	public int getNumberOfPages() {
		return _numberOfPages;
	}

	public String getPaginationType() {
		return _paginationType;
	}

	public String getTemplateKey() {
		return _templateKey;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, getItemId());
	}

	public boolean isDisplayAllItems() {
		return _displayAllItems;
	}

	public boolean isDisplayAllPages() {
		return _displayAllPages;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #isDisplayAllItems()}
	 */
	@Deprecated
	public boolean isShowAllItems() {
		return _showAllItems;
	}

	public void setCollectionJSONObject(JSONObject collectionJSONObject) {
		_collectionJSONObject = collectionJSONObject;
	}

	public void setDisplayAllItems(Boolean displayAllItems) {
		if (displayAllItems == null) {
			_displayAllItems = false;
		}
		else {
			_displayAllItems = displayAllItems;
		}
	}

	public void setDisplayAllPages(Boolean displayAllPages) {
		if (displayAllPages == null) {
			_displayAllPages = true;
		}
		else {
			_displayAllPages = displayAllPages;
		}
	}

	public void setListItemStyle(String listItemStyle) {
		_listItemStyle = listItemStyle;
	}

	public void setListStyle(String listStyle) {
		_listStyle = listStyle;
	}

	public void setNumberOfColumns(int numberOfColumns) {
		_numberOfColumns = numberOfColumns;
	}

	public void setNumberOfItems(int numberOfItems) {
		_numberOfItems = numberOfItems;
	}

	public void setNumberOfItemsPerPage(int numberOfItemsPerPage) {
		_numberOfItemsPerPage = numberOfItemsPerPage;
	}

	public void setNumberOfPages(int numberOfPages) {
		_numberOfPages = numberOfPages;
	}

	public void setPaginationType(String paginationType) {
		_paginationType = paginationType;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #setDisplayAllItems(Boolean)}
	 */
	@Deprecated
	public void setShowAllItems(Boolean showAllItems) {
		if (showAllItems == null) {
			_showAllItems = false;
		}
		else {
			_showAllItems = showAllItems;
		}
	}

	public void setTemplateKey(String templateKey) {
		_templateKey = templateKey;
	}

	@Override
	public void updateItemConfig(JSONObject itemConfigJSONObject) {
		super.updateItemConfig(itemConfigJSONObject);

		if (itemConfigJSONObject.has("collection")) {
			setCollectionJSONObject(
				itemConfigJSONObject.getJSONObject("collection"));
		}

		if (itemConfigJSONObject.has("displayAllItems")) {
			setDisplayAllItems(
				itemConfigJSONObject.getBoolean("displayAllItems"));
		}

		if (itemConfigJSONObject.has("displayAllPages")) {
			setDisplayAllPages(
				itemConfigJSONObject.getBoolean("displayAllPages"));
		}

		if (itemConfigJSONObject.has("showAllItems")) {
			setShowAllItems(itemConfigJSONObject.getBoolean("showAllItems"));
		}

		if (itemConfigJSONObject.has("listItemStyle")) {
			setListItemStyle(itemConfigJSONObject.getString("listItemStyle"));
		}

		if (itemConfigJSONObject.has("listStyle")) {
			setListStyle(itemConfigJSONObject.getString("listStyle"));
		}

		if (itemConfigJSONObject.has("numberOfColumns")) {
			setNumberOfColumns(itemConfigJSONObject.getInt("numberOfColumns"));
		}

		if (itemConfigJSONObject.has("numberOfItems")) {
			setNumberOfItems(itemConfigJSONObject.getInt("numberOfItems"));
		}

		if (itemConfigJSONObject.has("numberOfItemsPerPage")) {
			setNumberOfItemsPerPage(
				itemConfigJSONObject.getInt("numberOfItemsPerPage"));
		}

		if (itemConfigJSONObject.has("numberOfPages")) {
			setNumberOfPages(itemConfigJSONObject.getInt("numberOfPages"));
		}

		if (itemConfigJSONObject.has("paginationType")) {
			setPaginationType(itemConfigJSONObject.getString("paginationType"));
		}

		if (itemConfigJSONObject.has("templateKey")) {
			setTemplateKey(itemConfigJSONObject.getString("templateKey"));
		}
	}

	private JSONObject _collectionJSONObject;
	private boolean _displayAllItems;
	private boolean _displayAllPages = true;
	private String _listItemStyle;
	private String _listStyle;
	private int _numberOfColumns = 1;
	private int _numberOfItems = 5;
	private int _numberOfItemsPerPage = 20;
	private int _numberOfPages = 5;
	private String _paginationType =
		CollectionPaginationHelper.PAGINATION_TYPE_NUMERIC;
	private boolean _showAllItems;
	private String _templateKey;

}