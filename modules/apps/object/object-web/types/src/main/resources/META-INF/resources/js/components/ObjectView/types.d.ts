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
