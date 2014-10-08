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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.upgrade.RenameUpgradePortalPreferences;
import com.liferay.portal.kernel.util.PropsKeys;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Eduardo Garcia
 */
public class UpgradeEmailNotificationPreferences
	extends RenameUpgradePortalPreferences {

	public UpgradeEmailNotificationPreferences() {
		_preferenceNamesMap.put(
			PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_BODY,
			"adminEmailPasswordSentBody");
		_preferenceNamesMap.put(
			PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_SUBJECT,
			"adminEmailPasswordSentSubject");
		_preferenceNamesMap.put(
			PropsKeys.ADMIN_EMAIL_PASSWORD_RESET_BODY,
			"adminEmailPasswordResetBody");
		_preferenceNamesMap.put(
			PropsKeys.ADMIN_EMAIL_PASSWORD_RESET_SUBJECT,
			"adminEmailPasswordResetSubject");
		_preferenceNamesMap.put(
			PropsKeys.ADMIN_EMAIL_USER_ADDED_BODY, "adminEmailUserAddedBody");
		_preferenceNamesMap.put(
			PropsKeys.ADMIN_EMAIL_USER_ADDED_NO_PASSWORD_BODY,
			"adminEmailUserAddedNoPasswordBody");
		_preferenceNamesMap.put(
			PropsKeys.ADMIN_EMAIL_USER_ADDED_SUBJECT,
			"adminEmailUserAddedSubject");
		_preferenceNamesMap.put(
			PropsKeys.ADMIN_EMAIL_VERIFICATION_BODY,
			"adminEmailVerificationBody");
		_preferenceNamesMap.put(
			PropsKeys.ADMIN_EMAIL_VERIFICATION_SUBJECT,
			"adminEmailVerificationSubject");
	}

	@Override
	protected Map<String, String> getPreferenceNamesMap() {
		return _preferenceNamesMap;
	}

	private final Map<String, String> _preferenceNamesMap =
		new HashMap<String, String>();

}