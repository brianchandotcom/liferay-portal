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
import com.liferay.portal.kernel.util.PathUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.pacl.PACLClassUtil;
import com.liferay.portal.security.pacl.PACLPolicy;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalUtil;

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

	public AuthorizationProperty generateAuthorizationProperty(
		Object... arguments) {

		throw new UnsupportedOperationException();
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
		for (int i = 7;; i++) {
			Class<?> callerClass = Reflection.getCallerClass(i);

			if (callerClass == null) {
				return false;
			}

			Boolean allowed = null;

			String callerClassName = callerClass.getName();

			if (ServerDetector.isGeronimo()) {
				if (callerClassName.equals(_ClASS_NAME_COMPILER) ||
					callerClassName.startsWith(
						_CLASS_NAME_JASPER_SERVLET_CONTEXT_CUSTOMIZER) ||
					callerClassName.equals(_ClASS_NAME_TAG_HANDLER_POOL)) {

					String actualClassLocation = PACLClassUtil.getClassLocation(
						callerClass);
					String expectedClassLocation = PathUtil.toUnixPath(
						System.getProperty("org.apache.geronimo.home.dir") +
							"/repository/org/apache/geronimo/");

					allowed = actualClassLocation.contains(
						expectedClassLocation);
				}
			}
			else if (ServerDetector.isGlassfish()) {
				if (callerClassName.equals(_ClASS_NAME_COMPILER) ||
					callerClassName.equals(
						_ClASS_NAME_JSP_COMPILATION_CONTEXT)) {

					String classLocation = PACLClassUtil.getClassLocation(
						callerClass);

					allowed = classLocation.startsWith("bundle://");
				}
			}
			else if (ServerDetector.isJBoss()) {
				if (callerClassName.equals(_ClASS_NAME_COMPILER)) {
					ClassLoader callerClassLoader =
						ClassLoaderUtil.getClassLoader(callerClass);

					String callerClassLoaderString =
						callerClassLoader.toString();

					allowed = callerClassLoaderString.contains(
						_MODULE_NAME_ORG_JBOSS_AS_WEB_MAIN);
				}
			}
			else if (ServerDetector.isJetty()) {
				if (callerClassName.equals(_ClASS_NAME_COMPILER) ||
					callerClassName.equals(_ClASS_NAME_JASPER_LOADER)) {

					ClassLoader callerClassLoader =
						ClassLoaderUtil.getClassLoader(callerClass);

					allowed = (callerClassLoader == _commonClassLoader);
				}
			}
			else if (ServerDetector.isJOnAS()) {
				if (callerClassName.equals(
						_ClASS_NAME_DEFAULT_ANNOTATION_PROCESSOR) ||
					callerClassName.equals(_ClASS_NAME_JASPER_LOADER) ||
					callerClassName.equals(
						_ClASS_NAME_JSP_COMPILATION_CONTEXT)) {

					String classLocation = PACLClassUtil.getClassLocation(
						callerClass);

					allowed = classLocation.startsWith("bundle://");
				}
			}
			else if (ServerDetector.isResin()) {
				if (callerClassName.equals(_ClASS_NAME_JSP_MANAGER) ||
					callerClassName.equals(_ClASS_NAME_PAGE_MANAGER)) {

					String actualClassLocation = PACLClassUtil.getClassLocation(
						callerClass);
					String expectedClassLocation = PathUtil.toUnixPath(
						System.getProperty("resin.home") + "/lib/resin.jar!/");

					allowed = actualClassLocation.contains(
						expectedClassLocation);
				}
			}
			else if (ServerDetector.isTomcat()) {
				if (callerClassName.equals(
						_ClASS_NAME_DEFAULT_INSTANCE_MANAGER) ||
					callerClassName.equals(_ClASS_NAME_COMPILER) ||
					callerClassName.equals(_ClASS_NAME_TAG_HANDLER_POOL)) {

					ClassLoader callerClassLoader =
						ClassLoaderUtil.getClassLoader(callerClass);

					allowed = (callerClassLoader == _commonClassLoader);
				}
			}
			else if (ServerDetector.isWebLogic()) {
				if (callerClassName.equals(_CLASS_NAME_JSP_CLASS_LOADER) ||
					callerClassName.equals(_CLASS_NAME_JSP_STUB)) {

					String classLocation = PACLClassUtil.getClassLocation(
						callerClass);

					allowed = classLocation.contains(
						"/wlserver/server/lib/weblogic.jar!/");
				}
			}
			else if (ServerDetector.isWebSphere()) {
				if (callerClassName.equals(
						_CLASS_NAME_JSP_EXTENSION_CLASS_LOADER) ||
					callerClassName.equals(_ClASS_NAME_JSP_TRANSLATOR_UTIL)) {

					String classLocation = PACLClassUtil.getClassLocation(
						callerClass);

					return classLocation.startsWith("bundleresource://");
				}
			}

			if (allowed == null) {
				continue;
			}

			if (allowed) {
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
			else {
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
	}

	protected boolean isTrustedCallerClass(Class<?> callerClass) {
		String callerClassLocation = PACLClassUtil.getClassLocation(
			callerClass);

		if (callerClassLocation.startsWith(portalImplJarLocation) ||
			callerClassLocation.startsWith(portalServiceJarLocation) ||
			callerClassLocation.contains("/util-bridges.jar!/") ||
			callerClassLocation.contains("/util-java.jar!/") ||
			callerClassLocation.contains("/util-taglib.jar!/")) {

			return true;
		}

		return false;
	}

	protected void logSecurityException(Log log, String message) {
		if (log.isWarnEnabled()) {
			log.warn(message);
		}
	}

	protected String portalImplJarLocation = PACLClassUtil.getJarLocation(
		PortalImpl.class);
	protected String portalServiceJarLocation = PACLClassUtil.getJarLocation(
		PortalUtil.class);

	private static final String _ClASS_NAME_COMPILER =
		"org.apache.jasper.compiler.Compiler";

	private static final String _ClASS_NAME_DEFAULT_ANNOTATION_PROCESSOR =
		"org.apache.catalina.util.DefaultAnnotationProcessor";

	private static final String _ClASS_NAME_DEFAULT_INSTANCE_MANAGER =
		"org.apache.catalina.core.DefaultInstanceManager";

	private static final String _ClASS_NAME_JASPER_LOADER =
		"org.apache.jasper.servlet.JasperLoader";

	private static final String _CLASS_NAME_JASPER_SERVLET_CONTEXT_CUSTOMIZER =
		"org.apache.geronimo.jasper.JasperServletContextCustomizer$";

	private static final String _CLASS_NAME_JSP_CLASS_LOADER =
		"weblogic.servlet.jsp.JspClassLoader";

	private static final String _ClASS_NAME_JSP_COMPILATION_CONTEXT =
		"org.apache.jasper.JspCompilationContext";

	private static final String _CLASS_NAME_JSP_EXTENSION_CLASS_LOADER =
		"com.ibm.ws.jsp.webcontainerext.JSPExtensionClassLoader";

	private static final String _ClASS_NAME_JSP_MANAGER =
		"com.caucho.jsp.JspManager";

	private static final String _CLASS_NAME_JSP_STUB =
		"weblogic.servlet.jsp.JspStub";

	private static final String _ClASS_NAME_JSP_TRANSLATOR_UTIL =
		"com.ibm.ws.jsp.translator.utils.JspTranslatorUtil";

	private static final String _ClASS_NAME_PAGE_MANAGER =
		"com.caucho.jsp.PageManager";

	private static final String _ClASS_NAME_TAG_HANDLER_POOL =
		"org.apache.jasper.runtime.TagHandlerPool";

	private static final String _MODULE_NAME_ORG_JBOSS_AS_WEB_MAIN =
		"org.jboss.as.web:main";

	private static Log _log = LogFactoryUtil.getLog(BaseChecker.class);

	private ClassLoader _commonClassLoader;
	private PACLPolicy _paclPolicy;
	private ClassLoader _portalClassLoader;
	private ClassLoader _systemClassLoader;

}