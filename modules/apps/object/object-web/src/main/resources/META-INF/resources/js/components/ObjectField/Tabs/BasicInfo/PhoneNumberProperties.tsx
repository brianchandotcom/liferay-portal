/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import {
	COUNTRY_SOURCE,
	CountryCodePicker,
	CountryInfo,
	CountrySource,
	SingleSelect,
	getDefaultCountry,
} from '@liferay/object-js-components-web';
import React from 'react';

import {
	normalizeFieldSettings,
	updateFieldSettings,
} from '../../../../utils/fieldSettings';

interface IPhoneNumberPropertiesProps {
	countries: CountryInfo[];
	disabled?: boolean;
	objectFieldSettings: ObjectFieldSetting[];
	onSubmit?: (values?: Partial<ObjectField>) => void;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
}

export function PhoneNumberProperties({
	countries,
	objectFieldSettings,
	onSubmit,
	setValues,
	values,
}: IPhoneNumberPropertiesProps) {
	const countryPickerId = React.useId();
	const countrySourceId = React.useId();

	const settings = normalizeFieldSettings(objectFieldSettings);

	const defaultCountry = getDefaultCountry(countries);

	const country = settings.country || defaultCountry.a2;
	const countrySource =
		settings.countrySource || COUNTRY_SOURCE.DEFINED_BY_USER;

	const selectedCountry =
		countries.find((c) => c.a2 === country) || defaultCountry;

	const handleCountrySourceChange = (value: CountrySource) => {
		let updatedSettings = updateFieldSettings(objectFieldSettings, {
			name: 'countrySource',
			value,
		});

		if (value === COUNTRY_SOURCE.DEFINED_BY_USER) {
			updatedSettings = updatedSettings.filter(
				(setting) => setting.name !== 'country'
			);
		}
		else if (value === COUNTRY_SOURCE.FIXED) {
			updatedSettings = updateFieldSettings(updatedSettings, {
				name: 'country',
				value: defaultCountry.a2,
			});
		}

		setValues({
			objectFieldSettings: updatedSettings,
		});

		if (onSubmit) {
			onSubmit({
				...values,
				objectFieldSettings: updatedSettings,
			});
		}
	};

	const handleCountryChange = (country: CountryInfo) => {
		const updatedSettings = updateFieldSettings(objectFieldSettings, {
			name: 'country',
			value: country.a2,
		});

		setValues({
			objectFieldSettings: updatedSettings,
		});

		if (onSubmit) {
			onSubmit({
				...values,
				objectFieldSettings: updatedSettings,
			});
		}
	};

	const countrySourceOptions = [
		{
			label: Liferay.Language.get('defined-by-user'),
			value: COUNTRY_SOURCE.DEFINED_BY_USER,
		},
		{
			label: Liferay.Language.get('fixed'),
			value: COUNTRY_SOURCE.FIXED,
		},
	];

	return (
		<>
			<SingleSelect
				id={countrySourceId}
				items={countrySourceOptions}
				label={Liferay.Language.get('country-source')}
				onSelectionChange={(value) =>
					handleCountrySourceChange(value as CountrySource)
				}
				selectedKey={countrySource as string}
			/>

			{countrySource === COUNTRY_SOURCE.FIXED && (
				<div className="form-group-autofit">
					<ClayForm.Group className="form-group-item-shrink">
						<label id={countryPickerId}>
							{Liferay.Language.get('country')}
						</label>

						<CountryCodePicker
							aria-labelledby={countryPickerId}
							countries={countries}
							onSelectionChange={handleCountryChange}
							selectedKey={selectedCountry.a2}
						/>
					</ClayForm.Group>
				</div>
			)}
		</>
	);
}
