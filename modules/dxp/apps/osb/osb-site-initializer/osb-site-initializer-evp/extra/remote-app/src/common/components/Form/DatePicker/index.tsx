/* eslint-disable no-console */
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
import {InputHTMLAttributes} from 'react';

import BaseWrapper from '../Base/BaseWrapper';

type DatePickerTypes = {
	errors?: any;
	id?: string;
	label?: string;
	name: string;
	register?: any;
	required?: boolean;
	type?: string;
} & InputHTMLAttributes<HTMLInputElement>;

const DatePicker: React.FC<DatePickerTypes> = ({
	errors = {},
	label,
	name,
	register = () => {},
	id = name,
	value,
	required = false,
	...otherProps
}) => {
	return (
		<BaseWrapper
			error={errors[name]?.message}
			id={id}
			label={label}
			required={required}
		>
			<ClayDatePicker
				value={value}
				years={{
					end: 2024,
					start: 1997,
				}}
				{...otherProps}
				{...register(name, {required})}
			/>
		</BaseWrapper>
	);
};

export default DatePicker;
