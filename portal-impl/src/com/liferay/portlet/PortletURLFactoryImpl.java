/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.LiferayPortletURL;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletURLFactoryImpl implements PortletURLFactory {

	public LiferayPortletURL create(
		HttpServletRequest request, String portletName, long plid,
		String lifecycle) {

		return new PortletURLImpl(request, portletName, plid, lifecycle);
	}

	public LiferayPortletURL create(
		PortletRequest portletRequest, String portletName, long plid,
		String lifecycle) {

		return new PortletURLImpl(portletRequest, portletName, plid, lifecycle);
	}

}