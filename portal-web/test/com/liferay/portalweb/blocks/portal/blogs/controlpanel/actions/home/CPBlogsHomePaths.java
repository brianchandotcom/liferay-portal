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

package com.liferay.portalweb.blocks.portal.blogs.controlpanel.actions.home;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPBlogsHomePaths {
	public static Map<String, String> getPaths() {
		return _paths;
	}

	private static Map<String, String> _paths = new HashMap<String, String>();

	static {
		_paths.put("PAGE_NAME", "");
		_paths.put("BLOGS_ENTRY_LINK_CHECKBOX_ALL",
			"//input[@name='_161_allRowIds']");
		_paths.put("BLOGS_ENTRY_LINK_CHECKBOX",
			"//tr[contains(.,'Blogs Entry Title')]/td[1]/input[@name='_161_rowIds']");
		_paths.put("BLOGS_ENTRY_LINK_CHECKBOX_1",
			"//tr[3]/td[1]/input[@name='_161_rowIds']");
		_paths.put("BLOGS_ENTRY_LINK_CHECKBOX_2",
			"//tr[4]/td[1]/input[@name='_161_rowIds']");
		_paths.put("BLOGS_ENTRY_LINK_CHECKBOX_3",
			"//tr[5]/td[1]/input[@name='_161_rowIds']");
		_paths.put("BLOGS_ENTRY_LINK_TITLE",
			"//tr[contains(.,'Blogs Entry Title')]/td[2]/a");
		_paths.put("BLOGS_ENTRY_LINK_TITLE_1", "//tr[3]/td[2]/a");
		_paths.put("BLOGS_ENTRY_LINK_TITLE_2", "//tr[4]/td[2]/a");
		_paths.put("BLOGS_ENTRY_LINK_TITLE_3", "//tr[5]/td[2]/a");
		_paths.put("BLOGS_ENTRY_LINK_AUTHOR",
			"//tr[contains(.,'Blogs Entry Title')]/td[3]/a");
		_paths.put("BLOGS_ENTRY_LINK_AUTHOR_1", "//tr[3]/td[3]/a");
		_paths.put("BLOGS_ENTRY_LINK_AUTHOR_2", "//tr[4]/td[3]/a");
		_paths.put("BLOGS_ENTRY_LINK_AUTHOR_3", "//tr[5]/td[3]/a");
		_paths.put("BLOGS_ENTRY_LINK_CREATED_DATE",
			"//tr[contains(.,'Blogs Entry Title')]/td[4]/a");
		_paths.put("BLOGS_ENTRY_LINK_CREATED_DATE_1", "//tr[3]/td[4]/a");
		_paths.put("BLOGS_ENTRY_LINK_CREATED_DATE_2", "//tr[4]/td[4]/a");
		_paths.put("BLOGS_ENTRY_LINK_CREATED_DATE_3", "//tr[5]/td[4]/a");
		_paths.put("BLOGS_ENTRY_LINK_STATUS",
			"//tr[contains(.,'Blogs Entry Title')]/td[5]/a");
		_paths.put("BLOGS_ENTRY_LINK_STATUS_1", "//tr[3]/td[5]/a");
		_paths.put("BLOGS_ENTRY_LINK_STATUS_2", "//tr[4]/td[5]/a");
		_paths.put("BLOGS_ENTRY_LINK_STATUS_3", "//tr[5]/td[5]/a");
		_paths.put("BLOGS_ENTRY_LINK_ACTIONS",
			"//tr[contains(.,'Blogs Entry Title')]/td/span/ul/li/strong/a/span");
		_paths.put("BLOGS_ENTRY_LINK_ACTIONS_1",
			"//tr[3]/td/span/ul/li/strong/a/span");
		_paths.put("BLOGS_ENTRY_LINK_ACTIONS_2",
			"//tr[4]/td/span/ul/li/strong/a/span");
		_paths.put("BLOGS_ENTRY_LINK_ACTIONS_3",
			"//tr[5]/td/span/ul/li/strong/a/span");
		_paths.put("BLOGS_ENTRY_LINK_ACTIONS_DELETE",
			"//a[@role='menuitem' and contains(.,'Delete')]");
		_paths.put("BLOGS_ENTRY_LINK_ACTIONS_EDIT",
			"//a[@role='menuitem' and contains(.,'Edit')]");
		_paths.put("BLOGS_ENTRY_LINK_ACTIONS_PERMISSION",
			"//a[@role='menuitem' and contains(.,'Permissions')]");
		_paths.put("BLOGS_ENTRY_LINK_ACTIONS_VIEW",
			"//a[@role='menuitem' and contains(.,'View')]");
		_paths.put("BLOGS_ENTRY_LINK_CONFIRM_DELETE_ACTIONS", "");
		_paths.put("BLOGS_ENTRY_LINK_CONFIRM_DELETE_LIST", "");
		_paths.put("PORTLET_LINK_BREADCRUMB_1",
			"//nav[@id='breadcrumbs']/ul/li[1]/span/a");
		_paths.put("PORTLET_LINK_BREADCRUMB_2",
			"//nav[@id='breadcrumbs']/ul/li[2]/span/a");
		_paths.put("PORTLET_LINK_BREADCRUMB_3",
			"//nav[@id='breadcrumbs']/ul/li[3]/span/a");
		_paths.put("PORTLET_TEXT_TITLE", "//span[@class='portlet-title-text']");
		_paths.put("PORTLET_LINK_OPTIONS",
			"//span[@title='Options']/ul/li/strong/a");
		_paths.put("PORTLET_LINK_OPTIONS_EXPORT_IMPORT",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Export / Import')]");
		_paths.put("PORTLET_TEXT_DESCRIPTION", "cpContextPanelTemplate");
		_paths.put("PORTLET_LINK_ADD", "//span[contains(@class,'add-button')]/a");
		_paths.put("PORTLET_LINK_VIEW_ALL",
			"//span[contains(@class,'view-button')]/a");
		_paths.put("PORTLET_FIELD_SEARCH", "//input[@id='_161_keywords']");
		_paths.put("PORTLET_LINK_SEARCH", "//input[@value='Search']");
		_paths.put("PORTLET_LINK_DELETE",
			"//input[@value='Move to the Recycle Bin']");
		_paths.put("PORTLET_TEXT_RESULTS", "//div[@class='search-results']");
		_paths.put("PORTLET_TEXT_INFO", "//div[@class='portlet-msg-info']");
		_paths.put("PORTLET_TEXT_SUCCESS", "//div[@class='portlet-msg-success']");
		_paths.put("PORTLET_TEXT_SUCCESS_UNDO", "//form[@id='_161_undoForm']");
	}
}