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

package com.liferay.portlet.tck.bridge.setup;

/**
 * @author Vernon Singleton
 */
public class Portlet {

	public Portlet(
		String context, String portletName, String bundleName) {

		_context = context;
		_portletName = portletName;
		_bundleName = bundleName;
	}

	public String getBundleName() {
		return _bundleName;
	}

	public String getContext() {
		return _context;
	}

	public String getPortletName() {
		return _portletName;
	}

	private final String _context;
	private final String _bundleName;
	private final String _portletName;

}