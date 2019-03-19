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

package com.liferay.layout.type.controller.content.internal.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Objects;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class ToggleEditLayoutModeDisplayContext {

	public ToggleEditLayoutModeDisplayContext(HttpServletRequest request) {
		_request = request;

		_themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getMode() {
		if (Validator.isNotNull(_mode)) {
			return _mode;
		}

		_mode = ParamUtil.getString(_request, "p_l_mode", Constants.VIEW);

		return _mode;
	}

	public String getRedirect() throws PortalException {
		String redirect = _themeDisplay.getURLCurrent();

		Layout layout = _themeDisplay.getLayout();

		if ((layout.getClassPK() > 0) &&
			(PortalUtil.getClassNameId(Layout.class) ==
				layout.getClassNameId())) {

			redirect = PortalUtil.getLayoutFullURL(
				LayoutLocalServiceUtil.getLayout(layout.getClassPK()),
				_themeDisplay);
		}
		else {
			Layout draftLayout = LayoutLocalServiceUtil.fetchLayout(
				PortalUtil.getClassNameId(Layout.class), layout.getPlid());

			if (draftLayout != null) {
				redirect = PortalUtil.getLayoutFullURL(
					draftLayout, _themeDisplay);
			}
		}

		if (Objects.equals(getMode(), Constants.EDIT)) {
			redirect = HttpUtil.setParameter(
				redirect, "p_l_mode", Constants.VIEW);
		}
		else {
			redirect = HttpUtil.setParameter(
				redirect, "p_l_mode", Constants.EDIT);
		}

		return HtmlUtil.escapeJS(redirect);
	}

	public String getTitle() {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", _themeDisplay.getLocale(), getClass());

		if (Objects.equals(getMode(), Constants.EDIT)) {
			return LanguageUtil.get(resourceBundle, "edit-mode");
		}

		return LanguageUtil.get(resourceBundle, "view-mode");
	}

	private String _mode;
	private final HttpServletRequest _request;
	private final ThemeDisplay _themeDisplay;

}