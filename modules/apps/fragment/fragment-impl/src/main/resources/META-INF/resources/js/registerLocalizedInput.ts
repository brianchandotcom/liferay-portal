/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getOrCreateTranslationInput} from './getOrCreateTranslationInput';

type Args = {
	changeTextDirection: boolean;
	defaultLanguageId: Liferay.Language.Locale;
	initialValues?: Record<string, any>;
	inputElement?: HTMLInputElement;
	inputName: string;
	localizationInputsContainer: HTMLElement;
	namespace: string;
	onLocaleChange?: ({
		languageId,
		value,
	}: {
		languageId: string;
		value?: string;
	}) => void;
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
}: Args) {

	// Create hidden inputs for initial values if any

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

			if (!initialValues && !inputElement) {
				onLocaleChange?.({languageId});

				return;
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

				inputElement.value = defaultLanguageInput.value;
			}
		}
	);

	return {
		onChange: ({
			handleChange,
			value = null,
		}: {
			handleChange?: () => void;
			value?: string | null;
		}) => {
			handleChange?.();

			if (value !== null) {
				const translationInput = getOrCreateTranslationInput(
					inputElement?.id || inputName,
					inputName,
					currentLanguageId,
					localizationInputsContainer,
					namespace
				);

				translationInput.value = value;
			}

			Liferay.fire('localizationSelect:updateTranslationStatus', {
				languageId: currentLanguageId,
			});
		},
	};
}
