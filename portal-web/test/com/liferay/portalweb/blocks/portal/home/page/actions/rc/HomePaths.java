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

package com.liferay.portalweb.blocks.portal.home.page.actions.rc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class HomePaths {
	public static Map<String, String> getPaths() {
		return _paths;
	}

	private static Map<String, String> _paths = new HashMap<String, String>();

	static {
		_paths.put("PAGE_NAME", "");
		_paths.put("ADD_LINK", "//li[@id='_145_addContent']/a/span");
		_paths.put("ADD_LINK_APPLICATION", "_145_addApplication");
		_paths.put("ADD_LINK_PAGE", "addPage");
		_paths.put("PORTLET_FIELD_SEARCH", "layout_configuration_content");
		_paths.put("GOTO_LINK", "//li[@id='_145_mySites']/a/span");
		_paths.put("GOTO_LINK_CONTROL_PANEL", "link=Control Panel");
		_paths.put("MANAGE_LINK", "//li[@id='_145_manageContent']/a/span");
		_paths.put("NEW_PAGE_FIELD", "//input[@type='text']");
		_paths.put("NEW_PAGE_LINK_SAVE", "//button[contains(@id,'Save')]");
		_paths.put("URL_GUEST", "/web/guest/home/");
		_paths.put("URL_SITE", "/web/site-name/home/");
		_paths.put("URL_SITE_1", "/web/site1-name/home/");
		_paths.put("URL_SITE_2", "/web/site2-name/home/");
		_paths.put("URL_SITE_3", "/web/site3-name/home/");
	}
}