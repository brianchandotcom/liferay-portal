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

package com.liferay.bean.portlet.cdi.extension.internal.xml;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Neil Griffin
 */
public class LiferayDescriptor {

	public void addConfiguration(
		String portletName, Map<String, String> configuration) {

		_configurations.put(portletName, configuration);
	}

	public Map<String, String> getConfiguration(String portletName) {
		return _configurations.get(portletName);
	}

	public Set<String> getPortletNames() {
		return _configurations.keySet();
	}

	private final Map<String, Map<String, String>> _configurations =
		new HashMap<>();

}