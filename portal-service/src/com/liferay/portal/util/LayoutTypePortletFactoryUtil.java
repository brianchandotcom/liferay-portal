/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;

/**
 * @author Raymond Augé
 */
public class LayoutTypePortletFactoryUtil {

	public static LayoutTypePortlet create(Layout layout) {
		return getLayoutTypePortletFactory().create(layout);
	}

	public static LayoutTypePortletFactory getLayoutTypePortletFactory() {
		PortalRuntimePermission.checkGetBeanProperty(
			LayoutTypePortletFactoryUtil.class);

		return _layoutTypePortletFactory;
	}

	public void setLayoutTypePortletFactory(
		LayoutTypePortletFactory layoutTypePortletFactory) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_layoutTypePortletFactory = layoutTypePortletFactory;
	}

	private static LayoutTypePortletFactory _layoutTypePortletFactory;

}