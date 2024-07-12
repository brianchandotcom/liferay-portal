/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.language;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Iván Zaera Avellón
 */
public class LanguageState {

	public static final LanguageState EMPTY = new LanguageState(
		Collections.emptyMap());

	public static LanguageState get() {
		return _languageStateAtomicReference.get();
	}

	public static void reload() {
		LanguageState languageState = _languageStateAtomicReference.get();

		_languageStateAtomicReference.set(
			new LanguageState(languageState._webContextPathKeysMap));
	}

	public static void update(Map<String, List<String>> webContextPathKeysMap) {
		_languageStateAtomicReference.set(
			new LanguageState(webContextPathKeysMap));
	}

	public String getHash() {
		if (!_loaded) {
			_load();
		}

		return _hash;
	}

	public Collection<String> getKeys(String webContextPath) {
		return _webContextPathKeysMap.get(webContextPath);
	}

	public Map<String, String> getLabels(Locale locale) {
		if (!_loaded) {
			_load();
		}

		return _localeLabelsMap.get(locale);
	}

	private LanguageState(Map<String, List<String>> webContextPathKeysMap) {
		MapUtil.copy(webContextPathKeysMap, _webContextPathKeysMap);
	}

	private synchronized void _load() {
		if (_loaded) {
			return;
		}

		long start = System.currentTimeMillis();

		// Merge all keys into a single set and sort them

		SortedSet<String> sortedKeys = new TreeSet<>();

		for (List<String> webContextPathKeys :
				_webContextPathKeysMap.values()) {

			sortedKeys.addAll(webContextPathKeys);
		}

		// Initialize _localeLabelsMap

		_localeLabelsMap = new HashMap<>();

		Language language = LanguageUtil.getLanguage();

		for (Locale locale : language.getAvailableLocales()) {
			Map<String, String> labels = new HashMap<>();

			for (String key : sortedKeys) {
				labels.put(key, language.get(locale, key, key));
			}

			_localeLabelsMap.put(locale, labels);
		}

		// Initialize _hash

		MessageDigest messageDigest;

		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		}
		catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			throw new RuntimeException(noSuchAlgorithmException);
		}

		List<Locale> sortedLocales = new ArrayList<>(_localeLabelsMap.keySet());

		Collections.sort(
			sortedLocales,
			(locale1, locale2) -> {
				String locale1String = locale1.toString();
				String locale2String = locale2.toString();

				return locale1String.compareTo(locale2String);
			});

		for (Locale locale : sortedLocales) {
			String localeString = locale.toString();

			messageDigest.update(localeString.getBytes(StandardCharsets.UTF_8));

			messageDigest.update(_DIGEST_END_OF_LOCALE);

			Map<String, String> labels = _localeLabelsMap.get(locale);

			for (String key : sortedKeys) {
				String label = labels.get(key);

				messageDigest.update(key.getBytes(StandardCharsets.UTF_8));

				messageDigest.update(_DIGEST_END_OF_KEY);

				messageDigest.update(label.getBytes(StandardCharsets.UTF_8));

				messageDigest.update(_DIGEST_END_OF_VALUE);
			}
		}

		_hash = StringUtil.bytesToHexString(messageDigest.digest());

		_loaded = true;

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Loading language state ", getHash(), " took ",
					System.currentTimeMillis() - start, " ms (",
					sortedKeys.size(), " keys, ", _webContextPathKeysMap.size(),
					" contexts)"));
		}
	}

	private static final byte[] _DIGEST_END_OF_KEY = {2};

	private static final byte[] _DIGEST_END_OF_LOCALE = {1};

	private static final byte[] _DIGEST_END_OF_VALUE = {3};

	private static final Log _log = LogFactoryUtil.getLog(LanguageState.class);

	private static final AtomicReference<LanguageState>
		_languageStateAtomicReference = new AtomicReference<>(EMPTY);

	private String _hash;
	private volatile boolean _loaded;
	private Map<Locale, Map<String, String>> _localeLabelsMap;
	private final Map<String, List<String>> _webContextPathKeysMap =
		new HashMap<>();

}