/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

const getValidationFromExpression = (validations, validation) => {
	return function transformValidationFromExpression(expression) {
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

const getValidation = (validations, transformValidationFromExpression) => {
	return function transformValue(value) {
		const {errorMessage = {}, expression = {}, parameter = {}} = value;
		let parameterMessage = '';
		let selectedValidation = transformValidationFromExpression(expression);
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
};

export const normalizeDataType = (initialDataType) => {
	return initialDataType === 'double' || initialDataType === 'integer'
		? 'numeric'
		: initialDataType;
};

export const getLocalizedValue = ({defaultLanguageId, editingLanguageId}) => (
	value
) => value[editingLanguageId] || value[defaultLanguageId];

export const getSelectedValidation = (validations) => {
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
};

export const transformData = ({
	defaultLanguageId,
	editingLanguageId,
	initialDataType,
	validation,
	validations: initialValidations,
	value,
}) => {
	const dataType = validation?.dataType ?? initialDataType;
	const validations = transformValidations(initialValidations, dataType);
	const parsedValidation = getValidation(
		validations,
		getValidationFromExpression(validations, validation)
	)(value);
	const localizationMode = editingLanguageId !== defaultLanguageId;

	return {
		...parsedValidation,
		dataType,
		localizationMode,
		validations,
	};
};
