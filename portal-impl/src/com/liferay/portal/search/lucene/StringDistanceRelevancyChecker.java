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

package com.liferay.portal.search.lucene;

import org.apache.lucene.search.spell.StringDistance;
import org.apache.lucene.search.spell.SuggestWord;

/**
 * @author Michael C. Han
 */
public class StringDistanceRelevancyChecker implements RelevancyChecker {

	public StringDistanceRelevancyChecker(
		String word, float scoresThreshold, StringDistance stringDistance) {

		_word = word;
		_scoresThreshold = scoresThreshold;
		_stringDistance = stringDistance;
	}

	@Override
	public boolean isRelevant(SuggestWord suggestWord) {
		String word = suggestWord.string;

		if (word.equals(_word)) {
			return false;
		}

		suggestWord.score = _stringDistance.getDistance(
			_word, suggestWord.string);

		if (suggestWord.score <= _scoresThreshold) {
			return false;
		}

		return true;
	}

	private final float _scoresThreshold;
	private final StringDistance _stringDistance;
	private final String _word;

}