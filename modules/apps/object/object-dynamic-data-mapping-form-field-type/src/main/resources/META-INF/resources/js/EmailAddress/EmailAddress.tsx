/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAutocomplete from '@clayui/autocomplete';
import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import {useFormState} from 'data-engine-js-components-web';
import {LocalesDropdown} from 'dynamic-data-mapping-form-field-type';
import {ReactFieldBase as FieldBase} from 'dynamic-data-mapping-form-field-type/api';
import React, {useEffect, useMemo, useState} from 'react';

import {
	getDomain,
	replaceDomain,
	validateBlockedDomain,
	validateEmailAddressFormat,
} from './util/emailAddressUtil';

import type {LocalizedValue} from 'dynamic-data-mapping-form-field-type';

interface BaseEmailAddressProps {
	autocompleteDomains?: string;
	autocompleteEnabled?: boolean;
	blockedDomains?: string;
	displayErrors?: boolean;
	errorMessage?: string;
	id?: string;
	name: string;
	onBlur?: (event: React.FocusEvent<HTMLInputElement>) => void;
	onFocus?: (event: React.FocusEvent<HTMLInputElement>) => void;
	predefinedValue?: string;
	readOnly?: boolean;
	required?: boolean;
	tip?: string;
	valid?: boolean;
	[key: string]: unknown;
}

interface LocalizableEmailAddressProps extends BaseEmailAddressProps {
	localizedObjectField: true;
	onChange?: (event: {target: {value: LocalizedValue<string>}}) => void;
	value?: LocalizedValue<string>;
}

interface NonLocalizableEmailAddressProps extends BaseEmailAddressProps {
	localizedObjectField?: false;
	onChange?: (event: {target: {value: string}}) => void;
	value?: string;
}

type EmailAddressProps =
	| LocalizableEmailAddressProps
	| NonLocalizableEmailAddressProps;

const LocalizableEmailAddress = ({
	autocompleteDomains,
	autocompleteEnabled,
	blockedDomains,
	displayErrors,
	errorMessage,
	id,
	name,
	onBlur,
	onChange,
	onFocus,
	readOnly,
	required,
	tip,
	valid,
	value = {} as LocalizedValue<string>,
	...otherProps
}: LocalizableEmailAddressProps) => {
	const {availableLocales, defaultLanguageId, editingLanguageId} =
		useFormState();

	const [active, setActive] = useState(false);
	const [inputValue, setInputValue] = useState(
		value?.[editingLanguageId] || value?.[defaultLanguageId] || ''
	);
	const [showErrors, setShowErrors] = useState(false);

	useEffect(() => {
		setInputValue(
			value?.[editingLanguageId] || value?.[defaultLanguageId] || ''
		);

		// eslint-disable-next-line react-compiler/react-compiler
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [defaultLanguageId, editingLanguageId]);

	const disabled = readOnly || (otherProps.disabled as boolean);

	const domains = useMemo(
		() =>
			autocompleteDomains
				? autocompleteDomains
						.split(',')
						.map((domain) => domain.trim().replace(/^@/, ''))
						.filter(Boolean)
				: [],
		[autocompleteDomains]
	);

	const domainFragment = getDomain(inputValue);

	const localPart =
		domainFragment !== null
			? inputValue.slice(0, inputValue.lastIndexOf('@') + 1)
			: `${inputValue}@`;

	const validationResult =
		showErrors &&
		(validateBlockedDomain(inputValue, blockedDomains) ||
			validateEmailAddressFormat(inputValue));

	const accessibleProperties = {
		...((errorMessage || validationResult || tip) && {
			'aria-describedby': `${id ?? name}_fieldFeedback`,
		}),
		...((displayErrors && !valid) || !!validationResult
			? {'aria-invalid': true}
			: {}),
		'aria-required': required,
	};

	const filteredDomains = useMemo(
		() =>
			autocompleteEnabled && !!domains.length && !!inputValue.length
				? domainFragment !== null
					? domains.filter(
							(domain) =>
								domain
									.toLowerCase()
									.startsWith(domainFragment) &&
								!validateBlockedDomain(
									`@${domain}`,
									blockedDomains
								)
						)
					: domains.filter(
							(domain) =>
								!validateBlockedDomain(
									`@${domain}`,
									blockedDomains
								)
						)
				: [],
		[
			autocompleteEnabled,
			blockedDomains,
			domainFragment,
			domains,
			inputValue,
		]
	);

	const handleChange = (emailAddressValue: string) => {
		setInputValue(emailAddressValue);

		const isValid =
			!validateBlockedDomain(emailAddressValue, blockedDomains) &&
			!validateEmailAddressFormat(emailAddressValue);

		if (showErrors && isValid) {
			setShowErrors(false);
		}

		onChange?.({
			target: {
				value: {
					...value,
					[editingLanguageId]: emailAddressValue,
				},
			},
		});

		setActive(
			!!autocompleteEnabled &&
				!!emailAddressValue.length &&
				!!domains.length
		);
	};

	const handleSelectDomain = (domain: string) => {
		const newValue = replaceDomain(inputValue, domain);

		setInputValue(newValue);

		onChange?.({
			target: {
				value: {
					...value,
					[editingLanguageId]: newValue,
				},
			},
		});

		setActive(false);
	};

	return (
		<FieldBase
			{...otherProps}
			displayErrors={displayErrors}
			errorMessage={errorMessage}
			valid={valid}
			{...validationResult}
			id={id ?? name}
			name={name}
			readOnly={disabled}
			required={required}
		>
			<ClayInput.Group>
				<ClayInput.GroupItem>
					<ClayAutocomplete>
						<ClayInput
							{...accessibleProperties}
							autoComplete="off"
							className="ddm-field-text form-control"
							data-1p-ignore
							disabled={disabled}
							id={id ?? name}
							name={`${name}_input`}
							onBlur={(event) => {
								onBlur?.(event);
								setShowErrors(true);
							}}
							onChange={({target: {value}}) =>
								handleChange(value)
							}
							onFocus={(event) => {
								onFocus?.(event);

								if (
									autocompleteEnabled &&
									!!inputValue.length &&
									!!domains.length
								) {
									setActive(true);
								}
							}}
							required={required}
							type="text"
							value={inputValue}
						/>

						<ClayAutocomplete.DropDown
							active={
								!disabled && active && !!filteredDomains.length
							}
							onActiveChange={setActive}
						>
							<ClayDropDown.ItemList>
								{filteredDomains.map((domain) => (
									<ClayAutocomplete.Item
										key={domain}
										match={inputValue}
										onClick={() =>
											handleSelectDomain(domain)
										}
									>
										{`${localPart}${domain}`}
									</ClayAutocomplete.Item>
								))}
							</ClayDropDown.ItemList>
						</ClayAutocomplete.DropDown>
					</ClayAutocomplete>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem shrink>
					<LocalesDropdown
						availableLocales={availableLocales}
						fieldName={name}
						value={value}
					/>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</FieldBase>
	);
};

const NonLocalizableEmailAddress = ({
	autocompleteDomains,
	autocompleteEnabled,
	blockedDomains,
	displayErrors,
	errorMessage,
	id,
	name,
	onBlur,
	onChange,
	onFocus,
	predefinedValue,
	readOnly,
	required,
	tip,
	valid,
	value: initialValue,
	...otherProps
}: NonLocalizableEmailAddressProps) => {
	const [active, setActive] = useState(false);
	const [inputValue, setInputValue] = useState(
		typeof initialValue === 'string' ? initialValue : predefinedValue ?? ''
	);
	const [showErrors, setShowErrors] = useState(false);

	useEffect(() => {
		setInputValue(
			typeof initialValue === 'string'
				? initialValue
				: predefinedValue ?? ''
		);
	}, [initialValue, predefinedValue]);

	const disabled = readOnly || (otherProps.disabled as boolean);

	const domains = useMemo(
		() =>
			autocompleteDomains
				? autocompleteDomains
						.split(',')
						.map((domain) => domain.trim().replace(/^@/, ''))
						.filter(Boolean)
				: [],
		[autocompleteDomains]
	);

	const domainFragment = getDomain(inputValue);

	const localPart =
		domainFragment !== null
			? inputValue.slice(0, inputValue.lastIndexOf('@') + 1)
			: `${inputValue}@`;

	const validationResult =
		showErrors &&
		(validateBlockedDomain(inputValue, blockedDomains) ||
			validateEmailAddressFormat(inputValue));

	const accessibleProperties = {
		...((errorMessage || validationResult || tip) && {
			'aria-describedby': `${id ?? name}_fieldFeedback`,
		}),
		...((displayErrors && !valid) || !!validationResult
			? {'aria-invalid': true}
			: {}),
		'aria-required': required,
	};

	const filteredDomains = useMemo(
		() =>
			autocompleteEnabled && !!domains.length && !!inputValue.length
				? domainFragment !== null
					? domains.filter(
							(domain) =>
								domain
									.toLowerCase()
									.startsWith(domainFragment) &&
								!validateBlockedDomain(
									`@${domain}`,
									blockedDomains
								)
						)
					: domains.filter(
							(domain) =>
								!validateBlockedDomain(
									`@${domain}`,
									blockedDomains
								)
						)
				: [],
		[
			autocompleteEnabled,
			blockedDomains,
			domainFragment,
			domains,
			inputValue,
		]
	);

	const handleChange = (value: string) => {
		setInputValue(value);

		const isValid =
			!validateBlockedDomain(value, blockedDomains) &&
			!validateEmailAddressFormat(value);

		if (showErrors && isValid) {
			setShowErrors(false);
		}

		onChange?.({target: {value}});

		setActive(!!autocompleteEnabled && !!value.length && !!domains.length);
	};

	const handleSelectDomain = (domain: string) => {
		const newValue = replaceDomain(inputValue, domain);

		setInputValue(newValue);

		onChange?.({target: {value: newValue}});

		setActive(false);
	};

	return (
		<FieldBase
			{...otherProps}
			displayErrors={displayErrors}
			errorMessage={errorMessage}
			valid={valid}
			{...validationResult}
			id={id ?? name}
			name={name}
			readOnly={disabled}
			required={required}
		>
			<ClayAutocomplete>
				<ClayInput
					{...accessibleProperties}
					autoComplete="off"
					className="ddm-field-text form-control"
					data-1p-ignore
					disabled={disabled}
					id={id ?? name}
					name={`${name}_input`}
					onBlur={(event) => {
						onBlur?.(event);
						setShowErrors(true);
					}}
					onChange={({target: {value}}) => handleChange(value)}
					onFocus={(event) => {
						onFocus?.(event);

						if (
							autocompleteEnabled &&
							!!inputValue.length &&
							!!domains.length
						) {
							setActive(true);
						}
					}}
					required={required}
					type="text"
					value={inputValue}
				/>

				<ClayAutocomplete.DropDown
					active={!disabled && active && !!filteredDomains.length}
					onActiveChange={setActive}
				>
					<ClayDropDown.ItemList>
						{filteredDomains.map((domain) => (
							<ClayAutocomplete.Item
								key={domain}
								match={inputValue}
								onClick={() => handleSelectDomain(domain)}
							>
								{`${localPart}${domain}`}
							</ClayAutocomplete.Item>
						))}
					</ClayDropDown.ItemList>
				</ClayAutocomplete.DropDown>
			</ClayAutocomplete>

			<input name={name} type="hidden" value={inputValue} />
		</FieldBase>
	);
};

const EmailAddress = (props: EmailAddressProps) =>
	props.localizedObjectField ? (
		<LocalizableEmailAddress {...props} />
	) : (
		<NonLocalizableEmailAddress {...props} />
	);

export default EmailAddress;
