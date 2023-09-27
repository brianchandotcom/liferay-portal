/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.key;

import com.liferay.petra.nio.CharsetEncoderUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.key.CacheKeyGenerator;
import com.liferay.portal.kernel.cache.thread.local.Lifecycle;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCache;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;

import java.security.MessageDigest;

/**
 * @author Shuyang Zhou
 */
public class MessageDigestCacheKeyGenerator extends BaseCacheKeyGenerator {

	public MessageDigestCacheKeyGenerator(String algorithm) {
		this(algorithm, StringPool.UTF8);
	}

	public MessageDigestCacheKeyGenerator(
		String algorithm, String charsetName) {

		_algorithm = algorithm;
		_charsetName = charsetName;
	}

	@Override
	public CacheKeyGenerator clone() {
		return new MessageDigestCacheKeyGenerator(_algorithm, _charsetName);
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

	protected Serializable getCacheKey(String[] keys, int length) {
		try {
			ThreadLocalCache<MessageDigest> threadLocalCache =
				ThreadLocalCacheManager.getThreadLocalCache(
					Lifecycle.ETERNAL,
					MessageDigestCacheKeyGenerator.class.getName());

			MessageDigest messageDigest = threadLocalCache.get(_algorithm);

			if (messageDigest == null) {
				messageDigest = MessageDigest.getInstance(_algorithm);

				threadLocalCache.put(_algorithm, messageDigest);
			}

			CharsetEncoder charsetEncoder =
				CharsetEncoderUtil.getCharsetEncoder(_charsetName);

			for (int i = 0; i < length; i++) {
				messageDigest.update(
					charsetEncoder.encode(CharBuffer.wrap(keys[i])));
			}

			return StringUtil.bytesToHexString(messageDigest.digest());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	private final String _algorithm;
	private final String _charsetName;

}