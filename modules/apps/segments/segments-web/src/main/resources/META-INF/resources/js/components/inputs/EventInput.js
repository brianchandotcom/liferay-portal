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

import {ClaySelectWithOption} from '@clayui/form';
import {PropTypes} from 'prop-types';
import React from 'react';

import {
	DATE_OPERATORS,
	HAS_OPERATORS,
	PROPERTY_TYPES,
	RELATIONAL_OPERATORS,
	SINCE_VALUES,
	SUPPORTED_EVENT_DATE_OPERATORS,
	SUPPORTED_EVENT_OPERATORS,
	SUPPORTED_PROPERTY_TYPES,
} from '../../utils/constants';
import {getSupportedOperatorsFromEvent} from '../../utils/utils.es';
import DateTimeInput from './DateTimeInput';
import IntegerInput from './IntegerInput';
import SelectEventEntityInput from './SelectEventEntityInput.es';

const DEFAULT_SINCE_VALUE = 'last24Hours';

const SINCE_OPTIONS = Object.entries(SINCE_VALUES).map(([value, label]) => ({
	label,
	value,
}));

const SINCE_OPERATOR = {
	label: Liferay.Language.get('since'),
	value: 'since',
};

const DATE_OPERATOR_OPTIONS = SUPPORTED_EVENT_DATE_OPERATORS.map(
	({label, name}) => ({
		label,
		value: name,
	})
).concat([SINCE_OPERATOR]);

function EventInput({
	criterion = {},
	error,
	onChange,
	onInputChange,
	propertyLabel,
	renderEmptyValuesErrors,
	selectedProperty,
	value,
}) {
	const disabledInput = !!error;

	const notOperators = getSupportedOperatorsFromEvent(
		SUPPORTED_EVENT_OPERATORS,
		SUPPORTED_PROPERTY_TYPES,
		'NOT'
	);

	const notOperatorKey = criterion.operatorNot
		? HAS_OPERATORS.NOT_HAS
		: HAS_OPERATORS.HAS;

	const integerOperators = getSupportedOperatorsFromEvent(
		SUPPORTED_EVENT_OPERATORS,
		SUPPORTED_PROPERTY_TYPES,
		'INTEGER'
	);

	const integerOperatorLabel = integerOperators.find(
		(operator) => operator.name === criterion.operatorName
	)?.name;

	const isSinceOperator = Boolean(
		criterion.day?.operatorName === RELATIONAL_OPERATORS.GT &&
			criterion.day?.value in SINCE_VALUES
	);

	const onDateOperatorChange = (event) => {
		if (event.target.value === DATE_OPERATORS.EVER) {
			onChange({day: undefined});

			return;
		}

		const currentOperatorName = criterion.day?.operatorName;
		let nextOperatorName = event.target.value;
		let nextValue = criterion.day?.value;

		if (nextOperatorName === SINCE_OPERATOR.value) {
			nextOperatorName = RELATIONAL_OPERATORS.GT;
			nextValue = DEFAULT_SINCE_VALUE;
		}
		else if (isSinceOperator) {
			nextValue = undefined;
		}

		if (nextOperatorName === DATE_OPERATORS.BETWEEN) {
			nextValue = nextValue
				? {
						end: nextValue,
						start: nextValue,
				  }
				: nextValue;
		}
		else if (currentOperatorName === DATE_OPERATORS.BETWEEN) {
			nextValue = nextValue?.start || nextValue?.end || nextValue;
		}

		onChange({
			day: {
				operatorName: nextOperatorName,
				value: nextValue,
			},
		});
	};

	return (
		<div className="ml-2" style={{flexGrow: 1}}>
			<div className="align-items-center d-flex mb-2">
				<span className="mr-1 text-dark">
					{Liferay.Language.get('user')}
				</span>

				<ClaySelectWithOption
					aria-label={`${propertyLabel}: ${Liferay.Language.get(
						'select-option'
					)}`}
					className="criterion-input form-control operator-input"
					data-testid="select-has-operator"
					disabled={disabledInput}
					onChange={onInputChange('operatorNot')}
					options={notOperators.map(({label, name}) => ({
						label,
						value: name,
					}))}
					value={notOperatorKey}
				/>

				<span className="criterion-string">
					<b>{propertyLabel}</b>
				</span>

				<SelectEventEntityInput
					disabled={disabledInput}
					displayValue={criterion.assetId}
					onChange={onChange}
					propertyLabel={propertyLabel}
					renderEmptyValueErrors={renderEmptyValuesErrors}
					selectEntity={selectedProperty.selectEntity}
				/>
			</div>

			<div className="align-items-center d-flex">
				<ClaySelectWithOption
					aria-label={`${propertyLabel}: ${Liferay.Language.get(
						'select-option'
					)}`}
					className="criterion-input form-control operator-input"
					data-testid="integer-operator"
					disabled={disabledInput}
					onChange={onInputChange('operatorName')}
					options={integerOperators.map(({label, name}) => ({
						label,
						value: name,
					}))}
					value={integerOperatorLabel}
				/>

				<IntegerInput
					className="criterion-input form-control"
					data-testid="integer-number"
					disabled={disabledInput}
					onChange={onChange}
					propertyLabel={propertyLabel}
					type="number"
					value={value}
				/>

				<ClaySelectWithOption
					aria-label={`${propertyLabel}: ${Liferay.Language.get(
						'select-date-operator-option'
					)}`}
					className="criterion-input form-control"
					disabled={disabledInput}
					onChange={onDateOperatorChange}
					options={DATE_OPERATOR_OPTIONS}
					value={
						isSinceOperator
							? SINCE_OPERATOR.value
							: criterion.day?.operatorName || DATE_OPERATORS.EVER
					}
				/>

				{criterion.day ? (
					isSinceOperator ? (
						<ClaySelectWithOption
							aria-label={`${propertyLabel}: ${Liferay.Language.get(
								'select-since-value-option'
							)}`}
							className="criterion-input form-control"
							disabled={disabledInput}
							onChange={(event) =>
								onChange({
									day: {
										...criterion.day,
										value: event.target.value,
									},
								})
							}
							options={SINCE_OPTIONS}
							value={criterion.day.value || DEFAULT_SINCE_VALUE}
						/>
					) : (
						<DateTimeInput
							onChange={({value}) =>
								onChange({
									day: {...criterion.day, value},
								})
							}
							propertyLabel={propertyLabel}
							propertyType={PROPERTY_TYPES.DATE}
							range={
								criterion.day.operatorName ===
								DATE_OPERATORS.BETWEEN
							}
							value={criterion.day.value}
						/>
					)
				) : null}
			</div>
		</div>
	);
}

EventInput.propTypes = {
	criterion: PropTypes.object.isRequired,
	error: PropTypes.bool,
	onChange: PropTypes.func.isRequired,
	onInputChange: PropTypes.func.isRequired,
	renderEmptyValuesErrors: PropTypes.bool,
	selectedOperator: PropTypes.object,
	selectedProperty: PropTypes.object.isRequired,
	value: PropTypes.string,
};

export default EventInput;
