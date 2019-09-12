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

import com.liferay.petra.concurrent.ThreadPoolHandler;
import com.liferay.petra.concurrent.ThreadPoolHandlerAdapter;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
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
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.layoutconfiguration.util.velocity.CustomizationSettingsProcessor;
import com.liferay.portal.layoutconfiguration.util.velocity.TemplateProcessor;
import com.liferay.portal.layoutconfiguration.util.xml.ActionURLLogic;
import com.liferay.portal.layoutconfiguration.util.xml.PortletLogic;
import com.liferay.portal.layoutconfiguration.util.xml.RenderURLLogic;
import com.liferay.portal.servlet.ThreadLocalFacadeServletRequestWrapperUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.internal.PortletBagUtil;
import com.liferay.portlet.internal.PortletTypeUtil;
import com.liferay.taglib.servlet.PipingServletResponse;
import com.liferay.taglib.util.DummyVelocityTaglib;
import com.liferay.taglib.util.VelocityTaglib;

import java.io.Closeable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

	public static ThreadPoolHandler getThreadPoolHandler() {
		return new ThreadPoolHandlerAdapter() {

			@Override
			public void afterExecute(Runnable runnable, Throwable throwable) {
				CentralizedThreadLocal.clearShortLivedThreadLocals();
			}

		};
	}

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
		catch (Exception e) {
			_log.error(e, e);

			throw e;
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
		catch (Exception e) {
			_log.error(e, e);

			throw e;
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

		boolean portletParallelRender = GetterUtil.getBoolean(
			httpServletRequest.getAttribute(WebKeys.PORTLET_PARALLEL_RENDER));

		Lock lock = null;

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

			if (portletParallelRender && (portletRenderers.size() > 1)) {
				if (_log.isDebugEnabled()) {
					_log.debug("Start parallel rendering");
				}

				if (lock == null) {
					lock = new ReentrantLock();
				}

				httpServletRequest.setAttribute(
					WebKeys.PARALLEL_RENDERING_MERGE_LOCK, lock);

				ObjectValuePair<HttpServletRequest, Closeable> objectValuePair =
					ThreadLocalFacadeServletRequestWrapperUtil.inject(
						httpServletRequest);

				try {
					parallelyRenderPortlets(
						objectValuePair.getKey(), httpServletResponse,
						processor, contentsMap, portletHeaderRequestMap,
						portletRenderers);
				}
				finally {
					Closeable closeable = objectValuePair.getValue();

					closeable.close();
				}

				httpServletRequest.removeAttribute(
					WebKeys.PARALLEL_RENDERING_MERGE_LOCK);

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Finished parallel rendering in " +
							stopWatch.getTime() + " ms");
				}
			}
			else {
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
		}

		if (portletParallelRender && (_waitTime == Integer.MAX_VALUE)) {
			_waitTime = PropsValues.LAYOUT_PARALLEL_RENDER_TIMEOUT;
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

	protected void parallelyRenderPortlets(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			TemplateProcessor processor, Map<String, StringBundler> contentsMap,
			Map<String, Map<String, Object>> portletHeaderRequestMap,
			List<PortletRenderer> portletRenderers)
		throws Exception {

		ExecutorService executorService =
			_portalExecutorManager.getPortalExecutor(
				RuntimePageImpl.class.getName());

		Map<Future<StringBundler>, PortletRenderer> futures = new HashMap<>();

		for (PortletRenderer portletRenderer : portletRenderers) {
			Portlet portlet = portletRenderer.getPortlet();

			String rendererPortletId = portlet.getPortletId();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Submit portlet " + rendererPortletId +
						" for parallel rendering");
			}

			Callable<StringBundler> renderCallable =
				portletRenderer.getCallable(
					httpServletRequest, httpServletResponse,
					portletHeaderRequestMap.get(rendererPortletId));

			Future<StringBundler> future = null;

			try {
				future = executorService.submit(renderCallable);
			}
			catch (RejectedExecutionException ree) {

				// This should only happen when user configures an AbortPolicy
				// (or some other customized RejectedExecutionHandler that
				// throws RejectedExecutionException) for this
				// ThreadPoolExecutor. AbortPolicy is not the recommended
				// setting, but to be more robust, we take care of this by
				// converting the rejection to a fallback action.

				future = new FutureTask<>(renderCallable);

				// Cancel immediately

				future.cancel(true);
			}

			futures.put(future, portletRenderer);
		}

		long waitTime = _waitTime;

		for (Map.Entry<Future<StringBundler>, PortletRenderer> entry :
				futures.entrySet()) {

			Future<StringBundler> future = entry.getKey();

			PortletRenderer portletRenderer = entry.getValue();

			Portlet portlet = portletRenderer.getPortlet();

			if (future.isCancelled()) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Reject portlet " + portlet.getPortletId() +
							" for parallel rendering");
				}
			}
			else if ((waitTime > 0) || future.isDone()) {
				try {
					long startTime = System.currentTimeMillis();

					StringBundler sb = future.get(
						waitTime, TimeUnit.MILLISECONDS);

					long duration = System.currentTimeMillis() - startTime;

					waitTime -= duration;

					contentsMap.put(portlet.getPortletId(), sb);

					if (_log.isDebugEnabled()) {
						_log.debug(
							StringBundler.concat(
								"Parallely rendered portlet ",
								portlet.getPortletId(), " in ",
								String.valueOf(duration), " ms"));
					}

					continue;
				}
				catch (ExecutionException ee) {
					throw ee;
				}
				catch (InterruptedException ie) {

					// On interruption, stop waiting, force all pending portlets
					// to fall back to ajax loading or an error message.

					waitTime = -1;
				}
				catch (TimeoutException te) {

					// On timeout, stop waiting, force all pending portlets to
					// fall back to ajax loading or an error message.

					waitTime = -1;
				}
				catch (CancellationException ce) {

					// This should only happen on a concurrent shutdown of the
					// thread pool. Simply stops the render process.

					if (_log.isDebugEnabled()) {
						_log.debug(
							"Asynchronized cancellation detected that should " +
								"only be caused by a concurrent shutdown of " +
									"the thread pool",
							ce);
					}

					return;
				}

				// Cancel by interrupting rendering thread

				future.cancel(true);
			}

			StringBundler sb = null;

			if (processor.isPortletAjaxRender() && portlet.isAjaxable()) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Fall back to ajax rendering of portlet " +
							portlet.getPortletId());
				}

				sb = portletRenderer.renderAjax(
					httpServletRequest, httpServletResponse);
			}
			else {
				if (_log.isDebugEnabled()) {
					if (processor.isPortletAjaxRender()) {
						_log.debug(
							"Fall back to an error message for portlet " +
								portlet.getPortletId() +
									" since it is not ajaxable");
					}
					else {
						_log.debug(
							"Fall back to an error message for portlet " +
								portlet.getPortletId() +
									" since ajax rendering is disabled");
					}
				}

				sb = portletRenderer.renderError(
					httpServletRequest, httpServletResponse);
			}

			contentsMap.put(portlet.getPortletId(), sb);
		}

		for (PortletRenderer portletRender : portletRenderers) {
			portletRender.finishParallelRender();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RuntimePageImpl.class);

	private static volatile PortalExecutorManager _portalExecutorManager =
		ServiceProxyFactory.newServiceTrackedInstance(
			PortalExecutorManager.class, RuntimePageImpl.class,
			"_portalExecutorManager", true);

	private int _waitTime = Integer.MAX_VALUE;

}