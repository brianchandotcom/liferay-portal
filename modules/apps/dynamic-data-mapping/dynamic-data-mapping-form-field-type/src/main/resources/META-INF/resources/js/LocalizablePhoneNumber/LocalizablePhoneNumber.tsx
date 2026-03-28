/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import {useFormState} from 'data-engine-js-components-web';
import {ReactFieldBase as FieldBase} from 'dynamic-data-mapping-form-field-type/api';
import React, {useEffect, useState} from 'react';

import {
	COUNTRIES,
	getCombinedValue,
	getFlag,
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
	countries = COUNTRIES,
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
					<select
						className="btn btn-secondary form-control mr-2"
						disabled={readOnly}
						onChange={(e) => {
							const newA2 = e.target.value;

							setSelectedCountryA2(newA2);
							handleValueChange(newA2, localNumber);
						}}
						style={{maxWidth: '140px'}}
						value={selectedCountryA2}
					>
						<option value="">
							{Liferay.Language.get('country')}
						</option>

						{sortedCountries.map((country) => (
							<option key={country.a2} value={country.a2}>
								{getFlag(country.a2)} +{country.idd}{' '}

								{country.name}
							</option>
						))}
					</select>

					<input
						aria-label={label}
						className="ddm-field-text form-control"
						disabled={readOnly}
						id={name}
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
