/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import {ReactFieldBase as FieldBase} from 'dynamic-data-mapping-form-field-type/api';
import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';

import {
	CountryInfo,
	getCombinedValue,
	getFlag,
	parsePhoneValue,
} from './phoneNumberUtil';

interface PhoneNumberProps {
	countries?: CountryInfo[];
	name: string;
	onBlur?: (event: React.FocusEvent) => void;
	onChange?: (event: {target: {value: string}}) => void;
	onFocus?: (event: React.FocusEvent) => void;
	predefinedValue?: string;
	readOnly?: boolean;
	value?: string;
	[key: string]: unknown;
}

const PhoneNumber: React.FC<PhoneNumberProps> = ({
	countries = [],
	name,
	onBlur,
	onChange,
	onFocus,
	predefinedValue,
	readOnly: disabled,
	value: initialValue,
	...otherProps
}) => {
	const currentValue = initialValue || predefinedValue || '';

	const [selectedCountryA2, setSelectedCountryA2] = useState('');
	const [localNumber, setLocalNumber] = useState('');
	const [dropdownActive, setDropdownActive] = useState(false);
	const [searchTerm, setSearchTerm] = useState('');

	const triggerRef = useRef<HTMLButtonElement>(null);

	const selectedCountry = useMemo(
		() => countries.find((c) => c.a2 === selectedCountryA2),
		[countries, selectedCountryA2]
	);

	const filteredCountries = useMemo(() => {
		if (!searchTerm) {
			return countries;
		}

		const term = searchTerm.toLowerCase();

		return countries.filter(
			(c) =>
				c.name.toLowerCase().includes(term) ||
				c.idd.includes(term) ||
				c.a2.toLowerCase().includes(term)
		);
	}, [countries, searchTerm]);

	const fireChange = useCallback(
		(countryA2: string, number: string) => {
			if (!onChange) {
				return;
			}

			onChange({
				target: {
					value: getCombinedValue(countryA2, number, countries),
				},
			});
		},
		[countries, onChange]
	);

	useEffect(() => {
		const parsed = parsePhoneValue(currentValue);

		setSelectedCountryA2(parsed.countryA2);
		setLocalNumber(parsed.localNumber);
	}, [currentValue]);

	return (
		<FieldBase {...otherProps} name={name} readOnly={disabled}>
			<div className="d-flex">
				<ClayDropDown
					active={dropdownActive}
					onActiveChange={setDropdownActive}
					trigger={
						<ClayButton
							className="btn-secondary mr-2"
							disabled={disabled}
							displayType="secondary"
							ref={triggerRef}
							style={{minWidth: '90px'}}
						>
							{selectedCountry ? (
								<span>
									{getFlag(selectedCountry.a2)}

									{' +'}

									{selectedCountry.idd}
								</span>
							) : (
								<span>{Liferay.Language.get('country')}</span>
							)}
						</ClayButton>
					}
				>
					<div className="p-2">
						<ClayInput
							onChange={(e) => setSearchTerm(e.target.value)}
							placeholder={Liferay.Language.get('search')}
							sizing="sm"
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
									setDropdownActive(false);
									setSearchTerm('');
									fireChange(country.a2, localNumber);
								}}
							>
								{getFlag(country.a2)} {country.name}
+
								{country.idd}
							</ClayDropDown.Item>
						))}
					</ClayDropDown.ItemList>
				</ClayDropDown>

				<ClayInput
					className="ddm-field-text form-control"
					disabled={disabled}
					id={name}
					name={name}
					onBlur={onBlur}
					onChange={(event) => {
						const newNumber = event.target.value.replace(
							/[^0-9\s\-().]/g,
							''
						);

						setLocalNumber(newNumber);
						fireChange(selectedCountryA2, newNumber);
					}}
					onFocus={onFocus}
					pattern="[0-9\s\-().]*"
					placeholder={Liferay.Language.get('enter-a-phone-number')}
					type="tel"
					value={localNumber}
				/>
			</div>
		</FieldBase>
	);
};

export default PhoneNumber;
