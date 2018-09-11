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

package com.liferay.journal.web.internal.notifications;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Alejandro Tardín
 */
public abstract class BaseJournalUserNotificationDefinition
	extends UserNotificationDefinition {

	public BaseJournalUserNotificationDefinition(
		int notificationType, String description) {

		super(JournalPortletKeys.JOURNAL, 0, notificationType, description);

		_description = description;
	}

	@Override
	public String getDescription(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, "com.liferay.journal.lang");

		String description = ResourceBundleUtil.getString(
			resourceBundle, _description);

		if (description != null) {
			return description;
		}

		return _description;
	}

	private final String _description;

}