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

package com.liferay.portalweb.blocks.portal.controlpanel.webcontent.paths.folder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPWebContentFolderPaths {
	public static Map<String, String[]> getPaths() {
		return _paths;
	}

	private static String[] _PAGE_NAME = {
			"", "Control Panel Web Content Folder Page"
		};
	private static String[] _FOLDER_NAME = { "//input[@id='_15_name']", "" };
	private static String[] _FOLDER_DESCRIPTION = {
			"//textarea[@id='_15_description']", ""
		};
	private static String[] _PERMISSIONS_VIEWABLE_BY = {
			"//select[@id='_15_inputPermissionsViewRole']", "Viewable By"
		};
	private static String[] _PERMISSIONS_MORE_OPTIONS = {
			"//span[@id='_15_inputPermissionsShowOptionsLink']/a",
			"More Options"
		};
	private static String[] _PERMISSIONS_GUES_ACCESS = {
			"//input[@id='_15_guestPermissions_ACCESS']", "Guest Access"
		};
	private static String[] _PERMISSIONS_GUEST_ADD_ARTICLE = {
			"//input[@id='_15_guestPermissions_ADD_ARTICLE']",
			"Guest Add Article"
		};
	private static String[] _PERMISSIONS_GUEST_ADD_SUBFOLDER = {
			"//input[@id='_15_guestPermissions_ADD_SUBFOLDER']",
			"Guest Add Sub Folder"
		};
	private static String[] _PERMISSIONS_GUEST_DELETE = {
			"//input[@id='_15_guestPermissions_DELETE']", "Guest Delete"
		};
	private static String[] _PERMISSIONS_GUEST_PERMISSIONS = {
			"//input[@id='_15_guestPermissions_PERMISSIONS']",
			"Guest Permissions"
		};
	private static String[] _PERMISSIONS_GUEST_UPDATE = {
			"//input[@id='_15_guestPermissions_UPDATE']", "Guest Update"
		};
	private static String[] _PERMISSIONS_SITE_MEMBER_ACCESS = {
			"//input[@id='_15_groupPermissions_ACCESS']", "Site Member Access"
		};
	private static String[] _PERMISSIONS_SITE_MEMBER_ADD_ARTICLE = {
			"//input[@id='_15_groupPermissions_ADD_ARTICLE']",
			"Site Member Add Article"
		};
	private static String[] _PERMISSIONS_SITE_MEMBER_ADD_SUBFOLDER = {
			"//input[@id='_15_groupPermissions_ADD_SUBFOLDER']",
			"Site Member Add Subfolder"
		};
	private static String[] _PERMISSIONS_SITE_MEMBER_DELETE = {
			"//input[@id='_15_groupPermissions_DELETE']", "Site Member Delete"
		};
	private static String[] _PERMISSIONS_SITE_MEMBER_PERMISSIONS = {
			"//input[@id='_15_groupPermissions_PERMISSIONS']",
			"Site Member Permissions"
		};
	private static String[] _PERMISSIONS_SITE_MEMBER_UPDATE = {
			"//input[@id='_15_groupPermissions_UPDATE']", "Site Member Update"
		};
	private static String[] _PERMISSONS_HIDE_OPTIONS = {
			"//a[@id='_15_inputPermissionsHideOptionsLink']", "Hide Options"
		};
	private static String[] _BUTTONS_SAVE = { "//input[@value='Save']", "Save" };
	private static String[] _BUTTONS_CANCEL = {
			"//input[@value='Cancel']", "Cancel"
		};
	private static Map<String, String[]> _paths = new HashMap<String, String[]>();

	static {
		_paths.put("PAGE_NAME", _PAGE_NAME);
		_paths.put("FOLDER_NAME", _FOLDER_NAME);
		_paths.put("FOLDER_DESCRIPTION", _FOLDER_DESCRIPTION);
		_paths.put("PERMISSIONS_VIEWABLE_BY", _PERMISSIONS_VIEWABLE_BY);
		_paths.put("PERMISSIONS_MORE_OPTIONS", _PERMISSIONS_MORE_OPTIONS);
		_paths.put("PERMISSIONS_GUES_ACCESS", _PERMISSIONS_GUES_ACCESS);
		_paths.put("PERMISSIONS_GUEST_ADD_ARTICLE",
			_PERMISSIONS_GUEST_ADD_ARTICLE);
		_paths.put("PERMISSIONS_GUEST_ADD_SUBFOLDER",
			_PERMISSIONS_GUEST_ADD_SUBFOLDER);
		_paths.put("PERMISSIONS_GUEST_DELETE", _PERMISSIONS_GUEST_DELETE);
		_paths.put("PERMISSIONS_GUEST_PERMISSIONS",
			_PERMISSIONS_GUEST_PERMISSIONS);
		_paths.put("PERMISSIONS_GUEST_UPDATE", _PERMISSIONS_GUEST_UPDATE);
		_paths.put("PERMISSIONS_SITE_MEMBER_ACCESS",
			_PERMISSIONS_SITE_MEMBER_ACCESS);
		_paths.put("PERMISSIONS_SITE_MEMBER_ADD_ARTICLE",
			_PERMISSIONS_SITE_MEMBER_ADD_ARTICLE);
		_paths.put("PERMISSIONS_SITE_MEMBER_ADD_SUBFOLDER",
			_PERMISSIONS_SITE_MEMBER_ADD_SUBFOLDER);
		_paths.put("PERMISSIONS_SITE_MEMBER_DELETE",
			_PERMISSIONS_SITE_MEMBER_DELETE);
		_paths.put("PERMISSIONS_SITE_MEMBER_PERMISSIONS",
			_PERMISSIONS_SITE_MEMBER_PERMISSIONS);
		_paths.put("PERMISSIONS_SITE_MEMBER_UPDATE",
			_PERMISSIONS_SITE_MEMBER_UPDATE);
		_paths.put("PERMISSONS_HIDE_OPTIONS", _PERMISSONS_HIDE_OPTIONS);
		_paths.put("BUTTONS_SAVE", _BUTTONS_SAVE);
		_paths.put("BUTTONS_CANCEL", _BUTTONS_CANCEL);
	}
}