/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type LengthErrorParams = {
	configuration: {
		showCharactersCount: boolean;
	};
	formGroup: HTMLDivElement;
	lengthInfo: HTMLParagraphElement;
	lengthWarning: HTMLSpanElement;
	lengthWarningText: HTMLSpanElement;
};

export function handleInputLengthError({
	configuration,
	currentLength,
	errorContainer,
	event,
	formGroup,
	input,
	lengthInfo,
	lengthWarning,
	lengthWarningText,
}: {
	currentLength: HTMLElement;
	errorContainer: HTMLElement;
	event: KeyboardEvent;
	input: {attributes: {maxLength: number}};
} & LengthErrorParams) {
	const length = (event.target as HTMLInputElement).value.length;

	currentLength.innerText = String(length);

	errorContainer?.remove();

	const params = {
		configuration,
		formGroup,
		lengthInfo,
		lengthWarning,
		lengthWarningText,
	};

	if (length > input.attributes.maxLength) {
		hideLengthError(params);
	}
	else if (formGroup.classList.contains('has-error')) {
		showLengthError(params);
	}
}

export function hideLengthError({
	configuration,
	formGroup,
	lengthInfo,
	lengthWarning,
	lengthWarningText,
}: LengthErrorParams) {
	formGroup.classList.add('has-error');
	lengthInfo.classList.add('text-danger', 'font-weight-semi-bold');
	lengthWarning.classList.remove('sr-only');

	updateLengthError(lengthWarningText, 'error');

	if (!configuration.showCharactersCount) {
		lengthInfo.classList.remove('sr-only');
	}
}

function showLengthError({
	configuration,
	formGroup,
	lengthInfo,
	lengthWarning,
	lengthWarningText,
}: LengthErrorParams) {
	formGroup.classList.remove('has-error');
	lengthInfo.classList.remove('text-danger', 'font-weight-semi-bold');
	lengthWarning.classList.add('sr-only');

	updateLengthError(lengthWarningText, 'valid');

	if (!configuration.showCharactersCount) {
		lengthInfo.classList.add('sr-only');
	}
}

function updateLengthError(
	element: HTMLElement,
	messageType: 'error' | 'valid'
) {
	const message = element.getAttribute(`data-${messageType}-message`);

	if (message) {
		element.innerText = message;
	}
}
