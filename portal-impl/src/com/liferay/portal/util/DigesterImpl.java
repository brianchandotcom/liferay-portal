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

package com.liferay.portal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import java.nio.ByteBuffer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Connor McKay
 */
@DoPrivileged
public class DigesterImpl implements Digester {

	@Override
	public String digest(ByteBuffer byteBuffer) {
		return digest(Digester.DEFAULT_ALGORITHM, byteBuffer);
	}

	@Override
	public String digest(InputStream inputStream) {
		return digest(Digester.DEFAULT_ALGORITHM, inputStream);
	}

	@Override
	public String digest(String text) {
		return digest(Digester.DEFAULT_ALGORITHM, text);
	}

	@Override
	public String digest(String algorithm, ByteBuffer byteBuffer) {
		if (_BASE_64) {
			return digestBase64(algorithm, byteBuffer);
		}
		else {
			return digestHex(algorithm, byteBuffer);
		}
	}

	@Override
	public String digest(String algorithm, InputStream inputStream) {
		if (_BASE_64) {
			return digestBase64(algorithm, inputStream);
		}
		else {
			return digestHex(algorithm, inputStream);
		}
	}

	@Override
	public String digest(String algorithm, String... text) {
		if (_BASE_64) {
			return digestBase64(algorithm, text);
		}
		else {
			return digestHex(algorithm, text);
		}
	}

	@Override
	public String digestBase64(ByteBuffer byteBuffer) {
		return digestBase64(Digester.DEFAULT_ALGORITHM, byteBuffer);
	}

	@Override
	public String digestBase64(InputStream inputStream) {
		return digestBase64(Digester.DEFAULT_ALGORITHM, inputStream);
	}

	@Override
	public String digestBase64(String text) {
		return digestBase64(Digester.DEFAULT_ALGORITHM, text);
	}

	@Override
	public String digestBase64(String algorithm, ByteBuffer byteBuffer) {
		byte[] bytes = digestRaw(algorithm, byteBuffer);

		return Base64.encode(bytes);
	}

	@Override
	public String digestBase64(String algorithm, InputStream inputStream) {
		byte[] bytes = digestRaw(algorithm, inputStream);

		return Base64.encode(bytes);
	}

	@Override
	public String digestBase64(String algorithm, String... text) {
		byte[] bytes = digestRaw(algorithm, text);

		return Base64.encode(bytes);
	}

	@Override
	public String digestHex(ByteBuffer byteBuffer) {
		return digestHex(Digester.DEFAULT_ALGORITHM, byteBuffer);
	}

	@Override
	public String digestHex(InputStream inputStream) {
		return digestHex(Digester.DEFAULT_ALGORITHM, inputStream);
	}

	@Override
	public String digestHex(String text) {
		return digestHex(Digester.DEFAULT_ALGORITHM, text);
	}

	@Override
	public String digestHex(String algorithm, ByteBuffer byteBuffer) {
		byte[] bytes = digestRaw(algorithm, byteBuffer);

		return Hex.encodeHexString(bytes);
	}

	@Override
	public String digestHex(String algorithm, InputStream inputStream) {
		byte[] bytes = digestRaw(algorithm, inputStream);

		return Hex.encodeHexString(bytes);
	}

	@Override
	public String digestHex(String algorithm, String... text) {
		byte[] bytes = digestRaw(algorithm, text);

		return Hex.encodeHexString(bytes);
	}

	@Override
	public byte[] digestRaw(ByteBuffer byteBuffer) {
		return digestRaw(Digester.DEFAULT_ALGORITHM, byteBuffer);
	}

	@Override
	public byte[] digestRaw(String text) {
		return digestRaw(Digester.DEFAULT_ALGORITHM, text);
	}

	@Override
	public byte[] digestRaw(String algorithm, ByteBuffer byteBuffer) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance(algorithm);

			messageDigest.update(byteBuffer);
		}
		catch (NoSuchAlgorithmException nsae) {
			_log.error(nsae, nsae);
		}

		return messageDigest.digest();
	}

	@Override
	public byte[] digestRaw(String algorithm, InputStream inputStream) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance(algorithm);

			byte[] buffer = new byte[StreamUtil.BUFFER_SIZE];

			int read = 0;

			while ((read = inputStream.read(buffer)) != -1) {
				if (read > 0) {
					messageDigest.update(buffer, 0, read);
				}
			}
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}
		catch (NoSuchAlgorithmException nsae) {
			_log.error(nsae, nsae);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}

		return messageDigest.digest();
	}

	@Override
	public byte[] digestRaw(String algorithm, String... text) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance(algorithm);

			StringBundler sb = new StringBundler(text.length * 2 - 1);

			for (String t : text) {
				if (sb.length() > 0) {
					sb.append(StringPool.COLON);
				}

				sb.append(t);
			}

			String s = sb.toString();

			messageDigest.update(s.getBytes(Digester.ENCODING));
		}
		catch (NoSuchAlgorithmException nsae) {
			_log.error(nsae, nsae);
		}
		catch (UnsupportedEncodingException uee) {
			_log.error(uee, uee);
		}

		return messageDigest.digest();
	}

	private static final boolean _BASE_64 =
		PropsValues.PASSWORDS_DIGEST_ENCODING.equals("base64");

	private static final Log _log = LogFactoryUtil.getLog(DigesterImpl.class);

}