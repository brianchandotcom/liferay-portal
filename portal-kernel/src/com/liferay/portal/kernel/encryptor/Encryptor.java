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

package com.liferay.portal.kernel.encryptor;

import java.security.Key;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @author Mika Koivisto
 */
public interface Encryptor {

	public String decrypt(Key key, String encryptedString)
		throws EncryptorException;

	public byte[] decryptUnencodedAsBytes(Key key, byte[] encryptedBytes)
		throws EncryptorException;

	public Key deserializeKey(String base64String);

	public String encrypt(Key key, String plainText) throws EncryptorException;

	public byte[] encryptUnencoded(Key key, byte[] plainBytes)
		throws EncryptorException;

	public byte[] encryptUnencoded(Key key, String plainText)
		throws EncryptorException;

	public Key generateKey() throws EncryptorException;

	public String serializeKey(Key key);

}