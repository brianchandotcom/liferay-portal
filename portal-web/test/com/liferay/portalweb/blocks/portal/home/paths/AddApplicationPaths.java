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

package com.liferay.portalweb.blocks.portal.home.paths;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class AddApplicationPaths {
	public static Map<String, String[]> getPaths() {
		return _paths;
	}

	private static String[] _COMMUNITY_LINK = {
			"//h2/span[.='Community']", "Community"
		};
	private static String[] _COMMUNITY_LINK_PAGE_COMMENTS = {
			"//div[@title='Page Comments']/p", "Page Comments"
		};
	private static String[] _COMMUNITY_LINK_PAGE_COMMENTS_ADD = {
			"//div[@title='Page Comments']/p/a", "Add"
		};
	private static String[] _PORTLET_FIELD_SEARCH = {
			"layout_configuration_content", ""
		};
	private static Map<String, String[]> _paths = new HashMap<String, String[]>();

	static {
		_paths.put("COMMUNITY_LINK", _COMMUNITY_LINK);
		_paths.put("COMMUNITY_LINK_PAGE_COMMENTS", _COMMUNITY_LINK_PAGE_COMMENTS);
		_paths.put("COMMUNITY_LINK_PAGE_COMMENTS_ADD",
			_COMMUNITY_LINK_PAGE_COMMENTS_ADD);
		_paths.put("PORTLET_FIELD_SEARCH", _PORTLET_FIELD_SEARCH);
	}
}