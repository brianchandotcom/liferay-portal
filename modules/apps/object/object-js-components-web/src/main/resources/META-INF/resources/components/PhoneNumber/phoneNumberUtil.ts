/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import parsePhoneNumber from 'libphonenumber-js';

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

export const COUNTRY_SOURCE = {
	DEFINED_BY_USER: 'definedByUser',
	FIXED: 'fixed',
} as const;

export type CountrySource =
	(typeof COUNTRY_SOURCE)[keyof typeof COUNTRY_SOURCE];

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

export function getDefaultCountry(countries: CountryInfo[]): CountryInfo {
	const defaultLanguageCountryA2 =
		Liferay.ThemeDisplay.getDefaultLanguageId().split('_')[1] ?? '';

	return (
		countries.find((country) => country.a2 === defaultLanguageCountryA2) ||
		countries[0]
	);
}

export function getFlagSymbol(a2: string): string {
	return FLAG_ICON_MAP[a2.toUpperCase()] || '';
}

/**
 * Splits an E.164 phone number (e.g. "+18775433729") into its country and
 * local-number parts (e.g. {countryA2: "US", localNumber: "8775433729"}).
 *
 * Many countries share the same calling code, so the National Destination
 * Code is used to pick the right one — "+1877…" resolves to US while "+1416…"
 * resolves to CA. The detected country is only returned when it appears in
 * `countries`; otherwise the function falls back to matching the longest IDD
 * prefix from `countries`.
 *
 * Values that don't start with "+" are returned as-is in `localNumber` with an
 * empty `countryA2`.
 */
export function parsePhoneValue(
	value: string,
	countries: CountryInfo[] = DEFAULT_COUNTRIES
): {
	countryA2: string;
	localNumber: string;
} {
	if (!value || !value.startsWith('+')) {
		return {countryA2: '', localNumber: value || ''};
	}

	const parsed = parsePhoneNumber(value);

	if (parsed?.country) {
		const isSupportedCountry = countries.some(
			(country) => country.a2 === parsed.country
		);

		if (isSupportedCountry) {
			return {
				countryA2: parsed.country,
				localNumber: parsed.nationalNumber,
			};
		}
	}

	// Fallback: the parsed country is not in the backend-provided list (or the
	// number could not be parsed). Match by calling code against the allowed
	// countries, preferring the longest IDD prefix.

	const digits = value.slice(1); // Removes the "+" in the value.

	const countriesByIddLength = [...countries].sort(
		(a, b) => b.idd.length - a.idd.length
	);

	for (const country of countriesByIddLength) {
		if (digits.startsWith(country.idd)) {
			return {
				countryA2: country.a2,
				localNumber:
					parsed?.nationalNumber ?? digits.slice(country.idd.length),
			};
		}
	}

	return {countryA2: '', localNumber: value};
}
