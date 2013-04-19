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

package com.liferay.portal.kernel.javadoc;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.lang.reflect.Method;

/**
 * @author Igor Spasic
 */
public class JavadocManagerUtil {

	public static JavadocManager getJavadocManager() {
		PortalRuntimePermission.checkGetBeanProperty(JavadocManagerUtil.class);

		return _javadocManager;
	}

	public static void load(
		String servletContextName, ClassLoader classLoader) {

		getJavadocManager().load(servletContextName, classLoader);
	}

	public static JavadocClass lookupJavadocClass(Class clazz) {
		return getJavadocManager().lookupJavadocClass(clazz);
	}

	public static JavadocMethod lookupJavadocMethod(Method method) {
		return getJavadocManager().lookupJavadocMethod(method);
	}

	public static void unload(String servletContextName) {
		getJavadocManager().unload(servletContextName);
	}

	public void setJavadocManager(JavadocManager javadocManager) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_javadocManager = javadocManager;
	}

	private static JavadocManager _javadocManager;

}