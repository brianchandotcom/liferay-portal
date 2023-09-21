/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export enum EFilterType {
	CLIENT_EXTENSION = 'CLIENT_EXTENSION',
	DATE_RANGE = 'DATE_RANGE',
	SELECTION = 'SELECTION',
}

export enum EFieldFormat {
	DATE = 'date',
	DATE_TIME = 'date-time',
	STRING = 'string',
}

export interface IField {
	format: EFieldFormat;
	label: string;
	name: string;
	type: string;
}

export interface IFilter {
	fieldName: string;
	filterType?: EFilterType;
	id: number;
	label: string;
	name: string;
	type: string;
}

export interface IClientExtensionFilter extends IFilter {
	fdsFilterClientExtensionERC: string;
}

export interface IDateFilter extends IFilter {
	from: string;
	to: string;
}

export interface ISelectionFilter extends IFilter {
	include: boolean;
	listTypeDefinitionId: string;
	multiple: boolean;
	preselectedValues: string;
}

export interface IPickList {
	externalReferenceCode: string;
	id: string;
	listTypeEntries: IListTypeEntry[];
	name: string;
	name_i18n: {
		[key: string]: string;
	};
}

export interface IListTypeEntry {
	externalReferenceCode: string;
	id: number;
	key: string;
	name: string;
	name_i18n: {
		[key: string]: string;
	};
}
