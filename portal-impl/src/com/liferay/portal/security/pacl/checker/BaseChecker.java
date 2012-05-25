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
import com.liferay.portal.kernel.security.pacl.PACLConstants;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.security.pacl.PACLClassUtil;
import com.liferay.portal.security.pacl.PACLPolicy;

import java.util.Properties;
import java.util.Set;

import sun.reflect.Reflection;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public abstract class BaseChecker implements Checker, PACLConstants {

	public BaseChecker() {
		ClassLoader portalClassLoader = BaseChecker.class.getClassLoader();

		_portalClassLoader = portalClassLoader;
		_commonClassLoader = portalClassLoader.getParent();
		_systemClassLoader = ClassLoader.getSystemClassLoader();
	}

	public ClassLoader getClassLoader() {
		return _paclPolicy.getClassLoader();
	}

	public ClassLoader getCommonClassLoader() {
		return _commonClassLoader;
	}

	public PACLPolicy getPACLPolicy() {
		return _paclPolicy;
	}

	public ClassLoader getPortalClassLoader() {
		return _portalClassLoader;
	}

	public String getServletContextName() {
		return _paclPolicy.getServletContextName();
	}

	public ClassLoader getSystemClassLoader() {
		return _systemClassLoader;
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

	protected boolean isJSPCompiler(String subject, String actions) {
		for (int i = 1;; i++) {
			Class<?> callerClass = Reflection.getCallerClass(i);

			if (callerClass == null) {
				break;
			}

			String callerClassName = callerClass.getName();

			if (!callerClassName.startsWith(
					_PACKAGE_NAME_ORG_APACHE_JASPER_COMPILER) &&
				!callerClassName.startsWith(
					_PACKAGE_NAME_ORG_APACHE_JASPER_XMLPARSER) &&
				!callerClassName.startsWith(
					_PACKAGE_NAME_ORG_APACHE_NAMING_RESOURCES) &&
				!callerClassName.equals(
					_ClASS_NAME_DEFAULT_ANNOTATION_PROCESSOR) &&
				!callerClassName.equals(_ClASS_NAME_DEFAULT_INSTANCE_MANAGER) &&
				!callerClassName.equals(_ClASS_NAME_JASPER_LOADER) &&
				!callerClassName.startsWith(
					_CLASS_NAME_JASPER_SERVLET_CONTEXT_CUSTOMIZER) &&
				!callerClassName.equals(_ClASS_NAME_JSP_MANAGER) &&
				!callerClassName.equals(_ClASS_NAME_PAGE_MANAGER) &&
				!callerClassName.equals(_ClASS_NAME_TAG_HANDLER_POOL)) {

				continue;
			}

			ClassLoader callerClassLoader = PACLClassLoaderUtil.getClassLoader(
				callerClass);

			if (callerClassLoader != _commonClassLoader) {
				boolean allow = false;

				if (ServerDetector.isGeronimo()) {
					Class<?> callerClassLoaderClass =
						callerClassLoader.getClass();

					String callerClassLoaderClassName =
						callerClassLoaderClass.getName();

					if (callerClassLoaderClassName.equals(
							_CLASS_NAME_JAR_FILE_CLASS_LOADER)) {

						allow = true;
					}
				}
				else if (ServerDetector.isGlassfish()) {
					Class<?> callerClassLoaderClass =
						callerClassLoader.getClass();

					callerClassLoaderClass =
						callerClassLoaderClass.getEnclosingClass();

					if (callerClassLoaderClass != null) {
						String callerClassLoaderClassName =
							callerClassLoaderClass.getName();

						if (callerClassLoaderClassName.equals(
								_ClASS_NAME_BUNDLE_WIRING_IMPL)) {

							allow = true;
						}
					}
				}
				else if (ServerDetector.isJBoss()) {
					String callerClassLoaderString =
						callerClassLoader.toString();

					if (callerClassLoaderString.contains(
							_MODULE_NAME_ORG_JBOSS_AS_WEB_MAIN)) {

						allow = true;
					}
				}
				else if (ServerDetector.isJOnAS()) {
					Class<?> callerClassLoaderClass =
						callerClassLoader.getClass();

					String callerClassLoaderClassName =
						callerClassLoaderClass.getName();

					if (callerClassLoaderClassName.startsWith(
							_CLASS_NAME_MODULE_IMPL)) {

						allow = true;
					}
				}
				else if (ServerDetector.isResin()) {
					Class<?> callerClassLoaderClass =
						callerClassLoader.getClass();

					String classLocation = PACLClassUtil.getClassLocation(
						callerClassLoaderClass);

					if (Validator.isNull(classLocation)) {
						allow = true;
					}
				}

				if (!allow) {
					if (Validator.isNotNull(actions)) {
						_log.error(
							"A plugin is hijacking the JSP compiler via " +
								callerClassName + " to " + actions + " " +
									subject);
					}
					else {
						_log.error(
							"A plugin is hijacking the JSP compiler via " +
								callerClassName + " to " + subject);
					}

					return false;
				}
			}

			if (_log.isDebugEnabled()) {
				if (Validator.isNotNull(actions)) {
					_log.debug(
						"Allowing the JSP compiler via " + callerClassName +
							" to " + actions + " " + subject);
				}
				else {
					_log.debug(
						"Allowing the JSP compiler via " + callerClassName +
							" to " + subject);
				}
			}

			return true;
		}

		return false;
	}

	protected void throwSecurityException(Log log, String message) {
		if (log.isWarnEnabled()) {
			log.warn(message);
		}

		throw new SecurityException(message);
	}

	private static final String _ClASS_NAME_BUNDLE_WIRING_IMPL =
		"org.apache.felix.framework.BundleWiringImpl";

	private static final String _ClASS_NAME_DEFAULT_ANNOTATION_PROCESSOR =
		"org.apache.catalina.util.DefaultAnnotationProcessor";

	private static final String _ClASS_NAME_DEFAULT_INSTANCE_MANAGER =
		"org.apache.catalina.core.DefaultInstanceManager";

	private static final String _CLASS_NAME_JAR_FILE_CLASS_LOADER =
		"org.apache.geronimo.kernel.classloader.JarFileClassLoader";

	private static final String _ClASS_NAME_JASPER_LOADER =
		"org.apache.jasper.servlet.JasperLoader";

	private static final String _CLASS_NAME_JASPER_SERVLET_CONTEXT_CUSTOMIZER =
		"org.apache.geronimo.jasper.JasperServletContextCustomizer$";

	private static final String _ClASS_NAME_JSP_MANAGER =
		"com.caucho.jsp.JspManager";

	private static final String _CLASS_NAME_MODULE_IMPL =
		"org.apache.felix.framework.ModuleImpl";

	private static final String _ClASS_NAME_PAGE_MANAGER =
		"com.caucho.jsp.PageManager";

	private static final String _ClASS_NAME_TAG_HANDLER_POOL =
		"org.apache.jasper.runtime.TagHandlerPool";

	private static final String _MODULE_NAME_ORG_JBOSS_AS_WEB_MAIN =
		"org.jboss.as.web:main";

	private static final String _PACKAGE_NAME_ORG_APACHE_JASPER_COMPILER =
		"org.apache.jasper.compiler.";

	private static final String _PACKAGE_NAME_ORG_APACHE_JASPER_XMLPARSER =
		"org.apache.jasper.xmlparser.";

	private static final String _PACKAGE_NAME_ORG_APACHE_NAMING_RESOURCES =
		"org.apache.naming.resources";

	private static Log _log = LogFactoryUtil.getLog(BaseChecker.class);

	private ClassLoader _commonClassLoader;
	private PACLPolicy _paclPolicy;
	private ClassLoader _portalClassLoader;
	private ClassLoader _systemClassLoader;

}