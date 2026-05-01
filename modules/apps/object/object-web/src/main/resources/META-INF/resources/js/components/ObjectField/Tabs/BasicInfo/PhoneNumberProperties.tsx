/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import {
	CountryCodePicker,
	CountryInfo,
	SingleSelect,
} from '@liferay/object-js-components-web';
import React from 'react';

import {
	normalizeFieldSettings,
	updateFieldSettings,
} from '../../../../utils/fieldSettings';

export const PREFIX_TYPES = {
	DEFINED_BY_USER: 'definedByUser',
	FIXED: 'fixed',
} as const;

export type PrefixType = (typeof PREFIX_TYPES)[keyof typeof PREFIX_TYPES];

export type {CountryInfo};

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
	const settings = normalizeFieldSettings(objectFieldSettings);

	const prefix = settings.prefix || '+1';
	const prefixType = settings.prefixType || PREFIX_TYPES.DEFINED_BY_USER;

	const selectedCountry =
		countries.find((country) => `+${country.idd}` === prefix) ||
		countries[0];

	const handlePrefixTypeChange = (value: PrefixType) => {
		let updatedSettings = updateFieldSettings(objectFieldSettings, {
			name: 'prefixType',
			value,
		});

		if (value === PREFIX_TYPES.DEFINED_BY_USER) {
			updatedSettings = updatedSettings.filter(
				(setting) => setting.name !== 'prefix'
			);
		}
		else if (value === PREFIX_TYPES.FIXED) {
			updatedSettings = updateFieldSettings(updatedSettings, {
				name: 'prefix',
				value: `+${countries[0].idd}`,
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

	const handlePrefixChange = (country: CountryInfo) => {
		const updatedSettings = updateFieldSettings(objectFieldSettings, {
			name: 'prefix',
			value: `+${country.idd}`,
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

	const prefixTypeOptions = [
		{
			label: Liferay.Language.get('defined-by-user'),
			value: PREFIX_TYPES.DEFINED_BY_USER,
		},
		{
			label: Liferay.Language.get('fixed'),
			value: PREFIX_TYPES.FIXED,
		},
	];

	return (
		<>
			<SingleSelect
				items={prefixTypeOptions}
				label={Liferay.Language.get('prefix-type')}
				onSelectionChange={(value) =>
					handlePrefixTypeChange(value as PrefixType)
				}
				selectedKey={prefixType as string}
			/>

			{prefixType === PREFIX_TYPES.FIXED && (
				<div className="form-group-autofit">
					<ClayForm.Group className="form-group-item-shrink">
						<label>{Liferay.Language.get('prefix')}</label>

						<CountryCodePicker
							countries={countries}
							onSelectionChange={handlePrefixChange}
							selectedKey={selectedCountry.a2}
						/>
					</ClayForm.Group>
				</div>
			)}
		</>
	);
}
