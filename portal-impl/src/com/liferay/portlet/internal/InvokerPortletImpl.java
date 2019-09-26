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

package com.liferay.portlet.internal;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.InvokerFilterContainer;
import com.liferay.portal.kernel.portlet.InvokerPortlet;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.LiferayPortletContext;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletFilterUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.servlet.PluginContextListener;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.tools.deploy.PortletDeployer;
import com.liferay.portlet.InvokerPortletResponse;
import com.liferay.portlet.InvokerPortletUtil;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.HeaderRequest;
import javax.portlet.HeaderResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.UnavailableException;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.EventFilter;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.HeaderFilter;
import javax.portlet.filter.PortletFilter;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Raymond Augé
 * @author Neil Griffin
 */
public class InvokerPortletImpl
	implements InvokerFilterContainer, InvokerPortlet {

	public InvokerPortletImpl(
		com.liferay.portal.kernel.model.Portlet portletModel, Portlet portlet,
		PortletConfig portletConfig, PortletContext portletContext,
		InvokerFilterContainer invokerFilterContainer, boolean checkAuthToken,
		boolean facesPortlet, boolean headerPortlet) {

		_initialize(
			portletModel, portlet, portletConfig, portletContext,
			invokerFilterContainer, checkAuthToken, facesPortlet,
			headerPortlet);
	}

	public InvokerPortletImpl(
		com.liferay.portal.kernel.model.Portlet portletModel, Portlet portlet,
		PortletContext portletContext,
		InvokerFilterContainer invokerFilterContainer) {

		Map<String, String> initParams = portletModel.getInitParams();

		boolean checkAuthToken = GetterUtil.getBoolean(
			initParams.get("check-auth-token"), true);

		boolean facesPortlet = false;

		Class<? extends Portlet> portletClass = portlet.getClass();

		if (ClassUtil.isSubclass(portletClass, PortletDeployer.JSF_STANDARD)) {
			facesPortlet = true;
		}
		else if (portlet instanceof InvokerPortlet) {
			InvokerPortlet invokerPortlet = (InvokerPortlet)portlet;

			facesPortlet = invokerPortlet.isFacesPortlet();
		}

		boolean headerPortlet = PortletTypeUtil.isHeaderPortlet(portlet);

		_initialize(
			portletModel, portlet, null, portletContext, invokerFilterContainer,
			checkAuthToken, facesPortlet, headerPortlet);
	}

	@Override
	public void destroy() {
		if (PortletIdCodec.hasInstanceId(_portletModel.getPortletId())) {
			if (_log.isWarnEnabled()) {
				_log.warn("Destroying an instanced portlet is not allowed");
			}

			return;
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (_portletClassLoader != null) {
				currentThread.setContextClassLoader(_portletClassLoader);
			}

			cleanUp();

			_portlet.destroy();
		}
		finally {
			if (_portletClassLoader != null) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	@Override
	public List<ActionFilter> getActionFilters() {
		return _invokerFilterContainer.getActionFilters();
	}

	@Override
	public List<EventFilter> getEventFilters() {
		return _invokerFilterContainer.getEventFilters();
	}

	@Override
	public Integer getExpCache() {
		return _expCache;
	}

	@Override
	public List<HeaderFilter> getHeaderFilters() {
		return _invokerFilterContainer.getHeaderFilters();
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
			classLoader = PortalClassLoaderUtil.getClassLoader();
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
	public List<RenderFilter> getRenderFilters() {
		return _invokerFilterContainer.getRenderFilters();
	}

	@Override
	public List<ResourceFilter> getResourceFilters() {
		return _invokerFilterContainer.getResourceFilters();
	}

	@Override
	public void init(PortletConfig portletConfig) throws PortletException {
		_liferayPortletConfig = (LiferayPortletConfig)portletConfig;

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		_portletClassLoader = getPortletClassLoader();

		try {
			if (_portletClassLoader != null) {
				currentThread.setContextClassLoader(_portletClassLoader);
			}

			_portlet.init(portletConfig);
		}
		catch (Throwable t) {
			cleanUp();

			throw t;
		}
		finally {
			if (_portletClassLoader != null) {
				currentThread.setContextClassLoader(contextClassLoader);
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
	public boolean isHeaderPortlet() {
		return _headerPortlet;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isStrutsBridgePortlet() {
		return false;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isStrutsPortlet() {
		return false;
	}

	@Override
	public void processAction(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		try {
			invokeAction(actionRequest, actionResponse);
		}
		catch (Exception e) {
			processException(e, actionRequest, actionResponse);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"processAction for ", _portletId, " takes ",
					stopWatch.getTime(), " ms"));
		}
	}

	@Override
	public void processEvent(
		EventRequest eventRequest, EventResponse eventResponse) {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		try {
			invokeEvent(eventRequest, eventResponse);
		}
		catch (Exception e) {
			processException(e, eventRequest, eventResponse);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"processEvent for ", _portletId, " takes ",
					stopWatch.getTime(), " ms"));
		}
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletException portletException =
			(PortletException)renderRequest.getAttribute(_errorKey);

		if (portletException != null) {
			throw portletException;
		}

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

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

			Map<String, InvokerPortletResponse> sessionResponses =
				InvokerPortletUtil.getResponses(portletSession);

			String sessionResponseId = InvokerPortletUtil.encodeResponseKey(
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

		RenderResponseImpl renderResponseImpl =
			(RenderResponseImpl)renderResponse;

		Map<String, String[]> properties = renderResponseImpl.getProperties();

		if (properties.containsKey("clear-request-parameters")) {
			RenderRequestImpl renderRequestImpl =
				(RenderRequestImpl)renderRequest;

			renderRequestImpl.clearRenderParameters();
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"render for ", _portletId, " takes ", stopWatch.getTime(),
					" ms"));
		}
	}

	@Override
	public void renderHeaders(
			HeaderRequest headerRequest, HeaderResponse headerResponse)
		throws IOException, PortletException {

		PortletException portletException =
			(PortletException)headerRequest.getAttribute(_errorKey);

		if (portletException != null) {
			throw portletException;
		}

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		String remoteUser = headerRequest.getRemoteUser();

		if ((remoteUser == null) || (_expCache == null) || (_expCache == 0)) {
			invokeHeader(headerRequest, headerResponse);
		}
		else {
			HeaderResponseImpl headerResponseImpl =
				(HeaderResponseImpl)headerResponse;

			BufferCacheServletResponse bufferCacheServletResponse =
				(BufferCacheServletResponse)
					headerResponseImpl.getHttpServletResponse();

			PortletSession portletSession = headerRequest.getPortletSession();

			long now = System.currentTimeMillis();

			Layout layout = (Layout)headerRequest.getAttribute(WebKeys.LAYOUT);

			Map<String, InvokerPortletResponse> sessionResponses =
				InvokerPortletUtil.getResponses(portletSession);

			String sessionResponseId = InvokerPortletUtil.encodeResponseKey(
				layout.getPlid(), _portletId,
				LanguageUtil.getLanguageId(headerRequest));

			InvokerPortletResponse response = sessionResponses.get(
				sessionResponseId);

			if (response == null) {
				invokeHeader(headerRequest, headerResponse);
			}
			else if ((response.getTime() < now) && (_expCache > 0)) {
				invokeHeader(headerRequest, headerResponse);
				response.setContent(bufferCacheServletResponse.getString());
			}
			else {
				headerResponseImpl.setTitle(response.getTitle());

				PrintWriter printWriter =
					bufferCacheServletResponse.getWriter();

				printWriter.print(response.getContent());
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"header for", _portletId, " takes ", stopWatch.getTime(),
					" ms"));
		}
	}

	@Override
	public void serveResource(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		try {
			invokeResource(resourceRequest, resourceResponse);
		}
		catch (Exception e) {
			processException(e, resourceRequest, resourceResponse);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"serveResource for ", _portletId, " takes ",
					stopWatch.getTime(), " ms"));
		}
	}

	@Override
	public void setPortletFilters() {
	}

	protected void cleanUp() {
		try {
			Closeable closeable = (Closeable)_invokerFilterContainer;

			closeable.close();
		}
		catch (IOException ioe) {
			_log.error("Unable to close invoker filter container", ioe);
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

			HttpServletRequest httpServletRequest =
				portletRequest.getHttpServletRequest();
			HttpServletResponse httpServletResponse =
				portletResponse.getHttpServletResponse();

			httpServletRequest.setAttribute(
				JavaConstants.JAVAX_PORTLET_PORTLET, _portlet);
			httpServletRequest.setAttribute(
				PortletRequest.LIFECYCLE_PHASE, lifecycle);
			httpServletRequest.setAttribute(
				PortletServlet.PORTLET_SERVLET_FILTER_CHAIN, filterChain);

			try {

				// Resource phase must be a forward because includes do not
				// allow you to specify the content type or headers

				if (lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
					requestDispatcher.forward(
						httpServletRequest, httpServletResponse);
				}
				else {
					requestDispatcher.include(
						httpServletRequest, httpServletResponse);
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

		if (MapUtil.isNotEmpty(properties) && (_expCache != null)) {
			String[] expCache = properties.get(RenderResponse.EXPIRATION_CACHE);

			if ((expCache != null) && (expCache.length > 0) &&
				(expCache[0] != null)) {

				_expCache = Integer.valueOf(GetterUtil.getInteger(expCache[0]));
			}
		}
	}

	protected void invokeAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		LiferayPortletRequest portletRequest =
			PortalUtil.getLiferayPortletRequest(actionRequest);
		LiferayPortletResponse portletResponse =
			PortalUtil.getLiferayPortletResponse(actionResponse);

		invoke(
			portletRequest, portletResponse, PortletRequest.ACTION_PHASE,
			_invokerFilterContainer.getActionFilters());
	}

	protected void invokeEvent(
			EventRequest eventRequest, EventResponse eventResponse)
		throws IOException, PortletException {

		LiferayPortletRequest portletRequest =
			PortalUtil.getLiferayPortletRequest(eventRequest);
		LiferayPortletResponse portletResponse =
			PortalUtil.getLiferayPortletResponse(eventResponse);

		invoke(
			portletRequest, portletResponse, PortletRequest.EVENT_PHASE,
			_invokerFilterContainer.getEventFilters());
	}

	protected void invokeHeader(
			HeaderRequest headerRequest, HeaderResponse headerResponse)
		throws IOException, PortletException {

		LiferayPortletRequest portletRequest =
			PortalUtil.getLiferayPortletRequest(headerRequest);
		LiferayPortletResponse portletResponse =
			PortalUtil.getLiferayPortletResponse(headerResponse);

		try {
			invoke(
				portletRequest, portletResponse, PortletRequest.HEADER_PHASE,
				_invokerFilterContainer.getHeaderFilters());
		}
		catch (Exception e) {
			processException(e, headerRequest, headerResponse);

			throw e;
		}
	}

	protected String invokeRender(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		LiferayPortletRequest portletRequest =
			PortalUtil.getLiferayPortletRequest(renderRequest);
		LiferayPortletResponse portletResponse =
			PortalUtil.getLiferayPortletResponse(renderResponse);

		try {
			invoke(
				portletRequest, portletResponse, PortletRequest.RENDER_PHASE,
				_invokerFilterContainer.getRenderFilters());
		}
		catch (Exception e) {
			processException(e, renderRequest, renderResponse);

			throw e;
		}

		RenderResponseImpl renderResponseImpl =
			(RenderResponseImpl)renderResponse;

		return renderResponseImpl.getTitle();
	}

	protected void invokeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		LiferayPortletRequest portletRequest =
			PortalUtil.getLiferayPortletRequest(resourceRequest);
		LiferayPortletResponse portletResponse =
			PortalUtil.getLiferayPortletResponse(resourceResponse);

		invoke(
			portletRequest, portletResponse, PortletRequest.RESOURCE_PHASE,
			_invokerFilterContainer.getResourceFilters());
	}

	protected void processException(
		Exception e, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		if (portletResponse instanceof StateAwareResponseImpl) {

			// PLT.5.4.7, TCK xxiii and PLT.15.2.6, cxlvi

			StateAwareResponseImpl stateAwareResponseImpl =
				(StateAwareResponseImpl)portletResponse;

			stateAwareResponseImpl.reset();
		}

		if (e instanceof RuntimeException) {

			// PLT.5.4.7, TCK xxv

			e = new PortletException(e);
		}

		if (e instanceof UnavailableException) {

			// PLT.5.4.7, TCK xxiv

			destroy();

			PortletLocalServiceUtil.deletePortlet(_portletModel);
		}

		if (e instanceof PortletException) {
			if ((portletResponse instanceof StateAwareResponseImpl) &&
				!(e instanceof UnavailableException)) {

				return;
			}

			if (!(portletRequest instanceof RenderRequest)) {
				portletRequest.setAttribute(_errorKey, e);
			}
		}
		else {
			ReflectionUtil.throwException(e);
		}
	}

	private void _initialize(
		com.liferay.portal.kernel.model.Portlet portletModel, Portlet portlet,
		PortletConfig portletConfig, PortletContext portletContext,
		InvokerFilterContainer invokerFilterContainer, boolean checkAuthToken,
		boolean facesPortlet, boolean headerPortlet) {

		_portletModel = portletModel;
		_portlet = portlet;
		_invokerFilterContainer = invokerFilterContainer;
		_checkAuthToken = checkAuthToken;
		_facesPortlet = facesPortlet;
		_headerPortlet = headerPortlet;

		_expCache = portletModel.getExpCache();
		_liferayPortletConfig = (LiferayPortletConfig)portletConfig;
		_liferayPortletContext = (LiferayPortletContext)portletContext;

		_portletId = _portletModel.getPortletId();

		_errorKey = _portletId.concat(PortletException.class.getName());

		if (_log.isDebugEnabled()) {
			com.liferay.portal.kernel.model.Portlet portletContextPortet =
				_liferayPortletContext.getPortlet();

			_log.debug(
				"Create instance cache wrapper for " +
					portletContextPortet.getPortletId());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InvokerPortletImpl.class);

	private boolean _checkAuthToken;
	private String _errorKey;
	private Integer _expCache;
	private boolean _facesPortlet;
	private boolean _headerPortlet;
	private InvokerFilterContainer _invokerFilterContainer;
	private LiferayPortletConfig _liferayPortletConfig;
	private LiferayPortletContext _liferayPortletContext;
	private Portlet _portlet;
	private ClassLoader _portletClassLoader;
	private String _portletId;
	private com.liferay.portal.kernel.model.Portlet _portletModel;

}