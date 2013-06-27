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

package com.liferay.portlet;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.LiferayPortletContext;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletFilterUtil;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.servlet.PluginContextListener;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.impl.PortletFilterImpl;
import com.liferay.portal.tools.deploy.PortletDeployer;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.EventFilter;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.PortletFilter;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Raymond Augé
 */
public class InvokerPortletImpl implements InvokerPortlet {

	public static void clearResponse(
		HttpSession session, long plid, String portletId, String languageId) {

		String sesResponseId = encodeResponseKey(plid, portletId, languageId);

		getResponses(session).remove(sesResponseId);
	}

	public static void clearResponses(HttpSession session) {
		getResponses(session).clear();
	}

	public static void clearResponses(PortletSession session) {
		getResponses(session).clear();
	}

	public static String encodeResponseKey(
		long plid, String portletId, String languageId) {

		StringBundler sb = new StringBundler(5);

		sb.append(StringUtil.toHexString(plid));
		sb.append(StringPool.UNDERLINE);
		sb.append(portletId);
		sb.append(StringPool.UNDERLINE);
		sb.append(languageId);

		return sb.toString();
	}

	public static Map<String, InvokerPortletResponse> getResponses(
		HttpSession session) {

		Map<String, InvokerPortletResponse> responses =
			(Map<String, InvokerPortletResponse>)session.getAttribute(
				WebKeys.CACHE_PORTLET_RESPONSES);

		if (responses == null) {
			responses = new ConcurrentHashMap<String, InvokerPortletResponse>();

			session.setAttribute(WebKeys.CACHE_PORTLET_RESPONSES, responses);
		}

		return responses;
	}

	public static Map<String, InvokerPortletResponse> getResponses(
		PortletSession portletSession) {

		return getResponses(
			((PortletSessionImpl)portletSession).getHttpSession());
	}

	public InvokerPortletImpl(
			com.liferay.portal.model.Portlet portletModel, Portlet portlet,
			PortletConfig portletConfig, PortletContext portletContext,
			boolean checkAuthToken, boolean facesPortlet, boolean strutsPortlet,
			boolean strutsBridgePortlet)
		throws PortletException {

		_initialize(
			portletModel, portlet, portletConfig, portletContext,
			checkAuthToken, facesPortlet, strutsPortlet, strutsBridgePortlet);
	}

	public InvokerPortletImpl(
			com.liferay.portal.model.Portlet portletModel, Portlet portlet,
			PortletContext portletContext)
		throws PortletException {

		Map<String, String> initParams = portletModel.getInitParams();

		boolean checkAuthToken = GetterUtil.getBoolean(
			initParams.get("check-auth-token"), true);

		boolean facesPortlet = false;

		if (ClassUtil.isSubclass(
				portlet.getClass(), PortletDeployer.JSF_STANDARD)) {

			facesPortlet = true;
		}

		boolean strutsPortlet = ClassUtil.isSubclass(
			portlet.getClass(), StrutsPortlet.class);

		boolean strutsBridgePortlet = ClassUtil.isSubclass(
			portlet.getClass(),
			"org.apache.portals.bridges.struts.StrutsPortlet");

		_initialize(
			portletModel, portlet, null, portletContext, checkAuthToken,
			facesPortlet, strutsPortlet, strutsBridgePortlet);
	}

	@Override
	public void destroy() {
		if (PortletConstants.hasInstanceId(_portletModel.getPortletId())) {
			if (_log.isWarnEnabled()) {
				_log.warn("Destroying an instanced portlet is not allowed");
			}

			return;
		}

		ClassLoader contextClassLoader =
			ClassLoaderUtil.getContextClassLoader();

		ClassLoader portletClassLoader = getPortletClassLoader();

		try {
			if (portletClassLoader != null) {
				ClassLoaderUtil.setContextClassLoader(portletClassLoader);
			}

			removePortletFilters();

			_portlet.destroy();
		}
		finally {
			if (portletClassLoader != null) {
				ClassLoaderUtil.setContextClassLoader(contextClassLoader);
			}
		}
	}

	@Override
	public Integer getExpCache() {
		return _expCache;
	}

	@Override
	public Portlet getPortlet() {
		return _portlet;
	}

	@Override
	public ClassLoader getPortletClassLoader() {
		ClassLoader classLoader =
			(ClassLoader)_liferayPortletContext.getAttribute(
				PluginContextListener.PLUGIN_CLASS_LOADER);

		if (classLoader == null) {
			classLoader = ClassLoaderUtil.getPortalClassLoader();
		}

		return classLoader;
	}

	@Override
	public PortletConfig getPortletConfig() {
		return _liferayPortletConfig;
	}

	@Override
	public PortletContext getPortletContext() {
		return _liferayPortletContext;
	}

	@Override
	public Portlet getPortletInstance() {
		return _portlet;
	}

	@Override
	public void init(PortletConfig portletConfig) throws PortletException {
		_liferayPortletConfig = (LiferayPortletConfig)portletConfig;

		ClassLoader contextClassLoader =
			ClassLoaderUtil.getContextClassLoader();

		ClassLoader portletClassLoader = getPortletClassLoader();

		try {
			if (portletClassLoader != null) {
				ClassLoaderUtil.setContextClassLoader(portletClassLoader);
			}

			_portlet.init(portletConfig);
		}
		finally {
			if (portletClassLoader != null) {
				ClassLoaderUtil.setContextClassLoader(contextClassLoader);
			}
		}
	}

	@Override
	public boolean isCheckAuthToken() {
		return _checkAuthToken;
	}

	@Override
	public boolean isFacesPortlet() {
		return _facesPortlet;
	}

	@Override
	public boolean isStrutsBridgePortlet() {
		return _strutsBridgePortlet;
	}

	@Override
	public boolean isStrutsPortlet() {
		return _strutsPortlet;
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		try {
			invokeAction(actionRequest, actionResponse);
		}
		catch (PortletException pe) {
			actionRequest.setAttribute(
				_portletId + PortletException.class.getName(), pe);
		}

		if (_log.isDebugEnabled()) {
			if (stopWatch != null) {
				_log.debug(
					"processAction for " + _portletId + " takes " +
						stopWatch.getTime() + " ms");
			}
			else {
				_log.debug("processAction for " + _portletId + " is finished");
			}
		}
	}

	@Override
	public void processEvent(
			EventRequest eventRequest, EventResponse eventResponse)
		throws IOException, PortletException {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		invokeEvent(eventRequest, eventResponse);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"processEvent for " + _portletId + " takes " +
					stopWatch.getTime() + " ms");
		}
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletException portletException =
			(PortletException)renderRequest.getAttribute(
				_portletId + PortletException.class.getName());

		if (portletException != null) {
			throw portletException;
		}

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		String remoteUser = renderRequest.getRemoteUser();

		if ((remoteUser == null) || (_expCache == null) ||
			(_expCache.intValue() == 0)) {

			invokeRender(renderRequest, renderResponse);
		}
		else {
			RenderResponseImpl renderResponseImpl =
				(RenderResponseImpl)renderResponse;

			BufferCacheServletResponse bufferCacheServletResponse =
				(BufferCacheServletResponse)
					renderResponseImpl.getHttpServletResponse();

			PortletSession portletSession = renderRequest.getPortletSession();

			long now = System.currentTimeMillis();

			Layout layout = (Layout)renderRequest.getAttribute(WebKeys.LAYOUT);

			Map<String, InvokerPortletResponse> sessionResponses = getResponses(
				portletSession);

			String sessionResponseId = encodeResponseKey(
				layout.getPlid(), _portletId,
				LanguageUtil.getLanguageId(renderRequest));

			InvokerPortletResponse response = sessionResponses.get(
				sessionResponseId);

			if (response == null) {
				String title = invokeRender(renderRequest, renderResponse);

				response = new InvokerPortletResponse(
					title, bufferCacheServletResponse.getString(),
					now + Time.SECOND * _expCache.intValue());

				sessionResponses.put(sessionResponseId, response);
			}
			else if ((response.getTime() < now) && (_expCache.intValue() > 0)) {
				String title = invokeRender(renderRequest, renderResponse);

				response.setTitle(title);
				response.setContent(bufferCacheServletResponse.getString());
				response.setTime(now + Time.SECOND * _expCache.intValue());
			}
			else {
				renderResponseImpl.setTitle(response.getTitle());

				PrintWriter printWriter =
					bufferCacheServletResponse.getWriter();

				printWriter.print(response.getContent());
			}
		}

		Map<String, String[]> properties =
			((RenderResponseImpl)renderResponse).getProperties();

		if (properties.containsKey("clear-request-parameters")) {
			Map<String, String[]> renderParameters =
				((RenderRequestImpl)renderRequest).getRenderParameters();

			renderParameters.clear();
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"render for " + _portletId + " takes " + stopWatch.getTime() +
					" ms");
		}
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		try {
			invokeResource(resourceRequest, resourceResponse);
		}
		catch (PortletException pe) {
			resourceRequest.setAttribute(
				_portletId + PortletException.class.getName(), pe);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"serveResource for " + _portletId + " takes " +
					stopWatch.getTime() + " ms");
		}
	}

	@Override
	public void setPortletFilters() throws PortletException {
		PortletApp portletApp = _portletModel.getPortletApp();

		PortletContextBag portletContextBag = PortletContextBagPool.get(
			portletApp.getServletContextName());

		if (portletApp.isWARFile() && (portletContextBag == null)) {
			return;
		}

		removePortletFilters();

		Map<String, com.liferay.portal.model.PortletFilter> portletFilters =
			_portletModel.getPortletFilters();

		for (Map.Entry<String, com.liferay.portal.model.PortletFilter> entry :
				portletFilters.entrySet()) {

			com.liferay.portal.model.PortletFilter portletFilterModel =
				entry.getValue();

			PortletFilter portletFilter = PortletFilterFactory.create(
				portletFilterModel, _liferayPortletContext);

			Set<String> lifecycles = portletFilterModel.getLifecycles();

			if (lifecycles.contains(PortletRequest.ACTION_PHASE)) {
				_actionFilters.add((ActionFilter)portletFilter);
			}

			if (lifecycles.contains(PortletRequest.EVENT_PHASE)) {
				_eventFilters.add((EventFilter)portletFilter);
			}

			if (lifecycles.contains(PortletRequest.RENDER_PHASE)) {
				_renderFilters.add((RenderFilter)portletFilter);
			}

			if (lifecycles.contains(PortletRequest.RESOURCE_PHASE)) {
				_resourceFilters.add((ResourceFilter)portletFilter);
			}
		}

		ClassLoader classLoader = ClassLoaderUtil.getContextClassLoader();

		try {
			ClassLoaderUtil.setContextClassLoader(
				ClassLoaderUtil.getPortalClassLoader());

			for (String portletFilterClassName :
					PropsValues.PORTLET_FILTERS_SYSTEM) {

				com.liferay.portal.model.PortletFilter portletFilterModel =
					new PortletFilterImpl(
						portletFilterClassName, portletFilterClassName,
						Collections.<String>emptySet(),
						Collections.<String, String>emptyMap(), portletApp);

				PortletFilter portletFilter = PortletFilterFactory.create(
					portletFilterModel, _liferayPortletContext);

				_systemPortletFilters.add(portletFilter);

				if (portletFilter instanceof ActionFilter) {
					_actionFilters.add((ActionFilter)portletFilter);
				}

				if (portletFilter instanceof EventFilter) {
					_eventFilters.add((EventFilter)portletFilter);
				}

				if (portletFilter instanceof RenderFilter) {
					_renderFilters.add((RenderFilter)portletFilter);
				}

				if (portletFilter instanceof ResourceFilter) {
					_resourceFilters.add((ResourceFilter)portletFilter);
				}
			}
		}
		finally {
			ClassLoaderUtil.setContextClassLoader(classLoader);
		}
	}

	protected void invoke(
			LiferayPortletRequest portletRequest,
			LiferayPortletResponse portletResponse, String lifecycle,
			List<? extends PortletFilter> filters)
		throws IOException, PortletException {

		FilterChain filterChain = new FilterChainImpl(_portlet, filters);

		if (_liferayPortletConfig.isWARFile()) {
			String invokerPortletName = _liferayPortletConfig.getInitParameter(
				INIT_INVOKER_PORTLET_NAME);

			if (invokerPortletName == null) {
				invokerPortletName = _liferayPortletConfig.getPortletName();
			}

			String path = StringPool.SLASH + invokerPortletName + "/invoke";

			ServletContext servletContext =
				_liferayPortletContext.getServletContext();

			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(path);

			HttpServletRequest request = portletRequest.getHttpServletRequest();
			HttpServletResponse response =
				portletResponse.getHttpServletResponse();

			request.setAttribute(JavaConstants.JAVAX_PORTLET_PORTLET, _portlet);
			request.setAttribute(PortletRequest.LIFECYCLE_PHASE, lifecycle);
			request.setAttribute(
				PortletServlet.PORTLET_SERVLET_FILTER_CHAIN, filterChain);

			try {

				// Resource phase must be a forward because includes do not
				// allow you to specify the content type or headers

				if (lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
					requestDispatcher.forward(request, response);
				}
				else {
					requestDispatcher.include(request, response);
				}
			}
			catch (ServletException se) {
				Throwable cause = se.getRootCause();

				if (cause instanceof PortletException) {
					throw (PortletException)cause;
				}

				throw new PortletException(cause);
			}
		}
		else {
			PortletFilterUtil.doFilter(
				portletRequest, portletResponse, lifecycle, filterChain);
		}

		portletResponse.transferMarkupHeadElements();

		Map<String, String[]> properties = portletResponse.getProperties();

		if ((properties != null) && (properties.size() > 0)) {
			if (_expCache != null) {
				String[] expCache = properties.get(
					RenderResponse.EXPIRATION_CACHE);

				if ((expCache != null) && (expCache.length > 0) &&
					(expCache[0] != null)) {

					_expCache = new Integer(GetterUtil.getInteger(expCache[0]));
				}
			}
		}
	}

	protected void invokeAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		LiferayPortletRequest portletRequest =
			(LiferayPortletRequest)actionRequest;
		LiferayPortletResponse portletResponse =
			(LiferayPortletResponse)actionResponse;

		invoke(
			portletRequest, portletResponse, PortletRequest.ACTION_PHASE,
			_actionFilters);
	}

	protected void invokeEvent(
			EventRequest eventRequest, EventResponse eventResponse)
		throws IOException, PortletException {

		LiferayPortletRequest portletRequest =
			(LiferayPortletRequest)eventRequest;
		LiferayPortletResponse portletResponse =
			(LiferayPortletResponse)eventResponse;

		invoke(
			portletRequest, portletResponse, PortletRequest.EVENT_PHASE,
			_eventFilters);
	}

	protected String invokeRender(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		LiferayPortletRequest portletRequest =
			(LiferayPortletRequest)renderRequest;
		LiferayPortletResponse portletResponse =
			(LiferayPortletResponse)renderResponse;

		invoke(
			portletRequest, portletResponse, PortletRequest.RENDER_PHASE,
			_renderFilters);

		RenderResponseImpl renderResponseImpl =
			(RenderResponseImpl)renderResponse;

		return renderResponseImpl.getTitle();
	}

	protected void invokeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		LiferayPortletRequest portletRequest =
			(LiferayPortletRequest)resourceRequest;
		LiferayPortletResponse portletResponse =
			(LiferayPortletResponse)resourceResponse;

		invoke(
			portletRequest, portletResponse, PortletRequest.RESOURCE_PHASE,
			_resourceFilters);
	}

	protected void removePortletFilters() {
		_actionFilters.clear();
		_eventFilters.clear();
		_renderFilters.clear();
		_resourceFilters.clear();

		for (PortletFilter portletFilter : _systemPortletFilters) {
			portletFilter.destroy();
		}

		_systemPortletFilters.clear();
	}

	private void _initialize(
			com.liferay.portal.model.Portlet portletModel, Portlet portlet,
			PortletConfig portletConfig, PortletContext portletContext,
			boolean checkAuthToken, boolean facesPortlet, boolean strutsPortlet,
			boolean strutsBridgePortlet)
		throws PortletException {

		_portletModel = portletModel;
		_portlet = portlet;
		_liferayPortletConfig = (LiferayPortletConfig)portletConfig;
		_portletId = _portletModel.getPortletId();
		_liferayPortletContext = (LiferayPortletContext)portletContext;
		_checkAuthToken = checkAuthToken;
		_facesPortlet = facesPortlet;
		_strutsPortlet = strutsPortlet;
		_strutsBridgePortlet = strutsBridgePortlet;
		_expCache = portletModel.getExpCache();
		setPortletFilters();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Create instance cache wrapper for " +
					_liferayPortletContext.getPortlet().getPortletId());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(InvokerPortletImpl.class);

	private List<ActionFilter> _actionFilters = new ArrayList<ActionFilter>();
	private boolean _checkAuthToken;
	private List<EventFilter> _eventFilters = new ArrayList<EventFilter>();
	private Integer _expCache;
	private boolean _facesPortlet;
	private LiferayPortletConfig _liferayPortletConfig;
	private LiferayPortletContext _liferayPortletContext;
	private Portlet _portlet;
	private String _portletId;
	private com.liferay.portal.model.Portlet _portletModel;
	private List<RenderFilter> _renderFilters = new ArrayList<RenderFilter>();
	private List<ResourceFilter> _resourceFilters =
		new ArrayList<ResourceFilter>();
	private boolean _strutsBridgePortlet;
	private boolean _strutsPortlet;
	private List<PortletFilter> _systemPortletFilters =
		new ArrayList<PortletFilter>();

}