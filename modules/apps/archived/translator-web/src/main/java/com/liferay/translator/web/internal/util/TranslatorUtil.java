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

package com.liferay.translator.web.internal.util;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.webcache.WebCacheException;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.translator.web.internal.configuration.TranslatorConfiguration;
import com.liferay.translator.web.internal.model.Translation;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 */
public class TranslatorUtil {

	public static String[] getFromAndToLanguageIds(
		String translationId, Map<String, String> languageIdsMap) {

		try {
			int pos = translationId.indexOf(StringPool.UNDERLINE);

			String fromLanguageId = translationId.substring(0, pos);

			if (!languageIdsMap.containsKey(fromLanguageId)) {
				pos = translationId.indexOf(StringPool.UNDERLINE, pos + 1);

				fromLanguageId = translationId.substring(0, pos);

				if (!languageIdsMap.containsKey(fromLanguageId)) {
					return null;
				}
			}

			if (!languageIdsMap.containsKey(fromLanguageId)) {
				return null;
			}

			return new String[] {
				fromLanguageId, translationId.substring(pos + 1)
			};
		}
		catch (Exception e) {
			ReflectionUtil.throwException(e);
		}

		return null;
	}

	public static Map<String, String> getLanguageIdsMap(
		Locale locale, TranslatorConfiguration translatorConfiguration) {

		Map<String, String> languageIdsMap = new HashMap<>();

		String[] languageIds = translatorConfiguration.languageIds();

		for (String languageId : languageIds) {
			languageIdsMap.put(
				languageId, LanguageUtil.get(locale, "language." + languageId));
		}

		Map<String, String> sortedLanguageIdsMap = new TreeMap<>(
			new ValueComparator(languageIdsMap));

		sortedLanguageIdsMap.putAll(languageIdsMap);

		return sortedLanguageIdsMap;
	}

	public static Translation getTranslation(
			String fromLanguageId, String toLanguageId, String fromText)
		throws WebCacheException {

		WebCacheItem wci = new TranslatorWebCacheItem(
			fromLanguageId, toLanguageId, fromText);

		return (Translation)wci.convert("");
	}

	private static class ValueComparator implements Comparator<Object> {

		public ValueComparator(Map<String, String> map) {
			_map = map;
		}

		@Override
		public int compare(Object obj1, Object obj2) {
			String value1 = _map.get(obj1);
			String value2 = _map.get(obj2);

			return value1.compareTo(value2);
		}

		private final Map<String, String> _map;

	}

}