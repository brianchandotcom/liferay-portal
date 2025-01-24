/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type Args = {
	defaultLanguageId: string;
	inputElement?: HTMLInputElement;
	onLocaleChange?: (languageId: string) => void;
	unlocalizedFieldsState: 'disabled' | 'read-only';
	unlocalizedMessageContainer: HTMLElement;
};

export function registerUnlocalizedInput({
	defaultLanguageId,
	inputElement,
	onLocaleChange,
	unlocalizedFieldsState,
	unlocalizedMessageContainer,
}: Args) {
	Liferay.on('localizationSelect:localeChanged', ({languageId}) => {
		onLocaleChange?.(languageId);

		if (languageId === defaultLanguageId) {
			if (unlocalizedFieldsState === 'disabled') {
				inputElement?.removeAttribute('disabled');
			}
			else {
				inputElement?.removeAttribute('readonly');
			}

			unlocalizedMessageContainer?.classList.add('d-none');
		}
		else {
			if (unlocalizedFieldsState === 'disabled') {
				inputElement?.setAttribute('disabled', '');
			}
			else {
				inputElement?.setAttribute('readonly', '');
			}

			unlocalizedMessageContainer.classList.remove('d-none');
		}
	});
}
