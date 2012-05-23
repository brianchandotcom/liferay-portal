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

import com.liferay.portal.kernel.executor.PortalExecutorManagerUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateContextType;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.layoutconfiguration.util.velocity.CustomizationSettingsProcessor;
import com.liferay.portlet.layoutconfiguration.util.velocity.TemplateProcessor;
import com.liferay.portlet.layoutconfiguration.util.xml.RuntimeLogic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Shuyang Zhou
 */
public class RuntimePageImpl implements RuntimePage {

	public void processCustomizationSettings(
			PageContext pageContext, String velocityTemplateId,
			String velocityTemplateContent)
		throws Exception {

		if (Validator.isNull(velocityTemplateContent)) {
			return;
		}

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();
		HttpServletResponse response =
			(HttpServletResponse)pageContext.getResponse();

		CustomizationSettingsProcessor processor =
			new CustomizationSettingsProcessor(pageContext);

		Template template = TemplateManagerUtil.getTemplate(
			TemplateManager.VELOCITY, velocityTemplateId,
			velocityTemplateContent, TemplateContextType.STANDARD);

		template.put("processor", processor);

		// Velocity variables

		template.prepare(request);

		// liferay:include tag library

		MethodHandler methodHandler = new MethodHandler(
			_initMethodKey, pageContext.getServletContext(), request, response,
			pageContext);

		Object velocityTaglib = methodHandler.invoke(true);

		template.put("taglibLiferay", velocityTaglib);
		template.put("theme", velocityTaglib);

		try {
			template.processTemplate(pageContext.getOut());
		}
		catch (Exception e) {
			_log.error(e, e);

			throw e;
		}
	}

	public void processTemplate(
			PageContext pageContext, String velocityTemplateId,
			String velocityTemplateContent)
		throws Exception {

		processTemplate(
			pageContext, null, velocityTemplateId, velocityTemplateContent);
	}

	public void processTemplate(
			PageContext pageContext, String portletId,
			String velocityTemplateId, String velocityTemplateContent)
		throws Exception {

		if (Validator.isNull(velocityTemplateContent)) {
			return;
		}

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();
		HttpServletResponse response =
			(HttpServletResponse)pageContext.getResponse();

		TemplateProcessor processor = new TemplateProcessor(
			request, response, portletId);

		Template template = TemplateManagerUtil.getTemplate(
			TemplateManager.VELOCITY, velocityTemplateId,
			velocityTemplateContent, TemplateContextType.STANDARD);

		template.put("processor", processor);

		// Velocity variables

		template.prepare(request);

		// liferay:include tag library

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		MethodHandler methodHandler = new MethodHandler(
			_initMethodKey, pageContext.getServletContext(), request,
			new PipingServletResponse(response, unsyncStringWriter),
			pageContext);

		Object velocityTaglib = methodHandler.invoke(true);

		template.put("taglibLiferay", velocityTaglib);
		template.put("theme", velocityTaglib);

		try {
			template.processTemplate(unsyncStringWriter);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw e;
		}

		Map<Integer, List<PortletRenderer>> portletRenderersMap =
			processor.getPortletRenderersMap();

		Map<String, StringBundler> contentsMap =
			new HashMap<String, StringBundler>();

		Lock mergeLock = null;

		long timeStamp = 0;

		boolean parallelRenderEnable = GetterUtil.getBoolean(
			request.getAttribute(WebKeys.PORTLET_PARALLEL_RENDER));

		for (Map.Entry<Integer, List<PortletRenderer>> entry :
			portletRenderersMap.entrySet()) {

			if (_log.isDebugEnabled()) {
				_log.debug("Processing render weight : " + entry.getKey());
			}

			List<PortletRenderer> portletRenderers = entry.getValue();

			if (parallelRenderEnable && (portletRenderers.size() > 1)) {

				if (_log.isDebugEnabled()) {
					_log.debug("Start parallel rendering.");

					timeStamp = System.currentTimeMillis();
				}

				if (mergeLock == null) {
					mergeLock = new ReentrantLock();
				}

				request.setAttribute(
					WebKeys.PARALLEL_RENDERING_MERGE_LOCK, mergeLock);

				ExecutorService executorService =
					PortalExecutorManagerUtil.getPortalExecutor(
						RuntimePageImpl.class.getName());

				Map<Future<StringBundler>, PortletRenderer> futureResultMap =
					new HashMap<Future<StringBundler>, PortletRenderer>(
						portletRenderers.size());

				for (PortletRenderer portletRenderer : portletRenderers) {

					if (_log.isDebugEnabled()) {
						_log.debug(
							"Submit parallel rendering request for portlet : " +
								portletRenderer.getPortlet().getPortletId());
					}

					Future<StringBundler> futureResult = executorService.submit(
						portletRenderer.getCallable(request, response));

					futureResultMap.put(futureResult, portletRenderer);
				}

				long waitTime = _waitTime;

				for (Map.Entry<Future<StringBundler>, PortletRenderer>
					futureResultEntry : futureResultMap.entrySet()) {

					Future<StringBundler> futureResult =
						futureResultEntry.getKey();
					PortletRenderer portletRenderer =
						futureResultEntry.getValue();

					Portlet portlet = portletRenderer.getPortlet();

					if ((waitTime > 0) || futureResult.isDone()) {
						try {
							long startTime = System.currentTimeMillis();

							StringBundler result = futureResult.get(
								waitTime, TimeUnit.MILLISECONDS);

							long duration =
								System.currentTimeMillis() - startTime;

							waitTime -= duration;

							contentsMap.put(portlet.getPortletId(), result);

							portletRenderer.finishParallelRender();

							if (_log.isDebugEnabled()) {
								_log.debug(
									"Successfully parallel rendered portlet : "
										+ portlet.getPortletId());
							}

							continue;
						}
						catch (ExecutionException ee) {
							throw ee;
						}
						catch (InterruptedException ie) {
							// On interruption, stop waiting, force all pending
							// portlets fall back to ajax loading or error
							// message.

							waitTime = -1;
						}
						catch (TimeoutException te) {
							// On timeout, stop waiting, force all pending
							// portlets fall back to ajax loading or error
							// message.

							waitTime = -1;
						}
					}

					// Cancel by interrupting rendering thread.
					futureResult.cancel(true);

					StringBundler result = null;

					if (processor.isAjaxableRenderEnable() &&
						portlet.isAjaxable()) {

						if (_log.isDebugEnabled()) {
							_log.debug(
								"Fall back to ajax rendering portlet : " +
									portlet.getPortletId());
						}

						result = portletRenderer.renderAjax(request, response);
					}
					else {
						if (_log.isDebugEnabled()) {
							if (processor.isAjaxableRenderEnable()) {
								_log.debug(
									"Fall back to error page, as portlet : " +
										portlet.getPortletId() +
											" is not ajaxable");
							}
							else {
								_log.debug(
									"Fall back to error page for portlet : " +
										portlet.getPortletId() +
											", as ajaxable render is disabled");
							}
						}

						result = portletRenderer.renderError(request, response);
					}

					contentsMap.put(portlet.getPortletId(), result);
				}

				request.removeAttribute(WebKeys.PARALLEL_RENDERING_MERGE_LOCK);

				if (_log.isDebugEnabled()) {
					long duration = System.currentTimeMillis() - timeStamp;

					_log.debug(
						"End parallel rendering. Took " + duration + " ms");
				}
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("Start serial rendering.");

					timeStamp = System.currentTimeMillis();
				}

				for (PortletRenderer portletRenderer : portletRenderers) {
					StringBundler result = portletRenderer.render(
						request, response);

					Portlet portlet = portletRenderer.getPortlet();

					contentsMap.put(portlet.getPortletId(), result);

					if (_log.isDebugEnabled()) {
						_log.debug(
							"Serial rendered portlet : " +
								portlet.getPortletId());
					}
				}

				if (_log.isDebugEnabled()) {
					long duration = System.currentTimeMillis() - timeStamp;

					_log.debug(
						"End serial rendering. Took " + duration + " ms");
				}
			}
		}

		if (parallelRenderEnable && (_waitTime == Integer.MAX_VALUE)) {
			_waitTime = PropsValues.LAYOUT_PARALLEL_RENDER_TIMEOUT;
		}

		String output = unsyncStringWriter.toString();

		StringBundler sb = StringUtil.replaceWithStringBundler(
			output, "[$TEMPLATE_PORTLET_", "$]", contentsMap);

		sb.writeTo(pageContext.getOut());
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

	private static Log _log = LogFactoryUtil.getLog(RuntimePageImpl.class);

	private static MethodKey _initMethodKey = new MethodKey(
		"com.liferay.taglib.util.VelocityTaglib", "init", ServletContext.class,
		HttpServletRequest.class, HttpServletResponse.class, PageContext.class);

	// Set init wait time to Integer.MAX_VALUE to protect first hit jsp
	// compilation and class loading delay, lost update is acceptable, no need
	// to ensure thread safety.
	private int _waitTime = Integer.MAX_VALUE;

}