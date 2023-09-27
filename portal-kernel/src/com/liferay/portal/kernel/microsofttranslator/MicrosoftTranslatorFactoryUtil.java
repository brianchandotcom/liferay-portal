/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.microsofttranslator;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Hugo Huijser
 */
public class MicrosoftTranslatorFactoryUtil {

	public static MicrosoftTranslator getMicrosoftTranslator() {
		return _microsoftTranslatorFactory.getMicrosoftTranslator();
	}

	public static MicrosoftTranslatorFactory getMicrosoftTranslatorFactory() {
		return _microsoftTranslatorFactory;
	}

	private static volatile MicrosoftTranslatorFactory
		_microsoftTranslatorFactory =
			ServiceProxyFactory.newServiceTrackedInstance(
				MicrosoftTranslatorFactory.class,
				MicrosoftTranslatorFactoryUtil.class,
				"_microsoftTranslatorFactory", false);

}