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

import com.liferay.portal.kernel.microsofttranslator.MicrosoftTranslator;
import com.liferay.portal.kernel.microsofttranslator.MicrosoftTranslatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.webcache.WebCacheException;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portlet.translator.model.Translation;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 */
public class TranslationWebCacheItem implements WebCacheItem {

	public TranslationWebCacheItem(String translationId, String fromText) {
		_translationId = translationId;
		_fromText = fromText;
	}

	public Object convert(String key) throws WebCacheException {
		Translation translation = new Translation(_translationId, _fromText);

		try {
			MicrosoftTranslator microsoftTranslator =
				MicrosoftTranslatorFactoryUtil.getMicrosoftTranslator();

			int x = _translationId.indexOf(StringPool.UNDERLINE);

			if ((x == -1) || ((x + 1) == _translationId.length())) {
				throw new WebCacheException(
					"Invalid translation ID " + _translationId);
			}

			String fromLanguage = getLanguage(_translationId.substring(0, x));
			String toLanguage = getLanguage(_translationId.substring(x + 1));

			String toText = microsoftTranslator.translate(
				fromLanguage, toLanguage, _fromText);

			translation.setToText(toText);
		}
		catch (Exception e) {
			throw new WebCacheException(e);
		}

		return translation;
	}

	public long getRefreshTime() {
		return _REFRESH_TIME;
	}

	protected String getLanguage(String babelFishLanguageId) {
		if (babelFishLanguageId.equals("zh")) {
			return "zh-CHS";
		}
		else if (babelFishLanguageId.equals("zt")) {
			return "zh-CHT";
		}

		return babelFishLanguageId;
	}

	private static final long _REFRESH_TIME = Time.DAY * 90;

	private String _fromText;
	private String _translationId;

}