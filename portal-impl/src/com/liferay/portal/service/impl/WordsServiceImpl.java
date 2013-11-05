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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.service.base.WordsServiceBaseImpl;
import com.liferay.portal.words.WordsUtil;
import com.liferay.util.jazzy.InvalidWord;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class WordsServiceImpl extends WordsServiceBaseImpl {

	/**
	 * Checks the spelling of a block of text.
	 *
	 * <p>
	 * This method handles spell checking of text. It takes a block of text
	 * as input,and returns a list of any incorrectly spelled words found.
	 * </p>
	 *
	 * @param  text the block of text to be spell checked.
	 * @return the mis-spelled words
	 */
	public JSONObject checkSpelling(String text) {
		JSONArray inner = JSONFactoryUtil.createJSONArray();

		List<String> invalidWords = getInvalidWords(text);

		for (String s : invalidWords) {
			inner.put(s);
		}

		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(inner);

		jsonObj.put(_OUTCOME, _SUCCESS);
		jsonObj.put(_DATA, jsonArray);

		return jsonObj;
	}

	/**
	 * Finds suggestions for a misspelled word.
	 *
	 * <p>
	 * This method finds suggested spellings for a misspelled word.
	 * It takes a misspelled word as input,and returns a list of
	 * suggested correctly spelled words.
	 * </p>
	 *
	 * @param  word the misspelled word.
	 * @return the suggested corrections.
	 */
	public List<String> getSuggestions(String word) {
		List<InvalidWord> invalidWords = WordsUtil.checkSpelling(word);

		List<String> suggestions = new ArrayList<String>();

		if (!invalidWords.isEmpty()) {
			suggestions = invalidWords.get(0).getSuggestions();
		}

		return suggestions;
	}

	protected List<String> getInvalidWords(String text) {
		List<String> invalid = new ArrayList<String>();

		List<InvalidWord> invalidWords = WordsUtil.checkSpelling(text);

		for (InvalidWord invalidWord : invalidWords) {
			invalid.add(invalidWord.getInvalidWord());
		}

		return invalid;
	}

	private static final String _DATA = "data";

	private static final String _OUTCOME = "outcome";

	private static final String _SUCCESS = "success";

}