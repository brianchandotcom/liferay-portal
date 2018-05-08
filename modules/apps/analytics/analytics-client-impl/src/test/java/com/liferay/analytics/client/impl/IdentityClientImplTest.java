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

package com.liferay.analytics.client.impl;

import com.liferay.analytics.model.IdentityContextMessage;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Eduardo Garcia
 */
@Ignore
public class IdentityClientImplTest {

	@Test
	public void testGetUUID() throws Exception {
		IdentityContextMessage.Builder identityContextMessageBuilder =
			IdentityContextMessage.builder("ApplicationKey");

		identityContextMessageBuilder.dataSourceIdentifier("Liferay");
		identityContextMessageBuilder.dataSourceIndividualIdentifier("12345");
		identityContextMessageBuilder.domain("liferay.com");
		identityContextMessageBuilder.language("en-US");
		identityContextMessageBuilder.protocolVersion("1.0");

		identityContextMessageBuilder.identityFieldsProperty(
			"email", "julio.camarero@liferay.com");
		identityContextMessageBuilder.identityFieldsProperty(
			"name", "Julio Camarero");

		String response = _identityClientImpl.getUserId(
			identityContextMessageBuilder.build());

		Assert.assertNotNull(response);
	}

	private final IdentityClientImpl _identityClientImpl =
		new IdentityClientImpl();

}