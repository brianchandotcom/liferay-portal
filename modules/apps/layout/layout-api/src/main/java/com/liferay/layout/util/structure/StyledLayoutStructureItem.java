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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Pavel Savinov
 */
public abstract class StyledLayoutStructureItem extends LayoutStructureItem {

	public StyledLayoutStructureItem(String parentItemId) {
		super(parentItemId);
	}

	public String getAlign() {
		return stylesJSONObject.getString(
			"align",
			GetterUtil.getString(
				CommonStylesUtil.getDefaultStyleValue("align")));
	}

	public String getBackgroundColorCssClass() {
		return _getColorCssClass("backgroundColor");
	}

	public JSONObject getBackgroundImageJSONObject() {
		return stylesJSONObject.getJSONObject("backgroundImage");
	}

	public String getBorderColor() {
		return _getColorCssClass("borderColor");
	}

	public String getBorderRadius() {
		return stylesJSONObject.getString(
			"borderRadius",
			GetterUtil.getString(
				CommonStylesUtil.getDefaultStyleValue("borderRadius")));
	}

	public int getBorderWidth() {
		return stylesJSONObject.getInt(
			"borderWidth",
			GetterUtil.getInteger(
				CommonStylesUtil.getDefaultStyleValue("borderWidth")));
	}

	public String getContentDisplay() {
		return stylesJSONObject.getString(
			"display",
			GetterUtil.getString(
				CommonStylesUtil.getDefaultStyleValue("display")));
	}

	public String getFontFamily() {
		return stylesJSONObject.getString(
			"fontFamily",
			GetterUtil.getString(
				CommonStylesUtil.getDefaultStyleValue("fontFamily")));
	}

	public String getFontSizeCssClass() {
		return stylesJSONObject.getString(
			"fontSize",
			GetterUtil.getString(
				CommonStylesUtil.getDefaultStyleValue("fontSize")));
	}

	public String getFontWeightCssClass() {
		return stylesJSONObject.getString(
			"fontWeight",
			GetterUtil.getString(
				CommonStylesUtil.getDefaultStyleValue("fontWeight")));
	}

	public String getHeightCssClass() {
		return stylesJSONObject.getString(
			"height",
			GetterUtil.getString(
				CommonStylesUtil.getDefaultStyleValue("height")));
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
		return stylesJSONObject.getString("justify");
	}

	public int getMarginBottom() {
		return stylesJSONObject.getInt(
			"marginBottom",
			GetterUtil.getInteger(
				CommonStylesUtil.getDefaultStyleValue("marginBottom")));
	}

	public int getMarginLeft() {
		return stylesJSONObject.getInt(
			"marginLeft",
			GetterUtil.getInteger(
				CommonStylesUtil.getDefaultStyleValue("marginLeft")));
	}

	public int getMarginRight() {
		return stylesJSONObject.getInt(
			"marginRight",
			GetterUtil.getInteger(
				CommonStylesUtil.getDefaultStyleValue("marginRight")));
	}

	public int getMarginTop() {
		return stylesJSONObject.getInt(
			"marginTop",
			GetterUtil.getInteger(
				CommonStylesUtil.getDefaultStyleValue("marginTop")));
	}

	public String getMaxHeight() {
		return stylesJSONObject.getString("maxHeight");
	}

	public String getMaxWidth() {
		return stylesJSONObject.getString(
			"maxWidth",
			GetterUtil.getString(
				CommonStylesUtil.getDefaultStyleValue("maxWidth")));
	}

	public String getMinHeight() {
		return stylesJSONObject.getString(
			"minHeight",
			GetterUtil.getString(
				CommonStylesUtil.getDefaultStyleValue("minHeight")));
	}

	public String getMinWidth() {
		return stylesJSONObject.getString(
			"minWidth",
			GetterUtil.getString(
				CommonStylesUtil.getDefaultStyleValue("minWidth")));
	}

	public int getOpacity() {
		return stylesJSONObject.getInt(
			"opacity",
			GetterUtil.getInteger(
				CommonStylesUtil.getDefaultStyleValue("opacity")));
	}

	public String getOverflow() {
		return stylesJSONObject.getString(
			"overflow",
			GetterUtil.getString(
				CommonStylesUtil.getDefaultStyleValue("overflow")));
	}

	public int getPaddingBottom() {
		return stylesJSONObject.getInt(
			"paddingBottom",
			GetterUtil.getInteger(
				CommonStylesUtil.getDefaultStyleValue("paddingBottom")));
	}

	public int getPaddingLeft() {
		return stylesJSONObject.getInt(
			"paddingLeft",
			GetterUtil.getInteger(
				CommonStylesUtil.getDefaultStyleValue("paddingLeft")));
	}

	public int getPaddingRight() {
		return stylesJSONObject.getInt(
			"paddingRight",
			GetterUtil.getInteger(
				CommonStylesUtil.getDefaultStyleValue("paddingRight")));
	}

	public int getPaddingTop() {
		return stylesJSONObject.getInt(
			"paddingTop",
			GetterUtil.getInteger(
				CommonStylesUtil.getDefaultStyleValue("paddingTop")));
	}

	public String getShadow() {
		return stylesJSONObject.getString(
			"shadow",
			GetterUtil.getString(
				CommonStylesUtil.getDefaultStyleValue("shadow")));
	}

	public String getTextAlignCssClass() {
		return stylesJSONObject.getString(
			"textAlign",
			GetterUtil.getString(
				CommonStylesUtil.getDefaultStyleValue("textAlign")));
	}

	public String getTextColorCssClass() {
		return _getColorCssClass("textColor");
	}

	public String getWidthCssClass() {
		return stylesJSONObject.getString(
			"width",
			GetterUtil.getString(
				CommonStylesUtil.getDefaultStyleValue("width")));
	}

	@Override
	public void updateItemConfig(JSONObject itemConfigJSONObject) {
		if (itemConfigJSONObject.has("styles")) {
			JSONObject newStylesJSONObject = itemConfigJSONObject.getJSONObject(
				"styles");

			try {
				List<String> availableStyleNames =
					CommonStylesUtil.getAvailableStyleNames();

				for (String styleName : availableStyleNames) {
					if (newStylesJSONObject.has(styleName)) {
						stylesJSONObject.put(
							styleName, newStylesJSONObject.get(styleName));
					}
				}
			}
			catch (Exception exception) {
				_log.error("Unable to get available style names", exception);
			}
		}
	}

	protected JSONObject stylesJSONObject = JSONFactoryUtil.createJSONObject();

	private String _getColorCssClass(String property) {
		JSONObject colorJSONObject = stylesJSONObject.getJSONObject(property);

		if ((colorJSONObject == null) || !colorJSONObject.has("cssClass")) {
			return StringPool.BLANK;
		}

		return colorJSONObject.getString("cssClass");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StyledLayoutStructureItem.class);

}