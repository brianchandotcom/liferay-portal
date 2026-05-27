/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {focusInput} from './focusInput';
import {hideInputError, showInputError} from './handleInputError';

type Args = {
	errorContainer: HTMLSpanElement;
	errorMessageContainer: HTMLSpanElement;
	formGroup: HTMLDivElement;
	fragmentElement: HTMLElement;
	input?: {attributes: {maxLength: number}};
	inputElement: HTMLInputElement;
};

export function registerInputFeedback({
	errorContainer,
	errorMessageContainer,
	formGroup,
	fragmentElement,
	inputElement,
}: Args) {
	const errorContainers = {errorContainer, errorMessageContainer, formGroup};

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
