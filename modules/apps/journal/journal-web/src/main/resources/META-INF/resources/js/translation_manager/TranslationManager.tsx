/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	TranslationAdminSelector,
	TranslationProgress,
} from 'frontend-js-components-web';
import React, {useCallback, useEffect} from 'react';

import {Fields, TranslationsWrapper} from './TranslationsWrapper';

interface IProps extends TranslationsWrapper {
	getLocalizableFields: () => void;
	setFields: (fields: Fields) => void;
	setSelectedLanguageId: (languageId: Liferay.Language.Locale) => void;
	setTranslations: (translations: Translation[]) => void;
	translationProgress: TranslationProgress | null;
	updateTranslations: (fields: Fields) => void;
}

interface Translation {
	fieldName: string;
	languages: Liferay.Language.Locale[];
}

export default function TranslationManager({
	defaultLanguageId,
	fields,
	getLocalizableFields,
	locales,
	selectedLanguageId,
	setSelectedLanguageId,
	translationProgress,
	updateTranslations,
}: IProps) {
	const updateTranslationStatus = useCallback(
		() => updateTranslations(fields),
		[fields, updateTranslations]
	);

	useEffect(() => {
		if (fields) {
			Liferay.on(
				'inputLocalized:updateTranslationStatus',
				updateTranslationStatus
			);
		}

		return () => {
			Liferay.detach(
				'inputLocalized:updateTranslationStatus',
				updateTranslationStatus
			);
		};
	}, [fields, updateTranslationStatus]);

	useEffect(() => {
		Liferay.fire('inputLocalized:localeChanged', {
			item: document.querySelector(
				`[data-languageid="${selectedLanguageId}"][data-value="${selectedLanguageId}"]`
			),
		});
	}, [selectedLanguageId]);

	return (
		<TranslationAdminSelector
			activeLanguageIds={locales.map(({id}) => id)}
			availableLocales={locales}
			defaultLanguageId={defaultLanguageId}
			displayType="HORIZONTAL"
			onSelectedLanguageIdChange={setSelectedLanguageId}
			onSelectorActiveChange={getLocalizableFields}
			selectedLanguageId={selectedLanguageId}
			translationProgress={translationProgress}
		/>
	);
}
