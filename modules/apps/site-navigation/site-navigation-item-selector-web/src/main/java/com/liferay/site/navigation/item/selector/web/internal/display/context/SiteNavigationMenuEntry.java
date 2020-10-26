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

package com.liferay.site.navigation.item.selector.web.internal.display.context;

/**
 * @author Eudaldo Alonso
 */
public class SiteNavigationMenuEntry {

	public static SiteNavigationMenuEntry of(String name, String url) {
		return new SiteNavigationMenuEntry(name, url);
	}

	public SiteNavigationMenuEntry(String name, String url) {
		_name = name;
		_url = url;
	}

	public String getName() {
		return _name;
	}

	public String getURL() {
		return _url;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setURL(String url) {
		_url = url;
	}

	private String _name;
	private String _url;

}