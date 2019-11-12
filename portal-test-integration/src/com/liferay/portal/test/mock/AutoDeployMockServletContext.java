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

package com.liferay.portal.test.mock;

import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.File;

import java.util.Collections;
import java.util.Objects;

import javax.servlet.ServletRegistration;

import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockServletContext;

/**
 * @author     Cristina González
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Deprecated
public class AutoDeployMockServletContext extends MockServletContext {

	public AutoDeployMockServletContext(ResourceLoader resourceLoader) {
		super(getResourceBasePath(), resourceLoader);
	}

	@Override
	public ServletRegistration getServletRegistration(String servletName) {
		if (Objects.equals(servletName, "Main Servlet")) {
			return (ServletRegistration)ProxyUtil.newProxyInstance(
				ServletRegistration.class.getClassLoader(),
				new Class<?>[] {ServletRegistration.class},
				(proxy, method, args) -> {
					if (Objects.equals(method.getName(), "getMappings")) {
						return Collections.singleton("/c/*");
					}

					return null;
				});
		}

		return null;
	}

	protected static String getResourceBasePath() {
		File file = new File("portal-web/docroot");

		return "file:" + file.getAbsolutePath();
	}

	/**
	 * @see com.liferay.portal.server.capabilities.TomcatServerCapabilities
	 */
	protected Boolean autoDeploy = Boolean.TRUE;

}