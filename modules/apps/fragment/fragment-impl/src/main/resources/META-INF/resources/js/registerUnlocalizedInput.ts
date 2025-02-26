/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type Args = {
	changeTextDirection?: boolean;
	defaultLanguageId: Liferay.Language.Locale;
	inputElement?: HTMLInputElement;
	onLocaleChange?: (languageId: string) => void;
	readOnlyInputLabel?: HTMLSpanElement;
	unlocalizedFieldsState: 'disabled' | 'read-only';
	unlocalizedMessageContainer: HTMLElement;
};

export function registerUnlocalizedInput({
	changeTextDirection = true,
	defaultLanguageId,
	inputElement,
	onLocaleChange,
	readOnlyInputLabel,
	unlocalizedFieldsState,
	unlocalizedMessageContainer,
}: Args) {
	Liferay.on(
		'localizationSelect:localeChanged',
		({languageId}: {languageId: Liferay.Language.Locale}) => {
			onLocaleChange?.(languageId);

			const editingDefaultLanguage = languageId === defaultLanguageId;

			// Show unlocalized icon for non-default language

			unlocalizedMessageContainer?.classList.toggle(
				'd-none',
				editingDefaultLanguage
			);

			if (changeTextDirection) {
				inputElement?.setAttribute(
					'dir',
					Liferay.Language.direction[languageId]!
				);
			}

			// Change state of the input to disabled/readonly for non default language

			const isReadOnlyFieldState = unlocalizedFieldsState === 'read-only';

			if (editingDefaultLanguage) {
				inputElement?.removeAttribute(
					isReadOnlyFieldState ? 'readonly' : 'disabled'
				);
			}
			else {
				inputElement?.setAttribute(
					isReadOnlyFieldState ? 'readonly' : 'disabled',
					''
				);
			}

			if (isReadOnlyFieldState) {

				// Show "(Read Only)" label in input label

				readOnlyInputLabel?.classList.toggle(
					'd-none',
					editingDefaultLanguage
				);
			}
			else {

				// Remove disable attribute before submit to include the value in the form

				inputElement?.closest('form')?.addEventListener(
					'submit',
					() => {
						inputElement?.removeAttribute('disabled');
					},
					true
				);
			}
		}
	);
}
