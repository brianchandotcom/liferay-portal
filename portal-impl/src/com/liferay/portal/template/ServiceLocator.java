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

package com.liferay.portal.template;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.function.Function;

/**
 * @author Brian Wing Shun Chan
 */
public class ServiceLocator {

	public static ServiceLocator getInstance() {
		return _serviceLocator;
	}

	public Object findService(String serviceName) {
		Registry registry = RegistryUtil.getRegistry();

		return registry.callService(serviceName, Function.identity());
	}

	public Object findService(String servletContextName, String serviceName) {
		return findService(serviceName);
	}

	private ServiceLocator() {
	}

	private static final ServiceLocator _serviceLocator = new ServiceLocator();

}