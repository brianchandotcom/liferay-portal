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

import com.liferay.portal.kernel.microsofttranslator.MicrosoftTranslatorFactory;

/**
 * @author Hugo Huijser
 */
public class MicrosoftTranslatorFactoryImpl
	implements MicrosoftTranslatorFactory {

	public String translate(
			String fromLanguage, String toLanguage, String fromText)
		throws Exception {

		MicrosoftTranslator microsoftTranslator = getMicrosoftTranslator();

		return microsoftTranslator.translate(
			fromLanguage, toLanguage, fromText);
	}

	protected MicrosoftTranslator getMicrosoftTranslator() {
		MicrosoftTranslator microsoftTranslator = _microsoftTranslator;

		if (_microsoftTranslator == null) {
			microsoftTranslator = new MicrosoftTranslator();
		}

		return microsoftTranslator;
	}

	private static MicrosoftTranslator _microsoftTranslator;

}