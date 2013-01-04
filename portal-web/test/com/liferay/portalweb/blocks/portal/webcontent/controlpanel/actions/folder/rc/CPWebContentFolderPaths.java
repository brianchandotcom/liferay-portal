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

package com.liferay.portalweb.blocks.portal.webcontent.controlpanel.actions.folder.rc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPWebContentFolderPaths {
	public static Map<String, String> getPaths() {
		return _paths;
	}

	private static Map<String, String> _paths = new HashMap<String, String>();

	static {
		_paths.put("TOP", "relative=top");
		_paths.put("PAGE_NAME", "");
		_paths.put("FOLDER_NAME", "//input[@id='_15_name']");
		_paths.put("FOLDER_DESCRIPTION", "//textarea[@id='_15_description']");
		_paths.put("PERMISSIONS_VIEWABLE_BY",
			"//select[@id='_15_inputPermissionsViewRole']");
		_paths.put("PERMISSIONS_MORE_OPTIONS",
			"//span[@id='_15_inputPermissionsShowOptionsLink']/a");
		_paths.put("PERMISSIONS_GUES_ACCESS",
			"//input[@id='_15_guestPermissions_ACCESS']");
		_paths.put("PERMISSIONS_GUEST_ADD_ARTICLE",
			"//input[@id='_15_guestPermissions_ADD_ARTICLE']");
		_paths.put("PERMISSIONS_GUEST_ADD_SUBFOLDER",
			"//input[@id='_15_guestPermissions_ADD_SUBFOLDER']");
		_paths.put("PERMISSIONS_GUEST_DELETE",
			"//input[@id='_15_guestPermissions_DELETE']");
		_paths.put("PERMISSIONS_GUEST_PERMISSIONS",
			"//input[@id='_15_guestPermissions_PERMISSIONS']");
		_paths.put("PERMISSIONS_GUEST_UPDATE",
			"//input[@id='_15_guestPermissions_UPDATE']");
		_paths.put("PERMISSIONS_SITE_MEMBER_ACCESS",
			"//input[@id='_15_groupPermissions_ACCESS']");
		_paths.put("PERMISSIONS_SITE_MEMBER_ADD_ARTICLE",
			"//input[@id='_15_groupPermissions_ADD_ARTICLE']");
		_paths.put("PERMISSIONS_SITE_MEMBER_ADD_SUBFOLDER",
			"//input[@id='_15_groupPermissions_ADD_SUBFOLDER']");
		_paths.put("PERMISSIONS_SITE_MEMBER_DELETE",
			"//input[@id='_15_groupPermissions_DELETE']");
		_paths.put("PERMISSIONS_SITE_MEMBER_PERMISSIONS",
			"//input[@id='_15_groupPermissions_PERMISSIONS']");
		_paths.put("PERMISSIONS_SITE_MEMBER_UPDATE",
			"//input[@id='_15_groupPermissions_UPDATE']");
		_paths.put("PERMISSONS_HIDE_OPTIONS",
			"//a[@id='_15_inputPermissionsHideOptionsLink']");
		_paths.put("BUTTONS_SAVE", "//input[@value='Save']");
		_paths.put("BUTTONS_CANCEL", "//input[@value='Cancel']");
	}
}