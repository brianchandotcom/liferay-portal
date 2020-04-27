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

import VALIDATIONS from '../util/validations.es';

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

const transformValidations = (validations, initialDataType) => {
	const dataType = initialDataType == 'string' ? initialDataType : 'numeric';

	return VALIDATIONS[dataType].map((validation) => {
		return {
			...validation,
			checked: false,
			value: validation.name,
		};
	});
};

const getValidation = (
	defaultLanguageId,
	editingLanguageId,
	validations,
	transformValidationFromExpression
) => {
	return function transformValue(value) {
		const {errorMessage, expression, parameter} = value;
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
			errorMessage:
				errorMessage[editingLanguageId] ||
				errorMessage[defaultLanguageId],
			expression,
			parameter:
				parameter[editingLanguageId] || parameter[defaultLanguageId],
			parameterMessage,
			selectedValidation,
		};
	};
};

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
	initialValidations,
	validation,
	value,
}) => {
	const dataType =
		validation && validation.dataType
			? validation.dataType
			: initialDataType;
	const validations = transformValidations(initialValidations, dataType);
	const parsedValidation = getValidation(
		defaultLanguageId,
		editingLanguageId,
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
