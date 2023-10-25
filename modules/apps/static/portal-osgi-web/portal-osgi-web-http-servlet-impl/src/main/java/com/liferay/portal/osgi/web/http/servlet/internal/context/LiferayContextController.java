/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal.context;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
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

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeListener;
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
import org.eclipse.equinox.http.servlet.internal.registration.EndpointRegistration;
import org.eclipse.equinox.http.servlet.internal.registration.FilterRegistration;
import org.eclipse.equinox.http.servlet.internal.registration.ListenerRegistration;
import org.eclipse.equinox.http.servlet.internal.registration.ResourceRegistration;
import org.eclipse.equinox.http.servlet.internal.registration.ServletRegistration;
import org.eclipse.equinox.http.servlet.internal.servlet.HttpSessionAdaptor;
import org.eclipse.equinox.http.servlet.internal.servlet.Match;
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

		return _contextController.addFilterRegistration(serviceReference);
	}

	@Override
	public ListenerRegistration addListenerRegistration(
			ServiceReference<EventListener> serviceReference)
		throws ServletException {

		return _contextController.addListenerRegistration(serviceReference);
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

	private static final String _DEFAULT_CONTEXT_SELECT = StringBundler.concat(
		"(", HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME, "=",
		HttpWhiteboardConstants.HTTP_WHITEBOARD_DEFAULT_CONTEXT_NAME, ")");

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