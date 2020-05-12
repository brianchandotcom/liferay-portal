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
import React, {useState} from 'react';

import {Main as Checkbox} from '../Checkbox/Checkbox.es';
import {Main as Numeric} from '../Numeric/Numeric.es';
import {Main as Select} from '../Select/Select.es';
import {Main as Text} from '../Text/Text.es';
import getConnectedReactComponentAdapter from '../util/ReactComponentAdapter.es';
import {connectStore} from '../util/connectStore.es';
import {subWords} from '../util/strings.es';
import {getSelectedValidation, transformData} from './transform.es';

const Validation = ({
	dataType,
	editingLanguageId,
	enableValidation: initialEnableValidation,
	errorMessage: initialErrorMessage,
	label,
	localizationMode,
	name,
	onChange,
	parameter: initialParameter,
	parameterMessage,
	readOnly,
	selectedValidation: initialSelectedValidation,
	spritemap,
	validation,
	validations,
	value,
	visible,
	...otherProps
}) => {
	const [
		{enableValidation, errorMessage, parameter, selectedValidation},
		setState,
	] = useState({
		enableValidation: initialEnableValidation,
		errorMessage: initialErrorMessage,
		parameter: initialParameter,
		selectedValidation: initialSelectedValidation,
	});

	const DynamicComponent =
		selectedValidation &&
		selectedValidation.parameterMessage &&
		dataType === 'string'
			? Text
			: Numeric;

	const handleChange = (key, newValue) => {
		setState((prevState) => {
			const newState = {
				...prevState,
				[key]: newValue,
			};

			let expression = {};

			if (newState.enableValidation) {
				expression = {
					name: newState.selectedValidation.name,
					value: subWords(newState.selectedValidation.template, {
						name: validation.fieldName,
					}),
				};
			}

			onChange({
				enableValidation: newState.enableValidation,
				errorMessage: {
					...value.errorMessage,
					[editingLanguageId]: newState.errorMessage,
				},
				expression,
				parameter: {
					...value.parameter,
					[editingLanguageId]: !value.expression
						? parameterMessage
						: newState.parameter,
				},
			});

			return newState;
		});
	};

	const transformSelectedValidation = getSelectedValidation(validations);

	return (
		<ClayForm.Group className="lfr-ddm-form-field-validation">
			<Checkbox
				{...otherProps}
				disabled={readOnly}
				label={label}
				name="enableValidation"
				onChange={(event, value) =>
					handleChange('enableValidation', value)
				}
				showAsSwitcher
				spritemap={spritemap}
				value={enableValidation}
				visible={visible}
			/>

			{enableValidation && (
				<>
					<Select
						{...otherProps}
						disableEmptyOption
						label={Liferay.Language.get('if-input')}
						name="selectedValidation"
						onChange={({value}) =>
							handleChange(
								'selectedValidation',
								transformSelectedValidation(value)
							)
						}
						options={validations}
						placeholder={Liferay.Language.get('choose-an-option')}
						readOnly={readOnly || localizationMode}
						spritemap={spritemap}
						value={[selectedValidation.name]}
						visible={visible}
					/>
					{selectedValidation.parameterMessage && (
						<DynamicComponent
							{...otherProps}
							dataType={dataType}
							label={Liferay.Language.get('the-value')}
							name={`${name}_parameter`}
							onChange={(event) =>
								handleChange('parameter', event.target.value)
							}
							placeholder={selectedValidation.parameterMessage}
							readOnly={readOnly}
							required={false}
							spritemap={spritemap}
							value={parameter}
							visible={visible}
						/>
					)}
					<Text
						{...otherProps}
						label={Liferay.Language.get('show-error-message')}
						name={`${name}_errorMessage`}
						onChange={(event) =>
							handleChange('errorMessage', event.target.value)
						}
						placeholder={Liferay.Language.get('show-error-message')}
						readOnly={readOnly}
						required={false}
						spritemap={spritemap}
						value={errorMessage}
						visible={visible}
					/>
				</>
			)}
		</ClayForm.Group>
	);
};

const ValidationProxy = connectStore(
	({
		emit,
		dataType: initialDataType,
		defaultLanguageId,
		dispatch,
		editingLanguageId,
		label,
		name,
		readOnly,
		spritemap,
		store,
		validation,
		validations: initialValidations,
		value = {},
		visible,
	}) => {
		const data = transformData({
			defaultLanguageId,
			editingLanguageId,
			initialDataType,
			initialValidations,
			validation,
			value,
		});

		return (
			<Validation
				{...data}
				dispatch={dispatch}
				editingLanguageId={editingLanguageId}
				label={label}
				name={name}
				onChange={(value) => emit('fieldEdited', {}, value)}
				readOnly={readOnly}
				spritemap={spritemap}
				store={store}
				validation={validation}
				value={value}
				visible={visible}
			/>
		);
	}
);

const ReactValidationAdapter = getConnectedReactComponentAdapter(
	ValidationProxy,
	'validation'
);

export {ReactValidationAdapter};
export default ReactValidationAdapter;
