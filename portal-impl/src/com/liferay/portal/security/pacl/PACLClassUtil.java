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
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.spring.util.FilterClassLoader;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.net.URL;
import java.net.URLClassLoader;

import javax.servlet.ServletContext;

import sun.reflect.Reflection;

/**
 * @author Brian Wing Shun Chan
 */
public class PACLClassUtil {

	public static PACLPolicy getPACLPolicyByReflection(boolean debug) {
		return _instance._getPACLPolicyByReflection(debug);
	}

	public static PACLPolicy getPACLPolicyBySecurityManagerClassContext(
		Class<?>[] classes, boolean debug) {

		return _instance._getPACLPolicyBySecurityManagerClassContext(
			classes, debug);
	}

	private PACLClassUtil() {
		_systemClassLoader = ClassLoader.getSystemClassLoader();

		_portalClassLoader = PACLAdvice.class.getClassLoader();

		_commonClassLoader = _portalClassLoader.getParent();

		ServletContext servletContext = ServletContextPool.get(
			PortalUtil.getPathContext());

		File tempDir = (File)servletContext.getAttribute(
			JavaConstants.JAVAX_SERVLET_CONTEXT_TEMPDIR);

		_tempDirPath = tempDir.getAbsolutePath();
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

		if (callerClassLoaderClassName.equals(_JASPER_LOADER_CLASS_NAME)) {
			callerClassLoader = callerClassLoader.getParent();
		}

		return callerClassLoader;
	}

	private PACLPolicy _getPACLPolicyByReflection(boolean debug) {
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

			ClassLoader callerClassLoader = _getCallerClassLoader(callerClass);

			if (callerClassLoader == null) {
				continue;
			}

			if (!initialPortalClassLoaderPhase &&
				(callerClassLoader == _portalClassLoader)) {

				return PACLPolicyManager.getDefaultPACLPolicy();
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

			PACLPolicy paclPolicy = PACLPolicyManager.getPACLPolicy(
				callerClassLoader);

			if (paclPolicy != null) {
				return paclPolicy;
			}

			/*_log.error(
				"Unable to locate PACL policy for caller class loader " +
					callerClassLoader);*/
		}

		return null;
	}

	private PACLPolicy _getPACLPolicyBySecurityManagerClassContext(
		Class<?>[] classes, boolean debug) {

		ClassLoader foreignClassLoader = null;

		// Walk through the classes backwards

		for (int i = (classes.length - 1); i >= 0; i--) {
			Class<?> clazz = classes[i];

			ClassLoader classLoader = clazz.getClassLoader();

			// Locate the first foreign class loader

			foreignClassLoader = getForeignClassLoader(classLoader);

			if (foreignClassLoader != null) {
				break;
			}
		}

		if (foreignClassLoader == null) {
			return PACLPolicyManager.getDefaultPACLPolicy();
		}

		PACLPolicy paclPolicy = PACLPolicyManager.getPACLPolicy(
			foreignClassLoader);

		if (paclPolicy != null) {
			return paclPolicy;
		}

		return PACLPolicyManager.getDefaultPACLPolicy();
	}

	private ClassLoader getForeignClassLoader(ClassLoader classLoader) {
		if ((classLoader == null) ||
			(classLoader == _portalClassLoader) ||
			(classLoader.getParent() == _portalClassLoader) ||
			(classLoader == _commonClassLoader) ||
			(classLoader == _systemClassLoader)) {

			return null;
		}

		if (classLoader instanceof URLClassLoader) {
			URLClassLoader urlClassLoader = (URLClassLoader)classLoader;

			URL[] urLs = urlClassLoader.getURLs();

			if (urLs != null) {
				for (URL url : urLs) {
					String path = url.getPath();

					if (!path.contains(_globalLibPath) &&
						!path.contains(_tempDirPath) &&
						!path.contains(_webAppPath)) {

						return classLoader;
					}
				}
			}

			return null;
		}

		return classLoader;
	}

	private static final String _JASPER_LOADER_CLASS_NAME =
		"org.apache.jasper.servlet.JasperLoader";

	private static Log _log = LogFactoryUtil.getLog(PACLClassUtil.class);

	private static PACLClassUtil _instance = new PACLClassUtil();

	private ClassLoader _commonClassLoader;
	private String _globalLibPath = PropsValues.LIFERAY_LIB_GLOBAL_DIR;
	private ClassLoader _portalClassLoader;
	private ClassLoader _systemClassLoader;
	private String _tempDirPath;
	private String _webAppPath = PropsValues.LIFERAY_WEB_PORTAL_DIR;

}