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

/**
 * @author Jorge Ferrer
 */
public class MBSettings {

	public MBSettings(Settings serviceGroupSettings) {
		_serviceGroupSettings = serviceGroupSettings;
	}

	public String getEmailFromAddress() {
		return _serviceGroupSettings.getValue(
			PropsKeys.MESSAGE_BOARDS_EMAIL_FROM_ADDRESS,
			PropsValues.MESSAGE_BOARDS_EMAIL_FROM_ADDRESS);
	}

	public String getEmailFromName() {
		return _serviceGroupSettings.getValue(
			PropsKeys.MESSAGE_BOARDS_EMAIL_FROM_NAME,
			PropsValues.MESSAGE_BOARDS_EMAIL_FROM_NAME);
	}

	public String getEmailMessageAddedBody() {
		String emailMessageAddedBody = _serviceGroupSettings.getValue(
			"emailMessageAddedBody", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageAddedBody)) {
			return emailMessageAddedBody;
		}
		else {
			return ContentUtil.get(
				PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_BODY);
		}
	}

	public String getEmailMessageAddedSubject() {
		String emailMessageAddedSubject = _serviceGroupSettings.getValue(
			"emailMessageAddedSubject", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageAddedSubject)) {
			return emailMessageAddedSubject;
		}
		else {
			return ContentUtil.get(
				PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_SUBJECT);
		}
	}

	public String getEmailMessageUpdatedBody() {
		String emailMessageUpdatedBody = _serviceGroupSettings.getValue(
			"emailMessageUpdatedBody", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageUpdatedBody)) {
			return emailMessageUpdatedBody;
		}
		else {
			return ContentUtil.get(
				PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_BODY);
		}
	}

	public String getEmailMessageUpdatedSubject() {
		String emailMessageUpdatedSubject = _serviceGroupSettings.getValue(
			"emailMessageUpdatedSubject", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageUpdatedSubject)) {
			return emailMessageUpdatedSubject;
		}
		else {
			return ContentUtil.get(
				PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_SUBJECT);
		}
	}

	public String getMessageFormat() {
		String messageFormat = _serviceGroupSettings.getValue(
			"messageFormat", MBMessageConstants.DEFAULT_FORMAT);

		if (MBUtil.isValidMessageFormat(messageFormat)) {
			return messageFormat;
		}

		return "html";
	}

	public String[] getPriorities(String currentLanguageId) {
		return LocalizationUtil.getSettingsValues(
			_serviceGroupSettings, "priorities", currentLanguageId);
	}

	public String[] getRanks(String languageId) {
		return LocalizationUtil.getSettingsValues(
			_serviceGroupSettings, "ranks", languageId);
	}

	public String getRecentPostsDateOffset() {
		return _serviceGroupSettings.getValue("recentPostsDateOffset", "7");
	}

	public int getRSSDelta() {
		return GetterUtil.getInteger(
			_serviceGroupSettings.getValue("rssDelta", StringPool.BLANK),
			SearchContainer.DEFAULT_DELTA);
	}

	public String getRSSDisplayStyle() {
		return _serviceGroupSettings.getValue(
			"rssDisplayStyle", RSSUtil.DISPLAY_STYLE_DEFAULT);
	}

	public String getRSSFeedType() {
		return _serviceGroupSettings.getValue(
			"rssFeedType", RSSUtil.FEED_TYPE_DEFAULT);
	}

	public Settings getServiceGroupSettings() {
		return _serviceGroupSettings;
	}

	public boolean isAllowAnonymousPosting() {
		return GetterUtil.getBoolean(
			_serviceGroupSettings.getValue("allowAnonymousPosting", null),
			PropsValues.MESSAGE_BOARDS_ANONYMOUS_POSTING_ENABLED);
	}

	public boolean isEmailHtmlFormat() {
		String emailHtmlFormat = _serviceGroupSettings.getValue(
			"emailHtmlFormat", StringPool.BLANK);

		if (Validator.isNotNull(emailHtmlFormat)) {
			return GetterUtil.getBoolean(emailHtmlFormat);
		}
		else {
			return PropsValues.MESSAGE_BOARDS_EMAIL_HTML_FORMAT;
		}
	}

	public boolean isEmailMessageAddedEnabled() {
		String emailMessageAddedEnabled = _serviceGroupSettings.getValue(
			"emailMessageAddedEnabled", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageAddedEnabled)) {
			return GetterUtil.getBoolean(emailMessageAddedEnabled);
		}
		else {
			return PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_ENABLED;
		}
	}

	public boolean isEmailMessageUpdatedEnabled() {
		String emailMessageUpdatedEnabled = _serviceGroupSettings.getValue(
			"emailMessageUpdatedEnabled", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageUpdatedEnabled)) {
			return GetterUtil.getBoolean(emailMessageUpdatedEnabled);
		}
		else {
			return PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_ENABLED;
		}
	}

	public boolean isEnableFlags() {
		return GetterUtil.getBoolean(
			_serviceGroupSettings.getValue("enableFlags", null), true);
	}

	public boolean isEnableRatings() {
		return GetterUtil.getBoolean(
			_serviceGroupSettings.getValue("enableRatings", null), true);
	}

	public boolean isEnableRSS() {
		if (PortalUtil.isRSSFeedsEnabled()) {
			return GetterUtil.getBoolean(
				_serviceGroupSettings.getValue("enableRss", null), true);
		}

		return false;
	}

	public boolean isSubscribeByDefault() {
		return GetterUtil.getBoolean(
			_serviceGroupSettings.getValue("subscribeByDefault", null),
			PropsValues.MESSAGE_BOARDS_SUBSCRIBE_BY_DEFAULT);
	}

	public boolean isThreadAsQuestionByDefault() {
		return GetterUtil.getBoolean(
			_serviceGroupSettings.getValue("threadAsQuestionByDefault", null));
	}

	private final Settings _serviceGroupSettings;

}