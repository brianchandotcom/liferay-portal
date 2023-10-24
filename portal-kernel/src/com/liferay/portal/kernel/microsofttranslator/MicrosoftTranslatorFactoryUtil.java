/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.microsofttranslator;

import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Hugo Huijser
 */
public class MicrosoftTranslatorFactoryUtil {

	public static MicrosoftTranslator getMicrosoftTranslator() {
		MicrosoftTranslatorFactory microsoftTranslatorFactory =
			_microsoftTranslatorFactorySnapshot.get();

		return microsoftTranslatorFactory.getMicrosoftTranslator();
	}

	public static MicrosoftTranslatorFactory getMicrosoftTranslatorFactory() {
		return _microsoftTranslatorFactorySnapshot.get();
	}

	private static final Snapshot<MicrosoftTranslatorFactory>
		_microsoftTranslatorFactorySnapshot = new Snapshot<>(
			MicrosoftTranslatorFactoryUtil.class,
			MicrosoftTranslatorFactory.class);

}