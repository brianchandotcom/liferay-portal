/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.words;

import com.liferay.portal.kernel.jazzy.InvalidWord;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.List;
import java.util.Set;

/**
 * @author Shinn Lok
 */
public class WordsUtil {

	public static List<InvalidWord> checkSpelling(String text) {
		return _words.checkSpelling(text);
	}

	public static List<String> getDictionaryList() {
		return _words.getDictionaryList();
	}

	public static Set<String> getDictionarySet() {
		return _words.getDictionarySet();
	}

	public static String getRandomWord() {
		return _words.getRandomWord();
	}

	public static Words getWords() {
		return _words;
	}

	public static boolean isDictionaryWord(String word) {
		return _words.isDictionaryWord(word);
	}

	private static volatile Words _words =
		ServiceProxyFactory.newServiceTrackedInstance(
			Words.class, WordsUtil.class, "_words", true);

}