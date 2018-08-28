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

package com.liferay.asset.list.item.selector.web.internal.display.context;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class AssetListItemSelectorViewDisplayContext {

	public AssetListItemSelectorViewDisplayContext(
		HttpServletRequest request, String eventName, PortletURL portletURL) {

		_request = request;
		_eventName = eventName;
		_portletURL = portletURL;
	}

	private final String _eventName;
	private final PortletURL _portletURL;
	private final HttpServletRequest _request;

}