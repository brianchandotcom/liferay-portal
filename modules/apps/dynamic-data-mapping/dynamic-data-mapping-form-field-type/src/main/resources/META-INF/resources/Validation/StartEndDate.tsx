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

import CustomDate from './CustomDate';
import SelectDateType from './SelectDateType';

import './StartEndDate.scss';

const StartEndDate: React.FC<IProps> = ({
	dateFieldOptions,
	eventType,
	label,
	name,
	onChange,
	options,
	parameters,
	readOnly,
	tooltip,
	visible,
}) => {
	const handleChange = (properties: IParametersProperties) => {
		onChange(eventType, {
			...parameters,
			...properties,
		});
	};

	return (
		<>
			<SelectDateType
				dateFieldName={parameters.dateFieldName}
				dateFieldOptions={dateFieldOptions}
				label={label}
				onChange={(value, dateFieldName) =>
					handleChange({dateFieldName, type: value})
				}
				options={options}
				tooltip={tooltip}
				type={parameters.type}
			/>

			{parameters?.type === 'customDate' && (
				<CustomDate
					dateFieldOptions={dateFieldOptions}
					eventType={eventType}
					name={name}
					onChange={handleChange}
					options={options}
					parameters={parameters}
					readOnly={readOnly}
					visible={visible}
				/>
			)}
		</>
	);
};

export default StartEndDate;

interface IProps {
	dateFieldOptions: IDateFieldOption[];
	eventType: EventType;
	label: string;
	name: string;
	onChange: (eventType: EventType, parameters: IParameters) => void;
	options: IOptions[];
	parameters: IParameters;
	readOnly?: boolean;
	tooltip: string;
	visible: boolean;
}

interface IDateFieldOption {
	label: string;
	name: string;
}

interface IOptions {
	label: string;
	name: 'customDate' | 'responseDate';
	value: 'customDate' | 'responseDate';
}

type EventType = 'startsFrom' | 'endsOn';
