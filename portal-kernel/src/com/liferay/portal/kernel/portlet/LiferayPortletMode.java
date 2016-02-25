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

import javax.portlet.PortletMode;

/**
 * @author Brian Wing Shun Chan
 */
public class LiferayPortletMode extends PortletMode {

	public static final PortletMode ABOUT = new PortletMode("about");

	public static final PortletMode CONFIG = new PortletMode("config");

	public static final PortletMode EDIT_DEFAULTS = new PortletMode(
		"edit_defaults");

	public static final PortletMode EDIT_GUEST = new PortletMode("edit_guest");

	public static final PortletMode PREVIEW = new PortletMode("preview");

	public static final PortletMode PRINT = new PortletMode("print");

	public LiferayPortletMode(String name) {
		super(name);
	}

}