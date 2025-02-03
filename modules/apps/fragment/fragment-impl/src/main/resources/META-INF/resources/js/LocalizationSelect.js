/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LanguagePicker} from '@clayui/core';
import React, {useEffect, useMemo, useState} from 'react';

import './LocalizationSelect.scss';

const EVENT_TRANSLATION_STATUS = 'localizationSelect:updateTranslationStatus';

export function LocalizationSelect({
	defaultLanguageId,
	editMode,
	hideLanguageLabel,
	locales,
	size,
}) {
	const [active, setActive] = useState(false);
	const [selectedLocaleId, setSelectedLocaleId] = useState(defaultLanguageId);
	const [translations, setTranslations] = useState({});

	const localizableInputs = useMemo(
		() => document.querySelectorAll('[data-localizable="true"]').length,
		[]
	);

	const onSelectedLocaleChange = (localeId) => {
		setSelectedLocaleId(localeId);
		setActive(false);
	};

	useEffect(() => {
		const updateTranslationStatus = ({languageId}) => {
			const translatedInputs = Array.from(
				document.querySelectorAll(
					`[type="hidden"][name$="_${languageId}"]`
				)
			).filter((input) => input.getAttribute('value') !== null).length;

			const label = locales.find(
				(locale) => locale.id === languageId
			).label;

			setTranslations((previousState) => ({
				...previousState,
				...((defaultLanguageId === languageId || translatedInputs) && {
					[label]: {
						total: localizableInputs,
						translated: translatedInputs,
					},
				}),
			}));
		};

		Liferay.on(EVENT_TRANSLATION_STATUS, updateTranslationStatus);

		for (const locale of locales) {
			updateTranslationStatus({languageId: locale.id});
		}

		return () => {
			Liferay.detach(EVENT_TRANSLATION_STATUS);
		};
	}, [defaultLanguageId, locales, localizableInputs]);

	useEffect(() => {
		const onLocaleChanged = ({languageId}) => {
			if (selectedLocaleId !== languageId) {
				setSelectedLocaleId(languageId);
			}
		};

		Liferay.on('localizationSelect:localeChanged', onLocaleChanged);

		return () => {
			Liferay.detach('localizationSelect:localeChanged', onLocaleChanged);
		};
	}, [selectedLocaleId]);

	return (
		<LanguagePicker
			active={active}
			defaultLocaleId={defaultLanguageId}
			hideLanguageLabel={hideLanguageLabel}
			locales={locales}
			messages={{
				default: Liferay.Language.get('default'),
				option: Liferay.Language.get('x-language-x'),
				translated: Liferay.Language.get('translated'),
				translating: Liferay.Language.get('translating-x-x'),
				trigger: Liferay.Language.get(
					'select-a-language.-current-language-x'
				),
				untranslated: Liferay.Language.get('not-translated'),
			}}
			onActiveChange={(active) => {
				if (!editMode) {
					setActive(active);
				}
			}}
			onSelectedLocaleChange={(id) => {
				onSelectedLocaleChange(id);

				Liferay.fire('localizationSelect:localeChanged', {
					languageId: id,
				});
			}}
			selectedLocaleId={selectedLocaleId}
			small={size === 'small'}
			translations={translations}
		/>
	);
}
