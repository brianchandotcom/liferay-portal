/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import React from 'react';

import {CountryInfo, getFlagSymbol} from './phoneNumberUtil';

type CountryCodePickerProps = Omit<
	React.ComponentProps<typeof Picker>,
	'children' | 'items' | 'onSelectionChange'
> & {
	countries: CountryInfo[];
	onSelectionChange: (country: CountryInfo) => void;
};

const PickerTrigger = React.forwardRef<
	HTMLDivElement,
	{selectedCountry: CountryInfo} & React.ComponentProps<typeof ClayButton>
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

export function CountryCodePicker({
	countries,
	disabled,
	onSelectionChange,
	selectedKey,
	...otherProps
}: CountryCodePickerProps) {
	const selectedCountry =
		countries.find((country) => country.a2 === selectedKey) || countries[0];

	return (
		<Picker
			{...otherProps}
			aria-label={Liferay.Language.get('country-code')}
			as={PickerTrigger}
			disabled={disabled}
			items={countries}
			onSelectionChange={(key) => {
				const country = countries.find((country) => country.a2 === key);

				if (country) {
					onSelectionChange(country);
				}
			}}
			searchable
			selectedCountry={selectedCountry}
			selectedKey={selectedCountry.a2}
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
