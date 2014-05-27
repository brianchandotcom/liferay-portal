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
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PortalUtil;

/**
 * @author Iván Zaera
 */
public class BlogsSettings {

	public static FallbackKeys FALLBACK_KEYS = new FallbackKeys();

	static {
		FALLBACK_KEYS.add(
			"enableComments", PropsKeys.BLOGS_ENTRY_COMMENTS_ENABLED);
		FALLBACK_KEYS.add(
			"enableCommentRatings", PropsKeys.BLOGS_COMMENT_RATINGS_ENABLED);
		FALLBACK_KEYS.add("enableFlags", PropsKeys.BLOGS_FLAGS_ENABLED);
		FALLBACK_KEYS.add("enableRatings", PropsKeys.BLOGS_RATINGS_ENABLED);
		FALLBACK_KEYS.add(
			"enableRelatedAssets", PropsKeys.BLOGS_RELATED_ASSETS_ENABLED);
		FALLBACK_KEYS.add("enableRss", PropsKeys.BLOGS_RSS_ENABLED);
		FALLBACK_KEYS.add(
			"enableSocialBookmarks", PropsKeys.BLOGS_SOCIAL_BOOKMARKS_ENABLED);
		FALLBACK_KEYS.add("displayStyle", PropsKeys.BLOGS_DISPLAY_STYLE);
		FALLBACK_KEYS.add(
			"emailEntryAddedBody", PropsKeys.BLOGS_EMAIL_ENTRY_ADDED_BODY);
		FALLBACK_KEYS.add(
			"emailEntryAddedEnabled",
			PropsKeys.BLOGS_EMAIL_ENTRY_ADDED_ENABLED);
		FALLBACK_KEYS.add(
			"emailEntryAddedSubject",
			PropsKeys.BLOGS_EMAIL_ENTRY_ADDED_SUBJECT);
		FALLBACK_KEYS.add(
			"emailEntryUpdatedBody", PropsKeys.BLOGS_EMAIL_ENTRY_UPDATED_BODY);
		FALLBACK_KEYS.add(
			"emailEntryUpdatedEnabled",
			PropsKeys.BLOGS_EMAIL_ENTRY_UPDATED_ENABLED);
		FALLBACK_KEYS.add(
			"emailEntryUpdatedSubject",
			PropsKeys.BLOGS_EMAIL_ENTRY_UPDATED_SUBJECT);
		FALLBACK_KEYS.add(
			"emailFromAddress", PropsKeys.BLOGS_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		FALLBACK_KEYS.add(
			"emailFromName", PropsKeys.BLOGS_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);
		FALLBACK_KEYS.add(
			"pageDelta", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		FALLBACK_KEYS.add(
			"rssDelta", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		FALLBACK_KEYS.add(
			"rssDisplayStyle", PropsKeys.RSS_FEED_DISPLAY_STYLE_DEFAULT);
		FALLBACK_KEYS.add("rssFeedType", PropsKeys.RSS_FEED_TYPE_DEFAULT);
		FALLBACK_KEYS.add(
			"socialBookmarksDisplayPosition",
			PropsKeys.BLOGS_SOCIAL_BOOKMARKS_DISPLAY_POSITION);
		FALLBACK_KEYS.add(
			"socialBookmarksDisplayStyle",
			PropsKeys.BLOGS_SOCIAL_BOOKMARKS_DISPLAY_STYLE);
		FALLBACK_KEYS.add(
			"socialBookmarksTypes", PropsKeys.SOCIAL_BOOKMARK_TYPES);
	}

	public BlogsSettings(Settings settings) {
		_typedSettings = new TypedSettings(settings);
	}

	public String getDisplayStyle() {
		return _typedSettings.getValue("displayStyle");
	}

	public long getDisplayStyleGroupId(long defaultDisplayStyleGroupId) {
		return _typedSettings.getLongValue(
			"displayStyleGroupId", defaultDisplayStyleGroupId);
	}

	public LocalizedValuesMap getEmailEntryAddedBody() {
		return _typedSettings.getLocalizedValuesMap("emailEntryAddedBody");
	}

	public String getEmailEntryAddedBodyXml() {
		LocalizedValuesMap emailEntryAddedBodyMap = getEmailEntryAddedBody();

		return emailEntryAddedBodyMap.getLocalizationXml();
	}

	public boolean getEmailEntryAddedEnabled() {
		return _typedSettings.getBooleanValue("emailEntryAddedEnabled");
	}

	public LocalizedValuesMap getEmailEntryAddedSubject() {
		return _typedSettings.getLocalizedValuesMap("emailEntryAddedSubject");
	}

	public String getEmailEntryAddedSubjectXml() {
		LocalizedValuesMap emailEntryAddedSubjectMap =
			getEmailEntryAddedSubject();

		return emailEntryAddedSubjectMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailEntryUpdatedBody() {
		return _typedSettings.getLocalizedValuesMap("emailEntryUpdatedBody");
	}

	public String getEmailEntryUpdatedBodyXml() {
		LocalizedValuesMap emailEntryUpdatedBodyMap =
			getEmailEntryUpdatedBody();

		return emailEntryUpdatedBodyMap.getLocalizationXml();
	}

	public boolean getEmailEntryUpdatedEnabled() {
		return _typedSettings.getBooleanValue("emailEntryUpdatedEnabled");
	}

	public LocalizedValuesMap getEmailEntryUpdatedSubject() {
		return _typedSettings.getLocalizedValuesMap("emailEntryUpdatedSubject");
	}

	public String getEmailEntryUpdatedSubjectXml() {
		LocalizedValuesMap emailEntryUpdatedSubjectMap =
			getEmailEntryUpdatedSubject();

		return emailEntryUpdatedSubjectMap.getLocalizationXml();
	}

	public String getEmailFromAddress() {
		return _typedSettings.getValue("emailFromAddress");
	}

	public String getEmailFromName() {
		return _typedSettings.getValue("emailFromName");
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