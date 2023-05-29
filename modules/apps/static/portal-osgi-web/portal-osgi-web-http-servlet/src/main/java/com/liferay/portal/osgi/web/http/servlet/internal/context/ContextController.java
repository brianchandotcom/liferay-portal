/*******************************************************************************
 * Copyright (c) 2016 Raymond Augé and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 ******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.internal.context;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.osgi.web.http.servlet.internal.HttpServiceRuntimeImpl;
import com.liferay.portal.osgi.web.http.servlet.internal.customizer.ContextFilterTrackerCustomizer;
import com.liferay.portal.osgi.web.http.servlet.internal.customizer.ContextListenerTrackerCustomizer;
import com.liferay.portal.osgi.web.http.servlet.internal.customizer.ContextResourceTrackerCustomizer;
import com.liferay.portal.osgi.web.http.servlet.internal.customizer.ContextServletTrackerCustomizer;
import com.liferay.portal.osgi.web.http.servlet.internal.error.IllegalContextNameException;
import com.liferay.portal.osgi.web.http.servlet.internal.error.IllegalContextPathException;
import com.liferay.portal.osgi.web.http.servlet.internal.error.RegisteredFilterException;
import com.liferay.portal.osgi.web.http.servlet.internal.registration.EndpointRegistration;
import com.liferay.portal.osgi.web.http.servlet.internal.registration.FilterRegistration;
import com.liferay.portal.osgi.web.http.servlet.internal.registration.ListenerRegistration;
import com.liferay.portal.osgi.web.http.servlet.internal.registration.ResourceRegistration;
import com.liferay.portal.osgi.web.http.servlet.internal.registration.ServletRegistration;
import com.liferay.portal.osgi.web.http.servlet.internal.servlet.FilterConfigImpl;
import com.liferay.portal.osgi.web.http.servlet.internal.servlet.HttpSessionAdaptor;
import com.liferay.portal.osgi.web.http.servlet.internal.servlet.Match;
import com.liferay.portal.osgi.web.http.servlet.internal.servlet.ResourceServlet;
import com.liferay.portal.osgi.web.http.servlet.internal.servlet.ServletConfigImpl;
import com.liferay.portal.osgi.web.http.servlet.internal.servlet.ServletContextAdaptor;
import com.liferay.portal.osgi.web.http.servlet.internal.util.Const;
import com.liferay.portal.osgi.web.http.servlet.internal.util.DTOUtil;
import com.liferay.portal.osgi.web.http.servlet.internal.util.EventListeners;
import com.liferay.portal.osgi.web.http.servlet.internal.util.Path;
import com.liferay.portal.osgi.web.http.servlet.internal.util.ServiceProperties;
import com.liferay.portal.osgi.web.http.servlet.internal.util.StringPlus;

import java.net.URI;
import java.net.URISyntaxException;

import java.security.AccessController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceObjects;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.runtime.dto.DTOConstants;
import org.osgi.service.http.runtime.dto.ErrorPageDTO;
import org.osgi.service.http.runtime.dto.FilterDTO;
import org.osgi.service.http.runtime.dto.ListenerDTO;
import org.osgi.service.http.runtime.dto.RequestInfoDTO;
import org.osgi.service.http.runtime.dto.ResourceDTO;
import org.osgi.service.http.runtime.dto.ServletContextDTO;
import org.osgi.service.http.runtime.dto.ServletDTO;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Raymond Augé
 */
public class ContextController {

	public static void checkPattern(String pattern) {
		if (pattern == null) {
			throw new IllegalArgumentException("Pattern cannot be null");
		}

		if (pattern.indexOf("*.") == 0) {
			return;
		}

		if (!pattern.startsWith(Const.SLASH) ||
			(pattern.endsWith(Const.SLASH) && !pattern.equals(Const.SLASH))) {

			throw new IllegalArgumentException(
				"Invalid pattern '" + pattern + "'");
		}
	}

	public ContextController(
		BundleContext trackingBundleContext,
		BundleContext consumingBundleContext,
		ServiceReference<ServletContextHelper> serviceReference,
		ProxyContext proxyContext,
		HttpServiceRuntimeImpl httpServiceRuntimeImpl, String contextName,
		String contextPath) {

		_validate(contextName, contextPath);

		_trackingBundleContext = trackingBundleContext;
		_consumingBundleContext = consumingBundleContext;

		_servletContextHelperServiceReference = serviceReference;

		_proxyContext = proxyContext;
		_httpServiceRuntimeImpl = httpServiceRuntimeImpl;
		_contextName = contextName;

		if (contextPath.equals(Const.SLASH)) {
			contextPath = Const.BLANK;
		}

		_contextPath = contextPath;

		_contextServiceId = (long)serviceReference.getProperty(
			Constants.SERVICE_ID);

		_initParamsMap = ServiceProperties.parseInitParams(
			serviceReference,
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_INIT_PARAM_PREFIX,
			proxyContext.getServletContext());

		_servletContextListenerServiceTracker = new ServiceTracker<>(
			_trackingBundleContext, ServletContextListener.class.getName(),
			new ContextListenerTrackerCustomizer(
				_trackingBundleContext, httpServiceRuntimeImpl, this));

		_servletContextListenerServiceTracker.open();

		_servletContextAttributeListenerServiceTracker = new ServiceTracker<>(
			_trackingBundleContext,
			ServletContextAttributeListener.class.getName(),
			new ContextListenerTrackerCustomizer(
				_trackingBundleContext, httpServiceRuntimeImpl, this));

		_servletContextAttributeListenerServiceTracker.open();

		_servletRequestListenerServiceTracker = new ServiceTracker<>(
			_trackingBundleContext, ServletRequestListener.class.getName(),
			new ContextListenerTrackerCustomizer(
				_trackingBundleContext, httpServiceRuntimeImpl, this));

		_servletRequestListenerServiceTracker.open();

		_servletRequestAttributeListenerServiceTracker = new ServiceTracker<>(
			_trackingBundleContext,
			ServletRequestAttributeListener.class.getName(),
			new ContextListenerTrackerCustomizer(
				_trackingBundleContext, httpServiceRuntimeImpl, this));

		_servletRequestAttributeListenerServiceTracker.open();

		_httpSessionListenerServiceTracker = new ServiceTracker<>(
			_trackingBundleContext, HttpSessionListener.class.getName(),
			new ContextListenerTrackerCustomizer(
				_trackingBundleContext, httpServiceRuntimeImpl, this));

		_httpSessionListenerServiceTracker.open();

		_httpSessionAttributeListenerServiceTracker = new ServiceTracker<>(
			_trackingBundleContext,
			HttpSessionAttributeListener.class.getName(),
			new ContextListenerTrackerCustomizer(
				_trackingBundleContext, httpServiceRuntimeImpl, this));

		_httpSessionAttributeListenerServiceTracker.open();

		ServletContext servletContext =
			httpServiceRuntimeImpl.getParentServletContext();

		if ((servletContext.getMajorVersion() >= 3) &&
			(servletContext.getMinorVersion() > 0)) {

			_httpSessionIdListenerServiceTracker = new ServiceTracker<>(
				_trackingBundleContext, HttpSessionIdListener.class.getName(),
				new ContextListenerTrackerCustomizer(
					_trackingBundleContext, httpServiceRuntimeImpl, this));

			_httpSessionIdListenerServiceTracker.open();
		}
		else {
			_httpSessionIdListenerServiceTracker = null;
		}

		_filterServiceTracker = new ServiceTracker<>(
			_trackingBundleContext, Filter.class,
			new ContextFilterTrackerCustomizer(
				_trackingBundleContext, httpServiceRuntimeImpl, this));

		_filterServiceTracker.open();

		_servletServiceTracker = new ServiceTracker<>(
			_trackingBundleContext, Servlet.class,
			new ContextServletTrackerCustomizer(
				_trackingBundleContext, httpServiceRuntimeImpl, this));

		_servletServiceTracker.open();

		_resourceServiceTracker = new ServiceTracker<>(
			_trackingBundleContext, Object.class,
			new ContextResourceTrackerCustomizer(
				_trackingBundleContext, httpServiceRuntimeImpl, this));

		_resourceServiceTracker.open();
	}

	public FilterRegistration addFilterRegistration(
			ServiceReference<Filter> serviceReference)
		throws ServletException {

		_checkShutdown();

		ServiceHolder<Filter> filterHolder = new ServiceHolder<>(
			_consumingBundleContext.getServiceObjects(serviceReference));

		Filter filter = filterHolder.get();

		FilterRegistration registration = null;
		boolean addedRegisteredObject = false;

		Set<Object> registeredObjects =
			_httpServiceRuntimeImpl.getRegisteredObjects();

		try {
			if (filter == null) {
				throw new IllegalArgumentException("Filter cannot be null");
			}

			addedRegisteredObject = registeredObjects.add(filter);

			if (addedRegisteredObject) {
				registration = _addFilterRegistration(
					filterHolder, serviceReference);
			}
		}
		finally {
			if (registration == null) {
				filterHolder.release();

				if (addedRegisteredObject) {
					registeredObjects.remove(filter);
				}
			}
		}

		return registration;
	}

	public ListenerRegistration addListenerRegistration(
		ServiceReference<EventListener> serviceReference) {

		_checkShutdown();

		ServiceHolder<EventListener> listenerHolder = new ServiceHolder<>(
			_consumingBundleContext.getServiceObjects(serviceReference));

		EventListener listener = listenerHolder.get();

		ListenerRegistration registration = null;

		try {
			if (listener == null) {
				throw new IllegalArgumentException(
					"EventListener cannot be null");
			}

			registration = _addListenerRegistration(
				listenerHolder, serviceReference);
		}
		finally {
			if (registration == null) {
				listenerHolder.release();
			}
		}

		return registration;
	}

	public ResourceRegistration addResourceRegistration(
		ServiceReference<?> serviceReference) {

		_checkShutdown();

		String prefix = (String)serviceReference.getProperty(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PREFIX);

		_checkPrefix(prefix);

		String[] patterns = StringPlus.from(
			serviceReference.getProperty(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PATTERN));

		if (patterns.length < 1) {
			throw new IllegalArgumentException("Patterns must contain a value");
		}

		for (String pattern : patterns) {
			checkPattern(pattern);
		}

		Long serviceId = (Long)serviceReference.getProperty(
			Constants.SERVICE_ID);

		ClassLoader legacyTCCL = (ClassLoader)serviceReference.getProperty(
			Const.EQUINOX_LEGACY_TCCL_PROP);

		if (legacyTCCL != null) {
			serviceId = -serviceId;
		}

		Bundle bundle = serviceReference.getBundle();

		ServletContextHelper curServletContextHelper = _getServletContextHelper(
			bundle);

		ResourceDTO resourceDTO = new ResourceDTO();

		resourceDTO.patterns = _sort(patterns);
		resourceDTO.prefix = prefix;
		resourceDTO.serviceId = serviceId;
		resourceDTO.servletContextId = _contextServiceId;

		ResourceRegistration resourceRegistration = new ResourceRegistration(
			new ServiceHolder<>(
				new ResourceServlet(
					prefix, curServletContextHelper,
					AccessController.getContext()),
				bundle, serviceId,
				GetterUtil.getInteger(
					serviceReference.getProperty(Constants.SERVICE_RANKING))),
			resourceDTO, curServletContextHelper, this, legacyTCCL);

		try {
			resourceRegistration.init(
				new ServletConfigImpl(
					resourceRegistration.getName(), new HashMap<>(),
					_createServletContext(bundle, curServletContextHelper)));
		}
		catch (ServletException servletException) {
			if (_log.isDebugEnabled()) {
				_log.debug(servletException);
			}

			return null;
		}

		_endpointRegistrations.add(resourceRegistration);

		return resourceRegistration;
	}

	public ServletRegistration addServletRegistration(
			ServiceReference<Servlet> serviceReference)
		throws ServletException {

		_checkShutdown();

		ServiceHolder<Servlet> serviceHolder = new ServiceHolder<>(
			_consumingBundleContext.getServiceObjects(serviceReference));

		Servlet servlet = serviceHolder.get();

		ServletRegistration registration = null;
		boolean addedRegisteredObject = false;

		Set<Object> registeredObjects =
			_httpServiceRuntimeImpl.getRegisteredObjects();

		try {
			if (servlet == null) {
				throw new IllegalArgumentException("Servlet cannot be null");
			}

			addedRegisteredObject = registeredObjects.add(servlet);

			if (addedRegisteredObject) {
				registration = _addServletRegistration(
					serviceHolder, serviceReference);
			}
		}
		finally {
			if (registration == null) {
				serviceHolder.release();

				if (addedRegisteredObject) {
					registeredObjects.remove(servlet);
				}
			}
		}

		return registration;
	}

	public void createContextAttributes() {
		getProxyContext().createContextAttributes(this);
	}

	public void destroy() {
		_flushActiveSessions();
		_resourceServiceTracker.close();
		_servletServiceTracker.close();
		_filterServiceTracker.close();

		if (_httpSessionIdListenerServiceTracker != null) {
			_httpSessionIdListenerServiceTracker.close();
		}

		_httpSessionAttributeListenerServiceTracker.close();
		_httpSessionListenerServiceTracker.close();
		_servletRequestAttributeListenerServiceTracker.close();
		_servletRequestListenerServiceTracker.close();
		_servletContextAttributeListenerServiceTracker.close();
		_servletContextListenerServiceTracker.close();

		_endpointRegistrations.clear();
		_filterRegistrations.clear();
		_listenerRegistrations.clear();
		_eventListeners.clear();
		_proxyContext.destroy();

		_shutdown = true;
	}

	public void destroyContextAttributes() {
		if (_shutdown) {
			return;
		}

		_proxyContext.destroyContextAttributes(this);
	}

	public void fireSessionIdChanged(String oldSessionId) {
		if (_shutdown) {
			return;
		}

		ServletContext servletContext = _proxyContext.getServletContext();

		if ((servletContext.getMajorVersion() <= 3) &&
			(servletContext.getMinorVersion() < 1)) {

			return;
		}

		List<HttpSessionIdListener> listeners = _eventListeners.get(
			HttpSessionIdListener.class);

		if (listeners.isEmpty()) {
			return;
		}

		for (HttpSessionAdaptor httpSessionAdaptor :
				_activeSessionsMap.values()) {

			HttpSessionEvent httpSessionEvent = new HttpSessionEvent(
				httpSessionAdaptor);

			for (HttpSessionIdListener listener : listeners) {
				listener.sessionIdChanged(httpSessionEvent, oldSessionId);
			}
		}
	}

	public Map<String, HttpSessionAdaptor> getActiveSessions() {
		return _activeSessionsMap;
	}

	public String getContextName() {
		return _contextName;
	}

	public String getContextPath() {
		return _contextPath;
	}

	public DispatchTargets getDispatchTargets(
		String pathString, RequestInfoDTO requestInfoDTO) {

		Path path = new Path(pathString);

		String queryString = path.getQueryString();
		String requestURI = path.getRequestURI();

		// perfect match

		DispatchTargets dispatchTargets = _getDispatchTargets(
			requestURI, null, queryString, Match.EXACT, requestInfoDTO);

		if (dispatchTargets == null) {

			// extension match

			dispatchTargets = _getDispatchTargets(
				requestURI, path.getExtension(), queryString, Match.EXTENSION,
				requestInfoDTO);
		}

		if (dispatchTargets == null) {

			// regex match

			dispatchTargets = _getDispatchTargets(
				requestURI, null, queryString, Match.REGEX, requestInfoDTO);
		}

		if (dispatchTargets == null) {

			// handle '/' aliases

			dispatchTargets = _getDispatchTargets(
				requestURI, null, queryString, Match.DEFAULT_SERVLET,
				requestInfoDTO);
		}

		return dispatchTargets;
	}

	public DispatchTargets getDispatchTargets(
		String servletName, String requestURI, String servletPath,
		String pathInfo, String extension, String queryString, Match match,
		RequestInfoDTO requestInfoDTO) {

		_checkShutdown();

		EndpointRegistration<?> endpointRegistration = null;

		for (EndpointRegistration<?> curEndpointRegistration :
				_endpointRegistrations) {

			if (Objects.nonNull(
					curEndpointRegistration.match(
						servletName, servletPath, pathInfo, extension,
						match))) {

				endpointRegistration = curEndpointRegistration;

				break;
			}
		}

		if (endpointRegistration == null) {
			return null;
		}

		if (match == Match.EXTENSION) {
			servletPath = servletPath + pathInfo;
			pathInfo = null;
		}

		_addEndpointRegistrationsToRequestInfo(
			endpointRegistration, requestInfoDTO);

		if (_filterRegistrations.isEmpty()) {
			return new DispatchTargets(
				this, endpointRegistration, servletName, requestURI,
				servletPath, pathInfo, queryString);
		}

		if (requestURI != null) {
			int x = requestURI.lastIndexOf('.');

			if (x != -1) {
				extension = requestURI.substring(x + 1);
			}
		}

		List<FilterRegistration> matchingFilterRegistrations =
			new ArrayList<>();

		_collectFilters(
			matchingFilterRegistrations, endpointRegistration.getName(),
			requestURI, extension);

		_addFilterRegistrationsToRequestInfo(
			matchingFilterRegistrations, requestInfoDTO);

		return new DispatchTargets(
			this, endpointRegistration, matchingFilterRegistrations,
			servletName, requestURI, servletPath, pathInfo, queryString);
	}

	public Set<EndpointRegistration<?>> getEndpointRegistrations() {
		return _endpointRegistrations;
	}

	public EventListeners getEventListeners() {
		return _eventListeners;
	}

	public Set<FilterRegistration> getFilterRegistrations() {
		return _filterRegistrations;
	}

	public String getFullContextPath() {
		List<String> endpoints =
			_httpServiceRuntimeImpl.getHttpServiceEndpoints();

		if (endpoints.isEmpty()) {
			String servletPath = _proxyContext.getServletPath();

			return servletPath.concat(_contextPath);
		}

		String defaultEndpoint = endpoints.get(0);

		if (defaultEndpoint.endsWith("/")) {
			defaultEndpoint = defaultEndpoint.substring(
				0, defaultEndpoint.length() - 1);
		}

		return defaultEndpoint + _contextPath;
	}

	public HttpServiceRuntimeImpl getHttpServiceRuntime() {
		return _httpServiceRuntimeImpl;
	}

	public Map<String, String> getInitParams() {
		return _initParamsMap;
	}

	public Set<ListenerRegistration> getListenerRegistrations() {
		return _listenerRegistrations;
	}

	public ProxyContext getProxyContext() {
		return _proxyContext;
	}

	public long getServiceId() {
		return _contextServiceId;
	}

	public synchronized ServletContextDTO getServletContextDTO() {
		ServletContextDTO servletContextDTO = new ServletContextDTO();

		servletContextDTO.attributes = _getDTOAttributes(
			_proxyContext.getServletContext());
		servletContextDTO.contextPath = getContextPath();
		servletContextDTO.initParams = new HashMap<>(_initParamsMap);
		servletContextDTO.name = getContextName();
		servletContextDTO.serviceId = getServiceId();

		_collectEndpointDTOs(servletContextDTO);
		_collectFilterDTOs(servletContextDTO);
		_collectListenerDTOs(servletContextDTO);

		return servletContextDTO;
	}

	public HttpSessionAdaptor getSessionAdaptor(
		HttpSession httpSession, ServletContext servletContext) {

		String sessionId = httpSession.getId();

		HttpSessionAdaptor httpSessionAdaptor = _activeSessionsMap.get(
			sessionId);

		if (httpSessionAdaptor != null) {
			return httpSessionAdaptor;
		}

		httpSessionAdaptor = HttpSessionAdaptor.createHttpSessionAdaptor(
			httpSession, servletContext, this);

		HttpSessionAdaptor previousHttpSessionAdaptor =
			_activeSessionsMap.putIfAbsent(sessionId, httpSessionAdaptor);

		if (previousHttpSessionAdaptor != null) {
			return previousHttpSessionAdaptor;
		}

		List<HttpSessionListener> httpSessionListeners = _eventListeners.get(
			HttpSessionListener.class);

		if (httpSessionListeners.isEmpty()) {
			return httpSessionAdaptor;
		}

		HttpSessionEvent httpSessionEvent = new HttpSessionEvent(
			httpSessionAdaptor);

		for (HttpSessionListener httpSessionListener : httpSessionListeners) {
			httpSessionListener.sessionCreated(httpSessionEvent);
		}

		return httpSessionAdaptor;
	}

	public boolean matches(org.osgi.framework.Filter targetFilter) {
		return targetFilter.match(_servletContextHelperServiceReference);
	}

	public boolean matches(ServiceReference<?> serviceReference) {
		String contextSelector = (String)serviceReference.getProperty(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT);

		if (_contextName.equals(contextSelector)) {
			return true;
		}

		if (contextSelector == null) {
			contextSelector =
				_httpServiceRuntimeImpl.getDefaultContextSelectFilter(
					serviceReference);

			if (contextSelector == null) {
				contextSelector = StringBundler.concat(
					"(", HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME,
					"=",
					HttpWhiteboardConstants.
						HTTP_WHITEBOARD_DEFAULT_CONTEXT_NAME,
					")");
			}
		}

		if (contextSelector.startsWith(Const.OPEN_PAREN)) {
			org.osgi.framework.Filter targetFilter;

			try {
				targetFilter = FrameworkUtil.createFilter(contextSelector);
			}
			catch (InvalidSyntaxException invalidSyntaxException) {
				throw new IllegalArgumentException(invalidSyntaxException);
			}

			if (matches(targetFilter)) {
				return true;
			}
		}

		return false;
	}

	public void removeActiveSession(String id) {
		_activeSessionsMap.remove(id);
	}

	@Override
	public String toString() {
		String value = _string;

		if (value == null) {
			value = StringBundler.concat(
				ContextController.class.getSimpleName(), '[', _contextName,
				", ", _trackingBundleContext.getBundle(), ']');

			_string = value;
		}

		return value;
	}

	public void ungetServletContextHelper(Bundle curBundle) {
		BundleContext bundleContext = curBundle.getBundleContext();

		try {
			bundleContext.ungetService(_servletContextHelperServiceReference);
		}
		catch (IllegalStateException illegalStateException) {
			if (_log.isDebugEnabled()) {
				_log.debug(illegalStateException);
			}
		}
	}

	public static final class ServiceHolder<S>
		implements Comparable<ServiceHolder<?>> {

		public ServiceHolder(
			S service, Bundle bundle, long serviceId, int serviceRanking) {

			_service = service;
			_bundle = bundle;
			_serviceId = serviceId;
			_serviceRanking = serviceRanking;

			_serviceObjects = null;
		}

		public ServiceHolder(ServiceObjects<S> serviceObjects) {
			_serviceObjects = serviceObjects;

			ServiceReference<S> serviceReference =
				serviceObjects.getServiceReference();

			_service = serviceObjects.getService();
			_bundle = serviceReference.getBundle();
			_serviceId = (Long)serviceReference.getProperty(
				Constants.SERVICE_ID);

			_serviceRanking = GetterUtil.getInteger(
				serviceReference.getProperty(Constants.SERVICE_RANKING));
		}

		@Override
		public int compareTo(ServiceHolder<?> other) {
			if (_serviceRanking != other._serviceRanking) {
				if (_serviceRanking < other._serviceRanking) {
					return 1;
				}

				return -1;
			}

			return Long.compare(_serviceId, other._serviceId);
		}

		public S get() {
			return _service;
		}

		public Bundle getBundle() {
			return _bundle;
		}

		public ServiceReference<S> getServiceReference() {
			if (_serviceObjects == null) {
				return null;
			}

			return _serviceObjects.getServiceReference();
		}

		public void release() {
			if ((_serviceObjects != null) && (_service != null)) {
				try {
					_serviceObjects.ungetService(_service);
				}
				catch (IllegalStateException illegalStateException) {
					if (_log.isDebugEnabled()) {
						_log.debug(illegalStateException);
					}
				}
			}
		}

		private final Bundle _bundle;
		private final S _service;
		private final long _serviceId;
		private final ServiceObjects<S> _serviceObjects;
		private final int _serviceRanking;

	}

	private void _addEndpointRegistrationsToRequestInfo(
		EndpointRegistration<?> endpointRegistration,
		RequestInfoDTO requestInfoDTO) {

		if (requestInfoDTO == null) {
			return;
		}

		requestInfoDTO.servletContextId = getServiceId();

		if (endpointRegistration instanceof ResourceRegistration) {
			requestInfoDTO.resourceDTO =
				(ResourceDTO)endpointRegistration.getD();
		}
		else {
			requestInfoDTO.servletDTO = (ServletDTO)endpointRegistration.getD();
		}
	}

	private FilterRegistration _addFilterRegistration(
			ServiceHolder<Filter> serviceHolder,
			ServiceReference<Filter> serviceReference)
		throws ServletException {

		String[] patterns = StringPlus.from(
			serviceReference.getProperty(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN));

		String[] regexes = StringPlus.from(
			serviceReference.getProperty(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_REGEX));

		String[] servletNames = StringPlus.from(
			serviceReference.getProperty(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_SERVLET));

		if ((patterns.length == 0) && (regexes.length == 0) &&
			(servletNames.length == 0)) {

			throw new IllegalArgumentException(
				"Patterns, regex or servletNames must contain a value");
		}

		for (String pattern : patterns) {
			checkPattern(pattern);
		}

		Filter filter = serviceHolder.get();

		if (filter == null) {
			throw new IllegalArgumentException("Filter cannot be null");
		}

		String name = ServiceProperties.parseName(
			serviceReference.getProperty(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_NAME),
			serviceHolder.get());

		if (name == null) {
			Class<?> clazz = filter.getClass();

			name = clazz.getName();
		}

		for (FilterRegistration filterRegistration : _filterRegistrations) {
			if (Objects.equals(filter, filterRegistration.getT())) {
				throw new RegisteredFilterException(filter);
			}
		}

		Long serviceId = (Long)serviceReference.getProperty(
			Constants.SERVICE_ID);

		ClassLoader legacyTCCL = (ClassLoader)serviceReference.getProperty(
			Const.EQUINOX_LEGACY_TCCL_PROP);

		if (legacyTCCL != null) {
			serviceId = -serviceId;
		}

		Map<String, String> filterInitParamsMap =
			ServiceProperties.parseInitParams(
				serviceReference,
				HttpWhiteboardConstants.
					HTTP_WHITEBOARD_FILTER_INIT_PARAM_PREFIX);

		FilterDTO filterDTO = new FilterDTO();

		filterDTO.asyncSupported = GetterUtil.getBoolean(
			serviceReference.getProperty(
				HttpWhiteboardConstants.
					HTTP_WHITEBOARD_FILTER_ASYNC_SUPPORTED));
		filterDTO.dispatcher = _sort(
			_checkDispatcher(
				StringPlus.from(
					serviceReference.getProperty(
						HttpWhiteboardConstants.
							HTTP_WHITEBOARD_FILTER_DISPATCHER))));
		filterDTO.initParams = filterInitParamsMap;
		filterDTO.name = name;
		filterDTO.patterns = _sort(patterns);
		filterDTO.regexs = regexes;
		filterDTO.serviceId = serviceId;
		filterDTO.servletContextId = _contextServiceId;
		filterDTO.servletNames = _sort(servletNames);

		Integer filterPriority = (Integer)serviceReference.getProperty(
			Constants.SERVICE_RANKING);

		if (filterPriority == null) {
			filterPriority = 0;
		}

		FilterRegistration newFilterRegistration = new FilterRegistration(
			serviceHolder, filterDTO, filterPriority, this, legacyTCCL);

		newFilterRegistration.init(
			new FilterConfigImpl(
				name, filterInitParamsMap,
				_createServletContext(
					serviceHolder.getBundle(),
					_getServletContextHelper(serviceHolder.getBundle()))));

		_filterRegistrations.add(newFilterRegistration);

		return newFilterRegistration;
	}

	private void _addFilterRegistrationsToRequestInfo(
		List<FilterRegistration> matchedFilterRegistrations,
		RequestInfoDTO requestInfoDTO) {

		if (requestInfoDTO == null) {
			return;
		}

		FilterDTO[] filterDTOs =
			new FilterDTO[matchedFilterRegistrations.size()];

		for (int i = 0; i < filterDTOs.length; i++) {
			FilterRegistration filterRegistration =
				matchedFilterRegistrations.get(i);

			filterDTOs[i] = filterRegistration.getD();
		}

		requestInfoDTO.filterDTOs = filterDTOs;
	}

	private ListenerRegistration _addListenerRegistration(
		ServiceHolder<EventListener> serviceHolder,
		ServiceReference<EventListener> serviceReference) {

		List<Class<? extends EventListener>> classes = _getListenerClasses(
			serviceReference);

		if (classes.isEmpty()) {
			throw new IllegalArgumentException(
				"EventListener does not implement a supported type");
		}

		EventListener eventListener = serviceHolder.get();

		for (ListenerRegistration listenerRegistration :
				_listenerRegistrations) {

			if (Objects.equals(eventListener, listenerRegistration.getT())) {
				return null;
			}
		}

		ListenerDTO listenerDTO = new ListenerDTO();

		listenerDTO.serviceId = (Long)serviceReference.getProperty(
			Constants.SERVICE_ID);
		listenerDTO.servletContextId = _contextServiceId;
		listenerDTO.types = _asStringArray(classes);

		ServletContext servletContext = _createServletContext(
			serviceHolder.getBundle(),
			_getServletContextHelper(serviceHolder.getBundle()));

		ListenerRegistration listenerRegistration = new ListenerRegistration(
			serviceHolder, classes, listenerDTO, servletContext, this);

		if (classes.contains(ServletContextListener.class)) {
			ServletContextListener servletContextListener =
				(ServletContextListener)listenerRegistration.getT();

			servletContextListener.contextInitialized(
				new ServletContextEvent(servletContext));
		}

		_listenerRegistrations.add(listenerRegistration);

		_eventListeners.put(classes, listenerRegistration);

		return listenerRegistration;
	}

	private ServletRegistration _addServletRegistration(
			ServiceHolder<Servlet> serviceHolder,
			ServiceReference<Servlet> serviceReference)
		throws ServletException {

		String[] errorPages = StringPlus.from(
			serviceReference.getProperty(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_ERROR_PAGE));

		String[] patterns = StringPlus.from(
			serviceReference.getProperty(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN));

		String servletNameFromProperties = (String)serviceReference.getProperty(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME);

		if ((patterns.length == 0) && (errorPages.length == 0) &&
			(servletNameFromProperties == null)) {

			StringBundler sb = new StringBundler(7);

			sb.append("One of the service properties ");
			sb.append(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_ERROR_PAGE);
			sb.append(", ");
			sb.append(HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME);
			sb.append(", ");
			sb.append(HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN);
			sb.append(" must contain a value.");

			throw new IllegalArgumentException(sb.toString());
		}

		for (String pattern : patterns) {
			checkPattern(pattern);
		}

		boolean asyncSupported = GetterUtil.getBoolean(
			serviceReference.getProperty(
				HttpWhiteboardConstants.
					HTTP_WHITEBOARD_SERVLET_ASYNC_SUPPORTED));

		Long serviceId = (Long)serviceReference.getProperty(
			Constants.SERVICE_ID);

		ClassLoader legacyTCCL = (ClassLoader)serviceReference.getProperty(
			Const.EQUINOX_LEGACY_TCCL_PROP);

		if (legacyTCCL != null) {
			serviceId = -serviceId;
		}

		Map<String, String> servletInitParamsMap =
			ServiceProperties.parseInitParams(
				serviceReference,
				HttpWhiteboardConstants.
					HTTP_WHITEBOARD_SERVLET_INIT_PARAM_PREFIX);

		Servlet servlet = serviceHolder.get();

		String generatedServletName = ServiceProperties.parseName(
			serviceReference.getProperty(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME),
			servlet);

		ServletDTO servletDTO = new ServletDTO();

		servletDTO.asyncSupported = asyncSupported;
		servletDTO.initParams = servletInitParamsMap;
		servletDTO.name = generatedServletName;
		servletDTO.patterns = _sort(patterns);
		servletDTO.serviceId = serviceId;
		servletDTO.servletContextId = _contextServiceId;
		servletDTO.servletInfo = servlet.getServletInfo();

		ErrorPageDTO errorPageDTO = null;

		if (errorPages.length > 0) {
			List<String> exceptions = new ArrayList<>();

			Set<Long> errorCodeSet = new LinkedHashSet<>();

			for (String errorPage : errorPages) {
				try {
					if (Objects.equals(errorPage, "4xx")) {
						for (long code = 400; code < 500; code++) {
							errorCodeSet.add(code);
						}
					}
					else if (Objects.equals(errorPage, "5xx")) {
						for (long code = 500; code < 600; code++) {
							errorCodeSet.add(code);
						}
					}
					else {
						long code = Long.parseLong(errorPage);

						errorCodeSet.add(code);
					}
				}
				catch (NumberFormatException numberFormatException) {
					if (_log.isDebugEnabled()) {
						_log.debug(numberFormatException);
					}

					exceptions.add(errorPage);
				}
			}

			long[] errorCodes = new long[errorCodeSet.size()];
			int i = 0;

			for (Long code : errorCodeSet) {
				errorCodes[i] = code;
				i++;
			}

			errorPageDTO = new ErrorPageDTO();

			errorPageDTO.asyncSupported = asyncSupported;
			errorPageDTO.errorCodes = errorCodes;
			errorPageDTO.exceptions = exceptions.toArray(new String[0]);
			errorPageDTO.initParams = servletInitParamsMap;
			errorPageDTO.name = generatedServletName;
			errorPageDTO.serviceId = serviceId;
			errorPageDTO.servletContextId = _contextServiceId;
			errorPageDTO.servletInfo = servlet.getServletInfo();
		}

		ServletContextHelper curServletContextHelper = _getServletContextHelper(
			serviceHolder.getBundle());

		ServletRegistration servletRegistration = new ServletRegistration(
			serviceHolder, servletDTO, errorPageDTO, curServletContextHelper,
			this, legacyTCCL);

		servletRegistration.init(
			new ServletConfigImpl(
				generatedServletName, servletInitParamsMap,
				_createServletContext(
					serviceHolder.getBundle(), curServletContextHelper)));

		_endpointRegistrations.add(servletRegistration);

		return servletRegistration;
	}

	private String[] _asStringArray(
		List<Class<? extends EventListener>> classes) {

		String[] classesArray = new String[classes.size()];

		for (int i = 0; i < classesArray.length; i++) {
			Class<?> clazz = classes.get(i);

			classesArray[i] = clazz.getName();
		}

		Arrays.sort(classesArray);

		return classesArray;
	}

	private String[] _checkDispatcher(String[] dispatchers) {
		if ((dispatchers == null) || (dispatchers.length == 0)) {
			return _DISPATCHER;
		}

		for (String dispatcher : dispatchers) {
			try {
				DispatcherType.valueOf(dispatcher);
			}
			catch (IllegalArgumentException illegalArgumentException) {
				throw new IllegalArgumentException(
					"Invalid dispatcher '" + dispatcher + "'",
					illegalArgumentException);
			}
		}

		Arrays.sort(dispatchers);

		return dispatchers;
	}

	private void _checkPrefix(String prefix) {
		if (prefix == null) {
			throw new IllegalArgumentException("Prefix cannot be null");
		}

		if (prefix.endsWith(Const.SLASH) && !prefix.equals(Const.SLASH)) {
			throw new IllegalArgumentException(
				"Invalid prefix '" + prefix + "'");
		}
	}

	private void _checkShutdown() {
		if (_shutdown) {
			throw new IllegalStateException("Context is already shutdown");
		}
	}

	private void _collectEndpointDTOs(ServletContextDTO servletContextDTO) {
		List<ErrorPageDTO> errorPageDTOs = new ArrayList<>();
		List<ResourceDTO> resourceDTOs = new ArrayList<>();
		List<ServletDTO> servletDTOs = new ArrayList<>();

		for (EndpointRegistration<?> endpointRegistration :
				_endpointRegistrations) {

			if (endpointRegistration instanceof ResourceRegistration) {
				resourceDTOs.add(
					DTOUtil.clone((ResourceDTO)endpointRegistration.getD()));
			}
			else {
				ServletRegistration servletRegistration =
					(ServletRegistration)endpointRegistration;

				servletDTOs.add(DTOUtil.clone(servletRegistration.getD()));

				ErrorPageDTO errorPageDTO =
					servletRegistration.getErrorPageDTO();

				if (errorPageDTO != null) {
					errorPageDTOs.add(DTOUtil.clone(errorPageDTO));
				}
			}
		}

		servletContextDTO.errorPageDTOs = errorPageDTOs.toArray(
			new ErrorPageDTO[0]);
		servletContextDTO.resourceDTOs = resourceDTOs.toArray(
			new ResourceDTO[0]);
		servletContextDTO.servletDTOs = servletDTOs.toArray(new ServletDTO[0]);
	}

	private void _collectFilterDTOs(ServletContextDTO servletContextDTO) {
		List<FilterDTO> filterDTOs = new ArrayList<>();

		for (FilterRegistration filterRegistration : _filterRegistrations) {
			filterDTOs.add(DTOUtil.clone(filterRegistration.getD()));
		}

		servletContextDTO.filterDTOs = filterDTOs.toArray(new FilterDTO[0]);
	}

	private void _collectFilters(
		List<FilterRegistration> matchingFilterRegistrations,
		String servletName, String requestURI, String extension) {

		for (FilterRegistration filterRegistration : _filterRegistrations) {
			if (Objects.nonNull(
					filterRegistration.match(
						servletName, requestURI, extension)) &&
				!matchingFilterRegistrations.contains(filterRegistration)) {

				matchingFilterRegistrations.add(filterRegistration);
			}
		}
	}

	private void _collectListenerDTOs(ServletContextDTO servletContextDTO) {
		List<ListenerDTO> listenerDTOs = new ArrayList<>();

		for (ListenerRegistration listenerRegistration :
				_listenerRegistrations) {

			listenerDTOs.add(DTOUtil.clone(listenerRegistration.getD()));
		}

		servletContextDTO.listenerDTOs = listenerDTOs.toArray(
			new ListenerDTO[0]);
	}

	private ServletContext _createServletContext(
		Bundle curBundle, ServletContextHelper curServletContextHelper) {

		ServletContextAdaptor adaptor = new ServletContextAdaptor(
			this, curBundle, curServletContextHelper, _eventListeners,
			AccessController.getContext());

		return adaptor.createServletContext();
	}

	private void _flushActiveSessions() {
		Collection<HttpSessionAdaptor> httpSessionAdaptors =
			_activeSessionsMap.values();

		Iterator<HttpSessionAdaptor> iterator = httpSessionAdaptors.iterator();

		while (iterator.hasNext()) {
			HttpSessionAdaptor httpSessionAdaptor = iterator.next();

			httpSessionAdaptor.invalidate();

			iterator.remove();
		}
	}

	private DispatchTargets _getDispatchTargets(
		String requestURI, String extension, String queryString, Match match,
		RequestInfoDTO requestInfoDTO) {

		int pos = requestURI.lastIndexOf('/');

		String servletPath = requestURI;
		String pathInfo = null;

		if (match == Match.DEFAULT_SERVLET) {
			pathInfo = servletPath;
			servletPath = Const.SLASH;
		}

		while (true) {
			DispatchTargets dispatchTargets = getDispatchTargets(
				null, requestURI, servletPath, pathInfo, extension, queryString,
				match, requestInfoDTO);

			if (dispatchTargets != null) {
				return dispatchTargets;
			}

			if (match == Match.EXACT) {
				break;
			}

			if (pos > -1) {
				servletPath = requestURI.substring(0, pos);

				pathInfo = requestURI.substring(pos);

				pos = servletPath.lastIndexOf('/');

				continue;
			}

			break;
		}

		return null;
	}

	private Map<String, Object> _getDTOAttributes(
		ServletContext servletContext) {

		Map<String, Object> map = new HashMap<>();

		for (Enumeration<String> attributeNamesEnumeration =
				servletContext.getAttributeNames();
			 attributeNamesEnumeration.hasMoreElements();) {

			String name = attributeNamesEnumeration.nextElement();

			map.put(name, DTOUtil.mapValue(servletContext.getAttribute(name)));
		}

		return Collections.unmodifiableMap(map);
	}

	private List<Class<? extends EventListener>> _getListenerClasses(
		ServiceReference<EventListener> serviceReference) {

		List<String> objectClassList = Arrays.asList(
			StringPlus.from(
				serviceReference.getProperty(Constants.OBJECTCLASS)));

		List<Class<? extends EventListener>> classes = new ArrayList<>();

		if (objectClassList.contains(ServletContextListener.class.getName())) {
			classes.add(ServletContextListener.class);
		}

		if (objectClassList.contains(
				ServletContextAttributeListener.class.getName())) {

			classes.add(ServletContextAttributeListener.class);
		}

		if (objectClassList.contains(ServletRequestListener.class.getName())) {
			classes.add(ServletRequestListener.class);
		}

		if (objectClassList.contains(
				ServletRequestAttributeListener.class.getName())) {

			classes.add(ServletRequestAttributeListener.class);
		}

		if (objectClassList.contains(HttpSessionListener.class.getName())) {
			classes.add(HttpSessionListener.class);
		}

		if (objectClassList.contains(
				HttpSessionAttributeListener.class.getName())) {

			classes.add(HttpSessionAttributeListener.class);
		}

		ServletContext servletContext = _proxyContext.getServletContext();

		if ((servletContext.getMajorVersion() >= 3) &&
			(servletContext.getMinorVersion() > 0) &&
			objectClassList.contains(HttpSessionIdListener.class.getName())) {

			classes.add(HttpSessionIdListener.class);
		}

		return classes;
	}

	private ServletContextHelper _getServletContextHelper(Bundle curBundle) {
		BundleContext bundleContext = curBundle.getBundleContext();

		return bundleContext.getService(_servletContextHelperServiceReference);
	}

	private String[] _sort(String[] values) {
		if (values == null) {
			return null;
		}

		Arrays.sort(values);

		return values;
	}

	private void _validate(
		String preValidationContextName, String preValidationContextPath) {

		Matcher matcher = _contextNamePattern.matcher(preValidationContextName);

		if (!matcher.matches()) {
			throw new IllegalContextNameException(
				"The context name '" + preValidationContextName +
					"' does not follow Bundle-SymbolicName syntax.",
				DTOConstants.FAILURE_REASON_VALIDATION_FAILED);
		}

		try {
			new URI(
				Const.HTTP, Const.LOCALHOST, preValidationContextPath, null);
		}
		catch (URISyntaxException uriSyntaxException) {
			IllegalContextPathException illegalContextPathException =
				new IllegalContextPathException(
					"The context path '" + preValidationContextPath +
						"' is not valid URI path syntax.",
					DTOConstants.FAILURE_REASON_VALIDATION_FAILED);

			illegalContextPathException.addSuppressed(uriSyntaxException);

			throw illegalContextPathException;
		}
	}

	private static final String[] _DISPATCHER = {
		DispatcherType.REQUEST.toString()
	};

	private static final Log _log = LogFactoryUtil.getLog(
		ContextController.class.getName());

	private static final Pattern _contextNamePattern = Pattern.compile(
		"^([a-zA-Z_0-9\\-]+\\.)*[a-zA-Z_0-9\\-]+$");

	private final ConcurrentMap<String, HttpSessionAdaptor> _activeSessionsMap =
		new ConcurrentHashMap<>();
	private final BundleContext _consumingBundleContext;
	private final String _contextName;
	private final String _contextPath;
	private final long _contextServiceId;
	private final Set<EndpointRegistration<?>> _endpointRegistrations =
		new ConcurrentSkipListSet<>();
	private final EventListeners _eventListeners = new EventListeners();
	private final Set<FilterRegistration> _filterRegistrations =
		new ConcurrentSkipListSet<>();
	private final ServiceTracker<Filter, AtomicReference<FilterRegistration>>
		_filterServiceTracker;
	private final HttpServiceRuntimeImpl _httpServiceRuntimeImpl;
	private final ServiceTracker
		<EventListener, AtomicReference<ListenerRegistration>>
			_httpSessionAttributeListenerServiceTracker;
	private final ServiceTracker
		<EventListener, AtomicReference<ListenerRegistration>>
			_httpSessionIdListenerServiceTracker;
	private final ServiceTracker
		<EventListener, AtomicReference<ListenerRegistration>>
			_httpSessionListenerServiceTracker;
	private final Map<String, String> _initParamsMap;
	private final Set<ListenerRegistration> _listenerRegistrations =
		new HashSet<>();
	private final ProxyContext _proxyContext;
	private final ServiceTracker<Object, AtomicReference<ResourceRegistration>>
		_resourceServiceTracker;
	private final ServiceTracker
		<EventListener, AtomicReference<ListenerRegistration>>
			_servletContextAttributeListenerServiceTracker;
	private final ServiceReference<ServletContextHelper>
		_servletContextHelperServiceReference;
	private final ServiceTracker
		<EventListener, AtomicReference<ListenerRegistration>>
			_servletContextListenerServiceTracker;
	private final ServiceTracker
		<EventListener, AtomicReference<ListenerRegistration>>
			_servletRequestAttributeListenerServiceTracker;
	private final ServiceTracker
		<EventListener, AtomicReference<ListenerRegistration>>
			_servletRequestListenerServiceTracker;
	private final ServiceTracker<Servlet, AtomicReference<ServletRegistration>>
		_servletServiceTracker;
	private boolean _shutdown;
	private String _string;
	private final BundleContext _trackingBundleContext;

}