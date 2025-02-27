/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getOrCreateTranslationInput(
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
