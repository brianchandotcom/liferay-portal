/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.util.structure;

import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.constants.StyledLayoutStructureConstants;
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class ContainerStyledLayoutStructureItem
	extends StyledLayoutStructureItem {

	public ContainerStyledLayoutStructureItem(String parentItemId) {
		super(parentItemId);

		_linkJSONObject = JSONFactoryUtil.createJSONObject();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ContainerStyledLayoutStructureItem)) {
			return false;
		}

		ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem =
			(ContainerStyledLayoutStructureItem)object;

		if (!Objects.equals(
				_linkJSONObject.toString(),
				containerStyledLayoutStructureItem._linkJSONObject.
					toJSONString()) ||
			!Objects.equals(
				_widthType, containerStyledLayoutStructureItem._widthType)) {

			return false;
		}

		return super.equals(object);
	}

	public String getAlign() {
		return _align;
	}

	public String getContentDisplay() {
		return _contentDisplay;
	}

	public String getContentVisibility() {
		return _contentVisibility;
	}

	public String getDisplay() {
		return stylesJSONObject.getString("display");
	}

	public String getFlexWrap() {
		return _flexWrap;
	}

	public String getHtmlTag() {
		return _htmlTag;
	}

	@Override
	public JSONObject getItemConfigJSONObject() {
		JSONObject jsonObject = super.getItemConfigJSONObject();

		jsonObject.put(
			"align",
			() -> {
				if (Validator.isBlank(_align)) {
					return null;
				}

				return _align;
			}
		).put(
			"contentDisplay",
			() -> {
				if (Validator.isBlank(_contentDisplay)) {
					return null;
				}

				return _contentDisplay;
			}
		).put(
			"contentVisibility",
			() -> {
				if (Validator.isBlank(_contentVisibility)) {
					return null;
				}

				return _contentVisibility;
			}
		).put(
			"flexWrap",
			() -> {
				if (Validator.isBlank(_flexWrap)) {
					return null;
				}

				return _flexWrap;
			}
		).put(
			"htmlTag",
			() -> {
				if (Validator.isBlank(_htmlTag)) {
					return null;
				}

				return _htmlTag;
			}
		).put(
			"indexed",
			() -> {
				if (_indexed) {
					return null;
				}

				return false;
			}
		).put(
			"justify",
			() -> {
				if (Validator.isBlank(_justify)) {
					return null;
				}

				return _justify;
			}
		).put(
			"link",
			() -> {
				if (_linkJSONObject.length() == 0) {
					return null;
				}

				return _linkJSONObject;
			}
		).put(
			"widthType",
			() -> {
				if (Objects.equals(_widthType, "fluid")) {
					return null;
				}

				return _widthType;
			}
		);

		return jsonObject;
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

	public String getWidthType() {
		return _widthType;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, getItemId());
	}

	public boolean isIndexed() {
		return _indexed;
	}

	public void setAlign(String align) {
		_align = align;
	}

	public void setContentDisplay(String contentDisplay) {
		_contentDisplay = contentDisplay;
	}

	public void setContentVisibility(String contentVisibility) {
		_contentVisibility = contentVisibility;
	}

	public void setFlexWrap(String flexWrap) {
		_flexWrap = flexWrap;
	}

	public void setHtmlTag(String htmlTag) {
		_htmlTag = htmlTag;
	}

	public void setIndexed(boolean indexed) {
		_indexed = indexed;
	}

	public void setJustify(String justify) {
		_justify = justify;
	}

	public void setLinkJSONObject(JSONObject linkJSONObject) {
		_linkJSONObject = linkJSONObject;
	}

	public void setWidthType(String widthType) {
		_widthType = widthType;
	}

	@Override
	public void updateItemConfig(JSONObject itemConfigJSONObject) {
		_convertStyleProperties(itemConfigJSONObject);

		super.updateItemConfig(itemConfigJSONObject);

		if (itemConfigJSONObject.has("align")) {
			setAlign(itemConfigJSONObject.getString("align"));
		}

		if (itemConfigJSONObject.has("contentDisplay")) {
			setContentDisplay(itemConfigJSONObject.getString("contentDisplay"));
		}

		if (itemConfigJSONObject.has("contentVisibility")) {
			setContentVisibility(
				itemConfigJSONObject.getString("contentVisibility"));
		}

		if (itemConfigJSONObject.has("flexWrap")) {
			setFlexWrap(itemConfigJSONObject.getString("flexWrap"));
		}

		if (itemConfigJSONObject.has("htmlTag")) {
			setHtmlTag(itemConfigJSONObject.getString("htmlTag"));
		}

		if (itemConfigJSONObject.has("justify")) {
			setJustify(itemConfigJSONObject.getString("justify"));
		}

		if (itemConfigJSONObject.has("link")) {
			setLinkJSONObject(itemConfigJSONObject.getJSONObject("link"));
		}

		if (itemConfigJSONObject.has("indexed")) {
			setIndexed(itemConfigJSONObject.getBoolean("indexed"));
		}

		if (itemConfigJSONObject.has("containerType") ||
			itemConfigJSONObject.has("type") ||
			itemConfigJSONObject.has("widthType")) {

			if (itemConfigJSONObject.has("containerType")) {
				setWidthType(itemConfigJSONObject.getString("containerType"));
			}
			else if (itemConfigJSONObject.has("type")) {
				setWidthType(itemConfigJSONObject.getString("type"));
			}
			else {
				setWidthType(itemConfigJSONObject.getString("widthType"));
			}
		}
	}

	private void _convertStyleProperties(JSONObject itemConfigJSONObject) {
		String backgroundColorCssClass = itemConfigJSONObject.getString(
			"backgroundColorCssClass");

		if (Validator.isNotNull(backgroundColorCssClass)) {
			itemConfigJSONObject.put(
				"backgroundColor",
				_colors.getOrDefault(
					backgroundColorCssClass, backgroundColorCssClass));
		}

		String borderColor = itemConfigJSONObject.getString("borderColor");

		if (Validator.isNotNull(borderColor)) {
			itemConfigJSONObject.put(
				"borderColor", _colors.getOrDefault(borderColor, borderColor));
		}

		String borderRadius = itemConfigJSONObject.getString("borderRadius");

		if (Validator.isNotNull(borderRadius)) {
			itemConfigJSONObject.put(
				"borderRadius",
				_borderRadius.getOrDefault(borderRadius, borderRadius));
		}

		String shadow = itemConfigJSONObject.getString("shadow");

		if (Validator.isNotNull(shadow)) {
			itemConfigJSONObject.put(
				"shadow", _shadows.getOrDefault(shadow, shadow));
		}
	}

	private static final Map<String, String> _borderRadius = HashMapBuilder.put(
		"rounded", "0.25rem"
	).put(
		"rounded-circle", "50%"
	).put(
		"rounded-lg", "0.375rem"
	).put(
		"rounded-pill", "50rem"
	).put(
		"rounded-sm", "50rem"
	).build();
	private static final Map<String, String> _colors = HashMapBuilder.put(
		"danger", "#DA1414"
	).put(
		"dark", "#272833"
	).put(
		"gray-dark", "#393A4A"
	).put(
		"info", "#2E5AAC"
	).put(
		"light", "#F1F2F5"
	).put(
		"lighter", "#F7F8F9"
	).put(
		"primary", "#0B5FFF"
	).put(
		"secondary", "#6B6C7E"
	).put(
		"success", "#287D3C"
	).put(
		"warning", "#B95000"
	).put(
		"white", "#FFFFFF"
	).build();
	private static final Map<String, String> _shadows = HashMapBuilder.put(
		"shadow", "0 .5rem 1rem rgba(0, 0, 0, 0.15)"
	).put(
		"shadow-lg", "0 1rem 3rem rgba(0, 0, 0, 0.175)"
	).put(
		"shadow-none", "none"
	).put(
		"shadow-sm", "0 .125rem .25rem rgba(0, 0, 0, 0.075)"
	).build();

	private String _align = "";
	private String _contentDisplay = "";
	private String _contentVisibility = "";
	private String _flexWrap = "";
	private String _htmlTag = "";
	private boolean _indexed = true;
	private String _justify = "";
	private JSONObject _linkJSONObject;
	private String _widthType = StyledLayoutStructureConstants.WIDTH_TYPE;

}