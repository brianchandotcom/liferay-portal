/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface IParameters {
	date: DateType;
	dateFieldName?: string;
	quantity: number;
	type: Type;
	unit: Unit;
}

interface IParametersProperties {
	date?: DateType;
	dateFieldName?: string;
	quantity?: number;
	type?: Type;
	unit?: Unit;
}

type DateType = 'customDate' | 'responseDate' | 'dateField';
type Unit = 'days' | 'months' | 'years';
type Type = 'customDate' | DateType;
