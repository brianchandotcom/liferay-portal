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
import React, {useEffect, useState} from 'react';

import {
	getDomain,
	replaceDomain,
	validateBlockedDomain,
	validateEmailFormat,
} from './util/emailUtil';

import type {LocalizedValue} from 'dynamic-data-mapping-form-field-type';

interface BaseEmailProps {
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

interface LocalizableEmailProps extends BaseEmailProps {
	localizedObjectField: true;
	onChange?: (event: {target: {value: LocalizedValue<string>}}) => void;
	value?: LocalizedValue<string>;
}

interface NonLocalizableEmailProps extends BaseEmailProps {
	localizedObjectField?: false;
	onChange?: (event: {target: {value: string}}) => void;
	value?: string;
}

type EmailProps = LocalizableEmailProps | NonLocalizableEmailProps;

const LocalizableEmail = ({
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
}: LocalizableEmailProps) => {
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

	const domains = autocompleteDomains
		? autocompleteDomains
				.split(',')
				.map((domain) => domain.trim().replace(/^@/, ''))
				.filter(Boolean)
		: [];

	const domainFragment = getDomain(inputValue);

	const localPart =
		domainFragment !== null
			? inputValue.slice(0, inputValue.lastIndexOf('@') + 1)
			: `${inputValue}@`;

	const validationResult =
		showErrors &&
		(validateBlockedDomain(inputValue, blockedDomains) ||
			validateEmailFormat(inputValue));

	const accessibleProperties = {
		...((errorMessage || validationResult || tip) && {
			'aria-describedby': `${id ?? name}_fieldFeedback`,
		}),
		...((displayErrors && !valid) || !!validationResult
			? {'aria-invalid': true}
			: {}),
		'aria-required': required,
	};

	const filteredDomains =
		autocompleteEnabled && !!domains.length && !!inputValue.length
			? domainFragment !== null
				? domains.filter((domain) =>
						domain.toLowerCase().startsWith(domainFragment)
					)
				: domains
			: [];

	const handleChange = (emailValue: string) => {
		setInputValue(emailValue);

		const isValid =
			!validateBlockedDomain(emailValue, blockedDomains) &&
			!validateEmailFormat(emailValue);

		if (showErrors && isValid) {
			setShowErrors(false);
		}

		onChange?.({
			target: {
				value: {
					...value,
					[editingLanguageId]: emailValue,
				},
			},
		});

		setActive(
			!!autocompleteEnabled && !!emailValue.length && !!domains.length
		);
	};

	const handleSelectDomain = (domain: string) => {
		const newValue = replaceDomain(inputValue, domain);

		setInputValue(newValue);

		if (!validateBlockedDomain(newValue, blockedDomains)) {
			onChange?.({
				target: {
					value: {
						...value,
						[editingLanguageId]: newValue,
					},
				},
			});
		}

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

const NonLocalizableEmail = ({
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
}: NonLocalizableEmailProps) => {
	const [active, setActive] = useState(false);
	const [inputValue, setInputValue] = useState(
		(typeof initialValue === 'string' ? initialValue : '') ||
			predefinedValue ||
			''
	);
	const [showErrors, setShowErrors] = useState(false);

	useEffect(() => {
		setInputValue(
			(typeof initialValue === 'string' ? initialValue : '') ||
				predefinedValue ||
				''
		);
	}, [initialValue, predefinedValue]);

	const disabled = readOnly || (otherProps.disabled as boolean);

	const domains = autocompleteDomains
		? autocompleteDomains
				.split(',')
				.map((domain) => domain.trim().replace(/^@/, ''))
				.filter(Boolean)
		: [];

	const domainFragment = getDomain(inputValue);

	const localPart =
		domainFragment !== null
			? inputValue.slice(0, inputValue.lastIndexOf('@') + 1)
			: `${inputValue}@`;

	const validationResult =
		showErrors &&
		(validateBlockedDomain(inputValue, blockedDomains) ||
			validateEmailFormat(inputValue));

	const accessibleProperties = {
		...((errorMessage || validationResult || tip) && {
			'aria-describedby': `${id ?? name}_fieldFeedback`,
		}),
		...((displayErrors && !valid) || !!validationResult
			? {'aria-invalid': true}
			: {}),
		'aria-required': required,
	};

	const filteredDomains =
		autocompleteEnabled && !!domains.length && !!inputValue.length
			? domainFragment !== null
				? domains.filter((domain) =>
						domain.toLowerCase().startsWith(domainFragment)
					)
				: domains
			: [];

	const handleChange = (value: string) => {
		setInputValue(value);

		const isValid =
			!validateBlockedDomain(value, blockedDomains) &&
			!validateEmailFormat(value);

		if (showErrors && isValid) {
			setShowErrors(false);
		}

		onChange?.({target: {value}});

		setActive(!!autocompleteEnabled && !!value.length && !!domains.length);
	};

	const handleSelectDomain = (domain: string) => {
		const newValue = replaceDomain(inputValue, domain);

		setInputValue(newValue);

		if (!validateBlockedDomain(newValue, blockedDomains)) {
			onChange?.({target: {value: newValue}});
		}

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

const Email = (props: EmailProps) =>
	props.localizedObjectField ? (
		<LocalizableEmail {...props} />
	) : (
		<NonLocalizableEmail {...props} />
	);

export default Email;
