/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {Input} from '../Input';

interface NumericEntryBaseFieldProps {
	error?: string;
	id?: string;
	label: string;
	onChange: React.ChangeEventHandler<HTMLInputElement>;
	placeholder?: string;
	required?: boolean;
	value: string;
}

export function NumericEntryBaseField({
	error,
	id,
	label,
	onChange,
	placeholder,
	required,
	value,
}: NumericEntryBaseFieldProps) {
	return (
		<Input
			component="input"
			error={error}
			id={id}
			label={label}
			onChange={onChange}
			placeholder={placeholder}
			required={required}
			type="text"
			value={value}
		/>
	);
}
