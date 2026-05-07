/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import {
	CountryInfo,
	PREFIX_TYPE,
	PhoneNumberInput,
	PrefixType,
} from '@liferay/object-js-components-web';
import {useFormState} from 'data-engine-js-components-web';
import {LocalesDropdown} from 'dynamic-data-mapping-form-field-type';
import {ReactFieldBase as FieldBase} from 'dynamic-data-mapping-form-field-type/api';
import React, {useState} from 'react';

import type {
	FieldChangeEventHandler,
	LocalizedValue,
} from 'dynamic-data-mapping-form-field-type';

// E.164 format: a leading "+" followed by 7 to 15 digits.

const PHONE_NUMBER_PATTERN = /^\+[0-9]{7,15}$/;

interface BasePhoneNumberProps {
	countries?: CountryInfo[];
	disabled?: boolean;
	displayErrors?: boolean;
	errorMessage?: string;
	fieldName: string;
	id?: string;
	label?: string;
	name: string;
	onBlur?: (event: React.FocusEvent) => void;
	onFocus?: (event: React.FocusEvent) => void;
	predefinedValue?: string;
	prefix?: string;
	prefixType?: PrefixType;
	readOnly?: boolean;
	valid?: boolean;
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

const validate = (value: string) => {
	if (!value) {
		return {displayErrors: false, errorMessage: undefined, valid: true};
	}

	const valid = PHONE_NUMBER_PATTERN.test(value);

	return {
		displayErrors: !valid,
		errorMessage: valid
			? undefined
			: Liferay.Language.get('please-enter-a-valid-phone-number'),
		valid,
	};
};

const LocalizablePhoneNumber = ({
	countries = [],
	displayErrors,
	errorMessage,
	fieldName,
	name,
	onBlur,
	onChange,
	onFocus,
	predefinedValue,
	prefix,
	prefixType = PREFIX_TYPE.DEFINED_BY_USER,
	readOnly,
	valid = true,
	value = {} as LocalizedValue<string>,
	...otherProps
}: LocalizablePhoneNumberProps) => {
	const {availableLocales, editingLanguageId} = useFormState();

	const [validField, setValidField] = useState({
		displayErrors,
		errorMessage,
		valid,
	});

	const currentValue = value[editingLanguageId] ?? predefinedValue ?? '';
	const disabled = readOnly || otherProps.disabled;

	const handleBlur = (event: React.FocusEvent) => {
		setValidField(validate(currentValue));

		onBlur?.(event);
	};

	const handleLanguageClicked = (localeId: Liferay.Language.Locale) => {
		if (validField.displayErrors) {
			setValidField(validate(value[localeId] ?? predefinedValue ?? ''));
		}
	};

	const handleChange = (event: {target: {value: string}}) => {
		const newValue = {
			...value,
			[editingLanguageId]: event.target.value,
		} as LocalizedValue<string>;

		// Re-validate on change only while an error is already visible,
		// so the message clears live as the user fixes it (typing or
		// country switch) without flashing on first entry.

		if (validField.displayErrors) {
			setValidField(validate(event.target.value));
		}

		onChange?.({target: {value: newValue}});
	};

	return (
		<FieldBase
			{...otherProps}
			{...validField}
			name={name}
			readOnly={disabled}
		>
			<ClayInput.Group aria-label={otherProps.label} role="group">
				<PhoneNumberInput
					countries={countries}
					disabled={disabled}
					id={otherProps.id as string}
					key={editingLanguageId}
					name={name}
					onBlur={handleBlur}
					onChange={handleChange}
					onFocus={onFocus}
					prefix={prefix}
					prefixType={prefixType}
					value={currentValue}
				/>

				<ClayInput.GroupItem shrink>
					<LocalesDropdown
						availableLocales={availableLocales}
						fieldName={fieldName}
						onLanguageClicked={handleLanguageClicked}
						value={value}
					/>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</FieldBase>
	);
};

const NonLocalizablePhoneNumber = ({
	countries = [],
	displayErrors,
	errorMessage,
	name,
	onBlur,
	onChange,
	onFocus,
	predefinedValue,
	prefix,
	prefixType = PREFIX_TYPE.DEFINED_BY_USER,
	readOnly,
	valid = true,
	value: initialValue,
	...otherProps
}: NonLocalizablePhoneNumberProps) => {
	const [combinedValue, setCombinedValue] = useState(
		initialValue || predefinedValue || ''
	);
	const [validField, setValidField] = useState({
		displayErrors,
		errorMessage,
		valid,
	});

	const disabled = readOnly || otherProps.disabled;

	const handleBlur = (event: React.FocusEvent) => {
		setValidField(validate(combinedValue));

		onBlur?.(event);
	};

	const handleChange = (event: {target: {value: string}}) => {
		setCombinedValue(event.target.value);

		// Re-validate on change only while an error is already visible,
		// so the message clears as the user fixes it without flashing on
		// first entry.

		if (validField.displayErrors) {
			setValidField(validate(event.target.value));
		}

		onChange?.(event);
	};

	return (
		<FieldBase
			{...otherProps}
			{...validField}
			name={name}
			readOnly={disabled}
		>
			<ClayInput.Group aria-label={otherProps.label} role="group">
				<PhoneNumberInput
					countries={countries}
					disabled={disabled}
					id={otherProps.id as string}
					name={name}
					onBlur={handleBlur}
					onChange={handleChange}
					onFocus={onFocus}
					prefix={prefix}
					prefixType={prefixType}
					value={initialValue || predefinedValue}
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
