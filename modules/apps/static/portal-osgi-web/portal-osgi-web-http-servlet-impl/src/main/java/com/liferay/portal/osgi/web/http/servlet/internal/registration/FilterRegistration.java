/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal.registration;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.lang.ThreadContextClassLoaderUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.equinox.http.servlet.internal.HttpServletEndpointController;
import org.eclipse.equinox.http.servlet.internal.context.ContextController;
import org.eclipse.equinox.http.servlet.internal.servlet.FilterChainImpl;
import org.eclipse.equinox.http.servlet.internal.servlet.Match;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.http.runtime.dto.FilterDTO;

/**
 * @author Dante Wang
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

		_patterns = _getPatterns(filterDTO);

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
			"equinox.context.select");

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
		FilterDTO filterDTO = getD();

		DispatcherType dispatcherType = filterChainImpl.getDispatcherType();

		if (Arrays.binarySearch(filterDTO.dispatcher, dispatcherType.name()) >=
				0) {

			return true;
		}

		return false;
	}

	@Override
	public int compareTo(FilterRegistration filterRegistration) {
		int priorityDifference = _priority - filterRegistration._priority;

		if (priorityDifference != 0) {
			return -priorityDifference;
		}

		FilterDTO filterDTO = getD();

		FilterDTO otherFilterDTO = filterRegistration.getD();

		return Long.compare(
			Math.abs(filterDTO.serviceId), Math.abs(otherFilterDTO.serviceId));
	}

	@Override
	public void destroy() {
		if (_initDestroyWithContextController) {
			try (SafeCloseable safeCloseable =
					ThreadContextClassLoaderUtil.swap(_classLoader)) {

				HttpServletEndpointController httpServletEndpointController =
					_contextController.getHttpServletEndpointController();

				Set<Object> registeredObjects =
					httpServletEndpointController.getRegisteredObjects();

				Filter filter = getT();

				registeredObjects.remove(filter);

				Set<FilterRegistration> filterRegistrations =
					_contextController.getFilterRegistrations();

				filterRegistrations.remove(this);

				_contextController.ungetServletContextHelper(
					_serviceHolder.getBundle());

				super.destroy();

				filter.destroy();
			}
			finally {
				_serviceHolder.release();
			}
		}
	}

	public void doFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws IOException, ServletException {

		try (SafeCloseable safeCloseable = ThreadContextClassLoaderUtil.swap(
				_classLoader)) {

			Filter filter = getT();

			filter.doFilter(
				httpServletRequest, httpServletResponse, filterChain);
		}
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof FilterRegistration filterRegistration) {
			return Objects.equals(getT(), filterRegistration.getT());
		}

		return false;
	}

	@Override
	public int hashCode() {
		FilterDTO filterDTO = getD();

		return Long.hashCode(filterDTO.serviceId);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		if (_initDestroyWithContextController) {
			try (SafeCloseable safeCloseable =
					ThreadContextClassLoaderUtil.swap(_classLoader)) {

				Filter filter = getT();

				filter.init(filterConfig);
			}
		}
	}

	public String match(
		String name, String requestURI, String extension, Match match) {

		if ((name != null) && (getD().servletNames != null)) {
			for (String servletName : getD().servletNames) {
				if (servletName.equals(name)) {
					return name;
				}
			}
		}

		if ((requestURI != null) && !requestURI.isEmpty()) {
			for (String pattern : getD().patterns) {
				if (doPatternMatch(pattern, requestURI, extension)) {
					return pattern;
				}
			}

			for (Pattern pattern : _patterns) {
				Matcher matcher = pattern.matcher(requestURI);

				if (matcher.matches()) {
					return pattern.toString();
				}
			}

			return null;
		}

		return null;
	}

	@Override
	public String match(
		String name, String servletPath, String pathInfo, String extension,
		Match match) {

		throw new UnsupportedOperationException();
	}

	protected boolean doPatternMatch(
			String pattern, String path, String extension)
		throws IllegalArgumentException {

		if (pattern.indexOf("/*.") == 0) {
			pattern = pattern.substring(1);
		}

		int extensionMatchIndex = pattern.indexOf("/*.");

		String extensionWithPrefixMatch = null;

		if ((extensionMatchIndex >= 0) &&
			(pattern.lastIndexOf(47) == extensionMatchIndex)) {

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

	protected boolean isPathWildcardMatch(String pattern, String path) {
		if (path == null) {
			return false;
		}
		else if (pattern.endsWith("/*")) {
			int pathPatternLength = pattern.length() - 2;

			if (!path.regionMatches(0, pattern, 0, pathPatternLength)) {
				return false;
			}

			if ((path.length() <= pathPatternLength) ||
				(path.charAt(pathPatternLength) == '/')) {

				return true;
			}

			return false;
		}

		return pattern.equals(path);
	}

	private Pattern[] _getPatterns(FilterDTO filterDTO) {
		if (filterDTO.regexs == null) {
			return new Pattern[0];
		}

		return TransformUtil.transform(
			filterDTO.regexs, Pattern::compile, Pattern.class);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FilterRegistration.class);

	private final ClassLoader _classLoader;
	private final ContextController _contextController;
	private final boolean _initDestroyWithContextController;
	private final Pattern[] _patterns;
	private final int _priority;
	private final ContextController.ServiceHolder<Filter> _serviceHolder;

}