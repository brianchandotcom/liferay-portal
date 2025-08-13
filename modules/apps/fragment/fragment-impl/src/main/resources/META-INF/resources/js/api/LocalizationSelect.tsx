/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LanguagePicker} from '@clayui/core';
import ClayDropDown from '@clayui/drop-down';
import {openConfirmModal} from '@liferay/layout-js-components-web';
import React, {Key, useEffect, useRef, useState} from 'react';

import './LocalizationSelect.scss';

import {ClayButtonWithIcon} from '@clayui/button';
import {sub} from 'frontend-js-web';

const EVENT_TRANSLATION_STATUS = 'localizationSelect:updateTranslationStatus';

type Props = {
	allowLocalizationManagement: boolean;
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
	allowLocalizationManagement,
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

	const [dropdownTrigger, setDropdownTrigger] =
		useState<HTMLButtonElement | null>();

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
						`[data-localizable="true"] [type="file"][name$="_${languageId}"], ` +
							`[data-localizable="true"] [type="hidden"][name$="_${languageId}"]`
					)
				)
					.filter((input) => hasValue(input as HTMLInputElement))
					.map((input) => (input as HTMLInputElement).name),
			]).size;

			const label = locales.find(
				(locale) => locale.id === languageId
			)?.label;

			if (!label) {
				return;
			}

			setTranslations((previous) => {
				const nextTranslations: Translations = {
					...previous,
					[label]: {
						total,
						translated,
					},
				};

				if (!translated) {
					delete nextTranslations[label];
				}

				return nextTranslations;
			});
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
		<div className="align-items-center c-gap-2 d-flex" ref={containerRef}>
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

			{allowLocalizationManagement &&
			selectedLocaleId !== defaultLanguageId ? (
				<ClayDropDown
					hasLeftSymbols
					menuElementAttrs={{
						containerProps: {
							className: 'cadmin',
						},
					}}
					trigger={
						<ClayButtonWithIcon
							aria-label={Liferay.Language.get(
								'localization-actions'
							)}
							displayType="secondary"
							monospaced
							ref={setDropdownTrigger}
							size={size === 'small' ? 'sm' : 'regular'}
							symbol="ellipsis-v"
							title={Liferay.Language.get('localization-actions')}
						/>
					}
				>
					<ClayDropDown.ItemList>
						<ClayDropDown.Item disabled symbolLeft="stars">
							{Liferay.Language.get('auto-translate')}
						</ClayDropDown.Item>

						<ClayDropDown.Item
							onClick={async () => {
								if (
									await openConfirmModal({
										buttonLabel:
											Liferay.Language.get(
												'mark-as-translated'
											),
										center: true,
										onCloseFocusElement: dropdownTrigger,
										status: 'info',
										text: sub(
											Liferay.Language.get(
												'all-the-fields-for-x-will-be-marked-as-translated'
											),
											selectedLocaleId
										),
										title: sub(
											Liferay.Language.get(
												'mark-x-as-translated'
											),
											selectedLocaleId
										),
									})
								) {
									Liferay.fire(
										'localizationSelect:markAsTranslated',
										{
											formId: form?.id,
											languageId: selectedLocaleId,
										}
									);
								}
							}}
							symbolLeft="check-circle"
						>
							{Liferay.Language.get('mark-as-translated')}
						</ClayDropDown.Item>

						<ClayDropDown.Item
							onClick={async () => {
								if (
									await openConfirmModal({
										buttonLabel:
											Liferay.Language.get('delete'),
										center: true,
										onCloseFocusElement: dropdownTrigger,
										status: 'danger',
										text: sub(
											Liferay.Language.get(
												'x-translation-will-be-deleted-and-content-fields-will-be-set-to-default-value'
											),
											selectedLocaleId
										),
										title: sub(
											Liferay.Language.get(
												'delete-x-translation'
											),
											selectedLocaleId
										),
									})
								) {
									Liferay.fire(
										'localizationSelect:resetTranslation',
										{
											formId: form?.id,
											languageId: selectedLocaleId,
										}
									);
								}
							}}
							symbolLeft="trash"
						>
							{Liferay.Language.get('reset-translation')}
						</ClayDropDown.Item>
					</ClayDropDown.ItemList>
				</ClayDropDown>
			) : null}
		</div>
	);
}

function hasValue(input: HTMLInputElement) {
	if (input.type === 'file') {
		return Boolean(input.files?.length);
	}

	return Boolean(input.getAttribute('value')?.length);
}
