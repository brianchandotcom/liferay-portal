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

package com.liferay.portal.security.pacl;

import com.liferay.portal.dao.jdbc.pacl.PACLDataSource;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.security.lang.PortalSecurityManager;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;
import com.liferay.portal.spring.util.FilterClassLoader;

import sun.reflect.Reflection;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PACLClassUtil {

	public static PACLPolicy getPACLPolicyByReflection(boolean debug) {
		boolean enabled = PortalSecurityManagerThreadLocal.isEnabled();

		try {
			PortalSecurityManagerThreadLocal.setEnabled(false);

			return _instance._getPACLPolicyByReflection(debug);
		}
		finally {
			PortalSecurityManagerThreadLocal.setEnabled(enabled);
		}
	}

	public static PACLPolicy getPACLPolicyBySecurityManagerClassContext(
		Class<?>[] classes, boolean debug) {

		boolean enabled = PortalSecurityManagerThreadLocal.isEnabled();

		try {
			PortalSecurityManagerThreadLocal.setEnabled(false);

			return _instance._getPACLPolicyBySecurityManagerClassContext(
				classes, debug);
		}
		finally {
			PortalSecurityManagerThreadLocal.setEnabled(enabled);
		}
	}

	private PACLClassUtil() {
		_systemClassLoader = ClassLoader.getSystemClassLoader();

		_portalClassLoader = PACLAdvice.class.getClassLoader();

		_commonClassLoader = _portalClassLoader.getParent();
	}

	private ClassLoader _getCallerClassLoader(Class<?> callerClass) {
		ClassLoader callerClassLoader = callerClass.getClassLoader();

		if (callerClassLoader == null) {
			return null;
		}

		if (callerClassLoader instanceof FilterClassLoader) {
			callerClassLoader = callerClassLoader.getParent();

			if (callerClassLoader instanceof AggregateClassLoader) {
				callerClassLoader = callerClassLoader.getParent();
			}
		}

		return callerClassLoader;
	}

	private PACLPolicy _getPACLPolicyByReflection(boolean debug) {
		// int i = 0 always returns sun.reflect.Reflection
		// int i = 1, 2 are this class, so we can skip those

		for (int i = 3;; i++) {
			Class<?> callerClass = Reflection.getCallerClass(i);

			if (callerClass == null) {
				break;
			}

			if ((callerClass == PACLAdvice.class) ||
				(callerClass == PACLDataSource.class) ||
				(callerClass == PACLBeanHandler.class) ||
				(callerClass == PortalSecurityManager.class)) {

				continue;
			}

			if (debug) {
				_log.debug(
					"Frame " + i + " has caller class " +
						callerClass.getName());
			}

			ClassLoader callerClassLoader = _getCallerClassLoader(callerClass);

			if ((callerClassLoader == null) ||
				(callerClassLoader == _commonClassLoader) ||
				(callerClassLoader == _systemClassLoader) ||
				isAncestor(_portalClassLoader, callerClassLoader)) {

				continue;
			}

			PACLPolicy paclPolicy = PACLPolicyManager.getPACLPolicy(
				callerClassLoader);

			if (paclPolicy != null) {
				return paclPolicy;
			}

			/*_log.error(
				"Unable to locate PACL policy for caller class loader " +
					callerClassLoader);*/
		}

		return PACLPolicyManager.getDefaultPACLPolicy();
	}

	private PACLPolicy _getPACLPolicyBySecurityManagerClassContext(
		Class<?>[] classes, boolean debug) {

		// int i = 0 always returns
		// com.liferay.portal.security.lang.PortalSecurityManager

		for (int i = 1; i < classes.length; i++) {
			Class<?> callerClass = classes[i];

			if (callerClass == null) {
				break;
			}

			if ((callerClass == PACLAdvice.class) ||
				(callerClass == PACLDataSource.class) ||
				(callerClass == PACLBeanHandler.class) ||
				(callerClass == PortalSecurityManager.class)) {

				continue;
			}

			if (debug) {
				_log.debug(
					"Frame " + i + " has caller class " +
						callerClass.getName());
			}

			ClassLoader callerClassLoader = _getCallerClassLoader(callerClass);

			if ((callerClassLoader == null) ||
				(callerClassLoader == _commonClassLoader) ||
				(callerClassLoader == _systemClassLoader) ||
				isAncestor(_portalClassLoader, callerClassLoader)) {

				continue;
			}

			PACLPolicy paclPolicy = PACLPolicyManager.getPACLPolicy(
				callerClassLoader);

			if (paclPolicy != null) {
				return paclPolicy;
			}

			/*_log.error(
				"Unable to locate PACL policy for caller class loader " +
					callerClassLoader);*/
		}

		return PACLPolicyManager.getDefaultPACLPolicy();
	}

	private boolean isAncestor(
		ClassLoader ancestor, ClassLoader callerClassLoader) {

		ClassLoader temp = callerClassLoader;

		do {
			if (ancestor == temp) {
				return true;
			}
		}
		while (((temp = temp.getParent()) != null));

		return false;
	}

	private static Log _log = LogFactoryUtil.getLog(PACLClassUtil.class);

	private static PACLClassUtil _instance = new PACLClassUtil();

	private ClassLoader _commonClassLoader;
	private ClassLoader _portalClassLoader;
	private ClassLoader _systemClassLoader;

}