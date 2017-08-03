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

package com.liferay.lcs.license.messaging;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.license.messaging.LicenseManagerMessageType;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Igor Beslic
 */
public abstract class LicenseManagerBaseMessageListener
	extends BaseMessageListener {

	public LicenseManagerMessageType getAllowedLicenseManagerMessageType() {
		return _allowedLicenseManagerMessageType;
	}

	public void setAllowedLicenseManagerMessageType(
		LicenseManagerMessageType allowedLicenseManagerMessageType) {

		_allowedLicenseManagerMessageType = allowedLicenseManagerMessageType;
	}

	protected abstract Message createResponseMessage(JSONObject jsonObject);

	@Override
	protected void doReceive(Message message) throws Exception {
		if (!(message.getPayload() instanceof String)) {
			return;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			(String)message.getPayload());

		LicenseManagerMessageType licenseManagerMessageType =
			LicenseManagerMessageType.valueOf(jsonObject);

		if (!licenseManagerMessageType.equals(
				_allowedLicenseManagerMessageType)) {

			return;
		}

		Message responseMessage = createResponseMessage(jsonObject);

		if (isSynchronous(message)) {
			responseMessage.setDestinationName(
				message.getResponseDestinationName());
			responseMessage.setResponseId(message.getResponseId());
		}

		MessageBusUtil.sendMessage(
			responseMessage.getDestinationName(), responseMessage);
	}

	protected boolean isSynchronous(Message message) {
		if (Validator.isNull(message.getResponseDestinationName()) ||
			Validator.isNull(message.getResponseId())) {

			return false;
		}

		return true;
	}

	private LicenseManagerMessageType _allowedLicenseManagerMessageType;

}