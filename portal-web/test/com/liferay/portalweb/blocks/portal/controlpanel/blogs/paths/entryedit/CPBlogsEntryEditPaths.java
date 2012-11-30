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

package com.liferay.portalweb.blocks.portal.controlpanel.blogs.paths.entryedit;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPBlogsEntryEditPaths {
	public static Map<String, String[]> getPaths() {
		return _paths;
	}

	private static String[] _PAGE_NAME = { "", "Control Panel Blogs Entry Add" };
	private static String[] _ABSTRACT_FIELD_DESCRIPTION = {
			"_161_description", "Abstract Field Description"
		};
	private static String[] _ABSTRACT_FIELD_SMALL_IMAGE_CHECKBOX = {
			"_161_smallImageCheckbox", "Abstract Use Small Image checkbox"
		};
	private static String[] _ABSTRACT_FIELD_SMALL_IMAGE_URL = {
			"_161_smallImageURL", "Abstract Small Image URL"
		};
	private static String[] _ABSTRACT_FIELD_SMALL_IMAGE = {
			"_161_smallFile", "Abstract Small Image upload field"
		};
	private static String[] _ASSETS_LINK_SELECT = {
			"//span[@title='Select']/ul/li/strong/a/span",
			"Related Assets Select Button"
		};
	private static String[] _ASSETS_LINK_SELECT_BLOGS_ENTRY = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Blogs Entry')]",
			"Related Assets Menu Blogs Entry tab"
		};
	private static String[] _ASSETS_LINK_SELECT_BOOKMARKS_ENTRY = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Bookmarks Entry')]",
			"Related Assets Menu Bookmarks Entry tab"
		};
	private static String[] _ASSETS_LINK_SELECT_CALENDAR_EVENT = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Calendar Event')]",
			"Related Assets Menu Calendar Event Tab"
		};
	private static String[] _ASSETS_LINK_SELECT_DOCUMENT_LIBRARY_DOCUMENT = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Documents and Media Document')]",
			"Related Assets Menu Documents and Media Document Tab"
		};
	private static String[] _ASSETS_LINK_SELECT_MESSAGE_BOARDS_MESSAGE = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Message Boards Message')]",
			"Related Assets Menu Message Boards Message tab"
		};
	private static String[] _ASSETS_LINK_SELECT_WEB_CONTENT = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Web Content')]",
			"Related Assets Menu Web Content tab"
		};
	private static String[] _ASSETS_LINK_SELECT_WIKI_PAGE = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Wiki Page')]",
			"Related Assets Menu Wiki Page tab"
		};
	private static String[] _CATEGORIZATION_FIELD_TAG_FIELD = {
			"//input[@title='Add Tags']", "Categorization Tag Field input"
		};
	private static String[] _CATEGORIZATION_LINK_ADD_TAG = {
			"//button[@id='add']/span[.='Add']", "Categorization Add Tag button"
		};
	private static String[] _CATEGORIZATION_LINK_SELECT_TAG = {
			"//button[@id='select']/span[.='Select']",
			"Categorization Select Tag button"
		};
	private static String[] _CATEGORIZATION_LINK_SUGGEST_TAG = {
			"//button[@id='suggest']/span[.='Suggestions']",
			"Categorization Tag Suggestions button"
		};
	private static String[] _CONTENT_TEXT_HEADER = {
			"//h1[@class='header-title']/span", "Entry Content Header"
		};
	private static String[] _CONTENT_TEXT_ID = {
			"//span[@class='workflow-id']", "Entry Workflow ID"
		};
	private static String[] _CONTENT_TEXT_STATUS = {
			"//span[@class='workflow-status']", "Entry Content Workflow Status"
		};
	private static String[] _CONTENT_TEXT_TITLE_LABEL = {
			"//label[@for='_161_title']", "Entry Title Label"
		};
	private static String[] _CONTENT_FIELD_TITLE = {
			"_161_title", "Entry Title Field"
		};
	private static String[] _CONTENT_FIELD_TITLE_ALERT = {
			"//div[@role='alert']", "Entry Title Field Invalid Input Alert"
		};
	private static String[] _CONTENT_TEXT_DISPLAY_DATE_LABEL = {
			"//label[@for='_161_displayDate']",
			"Entry Content Display Date Label"
		};
	private static String[] _CONTENT_FIELD_DISPLAY_DATE_DAY = {
			"_161_displayDateDay", "Entry Content Display Date AM/PM select"
		};
	private static String[] _CONTENT_FIELD_DISPLAY_DATE_MONTH = {
			"_161_displayDateMonth", "Entry Content Display Date Month select"
		};
	private static String[] _CONTENT_FIELD_DISPLAY_DATE_YEAR = {
			"_161_displayDateYear", "Entry Content Display Date Year select"
		};
	private static String[] _CONTENT_LINK_CALENDAR = {
			"buttonTest", "Entry Content Display Date Open Calendar Button"
		};
	private static String[] _CONTENT_FIELD_DISPLAY_DATE_MINUTE = {
			"_161_displayDateMinute", "Entry Content Display Date Minute select"
		};
	private static String[] _CONTENT_FIELD_DISPLAY_DATE_HOUR = {
			"_161_displayDateHour", "Entry Content Display Date Hour select"
		};
	private static String[] _CONTENT_FIELD_DISPLAY_DATE_AM_PM = {
			"_161_displayDateAmPm", "Entry Content Display Date AM/PM selec"
		};
	private static String[] _CONTENT_FIELD_CONTENT = {
			"_161_editor", "Entry Content Editor Field"
		};
	private static String[] _CONTENT_LINK_ALLOW_PINGBACKS = {
			"_161_allowPingbacksCheckbox",
			"Entry Content Allow Pingbacks Checkbox"
		};
	private static String[] _CONTENT_TEXT_ALLOW_PINGBACKS_LABEL = {
			"//label[@for='_161_allowPingbacksCheckbox']",
			"Entry Content Allow Pingbacks Checkbox Label"
		};
	private static String[] _CONTENT_LINK_ALLOW_TRACKBACKS = {
			"_161_allowTrackbacksCheckbox",
			"Entry Content Allow Trackbacks Checkbox"
		};
	private static String[] _CONTENT_TEXT_ALLOW_TRACKBACKS_LABEL = {
			"//label[@for='_161_allowTrackbacksCheckbox']",
			"Entry Content Allow Trackbacks Checkbox Label"
		};
	private static String[] _CONTENT_FIELD_TRACKBACKS = {
			"_161_trackbacks", "Entry Content Trackbacks to Send Field"
		};
	private static String[] _CONTENT_FIELD_VIEWABLE_BY = {
			"_161__inputPermissionsViewRole",
			"Entry Content Viewable by Permissions Select (Dropdown)"
		};
	private static String[] _CONTENT_LINK_BACK = { "_161_TabsBack", "Back Button" };
	private static String[] _CONTENT_LINK_SAVE = {
			"//input[@value='Publish']", "Entry Content Publish Button"
		};
	private static String[] _CONTENT_LINK_DRAFT = {
			"//input[@value='Save as Draft']",
			"Entry Content Save as Draft Button"
		};
	private static String[] _CONTENT_LINK_CANCEL = {
			"//input[@value='Cancel']", "Entry Content Cancel Button"
		};
	private static String[] _CONTENT_LINK_PREVIEW = {
			"//input[@value='Preview']", "Entry Content Preview Button"
		};
	private static String[] _CONTENT_TEXT_CONTENT_LABEL = {
			"//label[contains(.,'Content')]", "Entry Content Editor Label"
		};
	private static String[] _CONTENT_TEXT_ERROR_MESSAGE_1 = {
			"xpath=(//div[@class='portlet-msg-error'])[1]",
			"Entry Content Your request failed to complete. Error Message"
		};
	private static String[] _CONTENT_TEXT_ERROR_MESSAGE_2 = {
			"xpath=(//div[@class='portlet-msg-error'])[2]",
			"Entry Content Please enter valid content. Error Message"
		};
	private static String[] _CONTENT_TEXT_PREVIEW = {
			"//div[@class='preview']", "Entry Text Preview"
		};
	private static String[] _CONTENT_TEXT_SAVE_STATUS = {
			"_161_saveStatus", "Entry Content Save Status"
		};
	private static String[] _CONTENT_TEXT_TRACKBACKS_LABEL = {
			"//label[@for='_161_trackbacks']",
			"Entry Content Trackbacks to Send Label"
		};
	private static String[] _CONTENT_TEXT_VIEWABLE_BY_LABEL = {
			"//label[@for='_161__inputPermissionsViewRole']",
			"Entry Content Viewable By Label"
		};
	private static String[] _PANEL_LINK_ABSTRACT = {
			"blogsEntryAbstractPanel", "Abstract Section Expand"
		};
	private static String[] _PANEL_LINK_CATEGORIZATION = {
			"blogsEntryCategorizationPanel", "Categorization Expand"
		};
	private static String[] _PANEL_LINK_ASSETS = {
			"blogsEntryAssetLinksPanel", "Related Assets Expand"
		};
	private static String[] _PORTLET_LINK_BREADCRUMB_1 = {
			"//nav[@id='breadcrumbs']/ul/li[1]/span/a",
			"Portlet 1st Breadcrumb &quot;Control Panel&quot; Link"
		};
	private static String[] _PORTLET_LINK_BREADCRUMB_2 = {
			"//nav[@id='breadcrumbs']/ul/li[2]/span/a",
			"Portlet 2nd Breadcrumb &quot;Blogs&quot; Link"
		};
	private static String[] _PORTLET_LINK_BREADCRUMB_3 = {
			"//nav[@id='breadcrumbs']/ul/li[3]/span/a",
			"Portlet 3rd Breadcrumb Entry Title Link"
		};
	private static String[] _PORTLET_TEXT_PORTLET_TITLE = {
			"cpPortletTitle", "Portlet Title"
		};
	private static String[] _PORTLET_LINK_OPTIONS = {
			"//span[@title='Options']/ul/li/strong/a",
			"Portlet Configuration Options Button"
		};
	private static String[] _PORTLET_LINK_OPTIONS_EXPORT_IMPORT = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Export / Import')]",
			"Portlet Configuration Options Menu Export/Import tab"
		};
	private static String[] _PORTLET_TEXT_DESCRIPTION = {
			"cpContextPanelTemplate", "Portlet Description"
		};
	private static String[] _PORTLET_TEXT_SUCCESS = {
			"//div[@class='portlet-msg-success']",
			"Portlet Your request completed successfully. Success Message"
		};
	private static String[] _TAG_TEXT_1 = {
			"xpath=(//span[@class='aui-textboxlistentry-text'])[1]",
			"Categorization Tag 1"
		};
	private static String[] _TAG_TEXT_2 = {
			"xpath=(//span[@class='aui-textboxlistentry-text'])[2]",
			"Categorization Tag 2"
		};
	private static String[] _TAG_TEXT_3 = {
			"xpath=(//span[@class='aui-textboxlistentry-text'])[3]",
			"Categorization Tag 3"
		};
	private static String[] _TAG_LINK_DELETE_1 = {
			"xpath=(//span[contains(@class,'aui-textboxlistentry-close')])[1]",
			"Categorization Tag 1 Delete Button"
		};
	private static String[] _TAG_LINK_DELETE_2 = {
			"xpath=(//span[contains(@class,'aui-textboxlistentry-close')])[2]",
			"Categorization Tag 2 Delete Button"
		};
	private static String[] _TAG_LINK_DELETE_3 = {
			"xpath=(//span[contains(@class,'aui-textboxlistentry-close')])[3]",
			"Categorization Tag 3 Delete Button"
		};
	private static Map<String, String[]> _paths = new HashMap<String, String[]>();

	static {
		_paths.put("PAGE_NAME", _PAGE_NAME);
		_paths.put("ABSTRACT_FIELD_DESCRIPTION", _ABSTRACT_FIELD_DESCRIPTION);
		_paths.put("ABSTRACT_FIELD_SMALL_IMAGE_CHECKBOX",
			_ABSTRACT_FIELD_SMALL_IMAGE_CHECKBOX);
		_paths.put("ABSTRACT_FIELD_SMALL_IMAGE_URL",
			_ABSTRACT_FIELD_SMALL_IMAGE_URL);
		_paths.put("ABSTRACT_FIELD_SMALL_IMAGE", _ABSTRACT_FIELD_SMALL_IMAGE);
		_paths.put("ASSETS_LINK_SELECT", _ASSETS_LINK_SELECT);
		_paths.put("ASSETS_LINK_SELECT_BLOGS_ENTRY",
			_ASSETS_LINK_SELECT_BLOGS_ENTRY);
		_paths.put("ASSETS_LINK_SELECT_BOOKMARKS_ENTRY",
			_ASSETS_LINK_SELECT_BOOKMARKS_ENTRY);
		_paths.put("ASSETS_LINK_SELECT_CALENDAR_EVENT",
			_ASSETS_LINK_SELECT_CALENDAR_EVENT);
		_paths.put("ASSETS_LINK_SELECT_DOCUMENT_LIBRARY_DOCUMENT",
			_ASSETS_LINK_SELECT_DOCUMENT_LIBRARY_DOCUMENT);
		_paths.put("ASSETS_LINK_SELECT_MESSAGE_BOARDS_MESSAGE",
			_ASSETS_LINK_SELECT_MESSAGE_BOARDS_MESSAGE);
		_paths.put("ASSETS_LINK_SELECT_WEB_CONTENT",
			_ASSETS_LINK_SELECT_WEB_CONTENT);
		_paths.put("ASSETS_LINK_SELECT_WIKI_PAGE", _ASSETS_LINK_SELECT_WIKI_PAGE);
		_paths.put("CATEGORIZATION_FIELD_TAG_FIELD",
			_CATEGORIZATION_FIELD_TAG_FIELD);
		_paths.put("CATEGORIZATION_LINK_ADD_TAG", _CATEGORIZATION_LINK_ADD_TAG);
		_paths.put("CATEGORIZATION_LINK_SELECT_TAG",
			_CATEGORIZATION_LINK_SELECT_TAG);
		_paths.put("CATEGORIZATION_LINK_SUGGEST_TAG",
			_CATEGORIZATION_LINK_SUGGEST_TAG);
		_paths.put("CONTENT_TEXT_HEADER", _CONTENT_TEXT_HEADER);
		_paths.put("CONTENT_TEXT_ID", _CONTENT_TEXT_ID);
		_paths.put("CONTENT_TEXT_STATUS", _CONTENT_TEXT_STATUS);
		_paths.put("CONTENT_TEXT_TITLE_LABEL", _CONTENT_TEXT_TITLE_LABEL);
		_paths.put("CONTENT_FIELD_TITLE", _CONTENT_FIELD_TITLE);
		_paths.put("CONTENT_FIELD_TITLE_ALERT", _CONTENT_FIELD_TITLE_ALERT);
		_paths.put("CONTENT_TEXT_DISPLAY_DATE_LABEL",
			_CONTENT_TEXT_DISPLAY_DATE_LABEL);
		_paths.put("CONTENT_FIELD_DISPLAY_DATE_DAY",
			_CONTENT_FIELD_DISPLAY_DATE_DAY);
		_paths.put("CONTENT_FIELD_DISPLAY_DATE_MONTH",
			_CONTENT_FIELD_DISPLAY_DATE_MONTH);
		_paths.put("CONTENT_FIELD_DISPLAY_DATE_YEAR",
			_CONTENT_FIELD_DISPLAY_DATE_YEAR);
		_paths.put("CONTENT_LINK_CALENDAR", _CONTENT_LINK_CALENDAR);
		_paths.put("CONTENT_FIELD_DISPLAY_DATE_MINUTE",
			_CONTENT_FIELD_DISPLAY_DATE_MINUTE);
		_paths.put("CONTENT_FIELD_DISPLAY_DATE_HOUR",
			_CONTENT_FIELD_DISPLAY_DATE_HOUR);
		_paths.put("CONTENT_FIELD_DISPLAY_DATE_AM_PM",
			_CONTENT_FIELD_DISPLAY_DATE_AM_PM);
		_paths.put("CONTENT_FIELD_CONTENT", _CONTENT_FIELD_CONTENT);
		_paths.put("CONTENT_LINK_ALLOW_PINGBACKS", _CONTENT_LINK_ALLOW_PINGBACKS);
		_paths.put("CONTENT_TEXT_ALLOW_PINGBACKS_LABEL",
			_CONTENT_TEXT_ALLOW_PINGBACKS_LABEL);
		_paths.put("CONTENT_LINK_ALLOW_TRACKBACKS",
			_CONTENT_LINK_ALLOW_TRACKBACKS);
		_paths.put("CONTENT_TEXT_ALLOW_TRACKBACKS_LABEL",
			_CONTENT_TEXT_ALLOW_TRACKBACKS_LABEL);
		_paths.put("CONTENT_FIELD_TRACKBACKS", _CONTENT_FIELD_TRACKBACKS);
		_paths.put("CONTENT_FIELD_VIEWABLE_BY", _CONTENT_FIELD_VIEWABLE_BY);
		_paths.put("CONTENT_LINK_BACK", _CONTENT_LINK_BACK);
		_paths.put("CONTENT_LINK_SAVE", _CONTENT_LINK_SAVE);
		_paths.put("CONTENT_LINK_DRAFT", _CONTENT_LINK_DRAFT);
		_paths.put("CONTENT_LINK_CANCEL", _CONTENT_LINK_CANCEL);
		_paths.put("CONTENT_LINK_PREVIEW", _CONTENT_LINK_PREVIEW);
		_paths.put("CONTENT_TEXT_CONTENT_LABEL", _CONTENT_TEXT_CONTENT_LABEL);
		_paths.put("CONTENT_TEXT_ERROR_MESSAGE_1", _CONTENT_TEXT_ERROR_MESSAGE_1);
		_paths.put("CONTENT_TEXT_ERROR_MESSAGE_2", _CONTENT_TEXT_ERROR_MESSAGE_2);
		_paths.put("CONTENT_TEXT_PREVIEW", _CONTENT_TEXT_PREVIEW);
		_paths.put("CONTENT_TEXT_SAVE_STATUS", _CONTENT_TEXT_SAVE_STATUS);
		_paths.put("CONTENT_TEXT_TRACKBACKS_LABEL",
			_CONTENT_TEXT_TRACKBACKS_LABEL);
		_paths.put("CONTENT_TEXT_VIEWABLE_BY_LABEL",
			_CONTENT_TEXT_VIEWABLE_BY_LABEL);
		_paths.put("PANEL_LINK_ABSTRACT", _PANEL_LINK_ABSTRACT);
		_paths.put("PANEL_LINK_CATEGORIZATION", _PANEL_LINK_CATEGORIZATION);
		_paths.put("PANEL_LINK_ASSETS", _PANEL_LINK_ASSETS);
		_paths.put("PORTLET_LINK_BREADCRUMB_1", _PORTLET_LINK_BREADCRUMB_1);
		_paths.put("PORTLET_LINK_BREADCRUMB_2", _PORTLET_LINK_BREADCRUMB_2);
		_paths.put("PORTLET_LINK_BREADCRUMB_3", _PORTLET_LINK_BREADCRUMB_3);
		_paths.put("PORTLET_TEXT_PORTLET_TITLE", _PORTLET_TEXT_PORTLET_TITLE);
		_paths.put("PORTLET_LINK_OPTIONS", _PORTLET_LINK_OPTIONS);
		_paths.put("PORTLET_LINK_OPTIONS_EXPORT_IMPORT",
			_PORTLET_LINK_OPTIONS_EXPORT_IMPORT);
		_paths.put("PORTLET_TEXT_DESCRIPTION", _PORTLET_TEXT_DESCRIPTION);
		_paths.put("PORTLET_TEXT_SUCCESS", _PORTLET_TEXT_SUCCESS);
		_paths.put("TAG_TEXT_1", _TAG_TEXT_1);
		_paths.put("TAG_TEXT_2", _TAG_TEXT_2);
		_paths.put("TAG_TEXT_3", _TAG_TEXT_3);
		_paths.put("TAG_LINK_DELETE_1", _TAG_LINK_DELETE_1);
		_paths.put("TAG_LINK_DELETE_2", _TAG_LINK_DELETE_2);
		_paths.put("TAG_LINK_DELETE_3", _TAG_LINK_DELETE_3);
	}
}