/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export interface CountryInfo {
	a2: string;
	idd: string;
	name: string;
	symbol?: string;
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

// Pre-sorted by IDD length descending for longest-match parsing

const COUNTRIES_BY_IDD_LENGTH = [...DEFAULT_COUNTRIES].sort(
	(a, b) => b.idd.length - a.idd.length
);

const FLAG_ICON_MAP: Record<string, string> = {
	AE: 'ar-sa',
	AR: 'es-ar',
	AT: 'de-at',
	AU: 'en-au',
	BE: 'nl-be',
	BR: 'pt-br',
	CA: 'en-ca',
	CH: 'de-ch',
	CL: 'es-es',
	CN: 'zh-cn',
	CO: 'es-co',
	DE: 'de-de',
	DK: 'da-dk',
	ES: 'es-es',
	FI: 'fi-fi',
	FR: 'fr-fr',
	GB: 'en-gb',
	ID: 'in-id',
	IE: 'en-ie',
	IL: 'iw-il',
	IN: 'hi-in',
	IT: 'it-it',
	JP: 'ja-jp',
	KR: 'ko-kr',
	MX: 'es-mx',
	MY: 'ms-my',
	NL: 'nl-nl',
	NO: 'no-no',
	NZ: 'en-au',
	PH: 'en-us',
	PL: 'pl-pl',
	PT: 'pt-pt',
	RU: 'ru-ru',
	SA: 'ar-sa',
	SE: 'sv-se',
	SG: 'en-us',
	TH: 'th-th',
	TR: 'tr-tr',
	US: 'en-us',
	ZA: 'en-gb',
};

export function getFlagSymbol(a2: string): string {
	return FLAG_ICON_MAP[a2.toUpperCase()] || '';
}

export function parsePhoneValue(value: string): {
	countryA2: string;
	localNumber: string;
} {
	if (!value || !value.startsWith('+')) {
		return {countryA2: '', localNumber: value || ''};
	}

	const digits = value.slice(1);

	for (const country of COUNTRIES_BY_IDD_LENGTH) {
		if (digits.startsWith(country.idd)) {
			return {
				countryA2: country.a2,
				localNumber: digits.slice(country.idd.length),
			};
		}
	}

	return {countryA2: '', localNumber: value};
}

export function getCombinedValue(
	countryA2: string,
	localNumber: string,
	countries: CountryInfo[] = DEFAULT_COUNTRIES
): string {
	const country = countries.find((c) => c.a2 === countryA2);

	if (country && localNumber) {
		return `+${country.idd}${localNumber}`;
	}

	return localNumber;
}
