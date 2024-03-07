/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {Locale, TranslationAdminSelector} from 'frontend-js-components-web';
import React from 'react';

import useTranslationProgress from './useTranslationProgress';

interface TranslationManagerProps {
	defaultLanguageId: Liferay.Language.Locale;
	fields: Fields;
	locales: Locale[];
	namespace: string;
	selectedLanguageId: Liferay.Language.Locale;
}
export type Field = Record<Liferay.Language.Locale, string>;
export type Fields = Record<string, Field>;

export default function TranslationManager({
	defaultLanguageId: initialDefaultLanguageId,
	fields: initialFields,
	locales,
	namespace,
	selectedLanguageId: initialSelectedLanguageId,
}: TranslationManagerProps) {
	const {
		defaultLanguageId,
		selectedLanguageId,
		translationProgress,
		updateTranslations,
	} = useTranslationProgress({
		defaultLanguageId: initialDefaultLanguageId,
		fields: initialFields,
		locales,
		namespace,
		selectedLanguageId: initialSelectedLanguageId,
	});

	const handleSelectedLanguageIdChange = (
		languageId: Liferay.Language.Locale
	) => {
		Liferay.fire('inputLocalized:localeChanged', {
			item: document.querySelector(
				`[data-languageid="${languageId}"][data-value="${languageId}"]`
			),
		});
	};

	return (
		<div
			className={classNames({
				'translation-manager': Liferay.FeatureFlags['LPD-11253'],
			})}
		>
			<TranslationAdminSelector
				activeLanguageIds={locales.map(({id}) => id)}
				availableLocales={locales}
				defaultLanguageId={defaultLanguageId}
				displayType="HORIZONTAL"
				onSelectedLanguageIdChange={handleSelectedLanguageIdChange}
				onSelectorActiveChange={updateTranslations}
				selectedLanguageId={selectedLanguageId}
				translationProgress={translationProgress}
			/>
		</div>
	);
}
