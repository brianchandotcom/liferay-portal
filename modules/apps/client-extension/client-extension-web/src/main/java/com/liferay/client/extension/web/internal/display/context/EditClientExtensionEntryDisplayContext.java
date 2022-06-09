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

import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.web.internal.util.CETLabelsUtil;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class EditClientExtensionEntryDisplayContext {

	public EditClientExtensionEntryDisplayContext(
		CET cet, ClientExtensionEntry clientExtensionEntry,
		PortletRequest portletRequest) {

		_cet = cet;
		_clientExtensionEntry = clientExtensionEntry;
		_portletRequest = portletRequest;
	}

	public String getCmd() {
		if (_clientExtensionEntry == null) {
			return Constants.ADD;
		}

		return Constants.UPDATE;
	}

	public String getDescription() {
		return BeanParamUtil.getString(
			_clientExtensionEntry, _portletRequest, "description");
	}

	public String getEditJSP() {
		return _cet.getEditJSP();
	}

	public String getExternalReferenceCode() {
		return BeanParamUtil.getString(
			_clientExtensionEntry, _portletRequest, "externalReferenceCode");
	}

	public String getName() {
		return BeanParamUtil.getString(
			_clientExtensionEntry, _portletRequest, "name");
	}

	public String getProperties() {
		return BeanParamUtil.getString(
			_clientExtensionEntry, _portletRequest, "properties");
	}

	public String getRedirect() {
		return ParamUtil.getString(_portletRequest, "redirect");
	}

	public String getSourceCodeURL() {
		return BeanParamUtil.getString(
			_clientExtensionEntry, _portletRequest, "sourceCodeURL");
	}

	public String getTitle() {
		if (_clientExtensionEntry == null) {
			return LanguageUtil.get(
				_getHttpServletRequest(),
				CETLabelsUtil.getNewLabel(
					_getHttpServletRequest(), _cet.getType()));
		}

		ThemeDisplay themeDisplay = _getThemeDisplay();

		return _cet.getName(themeDisplay.getLocale());
	}

	public String getType() {
		return BeanParamUtil.getString(
			_clientExtensionEntry, _portletRequest, "type");
	}

	public String getTypeLabel() {
		return LanguageUtil.get(
			_getHttpServletRequest(),
			CETLabelsUtil.getTypeNameLabel(
				_getHttpServletRequest(), getType()));
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
	private final ClientExtensionEntry _clientExtensionEntry;
	private final PortletRequest _portletRequest;

}