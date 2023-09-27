/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.notifications;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;

import java.util.Locale;

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
		String description = LanguageUtil.get(locale, _description);

		if (description != null) {
			return description;
		}

		return _description;
	}

	private final String _description;

}