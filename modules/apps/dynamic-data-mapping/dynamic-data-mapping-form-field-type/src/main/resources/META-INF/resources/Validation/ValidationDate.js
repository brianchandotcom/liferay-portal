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

import Select from '../Select/Select.es';
import Text from '../Text/Text.es';
import {EVENT_TYPES} from './validationReducer';

const getSelectedParameter = (localizedValue, selectedParameterName) => {
	if (localizedValue && typeof localizedValue === 'string') {
		try {
			localizedValue = JSON.parse(localizedValue);
		}
		catch (error) {}
	}

	return localizedValue?.[selectedParameterName];
};

const parameters = {
	futureDates: {
		label: Liferay.Language.get('starts-from'),
		name: 'startsFrom',
		options: [
			{
				checked: false,
				label: Liferay.Language.get('response-date'),
				name: 'responseDate',
				value: 'responseDate',
			},
		],
	},
};

const ValidationDate = ({
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
	const selectedParameter = parameters[selectedValidation.name];

	return (
		<>
			<Select
				disableEmptyOption
				label={Liferay.Language.get('accepted-date')}
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

			<Select
				disableEmptyOption
				label={selectedParameter.label}
				name="selectedParameter"
				onChange={(event, value) => {
					dispatch({
						payload: {
							parameter: {
								[selectedParameter.name]: value[0],
							},
						},
						type: EVENT_TYPES.SET_PARAMETER,
					});
				}}
				options={selectedParameter.options}
				placeholder={Liferay.Language.get('choose-an-option')}
				readOnly={localizationMode || readOnly}
				showEmptyOption={false}
				value={getSelectedParameter(
					localizedValue(parameter),
					selectedParameter.name
				)}
				visible={visible}
			/>

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
				placeholder={Liferay.Language.get('error-message')}
				readOnly={readOnly}
				required={false}
				value={localizedValue(errorMessage)}
				visible={visible}
			/>
		</>
	);
};

export default ValidationDate;
