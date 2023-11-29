/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	Language,
	TranslationSelector,
	Translations,
} from '@liferay/layout-js-components-web';
import React from 'react';

interface Props {
	defaultLanguageId: Liferay.Language.Locale;
	languages: Language[];
	selectedLanguageId: Liferay.Language.Locale;
	translations: Translations;
}

export default function TranslationManager({
	defaultLanguageId,
	languages,
	selectedLanguageId,
	translations,
}: Props) {
	return (
		<TranslationSelector
			defaultLanguageId={defaultLanguageId}
			languages={languages}
			onSelectedLanguageChange={(languageId) => {
				Liferay.fire('inputLocalized:localeChanged', {
					item: document.querySelector(
						`[data-languageid="${languageId}"]`
					),
				});
			}}
			selectedLanguageId={selectedLanguageId}
			translations={translations}
		/>
	);
}
