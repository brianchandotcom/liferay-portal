/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {RichTextEntryBaseField} from '@liferay/object-js-components-web';
import {debounce} from 'frontend-js-web';
import React, {useMemo, useState} from 'react';

import {getUpdatedDefaultValueFieldSettings} from '../../../utils/defaultValues';
import {InputAsValueFieldComponentProps} from '../Tabs/Advanced/DefaultValueContainer';

const RichTextDefaultValue: React.FC<
	{children?: React.ReactNode | undefined} & InputAsValueFieldComponentProps
> = ({
	ckEditor5Config,
	defaultValue,
	error,
	label,
	onSubmit,
	required,
	setValues,
	values,
}) => {
	const initialValue = typeof defaultValue === 'string' ? defaultValue : '';
	const [value, setValue] = useState(initialValue);

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

	const handleChangeInput = (event: any, editor: any) => {
		const newValue = editor.getData();

		setValue(newValue);

		if (onSubmit) {
			debouncedSave(newValue);
		}
		else {
			save(newValue);
		}
	};

	return (
		<RichTextEntryBaseField
			ckEditor5Config={ckEditor5Config}
			error={error}
			label={label}
			onChange={handleChangeInput}
			required={required}
			value={value}
		/>
	);
};

export default RichTextDefaultValue;
