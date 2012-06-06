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

package com.liferay.portal.microsofttranslator;

import com.liferay.portal.kernel.microsofttranslator.MicrosoftTranslatorException;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Hugo Huijser
 */
public class MicrosoftTranslator {

	public MicrosoftTranslator() {
		_microsoftTranslatorAuthenticator =
			new MicrosoftTranslatorAuthenticator();
	}

	public MicrosoftTranslator(String clientId, String clientSecret) {
		_microsoftTranslatorAuthenticator =
			new MicrosoftTranslatorAuthenticator(clientId, clientSecret);
	}

	public MicrosoftTranslatorAuthenticator
		getMicrosoftTranslatorAuthenticator() {

		return _microsoftTranslatorAuthenticator;
	}

	public String translate(
			String fromLanguage, String toLanguage, String fromText)
		throws Exception {

		Http.Options options = new Http.Options();

		StringBundler sb = new StringBundler(7);

		sb.append("http://api.microsofttranslator.com/v2/Http.svc/Translate?");
		sb.append("text=");
		sb.append(HttpUtil.encodeURL(fromText));
		sb.append("&from=");
		sb.append(fromLanguage);
		sb.append("&to=");
		sb.append(toLanguage);

		options.setLocation(sb.toString());

		String accessToken = _microsoftTranslatorAuthenticator.getAccessToken();

		if (Validator.isNull(accessToken)) {
			throw new MicrosoftTranslatorException(
				_microsoftTranslatorAuthenticator.getError());
		}

		options.addHeader("Authorization", "Bearer " + accessToken);

		String text = HttpUtil.URLtoString(options);

		int x = text.indexOf(">") + 1;
		int y = text.indexOf("</string>", x);

		if ((x == -1) || (y == -1)) {
			x = text.indexOf("Message: ");
			y = text.indexOf("<", x);

			if ((x > -1) && (y > -1)) {
				text = text.substring(x, y);
			}

			throw new MicrosoftTranslatorException(text);
		}

		String toText = text.substring(x, y).trim();

		return StringUtil.replace(toText, CharPool.NEW_LINE, CharPool.SPACE);
	}

	private MicrosoftTranslatorAuthenticator _microsoftTranslatorAuthenticator;

}