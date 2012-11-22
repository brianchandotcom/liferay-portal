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

package com.liferay.portlet.dynamicdatamapping.action;

import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMXSDUtil;

import java.util.Locale;

import javax.servlet.Servlet;
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
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			JspFactory factory = JspFactory.getDefaultFactory();

			Servlet servlet = getServlet();

			PageContext pageContext = factory.getPageContext(
				servlet, request, response, null, true,
				JspWriter.DEFAULT_BUFFER, true);

			String className = ParamUtil.getString(request, "className");
			long classPK = ParamUtil.getLong(request, "classPK");
			String fieldName = ParamUtil.getString(request, "fieldName");
			boolean readOnly = ParamUtil.getBoolean(request, "readOnly");
			int repeatableIndex = ParamUtil.getInteger(
				request, "repeatableIndex");

			Locale locale = PortalUtil.getLocale(request);

			String fieldHTML = DDMXSDUtil.getFieldHTMLByName(
				pageContext, className, classPK, fieldName, repeatableIndex,
				null, null, null, readOnly, locale);

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