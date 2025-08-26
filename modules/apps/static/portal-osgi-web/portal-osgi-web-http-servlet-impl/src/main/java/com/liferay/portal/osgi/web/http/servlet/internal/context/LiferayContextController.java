/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal.context;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.osgi.web.http.servlet.internal.context.osgi.util.tracker.EventListenerServiceTrackerCustomizer;
import com.liferay.portal.osgi.web.http.servlet.internal.context.osgi.util.tracker.FilterServiceTrackerCustomizer;
import com.liferay.portal.osgi.web.http.servlet.internal.context.osgi.util.tracker.ResourceServiceTrackerCustomizer;
import com.liferay.portal.osgi.web.http.servlet.internal.context.osgi.util.tracker.ServletServiceTrackerCustomizer;

import jakarta.servlet.Filter;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextAttributeListener;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequestAttributeListener;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

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

import org.eclipse.equinox.http.servlet.internal.HttpServletEndpointController;
import org.eclipse.equinox.http.servlet.internal.context.ContextController;
import org.eclipse.equinox.http.servlet.internal.context.ServletContextHelperDataContext;
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
					"The context path \"" + contextPath + "\" is invalid",
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

		_servletContextHelperServiceId = (long)serviceReference.getProperty(
			Constants.SERVICE_ID);
		_servletContextInitParams = ServiceProperties.parseInitParams(
			serviceReference,
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_INIT_PARAM_PREFIX,
			servletContextHelperDataContext.getServletContext());

		_filterServiceTracker = new ServiceTracker<>(
			bundleContext, Filter.class,
			new FilterServiceTrackerCustomizer(
				bundleContext, httpServletEndpointController, this,
				_servletContextHelperDataContext,
				_servletContextHelperServiceId));

		_filterServiceTracker.open(true);

		_httpSessionAttributeListenerServiceTracker = new ServiceTracker<>(
			bundleContext, HttpSessionAttributeListener.class.getName(),
			new EventListenerServiceTrackerCustomizer(
				bundleContext, httpServletEndpointController, this,
				_servletContextHelperDataContext,
				_servletContextHelperServiceId));

		_httpSessionAttributeListenerServiceTracker.open(true);

		_httpSessionListenerServiceTracker = new ServiceTracker<>(
			bundleContext, HttpSessionListener.class.getName(),
			new EventListenerServiceTrackerCustomizer(
				bundleContext, httpServletEndpointController, this,
				_servletContextHelperDataContext,
				_servletContextHelperServiceId));

		_httpSessionListenerServiceTracker.open(true);

		_resourceServiceTracker = new ServiceTracker<>(
			bundleContext, Object.class,
			new ResourceServiceTrackerCustomizer(
				bundleContext, httpServletEndpointController, this,
				_servletContextHelperDataContext,
				_servletContextHelperServiceId));

		_resourceServiceTracker.open(true);

		_servletContextAttributeListenerServiceTracker = new ServiceTracker<>(
			bundleContext, ServletContextAttributeListener.class.getName(),
			new EventListenerServiceTrackerCustomizer(
				bundleContext, httpServletEndpointController, this,
				_servletContextHelperDataContext,
				_servletContextHelperServiceId));

		_servletContextAttributeListenerServiceTracker.open(true);

		_servletContextListenerServiceTracker = new ServiceTracker<>(
			bundleContext, ServletContextListener.class.getName(),
			new EventListenerServiceTrackerCustomizer(
				bundleContext, httpServletEndpointController, this,
				_servletContextHelperDataContext,
				_servletContextHelperServiceId));

		_servletContextListenerServiceTracker.open(true);

		_servletRequestAttributeListenerServiceTracker = new ServiceTracker<>(
			bundleContext, ServletRequestAttributeListener.class.getName(),
			new EventListenerServiceTrackerCustomizer(
				bundleContext, httpServletEndpointController, this,
				_servletContextHelperDataContext,
				_servletContextHelperServiceId));

		_servletRequestAttributeListenerServiceTracker.open(true);

		_servletRequestListenerServiceTracker = new ServiceTracker<>(
			bundleContext, ServletRequestListener.class.getName(),
			new EventListenerServiceTrackerCustomizer(
				bundleContext, httpServletEndpointController, this,
				_servletContextHelperDataContext,
				_servletContextHelperServiceId));

		_servletRequestListenerServiceTracker.open(true);

		_servletServiceTracker = new ServiceTracker<>(
			bundleContext, Servlet.class,
			new ServletServiceTrackerCustomizer(
				bundleContext, httpServletEndpointController, this,
				_servletContextHelperDataContext,
				_servletContextHelperServiceId));

		_servletServiceTracker.open(true);
	}

	@Override
	public FilterRegistration addFilterRegistration(
			ServiceReference<Filter> serviceReference)
		throws ServletException {

		throw new UnsupportedOperationException();
	}

	@Override
	public ListenerRegistration addListenerRegistration(
		ServiceReference<EventListener> serviceReference) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ResourceRegistration addResourceRegistration(
		ServiceReference<?> serviceReference) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ServletRegistration addServletRegistration(
			ServiceReference<Servlet> serviceReference)
		throws ServletException {

		throw new UnsupportedOperationException();
	}

	public void checkShutdown() {
		if (_shutdown) {
			throw new IllegalStateException("Context is shutdown");
		}
	}

	@Override
	public void destroy() {
		Collection<HttpSessionAdaptor> httpSessionAdaptors =
			_activeHttpSessionAdaptors.values();

		Iterator<HttpSessionAdaptor> iterator = httpSessionAdaptors.iterator();

		while (iterator.hasNext()) {
			HttpSessionAdaptor httpSessionAdaptor = iterator.next();

			httpSessionAdaptor.invalidate();

			iterator.remove();
		}

		_filterServiceTracker.close();
		_httpSessionAttributeListenerServiceTracker.close();
		_httpSessionListenerServiceTracker.close();
		_resourceServiceTracker.close();
		_servletContextAttributeListenerServiceTracker.close();
		_servletContextListenerServiceTracker.close();
		_servletRequestAttributeListenerServiceTracker.close();
		_servletRequestListenerServiceTracker.close();
		_servletServiceTracker.close();

		_endpointRegistrations.clear();
		_eventListeners.clear();
		_filterRegistrations.clear();
		_listenerRegistrations.clear();
		_servletContextHelperDataContext.destroy();

		_shutdown = true;
	}

	@Override
	public Map<String, HttpSessionAdaptor> getActiveSessions() {
		return _activeHttpSessionAdaptors;
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
	public LiferayDispatchTargets getDispatchTargets(String pathString) {
		Path path = new Path(pathString);

		String queryString = path.getQueryString();
		String requestURI = path.getRequestURI();

		LiferayDispatchTargets liferayDispatchTargets =
			_getLiferayDispatchTargets(
				requestURI, null, queryString, Match.EXACT);

		if (liferayDispatchTargets == null) {
			liferayDispatchTargets = _getLiferayDispatchTargets(
				requestURI, path.getExtension(), queryString, Match.EXTENSION);
		}

		if (liferayDispatchTargets == null) {
			liferayDispatchTargets = _getLiferayDispatchTargets(
				requestURI, null, queryString, Match.REGEX);
		}

		if (liferayDispatchTargets == null) {
			liferayDispatchTargets = _getLiferayDispatchTargets(
				requestURI, null, queryString, Match.DEFAULT_SERVLET);
		}

		return liferayDispatchTargets;
	}

	@Override
	public LiferayDispatchTargets getDispatchTargets(
		String servletName, String requestURI, String servletPath,
		String pathInfo, String extension, String queryString, Match match) {

		checkShutdown();

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
			return new LiferayDispatchTargets(
				endpointRegistration, this, pathInfo, queryString, requestURI,
				servletName, servletPath);
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

		return new LiferayDispatchTargets(
			endpointRegistration, this, matchingFilterRegistrations, pathInfo,
			queryString, requestURI, servletName, servletPath);
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
		return _servletContextInitParams;
	}

	@Override
	public Set<ListenerRegistration> getListenerRegistrations() {
		return _listenerRegistrations;
	}

	public ServletContextHelper getServletContextHelper(Bundle bundle) {
		BundleContext bundleContext = bundle.getBundleContext();

		return bundleContext.getService(_serviceReference);
	}

	@Override
	public HttpSessionAdaptor getSessionAdaptor(
		HttpSession httpSession, ServletContext servletContext) {

		String sessionId = httpSession.getId();

		HttpSessionAdaptor httpSessionAdaptor = _activeHttpSessionAdaptors.get(
			sessionId);

		if (httpSessionAdaptor != null) {
			return httpSessionAdaptor;
		}

		httpSessionAdaptor = HttpSessionAdaptor.createHttpSessionAdaptor(
			httpSession, servletContext, this);

		HttpSessionAdaptor previousHttpSessionAdaptor =
			_activeHttpSessionAdaptors.putIfAbsent(
				sessionId, httpSessionAdaptor);

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

	@Override
	public boolean matches(org.osgi.framework.Filter osgiFilter) {
		return osgiFilter.match(_serviceReference);
	}

	@Override
	public boolean matches(ServiceReference<?> serviceReference) {
		String contextSelect = (String)serviceReference.getProperty(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT);

		if (contextSelect == null) {
			return _contextName.equals(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_DEFAULT_CONTEXT_NAME);
		}

		if (_contextName.equals(contextSelect)) {
			return true;
		}

		if (!contextSelect.startsWith(Const.OPEN_PAREN)) {
			return false;
		}

		try {
			return matches(FrameworkUtil.createFilter(contextSelect));
		}
		catch (InvalidSyntaxException invalidSyntaxException) {
			throw new IllegalArgumentException(invalidSyntaxException);
		}
	}

	@Override
	public void removeActiveSession(String sessionId) {
		_activeHttpSessionAdaptors.remove(sessionId);
	}

	@Override
	public void ungetServletContextHelper(Bundle bundle) {
		BundleContext bundleContext = bundle.getBundleContext();

		try {
			bundleContext.ungetService(_serviceReference);
		}
		catch (IllegalStateException illegalStateException) {
			if (_log.isDebugEnabled()) {
				_log.debug(illegalStateException);
			}
		}
	}

	private LiferayDispatchTargets _getLiferayDispatchTargets(
		String requestURI, String extension, String queryString, Match match) {

		int index = requestURI.lastIndexOf('/');

		String servletPath = requestURI;

		String pathInfo = null;

		if (match == Match.DEFAULT_SERVLET) {
			pathInfo = servletPath;
			servletPath = Const.SLASH;
		}

		while (true) {
			LiferayDispatchTargets liferayDispatchTargets = getDispatchTargets(
				null, requestURI, servletPath, pathInfo, extension, queryString,
				match);

			if (liferayDispatchTargets != null) {
				return liferayDispatchTargets;
			}

			if ((match == Match.EXACT) || (index == -1)) {
				break;
			}

			servletPath = requestURI.substring(0, index);

			pathInfo = requestURI.substring(index);

			index = servletPath.lastIndexOf('/');
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayContextController.class.getName());

	private static final Pattern _contextNamePattern = Pattern.compile(
		"^([a-zA-Z_0-9\\-]+\\.)*[a-zA-Z_0-9\\-]+$");

	private final ConcurrentMap<String, HttpSessionAdaptor>
		_activeHttpSessionAdaptors = new ConcurrentHashMap<>();
	private final BundleContext _bundleContext;
	private final String _contextName;
	private final String _contextPath;
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
			_httpSessionListenerServiceTracker;
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
	private final long _servletContextHelperServiceId;
	private final Map<String, String> _servletContextInitParams;
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