/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.blocks.portal.home.page.actions;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class HomePaths {
	public static Map<String, String[]> getPaths() {
		return _paths;
	}

	private static String[] _PAGE_NAME = { "", "Portal Home Page" };
	private static String[] _ADD_LINK = {
			"//li[@id='_145_addContent']/a/span", "Dockbar Add Link"
		};
	private static String[] _ADD_LINK_APPLICATION = {
			"_145_addApplication", "More Applications Link"
		};
	private static String[] _ADD_LINK_PAGE = { "addPage", "Add Page Link" };
	private static String[] _PORTLET_FIELD_SEARCH = {
			"layout_configuration_content", ""
		};
	private static String[] _GOTO_LINK = {
			"//li[@id='_145_mySites']/a/span", "Dockbar Go to Link"
		};
	private static String[] _GOTO_LINK_CONTROL_PANEL = {
			"link=Control Panel", "Control Panel"
		};
	private static String[] _MANAGE_LINK = {
			"//li[@id='_145_manageContent']/a/span", "Dockbar Manage Link"
		};
	private static String[] _NEW_PAGE_FIELD = { "//input[@type='text']", "" };
	private static String[] _NEW_PAGE_LINK_SAVE = {
			"//button[contains(@id,'Save')]", ""
		};
	private static String[] _URL_GUEST = { "/web/guest/home/", "Portal Guest URL" };
	private static String[] _URL_SITE = { "/web/site-name/home/", "Site URL" };
	private static String[] _URL_SITE_1 = { "/web/site1-name/home/", "Site URL" };
	private static String[] _URL_SITE_2 = { "/web/site2-name/home/", "Site URL" };
	private static String[] _URL_SITE_3 = { "/web/site3-name/home/", "Site URL" };
	private static Map<String, String[]> _paths = new HashMap<String, String[]>();

	static {
		_paths.put("PAGE_NAME", _PAGE_NAME);
		_paths.put("ADD_LINK", _ADD_LINK);
		_paths.put("ADD_LINK_APPLICATION", _ADD_LINK_APPLICATION);
		_paths.put("ADD_LINK_PAGE", _ADD_LINK_PAGE);
		_paths.put("PORTLET_FIELD_SEARCH", _PORTLET_FIELD_SEARCH);
		_paths.put("GOTO_LINK", _GOTO_LINK);
		_paths.put("GOTO_LINK_CONTROL_PANEL", _GOTO_LINK_CONTROL_PANEL);
		_paths.put("MANAGE_LINK", _MANAGE_LINK);
		_paths.put("NEW_PAGE_FIELD", _NEW_PAGE_FIELD);
		_paths.put("NEW_PAGE_LINK_SAVE", _NEW_PAGE_LINK_SAVE);
		_paths.put("URL_GUEST", _URL_GUEST);
		_paths.put("URL_SITE", _URL_SITE);
		_paths.put("URL_SITE_1", _URL_SITE_1);
		_paths.put("URL_SITE_2", _URL_SITE_2);
		_paths.put("URL_SITE_3", _URL_SITE_3);
	}
}