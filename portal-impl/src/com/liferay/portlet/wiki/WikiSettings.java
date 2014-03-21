/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.settings.BaseServiceSettings;
import com.liferay.portal.settings.FallbackPaths;
import com.liferay.portal.settings.Settings;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.ContentUtil;

/**
 * @author Iván Zaera
 */
public class WikiSettings extends BaseServiceSettings {

	public WikiSettings(Settings settings) {
		super(settings, _fallbackPaths);
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

	public String getEmailPageAddedBody() {
		String emailPageAddedBody = typedSettings.getValue(
			"emailPageAddedBody");

		if (Validator.isNotNull(emailPageAddedBody)) {
			return emailPageAddedBody;
		}

		return ContentUtil.get(
			typedSettings.getValue(PropsKeys.WIKI_EMAIL_PAGE_ADDED_BODY));
	}

	public boolean getEmailPageAddedEnabled() {
		return typedSettings.getBooleanValue("emailPageAddedEnabled");
	}

	public String getEmailPageAddedSubject() {
		return typedSettings.getValue("emailPageAddedSubject");
	}

	public String getEmailPageUpdatedBody() {
		String emailPageUpdatedBody = typedSettings.getValue(
			"emailPageUpdatedBody");

		if (Validator.isNotNull(emailPageUpdatedBody)) {
			return emailPageUpdatedBody;
		}

		return ContentUtil.get(
			typedSettings.getValue(PropsKeys.WIKI_EMAIL_PAGE_UPDATED_BODY));
	}

	public boolean getEmailPageUpdatedEnabled() {
		return typedSettings.getBooleanValue("emailPageUpdatedEnabled");
	}

	public String getEmailPageUpdatedSubject() {
		String emailPageUpdatedSubject = typedSettings.getValue(
			"emailPageUpdatedSubject");

		if (Validator.isNotNull(emailPageUpdatedSubject)) {
			return emailPageUpdatedSubject;
		}

		return ContentUtil.get(
			typedSettings.getValue(PropsKeys.WIKI_EMAIL_PAGE_UPDATED_SUBJECT));
	}

	public boolean getEnableCommentRatings() {
		return typedSettings.getBooleanValue("enableCommentRatings");
	}

	public boolean getEnableComments() {
		if (!PropsValues.WIKI_PAGE_COMMENTS_ENABLED) {
			return false;
		}

		return typedSettings.getBooleanValue("enableComments", true);
	}

	public boolean getEnablePageRatings() {
		if (!PropsValues.WIKI_PAGE_RATINGS_ENABLED) {
			return false;
		}

		return typedSettings.getBooleanValue("enablePageRatings", true);
	}

	public boolean getEnableRelatedAssets() {
		return typedSettings.getBooleanValue("enableRelatedAssets");
	}

	public boolean getEnableRSS() {
		if (!PortalUtil.isRSSFeedsEnabled()) {
			return false;
		}

		return typedSettings.getBooleanValue("enableRss", true);
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

	private static FallbackPaths _getFallbackPaths() {
		FallbackPaths fallbackPaths = new FallbackPaths();

		fallbackPaths.addPath(
			"emailFromAddress", PropsKeys.WIKI_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);

		fallbackPaths.addPath(
			"emailFromName", PropsKeys.WIKI_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);

		fallbackPaths.addPath(
			"emailPageAddedEnabled", PropsKeys.WIKI_EMAIL_PAGE_ADDED_ENABLED);

		fallbackPaths.addPath(
			"emailPageAddedSubject", PropsKeys.WIKI_EMAIL_PAGE_ADDED_SUBJECT);

		fallbackPaths.addPath(
			"emailPageUpdatedEnabled",
			PropsKeys.WIKI_EMAIL_PAGE_UPDATED_ENABLED);

		fallbackPaths.addPath(
			"enableCommentRatings", PropsKeys.WIKI_COMMENT_RATINGS_ENABLED);

		fallbackPaths.addPath(
			"enableRelatedAssets", PropsKeys.WIKI_RELATED_ASSETS_ENABLED);

		fallbackPaths.addPath(
			"rssDelta", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);

		fallbackPaths.addPath(
			"rssDisplayStyle", PropsKeys.RSS_FEED_DISPLAY_STYLE_DEFAULT);

		fallbackPaths.addPath("rssFeedType", PropsKeys.RSS_FEED_TYPE_DEFAULT);

		return fallbackPaths;
	}

	private static FallbackPaths _fallbackPaths = _getFallbackPaths();

}