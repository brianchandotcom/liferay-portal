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

package com.liferay.portal.microsofttranslator;

import com.liferay.portal.kernel.microsofttranslator.MicrosoftTranslator;
import com.liferay.portal.kernel.microsofttranslator.MicrosoftTranslatorFactory;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;

/**
 * @author Hugo Huijser
 */
@DoPrivileged
public class MicrosoftTranslatorFactoryImpl
	implements MicrosoftTranslatorFactory {

	@Override
	public MicrosoftTranslator getMicrosoftTranslator() {
		if (_microsoftTranslator == null) {
			_microsoftTranslator = new MicrosoftTranslatorImpl();
		}

		return _microsoftTranslator;
	}

	@Override
	public MicrosoftTranslator getMicrosoftTranslator(
		String clientId, String clientSecret) {

		return new MicrosoftTranslatorImpl(clientId, clientSecret);
	}

	private MicrosoftTranslator _microsoftTranslator;

}