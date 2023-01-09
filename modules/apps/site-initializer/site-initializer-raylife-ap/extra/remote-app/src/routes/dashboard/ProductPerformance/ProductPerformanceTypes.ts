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
