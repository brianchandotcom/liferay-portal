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

package com.liferay.portal.kernel.resiliency.spi.remote;

import com.liferay.portal.kernel.process.ProcessCallable;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Shuyang Zhou
 */
public class SystemPropertiesProcessCallable
	implements ProcessCallable<Serializable> {

	public SystemPropertiesProcessCallable(Map<String, String> propertiesMap) {
		_propertiesMap = new HashMap<>(propertiesMap);
	}

	@Override
	public Serializable call() {
		Properties systemProperties = System.getProperties();

		systemProperties.putAll(_propertiesMap);

		return null;
	}

	private static final long serialVersionUID = 1L;

	private final Map<String, String> _propertiesMap;

}