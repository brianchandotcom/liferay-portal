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

package com.liferay.portlet.admin.util;

/**
 * @author     Eudaldo Alonso
 * @deprecated As of 7.0.0, with a replacement. Theme developers must eventually
 *             switch from using Velocity  templates that leverage this taglib
 *             wrapper mechanism, to using FreeMarker templates that leverage
 *             the <code>liferay-control-menu:control-menu</code> tag.
 */
@Deprecated
public class PortalControlMenuApplicationType {

	public interface ControlMenu {

		public static final String CLASS_NAME =
			"com.liferay.portlet.admin.util." +
				"PortalControlMenuApplicationType$ControlMenu";

	}

}