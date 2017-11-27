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

package com.liferay.frontend.taglib.clay.servlet.taglib.soy;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.base.BaseClayTag;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Chema Balsas
 */
public class ButtonTag extends BaseClayTag {

	public ButtonTag() {
		super("button", "ClayButton");
	}

	@Override
	public int doStartTag() {
		Map<String, Object> context = getContext();

		if (Validator.isNotNull(context.get("icon"))) {
			Map<String, String> icon = new HashMap();

			String spritemap = (String)context.get("spritemap");

			if (Validator.isNull(spritemap)) {
				ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
					WebKeys.THEME_DISPLAY);

				spritemap = themeDisplay.getPathThemeImages().concat(
					"/clay/icons.svg");
			}

			String alignment = (String)context.get("iconAlignment");

			if (Validator.isNotNull(alignment)) {
				icon.put("alignment", alignment);
			}

			icon.put("spritemap", spritemap);
			icon.put("symbol", (String)context.get("icon"));

			putValue("icon", icon);
		}

		return super.doStartTag();
	}

	public void setAriaLabel(String ariaLabel) {
		putValue("ariaLabel", ariaLabel);
	}

	public void setBlock(Boolean block) {
		putValue("block", block);
	}

	public void setDisabled(Boolean disabled) {
		putValue("disabled", disabled);
	}

	public void setIcon(String icon) {
		putValue("icon", icon);
	}

	public void setIconAlignment(String iconAlignment) {
		putValue("iconAlignment", iconAlignment);
	}

	public void setId(String id) {
		putValue("id", id);
	}

	public void setLabel(String label) {
		putValue("label", label);
	}

	public void setMonospaced(Boolean monospaced) {
		putValue("monospaced", monospaced);
	}

	public void setName(String name) {
		putValue("name", name);
	}

	public void setSize(String size) {
		putValue("size", size);
	}

	public void setSpritemap(String spritemap) {
		putValue("spritemap", spritemap);
	}

	public void setStyle(String style) {
		putValue("style", style);
	}

	public void setType(String type) {
		putValue("type", type);
	}

	public void setValue(String value) {
		putValue("value", value);
	}

}