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

package com.liferay.lcs.util;

import com.liferay.lcs.messaging.CommandMessage;
import com.liferay.lcs.messaging.Message;
import com.liferay.lcs.messaging.ResponseMessage;

/**
 * @author Ivica Cardic
 */
public class ResponseMessageUtil {

	public static ResponseMessage createResponseMessage(
		CommandMessage commandMessage, Object payload) {

		return createResponseMessage(commandMessage, payload, null);
	}

	public static ResponseMessage createResponseMessage(
		CommandMessage commandMessage, Object payload, String error) {

		ResponseMessage responseMessage = new ResponseMessage();

		if (error != null) {
			responseMessage.put(Message.KEY_ERROR, error);
		}

		responseMessage.setCommandType(commandMessage.getCommandType());
		responseMessage.setCorrelationId(commandMessage.getCorrelationId());
		responseMessage.setCreateTime(System.currentTimeMillis());
		responseMessage.setKey(commandMessage.getKey());

		if (payload != null) {
			responseMessage.setPayload(payload);
		}

		return responseMessage;
	}

}