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

package com.liferay.petra.encryptor;

import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;

import java.security.Key;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Mika Koivisto
 * @see    com.liferay.util.EncryptorTest
 */
public class EncryptorTest {

	@Test
	public void testKeySerialization() throws Exception {
		Map<String, Object> properties = new HashMap<>();

		properties.put(PropsKeys.COMPANY_ENCRYPTION_ALGORITHM, "AES");
		properties.put(PropsKeys.COMPANY_ENCRYPTION_KEY_SIZE, "128");

		PropsTestUtil.setProps(properties);

		Key key = Encryptor.generateKey();

		String encryptedString = Encryptor.encrypt(key, "Hello World!");

		String serializedKey = Encryptor.serializeKey(key);

		key = Encryptor.deserializeKey(serializedKey);

		Assert.assertEquals(
			"Hello World!", Encryptor.decrypt(key, encryptedString));
	}

}