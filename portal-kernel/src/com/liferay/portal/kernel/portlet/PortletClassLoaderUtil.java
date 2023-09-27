/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.portlet;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.servlet.ServletContextClassLoaderPool;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletClassLoaderUtil {

	public static ClassLoader getClassLoader() {
		String servletContextName = getServletContextName();

		ClassLoader classLoader = ServletContextClassLoaderPool.getClassLoader(
			servletContextName);

		if (classLoader == null) {
			throw new IllegalStateException(
				"Unable to find the class loader for servlet context " +
					servletContextName);
		}

		return classLoader;
	}

	public static String getServletContextName() {
		String servletContextName = _servletContextName.get();

		if (servletContextName == null) {
			throw new IllegalStateException(
				"No servlet context name specified");
		}

		return servletContextName;
	}

	public static void setServletContextName(String servletContextName) {
		_servletContextName.set(servletContextName);
	}

	private static final ThreadLocal<String> _servletContextName =
		new CentralizedThreadLocal<>(
			PortletClassLoaderUtil.class + "._servletContextName");

}