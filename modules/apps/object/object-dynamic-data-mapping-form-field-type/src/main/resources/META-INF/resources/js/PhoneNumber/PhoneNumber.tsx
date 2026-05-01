/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import {CountryInfo, PhoneNumberInput} from '@liferay/object-js-components-web';
import {useFormState} from 'data-engine-js-components-web';
import {LocalesDropdown} from 'dynamic-data-mapping-form-field-type';
import {ReactFieldBase as FieldBase} from 'dynamic-data-mapping-form-field-type/api';
import React, {useState} from 'react';

import type {
	FieldChangeEventHandler,
	LocalizedValue,
} from 'dynamic-data-mapping-form-field-type';

interface BasePhoneNumberProps {
	countries?: CountryInfo[];
	disabled?: boolean;
	fieldName: string;
	id?: string;
	name: string;
	onBlur?: (event: React.FocusEvent) => void;
	onFocus?: (event: React.FocusEvent) => void;
	predefinedValue?: string;
	prefix?: string;
	prefixType?: 'definedByUser' | 'fixed';
	readOnly?: boolean;
	[key: string]: unknown;
}

interface LocalizablePhoneNumberProps extends BasePhoneNumberProps {
	onChange?: FieldChangeEventHandler<LocalizedValue<string>>;
	value?: LocalizedValue<string>;
}

interface NonLocalizablePhoneNumberProps extends BasePhoneNumberProps {
	onChange?: (event: {target: {value: string}}) => void;
	value?: string;
}

type PhoneNumberProps =
	| (LocalizablePhoneNumberProps & {localizedObjectField: true})
	| (NonLocalizablePhoneNumberProps & {localizedObjectField?: false});

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

const NonLocalizablePhoneNumber = ({
	countries = [],
	name,
	onBlur,
	onChange,
	onFocus,
	predefinedValue,
	prefix,
	prefixType = 'definedByUser',
	readOnly,
	value: initialValue,
	...otherProps
}: NonLocalizablePhoneNumberProps) => {
	const disabled = readOnly || (otherProps.disabled as boolean);

	const [combinedValue, setCombinedValue] = useState(
		initialValue || predefinedValue || ''
	);

	return (
		<FieldBase {...otherProps} name={name} readOnly={disabled}>
			<ClayInput.Group>
				<PhoneNumberInput
					countries={countries}
					disabled={disabled}
					id={otherProps.id as string}
					name={name}
					onBlur={onBlur}
					onChange={(event) => {
						setCombinedValue(event.target.value);
						onChange?.(event);
					}}
					onFocus={onFocus}
					predefinedValue={predefinedValue}
					prefix={prefix}
					prefixType={prefixType}
					value={initialValue}
				/>
			</ClayInput.Group>

			<input name={name} type="hidden" value={combinedValue} />
		</FieldBase>
	);
};

const PhoneNumber = (props: PhoneNumberProps) =>
	props.localizedObjectField ? (
		<LocalizablePhoneNumber {...props} />
	) : (
		<NonLocalizablePhoneNumber {...props} />
	);

export default PhoneNumber;
