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
public class ContainerLayoutStructureItem extends StyledLayoutStructureItem {

	public ContainerLayoutStructureItem(String parentItemId) {
		super(parentItemId);

		_backgroundImageJSONObject = JSONFactoryUtil.createJSONObject();
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

		if (!Objects.equals(_align, containerLayoutStructureItem._align) ||
			!Objects.equals(
				_backgroundColorCssClass,
				containerLayoutStructureItem._backgroundColorCssClass) ||
			!Objects.equals(
				_backgroundImageJSONObject.toJSONString(),
				containerLayoutStructureItem._backgroundImageJSONObject.
					toJSONString()) ||
			!Objects.equals(
				_borderColor, containerLayoutStructureItem._borderColor) ||
			!Objects.equals(
				_borderRadius, containerLayoutStructureItem._borderRadius) ||
			!Objects.equals(
				_borderWidth, containerLayoutStructureItem._borderWidth) ||
			!Objects.equals(
				_contentDisplay,
				containerLayoutStructureItem._contentDisplay) ||
			!Objects.equals(
				_linkJSONObject.toJSONString(),
				containerLayoutStructureItem._linkJSONObject.toJSONString()) ||
			!Objects.equals(
				_marginBottom, containerLayoutStructureItem._marginBottom) ||
			!Objects.equals(
				_marginLeft, containerLayoutStructureItem._marginLeft) ||
			!Objects.equals(
				_marginRight, containerLayoutStructureItem._marginRight) ||
			!Objects.equals(
				_marginTop, containerLayoutStructureItem._marginTop) ||
			!Objects.equals(
				_paddingBottom, containerLayoutStructureItem._paddingBottom) ||
			!Objects.equals(
				_paddingHorizontal,
				containerLayoutStructureItem._paddingHorizontal) ||
			!Objects.equals(
				_paddingLeft, containerLayoutStructureItem._paddingLeft) ||
			!Objects.equals(
				_paddingRight, containerLayoutStructureItem._paddingRight) ||
			!Objects.equals(
				_paddingTop, containerLayoutStructureItem._paddingTop) ||
			!Objects.equals(_shadow, containerLayoutStructureItem._shadow) ||
			!Objects.equals(
				_widthType, containerLayoutStructureItem._widthType)) {

			return false;
		}

		return super.equals(object);
	}

	public String getAlign() {
		return _align;
	}

	public String getBackgroundColorCssClass() {
		return _backgroundColorCssClass;
	}

	public JSONObject getBackgroundImageJSONObject() {
		return _backgroundImageJSONObject;
	}

	public String getBorderColor() {
		return _borderColor;
	}

	public String getBorderRadius() {
		return _borderRadius;
	}

	public int getBorderWidth() {
		return _borderWidth;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getWidthType()}
	 */
	@Deprecated
	public String getContainerType() {
		return _widthType;
	}

	public String getContentDisplay() {
		return _contentDisplay;
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

	public String getJustify() {
		return _justify;
	}

	public JSONObject getLinkJSONObject() {
		return _linkJSONObject;
	}

	public int getMarginBottom() {
		return _marginBottom;
	}

	public int getMarginLeft() {
		return _marginLeft;
	}

	public int getMarginRight() {
		return _marginRight;
	}

	public int getMarginTop() {
		return _marginTop;
	}

	public int getOpacity() {
		return _opacity;
	}

	public int getPaddingBottom() {
		return _paddingBottom;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getPaddingLeft()} and {@link #getPaddingRight()}
	 */
	@Deprecated
	public int getPaddingHorizontal() {
		return _paddingHorizontal;
	}

	public int getPaddingLeft() {
		return _paddingLeft;
	}

	public int getPaddingRight() {
		return _paddingRight;
	}

	public int getPaddingTop() {
		return _paddingTop;
	}

	public String getShadow() {
		return _shadow;
	}

	public String getWidthType() {
		return _widthType;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, getItemId());
	}

	public void setAlign(String align) {
		_align = align;
	}

	public void setBackgroundColorCssClass(String backgroundColorCssClass) {
		_backgroundColorCssClass = backgroundColorCssClass;
	}

	public void setBackgroundImageJSONObject(
		JSONObject backgroundImageJSONObject) {

		_backgroundImageJSONObject = backgroundImageJSONObject;
	}

	public void setBorderColor(String borderColor) {
		_borderColor = borderColor;
	}

	public void setBorderRadius(String borderRadius) {
		_borderRadius = borderRadius;
	}

	public void setBorderWidth(int borderWidth) {
		_borderWidth = borderWidth;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #setWidthType(String)}
	 */
	@Deprecated
	public void setContainerType(String containerType) {
		_widthType = containerType;
	}

	public void setContentDisplay(String contentDisplay) {
		_contentDisplay = contentDisplay;
	}

	public void setJustify(String justify) {
		_justify = justify;
	}

	public void setLinkJSONObject(JSONObject linkJSONObject) {
		_linkJSONObject = linkJSONObject;
	}

	public void setMarginBottom(int marginBottom) {
		_marginBottom = marginBottom;
	}

	public void setMarginLeft(int marginLeft) {
		_marginLeft = marginLeft;
	}

	public void setMarginRight(int marginRight) {
		_marginRight = marginRight;
	}

	public void setMarginTop(int marginTop) {
		_marginTop = marginTop;
	}

	public void setOpacity(int opacity) {
		_opacity = opacity;
	}

	public void setPaddingBottom(int paddingBottom) {
		_paddingBottom = paddingBottom;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #setPaddingLeft(int)} and {@link #setPaddingRight(int)}
	 */
	@Deprecated
	public void setPaddingHorizontal(int paddingHorizontal) {
		_paddingHorizontal = paddingHorizontal;
	}

	public void setPaddingLeft(int paddingLeft) {
		_paddingLeft = paddingLeft;
	}

	public void setPaddingRight(int paddingRight) {
		_paddingRight = paddingRight;
	}

	public void setPaddingTop(int paddingTop) {
		_paddingTop = paddingTop;
	}

	public void setShadow(String shadow) {
		_shadow = shadow;
	}

	public void setWidthType(String widthType) {
		_widthType = widthType;
	}

	@Override
	public void updateItemConfig(JSONObject itemConfigJSONObject) {
		super.updateItemConfig(itemConfigJSONObject);

		if (itemConfigJSONObject.has("align")) {
			setAlign(itemConfigJSONObject.getString("align"));
		}

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

		if (itemConfigJSONObject.has("borderColor")) {
			setBorderColor(itemConfigJSONObject.getString("borderColor"));
		}

		if (itemConfigJSONObject.has("borderRadius")) {
			setBorderRadius(itemConfigJSONObject.getString("borderRadius"));
		}

		if (itemConfigJSONObject.has("borderWidth")) {
			setBorderWidth(itemConfigJSONObject.getInt("borderWidth"));
		}

		if (itemConfigJSONObject.has("contentDisplay")) {
			setContentDisplay(itemConfigJSONObject.getString("contentDisplay"));
		}

		if (itemConfigJSONObject.has("justify")) {
			setJustify(itemConfigJSONObject.getString("justify"));
		}

		if (itemConfigJSONObject.has("link")) {
			setLinkJSONObject(itemConfigJSONObject.getJSONObject("link"));
		}

		if (itemConfigJSONObject.has("marginBottom")) {
			setMarginBottom(itemConfigJSONObject.getInt("marginBottom"));
		}

		if (itemConfigJSONObject.has("marginLeft")) {
			setMarginLeft(itemConfigJSONObject.getInt("marginLeft"));
		}

		if (itemConfigJSONObject.has("marginRight")) {
			setMarginRight(itemConfigJSONObject.getInt("marginRight"));
		}

		if (itemConfigJSONObject.has("marginTop")) {
			setMarginTop(itemConfigJSONObject.getInt("marginTop"));
		}

		if (itemConfigJSONObject.has("opacity")) {
			setOpacity(itemConfigJSONObject.getInt("opacity"));
		}

		if (itemConfigJSONObject.has("paddingBottom")) {
			setPaddingBottom(itemConfigJSONObject.getInt("paddingBottom"));
		}

		if (itemConfigJSONObject.has("paddingHorizontal")) {
			setPaddingHorizontal(
				itemConfigJSONObject.getInt("paddingHorizontal"));

			if (!itemConfigJSONObject.has("paddingLeft")) {
				setPaddingLeft(_paddingHorizontal);
			}

			if (!itemConfigJSONObject.has("paddingRight")) {
				setPaddingRight(_paddingHorizontal);
			}
		}

		if (itemConfigJSONObject.has("paddingLeft")) {
			setPaddingLeft(itemConfigJSONObject.getInt("paddingLeft"));
		}

		if (itemConfigJSONObject.has("paddingRight")) {
			setPaddingRight(itemConfigJSONObject.getInt("paddingRight"));
		}

		if (itemConfigJSONObject.has("paddingTop")) {
			setPaddingTop(itemConfigJSONObject.getInt("paddingTop"));
		}

		if (itemConfigJSONObject.has("shadow")) {
			setShadow(itemConfigJSONObject.getString("shadow"));
		}

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

	private String _align;
	private String _backgroundColorCssClass;
	private JSONObject _backgroundImageJSONObject;
	private String _borderColor = "";
	private String _borderRadius = "";
	private int _borderWidth;
	private String _contentDisplay = "block";
	private String _justify = "";
	private JSONObject _linkJSONObject;
	private int _marginBottom;
	private int _marginLeft;
	private int _marginRight;
	private int _marginTop;
	private int _opacity = 100;
	private int _paddingBottom;
	private int _paddingHorizontal;
	private int _paddingLeft;
	private int _paddingRight;
	private int _paddingTop;
	private String _shadow = "";
	private String _widthType = "fluid";

}