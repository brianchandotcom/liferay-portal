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

package com.liferay.portalweb.blocks.controlpanel.blogs.paths;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPBlogsEntryCommentEditPaths {
	public static Map<String, String[]> getPaths() {
		return _paths;
	}

	private static String[] _PAGE_NAME = {
			"", "Control Panel Blogs Entry Comment Edit"
		};
	private static String[] _BLOGS_COMMENT_FIELD_BODY = {
			"//textarea[contains(@id,'_editReplyBody1')]",
			"Comment Content Body Editor Field"
		};
	private static String[] _BLOGS_COMMENT_LINK_CANCEL = {
			"//input[@value='Cancel']", "Cancel Button"
		};
	private static String[] _BLOGS_COMMENT_LINK_SAVE = {
			"//input[@value='Publish']", "Publish Button"
		};
	private static String[] _BLOGS_COMMENT_TEXT_SUCCESS = {
			"_161_discussion-status-messages",
			"Your request processed successfully. Success Message"
		};
	private static Map<String, String[]> _paths = new HashMap<String, String[]>();

	static {
		_paths.put("PAGE_NAME", _PAGE_NAME);
		_paths.put("BLOGS_COMMENT_FIELD_BODY", _BLOGS_COMMENT_FIELD_BODY);
		_paths.put("BLOGS_COMMENT_LINK_CANCEL", _BLOGS_COMMENT_LINK_CANCEL);
		_paths.put("BLOGS_COMMENT_LINK_SAVE", _BLOGS_COMMENT_LINK_SAVE);
		_paths.put("BLOGS_COMMENT_TEXT_SUCCESS", _BLOGS_COMMENT_TEXT_SUCCESS);
	}
}