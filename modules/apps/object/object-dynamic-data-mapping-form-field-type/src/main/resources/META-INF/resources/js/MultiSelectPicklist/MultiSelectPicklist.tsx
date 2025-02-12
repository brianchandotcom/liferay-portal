/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	AvailableLocale,
	EditingLocale,
	LocalizedValue,
	MultipleSelection,
	ReactFieldBase as FieldBase,
} from 'dynamic-data-mapping-form-field-type';
import React, {useEffect, useRef} from 'react';

interface MultiSelectOption {
	label: string;
	reference: string | null;
	value: string;
}

type Values = string[] | LocalizedValue<string[]>;

interface MultiSelectPicklistProps {
	availableLocales: AvailableLocale[];
	defaultLanguageId: Liferay.Language.Locale;
	defaultLocale: EditingLocale;
	errorMessage: string;
	fieldName: string;
	id: string;
	label: string;
	localizedObjectField?: boolean;
	localizedValue?: Liferay.Language.FullyLocalizedValue<string> | {};
	name: string;
	onChange: Function;
	options: MultiSelectOption[];
	placeholder?: string;
	readOnly: boolean;
	required: boolean;
	tip?: string;
	value: Values;
}

const normalizeValues = (value: Values | '') => {
	if (value === '') {
		return [];
	}
	else if (typeof value === 'string') {
		return JSON.parse(value);
	}

	return value;
};

export default function MultiSelectPicklist({
	errorMessage,
	label,
	localizedObjectField,
	localizedValue = {},
	name,
	onChange,
	id,
	options = [],
	placeholder = Liferay.Language.get('choose-an-option'),
	readOnly = false,
	required,
	tip,
	value,
	...otherProps
}: MultiSelectPicklistProps) {
	const normalizedValue = normalizeValues(value);

	const onChangeRef = useRef(onChange);

	useEffect(() => {
		onChangeRef.current = onChange;
	}, [onChange]);

	useEffect(() => {
		onChangeRef.current({target: {value: normalizedValue}});
	}, [normalizedValue]);

	return (
		<FieldBase
			errorMessage={errorMessage}
			label={label}
			localizedValue={localizedValue}
			name={name}
			readOnly={readOnly}
			required={required}
			tip={tip}
			{...otherProps}
		>
			<MultipleSelection
				{...otherProps}
				errorMessage={errorMessage}
				id={id}
				label={label}
				localizedObjectField={localizedObjectField}
				name={name}
				onChange={onChange}
				options={options}
				placeholder={placeholder}
				readOnly={readOnly}
				required={required}
				tip={tip}
				value={normalizeValues(value)}
			/>
		</FieldBase>
	);
}
