/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useFormState} from 'data-engine-js-components-web';
import {ReactFieldBase as FieldBase} from 'dynamic-data-mapping-form-field-type/api';
import React, {useEffect, useMemo, useState} from 'react';

import {
	DEFAULT_COUNTRIES,
	getCombinedValue,
	getFlagSymbol,
	parsePhoneValue,
} from '../PhoneNumber/phoneNumberUtil';
import LocalesDropdown from '../util/localizable/LocalesDropdown';
import {
	convertValueToJSON,
	getEditingValue,
	getInitialInternalValue,
	normalizeLocaleId,
	transformAvailableLocales,
	transformAvailableLocalesAndValue,
	transformEditingLocale,
} from '../util/localizable/transform.es';

const INITIAL_DEFAULT_LOCALE = {
	icon: themeDisplay.getDefaultLanguageId(),
	localeId: themeDisplay.getDefaultLanguageId(),
};
const INITIAL_EDITING_LOCALE = {
	icon: normalizeLocaleId(themeDisplay.getDefaultLanguageId()),
	localeId: themeDisplay.getDefaultLanguageId(),
};

const LocalizablePhoneNumberInner = ({
	availableLocales = [],
	countries = DEFAULT_COUNTRIES,
	defaultLocale = INITIAL_DEFAULT_LOCALE,
	editingLocale = INITIAL_EDITING_LOCALE,
	fieldName,
	id,
	label,
	localizedObjectField,
	name,
	onFieldBlurred,
	onFieldChanged = () => {},
	onFieldFocused,
	readOnly,
	value,
}) => {
	const [currentAvailableLocales, setCurrentAvailableLocales] =
		useState(availableLocales);
	const [currentEditingLocale, setCurrentEditingLocale] =
		useState(editingLocale);
	const [currentValue, setCurrentValue] = useState(value);
	const [currentInternalValue, setCurrentInternalValue] = useState(
		getInitialInternalValue({editingLocale, value})
	);

	const sortedCountries = useMemo(() => {
		return [...countries].sort((a, b) => a.name.localeCompare(b.name));
	}, [countries]);

	const [selectedCountryA2, setSelectedCountryA2] = useState('');
	const [localNumber, setLocalNumber] = useState('');
	const [searchTerm, setSearchTerm] = useState('');

	const filteredCountries = useMemo(() => {
		return sortedCountries.filter(
			(country) =>
				country.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
				country.idd.includes(searchTerm)
		);
	}, [searchTerm, sortedCountries]);

	const selectedCountry = useMemo(() => {
		return countries.find((country) => country.a2 === selectedCountryA2);
	}, [countries, selectedCountryA2]);

	useEffect(() => {
		const parsed = parsePhoneValue(currentInternalValue || '');

		setSelectedCountryA2(parsed.countryA2);
		setLocalNumber(parsed.localNumber);
	}, [currentInternalValue]);

	useEffect(() => {
		let locale = editingLocale;

		if (!localizedObjectField) {
			const translationManager = Liferay.component('translationManager');

			if (!translationManager) {
				return;
			}

			const newAvailableLocales =
				translationManager.get('availableLocales');

			const {availableLocales} = {
				...transformAvailableLocales(
					[...newAvailableLocales],
					defaultLocale,
					currentValue
				),
			};

			locale = transformEditingLocale({
				defaultLocale,
				editingLocale: newAvailableLocales.get(
					translationManager.get('editingLocale')
				),
				value: currentValue,
			});

			setCurrentAvailableLocales(availableLocales);
		}

		setCurrentEditingLocale(locale);

		setCurrentInternalValue(
			getEditingValue({
				defaultLocale,
				editingLocale: locale,
				fieldName,
				value: currentValue,
			})
		);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [defaultLocale, editingLocale, fieldName]);

	const handleValueChange = (newCountryA2, newLocalNumber) => {
		const combined = getCombinedValue(
			newCountryA2,
			newLocalNumber,
			countries
		);
		const valueJSON = convertValueToJSON(currentValue);

		const newValue = JSON.stringify({
			...valueJSON,
			[currentEditingLocale.localeId]: combined,
		});

		setCurrentValue(newValue);
		setCurrentInternalValue(combined);

		const {availableLocales} = {
			...transformAvailableLocalesAndValue({
				availableLocales: currentAvailableLocales,
				defaultLocale,
				value: newValue,
			}),
		};

		setCurrentAvailableLocales(availableLocales);

		onFieldChanged({
			event: {target: {value: combined}},
			value: newValue,
		});
	};

	return (
		<ClayInput.Group>
			<ClayInput.GroupItem append>
				<div className="d-flex w-100">
					<ClayDropDown
						trigger={
							<ClayButton
								className="btn-secondary mr-2"
								disabled={readOnly}
								displayType="secondary"
								style={{minWidth: '90px'}}
							>
								{selectedCountry ? (
									<span className="align-items-center d-flex">
										<ClayIcon
											symbol={getFlagSymbol(
												selectedCountry.a2
											)}
										/>

										<span className="ml-1">
											+{selectedCountry.idd}
										</span>
									</span>
								) : (
									<span>
										{Liferay.Language.get('country')}
									</span>
								)}

								<ClayIcon
									className="inline-item inline-item-after"
									symbol="caret-double"
								/>
							</ClayButton>
						}
					>
						<div className="p-2">
							<ClayInput
								onChange={(event) =>
									setSearchTerm(event.target.value)
								}
								type="text"
								value={searchTerm}
							/>
						</div>

						<ClayDropDown.ItemList>
							{filteredCountries.map((country) => (
								<ClayDropDown.Item
									key={country.a2}
									onClick={() => {
										setSelectedCountryA2(country.a2);
										setSearchTerm('');
										handleValueChange(
											country.a2,
											localNumber
										);
									}}
								>
									<span className="align-items-center d-flex">
										<ClayIcon
											symbol={getFlagSymbol(country.a2)}
										/>

										<span className="ml-2">
											{`+${country.idd} ${country.name}`}
										</span>
									</span>
								</ClayDropDown.Item>
							))}
						</ClayDropDown.ItemList>
					</ClayDropDown>

					<input
						aria-label={label}
						className="ddm-field-text form-control"
						disabled={readOnly}
						id={id ?? name}
						name={`${name}_localNumber`}
						onBlur={onFieldBlurred}
						onChange={(e) => {
							const newNumber = e.target.value.replace(
								/[^0-9\s\-().]/g,
								''
							);

							setLocalNumber(newNumber);
							handleValueChange(selectedCountryA2, newNumber);
						}}
						onFocus={onFieldFocused}
						pattern="[0-9\s\-().]*"
						type="tel"
						value={localNumber}
					/>
				</div>
			</ClayInput.GroupItem>

			<input
				id={id}
				name={name}
				type="hidden"
				value={currentValue || ''}
			/>

			<ClayInput.GroupItem
				className="liferay-ddm-form-field-localizable-text"
				shrink
			>
				<LocalesDropdown
					availableLocales={
						localizedObjectField
							? availableLocales
							: currentAvailableLocales
					}
					fieldName={fieldName}
					onLanguageClicked={(localeId) => {
						const newEditingLocale = currentAvailableLocales.find(
							(availableLocale) =>
								availableLocale.localeId === localeId
						);

						setCurrentEditingLocale({
							...newEditingLocale,
							icon: normalizeLocaleId(newEditingLocale.localeId),
						});

						setCurrentInternalValue(
							getEditingValue({
								defaultLocale,
								editingLocale: newEditingLocale,
								fieldName,
								value: currentValue,
							})
						);
					}}
					value={convertValueToJSON(value)}
				/>
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
};

const LocalizablePhoneNumber = ({
	countries,
	defaultLocale,
	editingLocale,
	fieldName,
	id,
	label,
	localizedObjectField,
	name,
	onBlur,
	onChange,
	onFocus,
	readOnly,
	value = {},
	...otherProps
}) => {
	const {availableLocales, defaultLanguageId, editingLanguageId} =
		useFormState();

	return (
		<FieldBase
			{...otherProps}
			id={id}
			label={label}
			name={name}
			readOnly={readOnly}
		>
			<LocalizablePhoneNumberInner
				{...transformAvailableLocalesAndValue({
					availableLocales,
					defaultLocale,
					value,
				})}
				countries={countries}
				defaultLocale={
					localizedObjectField
						? availableLocales.find(
								({localeId}) => localeId === defaultLanguageId
							)
						: defaultLocale
				}
				editingLocale={
					localizedObjectField
						? availableLocales.find(
								({localeId}) => localeId === editingLanguageId
							)
						: editingLocale
				}
				fieldName={fieldName}
				id={id}
				label={label}
				localizedObjectField={localizedObjectField}
				name={name}
				onFieldBlurred={onBlur}
				onFieldChanged={({event, value}) => onChange(event, value)}
				onFieldFocused={onFocus}
				readOnly={readOnly}
			/>
		</FieldBase>
	);
};

LocalizablePhoneNumber.displayName = 'LocalizablePhoneNumber';

export default LocalizablePhoneNumber;
