/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DateEntryBaseField} from '@liferay/object-js-components-web';
import React, {useState} from 'react';

import {getUpdatedDefaultValueFieldSettings} from '../../../utils/defaultValues';
import {InputAsValueFieldComponentProps} from '../Tabs/Advanced/DefaultValueContainer';

const DateDefaultValueInput: React.FC<
	{children?: React.ReactNode | undefined} & InputAsValueFieldComponentProps
> = ({
	defaultValue,
	error,
	id,
	label,
	onSubmit,
	required,
	setValues,
	values,
}: InputAsValueFieldComponentProps) => {
	const initialValue = typeof defaultValue === 'string' ? defaultValue : '';
	const [value, setValue] = useState(initialValue);

	const fieldType = values.businessType === 'DateTime' ? 'date_time' : 'date';

	const handleChangeInput = (value: string) => {
		const newObjectFieldSettings = getUpdatedDefaultValueFieldSettings(
			values,
			value,
			'inputAsValue'
		);

		setValues({
			objectFieldSettings: newObjectFieldSettings,
		});

		if (onSubmit) {
			onSubmit({
				...values,
				objectFieldSettings: newObjectFieldSettings,
			});
		}

		setValue(value);
	};

	return (
		<DateEntryBaseField
			error={error}
			id={id}
			label={label}
			onChange={handleChangeInput}
			required={required}
			type={fieldType}
			value={value}
		/>
	);
};

export default DateDefaultValueInput;
