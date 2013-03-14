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

package com.liferay.portal.kernel.security.pacl.permission;

import com.liferay.portal.kernel.security.pacl.PACLConstants;
import com.liferay.portal.kernel.util.StringBundler;

import java.security.BasicPermission;
import java.security.Permission;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PortalRuntimePermission extends BasicPermission {

	public static void checkExpandoBridge(String className) {
		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager == null) {
			return;
		}

		Permission permission = new PortalRuntimePermission(
			PACLConstants.PORTAL_RUNTIME_PERMISSION_EXPANDO_BRIDGE, "portal",
			className);

		securityManager.checkPermission(permission);
	}

	public static void checkGetBeanProperty(Class<?> clazz) {
		_checkGetBeanProperty("portal", clazz, null);
	}

	public static void checkGetBeanProperty(Class<?> clazz, String property) {
		_checkGetBeanProperty("portal", clazz, property);
	}

	public static void checkGetBeanProperty(
		String servletContextName, Class<?> clazz) {

		_checkGetBeanProperty(servletContextName, clazz, null);
	}

	public static void checkGetBeanProperty(
		String servletContextName, Class<?> clazz, String property) {

		_checkGetBeanProperty(servletContextName, clazz, property);
	}

	public static void checkGetBeanProperty(
		String servletContextName, String className, String property) {

		_checkGetBeanProperty(servletContextName, className, property);
	}

	public static void checkGetClassLoader(String classLoaderReferenceId) {
		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager == null) {
			return;
		}

		Permission permission = new PortalRuntimePermission(
			PACLConstants.PORTAL_RUNTIME_PERMISSION_GET_CLASSLOADER, "portal",
			classLoaderReferenceId);

		securityManager.checkPermission(permission);
	}

	public static void checkSearchEngine(String searchEngineId) {
		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager == null) {
			return;
		}

		Permission permission = new PortalRuntimePermission(
			PACLConstants.PORTAL_RUNTIME_PERMISSION_SEARCH_ENGINE, "portal",
			searchEngineId);

		securityManager.checkPermission(permission);
	}

	public static void checkSetBeanProperty(Class<?> clazz) {
		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager == null) {
			return;
		}

		Permission permission = new PortalRuntimePermission(
			PACLConstants.PORTAL_RUNTIME_PERMISSION_SET_BEAN_PROPERTY, "portal",
			clazz, null);

		securityManager.checkPermission(permission);
	}

	public static void checkSetBeanProperty(Class<?> clazz, String property) {
		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager == null) {
			return;
		}

		Permission permission = new PortalRuntimePermission(
			PACLConstants.PORTAL_RUNTIME_PERMISSION_SET_BEAN_PROPERTY, "portal",
			clazz, property);

		securityManager.checkPermission(permission);
	}

	public static void checkSetBeanProperty(
		String servletContextName, Class<?> clazz) {

		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager == null) {
			return;
		}

		Permission permission = new PortalRuntimePermission(
			PACLConstants.PORTAL_RUNTIME_PERMISSION_SET_BEAN_PROPERTY,
			servletContextName, clazz, null);

		securityManager.checkPermission(permission);
	}

	public static void checkSetBeanProperty(
		String servletContextName, Class<?> clazz, String property) {

		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager == null) {
			return;
		}

		Permission permission = new PortalRuntimePermission(
			PACLConstants.PORTAL_RUNTIME_PERMISSION_SET_BEAN_PROPERTY,
			servletContextName, clazz, property);

		securityManager.checkPermission(permission);
	}

	public static void checkThreadPoolExecutor(String name) {
		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager == null) {
			return;
		}

		Permission permission = new PortalRuntimePermission(
			PACLConstants.PORTAL_RUNTIME_PERMISSION_THREAD_POOL_EXECUTOR,
			"portal", name);

		securityManager.checkPermission(permission);
	}

	public PortalRuntimePermission(
		String name, String servletContextName, Object subject) {

		this(name, servletContextName, subject, null);
	}

	public PortalRuntimePermission(
		String name, String servletContextName, Object subject,
		String property) {

		super(name);

		_servletContextName = servletContextName;
		_property = property;
		_subject = subject;
	}

	public String getProperty() {
		return _property;
	}

	public String getServletContextName() {
		return _servletContextName;
	}

	public Object getSubject() {
		return _subject;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("(");
		sb.append(getClass().getName());
		sb.append(" ");
		sb.append(getName());
		sb.append(" ");
		sb.append(getServletContextName());
		sb.append(" ");
		sb.append(getSubject());

		if (_property != null) {
			sb.append("#");
			sb.append(_property);
		}

		sb.append(")");

		return sb.toString();
	}

	/*
	 * This method ensures the calls stack is the proper length.
	 */
	private static void _checkGetBeanProperty(
		String servletContextName, Class<?> clazz, String property) {

		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager == null) {
			return;
		}

		Permission permission = new PortalRuntimePermission(
			PACLConstants.PORTAL_RUNTIME_PERMISSION_GET_BEAN_PROPERTY,
			servletContextName, clazz, property);

		securityManager.checkPermission(permission);
	}

	/*
	 * This method ensures the calls stack is the proper length.
	 */
	private static void _checkGetBeanProperty(
		String servletContextName, String className, String property) {

		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager == null) {
			return;
		}

		Permission permission = new PortalRuntimePermission(
			PACLConstants.PORTAL_RUNTIME_PERMISSION_GET_BEAN_PROPERTY,
			servletContextName, className, property);

		securityManager.checkPermission(permission);
	}

	private String _property;
	private String _servletContextName;
	private Object _subject;

}