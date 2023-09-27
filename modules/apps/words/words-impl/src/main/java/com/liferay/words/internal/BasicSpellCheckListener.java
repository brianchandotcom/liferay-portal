/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.words.internal;

import com.liferay.portal.kernel.jazzy.InvalidWord;

import com.swabunga.spell.engine.Word;
import com.swabunga.spell.event.SpellCheckEvent;
import com.swabunga.spell.event.SpellCheckListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class BasicSpellCheckListener implements SpellCheckListener {

	public BasicSpellCheckListener(String text) {
		_text = text;

		_textCharArray = text.toCharArray();
	}

	public List<InvalidWord> getInvalidWords() {
		return _invalidWords;
	}

	@Override
	public void spellingError(SpellCheckEvent event) {
		List<String> suggestions = new ArrayList<>();

		for (Word word : (List<Word>)event.getSuggestions()) {
			suggestions.add(word.getWord());
		}

		int pos = event.getWordContextPosition();

		if (pos >= 0) {
			String invalidWord = event.getInvalidWord();

			if ((pos == 0) ||
				((pos > 0) &&
				 //(_text.charAt(pos - 1) != '<') &&
				 (!_isInsideHtmlTag(pos)) &&
				 (_text.charAt(pos - 1) != '&') &&
				 (invalidWord.length() > 1))) {

				_invalidWords.add(
					new InvalidWord(
						invalidWord, suggestions, event.getWordContext(), pos));
			}
		}
	}

	private boolean _isInsideHtmlTag(int pos) {
		boolean insideHtmlTag = false;

		for (int i = pos; i >= 0; i--) {
			if (_textCharArray[i] == '<') {
				insideHtmlTag = true;

				break;
			}
			else if (_textCharArray[i] == '>') {
				break;
			}
		}

		if (insideHtmlTag) {
			for (int i = pos; i < _textCharArray.length; i++) {
				if (_textCharArray[i] == '<') {
					insideHtmlTag = false;

					break;
				}
				else if (_textCharArray[i] == '>') {
					break;
				}
			}
		}

		return insideHtmlTag;
	}

	private final List<InvalidWord> _invalidWords = new ArrayList<>();
	private final String _text;
	private final char[] _textCharArray;

}