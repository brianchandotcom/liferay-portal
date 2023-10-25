/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal.context;

import com.liferay.osgi.util.StringPlus;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.net.URI;
import java.net.URISyntaxException;

import java.security.AccessController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Iterator;
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

import org.eclipse.equinox.http.servlet.internal.HttpServletEndpointController;
import org.eclipse.equinox.http.servlet.internal.context.ContextController;
import org.eclipse.equinox.http.servlet.internal.context.DispatchTargets;
import org.eclipse.equinox.http.servlet.internal.context.ServletContextHelperDataContext;
import org.eclipse.equinox.http.servlet.internal.customizer.ContextFilterTrackerCustomizer;
import org.eclipse.equinox.http.servlet.internal.customizer.ContextListenerTrackerCustomizer;
import org.eclipse.equinox.http.servlet.internal.customizer.ContextResourceTrackerCustomizer;
import org.eclipse.equinox.http.servlet.internal.customizer.ContextServletTrackerCustomizer;
import org.eclipse.equinox.http.servlet.internal.error.IllegalContextNameException;
import org.eclipse.equinox.http.servlet.internal.error.IllegalContextPathException;
import org.eclipse.equinox.http.servlet.internal.error.RegisteredFilterException;
import org.eclipse.equinox.http.servlet.internal.registration.EndpointRegistration;
import org.eclipse.equinox.http.servlet.internal.registration.FilterRegistration;
import org.eclipse.equinox.http.servlet.internal.registration.ListenerRegistration;
import org.eclipse.equinox.http.servlet.internal.registration.ResourceRegistration;
import org.eclipse.equinox.http.servlet.internal.registration.ServletRegistration;
import org.eclipse.equinox.http.servlet.internal.servlet.FilterConfigImpl;
import org.eclipse.equinox.http.servlet.internal.servlet.HttpSessionAdaptor;
import org.eclipse.equinox.http.servlet.internal.servlet.Match;
import org.eclipse.equinox.http.servlet.internal.servlet.ServletContextAdaptor;
import org.eclipse.equinox.http.servlet.internal.util.Const;
import org.eclipse.equinox.http.servlet.internal.util.EventListeners;
import org.eclipse.equinox.http.servlet.internal.util.Path;
import org.eclipse.equinox.http.servlet.internal.util.ServiceProperties;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.runtime.dto.DTOConstants;
import org.osgi.service.http.runtime.dto.FilterDTO;
import org.osgi.service.http.runtime.dto.ListenerDTO;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Dante Wang
 */
public class LiferayContextController extends ContextController {

	public LiferayContextController(
		BundleContext bundleContext,
		ServiceReference<ServletContextHelper> serviceReference,
		ServletContextHelperDataContext servletContextHelperDataContext,
		HttpServletEndpointController httpServletEndpointController,
		String contextName, String contextPath) {

		Matcher matcher = _contextNamePattern.matcher(contextName);

		if (!matcher.matches()) {
			throw new IllegalContextNameException(
				"The context name '" + contextName +
					"' does not follow Bundle-SymbolicName syntax",
				DTOConstants.FAILURE_REASON_VALIDATION_FAILED);
		}

		try {
			new URI(Const.HTTP, Const.LOCALHOST, contextPath, null);
		}
		catch (URISyntaxException uriSyntaxException) {
			IllegalContextPathException illegalContextPathException =
				new IllegalContextPathException(
					"The context path '" + contextPath +
						"' is not valid URI path syntax",
					DTOConstants.FAILURE_REASON_VALIDATION_FAILED);

			illegalContextPathException.addSuppressed(uriSyntaxException);

			throw illegalContextPathException;
		}

		_bundleContext = bundleContext;
		_serviceReference = serviceReference;
		_servletContextHelperDataContext = servletContextHelperDataContext;
		_httpServletEndpointController = httpServletEndpointController;
		_contextName = contextName;

		if (contextPath.equals(Const.SLASH)) {
			contextPath = Const.BLANK;
		}

		_contextPath = contextPath;

		_contextServiceId = (long)serviceReference.getProperty(
			Constants.SERVICE_ID);

		_initParams = ServiceProperties.parseInitParams(
			serviceReference,
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_INIT_PARAM_PREFIX,
			servletContextHelperDataContext.getServletContext());

		_servletContextListenerServiceTracker = new ServiceTracker<>(
			bundleContext, ServletContextListener.class.getName(),
			new ContextListenerTrackerCustomizer(
				bundleContext, httpServletEndpointController, this));

		_servletContextListenerServiceTracker.open();

		_servletContextAttributeListenerServiceTracker = new ServiceTracker<>(
			bundleContext, ServletContextAttributeListener.class.getName(),
			new ContextListenerTrackerCustomizer(
				bundleContext, httpServletEndpointController, this));

		_servletContextAttributeListenerServiceTracker.open();

		_servletRequestListenerServiceTracker = new ServiceTracker<>(
			bundleContext, ServletRequestListener.class.getName(),
			new ContextListenerTrackerCustomizer(
				bundleContext, httpServletEndpointController, this));

		_servletRequestListenerServiceTracker.open();

		_servletRequestAttributeListenerServiceTracker = new ServiceTracker<>(
			bundleContext, ServletRequestAttributeListener.class.getName(),
			new ContextListenerTrackerCustomizer(
				bundleContext, httpServletEndpointController, this));

		_servletRequestAttributeListenerServiceTracker.open();

		_httpSessionListenerServiceTracker = new ServiceTracker<>(
			bundleContext, HttpSessionListener.class.getName(),
			new ContextListenerTrackerCustomizer(
				bundleContext, httpServletEndpointController, this));

		_httpSessionListenerServiceTracker.open();

		_httpSessionAttributeListenerServiceTracker = new ServiceTracker<>(
			bundleContext, HttpSessionAttributeListener.class.getName(),
			new ContextListenerTrackerCustomizer(
				bundleContext, httpServletEndpointController, this));

		_httpSessionAttributeListenerServiceTracker.open();

		ServletContext servletContext =
			httpServletEndpointController.getParentServletContext();

		if ((servletContext.getMajorVersion() >= 3) &&
			(servletContext.getMinorVersion() > 0)) {

			_httpSessionIdListenerServiceTracker = new ServiceTracker<>(
				bundleContext, HttpSessionIdListener.class.getName(),
				new ContextListenerTrackerCustomizer(
					bundleContext, httpServletEndpointController, this));

			_httpSessionIdListenerServiceTracker.open();
		}
		else {
			_httpSessionIdListenerServiceTracker = null;
		}

		_filterServiceTracker = new ServiceTracker<>(
			bundleContext, Filter.class,
			new ContextFilterTrackerCustomizer(
				bundleContext, httpServletEndpointController, this));

		_filterServiceTracker.open();

		_servletServiceTracker = new ServiceTracker<>(
			bundleContext, Servlet.class,
			new ContextServletTrackerCustomizer(
				bundleContext, httpServletEndpointController, this));

		_servletServiceTracker.open();

		_resourceServiceTracker = new ServiceTracker<>(
			bundleContext, Object.class,
			new ContextResourceTrackerCustomizer(
				bundleContext, httpServletEndpointController, this));

		_resourceServiceTracker.open();
	}

	@Override
	public FilterRegistration addFilterRegistration(
			ServiceReference<Filter> serviceReference)
		throws ServletException {

		_checkShutdown();

		ContextController.ServiceHolder<Filter> filterServiceHolder =
			new ContextController.ServiceHolder<>(
				_bundleContext.getServiceObjects(serviceReference));

		Filter filter = filterServiceHolder.get();

		FilterRegistration filterRegistration = null;

		boolean addedRegisteredObject = false;

		Set<Object> registeredObjects =
			_httpServletEndpointController.getRegisteredObjects();

		try {
			if (filter == null) {
				throw new IllegalArgumentException("Filter can not be null");
			}

			addedRegisteredObject = registeredObjects.add(filter);

			if (addedRegisteredObject) {
				for (FilterRegistration curFilterRegistration :
						_filterRegistrations) {

					if (Objects.equals(curFilterRegistration.getT(), filter)) {
						throw new RegisteredFilterException(filter);
					}
				}

				FilterDTO filterDTO = _createFilterDTO(
					serviceReference, filter);

				filterRegistration = new FilterRegistration(
					filterServiceHolder, filterDTO,
					GetterUtil.getInteger(
						serviceReference.getProperty(
							Constants.SERVICE_RANKING)),
					this, null);

				filterRegistration.init(
					new FilterConfigImpl(
						filterDTO.name, filterDTO.initParams,
						_createServletContext(
							filterServiceHolder.getBundle(),
							_getServletContextHelper(
								filterServiceHolder.getBundle()))));

				_filterRegistrations.add(filterRegistration);
			}
		}
		finally {
			if (filterRegistration == null) {
				filterServiceHolder.release();

				if (addedRegisteredObject) {
					registeredObjects.remove(filter);
				}
			}
		}

		return filterRegistration;
	}

	@Override
	public ListenerRegistration addListenerRegistration(
		ServiceReference<EventListener> serviceReference) {

		_checkShutdown();

		ContextController.ServiceHolder<EventListener>
			eventListenerServiceHolder = new ContextController.ServiceHolder<>(
				_bundleContext.getServiceObjects(serviceReference));

		EventListener eventListener = eventListenerServiceHolder.get();

		ListenerRegistration listenerRegistration = null;

		try {
			if (eventListener == null) {
				throw new IllegalArgumentException(
					"EventListener can not be null");
			}

			List<Class<? extends EventListener>> listenerClasses =
				_getListenerClasses(serviceReference);

			if (listenerClasses.isEmpty()) {
				throw new IllegalArgumentException(
					"EventListener does not implement a supported type");
			}

			for (ListenerRegistration curListenerRegistration :
					_listenerRegistrations) {

				if (Objects.equals(
						curListenerRegistration.getT(), eventListener)) {

					return null;
				}
			}

			ServletContext servletContext = _createServletContext(
				eventListenerServiceHolder.getBundle(),
				_getServletContextHelper(
					eventListenerServiceHolder.getBundle()));

			listenerRegistration = new ListenerRegistration(
				eventListenerServiceHolder, listenerClasses,
				_createListenerDTO(serviceReference, listenerClasses),
				servletContext, this);

			if (listenerClasses.contains(ServletContextListener.class)) {
				ServletContextListener servletContextListener =
					(ServletContextListener)listenerRegistration.getT();

				servletContextListener.contextInitialized(
					new ServletContextEvent(servletContext));
			}

			_listenerRegistrations.add(listenerRegistration);

			_eventListeners.put(listenerClasses, listenerRegistration);
		}
		finally {
			if (listenerRegistration == null) {
				eventListenerServiceHolder.release();
			}
		}

		return listenerRegistration;
	}

	@Override
	public ResourceRegistration addResourceRegistration(
		ServiceReference<?> serviceReference) {

		return _contextController.addResourceRegistration(serviceReference);
	}

	@Override
	public ServletRegistration addServletRegistration(
			ServiceReference<Servlet> serviceReference)
		throws ServletException {

		return _contextController.addServletRegistration(serviceReference);
	}

	@Override
	public void destroy() {
		Collection<HttpSessionAdaptor> httpSessionAdaptors =
			_activeSessions.values();

		Iterator<HttpSessionAdaptor> iterator = httpSessionAdaptors.iterator();

		while (iterator.hasNext()) {
			HttpSessionAdaptor httpSessionAdaptor = iterator.next();

			httpSessionAdaptor.invalidate();

			iterator.remove();
		}

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
		_servletContextHelperDataContext.destroy();

		_shutdown = true;
	}

	@Override
	public Map<String, HttpSessionAdaptor> getActiveSessions() {
		return _activeSessions;
	}

	@Override
	public String getContextName() {
		return _contextName;
	}

	@Override
	public String getContextPath() {
		return _contextPath;
	}

	@Override
	public DispatchTargets getDispatchTargets(String pathString) {
		Path path = new Path(pathString);

		String queryString = path.getQueryString();
		String requestURI = path.getRequestURI();

		DispatchTargets dispatchTargets = _getDispatchTargets(
			requestURI, null, queryString, Match.EXACT);

		if (dispatchTargets == null) {
			dispatchTargets = _getDispatchTargets(
				requestURI, path.getExtension(), queryString, Match.EXTENSION);
		}

		if (dispatchTargets == null) {
			dispatchTargets = _getDispatchTargets(
				requestURI, null, queryString, Match.REGEX);
		}

		if (dispatchTargets == null) {
			dispatchTargets = _getDispatchTargets(
				requestURI, null, queryString, Match.DEFAULT_SERVLET);
		}

		return dispatchTargets;
	}

	@Override
	public DispatchTargets getDispatchTargets(
		String servletName, String requestURI, String servletPath,
		String pathInfo, String extension, String queryString, Match match) {

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

		if (_filterRegistrations.isEmpty()) {
			return new DispatchTargets(
				this, endpointRegistration, servletName, requestURI,
				servletPath, pathInfo, queryString);
		}

		if (requestURI != null) {
			int index = requestURI.lastIndexOf('.');

			if (index != -1) {
				extension = requestURI.substring(index + 1);
			}
		}

		List<FilterRegistration> matchingFilterRegistrations =
			new ArrayList<>();

		String endpointRegistrationName = endpointRegistration.getName();

		for (FilterRegistration filterRegistration : _filterRegistrations) {
			if (Objects.nonNull(
					filterRegistration.match(
						endpointRegistrationName, requestURI, extension,
						null)) &&
				!matchingFilterRegistrations.contains(filterRegistration)) {

				matchingFilterRegistrations.add(filterRegistration);
			}
		}

		return new DispatchTargets(
			this, endpointRegistration, matchingFilterRegistrations,
			servletName, requestURI, servletPath, pathInfo, queryString);
	}

	@Override
	public Set<EndpointRegistration<?>> getEndpointRegistrations() {
		return _endpointRegistrations;
	}

	@Override
	public EventListeners getEventListeners() {
		return _eventListeners;
	}

	@Override
	public Set<FilterRegistration> getFilterRegistrations() {
		return _filterRegistrations;
	}

	@Override
	public String getFullContextPath() {
		List<String> httpServiceEndpoints =
			_httpServletEndpointController.getHttpServiceEndpoints();

		if (httpServiceEndpoints.isEmpty()) {
			return _contextPath;
		}

		String defaultHttpServiceEndpoint = httpServiceEndpoints.get(0);

		if (defaultHttpServiceEndpoint.endsWith("/")) {
			defaultHttpServiceEndpoint = defaultHttpServiceEndpoint.substring(
				0, defaultHttpServiceEndpoint.length() - 1);
		}

		return defaultHttpServiceEndpoint.concat(_contextPath);
	}

	@Override
	public HttpServletEndpointController getHttpServletEndpointController() {
		return _httpServletEndpointController;
	}

	@Override
	public Map<String, String> getInitParams() {
		return _initParams;
	}

	@Override
	public Set<ListenerRegistration> getListenerRegistrations() {
		return _listenerRegistrations;
	}

	@Override
	public HttpSessionAdaptor getSessionAdaptor(
		HttpSession httpSession, ServletContext servletContext) {

		String sessionId = httpSession.getId();

		HttpSessionAdaptor httpSessionAdaptor = _activeSessions.get(sessionId);

		if (httpSessionAdaptor != null) {
			return httpSessionAdaptor;
		}

		httpSessionAdaptor = HttpSessionAdaptor.createHttpSessionAdaptor(
			httpSession, servletContext, this);

		HttpSessionAdaptor previousHttpSessionAdaptor =
			_activeSessions.putIfAbsent(sessionId, httpSessionAdaptor);

		if (previousHttpSessionAdaptor != null) {
			return previousHttpSessionAdaptor;
		}

		List<HttpSessionListener> listeners = _eventListeners.get(
			HttpSessionListener.class);

		if (listeners.isEmpty()) {
			return httpSessionAdaptor;
		}

		HttpSessionEvent httpSessionEvent = new HttpSessionEvent(
			httpSessionAdaptor);

		for (HttpSessionListener listener : listeners) {
			listener.sessionCreated(httpSessionEvent);
		}

		return httpSessionAdaptor;
	}

	@Override
	public boolean matches(org.osgi.framework.Filter filter) {
		return filter.match(_serviceReference);
	}

	@Override
	public boolean matches(ServiceReference<?> serviceReference) {
		String contextSelect = (String)serviceReference.getProperty(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT);

		if (_contextName.equals(contextSelect)) {
			return true;
		}

		if (contextSelect == null) {
			contextSelect = _DEFAULT_CONTEXT_SELECT;
		}

		if (!contextSelect.startsWith(Const.OPEN_PAREN)) {
			return false;
		}

		org.osgi.framework.Filter targetFilter = null;

		try {
			targetFilter = FrameworkUtil.createFilter(contextSelect);
		}
		catch (InvalidSyntaxException invalidSyntaxException) {
			throw new IllegalArgumentException(invalidSyntaxException);
		}

		return matches(targetFilter);
	}

	@Override
	public void removeActiveSession(String sessionId) {
		_activeSessions.remove(sessionId);
	}

	@Override
	public void ungetServletContextHelper(Bundle bundle) {
		BundleContext bundleContext = bundle.getBundleContext();

		try {
			bundleContext.ungetService(_serviceReference);
		}
		catch (IllegalStateException illegalStateException) {

			// this can happen if the whiteboard bundle is in the process of
			// stopping and the framework is in the middle of auto-unregistering
			// any services the bundle forgot to unregister on stop

			if (_log.isDebugEnabled()) {
				_log.debug(illegalStateException);
			}
		}
	}

	private void _checkShutdown() {
		if (_shutdown) {
			throw new IllegalStateException("Context is shutdown");
		}
	}

	private FilterDTO _createFilterDTO(
		ServiceReference<Filter> filterServiceReference, Filter filter) {

		String[] patterns = ArrayUtil.toStringArray(
			StringPlus.asList(
				filterServiceReference.getProperty(
					HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN)));

		String[] regexes = ArrayUtil.toStringArray(
			StringPlus.asList(
				filterServiceReference.getProperty(
					HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_REGEX)));

		String[] servletNames = ArrayUtil.toStringArray(
			StringPlus.asList(
				filterServiceReference.getProperty(
					HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_SERVLET)));

		if ((patterns.length == 0) && (regexes.length == 0) &&
			(servletNames.length == 0)) {

			throw new IllegalArgumentException(
				"Patterns, regex or servletNames must contain a value");
		}

		for (String pattern : patterns) {
			ContextController.checkPattern(pattern);
		}

		String[] dispatchers = ArrayUtil.toStringArray(
			StringPlus.asList(
				filterServiceReference.getProperty(
					HttpWhiteboardConstants.
						HTTP_WHITEBOARD_FILTER_DISPATCHER)));

		if (dispatchers.length == 0) {
			dispatchers = _DEFAULT_DISPATCHERS;
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

		Class<?> clazz = filter.getClass();

		FilterDTO filterDTO = new FilterDTO();

		filterDTO.asyncSupported = ServiceProperties.parseBoolean(
			filterServiceReference,
			HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_ASYNC_SUPPORTED);
		filterDTO.dispatcher = _sort(dispatchers);
		filterDTO.initParams = ServiceProperties.parseInitParams(
			filterServiceReference,
			HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_INIT_PARAM_PREFIX);
		filterDTO.name = GetterUtil.getString(
			ServiceProperties.parseName(
				filterServiceReference.getProperty(
					HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_NAME),
				filter),
			clazz.getName());
		filterDTO.patterns = _sort(patterns);
		filterDTO.regexs = regexes;
		filterDTO.serviceId = (long)filterServiceReference.getProperty(
			Constants.SERVICE_ID);
		filterDTO.servletContextId = _contextServiceId;
		filterDTO.servletNames = _sort(servletNames);

		return filterDTO;
	}

	private ListenerDTO _createListenerDTO(
		ServiceReference<EventListener> serviceReference,
		List<Class<? extends EventListener>> listenerClasses) {

		ListenerDTO listenerDTO = new ListenerDTO();

		listenerDTO.serviceId = (long)serviceReference.getProperty(
			Constants.SERVICE_ID);
		listenerDTO.servletContextId = _contextServiceId;
		listenerDTO.types = TransformUtil.transformToArray(
			listenerClasses, Class::getName, String.class);

		return listenerDTO;
	}

	private ServletContext _createServletContext(
		Bundle bundle, ServletContextHelper servletContextHelper) {

		ServletContextAdaptor servletContextAdaptor = new ServletContextAdaptor(
			this, bundle, servletContextHelper,
			_servletContextHelperDataContext, _eventListeners,
			AccessController.getContext());

		return servletContextAdaptor.createServletContext();
	}

	private DispatchTargets _getDispatchTargets(
		String requestURI, String extension, String queryString, Match match) {

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
				match);

			if (dispatchTargets != null) {
				return dispatchTargets;
			}

			if (match == Match.EXACT) {
				break;
			}

			if (pos > -1) {
				String newServletPath = requestURI.substring(0, pos);
				pathInfo = requestURI.substring(pos);

				servletPath = newServletPath;

				pos = servletPath.lastIndexOf('/');

				continue;
			}

			break;
		}

		return null;
	}

	private List<Class<? extends EventListener>> _getListenerClasses(
		ServiceReference<EventListener> serviceReference) {

		List<String> objectClassList = StringPlus.asList(
			serviceReference.getProperty(Constants.OBJECTCLASS));

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

		ServletContext servletContext =
			_servletContextHelperDataContext.getServletContext();

		if ((servletContext.getMajorVersion() >= 3) &&
			(servletContext.getMinorVersion() > 0) &&
			objectClassList.contains(HttpSessionIdListener.class.getName())) {

			classes.add(HttpSessionIdListener.class);
		}

		return classes;
	}

	private ServletContextHelper _getServletContextHelper(Bundle bundle) {
		BundleContext bundleContext = bundle.getBundleContext();

		return bundleContext.getService(_serviceReference);
	}

	private String[] _sort(String[] values) {
		if (values == null) {
			return null;
		}

		Arrays.sort(values);

		return values;
	}

	private static final String _DEFAULT_CONTEXT_SELECT = StringBundler.concat(
		"(", HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME, "=",
		HttpWhiteboardConstants.HTTP_WHITEBOARD_DEFAULT_CONTEXT_NAME, ")");

	private static final String[] _DEFAULT_DISPATCHERS = {
		DispatcherType.REQUEST.toString()
	};

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayContextController.class.getName());

	private static final Pattern _contextNamePattern = Pattern.compile(
		"^([a-zA-Z_0-9\\-]+\\.)*[a-zA-Z_0-9\\-]+$");

	private final ConcurrentMap<String, HttpSessionAdaptor> _activeSessions =
		new ConcurrentHashMap<>();
	private final BundleContext _bundleContext;
	private final ContextController _contextController;
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
	private final HttpServletEndpointController _httpServletEndpointController;
	private final ServiceTracker
		<EventListener, AtomicReference<ListenerRegistration>>
			_httpSessionAttributeListenerServiceTracker;
	private final ServiceTracker
		<EventListener, AtomicReference<ListenerRegistration>>
			_httpSessionIdListenerServiceTracker;
	private final ServiceTracker
		<EventListener, AtomicReference<ListenerRegistration>>
			_httpSessionListenerServiceTracker;
	private final Map<String, String> _initParams;
	private final Set<ListenerRegistration> _listenerRegistrations =
		new HashSet<>();
	private final ServiceTracker<Object, AtomicReference<ResourceRegistration>>
		_resourceServiceTracker;
	private final ServiceReference<ServletContextHelper> _serviceReference;
	private final ServiceTracker
		<EventListener, AtomicReference<ListenerRegistration>>
			_servletContextAttributeListenerServiceTracker;
	private final ServletContextHelperDataContext
		_servletContextHelperDataContext;
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

}