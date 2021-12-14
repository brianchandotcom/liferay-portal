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
	visible
}) => {

	const handleChange = (key: string, value: string | number, dateFieldName?: string) => {

		onChange(eventType,{
			...parameters,
			dateFieldName,
			[key]: value,
		});
	}

	return (
		<>
			<SelectDateType 
				type={parameters.type}
				dateFieldName={parameters.dateFieldName}
				dateFieldOptions={dateFieldOptions}
				options={options}
				onChange={(value, options) => handleChange('type', value, options)}
				label={label}
				tooltip={tooltip}
			/>

			{parameters?.type === 'customDate' && (
				<CustomDate
					onChange={handleChange}
					name={name}
					options={options}
					dateFieldOptions={dateFieldOptions}
					parameters={parameters}
					eventType={eventType}
					readOnly={readOnly}
					visible={visible}
				/>
			)}
		</>
	);
};

export default StartEndDate;

interface IProps {
	eventType: EventType;
	label: string;
	name: string;
	options: IOptions[];
	onChange: (eventType: EventType, parameters: IParameters) => void;
	tooltip: string;
	parameters: IParameters;
	readOnly?: boolean;
	dateFieldOptions: IDateFieldOption[];
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

