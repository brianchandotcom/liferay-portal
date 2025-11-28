/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {DatePicker} from '../DatePicker';

interface DateEntryBaseFieldProps {
	error?: string;
	id?: string;
	label: string;
	locale?: Liferay.Language.Locale;
	onChange: (value: string) => void;
	placeholder?: string;
	required?: boolean;
	type: 'date' | 'date_time';
	value: string;
}

export function DateEntryBaseField({
	error,
	id,
	label,
	locale = Liferay.ThemeDisplay.getDefaultLanguageId(),
	onChange,
	placeholder,
	required,
	type,
	value,
}: DateEntryBaseFieldProps) {
	return (
		<DatePicker
			error={error}
			id={id}
			label={label}
			locale={locale}
			onChange={onChange}
			placeholder={placeholder}
			required={required}
			type={type}
			value={value}
		/>
	);
}
