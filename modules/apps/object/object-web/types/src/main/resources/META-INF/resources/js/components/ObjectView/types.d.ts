/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare type TWorkflowStatus = {
	label: string;
	value: string;
};
export declare type TObjectColumn = {
	defaultSort?: boolean;
	fieldLabel?: string;
	filterBy?: string;
	label: LocalizedValue<string>;
	objectFieldBusinessType?: string;
	objectFieldName: string;
	priority?: number;
	sortOrder?: string;
	type?: string;
	value?: string;
	valueList?: LabelValueObject[];
};
export declare type TObjectViewColumn = {
	defaultSort?: boolean;
	fieldLabel?: string;
	label: LocalizedValue<string>;
	objectFieldBusinessType?: string;
	objectFieldName: string;
	priority?: number;
};
export declare type TObjectViewSortColumn = {
	fieldLabel?: string;
	label: LocalizedValue<string>;
	objectFieldName: string;
	priority?: number;
	sortOrder?: string;
};
export declare type TObjectViewFilterColumn = {
	definition: {
		[key: string]: string[];
	} | null;
	disableEdit?: boolean;
	fieldLabel?: string;
	filterBy?: string;
	filterType: string | null;
	label: LocalizedValue<string>;
	objectFieldBusinessType?: string;
	objectFieldName: string;
	value?: string;
	valueList?: LabelValueObject[];
};
export declare type TObjectView = {
	defaultObjectView: boolean;
	name: LocalizedValue<string>;
	objectDefinitionId: number;
	objectViewColumns: TObjectViewColumn[];
	objectViewFilterColumns: TObjectViewFilterColumn[];
	objectViewSortColumns: TObjectViewSortColumn[];
};
export declare type TState = {
	creationLanguageId: Liferay.Language.Locale;
	filterOperators: TFilterOperators;
	isViewOnly: boolean;
	objectDefinitionExternalReferenceCode: string;
	objectFields: ObjectField[];
	objectView: TObjectView;
	objectViewId: string;
	workflowStatusJSONArray: TWorkflowStatus[];
};
