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
import React, {ChangeEvent, useEffect, useState} from 'react';

// @ts-ignore

import {limitValue} from '../util/numericalOperations';

import './CustomDate.scss';
import DDMSelect from './DDMSelect';
import SelectDateType from './SelectDateType';

const MAX_QUANTITY = 999;
const MIN_QUANTITY = 1;
const PLUS_VALUE = 'plus';
const MINUS_VALUE = 'minus';
const CUSTOM_DATE = 'customDate';

const OPERATION_OPTIONS: ISelectOptions[] = [
	{
		label: Liferay.Language.get('plus'),
		value: PLUS_VALUE,
	},
	{
		label: Liferay.Language.get('minus'),
		value: MINUS_VALUE,
	},
];

const UNIT_OPTIONS = [
	{
		label: Liferay.Language.get('days'),
		value: 'days',
	},
	{
		label: Liferay.Language.get('months'),
		value: 'months',
	},
	{
		label: Liferay.Language.get('years'),
		value: 'years',
	},
];

const CustomDate: React.FC<IProps> = ({
	dateFieldOptions,
	eventType,
	onChange,
	options,
	parameters: parametersInit,
	readOnly,
}) => {
	const [parameters, setParameters] = useState<IParameters>(parametersInit);

	useEffect(() => {
		setParameters(parametersInit);
	}, [parametersInit]);

	const handleQuantityChange: (
		event: ChangeEvent<HTMLInputElement>
	) => void = ({target: {value}}) => {
		const limit = limitValue({
			defaultValue: MIN_QUANTITY,
			max: MAX_QUANTITY,
			min: MIN_QUANTITY,
			value: parseInt(value, 10),
		});

		const quantity = parameters.quantity < 0 ? limit * -1 : limit;

		updateParameter({
			dateFieldName: parameters.dateFieldName,
			quantity,
		});
	};

	function updateParameter(properties: IParametersProperties) {
		const isChanged = Object.entries(properties).some(
			([key, value]) => parameters[key as keyof IParameters] !== value
		);

		if (isChanged) {
			setParameters((parameters) => ({
				...parameters,
				...properties,
			}));

			onChange(properties);
		}
	}

	return (
		<>
			<SelectDateType
				dateFieldName={parameters.dateFieldName}
				dateFieldOptions={dateFieldOptions}
				label={Liferay.Language.get('date')}
				onChange={(value, dateFieldName) =>
					updateParameter({date: value, dateFieldName})
				}
				options={options.filter(({name}) => name !== CUSTOM_DATE)}
				type={parameters.date}
			/>
			<div className="ddm__custom-date">
				<DDMSelect
					disabled={readOnly}
					label={Liferay.Language.get('operation')}
					name={`selectedOperation_${eventType}`}
					onChange={({target: {value}}) => {
						const currentOperation =
							parameters.quantity < 0 ? MINUS_VALUE : PLUS_VALUE;

						if (value !== currentOperation) {
							const quantity = parameters.quantity * -1;

							updateParameter({
								dateFieldName: parameters.dateFieldName,
								quantity,
							});
						}
					}}
					options={OPERATION_OPTIONS}
					value={parameters.quantity < 0 ? MINUS_VALUE : PLUS_VALUE}
				/>

				<div>
					<label>{Liferay.Language.get('quantity')}</label>

					<ClayInput
						className="ddm-field-text"
						disabled={readOnly}
						max={MAX_QUANTITY}
						min={MIN_QUANTITY}
						name={`inputedQuantity_${eventType}`}
						onChange={handleQuantityChange}
						type="number"
						value={Math.abs(parameters.quantity)}
					/>
				</div>

				<DDMSelect
					disabled={readOnly}
					label={Liferay.Language.get('unit')}
					name={`selectedUnit_${eventType}`}
					onChange={({target: {value}}) =>
						updateParameter({
							dateFieldName: parameters.dateFieldName,
							unit: value as Unit,
						})
					}
					options={UNIT_OPTIONS}
					value={parameters.unit}
				/>
			</div>
		</>
	);
};

export default CustomDate;

interface IProps {
	dateFieldOptions: IDateFieldOption[];
	eventType: 'startsFrom' | 'endsOn';
	name: string;
	onChange: (properties: IParametersProperties) => void;
	options: IOptions[];
	parameters: IParameters;
	readOnly?: boolean;
	visible: boolean;
}

interface IDateFieldOption {
	label: string;
	name: string;
}

type Unit = 'days' | 'months' | 'years';
interface IOptions {
	label: string;
	name: 'customDate' | 'responseDate';
	value: 'customDate' | 'responseDate';
}

interface ISelectOptions {
	label: string;
	value: 'minus' | 'plus';
}
