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

package com.liferay.portal.action;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.SessionClicks;

import java.util.Enumeration;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class SessionClickAction extends Action {

	@Override
	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			HttpSession session = request.getSession();

			Enumeration<String> enu = request.getParameterNames();

			boolean useHttpSession = ParamUtil.getBoolean(
				request, "useHttpSession");

			while (enu.hasMoreElements()) {
				String name = enu.nextElement();

				if (!name.equals("doAsUserId")) {
					String value = ParamUtil.getString(request, name);

					if (useHttpSession) {
						SessionClicks.put(session, name, value);
					}
					else {
						SessionClicks.put(request, name, value);
					}
				}
			}

			String value = getValue(request);

			if (value != null) {
				ServletOutputStream servletOutputStream =
					response.getOutputStream();

				servletOutputStream.print(value);
			}

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

	protected String getValue(HttpServletRequest request) {
		HttpSession session = request.getSession();

		String cmd = ParamUtil.getString(request, Constants.CMD);

		boolean useHttpSession = ParamUtil.getBoolean(
			request, "useHttpSession");

		if (cmd.equals("get")) {
			String key = ParamUtil.getString(request, "key");
			String value = StringPool.BLANK;

			if (useHttpSession) {
				value = SessionClicks.get(session, key, cmd);
			}
			else {
				value = SessionClicks.get(request, key, cmd);
			}

			return value;
		}
		else if (cmd.equals("getAll")) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			String[] keys = request.getParameterValues("key");

			for (String key : keys) {
				String value = StringPool.BLANK;

				if (useHttpSession) {
					value = SessionClicks.get(session, key, cmd);
				}
				else {
					value = SessionClicks.get(request, key, cmd);
				}

				jsonObject.put(key, value);
			}

			return jsonObject.toString();
		}

		return null;
	}

}