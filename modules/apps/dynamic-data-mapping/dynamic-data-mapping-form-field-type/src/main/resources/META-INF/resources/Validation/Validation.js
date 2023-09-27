/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	...otherProps
}) => {
	const {focusedField} = useFormState();

	/* TODO: Remove the focusedField variable when the fieldName is properly delivered by the validator prop */
	const parentFieldName = validation?.fieldName ?? focusedField?.fieldName;

	const [
		{enableValidation, errorMessage, parameter, selectedValidation},
		dispatch,
	] = useReducer(
		reducer({
			editingLanguageId,
			fieldName: parentFieldName,
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
					{...otherProps}
					dataType={dataType}
					dispatch={dispatch}
					errorMessage={errorMessage}
					localizationMode={localizationMode}
					localizedValue={localizedValue}
					name={name}
					onBlur={onBlur}
					parameter={parameter}
					parentFieldName={parentFieldName}
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
	...otherProps
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
			{...otherProps}
			{...data}
			defaultLanguageId={defaultLanguageId}
			editingLanguageId={editingLanguageId}
			label={label}
			name={name}
			onBlur={onBlur}
			onChange={(value) => onChange({target: {value}})}
			readOnly={readOnly}
			validation={validation}
			visible={visible}
		/>
	);
};

export default ValidationWrapper;
