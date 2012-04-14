/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.SessionClicks;

import java.util.Enumeration;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			Enumeration<String> enu = request.getParameterNames();

			while (enu.hasMoreElements()) {
				String name = enu.nextElement();

				if (!name.equals("doAsUserId")) {
					String value = ParamUtil.getString(request, name);

					SessionClicks.put(request, name, value);
				}
			}

			String cmd = ParamUtil.getString(request, "cmd");

			String key = ParamUtil.getString(request, "key");

			if (Validator.isNotNull(key) &&
			    (cmd.equals("get") || cmd.equals("getAll"))) {

				String result = StringPool.BLANK;

				if (cmd.equals("get")) {
					result = SessionClicks.get(request, key, cmd);
				}
				else if (cmd.equals("getAll")) {
					String[] keys = request.getParameterValues("key");

					JSONObject storedValues = 
						JSONFactoryUtil.createJSONObject();

					for (String storedKey : keys) {
						storedValues.put(storedKey,
							SessionClicks.get(request, storedKey, cmd));
					}

					result = storedValues.toString();
				}

				ServletOutputStream out = response.getOutputStream();

				out.println(result);
			}

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}
}