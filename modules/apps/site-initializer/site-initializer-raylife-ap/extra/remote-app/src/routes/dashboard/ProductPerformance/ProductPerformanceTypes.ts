/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type DataPropertiesTypes = {
	columns: Array<string | number>[];
	groups: Array<string | number>[];
};

export type DataChartType = {
	[keys: string]: DataPropertiesTypes;
};

export type PolicyTypes = {
	boundDate: string;
	productExternalReferenceCode: string;
	productName: string;
	termPremium: number;
};

export type MonthTypes = [
	{
		achieved: (string | number)[];
		dataGroups: string[];
		exceeded: (string | number)[];
		goals: (string | number)[];
		label: string[];
		period: number;
		periodDate: string;
	}
];

export type MonthListType = {
	[keys: string]: MonthTypes;
};

export type MonthPropertiesTypes = [
	{
		achieved: number;
		exceeded: number;
		goals: number;
		index: number;
		label: string[];
		period: number;
		periodDate: string;
	}
];

export type FilterMonthType = {
	[keys: string]: MonthPropertiesTypes;
};

export type ProductListType = {
	[keys: string]: ProductTypes;
};

type ProductTypes = {
	goalValue: number;
	productExternalReferenceCode: string;
	productName: string;
	totalSales: number;
};

export type ProductPropertiesTypes = {
	externalReferenceCode: string;
	name: string;
};

export type SalesGoalTypes = {
	finalReferenceDate: string;
	goalValue: number;
	initialReferenceDate?: string;
	productExternalReferenceCode: string;
};

export type SalesPolicesTypes = {
	boundDate: string;
	productExternalReferenceCode: string;
	productName: string;
	termPremium: number;
};

export type GoalsArrayType = {
	year: {month: number}[];
};

export type BarChartPerformanceTypes = {
	colors: string[];
	dataColumns: string[];
	groups: string[];
	height: number;
	labelColumns: string[];
	showLegend: boolean;
	showTooltip: boolean;
	titleTotal: boolean;
	totalSum: number;
	width: number;
};
