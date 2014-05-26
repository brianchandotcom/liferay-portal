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

package com.liferay.portlet.messageboards;

import com.liferay.portal.kernel.settings.BaseServiceSettings;
import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messageboards.util.MBUtil;
import com.liferay.util.RSSUtil;

/**
 * @author Jorge Ferrer
 */
public class MBSettings extends BaseServiceSettings {

	public MBSettings(Settings settings) {
		super(settings, _fallbackKeys);
	}

	public String getEmailFromAddress() {
		return typedSettings.getValue("emailFromAddress");
	}

	public String getEmailFromName() {
		return typedSettings.getValue("emailFromName");
	}

	public LocalizedValuesMap getEmailMessageAddedBody() {
		return typedSettings.getLocalizedValuesMap("emailMessageAddedBody");
	}

	public String getEmailMessageAddedBodyXml() {
		LocalizedValuesMap emailMessageBodyMap = getEmailMessageAddedBody();

		return emailMessageBodyMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailMessageAddedSubject() {
		return typedSettings.getLocalizedValuesMap("emailMessageAddedSubject");
	}

	public String getEmailMessageAddedSubjectXml() {
		LocalizedValuesMap emailMessageAddedSubjectMap =
			getEmailMessageAddedSubject();

		return emailMessageAddedSubjectMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailMessageUpdatedBody() {
		return typedSettings.getLocalizedValuesMap("emailMessageUpdatedBody");
	}

	public String getEmailMessageUpdatedBodyXml() {
		LocalizedValuesMap emailMessageUpdatedBodyMap =
			getEmailMessageUpdatedBody();

		return emailMessageUpdatedBodyMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailMessageUpdatedSubject() {
		return typedSettings.getLocalizedValuesMap(
			"emailMessageUpdatedSubject");
	}

	public String getEmailMessageUpdatedSubjectXml() {
		LocalizedValuesMap emailMessageUpdatedSubjectMap =
			getEmailMessageUpdatedSubject();

		return emailMessageUpdatedSubjectMap.getLocalizationXml();
	}

	public String getMessageFormat() {
		String messageFormat = typedSettings.getValue("messageFormat");

		if (MBUtil.isValidMessageFormat(messageFormat)) {
			return messageFormat;
		}

		return "html";
	}

	public String[] getPriorities(String currentLanguageId) {
		return LocalizationUtil.getSettingsValues(
			typedSettings, "priorities", currentLanguageId);
	}

	public String[] getRanks(String languageId) {
		return LocalizationUtil.getSettingsValues(
			typedSettings, "ranks", languageId);
	}

	public String getRecentPostsDateOffset() {
		return typedSettings.getValue("recentPostsDateOffset");
	}

	public int getRSSDelta() {
		return typedSettings.getIntegerValue("rssDelta");
	}

	public String getRSSDisplayStyle() {
		return typedSettings.getValue(
			"rssDisplayStyle", RSSUtil.DISPLAY_STYLE_FULL_CONTENT);
	}

	public String getRSSFeedType() {
		return typedSettings.getValue(
			"rssFeedType", RSSUtil.getFeedType(RSSUtil.ATOM, 1.0));
	}

	public boolean isAllowAnonymousPosting() {
		return typedSettings.getBooleanValue("allowAnonymousPosting");
	}

	public boolean isEmailHtmlFormat() {
		return typedSettings.getBooleanValue("emailHtmlFormat");
	}

	public boolean isEmailMessageAddedEnabled() {
		return typedSettings.getBooleanValue("emailMessageAddedEnabled");
	}

	public boolean isEmailMessageUpdatedEnabled() {
		return typedSettings.getBooleanValue("emailMessageUpdatedEnabled");
	}

	public boolean isEnableFlags() {
		return typedSettings.getBooleanValue("enableFlags");
	}

	public boolean isEnableRatings() {
		return typedSettings.getBooleanValue("enableRatings");
	}

	public boolean isEnableRSS() {
		if (!PortalUtil.isRSSFeedsEnabled()) {
			return false;
		}

		return typedSettings.getBooleanValue("enableRss");
	}

	public boolean isSubscribeByDefault() {
		return typedSettings.getBooleanValue("subscribeByDefault");
	}

	public boolean isThreadAsQuestionByDefault() {
		return typedSettings.getBooleanValue("threadAsQuestionByDefault");
	}

	private static FallbackKeys _fallbackKeys = new FallbackKeys();

	static {
		_fallbackKeys.add(
			"allowAnonymousPosting",
			PropsKeys.MESSAGE_BOARDS_ANONYMOUS_POSTING_ENABLED);
		_fallbackKeys.add(
			"emailFromAddress", PropsKeys.MESSAGE_BOARDS_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		_fallbackKeys.add(
			"emailFromName", PropsKeys.MESSAGE_BOARDS_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);
		_fallbackKeys.add(
			"emailHtmlFormat", PropsKeys.MESSAGE_BOARDS_EMAIL_HTML_FORMAT);
		_fallbackKeys.add(
			"emailMessageAddedBody",
			PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_BODY);
		_fallbackKeys.add(
			"emailMessageAddedEnabled",
			PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_ENABLED);
		_fallbackKeys.add(
			"emailMessageAddedSubject",
			PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_SUBJECT);
		_fallbackKeys.add(
			"emailMessageUpdatedBody",
			PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_BODY);
		_fallbackKeys.add(
			"emailMessageUpdatedEnabled",
			PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_ENABLED);
		_fallbackKeys.add(
			"emailMessageUpdatedSubject",
			PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_SUBJECT);
		_fallbackKeys.add(
			"enableFlags", PropsKeys.MESSAGE_BOARDS_FLAGS_ENABLED);
		_fallbackKeys.add(
			"enableRatings", PropsKeys.MESSAGE_BOARDS_RATINGS_ENABLED);
		_fallbackKeys.add("enableRss", PropsKeys.MESSAGE_BOARDS_RSS_ENABLED);
		_fallbackKeys.add(
			"messageFormat", PropsKeys.MESSAGE_BOARDS_MESSAGE_FORMATS_DEFAULT);
		_fallbackKeys.add(
			"priorities", PropsKeys.MESSAGE_BOARDS_THREAD_PRIORITIES);
		_fallbackKeys.add("ranks", PropsKeys.MESSAGE_BOARDS_USER_RANKS);
		_fallbackKeys.add(
			"recentPostsDateOffset",
			PropsKeys.MESSAGE_BOARDS_RECENT_POSTS_DATE_OFFSET);
		_fallbackKeys.add(
			"rssDelta", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		_fallbackKeys.add(
			"rssDisplayStyle", PropsKeys.RSS_FEED_DISPLAY_STYLE_DEFAULT);
		_fallbackKeys.add("rssFeedType", PropsKeys.RSS_FEED_TYPE_DEFAULT);
		_fallbackKeys.add(
			"subscribeByDefault",
			PropsKeys.MESSAGE_BOARDS_SUBSCRIBE_BY_DEFAULT);
	}

}