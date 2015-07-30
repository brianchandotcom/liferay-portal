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
import com.liferay.portal.kernel.configuration.module.ModuleConfigurationException;
import com.liferay.portal.kernel.exception.PortalException;
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
		throws ModuleConfigurationException {

		_iFrameConfiguration = iFrameConfiguration;
		_request = request;

		_themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		_iFramePortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				IFramePortletInstanceConfiguration.class);
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

	public String getHeight() {
		if (_height != null) {
			return _height;
		}

		String windowState = String.valueOf(_request.getWindowState());

		if (windowState.equals(WindowState.MAXIMIZED)) {
			_height = _iFramePortletInstanceConfiguration.heightMaximized();
		}
		else {
			_height = _iFramePortletInstanceConfiguration.heightNormal();
		}

		return _height;
	}

	public List<KeyValuePair> getHiddenVariableKVPs() {
		List<KeyValuePair> hiddenVariableKVPs = new ArrayList<>();

		List<String> hiddenVariables = ListUtil.toList(
			StringUtil.split(getHiddenVariables(), CharPool.SEMICOLON));

		hiddenVariables.addAll(getIFrameVariables());

		for (String hiddenVariable : hiddenVariables) {
			String key = StringPool.BLANK;
			String value = StringPool.BLANK;

			int pos = hiddenVariable.indexOf(StringPool.EQUAL);

			if (pos != -1) {
				key = hiddenVariable.substring(0, pos);
				value = hiddenVariable.substring(pos + 1);
			}

			hiddenVariableKVPs.add(new KeyValuePair(key, value));
		}

		return hiddenVariableKVPs;
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

	public String getIframeBaseSrc() {
		if (_iFrameBaseSrc != null) {
			return _iFrameBaseSrc;
		}

		_iFrameBaseSrc = getIframeSrc();

		int index = 0;

		if (_iFrameBaseSrc.length() > 6) {
			index = _iFrameBaseSrc.substring(7).lastIndexOf(StringPool.SLASH);

			if (index != -1) {
				_iFrameBaseSrc = _iFrameBaseSrc.substring(0, index + 8);
			}
		}

		return _iFrameBaseSrc;
	}

	public IFramePortletInstanceConfiguration
		getIFramePortletInstanceConfiguration() {

		return _iFramePortletInstanceConfiguration;
	}

	public String getIframeSrc() {
		if (_iFrameSrc != null) {
			return _iFrameSrc;
		}

		_iFrameSrc = StringPool.BLANK;

		if (_iFramePortletInstanceConfiguration.relative()) {
			_iFrameSrc = _themeDisplay.getPathContext();
		}

		_iFrameSrc += (String)_request.getAttribute(IFrameWebKeys.IFRAME_SRC);

		if (!ListUtil.isEmpty(getIFrameVariables())) {
			if (_iFrameSrc.contains(StringPool.QUESTION)) {
				_iFrameSrc += StringPool.AMPERSAND;
			}
			else {
				_iFrameSrc += StringPool.QUESTION;
			}

			_iFrameSrc += StringUtil.merge(
				getIFrameVariables(), StringPool.AMPERSAND);
		}

		return _iFrameSrc;
	}

	public List<String> getIFrameVariables() {
		List<String> iFrameVariables = new ArrayList<>();

		Enumeration<String> enu = _request.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.startsWith(_IFRAME_PREFIX)) {
				iFrameVariables.add(
					name.substring(_IFRAME_PREFIX.length()) +
						StringPool.EQUAL + _request.getParameter(name));
			}
		}

		return iFrameVariables;
	}

	public String getPassword() throws PortalException {
		if (_password != null) {
			return _password;
		}

		String authType = getAuthType();

		if (authType.equals("basic")) {
			_password = _iFramePortletInstanceConfiguration.basicPassword();
		}
		else {
			_password = _iFramePortletInstanceConfiguration.formPassword();
		}

		if (Validator.isNull(_password)) {
			return StringPool.BLANK;
		}

		String passwordField =
			_iFramePortletInstanceConfiguration.passwordField();

		if (Validator.isNull(passwordField)) {
			int pos = _password.indexOf(StringPool.EQUAL);

			if (pos != -1) {
				String fieldValuePair = _password;

				passwordField = fieldValuePair.substring(0, pos);

				_password = fieldValuePair.substring(pos + 1);
			}
		}

		if (Validator.isNotNull(passwordField)) {
			_password = IFrameUtil.getPassword(_request, _password);
		}

		return _password;
	}

	public String getUserName() throws PortalException {
		if (_userName != null) {
			return _userName;
		}

		String authType = getAuthType();

		if (authType.equals("basic")) {
			_userName = _iFramePortletInstanceConfiguration.basicUserName();
		}
		else {
			_userName = _iFramePortletInstanceConfiguration.formUserName();
		}

		if (Validator.isNull(_userName)) {
			return StringPool.BLANK;
		}

		String userNameField =
			_iFramePortletInstanceConfiguration.userNameField();

		if (Validator.isNull(userNameField)) {
			int pos = _userName.indexOf(StringPool.EQUAL);

			if (pos != -1) {
				String fieldValuePair = _userName;

				userNameField = fieldValuePair.substring(0, pos);

				_userName = fieldValuePair.substring(pos + 1);
			}
		}

		if (Validator.isNotNull(userNameField)) {
			_userName = IFrameUtil.getUserName(_request, _userName);
		}

		return _userName;
	}

	private static final String _IFRAME_PREFIX = "iframe_";

	private String _authType;
	private String _formMethod;
	private String _height;
	private String _hiddenVariables;
	private String _iFrameBaseSrc;
	private final IFrameConfiguration _iFrameConfiguration;
	private final IFramePortletInstanceConfiguration
		_iFramePortletInstanceConfiguration;
	private String _iFrameSrc;
	private String _password;
	private final PortletRequest _request;
	private final ThemeDisplay _themeDisplay;
	private String _userName;

}