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

package com.liferay.calendar.model.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.IOException;

/**
 * @author Adam Brandizzi
 */
public class CalendarNotificationTemplateImpl
	extends CalendarNotificationTemplateBaseImpl {

	@Override
	public String getNotificationTypeSettings() {
		if (_notificationTypeSettingsUnicodeProperties == null) {
			return super.getNotificationTypeSettings();
		}

		return _notificationTypeSettingsUnicodeProperties.toString();
	}

	@Override
	public UnicodeProperties getNotificationTypeSettingsProperties() {
		if (_notificationTypeSettingsUnicodeProperties == null) {
			_notificationTypeSettingsUnicodeProperties = new UnicodeProperties(
				true);

			try {
				_notificationTypeSettingsUnicodeProperties.load(
					super.getNotificationTypeSettings());
			}
			catch (IOException ioException) {
				_log.error(ioException, ioException);
			}
		}

		return _notificationTypeSettingsUnicodeProperties;
	}

	@Override
	public void setNotificationTypeSettings(String notificationTypeSettings) {
		_notificationTypeSettingsUnicodeProperties = null;

		super.setNotificationTypeSettings(notificationTypeSettings);
	}

	@Override
	public void setTypeSettingsProperties(
		UnicodeProperties notificationTypeSettingsUnicodeProperties) {

		_notificationTypeSettingsUnicodeProperties =
			notificationTypeSettingsUnicodeProperties;

		super.setNotificationTypeSettings(
			_notificationTypeSettingsUnicodeProperties.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CalendarNotificationTemplateImpl.class);

	private UnicodeProperties _notificationTypeSettingsUnicodeProperties;

}