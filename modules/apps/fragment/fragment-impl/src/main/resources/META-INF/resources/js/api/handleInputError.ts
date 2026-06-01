/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type ErrorArgs = {
	errorContainer: HTMLSpanElement;
	errorMessageContainer: HTMLSpanElement;
	formGroup: HTMLDivElement;
	lengthInfoContainer?: HTMLParagraphElement;
	message?: string | null;
};

export function getLengthErrorMessage({
	errorMessageContainer,
	length,
	maxLength,
}: {
	errorMessageContainer: HTMLSpanElement;
	length: number;
	maxLength: number;
}): string {
	const lengthFeedback = errorMessageContainer.getAttribute(
		'data-length-feedback'
	);

	return `${lengthFeedback}: ${length} / ${maxLength}`;
}

export function handleInputLengthError({
	currentLength,
	errorContainer,
	errorMessageContainer,
	event,
	formGroup,
	input,
	lengthInfoContainer,
}: {
	currentLength?: HTMLElement;
	event: KeyboardEvent;
	input: {attributes: {maxLength: number}};
	lengthInfoContainer?: HTMLParagraphElement;
} & ErrorArgs) {
	const length = (event.target as HTMLInputElement).value.length;
	const {maxLength} = input.attributes;

	if (currentLength) {
		currentLength.innerText = String(length);
	}

	const params = {
		errorContainer,
		errorMessageContainer,
		formGroup,
		lengthInfoContainer,
	};

	if (length > maxLength) {
		showInputError({
			...params,
			message: getLengthErrorMessage({
				errorMessageContainer,
				length,
				maxLength,
			}),
		});
	}
	else if (formGroup.classList.contains('has-error')) {
		const validFeedback =
			errorMessageContainer.getAttribute('data-valid-feedback') ?? '';

		hideInputError({...params, message: validFeedback});
	}
}

export function showInputError({
	errorContainer,
	errorMessageContainer,
	formGroup,
	lengthInfoContainer,
	message,
}: ErrorArgs) {
	errorContainer.classList.remove('sr-only');
	formGroup.classList.add('has-error');
	lengthInfoContainer?.classList.add('d-none');

	if (message) {
		errorMessageContainer.innerText = message;
	}
}

export function hideInputError({
	errorContainer,
	errorMessageContainer,
	formGroup,
	lengthInfoContainer,
	message,
}: ErrorArgs) {
	errorContainer.classList.add('sr-only');
	formGroup.classList.remove('has-error');
	lengthInfoContainer?.classList.remove('d-none');

	if (message) {
		errorMessageContainer.innerText = message;
	}
}
