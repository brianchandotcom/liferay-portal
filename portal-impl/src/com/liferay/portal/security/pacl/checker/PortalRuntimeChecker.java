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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.security.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalRuntimeChecker extends BaseChecker {

	public void afterPropertiesSet() {
		initClassLoaderReferenceIds();
		initExpandoBridgeClassNames();
		initGetBeanPropertyClassNames();
		initPortletBagPoolPortletIds();
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
			key = "security-manager-get-bean-property";

			Class<?> clazz = (Class<?>)subject;

			value = clazz.getName();

			if (Validator.isNotNull(property)) {
				value = value + StringPool.POUND + property;
			}
		}
		else if (name.equals(PORTAL_RUNTIME_PERMISSION_PORTLET_BAG_POOL)) {
			key = "security-manager-portlet-bag-pool-portlet-ids";
			value = (String)subject;
		}
		else if (name.equals(PORTAL_RUNTIME_PERMISSION_SEARCH_ENGINE)) {
			key = "security-manager-search-engine-ids";
			value = (String)subject;
		}
		else if (name.equals(PORTAL_RUNTIME_PERMISSION_SET_BEAN_PROPERTY)) {
			key = "security-manager-set-bean-property";

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

			if (!hasGetBeanProperty(clazz, property)) {
				if (Validator.isNotNull(property)) {
					logSecurityException(
						_log,
						"Attempted to get bean property " + property + " on " +
							clazz);
				}
				else {
					logSecurityException(
						_log, "Attempted to get bean property on " + clazz);
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
		else if (name.equals(PORTAL_RUNTIME_PERMISSION_PORTLET_BAG_POOL)) {
			String portletId = (String)subject;

			if (!hasPortletBagPoolPortletId(portletId)) {
				logSecurityException(
					_log,
					"Attempted to handle portlet bag pool portlet ID " +
						portletId);

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

			if (!hasSetBeanProperty(clazz, property)) {
				if (Validator.isNotNull(property)) {
					logSecurityException(
						_log,
						"Attempted to set bean property " + property + " on " +
							clazz);
				}
				else {
					logSecurityException(
						_log, "Attempted to set bean property on " + clazz);
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

	protected boolean hasGetBeanProperty(Class<?> clazz, String property) {
		String className = clazz.getName();

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

	protected boolean hasSetBeanProperty(Class<?> clazz, String property) {
		String className = clazz.getName();

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

	protected boolean hasPortletBagPoolPortletId(String portletId) {
		for (Pattern portletBagPoolPortletIdPattern :
				_portletBagPoolPortletIdPatterns) {

			Matcher matcher = portletBagPoolPortletIdPattern.matcher(portletId);

			if (matcher.matches()) {
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
		_getBeanPropertyClassNames = getPropertySet(
			"security-manager-get-bean-property");

		if (_log.isDebugEnabled()) {
			Set<String> classNames = new TreeSet<String>(
				_getBeanPropertyClassNames);

			for (String className : classNames) {
				_log.debug("Allowing get bean property on class " + className);
			}
		}
	}

	protected void initPortletBagPoolPortletIds() {
		Set<String> portletBagPoolPortletIds = getPropertySet(
			"security-manager-portlet-bag-pool-portlet-ids");

		_portletBagPoolPortletIdPatterns = new ArrayList<Pattern>(
			portletBagPoolPortletIds.size());

		for (String portletBagPoolPortletId : portletBagPoolPortletIds) {
			Pattern portletBagPoolPortletIdPattern = Pattern.compile(
				portletBagPoolPortletId);

			_portletBagPoolPortletIdPatterns.add(
				portletBagPoolPortletIdPattern);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Allowing portlet bag pool portlet IDs that match the " +
						"regular expression " + portletBagPoolPortletId);
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
		_setBeanPropertyClassNames = getPropertySet(
			"security-manager-set-bean-property");

		if (_log.isDebugEnabled()) {
			Set<String> classNames = new TreeSet<String>(
				_setBeanPropertyClassNames);

			for (String className : classNames) {
				_log.debug("Allowing set bean property on class " + className);
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

	private static Log _log = LogFactoryUtil.getLog(PortalRuntimeChecker.class);

	private Set<String> _classLoaderReferenceIds;
	private Set<String> _expandoBridgeClassNames;
	private Set<String> _getBeanPropertyClassNames;
	private List<Pattern>  _portletBagPoolPortletIdPatterns;
	private Set<String> _searchEngineIds;
	private Set<String> _setBeanPropertyClassNames;
	private List<Pattern> _threadPoolExecutorNamePatterns;

}