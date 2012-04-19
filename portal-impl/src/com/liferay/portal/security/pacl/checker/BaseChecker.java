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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.security.pacl.PACLConstants;
import com.liferay.portal.security.pacl.PACLPolicy;

import java.util.Properties;
import java.util.Set;

import sun.reflect.Reflection;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseChecker implements Checker, PACLConstants {

	public BaseChecker() {
		ClassLoader portalClassLoader = BaseChecker.class.getClassLoader();

		_commonClassLoader = portalClassLoader.getParent();
	}

	public ClassLoader getClassLoader() {
		return _paclPolicy.getClassLoader();
	}

	public PACLPolicy getPACLPolicy() {
		return _paclPolicy;
	}

	public String getServletContextName() {
		return _paclPolicy.getServletContextName();
	}

	public void setPACLPolicy(PACLPolicy paclPolicy) {
		_paclPolicy = paclPolicy;
	}

	protected Properties getProperties() {
		return _paclPolicy.getProperties();
	}

	protected String getProperty(String key) {
		return _paclPolicy.getProperty(key);
	}

	protected String[] getPropertyArray(String key) {
		return _paclPolicy.getPropertyArray(key);
	}

	protected boolean getPropertyBoolean(String key) {
		return _paclPolicy.getPropertyBoolean(key);
	}

	protected Set<String> getPropertySet(String key) {
		return _paclPolicy.getPropertySet(key);
	}

	protected boolean isJSPCompiler(String subject, String action) {
		for (int i = 1;; i++) {
			Class<?> callerClass = Reflection.getCallerClass(i);

			if (callerClass == null) {
				break;
			}

			String callerClassName = callerClass.getName();

			if (callerClassName.startsWith(
					_PACKAGE_ORG_APACHE_JASPER_COMPILER) ||
				callerClassName.startsWith(
					_PACKAGE_ORG_APACHE_JASPER_XMLPARSER) ||
				callerClassName.startsWith(
					_PACKAGE_ORG_APACHE_NAMING_RESOURCES) ||
				callerClassName.equals(_ClASS_NAME_JASPER_LOADER)) {

				ClassLoader callerClassLoader =
					PACLClassLoaderUtil.getClassLoader(callerClass);

				if (callerClassLoader != _commonClassLoader) {
					_log.error(
						"A plugin is hijacking the JSP compiler via " +
							callerClassName + " to " + action + " " + subject);

					return false;
				}

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Allowing the JSP compiler via " + callerClassName +
							" to " + action + " " + subject);
				}

				return true;
			}
		}

		return false;
	}

	private static final String _ClASS_NAME_JASPER_LOADER =
		"org.apache.jasper.servlet.JasperLoader";

	private static final String _PACKAGE_ORG_APACHE_JASPER_COMPILER =
		"org.apache.jasper.compiler.";

	private static final String _PACKAGE_ORG_APACHE_JASPER_XMLPARSER =
		"org.apache.jasper.xmlparser.";

	private static final String _PACKAGE_ORG_APACHE_NAMING_RESOURCES =
		"org.apache.naming.resources";

	private static Log _log = LogFactoryUtil.getLog(BaseChecker.class);

	private ClassLoader _commonClassLoader;
	private PACLPolicy _paclPolicy;

}