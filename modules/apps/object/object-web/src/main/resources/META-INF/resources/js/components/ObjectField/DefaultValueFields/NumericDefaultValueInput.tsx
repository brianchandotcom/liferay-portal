/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {NumericEntryBaseField} from '@liferay/object-js-components-web';
import React, {useState} from 'react';
import {createNumberMask} from 'text-mask-addons';
import {conformToMask} from 'text-mask-core';

import {getUpdatedDefaultValueFieldSettings} from '../../../utils/defaultValues';
import {InputAsValueFieldComponentProps} from '../Tabs/Advanced/DefaultValueContainer';

const NumericDefaultValueInput: React.FC<
	{children?: React.ReactNode | undefined} & InputAsValueFieldComponentProps
> = ({
	dataType,
	decimalSeparator,
	defaultValue,
	error,
	label,
	onSubmit,
	required,
	setValues,
	values,
}: InputAsValueFieldComponentProps) => {
	const initialValue = typeof defaultValue === 'string' ? defaultValue : '';
	const [value, setValue] = useState(initialValue);
	const handleChangeInput = (event: any) => {
		const config = {
			allowDecimal: dataType === 'double',
			allowLeadingZeroes: true,
			allowNegative: true,
			decimalLimit: null,
			decimalSymbol: decimalSeparator ?? '.',
			includeThousandsSeparator: false,
			prefix: '',
		};

		const mask = createNumberMask(config);

		const {conformedValue: masked} = conformToMask(
			event.target.value,
			mask,
			{
				guide: false,
				keepCharPositions: false,
				placeholderChar: '\u2000',
			}
		);

		if (masked) {
			const newObjectFieldSettings = getUpdatedDefaultValueFieldSettings(
				values,
				masked,
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
		}

		setValue(masked);
	};

	return (
		<NumericEntryBaseField
			error={error}
			label={label}
			onChange={handleChangeInput}
			placeholder={Liferay.Language.get('enter-a-default-value')}
			required={required}
			value={value}
		/>
	);
};

export default NumericDefaultValueInput;
