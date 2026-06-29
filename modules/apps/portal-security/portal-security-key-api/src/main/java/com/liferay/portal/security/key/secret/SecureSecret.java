/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.secret;

import com.liferay.portal.security.key.KeyReference;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.StandardCharsets;

import java.util.Arrays;

import javax.security.auth.Destroyable;

/**
 * @author Tomas Polesovsky
 * @author Christopher Kian
 */
public final class SecureSecret implements AutoCloseable, Destroyable {

	public SecureSecret(byte[] bytes, KeyReference keyReference) {
		if (keyReference == null) {
			throw new IllegalArgumentException(
				"Key reference must not be null");
		}

		if (bytes == null) {
			_bytes = new byte[0];
		}
		else {
			_bytes = Arrays.copyOf(bytes, bytes.length);
		}

		_keyReference = keyReference;
	}

	public SecureSecret(char[] chars, KeyReference keyReference) {
		if (keyReference == null) {
			throw new IllegalArgumentException(
				"Key reference must not be null");
		}

		if (chars != null) {
			_bytes = _encode(chars);
		}
		else {
			_bytes = new byte[0];
		}

		_keyReference = keyReference;
	}

	public SecureSecret(KeyReference keyReference, String value) {
		if (keyReference == null) {
			throw new IllegalArgumentException(
				"Key reference must not be null");
		}

		_keyReference = keyReference;

		if (value == null) {
			_bytes = new byte[0];

			return;
		}

		char[] chars = value.toCharArray();

		try {
			_bytes = _encode(chars);
		}
		finally {
			Arrays.fill(chars, '\0');
		}
	}

	@Override
	public void close() {
		destroy();
	}

	@Override
	public synchronized void destroy() {
		if (_bytes != null) {
			Arrays.fill(_bytes, (byte)0);
		}

		if (_chars != null) {
			Arrays.fill(_chars, '\0');
		}

		_destroyed = true;
	}

	public synchronized byte[] getBytes() {
		if (_destroyed) {
			throw new IllegalStateException("Secret is destroyed");
		}

		return _bytes;
	}

	public synchronized char[] getChars() {
		if (_destroyed) {
			throw new IllegalStateException("Secret is destroyed");
		}

		if (_chars == null) {
			_chars = _decode(_bytes);
		}

		return _chars;
	}

	public KeyReference getKeyReference() {
		return _keyReference;
	}

	@Override
	public synchronized boolean isDestroyed() {
		return _destroyed;
	}

	private char[] _decode(byte[] bytes) {
		try {
			CharBuffer charBuffer = StandardCharsets.UTF_8.newDecoder(
			).decode(
				ByteBuffer.wrap(bytes)
			);

			char[] chars = new char[charBuffer.remaining()];

			charBuffer.get(chars);

			Arrays.fill(charBuffer.array(), '\0');

			return chars;
		}
		catch (CharacterCodingException characterCodingException) {
			throw new IllegalArgumentException(
				"Stored secret is not valid UTF-8", characterCodingException);
		}
	}

	private byte[] _encode(char[] chars) {
		try {
			ByteBuffer byteBuffer = StandardCharsets.UTF_8.newEncoder(
			).encode(
				CharBuffer.wrap(chars)
			);

			byte[] bytes = new byte[byteBuffer.remaining()];

			byteBuffer.get(bytes);

			Arrays.fill(byteBuffer.array(), (byte)0);

			return bytes;
		}
		catch (CharacterCodingException characterCodingException) {
			throw new IllegalArgumentException(
				"Input character sequence is not valid UTF-16",
				characterCodingException);
		}
	}

	private final byte[] _bytes;
	private char[] _chars;
	private boolean _destroyed;
	private final KeyReference _keyReference;

}