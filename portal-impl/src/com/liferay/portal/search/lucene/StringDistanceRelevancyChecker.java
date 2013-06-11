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

package com.liferay.portal.search.lucene;

import org.apache.lucene.search.spell.StringDistance;
import org.apache.lucene.search.spell.SuggestWord;

/**
 * @author Michael C. Han
 */
public class StringDistanceRelevancyChecker implements RelevancyChecker {

	public StringDistanceRelevancyChecker(
		String word, float scoringThreshold, StringDistance stringDistance) {

		_scoringThreshold = scoringThreshold;
		_stringDistance = stringDistance;
		_word = word;
	}

	@Override
	public boolean isRelevant(SuggestWord suggestWord) {
		if (suggestWord.string.equals(_word)) {
			return false;
		}

		suggestWord.score = _stringDistance.getDistance(
			_word, suggestWord.string);

		if (suggestWord.score <= _scoringThreshold) {
			return false;
		}

		return true;
	}

	private float _scoringThreshold;
	private StringDistance _stringDistance;
	private String _word;

}