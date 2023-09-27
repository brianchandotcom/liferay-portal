/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.push.notifications.sender.sms.internal;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.push.notifications.sender.BaseResponse;

import com.twilio.rest.api.v2010.account.Message;

/**
 * @author Bruno Farache
 */
public class SMSResponse extends BaseResponse {

	public SMSResponse(Message message, JSONObject payloadJSONObject) {
		super(SMSPushNotificationsSender.PLATFORM);

		accountSid = message.getAccountSid();
		id = message.getSid();
		payload = payloadJSONObject.toString();

		price = message.getPrice();

		Message.Status messageStatus = message.getStatus();

		status = messageStatus.toString();

		if (Message.Status.QUEUED.equals(messageStatus)) {
			succeeded = true;
		}

		token = message.getTo();
	}

	public String getAccountSid() {
		return accountSid;
	}

	public String getPrice() {
		return price;
	}

	protected String accountSid;
	protected String price;

}