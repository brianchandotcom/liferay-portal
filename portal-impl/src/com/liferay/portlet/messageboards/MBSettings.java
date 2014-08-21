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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.ParameterMapSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messageboards.util.MBConstants;
import com.liferay.portlet.messageboards.util.MBUtil;
import com.liferay.util.RSSUtil;

import java.util.Map;

/**
 * @author Jorge Ferrer
 */
public class MBSettings {

	public static final String[] ALL_KEYS = {
		"emailFromAddress", "emailFromName", "emailMessageAddedBody",
		"emailMessageAddedSubject", "emailMessageUpdatedBody",
		"emailMessageUpdatedSubject", "messageFormat", "priorities", "ranks",
		"recentPostsDateOffset", "rssDelta", "rssDisplayStyle", "rssFeedType",
		"allowAnonymousPosting", "emailHtmlFormat", "emailMessageAddedEnabled",
		"emailMessageUpdatedEnabled", "enableFlags", "enableRatings",
		"enableRss", "subscribeByDefault", "threadAsQuestionByDefault"
	};

	public static MBSettings getInstance(long groupId) throws PortalException {
		Settings settings = SettingsFactoryUtil.getGroupServiceSettings(
			groupId, MBConstants.SERVICE_NAME);

		return new MBSettings(settings);
	}

	public static MBSettings getInstance(
			long groupId, Map<String, String[]> parameterMap)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getGroupServiceSettings(
			groupId, MBConstants.SERVICE_NAME);

		ParameterMapSettings parameterMapSettings = new ParameterMapSettings(
			parameterMap, settings);

		return new MBSettings(parameterMapSettings);
	}

	public MBSettings(Settings settings) {
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
		LocalizedValuesMap emailMessageBodyMap = getEmailMessageAddedBody();

		return emailMessageBodyMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailMessageAddedSubject() {
		return _typedSettings.getLocalizedValuesMap("emailMessageAddedSubject");
	}

	public String getEmailMessageAddedSubjectXml() {
		LocalizedValuesMap emailMessageAddedSubjectMap =
			getEmailMessageAddedSubject();

		return emailMessageAddedSubjectMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailMessageUpdatedBody() {
		return _typedSettings.getLocalizedValuesMap("emailMessageUpdatedBody");
	}

	public String getEmailMessageUpdatedBodyXml() {
		LocalizedValuesMap emailMessageUpdatedBodyMap =
			getEmailMessageUpdatedBody();

		return emailMessageUpdatedBodyMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailMessageUpdatedSubject() {
		return _typedSettings.getLocalizedValuesMap(
			"emailMessageUpdatedSubject");
	}

	public String getEmailMessageUpdatedSubjectXml() {
		LocalizedValuesMap emailMessageUpdatedSubjectMap =
			getEmailMessageUpdatedSubject();

		return emailMessageUpdatedSubjectMap.getLocalizationXml();
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

	public int getRSSDelta() {
		return _typedSettings.getIntegerValue("rssDelta");
	}

	public String getRSSDisplayStyle() {
		return _typedSettings.getValue(
			"rssDisplayStyle", RSSUtil.DISPLAY_STYLE_FULL_CONTENT);
	}

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

	private static final String[] _MULTI_VALUED_KEYS = {"ranks"};

	static {
		SettingsFactory settingsFactory =
			SettingsFactoryUtil.getSettingsFactory();

		settingsFactory.registerSettingsMetadata(
			MBConstants.SERVICE_NAME, _getFallbackKeys(), _MULTI_VALUED_KEYS);
	}

	private final TypedSettings _typedSettings;

}