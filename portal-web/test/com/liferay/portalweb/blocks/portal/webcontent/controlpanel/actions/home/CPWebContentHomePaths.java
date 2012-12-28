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

package com.liferay.portalweb.blocks.portal.webcontent.controlpanel.actions.home;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPWebContentHomePaths {
	public static Map<String, String> getPaths() {
		return _paths;
	}

	private static Map<String, String> _paths = new HashMap<String, String>();

	static {
		_paths.put("PAGE_NAME", "");
		_paths.put("BREADCRUMB_1", "//nav[@id='breadcrumbs']/ul/li[1]/span/a");
		_paths.put("BREADCRUMB_2", "//nav[@id='breadcrumbs']/ul/li[2]/span/a");
		_paths.put("BREADCRUMB_3", "//nav[@id='breadcrumbs']/ul/li[3]/span/a");
		_paths.put("HEADER_PORTLET_TITLE", "//h1[@id='cpPortletTitle']/span");
		_paths.put("HEADER_PORTLET_DESCRIPTION",
			"//div[@id='cpContextPanelTemplate']");
		_paths.put("HEADER_PORTLET_SUCCESS",
			"//div[@class='portlet-msg-success']");
		_paths.put("HEADER_DELETE_WEB_CONTENT_CONFIRM", "");
		_paths.put("OPTIONS", "//span[@title='Options']/ul/li/strong/a/span");
		_paths.put("OPTIONS_CONFIGURATION",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Configuration')]");
		_paths.put("OPTIONS_EXPORT_IMPORT",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Export / Import')]");
		_paths.put("BUTTONS_ALL_CHECKBOX",
			"//input[@id='_15_allRowIdsCheckbox']");
		_paths.put("BUTTONS_ACTIONS",
			"//span[@title='Actions']/ul/li/strong/a/span");
		_paths.put("BUTTONS_ADD", "//span[@title='Add']/ul/li/strong/a/span");
		_paths.put("BUTTONS_MANAGE",
			"//span[@title='Manage']/ul/li/strong/a/span");
		_paths.put("BUTTONS_ICON_VIEW",
			"//span[@class='aui-toolbar-content']/button[1]");
		_paths.put("BUTTONS_DESCRIPTIVIE_VIEW",
			"//span[@class='aui-toolbar-content']/button[2]");
		_paths.put("BUTTONS_LIST_VIEW",
			"//span[@class='aui-toolbar-content']/button[3]");
		_paths.put("BUTTONS_SEARCH_FIELD", "//input[@id='_15_keywords']");
		_paths.put("BUTTONS_SEARCH", "//input[@id='_15_search']");
		_paths.put("BUTTONS_SHOW_ADVANCED_SEARCH",
			"//input[@id='_15_showAdvancedSearch']");
		_paths.put("ACTIONS_DELETE",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Delete')]");
		_paths.put("ACTIONS_EXPIRE",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Expire')]");
		_paths.put("ACTIONS_MOVE",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Move')]");
		_paths.put("ADD_FOLDER",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Folder')]");
		_paths.put("ADD_BASIC_WEB_CONTENT",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Basic Web Content')]");
		_paths.put("MANAGE_STRUCTURES",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Structures')]");
		_paths.put("MANAGE_TEMPLATES",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Templates')]");
		_paths.put("MANAGE_FEEDS",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Feeds')]");
		_paths.put("ADVANCED_SEARCH_MATCH", "//select[@id='_15_andOperator']");
		_paths.put("ADVANCED_SEARCH_ID", "//input[@id='_15_searchArticleId']");
		_paths.put("ADVANCED_SEARCH_TITLE", "//input[@id='_15_title']");
		_paths.put("ADVANCED_SEARCH_DESCRIPTION",
			"//input[@id='_15_description']");
		_paths.put("ADVANCED_SEARCH_CONTENT", "//input[@id='_15_content']");
		_paths.put("ADVANCED_SEARCH_TYPE", "//select[@id='_15_type']");
		_paths.put("ADVANCED_SEARCH_BUTTON",
			"//form[@id='_15_fmAdvancedSearch']/span[2]/span/input");
		_paths.put("LEFT_HOME_MENU_HOME",
			"//div[@id='_15_folderContainer']/ul/li[1]/a/span[2]");
		_paths.put("LEFT_HOME_MENU_HOME_DROP_DOWN",
			"//span[contains(@class,'entry-action')]/span/ul/li/strong/a");
		_paths.put("LEFT_HOME_MENU_EXPAND_FOLDER",
			"//span[@class='expand-folder-arrow']");
		_paths.put("LEFT_HOME_MENU_RECENT",
			"//div[@id='_15_folderContainer']/ul/li[2]/a/span[2]");
		_paths.put("LEFT_HOME_MENU_MINE",
			"//div[@id='_15_folderContainer']/ul/li[3]/a/span[2]");
		_paths.put("HOME_ADD_FOLDER",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Add Folder')]");
		_paths.put("HOME_SUBSCRIBE",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Subscribe')]");
		_paths.put("HOME_PERMISSIONS",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Permissions')]");
		_paths.put("ENTRIES_FOLDER_1",
			"xpath=(//a[contains(@data-folder,'true')])[1]");
		_paths.put("ENTRIES_FOLDER_1_CHECKBOX",
			"//input[@id='_15_rowIdsJournalFolderCheckbox'][1]");
		_paths.put("ENTRIES_FOLDER_1_ACTIONS_MENU",
			"xpath=(//a[contains(@data-folder,'true')])[1]/preceding-sibling::span[contains(@class,'entry-action')]");
		_paths.put("ENTRIES_FOLDER_2",
			"xpath=(//a[contains(@data-folder,'true')])[2]");
		_paths.put("ENTRIES_FOLDER_2_CHECKBOX",
			"//input[@id='_15_rowIdsJournalFolderCheckbox'][2]");
		_paths.put("ENTRIES_FOLDER_2_ACTIONS_MENU",
			"xpath=(//a[contains(@data-folder,'true')])[2]/preceding-sibling::span[contains(@class,'entry-action')]");
		_paths.put("ENTRIES_FOLDER_3",
			"xpath=(//a[contains(@data-folder,'true')])[3]");
		_paths.put("ENTRIES_FOLDER_3_CHECKBOX",
			"//input[@id='_15_rowIdsJournalFolderCheckbox'][3]");
		_paths.put("ENTRIES_FOLDER_3_ACTIONS_MENU",
			"xpath=(//a[contains(@data-folder,'true')])[3]/preceding-sibling::span[contains(@class,'entry-action')]");
		_paths.put("ENTRIES_ARTICLE_1",
			"xpath=(//a[contains(@data-folder,'false')])[1]");
		_paths.put("ENTRIES_ARTICLE_1_CHECKBOX",
			"//input[@id='_15_rowIdsJournalArticleCheckbox'][1]");
		_paths.put("ENTRIES_ARTICLE_1_ACTIONS_MENU",
			"xpath=(//a[contains(@data-folder,'false')])[1]/preceding-sibling::span[contains(@class,'entry-action')]");
		_paths.put("ENTRIES_ARTICLE_2",
			"xpath=(//a[contains(@data-folder,'false')])[2]");
		_paths.put("ENTRIES_ARTICLE_2_CHECKBOX",
			"//input[@id='_15_rowIdsJournalArticleCheckbox'][2]");
		_paths.put("ENTRIES_ARTICLE_2_ACTIONS_MENU",
			"xpath=(//a[contains(@data-folder,'false')])[2]/preceding-sibling::span[contains(@class,'entry-action')]");
		_paths.put("ENTRIES_ARTICLE_3",
			"xpath=(//a[contains(@data-folder,'false')])[3]");
		_paths.put("ENTRIES_ARTICLE_3_CHECKBOX",
			"//input[@id='_15_rowIdsJournalArticleCheckbox'][3]");
		_paths.put("ENTRIES_ARTICLE_3_ACTIONS_MENU",
			"xpath=(//a[contains(@data-folder,'false')])[3]/preceding-sibling::span[contains(@class,'entry-action')]");
		_paths.put("WEB_CONTENT_FOLDER_EDIT",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]");
		_paths.put("WEB_CONTENT_FOLDER_MOVE",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Move')]");
		_paths.put("WEB_CONTENT_FOLDER_DELETE",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Delete')]");
		_paths.put("WEB_CONTENT_FOLDER_ADD_SUBFOLDER",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Add Subfolder')]");
		_paths.put("WEB_CONTENT_FOLDER_EDIT_PERMISSIONS",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Permissions')]");
		_paths.put("WEB_CONTENT_ARTICLE_EDIT",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]");
		_paths.put("WEB_CONTENT_ARTICLE_MOVE",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Move')]");
		_paths.put("WEB_CONTENT_ARTICLE_PERMISSIONS",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Permissions')]");
		_paths.put("WEB_CONTENT_ARTICLE_VIEW",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'View')]");
		_paths.put("WEB_CONTENT_ARTICLE_COPY",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Copy')]");
		_paths.put("WEB_CONTENT_ARTICLE_EXPIRE",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Expire')]");
		_paths.put("WEB_CONTENT_ARTICLE_DELETE",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Delete')]");
		_paths.put("PAGINATION_FIRST",
			"xpath=(//a[contains(@class,'aui-paginator-first-link')])[2]");
		_paths.put("PAGINATION_PREVIOUS",
			"xpath=(//a[contains(@class,'aui-paginator-prev-link')])[2]");
		_paths.put("PAGINATION_NEXT",
			"xpath=(//a[contains(@class,'aui-paginator-next-link')])[2]");
		_paths.put("PAGINATION_LAST",
			"xpath=(//a[contains(@class,'aui-paginator-last-link')])[2]");
		_paths.put("PAGINATION_OPTION",
			"xpath=(//select[contains(@class,'aui-paginator-rows-per-page')])[2]");
	}
}