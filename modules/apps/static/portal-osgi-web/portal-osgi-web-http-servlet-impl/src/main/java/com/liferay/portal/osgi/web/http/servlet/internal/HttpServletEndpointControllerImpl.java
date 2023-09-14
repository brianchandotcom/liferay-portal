/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletContext;

import org.eclipse.equinox.http.servlet.internal.HttpServletEndpointController;
import org.eclipse.equinox.http.servlet.internal.context.ContextController;
import org.eclipse.equinox.http.servlet.internal.context.DispatchTargets;
import org.eclipse.equinox.http.servlet.internal.context.ProxyContext;
import org.eclipse.equinox.http.servlet.internal.error.IllegalContextNameException;
import org.eclipse.equinox.http.servlet.internal.error.IllegalContextPathException;
import org.eclipse.equinox.http.servlet.internal.servlet.Match;
import org.eclipse.equinox.http.servlet.internal.util.Const;
import org.eclipse.equinox.http.servlet.internal.util.Path;
import org.eclipse.equinox.http.servlet.internal.util.StringPlus;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.runtime.HttpServiceRuntimeConstants;
import org.osgi.service.http.runtime.dto.DTOConstants;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Dante Wang
 */
public class HttpServletEndpointControllerImpl
	implements HttpServletEndpointController {

	public HttpServletEndpointControllerImpl(
		BundleContext bundleContext, ServletContext parentServletContext,
		Map<String, Object> attributesMap) {

		_bundleContext = bundleContext;
		_parentServletContext = parentServletContext;
		_attributesMap = attributesMap;

		String targetFilter =
			"(http.servlet.endpoint.id=" +
				attributesMap.get("http.servlet.endpoint.id") + ")";

		_servletContextHelperServiceTracker = new ServiceTracker<>(
			bundleContext, ServletContextHelper.class,
			new ServletContextHelperServiceTrackerCustomizer());

		_servletContextHelperServiceTracker.open();

		_serviceRegistration = bundleContext.registerService(
			ServletContextHelper.class,
			new DefaultServletContextHelperFactory(),
			HashMapDictionaryBuilder.<String, Object>put(
				Constants.SERVICE_RANKING, Integer.MIN_VALUE
			).put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME,
				HttpWhiteboardConstants.HTTP_WHITEBOARD_DEFAULT_CONTEXT_NAME
			).put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH,
				Const.SLASH
			).put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_TARGET, targetFilter
			).build());
	}

	@Override
	public void destroy() {
		_serviceRegistration.unregister();

		_servletContextHelperServiceTracker.close();
	}

	@Override
	public Collection<ContextController> getContextControllers() {
		return _contextControllersMap.values();
	}

	@Override
	public DispatchTargets getDispatchTargets(String pathString) {
		Path path = new Path(pathString);

		String requestURI = path.getRequestURI();

		List<ContextController> contextControllers = _getContextControllers(
			requestURI);

		if (ListUtil.isEmpty(contextControllers)) {
			return null;
		}

		String queryString = path.getQueryString();

		DispatchTargets dispatchTargets = _getDispatchTargets(
			contextControllers, requestURI, null, queryString, Match.EXACT);

		if (dispatchTargets == null) {
			dispatchTargets = _getDispatchTargets(
				contextControllers, requestURI, path.getExtension(),
				queryString, Match.EXTENSION);
		}

		if (dispatchTargets == null) {
			dispatchTargets = _getDispatchTargets(
				contextControllers, requestURI, null, queryString, Match.REGEX);
		}

		if (dispatchTargets == null) {
			dispatchTargets = _getDispatchTargets(
				contextControllers, requestURI, null, queryString,
				Match.DEFAULT_SERVLET);
		}

		return dispatchTargets;
	}

	@Override
	public List<String> getHttpServiceEndpoints() {
		return StringPlus.from(
			_attributesMap.get(
				HttpServiceRuntimeConstants.HTTP_SERVICE_ENDPOINT));
	}

	@Override
	public ServletContext getParentServletContext() {
		return _parentServletContext;
	}

	@Override
	public Set<Object> getRegisteredObjects() {
		return _registeredObjects;
	}

	@Override
	public void log(String message, Throwable throwable) {
		_log.error(message, throwable);
	}

	@Override
	public boolean matches(ServiceReference<?> serviceReference) {
		String target = (String)serviceReference.getProperty(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_TARGET);

		if (target == null) {
			return true;
		}

		try {
			Filter targetFilter = FrameworkUtil.createFilter(target);

			if (targetFilter.matches(_attributesMap)) {
				return true;
			}
		}
		catch (InvalidSyntaxException invalidSyntaxException) {
			throw new IllegalArgumentException(invalidSyntaxException);
		}

		return false;
	}

	private List<ContextController> _getContextControllers(String requestURI) {
		int index = requestURI.lastIndexOf('/');

		while (true) {
			List<ContextController> contextControllers = new ArrayList<>();

			for (ContextController contextController :
					_contextControllersMap.values()) {

				if (Objects.equals(
						contextController.getContextPath(), requestURI)) {

					contextControllers.add(contextController);
				}
			}

			if (!contextControllers.isEmpty()) {
				return contextControllers;
			}

			if (index == -1) {
				break;
			}

			requestURI = requestURI.substring(0, index);

			index = requestURI.lastIndexOf('/');
		}

		return null;
	}

	private DispatchTargets _getDispatchTargets(
		List<ContextController> contextControllers, String requestURI,
		String extension, String queryString, Match match) {

		ContextController firstContextController = contextControllers.get(0);

		String contextPath = firstContextController.getContextPath();

		requestURI = requestURI.substring(contextPath.length());

		int index = requestURI.lastIndexOf('/');

		String servletPath = requestURI;

		String pathInfo = null;

		if (match == Match.DEFAULT_SERVLET) {
			pathInfo = servletPath;
			servletPath = StringPool.SLASH;
		}

		while (true) {
			for (ContextController contextController : contextControllers) {
				DispatchTargets dispatchTargets =
					contextController.getDispatchTargets(
						null, requestURI, servletPath, pathInfo, extension,
						queryString, match, null);

				if (dispatchTargets != null) {
					return dispatchTargets;
				}
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
		HttpServletEndpointControllerImpl.class.getName());

	private final Map<String, Object> _attributesMap;
	private final BundleContext _bundleContext;
	private final ConcurrentMap
		<ServiceReference<ServletContextHelper>, ContextController>
			_contextControllersMap = new ConcurrentHashMap<>();
	private final ServletContext _parentServletContext;
	private final Set<Object> _registeredObjects = Collections.newSetFromMap(
		new ConcurrentHashMap<>());
	private final ServiceRegistration<ServletContextHelper>
		_serviceRegistration;
	private final ServiceTracker<ServletContextHelper, ContextController>
		_servletContextHelperServiceTracker;

	private static class DefaultServletContextHelper
		extends ServletContextHelper {

		public DefaultServletContextHelper(Bundle bundle) {
			super(bundle);
		}

	}

	private static class DefaultServletContextHelperFactory
		implements ServiceFactory<ServletContextHelper> {

		@Override
		public ServletContextHelper getService(
			Bundle bundle,
			ServiceRegistration<ServletContextHelper> serviceRegistration) {

			return new DefaultServletContextHelper(bundle);
		}

		@Override
		public void ungetService(
			Bundle bundle,
			ServiceRegistration<ServletContextHelper> serviceRegistration,
			ServletContextHelper servletContextHelper) {
		}

	}

	private class ServletContextHelperServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ServletContextHelper, ContextController> {

		@Override
		public ContextController addingService(
			ServiceReference<ServletContextHelper> serviceReference) {

			if (!matches(serviceReference)) {
				return null;
			}

			String contextName = (String)serviceReference.getProperty(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME);
			String contextPath = (String)serviceReference.getProperty(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH);

			try {
				if (contextName == null) {
					throw new IllegalContextNameException(
						HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME +
							" is null. Ignoring!",
						DTOConstants.FAILURE_REASON_VALIDATION_FAILED);
				}

				if (contextPath == null) {
					throw new IllegalContextPathException(
						HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH +
							" is null. Ignoring!",
						DTOConstants.FAILURE_REASON_VALIDATION_FAILED);
				}

				ContextController contextController = new ContextController(
					_bundleContext, _bundleContext, serviceReference,
					new ProxyContext(contextName, _parentServletContext),
					HttpServletEndpointControllerImpl.this, contextName,
					contextPath);

				_contextControllersMap.put(serviceReference, contextController);

				return contextController;
			}
			catch (Exception exception) {
				_log.error(exception);
			}

			return null;
		}

		@Override
		public void modifiedService(
			ServiceReference<ServletContextHelper> serviceReference,
			ContextController contextController) {
		}

		@Override
		public void removedService(
			ServiceReference<ServletContextHelper> serviceReference,
			ContextController contextController) {

			if (contextController != null) {
				contextController.destroy();
			}

			_contextControllersMap.remove(serviceReference);
			_bundleContext.ungetService(serviceReference);
		}

	}

}