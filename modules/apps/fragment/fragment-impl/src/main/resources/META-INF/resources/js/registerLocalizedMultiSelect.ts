/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type Args = {
	defaultLanguageId: string;
	initialValues: Record<string, any>;
	inputElements: HTMLInputElement[];
	namespace: string;
};

export function registerLocalizedMultiSelect({
	defaultLanguageId,
	initialValues,
	inputElements,
	namespace,
}: Args) {
	if (initialValues) {
		inputElements.forEach((inputElement, index) => {
			Object.entries(initialValues).forEach(([languageId, value]) => {
				const input = getOrCreateTranslationInput(
					inputElement.id,
					inputElement.name,
					languageId,
					inputElement.parentNode as HTMLElement,
					namespace
				);

				if (index !== 0) {
					input.setAttribute('data-multiselect', 'true');
				}

				input.value = value.includes(inputElement.value)
					? inputElement.value
					: '';
			});
		});
	}

	let currentLanguageId = defaultLanguageId;

	Liferay.on('localizationSelect:localeChanged', ({languageId}) => {
		currentLanguageId = languageId;

		inputElements.forEach((inputElement, index) => {
			const translationInput = getOrCreateTranslationInput(
				inputElement.id,
				inputElement.name,
				languageId,
				inputElement.parentNode as HTMLElement,
				namespace
			);

			if (index !== 0) {
				translationInput.setAttribute('data-multiselect', 'true');
			}

			if (translationInput.getAttribute('value') !== null) {
				if (inputElement) {
					inputElement.checked = Boolean(translationInput.value);
				}
			}
			else {
				const defaultLanguageInput = getOrCreateTranslationInput(
					inputElement.id,
					inputElement.name,
					defaultLanguageId,
					inputElement.parentNode as HTMLElement,
					namespace
				);

				if (defaultLanguageInput) {
					inputElement.checked = Boolean(defaultLanguageInput.value);
				}
			}
		});
	});

	return {
		onChange: () => {
			inputElements.forEach((inputElement) => {
				const translationInput = getOrCreateTranslationInput(
					inputElement.id,
					inputElement.name,
					currentLanguageId,
					inputElement.parentNode as HTMLElement,
					namespace
				);

				translationInput.value = inputElement.checked
					? inputElement.value
					: '';
			});

			Liferay.fire('localizationSelect:updateTranslationStatus', {
				languageId: currentLanguageId,
			});
		},
	};
}

function getOrCreateTranslationInput(
	inputId: string,
	inputName: string,
	languageId: string,
	localizationInputsContainer: HTMLElement,
	namespace: string
) {
	const id = `${namespace}${inputId}_${languageId}`;

	let translationInput = document.getElementById(id) as HTMLInputElement;

	if (!translationInput) {
		translationInput = document.createElement('input');
		translationInput.type = 'hidden';
		translationInput.id = id;
		translationInput.name = `${inputName}_${languageId}`;
		localizationInputsContainer.appendChild(translationInput);
	}

	return translationInput;
}
