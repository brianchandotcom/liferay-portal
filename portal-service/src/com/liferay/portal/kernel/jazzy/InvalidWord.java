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

package com.liferay.portal.kernel.jazzy;

import java.io.Serializable;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class InvalidWord implements Serializable {

	public InvalidWord(
		String invalidWord, List<String> suggestions, String wordContext,
		int wordContextPosition) {

		_invalidWord = invalidWord;
		_suggestions = suggestions;
		_wordContext = wordContext;
		_wordContextPosition = wordContextPosition;
	}

	public String getInvalidWord() {
		return _invalidWord;
	}

	public List<String> getSuggestions() {
		return _suggestions;
	}

	public String getWordContext() {
		return _wordContext;
	}

	public int getWordContextPosition() {
		return _wordContextPosition;
	}

	private final String _invalidWord;
	private final List<String> _suggestions;
	private final String _wordContext;
	private final int _wordContextPosition;

}