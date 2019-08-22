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

import com.liferay.portal.kernel.messaging.Message;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class LicenseManagerMessageTypeTest {

	@Test
	public void testCreateMessage() {
		LicenseManagerMessageType validateLCSLicenseManagerMessageType =
			LicenseManagerMessageType.LCS_AVAILABLE;

		Message message = validateLCSLicenseManagerMessageType.createMessage();

		Object payload = message.getPayload();

		Assert.assertEquals("liferay/lcs_status", message.getDestinationName());

		Assert.assertTrue(
			"message payload is string", payload instanceof String);

		Assert.assertEquals("{\"type\": \"LCS_AVAILABLE\"}", payload);

		message = validateLCSLicenseManagerMessageType.createMessage(
			LCSPortletState.NO_SUBSCRIPTION);

		payload = message.getPayload();

		Assert.assertTrue(
			"message payload is string", payload instanceof String);

		Assert.assertEquals(
			"{\"state\": 4, \"type\": \"LCS_AVAILABLE\"}", payload);
	}

}