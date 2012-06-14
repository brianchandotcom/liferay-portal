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

			String fromLanguageId = translationId.substring(0, x);

			if (!ArrayUtil.contains(allLanguageIds, fromLanguageId)) {
				x = translationId.indexOf(StringPool.UNDERLINE, x + 1);

				fromLanguageId = translationId.substring(0, x);

				if (!ArrayUtil.contains(allLanguageIds, fromLanguageId)) {
					return null;
				}
			}

			String toLanguageId = translationId.substring(x + 1);

			if (!ArrayUtil.contains(allLanguageIds, toLanguageId)) {
				return null;
			}

			return new String[] {fromLanguageId, toLanguageId};
		}
		catch (Exception e) {
		}

		return null;
	}

	public static Translation getTranslation(
			String fromLanguageId, String toLanguageId, String fromText)
		throws WebCacheException {

		WebCacheItem wci = new TranslationWebCacheItem(
			fromLanguageId, toLanguageId, fromText);

		return (Translation)wci.convert("");
	}

}