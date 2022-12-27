/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayDatePicker from '@clayui/date-picker';
import {InputHTMLAttributes, useEffect, useState} from 'react';

import BaseWrapper from '../Base/BaseWrapper';

type DatePickerTypes = {
	errors?: any;
	id?: string;
	label?: string;
	name: string;
	register?: any;
	required?: boolean;
	setValue?: any;
	type?: string;
} & InputHTMLAttributes<HTMLInputElement>;

const DatePicker: React.FC<DatePickerTypes> = ({
	errors = {},
	label,
	name,
	id = name,
	required = false,
	setValue = () => {},
}) => {
	const [data, setData] = useState('');

	useEffect(() => {
		setValue(name, data);
	}, [data, name, setValue]);

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
					end: 2024,
					start: 1997,
				}}
			/>
		</BaseWrapper>
	);
};

export default DatePicker;
