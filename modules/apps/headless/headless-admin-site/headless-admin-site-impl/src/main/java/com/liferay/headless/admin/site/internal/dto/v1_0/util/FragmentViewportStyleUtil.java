/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.headless.admin.site.dto.v1_0.FragmentViewportStyle;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Mikel Lorza
 */
public class FragmentViewportStyleUtil {

	public static boolean isViewportStyleJSONObjectEmpty(
		JSONObject jsonObject) {

		if (JSONUtil.isEmpty(jsonObject) ||
			(jsonObject.has("backgroundImage") && (jsonObject.length() == 1))) {

			return true;
		}

		return false;
	}

	public static FragmentViewportStyle toFragmentViewportStyle(
		JSONObject jsonObject) {

		if (isViewportStyleJSONObjectEmpty(jsonObject)) {
			return null;
		}

		return new FragmentViewportStyle() {
			{
				setBackgroundColor(
					() -> jsonObject.getString("backgroundColor", null));
				setBorderColor(() -> jsonObject.getString("borderColor", null));
				setBorderRadius(
					() -> jsonObject.getString("borderRadius", null));
				setBorderWidth(() -> jsonObject.getString("borderWidth", null));
				setFontFamily(() -> jsonObject.getString("fontFamily", null));
				setFontSize(() -> jsonObject.getString("fontSize", null));
				setFontWeight(() -> jsonObject.getString("fontWeight", null));
				setHeight(() -> jsonObject.getString("height", null));
				setHidden(
					() -> StringUtil.equals(
						jsonObject.getString("display"), "none"));
				setMarginBottom(
					() -> jsonObject.getString("marginBottom", null));
				setMarginLeft(() -> jsonObject.getString("marginLeft", null));
				setMarginRight(() -> jsonObject.getString("marginRight", null));
				setMarginTop(() -> jsonObject.getString("marginTop", null));
				setMaxHeight(() -> jsonObject.getString("maxHeight", null));
				setMaxWidth(() -> jsonObject.getString("maxWidth", null));
				setMinHeight(() -> jsonObject.getString("minHeight", null));
				setMinWidth(() -> jsonObject.getString("minWidth", null));
				setOpacity(() -> jsonObject.getString("opacity", null));
				setOverflow(() -> jsonObject.getString("overflow", null));
				setPaddingBottom(
					() -> jsonObject.getString("paddingBottom", null));
				setPaddingLeft(() -> jsonObject.getString("paddingLeft", null));
				setPaddingRight(
					() -> jsonObject.getString("paddingRight", null));
				setPaddingTop(() -> jsonObject.getString("paddingTop", null));
				setShadow(() -> jsonObject.getString("shadow", null));
				setTextAlign(() -> jsonObject.getString("textAlign", null));
				setTextColor(() -> jsonObject.getString("textColor", null));
				setWidth(() -> jsonObject.getString("width", null));
			}
		};
	}

	public static JSONObject toJSONObject(
		FragmentViewportStyle fragmentViewportStyle) {

		if (fragmentViewportStyle == null) {
			return null;
		}

		return JSONUtil.put(
			"backgroundColor", fragmentViewportStyle.getBackgroundColor()
		).put(
			"borderColor", fragmentViewportStyle.getBorderColor()
		).put(
			"borderRadius", fragmentViewportStyle.getBorderRadius()
		).put(
			"borderWidth", fragmentViewportStyle.getBorderWidth()
		).put(
			"display",
			() -> {
				if (Boolean.TRUE.equals(fragmentViewportStyle.getHidden())) {
					return "none";
				}

				return "block";
			}
		).put(
			"fontFamily", fragmentViewportStyle.getFontFamily()
		).put(
			"fontSize", fragmentViewportStyle.getFontSize()
		).put(
			"fontWeight", fragmentViewportStyle.getFontWeight()
		).put(
			"height", fragmentViewportStyle.getHeight()
		).put(
			"marginBottom", fragmentViewportStyle.getMarginBottom()
		).put(
			"marginLeft", fragmentViewportStyle.getMarginLeft()
		).put(
			"marginRight", fragmentViewportStyle.getMarginRight()
		).put(
			"marginTop", fragmentViewportStyle.getMarginTop()
		).put(
			"maxHeight", fragmentViewportStyle.getMaxHeight()
		).put(
			"maxWidth", fragmentViewportStyle.getMaxWidth()
		).put(
			"minHeight", fragmentViewportStyle.getMinHeight()
		).put(
			"minWidth", fragmentViewportStyle.getMinWidth()
		).put(
			"opacity", fragmentViewportStyle.getOpacity()
		).put(
			"overflow", fragmentViewportStyle.getOverflow()
		).put(
			"paddingBottom", fragmentViewportStyle.getPaddingBottom()
		).put(
			"paddingLeft", fragmentViewportStyle.getPaddingLeft()
		).put(
			"paddingRight", fragmentViewportStyle.getPaddingRight()
		).put(
			"paddingTop", fragmentViewportStyle.getPaddingTop()
		).put(
			"shadow", fragmentViewportStyle.getShadow()
		).put(
			"textAlign", fragmentViewportStyle.getTextAlign()
		).put(
			"textColor", fragmentViewportStyle.getTextColor()
		).put(
			"width", fragmentViewportStyle.getWidth()
		);
	}

}