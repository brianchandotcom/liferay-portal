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

package com.liferay.portal.kernel.microsofttranslator;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Hugo Huijser
 */
public class MicrosoftTranslatorFactoryUtil {

	public static MicrosoftTranslator getMicrosoftTranslator() {
		return getMicrosoftTranslatorFactory().getMicrosoftTranslator();
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static MicrosoftTranslator getMicrosoftTranslator(
		String clientId, String clientSecret) {

		return getMicrosoftTranslatorFactory().getMicrosoftTranslator(
			clientId, clientSecret);
	}

	public static MicrosoftTranslatorFactory getMicrosoftTranslatorFactory() {
		return _microsoftTranslatorFactory;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public void setMicrosoftTranslatorFactory(
		MicrosoftTranslatorFactory microsoftTranslatorFactory) {
	}

	private static volatile MicrosoftTranslatorFactory
		_microsoftTranslatorFactory =
			ServiceProxyFactory.newServiceTrackedInstance(
				MicrosoftTranslatorFactory.class,
				MicrosoftTranslatorFactoryUtil.class,
				"_microsoftTranslatorFactory", false);

}