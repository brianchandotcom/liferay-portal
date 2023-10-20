/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.words;

import com.liferay.portal.kernel.jazzy.InvalidWord;
import com.liferay.portal.kernel.module.service.Snapshot;

import java.util.List;
import java.util.Set;

/**
 * @author Shinn Lok
 */
public class WordsUtil {

	public static List<InvalidWord> checkSpelling(String text) {
		Words words = _wordsSnapshot.get();

		return words.checkSpelling(text);
	}

	public static List<String> getDictionaryList() {
		Words words = _wordsSnapshot.get();

		return words.getDictionaryList();
	}

	public static Set<String> getDictionarySet() {
		Words words = _wordsSnapshot.get();

		return words.getDictionarySet();
	}

	public static String getRandomWord() {
		Words words = _wordsSnapshot.get();

		return words.getRandomWord();
	}

	public static Words getWords() {
		return _wordsSnapshot.get();
	}

	public static boolean isDictionaryWord(String word) {
		Words words = _wordsSnapshot.get();

		return words.isDictionaryWord(word);
	}

	private static final Snapshot<Words> _wordsSnapshot = new Snapshot<>(
		WordsUtil.class, Words.class);

}