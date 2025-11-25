/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {RichTextEntryBaseField} from '@liferay/object-js-components-web';
import React, {useState} from 'react';

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

	const handleChangeInput = (event: any, editor: any) => {
		const newValue = editor.getData();

		const newObjectFieldSettings = getUpdatedDefaultValueFieldSettings(
			values,
			newValue,
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

		setValue(newValue);
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
