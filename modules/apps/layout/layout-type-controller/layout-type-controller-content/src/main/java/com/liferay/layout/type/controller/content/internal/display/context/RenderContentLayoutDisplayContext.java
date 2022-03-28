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

import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.PortletPathsUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rubén Pulido
 */
public class RenderContentLayoutDisplayContext {

	public RenderContentLayoutDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getPortletFooterPaths() {
		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			_httpServletResponse, unsyncStringWriter);

		for (Portlet portlet : _getPortlets()) {
			try {
				PortletPathsUtil.writeFooterPaths(
					pipingServletResponse, _getPortletPaths(portlet));
			}
			catch (Exception exception) {
				_log.error(
					"Unable to write portlet footer paths " +
						portlet.getPortletId(),
					exception);
			}
		}

		return unsyncStringWriter.toString();
	}

	public String getPortletHeaderPaths() {
		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			_httpServletResponse, unsyncStringWriter);

		for (Portlet portlet : _getPortlets()) {
			try {
				PortletPathsUtil.writeHeaderPaths(
					pipingServletResponse, _getPortletPaths(portlet));
			}
			catch (Exception exception) {
				_log.error(
					"Unable to write portlet header paths " +
						portlet.getPortletId(),
					exception);
			}
		}

		return unsyncStringWriter.toString();
	}

	private Map<String, Object> _getPortletPaths(Portlet portlet) {
		String portletId = portlet.getPortletId();

		Map<String, Object> portletIdPath = _portletIdPaths.get(portletId);

		if (portletIdPath != null) {
			return portletIdPath;
		}

		Map<String, Object> paths = PortletPathsUtil.getPortletPaths(
			_httpServletRequest, StringPool.BLANK, portlet);

		_portletIdPaths.put(portletId, paths);

		return paths;
	}

	private List<Portlet> _getPortlets() {
		if (_portlets != null) {
			return _portlets;
		}

		_portlets = new ArrayList<>();

		Set<String> uniquePortletNames = new HashSet<>();

		List<PortletPreferences> portletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _themeDisplay.getPlid());

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				_themeDisplay.getCompanyId(),
				portletPreferences.getPortletId());

			if ((portlet == null) || !portlet.isActive() ||
				portlet.isUndeployedPortlet()) {

				continue;
			}

			if (uniquePortletNames.add(portlet.getPortletName())) {
				_portlets.add(portlet);
			}
		}

		return _portlets;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RenderContentLayoutDisplayContext.class);

	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private final Map<String, Map<String, Object>> _portletIdPaths =
		new HashMap<>();
	private List<Portlet> _portlets;
	private final ThemeDisplay _themeDisplay;

}