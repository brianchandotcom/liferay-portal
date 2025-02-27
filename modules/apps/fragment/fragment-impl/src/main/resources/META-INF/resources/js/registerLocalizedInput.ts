/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getOrCreateTranslationInput} from './getOrCreateTranslationInput';

type Args = {
	changeTextDirection: boolean;
	defaultLanguageId: Liferay.Language.Locale;
	initialValues: Record<string, any>;
	inputElement?: HTMLInputElement;
	inputName: string;
	localizationInputsContainer: HTMLElement;
	namespace: string;
	onLocaleChange?: ({
		languageId,
		value,
	}: {
		languageId: string;
		value: string;
	}) => void;
	optionValues: Record<string, string>;
};

export function registerLocalizedInput({
	changeTextDirection = true,
	defaultLanguageId,
	initialValues,
	inputElement,
	inputName,
	localizationInputsContainer,
	namespace,
	onLocaleChange,
	optionValues,
}: Args) {
	if (initialValues) {
		Object.entries(initialValues).forEach(([languageId, value]) => {
			const input = getOrCreateTranslationInput(
				inputElement?.id || inputName,
				inputName,
				languageId,
				localizationInputsContainer,
				namespace
			);

			input.value = value;

			if (optionValues) {
				input.dataset.label = optionValues[value];
			}
		});
	}

	let currentLanguageId = defaultLanguageId;

	if (changeTextDirection) {
		inputElement?.setAttribute(
			'dir',
			Liferay.Language.direction[defaultLanguageId]!
		);
	}

	Liferay.on(
		'localizationSelect:localeChanged',
		({languageId}: {languageId: Liferay.Language.Locale}) => {
			currentLanguageId = languageId;

			if (changeTextDirection) {
				inputElement?.setAttribute(
					'dir',
					Liferay.Language.direction[languageId]!
				);
			}

			const translationInput = getOrCreateTranslationInput(
				inputElement?.id || inputName,
				inputName,
				languageId,
				localizationInputsContainer,
				namespace
			);

			if (translationInput.getAttribute('value') !== null) {
				onLocaleChange?.({languageId, value: translationInput.value});

				if (!inputElement) {
					return;
				}

				if (inputElement.type === 'checkbox') {
					inputElement.checked = translationInput.value === 'true';
				}
				else if (inputElement.getAttribute('role') === 'combobox') {
					inputElement.value = translationInput.dataset.label || '';
				}
				else {
					inputElement.value = translationInput.value;
				}
			}
			else {
				const defaultLanguageInput = getOrCreateTranslationInput(
					inputElement?.id || inputName,
					inputName,
					defaultLanguageId,
					localizationInputsContainer,
					namespace
				);

				onLocaleChange?.({
					languageId,
					value: defaultLanguageInput.value,
				});

				if (!inputElement) {
					return;
				}

				if (inputElement.getAttribute('role') === 'combobox') {
					inputElement.value =
						defaultLanguageInput.dataset.label || '';
				}
				else {
					inputElement.value = defaultLanguageInput.value;
				}
			}
		}
	);

	return {
		onChange: (value: string, label?: string) => {
			const translationInput = getOrCreateTranslationInput(
				inputElement?.id || inputName,
				inputName,
				currentLanguageId,
				localizationInputsContainer,
				namespace
			);

			translationInput.value = value;

			if (label) {
				translationInput.dataset.label = label;
			}

			Liferay.fire('localizationSelect:updateTranslationStatus', {
				languageId: currentLanguageId,
			});
		},
	};
}
