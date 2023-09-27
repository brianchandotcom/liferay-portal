/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.push.notifications.sender.apple.internal;

import com.eatthepath.pushy.apns.ApnsPushNotification;

import com.liferay.push.notifications.sender.BaseResponse;

import java.time.Instant;

/**
 * @author Bruno Farache
 */
public class AppleResponse extends BaseResponse {

	public AppleResponse(
		ApnsPushNotification apnsPushNotification, boolean resent) {

		this(apnsPushNotification);

		this.resent = resent;

		succeeded = true;
	}

	public AppleResponse(
		ApnsPushNotification apnsPushNotification, Throwable throwable) {

		this(apnsPushNotification);

		status = throwable.getMessage();
	}

	public int getExpiry() {
		return expiry;
	}

	public boolean isResent() {
		return resent;
	}

	protected AppleResponse(ApnsPushNotification apnsPushNotification) {
		super(ApplePushNotificationsSender.PLATFORM);

		if (apnsPushNotification != null) {
			Instant instant = apnsPushNotification.getExpiration();

			expiry = instant.getNano();

			id = apnsPushNotification.getCollapseId();
			payload = apnsPushNotification.getPayload();
			token = apnsPushNotification.getToken();
		}
	}

	protected int expiry;
	protected boolean resent;

}