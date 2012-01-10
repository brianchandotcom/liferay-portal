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
public class RuntimePortletUtil {

	public static RuntimePortlet getRuntimePortlet() {
		return _runtimePortlet;
	}

	public static String processCustomizationSettings(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, PageContext pageContext,
			String velocityTemplateId, String velocityTemplateContent)
		throws Exception {

		return getRuntimePortlet().processCustomizationSettings(
			servletContext, request, response, pageContext, velocityTemplateId,
			velocityTemplateContent);
	}

	public static String processPortlet(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, RenderRequest renderRequest,
			RenderResponse renderResponse, String portletId, String queryString,
			boolean writeOutput)
		throws Exception {

		return getRuntimePortlet().processPortlet(
			servletContext, request, response, renderRequest, renderResponse,
			portletId, queryString, writeOutput);
	}

	public static String processPortlet(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, RenderRequest renderRequest,
			RenderResponse renderResponse, String portletId, String queryString,
			String columnId, Integer columnPos, Integer columnCount,
			boolean writeOutput)
		throws Exception {

		return getRuntimePortlet().processPortlet(
			servletContext, request, response, renderRequest, renderResponse,
			portletId, queryString, columnId, columnPos, columnCount,
			writeOutput);
	}

	public static String processPortlet(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, Portlet portlet, String queryString,
			String columnId, Integer columnPos, Integer columnCount,
			String path, boolean writeOutput)
		throws Exception {

		return getRuntimePortlet().processPortlet(
			servletContext, request, response, portlet, queryString, columnId,
			columnPos, columnCount, path, writeOutput);
	}

	public static String processPortlet(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, RenderRequest renderRequest,
			RenderResponse renderResponse, Portlet portlet, String portletId,
			String queryString, String columnId, Integer columnPos,
			Integer columnCount, String path, boolean writeOutput)
		throws Exception {

		return getRuntimePortlet().processPortlet(
			servletContext, request, response, renderRequest, renderResponse,
			portlet, portletId, queryString, columnId, columnPos, columnCount,
			path, writeOutput);
	}

	public static void processTemplate(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, PageContext pageContext,
			JspWriter jspWriter, String velocityTemplateId,
			String velocityTemplateContent)
		throws Exception {

		getRuntimePortlet().processTemplate(
			servletContext, request, response, pageContext, jspWriter,
			velocityTemplateId, velocityTemplateContent);
	}

	public static void processTemplate(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, PageContext pageContext,
			JspWriter jspWriter, String portletId, String velocityTemplateId,
			String velocityTemplateContent)
		throws Exception {

		getRuntimePortlet().processTemplate(
			servletContext, request, response, pageContext, jspWriter,
			portletId, velocityTemplateId, velocityTemplateContent);
	}

	public static String processXML(
			HttpServletRequest request, String content,
			RuntimeLogic runtimeLogic)
		throws Exception {

		return getRuntimePortlet().processXML(request, content, runtimeLogic);
	}

	public void setRuntimePortlet(RuntimePortlet runtimePortlet) {
		_runtimePortlet = runtimePortlet;
	}

	private static RuntimePortlet _runtimePortlet;

}