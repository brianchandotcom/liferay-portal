/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.util.hash;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.gradle.api.UncheckedIOException;
import org.gradle.internal.UncheckedException;

/**
 * @author Brian Wing Shun Chan
 */
public class HashUtil {

	public static HashValue createHash(File file, String algorithm) {
		try {
			return _createHash(new FileInputStream(file), algorithm);
		}
		catch (UncheckedIOException uncheckedIOException) {
			throw new UncheckedIOException(
				String.format(
					"Failed to create %s hash for file %s.", algorithm,
					file.getAbsolutePath()),
				uncheckedIOException.getCause());
		}
		catch (FileNotFoundException fileNotFoundException) {
			throw new UncheckedIOException(fileNotFoundException);
		}
	}

	public static HashValue md5(File file) {
		return createHash(file, "MD5");
	}

	public static HashValue sha1(byte[] bytes) {
		return _createHash(new ByteArrayInputStream(bytes), "SHA1");
	}

	public static HashValue sha1(File file) {
		return createHash(file, "SHA1");
	}

	public static HashValue sha1(InputStream inputStream) {
		return _createHash(inputStream, "SHA1");
	}

	public static HashValue sha256(byte[] bytes) {
		return _createHash(new ByteArrayInputStream(bytes), "SHA-256");
	}

	public static HashValue sha256(File file) {
		return createHash(file, "SHA-256");
	}

	public static HashValue sha256(InputStream inputStream) {
		return _createHash(inputStream, "SHA-256");
	}

	public static HashValue sha512(File file) {
		return createHash(file, "SHA-512");
	}

	public static HashValue sha512(InputStream inputStream) {
		return _createHash(inputStream, "SHA-512");
	}

	private static HashValue _createHash(
		InputStream inputStream, String algorithm) {

		MessageDigest messageDigest = null;

		try {
			messageDigest = _createMessageDigest(algorithm);
			byte[] buffer = new byte[4096];

			try {
				while (true) {
					int nread = inputStream.read(buffer);

					if (nread < 0) {
						break;
					}

					messageDigest.update(buffer, 0, nread);
				}
			}
			finally {
				inputStream.close();
			}
		}
		catch (IOException ioException) {
			throw new UncheckedIOException(ioException);
		}

		return new HashValue(messageDigest.digest());
	}

	private static MessageDigest _createMessageDigest(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		}
		catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			throw UncheckedException.throwAsUncheckedException(
				noSuchAlgorithmException);
		}
	}

}