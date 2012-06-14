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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.webcache.WebCacheException;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portlet.translator.model.Translation;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 */
public class TranslatorUtil {

	public static String[] getFromAndToLanguageIds(
		String translationId, String[] allLanguageIds) {

		try {
			int x = translationId.indexOf(StringPool.UNDERLINE);

			String fromLanguage = translationId.substring(0, x);

			if (!ArrayUtil.contains(allLanguageIds, fromLanguage)) {
				x = translationId.indexOf(StringPool.UNDERLINE, x + 1);

				fromLanguage = translationId.substring(0, x);

				if (!ArrayUtil.contains(allLanguageIds, fromLanguage)) {
					return null;
				}
			}

			String toLanguage = translationId.substring(x + 1);

			if (!ArrayUtil.contains(allLanguageIds, toLanguage)) {
				return null;
			}

			return new String[] {fromLanguage, toLanguage};
		}
		catch (Exception e) {
		}

		return null;
	}

	public static Translation getTranslation(
			String fromLanguage, String toLanguage, String fromText)
		throws WebCacheException {

		WebCacheItem wci = new TranslationWebCacheItem(
			fromLanguage, toLanguage, fromText);

		return (Translation)wci.convert("");
	}

}