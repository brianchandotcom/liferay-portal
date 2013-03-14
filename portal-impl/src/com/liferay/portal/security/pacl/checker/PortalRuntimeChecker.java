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

package com.liferay.portal.security.pacl.checker;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.security.Permission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PortalRuntimeChecker extends BaseChecker {

	public void afterPropertiesSet() {
		initClassLoaderReferenceIds();
		initExpandoBridgeClassNames();
		initGetBeanPropertyClassNames();
		initSearchEngineIds();
		initSetBeanPropertyClassNames();
		initThreadPoolExecutorNames();
	}

	@Override
	public AuthorizationProperty generateAuthorizationProperty(
		Object... arguments) {

		if ((arguments == null) || (arguments.length != 1) ||
			!(arguments[0] instanceof Permission)) {

			return null;
		}

		PortalRuntimePermission portalRuntimePermission =
			(PortalRuntimePermission)arguments[0];

		String name = portalRuntimePermission.getName();
		String servletContextName =
			portalRuntimePermission.getServletContextName();
		String property = portalRuntimePermission.getProperty();
		Object subject = portalRuntimePermission.getSubject();

		String key = null;
		String value = null;

		if (name.startsWith(PORTAL_RUNTIME_PERMISSION_GET_CLASSLOADER)) {
			key = "security-manager-class-loader-reference-ids";
			value = (String)subject;
		}
		else if (name.equals(PORTAL_RUNTIME_PERMISSION_EXPANDO_BRIDGE)) {
			key = "security-manager-expando-bridge";
			value = (String)subject;
		}
		else if (name.equals(PORTAL_RUNTIME_PERMISSION_GET_BEAN_PROPERTY)) {
			String filter =
				StringPool.OPEN_BRACKET + servletContextName +
						StringPool.CLOSE_BRACKET;

			key = "security-manager-get-bean-property".concat(filter);

			Class<?> clazz = (Class<?>)subject;

			value = clazz.getName();

			if (Validator.isNotNull(property)) {
				value = value + StringPool.POUND + property;
			}
		}
		else if (name.equals(PORTAL_RUNTIME_PERMISSION_SEARCH_ENGINE)) {
			key = "security-manager-search-engine-ids";
			value = (String)subject;
		}
		else if (name.equals(PORTAL_RUNTIME_PERMISSION_SET_BEAN_PROPERTY)) {
			String filter =
				StringPool.OPEN_BRACKET + servletContextName +
					StringPool.CLOSE_BRACKET;

			key = "security-manager-set-bean-property".concat(filter);

			Class<?> clazz = (Class<?>)subject;

			value = clazz.getName();

			if (Validator.isNotNull(property)) {
				value = value + StringPool.POUND + property;
			}
		}
		else if (name.equals(PORTAL_RUNTIME_PERMISSION_THREAD_POOL_EXECUTOR)) {
			key = "security-manager-thread-pool-executor-names";
			value = (String)subject;
		}
		else {
			return null;
		}

		AuthorizationProperty authorizationProperty =
			new AuthorizationProperty();

		authorizationProperty.setKey(key);
		authorizationProperty.setValue(value);

		return authorizationProperty;
	}

	public boolean implies(Permission permission) {
		PortalRuntimePermission portalRuntimePermission =
			(PortalRuntimePermission)permission;

		String name = portalRuntimePermission.getName();
		Object subject = portalRuntimePermission.getSubject();
		String property = GetterUtil.getString(
			portalRuntimePermission.getProperty());

		if (name.equals(PORTAL_RUNTIME_PERMISSION_EXPANDO_BRIDGE)) {
			String className = (String)subject;

			if (!_expandoBridgeClassNames.contains(className)) {
				logSecurityException(
					_log, "Attempted to get Expando bridge on " + className);

				return false;
			}
		}
		else if (name.equals(PORTAL_RUNTIME_PERMISSION_GET_BEAN_PROPERTY)) {
			Class<?> clazz = (Class<?>)subject;

			String servletContextName =
				portalRuntimePermission.getServletContextName();

			if (!hasGetBeanProperty(servletContextName, clazz, property)) {
				if (Validator.isNotNull(property)) {
					logSecurityException(
						_log,
						"Attempted to get bean property " + property + " on " +
							clazz + " from " + servletContextName);
				}
				else {
					logSecurityException(
						_log, "Attempted to get bean property on " + clazz +
							" from " + servletContextName);
				}

				return false;
			}
		}
		else if (name.startsWith(PORTAL_RUNTIME_PERMISSION_GET_CLASSLOADER)) {
			String classLoaderReferenceId = (String)subject;

			if (!hasGetClassLoader(classLoaderReferenceId)) {
				logSecurityException(
					_log, "Attempted to get class loader " +
						classLoaderReferenceId);

				return false;
			}
		}
		else if (name.equals(PORTAL_RUNTIME_PERMISSION_SEARCH_ENGINE)) {
			String searchEngineId = (String)subject;

			if (!_searchEngineIds.contains(searchEngineId)) {
				logSecurityException(
					_log, "Attempted to get search engine " + searchEngineId);

				return false;
			}
		}
		else if (name.equals(PORTAL_RUNTIME_PERMISSION_SET_BEAN_PROPERTY)) {
			Class<?> clazz = (Class<?>)subject;

			String servletContextName =
				portalRuntimePermission.getServletContextName();

			if (!hasSetBeanProperty(servletContextName, clazz, property)) {
				if (Validator.isNotNull(property)) {
					logSecurityException(
						_log,
						"Attempted to set bean property " + property + " on " +
							clazz + " from " + servletContextName);
				}
				else {
					logSecurityException(
						_log, "Attempted to set bean property on " + clazz +
							" from " + servletContextName);
				}

				return false;
			}
		}
		else if (name.equals(PORTAL_RUNTIME_PERMISSION_THREAD_POOL_EXECUTOR)) {
			String threadPoolExecutorName = (String)subject;

			if (!hasThreadPoolExecutorNames(threadPoolExecutorName)) {
				logSecurityException(
					_log,
					"Attempted to modify thread pool executor " +
						threadPoolExecutorName);

				return false;
			}
		}

		return true;
	}

	protected boolean hasGetBeanProperty(
		String servletContextName, Class<?> clazz, String property) {

		String className = clazz.getName();

		if (servletContextName.equals(getServletContextName())) {
			return true;
		}

		Set<String> _getBeanPropertyClassNames =
			_getBeanPropertyClassNamesMap.get(servletContextName);

		if (_getBeanPropertyClassNames == null) {
			return false;
		}

		if (_getBeanPropertyClassNames.contains(className)) {
			return true;
		}

		if (Validator.isNotNull(property)) {
			if (_getBeanPropertyClassNames.contains(
					className.concat(StringPool.POUND).concat(property))) {

				return true;
			}
		}

		return false;
	}

	protected boolean hasGetClassLoader(String classLoaderReferenceId) {

		// Temporarily return true

		return true;
	}

	protected boolean hasSetBeanProperty(
		String servletContextName, Class<?> clazz, String property) {

		String className = clazz.getName();

		if (servletContextName.equals(getServletContextName())) {
			return true;
		}

		Set<String> _setBeanPropertyClassNames =
			_setBeanPropertyClassNamesMap.get(servletContextName);

		if (_setBeanPropertyClassNames == null) {
			return false;
		}

		if (_setBeanPropertyClassNames.contains(className)) {
			return true;
		}

		if (Validator.isNotNull(property)) {
			if (_setBeanPropertyClassNames.contains(
					className.concat(StringPool.POUND).concat(property))) {

				return true;
			}
		}

		return false;
	}

	protected boolean hasThreadPoolExecutorNames(
		String threadPoolExecutorName) {

		for (Pattern threadPoolExecutorNamePattern :
				_threadPoolExecutorNamePatterns) {

			Matcher matcher = threadPoolExecutorNamePattern.matcher(
				threadPoolExecutorName);

			if (matcher.matches()) {
				return true;
			}
		}

		return false;
	}

	protected void initClassLoaderReferenceIds() {
		_classLoaderReferenceIds = getPropertySet(
			"security-manager-class-loader-reference-ids");

		if (_log.isDebugEnabled()) {
			Set<String> classLoaderReferenceIds = new TreeSet<String>(
				_classLoaderReferenceIds);

			for (String classLoaderReferenceId : classLoaderReferenceIds) {
				_log.debug(
					"Allowing access to class loader for reference " +
						classLoaderReferenceId);
			}
		}
	}

	protected void initExpandoBridgeClassNames() {
		_expandoBridgeClassNames = getPropertySet(
			"security-manager-expando-bridge");

		if (_log.isDebugEnabled()) {
			Set<String> classNames = new TreeSet<String>(
				_expandoBridgeClassNames);

			for (String className : classNames) {
				_log.debug("Allowing Expando bridge on class " + className);
			}
		}
	}

	protected void initGetBeanPropertyClassNames() {
		Properties properties = getProperties();

		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			String key = (String)entry.getKey();
			String value = (String)entry.getValue();

			if (!key.startsWith("security-manager-get-bean-property[")) {
				continue;
			}

			int x = key.indexOf("[");
			int y = key.indexOf("]", x);

			String servletContextName = key.substring(x + 1, y);

			Set<String> beanPropertyClassNames = SetUtil.fromArray(
				StringUtil.split(value));

			_getBeanPropertyClassNamesMap.put(
				servletContextName, beanPropertyClassNames);

			if (_log.isDebugEnabled() &&
				!servletContextName.equals(_PORTAL_SERVLET_CONTEXT_NAME)) {

				Set<String> classNames = new TreeSet<String>(
					beanPropertyClassNames);

				for (String className : classNames) {
					_log.debug(
						"Allowing get bean property from " +
							servletContextName + " on class " + className);
				}
			}
		}

		// legacy support

		Set<String> beanPropertyClassNames = _getBeanPropertyClassNamesMap.get(
			_PORTAL_SERVLET_CONTEXT_NAME);

		if (beanPropertyClassNames == null) {
			beanPropertyClassNames = getPropertySet(
				"security-manager-get-bean-property");
		}
		else {
			beanPropertyClassNames.addAll(
				getPropertySet("security-manager-get-bean-property"));
		}

		_getBeanPropertyClassNamesMap.put(
			_PORTAL_SERVLET_CONTEXT_NAME, beanPropertyClassNames);

		if (_log.isDebugEnabled()) {
			Set<String> classNames = new TreeSet<String>(
				beanPropertyClassNames);

			for (String className : classNames) {
				_log.debug(
					"Allowing get bean property from " +
						_PORTAL_SERVLET_CONTEXT_NAME + " on class " +
							className);
			}
		}
	}

	protected void initSearchEngineIds() {
		_searchEngineIds = getPropertySet("security-manager-search-engine-ids");

		if (_log.isDebugEnabled()) {
			Set<String> searchEngineIds = new TreeSet<String>(_searchEngineIds);

			for (String searchEngineId : searchEngineIds) {
				_log.debug("Allowing search engine " + searchEngineId);
			}
		}
	}

	protected void initSetBeanPropertyClassNames() {
		Properties properties = getProperties();

		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			String key = (String)entry.getKey();
			String value = (String)entry.getValue();

			if (!key.startsWith("security-manager-set-bean-property[")) {
				continue;
			}

			int x = key.indexOf("[");
			int y = key.indexOf("]", x);

			String servletContextName = key.substring(x + 1, y);

			Set<String> beanPropertyClassNames = SetUtil.fromArray(
				StringUtil.split(value));

			_setBeanPropertyClassNamesMap.put(
				servletContextName, beanPropertyClassNames);

			if (_log.isDebugEnabled() &&
				!servletContextName.equals(_PORTAL_SERVLET_CONTEXT_NAME)) {

				Set<String> classNames = new TreeSet<String>(
					beanPropertyClassNames);

				for (String className : classNames) {
					_log.debug(
						"Allowing set bean property from " +
							servletContextName + " on class " + className);
				}
			}
		}

		// legacy support

		Set<String> beanPropertyClassNames = _setBeanPropertyClassNamesMap.get(
			_PORTAL_SERVLET_CONTEXT_NAME);

		if (beanPropertyClassNames == null) {
			beanPropertyClassNames = getPropertySet(
				"security-manager-set-bean-property");
		}
		else {
			beanPropertyClassNames.addAll(
				getPropertySet("security-manager-set-bean-property"));
		}

		_setBeanPropertyClassNamesMap.put(
			_PORTAL_SERVLET_CONTEXT_NAME, beanPropertyClassNames);

		if (_log.isDebugEnabled()) {
			Set<String> classNames = new TreeSet<String>(
				beanPropertyClassNames);

			for (String className : classNames) {
				_log.debug(
					"Allowing set bean property from " +
						_PORTAL_SERVLET_CONTEXT_NAME + " on class " +
							className);
			}
		}
	}

	protected void initThreadPoolExecutorNames() {
		Set<String> threadPoolExecutorNames = getPropertySet(
			"security-manager-thread-pool-executor-names");

		_threadPoolExecutorNamePatterns = new ArrayList<Pattern>(
			threadPoolExecutorNames.size());

		for (String threadPoolExecutorName : threadPoolExecutorNames) {
			Pattern threadPoolExecutorNamePattern = Pattern.compile(
				threadPoolExecutorName);

			_threadPoolExecutorNamePatterns.add(threadPoolExecutorNamePattern);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Allowing thread pool executors that match the regular " +
						"expression " + threadPoolExecutorName);
			}
		}
	}

	private static final String _PORTAL_SERVLET_CONTEXT_NAME = "portal";

	private static Log _log = LogFactoryUtil.getLog(PortalRuntimeChecker.class);

	private Set<String> _classLoaderReferenceIds;
	private Set<String> _expandoBridgeClassNames;
	private Map<String, Set<String>> _getBeanPropertyClassNamesMap =
		new HashMap<String, Set<String>>();
	private Set<String> _searchEngineIds;
	private Map<String, Set<String>> _setBeanPropertyClassNamesMap =
		new HashMap<String, Set<String>>();
	private List<Pattern> _threadPoolExecutorNamePatterns;

}