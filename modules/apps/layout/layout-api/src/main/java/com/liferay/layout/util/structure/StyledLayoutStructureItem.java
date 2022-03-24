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

import com.liferay.layout.responsive.ViewportSize;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
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

	public String getBackgroundColor() {
		return _getColor("backgroundColor");
	}

	public String getBackgroundColorCssClass() {
		return _getColorCssClass("backgroundColor");
	}

	public JSONObject getBackgroundImageJSONObject() {
		return (JSONObject)_getStyleProperty("backgroundImage");
	}

	public String getBorderColor() {
		return _getColor("borderColor");
	}

	public String getBorderColorCssClass() {
		return _getColorCssClass("borderColor");
	}

	public String getBorderRadius() {
		return GetterUtil.getString(_getStyleProperty("borderRadius"));
	}

	public String getBorderWidth() {
		return _getStringStyleProperty("borderWidth");
	}

	public String getContentDisplay() {
		return StringPool.BLANK;
	}

	public String getDisplay() {
		return _getStringStyleProperty("display");
	}

	public String getFontFamily() {
		return GetterUtil.getString(_getStyleProperty("fontFamily"));
	}

	public String getFontSize() {
		return GetterUtil.getString(_getStyleProperty("fontSize"));
	}

	public String getFontWeight() {
		return GetterUtil.getString(_getStyleProperty("fontWeight"));
	}

	public String getHeight() {
		return GetterUtil.getString(_getStyleProperty("height"));
	}

	@Override
	public JSONObject getItemConfigJSONObject() {
		JSONObject jsonObject = JSONUtil.put("styles", stylesJSONObject);

		for (ViewportSize viewportSize : _viewportSizes) {
			if (viewportSize.equals(ViewportSize.DESKTOP)) {
				continue;
			}

			jsonObject.put(
				viewportSize.getViewportSizeId(),
				JSONUtil.put(
					"styles",
					viewportStyleJSONObjects.getOrDefault(
						viewportSize.getViewportSizeId(),
						JSONFactoryUtil.createJSONObject())));
		}

		return jsonObject;
	}

	public String getJustify() {
		return GetterUtil.getString(_getStyleProperty("justify"));
	}

	public String getMarginBottom() {
		return _getStringStyleProperty("marginBottom");
	}

	public String getMarginLeft() {
		return _getStringStyleProperty("marginLeft");
	}

	public String getMarginRight() {
		return _getStringStyleProperty("marginRight");
	}

	public String getMarginTop() {
		return _getStringStyleProperty("marginTop");
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

	public String getOpacity() {
		return _getStringStyleProperty("opacity");
	}

	public String getOverflow() {
		return GetterUtil.getString(_getStyleProperty("overflow"));
	}

	public String getPaddingBottom() {
		return _getStringStyleProperty("paddingBottom");
	}

	public String getPaddingLeft() {
		return _getStringStyleProperty("paddingLeft");
	}

	public String getPaddingRight() {
		return _getStringStyleProperty("paddingRight");
	}

	public String getPaddingTop() {
		return _getStringStyleProperty("paddingTop");
	}

	public String getShadow() {
		return GetterUtil.getString(_getStyleProperty("shadow"));
	}

	public String getTextAlignCssClass() {
		return GetterUtil.getString(_getStyleProperty("textAlign"));
	}

	public String getTextColor() {
		return _getColor("textColor");
	}

	public String getTextColorCssClass() {
		return _getColorCssClass("textColor");
	}

	public String getWidth() {
		return GetterUtil.getString(_getStyleProperty("width"));
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, getItemId());
	}

	@Override
	public void updateItemConfig(JSONObject itemConfigJSONObject) {
		try {
			_updateItemConfigValues(stylesJSONObject, itemConfigJSONObject);

			if (itemConfigJSONObject.has("styles")) {
				JSONObject newStylesJSONObject =
					itemConfigJSONObject.getJSONObject("styles");

				_updateItemConfigValues(stylesJSONObject, newStylesJSONObject);
			}

			for (ViewportSize viewportSize : _viewportSizes) {
				if (viewportSize.equals(ViewportSize.DESKTOP)) {
					continue;
				}

				JSONObject currentViewportStyleJSONObject =
					viewportStyleJSONObjects.getOrDefault(
						viewportSize.getViewportSizeId(),
						JSONFactoryUtil.createJSONObject());

				if (itemConfigJSONObject.has(
						viewportSize.getViewportSizeId())) {

					JSONObject viewportItemConfigJSONObject =
						itemConfigJSONObject.getJSONObject(
							viewportSize.getViewportSizeId());

					JSONObject newStylesJSONObject =
						viewportItemConfigJSONObject.getJSONObject("styles");

					if (newStylesJSONObject == null) {
						continue;
					}

					List<String> availableStyleNames =
						CommonStylesUtil.getAvailableStyleNames();

					for (String styleName : availableStyleNames) {
						if (newStylesJSONObject.has(styleName)) {
							currentViewportStyleJSONObject.put(
								styleName, newStylesJSONObject.get(styleName));
						}
					}
				}

				viewportStyleJSONObjects.put(
					viewportSize.getViewportSizeId(),
					currentViewportStyleJSONObject);
			}
		}
		catch (Exception exception) {
			_log.error("Unable to get available style names", exception);
		}
	}

	protected JSONObject stylesJSONObject = JSONFactoryUtil.createJSONObject();
	protected Map<String, JSONObject> viewportStyleJSONObjects =
		new HashMap<>();

	private String _getColor(String property) {
		JSONObject configJSONObject = getItemConfigJSONObject();

		Object configColorObject = configJSONObject.get(property);

		Object styleColorObject = stylesJSONObject.get(property);

		if ((styleColorObject == null) && (configColorObject != null)) {
			if (configColorObject instanceof String) {
				return GetterUtil.getString(configColorObject);
			}

			JSONObject configColorJSONObject = configJSONObject.getJSONObject(
				property);

			return configColorJSONObject.getString(
				"rgbValue", StringPool.BLANK);
		}

		if ((styleColorObject != null) &&
			(styleColorObject instanceof String)) {

			return GetterUtil.getString(styleColorObject);
		}
		else if ((styleColorObject != null) &&
				 (styleColorObject instanceof JSONObject)) {

			JSONObject styleColorJSONObject = stylesJSONObject.getJSONObject(
				property);

			return styleColorJSONObject.getString("rgbValue", StringPool.BLANK);
		}

		return StringPool.BLANK;
	}

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
				"cssClass",
				configColorJSONObject.getString("color", StringPool.BLANK));
		}
		else if (styleColorJSONObject == null) {
			String styleColor = stylesJSONObject.getString(property);

			if (!styleColor.startsWith(StringPool.POUND)) {
				return styleColor;
			}

			return StringPool.BLANK;
		}

		return styleColorJSONObject.getString(
			"cssClass",
			styleColorJSONObject.getString("color", StringPool.BLANK));
	}

	private String _getStringStyleProperty(String propertyKey) {
		Object object = _getStyleProperty(propertyKey);

		if (Validator.isNull(object)) {
			return StringPool.BLANK;
		}

		return String.valueOf(object);
	}

	private Object _getStyleProperty(String propertyKey) {
		JSONObject configJSONObject = getItemConfigJSONObject();

		Object configValue = configJSONObject.get(propertyKey);

		Object styleValue = stylesJSONObject.get(propertyKey);

		if ((configValue != null) && (styleValue == null)) {
			return configValue;
		}

		if (styleValue != null) {
			return styleValue;
		}

		return CommonStylesUtil.getDefaultStyleValue(propertyKey);
	}

	private void _updateItemConfigValues(
			JSONObject currentJSONObject, JSONObject newJSONObject)
		throws Exception {

		List<String> availableStyleNames =
			CommonStylesUtil.getAvailableStyleNames();

		for (String styleName : availableStyleNames) {
			if (newJSONObject.has(styleName)) {
				Object styleValue = newJSONObject.get(styleName);

				if (Objects.equals(
						styleValue,
						CommonStylesUtil.getDefaultStyleValue(styleName))) {

					currentJSONObject.remove(styleName);
				}
				else {
					currentJSONObject.put(styleName, styleValue);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StyledLayoutStructureItem.class);

	private static final ViewportSize[] _viewportSizes = ViewportSize.values();

}