/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.search.similarity;

import com.dynatrace.hash4j.hashing.Hasher64;
import com.dynatrace.hash4j.hashing.Hashing;
import com.dynatrace.hash4j.similarity.ElementHashProvider;
import com.dynatrace.hash4j.similarity.SimilarityHashPolicy;
import com.dynatrace.hash4j.similarity.SimilarityHashing;
import com.dynatrace.hash4j.similarity.SuperMinHashVersion;

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

		Set<String> wordSequences = _getWordSequences(text);

		if (wordSequences.isEmpty()) {
			return new String[0];
		}

		byte[] signature = _similarityHashPolicy.createHasher(
		).compute(
			ElementHashProvider.ofCollection(
				wordSequences, _hasher64::hashCharsToLong)
		);

		String[] similarityKeys = new String[_SIMILARITY_KEYS];

		for (int i = 0; i < _SIMILARITY_KEYS; i++) {
			StringBundler sb = new StringBundler(
				(_SAMPLES_PER_SIMILARITY_KEY * 2) + 2);

			sb.append("k");
			sb.append(i);

			for (int j = 0; j < _SAMPLES_PER_SIMILARITY_KEY; j++) {
				sb.append(StringPool.UNDERLINE);
				sb.append(
					_similarityHashPolicy.getComponent(
						signature, (i * _SAMPLES_PER_SIMILARITY_KEY) + j));
			}

			similarityKeys[i] = sb.toString();
		}

		return similarityKeys;
	}

	private static Set<String> _getWordSequences(String text) {
		Set<String> wordSequences = new HashSet<>();

		String[] words = text.toLowerCase(
		).replaceAll(
			"[^\\p{L}\\p{Nd}]+", " "
		).trim(
		).split(
			"\\s+"
		);

		if ((words.length == 1) && words[0].isEmpty()) {
			return wordSequences;
		}

		if (words.length < _WORD_SEQUENCE_SIZE) {
			for (String word : words) {
				wordSequences.add(word);
			}

			return wordSequences;
		}

		for (int i = 0; i <= (words.length - _WORD_SEQUENCE_SIZE); i++) {
			StringBundler sb = new StringBundler();

			for (int j = 0; j < _WORD_SEQUENCE_SIZE; j++) {
				if (j > 0) {
					sb.append(StringPool.SPACE);
				}

				sb.append(words[i + j]);
			}

			wordSequences.add(sb.toString());
		}

		return wordSequences;
	}

	private static final int _BITS_PER_SAMPLE = 8;

	private static final int _SAMPLES_PER_SIMILARITY_KEY = 4;

	private static final int _SIMILARITY_KEYS = 32;

	private static final int _WORD_SEQUENCE_SIZE = 3;

	private static final Hasher64 _hasher64 = Hashing.murmur3_128();
	private static final SimilarityHashPolicy _similarityHashPolicy =
		SimilarityHashing.superMinHash(
			_SIMILARITY_KEYS * _SAMPLES_PER_SIMILARITY_KEY, _BITS_PER_SAMPLE,
			SuperMinHashVersion.V1);

}