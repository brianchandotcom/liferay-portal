/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.language;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.Language;
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
		Collections.emptyMap(), null);

	public static LanguageState get() {
		return _languageStateAtomicReference.get();
	}

	public static void set(LanguageState languageState) {
		_languageStateAtomicReference.set(languageState);
	}

	public LanguageState(
		Map<String, List<String>> webContextPathKeysMap, Language language) {

		// Initialize _webContextPathKeysMap

		MapUtil.copy(webContextPathKeysMap, _webContextPathKeysMap);

		// Merge all keys into a single set and sort them

		for (List<String> webContextPathKeys :
				_webContextPathKeysMap.values()) {

			_sortedKeys.addAll(webContextPathKeys);
		}

		// Initialize _localeLabelsMap

		if (language != null) {
			for (Locale locale : language.getAvailableLocales()) {
				Map<String, String> labels = new HashMap<>();

				for (String key : _sortedKeys) {
					labels.put(key, language.get(locale, key, key));
				}

				_localeLabelsMap.put(locale, labels);
			}
		}
	}

	public String getHash() {
		if (_hash == null) {
			long start = System.currentTimeMillis();

			MessageDigest messageDigest;

			try {
				messageDigest = MessageDigest.getInstance("SHA-256");
			}
			catch (NoSuchAlgorithmException noSuchAlgorithmException) {
				throw new RuntimeException(noSuchAlgorithmException);
			}

			List<Locale> sortedLocales = new ArrayList<>(
				_localeLabelsMap.keySet());

			Collections.sort(
				sortedLocales,
				(locale1, locale2) -> {
					String locale1String = locale1.toString();
					String locale2String = locale2.toString();

					return locale1String.compareTo(locale2String);
				});

			for (Locale locale : sortedLocales) {
				String localeString = locale.toString();

				messageDigest.update(
					localeString.getBytes(StandardCharsets.UTF_8));

				messageDigest.update(_DIGEST_END_OF_LOCALE);

				Map<String, String> labels = _localeLabelsMap.get(locale);

				for (String key : _sortedKeys) {
					String label = labels.get(key);

					messageDigest.update(key.getBytes(StandardCharsets.UTF_8));

					messageDigest.update(_DIGEST_END_OF_KEY);

					messageDigest.update(
						label.getBytes(StandardCharsets.UTF_8));

					messageDigest.update(_DIGEST_END_OF_VALUE);
				}
			}

			if (_hash == null) {
				_hash = StringUtil.bytesToHexString(messageDigest.digest());

				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Hash computation for language state ", getHash(),
							" took ", System.currentTimeMillis() - start,
							" ms (", _sortedKeys.size(), " keys, ",
							_webContextPathKeysMap.size(), " contexts)"));
				}
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Hash computation for language state ", getHash(),
							" resulted in multiple calculations due to ",
							"multithreading (this is expected, not an error)"));
				}
			}
		}

		return _hash;
	}

	public Collection<String> getKeys(String webContextPath) {
		return _webContextPathKeysMap.get(webContextPath);
	}

	public Map<String, String> getLabels(Locale locale) {
		return _localeLabelsMap.get(locale);
	}

	private static final byte[] _DIGEST_END_OF_KEY = {2};

	private static final byte[] _DIGEST_END_OF_LOCALE = {1};

	private static final byte[] _DIGEST_END_OF_VALUE = {3};

	private static final Log _log = LogFactoryUtil.getLog(LanguageState.class);

	private static final AtomicReference<LanguageState>
		_languageStateAtomicReference = new AtomicReference<>(EMPTY);

	private volatile String _hash;
	private final Map<Locale, Map<String, String>> _localeLabelsMap =
		new HashMap<>();
	private final SortedSet<String> _sortedKeys = new TreeSet<>();
	private final Map<String, List<String>> _webContextPathKeysMap =
		new HashMap<>();

}