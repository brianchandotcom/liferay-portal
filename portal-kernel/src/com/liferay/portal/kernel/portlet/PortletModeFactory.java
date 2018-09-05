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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletMode;

/**
 * @author Brian Wing Shun Chan
 * @author Neil Griffin
 */
public class PortletModeFactory {

	public static PortletMode getPortletMode(String name) {
		return getPortletMode(name, 2);
	}

	public static PortletMode getPortletMode(
		String name, int portletMajorVersion) {

		if (Validator.isNull(name)) {
			if (portletMajorVersion < 3) {
				return PortletMode.VIEW;
			}

			return PortletMode.UNDEFINED;
		}

		PortletMode portletMode = _portletModes.get(name);

		if (portletMode == null) {
			portletMode = new PortletMode(name);
		}

		return portletMode;
	}

	private static final Map<String, PortletMode> _portletModes =
		new HashMap<>();

	static {
		try {
			for (Field field : LiferayPortletMode.class.getFields()) {
				if (Modifier.isStatic(field.getModifiers()) &&
					(field.getType() == PortletMode.class)) {

					PortletMode portletMode = (PortletMode)field.get(null);

					_portletModes.put(portletMode.toString(), portletMode);
				}
			}
		}
		catch (IllegalAccessException iae) {
			throw new ExceptionInInitializerError(iae);
		}
	}

}