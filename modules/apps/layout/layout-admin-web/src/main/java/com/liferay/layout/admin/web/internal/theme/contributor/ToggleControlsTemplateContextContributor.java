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

package com.liferay.layout.admin.web.internal.theme.contributor;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.template.TemplateContextContributor;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Julio Camarero
 */
@Component(
	immediate = true,
	property = "type=" + TemplateContextContributor.TYPE_THEME,
	service = TemplateContextContributor.class
)
public class ToggleControlsTemplateContextContributor
	implements TemplateContextContributor {

	@Override
	public void prepare(
		Map<String, Object> contextObjects,
		HttpServletRequest httpServletRequest) {

		String liferayToggleControls = SessionClicks.get(
			httpServletRequest, "com.liferay.frontend.js.web_toggleControls",
			"visible");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (layout.isTypeControlPanel()) {
			liferayToggleControls = "visible";
		}

		String cssClass = GetterUtil.getString(
			contextObjects.get("bodyCssClass"));

		if (Objects.equals(liferayToggleControls, "visible")) {
			cssClass += " controls-visible";
		}
		else {
			cssClass += " controls-hidden";
		}

		contextObjects.put("bodyCssClass", cssClass);

		contextObjects.put("liferay_toggle_controls", liferayToggleControls);
		contextObjects.put("show_toggle_controls", themeDisplay.isSignedIn());

		if (themeDisplay.isSignedIn()) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", themeDisplay.getLocale(), getClass());

			contextObjects.put(
				"toggle_controls_text",
				LanguageUtil.get(resourceBundle, "toggle-controls"));

			contextObjects.put("toggle_controls_url", "javascript:;");
		}
	}

}