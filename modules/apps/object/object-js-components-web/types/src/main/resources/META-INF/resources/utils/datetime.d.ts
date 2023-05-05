/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

interface Date {
	formattedDate?: string;
	locale?: string;
	name?: string;
	predefinedValue?: string;
	rawDate?: string;
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
	months: any;
	placeholder: any;
	serverFormat: string;
	use12Hours: boolean;
	weekdaysShort: any;
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
