/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletURLListener;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletURLListenerImpl implements PortletURLListener {

	public PortletURLListenerImpl(String listenerClass, PortletApp portletApp) {
		_listenerClass = listenerClass;
		_portletApp = portletApp;
	}

	@Override
	public String getListenerClass() {
		return _listenerClass;
	}

	@Override
	public PortletApp getPortletApp() {
		return _portletApp;
	}

	@Override
	public void setListenerClass(String listenerClass) {
		_listenerClass = listenerClass;
	}

	@Override
	public void setPortletApp(PortletApp portletApp) {
		_portletApp = portletApp;
	}

	private String _listenerClass;
	private PortletApp _portletApp;

}