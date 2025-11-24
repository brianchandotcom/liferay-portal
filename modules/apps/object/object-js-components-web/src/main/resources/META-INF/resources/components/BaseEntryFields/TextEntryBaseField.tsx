/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {Input} from '../Input';

interface TextEntryBaseFieldProps {
	component?: 'input' | 'textarea';
	error?: string;
	label: string;
	onChange: React.ChangeEventHandler<HTMLInputElement>;
	placeholder?: string;
	required?: boolean;
	value: string;
}

export function TextEntryBaseField({
	component,
	error,
	label,
	onChange,
	placeholder,
	required,
	value,
}: TextEntryBaseFieldProps) {
	return (
		<Input
			component={component}
			error={error}
			label={label}
			onChange={onChange}
			placeholder={placeholder}
			required={required}
			type="text"
			value={value}
		/>
	);
}
