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

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PipingPageContext;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.velocity.VelocityContext;
import com.liferay.portal.kernel.velocity.VelocityEngineUtil;
import com.liferay.portal.kernel.velocity.VelocityVariablesUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.PortletDisplayFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.layoutconfiguration.util.velocity.CustomizationSettingsProcessor;
import com.liferay.portlet.layoutconfiguration.util.velocity.TemplateProcessor;
import com.liferay.portlet.layoutconfiguration.util.xml.RuntimeLogic;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletConfig;
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
public class RuntimePortletImpl implements RuntimePortlet {

	public String processCustomizationSettings(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, PageContext pageContext,
			String velocityTemplateId, String velocityTemplateContent)
		throws Exception {

		if (Validator.isNull(velocityTemplateContent)) {
			return StringPool.BLANK;
		}

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		CustomizationSettingsProcessor processor =
			new CustomizationSettingsProcessor(
				request, new PipingPageContext(pageContext, unsyncStringWriter),
				unsyncStringWriter);

		VelocityContext velocityContext =
			VelocityEngineUtil.getWrappedStandardToolsContext();

		velocityContext.put("processor", processor);

		// Velocity variables

		VelocityVariablesUtil.insertVariables(velocityContext, request);

		// liferay:include tag library

		MethodHandler methodHandler = new MethodHandler(
			_initMethodKey, servletContext, request,
			new PipingServletResponse(response, unsyncStringWriter),
			pageContext);

		Object velocityTaglib = methodHandler.invoke(true);

		velocityContext.put("taglibLiferay", velocityTaglib);
		velocityContext.put("theme", velocityTaglib);

		try {
			VelocityEngineUtil.mergeTemplate(
				velocityTemplateId, velocityTemplateContent, velocityContext,
				unsyncStringWriter);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw e;
		}

		return unsyncStringWriter.toString();
	}

	public String processPortlet(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, RenderRequest renderRequest,
			RenderResponse renderResponse, String portletId, String queryString,
			boolean writeOutput)
		throws Exception {

		return processPortlet(
			servletContext, request, response, renderRequest, renderResponse,
			portletId, queryString, null, null, null, writeOutput);
	}

	public String processPortlet(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, RenderRequest renderRequest,
			RenderResponse renderResponse, String portletId, String queryString,
			String columnId, Integer columnPos, Integer columnCount,
			boolean writeOutput)
		throws Exception {

		return processPortlet(
			servletContext, request, response, renderRequest, renderResponse,
			null, portletId, queryString, columnId, columnPos, columnCount,
			null, writeOutput);
	}

	public String processPortlet(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, Portlet portlet, String queryString,
			String columnId, Integer columnPos, Integer columnCount,
			String path, boolean writeOutput)
		throws Exception {

		return processPortlet(
			servletContext, request, response, null, null, portlet,
			portlet.getPortletId(), queryString, columnId, columnPos,
			columnCount, path, writeOutput);
	}

	public String processPortlet(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, RenderRequest renderRequest,
			RenderResponse renderResponse, Portlet portlet, String portletId,
			String queryString, String columnId, Integer columnPos,
			Integer columnCount, String path, boolean writeOutput)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (portlet == null) {
			portlet = PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(), portletId);
		}

		if ((portlet != null) && (portlet.isInstanceable()) &&
			(!portlet.isAddDefaultResource())) {

			String instanceId = portlet.getInstanceId();

			if (Validator.isNotNull(instanceId) &&
				Validator.isPassword(instanceId) &&
				(instanceId.length() >= 4)) {

				/*portletId += PortletConstants.INSTANCE_SEPARATOR + instanceId;

				portlet = PortletLocalServiceUtil.getPortletById(
					themeDisplay.getCompanyId(), portletId);*/
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Portlet " + portlet.getPortletId() +
							" is instanceable but does not have a " +
								"valid instance id");
				}

				portlet = null;
			}
		}

		if (portlet == null) {
			return StringPool.BLANK;
		}

		// Capture the current portlet's settings to reset them once the child
		// portlet is rendered

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletDisplay portletDisplayClone = PortletDisplayFactory.create();

		portletDisplay.copyTo(portletDisplayClone);

		PortletConfig portletConfig = (PortletConfig)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		try {
			return PortalUtil.renderPortlet(
				servletContext, request, response, portlet, queryString,
				columnId, columnPos, columnCount, path, writeOutput);
		}
		finally {
			portletDisplay.copyFrom(portletDisplayClone);

			portletDisplayClone.recycle();

			_defineObjects(
				request, portletConfig, renderRequest, renderResponse);
		}
	}

	public void processTemplate(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, PageContext pageContext,
			JspWriter jspWriter, String velocityTemplateId,
			String velocityTemplateContent)
		throws Exception {

		processTemplate(
			servletContext, request, response, pageContext, jspWriter, null,
			velocityTemplateId, velocityTemplateContent);
	}

	public void processTemplate(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, PageContext pageContext,
			JspWriter jspWriter, String portletId, String velocityTemplateId,
			String velocityTemplateContent)
		throws Exception {

		if (Validator.isNull(velocityTemplateContent)) {
			return;
		}

		TemplateProcessor processor = new TemplateProcessor(
			servletContext, request, response, portletId);

		VelocityContext velocityContext =
			VelocityEngineUtil.getWrappedStandardToolsContext();

		velocityContext.put("processor", processor);

		// Velocity variables

		VelocityVariablesUtil.insertVariables(velocityContext, request);

		// liferay:include tag library

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		MethodHandler methodHandler = new MethodHandler(
			_initMethodKey, servletContext, request,
			new PipingServletResponse(response, unsyncStringWriter),
			pageContext);

		Object velocityTaglib = methodHandler.invoke(true);

		velocityContext.put("taglibLiferay", velocityTaglib);
		velocityContext.put("theme", velocityTaglib);

		try {
			VelocityEngineUtil.mergeTemplate(
				velocityTemplateId, velocityTemplateContent, velocityContext,
				unsyncStringWriter);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw e;
		}

		String output = unsyncStringWriter.toString();

		Map<Portlet, Object[]> portletsMap = processor.getPortletsMap();

		Map<String, StringBundler> contentsMap =
			new HashMap<String, StringBundler>(portletsMap.size());

		for (Map.Entry<Portlet, Object[]> entry : portletsMap.entrySet()) {
			Portlet portlet = entry.getKey();
			Object[] value = entry.getValue();

			String queryString = (String)value[0];
			String columnId = (String)value[1];
			Integer columnPos = (Integer)value[2];
			Integer columnCount = (Integer)value[3];

			UnsyncStringWriter portletUnsyncStringWriter =
				new UnsyncStringWriter();

			PipingServletResponse pipingServletResponse =
				new PipingServletResponse(response, portletUnsyncStringWriter);

			processPortlet(
				servletContext, request, pipingServletResponse, portlet,
				queryString, columnId, columnPos, columnCount, null, true);

			contentsMap.put(
				portlet.getPortletId(),
				portletUnsyncStringWriter.getStringBundler());
		}

		StringBundler sb = StringUtil.replaceWithStringBundler(
			output, "[$TEMPLATE_PORTLET_", "$]", contentsMap);

		sb.writeTo(jspWriter);
	}

	public String processXML(
			HttpServletRequest request, String content,
			RuntimeLogic runtimeLogic)
		throws Exception {

		if (Validator.isNull(content)) {
			return StringPool.BLANK;
		}

		int index = content.indexOf(runtimeLogic.getOpenTag());

		if (index == -1) {
			return content;
		}

		Portlet renderPortlet = (Portlet)request.getAttribute(
			WebKeys.RENDER_PORTLET);

		Boolean renderPortletResource = (Boolean)request.getAttribute(
			WebKeys.RENDER_PORTLET_RESOURCE);

		String outerPortletId = (String)request.getAttribute(
			WebKeys.OUTER_PORTLET_ID);

		if (outerPortletId == null) {
			request.setAttribute(
				WebKeys.OUTER_PORTLET_ID, renderPortlet.getPortletId());
		}

		try {
			request.setAttribute(WebKeys.RENDER_PORTLET_RESOURCE, Boolean.TRUE);

			StringBundler sb = new StringBundler();

			int x = 0;
			int y = index;

			while (y != -1) {
				sb.append(content.substring(x, y));

				int close1 = content.indexOf(runtimeLogic.getClose1Tag(), y);
				int close2 = content.indexOf(runtimeLogic.getClose2Tag(), y);

				if ((close2 == -1) || ((close1 != -1) && (close1 < close2))) {
					x = close1 + runtimeLogic.getClose1Tag().length();
				}
				else {
					x = close2 + runtimeLogic.getClose2Tag().length();
				}

				sb.append(runtimeLogic.processXML(content.substring(y, x)));

				y = content.indexOf(runtimeLogic.getOpenTag(), x);
			}

			if (y == -1) {
				sb.append(content.substring(x, content.length()));
			}

			return sb.toString();
		}
		finally {
			if (outerPortletId == null) {
				request.removeAttribute(WebKeys.OUTER_PORTLET_ID);
			}

			request.setAttribute(WebKeys.RENDER_PORTLET, renderPortlet);

			if (renderPortletResource == null) {
				request.removeAttribute(WebKeys.RENDER_PORTLET_RESOURCE);
			}
			else {
				request.setAttribute(
					WebKeys.RENDER_PORTLET_RESOURCE, renderPortletResource);
			}
		}
	}

	private static void _defineObjects(
		HttpServletRequest request, PortletConfig portletConfig,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		if (portletConfig != null) {
			request.setAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG, portletConfig);
		}

		if (renderRequest != null) {
			request.setAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST, renderRequest);
		}

		if (renderResponse != null) {
			request.setAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE, renderResponse);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(RuntimePortletUtil.class);

	private static MethodKey _initMethodKey = new MethodKey(
		"com.liferay.taglib.util.VelocityTaglib", "init", ServletContext.class,
		HttpServletRequest.class, HttpServletResponse.class, PageContext.class);

}