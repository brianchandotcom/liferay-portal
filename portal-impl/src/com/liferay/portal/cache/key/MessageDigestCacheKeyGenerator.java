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

package com.liferay.portal.cache.key;

import com.liferay.petra.nio.CharsetEncoderUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.key.CacheKeyGenerator;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Shuyang Zhou
 */
public class MessageDigestCacheKeyGenerator extends BaseCacheKeyGenerator {

	public MessageDigestCacheKeyGenerator(String algorithm)
		throws NoSuchAlgorithmException {

		this(algorithm, StringPool.UTF8);
	}

	public MessageDigestCacheKeyGenerator(String algorithm, String charsetName)
		throws NoSuchAlgorithmException {

		_messageDigest = MessageDigest.getInstance(algorithm);
		_charsetEncoder = CharsetEncoderUtil.getCharsetEncoder(charsetName);
	}

	@Override
	public CacheKeyGenerator clone() {
		Charset charset = _charsetEncoder.charset();

		try {
			return new MessageDigestCacheKeyGenerator(
				_messageDigest.getAlgorithm(), charset.name());
		}
		catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			throw new IllegalStateException(noSuchAlgorithmException);
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getCacheKey(StringBundler)}
	 */
	@Deprecated
	@Override
	public Serializable getCacheKey(
		com.liferay.portal.kernel.util.StringBundler sb) {

		return getCacheKey(sb.getStrings(), sb.index());
	}

	@Override
	public Serializable getCacheKey(String key) {
		return getCacheKey(new String[] {key}, 1);
	}

	@Override
	public Serializable getCacheKey(String[] keys) {
		return getCacheKey(keys, keys.length);
	}

	@Override
	public Serializable getCacheKey(StringBundler sb) {
		return getCacheKey(sb.getStrings(), sb.index());
	}

	@Override
	public boolean isCallingGetCacheKeyThreadSafe() {
		return _CALLING_GET_CACHE_KEY_THREAD_SAFE;
	}

	protected Serializable getCacheKey(String[] keys, int length) {
		try {
			for (int i = 0; i < length; i++) {
				_messageDigest.update(
					_charsetEncoder.encode(CharBuffer.wrap(keys[i])));
			}

			return StringUtil.bytesToHexString(_messageDigest.digest());
		}
		catch (CharacterCodingException characterCodingException) {
			throw new SystemException(characterCodingException);
		}
	}

	private static final boolean _CALLING_GET_CACHE_KEY_THREAD_SAFE = false;

	private final CharsetEncoder _charsetEncoder;
	private final MessageDigest _messageDigest;

}