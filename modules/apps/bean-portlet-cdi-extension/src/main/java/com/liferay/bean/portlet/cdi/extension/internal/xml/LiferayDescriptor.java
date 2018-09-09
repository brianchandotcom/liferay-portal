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

	public void addPortletConfiguration(
		String portletName, Map<String, String> portletConfiguration) {

		_liferayPortletConfigurations.put(portletName, portletConfiguration);
	}

	public Map<String, String> getPortletConfiguration(String portletName) {
		return _liferayPortletConfigurations.get(portletName);
	}

	public Set<String> getPortletNames() {
		return _liferayPortletConfigurations.keySet();
	}

	private final Map<String, Map<String, String>>
		_liferayPortletConfigurations = new HashMap<>();

}