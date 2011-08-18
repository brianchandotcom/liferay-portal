/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
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
 */
public class PortletURLUtil {

	public static PortletURL getCurrent(
		PortletRequest portletRequest, MimeResponse mimeResponse) {

		PortletURL portletURL = mimeResponse.createRenderURL();

		Enumeration<String> enu = portletRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String param = enu.nextElement();
			String[] values = portletRequest.getParameterValues(param);

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

		return portletURL;
	}

	public static PortletURL clone(
			PortletURL portletURL, MimeResponse mimeResponse)
		throws PortletException {

		LiferayPortletURL liferayPortletURL = (LiferayPortletURL)portletURL;

		return clone(
			liferayPortletURL, liferayPortletURL.getLifecycle(), mimeResponse);
	}

	public static PortletURL clone(
			PortletURL portletURL, String lifecycle, MimeResponse mimeResponse)
		throws PortletException {

		LiferayPortletURL liferayPortletURL = (LiferayPortletURL)portletURL;

		return clone(liferayPortletURL, lifecycle, mimeResponse);
	}

	public static PortletURL clone(
			LiferayPortletURL liferayPortletURL, String lifecycle,
			MimeResponse mimeResponse)
		throws PortletException {

		LiferayPortletURL newURLImpl = null;

		if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
			newURLImpl = (LiferayPortletURL)mimeResponse.createActionURL();
		}
		else if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
			newURLImpl = (LiferayPortletURL)mimeResponse.createRenderURL();
		}

		newURLImpl.setPortletId(liferayPortletURL.getPortletId());

		WindowState windowState = liferayPortletURL.getWindowState();

		if (windowState != null) {
			newURLImpl.setWindowState(windowState);
		}

		PortletMode portletMode = liferayPortletURL.getPortletMode();

		if (portletMode != null) {
			newURLImpl.setPortletMode(portletMode);
		}

		newURLImpl.setParameters(liferayPortletURL.getParameterMap());

		return newURLImpl;
	}

	public static String getRefreshURL(
		HttpServletRequest request, ThemeDisplay themeDisplay) {

		StringBundler sb = new StringBundler(32);

		sb.append(themeDisplay.getPathMain());
		sb.append("/portal/render_portlet?p_l_id=");

		long plid = themeDisplay.getPlid();

		sb.append(plid);

		Portlet portlet = (Portlet)request.getAttribute(
			WebKeys.RENDER_PORTLET);

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

		String doAsUserId = themeDisplay.getDoAsUserId();

		if (Validator.isNotNull(doAsUserId)) {
			sb.append("&doAsUserId=");
			sb.append(HttpUtil.encodeURL(doAsUserId));
		}

		String currentURL = PortalUtil.getCurrentURL(request);

		sb.append("&currentURL=");
		sb.append(HttpUtil.encodeURL(currentURL));

		String ppid = ParamUtil.getString(request, "p_p_id");

		if (ppid.equals(portletId)) {
			Map<String, String[]> parameters = request.getParameterMap();

			for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
				String name = entry.getKey();

				if (!PortalUtil.isReservedParameter(name) &&
					!name.equals("currentURL")) {

					String[] values = entry.getValue();

					for (int i = 0; i < values.length; i++) {
						sb.append(StringPool.AMPERSAND);
						sb.append(name);
						sb.append(StringPool.EQUAL);
						sb.append(HttpUtil.encodeURL(values[i]));
					}
				}
			}
		}

		String outerPortletId = PortalUtil.getOuterPortletId(request);

		if (outerPortletId != null) {
			sb.append(StringPool.AMPERSAND);
			sb.append("p_o_p_id");
			sb.append(StringPool.EQUAL);
			sb.append(HttpUtil.encodeURL(outerPortletId));
		}

		return sb.toString();
	}

	private static final int _CURRENT_URL_PARAMETER_THRESHOLD = 32768;

}