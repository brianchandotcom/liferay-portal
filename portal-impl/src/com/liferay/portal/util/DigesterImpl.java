/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import java.nio.ByteBuffer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Connor McKay
 */
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

		return digestHex(algorithm, byteBuffer);
	}

	@Override
	public String digest(String algorithm, InputStream inputStream) {
		if (_BASE_64) {
			return digestBase64(algorithm, inputStream);
		}

		return digestHex(algorithm, inputStream);
	}

	@Override
	public String digest(String algorithm, String... text) {
		if (_BASE_64) {
			return digestBase64(algorithm, text);
		}

		return digestHex(algorithm, text);
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

		return StringUtil.bytesToHexString(bytes);
	}

	@Override
	public String digestHex(String algorithm, InputStream inputStream) {
		byte[] bytes = digestRaw(algorithm, inputStream);

		return StringUtil.bytesToHexString(bytes);
	}

	@Override
	public String digestHex(String algorithm, String... text) {
		byte[] bytes = digestRaw(algorithm, text);

		return StringUtil.bytesToHexString(bytes);
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
		catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			_log.error(noSuchAlgorithmException);
		}

		return messageDigest.digest();
	}

	@Override
	public byte[] digestRaw(String algorithm, InputStream inputStream1) {
		MessageDigest messageDigest = null;

		try (InputStream inputStream2 = inputStream1) {
			messageDigest = MessageDigest.getInstance(algorithm);

			byte[] buffer = new byte[StreamUtil.BUFFER_SIZE];

			int read = 0;

			while ((read = inputStream2.read(buffer)) != -1) {
				if (read > 0) {
					messageDigest.update(buffer, 0, read);
				}
			}
		}
		catch (IOException ioException) {
			_log.error(ioException);
		}
		catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			_log.error(noSuchAlgorithmException);
		}

		return messageDigest.digest();
	}

	@Override
	public byte[] digestRaw(String algorithm, String... text) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance(algorithm);

			StringBundler sb = new StringBundler((text.length * 2) - 1);

			for (String t : text) {
				if (sb.length() > 0) {
					sb.append(StringPool.COLON);
				}

				sb.append(t);
			}

			String s = sb.toString();

			messageDigest.update(s.getBytes(Digester.ENCODING));
		}
		catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			_log.error(noSuchAlgorithmException);
		}
		catch (UnsupportedEncodingException unsupportedEncodingException) {
			_log.error(unsupportedEncodingException);
		}

		return messageDigest.digest();
	}

	private static final boolean _BASE_64 =
		PropsValues.PASSWORDS_DIGEST_ENCODING.equals("base64");

	private static final Log _log = LogFactoryUtil.getLog(DigesterImpl.class);

}