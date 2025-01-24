/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type Args = {
	defaultLanguageId: string;
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
};

export function registerLocalizedInput({
	defaultLanguageId,
	initialValues,
	inputElement,
	inputName,
	localizationInputsContainer,
	namespace,
	onLocaleChange,
}: Args) {
	if (initialValues) {
		Object.entries(initialValues).forEach(([languageId, value]) => {
			const input = getOrCreateTranslationInput(
				inputName,
				languageId,
				localizationInputsContainer,
				namespace
			);

			if (input.type === 'checkbox') {
				input.checked = value;
			}
			else {
				input.value = value;
			}
		});
	}

	let currentLanguageId = defaultLanguageId;

	Liferay.on('localizationSelect:localeChanged', ({languageId}) => {
		currentLanguageId = languageId;

		const translationInput = getOrCreateTranslationInput(
			inputName,
			languageId,
			localizationInputsContainer,
			namespace
		);

		if (translationInput.getAttribute('value') !== null) {
			onLocaleChange?.({languageId, value: translationInput.value});

			if (inputElement) {
				inputElement.value = translationInput.value;
			}
		}
		else {
			const defaultLanguageInput = getOrCreateTranslationInput(
				inputName,
				defaultLanguageId,
				localizationInputsContainer,
				namespace
			);

			onLocaleChange?.({languageId, value: defaultLanguageInput.value});

			if (inputElement) {
				inputElement.value = defaultLanguageInput.value;
			}
		}
	});

	return {
		onChange: (value: string) => {
			const translationInput = getOrCreateTranslationInput(
				inputName,
				currentLanguageId,
				localizationInputsContainer,
				namespace
			);

			translationInput.value = value;

			Liferay.fire('localizationSelect:updateTranslationStatus', {
				languageId: currentLanguageId,
			});
		},
	};
}

function getOrCreateTranslationInput(
	inputName: string,
	languageId: string,
	localizationInputsContainer: HTMLElement,
	namespace: string
) {
	const inputId = `${namespace}${inputName}_${languageId}`;

	let translationInput = document.getElementById(inputId) as HTMLInputElement;

	if (!translationInput) {
		translationInput = document.createElement('input');
		translationInput.type = 'hidden';
		translationInput.id = inputId;
		translationInput.name = `${inputName}_${languageId}`;
		localizationInputsContainer.appendChild(translationInput);
	}

	return translationInput;
}
