/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import java.nio.ByteBuffer;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Connor McKay
 */
public class DigesterUtil {

	public static String digest(ByteBuffer buffer) {
		return getDigester().digest(buffer);
	}

	public static String digest(String algorithm, ByteBuffer buffer) {
		return getDigester().digest(algorithm, buffer);
	}

	public static String digest(String text) {
		return getDigester().digest(text);
	}

	public static String digest(String algorithm, String... text) {
		return getDigester().digest(algorithm, text);
	}

	public static String digestBase64(ByteBuffer buffer) {
		return getDigester().digestBase64(buffer);
	}

	public static String digestBase64(String algorithm, ByteBuffer buffer) {
		return getDigester().digestBase64(algorithm, buffer);
	}

	public static String digestBase64(String text) {
		return getDigester().digestBase64(text);
	}

	public static String digestBase64(String algorithm, String... text) {
		return getDigester().digestBase64(algorithm, text);
	}

	public static String digestHex(ByteBuffer buffer) {
		return getDigester().digestHex(buffer);
	}

	public static String digestHex(String algorithm, ByteBuffer buffer) {
		return getDigester().digestHex(algorithm, buffer);
	}

	public static String digestHex(String text) {
		return getDigester().digestHex(text);
	}

	public static String digestHex(String algorithm, String... text) {
		return getDigester().digestHex(algorithm, text);
	}

	public static byte[] digestRaw(ByteBuffer buffer) {
		return getDigester().digestRaw(buffer);
	}

	public static byte[] digestRaw(String algorithm, ByteBuffer buffer) {
		return getDigester().digestRaw(algorithm, buffer);
	}

	public static byte[] digestRaw(String text) {
		return getDigester().digestRaw(text);
	}

	public static byte[] digestRaw(String algorithm, String... text) {
		return getDigester().digestRaw(algorithm, text);
	}

	public static Digester getDigester() {
		return _digester;
	}

	public void setDigester(Digester digester) {
		_digester = digester;
	}

	private static Digester _digester;

}