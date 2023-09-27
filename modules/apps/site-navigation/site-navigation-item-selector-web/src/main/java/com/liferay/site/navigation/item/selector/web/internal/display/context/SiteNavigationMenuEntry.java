/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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