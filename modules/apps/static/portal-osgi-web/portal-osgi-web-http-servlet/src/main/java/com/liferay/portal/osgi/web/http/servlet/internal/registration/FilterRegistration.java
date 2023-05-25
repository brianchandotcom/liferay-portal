/*******************************************************************************
 * Copyright (c) 2011, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 *******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.internal.registration;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.osgi.web.http.servlet.internal.HttpServiceRuntimeImpl;
import com.liferay.portal.osgi.web.http.servlet.internal.context.ContextController;
import com.liferay.portal.osgi.web.http.servlet.internal.servlet.FilterChainImpl;
import com.liferay.portal.osgi.web.http.servlet.internal.servlet.Match;
import com.liferay.portal.osgi.web.http.servlet.internal.util.Const;

import java.io.IOException;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.http.runtime.dto.FilterDTO;

/**
 * @author IBM Corporation
 * @author Raymond Augé
 */
public class FilterRegistration
	extends MatchableRegistration<Filter, FilterDTO>
	implements Comparable<FilterRegistration> {

	public FilterRegistration(
		ContextController.ServiceHolder<Filter> serviceHolder,
		FilterDTO filterDTO, int priority, ContextController contextController,
		ClassLoader legacyTCCL) {

		super(serviceHolder.get(), filterDTO);

		_serviceHolder = serviceHolder;
		_priority = priority;
		_contextController = contextController;

		_compiledPatterns = _getCompiledRegex(filterDTO);

		if (legacyTCCL != null) {
			_classLoader = legacyTCCL;
		}
		else {
			Bundle bundle = serviceHolder.getBundle();

			BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

			_classLoader = bundleWiring.getClassLoader();
		}

		ServiceReference<Filter> serviceReference =
			serviceHolder.getServiceReference();

		String legacyContextFilter = (String)serviceReference.getProperty(
			Const.EQUINOX_LEGACY_CONTEXT_SELECT);

		if (legacyContextFilter != null) {
			org.osgi.framework.Filter filter = null;

			try {
				filter = FrameworkUtil.createFilter(legacyContextFilter);
			}
			catch (InvalidSyntaxException invalidSyntaxException) {
				if (_log.isDebugEnabled()) {
					_log.debug(invalidSyntaxException);
				}
			}

			_initDestroyWithContextController =
				(filter == null) || contextController.matches(filter);
		}
		else {
			_initDestroyWithContextController = true;
		}
	}

	public boolean appliesTo(FilterChainImpl filterChainImpl) {
		DispatcherType dispatcherType = filterChainImpl.getDispatcherType();

		int result = Arrays.binarySearch(
			getD().dispatcher, dispatcherType.name());

		if (result >= 0) {
			return true;
		}

		return false;
	}

	@Override
	public int compareTo(FilterRegistration otherFilterRegistration) {
		int priorityDifference = _priority - otherFilterRegistration._priority;

		if (priorityDifference != 0) {
			return -priorityDifference;
		}

		FilterDTO otherFilterDTO = otherFilterRegistration.getD();

		return Long.compare(
			Math.abs(getD().serviceId), Math.abs(otherFilterDTO.serviceId));
	}

	@Override
	public void destroy() {
		if (!_initDestroyWithContextController) {
			return;
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);

			HttpServiceRuntimeImpl httpServiceRuntimeImpl =
				_contextController.getHttpServiceRuntime();

			Set<Object> registeredObjects =
				httpServiceRuntimeImpl.getRegisteredObjects();

			registeredObjects.remove(getT());

			Set<FilterRegistration> filterRegistrations =
				_contextController.getFilterRegistrations();

			filterRegistrations.remove(this);

			_contextController.ungetServletContextHelper(
				_serviceHolder.getBundle());

			super.destroy();

			getT().destroy();
		}
		finally {
			_contextController.destroyContextAttributes();

			currentThread.setContextClassLoader(contextClassLoader);

			_serviceHolder.release();
		}
	}

	public void doFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws IOException, ServletException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);

			getT().doFilter(
				httpServletRequest, httpServletResponse, filterChain);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof FilterRegistration)) {
			return false;
		}

		FilterRegistration filterRegistration = (FilterRegistration)object;

		return getT().equals(filterRegistration.getT());
	}

	@Override
	public int hashCode() {
		return Long.hashCode(getD().serviceId);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		if (!_initDestroyWithContextController) {
			return;
		}

		boolean initialized = false;

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);

			_contextController.createContextAttributes();

			getT().init(filterConfig);

			initialized = true;
		}
		finally {
			if (!initialized) {
				_contextController.destroyContextAttributes();
			}

			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public String match(String name, String requestURI, String extension) {
		if ((name != null) && (getD().servletNames != null)) {
			for (String servletName : getD().servletNames) {
				if (servletName.equals(name)) {
					return name;
				}
			}
		}

		if ((requestURI == null) || requestURI.isEmpty()) {
			return null;
		}

		for (String pattern : getD().patterns) {
			if (doPatternMatch(pattern, requestURI, extension)) {
				return pattern;
			}
		}

		for (Pattern pattern : _compiledPatterns) {
			Matcher matcher = pattern.matcher(requestURI);

			if (matcher.matches()) {
				return pattern.toString();
			}
		}

		return null;
	}

	@Override
	public String match(
		String name, String servletPath, String pathInfo, String extension,
		Match match) {

		throw new UnsupportedOperationException(
			"Should not be calling this method on FilterRegistration");
	}

	protected boolean doPatternMatch(
			String pattern, String path, String extension)
		throws IllegalArgumentException {

		if (pattern.indexOf(Const.SLASH_STAR_DOT) == 0) {
			pattern = pattern.substring(1);
		}

		int extensionMatchIndex = pattern.indexOf(Const.SLASH_STAR_DOT);
		String extensionWithPrefixMatch = null;

		if ((extensionMatchIndex >= 0) &&
			(pattern.lastIndexOf('/') == extensionMatchIndex)) {

			extensionWithPrefixMatch = pattern.substring(
				extensionMatchIndex + 3);

			pattern = pattern.substring(0, extensionMatchIndex + 2);
		}

		if (pattern.charAt(0) == '/') {
			if (isPathWildcardMatch(pattern, path)) {
				if (extensionWithPrefixMatch != null) {
					return extensionWithPrefixMatch.equals(extension);
				}

				return true;
			}

			return false;
		}

		if (pattern.charAt(0) == '*') {
			return Objects.equals(pattern.substring(2), extension);
		}

		return false;
	}

	@Override
	protected boolean isPathWildcardMatch(String pattern, String path) {
		if (path == null) {
			return false;
		}

		if (pattern.endsWith("/*")) {
			int pathPatternLength = pattern.length() - 2;

			if (path.regionMatches(0, pattern, 0, pathPatternLength)) {
				if ((path.length() <= pathPatternLength) ||
					(path.charAt(pathPatternLength) == '/')) {

					return true;
				}

				return false;
			}

			return false;
		}

		return pattern.equals(path);
	}

	private Pattern[] _getCompiledRegex(FilterDTO filterDTO) {
		if (filterDTO.regexs == null) {
			return new Pattern[0];
		}

		Pattern[] patterns = new Pattern[filterDTO.regexs.length];

		for (int i = 0; i < filterDTO.regexs.length; i++) {
			patterns[i] = Pattern.compile(filterDTO.regexs[i]);
		}

		return patterns;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FilterRegistration.class.getName());

	private final ClassLoader _classLoader;
	private final Pattern[] _compiledPatterns;
	private final ContextController _contextController;
	private final boolean _initDestroyWithContextController;
	private final int _priority;
	private final ContextController.ServiceHolder<Filter> _serviceHolder;

}