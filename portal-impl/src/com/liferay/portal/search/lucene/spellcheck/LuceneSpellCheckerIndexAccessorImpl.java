/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.lucene.spellcheck;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.resource.ResourceLoader;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.lucene.BaseIndexAccessorAdapter;
import com.liferay.portal.search.lucene.SpellCheckerIndexAccessor;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;
import java.io.InputStream;

import java.util.Locale;

import org.apache.lucene.search.spell.Dictionary;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.store.Directory;

/**
 * @author Michael C. Han
 */
public class LuceneSpellCheckerIndexAccessorImpl
	extends BaseIndexAccessorAdapter implements SpellCheckerIndexAccessor {

	public void afterPropertiesSet() {
		try {
			Directory spellCheckerDirectory = getDirectory(doGetPath());

			_spellChecker = _spellCheckerFactory.createSpellChecker(
				spellCheckerDirectory);
		}
		catch (Exception e) {
			throw new IllegalStateException(
				"Unable to initialize spell checker", e);
		}
	}

	public void close() throws IOException {
		_spellChecker.close();
	}

	public SpellChecker getSpellChecker() {
		return _spellChecker;
	}

	public void indexDictionaries() throws IOException {

		String[] localeStrings = PropsValues.LUCENE_SPELL_CHECKER_LOCALES;

		for (String localeString : localeStrings) {
			Locale locale = LocaleUtil.fromLanguageId(localeString);

			indexDictionary(locale);
		}
	}

	public void indexDictionary(Locale locale) throws IOException {

		String dictionaryFileNameList = PropsUtil.get(
			PropsKeys.LUCENE_SPELL_CHECKER_DICTIONARY,
			new Filter(locale.toString()));

		String[] dictionaryFileNames = StringUtil.split(dictionaryFileNameList);

		for (String dictionaryFileName : dictionaryFileNames) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Commencing dictionary indexing for: " + locale + " - " +
					dictionaryFileName);
			}

			InputStream dictionaryFileAsStream = _resourceLoader.getInputStream(
				dictionaryFileName);

			if (dictionaryFileAsStream != null) {
				Dictionary dictionary = new PlainTextDictionary(
					dictionaryFileAsStream);

				_spellChecker.indexDictionary(
					dictionary, locale, getIndexWriterConfig(), true);
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn("No dictionary defined for: " + locale);
				}
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("Completing text file based dictionary: " + locale);
		}
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		_resourceLoader = resourceLoader;
	}

	public void setSpellCheckerFactory(
		SpellCheckerFactory spellCheckerFactory) {

		_spellCheckerFactory = spellCheckerFactory;
	}

	@Override
	protected String doGetPath() {
		return PropsValues.LUCENE_SPELL_CHECKER_DIR.concat(StringPool.SLASH);
	}

	private static Log _log = LogFactoryUtil.getLog(
		LuceneSpellCheckerIndexAccessorImpl.class);

	private ResourceLoader _resourceLoader;

	private SpellChecker _spellChecker;
	private SpellCheckerFactory _spellCheckerFactory;

}