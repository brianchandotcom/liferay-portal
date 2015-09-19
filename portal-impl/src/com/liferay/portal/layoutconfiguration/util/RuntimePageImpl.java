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

package com.liferay.portal.layoutconfiguration.util;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.layoutconfiguration.util.velocity.CustomizationSettingsProcessor;
import com.liferay.portal.layoutconfiguration.util.velocity.TemplateProcessor;
import com.liferay.portal.layoutconfiguration.util.xml.ActionURLLogic;
import com.liferay.portal.layoutconfiguration.util.xml.PortletLogic;
import com.liferay.portal.layoutconfiguration.util.xml.RenderURLLogic;
import com.liferay.portal.layoutconfiguration.util.xml.RuntimeLogic;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.LayoutTemplateConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.LayoutTemplateLocalServiceUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.taglib.servlet.PipingServletResponse;
import com.liferay.taglib.util.DummyVelocityTaglib;
import com.liferay.taglib.util.VelocityTaglib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletResponse;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Shuyang Zhou
 */
@DoPrivileged
public class RuntimePageImpl implements RuntimePage {

	@Override
	public StringBundler getProcessedTemplate(
			HttpServletRequest request, HttpServletResponse response,
			String portletId, TemplateResource templateResource)
		throws Exception {

		return doDispatch(request, response, portletId, templateResource, true);
	}

	@Override
	public void processCustomizationSettings(
			HttpServletRequest request, HttpServletResponse response,
			TemplateResource templateResource)
		throws Exception {

		doDispatch(request, response, null, templateResource, false);
	}

	@Override
	public void processTemplate(
			HttpServletRequest request, HttpServletResponse response,
			String portletId, TemplateResource templateResource)
		throws Exception {

		StringBundler sb = doDispatch(
			request, response, portletId, templateResource, true);

		sb.writeTo(response.getWriter());
	}

	@Override
	public void processTemplate(
			HttpServletRequest request, HttpServletResponse response,
			TemplateResource templateResource)
		throws Exception {

		processTemplate(request, response, null, templateResource);
	}

	@Override
	public String processXML(
			HttpServletRequest request, HttpServletResponse response,
			String content)
		throws Exception {

		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		if ((portletResponse != null) &&
			!(portletResponse instanceof RenderResponse)) {

			throw new IllegalArgumentException(
				"processXML can only be invoked in the render phase");
		}

		RuntimeLogic portletLogic = new PortletLogic(request, response);

		content = processXML(request, content, portletLogic);

		if (portletResponse == null) {
			return content;
		}

		RenderResponse renderResponse = (RenderResponse)portletResponse;

		RuntimeLogic actionURLLogic = new ActionURLLogic(renderResponse);
		RuntimeLogic renderURLLogic = new RenderURLLogic(renderResponse);

		content = processXML(request, content, actionURLLogic);
		content = processXML(request, content, renderURLLogic);

		return content;
	}

	@Override
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

				String runtimePortletTag = content.substring(y, x);

				if ((renderPortlet != null) &&
					runtimePortletTag.contains(renderPortlet.getPortletId())) {

					return StringPool.BLANK;
				}

				sb.append(runtimeLogic.processXML(runtimePortletTag));

				y = content.indexOf(runtimeLogic.getOpenTag(), x);
			}

			if (y == -1) {
				sb.append(content.substring(x));
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

	protected StringBundler doDispatch(
			HttpServletRequest request, HttpServletResponse response,
			String portletId, TemplateResource templateResource,
			boolean processTemplate)
		throws Exception {

		ClassLoader pluginClassLoader = null;

		LayoutTemplate layoutTemplate = getLayoutTemplate(
			templateResource.getTemplateId());

		if (layoutTemplate != null) {
			String pluginServletContextName = GetterUtil.getString(
				layoutTemplate.getServletContextName());

			ServletContext pluginServletContext = ServletContextPool.get(
				pluginServletContextName);

			if (pluginServletContext != null) {
				pluginClassLoader = pluginServletContext.getClassLoader();
			}
		}

		ClassLoader contextClassLoader =
			ClassLoaderUtil.getContextClassLoader();

		try {
			if ((pluginClassLoader != null) &&
				(pluginClassLoader != contextClassLoader)) {

				ClassLoaderUtil.setContextClassLoader(pluginClassLoader);
			}

			if (processTemplate) {
				return doProcessTemplate(
					request, response, portletId, templateResource, false);
			}
			else {
				doProcessCustomizationSettings(
					request, response, templateResource, false);

				return null;
			}
		}
		finally {
			if ((pluginClassLoader != null) &&
				(pluginClassLoader != contextClassLoader)) {

				ClassLoaderUtil.setContextClassLoader(contextClassLoader);
			}
		}
	}

	protected void doProcessCustomizationSettings(
			HttpServletRequest request, HttpServletResponse response,
			TemplateResource templateResource, boolean restricted)
		throws Exception {

		CustomizationSettingsProcessor processor =
			new CustomizationSettingsProcessor(request, response);

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_VM, templateResource, restricted);

		template.put("processor", processor);

		// Velocity variables

		template.prepare(request);

		// liferay:include tag library

		VelocityTaglib velocityTaglib = new DummyVelocityTaglib();

		template.put("taglibLiferay", velocityTaglib);
		template.put("theme", velocityTaglib);

		try {
			template.processTemplate(response.getWriter());
		}
		catch (Exception e) {
			_log.error(e, e);

			throw e;
		}
	}

	protected StringBundler doProcessTemplate(
			HttpServletRequest request, HttpServletResponse response,
			String portletId, TemplateResource templateResource,
			boolean restricted)
		throws Exception {

		TemplateProcessor processor = new TemplateProcessor(
			request, response, portletId);

		TemplateManager templateManager =
			TemplateManagerUtil.getTemplateManager(
				TemplateConstants.LANG_TYPE_VM);

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_VM, templateResource, restricted);

		template.put("processor", processor);

		// Velocity variables

		template.prepare(request);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		templateManager.addTaglibTheme(
			template, "taglibLiferay", request,
			new PipingServletResponse(response, unsyncStringWriter));

		try {
			template.processTemplate(unsyncStringWriter);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw e;
		}

		Map<String, StringBundler> contentsMap = new HashMap<>();

		Map<Integer, List<PortletRenderer>> portletRenderersMap =
			processor.getPortletRenderers();

		for (Map.Entry<Integer, List<PortletRenderer>> entry :
				portletRenderersMap.entrySet()) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Processing portlets with render weight " + entry.getKey());
			}

			List<PortletRenderer> portletRenderers = entry.getValue();

			StopWatch stopWatch = new StopWatch();

			stopWatch.start();

			if (_log.isDebugEnabled()) {
				_log.debug("Start serial rendering");
			}

			for (PortletRenderer portletRenderer : portletRenderers) {
				Portlet portlet = portletRenderer.getPortlet();

				contentsMap.put(
					portlet.getPortletId(),
					portletRenderer.render(request, response));

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Serially rendered portlet " +
							portlet.getPortletId() + " in " +
								stopWatch.getTime() + " ms");
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Finished serial rendering in " + stopWatch.getTime() +
						" ms");
			}
		}

		StringBundler sb = StringUtil.replaceWithStringBundler(
			unsyncStringWriter.toString(), "[$TEMPLATE_PORTLET_", "$]",
			contentsMap);

		return sb;
	}

	protected LayoutTemplate getLayoutTemplate(String velocityTemplateId) {
		String separator = LayoutTemplateConstants.CUSTOM_SEPARATOR;
		boolean standard = false;

		if (velocityTemplateId.contains(
				LayoutTemplateConstants.STANDARD_SEPARATOR)) {

			separator = LayoutTemplateConstants.STANDARD_SEPARATOR;
			standard = true;
		}

		String layoutTemplateId = null;

		String themeId = null;

		int pos = velocityTemplateId.indexOf(separator);

		if (pos != -1) {
			layoutTemplateId = velocityTemplateId.substring(
				pos + separator.length());

			themeId = velocityTemplateId.substring(0, pos);
		}

		pos = layoutTemplateId.indexOf(
			LayoutTemplateConstants.INSTANCE_SEPARATOR);

		if (pos != -1) {
			layoutTemplateId = layoutTemplateId.substring(
				pos + LayoutTemplateConstants.INSTANCE_SEPARATOR.length() + 1);

			pos = layoutTemplateId.indexOf(StringPool.UNDERLINE);

			layoutTemplateId = layoutTemplateId.substring(pos + 1);
		}

		return LayoutTemplateLocalServiceUtil.getLayoutTemplate(
			layoutTemplateId, standard, themeId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RuntimePageImpl.class);

}