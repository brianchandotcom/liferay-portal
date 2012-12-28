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
	public static Map<String, String[]> getPaths() {
		return _paths;
	}

	private static String[] _PAGE_NAME = {
			"", "Control Panel Web Content Home Page"
		};
	private static String[] _BREADCRUMB_1 = {
			"//nav[@id='breadcrumbs']/ul/li[1]/span/a", "Breadcrumb 1"
		};
	private static String[] _BREADCRUMB_2 = {
			"//nav[@id='breadcrumbs']/ul/li[2]/span/a", "Breadcrumb 2"
		};
	private static String[] _BREADCRUMB_3 = {
			"//nav[@id='breadcrumbs']/ul/li[3]/span/a", "Breadcrumb 3"
		};
	private static String[] _HEADER_PORTLET_TITLE = {
			"//h1[@id='cpPortletTitle']/span", "Portlet Title"
		};
	private static String[] _HEADER_PORTLET_DESCRIPTION = {
			"//div[@id='cpContextPanelTemplate']", "Portlet Description"
		};
	private static String[] _HEADER_PORTLET_SUCCESS = {
			"//div[@class='portlet-msg-success']", "Portlet Succes Message"
		};
	private static String[] _HEADER_DELETE_WEB_CONTENT_CONFIRM = {
			"", "Confirmation Message"
		};
	private static String[] _OPTIONS = {
			"//span[@title='Options']/ul/li/strong/a/span", "Options"
		};
	private static String[] _OPTIONS_CONFIGURATION = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Configuration')]",
			"Configuration"
		};
	private static String[] _OPTIONS_EXPORT_IMPORT = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Export / Import')]",
			"Export / Import"
		};
	private static String[] _BUTTONS_ALL_CHECKBOX = {
			"//input[@id='_15_allRowIdsCheckbox']", "All Checkbox"
		};
	private static String[] _BUTTONS_ACTIONS = {
			"//span[@title='Actions']/ul/li/strong/a/span",
			"Actions Drop Down Button"
		};
	private static String[] _BUTTONS_ADD = {
			"//span[@title='Add']/ul/li/strong/a/span", "Add Drop Down Button"
		};
	private static String[] _BUTTONS_MANAGE = {
			"//span[@title='Manage']/ul/li/strong/a/span",
			"Manage Drop Down Button"
		};
	private static String[] _BUTTONS_ICON_VIEW = {
			"//span[@class='aui-toolbar-content']/button[1]", "Icon View Icon"
		};
	private static String[] _BUTTONS_DESCRIPTIVIE_VIEW = {
			"//span[@class='aui-toolbar-content']/button[2]",
			"Description View Icon"
		};
	private static String[] _BUTTONS_LIST_VIEW = {
			"//span[@class='aui-toolbar-content']/button[3]", "List View Icon"
		};
	private static String[] _BUTTONS_SEARCH_FIELD = {
			"//input[@id='_15_keywords']", "Search Field"
		};
	private static String[] _BUTTONS_SEARCH = {
			"//input[@id='_15_search']", "Search Button"
		};
	private static String[] _BUTTONS_SHOW_ADVANCED_SEARCH = {
			"//input[@id='_15_showAdvancedSearch']", "Show Advanced Search Icon"
		};
	private static String[] _ACTIONS_DELETE = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Delete')]",
			"Actions Delete Link"
		};
	private static String[] _ACTIONS_EXPIRE = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Expire')]",
			"Actions Expire Link"
		};
	private static String[] _ACTIONS_MOVE = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Move')]",
			"Actions Move Link"
		};
	private static String[] _ADD_FOLDER = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Folder')]",
			"Add Folder Link"
		};
	private static String[] _ADD_BASIC_WEB_CONTENT = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Basic Web Content')]",
			"Add Basic Web Content Link"
		};
	private static String[] _MANAGE_STRUCTURES = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Structures')]",
			"Manage Structures Link"
		};
	private static String[] _MANAGE_TEMPLATES = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Templates')]",
			"Manage Templates Link"
		};
	private static String[] _MANAGE_FEEDS = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Feeds')]",
			"Manage Feeds Link"
		};
	private static String[] _ADVANCED_SEARCH_MATCH = {
			"//select[@id='_15_andOperator']", "Match Search"
		};
	private static String[] _ADVANCED_SEARCH_ID = {
			"//input[@id='_15_searchArticleId']", "Search Id Field"
		};
	private static String[] _ADVANCED_SEARCH_TITLE = {
			"//input[@id='_15_title']", "Search Title Field"
		};
	private static String[] _ADVANCED_SEARCH_DESCRIPTION = {
			"//input[@id='_15_description']", "Search Description Field"
		};
	private static String[] _ADVANCED_SEARCH_CONTENT = {
			"//input[@id='_15_content']", "Search Content Field"
		};
	private static String[] _ADVANCED_SEARCH_TYPE = {
			"//select[@id='_15_type']", "Search Type Field"
		};
	private static String[] _ADVANCED_SEARCH_BUTTON = {
			"//form[@id='_15_fmAdvancedSearch']/span[2]/span/input",
			"Advanced Search Button"
		};
	private static String[] _LEFT_HOME_MENU_HOME = {
			"//div[@id='_15_folderContainer']/ul/li[1]/a/span[2]", "Home"
		};
	private static String[] _LEFT_HOME_MENU_HOME_DROP_DOWN = {
			"//span[contains(@class,'entry-action')]/span/ul/li/strong/a",
			"Home Drop Down"
		};
	private static String[] _LEFT_HOME_MENU_EXPAND_FOLDER = {
			"//span[@class='expand-folder-arrow']", "Expand Folder"
		};
	private static String[] _LEFT_HOME_MENU_RECENT = {
			"//div[@id='_15_folderContainer']/ul/li[2]/a/span[2]", "Recent"
		};
	private static String[] _LEFT_HOME_MENU_MINE = {
			"//div[@id='_15_folderContainer']/ul/li[3]/a/span[2]", "Mine"
		};
	private static String[] _HOME_ADD_FOLDER = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Add Folder')]",
			"Add Folder"
		};
	private static String[] _HOME_SUBSCRIBE = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Subscribe')]",
			"Subscribe"
		};
	private static String[] _HOME_PERMISSIONS = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Permissions')]",
			"Permissions"
		};
	private static String[] _ENTRIES_FOLDER_1 = {
			"xpath=(//a[contains(@data-folder,'true')])[1]", "First Folder"
		};
	private static String[] _ENTRIES_FOLDER_1_CHECKBOX = {
			"//input[@id='_15_rowIdsJournalFolderCheckbox'][1]",
			"First Folder's Checkbox"
		};
	private static String[] _ENTRIES_FOLDER_1_ACTIONS_MENU = {
			"xpath=(//a[contains(@data-folder,'true')])[1]/preceding-sibling::span[contains(@class,'entry-action')]",
			"First Folder's Menu"
		};
	private static String[] _ENTRIES_FOLDER_2 = {
			"xpath=(//a[contains(@data-folder,'true')])[2]", "Second Folder"
		};
	private static String[] _ENTRIES_FOLDER_2_CHECKBOX = {
			"//input[@id='_15_rowIdsJournalFolderCheckbox'][2]",
			"Second Folder's Checkbox"
		};
	private static String[] _ENTRIES_FOLDER_2_ACTIONS_MENU = {
			"xpath=(//a[contains(@data-folder,'true')])[2]/preceding-sibling::span[contains(@class,'entry-action')]",
			"Second Folder's Menu"
		};
	private static String[] _ENTRIES_FOLDER_3 = {
			"xpath=(//a[contains(@data-folder,'true')])[3]", "Third Folder"
		};
	private static String[] _ENTRIES_FOLDER_3_CHECKBOX = {
			"//input[@id='_15_rowIdsJournalFolderCheckbox'][3]",
			"Third Folder's Checkbox"
		};
	private static String[] _ENTRIES_FOLDER_3_ACTIONS_MENU = {
			"xpath=(//a[contains(@data-folder,'true')])[3]/preceding-sibling::span[contains(@class,'entry-action')]",
			"Third Folder's Menu"
		};
	private static String[] _ENTRIES_ARTICLE_1 = {
			"xpath=(//a[contains(@data-folder,'false')])[1]", "First Article"
		};
	private static String[] _ENTRIES_ARTICLE_1_CHECKBOX = {
			"//input[@id='_15_rowIdsJournalArticleCheckbox'][1]",
			"First Article's Checkbox"
		};
	private static String[] _ENTRIES_ARTICLE_1_ACTIONS_MENU = {
			"xpath=(//a[contains(@data-folder,'false')])[1]/preceding-sibling::span[contains(@class,'entry-action')]",
			"First Article's Menu"
		};
	private static String[] _ENTRIES_ARTICLE_2 = {
			"xpath=(//a[contains(@data-folder,'false')])[2]", "Second Article"
		};
	private static String[] _ENTRIES_ARTICLE_2_CHECKBOX = {
			"//input[@id='_15_rowIdsJournalArticleCheckbox'][2]",
			"Second Article's Checkbox"
		};
	private static String[] _ENTRIES_ARTICLE_2_ACTIONS_MENU = {
			"xpath=(//a[contains(@data-folder,'false')])[2]/preceding-sibling::span[contains(@class,'entry-action')]",
			"Second Article's Menu"
		};
	private static String[] _ENTRIES_ARTICLE_3 = {
			"xpath=(//a[contains(@data-folder,'false')])[3]", "Third Article"
		};
	private static String[] _ENTRIES_ARTICLE_3_CHECKBOX = {
			"//input[@id='_15_rowIdsJournalArticleCheckbox'][3]",
			"Third Article's Checkbox"
		};
	private static String[] _ENTRIES_ARTICLE_3_ACTIONS_MENU = {
			"xpath=(//a[contains(@data-folder,'false')])[3]/preceding-sibling::span[contains(@class,'entry-action')]",
			"Third Article's Menu"
		};
	private static String[] _WEB_CONTENT_FOLDER_EDIT = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]",
			"Edit Folder Link"
		};
	private static String[] _WEB_CONTENT_FOLDER_MOVE = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Move')]",
			"Move Folder Link"
		};
	private static String[] _WEB_CONTENT_FOLDER_DELETE = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Delete')]",
			"Delete Folder Link"
		};
	private static String[] _WEB_CONTENT_FOLDER_ADD_SUBFOLDER = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Add Subfolder')]",
			"Add Subfolder Link"
		};
	private static String[] _WEB_CONTENT_FOLDER_EDIT_PERMISSIONS = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Permissions')]",
			"Folder Permissions Link"
		};
	private static String[] _WEB_CONTENT_ARTICLE_EDIT = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]",
			"Edit Article Link"
		};
	private static String[] _WEB_CONTENT_ARTICLE_MOVE = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Move')]",
			"Move Article Link"
		};
	private static String[] _WEB_CONTENT_ARTICLE_PERMISSIONS = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Permissions')]",
			"Article Permissions Link"
		};
	private static String[] _WEB_CONTENT_ARTICLE_VIEW = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'View')]",
			"View Article Link"
		};
	private static String[] _WEB_CONTENT_ARTICLE_COPY = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Copy')]",
			"Copy Article Link"
		};
	private static String[] _WEB_CONTENT_ARTICLE_EXPIRE = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Expire')]",
			"Expire Article Link"
		};
	private static String[] _WEB_CONTENT_ARTICLE_DELETE = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Delete')]",
			"Delete Article Link"
		};
	private static String[] _PAGINATION_FIRST = {
			"xpath=(//a[contains(@class,'aui-paginator-first-link')])[2]",
			"First Icon"
		};
	private static String[] _PAGINATION_PREVIOUS = {
			"xpath=(//a[contains(@class,'aui-paginator-prev-link')])[2]",
			"Previous Icon"
		};
	private static String[] _PAGINATION_NEXT = {
			"xpath=(//a[contains(@class,'aui-paginator-next-link')])[2]",
			"Next Icon"
		};
	private static String[] _PAGINATION_LAST = {
			"xpath=(//a[contains(@class,'aui-paginator-last-link')])[2]",
			"Last Icon"
		};
	private static String[] _PAGINATION_OPTION = {
			"xpath=(//select[contains(@class,'aui-paginator-rows-per-page')])[2]",
			"Pagination Option"
		};
	private static Map<String, String[]> _paths = new HashMap<String, String[]>();

	static {
		_paths.put("PAGE_NAME", _PAGE_NAME);
		_paths.put("BREADCRUMB_1", _BREADCRUMB_1);
		_paths.put("BREADCRUMB_2", _BREADCRUMB_2);
		_paths.put("BREADCRUMB_3", _BREADCRUMB_3);
		_paths.put("HEADER_PORTLET_TITLE", _HEADER_PORTLET_TITLE);
		_paths.put("HEADER_PORTLET_DESCRIPTION", _HEADER_PORTLET_DESCRIPTION);
		_paths.put("HEADER_PORTLET_SUCCESS", _HEADER_PORTLET_SUCCESS);
		_paths.put("HEADER_DELETE_WEB_CONTENT_CONFIRM",
			_HEADER_DELETE_WEB_CONTENT_CONFIRM);
		_paths.put("OPTIONS", _OPTIONS);
		_paths.put("OPTIONS_CONFIGURATION", _OPTIONS_CONFIGURATION);
		_paths.put("OPTIONS_EXPORT_IMPORT", _OPTIONS_EXPORT_IMPORT);
		_paths.put("BUTTONS_ALL_CHECKBOX", _BUTTONS_ALL_CHECKBOX);
		_paths.put("BUTTONS_ACTIONS", _BUTTONS_ACTIONS);
		_paths.put("BUTTONS_ADD", _BUTTONS_ADD);
		_paths.put("BUTTONS_MANAGE", _BUTTONS_MANAGE);
		_paths.put("BUTTONS_ICON_VIEW", _BUTTONS_ICON_VIEW);
		_paths.put("BUTTONS_DESCRIPTIVIE_VIEW", _BUTTONS_DESCRIPTIVIE_VIEW);
		_paths.put("BUTTONS_LIST_VIEW", _BUTTONS_LIST_VIEW);
		_paths.put("BUTTONS_SEARCH_FIELD", _BUTTONS_SEARCH_FIELD);
		_paths.put("BUTTONS_SEARCH", _BUTTONS_SEARCH);
		_paths.put("BUTTONS_SHOW_ADVANCED_SEARCH", _BUTTONS_SHOW_ADVANCED_SEARCH);
		_paths.put("ACTIONS_DELETE", _ACTIONS_DELETE);
		_paths.put("ACTIONS_EXPIRE", _ACTIONS_EXPIRE);
		_paths.put("ACTIONS_MOVE", _ACTIONS_MOVE);
		_paths.put("ADD_FOLDER", _ADD_FOLDER);
		_paths.put("ADD_BASIC_WEB_CONTENT", _ADD_BASIC_WEB_CONTENT);
		_paths.put("MANAGE_STRUCTURES", _MANAGE_STRUCTURES);
		_paths.put("MANAGE_TEMPLATES", _MANAGE_TEMPLATES);
		_paths.put("MANAGE_FEEDS", _MANAGE_FEEDS);
		_paths.put("ADVANCED_SEARCH_MATCH", _ADVANCED_SEARCH_MATCH);
		_paths.put("ADVANCED_SEARCH_ID", _ADVANCED_SEARCH_ID);
		_paths.put("ADVANCED_SEARCH_TITLE", _ADVANCED_SEARCH_TITLE);
		_paths.put("ADVANCED_SEARCH_DESCRIPTION", _ADVANCED_SEARCH_DESCRIPTION);
		_paths.put("ADVANCED_SEARCH_CONTENT", _ADVANCED_SEARCH_CONTENT);
		_paths.put("ADVANCED_SEARCH_TYPE", _ADVANCED_SEARCH_TYPE);
		_paths.put("ADVANCED_SEARCH_BUTTON", _ADVANCED_SEARCH_BUTTON);
		_paths.put("LEFT_HOME_MENU_HOME", _LEFT_HOME_MENU_HOME);
		_paths.put("LEFT_HOME_MENU_HOME_DROP_DOWN",
			_LEFT_HOME_MENU_HOME_DROP_DOWN);
		_paths.put("LEFT_HOME_MENU_EXPAND_FOLDER", _LEFT_HOME_MENU_EXPAND_FOLDER);
		_paths.put("LEFT_HOME_MENU_RECENT", _LEFT_HOME_MENU_RECENT);
		_paths.put("LEFT_HOME_MENU_MINE", _LEFT_HOME_MENU_MINE);
		_paths.put("HOME_ADD_FOLDER", _HOME_ADD_FOLDER);
		_paths.put("HOME_SUBSCRIBE", _HOME_SUBSCRIBE);
		_paths.put("HOME_PERMISSIONS", _HOME_PERMISSIONS);
		_paths.put("ENTRIES_FOLDER_1", _ENTRIES_FOLDER_1);
		_paths.put("ENTRIES_FOLDER_1_CHECKBOX", _ENTRIES_FOLDER_1_CHECKBOX);
		_paths.put("ENTRIES_FOLDER_1_ACTIONS_MENU",
			_ENTRIES_FOLDER_1_ACTIONS_MENU);
		_paths.put("ENTRIES_FOLDER_2", _ENTRIES_FOLDER_2);
		_paths.put("ENTRIES_FOLDER_2_CHECKBOX", _ENTRIES_FOLDER_2_CHECKBOX);
		_paths.put("ENTRIES_FOLDER_2_ACTIONS_MENU",
			_ENTRIES_FOLDER_2_ACTIONS_MENU);
		_paths.put("ENTRIES_FOLDER_3", _ENTRIES_FOLDER_3);
		_paths.put("ENTRIES_FOLDER_3_CHECKBOX", _ENTRIES_FOLDER_3_CHECKBOX);
		_paths.put("ENTRIES_FOLDER_3_ACTIONS_MENU",
			_ENTRIES_FOLDER_3_ACTIONS_MENU);
		_paths.put("ENTRIES_ARTICLE_1", _ENTRIES_ARTICLE_1);
		_paths.put("ENTRIES_ARTICLE_1_CHECKBOX", _ENTRIES_ARTICLE_1_CHECKBOX);
		_paths.put("ENTRIES_ARTICLE_1_ACTIONS_MENU",
			_ENTRIES_ARTICLE_1_ACTIONS_MENU);
		_paths.put("ENTRIES_ARTICLE_2", _ENTRIES_ARTICLE_2);
		_paths.put("ENTRIES_ARTICLE_2_CHECKBOX", _ENTRIES_ARTICLE_2_CHECKBOX);
		_paths.put("ENTRIES_ARTICLE_2_ACTIONS_MENU",
			_ENTRIES_ARTICLE_2_ACTIONS_MENU);
		_paths.put("ENTRIES_ARTICLE_3", _ENTRIES_ARTICLE_3);
		_paths.put("ENTRIES_ARTICLE_3_CHECKBOX", _ENTRIES_ARTICLE_3_CHECKBOX);
		_paths.put("ENTRIES_ARTICLE_3_ACTIONS_MENU",
			_ENTRIES_ARTICLE_3_ACTIONS_MENU);
		_paths.put("WEB_CONTENT_FOLDER_EDIT", _WEB_CONTENT_FOLDER_EDIT);
		_paths.put("WEB_CONTENT_FOLDER_MOVE", _WEB_CONTENT_FOLDER_MOVE);
		_paths.put("WEB_CONTENT_FOLDER_DELETE", _WEB_CONTENT_FOLDER_DELETE);
		_paths.put("WEB_CONTENT_FOLDER_ADD_SUBFOLDER",
			_WEB_CONTENT_FOLDER_ADD_SUBFOLDER);
		_paths.put("WEB_CONTENT_FOLDER_EDIT_PERMISSIONS",
			_WEB_CONTENT_FOLDER_EDIT_PERMISSIONS);
		_paths.put("WEB_CONTENT_ARTICLE_EDIT", _WEB_CONTENT_ARTICLE_EDIT);
		_paths.put("WEB_CONTENT_ARTICLE_MOVE", _WEB_CONTENT_ARTICLE_MOVE);
		_paths.put("WEB_CONTENT_ARTICLE_PERMISSIONS",
			_WEB_CONTENT_ARTICLE_PERMISSIONS);
		_paths.put("WEB_CONTENT_ARTICLE_VIEW", _WEB_CONTENT_ARTICLE_VIEW);
		_paths.put("WEB_CONTENT_ARTICLE_COPY", _WEB_CONTENT_ARTICLE_COPY);
		_paths.put("WEB_CONTENT_ARTICLE_EXPIRE", _WEB_CONTENT_ARTICLE_EXPIRE);
		_paths.put("WEB_CONTENT_ARTICLE_DELETE", _WEB_CONTENT_ARTICLE_DELETE);
		_paths.put("PAGINATION_FIRST", _PAGINATION_FIRST);
		_paths.put("PAGINATION_PREVIOUS", _PAGINATION_PREVIOUS);
		_paths.put("PAGINATION_NEXT", _PAGINATION_NEXT);
		_paths.put("PAGINATION_LAST", _PAGINATION_LAST);
		_paths.put("PAGINATION_OPTION", _PAGINATION_OPTION);
	}
}