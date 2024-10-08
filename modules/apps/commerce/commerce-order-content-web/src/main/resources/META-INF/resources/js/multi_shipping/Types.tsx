/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export interface IAPIResponseError {
	detail?: string;
	errorDescription?: string;
}

export interface ICountry {
	a2: string;
	a3: string;
	active: boolean;
	id: number;
	name: string;
	regions: Array<IRegion>;
	title_i18n: {
		[key: string]: string;
	};
}

export interface ICountryAPIResponse {
	items: Array<ICountry>;
}

export interface IDeliveryGroup {
	addressId: number;
	deliveryDate: string;
	id: number;
	name: string;
}

export interface IFieldError {
	[key: string]: string;
}

export interface IPostalAddress {
	addressCountry?: string;
	addressLocality?: string;
	addressRegion?: string;
	addressType?: string;
	externalReferenceCode?: string;
	id: number;
	name?: string;
	phoneNumber?: string;
	postalCode?: string;
	primary?: boolean;
	streetAddressLine1?: string;
	streetAddressLine2?: string;
	streetAddressLine3?: string;
	[key: string]: boolean | number | string | undefined;
}

export interface IPostalAddressAPIResponse {
	items: Array<IPostalAddress>;
}

export interface IRegion {
	active: boolean;
	countryId: number;
	id: number;
	label?: string;
	name: string;
	regionCode: string;
	title_i18n: {
		[key: string]: string;
	};
}
