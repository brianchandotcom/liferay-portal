/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.messaging.config;

import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.servlet.ServletContextClassLoaderPool;

/**
 * @author Michael C. Han
 */
public class PluginMessagingConfigurator extends BaseMessagingConfigurator {

	@Override
	public void afterPropertiesSet() {
		_servletContextName = PortletClassLoaderUtil.getServletContextName();

		super.afterPropertiesSet();
	}

	@Override
	protected ClassLoader getOperatingClassLoader() {
		ClassLoader classLoader = ServletContextClassLoaderPool.getClassLoader(
			_servletContextName);

		if (classLoader == null) {
			throw new IllegalStateException(
				"Unable to find the class loader for servlet context " +
					_servletContextName);
		}

		return classLoader;
	}

	private String _servletContextName;

}