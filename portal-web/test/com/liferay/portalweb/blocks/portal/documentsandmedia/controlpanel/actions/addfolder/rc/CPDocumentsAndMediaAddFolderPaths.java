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

package com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.actions.addfolder.rc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPDocumentsAndMediaAddFolderPaths {
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
		_paths.put("HEADER_PORTLET_TITLE", "//span[@class='portlet-title-text']");
		_paths.put("HEADER_PORTLET_INFO", "//div[@id='cpContextPanelTemplate']");
		_paths.put("HEADER_OPTIONS_ICON",
			"//span[@title='Options']/ul/li/strong/a");
		_paths.put("HEADER_PAGE_TITLE", "//h1[@class='header-title']/span");
		_paths.put("HEADER_BACK_BUTTON", "//a[@id='_20_TabsBack']");
		_paths.put("CONTENT_NAME", "//input[@id='_20_name']");
		_paths.put("CONTENT_DESCRIPTION", "//textarea[@id='_20_description']");
		_paths.put("BUTTON_SAVE", "//input[@value='Save']");
		_paths.put("BUTTON_CANCEL", "//input[@value='Cancel']");
	}
}