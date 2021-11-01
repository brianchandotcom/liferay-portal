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

import {ClayInput, ClaySelectWithOption} from '@clayui/form';
import {PagesVisitor, useFormState} from 'data-engine-js-components-web';
import React, {useMemo, useState} from 'react';

import Select from '../Select/Select.es';
import StartEndDate from './StartEndDate';

import './ValidationDate.scss';
import {EVENT_TYPES} from './validationReducer';

const customDateLabel = Liferay.Language.get('custom-date');
const endsOnLabel = Liferay.Language.get('ends-on');
const responseDateLabel = Liferay.Language.get('response-date');
const startsFromLabel = Liferay.Language.get('starts-from');

const responseDateOption = {
	checked: false,
	label: responseDateLabel,
	name: 'responseDate',
	value: 'responseDate',
};

const getDateOptionsByType = (label, name) => ({
	label,
	name,
	options: [
		responseDateOption,
		{
			checked: false,
			label: customDateLabel,
			name: 'customDate',
			value: 'customDate',
		},
	],
});

const getOperation = (quantity) => {
	return quantity < 0 ? 'minus' : 'plus';
};

const getSelectedParameter = (
	value,
	selectedParameterName,
) => {
	if (value && typeof value === 'string') {
		try {
			value = JSON.parse(value);
		}
		catch (error) {}
	}

	return value?.[selectedParameterName];
};

const getSignedValue = (operation, value) => {
	let signedValue = value;

	if (
		(operation === 'minus' && value > 0) ||
		(operation === 'plus' && value < 0)
	) {
		signedValue = value * -1;
	}

	return signedValue;
};

const parameters = {
	dateRange: [
		getDateOptionsByType(startsFromLabel, 'startsFrom'),
		getDateOptionsByType(endsOnLabel, 'endsOn'),
	],
	futureDates: [getDateOptionsByType(startsFromLabel, 'startsFrom')],
	pastDates: [getDateOptionsByType(endsOnLabel, 'endsOn')],
};

const ValidationDate = ({
	dispatch,
	errorMessage,
	localizationMode,
	localizedValue,
	name,
	onBlur,
	parameter,
	parentFieldName,
	readOnly,
	selectedValidation,
	transformSelectedValidation,
	validations,
	visible,
}) => {

	const startDate = getSelectedParameter(
		localizedValue(parameter),
		'startsFrom',
	);
	const endDate = getSelectedParameter(
		localizedValue(parameter),
		'endsOn',
	);

	const selectedParameter = parameters[selectedValidation.name];
	
	const handleChangeParameters = (typeName, parameters) => {

		const parameter = {}

		if (typeName === 'startsFrom') {
			if(selectedValidation.name === 'dateRange') {
				parameter.endsOn = endDate;
			}
			parameter.startsFrom = parameters
			
		} else if (typeName === 'endsOn') {
			if(selectedValidation.name === 'dateRange') {
				parameter.startsFrom = startDate;
			}
			parameter.endsOn = parameters
		}

		dispatch({payload: {parameter}, type: EVENT_TYPES.SET_PARAMETER});
	};

	const errorMessageName = name + '_errorMessage';
	const getDateTypeValue = (name) => {
		return name === 'startsFrom' ? startsFrom : endsOn;
	};

	const {builderPages} = useFormState();

	
	const fields = useMemo(() => {
		const fields = [];
		const visitor = new PagesVisitor(builderPages);

		visitor.visitFields((field) => {
			if (
				!field.repeatable &&
				field.type === 'date' &&
				field.fieldName !== parentFieldName
			) {
				fields.push({
					checked: false,
					label: field.label,
					name: field.fieldName,
					value: field.fieldName,
				});
			}
		});

		return fields;
	}, [builderPages]);

	return (
		<>
			<label>
				{Liferay.Language.get('accepted-date')}
				<ClaySelectWithOption 
					disabled={readOnly || localizationMode}
					name="selectedValidation"
					onChange={({target: {value}}) => {
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
					value={selectedValidation.name}		
				/>
			</label>

			{selectedParameter.map(({label, name: eventType, options}, index) => {

				const {title, tooltip, parameters} = eventType === 'startsFrom' ? {
					title: Liferay.Language.get('start-date'),
					tooltip: Liferay.Language.get(
						'starts-from-tooltip'
				  ),
				  	parameters: startDate
				} : {
					title: Liferay.Language.get('end-date'),
					tooltip: Liferay.Language.get('ends-on-tooltip'),
					parameters: endDate
				}

				return (
					<React.Fragment key={index}>
						{selectedParameter.length > 1 && (
							<>
								<label>{title.toUpperCase()}</label>
								<div className="separator" />
							</>
						)}

						<StartEndDate
							dateFieldOptions={fields}
							eventType={eventType}
							label={label}
							name={name}
							onChange={handleChangeParameters}
							options={options}
							parameters={parameters}
							readOnly={localizationMode || readOnly }
							tooltip={tooltip}
							visible={visible}
						/>
					</React.Fragment>
				);
			})}
			<label htmlFor={errorMessageName}>
				{Liferay.Language.get('error-message')}
			</label>
			<ClayInput
				id={errorMessageName}
				name={errorMessageName}
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
				type="text"
				value={localizedValue(errorMessage)}
			/>
		</>
	);
};

export default ValidationDate;
