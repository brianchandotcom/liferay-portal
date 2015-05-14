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

package com.liferay.iframe.web.display.context;

import com.liferay.iframe.web.configuration.IFrameConfiguration;
import com.liferay.iframe.web.configuration.IFramePortletInstanceConfiguration;
import com.liferay.iframe.web.constants.IFrameWebKeys;
import com.liferay.iframe.web.util.IFrameUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

/**
 * @author Juergen Kappler
 */
public class IFrameDisplayContext {

	public IFrameDisplayContext(
			IFrameConfiguration iFrameConfiguration, PortletRequest request)
		throws SettingsException {

		_iFrameConfiguration = iFrameConfiguration;
		_request = request;

		_themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		_iFramePortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				IFramePortletInstanceConfiguration.class);
	}

	public String getAlt() {
		if (_alt != null) {
			return _alt;
		}

		_alt = _iFramePortletInstanceConfiguration.alt();

		return _alt;
	}

	public String getAuthType() {
		if (_authType != null) {
			return _authType;
		}

		_authType = _iFramePortletInstanceConfiguration.authType();

		if (Validator.isNull(_authType)) {
			_authType = _iFrameConfiguration.authType();
		}

		return _authType;
	}

	public String getBasicPassword() {
		if (_basicPassword != null) {
			return _basicPassword;
		}

		_basicPassword = _iFramePortletInstanceConfiguration.basicPassword();

		return _basicPassword;
	}

	public String getBasicUserName() {
		if (_basicUserName != null) {
			return _basicUserName;
		}

		_basicUserName = _iFramePortletInstanceConfiguration.basicUserName();

		return _basicUserName;
	}

	public String getBorder() {
		if (_border != null) {
			return _border;
		}

		_border = _iFramePortletInstanceConfiguration.border();

		return _border;
	}

	public String getBordercolor() {
		if (_bordercolor != null) {
			return _bordercolor;
		}

		_bordercolor = _iFramePortletInstanceConfiguration.bordercolor();

		return _bordercolor;
	}

	public String getFormMethod() {
		if (_formMethod != null) {
			return _formMethod;
		}

		_formMethod = _iFramePortletInstanceConfiguration.formMethod();

		if (Validator.isNull(_formMethod)) {
			_formMethod = _iFrameConfiguration.formMethod();
		}

		return _formMethod;
	}

	public String getFormPassword() {
		if (_formPassword != null) {
			return _formPassword;
		}

		_formPassword = _iFramePortletInstanceConfiguration.formPassword();

		return _formPassword;
	}

	public String getFormUserName() {
		if (_formUserName != null) {
			return _formUserName;
		}

		_formUserName = _iFramePortletInstanceConfiguration.formUserName();

		return _formUserName;
	}

	public String getFrameborder() {
		if (_frameborder != null) {
			return _frameborder;
		}

		_frameborder = _iFramePortletInstanceConfiguration.frameborder();

		return _frameborder;
	}

	public String getHeight() {
		if (_height != null) {
			return _height;
		}

		String windowState = String.valueOf(_request.getWindowState());

		if (windowState.equals(WindowState.MAXIMIZED)) {
			_height = getHeightMaximized();
		}
		else {
			_height = getHeightNormal();
		}

		return _height;
	}

	public String getHeightMaximized() {
		if (_heightMaximized != null) {
			return _heightMaximized;
		}

		_heightMaximized =
			_iFramePortletInstanceConfiguration.heightMaximized();

		return _heightMaximized;
	}

	public String getHeightNormal() {
		if (_heightNormal != null) {
			return _heightNormal;
		}

		_heightNormal = _iFramePortletInstanceConfiguration.heightNormal();

		return _heightNormal;
	}

	public String getHiddenVariables() {
		if (_hiddenVariables != null) {
			return _hiddenVariables;
		}

		_hiddenVariables =
			_iFramePortletInstanceConfiguration.hiddenVariables();

		if (Validator.isNull(_hiddenVariables)) {
			_hiddenVariables = StringUtil.merge(
				_iFrameConfiguration.hiddenVariables(), StringPool.SEMICOLON);
		}

		return _hiddenVariables;
	}

	public List<KeyValuePair> getHiddenVariablesKVP() {
		List<KeyValuePair> hiddenVariablesKVP = new ArrayList<>();

		List<String> hiddenVariableList = ListUtil.toList(
			StringUtil.split(getHiddenVariables(), CharPool.SEMICOLON));

		hiddenVariableList.addAll(getIframeVariables());

		for (String hiddenVariable : hiddenVariableList) {
			String hiddenKey = StringPool.BLANK;
			String hiddenValue = StringPool.BLANK;

			int pos = hiddenVariable.indexOf(StringPool.EQUAL);

			if (pos != -1) {
				hiddenKey = hiddenVariable.substring(0, pos);
				hiddenValue = hiddenVariable.substring(pos + 1);
			}

			hiddenVariablesKVP.add(new KeyValuePair(hiddenKey, hiddenValue));
		}

		return hiddenVariablesKVP;
	}

	public String getHspace() {
		if (_hspace != null) {
			return _hspace;
		}

		_hspace = _iFramePortletInstanceConfiguration.hspace();

		return _hspace;
	}

	public String getIframeBaseSrc() {
		if (_iframeBaseSrc != null) {
			return _iframeBaseSrc;
		}

		_iframeBaseSrc = getIframeSrc();

		int lastSlashPos = 0;

		if (_iframeBaseSrc.length() > 6) {
			lastSlashPos = _iframeBaseSrc.substring(7).lastIndexOf(
				StringPool.SLASH);

			if (lastSlashPos != -1) {
				_iframeBaseSrc = _iframeBaseSrc.substring(0, lastSlashPos + 8);
			}
		}

		return _iframeBaseSrc;
	}

	public String getIframeSrc() {
		if (_iframeSrc != null) {
			return _iframeSrc;
		}

		_iframeSrc = StringPool.BLANK;

		if (isRelative()) {
			_iframeSrc = _themeDisplay.getPathContext();
		}

		_iframeSrc += (String)_request.getAttribute(IFrameWebKeys.IFRAME_SRC);

		if (!ListUtil.isEmpty(getIframeVariables())) {
			if (_iframeSrc.contains(StringPool.QUESTION)) {
				_iframeSrc += StringPool.AMPERSAND;
			}
			else {
				_iframeSrc += StringPool.QUESTION;
			}

			_iframeSrc += StringUtil.merge(
				getIframeVariables(), StringPool.AMPERSAND);
		}

		return _iframeSrc;
	}

	public List<String> getIframeVariables() {
		List<String> iframeVariables = new ArrayList<>();

		Enumeration<String> enu = _request.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.startsWith(_IFRAME_PREFIX)) {
				iframeVariables.add(
					name.substring(_IFRAME_PREFIX.length()).concat(
						StringPool.EQUAL).concat(_request.getParameter(name)));
			}
		}

		return iframeVariables;
	}

	public String getLongdesc() {
		if (_longdesc != null) {
			return _longdesc;
		}

		_longdesc = _iFramePortletInstanceConfiguration.longdesc();

		return _longdesc;
	}

	public String getPassword() throws PortalException {
		if (_password != null) {
			return _password;
		}

		String authType = getAuthType();

		if (authType.equals("basic")) {
			_password = getBasicPassword();
		}
		else {
			_password = getFormPassword();
		}

		if (Validator.isNull(_password)) {
			return StringPool.BLANK;
		}

		if (Validator.isNull(getPasswordField())) {
			int pos = _password.indexOf(StringPool.EQUAL);

			if (pos != -1) {
				String fieldValuePair = _password;

				_passwordField = fieldValuePair.substring(0, pos);

				_password = fieldValuePair.substring(pos + 1);
			}
		}

		if (Validator.isNotNull(getPasswordField())) {
			_password = IFrameUtil.getPassword(_request, _password);
		}

		return _password;
	}

	public String getPasswordField() {
		if (_passwordField != null) {
			return _passwordField;
		}

		_passwordField = _iFramePortletInstanceConfiguration.passwordField();

		return _passwordField;
	}

	public String getScrolling() {
		if (_scrolling != null) {
			return _scrolling;
		}

		_scrolling = _iFramePortletInstanceConfiguration.scrolling();

		return _scrolling;
	}

	public String getSrc() {
		if (_src != null) {
			return _src;
		}

		_src = _iFramePortletInstanceConfiguration.src();

		return _src;
	}

	public String getTitle() {
		if (_title != null) {
			return _title;
		}

		_title = _iFramePortletInstanceConfiguration.title();

		return _title;
	}

	public String getUserName() throws PortalException {
		if (_userName != null) {
			return _userName;
		}

		String authType = getAuthType();

		if (authType.equals("basic")) {
			_userName = getBasicUserName();
		}
		else {
			_userName = getFormUserName();
		}

		if (Validator.isNull(_userName)) {
			return StringPool.BLANK;
		}

		if (Validator.isNull(getUserNameField())) {
			int pos = _userName.indexOf(StringPool.EQUAL);

			if (pos != -1) {
				String fieldValuePair = _userName;

				_userNameField = fieldValuePair.substring(0, pos);

				_userName = fieldValuePair.substring(pos + 1);
			}
		}

		if (Validator.isNotNull(getUserNameField())) {
			_userName = IFrameUtil.getUserName(_request, _userName);
		}

		return _userName;
	}

	public String getUserNameField() {
		if (_userNameField != null) {
			return _userNameField;
		}

		_userNameField = _iFramePortletInstanceConfiguration.userNameField();

		return _userNameField;
	}

	public String getVspace() {
		if (_vspace != null) {
			return _vspace;
		}

		_vspace = _iFramePortletInstanceConfiguration.vspace();

		return _vspace;
	}

	public String getWidth() {
		if (_width != null) {
			return _width;
		}

		_width = _iFramePortletInstanceConfiguration.width();

		return _width;
	}

	public boolean isAuth() {
		if (_auth != null) {
			return _auth;
		}

		_auth = _iFramePortletInstanceConfiguration.auth();

		return _auth;
	}

	public boolean isRelative() {
		if (_relative != null) {
			return _relative;
		}

		_relative = _iFramePortletInstanceConfiguration.relative();

		return _relative;
	}

	public boolean isResizeAutomatically() {
		if (_resizeAutomatically != null) {
			return _resizeAutomatically;
		}

		_resizeAutomatically =
			_iFramePortletInstanceConfiguration.resizeAutomatically();

		return _resizeAutomatically;
	}

	private static final String _IFRAME_PREFIX = "iframe_";

	private String _alt;
	private Boolean _auth;
	private String _authType;
	private String _basicPassword;
	private String _basicUserName;
	private String _border;
	private String _bordercolor;
	private String _formMethod;
	private String _formPassword;
	private String _formUserName;
	private String _frameborder;
	private String _height;
	private String _heightMaximized;
	private String _heightNormal;
	private String _hiddenVariables;
	private String _hspace;
	private String _iframeBaseSrc;
	private final IFrameConfiguration _iFrameConfiguration;
	private final IFramePortletInstanceConfiguration
		_iFramePortletInstanceConfiguration;
	private String _iframeSrc;
	private String _longdesc;
	private String _password;
	private String _passwordField;
	private Boolean _relative;
	private final PortletRequest _request;
	private Boolean _resizeAutomatically;
	private String _scrolling;
	private String _src;
	private final ThemeDisplay _themeDisplay;
	private String _title;
	private String _userName;
	private String _userNameField;
	private String _vspace;
	private String _width;

}