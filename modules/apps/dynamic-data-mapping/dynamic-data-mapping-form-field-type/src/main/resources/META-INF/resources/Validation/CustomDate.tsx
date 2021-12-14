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

import { ClayInput, ClaySelectWithOption } from '@clayui/form';
import React, { ChangeEvent, useEffect, useState } from 'react';
// @ts-ignore
import { limitValue } from '../util/numericalOperations';
import './CustomDate.scss';
import SelectDateType from './SelectDateType';

const MAX_QUANTITY = 999;
const MIN_QUANTITY = 1;
const PLUS_VALUE = 'plus'
const MINUS_VALUE = 'minus'

const operationsOptions: ISelectOptions[] = [
	{
		label: Liferay.Language.get('plus'),
		value: PLUS_VALUE,
	},
	{
		label: Liferay.Language.get('minus'),
		value: MINUS_VALUE,
	},
];

const unitOptions = [
	{
		label:  Liferay.Language.get('days'),
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

const CustomDate:React.FC<IProps> = ({
	onChange,
	options,
	dateFieldOptions,
	parameters: parametersInit,
	eventType,
	readOnly,
}) => {

	const [parameters, setParameters] = useState<IParameters>(parametersInit);

	useEffect(() => {
		setParameters(parametersInit);
	},[parametersInit]);

	const handleQuantityChange:(event: ChangeEvent<HTMLInputElement>) => void = ({target: {value}}) => {
		const limit = limitValue({
			defaultValue: MIN_QUANTITY,
			max: MAX_QUANTITY,
			min: MIN_QUANTITY,
			value: parseInt(value,10),
		});

		const quantity = parameters.quantity < 0 ? limit * -1 : limit;

		const onChange = parameterAttUpdater('quantity');

		onChange(quantity);
	}

	function parameterAttUpdater(key: keyof IParameters) {

		return (value: string | number, dateFieldName?: string) => {

		if(value !== parameters[key] || dateFieldName !== parameters.dateFieldName) {
	
			setParameters((parameters) => ({
				...parameters,
				dateFieldName,
				[key]: value
			}));

			onChange(key, value, dateFieldName);
		}
	}}

	return (
		<>
			<SelectDateType 
				type={parameters.date}
				dateFieldName={parameters.dateFieldName}
				dateFieldOptions={dateFieldOptions}
				options={options.filter(({name}) => name !== 'customDate') }
				onChange={(value, options) => {
					const onChange = parameterAttUpdater('date');

					onChange(value, options);
				}}
				label={Liferay.Language.get('date')}
			/>
			<div className="ddm__custom-date">
					<label>
						{Liferay.Language.get('operation')}
						<ClaySelectWithOption
							disabled={readOnly}
							name={`selectedOperation_${eventType}`}
							onChange={({target: {value}}) => {
								
								const currentOperation = parameters.quantity < 0  ? MINUS_VALUE : PLUS_VALUE
								
								if(value !== currentOperation) {
									const quantity = parameters.quantity * -1

									const onChange = parameterAttUpdater('quantity');
									onChange(quantity);
								}
							}}
							options={operationsOptions}
							value={parameters.quantity < 0  ? MINUS_VALUE : PLUS_VALUE}					
						/>
					</label>

					<label>
						{Liferay.Language.get('quantity')}
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
					</label>

					<label>
						{Liferay.Language.get('unit')}
						<ClaySelectWithOption
							disabled={readOnly}
							name={`selectedUnit_${eventType}`}
							onChange={({target: {value}}) => {
								const onChange = parameterAttUpdater('unit');
								onChange(value);
							}}
							options={unitOptions}
							value={parameters.unit}
						/>
					</label>

			</div>
		</>
	);
};

export default CustomDate;

interface IProps {
    eventType: 'startsFrom' | 'endsOn';
    readOnly?: boolean;
// 	label: string;
	name: string;
	options: IOptions[];
	onChange: (key: string, value: string | number, dateFieldName?: string) => void;
// 	tooltip: string;
	parameters: IParameters;
	dateFieldOptions: IDateFieldOption[];
	visible: boolean;
}

interface IDateFieldOption {
	label: string;
	name: string;
}



type Unit = 'days' | 'months' | 'years'
interface IOptions {
	label: string;
	name: 'customDate' | 'responseDate';
	value: 'customDate' | 'responseDate';
}

interface ISelectOptions {
	label: string;
	value: 'minus' | 'plus';
}

// type DateType = 'customDate' | 'responseDate' | 'dateFieldName';
