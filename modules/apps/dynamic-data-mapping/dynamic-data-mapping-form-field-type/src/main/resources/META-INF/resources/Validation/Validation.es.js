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
import {usePrevious} from '@liferay/frontend-js-react-web';
import {useFormState} from 'data-engine-js-components-web';
import React, {useEffect, useState} from 'react';

import Checkbox from '../Checkbox/Checkbox.es';
import Numeric from '../Numeric/Numeric.es';
import Select from '../Select/Select.es';
import Text from '../Text/Text.es';
import {subWords} from '../util/strings.es';
import {getSelectedValidation, transformData} from './transform.es';

const Validation = ({
	dataType,
	defaultLanguageId,
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
						name: validation?.fieldName,
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

	const prevEditingLanguageId = usePrevious(editingLanguageId);

	useEffect(() => {
		if (prevEditingLanguageId !== editingLanguageId) {
			setState((prevState) => {
				const {errorMessage = {}, parameter = {}} = value;

				return {
					...prevState,
					errorMessage:
						errorMessage[editingLanguageId] !== undefined
							? errorMessage[editingLanguageId]
							: errorMessage[defaultLanguageId],
					parameter:
						parameter[editingLanguageId] !== undefined
							? parameter[editingLanguageId]
							: parameter[defaultLanguageId],
				};
			});
		}
	}, [defaultLanguageId, editingLanguageId, prevEditingLanguageId, value]);

	return (
		<ClayForm.Group className="lfr-ddm-form-field-validation">
			<Checkbox
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
						disableEmptyOption
						label={Liferay.Language.get('accept-if-input')}
						name="selectedValidation"
						onChange={(event, value) =>
							handleChange(
								'selectedValidation',
								transformSelectedValidation(value)
							)
						}
						options={validations}
						placeholder={Liferay.Language.get('choose-an-option')}
						readOnly={readOnly || localizationMode}
						showEmptyOption={false}
						spritemap={spritemap}
						value={[selectedValidation.name]}
						visible={visible}
					/>
					{selectedValidation.parameterMessage && (
						<DynamicComponent
							dataType={dataType}
							label={Liferay.Language.get('value')}
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
						label={Liferay.Language.get('error-message')}
						name={`${name}_errorMessage`}
						onChange={(event) =>
							handleChange('errorMessage', event.target.value)
						}
						placeholder={Liferay.Language.get('error-message')}
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

const Main = ({
	dataType: initialDataType,
	defaultLanguageId,
	editingLanguageId,
	ffCustomDDMValidationEnabled,
	label,
	name,
	onChange,
	readOnly,
	spritemap,
	validation,
	value = {},
	visible,
}) => {
	const {validations} = useFormState();
	const data = transformData({
		defaultLanguageId,
		editingLanguageId,
		ffCustomDDMValidationEnabled,
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
			onChange={(value) => onChange({}, value)}
			readOnly={readOnly}
			spritemap={spritemap}
			validation={validation}
			value={value}
			visible={visible}
		/>
	);
};

export default Main;
