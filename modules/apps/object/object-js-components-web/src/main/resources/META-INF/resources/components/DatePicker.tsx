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

import ClayDatePicker from '@clayui/date-picker';
import moment from 'moment';
import React, {useState} from 'react';

import {FieldBase} from './FieldBase';

interface DatePickerProps
	extends Omit<React.HTMLAttributes<HTMLInputElement>, 'onChange'> {
	className?: string;
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	id?: string;
	label?: string;
	name?: string;
	onChange: (value: string) => void;
	range?: boolean;
	required?: boolean;
	value?: string;
}

const NON_NUMERIC_REGEX = /[^0-9-]/;

export function DatePicker({
	className,
	disabled,
	error,
	feedbackMessage,
	id,
	label,
	onChange,
	range,
	required,
	value,
}: DatePickerProps) {
	const [expanded, setExpanded] = useState(false);

	return (
		<FieldBase
			className={className}
			disabled={disabled}
			errorMessage={error}
			helpMessage={feedbackMessage}
			id={id}
			label={label}
			required={required}
		>
			<ClayDatePicker
				expanded={expanded}
				onChange={(value: string) => {
					onChange(value.replace(NON_NUMERIC_REGEX, ''));
					setExpanded(false);
				}}
				onExpandedChange={() => {
					setExpanded(!expanded);
				}}
				placeholder="YYYY-MM-DD"
				range={range}
				value={value}
				years={{
					end: moment().year() + 5,
					start: moment().year() - 5,
				}}
			/>
		</FieldBase>
	);
}
