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

package com.liferay.admin.kernel.util;

/**
 * @author     Eudaldo Alonso
 * @deprecated As of Wilberforce (7.0.x), with a replacement. Theme developers
 *             must eventually switch from using Velocity  templates that
 *             leverage this taglib wrapper mechanism, to using FreeMarker
 *             templates that leverage the
 *             <code>liferay-product-navigation:control-menu</code> tag.
 */
@Deprecated
public class PortalProductNavigationControlMenuApplicationType {

	public interface ProductNavigationControlMenu {

		public static final String CLASS_NAME =
			"com.liferay.admin.kernel.util." +
				"PortalProductNavigationControlMenuApplicationType" +
					"$ProductNavigationControlMenu";

	}

}