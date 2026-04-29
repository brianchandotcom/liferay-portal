/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {SingleSelect} from '@liferay/object-js-components-web';
import React from 'react';

import {
	normalizeFieldSettings,
	updateFieldSettings,
} from '../../../../utils/fieldSettings';

export const PREFIX_TYPES = {
	DEFINE_BY_USER: 'defineByUser',
	FIXED: 'fixed',
} as const;

export type PrefixType = (typeof PREFIX_TYPES)[keyof typeof PREFIX_TYPES];

export interface CountryInfo {
	a2: string;
	idd: string;
	name: string;
}

const FLAG_ICON_MAP: Record<string, string> = {
	AD: 'ca-ad',
	AE: 'ar-sa',
	AR: 'es-ar',
	AT: 'de-at',
	AU: 'en-au',
	BE: 'nl-be',
	BG: 'bg-bg',
	BR: 'pt-br',
	CA: 'en-ca',
	CH: 'de-ch',
	CL: 'es-es',
	CN: 'zh-cn',
	CO: 'es-co',
	CZ: 'cs-cz',
	DE: 'de-de',
	DK: 'da-dk',
	EE: 'et-ee',
	ES: 'es-es',
	FI: 'fi-fi',
	FR: 'fr-fr',
	GB: 'en-gb',
	GR: 'el-gr',
	HK: 'zh-cn',
	HR: 'hr-hr',
	HU: 'hu-hu',
	ID: 'in-id',
	IE: 'en-ie',
	IL: 'iw-il',
	IN: 'hi-in',
	IR: 'fa-ir',
	IT: 'it-it',
	JP: 'ja-jp',
	KH: 'km-kh',
	KR: 'ko-kr',
	KZ: 'kk-kz',
	LA: 'lo-la',
	LT: 'lt-lt',
	MX: 'es-mx',
	MY: 'ms-my',
	NL: 'nl-nl',
	NO: 'no-no',
	NZ: 'en-au',
	PH: 'en-us',
	PL: 'pl-pl',
	PT: 'pt-pt',
	RO: 'ro-ro',
	RS: 'sr-rs',
	RU: 'ru-ru',
	SA: 'ar-sa',
	SE: 'sv-se',
	SG: 'en-us',
	SI: 'sl-si',
	SK: 'sk-sk',
	TH: 'th-th',
	TR: 'tr-tr',
	TW: 'zh-tw',
	UA: 'uk-ua',
	US: 'en-us',
	VN: 'vi-vn',
	ZA: 'en-gb',
};

function getFlagSymbol(a2: string): string {
	return FLAG_ICON_MAP[a2.toUpperCase()] || '';
}

interface IPhoneNumberPropertiesProps {
	countries: CountryInfo[];
	disabled?: boolean;
	objectFieldSettings: ObjectFieldSetting[];
	onSubmit?: (values?: Partial<ObjectField>) => void;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
}

const PickerTrigger = React.forwardRef<
	HTMLDivElement,
	{selectedCountry: CountryInfo & {symbol?: string}} & React.ComponentProps<
		typeof ClayButton
	>
>(function PickerTrigger({selectedCountry, ...otherProps}, ref) {
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

export function PhoneNumberProperties({
	countries,
	disabled,
	objectFieldSettings,
	onSubmit,
	setValues,
	values,
}: IPhoneNumberPropertiesProps) {
	const settings = normalizeFieldSettings(objectFieldSettings);

	const prefix = settings.prefix || '+1';
	const prefixType = settings.prefixType || PREFIX_TYPES.DEFINE_BY_USER;

	const selectedCountry =
		countries.find((country) => `+${country.idd}` === prefix) ||
		countries[0];

	const handlePrefixTypeChange = (value: PrefixType) => {
		let updatedSettings = updateFieldSettings(objectFieldSettings, {
			name: 'prefixType',
			value,
		});

		if (value === PREFIX_TYPES.DEFINE_BY_USER) {
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
			label: Liferay.Language.get('define-by-user'),
			value: PREFIX_TYPES.DEFINE_BY_USER,
		},
		{
			label: Liferay.Language.get('fixed'),
			value: PREFIX_TYPES.FIXED,
		},
	];

	return (
		<>
			<SingleSelect
				disabled={disabled}
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

						<Picker
							as={PickerTrigger}
							disabled={disabled}
							items={countries}
							onSelectionChange={(key) => {
								const selectedCountry = countries.find(
									(country) => country.a2 === key
								);

								if (selectedCountry) {
									handlePrefixChange(selectedCountry);
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
												{flagSymbol && (
													<ClayIcon
														symbol={flagSymbol}
													/>
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
					</ClayForm.Group>
				</div>
			)}
		</>
	);
}
