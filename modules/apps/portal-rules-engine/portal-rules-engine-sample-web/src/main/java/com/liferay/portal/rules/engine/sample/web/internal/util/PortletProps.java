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

package com.liferay.portal.rules.engine.sample.web.internal.util;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;

/**
 * @author Marcellus Tavares
 */
public class PortletProps {

	public static String get(String key) {
		return _portletProps._configuration.get(key);
	}

	private PortletProps() {
		Class<?> clazz = getClass();

		_configuration = ConfigurationFactoryUtil.getConfiguration(
			clazz.getClassLoader(), "portlet");
	}

	private static final PortletProps _portletProps = new PortletProps();

	private final Configuration _configuration;

}