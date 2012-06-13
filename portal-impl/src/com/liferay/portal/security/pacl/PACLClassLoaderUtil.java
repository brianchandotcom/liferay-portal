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

import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;

/**
 * @author Raymond Augé
 */
public class PACLClassLoaderUtil {

	public static ClassLoader getClassLoader(Class<?> clazz) {
		boolean checkGetClassLoader =
			PortalSecurityManagerThreadLocal.isCheckGetClassLoader();

		try {
			PortalSecurityManagerThreadLocal.setCheckGetClassLoader(false);

			return clazz.getClassLoader();
		}
		finally {
			PortalSecurityManagerThreadLocal.setCheckGetClassLoader(
				checkGetClassLoader);
		}
	}

	public static ClassLoader getContextClassLoader() {
		boolean checkGetClassLoader =
			PortalSecurityManagerThreadLocal.isCheckGetClassLoader();

		try {
			PortalSecurityManagerThreadLocal.setCheckGetClassLoader(false);

			Thread thread = Thread.currentThread();

			return thread.getContextClassLoader();
		}
		finally {
			PortalSecurityManagerThreadLocal.setCheckGetClassLoader(
				checkGetClassLoader);
		}
	}

	public static ClassLoader getPortalClassLoader() {
		boolean checkGetClassLoader =
			PortalSecurityManagerThreadLocal.isCheckGetClassLoader();

		try {
			PortalSecurityManagerThreadLocal.setCheckGetClassLoader(false);

			return PortalClassLoaderUtil.getClassLoader();
		}
		finally {
			PortalSecurityManagerThreadLocal.setCheckGetClassLoader(
				checkGetClassLoader);
		}
	}

	public static void setContextClassLoader(ClassLoader classLoader) {
		boolean checkGetClassLoader =
			PortalSecurityManagerThreadLocal.isCheckGetClassLoader();

		try {
			PortalSecurityManagerThreadLocal.setCheckGetClassLoader(false);

			Thread thread = Thread.currentThread();

			thread.setContextClassLoader(classLoader);
		}
		finally {
			PortalSecurityManagerThreadLocal.setCheckGetClassLoader(
				checkGetClassLoader);
		}
	}

}