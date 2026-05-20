/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

export default function useLocalizationLanguageId(
	defaultLanguageId: Liferay.Language.Locale
) {
	const [languageId, setLanguageId] =
		useState<Liferay.Language.Locale>(defaultLanguageId);

	useEffect(() => {
		const handleLocaleChanged = ({
			languageId,
		}: {
			languageId: Liferay.Language.Locale;
		}) => setLanguageId(languageId);

		Liferay.on('localizationSelect:localeChanged', handleLocaleChanged);

		return () =>
			Liferay.detach(
				'localizationSelect:localeChanged',
				handleLocaleChanged
			);
	}, []);

	return languageId;
}
