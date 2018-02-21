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

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringPool;

import java.io.InputStream;

import java.nio.ByteBuffer;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Connor McKay
 */
public interface Digester {

	public static final String DEFAULT_ALGORITHM = "SHA";

	public static final String ENCODING = StringPool.UTF8;

	public static final String MD5 = "MD5";

	public static final String SHA = "SHA";

	public static final String SHA_1 = "SHA-1";

	public static final String SHA_256 = "SHA-256";

	public String digest(ByteBuffer byteBuffer);

	public String digest(InputStream inputStream);

	public String digest(String text);

	public String digest(String algorithm, ByteBuffer byteBuffer);

	public String digest(String algorithm, InputStream inputStream);

	public String digest(String algorithm, String... text);

	public String digestBase64(ByteBuffer byteBuffer);

	public String digestBase64(InputStream inputStream);

	public String digestBase64(String text);

	public String digestBase64(String algorithm, ByteBuffer byteBuffer);

	public String digestBase64(String algorithm, InputStream inputStream);

	public String digestBase64(String algorithm, String... text);

	public String digestHex(ByteBuffer byteBuffer);

	public String digestHex(InputStream inputStream);

	public String digestHex(String text);

	public String digestHex(String algorithm, ByteBuffer byteBuffer);

	public String digestHex(String algorithm, InputStream inputStream);

	public String digestHex(String algorithm, String... text);

	public byte[] digestRaw(ByteBuffer byteBuffer);

	public byte[] digestRaw(String text);

	public byte[] digestRaw(String algorithm, ByteBuffer byteBuffer);

	public byte[] digestRaw(String algorithm, InputStream inputStream);

	public byte[] digestRaw(String algorithm, String... text);

}