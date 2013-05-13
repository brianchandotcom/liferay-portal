/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.webcache.WebCacheException;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portlet.translator.model.Translation;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 */
public class TranslationWebCacheItem implements WebCacheItem {

	public TranslationWebCacheItem(
		String fromLanguageId, String toLanguageId, String fromText) {

		_fromLanguageId = fromLanguageId;
		_toLanguageId = toLanguageId;
		_fromText = fromText;
	}

	@Override
	public Object convert(String key) throws WebCacheException {
		Translation translation = new Translation(
			_fromLanguageId, _toLanguageId, _fromText);

		try {
			MicrosoftTranslator microsoftTranslator =
				MicrosoftTranslatorFactoryUtil.getMicrosoftTranslator();

			String toText = microsoftTranslator.translate(
				_fromLanguageId, _toLanguageId, _fromText);

			translation.setToText(toText);
		}
		catch (Exception e) {
			throw new WebCacheException(e);
		}

		return translation;
	}

	@Override
	public long getRefreshTime() {
		return _REFRESH_TIME;
	}

	private static final long _REFRESH_TIME = Time.DAY * 90;

	private String _fromLanguageId;
	private String _fromText;
	private String _toLanguageId;

}