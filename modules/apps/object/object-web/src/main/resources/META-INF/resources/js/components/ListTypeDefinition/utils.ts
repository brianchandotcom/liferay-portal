/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {translationsNormalizer} from 'frontend-js-components-web';

export function fixLocaleKeys(name_i18n: {[key: string]: string}) {
	const newTranslationsObject: LocalizedValue<string> = {};

	for (const [key, value] of Object.entries(name_i18n)) {
		newTranslationsObject[
			key.replace(/-/g, '_') as Liferay.Language.Locale
		] = value;
	}

	return translationsNormalizer(newTranslationsObject);
}
