/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type ErrorArgs = {
	errorContainer: HTMLSpanElement;
	errorMessageContainer: HTMLSpanElement;
	errorType: 'length' | 'required' | 'valid';
	formGroup: HTMLDivElement;
	lengthInfoContainer?: HTMLParagraphElement;
};

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
	lengthInfoContainer: HTMLParagraphElement;
} & ErrorArgs) {
	const length = (event.target as HTMLInputElement).value.length;

	if (currentLength) {
		currentLength.innerText = String(length);
	}

	const params = {
		additionalMessage: `: ${length} / ${input.attributes.maxLength}`,
		errorContainer,
		errorMessageContainer,
		formGroup,
		lengthInfoContainer,
	};

	if (length > input.attributes.maxLength) {
		showInputError({...params, errorType: 'length'});
	}
	else if (formGroup.classList.contains('has-error')) {
		hideInputError({...params, errorType: 'valid'});
	}
}

export function showInputError({
	additionalMessage,
	errorContainer,
	errorMessageContainer,
	errorType = 'required',
	formGroup,
	lengthInfoContainer,
}: ErrorArgs & {
	additionalMessage?: string;
}) {
	errorContainer.classList.remove('sr-only');
	formGroup.classList.add('has-error');
	lengthInfoContainer?.classList.add('d-none');

	updateFeedback(errorMessageContainer, errorType, additionalMessage);
}

function hideInputError({
	errorContainer,
	errorMessageContainer,
	errorType,
	formGroup,
	lengthInfoContainer,
}: ErrorArgs) {
	errorContainer.classList.add('sr-only');
	formGroup.classList.remove('has-error');
	lengthInfoContainer?.classList.remove('d-none');

	updateFeedback(errorMessageContainer, errorType);
}

function updateFeedback(
	element: HTMLElement,
	messageType: 'length' | 'required' | 'valid',
	additionalMessage: string = ''
) {
	const message = element.getAttribute(`data-${messageType}-feedback`);

	if (message) {
		element.innerText = `${message}${additionalMessage}`;
	}
}
