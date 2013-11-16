/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.service.base.WordsServiceBaseImpl;
import com.liferay.portal.words.WordsUtil;
import com.liferay.util.jazzy.InvalidWord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class WordsServiceImpl extends WordsServiceBaseImpl {

	@Override
	public List<String> checkSpelling(String text) {
		List<String> invalidWordsList = new ArrayList<String>();

		for (InvalidWord invalidWord : WordsUtil.checkSpelling(text)) {
			invalidWordsList.add(invalidWord.getInvalidWord());
		}

		return invalidWordsList;
	}

	@Override
	public List<String> getSuggestions(String word) {
		List<InvalidWord> invalidWords = WordsUtil.checkSpelling(word);

		if (invalidWords.isEmpty()) {
			return Collections.emptyList();
		}

		InvalidWord invalidWord = invalidWords.get(0);

		return invalidWord.getSuggestions();
	}

}