/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs;

import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PortalUtil;

/**
 * @author Iván Zaera
 */
public class BlogsPortletInstanceSettings {

	public static final String[] MULTI_VALUED_KEYS = {};

	public static FallbackKeys getFallbackKeys() {
		FallbackKeys fallbackKeys = new FallbackKeys();

		fallbackKeys.add(
			"enableComments", PropsKeys.BLOGS_ENTRY_COMMENTS_ENABLED);
		fallbackKeys.add(
			"enableCommentRatings", PropsKeys.BLOGS_COMMENT_RATINGS_ENABLED);
		fallbackKeys.add("enableFlags", PropsKeys.BLOGS_FLAGS_ENABLED);
		fallbackKeys.add("enableRatings", PropsKeys.BLOGS_RATINGS_ENABLED);
		fallbackKeys.add(
			"enableRelatedAssets", PropsKeys.BLOGS_RELATED_ASSETS_ENABLED);
		fallbackKeys.add("enableRss", PropsKeys.BLOGS_RSS_ENABLED);
		fallbackKeys.add(
			"enableSocialBookmarks", PropsKeys.BLOGS_SOCIAL_BOOKMARKS_ENABLED);
		fallbackKeys.add("displayStyle", PropsKeys.BLOGS_DISPLAY_STYLE);
		fallbackKeys.add(
			"pageDelta", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		fallbackKeys.add(
			"rssDelta", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		fallbackKeys.add(
			"rssDisplayStyle", PropsKeys.RSS_FEED_DISPLAY_STYLE_DEFAULT);
		fallbackKeys.add("rssFeedType", PropsKeys.RSS_FEED_TYPE_DEFAULT);
		fallbackKeys.add(
			"socialBookmarksDisplayPosition",
			PropsKeys.BLOGS_SOCIAL_BOOKMARKS_DISPLAY_POSITION);
		fallbackKeys.add(
			"socialBookmarksDisplayStyle",
			PropsKeys.BLOGS_SOCIAL_BOOKMARKS_DISPLAY_STYLE);
		fallbackKeys.add(
			"socialBookmarksTypes", PropsKeys.SOCIAL_BOOKMARK_TYPES);

		return fallbackKeys;
	}

	public BlogsPortletInstanceSettings(Settings settings) {
		_typedSettings = new TypedSettings(settings);
	}

	public String getDisplayStyle() {
		return _typedSettings.getValue("displayStyle");
	}

	public long getDisplayStyleGroupId(long defaultDisplayStyleGroupId) {
		return _typedSettings.getLongValue(
			"displayStyleGroupId", defaultDisplayStyleGroupId);
	}

	public boolean getEnableCommentRatings() {
		return _typedSettings.getBooleanValue("enableCommentRatings");
	}

	public boolean getEnableComments() {
		return _typedSettings.getBooleanValue("enableComments");
	}

	public boolean getEnableFlags() {
		return _typedSettings.getBooleanValue("enableFlags");
	}

	public boolean getEnableRatings() {
		return _typedSettings.getBooleanValue("enableRatings");
	}

	public boolean getEnableRelatedAssets() {
		return _typedSettings.getBooleanValue("enableRelatedAssets");
	}

	public boolean getEnableRSS() {
		if (!PortalUtil.isRSSFeedsEnabled()) {
			return false;
		}

		return _typedSettings.getBooleanValue("enableRss");
	}

	public boolean getEnableSocialBookmarks() {
		return _typedSettings.getBooleanValue("enableSocialBookmarks");
	}

	public int getPageDelta() {
		return _typedSettings.getIntegerValue("pageDelta");
	}

	public int getRssDelta() {
		return _typedSettings.getIntegerValue("rssDelta");
	}

	public String getRssDisplayStyle() {
		return _typedSettings.getValue("rssDisplayStyle");
	}

	public String getRssFeedType() {
		return _typedSettings.getValue("rssFeedType");
	}

	public String getSocialBookmarksDisplayPosition() {
		return _typedSettings.getValue("socialBookmarksDisplayPosition");
	}

	public String getSocialBookmarksDisplayStyle() {
		return _typedSettings.getValue("socialBookmarksDisplayStyle");
	}

	public String getSocialBookmarksTypes() {
		return _typedSettings.getValue("socialBookmarksTypes");
	}

	private TypedSettings _typedSettings;

}