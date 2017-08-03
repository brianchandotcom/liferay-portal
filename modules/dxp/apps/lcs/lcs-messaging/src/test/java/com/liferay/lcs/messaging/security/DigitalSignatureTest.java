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

package com.liferay.lcs.messaging.security;

import com.liferay.lcs.messaging.SendPortalPropertiesCommandMessage;
import com.liferay.lcs.messaging.internal.security.DigitalSignatureImpl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Igor Beslic
 */
@RunWith(JUnit4.class)
public class DigitalSignatureTest {

	@Test
	public void test() {
		DigitalSignatureImpl digitalSignatureImpl = new DigitalSignatureImpl();

		digitalSignatureImpl.setAlgorithmProvider("SunRsaSign");
		digitalSignatureImpl.setKeyName("localhost");
		digitalSignatureImpl.setKeyStorePath(
			"classpath:/com/liferay/lcs/messaging/security/dependencies" +
				"/keystore.jks");
		digitalSignatureImpl.setKeyStoreType("JKS");
		digitalSignatureImpl.setSigningAlgorithm("MD5withRSA");

		SendPortalPropertiesCommandMessage sendPortalPropertiesCommandMessage =
			new SendPortalPropertiesCommandMessage();

		sendPortalPropertiesCommandMessage.setHashCode("hashCode");
		sendPortalPropertiesCommandMessage.setKey("key");

		digitalSignatureImpl.signMessage(
			302, sendPortalPropertiesCommandMessage);

		boolean verify = digitalSignatureImpl.verifyMessage(
			302, sendPortalPropertiesCommandMessage);

		Assert.assertEquals(true, verify);
	}

}