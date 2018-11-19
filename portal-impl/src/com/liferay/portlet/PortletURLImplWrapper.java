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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayPortletURLWrapper;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.internal.PortletResponseImpl;

import javax.portlet.PortletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletURLImplWrapper extends LiferayPortletURLWrapper {

	public PortletURLImplWrapper(
		PortletResponse portletResponse, long plid, String lifecycle) {

		super(_createLiferayPortletURL(portletResponse, lifecycle));

		setPlid(plid);
	}

	private static LiferayPortletURL _createLiferayPortletURL(
		PortletResponse portletResponse, String lifecycle) {

		LiferayPortletResponse liferayPortletResponse =
			LiferayPortletUtil.getLiferayPortletResponse(portletResponse);

		PortletResponseImpl portletResponseImpl =
			(PortletResponseImpl)liferayPortletResponse;

		return PortletURLFactoryUtil.create(
			portletResponseImpl.getPortletRequest(),
			portletResponseImpl.getPortlet(), null, lifecycle);
	}

}