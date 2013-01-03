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

package com.liferay.portalweb.blocks.portal.blogs.controlpanel.actions.entry.rc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPBlogsEntryViewPaths {
	public static Map<String, String> getPaths() {
		return _paths;
	}

	private static Map<String, String> _paths = new HashMap<String, String>();

	static {
		_paths.put("PAGE_NAME", "");
		_paths.put("BLOGS_COMMENT_LINK_AUTHOR_1",
			"xpath=(//span[@class='user-name'])[1]");
		_paths.put("BLOGS_COMMENT_LINK_AUTHOR_2",
			"xpath=(//span[@class='user-name'])[2]");
		_paths.put("BLOGS_COMMENT_LINK_AUTHOR_3",
			"xpath=(//span[@class='user-name'])[3]");
		_paths.put("BLOGS_COMMENT_LINK_CONFIRM_DELETE", "");
		_paths.put("BLOGS_COMMENT_LINK_DELETE_1",
			"xpath=(//span/a/span[.='Delete'])[2]");
		_paths.put("BLOGS_COMMENT_LINK_DELETE_2",
			"xpath=(//span/a/span[.='Delete'])[3]");
		_paths.put("BLOGS_COMMENT_LINK_DELETE_3",
			"xpath=(//span/a/span[.='Delete'])[4]");
		_paths.put("BLOGS_COMMENT_LINK_EDIT_1",
			"xpath=(//span/a/span[.='Edit'])[2]");
		_paths.put("BLOGS_COMMENT_LINK_EDIT_2",
			"xpath=(//span/a/span[.='Edit'])[3]");
		_paths.put("BLOGS_COMMENT_LINK_EDIT_3",
			"xpath=(//span/a/span[.='Edit'])[4]");
		_paths.put("BLOGS_COMMENT_LINK_READ_MORE",
			"xpath=(//div[@class='lfr-discussion-message'])[1]/a");
		_paths.put("BLOGS_COMMENT_LINK_REPLY_1",
			"xpath=(//span/a/span[.='Post Reply'])[1]");
		_paths.put("BLOGS_COMMENT_LINK_REPLY_2",
			"xpath=(//span/a/span[.='Post Reply'])[2]");
		_paths.put("BLOGS_COMMENT_LINK_REPLY_3",
			"xpath=(//span/a/span[.='Post Reply'])[3]");
		_paths.put("BLOGS_COMMENT_LINK_TOP_1",
			"xpath=(//span/a/span[.='Top'])[1]");
		_paths.put("BLOGS_COMMENT_LINK_TOP_2",
			"xpath=(//span/a/span[.='Top'])[2]");
		_paths.put("BLOGS_COMMENT_LINK_TOP_3",
			"xpath=(//span/a/span[.='Top'])[3]");
		_paths.put("BLOGS_COMMENT_TEXT_BODY_1",
			"xpath=(//div[@class='lfr-discussion-message'])[1]");
		_paths.put("BLOGS_COMMENT_TEXT_BODY_2",
			"xpath=(//div[@class='lfr-discussion-message'])[2]");
		_paths.put("BLOGS_COMMENT_TEXT_BODY_3",
			"xpath=(//div[@class='lfr-discussion-message'])[3]");
		_paths.put("BLOGS_COMMENT_TEXT_SUCCESS",
			"_161_discussion-status-messages");
		_paths.put("BLOGS_COMMENT_TEXT_VOTE_1",
			"xpath=(//div[contains(@id,'_ratingThumbContent')]/div[@class='aui-rating-label-element'])[1]");
		_paths.put("BLOGS_COMMENT_TEXT_VOTE_2",
			"xpath=(//div[contains(@id,'_ratingThumbContent')]/div[@class='aui-rating-label-element'])[2]");
		_paths.put("BLOGS_COMMENT_TEXT_VOTE_3",
			"xpath=(//div[contains(@id,'_ratingThumbContent')]/div[@class='aui-rating-label-element'])[3]");
		_paths.put("BLOGS_COMMENT_TEXT_VOTE_DOWN_1",
			"xpath=(//div[contains(@id,'_ratingThumbContent')]/a[2])[1]");
		_paths.put("BLOGS_COMMENT_TEXT_VOTE_DOWN_2",
			"xpath=(//div[contains(@id,'_ratingThumbContent')]/a[2])[2]");
		_paths.put("BLOGS_COMMENT_TEXT_VOTE_DOWN_3",
			"xpath=(//div[contains(@id,'_ratingThumbContent')]/a[2])[3]");
		_paths.put("BLOGS_COMMENT_TEXT_VOTE_UP_1",
			"xpath=(//div[contains(@id,'_ratingThumbContent')]/a[1])[1]");
		_paths.put("BLOGS_COMMENT_TEXT_VOTE_UP_2",
			"xpath=(//div[contains(@id,'_ratingThumbContent')]/a[1])[2]");
		_paths.put("BLOGS_COMMENT_TEXT_VOTE_UP_3",
			"xpath=(//div[contains(@id,'_ratingThumbContent')]/a[1])[3]");
		_paths.put("BLOGS_COMMENT_ADD_FIELD_BODY",
			"//textarea[contains(@id,'_postReplyBody0')]");
		_paths.put("BLOGS_COMMENT_ADD_LINK_CANCEL", "//input[@value='Cancel']");
		_paths.put("BLOGS_COMMENT_ADD_LINK_SAVE", "//input[@value='Reply']");
		_paths.put("BLOGS_COMMENT_ADD_LINK_SUBSCRIBE_ME",
			"_161_subscribeCheckbox");
		_paths.put("BLOGS_COMMENT_ADD_TEXT_SUBSCRIBE_ME",
			"//label[@for='_161_subscribeCheckbox']");
		_paths.put("BLOGS_COMMENT_ADD_TEXT_SUCCESS",
			"_161_discussion-status-messages");
		_paths.put("BLOGS_COMMENT_EDIT_FIELD_BODY",
			"//textarea[contains(@id,'_editReplyBody1')]");
		_paths.put("BLOGS_COMMENT_EDIT_LINK_CANCEL", "//input[@value='Cancel']");
		_paths.put("BLOGS_COMMENT_EDIT_LINK_SAVE", "//input[@value='Publish']");
		_paths.put("BLOGS_COMMENT_EDIT_TEXT_SUCCESS",
			"_161_discussion-status-messages");
		_paths.put("BLOGS_ENTRY_TEXT_TITLE", "//h1[@class='header-title']/span");
		_paths.put("BLOGS_ENTRY_LINK_TEXT", "//div[@class='entry-title']/h2/a");
		_paths.put("BLOGS_ENTRY_TEXT_DATE", "//div[@class='entry-date']");
		_paths.put("BLOGS_ENTRY_LINK_EDIT", "//span/a/span[contains(.,'Edit')]");
		_paths.put("BLOGS_ENTRY_LINK_PERMISSIONS",
			"//span/a/span[contains(.,'Permissions')]");
		_paths.put("BLOGS_ENTRY_LINK_DELETE",
			"//span/a/span[contains(.,'Delete')]");
		_paths.put("BLOGS_ENTRY_TEXT_CONTENT", "//div[@class='entry-body']/p");
		_paths.put("BLOGS_ENTRY_TEXT_ENTRY_AUTHOR",
			"//div[@class='entry-author']");
		_paths.put("BLOGS_ENTRY_LINK_COMMENT_COUNT",
			"//div[@class='comments']/a");
		_paths.put("BLOGS_ENTRY_LINK_FLAG", "//div[@title='Flag']/span/a/span");
		_paths.put("BLOGS_ENTRY_TEXT_DRAFT",
			"//div[contains(@class,'draft')]/div/h3");
		_paths.put("BLOGS_ENTRY_LINK_CONFIRM_DELETE_VIEW", "");
		_paths.put("BLOGS_ENTRY_LINK_NEXT", "//a[@class='next']");
		_paths.put("BLOGS_ENTRY_LINK_PREVIOUS", "//a[@class='previous']");
		_paths.put("BLOGS_ENTRY_LINK_RATE_STAR_1",
			"//div[contains(@id,'_ratingStarContent')]/a[1]");
		_paths.put("BLOGS_ENTRY_LINK_RATE_STAR_2",
			"//div[contains(@id,'_ratingStarContent')]/a[2]");
		_paths.put("BLOGS_ENTRY_LINK_RATE_STAR_3",
			"//div[contains(@id,'_ratingStarContent')]/a[3]");
		_paths.put("BLOGS_ENTRY_LINK_RATE_STAR_4",
			"//div[contains(@id,'_ratingStarContent')]/a[4]");
		_paths.put("BLOGS_ENTRY_LINK_RATE_STAR_5",
			"//div[contains(@id,'_ratingStarContent')]/a[5]");
		_paths.put("BLOGS_ENTRY_LINK_ADD_COMMENT_1",
			"//fieldset[contains(@class,'add-comment')]/div/a[.='Be the first.']");
		_paths.put("BLOGS_ENTRY_LINK_ADD_COMMENT_2",
			"//fieldset[contains(@class,'add-comment')]/div/span/a/span[.='Add Comment']");
		_paths.put("BLOGS_ENTRY_LINK_SUBSCRIBE_COMMENT",
			"//span[@class='subscribe-link']/a/span[.='Subscribe to Comments']");
		_paths.put("BLOGS_ENTRY_LINK_UNSUBSCRIBE_COMMENT",
			"//span[@class='subscribe-link']/a/span[.='Unsubscribe from Comments']");
		_paths.put("BLOGS_ENTRY_TEXT_NEXT", "//span[@class='next']");
		_paths.put("BLOGS_ENTRY_TEXT_PREVIOUS", "//span[@class='previous']");
		_paths.put("BLOGS_ENTRY_TEXT_RATING",
			"xpath=(//div[@class='aui-rating-label-element'])[1]");
		_paths.put("BLOGS_ENTRY_TEXT_RATING_AVERAGE",
			"xpath=(//div[@class='aui-rating-label-element'])[2]");
		_paths.put("BLOGS_ENTRY_TEXT_VIEW_COUNT", "//span[@class='view-count']");
		_paths.put("BLOGS_ENTRY_FIELD_TRACKBACK_URL",
			"//div[contains(.,'Trackback URL:')]/input");
		_paths.put("PANEL_LINK_COMMENTS", "blogsCommentsPanel");
		_paths.put("PORTLET_LINK_BREADCRUMB_1",
			"//nav[@id='breadcrumbs']/ul/li[1]/span/a");
		_paths.put("PORTLET_LINK_BREADCRUMB_2",
			"//nav[@id='breadcrumbs']/ul/li[2]/span/a");
		_paths.put("PORTLET_LINK_BREADCRUMB_3",
			"//nav[@id='breadcrumbs']/ul/li[3]/span/a");
		_paths.put("PORTLET_TEXT_PORTLET_TITLE", "cpPortletTitle");
		_paths.put("PORTLET_TEXT_DESCRIPTION", "cpContextPanelTemplate");
		_paths.put("PORTLET_LINK_OPTIONS",
			"//span[@title='Options']/ul/li/strong/a");
		_paths.put("PORTLET_LINK_OPTIONS_EXPORT_IMPORT",
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Export / Import')]");
		_paths.put("PORTLET_TEXT_COMMENT_MESSAGE",
			"//fieldset[contains(@class,'add-comment')]/div");
		_paths.put("PORTLET_TEXT_SUCCESS", "//div[@class='portlet-msg-success']");
		_paths.put("PORTLET_LINK_BACK", "_161_TabsBack");
	}
}