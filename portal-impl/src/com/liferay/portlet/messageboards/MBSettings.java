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

package com.liferay.portlet.messageboards;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.settings.Settings;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.util.MBUtil;
import com.liferay.util.ContentUtil;
import com.liferay.util.RSSUtil;

import java.io.IOException;

import javax.portlet.ValidatorException;

/**
 * @author Jorge Ferrer
 */
public class MBSettings implements Settings {

	public MBSettings(Settings settings) {
		_settings = settings;
	}

	public String getEmailFromAddress() {
		return _settings.getValue(
			PropsKeys.MESSAGE_BOARDS_EMAIL_FROM_ADDRESS,
			PropsValues.MESSAGE_BOARDS_EMAIL_FROM_ADDRESS);
	}

	public String getEmailFromName() {
		return _settings.getValue(
			PropsKeys.MESSAGE_BOARDS_EMAIL_FROM_NAME,
			PropsValues.MESSAGE_BOARDS_EMAIL_FROM_NAME);
	}

	public String getEmailMessageAddedBody() {
		String emailMessageAddedBody = _settings.getValue(
			"emailMessageAddedBody", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageAddedBody)) {
			return emailMessageAddedBody;
		}

		return ContentUtil.get(
			PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_BODY);
	}

	public String getEmailMessageAddedSubject() {
		String emailMessageAddedSubject = _settings.getValue(
			"emailMessageAddedSubject", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageAddedSubject)) {
			return emailMessageAddedSubject;
		}

		return ContentUtil.get(
			PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_SUBJECT);
	}

	public String getEmailMessageUpdatedBody() {
		String emailMessageUpdatedBody = _settings.getValue(
			"emailMessageUpdatedBody", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageUpdatedBody)) {
			return emailMessageUpdatedBody;
		}

		return ContentUtil.get(
			PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_BODY);
	}

	public String getEmailMessageUpdatedSubject() {
		String emailMessageUpdatedSubject = _settings.getValue(
			"emailMessageUpdatedSubject", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageUpdatedSubject)) {
			return emailMessageUpdatedSubject;
		}

		return ContentUtil.get(
			PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_SUBJECT);
	}

	public String getMessageFormat() {
		String messageFormat = _settings.getValue(
			"messageFormat", MBMessageConstants.DEFAULT_FORMAT);

		if (MBUtil.isValidMessageFormat(messageFormat)) {
			return messageFormat;
		}

		return "html";
	}

	public String[] getPriorities(String currentLanguageId) {
		return LocalizationUtil.getSettingsValues(
			_settings, "priorities", currentLanguageId);
	}

	public String[] getRanks(String languageId) {
		return LocalizationUtil.getSettingsValues(
			_settings, "ranks", languageId);
	}

	public String getRecentPostsDateOffset() {
		return _settings.getValue("recentPostsDateOffset", "7");
	}

	public int getRSSDelta() {
		return GetterUtil.getInteger(
			_settings.getValue("rssDelta", StringPool.BLANK),
			SearchContainer.DEFAULT_DELTA);
	}

	public String getRSSDisplayStyle() {
		return _settings.getValue(
			"rssDisplayStyle", RSSUtil.DISPLAY_STYLE_DEFAULT);
	}

	public String getRSSFeedType() {
		return _settings.getValue("rssFeedType", RSSUtil.FEED_TYPE_DEFAULT);
	}

	@Override
	public String getValue(String key, String defaultValue) {
		return _settings.getValue(key, defaultValue);
	}

	@Override
	public String[] getValues(String key, String[] defaultValue) {
		return _settings.getValues(key, defaultValue);
	}

	public boolean isAllowAnonymousPosting() {
		return GetterUtil.getBoolean(
			_settings.getValue("allowAnonymousPosting", null),
			PropsValues.MESSAGE_BOARDS_ANONYMOUS_POSTING_ENABLED);
	}

	public boolean isEmailHtmlFormat() {
		String emailHtmlFormat = _settings.getValue(
			"emailHtmlFormat", StringPool.BLANK);

		if (Validator.isNotNull(emailHtmlFormat)) {
			return GetterUtil.getBoolean(emailHtmlFormat);
		}

		return PropsValues.MESSAGE_BOARDS_EMAIL_HTML_FORMAT;
	}

	public boolean isEmailMessageAddedEnabled() {
		String emailMessageAddedEnabled = _settings.getValue(
			"emailMessageAddedEnabled", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageAddedEnabled)) {
			return GetterUtil.getBoolean(emailMessageAddedEnabled);
		}

		return PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_ENABLED;
	}

	public boolean isEmailMessageUpdatedEnabled() {
		String emailMessageUpdatedEnabled = _settings.getValue(
			"emailMessageUpdatedEnabled", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageUpdatedEnabled)) {
			return GetterUtil.getBoolean(emailMessageUpdatedEnabled);
		}

		return PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_ENABLED;
	}

	public boolean isEnableFlags() {
		return GetterUtil.getBoolean(
			_settings.getValue("enableFlags", null), true);
	}

	public boolean isEnableRatings() {
		return GetterUtil.getBoolean(
			_settings.getValue("enableRatings", null), true);
	}

	public boolean isEnableRSS() {
		if (PortalUtil.isRSSFeedsEnabled()) {
			return GetterUtil.getBoolean(
				_settings.getValue("enableRss", null), true);
		}

		return false;
	}

	public boolean isSubscribeByDefault() {
		return GetterUtil.getBoolean(
			_settings.getValue("subscribeByDefault", null),
			PropsValues.MESSAGE_BOARDS_SUBSCRIBE_BY_DEFAULT);
	}

	public boolean isThreadAsQuestionByDefault() {
		return GetterUtil.getBoolean(
			_settings.getValue("threadAsQuestionByDefault", null));
	}

	@Override
	public Settings setValue(String key, String value) {
		return _settings.setValue(key, value);
	}

	@Override
	public Settings setValues(String key, String[] values) {
		return _settings.setValues(key, values);
	}

	@Override
	public void store() throws IOException, ValidatorException {
		_settings.store();
	}

	private Settings _settings;

}