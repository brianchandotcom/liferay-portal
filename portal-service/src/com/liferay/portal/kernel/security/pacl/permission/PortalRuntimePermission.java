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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.security.BasicPermission;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PortalRuntimePermission extends BasicPermission {

	public static void checkExpandoBridge(String className) {
		_pacl.checkExpandoBridge(className);
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

	public static void checkGetClassLoader(String classLoaderReferenceId) {
		_pacl.checkGetClassLoader(classLoaderReferenceId);
	}

	public static void checkPortletBagPool(String portletId) {
		_pacl.checkPortletBagPool(portletId);
	}

	public static void checkSearchEngine(String searchEngineId) {
		_pacl.checkSearchEngine(searchEngineId);
	}

	public static void checkSetBeanProperty(Class<?> clazz) {
		_pacl.checkSetBeanProperty("portal", clazz, null);
	}

	public static void checkSetBeanProperty(Class<?> clazz, String property) {
		_pacl.checkSetBeanProperty("portal", clazz, property);
	}

	public static void checkSetBeanProperty(
		String servletContextName, Class<?> clazz) {

		_pacl.checkSetBeanProperty(servletContextName, clazz, null);
	}

	public static void checkSetBeanProperty(
		String servletContextName, Class<?> clazz, String property) {

		_pacl.checkSetBeanProperty(servletContextName, clazz, property);
	}

	public static void checkThreadPoolExecutor(String name) {
		_pacl.checkThreadPoolExecutor(name);
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
		if (Validator.isNull(_servletContextName)) {
			return "portal";
		}

		return _servletContextName;
	}

	public Object getSubject() {
		return _subject;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{class=");

		Class<?> clazz = getClass();

		sb.append(clazz.getName());

		sb.append(", name=");
		sb.append(getName());

		if (_property != null) {
			sb.append(", property=");
			sb.append(_property);
		}

		sb.append(", servletContextName=");
		sb.append(getServletContextName());
		sb.append(", subject=");
		sb.append(getSubject());
		sb.append("}");

		return sb.toString();
	}

	/**
	 * This method ensures the calls stack is the proper length.
	 */
	private static void _checkGetBeanProperty(
		String servletContextName, Class<?> clazz, String property) {

		_pacl.checkGetBeanProperty(servletContextName, clazz, property);
	}

	private static PACL _pacl = new NoPACL();

	private String _property;
	private String _servletContextName;
	private Object _subject;

	private static class NoPACL implements PACL {

		public void checkExpandoBridge(String className) {
		}

		public void checkGetBeanProperty(
			String servletContextName, Class<?> clazz, String property) {
		}

		public void checkGetClassLoader(String classLoaderReferenceId) {
		}

		public void checkPortletBagPool(String portletId) {
		}

		public void checkSearchEngine(String searchEngineId) {
		}

		public void checkSetBeanProperty(
			String servletContextName, Class<?> clazz, String property) {
		}

		public void checkThreadPoolExecutor(String name) {
		}

	}

	public static interface PACL {

		public void checkExpandoBridge(String className);

		public void checkGetBeanProperty(
			String servletContextName, Class<?> clazz, String property);

		public void checkGetClassLoader(String classLoaderReferenceId);

		public void checkPortletBagPool(String portletId);

		public void checkSearchEngine(String searchEngineId);

		public void checkSetBeanProperty(
			String servletContextName, Class<?> clazz, String property);

		public void checkThreadPoolExecutor(String name);

	}

}