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

package com.liferay.portalweb.blocks.gmail.home.actions;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class GmailHomePaths {
	public static Map<String, String> getPaths() {
		return _paths;
	}

	private static Map<String, String> _paths = new HashMap<String, String>();

	static {
		_paths.put("PAGE_NAME", "");
		_paths.put("LOGIN_FIELD_USERNAME", "//input[@id='Email']");
		_paths.put("LOGIN_FIELD_PASSWORD", "//input[@id='Passwd']");
		_paths.put("EMAIL_LINK_DELETE", "//div[@aria-label='Delete']/div/div");
		_paths.put("LOGIN_LINK_SIGN_IN", "//input[@id='signIn']");
		_paths.put("EMAIL_LIST_LINK_NAME", "//span[@name='Joe Bloggs']");
		_paths.put("EMAIL_LIST_LINK_TITLE_BLOGS_COMMENT",
			"//b[contains(.,'New Comments')]");
	}
}