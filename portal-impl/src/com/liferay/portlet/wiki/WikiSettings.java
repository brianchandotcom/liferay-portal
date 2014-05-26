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

package com.liferay.portlet.wiki;

import com.liferay.portal.kernel.settings.BaseServiceSettings;
import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PortalUtil;

/**
 * @author Iván Zaera
 */
public class WikiSettings extends BaseServiceSettings {

	public WikiSettings(Settings settings) {
		super(settings, _fallbackKeys);
	}

	public String getDisplayStyle() {
		return typedSettings.getValue("displayStyle");
	}

	public long getDisplayStyleGroupId(long defaultDisplayStyleGroupId) {
		return typedSettings.getLongValue(
			"displayStyleGroupId", defaultDisplayStyleGroupId);
	}

	public String getEmailFromAddress() {
		return typedSettings.getValue("emailFromAddress");
	}

	public String getEmailFromName() {
		return typedSettings.getValue("emailFromName");
	}

	public LocalizedValuesMap getEmailPageAddedBody() {
		return typedSettings.getLocalizedValuesMap("emailPageAddedBody");
	}

	public String getEmailPageAddedBodyXml() {
		LocalizedValuesMap emailPageAddedBodyMap = getEmailPageAddedBody();

		return emailPageAddedBodyMap.getLocalizationXml();
	}

	public boolean getEmailPageAddedEnabled() {
		return typedSettings.getBooleanValue("emailPageAddedEnabled");
	}

	public LocalizedValuesMap getEmailPageAddedSubject() {
		return typedSettings.getLocalizedValuesMap("emailPageAddedSubject");
	}

	public String getEmailPageAddedSubjectXml() {
		LocalizedValuesMap emailPageAddedSubjectMap =
			getEmailPageAddedSubject();

		return emailPageAddedSubjectMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailPageUpdatedBody() {
		return typedSettings.getLocalizedValuesMap("emailPageUpdatedBody");
	}

	public String getEmailPageUpdatedBodyXml() {
		LocalizedValuesMap emailPageUpdatedBodyMap = getEmailPageUpdatedBody();

		return emailPageUpdatedBodyMap.getLocalizationXml();
	}

	public boolean getEmailPageUpdatedEnabled() {
		return typedSettings.getBooleanValue("emailPageUpdatedEnabled");
	}

	public LocalizedValuesMap getEmailPageUpdatedSubject() {
		return typedSettings.getLocalizedValuesMap("emailPageUpdatedSubject");
	}

	public String getEmailPageUpdatedSubjectXml() {
		LocalizedValuesMap emailPageUpdatedSubjectMap =
			getEmailPageUpdatedSubject();

		return emailPageUpdatedSubjectMap.getLocalizationXml();
	}

	public boolean getEnableCommentRatings() {
		return typedSettings.getBooleanValue("enableCommentRatings");
	}

	public boolean getEnableComments() {
		return typedSettings.getBooleanValue("enableComments");
	}

	public boolean getEnablePageRatings() {
		return typedSettings.getBooleanValue("enablePageRatings");
	}

	public boolean getEnableRelatedAssets() {
		return typedSettings.getBooleanValue("enableRelatedAssets");
	}

	public boolean getEnableRSS() {
		if (!PortalUtil.isRSSFeedsEnabled()) {
			return false;
		}

		return typedSettings.getBooleanValue("enableRss");
	}

	public String[] getHiddenNodes() {
		return typedSettings.getValues("hiddenNodes");
	}

	public int getRssDelta() {
		return typedSettings.getIntegerValue("rssDelta");
	}

	public String getRssDisplayStyle() {
		return typedSettings.getValue("rssDisplayStyle");
	}

	public String getRssFeedType() {
		return typedSettings.getValue("rssFeedType");
	}

	public String[] getVisibleNodes() {
		return typedSettings.getValues("visibleNodes");
	}

	public void setHiddenNodes(String[] hiddenNodes) {
		typedSettings.setValues("hiddenNodes", hiddenNodes);
	}

	public void setVisibleNodes(String[] visibleNodes) {
		typedSettings.setValues("visibleNodes", visibleNodes);
	}

	private static FallbackKeys _fallbackKeys = new FallbackKeys();

	static {
		_fallbackKeys.add(
			"emailFromAddress", PropsKeys.WIKI_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		_fallbackKeys.add(
			"emailFromName", PropsKeys.WIKI_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);
		_fallbackKeys.add(
			"emailPageAddedBody", PropsKeys.WIKI_EMAIL_PAGE_ADDED_BODY);
		_fallbackKeys.add(
			"emailPageAddedEnabled", PropsKeys.WIKI_EMAIL_PAGE_ADDED_ENABLED);
		_fallbackKeys.add(
			"emailPageAddedSubject", PropsKeys.WIKI_EMAIL_PAGE_ADDED_SUBJECT);
		_fallbackKeys.add(
			"emailPageUpdatedBody", PropsKeys.WIKI_EMAIL_PAGE_UPDATED_BODY);
		_fallbackKeys.add(
			"emailPageUpdatedEnabled",
			PropsKeys.WIKI_EMAIL_PAGE_UPDATED_ENABLED);
		_fallbackKeys.add(
			"emailPageUpdatedSubject",
			PropsKeys.WIKI_EMAIL_PAGE_UPDATED_SUBJECT);
		_fallbackKeys.add(
			"enableComments", PropsKeys.WIKI_PAGE_COMMENTS_ENABLED);
		_fallbackKeys.add(
			"enableCommentRatings", PropsKeys.WIKI_COMMENT_RATINGS_ENABLED);
		_fallbackKeys.add(
			"enablePageRatings", PropsKeys.WIKI_PAGE_RATINGS_ENABLED);
		_fallbackKeys.add(
			"enableRelatedAssets", PropsKeys.WIKI_RELATED_ASSETS_ENABLED);
		_fallbackKeys.add("enableRss", PropsKeys.WIKI_RSS_ENABLED);
		_fallbackKeys.add(
			"rssDelta", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		_fallbackKeys.add(
			"rssDisplayStyle", PropsKeys.RSS_FEED_DISPLAY_STYLE_DEFAULT);
		_fallbackKeys.add("rssFeedType", PropsKeys.RSS_FEED_TYPE_DEFAULT);
	}

}