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

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletURLFactoryUtil {

	public static LiferayPortletURL create(
		HttpServletRequest request, Portlet portlet, Layout layout,
		String lifecycle) {

		return getPortletURLFactory().create(
			request, portlet, layout, lifecycle);
	}

	public static LiferayPortletURL create(
		HttpServletRequest request, Portlet portlet, String lifecycle) {

		return getPortletURLFactory().create(request, portlet, lifecycle);
	}

	public static LiferayPortletURL create(
		HttpServletRequest request, String portletId, Layout layout,
		String lifecycle) {

		return getPortletURLFactory().create(
			request, portletId, layout, lifecycle);
	}

	public static LiferayPortletURL create(
		HttpServletRequest request, String portletId, long plid,
		String lifecycle) {

		return getPortletURLFactory().create(
			request, portletId, plid, lifecycle);
	}

	public static LiferayPortletURL create(
		HttpServletRequest request, String portletId, String lifecycle) {

		return getPortletURLFactory().create(request, portletId, lifecycle);
	}

	public static LiferayPortletURL create(
		PortletRequest portletRequest, Portlet portlet, Layout layout,
		String lifecycle) {

		return getPortletURLFactory().create(
			portletRequest, portlet, layout, lifecycle);
	}

	public static LiferayPortletURL create(
		PortletRequest portletRequest, Portlet portlet, long plid,
		String lifecycle) {

		return getPortletURLFactory().create(
			portletRequest, portlet, plid, lifecycle);
	}

	public static LiferayPortletURL create(
		PortletRequest portletRequest, String portletId, Layout layout,
		String lifecycle) {

		return getPortletURLFactory().create(
			portletRequest, portletId, layout, lifecycle);
	}

	public static LiferayPortletURL create(
		PortletRequest portletRequest, String portletId, long plid,
		String lifecycle) {

		return getPortletURLFactory().create(
			portletRequest, portletId, plid, lifecycle);
	}

	public static LiferayPortletURL create(
		PortletRequest portletRequest, String portletId, String lifecycle) {

		return getPortletURLFactory().create(
			portletRequest, portletId, lifecycle);
	}

	public static PortletURLFactory getPortletURLFactory() {
		PortalRuntimePermission.checkGetBeanProperty(
			PortletURLFactoryUtil.class);

		return _portletURLFactory;
	}

	public void setPortletURLFactory(PortletURLFactory portletURLFactory) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_portletURLFactory = portletURLFactory;
	}

	private static PortletURLFactory _portletURLFactory;

}