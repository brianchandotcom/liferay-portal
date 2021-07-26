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

import React, {useState} from 'react';

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

	return localizedValue?.[selectedParameterName]?.type;
};

const endsOnLabel = Liferay.Language.get('ends-on');
const responseDateLabel = Liferay.Language.get('response-date');
const startsFromLabel = Liferay.Language.get('starts-from');

const getResponseDateOption = (label, name) => ({
	label,
	name,
	options: [
		{
			checked: false,
			label: responseDateLabel,
			name: 'responseDate',
			value: 'responseDate',
		},
	],
});

const parameters = {
	dateRange: [
		getResponseDateOption(startsFromLabel, 'startsFrom'),
		getResponseDateOption(endsOnLabel, 'endsOn'),
	],
	futureDates: [getResponseDateOption(startsFromLabel, 'startsFrom')],
	pastDates: [getResponseDateOption(endsOnLabel, 'endsOn')],
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
	const [startsFrom, setStartsFrom] = useState(
		getSelectedParameter(localizedValue(parameter), 'startsFrom')
	);
	const [endsOn, setEndsOn] = useState(
		getSelectedParameter(localizedValue(parameter), 'endsOn')
	);
	const handleChange = (value, typeName) => {
		const parameter = {};

		if (startsFrom) {
			parameter['startsFrom'] = {
				type: startsFrom,
			};
		}

		if (endsOn) {
			parameter['endsOn'] = {
				type: endsOn,
			};
		}

		parameter[typeName] = {type: value};

		dispatch({payload: {parameter}, type: EVENT_TYPES.SET_PARAMETER});
	};

	return (
		<>
			<Select
				disableEmptyOption
				label={Liferay.Language.get('accepted-date')}
				name="selectedValidation"
				onChange={(event, value) => {
					setStartsFrom('');
					setEndsOn('');
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
			{selectedParameter.map((element, index) => {
				let label = '';
				if (selectedParameter.length > 1) {
					label =
						element.name === 'startsFrom'
							? Liferay.Language.get('start-date')
							: Liferay.Language.get('end-date');
				}

				return (
					<>
						{label !== '' && (
							<>
								<label>{label.toUpperCase()}</label>
								<div className="separator" />
							</>
						)}

						<Select
							disableEmptyOption
							key={`selectedParameter_${index}`}
							label={element.label}
							name="selectedParameter"
							onChange={(event, value) => {
								if (element.name === 'startsFrom') {
									setStartsFrom(value[0]);
								}
								else {
									setEndsOn(value[0]);
								}
								handleChange(value[0], element.name);
							}}
							options={selectedParameter[index].options}
							placeholder={Liferay.Language.get(
								'choose-an-option'
							)}
							readOnly={localizationMode || readOnly}
							showEmptyOption={false}
							value={
								element.name === 'startsFrom'
									? startsFrom
									: endsOn
							}
							visible={visible}
						/>
					</>
				);
			})}
			{selectedValidation.name !== 'dateRange' && (
				<Text
					key="errorMessage"
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
			)}
		</>
	);
};

export default ValidationDate;
