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
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

/**
 * @author Eudaldo Alonso
 */
public class ContainerLayoutStructureItem extends LayoutStructureItem {

	public ContainerLayoutStructureItem(String parentItemId) {
		super(parentItemId);
	}

	public String getBackgroundColorCssClass() {
		return _backgroundColorCssClass;
	}

	public String getBackgroundImageTitle() {
		return _backgroundImageTitle;
	}

	public String getBackgroundImageURL() {
		return _backgroundImageURL;
	}

	public String getContainerType() {
		return _containerType;
	}

	@Override
	public String getItemType() {
		return LayoutDataItemTypeConstants.TYPE_CONTAINER;
	}

	public int getPaddingBottom() {
		return _paddingBottom;
	}

	public int getPaddingHorizontal() {
		return _paddingHorizontal;
	}

	public int getPaddingTop() {
		return _paddingTop;
	}

	public void setBackgroundColorCssClass(String backgroundColorCssClass) {
		_backgroundColorCssClass = backgroundColorCssClass;
	}

	public void setBackgroundImageTitle(String backgroundImageTitle) {
		_backgroundImageTitle = backgroundImageTitle;
	}

	public void setBackgroundImageURL(String backgroundImageURL) {
		_backgroundImageURL = backgroundImageURL;
	}

	public void setContainerType(String containerType) {
		_containerType = containerType;
	}

	public void setPaddingBottom(int paddingBottom) {
		_paddingBottom = paddingBottom;
	}

	public void setPaddingHorizontal(int paddingHorizontal) {
		_paddingHorizontal = paddingHorizontal;
	}

	public void setPaddingTop(int paddingTop) {
		_paddingTop = paddingTop;
	}

	@Override
	public void updateItemConfig(JSONObject itemConfigJSONObject) {
		if (itemConfigJSONObject.has("backgroundColorCssClass")) {
			setBackgroundColorCssClass(
				itemConfigJSONObject.getString("backgroundColorCssClass"));
		}

		if (itemConfigJSONObject.has("backgroundImage")) {
			JSONObject backgroundImageJSONObject =
				itemConfigJSONObject.getJSONObject("backgroundImage");

			setBackgroundImageTitle(
				backgroundImageJSONObject.getString("title"));
			setBackgroundImageURL(backgroundImageJSONObject.getString("url"));
		}

		if (itemConfigJSONObject.has("containerType")) {
			setContainerType(itemConfigJSONObject.getString("containerType"));
		}

		if (itemConfigJSONObject.has("paddingBottom")) {
			setPaddingBottom(itemConfigJSONObject.getInt("paddingBottom"));
		}

		if (itemConfigJSONObject.has("paddingHorizontal")) {
			setPaddingHorizontal(
				itemConfigJSONObject.getInt("paddingHorizontal"));
		}

		if (itemConfigJSONObject.has("paddingTop")) {
			setPaddingTop(itemConfigJSONObject.getInt("paddingTop"));
		}
	}

	@Override
	protected JSONObject getItemConfigJSONObject() {
		return JSONUtil.put(
			"backgroundColorCssClass", _backgroundColorCssClass
		).put(
			"backgroundImage",
			JSONUtil.put(
				"title", _backgroundImageTitle
			).put(
				"url", _backgroundImageURL
			)
		).put(
			"containerType", _containerType
		).put(
			"paddingBottom", _paddingBottom
		).put(
			"paddingHorizontal", _paddingHorizontal
		).put(
			"paddingTop", _paddingTop
		);
	}

	private String _backgroundColorCssClass;
	private String _backgroundImageTitle;
	private String _backgroundImageURL;
	private String _containerType = "fluid";
	private int _paddingBottom = 3;
	private int _paddingHorizontal = 3;
	private int _paddingTop = 3;

}