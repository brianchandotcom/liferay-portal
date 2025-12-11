/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {NumericEntryBaseField} from '@liferay/object-js-components-web';
import {debounce} from 'frontend-js-web';
import React, {useMemo, useState} from 'react';
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

	const getConformedValue = (value: string): string => {
		const {conformedValue} = conformToMask(value, mask, {
			guide: false,
			keepCharPositions: false,
			placeholderChar: '\u2000',
		});

		return conformedValue;
	};

	const initialValue = typeof defaultValue === 'string' ? defaultValue : '';

	const [value, setValue] = useState<{masked: string; raw: string}>({
		masked: getConformedValue(
			initialValue.replace('.', decimalSeparator || ',')
		),
		raw: initialValue,
	});

	const debouncedSave = useMemo(
		() =>
			debounce((masked: string) => {
				const newObjectFieldSettings =
					getUpdatedDefaultValueFieldSettings(
						values,
						masked.replace(decimalSeparator || ',', '.'),
						'inputAsValue'
					);

				setValues({
					objectFieldSettings: newObjectFieldSettings,
				});

				onSubmit?.({
					...values,
					objectFieldSettings: newObjectFieldSettings,
				});
			}, 300),
		[decimalSeparator, onSubmit, setValues, values]
	);

	const save = (masked: string) => {
		const newSettings = getUpdatedDefaultValueFieldSettings(
			values,
			masked.replace(decimalSeparator || ',', '.'),
			'inputAsValue'
		);

		setValues({objectFieldSettings: newSettings});
	};

	const handleChangeInput = (event: React.ChangeEvent<HTMLInputElement>) => {
		const masked = getConformedValue(event.target.value);

		setValue({
			masked,
			raw: event.target.value,
		});

		if (onSubmit) {
			debouncedSave(masked);
		}
		else {
			save(masked);
		}
	};

	return (
		<NumericEntryBaseField
			error={error}
			label={label}
			onChange={handleChangeInput}
			placeholder={Liferay.Language.get('enter-a-default-value')}
			required={required}
			value={value.masked}
		/>
	);
};

export default NumericDefaultValueInput;
