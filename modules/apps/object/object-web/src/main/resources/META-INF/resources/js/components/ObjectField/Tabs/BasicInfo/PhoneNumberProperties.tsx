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

export const DEFAULT_COUNTRIES: CountryInfo[] = [
	{a2: 'US', idd: '1', name: 'United States'},
	{a2: 'GB', idd: '44', name: 'United Kingdom'},
	{a2: 'DE', idd: '49', name: 'Germany'},
	{a2: 'FR', idd: '33', name: 'France'},
	{a2: 'IT', idd: '39', name: 'Italy'},
	{a2: 'ES', idd: '34', name: 'Spain'},
	{a2: 'PT', idd: '351', name: 'Portugal'},
	{a2: 'BR', idd: '55', name: 'Brazil'},
	{a2: 'JP', idd: '81', name: 'Japan'},
	{a2: 'CN', idd: '86', name: 'China'},
	{a2: 'IN', idd: '91', name: 'India'},
	{a2: 'AU', idd: '61', name: 'Australia'},
	{a2: 'CA', idd: '1', name: 'Canada'},
	{a2: 'MX', idd: '52', name: 'Mexico'},
	{a2: 'AR', idd: '54', name: 'Argentina'},
	{a2: 'CL', idd: '56', name: 'Chile'},
	{a2: 'CO', idd: '57', name: 'Colombia'},
	{a2: 'KR', idd: '82', name: 'South Korea'},
	{a2: 'NL', idd: '31', name: 'Netherlands'},
	{a2: 'BE', idd: '32', name: 'Belgium'},
	{a2: 'CH', idd: '41', name: 'Switzerland'},
	{a2: 'AT', idd: '43', name: 'Austria'},
	{a2: 'SE', idd: '46', name: 'Sweden'},
	{a2: 'NO', idd: '47', name: 'Norway'},
	{a2: 'DK', idd: '45', name: 'Denmark'},
	{a2: 'FI', idd: '358', name: 'Finland'},
	{a2: 'PL', idd: '48', name: 'Poland'},
	{a2: 'IE', idd: '353', name: 'Ireland'},
	{a2: 'NZ', idd: '64', name: 'New Zealand'},
	{a2: 'SG', idd: '65', name: 'Singapore'},
	{a2: 'ZA', idd: '27', name: 'South Africa'},
	{a2: 'RU', idd: '7', name: 'Russia'},
	{a2: 'TR', idd: '90', name: 'Turkey'},
	{a2: 'IL', idd: '972', name: 'Israel'},
	{a2: 'AE', idd: '971', name: 'United Arab Emirates'},
	{a2: 'SA', idd: '966', name: 'Saudi Arabia'},
	{a2: 'TH', idd: '66', name: 'Thailand'},
	{a2: 'PH', idd: '63', name: 'Philippines'},
	{a2: 'ID', idd: '62', name: 'Indonesia'},
	{a2: 'MY', idd: '60', name: 'Malaysia'},
];

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
		DEFAULT_COUNTRIES.find((country) => `+${country.idd}` === prefix) ||
		DEFAULT_COUNTRIES[0];

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
				value: `+${DEFAULT_COUNTRIES[0].idd}`,
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
							items={DEFAULT_COUNTRIES}
							onSelectionChange={(key) => {
								const selectedCountry = DEFAULT_COUNTRIES.find(
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
