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

package com.liferay.portal.spring.extender.internal.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;

import org.springframework.context.ApplicationContext;

/**
 * @author Preston Crary
 */
public class ParentModuleApplicationContextHolder {

	public static ApplicationContext getApplicationContext(Bundle bundle) {
		return _applicationContexts.get(bundle.getSymbolicName());
	}

	public static void removeApplicationContext(Bundle bundle) {
		_applicationContexts.remove(bundle.getSymbolicName());
	}

	public static void setApplicationContext(
		Bundle bundle, ApplicationContext applicationContext) {

		_applicationContexts.put(bundle.getSymbolicName(), applicationContext);
	}

	private static final Map<String, ApplicationContext> _applicationContexts =
		new ConcurrentHashMap<>();

}