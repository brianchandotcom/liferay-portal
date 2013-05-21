/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.struts;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletModeFactory;
import com.liferay.portal.kernel.portlet.WindowStateFactory;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class StrutsURLEncoder implements URLEncoder {

	public static void setParameters(
		LiferayPortletURL liferayPortletURL, String queryString) {

		String[] params = StringUtil.split(queryString, '&');

		for (int i = 0; i < params.length; i++) {
			int pos = params[i].indexOf("=");

			if (pos != -1) {
				String param = params[i].substring(0, pos);
				String value = params[i].substring(pos + 1);

				if (param.equals("windowState")) {
					try {
						liferayPortletURL.setWindowState(
							WindowStateFactory.getWindowState(value));
					}
					catch (WindowStateException wse) {
						_log.error(wse.getMessage());
					}
				}
				else if (param.equals("portletMode")) {
					try {
						liferayPortletURL.setPortletMode(
							PortletModeFactory.getPortletMode(value));
					}
					catch (PortletModeException pme) {
						_log.error(pme.getMessage());
					}
				}
				else if (param.equals("actionURL")) {
					String lifecycle = PortletRequest.RENDER_PHASE;

					if (GetterUtil.getBoolean(value)) {
						lifecycle = PortletRequest.ACTION_PHASE;
					}

					liferayPortletURL.setLifecycle(lifecycle);
				}
				else {
					liferayPortletURL.setParameter(
						param, HttpUtil.decodeURL(value), true);
				}
			}
		}
	}

	public StrutsURLEncoder(
		String contextPath, String mainPath, String servletMapping,
		LiferayPortletURL liferayPortletURL) {

		_contextPath = contextPath;
		_mainPath = mainPath;
		_setServletMapping(servletMapping);
		_liferayPortletURL = liferayPortletURL;
		_windowState = liferayPortletURL.getWindowState();
		_portletMode = liferayPortletURL.getPortletMode();
	}

	public String encodeURL(HttpServletResponse response, String path) {
		if (_log.isDebugEnabled()) {
			_log.debug("Path " + path);
			_log.debug("Context path " + _contextPath);
			_log.debug("Servlet mapping " + _servletMapping);
		}

		String encodedURL = path;

		if (!path.startsWith("//") &&
			!path.startsWith(_contextPath) &&
			!path.startsWith(_servletMapping)) {

			return encodedURL;
		}

		// Struts uses &amp; instead of & to delimit parameter key value pairs
		// when you set the "name" attribute for html:link.

		path = StringUtil.replace(path, "&amp;", "&");

		// Reset portlet URL settings so it can be reused

		_liferayPortletURL.setLifecycle(PortletRequest.RENDER_PHASE);
		_liferayPortletURL.setParameters(new HashMap<String, String[]>());

		try {
			_liferayPortletURL.setWindowState(_windowState);
		}
		catch (WindowStateException wse) {
		}

		try {
			_liferayPortletURL.setPortletMode(_portletMode);
		}
		catch (PortletModeException pme) {
		}

		// Separate the Struts action from the query string

		String strutsAction = path;
		String queryString = StringPool.BLANK;

		int pos = strutsAction.indexOf(CharPool.QUESTION);

		if (pos != -1) {
			strutsAction = path.substring(0, pos);
			queryString = path.substring(pos + 1);
		}

		// Set the Struts action

		if (strutsAction.startsWith("c/")) {
			strutsAction = strutsAction.substring(1);
		}
		else if (strutsAction.startsWith("/c/")) {
			strutsAction = strutsAction.substring(2);
		}

		if (Validator.isNotNull(_contextPath)) {
			strutsAction = strutsAction.substring(_contextPath.length());
		}

		if (strutsAction.startsWith(_servletMapping)) {
			strutsAction = strutsAction.substring(_servletMapping.length());
		}

		if (!strutsAction.startsWith(StringPool.SLASH)) {
			strutsAction = StringPool.SLASH + strutsAction;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Struts action " + strutsAction);
		}

		_liferayPortletURL.setParameter("struts_action", strutsAction);

		// Set the query string

		setParameters(_liferayPortletURL, queryString);

		// Return the portlet URL

		encodedURL = _liferayPortletURL.toString();

		if (_log.isDebugEnabled()) {
			_log.debug("Encoded portlet URL " + encodedURL);
		}

		return encodedURL;
	}

	private void _setServletMapping(String servletMapping) {
		if (servletMapping != null) {

			// See org.apache.struts.util.RequestUtils.getActionMappingURL

			if (servletMapping.endsWith("/*")) {
				int pos = 0;

				if (servletMapping.startsWith(_mainPath)) {
					pos = _mainPath.length() - 2;
				}

				_servletMapping = servletMapping.substring(
					pos, servletMapping.length() - 1);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(StrutsURLEncoder.class);

	private String _contextPath;
	private LiferayPortletURL _liferayPortletURL;
	private String _mainPath;
	private PortletMode _portletMode;
	private String _servletMapping = StringPool.BLANK;
	private WindowState _windowState;

}