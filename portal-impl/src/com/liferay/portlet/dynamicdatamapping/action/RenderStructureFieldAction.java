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

package com.liferay.portlet.dynamicdatamapping.action;

import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.dynamicdatamapping.util.DDMXSDUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Bruno Basto
 */
public class RenderStructureFieldAction extends Action {

	@Override
	public ActionForward execute(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		try {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			JspFactory jspFactory = JspFactory.getDefaultFactory();

			PageContext pageContext = jspFactory.getPageContext(
				getServlet(), request, response, null, true,
				JspWriter.DEFAULT_BUFFER, true);

			long classNameId = ParamUtil.getLong(request, "classNameId");
			long classPK = ParamUtil.getLong(request, "classPK");
			String fieldName = ParamUtil.getString(request, "fieldName");
			String namespace = ParamUtil.getString(request, "namespace");
			String portletNamespace = ParamUtil.getString(
				request, "portletNamespace");
			boolean readOnly = ParamUtil.getBoolean(request, "readOnly");

			request.setAttribute("aui:form:portletNamespace", portletNamespace);

			String fieldHTML = DDMXSDUtil.getFieldHTMLByName(
				pageContext, classNameId, classPK, fieldName, null,
				portletNamespace, namespace, null, readOnly,
				themeDisplay.getLocale());

			response.setContentType(ContentTypes.TEXT_HTML);

			ServletResponseUtil.write(response, fieldHTML);

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

}