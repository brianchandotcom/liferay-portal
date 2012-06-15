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

package com.liferay.portlet.translator.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.webcache.WebCacheException;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portlet.translator.model.Translation;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
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

			String toLanguageId = translationId.substring(pos + 1);

			if (!languageIdsMap.containsKey(fromLanguageId)) {
				return null;
			}

			return new String[] {fromLanguageId, toLanguageId};
		}
		catch (Exception e) {
		}

		return null;
	}

	public static Map<String, String> getLanguageIdsMap(Locale locale)
		throws SystemException {

		Map<String, String> languageIdsMap = new HashMap<String, String>();

		String[] languageIds =
			PrefsPropsUtil.getStringArray(
				PropsKeys.TRANSLATOR_LANGUAGES, StringPool.COMMA);

		for (String languageId : languageIds) {
			languageIdsMap.put(
				languageId, LanguageUtil.get(locale, "language." + languageId));
		}

		SortedMap languageIdsSortedMap = new TreeMap<String, String>(
			new ValueComparator(languageIdsMap));

		languageIdsSortedMap.putAll(languageIdsMap);

		return languageIdsSortedMap;
	}

	public static Translation getTranslation(
			String fromLanguageId, String toLanguageId, String fromText)
		throws WebCacheException {

		WebCacheItem wci = new TranslationWebCacheItem(
			fromLanguageId, toLanguageId, fromText);

		return (Translation)wci.convert("");
	}

	public static class ValueComparator implements Comparator<Object> {

		public ValueComparator(Map<String, String> data) {
			_data = data;
		}

		public int compare(Object obj1, Object obj2) {
			String value1 = (String)_data.get(obj1);
			String value2 = (String)_data.get(obj2);

			return value1.compareTo(value2);
		}

		private Map<String, String> _data = null;

	}

}