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
public class CPBlogsEntryViewPaths {
	public static Map<String, String[]> getPaths() {
		return _paths;
	}

	private static String[] _BLOGS_COMMENT_LINK_AUTHOR_1 = {
			"xpath=(//span[@class='user-name'])[1]", ""
		};
	private static String[] _BLOGS_COMMENT_LINK_AUTHOR_2 = {
			"xpath=(//span[@class='user-name'])[2]", ""
		};
	private static String[] _BLOGS_COMMENT_LINK_AUTHOR_3 = {
			"xpath=(//span[@class='user-name'])[3]", ""
		};
	private static String[] _BLOGS_COMMENT_LINK_CONFIRM_DELETE = {
			"", "Are you sure you want to delete this?"
		};
	private static String[] _BLOGS_COMMENT_LINK_DELETE_1 = {
			"xpath=(//span/a/span[.='Delete'])[2]", "Delete"
		};
	private static String[] _BLOGS_COMMENT_LINK_DELETE_2 = {
			"xpath=(//span/a/span[.='Delete'])[3]", "Delete"
		};
	private static String[] _BLOGS_COMMENT_LINK_DELETE_3 = {
			"xpath=(//span/a/span[.='Delete'])[4]", "Delete"
		};
	private static String[] _BLOGS_COMMENT_LINK_EDIT_1 = {
			"xpath=(//span/a/span[.='Edit'])[2]", "Edit"
		};
	private static String[] _BLOGS_COMMENT_LINK_EDIT_2 = {
			"xpath=(//span/a/span[.='Edit'])[3]", "Edit"
		};
	private static String[] _BLOGS_COMMENT_LINK_EDIT_3 = {
			"xpath=(//span/a/span[.='Edit'])[4]", "Edit"
		};
	private static String[] _BLOGS_COMMENT_LINK_READ_MORE = {
			"xpath=(//div[@class='lfr-discussion-message'])[1]/a", "Read More"
		};
	private static String[] _BLOGS_COMMENT_LINK_REPLY_1 = {
			"xpath=(//span/a/span[.='Post Reply'])[1]", "Post Reply"
		};
	private static String[] _BLOGS_COMMENT_LINK_REPLY_2 = {
			"xpath=(//span/a/span[.='Post Reply'])[2]", "Post Reply"
		};
	private static String[] _BLOGS_COMMENT_LINK_REPLY_3 = {
			"xpath=(//span/a/span[.='Post Reply'])[3]", "Post Reply"
		};
	private static String[] _BLOGS_COMMENT_LINK_TOP_1 = {
			"xpath=(//span/a/span[.='Top'])[1]", "Top"
		};
	private static String[] _BLOGS_COMMENT_LINK_TOP_2 = {
			"xpath=(//span/a/span[.='Top'])[2]", "Top"
		};
	private static String[] _BLOGS_COMMENT_LINK_TOP_3 = {
			"xpath=(//span/a/span[.='Top'])[3]", "Top"
		};
	private static String[] _BLOGS_COMMENT_TEXT_BODY_1 = {
			"xpath=(//div[@class='lfr-discussion-message'])[1]", ""
		};
	private static String[] _BLOGS_COMMENT_TEXT_BODY_2 = {
			"xpath=(//div[@class='lfr-discussion-message'])[2]", ""
		};
	private static String[] _BLOGS_COMMENT_TEXT_BODY_3 = {
			"xpath=(//div[@class='lfr-discussion-message'])[3]", ""
		};
	private static String[] _BLOGS_COMMENT_TEXT_SUCCESS = {
			"_161_discussion-status-messages",
			"Your request processed successfully."
		};
	private static String[] _BLOGS_COMMENT_TEXT_VOTE_1 = {
			"xpath=(//div[contains(@id,'_ratingThumbContent')]/div[@class='aui-rating-label-element'])[1]",
			""
		};
	private static String[] _BLOGS_COMMENT_TEXT_VOTE_2 = {
			"xpath=(//div[contains(@id,'_ratingThumbContent')]/div[@class='aui-rating-label-element'])[2]",
			""
		};
	private static String[] _BLOGS_COMMENT_TEXT_VOTE_3 = {
			"xpath=(//div[contains(@id,'_ratingThumbContent')]/div[@class='aui-rating-label-element'])[3]",
			""
		};
	private static String[] _BLOGS_COMMENT_TEXT_VOTE_DOWN_1 = {
			"xpath=(//div[contains(@id,'_ratingThumbContent')]/a[2])[1]", ""
		};
	private static String[] _BLOGS_COMMENT_TEXT_VOTE_DOWN_2 = {
			"xpath=(//div[contains(@id,'_ratingThumbContent')]/a[2])[2]", ""
		};
	private static String[] _BLOGS_COMMENT_TEXT_VOTE_DOWN_3 = {
			"xpath=(//div[contains(@id,'_ratingThumbContent')]/a[2])[3]", ""
		};
	private static String[] _BLOGS_COMMENT_TEXT_VOTE_UP_1 = {
			"xpath=(//div[contains(@id,'_ratingThumbContent')]/a[1])[1]", ""
		};
	private static String[] _BLOGS_COMMENT_TEXT_VOTE_UP_2 = {
			"xpath=(//div[contains(@id,'_ratingThumbContent')]/a[1])[2]", ""
		};
	private static String[] _BLOGS_COMMENT_TEXT_VOTE_UP_3 = {
			"xpath=(//div[contains(@id,'_ratingThumbContent')]/a[1])[3]", ""
		};
	private static String[] _BLOGS_ENTRY_FIELD_TRACKBACK_URL = {
			"//div[contains(.,'Trackback URL:')]/input", ""
		};
	private static String[] _BLOGS_ENTRY_LINK_ADD_COMMENT_1 = {
			"//fieldset[contains(@class,'add-comment')]/div/a[.='Be the first.']",
			"Be the first."
		};
	private static String[] _BLOGS_ENTRY_LINK_ADD_COMMENT_2 = {
			"//fieldset[contains(@class,'add-comment')]/div/span/a/span[.='Add Comment']",
			"Add Comment"
		};
	private static String[] _BLOGS_ENTRY_LINK_COMMENT_COUNT = {
			"//div[@class='comments']/a", ""
		};
	private static String[] _BLOGS_ENTRY_LINK_CONFIRM_DELETE_VIEW = {
			"", "Are you sure you want to delete this?"
		};
	private static String[] _BLOGS_ENTRY_LINK_DELETE = {
			"//span/a/span[contains(.,'Move to the Recycle Bin')]",
			"Move to the Recycle Bin"
		};
	private static String[] _BLOGS_ENTRY_LINK_EDIT = {
			"//span/a/span[contains(.,'Edit')]", "Edit"
		};
	private static String[] _BLOGS_ENTRY_LINK_FLAG = {
			"//div[@title='Flag']/span/a/span", "Flag"
		};
	private static String[] _BLOGS_ENTRY_LINK_TEXT = { "//a[@class='next']", "" };
	private static String[] _BLOGS_ENTRY_LINK_TITLE = {
			"//div[@class='entry-title']/h2/a", ""
		};
	private static String[] _BLOGS_ENTRY_LINK_PERMISSIONS = {
			"//span/a/span[contains(.,'Permissions')]", ""
		};
	private static String[] _BLOGS_ENTRY_LINK_PREVIOUS = {
			"//a[@class='previous']", ""
		};
	private static String[] _BLOGS_ENTRY_LINK_RATE_STAR_1 = {
			"//div[contains(@id,'_ratingStarContent')]/a[1]", ""
		};
	private static String[] _BLOGS_ENTRY_LINK_RATE_STAR_2 = {
			"//div[contains(@id,'_ratingStarContent')]/a[2]", ""
		};
	private static String[] _BLOGS_ENTRY_LINK_RATE_STAR_3 = {
			"//div[contains(@id,'_ratingStarContent')]/a[3]", ""
		};
	private static String[] _BLOGS_ENTRY_LINK_RATE_STAR_4 = {
			"//div[contains(@id,'_ratingStarContent')]/a[4]", ""
		};
	private static String[] _BLOGS_ENTRY_LINK_RATE_STAR_5 = {
			"//div[contains(@id,'_ratingStarContent')]/a[5]", ""
		};
	private static String[] _BLOGS_ENTRY_LINK_SUBSCRIBE_COMMENT = {
			"//span[@class='subscribe-link']/a/span[.='Subscribe to Comments']",
			"Subscribe to Comments"
		};
	private static String[] _BLOGS_ENTRY_LINK_UNSUBSCRIBE_COMMENT = {
			"//span[@class='subscribe-link']/a/span[.='Unsubscribe from Comments']",
			"Unsubscribe from Comments"
		};
	private static String[] _BLOGS_ENTRY_TEXT_ENTRY_AUTHOR = {
			"//div[@class='entry-author']", ""
		};
	private static String[] _BLOGS_ENTRY_TEXT_CONTENT = {
			"//div[@class='entry-body']/p", ""
		};
	private static String[] _BLOGS_ENTRY_TEXT_DATE = {
			"//div[@class='entry-date']", ""
		};
	private static String[] _BLOGS_ENTRY_TEXT_DRAFT = {
			"//div[contains(@class,'draft')]/div/h3", "Draft"
		};
	private static String[] _BLOGS_ENTRY_TEXT_TITLE = {
			"//h1[@class='header-title']/span", ""
		};
	private static String[] _BLOGS_ENTRY_TEXT_NEXT = {
			"//span[@class='next']", ""
		};
	private static String[] _BLOGS_ENTRY_TEXT_PREVIOUS = {
			"//span[@class='previous']", ""
		};
	private static String[] _BLOGS_ENTRY_TEXT_RATING = {
			"xpath=(//div[@class='aui-rating-label-element'])[1]", ""
		};
	private static String[] _BLOGS_ENTRY_TEXT_RATING_AVERAGE = {
			"xpath=(//div[@class='aui-rating-label-element'])[2]", ""
		};
	private static String[] _BLOGS_ENTRY_TEXT_VIEW_COUNT = {
			"//span[@class='view-count']", ""
		};
	private static String[] _PANEL_LINK_COMMENTS = { "blogsCommentsPanel", "" };
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
	private static String[] _PORTLET_LINK_OPTIONS = {
			"//span[@title='Options']/ul/li/strong/a", ""
		};
	private static String[] _PORTLET_LINK_OPTIONS_EXPORT_IMPORT = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Export / Import')]",
			"Export / Import"
		};
	private static String[] _PORTLET_TEXT_COMMENT_MESSAGE = {
			"//fieldset[contains(@class,'add-comment')]/div", "No comments yet."
		};
	private static String[] _PORTLET_TEXT_DESCRIPTION = {
			"cpContextPanelTemplate", ""
		};
	private static String[] _PORTLET_TEXT_PORTLET_TITLE = { "cpPortletTitle", "" };
	private static String[] _PORTLET_TEXT_SUCCESS = {
			"//div[@class='portlet-msg-success']",
			"Your request completed successfully."
		};
	private static Map<String, String[]> _paths = new HashMap<String, String[]>();

	static {
		_paths.put("BLOGS_COMMENT_LINK_AUTHOR_1", _BLOGS_COMMENT_LINK_AUTHOR_1);
		_paths.put("BLOGS_COMMENT_LINK_AUTHOR_2", _BLOGS_COMMENT_LINK_AUTHOR_2);
		_paths.put("BLOGS_COMMENT_LINK_AUTHOR_3", _BLOGS_COMMENT_LINK_AUTHOR_3);
		_paths.put("BLOGS_COMMENT_LINK_CONFIRM_DELETE",
			_BLOGS_COMMENT_LINK_CONFIRM_DELETE);
		_paths.put("BLOGS_COMMENT_LINK_DELETE_1", _BLOGS_COMMENT_LINK_DELETE_1);
		_paths.put("BLOGS_COMMENT_LINK_DELETE_2", _BLOGS_COMMENT_LINK_DELETE_2);
		_paths.put("BLOGS_COMMENT_LINK_DELETE_3", _BLOGS_COMMENT_LINK_DELETE_3);
		_paths.put("BLOGS_COMMENT_LINK_EDIT_1", _BLOGS_COMMENT_LINK_EDIT_1);
		_paths.put("BLOGS_COMMENT_LINK_EDIT_2", _BLOGS_COMMENT_LINK_EDIT_2);
		_paths.put("BLOGS_COMMENT_LINK_EDIT_3", _BLOGS_COMMENT_LINK_EDIT_3);
		_paths.put("BLOGS_COMMENT_LINK_READ_MORE", _BLOGS_COMMENT_LINK_READ_MORE);
		_paths.put("BLOGS_COMMENT_LINK_REPLY_1", _BLOGS_COMMENT_LINK_REPLY_1);
		_paths.put("BLOGS_COMMENT_LINK_REPLY_2", _BLOGS_COMMENT_LINK_REPLY_2);
		_paths.put("BLOGS_COMMENT_LINK_REPLY_3", _BLOGS_COMMENT_LINK_REPLY_3);
		_paths.put("BLOGS_COMMENT_LINK_TOP_1", _BLOGS_COMMENT_LINK_TOP_1);
		_paths.put("BLOGS_COMMENT_LINK_TOP_2", _BLOGS_COMMENT_LINK_TOP_2);
		_paths.put("BLOGS_COMMENT_LINK_TOP_3", _BLOGS_COMMENT_LINK_TOP_3);
		_paths.put("BLOGS_COMMENT_TEXT_BODY_1", _BLOGS_COMMENT_TEXT_BODY_1);
		_paths.put("BLOGS_COMMENT_TEXT_BODY_2", _BLOGS_COMMENT_TEXT_BODY_2);
		_paths.put("BLOGS_COMMENT_TEXT_BODY_3", _BLOGS_COMMENT_TEXT_BODY_3);
		_paths.put("BLOGS_COMMENT_TEXT_SUCCESS", _BLOGS_COMMENT_TEXT_SUCCESS);
		_paths.put("BLOGS_COMMENT_TEXT_VOTE_1", _BLOGS_COMMENT_TEXT_VOTE_1);
		_paths.put("BLOGS_COMMENT_TEXT_VOTE_2", _BLOGS_COMMENT_TEXT_VOTE_2);
		_paths.put("BLOGS_COMMENT_TEXT_VOTE_3", _BLOGS_COMMENT_TEXT_VOTE_3);
		_paths.put("BLOGS_COMMENT_TEXT_VOTE_DOWN_1",
			_BLOGS_COMMENT_TEXT_VOTE_DOWN_1);
		_paths.put("BLOGS_COMMENT_TEXT_VOTE_DOWN_2",
			_BLOGS_COMMENT_TEXT_VOTE_DOWN_2);
		_paths.put("BLOGS_COMMENT_TEXT_VOTE_DOWN_3",
			_BLOGS_COMMENT_TEXT_VOTE_DOWN_3);
		_paths.put("BLOGS_COMMENT_TEXT_VOTE_UP_1", _BLOGS_COMMENT_TEXT_VOTE_UP_1);
		_paths.put("BLOGS_COMMENT_TEXT_VOTE_UP_2", _BLOGS_COMMENT_TEXT_VOTE_UP_2);
		_paths.put("BLOGS_COMMENT_TEXT_VOTE_UP_3", _BLOGS_COMMENT_TEXT_VOTE_UP_3);
		_paths.put("BLOGS_ENTRY_FIELD_TRACKBACK_URL",
			_BLOGS_ENTRY_FIELD_TRACKBACK_URL);
		_paths.put("BLOGS_ENTRY_LINK_ADD_COMMENT_1",
			_BLOGS_ENTRY_LINK_ADD_COMMENT_1);
		_paths.put("BLOGS_ENTRY_LINK_ADD_COMMENT_2",
			_BLOGS_ENTRY_LINK_ADD_COMMENT_2);
		_paths.put("BLOGS_ENTRY_LINK_COMMENT_COUNT",
			_BLOGS_ENTRY_LINK_COMMENT_COUNT);
		_paths.put("BLOGS_ENTRY_LINK_CONFIRM_DELETE_VIEW",
			_BLOGS_ENTRY_LINK_CONFIRM_DELETE_VIEW);
		_paths.put("BLOGS_ENTRY_LINK_DELETE", _BLOGS_ENTRY_LINK_DELETE);
		_paths.put("BLOGS_ENTRY_LINK_EDIT", _BLOGS_ENTRY_LINK_EDIT);
		_paths.put("BLOGS_ENTRY_LINK_FLAG", _BLOGS_ENTRY_LINK_FLAG);
		_paths.put("BLOGS_ENTRY_LINK_TEXT", _BLOGS_ENTRY_LINK_TEXT);
		_paths.put("BLOGS_ENTRY_LINK_TITLE", _BLOGS_ENTRY_LINK_TITLE);
		_paths.put("BLOGS_ENTRY_LINK_PERMISSIONS", _BLOGS_ENTRY_LINK_PERMISSIONS);
		_paths.put("BLOGS_ENTRY_LINK_PREVIOUS", _BLOGS_ENTRY_LINK_PREVIOUS);
		_paths.put("BLOGS_ENTRY_LINK_RATE_STAR_1", _BLOGS_ENTRY_LINK_RATE_STAR_1);
		_paths.put("BLOGS_ENTRY_LINK_RATE_STAR_2", _BLOGS_ENTRY_LINK_RATE_STAR_2);
		_paths.put("BLOGS_ENTRY_LINK_RATE_STAR_3", _BLOGS_ENTRY_LINK_RATE_STAR_3);
		_paths.put("BLOGS_ENTRY_LINK_RATE_STAR_4", _BLOGS_ENTRY_LINK_RATE_STAR_4);
		_paths.put("BLOGS_ENTRY_LINK_RATE_STAR_5", _BLOGS_ENTRY_LINK_RATE_STAR_5);
		_paths.put("BLOGS_ENTRY_LINK_SUBSCRIBE_COMMENT",
			_BLOGS_ENTRY_LINK_SUBSCRIBE_COMMENT);
		_paths.put("BLOGS_ENTRY_LINK_UNSUBSCRIBE_COMMENT",
			_BLOGS_ENTRY_LINK_UNSUBSCRIBE_COMMENT);
		_paths.put("BLOGS_ENTRY_TEXT_ENTRY_AUTHOR",
			_BLOGS_ENTRY_TEXT_ENTRY_AUTHOR);
		_paths.put("BLOGS_ENTRY_TEXT_CONTENT", _BLOGS_ENTRY_TEXT_CONTENT);
		_paths.put("BLOGS_ENTRY_TEXT_DATE", _BLOGS_ENTRY_TEXT_DATE);
		_paths.put("BLOGS_ENTRY_TEXT_DRAFT", _BLOGS_ENTRY_TEXT_DRAFT);
		_paths.put("BLOGS_ENTRY_TEXT_TITLE", _BLOGS_ENTRY_TEXT_TITLE);
		_paths.put("BLOGS_ENTRY_TEXT_NEXT", _BLOGS_ENTRY_TEXT_NEXT);
		_paths.put("BLOGS_ENTRY_TEXT_PREVIOUS", _BLOGS_ENTRY_TEXT_PREVIOUS);
		_paths.put("BLOGS_ENTRY_TEXT_RATING", _BLOGS_ENTRY_TEXT_RATING);
		_paths.put("BLOGS_ENTRY_TEXT_RATING_AVERAGE",
			_BLOGS_ENTRY_TEXT_RATING_AVERAGE);
		_paths.put("BLOGS_ENTRY_TEXT_VIEW_COUNT", _BLOGS_ENTRY_TEXT_VIEW_COUNT);
		_paths.put("PANEL_LINK_COMMENTS", _PANEL_LINK_COMMENTS);
		_paths.put("PORTLET_LINK_BACK", _PORTLET_LINK_BACK);
		_paths.put("PORTLET_LINK_BREADCRUMB_1", _PORTLET_LINK_BREADCRUMB_1);
		_paths.put("PORTLET_LINK_BREADCRUMB_2", _PORTLET_LINK_BREADCRUMB_2);
		_paths.put("PORTLET_LINK_BREADCRUMB_3", _PORTLET_LINK_BREADCRUMB_3);
		_paths.put("PORTLET_LINK_OPTIONS", _PORTLET_LINK_OPTIONS);
		_paths.put("PORTLET_LINK_OPTIONS_EXPORT_IMPORT",
			_PORTLET_LINK_OPTIONS_EXPORT_IMPORT);
		_paths.put("PORTLET_TEXT_COMMENT_MESSAGE", _PORTLET_TEXT_COMMENT_MESSAGE);
		_paths.put("PORTLET_TEXT_DESCRIPTION", _PORTLET_TEXT_DESCRIPTION);
		_paths.put("PORTLET_TEXT_PORTLET_TITLE", _PORTLET_TEXT_PORTLET_TITLE);
		_paths.put("PORTLET_TEXT_SUCCESS", _PORTLET_TEXT_SUCCESS);
	}
}