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

export type DataProperties = {
	columns: Array<string | number>[];
	groups: Array<string | number>[];
};

export type DataChart = {
	[keys: string]: DataProperties;
};

export type MonthProperties = {
	achieved: number;
	exceeded: number;
	goals: number;
	index: number;
	label: string;
};

export type FilterMonth = {
	[keys: string]: MonthProperties;
};

export type Policy = {
	boundDate: string;
	productExternalReferenceCode: string;
	productName: string;
	termPremium: number;
};

export type ProductListType = {
	[keys: string]: ProductType;
};

type ProductType = {
	goalValue: number;
	productName: string;
	totalSales: number;
};

export type SalesGoal = {
	finalReferenceDate?: string;
	goalValue: number;
	initialReferenceDate?: string;
	productExternalReferenceCode: string;
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
