/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import {useFormState} from 'data-engine-js-components-web';
import {LocalesDropdown} from 'dynamic-data-mapping-form-field-type';
import {ReactFieldBase as FieldBase} from 'dynamic-data-mapping-form-field-type/api';
import React from 'react';

import {PhoneNumberInput} from './PhoneNumber';
import {CountryInfo} from './phoneNumberUtil';

import type {
	FieldChangeEventHandler,
	LocalizedValue,
} from 'dynamic-data-mapping-form-field-type';

interface LocalizablePhoneNumberProps {
	countries?: CountryInfo[];
	fieldName: string;
	name: string;
	onBlur?: (event: React.FocusEvent) => void;
	onChange?: FieldChangeEventHandler<LocalizedValue<string>>;
	onFocus?: (event: React.FocusEvent) => void;
	predefinedValue?: string;
	prefix?: string;
	prefixType?: 'definedByUser' | 'fixed';
	readOnly?: boolean;
	value?: LocalizedValue<string>;
	[key: string]: unknown;
}

const LocalizablePhoneNumber = ({
	countries = [],
	fieldName,
	name,
	onBlur,
	onChange,
	onFocus,
	predefinedValue,
	prefix,
	prefixType = 'definedByUser',
	readOnly,
	value = {} as LocalizedValue<string>,
	...otherProps
}: LocalizablePhoneNumberProps) => {
	const {availableLocales, editingLanguageId} = useFormState();

	const disabled = readOnly || (otherProps.disabled as boolean);

	const handleLocalChange = (event: {target: {value: string}}) => {
		const nextValue = {
			...value,
			[editingLanguageId]: event.target.value,
		} as LocalizedValue<string>;

		onChange?.({target: {value: nextValue}});
	};

	return (
		<FieldBase {...otherProps} name={name} readOnly={disabled}>
			<ClayInput.Group>
				<PhoneNumberInput
					countries={countries}
					disabled={disabled}
					id={otherProps.id as string}
					key={editingLanguageId}
					name={name}
					onBlur={onBlur}
					onChange={handleLocalChange}
					onFocus={onFocus}
					predefinedValue={predefinedValue}
					prefix={prefix}
					prefixType={prefixType}
					value={value[editingLanguageId] ?? ''}
				/>

				<ClayInput.GroupItem shrink>
					<LocalesDropdown
						availableLocales={availableLocales}
						fieldName={fieldName}
						value={value}
					/>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</FieldBase>
	);
};

export default LocalizablePhoneNumber;
