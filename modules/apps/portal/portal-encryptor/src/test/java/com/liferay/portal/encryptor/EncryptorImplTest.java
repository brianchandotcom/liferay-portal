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

package com.liferay.portal.encryptor;

import com.liferay.portal.kernel.encryptor.Encryptor;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.security.Key;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Mika Koivisto
 */
public class EncryptorImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testKeySerialization() throws Exception {
		Map<String, Object> properties = new HashMap<>();

		Encryptor encryptor = new EncryptorImpl();

		properties.put(PropsKeys.COMPANY_ENCRYPTION_ALGORITHM, "AES");
		properties.put(PropsKeys.COMPANY_ENCRYPTION_KEY_SIZE, "128");

		PropsTestUtil.setProps(properties);

		Key key = encryptor.generateKey();

		String encryptedString = encryptor.encrypt(key, "Hello World!");

		String serializedKey = encryptor.serializeKey(key);

		key = encryptor.deserializeKey(serializedKey);

		Assert.assertEquals(
			"Hello World!", encryptor.decrypt(key, encryptedString));
	}

}