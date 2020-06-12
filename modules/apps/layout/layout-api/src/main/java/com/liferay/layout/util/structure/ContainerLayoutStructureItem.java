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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class ContainerLayoutStructureItem extends LayoutStructureItem {

	public ContainerLayoutStructureItem(String parentItemId) {
		super(parentItemId);

		_backgroundImageJSONObject = JSONFactoryUtil.createJSONObject();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ContainerLayoutStructureItem)) {
			return false;
		}

		ContainerLayoutStructureItem containerLayoutStructureItem =
			(ContainerLayoutStructureItem)object;

		if (!Objects.equals(
				_backgroundColorCssClass,
				containerLayoutStructureItem._backgroundColorCssClass) ||
			!Objects.equals(
				_backgroundImageJSONObject.toJSONString(),
				containerLayoutStructureItem._backgroundImageJSONObject.
					toJSONString()) ||
			!Objects.equals(
				_containerWidth,
				containerLayoutStructureItem._containerWidth) ||
			!Objects.equals(
				_paddingBottom, containerLayoutStructureItem._paddingBottom) ||
			!Objects.equals(
				_paddingHorizontal,
				containerLayoutStructureItem._paddingHorizontal) ||
			!Objects.equals(
				_paddingTop, containerLayoutStructureItem._paddingTop)) {

			return false;
		}

		return super.equals(object);
	}

	public String getBackgroundColorCssClass() {
		return _backgroundColorCssClass;
	}

	public JSONObject getBackgroundImageJSONObject() {
		return _backgroundImageJSONObject;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getContainerWidth()}
	 */
	@Deprecated
	public String getContainerType() {
		return _containerWidth;
	}

	public String getContainerWidth() {
		return _containerWidth;
	}

	@Override
	public JSONObject getItemConfigJSONObject() {
		return JSONUtil.put(
			"backgroundColorCssClass", _backgroundColorCssClass
		).put(
			"backgroundImage", _backgroundImageJSONObject
		).put(
			"containerWidth", _containerWidth
		).put(
			"paddingBottom", _paddingBottom
		).put(
			"paddingHorizontal", _paddingHorizontal
		).put(
			"paddingLeft", _paddingLeft
		).put(
			"paddingRight", _paddingRight
		).put(
			"paddingTop", _paddingTop
		).put(
			"type", _containerWidth
		);
	}

	@Override
	public String getItemType() {
		return LayoutDataItemTypeConstants.TYPE_CONTAINER;
	}

	public int getPaddingBottom() {
		return _paddingBottom;
	}

	@Deprecated
	public int getPaddingHorizontal() {
		return _paddingHorizontal;
	}

	public int getPaddingTop() {
		return _paddingTop;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, getItemId());
	}

	public void setBackgroundColorCssClass(String backgroundColorCssClass) {
		_backgroundColorCssClass = backgroundColorCssClass;
	}

	public void setBackgroundImageJSONObject(
		JSONObject backgroundImageJSONObject) {

		_backgroundImageJSONObject = backgroundImageJSONObject;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #setContainerWidth(String)}
	 */
	@Deprecated
	public void setContainerType(String containerType) {
		_containerWidth = containerType;
	}

	public void setContainerWidth(String containerWidth) {
		_containerWidth = containerWidth;
	}

	public void setPaddingBottom(int paddingBottom) {
		_paddingBottom = paddingBottom;
	}

	@Deprecated
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
			JSONObject backgroundImageJSONObject = null;

			Object backgroundImage = itemConfigJSONObject.get(
				"backgroundImage");

			if (backgroundImage instanceof JSONObject) {
				backgroundImageJSONObject = (JSONObject)backgroundImage;
			}
			else {
				backgroundImageJSONObject = JSONUtil.put(
					"url", backgroundImage);
			}

			setBackgroundImageJSONObject(backgroundImageJSONObject);
		}

		if (itemConfigJSONObject.has("containerWidth") ||
			itemConfigJSONObject.has("type")) {

			if (itemConfigJSONObject.has("containerWidth")) {
				setContainerWidth(
					itemConfigJSONObject.getString("containerWidth"));
			}
			else {
				setContainerWidth(itemConfigJSONObject.getString("type"));
			}
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

	private String _backgroundColorCssClass;
	private JSONObject _backgroundImageJSONObject;
	private String _containerWidth = "fluid";
	private int _paddingBottom = 3;
	private int _paddingHorizontal = 3;
	private int _paddingTop = 3;

}