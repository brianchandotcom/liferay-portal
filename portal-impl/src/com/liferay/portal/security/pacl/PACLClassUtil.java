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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;
import com.liferay.portal.spring.util.FilterClassLoader;

import java.net.URL;

import sun.reflect.Reflection;

/**
 * @author Brian Wing Shun Chan
 */
public class PACLClassUtil {

	public static ClassLoader getCallerClassLoader(Class<?> callerClass) {
		boolean enabled = PortalSecurityManagerThreadLocal.isEnabled();

		try {
			PortalSecurityManagerThreadLocal.setEnabled(false);

			return _instance._getCallerClassLoader(callerClass);
		}
		finally {
			PortalSecurityManagerThreadLocal.setEnabled(enabled);
		}
	}

	public static String getClassLocation(Class<?> clazz) {
		boolean enabled = PortalSecurityManagerThreadLocal.isEnabled();

		try {
			PortalSecurityManagerThreadLocal.setEnabled(false);

			ClassLoader classLoader = clazz.getClassLoader();

			if (classLoader == null) {
				return StringPool.BLANK;
			}

			String className = clazz.getName();

			String name = StringUtil.replace(
				className, StringPool.PERIOD, StringPool.SLASH);

			name += ".class";

			URL url = classLoader.getResource(name);

			return url.toString();
		}
		finally {
			PortalSecurityManagerThreadLocal.setEnabled(enabled);
		}
	}

	public static PACLPolicy getPACLPolicy(boolean deep, boolean debug) {
		PACLPolicy paclPolicy =
			PortalSecurityManagerThreadLocal.getPACLPolicy();

		if (paclPolicy != null) {
			return paclPolicy;
		}

		return getPACLPolicyByReflection(deep, debug);
	}

	public static PACLPolicy getPACLPolicyByReflection(
		boolean deep, boolean debug) {

		boolean enabled = PortalSecurityManagerThreadLocal.isEnabled();

		try {
			PortalSecurityManagerThreadLocal.setEnabled(false);

			return _instance._getPACLPolicyByReflection(deep, debug);
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

		Class<?> callerClassLoaderClass = callerClassLoader.getClass();

		String callerClassLoaderClassName = callerClassLoaderClass.getName();

		if (callerClassLoader instanceof FilterClassLoader) {
			callerClassLoader = callerClassLoader.getParent();

			if (callerClassLoader instanceof AggregateClassLoader) {
				callerClassLoader = callerClassLoader.getParent();
			}
		}

		if (callerClassLoaderClassName.equals(_ClASS_NAME_JASPER_LOADER)) {
			callerClassLoader = callerClassLoader.getParent();
		}
		else if (ServerDetector.isResin()) {
			if (callerClassLoaderClassName.equals(
					_ClASS_NAME_DYNAMIC_CLASS_LOADER)) {

				callerClassLoader = callerClassLoader.getParent();
			}
		}
		else if (ServerDetector.isWebLogic()) {
			if (callerClassLoaderClassName.equals(
					_CLASS_NAME_JSP_CLASS_LOADER)) {

				// weblogic.servlet.jsp.TagFileClassLoader

				callerClassLoader = callerClassLoader.getParent();

				// weblogic.utils.classloaders.ChangeAwareClassLoader

				callerClassLoader = callerClassLoader.getParent();
			}
		}
		else if (ServerDetector.isWebSphere()) {
			if (callerClassLoaderClassName.equals(
					_CLASS_NAME_JSP_EXTENSION_CLASS_LOADER)) {

				callerClassLoader = callerClassLoader.getParent();
			}
		}

		return callerClassLoader;
	}

	private PACLPolicy _getPACLPolicyByReflection(boolean deep, boolean debug) {
		PACLPolicy paclPolicy = null;

		boolean initialPortalClassLoaderPhase = true;

		// int i = 0 always returns sun.reflect.Reflection

		for (int i = 1;; i++) {
			Class<?> callerClass = Reflection.getCallerClass(i);

			if (callerClass == null) {
				break;
			}

			if (debug) {
				_log.debug(
					"Frame " + i + " has caller class " +
						callerClass.getName());
			}

			if (ProxyUtil.isProxyClass(callerClass)) {
				if (debug) {
					_log.debug("Skipping frame because it is proxy class");
				}

				continue;
			}

			ClassLoader callerClassLoader = _getCallerClassLoader(callerClass);

			if (callerClassLoader == null) {
				continue;
			}

			/*if (debug) {
				StringBundler sb = new StringBundler(10);

				sb.append("Frame ");
				sb.append(i);
				sb.append(" ");
				sb.append(callerClass);
				sb.append(" ");
				sb.append(callerClassLoader);
				sb.append(" ");
				sb.append(callerClassLoader.getClass());
				sb.append(" ");
				sb.append(PACLPolicyManager.getPACLPolicy(callerClassLoader));

				System.out.println(sb.toString());
			}*/

			if (!initialPortalClassLoaderPhase &&
				(callerClassLoader == _portalClassLoader)) {

				PACLPolicy defaultPACLPolicy =
					PACLPolicyManager.getDefaultPACLPolicy();

				if (paclPolicy == null) {
					if (debug) {
						_log.debug(
							"Possibly return default PACL policy " +
								defaultPACLPolicy);
					}

					paclPolicy = defaultPACLPolicy;
				}

				if (!deep) {
					break;
				}

				continue;
			}

			if (initialPortalClassLoaderPhase &&
				(callerClassLoader != _portalClassLoader)) {

				initialPortalClassLoaderPhase = false;
			}

			if ((callerClassLoader == _commonClassLoader) ||
				(callerClassLoader == _portalClassLoader) ||
				(callerClassLoader == _systemClassLoader)) {

				continue;
			}

			if (debug) {
				_log.debug(
					"Lookup PACL policy for caller class loader " +
						callerClassLoader);
			}

			PACLPolicy callerPACLPolicy = PACLPolicyManager.getPACLPolicy(
				callerClassLoader);

			if (callerPACLPolicy != null) {
				paclPolicy = callerPACLPolicy;

				if (debug) {
					_log.debug("Possibly return PACL policy " + paclPolicy);
				}

				if (!deep) {
					break;
				}
			}
		}

		if (debug) {
			_log.debug("Returning PACL policy " + paclPolicy);
		}

		return paclPolicy;
	}

	private static final String _ClASS_NAME_DYNAMIC_CLASS_LOADER =
		"com.caucho.loader.DynamicClassLoader";

	private static final String _ClASS_NAME_JASPER_LOADER =
		"org.apache.jasper.servlet.JasperLoader";

	private static final String _CLASS_NAME_JSP_CLASS_LOADER =
		"weblogic.servlet.jsp.JspClassLoader";

	private static final String _CLASS_NAME_JSP_EXTENSION_CLASS_LOADER =
		"com.ibm.ws.jsp.webcontainerext.JSPExtensionClassLoader";

	private static Log _log = LogFactoryUtil.getLog(PACLClassUtil.class);

	private static PACLClassUtil _instance = new PACLClassUtil();

	private ClassLoader _commonClassLoader;
	private ClassLoader _portalClassLoader;
	private ClassLoader _systemClassLoader;

}