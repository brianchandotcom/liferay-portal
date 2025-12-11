/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TextEntryBaseField} from '@liferay/object-js-components-web';
import {debounce} from 'frontend-js-web';
import React, {useMemo, useState} from 'react';

import {getUpdatedDefaultValueFieldSettings} from '../../../utils/defaultValues';
import {InputAsValueFieldComponentProps} from '../Tabs/Advanced/DefaultValueContainer';

const TextDefaultValueInput: React.FC<
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

	const isLongText = values.businessType === 'LongText';

	const debouncedSave = useMemo(
		() =>
			debounce((nextValue: string) => {
				const newSettings = getUpdatedDefaultValueFieldSettings(
					values,
					nextValue,
					'inputAsValue'
				);

				setValues({objectFieldSettings: newSettings});

				onSubmit?.({
					...values,
					objectFieldSettings: newSettings,
				});
			}, 300),
		[onSubmit, setValues, values]
	);

	const save = (nextValue: string) => {
		const newSettings = getUpdatedDefaultValueFieldSettings(
			values,
			nextValue,
			'inputAsValue'
		);

		setValues({objectFieldSettings: newSettings});
	};

	const handleChangeInput = (event: any) => {
		const newValue = event.target.value;

		setValue(newValue);

		if (onSubmit) {
			debouncedSave(newValue);
		}
		else {
			save(newValue);
		}
	};

	return (
		<TextEntryBaseField
			component={isLongText ? 'textarea' : 'input'}
			error={error}
			id={id}
			label={label}
			onChange={handleChangeInput}
			placeholder={
				isLongText
					? undefined
					: Liferay.Language.get('enter-a-default-value')
			}
			required={required}
			value={value}
		/>
	);
};

export default TextDefaultValueInput;
