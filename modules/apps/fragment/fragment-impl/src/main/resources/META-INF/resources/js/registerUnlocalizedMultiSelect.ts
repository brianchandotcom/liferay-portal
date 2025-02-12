/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type Args = {
	defaultLanguageId: string;
	inputElements: HTMLInputElement[];
	onLocaleChange?: (languageId: string) => void;
	readOnlyInputLabel: HTMLSpanElement;
	unlocalizedFieldsState: 'disabled' | 'read-only';
	unlocalizedMessageContainer: HTMLElement;
};

export function registerUnlocalizedMultiSelect({
	defaultLanguageId,
	inputElements,
	onLocaleChange,
	readOnlyInputLabel,
	unlocalizedFieldsState,
	unlocalizedMessageContainer,
}: Args) {
	Liferay.on('localizationSelect:localeChanged', ({languageId}) => {
		onLocaleChange?.(languageId);

		if (languageId === defaultLanguageId) {
			if (unlocalizedFieldsState === 'disabled') {
				inputElements.forEach((inputElement) => {
					inputElement.removeAttribute('disabled');
				});
			}
			else {
				readOnlyInputLabel?.classList.add('d-none');
			}

			unlocalizedMessageContainer.classList.add('d-none');
		}
		else {
			if (unlocalizedFieldsState === 'disabled') {
				inputElements.forEach((inputElement) => {
					inputElement.setAttribute('disabled', '');
				});
			}
			else {
				readOnlyInputLabel.classList.remove('d-none');
			}

			unlocalizedMessageContainer.classList.remove('d-none');
		}
	});
}
