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

import java.util.Map;

/**
 * @author Chema Balsas
 */
public class ProgressBarTag extends TemplateRendererTag {

	@Override
	public int doStartTag() {
		Map<String, Object> context = getContext();

		if (Validator.isNull(context.get("spritemap"))) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			putValue(
				"spritemap",
				themeDisplay.getPathThemeImages().concat("/clay/icons.svg"));
		}

		setTemplateNamespace("ClayProgressBar.render");

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		return "clay-taglib/clay-progress-bar/src/ClayProgressBar";
	}

	public void setId(String id) {
		putValue("id", id);
	}

	public void setMaxValue(int maxValue) {
		putValue("maxValue", maxValue);
	}

	public void setMinValue(int minValue) {
		putValue("minValue", minValue);
	}

	public void setSpritemap(String spritemap) {
		putValue("spritemap", spritemap);
	}

	public void setStatus(String status) {
		putValue("status", status);
	}

	public void setValue(int value) {
		putValue("value", value);
	}

}