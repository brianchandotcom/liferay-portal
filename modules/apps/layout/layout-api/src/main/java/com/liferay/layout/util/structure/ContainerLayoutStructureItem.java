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

import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class ContainerLayoutStructureItem extends StyledLayoutStructureItem {

	public ContainerLayoutStructureItem(String parentItemId) {
		super(parentItemId);

		_linkJSONObject = JSONFactoryUtil.createJSONObject();
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
				_linkJSONObject.toJSONString(),
				containerLayoutStructureItem._linkJSONObject.toJSONString()) ||
			!Objects.equals(
				_widthType, containerLayoutStructureItem._widthType)) {

			return false;
		}

		return super.equals(object);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getWidthType()}
	 */
	@Deprecated
	public String getContainerType() {
		return _widthType;
	}

	@Override
	public JSONObject getItemConfigJSONObject() {
		JSONObject jsonObject = super.getItemConfigJSONObject();

		return jsonObject.put("widthType", _widthType);
	}

	@Override
	public String getItemType() {
		return LayoutDataItemTypeConstants.TYPE_CONTAINER;
	}

	public JSONObject getLinkJSONObject() {
		return _linkJSONObject;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getPaddingLeft()} and {@link #getPaddingRight()}
	 */
	@Deprecated
	public int getPaddingHorizontal() {
		return 0;
	}

	public String getWidthType() {
		return _widthType;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, getItemId());
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setAlign(String align) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setBackgroundColorCssClass(String backgroundColorCssClass) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setBackgroundImageJSONObject(
		JSONObject backgroundImageJSONObject) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setBorderColor(String borderColor) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setBorderRadius(String borderRadius) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setBorderWidth(int borderWidth) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #setWidthType(String)}
	 */
	@Deprecated
	public void setContainerType(String containerType) {
		_widthType = containerType;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setContentDisplay(String contentDisplay) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setJustify(String justify) {
	}

	public void setLinkJSONObject(JSONObject linkJSONObject) {
		_linkJSONObject = linkJSONObject;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setMarginBottom(int marginBottom) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setMarginLeft(int marginLeft) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setMarginRight(int marginRight) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setMarginTop(int marginTop) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setOpacity(int opacity) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setPaddingBottom(int paddingBottom) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #setPaddingLeft(int)} and {@link #setPaddingRight(int)}
	 */
	@Deprecated
	public void setPaddingHorizontal(int paddingHorizontal) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setPaddingLeft(int paddingLeft) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setPaddingRight(int paddingRight) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setPaddingTop(int paddingTop) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setShadow(String shadow) {
	}

	public void setWidthType(String widthType) {
		_widthType = widthType;
	}

	@Override
	public void updateItemConfig(JSONObject itemConfigJSONObject) {
		super.updateItemConfig(itemConfigJSONObject);

		if (itemConfigJSONObject.has("widthType") ||
			itemConfigJSONObject.has("type")) {

			if (itemConfigJSONObject.has("widthType")) {
				setWidthType(itemConfigJSONObject.getString("widthType"));
			}
			else {
				setWidthType(itemConfigJSONObject.getString("type"));
			}
		}
	}

	private JSONObject _linkJSONObject;
	private String _widthType = "fluid";

}