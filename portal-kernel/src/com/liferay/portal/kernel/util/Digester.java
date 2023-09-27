/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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