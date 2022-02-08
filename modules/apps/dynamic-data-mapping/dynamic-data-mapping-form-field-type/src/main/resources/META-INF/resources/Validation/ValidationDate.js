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

import {ClayInput} from '@clayui/form';
import {PagesVisitor, useFormState} from 'data-engine-js-components-web';
import React, {useMemo} from 'react';

import DDMSelect from './DDMSelect';
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

/* TODO: enforce parameter type consistency and remove this function */
function getFromParameter(parameter, key, getLocalizedValue) {
	let value = getLocalizedValue(parameter) ?? parameter;

	if (value && typeof value === 'string') {
		try {
			value = JSON.parse(value);
		}
		catch (error) {}
	}

	return value?.[key];
}

const parameters = {
	dateRange: [
		getDateOptionsByType(startsFromLabel, 'startsFrom'),
		getDateOptionsByType(endsOnLabel, 'endsOn'),
	],
	futureDates: [getDateOptionsByType(startsFromLabel, 'startsFrom')],
	pastDates: [getDateOptionsByType(endsOnLabel, 'endsOn')],
};

export default function ValidationDate({
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
}) {
	const startDate = getFromParameter(parameter, 'startsFrom', localizedValue);
	const endDate = getFromParameter(parameter, 'endsOn', localizedValue);

	const selectedParameter = parameters[selectedValidation.name];

	const handleChangeParameters = (typeName, parameters) => {
		const parameter = {};

		if (typeName === 'startsFrom') {
			parameter.startsFrom = parameters;
			parameter.endsOn = endDate;
		}
		else if (typeName === 'endsOn') {
			parameter.endsOn = parameters;
			parameter.startsFrom = startDate;
		}

		dispatch({payload: {parameter}, type: EVENT_TYPES.SET_PARAMETER});
	};

	const errorMessageName = name + '_errorMessage';

	const {formBuilder} = useFormState();

	const fields = useMemo(() => {
		const fields = [];

		const visitor = new PagesVisitor(formBuilder.pages);

		visitor.mapFields(
			(field, _pageIndex, _rowIndex, _columnIndex, ...parentFields) => {
				if (
					field.repeatable ||
					field.type !== 'date' ||
					field.fieldName === parentFieldName ||
					parentFields.some(({repeatable}) => repeatable)
				) {
					return;
				}

				fields.push({
					checked: false,
					label: field.label,
					name: field.fieldName,
					value: field.fieldName,
				});
			},
			true,
			true
		);

		return fields;
	}, [formBuilder.pages, parentFieldName]);

	return (
		<>
			<div className="ddm-form-field-type__validation-date-accepted-date">
				<DDMSelect
					className="lfr-ddm__validation-date-select"
					disabled={readOnly || localizationMode}
					label={Liferay.Language.get('accepted-date')}
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
			</div>

			{selectedParameter.map(
				({label, name: eventType, options}, index) => {
					const {parameters, title, tooltip} =
						eventType === 'startsFrom'
							? {
									parameters: startDate,
									title: Liferay.Language.get('start-date'),
									tooltip: Liferay.Language.get(
										'starts-from-tooltip'
									),
							  }
							: {
									parameters: endDate,
									title: Liferay.Language.get('end-date'),
									tooltip: Liferay.Language.get(
										'ends-on-tooltip'
									),
							  };

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
								readOnly={localizationMode || readOnly}
								tooltip={tooltip}
								visible={visible}
							/>
						</React.Fragment>
					);
				}
			)}
			<label
				className="lfr-ddm__validation-date-error-message"
				htmlFor={errorMessageName}
			>
				{Liferay.Language.get('error-message')}

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
			</label>
		</>
	);
}
