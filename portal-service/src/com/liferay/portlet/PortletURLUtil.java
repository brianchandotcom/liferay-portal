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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import java.util.Enumeration;
import java.util.Map;

import javax.portlet.MimeResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Miguel Pastor
 */
public class PortletURLUtil {

	public static PortletURL clone(
			LiferayPortletURL liferayPortletURL, String lifecycle,
			LiferayPortletResponse liferayPortletResponse)
		throws PortletException {

		LiferayPortletURL newLiferayPortletURL =
			liferayPortletResponse.createLiferayPortletURL(lifecycle);

		newLiferayPortletURL.setPortletId(liferayPortletURL.getPortletId());

		WindowState windowState = liferayPortletURL.getWindowState();

		if (windowState != null) {
			newLiferayPortletURL.setWindowState(windowState);
		}

		PortletMode portletMode = liferayPortletURL.getPortletMode();

		if (portletMode != null) {
			newLiferayPortletURL.setPortletMode(portletMode);
		}

		newLiferayPortletURL.setParameters(liferayPortletURL.getParameterMap());

		return newLiferayPortletURL;
	}

	public static PortletURL clone(
			PortletURL portletURL,
			LiferayPortletResponse liferayPortletResponse)
		throws PortletException {

		LiferayPortletURL liferayPortletURL = (LiferayPortletURL)portletURL;

		return clone(
			liferayPortletURL, liferayPortletURL.getLifecycle(),
			liferayPortletResponse);
	}

	public static PortletURL clone(
			PortletURL portletURL, MimeResponse mimeResponse)
		throws PortletException {

		LiferayPortletURL liferayPortletURL = (LiferayPortletURL)portletURL;

		return clone(
			liferayPortletURL, liferayPortletURL.getLifecycle(),
			(LiferayPortletResponse)mimeResponse);
	}

	public static PortletURL clone(
			PortletURL portletURL, String lifecycle,
			LiferayPortletResponse liferayPortletResponse)
		throws PortletException {

		LiferayPortletURL liferayPortletURL = (LiferayPortletURL)portletURL;

		return clone(liferayPortletURL, lifecycle, liferayPortletResponse);
	}

	public static PortletURL clone(
			PortletURL portletURL, String lifecycle, MimeResponse mimeResponse)
		throws PortletException {

		LiferayPortletURL liferayPortletURL = (LiferayPortletURL)portletURL;

		return clone(
			liferayPortletURL, lifecycle, (LiferayPortletResponse)mimeResponse);
	}

	public static PortletURL getCurrent(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL portletURL = (PortletURL)liferayPortletRequest.getAttribute(
			WebKeys.CURRENT_PORTLET_URL);

		if (portletURL != null) {
			return portletURL;
		}

		portletURL = liferayPortletResponse.createRenderURL();

		Enumeration<String> enu = liferayPortletRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String param = enu.nextElement();
			String[] values = liferayPortletRequest.getParameterValues(param);

			boolean addParam = true;

			// Don't set paramter values that are over 32 kb. See LEP-1755.

			for (int i = 0; i < values.length; i++) {
				if (values[i].length() > _CURRENT_URL_PARAMETER_THRESHOLD) {
					addParam = false;

					break;
				}
			}

			if (addParam) {
				portletURL.setParameter(param, values);
			}
		}

		liferayPortletRequest.setAttribute(
			WebKeys.CURRENT_PORTLET_URL, portletURL);

		return portletURL;
	}

	public static PortletURL getCurrent(
		PortletRequest portletRequest, MimeResponse mimeResponse) {

		return getCurrent(
			(LiferayPortletRequest)portletRequest,
			(LiferayPortletResponse)mimeResponse);
	}

	public static String getRefreshURL(
		HttpServletRequest request, ThemeDisplay themeDisplay) {

		StringBundler sb = new StringBundler(34);

		sb.append(themeDisplay.getPathMain());
		sb.append("/portal/render_portlet?p_l_id=");

		long plid = themeDisplay.getPlid();

		sb.append(plid);

		Portlet portlet = (Portlet)request.getAttribute(WebKeys.RENDER_PORTLET);

		String portletId = portlet.getPortletId();

		sb.append("&p_p_id=");
		sb.append(portletId);

		sb.append("&p_p_lifecycle=0&p_t_lifecycle=");
		sb.append(themeDisplay.getLifecycle());

		WindowState windowState = WindowState.NORMAL;

		if (themeDisplay.isStatePopUp()) {
			windowState = LiferayWindowState.POP_UP;
		}
		else {
			LayoutTypePortlet layoutTypePortlet =
				themeDisplay.getLayoutTypePortlet();

			if (layoutTypePortlet.hasStateMaxPortletId(portletId)) {
				windowState = WindowState.MAXIMIZED;
			}
			else if (layoutTypePortlet.hasStateMinPortletId(portletId)) {
				windowState = WindowState.MINIMIZED;
			}
		}

		sb.append("&p_p_state=");
		sb.append(windowState);

		sb.append("&p_p_mode=view&p_p_col_id=");

		String columnId = (String)request.getAttribute(
			WebKeys.RENDER_PORTLET_COLUMN_ID);

		sb.append(columnId);

		Integer columnPos = (Integer)request.getAttribute(
			WebKeys.RENDER_PORTLET_COLUMN_POS);

		sb.append("&p_p_col_pos=");
		sb.append(columnPos);

		Integer columnCount = (Integer)request.getAttribute(
			WebKeys.RENDER_PORTLET_COLUMN_COUNT);

		sb.append("&p_p_col_count=");
		sb.append(columnCount);

		if (portlet.isStatic()) {
			sb.append("&p_p_static=1");

			if (portlet.isStaticStart()) {
				sb.append("&p_p_static_start=1");
			}
		}

		sb.append("&p_p_isolated=1");

		long sourceGroupId = ParamUtil.getLong(request, "p_v_l_s_g_id");

		if (sourceGroupId > 0) {
			sb.append("&p_v_l_s_g_id=");
			sb.append(sourceGroupId);
		}

		String doAsUserId = themeDisplay.getDoAsUserId();

		if (Validator.isNotNull(doAsUserId)) {
			sb.append("&doAsUserId=");
			sb.append(HttpUtil.encodeURL(doAsUserId));
		}

		String currentURL = PortalUtil.getCurrentURL(request);

		sb.append("&currentURL=");
		sb.append(HttpUtil.encodeURL(currentURL));

		String ppid = ParamUtil.getString(request, "p_p_id");

		if (!ppid.equals(portletId)) {
			return sb.toString();
		}

		String settingsScope = (String)request.getAttribute(
			WebKeys.SETTINGS_SCOPE);

		settingsScope = ParamUtil.get(request, "settingsScope", settingsScope);

		if (Validator.isNotNull(settingsScope)) {
			sb.append("&settingsScope=");
			sb.append(settingsScope);
		}

		String namespace = PortalUtil.getPortletNamespace(portletId);

		Map<String, String[]> parameters = request.getParameterMap();

		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			String name = entry.getKey();

			if (name.startsWith(StringPool.UNDERLINE) &&
				!name.startsWith(namespace)) {

				continue;
			}

			if (!PortalUtil.isReservedParameter(name) &&
				!name.equals("currentURL") && !name.equals("settingsScope") &&
				!isRefreshURLReservedParameter(name, namespace)) {

				String[] values = entry.getValue();

				if (values == null) {
					continue;
				}

				for (int i = 0; i < values.length; i++) {
					sb.append(StringPool.AMPERSAND);
					sb.append(name);
					sb.append(StringPool.EQUAL);
					sb.append(HttpUtil.encodeURL(values[i]));
				}
			}
		}

		return sb.toString();
	}

	protected static boolean isRefreshURLReservedParameter(
		String parameter, String namespace) {

		if (ArrayUtil.isEmpty(_PORTLET_URL_REFRESH_URL_RESERVED_PARAMETERS)) {
			return false;
		}

		for (String reservedParameter :
				_PORTLET_URL_REFRESH_URL_RESERVED_PARAMETERS) {

			if (parameter.equals(reservedParameter) ||
				parameter.equals(namespace.concat(reservedParameter))) {

				return true;
			}
		}

		return false;
	}

	private static final int _CURRENT_URL_PARAMETER_THRESHOLD = 32768;

	private static final String[] _PORTLET_URL_REFRESH_URL_RESERVED_PARAMETERS =
		PropsUtil.getArray(
			PropsKeys.PORTLET_URL_REFRESH_URL_RESERVED_PARAMETERS);

}