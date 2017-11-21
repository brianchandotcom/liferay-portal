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

import java.util.Map;

/**
 * @author Carlos Lancha
 */
public class AlertTag extends TemplateRendererTag {

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

		setTemplateNamespace("ClayAlert.render");

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		return "clay-taglib/clay-alert/src/ClayAlert";
	}

	public void setAutoclose(Boolean autoClose) {
		putValue("autoClose", autoClose);
	}

	public void setCloseable(Boolean closeable) {
		putValue("closeable", closeable);
	}

	public void setDestroyOnHide(Boolean destroyOnHide) {
		putValue("destroyOnHide", destroyOnHide);
	}

	public void setMessage(String message) {
		putValue("message", message);
	}

	public void setSpritemap(String spritemap) {
		putValue("spritemap", spritemap);
	}

	public void setStyle(String style) {
		putValue("style", style);
	}

	public void setTitle(String title) {
		putValue("title", title);
	}

	public void setType(String type) {
		putValue("type", type);
	}

}