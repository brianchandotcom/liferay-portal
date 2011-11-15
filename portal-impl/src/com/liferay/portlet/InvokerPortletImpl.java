/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletFilterUtil;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.tools.deploy.PortletDeployer;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
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

	public InvokerPortlet create(
			com.liferay.portal.model.Portlet portletModel, Portlet portlet,
			PortletContext portletContext)
		throws PortletException {

		try {
			InvokerPortlet invokerPortlet = (InvokerPortlet)clone();

			invokerPortlet.prepare(portletModel, portlet, portletContext);

			return invokerPortlet;
		}
		catch (PortletException pe) {
			throw pe;
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	public InvokerPortlet create(
			com.liferay.portal.model.Portlet portletModel, Portlet portlet,
			PortletConfig portletConfig, PortletContext portletContext,
			boolean checkAuthToken, boolean facesPortlet, boolean strutsPortlet,
			boolean strutsBridgePortlet)
		throws PortletException {

		try {
			InvokerPortlet invokerPortlet = (InvokerPortlet)clone();

			invokerPortlet.prepare(
				portletModel, portlet, portletConfig, portletContext,
				checkAuthToken, facesPortlet, strutsPortlet,
				strutsBridgePortlet);

			return invokerPortlet;
		}
		catch (PortletException pe) {
			throw pe;
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	public void destroy() {
		if (_destroyable) {
			Thread currentThread = Thread.currentThread();

			ClassLoader contextClassLoader =
				currentThread.getContextClassLoader();

			ClassLoader portletClassLoader = getPortletClassLoader();

			try {
				if (portletClassLoader != null) {
					currentThread.setContextClassLoader(portletClassLoader);
				}

				removePortletFilters();

				_portlet.destroy();
			}
			finally {
				if (portletClassLoader != null) {
					currentThread.setContextClassLoader(contextClassLoader);
				}
			}
		}

		_destroyable = false;
	}

	public Portlet getPortlet() {
		return _portlet;
	}

	public ClassLoader getPortletClassLoader() {
		return (ClassLoader)_portletContextImpl.getAttribute(
			PortletServlet.PORTLET_CLASS_LOADER);
	}

	public PortletConfigImpl getPortletConfig() {
		return _portletConfigImpl;
	}

	public PortletContextImpl getPortletContext() {
		return _portletContextImpl;
	}

	public Portlet getPortletInstance() {
		return _portlet;
	}

	public Integer getExpCache() {
		return _expCache;
	}

	public void init(PortletConfig portletConfig) throws PortletException {
		_portletConfigImpl = (PortletConfigImpl)portletConfig;

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		ClassLoader portletClassLoader = getPortletClassLoader();

		try {
			if (portletClassLoader != null) {
				currentThread.setContextClassLoader(portletClassLoader);
			}

			_portlet.init(portletConfig);
		}
		finally {
			if (portletClassLoader != null) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}

		_destroyable = true;
	}

	public boolean isCheckAuthToken() {
		return _checkAuthToken;
	}

	public boolean isDestroyable() {
		return _destroyable;
	}

	public boolean isFacesPortlet() {
		return _facesPortlet;
	}

	public boolean isStrutsBridgePortlet() {
		return _strutsBridgePortlet;
	}

	public boolean isStrutsPortlet() {
		return _strutsPortlet;
	}

	public void prepare(
			com.liferay.portal.model.Portlet portletModel, Portlet portlet,
			PortletContext portletContext)
		throws PortletException {

		_portletModel = portletModel;
		_portletId = _portletModel.getPortletId();
		_portlet = portlet;
		_portletContextImpl = (PortletContextImpl)portletContext;

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Create root cache wrapper for " +
					_portletContextImpl.getPortlet().getPortletId());
		}

		Map<String, String> initParams = portletModel.getInitParams();

		_checkAuthToken = GetterUtil.getBoolean(
			initParams.get("check-auth-token"), true);

		if (ClassUtil.isSubclass(
				_portlet.getClass(), PortletDeployer.JSF_MYFACES) ||
			ClassUtil.isSubclass(
				_portlet.getClass(), PortletDeployer.JSF_STANDARD) ||
			ClassUtil.isSubclass(
				_portlet.getClass(), PortletDeployer.JSF_SUN)) {

			_facesPortlet = true;
		}

		_strutsPortlet = ClassUtil.isSubclass(
			portlet.getClass(), StrutsPortlet.class);
		_strutsBridgePortlet = ClassUtil.isSubclass(
			portlet.getClass(),
			"org.apache.portals.bridges.struts.StrutsPortlet");
		_expCache = portletModel.getExpCache();
		setPortletFilters();
	}

	public void prepare(
			com.liferay.portal.model.Portlet portletModel, Portlet portlet,
			PortletConfig portletConfig, PortletContext portletContext,
			boolean checkAuthToken, boolean facesPortlet, boolean strutsPortlet,
			boolean strutsBridgePortlet)
		throws PortletException {

 		// From prepare

		_portletModel = portletModel;
		_portlet = portlet;
		_portletId = _portletModel.getPortletId();
		_portletContextImpl = (PortletContextImpl)portletContext;
		_checkAuthToken = checkAuthToken;
		_facesPortlet = facesPortlet;
		_strutsPortlet = strutsPortlet;
		_strutsBridgePortlet = strutsBridgePortlet;
		_expCache = portletModel.getExpCache();
		setPortletFilters();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Create instance cache wrapper for " +
					_portletContextImpl.getPortlet().getPortletId());
		}

		// From init

		_portletConfigImpl = (PortletConfigImpl)portletConfig;
	}

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

			StringServletResponse stringResponse = (StringServletResponse)
				renderResponseImpl.getHttpServletResponse();

			PortletSession portletSession = renderRequest.getPortletSession();

			long now = System.currentTimeMillis();

			Layout layout = (Layout)renderRequest.getAttribute(WebKeys.LAYOUT);

			Map<String, InvokerPortletResponse> sessionResponses =
				getResponses(portletSession);

			String sessionResponseId = encodeResponseKey(
				layout.getPlid(), _portletId,
				LanguageUtil.getLanguageId(renderRequest));

			InvokerPortletResponse response = sessionResponses.get(
				sessionResponseId);

			if (response == null) {
				String title = invokeRender(renderRequest, renderResponse);

				response = new InvokerPortletResponse(
					title, stringResponse.getString(),
					now + Time.SECOND * _expCache.intValue());

				sessionResponses.put(sessionResponseId, response);
			}
			else if ((response.getTime() < now) &&
					 (_expCache.intValue() > 0)) {

				String title = invokeRender(renderRequest, renderResponse);

				response.setTitle(title);
				response.setContent(stringResponse.getString());
				response.setTime(now + Time.SECOND * _expCache.intValue());
			}
			else {
				renderResponseImpl.setTitle(response.getTitle());
				stringResponse.getWriter().print(response.getContent());
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
				portletFilterModel, _portletContextImpl);

			Set<String> lifecycles = portletFilterModel.getLifecycles();

			if (lifecycles.contains(PortletRequest.ACTION_PHASE)) {
				List<ActionFilter> actionFilters = _actionFiltersMap.get(
					_portletId);

				if (actionFilters == null) {
					actionFilters = new ArrayList<ActionFilter>();
				}

				actionFilters.add((ActionFilter)portletFilter);

				_actionFiltersMap.put(_portletId, actionFilters);
			}

			if (lifecycles.contains(PortletRequest.EVENT_PHASE)) {
				List<EventFilter> eventFilters = _eventFiltersMap.get(
					_portletId);

				if (eventFilters == null) {
					eventFilters = new ArrayList<EventFilter>();
				}

				eventFilters.add((EventFilter)portletFilter);

				_eventFiltersMap.put(_portletId, eventFilters);
			}

			if (lifecycles.contains(PortletRequest.RENDER_PHASE)) {
				List<RenderFilter> renderFilters = _renderFiltersMap.get(
					_portletId);

				if (renderFilters == null) {
					renderFilters = new ArrayList<RenderFilter>();
				}

				renderFilters.add((RenderFilter)portletFilter);

				_renderFiltersMap.put(_portletId, renderFilters);
			}

			if (lifecycles.contains(PortletRequest.RESOURCE_PHASE)) {
				List<ResourceFilter> resourceFilters = _resourceFiltersMap.get(
					_portletId);

				if (resourceFilters == null) {
					resourceFilters = new ArrayList<ResourceFilter>();
				}

				resourceFilters.add((ResourceFilter)portletFilter);

				_resourceFiltersMap.put(_portletId, resourceFilters);
			}
		}
	}

	protected void invoke(
			LiferayPortletRequest portletRequest,
			LiferayPortletResponse portletResponse, String lifecycle,
			List<? extends PortletFilter> filters)
		throws IOException, PortletException {

		FilterChain filterChain = new FilterChainImpl(_portlet, filters);

		if (_portletConfigImpl.isWARFile()) {
			String invokerPortletName = _portletConfigImpl.getInitParameter(
				INIT_INVOKER_PORTLET_NAME);

			if (invokerPortletName == null) {
				invokerPortletName = _portletConfigImpl.getPortletName();
			}

			String path = StringPool.SLASH + invokerPortletName + "/invoke";

			RequestDispatcher requestDispatcher =
				_portletContextImpl.getServletContext().getRequestDispatcher(
					path);

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

		String portletId = _getPortletId(portletResponse);

		List<ActionFilter> actionFilters = _actionFiltersMap.get(portletId);

		invoke(
			portletRequest, portletResponse, PortletRequest.ACTION_PHASE,
			actionFilters);
	}

	protected void invokeEvent(
			EventRequest eventRequest, EventResponse eventResponse)
		throws IOException, PortletException {

		LiferayPortletRequest portletRequest =
			(LiferayPortletRequest)eventRequest;
		LiferayPortletResponse portletResponse =
			(LiferayPortletResponse)eventResponse;

		String portletId = _getPortletId(portletResponse);

		List<EventFilter> eventFilters = _eventFiltersMap.get(portletId);

		invoke(
			portletRequest, portletResponse, PortletRequest.EVENT_PHASE,
			eventFilters);
	}

	protected String invokeRender(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		LiferayPortletRequest portletRequest =
			(LiferayPortletRequest)renderRequest;
		LiferayPortletResponse portletResponse =
			(LiferayPortletResponse)renderResponse;

		String portletId = _getPortletId(portletResponse);

		List<RenderFilter> renderFilters = _renderFiltersMap.get(portletId);

		invoke(
			portletRequest, portletResponse, PortletRequest.RENDER_PHASE,
			renderFilters);

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

		String portletId = _getPortletId(portletResponse);

		List<ResourceFilter> resourceFilters = _resourceFiltersMap.get(
			portletId);

		invoke(
			portletRequest, portletResponse, PortletRequest.RESOURCE_PHASE,
			resourceFilters);
	}

	protected void removePortletFilters() {
		_actionFiltersMap.remove(_portletId);
		_eventFiltersMap.remove(_portletId);
		_renderFiltersMap.remove(_portletId);
		_resourceFiltersMap.remove(_portletId);
	}

	private String _getPortletId(LiferayPortletResponse portletResponse) {
		PortletResponseImpl portletResponseImpl =
			(PortletResponseImpl)portletResponse;

		com.liferay.portal.model.Portlet portlet =
			portletResponseImpl.getPortlet();

		return portlet.getPortletId();
	}

	private static Log _log = LogFactoryUtil.getLog(InvokerPortletImpl.class);

	private com.liferay.portal.model.Portlet _portletModel;
	private String _portletId;
	private Portlet _portlet;
	private PortletConfigImpl _portletConfigImpl;
	private PortletContextImpl _portletContextImpl;
	private Integer _expCache;
	private boolean _checkAuthToken;
	private boolean _destroyable;
	private boolean _facesPortlet;
	private boolean _strutsPortlet;
	private boolean _strutsBridgePortlet;
	private Map<String, List<ActionFilter>> _actionFiltersMap =
		new HashMap<String, List<ActionFilter>>();
	private Map<String, List<EventFilter>> _eventFiltersMap =
		new HashMap<String, List<EventFilter>>();
	private Map<String, List<RenderFilter>> _renderFiltersMap =
		new HashMap<String, List<RenderFilter>>();
	private Map<String, List<ResourceFilter>> _resourceFiltersMap =
		new HashMap<String, List<ResourceFilter>>();

}