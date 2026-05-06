/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useEffect, useState} from 'react';

import {CountryCodePicker} from './CountryCodePicker';
import {
	CountryInfo,
	PREFIX_TYPE,
	PrefixType,
	getDefaultCountry,
	getFlagSymbol,
	parsePhoneValue,
} from './phoneNumberUtil';

interface PhoneNumberInputProps {
	countries?: CountryInfo[];
	disabled?: boolean;
	id?: string;
	name: string;
	onBlur?: (event: React.FocusEvent) => void;
	onChange?: (event: {target: {value: string}}) => void;
	onFocus?: (event: React.FocusEvent) => void;
	prefix?: string;
	prefixType?: PrefixType;
	value?: string;
}

export function PhoneNumberInput({
	countries = [],
	disabled,
	id,
	name,
	onBlur,
	onChange,
	onFocus,
	prefix,
	prefixType = PREFIX_TYPE.DEFINED_BY_USER,
	value = '',
}: PhoneNumberInputProps) {
	const [localNumber, setLocalNumber] = useState('');
	const [selectedCountry, setSelectedCountry] = useState<CountryInfo>(
		getDefaultCountry(countries)
	);

	const fixedCountry =
		prefixType === PREFIX_TYPE.FIXED
			? countries.find((country) => `+${country.idd}` === prefix)
			: null;

	const fixedFlagSymbol = fixedCountry ? getFlagSymbol(fixedCountry.a2) : '';

	const handleValueChange = (country: CountryInfo, number: string) => {
		if (onChange) {
			const resolvedPrefix =
				prefixType === PREFIX_TYPE.FIXED
					? prefix || ''
					: `+${country.idd}`;

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

	/** Parse the phone value to set the initial states. */
	useEffect(() => {
		if (prefixType === PREFIX_TYPE.FIXED) {
			if (prefix && value.startsWith(prefix)) {
				setLocalNumber(value.substring(prefix.length));
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
			const {countryA2, localNumber: parsedLocalNumber} = parsePhoneValue(
				value,
				countries
			);

			const country = countries.find(
				(country) => country.a2 === countryA2
			);

			setSelectedCountry(country || getDefaultCountry(countries));
			setLocalNumber(parsedLocalNumber);
		}

		// eslint-disable-next-line react-compiler/react-compiler
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<>
			<ClayInput.GroupItem
				prepend={prefixType === PREFIX_TYPE.FIXED}
				shrink
			>
				{prefixType === PREFIX_TYPE.FIXED ? (
					<ClayInput.GroupText>
						{fixedFlagSymbol && (
							<span className="inline-item inline-item-before">
								<ClayIcon symbol={fixedFlagSymbol} />
							</span>
						)}

						{prefix}
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

			<ClayInput.GroupItem prepend={prefixType === PREFIX_TYPE.FIXED}>
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
