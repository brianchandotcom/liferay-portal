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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.ParameterMapSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;

import java.util.Map;

/**
 * @author Iván Zaera
 */
public class BlogsPortletInstanceSettings {

	public static final String[] ALL_KEYS = {
		"displayStyle", "displayStyleGroupId", "pageDelta", "rssDelta",
		"rssDisplayStyle", "rssFeedType", "socialBookmarksDisplayPosition",
		"socialBookmarksDisplayStyle", "socialBookmarksTypes",
		"enableCommentRatings", "enableComments", "enableFlags",
		"enableRatings", "enableRelatedAssets", "enableRss",
		"enableSocialBookmarks"
	};

	public static BlogsPortletInstanceSettings getInstance(
			Layout layout, String portletId)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getPortletInstanceSettings(
			layout, portletId);

		return new BlogsPortletInstanceSettings(settings);
	}

	public static BlogsPortletInstanceSettings getInstance(
			Layout layout, String portletId, Map<String, String[]> parameterMap)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getPortletInstanceSettings(
			layout, portletId);

		return new BlogsPortletInstanceSettings(
			new ParameterMapSettings(parameterMap, settings));
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

	public boolean isEnableCommentRatings() {
		return _typedSettings.getBooleanValue("enableCommentRatings");
	}

	public boolean isEnableComments() {
		return _typedSettings.getBooleanValue("enableComments");
	}

	public boolean isEnableFlags() {
		return _typedSettings.getBooleanValue("enableFlags");
	}

	public boolean isEnableRatings() {
		return _typedSettings.getBooleanValue("enableRatings");
	}

	public boolean isEnableRelatedAssets() {
		return _typedSettings.getBooleanValue("enableRelatedAssets");
	}

	public boolean isEnableRSS() {
		if (!PortalUtil.isRSSFeedsEnabled()) {
			return false;
		}

		return _typedSettings.getBooleanValue("enableRss");
	}

	public boolean isEnableSocialBookmarks() {
		return _typedSettings.getBooleanValue("enableSocialBookmarks");
	}

	private static FallbackKeys _getFallbackKeys() {
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

	private static final String[] _MULTI_VALUED_KEYS = {};

	static {
		SettingsFactory settingsFactory =
			SettingsFactoryUtil.getSettingsFactory();

		settingsFactory.registerSettingsMetadata(
			PortletKeys.BLOGS, _getFallbackKeys(), _MULTI_VALUED_KEYS);
		settingsFactory.registerSettingsMetadata(
			PortletKeys.BLOGS_ADMIN, _getFallbackKeys(), _MULTI_VALUED_KEYS);
	}

	private final TypedSettings _typedSettings;

}