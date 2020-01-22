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

package com.liferay.layout.content.page.editor.web.internal.util.layout.structure;

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

	public String getBackgroundImage() {
		return _backgroundImage;
	}

	public String getContainerType() {
		return _containerType;
	}

	@Override
	public String getItemType() {
		return LayoutDataItemTypeConstants.TYPE_CONTAINER;
	}

	public long getPaddingBottom() {
		return _paddingBottom;
	}

	public long getPaddingHorizontal() {
		return _paddingHorizontal;
	}

	public long getPaddingTop() {
		return _paddingTop;
	}

	public void setBackgroundColorCssClass(String backgroundColorCssClass) {
		_backgroundColorCssClass = backgroundColorCssClass;
	}

	public void setBackgroundImage(String backgroundImage) {
		_backgroundImage = backgroundImage;
	}

	public void setContainerType(String containerType) {
		_containerType = containerType;
	}

	public void setPaddingBottom(long paddingBottom) {
		_paddingBottom = paddingBottom;
	}

	public void setPaddingHorizontal(long paddingHorizontal) {
		_paddingHorizontal = paddingHorizontal;
	}

	public void setPaddingTop(long paddingTop) {
		_paddingTop = paddingTop;
	}

	@Override
	public void updateItemConfig(JSONObject itemConfigJSONObject) {
		if (itemConfigJSONObject.has("backgroundColorCssClass")) {
			setBackgroundColorCssClass(
				itemConfigJSONObject.getString("backgroundColorCssClass"));
		}

		if (itemConfigJSONObject.has("backgroundImage")) {
			setBackgroundImage(
				itemConfigJSONObject.getString("backgroundImage"));
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
			"backgroundImage", _backgroundImage
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
	private String _backgroundImage;
	private String _containerType = "fluid";
	private long _paddingBottom = 3;
	private long _paddingHorizontal = 3;
	private long _paddingTop = 3;

}