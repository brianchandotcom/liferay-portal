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
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.security.pacl.PACLClassUtil;

import java.security.Permission;

import sun.reflect.Reflection;

/**
 * @author Brian Wing Shun Chan
 */
public class SecurityChecker extends BaseChecker {

	public void afterPropertiesSet() {
	}

	public void checkPermission(Permission permission) {
		String name = permission.getName();

		if (name.equals(SECURITY_PERMISSION_GET_POLICY)) {
			if (!hasGetPolicy()) {
				throwSecurityException(_log, "Attempted to get the policy");
			}
		}
		else if (name.equals(SECURITY_PERMISSION_SET_POLICY)) {
			if (!hasSetPolicy()) {
				throwSecurityException(_log, "Attempted to set the policy");
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				Thread.dumpStack();
			}

			throwSecurityException(
				_log,
				"Attempted to " + permission.getName() + " on " +
					permission.getActions());
		}
	}

	protected boolean hasGetPolicy() {
		Class<?> callerClass8 = Reflection.getCallerClass(8);

		if (isGlassfishJ2EEInstanceListener(
				callerClass8.getEnclosingClass()) &&
			CheckerUtil.isAccessControllerDoPrivileged(9)) {

			logGetPolicy(callerClass8, 8);

			return true;
		}

		if (isWebSphereWASJSPExtensionServletWrapper(callerClass8)) {
			logGetPolicy(callerClass8, 8);

			return true;
		}

		return false;
	}

	protected boolean hasSetPolicy() {
		Class<?> callerClass6 = Reflection.getCallerClass(6);

		if (isGlassfishPolicyContextHandlerImpl(callerClass6)) {
			logSetPolicy(callerClass6, 6);

			return true;
		}

		Class<?> callerClass7 = Reflection.getCallerClass(7);

		if (isGeronimoDispatchListener(callerClass7)) {
			logSetPolicy(callerClass7, 7);

			return true;
		}

		return false;
	}

	protected boolean isGeronimoDispatchListener(Class<?> clazz) {
		if (!ServerDetector.isGeronimo()) {
			return false;
		}

		if (clazz == null) {
			return false;
		}

		String className = clazz.getName();

		if (!className.equals(_CLASS_NAME_DISPATCH_LISTENER)) {
			return false;
		}

		String classLocation = PACLClassUtil.getClassLocation(clazz);

		return classLocation.contains(
			"/repository/org/apache/geronimo/modules/geronimo-tomcat6/");
	}

	protected boolean isGlassfishJ2EEInstanceListener(Class<?> clazz) {
		if (!ServerDetector.isGlassfish()) {
			return false;
		}

		if (clazz == null) {
			return false;
		}

		String className = clazz.getName();

		if (!className.equals(_CLASS_NAME_J2EE_INSTANCE_LISTENER)) {
			return false;
		}

		String classLocation = PACLClassUtil.getClassLocation(clazz);

		return classLocation.startsWith("bundle://");
	}

	protected boolean isGlassfishPolicyContextHandlerImpl(Class<?> clazz) {
		if (!ServerDetector.isGlassfish()) {
			return false;
		}

		if (clazz == null) {
			return false;
		}

		String className = clazz.getName();

		if (!className.equals(_CLASS_NAME_POLICY_CONTEXT_HANDLER_IMPL)) {
			return false;
		}

		String classLocation = PACLClassUtil.getClassLocation(clazz);

		return classLocation.startsWith("bundle://");
	}

	protected boolean isWebSphereWASJSPExtensionServletWrapper(Class<?> clazz) {
		if (!ServerDetector.isWebSphere()) {
			return false;
		}

		String className = clazz.getName();

		if (!className.equals(_CLASS_NAME_WAS_JSP_EXTENSION_SERVLET_WRAPPER)) {
			return false;
		}

		String classLocation = PACLClassUtil.getClassLocation(clazz);

		return classLocation.startsWith("bundleresource://");
	}

	protected void logGetPolicy(Class<?> callerClass, int frame) {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Allowing frame " + frame + " with caller " + callerClass +
					" to get the policy");
		}
	}

	protected void logSetPolicy(Class<?> callerClass, int frame) {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Allowing frame " + frame + " with caller " + callerClass +
					" to set the policy");
		}
	}

	private static final String _CLASS_NAME_DISPATCH_LISTENER =
		"org.apache.geronimo.tomcat.listener.DispatchListener";

	private static final String _CLASS_NAME_J2EE_INSTANCE_LISTENER =
		"com.sun.web.server.J2EEInstanceListener";

	private static final String _CLASS_NAME_POLICY_CONTEXT_HANDLER_IMPL =
		"com.sun.enterprise.security.authorize.PolicyContextHandlerImpl";

	private static final String _CLASS_NAME_WAS_JSP_EXTENSION_SERVLET_WRAPPER =
		"com.ibm.ws.jsp.webcontainerext.ws.WASJSPExtensionServletWrapper";

	private static Log _log = LogFactoryUtil.getLog(SecurityChecker.class);

}