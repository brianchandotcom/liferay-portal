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

import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.layoutconfiguration.util.RuntimePage;
import com.liferay.portal.kernel.layoutconfiguration.util.xml.RuntimeLogic;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutTemplate;
import com.liferay.portal.kernel.model.LayoutTemplateConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.LayoutTemplateLocalServiceUtil;
import com.liferay.portal.kernel.servlet.PluginContextListener;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.layoutconfiguration.util.velocity.CustomizationSettingsProcessor;
import com.liferay.portal.layoutconfiguration.util.velocity.TemplateProcessor;
import com.liferay.portal.layoutconfiguration.util.xml.ActionURLLogic;
import com.liferay.portal.layoutconfiguration.util.xml.PortletLogic;
import com.liferay.portal.layoutconfiguration.util.xml.RenderURLLogic;
import com.liferay.portlet.internal.PortletBagUtil;
import com.liferay.portlet.internal.PortletTypeUtil;
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
public class RuntimePageImpl implements RuntimePage {

	@Override
	public StringBundler getProcessedTemplate(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String portletId,
			TemplateResource templateResource)
		throws Exception {

		return doDispatch(
			httpServletRequest, httpServletResponse, portletId,
			templateResource, TemplateConstants.LANG_TYPE_VM, true);
	}

	@Override
	public void processCustomizationSettings(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			TemplateResource templateResource)
		throws Exception {

		processCustomizationSettings(
			httpServletRequest, httpServletResponse, templateResource,
			TemplateConstants.LANG_TYPE_VM);
	}

	@Override
	public void processCustomizationSettings(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			TemplateResource templateResource, String langType)
		throws Exception {

		doDispatch(
			httpServletRequest, httpServletResponse, null, templateResource,
			langType, false);
	}

	@Override
	public void processTemplate(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String portletId,
			TemplateResource templateResource)
		throws Exception {

		StringBundler sb = doDispatch(
			httpServletRequest, httpServletResponse, portletId,
			templateResource, TemplateConstants.LANG_TYPE_VM, true);

		sb.writeTo(httpServletResponse.getWriter());
	}

	@Override
	public void processTemplate(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String portletId,
			TemplateResource templateResource, String langType)
		throws Exception {

		StringBundler sb = doDispatch(
			httpServletRequest, httpServletResponse, portletId,
			templateResource, langType, true);

		sb.writeTo(httpServletResponse.getWriter());
	}

	@Override
	public void processTemplate(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			TemplateResource templateResource)
		throws Exception {

		processTemplate(
			httpServletRequest, httpServletResponse, null, templateResource);
	}

	@Override
	public void processTemplate(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			TemplateResource templateResource, String langType)
		throws Exception {

		processTemplate(
			httpServletRequest, httpServletResponse, null, templateResource,
			langType);
	}

	@Override
	public String processXML(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String content)
		throws Exception {

		PortletResponse portletResponse =
			(PortletResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		if ((portletResponse != null) &&
			!(portletResponse instanceof RenderResponse)) {

			throw new IllegalArgumentException(
				"processXML can only be invoked in the render phase");
		}

		RuntimeLogic portletLogic = new PortletLogic(
			httpServletRequest, httpServletResponse);

		content = processXML(httpServletRequest, content, portletLogic);

		if (portletResponse == null) {
			return content;
		}

		RenderResponse renderResponse = (RenderResponse)portletResponse;

		RuntimeLogic actionURLLogic = new ActionURLLogic(renderResponse);
		RuntimeLogic renderURLLogic = new RenderURLLogic(renderResponse);

		content = processXML(httpServletRequest, content, actionURLLogic);
		content = processXML(httpServletRequest, content, renderURLLogic);

		return content;
	}

	@Override
	public String processXML(
			HttpServletRequest httpServletRequest, String content,
			RuntimeLogic runtimeLogic)
		throws Exception {

		if (Validator.isNull(content)) {
			return StringPool.BLANK;
		}

		int index = content.indexOf(runtimeLogic.getOpenTag());

		if (index == -1) {
			return content;
		}

		Portlet renderPortlet = (Portlet)httpServletRequest.getAttribute(
			WebKeys.RENDER_PORTLET);

		Boolean renderPortletResource =
			(Boolean)httpServletRequest.getAttribute(
				WebKeys.RENDER_PORTLET_RESOURCE);

		String outerPortletId = (String)httpServletRequest.getAttribute(
			WebKeys.OUTER_PORTLET_ID);

		if (outerPortletId == null) {
			httpServletRequest.setAttribute(
				WebKeys.OUTER_PORTLET_ID, renderPortlet.getPortletId());
		}

		try {
			httpServletRequest.setAttribute(
				WebKeys.RENDER_PORTLET_RESOURCE, Boolean.TRUE);

			StringBundler sb = new StringBundler();

			int x = 0;
			int y = index;

			while (y != -1) {
				sb.append(content.substring(x, y));

				String close1Tag = runtimeLogic.getClose1Tag();
				String close2Tag = runtimeLogic.getClose2Tag();

				int close1 = content.indexOf(close1Tag, y);
				int close2 = content.indexOf(close2Tag, y);

				if ((close2 == -1) || ((close1 != -1) && (close1 < close2))) {
					x = close1 + close1Tag.length();
				}
				else {
					x = close2 + close2Tag.length();
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
				httpServletRequest.removeAttribute(WebKeys.OUTER_PORTLET_ID);
			}

			httpServletRequest.setAttribute(
				WebKeys.RENDER_PORTLET, renderPortlet);

			if (renderPortletResource == null) {
				httpServletRequest.removeAttribute(
					WebKeys.RENDER_PORTLET_RESOURCE);
			}
			else {
				httpServletRequest.setAttribute(
					WebKeys.RENDER_PORTLET_RESOURCE, renderPortletResource);
			}
		}
	}

	protected StringBundler doDispatch(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String portletId,
			TemplateResource templateResource, String langType,
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
				pluginClassLoader =
					(ClassLoader)pluginServletContext.getAttribute(
						PluginContextListener.PLUGIN_CLASS_LOADER);
			}
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if ((pluginClassLoader != null) &&
				(pluginClassLoader != contextClassLoader)) {

				currentThread.setContextClassLoader(pluginClassLoader);
			}

			if (processTemplate) {
				return doProcessTemplate(
					httpServletRequest, httpServletResponse, portletId,
					templateResource, langType, false);
			}

			doProcessCustomizationSettings(
				httpServletRequest, httpServletResponse, templateResource,
				langType, false);

			return null;
		}
		finally {
			if ((pluginClassLoader != null) &&
				(pluginClassLoader != contextClassLoader)) {

				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	protected void doProcessCustomizationSettings(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			TemplateResource templateResource, String langType,
			boolean restricted)
		throws Exception {

		CustomizationSettingsProcessor processor =
			new CustomizationSettingsProcessor(
				httpServletRequest, httpServletResponse);

		Template template = TemplateManagerUtil.getTemplate(
			langType, templateResource, restricted);

		template.put("processor", processor);

		// Velocity variables

		template.prepare(httpServletRequest);

		// liferay:include tag library

		VelocityTaglib velocityTaglib = new DummyVelocityTaglib();

		template.put("taglibLiferay", velocityTaglib);
		template.put("theme", velocityTaglib);

		try {
			template.processTemplate(httpServletResponse.getWriter());
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw exception;
		}
	}

	protected StringBundler doProcessTemplate(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String portletId,
			TemplateResource templateResource, String langType,
			boolean restricted)
		throws Exception {

		TemplateProcessor processor = new TemplateProcessor(
			httpServletRequest, httpServletResponse, portletId);

		TemplateManager templateManager =
			TemplateManagerUtil.getTemplateManager(langType);

		Template template = templateManager.getTemplate(
			templateResource, restricted);

		template.put("processor", processor);

		// Velocity variables

		template.prepare(httpServletRequest);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		templateManager.addTaglibTheme(
			template, "taglibLiferay", httpServletRequest,
			new PipingServletResponse(httpServletResponse, unsyncStringWriter));

		try {
			template.processTemplate(unsyncStringWriter);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw exception;
		}

		Map<Integer, List<PortletRenderer>> portletRenderersMap =
			processor.getPortletRenderers();

		Map<String, Map<String, Object>> portletHeaderRequestMap =
			new HashMap<>();

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
				_log.debug("Start serial header phase");
			}

			for (PortletRenderer portletRenderer : portletRenderers) {
				Portlet portletModel = portletRenderer.getPortlet();

				if (!portletModel.isReady()) {
					continue;
				}

				javax.portlet.Portlet portlet =
					PortletBagUtil.getPortletInstance(
						httpServletRequest.getServletContext(), portletModel,
						portletModel.getRootPortletId());

				if (!PortletTypeUtil.isHeaderPortlet(portlet)) {
					continue;
				}

				Map<String, Object> headerRequestMap =
					portletRenderer.renderHeaders(
						httpServletRequest, httpServletResponse,
						portletModel.getHeaderRequestAttributePrefixes());

				String rendererPortletId = portletModel.getPortletId();

				portletHeaderRequestMap.put(
					rendererPortletId, headerRequestMap);

				if (_log.isDebugEnabled()) {
					StringBundler sb = new StringBundler(5);

					sb.append("Serially rendered headers for portlet ");
					sb.append(rendererPortletId);
					sb.append(" in ");
					sb.append(stopWatch.getTime());
					sb.append(" ms");

					_log.debug(sb.toString());
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Finished serial header phase in " + stopWatch.getTime() +
						" ms");
			}
		}

		Map<String, StringBundler> contentsMap = new HashMap<>();

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

				String rendererPortletId = portlet.getPortletId();

				contentsMap.put(
					rendererPortletId,
					portletRenderer.render(
						httpServletRequest, httpServletResponse,
						portletHeaderRequestMap.get(rendererPortletId)));

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Serially rendered portlet ", rendererPortletId,
							" in ", String.valueOf(stopWatch.getTime()),
							" ms"));
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Finished serial rendering in " + stopWatch.getTime() +
						" ms");
			}
		}

		return StringUtil.replaceWithStringBundler(
			unsyncStringWriter.toString(), "[$TEMPLATE_PORTLET_", "$]",
			contentsMap);
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