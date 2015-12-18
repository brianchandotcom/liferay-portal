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

package com.liferay.portal.kernel.license.messaging;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.Message;

/**
 * @author Igor Beslic
 */
public enum LicenseManagerMessageType {

	LCS_AVAILABLE, SUBSCRIPTION_VALID, VALIDATE_LCS, VALIDATE_SUBSCRIPTION;

	public static String MESSAGE_BUS_DESTINATION_REQUEST =
		"liferay/lcs_request";

	public static String MESSAGE_BUS_DESTINATION_STATUS = "liferay/lcs_status";

	public static JSONObject getMessagePayload(Message message) {
		if (!(message.getPayload() instanceof String)) {
			return null;
		}

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				(String)message.getPayload());

			valueOf(jsonObject);

			return jsonObject;
		}
		catch (Exception e) {
			return null;
		}
	}

	public static JSONObject getMessagePayload(Object object) {
		if (!(object instanceof Message)) {
			return null;
		}

		return getMessagePayload((Message)object);
	}

	public static LicenseManagerMessageType valueOf(JSONObject jsonObject) {
		String type = jsonObject.getString("type");

		return valueOf(type);
	}

	public Message createMessage() {
		Message message = new Message();

		message.setDestinationName(getDestinationName());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("type", name());

		message.setPayload(jsonObject.toString());

		return message;
	}

	public Message createMessage(LCSPortletState state) {
		Message message = new Message();

		message.setDestinationName(getDestinationName());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("type", name());
		jsonObject.put("state", state.intValue());

		message.setPayload(jsonObject.toString());

		return message;
	}

	public String getDestinationName() {
		if ((this == LCS_AVAILABLE) || (this == SUBSCRIPTION_VALID)) {
			return MESSAGE_BUS_DESTINATION_STATUS;
		}
		else if ((this == VALIDATE_LCS) || (this == VALIDATE_SUBSCRIPTION)) {
			return MESSAGE_BUS_DESTINATION_REQUEST;
		}

		return null;
	}

}