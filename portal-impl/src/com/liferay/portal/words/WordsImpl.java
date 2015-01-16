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

package com.liferay.portal.words;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.jazzy.InvalidWord;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.words.Words;
import com.liferay.util.ContentUtil;

import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.DefaultWordFinder;
import com.swabunga.spell.event.SpellChecker;
import com.swabunga.spell.event.StringWordTokenizer;

import java.io.IOException;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Shinn Lok
 */
public class WordsImpl implements Words {

	@Override
	public List<InvalidWord> checkSpelling(String text) {
		SpellChecker spellChecker = new SpellChecker(
			getSpellDictionaryHashMap());

		BasicSpellCheckListener basicSpellCheckListener =
			new BasicSpellCheckListener(text);

		spellChecker.addSpellCheckListener(basicSpellCheckListener);

		spellChecker.checkSpelling(
			new StringWordTokenizer(new DefaultWordFinder(text)));

		return basicSpellCheckListener.getInvalidWords();
	}

	@Override
	public List<String> getDictionaryList() {
		if (_dictionaryList == null) {
			_dictionaryList = ListUtil.fromArray(
				StringUtil.splitLines(
					ContentUtil.get(
						"com/liferay/portal/words/dependencies/words.txt")));
		}

		return _dictionaryList;
	}

	@Override
	public Set<String> getDictionarySet() {
		if (_dictionarySet == null) {
			_dictionarySet = new HashSet<>(getDictionaryList());
		}

		return _dictionarySet;
	}

	@Override
	public String getRandomWord() {
		List<String> dictionaryList = getDictionaryList();

		Random random = new Random(SecureRandomUtil.nextLong());

		int pos = random.nextInt(dictionaryList.size());

		return dictionaryList.get(pos);
	}

	@Override
	public boolean isDictionaryWord(String word) {
		Set<String> dictionarySet = getDictionarySet();

		return dictionarySet.contains(word);
	}

	protected SpellDictionaryHashMap getSpellDictionaryHashMap() {
		if (_spellDictionaryHashMap != null) {
			return _spellDictionaryHashMap;
		}

		try {
			_spellDictionaryHashMap = new SpellDictionaryHashMap();

			String[] dics = new String[] {
				"center.dic", "centre.dic", "color.dic", "colour.dic",
				"eng_com.dic", "english.0", "english.1", "ise.dic", "ize.dic",
				"labeled.dic", "labelled.dic", "yse.dic", "yze.dic"
			};

			for (int i = 0; i < dics.length; i++) {
				_spellDictionaryHashMap.addDictionary(
					new UnsyncStringReader(
						ContentUtil.get(
							"com/liferay/portal/words/dependencies/" +
								dics[i])));
			}
		}
		catch (IOException ioe) {
			_log.error(ioe);
		}

		return _spellDictionaryHashMap;
	}

	private static final Log _log = LogFactoryUtil.getLog(WordsImpl.class);

	private List<String> _dictionaryList;
	private Set<String> _dictionarySet;
	private SpellDictionaryHashMap _spellDictionaryHashMap;

}