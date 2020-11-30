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