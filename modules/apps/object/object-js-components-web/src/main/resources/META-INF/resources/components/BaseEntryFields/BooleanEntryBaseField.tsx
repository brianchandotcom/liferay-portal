/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {SingleSelect} from '../Select/SingleSelect';

interface BooleanEntryBaseFieldProps {
	error?: string;
	label: string;
	onChange: (selected: string | undefined) => void;
	placeholder?: string;
	required?: boolean;
	selectedBooleanItem: string;
}

const BOOLEAN_ITEMS = [
	{label: Liferay.Language.get('true'), value: 'true'},
	{label: Liferay.Language.get('false'), value: 'false'},
];

export function BooleanEntryBaseField({
	error,
	label,
	onChange,
	placeholder,
	required,
	selectedBooleanItem,
}: BooleanEntryBaseFieldProps) {
	return (
		<SingleSelect
			error={error}
			items={BOOLEAN_ITEMS}
			label={label}
			onSelectionChange={(value) => {
				const newValue = value === undefined ? '' : value;
				onChange(newValue as string);
			}}
			placeholder={placeholder}
			required={required}
			selectedKey={selectedBooleanItem}
		/>
	);
}
