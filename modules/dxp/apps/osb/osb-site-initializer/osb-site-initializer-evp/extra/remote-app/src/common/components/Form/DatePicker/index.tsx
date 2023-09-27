/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDatePicker from '@clayui/date-picker';
import dayjs from 'dayjs';
import {InputHTMLAttributes, useEffect, useState} from 'react';
import {UseFormRegister} from 'react-hook-form';

import {generateReportsType} from '../../../../routes/reports/generateReport';
import BaseWrapper from '../Base/BaseWrapper';

type DatePickerTypes = {
	clearErrors?: any;
	errors?: any;
	id?: string;
	label?: string;
	name: string;
	register?: UseFormRegister<generateReportsType>;
	required?: boolean;
	setValue?: any;
	type?: string;
	value: string;
} & InputHTMLAttributes<HTMLInputElement>;

const DatePicker: React.FC<DatePickerTypes> = ({
	clearErrors = {},
	errors = {},
	label,
	name,
	id = name,
	required = false,
	setValue = () => {},
	value,
}) => {
	const [data, setData] = useState(value);

	useEffect(() => {
		clearErrors(name);
		setValue(name, data);
	}, [clearErrors, data, name, setValue]);

	return (
		<BaseWrapper
			error={errors[name]?.message}
			id={id}
			label={label}
			required={required}
		>
			<ClayDatePicker
				onChange={setData}
				placeholder="YYYY-MM-DD"
				value={data}
				years={{
					end: dayjs().year(),
					start: 1997,
				}}
			/>
		</BaseWrapper>
	);
};

export default DatePicker;
