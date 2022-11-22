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

package com.liferay.client.extension.web.internal.display.context;

import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.web.internal.display.context.util.CETLabelUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class ViewClientExtensionEntryDisplayContext {

	public ViewClientExtensionEntryDisplayContext(
		CET cet, PortletRequest portletRequest) {

		_cet = cet;
		_portletRequest = portletRequest;
	}

	public String getDescription() {
		return _cet.getDescription();
	}

	public String getExternalReferenceCode() {
		return _cet.getExternalReferenceCode();
	}

	public String getName() {
		ThemeDisplay themeDisplay = _getThemeDisplay();

		return _cet.getName(themeDisplay.getLocale());
	}

	public String getProperties() {
		return PropertiesUtil.toString(_cet.getProperties());
	}

	public String getRedirect() {
		return ParamUtil.getString(_portletRequest, "redirect");
	}

	public String getSourceCodeURL() {
		return _cet.getSourceCodeURL();
	}

	public String getTitle() {
		ThemeDisplay themeDisplay = _getThemeDisplay();

		return _cet.getName(themeDisplay.getLocale());
	}

	public String getType() {
		return _cet.getType();
	}

	public String getTypeLabel() {
		ThemeDisplay themeDisplay = _getThemeDisplay();

		return LanguageUtil.get(
			_getHttpServletRequest(),
			CETLabelUtil.getTypeNameLabel(themeDisplay.getLocale(), getType()));
	}

	public String getViewJSP() {
		return _cet.getViewJSP();
	}

	public boolean isPropertiesVisible() {
		return _cet.hasProperties();
	}

	private HttpServletRequest _getHttpServletRequest() {
		return PortalUtil.getHttpServletRequest(_portletRequest);
	}

	private ThemeDisplay _getThemeDisplay() {
		HttpServletRequest httpServletRequest = _getHttpServletRequest();

		return (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	private final CET _cet;
	private final PortletRequest _portletRequest;

}