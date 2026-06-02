/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {focusInput} from './focusInput';
import {
	getLengthErrorMessage,
	handleInputLengthError,
	hideInputError,
	showInputError,
} from './handleInputError';

type Args = {
	currentLengthContainer?: HTMLSpanElement;
	errorContainer: HTMLSpanElement;
	errorMessageContainer: HTMLSpanElement;
	formGroup: HTMLDivElement;
	fragmentElement: HTMLElement;
	input?: {attributes: {maxLength: number}};
	inputElement: HTMLInputElement;
	lengthInfoContainer?: HTMLParagraphElement;
};

export function registerInputFeedback({
	currentLengthContainer,
	errorContainer,
	errorMessageContainer,
	formGroup,
	fragmentElement,
	input,
	inputElement,
	lengthInfoContainer,
}: Args) {
	const errorContainers = {errorContainer, errorMessageContainer, formGroup};
	const maxLength = input?.attributes?.maxLength;

	if (formGroup.classList.contains('has-error')) {
		focusInput(inputElement);
	}

	const updateValidityError = () => {
		const error = getValidityError(
			errorMessageContainer,
			inputElement.validity
		);

		if (error) {
			showInputError({...errorContainers, message: error});
		}
		else if (formGroup.classList.contains('has-error')) {
			hideInputError({
				...errorContainers,
				message: errorMessageContainer.getAttribute(
					'data-valid-feedback'
				),
			});
		}
	};

	inputElement.addEventListener('invalid', (event) => {
		event.preventDefault();

		updateValidityError();

		focusInput(inputElement);
	});

	if (fragmentElement) {
		fragmentElement.addEventListener('focusout', (event) => {
			if (
				fragmentElement.contains(event.relatedTarget as Node | null) ||
				formGroup.classList.contains('has-error')
			) {
				return;
			}

			updateValidityError();
		});
	}

	if (maxLength) {
		const length = inputElement.value.length;

		if (currentLengthContainer) {
			currentLengthContainer.innerText = length.toString();
		}

		if (!formGroup.classList.contains('has-error') && length > maxLength) {
			showInputError({
				...errorContainers,
				lengthInfoContainer,
				message: getLengthErrorMessage({
					length,
					maxLength,
					message: errorMessageContainer.getAttribute(
						'data-length-feedback'
					),
				}),
			});
		}

		inputElement.addEventListener('keyup', (event: KeyboardEvent) => {
			handleInputLengthError({
				...errorContainers,
				currentLength: currentLengthContainer,
				event,
				input,
				lengthInfoContainer,
			});
		});
	}
}

function getValidityError(
	errorMessageContainer: HTMLSpanElement,
	validity: ValidityState
): string | null {
	if (validity.valid) {
		return null;
	}

	if (validity.valueMissing) {
		return (
			errorMessageContainer.getAttribute('data-required-feedback') ??
			Liferay.Language.get('this-field-is-required')
		);
	}

	return errorMessageContainer.getAttribute('data-invalid-feedback');
}
