/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const getValidationFromExpression = (validations, validation, expression) => {
	let mutValidation;

	if (!expression && validation) {
		expression = validation.expression;
	}

	if (expression) {
		mutValidation = validations.find(
			(validation) => validation.name === expression.name
		);
	}

	return mutValidation;
};

const transformValidations = (validations, dataType) => {
	return validations[normalizeDataType(dataType)].map((validation) => {
		return {
			...validation,
			checked: false,
			value: validation.name,
		};
	});
};

const getValidation = (validations, validation, value) => {
	const {errorMessage = {}, expression = {}, parameter = {}} = value;
	let parameterMessage = '';
	let selectedValidation = getValidationFromExpression(
		validations,
		validation,
		expression
	);
	const enableValidation = !!expression.value;

	if (selectedValidation) {
		parameterMessage = selectedValidation.parameterMessage;
	}
	else {
		selectedValidation = validations[0];
	}

	return {
		enableValidation,
		errorMessage,
		expression,
		parameter,
		parameterMessage,
		selectedValidation,
	};
};

export function normalizeDataType(initialDataType) {
	return initialDataType === 'double' || initialDataType === 'integer'
		? 'numeric'
		: initialDataType;
}

export function getLocalizedValue({defaultLanguageId, editingLanguageId}) {
	return (value) => value[editingLanguageId] ?? value[defaultLanguageId];
}

export function getSelectedValidation(validations) {
	return function transformSelectedValidation(value) {
		if (Array.isArray(value)) {
			value = value[0];
		}

		let selectedValidation = validations.find(({name}) => name === value);

		if (!selectedValidation) {
			selectedValidation = validations[0];
		}

		return selectedValidation;
	};
}

export function transformData({
	defaultLanguageId,
	editingLanguageId,
	initialDataType,
	validation,
	validations: initialValidations,
	value,
}) {
	const dataType = validation?.dataType ?? initialDataType;
	const validations = transformValidations(initialValidations, dataType);
	const parsedValidation = getValidation(validations, validation, value);
	const localizationMode = editingLanguageId !== defaultLanguageId;

	return {
		...parsedValidation,
		dataType,
		localizationMode,
		validations,
	};
}
