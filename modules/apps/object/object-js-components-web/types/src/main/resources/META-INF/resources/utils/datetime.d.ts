/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export interface Date {
	formattedDate?: string;
	locale?: string;
	name?: string;
	predefinedValue?: string;
	rawDate?: string;
	value?: string;
	years?: {
		end: number;
		start: number;
	};
}
interface GenerateDateConfigurationsProps {
	defaultLanguageId: Liferay.Language.Locale;
	locale?: Liferay.Language.Locale;
	type: 'date' | 'date_time' | 'Date' | 'DateTime';
}
interface GenerateDateProps {
	isDateTime: boolean;
	momentFormat: string;
	serverFormat: string;
	value: string;
}
declare type maskItem = string | RegExp;
export declare function generateDateConfigurations({
	defaultLanguageId,
	locale,
	type,
}: GenerateDateConfigurationsProps): {
	clayFormat: any;
	firstDayOfWeek: any;
	isDateTime: boolean;
	momentFormat: any;
	placeholder: any;
	serverFormat: string;
	use12Hours: boolean;
};
export declare function generateDate({
	isDateTime,
	momentFormat,
	serverFormat,
	value,
}: GenerateDateProps): Date;
export declare function generateInputMask(
	momentFormat: string
): {
	mask: maskItem[];
	pipeFormat: string;
};
export {};
