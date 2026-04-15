/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ReactFieldBase as FieldBase} from 'dynamic-data-mapping-form-field-type/api';
import React, {useEffect, useMemo, useState} from 'react';

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
	HTMLButtonElement,
	{selectedCountry: CountryInfo & {symbol?: string}} & React.ComponentProps<
		typeof ClayButton
	>
>(({children, selectedCountry, ...otherProps}, ref) => (
	<ClayButton
		{...otherProps}
		className="btn-secondary mr-2"
		displayType="secondary"
		ref={ref}
		style={{minWidth: '90px'}}
	>
		<span className="align-items-center d-flex">
			{selectedCountry.symbol && (
				<ClayIcon symbol={selectedCountry.symbol} />
			)}

			<span className="ml-1">+{selectedCountry.idd}</span>

			<span className="inline-item inline-item-after">
				<ClayIcon symbol="caret-double" />
			</span>
		</span>
	</ClayButton>
));

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

	const countriesWithFlagSymbol = useMemo(() => {
		return countries.map((country) => {
			return {
				...country,
				symbol: getFlagSymbol(country.a2),
			};
		});
	}, [countries]);

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
			initialValue || predefinedValue || ''
		);

		const country = countriesWithFlagSymbol.find(
			(country) => country.a2 === countryA2
		);

		setSelectedCountry(country || countriesWithFlagSymbol[0]);
		setLocalNumber(localNumber);

		// eslint-disable-next-line react-compiler/react-compiler
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<FieldBase {...otherProps} name={name} readOnly={disabled}>
			<div className="d-flex">
				<Picker
					as={PickerTrigger}
					disabled={disabled}
					items={countriesWithFlagSymbol}
					onSelectionChange={(key) => {
						const country = countriesWithFlagSymbol.find(
							(c) => c.a2 === key
						);

						if (country) {
							setSelectedCountry(country);
							handleValueChange(country, localNumber);
						}
					}}
					searchable
					selectedCountry={selectedCountry}
					selectedKey={selectedCountry.a2}
				>
					{(country) => (
						<Option key={country.a2} textValue={country.name}>
							<div className="autofit-row">
								<div className="autofit-col">
									{country.symbol && (
										<ClayIcon symbol={country.symbol} />
									)}
								</div>

								<div
									className="autofit-col"
									style={{minWidth: '30px'}}
								>
									{`+${country.idd}`}
								</div>

								<div className="autofit-col autofit-col-expand">
									{country.name}
								</div>
							</div>
						</Option>
					)}
				</Picker>

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
			</div>

			<input name={name} type="hidden" value={combinedValue} />
		</FieldBase>
	);
};

export default PhoneNumber;
