/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useEffect, useState} from 'react';

import {CountryCodePicker} from './CountryCodePicker';
import {
	COUNTRY_SOURCE,
	CountryInfo,
	CountrySource,
	findCountry,
	getDefaultCountry,
	getFlagSymbol,
	parsePhoneValue,
} from './phoneNumberUtil';

interface PhoneNumberInputProps {
	countries?: CountryInfo[];
	countryA2?: CountryInfo['a2'];
	countrySource?: CountrySource;
	disabled?: boolean;
	id?: string;
	name: string;
	onBlur?: (event: React.FocusEvent) => void;
	onChange?: (event: {target: {value: string}}) => void;
	onFocus?: (event: React.FocusEvent) => void;
	value?: string;
}

export function PhoneNumberInput({
	countries = [],
	countryA2,
	countrySource = COUNTRY_SOURCE.DEFINED_BY_USER,
	disabled,
	id,
	name,
	onBlur,
	onChange,
	onFocus,
	value = '',
}: PhoneNumberInputProps) {
	const [localNumber, setLocalNumber] = useState('');
	const [selectedCountry, setSelectedCountry] = useState<
		CountryInfo | undefined
	>(getDefaultCountry(countries));

	const fixedCountry = findCountry(countries, countryA2);

	const fixedFlagSymbol = fixedCountry ? getFlagSymbol(fixedCountry.a2) : '';
	const fixedPrefix = fixedCountry ? `+${fixedCountry.idd}` : '';

	const handleValueChange = (
		nextCountry: CountryInfo | undefined,
		number: string
	) => {
		if (onChange) {
			const resolvedPrefix =
				countrySource === COUNTRY_SOURCE.FIXED
					? fixedPrefix
					: `+${nextCountry?.idd ?? ''}`;

			const sanitizedNumber = number.replace(/\D/g, '');

			onChange({
				target: {
					value: sanitizedNumber
						? `${resolvedPrefix}${sanitizedNumber}`
						: '',
				},
			});
		}
	};

	/**
	 * Parse the phone value to set the initial states.
	 */
	useEffect(() => {
		if (countrySource === COUNTRY_SOURCE.FIXED) {
			if (fixedPrefix && value.startsWith(fixedPrefix)) {
				setLocalNumber(value.substring(fixedPrefix.length));
			}
			else {
				const {localNumber: parsedLocalNumber} = parsePhoneValue(
					value,
					countries
				);

				setLocalNumber(parsedLocalNumber);
			}
		}
		else {
			const {countryA2: parsedCountryA2, localNumber: parsedLocalNumber} =
				parsePhoneValue(value, countries);

			const country = findCountry(countries, parsedCountryA2);

			setSelectedCountry(country || getDefaultCountry(countries));
			setLocalNumber(parsedLocalNumber);
		}

		// eslint-disable-next-line react-compiler/react-compiler
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<>
			<ClayInput.GroupItem
				prepend={countrySource === COUNTRY_SOURCE.FIXED}
				shrink
			>
				{countrySource === COUNTRY_SOURCE.FIXED ? (
					<ClayInput.GroupText>
						{fixedFlagSymbol && (
							<span className="inline-item inline-item-before">
								<ClayIcon symbol={fixedFlagSymbol} />
							</span>
						)}

						{fixedPrefix || countryA2}
					</ClayInput.GroupText>
				) : (
					<CountryCodePicker
						countries={countries}
						disabled={disabled}
						onSelectionChange={(country) => {
							setSelectedCountry(country);
							handleValueChange(country, localNumber);
						}}
						selectedKey={selectedCountry?.a2 ?? ''}
					/>
				)}
			</ClayInput.GroupItem>

			<ClayInput.GroupItem
				prepend={countrySource === COUNTRY_SOURCE.FIXED}
			>
				<ClayInput
					aria-label={Liferay.Language.get('phone-number')}
					className="ddm-field-text form-control"
					disabled={disabled}
					id={id ?? name}
					name={`${name}_localNumber`}
					onBlur={onBlur}
					onChange={(event) => {
						const newNumber = event.target.value.replace(
							/[^0-9\s\-().]/g,
							''
						);

						setLocalNumber(newNumber);
						handleValueChange(selectedCountry, newNumber);
					}}
					onFocus={onFocus}
					pattern="[0-9\s\-\(\).]*"
					type="tel"
					value={localNumber}
				/>
			</ClayInput.GroupItem>
		</>
	);
}
