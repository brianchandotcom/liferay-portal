/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.executor.PortalExecutorManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseAsyncDestination;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.security.Permission;

import java.util.Set;
import java.util.TreeSet;

import sun.reflect.Reflection;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalRuntimeChecker extends BaseChecker {

	public void afterPropertiesSet() {
		initExpandoBridgeClassNames();
		initGetBeanPropertyClassNames();
		initSetBeanPropertyClassNames();
	}

	public void checkPermission(Permission permission) {
		PortalRuntimePermission portalRuntimePermission =
			(PortalRuntimePermission)permission;

		String name = portalRuntimePermission.getName();
		Object subject = portalRuntimePermission.getSubject();
		String property = portalRuntimePermission.getProperty();

		if (Validator.isNull(property)) {
			property = StringPool.BLANK;
		}

		if (name.equals(PORTAL_RUNTIME_PERMISSION_EXPANDO_BRIDGE)) {
			String className = (String)subject;

			if (!_expandoBridgeClassNames.contains(className)) {
				throwSecurityException(
					_log, "Attempted to get Expando bridge on " + className);
			}
		}
		else if (name.equals(PORTAL_RUNTIME_PERMISSION_GET_BEAN_PROPERTY)) {
			Class<?> clazz = (Class<?>)subject;

			if (!hasGetBeanProperty(clazz, property)) {
				throwSecurityException(
					_log, "Attempted to get bean property " + property + " on " +
						clazz);
			}
		}
		else if (name.equals(PORTAL_RUNTIME_PERMISSION_SET_BEAN_PROPERTY)) {
			Class<?> clazz = (Class<?>)subject;

			if (!hasSetBeanProperty(clazz, property)) {
				throwSecurityException(
					_log, "Attempted to set bean property " + property + " on " +
						clazz);
			}
		}
	}

	protected boolean hasGetBeanProperty(Class<?> clazz, String property) {
		String className = clazz.getName();

		if (_getBeanPropertyClassNames.contains(className)) {
			return true;
		}

		if (Validator.isNotNull(property)) {
			className = className.concat(StringPool.POUND).concat(property);
		}

		if (_getBeanPropertyClassNames.contains(className)) {
			return true;
		}

		if (clazz == PortalExecutorManagerUtil.class) {
			Class<?> callerClass9 = Reflection.getCallerClass(9);

			if (callerClass9 == BaseAsyncDestination.class) {
				return true;
			}
		}

		return false;
	}

	protected boolean hasSetBeanProperty(Class<?> clazz, String property) {
		String className = clazz.getName();

		if (_getBeanPropertyClassNames.contains(className)) {
			return true;
		}

		if (Validator.isNotNull(property)) {
			className = className.concat(StringPool.POUND).concat(property);
		}

		if (_setBeanPropertyClassNames.contains(className)) {
			return true;
		}

		return false;
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

	private static Log _log = LogFactoryUtil.getLog(PortalRuntimeChecker.class);

	private Set<String> _expandoBridgeClassNames;
	private Set<String> _getBeanPropertyClassNames;
	private Set<String> _setBeanPropertyClassNames;

}