/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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