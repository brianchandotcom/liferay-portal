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

package com.liferay.message.boards.settings;

import com.liferay.message.boards.constants.MBConstants;
import com.liferay.message.boards.util.MBUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.ParameterMapSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.rss.util.RSSUtil;

import java.util.Map;

/**
 * @author Jorge Ferrer
 */
@Settings.Config(settingsIds = MBConstants.SERVICE_NAME)
public class MBGroupServiceSettings {

	public static final String[] ALL_KEYS = {};

	public static MBGroupServiceSettings getInstance(long groupId)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getSettings(
			new GroupServiceSettingsLocator(groupId, MBConstants.SERVICE_NAME));

		return new MBGroupServiceSettings(settings);
	}

	public static MBGroupServiceSettings getInstance(
			long groupId, Map<String, String[]> parameterMap)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getSettings(
			new GroupServiceSettingsLocator(groupId, MBConstants.SERVICE_NAME));

		ParameterMapSettings parameterMapSettings = new ParameterMapSettings(
			parameterMap, settings);

		return new MBGroupServiceSettings(parameterMapSettings);
	}

	public static void registerSettingsMetadata() {
		SettingsFactoryUtil.registerSettingsMetadata(
			MBGroupServiceSettings.class, null, _getFallbackKeys());
	}

	public MBGroupServiceSettings(Settings settings) {
		_typedSettings = new TypedSettings(settings);
	}

	public String getEmailFromAddress() {
		return _typedSettings.getValue("emailFromAddress");
	}

	public String getEmailFromName() {
		return _typedSettings.getValue("emailFromName");
	}

	public LocalizedValuesMap getEmailMessageAddedBody() {
		return _typedSettings.getLocalizedValuesMap("emailMessageAddedBody");
	}

	public String getEmailMessageAddedBodyXml() {
		return LocalizationUtil.getXml(
			getEmailMessageAddedBody(), "emailMessageAddedBody");
	}

	public LocalizedValuesMap getEmailMessageAddedSubject() {
		return _typedSettings.getLocalizedValuesMap("emailMessageAddedSubject");
	}

	public String getEmailMessageAddedSubjectXml() {
		return LocalizationUtil.getXml(
			getEmailMessageAddedSubject(), "emailMessageAddedSubject");
	}

	public LocalizedValuesMap getEmailMessageUpdatedBody() {
		return _typedSettings.getLocalizedValuesMap("emailMessageUpdatedBody");
	}

	public String getEmailMessageUpdatedBodyXml() {
		return LocalizationUtil.getXml(
			getEmailMessageUpdatedBody(), "emailMessageUpdatedBody");
	}

	public LocalizedValuesMap getEmailMessageUpdatedSubject() {
		return _typedSettings.getLocalizedValuesMap(
			"emailMessageUpdatedSubject");
	}

	public String getEmailMessageUpdatedSubjectXml() {
		return LocalizationUtil.getXml(
			getEmailMessageUpdatedSubject(), "emailMessageUpdatedSubject");
	}

	public String getMessageFormat() {
		String messageFormat = _typedSettings.getValue("messageFormat");

		if (MBUtil.isValidMessageFormat(messageFormat)) {
			return messageFormat;
		}

		return "html";
	}

	public String[] getPriorities(String currentLanguageId) {
		return LocalizationUtil.getSettingsValues(
			_typedSettings.getWrappedSettings(), "priorities",
			currentLanguageId);
	}

	public String[] getRanks(String languageId) {
		return LocalizationUtil.getSettingsValues(
			_typedSettings.getWrappedSettings(), "ranks", languageId);
	}

	public String getRecentPostsDateOffset() {
		return _typedSettings.getValue("recentPostsDateOffset");
	}

	@Settings.Property(name = "rssDelta")
	public int getRSSDelta() {
		return _typedSettings.getIntegerValue("rssDelta");
	}

	@Settings.Property(name = "rssDisplayStyle")
	public String getRSSDisplayStyle() {
		return _typedSettings.getValue(
			"rssDisplayStyle", RSSUtil.DISPLAY_STYLE_FULL_CONTENT);
	}

	@Settings.Property(name = "rssFeedType")
	public String getRSSFeedType() {
		return _typedSettings.getValue(
			"rssFeedType", RSSUtil.getFeedType(RSSUtil.ATOM, 1.0));
	}

	public boolean isAllowAnonymousPosting() {
		return _typedSettings.getBooleanValue("allowAnonymousPosting");
	}

	public boolean isEmailHtmlFormat() {
		return _typedSettings.getBooleanValue("emailHtmlFormat");
	}

	public boolean isEmailMessageAddedEnabled() {
		return _typedSettings.getBooleanValue("emailMessageAddedEnabled");
	}

	public boolean isEmailMessageUpdatedEnabled() {
		return _typedSettings.getBooleanValue("emailMessageUpdatedEnabled");
	}

	public boolean isEnableFlags() {
		return _typedSettings.getBooleanValue("enableFlags");
	}

	public boolean isEnableRatings() {
		return _typedSettings.getBooleanValue("enableRatings");
	}

	@Settings.Property(name = "enableRss")
	public boolean isEnableRSS() {
		if (!PortalUtil.isRSSFeedsEnabled()) {
			return false;
		}

		return _typedSettings.getBooleanValue("enableRss");
	}

	public boolean isSubscribeByDefault() {
		return _typedSettings.getBooleanValue("subscribeByDefault");
	}

	public boolean isThreadAsQuestionByDefault() {
		return _typedSettings.getBooleanValue("threadAsQuestionByDefault");
	}

	private static FallbackKeys _getFallbackKeys() {
		FallbackKeys fallbackKeys = new FallbackKeys();

		fallbackKeys.add(
			"allowAnonymousPosting",
			PropsKeys.MESSAGE_BOARDS_ANONYMOUS_POSTING_ENABLED);
		fallbackKeys.add(
			"emailFromAddress", PropsKeys.MESSAGE_BOARDS_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		fallbackKeys.add(
			"emailFromName", PropsKeys.MESSAGE_BOARDS_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);
		fallbackKeys.add(
			"emailHtmlFormat", PropsKeys.MESSAGE_BOARDS_EMAIL_HTML_FORMAT);
		fallbackKeys.add(
			"emailMessageAddedBody",
			PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_BODY);
		fallbackKeys.add(
			"emailMessageAddedEnabled",
			PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_ENABLED);
		fallbackKeys.add(
			"emailMessageAddedSubject",
			PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_SUBJECT);
		fallbackKeys.add(
			"emailMessageUpdatedBody",
			PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_BODY);
		fallbackKeys.add(
			"emailMessageUpdatedEnabled",
			PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_ENABLED);
		fallbackKeys.add(
			"emailMessageUpdatedSubject",
			PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_SUBJECT);
		fallbackKeys.add("enableFlags", PropsKeys.MESSAGE_BOARDS_FLAGS_ENABLED);
		fallbackKeys.add(
			"enableRatings", PropsKeys.MESSAGE_BOARDS_RATINGS_ENABLED);
		fallbackKeys.add("enableRss", PropsKeys.MESSAGE_BOARDS_RSS_ENABLED);
		fallbackKeys.add(
			"messageFormat", PropsKeys.MESSAGE_BOARDS_MESSAGE_FORMATS_DEFAULT);
		fallbackKeys.add(
			"priorities", PropsKeys.MESSAGE_BOARDS_THREAD_PRIORITIES);
		fallbackKeys.add("ranks", PropsKeys.MESSAGE_BOARDS_USER_RANKS);
		fallbackKeys.add(
			"recentPostsDateOffset",
			PropsKeys.MESSAGE_BOARDS_RECENT_POSTS_DATE_OFFSET);
		fallbackKeys.add(
			"rssDelta", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		fallbackKeys.add(
			"rssDisplayStyle", PropsKeys.RSS_FEED_DISPLAY_STYLE_DEFAULT);
		fallbackKeys.add("rssFeedType", PropsKeys.RSS_FEED_TYPE_DEFAULT);
		fallbackKeys.add(
			"subscribeByDefault",
			PropsKeys.MESSAGE_BOARDS_SUBSCRIBE_BY_DEFAULT);
		fallbackKeys.add(
			"threadAsQuestionByDefault",
			PropsKeys.MESSAGE_BOARDS_THREAD_AS_QUESTION_BY_DEFAULT);

		return fallbackKeys;
	}

	static {
		SettingsFactoryUtil.registerSettingsMetadata(
			MBGroupServiceSettings.class, null, _getFallbackKeys());
	}

	private final TypedSettings _typedSettings;

}