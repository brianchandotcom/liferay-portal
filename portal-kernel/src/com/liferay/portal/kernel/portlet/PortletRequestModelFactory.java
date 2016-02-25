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

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Shuyang Zhou
 */
public class PortletRequestModelFactory {

	public PortletRequestModelFactory(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		_portletRequest = portletRequest;
		_portletResponse = portletResponse;
	}

	public PortletRequestModel getPortletRequestModel() {
		if (_portletRequestModel == null) {
			_portletRequestModel = new PortletRequestModel(
				_portletRequest, _portletResponse);
		}

		return _portletRequestModel;
	}

	private final PortletRequest _portletRequest;
	private PortletRequestModel _portletRequestModel;
	private final PortletResponse _portletResponse;

}