/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locale, TranslationAdminSelector} from 'frontend-js-components-web';
import React, {useEffect, useState} from 'react';

type Field = Record<Liferay.Language.Locale, string>;

interface Props {
	defaultLanguageId: Liferay.Language.Locale;
	fields: Record<string, Field>;
	locales: Locale[];
	selectedLanguageId: Liferay.Language.Locale;
}

export default function TranslationManager({
	defaultLanguageId,
	fields,
	locales,
	selectedLanguageId: initialSelectedLanguageId,
}: Props) {
	const [selectedLanguageId, setSelectedLanguageId] = useState<
		Liferay.Language.Locale
	>(initialSelectedLanguageId);
	const [, setTranslations] = useState(fieldToTranslations(fields));

	useEffect(() => {
		const updateTranslations = () => {
			const newTranslations = Object.keys(fields).map((fieldName) => {
				const languages = Array.from(
					document.querySelectorAll<HTMLInputElement>(
						`[type="hidden"][data-field-name="${fieldName}"]`
					)
				)
					.filter((input) => input.value)
					.map(
						(input) =>
							input.dataset.languageId as Liferay.Language.Locale
					);

				return {
					fieldName,
					languages,
				};
			});

			setTranslations(newTranslations);
		};

		Liferay.on(
			'inputLocalized:updateTranslationStatus',
			updateTranslations
		);

		return () => {
			Liferay.detach(
				'inputLocalized:updateTranslationStatus',
				updateTranslations
			);
		};
	}, [fields]);

	useEffect(() => {
		Liferay.fire('inputLocalized:localeChanged', {
			item: document.querySelector(
				`[data-languageid="${selectedLanguageId}"]`
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
			selectedLanguageId={selectedLanguageId}
		/>
	);
}

function fieldToTranslations(fields: Record<string, Field>) {
	const translations = [];

	for (const fieldName in fields) {
		const languages = fields[fieldName]
			? (Object.keys(fields[fieldName]) as Liferay.Language.Locale[])
			: [];

		translations.push({
			fieldName,
			languages,
		});
	}

	return translations;
}
