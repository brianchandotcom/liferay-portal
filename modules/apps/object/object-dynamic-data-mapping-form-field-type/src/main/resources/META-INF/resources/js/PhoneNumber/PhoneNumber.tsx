/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ReactFieldBase as FieldBase} from 'dynamic-data-mapping-form-field-type/api';
import React, {useEffect, useState} from 'react';

import {CountryInfo, getFlagSymbol, parsePhoneValue} from './phoneNumberUtil';

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

const PickerTrigger = React.forwardRef<
	HTMLDivElement,
	{selectedCountry: CountryInfo & {symbol?: string}} & React.ComponentProps<
		typeof ClayButton
	>
>(({selectedCountry, ...otherProps}, ref) => {
	const flagSymbol = getFlagSymbol(selectedCountry.a2);

	return (
		<div {...(otherProps as any)} ref={ref}>
			{flagSymbol && (
				<span className="inline-item inline-item-before">
					<ClayIcon symbol={flagSymbol} />
				</span>
			)}

			<span>+{selectedCountry.idd}</span>
		</div>
	);
});

const PhoneNumber = ({
	countries = [],
	name,
	onBlur,
	onChange,
	onFocus,
	predefinedValue,
	readOnly,
	value: initialValue,
	...otherProps
}: PhoneNumberProps) => {
	const [localNumber, setLocalNumber] = useState('');
	const [selectedCountry, setSelectedCountry] = useState<CountryInfo>(
		countries[0]
	);

	const combinedValue = `+${selectedCountry.idd}${localNumber.replace(/\D/g, '')}`;

	const disabled = readOnly || (otherProps.disabled as boolean);

	const handleValueChange = (country: CountryInfo, number: string) => {
		if (onChange) {
			onChange({
				target: {
					value: `+${country.idd}${number.replace(/\D/g, '')}`,
				},
			});
		}
	};

	/** Parse the phone value to set the initial states. */
	useEffect(() => {
		const {countryA2, localNumber} = parsePhoneValue(
			initialValue || predefinedValue || '',
			countries
		);

		const selectedCountry = countries.find(
			(country) => country.a2 === countryA2
		);

		setSelectedCountry(selectedCountry || countries[0]);
		setLocalNumber(localNumber);

		// eslint-disable-next-line react-compiler/react-compiler
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<FieldBase {...otherProps} name={name} readOnly={disabled}>
			<ClayInput.Group>
				<ClayInput.GroupItem shrink>
					<Picker
						as={PickerTrigger}
						disabled={disabled}
						items={countries}
						onSelectionChange={(key) => {
							const selectedCountry = countries.find(
								(country) => country.a2 === key
							);

							if (selectedCountry) {
								setSelectedCountry(selectedCountry);
								handleValueChange(selectedCountry, localNumber);
							}
						}}
						searchable
						selectedCountry={selectedCountry}
						selectedKey={selectedCountry.a2}
					>
						{(country) => {
							const flagSymbol = getFlagSymbol(country.a2);

							return (
								<Option
									key={country.a2}
									textValue={`+${country.idd} ${country.name}`}
								>
									<div className="autofit-row">
										<div className="autofit-col">
											{flagSymbol && (
												<ClayIcon symbol={flagSymbol} />
											)}
										</div>

										<div
											className="autofit-col"
											style={{minWidth: '45px'}}
										>
											{`+${country.idd}`}
										</div>

										<div className="autofit-col autofit-col-expand">
											{country.name}
										</div>
									</div>
								</Option>
							);
						}}
					</Picker>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem>
					<ClayInput
						className="ddm-field-text form-control"
						disabled={disabled}
						id={(otherProps.id as string) ?? name}
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
						pattern="[0-9\s\-().]*"
						type="tel"
						value={localNumber}
					/>
				</ClayInput.GroupItem>
			</ClayInput.Group>

			<input name={name} type="hidden" value={combinedValue} />
		</FieldBase>
	);
};

export default PhoneNumber;
