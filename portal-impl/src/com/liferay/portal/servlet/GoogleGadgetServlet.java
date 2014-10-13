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

package com.liferay.portal.servlet;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alberto Montero
 */
public class GoogleGadgetServlet extends HttpServlet {

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		try {
			String content = getContent(request);

			if (content == null) {
				PortalUtil.sendError(
					HttpServletResponse.SC_NOT_FOUND,
					new NoSuchLayoutException(), request, response);
			}
			else {
				request.setAttribute(WebKeys.GOOGLE_GADGET, Boolean.TRUE);

				response.setContentType(ContentTypes.TEXT_XML);

				ServletResponseUtil.write(response, content);
			}
		}
		catch (Exception e) {
			_log.error(e, e);

			PortalUtil.sendError(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e, request,
				response);
		}
	}

	protected String getContent(HttpServletRequest request) throws Exception {
		String path = GetterUtil.getString(request.getPathInfo());

		if (Validator.isNull(path)) {
			return null;
		}

		int pos = path.indexOf(Portal.FRIENDLY_URL_SEPARATOR);

		if (pos == -1) {
			return null;
		}

		long companyId = PortalUtil.getCompanyId(request);

		String portletId = path.substring(
			pos + Portal.FRIENDLY_URL_SEPARATOR.length());

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			companyId, portletId);

		String title = portlet.getDisplayName();

		String widgetJsURL =
			PortalUtil.getPortalURL(request) + PortalUtil.getPathContext() +
				"/html/js/liferay/widget.js";

		String widgetURL = request.getRequestURL().toString();

		widgetURL = widgetURL.replaceFirst(
			PropsValues.GOOGLE_GADGET_SERVLET_MAPPING,
			PropsValues.WIDGET_SERVLET_MAPPING);

		StringBundler sb = new StringBundler(19);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Module>");
		sb.append("<ModulePrefs title=\"");
		sb.append(title);
		sb.append("\"/>");
		sb.append("<Content type=\"html\">");
		sb.append("<![CDATA[");
		sb.append("<script src=\"");
		sb.append(widgetJsURL);
		sb.append("\" ");
		sb.append("type=\"text/javascript\"></script>");
		sb.append("<script type=\"text/javascript\">");
		sb.append("window.Liferay.Widget({url:'");
		sb.append(widgetURL);
		sb.append("'});");
		sb.append("</script>");
		sb.append("]]>");
		sb.append("</Content>");
		sb.append("</Module>");

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GoogleGadgetServlet.class);

}