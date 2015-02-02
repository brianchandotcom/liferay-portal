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

package com.liferay.portal.security.pacl.test.messaging;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.model.User;
import com.liferay.portal.service.PortalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class TestPACLMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		message.setPayload(getResults(message));

		MessageBusUtil.sendMessage(
			message.getResponseDestinationName(), message);
	}

	protected Map<String, Object> getResults(Message message) throws Exception {
		Map<String, Object> results = new HashMap<>();

		try {
			int buildNumber = PortalServiceUtil.getBuildNumber();

			results.put("PortalServiceUtil#getBuildNumber", buildNumber);
		}
		catch (SecurityException se) {
		}

		try {
			User user = UserLocalServiceUtil.getUser(
				TestPropsValues.getUserId());

			results.put("UserLocalServiceUtil#getUser", user);
		}
		catch (SecurityException se) {
		}

		return results;
	}

}