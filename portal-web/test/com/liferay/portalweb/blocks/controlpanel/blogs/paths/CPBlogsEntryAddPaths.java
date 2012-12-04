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
public class CPBlogsEntryAddPaths {
	public static Map<String, String[]> getPaths() {
		return _paths;
	}

	private static String[] _ABSTRACT_FIELD_DESCRIPTION = {
			"_161_description", ""
		};
	private static String[] _ABSTRACT_FIELD_SMALL_IMAGE = { "_161_smallFile", "" };
	private static String[] _ABSTRACT_FIELD_SMALL_IMAGE_CHECKBOX = {
			"_161_smallImageCheckbox", ""
		};
	private static String[] _ABSTRACT_FIELD_SMALL_IMAGE_URL = {
			"_161_smallImageURL", ""
		};
	private static String[] _ASSETS_LINK_SELECT = {
			"//span[@title='Select']/ul/li/strong/a/span", ""
		};
	private static String[] _ASSETS_LINK_SELECT_BLOGS_ENTRY = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Blogs Entry')]",
			"Blogs Entry"
		};
	private static String[] _ASSETS_LINK_SELECT_BOOKMARKS_ENTRY = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Bookmarks Entry')]",
			"Bookmarks Entry"
		};
	private static String[] _ASSETS_LINK_SELECT_CALENDAR_EVENT = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Calendar Event')]",
			"Calendar Event"
		};
	private static String[] _ASSETS_LINK_SELECT_DOCUMENT_LIBRARY_DOCUMENT = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Documents and Media Document')]",
			"Documents and Media Document"
		};
	private static String[] _ASSETS_LINK_SELECT_MESSAGE_BOARDS_MESSAGE = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Message Boards Message')]",
			"Message Boards Message"
		};
	private static String[] _ASSETS_LINK_SELECT_WEB_CONTENT = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Web Content')]",
			"Web Content"
		};
	private static String[] _ASSETS_LINK_SELECT_WIKI_PAGE = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Wiki Page')]",
			"Wiki Page"
		};
	private static String[] _CATEGORIZATION_FIELD_TAG_FIELD = {
			"//input[@title='Add Tags']", "Add Tags"
		};
	private static String[] _CATEGORIZATION_LINK_ADD_TAG = {
			"//button[@id='add']/span[.='Add']", "Add"
		};
	private static String[] _CATEGORIZATION_LINK_SELECT_TAG = {
			"//button[@id='select']/span[.='Select']", "Select"
		};
	private static String[] _CATEGORIZATION_LINK_SUGGEST_TAG = {
			"//button[@id='suggest']/span[.='Suggestions']", "Suggestions"
		};
	private static String[] _CONTENT_LINK_ALLOW_PINGBACKS = {
			"_161_allowPingbacksCheckbox", ""
		};
	private static String[] _CONTENT_LINK_ALLOW_TRACKBACKS = {
			"_161_allowTrackbacksCheckbox", ""
		};
	private static String[] _CONTENT_FIELD_CONTENT = { "_161_editor", "" };
	private static String[] _CONTENT_FIELD_DISPLAY_DATE_AM_PM = {
			"_161_displayDateAmPm", ""
		};
	private static String[] _CONTENT_FIELD_DISPLAY_DATE_DAY = {
			"_161_displayDateDay", ""
		};
	private static String[] _CONTENT_FIELD_DISPLAY_DATE_HOUR = {
			"_161_displayDateHour", ""
		};
	private static String[] _CONTENT_FIELD_DISPLAY_DATE_MINUTE = {
			"_161_displayDateMinute", ""
		};
	private static String[] _CONTENT_FIELD_DISPLAY_DATE_MONTH = {
			"_161_displayDateMonth", ""
		};
	private static String[] _CONTENT_FIELD_DISPLAY_DATE_YEAR = {
			"_161_displayDateYear", ""
		};
	private static String[] _CONTENT_FIELD_TITLE = { "_161_title", "" };
	private static String[] _CONTENT_FIELD_TITLE_ALERT = {
			"//div[@role='alert']", ""
		};
	private static String[] _CONTENT_FIELD_TRACKBACKS = { "_161_trackbacks", "" };
	private static String[] _CONTENT_FIELD_VIEWABLE_BY = {
			"_161__inputPermissionsViewRole", ""
		};
	private static String[] _CONTENT_LINK_CALENDAR = { "buttonTest", "" };
	private static String[] _CONTENT_LINK_CANCEL = {
			"//input[@value='Cancel']", "Cancel"
		};
	private static String[] _CONTENT_LINK_DRAFT = {
			"//input[@value='Save as Draft']", "Save as Draft"
		};
	private static String[] _CONTENT_LINK_PREVIEW = {
			"//input[@value='Preview']", "Preview"
		};
	private static String[] _CONTENT_LINK_SAVE = {
			"//input[@value='Publish']", "Publish"
		};
	private static String[] _CONTENT_TEXT_ALLOW_PINGBACKS_LABEL = {
			"//label[@for='_161_allowPingbacksCheckbox']", ""
		};
	private static String[] _CONTENT_TEXT_ALLOW_TRACKBACKS_LABEL = {
			"//label[@for='_161_allowTrackbacksCheckbox']", ""
		};
	private static String[] _CONTENT_TEXT_CONTENT_LABEL = {
			"//label[contains(.,'Content')]", ""
		};
	private static String[] _CONTENT_TEXT_DISPLAY_DATE_LABEL = {
			"//label[@for='_161_displayDate']", ""
		};
	private static String[] _CONTENT_TEXT_ERROR_MESSAGE_1 = {
			"xpath=(//div[@class='portlet-msg-error'])[1]", ""
		};
	private static String[] _CONTENT_TEXT_ERROR_MESSAGE_2 = {
			"xpath=(//div[@class='portlet-msg-error'])[2]", ""
		};
	private static String[] _CONTENT_TEXT_HEADER = {
			"//h1[@class='header-title']/span", ""
		};
	private static String[] _CONTENT_TEXT_SAVE_STATUS = {
			"_161_saveStatus", "Draft saved"
		};
	private static String[] _CONTENT_TEXT_SUCCESS_MESSAGE = {
			"//div[@class='portlet-msg-success']", ""
		};
	private static String[] _CONTENT_TEXT_TITLE_LABEL = {
			"//label[@for='_161_title']", ""
		};
	private static String[] _CONTENT_TEXT_TRACKBACKS_LABEL = {
			"//label[@for='_161_trackbacks']", ""
		};
	private static String[] _CONTENT_TEXT_VIEWABLE_BY_LABEL = {
			"//label[@for='_161__inputPermissionsViewRole']", ""
		};
	private static String[] _PANEL_LINK_ABSTRACT = {
			"blogsEntryAbstractPanel", ""
		};
	private static String[] _PANEL_LINK_ASSETS = {
			"blogsEntryAssetLinksPanel", ""
		};
	private static String[] _PANEL_LINK_CATEGORIZATION = {
			"blogsEntryCategorizationPanel", ""
		};
	private static String[] _PORTLET_LINK_BACK = { "_161_TabsBack", "" };
	private static String[] _PORTLET_LINK_BREADCRUMB_1 = {
			"//nav[@id='breadcrumbs']/ul/li[1]/span/a", ""
		};
	private static String[] _PORTLET_LINK_BREADCRUMB_2 = {
			"//nav[@id='breadcrumbs']/ul/li[2]/span/a", ""
		};
	private static String[] _PORTLET_LINK_BREADCRUMB_3 = {
			"//nav[@id='breadcrumbs']/ul/li[3]/span/a", ""
		};
	private static String[] _PORTLET_TEXT_DESCRIPTION = {
			"cpContextPanelTemplate", ""
		};
	private static String[] _PORTLET_LINK_OPTIONS = {
			"//span[@title='Options']/ul/li/strong/a", ""
		};
	private static String[] _PORTLET_LINK_OPTIONS_EXPORT_IMPORT = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Export / Import')]",
			""
		};
	private static String[] _PORTLET_TEXT_PORTLET_TITLE = { "cpPortletTitle", "" };
	private static String[] _PORTLET_TEXT_SUCCESS = {
			"//div[@class='portlet-msg-success']",
			"Your request completed successfully."
		};
	private static String[] _TAG_TEXT_1 = {
			"xpath=(//span[@class='aui-textboxlistentry-text'])[1]", ""
		};
	private static String[] _TAG_TEXT_2 = {
			"xpath=(//span[@class='aui-textboxlistentry-text'])[2]", ""
		};
	private static String[] _TAG_TEXT_3 = {
			"xpath=(//span[@class='aui-textboxlistentry-text'])[3]", ""
		};
	private static String[] _TAG_LINK_DELETE_1 = {
			"xpath=(//span[contains(@class,'aui-textboxlistentry-close')])[1]",
			""
		};
	private static String[] _TAG_LINK_DELETE_2 = {
			"xpath=(//span[contains(@class,'aui-textboxlistentry-close')])[2]",
			""
		};
	private static String[] _TAG_LINK_DELETE_3 = {
			"xpath=(//span[contains(@class,'aui-textboxlistentry-close')])[3]",
			""
		};
	private static Map<String, String[]> _paths = new HashMap<String, String[]>();

	static {
		_paths.put("ABSTRACT_FIELD_DESCRIPTION", _ABSTRACT_FIELD_DESCRIPTION);
		_paths.put("ABSTRACT_FIELD_SMALL_IMAGE", _ABSTRACT_FIELD_SMALL_IMAGE);
		_paths.put("ABSTRACT_FIELD_SMALL_IMAGE_CHECKBOX",
			_ABSTRACT_FIELD_SMALL_IMAGE_CHECKBOX);
		_paths.put("ABSTRACT_FIELD_SMALL_IMAGE_URL",
			_ABSTRACT_FIELD_SMALL_IMAGE_URL);
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
		_paths.put("CONTENT_LINK_ALLOW_PINGBACKS", _CONTENT_LINK_ALLOW_PINGBACKS);
		_paths.put("CONTENT_LINK_ALLOW_TRACKBACKS",
			_CONTENT_LINK_ALLOW_TRACKBACKS);
		_paths.put("CONTENT_FIELD_CONTENT", _CONTENT_FIELD_CONTENT);
		_paths.put("CONTENT_FIELD_DISPLAY_DATE_AM_PM",
			_CONTENT_FIELD_DISPLAY_DATE_AM_PM);
		_paths.put("CONTENT_FIELD_DISPLAY_DATE_DAY",
			_CONTENT_FIELD_DISPLAY_DATE_DAY);
		_paths.put("CONTENT_FIELD_DISPLAY_DATE_HOUR",
			_CONTENT_FIELD_DISPLAY_DATE_HOUR);
		_paths.put("CONTENT_FIELD_DISPLAY_DATE_MINUTE",
			_CONTENT_FIELD_DISPLAY_DATE_MINUTE);
		_paths.put("CONTENT_FIELD_DISPLAY_DATE_MONTH",
			_CONTENT_FIELD_DISPLAY_DATE_MONTH);
		_paths.put("CONTENT_FIELD_DISPLAY_DATE_YEAR",
			_CONTENT_FIELD_DISPLAY_DATE_YEAR);
		_paths.put("CONTENT_FIELD_TITLE", _CONTENT_FIELD_TITLE);
		_paths.put("CONTENT_FIELD_TITLE_ALERT", _CONTENT_FIELD_TITLE_ALERT);
		_paths.put("CONTENT_FIELD_TRACKBACKS", _CONTENT_FIELD_TRACKBACKS);
		_paths.put("CONTENT_FIELD_VIEWABLE_BY", _CONTENT_FIELD_VIEWABLE_BY);
		_paths.put("CONTENT_LINK_CALENDAR", _CONTENT_LINK_CALENDAR);
		_paths.put("CONTENT_LINK_CANCEL", _CONTENT_LINK_CANCEL);
		_paths.put("CONTENT_LINK_DRAFT", _CONTENT_LINK_DRAFT);
		_paths.put("CONTENT_LINK_PREVIEW", _CONTENT_LINK_PREVIEW);
		_paths.put("CONTENT_LINK_SAVE", _CONTENT_LINK_SAVE);
		_paths.put("CONTENT_TEXT_ALLOW_PINGBACKS_LABEL",
			_CONTENT_TEXT_ALLOW_PINGBACKS_LABEL);
		_paths.put("CONTENT_TEXT_ALLOW_TRACKBACKS_LABEL",
			_CONTENT_TEXT_ALLOW_TRACKBACKS_LABEL);
		_paths.put("CONTENT_TEXT_CONTENT_LABEL", _CONTENT_TEXT_CONTENT_LABEL);
		_paths.put("CONTENT_TEXT_DISPLAY_DATE_LABEL",
			_CONTENT_TEXT_DISPLAY_DATE_LABEL);
		_paths.put("CONTENT_TEXT_ERROR_MESSAGE_1", _CONTENT_TEXT_ERROR_MESSAGE_1);
		_paths.put("CONTENT_TEXT_ERROR_MESSAGE_2", _CONTENT_TEXT_ERROR_MESSAGE_2);
		_paths.put("CONTENT_TEXT_HEADER", _CONTENT_TEXT_HEADER);
		_paths.put("CONTENT_TEXT_SAVE_STATUS", _CONTENT_TEXT_SAVE_STATUS);
		_paths.put("CONTENT_TEXT_SUCCESS_MESSAGE", _CONTENT_TEXT_SUCCESS_MESSAGE);
		_paths.put("CONTENT_TEXT_TITLE_LABEL", _CONTENT_TEXT_TITLE_LABEL);
		_paths.put("CONTENT_TEXT_TRACKBACKS_LABEL",
			_CONTENT_TEXT_TRACKBACKS_LABEL);
		_paths.put("CONTENT_TEXT_VIEWABLE_BY_LABEL",
			_CONTENT_TEXT_VIEWABLE_BY_LABEL);
		_paths.put("PANEL_LINK_ABSTRACT", _PANEL_LINK_ABSTRACT);
		_paths.put("PANEL_LINK_ASSETS", _PANEL_LINK_ASSETS);
		_paths.put("PANEL_LINK_CATEGORIZATION", _PANEL_LINK_CATEGORIZATION);
		_paths.put("PORTLET_LINK_BACK", _PORTLET_LINK_BACK);
		_paths.put("PORTLET_LINK_BREADCRUMB_1", _PORTLET_LINK_BREADCRUMB_1);
		_paths.put("PORTLET_LINK_BREADCRUMB_2", _PORTLET_LINK_BREADCRUMB_2);
		_paths.put("PORTLET_LINK_BREADCRUMB_3", _PORTLET_LINK_BREADCRUMB_3);
		_paths.put("PORTLET_TEXT_DESCRIPTION", _PORTLET_TEXT_DESCRIPTION);
		_paths.put("PORTLET_LINK_OPTIONS", _PORTLET_LINK_OPTIONS);
		_paths.put("PORTLET_LINK_OPTIONS_EXPORT_IMPORT",
			_PORTLET_LINK_OPTIONS_EXPORT_IMPORT);
		_paths.put("PORTLET_TEXT_PORTLET_TITLE", _PORTLET_TEXT_PORTLET_TITLE);
		_paths.put("PORTLET_TEXT_SUCCESS", _PORTLET_TEXT_SUCCESS);
		_paths.put("TAG_TEXT_1", _TAG_TEXT_1);
		_paths.put("TAG_TEXT_2", _TAG_TEXT_2);
		_paths.put("TAG_TEXT_3", _TAG_TEXT_3);
		_paths.put("TAG_LINK_DELETE_1", _TAG_LINK_DELETE_1);
		_paths.put("TAG_LINK_DELETE_2", _TAG_LINK_DELETE_2);
		_paths.put("TAG_LINK_DELETE_3", _TAG_LINK_DELETE_3);
	}
}