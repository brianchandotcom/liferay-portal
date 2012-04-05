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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

	public static void processCustomizationSettings(
			PageContext pageContext, String velocityTemplateId,
			String velocityTemplateContent)
		throws Exception {

		getRuntimePortlet().processCustomizationSettings(
			pageContext, velocityTemplateId, velocityTemplateContent);
	}

	public static void processPortlet(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws Exception {

		getRuntimePortlet().processPortlet(request, response, portlet);
	}

	public static void processPortlet(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, String columnId, Integer columnPos,
			Integer columnCount, String path)
		throws Exception {

		getRuntimePortlet().processPortlet(
			request, response, portlet, columnId, columnPos, columnCount, path);
	}

	public static void processPortlet(
			HttpServletRequest request, HttpServletResponse response,
			String portletId)
		throws Exception {

		getRuntimePortlet().processPortlet(request, response, portletId);
	}

	public static void processTemplate(
			PageContext pageContext, String velocityTemplateId,
			String velocityTemplateContent)
		throws Exception {

		getRuntimePortlet().processTemplate(
			pageContext, velocityTemplateId, velocityTemplateContent);
	}

	public static void processTemplate(
			PageContext pageContext, String portletId,
			String velocityTemplateId, String velocityTemplateContent)
		throws Exception {

		getRuntimePortlet().processTemplate(
			pageContext, portletId, velocityTemplateId,
			velocityTemplateContent);
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