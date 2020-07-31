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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Pavel Savinov
 */
public abstract class StyledLayoutStructureItem extends LayoutStructureItem {

	public StyledLayoutStructureItem(String parentItemId) {
		super(parentItemId);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof StyledLayoutStructureItem)) {
			return false;
		}

		StyledLayoutStructureItem styledLayoutStructureItem =
			(StyledLayoutStructureItem)object;

		JSONObject stylesJSONObject =
			styledLayoutStructureItem.stylesJSONObject;

		for (String key : this.stylesJSONObject.keySet()) {
			if (!Objects.deepEquals(
					GetterUtil.getString(this.stylesJSONObject.get(key)),
					GetterUtil.getString(stylesJSONObject.get(key)))) {

				return false;
			}
		}

		return super.equals(object);
	}

	public String getAlign() {
		return GetterUtil.getString(_getStyleProperty("align"));
	}

	public String getBackgroundColorCssClass() {
		return _getColorCssClass("backgroundColor");
	}

	public JSONObject getBackgroundImageJSONObject() {
		return (JSONObject)_getStyleProperty("backgroundImage");
	}

	public String getBorderColor() {
		return _getColorCssClass("borderColor");
	}

	public String getBorderRadius() {
		return GetterUtil.getString(_getStyleProperty("borderRadius"));
	}

	public int getBorderWidth() {
		return GetterUtil.getInteger(_getStyleProperty("borderWidth"));
	}

	public String getContentDisplay() {
		return GetterUtil.getString(_getStyleProperty("display"));
	}

	public String getFontFamily() {
		return GetterUtil.getString(_getStyleProperty("fontFamily"));
	}

	public String getFontSizeCssClass() {
		return GetterUtil.getString(_getStyleProperty("fontSize"));
	}

	public String getFontWeightCssClass() {
		return GetterUtil.getString(_getStyleProperty("fontWeight"));
	}

	public String getHeightCssClass() {
		return GetterUtil.getString(_getStyleProperty("height"));
	}

	@Override
	public JSONObject getItemConfigJSONObject() {
		try {
			Map<String, Object> defaultValues =
				CommonStylesUtil.getDefaultStyleValues();

			for (Map.Entry<String, Object> entry : defaultValues.entrySet()) {
				if (!stylesJSONObject.has(entry.getKey())) {
					stylesJSONObject.put(entry.getKey(), entry.getValue());
				}
			}
		}
		catch (Exception exception) {
			_log.error("Unable to get default style values", exception);
		}

		return JSONUtil.put("styles", stylesJSONObject);
	}

	public String getJustify() {
		return GetterUtil.getString(_getStyleProperty("justify"));
	}

	public int getMarginBottom() {
		return GetterUtil.getInteger(_getStyleProperty("marginBottom"));
	}

	public int getMarginLeft() {
		return GetterUtil.getInteger(_getStyleProperty("marginLeft"));
	}

	public int getMarginRight() {
		return GetterUtil.getInteger(_getStyleProperty("marginRight"));
	}

	public int getMarginTop() {
		return GetterUtil.getInteger(_getStyleProperty("marginTop"));
	}

	public String getMaxHeight() {
		return GetterUtil.getString(_getStyleProperty("maxHeight"));
	}

	public String getMaxWidth() {
		return GetterUtil.getString(_getStyleProperty("maxWidth"));
	}

	public String getMinHeight() {
		return GetterUtil.getString(_getStyleProperty("minHeight"));
	}

	public String getMinWidth() {
		return GetterUtil.getString(_getStyleProperty("minWidth"));
	}

	public int getOpacity() {
		return GetterUtil.getInteger(_getStyleProperty("opacity"));
	}

	public String getOverflow() {
		return GetterUtil.getString(_getStyleProperty("overflow"));
	}

	public int getPaddingBottom() {
		return GetterUtil.getInteger(_getStyleProperty("paddingBottom"));
	}

	public int getPaddingLeft() {
		return GetterUtil.getInteger(_getStyleProperty("paddingLeft"));
	}

	public int getPaddingRight() {
		return GetterUtil.getInteger(_getStyleProperty("paddingRight"));
	}

	public int getPaddingTop() {
		return GetterUtil.getInteger(_getStyleProperty("paddingTop"));
	}

	public String getShadow() {
		return GetterUtil.getString(_getStyleProperty("shadow"));
	}

	public String getTextAlignCssClass() {
		return GetterUtil.getString(_getStyleProperty("textAlign"));
	}

	public String getTextColorCssClass() {
		return _getColorCssClass("textColor");
	}

	public String getWidthCssClass() {
		return GetterUtil.getString(_getStyleProperty("width"));
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, getItemId());
	}

	@Override
	public void updateItemConfig(JSONObject itemConfigJSONObject) {
		try {
			List<String> availableStyleNames =
				CommonStylesUtil.getAvailableStyleNames();

			for (String styleName : availableStyleNames) {
				if (itemConfigJSONObject.has(styleName)) {
					stylesJSONObject.put(
						styleName, itemConfigJSONObject.get(styleName));
				}
			}

			if (itemConfigJSONObject.has("styles")) {
				JSONObject newStylesJSONObject =
					itemConfigJSONObject.getJSONObject("styles");

				for (String styleName : availableStyleNames) {
					if (newStylesJSONObject.has(styleName)) {
						stylesJSONObject.put(
							styleName, newStylesJSONObject.get(styleName));
					}
				}
			}
		}
		catch (Exception exception) {
			_log.error("Unable to get available style names", exception);
		}
	}

	protected JSONObject stylesJSONObject = JSONFactoryUtil.createJSONObject();

	private String _getColorCssClass(String property) {
		JSONObject configJSONObject = getItemConfigJSONObject();

		JSONObject configColorJSONObject = configJSONObject.getJSONObject(
			property);

		JSONObject styleColorJSONObject = stylesJSONObject.getJSONObject(
			property);

		if (((styleColorJSONObject == null) ||
			 !styleColorJSONObject.has("cssClass")) &&
			(configColorJSONObject != null)) {

			return configColorJSONObject.getString(
				"cssClass", StringPool.BLANK);
		}
		else if (styleColorJSONObject == null) {
			return StringPool.BLANK;
		}

		return styleColorJSONObject.getString("cssClass", StringPool.BLANK);
	}

	private Object _getStyleProperty(String propertyKey) {
		JSONObject configJSONObject = getItemConfigJSONObject();

		Object configValue = configJSONObject.get(propertyKey);

		Object defaultValue = CommonStylesUtil.getDefaultStyleValue(
			propertyKey);

		Object styleValue = stylesJSONObject.get(propertyKey);

		if ((styleValue == null) && (configValue != null)) {
			return configValue;
		}

		if (styleValue != null) {
			return styleValue;
		}

		return defaultValue;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StyledLayoutStructureItem.class);

}