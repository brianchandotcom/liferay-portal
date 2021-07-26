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

import React from 'react';

import Numeric from '../Numeric/Numeric';
import Select from '../Select/Select.es';
import Text from '../Text/Text.es';
import {EVENT_TYPES} from './validationReducer';

const ValidationTextAndNumeric = ({
	dataType,
	dispatch,
	errorMessage,
	localizationMode,
	localizedValue,
	name,
	onBlur,
	parameter,
	readOnly,
	selectedValidation,
	transformSelectedValidation,
	validations,
	visible,
}) => {
	const DynamicComponent =
		selectedValidation &&
		selectedValidation.parameterMessage &&
		dataType === 'string'
			? Text
			: Numeric;

	return (
		<>
			<Select
				disableEmptyOption
				label={Liferay.Language.get('accept-if-input')}
				name="selectedValidation"
				onChange={(event, value) => {
					dispatch({
						payload: {
							selectedValidation: transformSelectedValidation(
								value
							),
						},
						type: EVENT_TYPES.CHANGE_SELECTED_VALIDATION,
					});
				}}
				options={validations}
				placeholder={Liferay.Language.get('choose-an-option')}
				readOnly={readOnly || localizationMode}
				showEmptyOption={false}
				value={[selectedValidation.name]}
				visible={visible}
			/>

			{selectedValidation.parameterMessage && (
				<DynamicComponent
					dataType={dataType}
					label={Liferay.Language.get('value')}
					name={`${name}_parameter`}
					onChange={(event) => {
						dispatch({
							payload: {
								parameter: event.target.value,
							},
							type: EVENT_TYPES.SET_PARAMETER,
						});
					}}
					placeholder={selectedValidation.parameterMessage}
					readOnly={readOnly}
					required={false}
					value={localizedValue(parameter)}
					visible={visible}
				/>
			)}

			<Text
				label={Liferay.Language.get('error-message')}
				name={`${name}_errorMessage`}
				onBlur={onBlur}
				onChange={(event) => {
					dispatch({
						payload: {
							errorMessage: event.target.value,
						},
						type: EVENT_TYPES.CHANGE_ERROR_MESSAGE,
					});
				}}
				placeholder={Liferay.Language.get('this-field-is-invalid')}
				readOnly={readOnly}
				required={false}
				value={localizedValue(errorMessage)}
				visible={visible}
			/>
		</>
	);
};

export default ValidationTextAndNumeric;
