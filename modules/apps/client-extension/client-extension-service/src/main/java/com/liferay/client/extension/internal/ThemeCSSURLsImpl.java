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

package com.liferay.client.extension.internal;

import com.liferay.portal.kernel.client.extension.ThemeCSSURLs;

/**
 * @author Iván Zaera Avellón
 */
public class ThemeCSSURLsImpl implements ThemeCSSURLs {

	public ThemeCSSURLsImpl(String main, String portal) {
		_main = main;
		_portal = portal;
	}

	@Override
	public String getMain() {
		return _main;
	}

	@Override
	public String getPortal() {
		return _portal;
	}

	private final String _main;
	private final String _portal;

}