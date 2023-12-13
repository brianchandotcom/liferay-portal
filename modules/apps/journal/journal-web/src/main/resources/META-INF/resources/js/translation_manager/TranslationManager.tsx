/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locale, TranslationAdminSelector} from 'frontend-js-components-web';
import React, {useState} from 'react';

interface Props {
	defaultLanguageId: Liferay.Language.Locale;
	locales: Locale[];
	selectedLanguageId: Liferay.Language.Locale;
}

export default function TranslationManager({
	defaultLanguageId,
	locales,
	selectedLanguageId: initialSelectedLanguageId,
}: Props) {
	const [selectedLanguageId, setSelectedLanguageId] = useState<
		Liferay.Language.Locale
	>(initialSelectedLanguageId);

	return (
		<TranslationAdminSelector
			activeLanguageIds={locales.map(({id}) => id)}
			availableLocales={locales}
			defaultLanguageId={defaultLanguageId}
			displayType="HORIZONTAL"
			onSelectedLanguageIdChange={setSelectedLanguageId}
			selectedLanguageId={selectedLanguageId}
		/>
	);
}
