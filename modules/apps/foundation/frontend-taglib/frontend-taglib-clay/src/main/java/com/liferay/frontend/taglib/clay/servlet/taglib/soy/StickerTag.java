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

import com.liferay.frontend.taglib.soy.servlet.taglib.TemplateRendererTag;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Carlos Lancha
 */
public class StickerTag extends TemplateRendererTag {

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

			icon.put("spritemap", spritemap);
			icon.put("symbol", (String)context.get("icon"));

			putValue("icon", icon);
		}

		setTemplateNamespace("ClaySticker.render");

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		return "clay-taglib/clay-sticker/src/ClaySticker";
	}

	public void setIcon(String icon) {
		putValue("icon", icon);
	}

	public void setId(String id) {
		putValue("id", id);
	}

	public void setLabel(String label) {
		putValue("label", label);
	}

	public void setOutside(Boolean outside) {
		putValue("outside", outside);
	}

	public void setPosition(String position) {
		putValue("position", position);
	}

	public void setShape(String shape) {
		putValue("shape", shape);
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

}