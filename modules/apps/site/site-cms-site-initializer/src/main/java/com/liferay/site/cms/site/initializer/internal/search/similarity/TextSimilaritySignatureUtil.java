/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.search.similarity;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Mikel Lorza
 */
public class TextSimilaritySignatureUtil {

	public static String[] getSimilarityKeys(String text) {
		if (text == null) {
			return new String[0];
		}

		Set<String> shingles = _getShingles(text);

		if (shingles.isEmpty()) {
			return new String[0];
		}

		long[] signature = _getMinHashSignature(shingles);

		String[] similarityKeys = new String[_SIMILARITY_KEYS];

		for (int similarityKey = 0; similarityKey < _SIMILARITY_KEYS;
			 similarityKey++) {

			long similarityKeyHash = _FNV_OFFSET_BASIS;

			for (int row = 0; row < _ROWS; row++) {
				similarityKeyHash = _mix(
					similarityKeyHash,
					signature[(similarityKey * _ROWS) + row]);
			}

			similarityKeys[similarityKey] = StringBundler.concat(
				"k", similarityKey, "_", Long.toHexString(similarityKeyHash));
		}

		return similarityKeys;
	}

	private static long _fnv1a(String value) {
		long hash = _FNV_OFFSET_BASIS;

		for (int i = 0; i < value.length(); i++) {
			hash ^= value.charAt(i);
			hash *= _FNV_PRIME;
		}

		return hash & Long.MAX_VALUE;
	}

	private static long[] _getMinHashSignature(Set<String> shingles) {
		long[] signature = new long[_HASH_COUNT];

		for (int i = 0; i < _HASH_COUNT; i++) {
			signature[i] = Long.MAX_VALUE;
		}

		for (String shingle : shingles) {
			long baseHash = _fnv1a(shingle);

			for (int i = 0; i < _HASH_COUNT; i++) {
				long permuted =
					((_PERMUTATION_A[i] * baseHash) + _PERMUTATION_B[i]) %
						_MERSENNE_PRIME;

				if (permuted < 0) {
					permuted += _MERSENNE_PRIME;
				}

				if (permuted < signature[i]) {
					signature[i] = permuted;
				}
			}
		}

		return signature;
	}

	private static Set<String> _getShingles(String text) {
		Set<String> shingles = new HashSet<>();

		String[] tokens = text.toLowerCase(
		).replaceAll(
			"[^\\p{L}\\p{Nd}]+", " "
		).trim(
		).split(
			"\\s+"
		);

		if ((tokens.length == 1) && tokens[0].isEmpty()) {
			return shingles;
		}

		if (tokens.length < _SHINGLE_SIZE) {
			for (String token : tokens) {
				shingles.add(token);
			}

			return shingles;
		}

		for (int i = 0; i <= (tokens.length - _SHINGLE_SIZE); i++) {
			StringBundler sb = new StringBundler();

			for (int j = 0; j < _SHINGLE_SIZE; j++) {
				if (j > 0) {
					sb.append(StringPool.SPACE);
				}

				sb.append(tokens[i + j]);
			}

			shingles.add(sb.toString());
		}

		return shingles;
	}

	private static long _mix(long hash, long value) {
		for (int shift = 0; shift < 64; shift += 8) {
			hash ^= (value >>> shift) & 0xff;
			hash *= _FNV_PRIME;
		}

		return hash;
	}

	private static final long _FNV_OFFSET_BASIS = 0xcbf29ce484222325L;

	private static final long _FNV_PRIME = 0x100000001b3L;

	private static final int _HASH_COUNT = 128;

	private static final long _MERSENNE_PRIME = (1L << 61) - 1;

	private static final long[] _PERMUTATION_A = new long[_HASH_COUNT];

	private static final long[] _PERMUTATION_B = new long[_HASH_COUNT];

	private static final int _ROWS = 4;

	private static final int _SHINGLE_SIZE = 3;

	private static final int _SIMILARITY_KEYS = 32;

	static {
		long state = 0x9e3779b97f4a7c15L;

		for (int i = 0; i < _HASH_COUNT; i++) {
			state = (state * 6364136223846793005L) + 1442695040888963407L;

			_PERMUTATION_A[i] = ((state >>> 1) % (_MERSENNE_PRIME - 1)) + 1;

			state = (state * 6364136223846793005L) + 1442695040888963407L;

			_PERMUTATION_B[i] = (state >>> 1) % _MERSENNE_PRIME;
		}
	}

}