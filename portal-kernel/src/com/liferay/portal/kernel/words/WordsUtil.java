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

package com.liferay.portal.kernel.words;

import com.liferay.portal.kernel.jazzy.InvalidWord;

import java.util.List;
import java.util.Set;

/**
 * @author Shinn Lok
 */
public class WordsUtil {

	public static List<InvalidWord> checkSpelling(String text) {
		return getWords().checkSpelling(text);
	}

	public static List<String> getDictionaryList() {
		return getWords().getDictionaryList();
	}

	public static Set<String> getDictionarySet() {
		return getWords().getDictionarySet();
	}

	public static String getRandomWord() {
		return getWords().getRandomWord();
	}

	public static Words getWords() {
		return _words;
	}

	public static boolean isDictionaryWord(String word) {
		return getWords().isDictionaryWord(word);
	}

	public void setWords(Words words) {
		_words = words;
	}

	private static Words _words;

}