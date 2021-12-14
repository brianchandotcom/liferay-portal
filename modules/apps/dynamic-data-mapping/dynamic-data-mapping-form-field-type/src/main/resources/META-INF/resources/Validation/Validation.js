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

import ClayForm from '@clayui/form';
import {useFormState} from 'data-engine-js-components-web';
import React, {useEffect, useReducer} from 'react';

import Checkbox from '../Checkbox/Checkbox';
import ValidationDate from './ValidationDate';
import ValidationTextAndNumeric from './ValidationTextAndNumeric';
import {
	getLocalizedValue,
	getSelectedValidation,
	normalizeDataType,
	transformData,
} from './transform';
import reducer, {EVENT_TYPES} from './validationReducer';

const COMPONENTS = {
	date: ValidationDate,
	numeric: ValidationTextAndNumeric,
	string: ValidationTextAndNumeric,
};

const Validation = ({
	dataType,
	defaultLanguageId,
	editingLanguageId,
	enableValidation: initialEnableValidation,
	errorMessage: initialErrorMessage,
	label,
	localizationMode,
	name,
	onBlur,
	onChange,
	parameter: initialParameter,
	readOnly,
	selectedValidation: initialSelectedValidation,
	validation,
	validations,
	visible,
}) => {
	const {focusedField} = useFormState();

	const [
		{enableValidation, errorMessage, parameter, selectedValidation},
		dispatch,
	] = useReducer(
		reducer({
			editingLanguageId,
			fieldName: validation?.fieldName ?? focusedField?.fieldName,
			onChange,
		}),
		{
			enableValidation: initialEnableValidation,
			errorMessage: initialErrorMessage,
			parameter: initialParameter,
			selectedValidation: initialSelectedValidation,
		}
	);

	const ValidationComponent = COMPONENTS[normalizeDataType(dataType)];

	const transformSelectedValidation = getSelectedValidation(validations);

	const localizedValue = getLocalizedValue({
		defaultLanguageId,
		editingLanguageId,
	});

	useEffect(() => {
		if (readOnly || !visible) {
			dispatch({
				payload: {enableValidation: false},
				type: EVENT_TYPES.ENABLE_VALIDATION,
			});
		}
	}, [readOnly, visible]);

	return (
		<ClayForm.Group className="lfr-ddm-form-field-validation">
			<Checkbox
				label={label}
				name="enableValidation"
				onChange={({target: {value: enableValidation}}) => {
					dispatch({
						payload: {
							enableValidation,
							fieldName: validation?.fieldName,
						},
						type: EVENT_TYPES.ENABLE_VALIDATION,
					});
				}}
				readOnly={readOnly}
				showAsSwitcher
				value={enableValidation}
				visible={visible}
			/>

			{enableValidation && (
				<ValidationComponent
					dataType={dataType}
					dispatch={dispatch}
					errorMessage={errorMessage}
					localizationMode={localizationMode}
					localizedValue={localizedValue}
					name={name}
					onBlur={onBlur}
					parameter={parameter}
					readOnly={readOnly}
					selectedValidation={selectedValidation}
					transformSelectedValidation={transformSelectedValidation}
					validations={validations}
					visible={visible}
				/>
			)}
		</ClayForm.Group>
	);
};

const ValidationWrapper = ({
	dataType: initialDataType,
	defaultLanguageId,
	editingLanguageId,
	label,
	name,
	onBlur,
	onChange,
	readOnly,
	validation,
	value,
	visible,
}) => {
	const {validations} = useFormState();
	const data = transformData({
		defaultLanguageId,
		editingLanguageId,
		initialDataType,
		validation,
		validations,
		value,
	});

	return (
		<Validation
			{...data}
			defaultLanguageId={defaultLanguageId}
			editingLanguageId={editingLanguageId}
			label={label}
			name={name}
			onBlur={onBlur}
			onChange={(value) => onChange({target: {value}})}
			readOnly={readOnly}
			validation={validation}
			value={value}
			visible={visible}
		/>
	);
};

export default ValidationWrapper;
