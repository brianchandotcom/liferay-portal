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

package com.liferay.portalweb.blocks.portal.roles.controlpanel.actions.editrole.rc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPRolesEditRolePaths {
	public static Map<String, String> getPaths() {
		return _paths;
	}

	private static Map<String, String> _paths = new HashMap<String, String>();

	static {
		_paths.put("TOP", "relative=top");
		_paths.put("PAGE_NAME", "");
		_paths.put("BREADCRUMB_1", "//div[@id='breadcrumbs']/ul/li[1]/span/a");
		_paths.put("BREADCRUMB_2", "//div[@id='breadcrumbs']/ul/li[2]/span/a");
		_paths.put("BREADCRUMB_3", "//div[@id='breadcrumbs']/ul/li[3]/span/a");
		_paths.put("BREADCRUMB_4", "//div[@id='breadcrumbs']/ul/li[4]/span");
		_paths.put("BREADCRUMB_5", "//div[@id='breadcrumbs']/ul/li[5]/span/a");
		_paths.put("TOOLBAR_EDIT",
			"//a[@class='aui-tab-label' and contains(.,'Edit')]");
		_paths.put("TOOLBAR_DEFINE_PERMISSIONS",
			"//a[@class='aui-tab-label' and contains(.,'Define Permissions')]");
		_paths.put("TOOLBAR_ASSIGN_MEMBERS",
			"//a[@class='aui-tab-label' and contains(.,'Assign Members')]");
		_paths.put("ROLE_TYPE", "//fieldset/div/div/div");
		_paths.put("ROLE_NAME", "//input[@id='_128_name']");
		_paths.put("ROLE_TITLE", "//input[@id='_128_title_en_US']");
		_paths.put("ROLE_DESCRIPTION",
			"//textarea[@id='_128_description_en_US']");
		_paths.put("BUTTON_SAVE", "//input[@value='Save']");
		_paths.put("BUTTON_CANCEL", "//input[@value='Cancel']");
	}
}