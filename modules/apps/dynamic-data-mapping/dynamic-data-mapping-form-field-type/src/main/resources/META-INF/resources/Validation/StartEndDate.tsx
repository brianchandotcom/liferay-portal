/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
