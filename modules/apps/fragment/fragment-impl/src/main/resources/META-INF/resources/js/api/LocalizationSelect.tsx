/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LanguagePicker} from '@clayui/core';
import React, {Key, useEffect, useRef, useState} from 'react';

import './LocalizationSelect.scss';

const EVENT_TRANSLATION_STATUS = 'localizationSelect:updateTranslationStatus';

type Props = {
	defaultLanguageId: Liferay.Language.Locale;
	editMode: boolean;
	hideLanguageLabel: boolean;
	locales: Array<{
		id: Liferay.Language.Locale;
		label: string;
		name?: string;
		symbol: string;
	}>;
	size: string;
};

type Translations = {
	[key: string]: {total: number; translated: number};
};

export function LocalizationSelect({
	defaultLanguageId,
	editMode,
	hideLanguageLabel,
	locales,
	size,
}: Props) {
	const [active, setActive] = useState(false);
	const [selectedLocaleId, setSelectedLocaleId] = useState(defaultLanguageId);
	const [translations, setTranslations] = useState<Translations>({});
	const [form, setForm] = useState<HTMLFormElement>();

	const containerRef = useRef<HTMLDivElement>(null);

	const onSelectedLocaleChange = (localeId: Liferay.Language.Locale) => {
		setSelectedLocaleId(localeId);
		setActive(false);
	};

	useEffect(() => {
		const form = containerRef.current?.closest(
			'.lfr-layout-structure-item-form'
		);

		if (form) {
			setForm(form as HTMLFormElement);
		}
	}, []);

	useEffect(() => {
		const updateTranslationStatus = ({
			languageId,
		}: {
			languageId: Liferay.Language.Locale;
		}) => {
			const element = form || document;

			const total = element.querySelectorAll(
				'[data-localizable="true"]'
			).length;

			const translated = new Set([
				...Array.from(
					element.querySelectorAll(
						`[data-localizable="true"] [type="file"][name$="_${languageId}"]`
					)
				),
				...Array.from(
					element.querySelectorAll(
						`[data-localizable="true"] [type="hidden"][name$="_${languageId}"]`
					)
				)
					.filter((input) => input.getAttribute('value') !== null)
					.map((input) => (input as HTMLInputElement).name),
			]).size;

			const label = locales.find(
				(locale) => locale.id === languageId
			)?.label;

			if (!label) {
				return;
			}

			setTranslations((previousState) => ({
				...previousState,
				...(translated && {
					[label]: {
						total,
						translated,
					},
				}),
			}));
		};

		Liferay.on(EVENT_TRANSLATION_STATUS, updateTranslationStatus);

		for (const locale of locales) {
			updateTranslationStatus({languageId: locale.id});
		}

		return () => {
			Liferay.detach(EVENT_TRANSLATION_STATUS, updateTranslationStatus);
		};
	}, [defaultLanguageId, form, locales]);

	useEffect(() => {
		const onLocaleChanged = ({
			formId,
			languageId,
		}: {
			formId?: string;
			languageId: Liferay.Language.Locale;
		}) => {
			if (formId && formId !== form?.id) {
				return;
			}

			if (selectedLocaleId !== languageId) {
				setSelectedLocaleId(languageId);
			}
		};

		Liferay.on('localizationSelect:localeChanged', onLocaleChanged);

		return () => {
			Liferay.detach('localizationSelect:localeChanged', onLocaleChanged);
		};
	}, [form, selectedLocaleId]);

	return (
		<div ref={containerRef}>
			<LanguagePicker
				active={active}
				defaultLocaleId={defaultLanguageId}
				hideTriggerText={hideLanguageLabel}
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
				onActiveChange={(active: boolean) => {
					if (!editMode) {
						setActive(active);
					}
				}}
				onSelectedLocaleChange={(id: Key) => {
					onSelectedLocaleChange(id as Liferay.Language.Locale);

					Liferay.fire('localizationSelect:localeChanged', {
						formId: form?.id,
						languageId: id,
					});
				}}
				selectedLocaleId={selectedLocaleId}
				small={size === 'small'}
				translations={translations}
			/>
		</div>
	);
}
