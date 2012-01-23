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

package com.liferay.portlet.layoutconfiguration.util;

import com.liferay.portal.model.Portlet;
import com.liferay.portlet.layoutconfiguration.util.xml.RuntimeLogic;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Shuyang Zhou
 */
public interface RuntimePortlet {

	public String processCustomizationSettings(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, PageContext pageContext,
			String velocityTemplateId, String velocityTemplateContent)
		throws Exception;

	public String processPortlet(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, Portlet portlet, String queryString,
			String columnId, Integer columnPos, Integer columnCount,
			String path, boolean writeOutput)
		throws Exception;

	public String processPortlet(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, RenderRequest renderRequest,
			RenderResponse renderResponse, Portlet portlet, String portletId,
			String queryString, String columnId, Integer columnPos,
			Integer columnCount, String path, boolean writeOutput)
		throws Exception;

	public String processPortlet(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, RenderRequest renderRequest,
			RenderResponse renderResponse, String portletId, String queryString,
			boolean writeOutput)
		throws Exception;

	public String processPortlet(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, RenderRequest renderRequest,
			RenderResponse renderResponse, String portletId, String queryString,
			String columnId, Integer columnPos, Integer columnCount,
			boolean writeOutput)
		throws Exception;

	public void processTemplate(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, PageContext pageContext,
			JspWriter jspWriter, String velocityTemplateId,
			String velocityTemplateContent)
		throws Exception;

	public void processTemplate(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, PageContext pageContext,
			JspWriter jspWriter, String portletId, String velocityTemplateId,
			String velocityTemplateContent)
		throws Exception;

	public String processXML(
			HttpServletRequest request, String content,
			RuntimeLogic runtimeLogic)
		throws Exception;

}