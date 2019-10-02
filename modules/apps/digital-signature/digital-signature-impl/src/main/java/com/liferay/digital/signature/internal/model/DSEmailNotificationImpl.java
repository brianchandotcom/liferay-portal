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

package com.liferay.digital.signature.internal.model;

import com.liferay.digital.signature.model.DSEmailNotification;
import com.liferay.digital.signature.model.DSEmailNotificationSettings;

/**
 * @author Michael C. Han
 */
public class DSEmailNotificationImpl implements DSEmailNotification {

	public DSEmailNotificationImpl(String message, String subject) {
		_message = message;
		_subject = subject;
	}

	@Override
	public DSEmailNotificationSettings getDSEmailNotificationSettings() {
		return _dsEmailNotificationSettings;
	}

	@Override
	public String getMessage() {
		return _message;
	}

	@Override
	public String getSubject() {
		return _subject;
	}

	@Override
	public String getSupportedLanguage() {
		return _supportedLanguage;
	}

	public void setDSEmailNotificationSettings(
		DSEmailNotificationSettingsImpl dsEmailNotificationSettingsImpl) {

		_dsEmailNotificationSettings = dsEmailNotificationSettingsImpl;
	}

	public void setSupportedLanguage(String supportedLanguage) {
		_supportedLanguage = supportedLanguage;
	}

	private DSEmailNotificationSettings _dsEmailNotificationSettings;
	private final String _message;
	private final String _subject;
	private String _supportedLanguage;

}