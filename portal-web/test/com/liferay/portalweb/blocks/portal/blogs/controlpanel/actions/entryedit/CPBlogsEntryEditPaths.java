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

package com.liferay.portalweb.blocks.portal.blogs.controlpanel.actions.entryedit;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPBlogsEntryEditPaths {
	public static Map<String, String> getPaths() {
		return _paths;
	}

	private static Map<String, String> _paths = new HashMap<String, String>();

	static {
		_paths.put("PAGE_NAME", "");
		_paths.put("ABSTRACT_FIELD_DESCRIPTION", "_161_description");
		_paths.put("ABSTRACT_FIELD_SMALL_IMAGE_CHECKBOX",
			"_161_smallImageCheckbox");
		_paths.put("ABSTRACT_FIELD_SMALL_IMAGE_URL", "_161_smallImageURL");
		_paths.put("ABSTRACT_FIELD_SMALL_IMAGE", "_161_smallFile");
		_paths.put("ASSETS_LINK_SELECT",
			"//span[@title='Select']/ul/li/strong/a/span");
		_paths.put("ASSETS_LINK_SELECT_BLOGS_ENTRY",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Blogs Entry')]");
		_paths.put("ASSETS_LINK_SELECT_BOOKMARKS_ENTRY",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Bookmarks Entry')]");
		_paths.put("ASSETS_LINK_SELECT_CALENDAR_EVENT",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Calendar Event')]");
		_paths.put("ASSETS_LINK_SELECT_DOCUMENT_LIBRARY_DOCUMENT",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Documents and Media Document')]");
		_paths.put("ASSETS_LINK_SELECT_MESSAGE_BOARDS_MESSAGE",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Message Boards Message')]");
		_paths.put("ASSETS_LINK_SELECT_WEB_CONTENT",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Web Content')]");
		_paths.put("ASSETS_LINK_SELECT_WIKI_PAGE",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Wiki Page')]");
		_paths.put("CATEGORIZATION_FIELD_TAG_FIELD",
			"//input[@title='Add Tags']");
		_paths.put("CATEGORIZATION_LINK_ADD_TAG",
			"//button[@id='add']/span[.='Add']");
		_paths.put("CATEGORIZATION_LINK_SELECT_TAG",
			"//button[@id='select']/span[.='Select']");
		_paths.put("CATEGORIZATION_LINK_SUGGEST_TAG",
			"//button[@id='suggest']/span[.='Suggestions']");
		_paths.put("CONTENT_TEXT_HEADER", "//h1[@class='header-title']/span");
		_paths.put("CONTENT_TEXT_ID", "//span[@class='workflow-id']");
		_paths.put("CONTENT_TEXT_STATUS", "//span[@class='workflow-status']");
		_paths.put("CONTENT_TEXT_TITLE_LABEL", "//label[@for='_161_title']");
		_paths.put("CONTENT_FIELD_TITLE", "_161_title");
		_paths.put("CONTENT_FIELD_TITLE_ALERT", "//div[@role='alert']");
		_paths.put("CONTENT_TEXT_DISPLAY_DATE_LABEL",
			"//label[@for='_161_displayDate']");
		_paths.put("CONTENT_FIELD_DISPLAY_DATE_DAY", "_161_displayDateDay");
		_paths.put("CONTENT_FIELD_DISPLAY_DATE_MONTH", "_161_displayDateMonth");
		_paths.put("CONTENT_FIELD_DISPLAY_DATE_YEAR", "_161_displayDateYear");
		_paths.put("CONTENT_LINK_CALENDAR", "buttonTest");
		_paths.put("CONTENT_FIELD_DISPLAY_DATE_MINUTE", "_161_displayDateMinute");
		_paths.put("CONTENT_FIELD_DISPLAY_DATE_HOUR", "_161_displayDateHour");
		_paths.put("CONTENT_FIELD_DISPLAY_DATE_AM_PM", "_161_displayDateAmPm");
		_paths.put("CONTENT_FIELD_CONTENT", "_161_editor");
		_paths.put("CONTENT_LINK_ALLOW_PINGBACKS", "_161_allowPingbacksCheckbox");
		_paths.put("CONTENT_TEXT_ALLOW_PINGBACKS_LABEL",
			"//label[@for='_161_allowPingbacksCheckbox']");
		_paths.put("CONTENT_LINK_ALLOW_TRACKBACKS",
			"_161_allowTrackbacksCheckbox");
		_paths.put("CONTENT_TEXT_ALLOW_TRACKBACKS_LABEL",
			"//label[@for='_161_allowTrackbacksCheckbox']");
		_paths.put("CONTENT_FIELD_TRACKBACKS", "_161_trackbacks");
		_paths.put("CONTENT_FIELD_VIEWABLE_BY", "_161__inputPermissionsViewRole");
		_paths.put("CONTENT_LINK_BACK", "_161_TabsBack");
		_paths.put("CONTENT_LINK_SAVE", "//input[@value='Publish']");
		_paths.put("CONTENT_LINK_DRAFT", "//input[@value='Save as Draft']");
		_paths.put("CONTENT_LINK_CANCEL", "//input[@value='Cancel']");
		_paths.put("CONTENT_LINK_PREVIEW", "//input[@value='Preview']");
		_paths.put("CONTENT_TEXT_CONTENT_LABEL",
			"//label[contains(.,'Content')]");
		_paths.put("CONTENT_TEXT_ERROR_MESSAGE_1",
			"xpath=(//div[@class='portlet-msg-error'])[1]");
		_paths.put("CONTENT_TEXT_ERROR_MESSAGE_2",
			"xpath=(//div[@class='portlet-msg-error'])[2]");
		_paths.put("CONTENT_TEXT_PREVIEW", "//div[@class='preview']");
		_paths.put("CONTENT_TEXT_SAVE_STATUS", "_161_saveStatus");
		_paths.put("CONTENT_TEXT_TRACKBACKS_LABEL",
			"//label[@for='_161_trackbacks']");
		_paths.put("CONTENT_TEXT_VIEWABLE_BY_LABEL",
			"//label[@for='_161__inputPermissionsViewRole']");
		_paths.put("PANEL_LINK_ABSTRACT", "blogsEntryAbstractPanel");
		_paths.put("PANEL_LINK_CATEGORIZATION", "blogsEntryCategorizationPanel");
		_paths.put("PANEL_LINK_ASSETS", "blogsEntryAssetLinksPanel");
		_paths.put("PORTLET_LINK_BREADCRUMB_1",
			"//nav[@id='breadcrumbs']/ul/li[1]/span/a");
		_paths.put("PORTLET_LINK_BREADCRUMB_2",
			"//nav[@id='breadcrumbs']/ul/li[2]/span/a");
		_paths.put("PORTLET_LINK_BREADCRUMB_3",
			"//nav[@id='breadcrumbs']/ul/li[3]/span/a");
		_paths.put("PORTLET_TEXT_PORTLET_TITLE", "cpPortletTitle");
		_paths.put("PORTLET_LINK_OPTIONS",
			"//span[@title='Options']/ul/li/strong/a");
		_paths.put("PORTLET_LINK_OPTIONS_EXPORT_IMPORT",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Export / Import')]");
		_paths.put("PORTLET_TEXT_DESCRIPTION", "cpContextPanelTemplate");
		_paths.put("PORTLET_TEXT_SUCCESS", "//div[@class='portlet-msg-success']");
		_paths.put("TAG_TEXT_1",
			"xpath=(//span[@class='aui-textboxlistentry-text'])[1]");
		_paths.put("TAG_TEXT_2",
			"xpath=(//span[@class='aui-textboxlistentry-text'])[2]");
		_paths.put("TAG_TEXT_3",
			"xpath=(//span[@class='aui-textboxlistentry-text'])[3]");
		_paths.put("TAG_LINK_DELETE_1",
			"xpath=(//span[contains(@class,'aui-textboxlistentry-close')])[1]");
		_paths.put("TAG_LINK_DELETE_2",
			"xpath=(//span[contains(@class,'aui-textboxlistentry-close')])[2]");
		_paths.put("TAG_LINK_DELETE_3",
			"xpath=(//span[contains(@class,'aui-textboxlistentry-close')])[3]");
	}
}