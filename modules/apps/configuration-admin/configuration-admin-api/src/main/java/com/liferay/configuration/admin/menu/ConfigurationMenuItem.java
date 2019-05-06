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

package com.liferay.configuration.admin.menu;

import java.util.Dictionary;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Drew Brokke  Contributes a menu item to be displayed in the
 *         auto-generated of a particular configuration. Implementations must be
 *         registered as a ConfigurationMenuItem service, and must have the
 *         property "configuration.pid" whose value matches the ID of the
 *         corresponding configuration interface (usually the fully qualified
 *         class name).
 * @review
 */
public interface ConfigurationMenuItem {

	public String getLabel(Locale locale);

	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse,
		String pid, String factoryPid, Dictionary<String, Object> properties);

}