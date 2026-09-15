/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import {sub} from 'frontend-js-web';
import React from 'react';

import {
	CountryInfo,
	findCountry,
	getDefaultCountry,
	getFlagSymbol,
} from './phoneNumberUtil';

type CountryCodePickerProps = Omit<
	React.ComponentProps<typeof Picker>,
	'children' | 'items' | 'onSelectionChange'
> & {
	countries: CountryInfo[];
	onSelectionChange: (country: CountryInfo) => void;
};

const PickerTrigger = React.forwardRef<
	HTMLDivElement,
	{
		countryA2: string;
		selectedCountry?: CountryInfo;
	} & React.ComponentProps<typeof ClayButton>
>(({countryA2, selectedCountry, ...otherProps}, ref) => {
	if (selectedCountry) {
		const flagSymbol = getFlagSymbol(selectedCountry.a2);

		return (
			<button {...(otherProps as any)} ref={ref}>
				{flagSymbol && (
					<span className="inline-item inline-item-before">
						<ClayIcon symbol={flagSymbol} />
					</span>
				)}

				<span>{`+${selectedCountry.idd}`}</span>
			</button>
		);
	}

	const unavailableMessage = sub(
		Liferay.Language.get('x-is-not-available'),
		countryA2
	);

	return (
		<button {...(otherProps as any)} ref={ref} title={unavailableMessage}>
			<span className="text-danger">{countryA2}</span>

			<span className="sr-only">{unavailableMessage}</span>
		</button>
	);
});

export function CountryCodePicker({
	countries,
	disabled,
	onSelectionChange,
	selectedKey,
	...otherProps
}: CountryCodePickerProps) {
	const countryA2 = selectedKey ? String(selectedKey) : '';

	const selectedCountry = countryA2
		? findCountry(countries, countryA2)
		: getDefaultCountry(countries);

	return (
		<Picker
			{...otherProps}
			aria-label={Liferay.Language.get('country-code')}
			as={PickerTrigger}
			countryA2={countryA2}
			disabled={disabled}
			items={countries}
			onSelectionChange={(key) => {
				const country = findCountry(countries, String(key));

				if (country) {
					onSelectionChange(country);
				}
			}}
			searchable
			selectedCountry={selectedCountry}
			selectedKey={selectedCountry?.a2 ?? ''}
		>
			{(country: CountryInfo) => {
				const flagSymbol = getFlagSymbol(country.a2);

				return (
					<Option
						key={country.a2}
						textValue={`+${country.idd} ${country.name}`}
					>
						<div className="autofit-row">
							<div className="autofit-col">
								{flagSymbol && <ClayIcon symbol={flagSymbol} />}
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
	);
}
