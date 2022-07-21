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

/// <reference types="react" />

import './ModalAddFilter.scss';
export declare function ModalAddFilter({
	currentFilters,
	disableDateValues,
	editingFilter,
	editingObjectFieldName,
	header,
	objectFields,
	observer,
	onClose,
	onSave,
	workflowStatusJSONArray,
}: IProps): JSX.Element;
interface IProps {
	currentFilters: TCurrentFilter[];
	disableDateValues?: boolean;
	editingFilter: boolean;
	editingObjectFieldName: string;
	header: string;
	objectFields: ObjectField[];
	observer: any;
	onClose: () => void;
	onSave: (
		filterBy?: string,
		fieldLabel?: LocalizedValue<string>,
		objectFieldBusinessType?: string,
		filterType?: string,
		objectFieldName?: string,
		valueList?: IItem[],
		value?: string
	) => void;
	workflowStatusJSONArray: TWorkflowStatus[];
}
interface IItem extends LabelValueObject {
	checked?: boolean;
}
declare type TCurrentFilter = {
	definition: {
		[key: string]: string[];
	} | null;
	fieldLabel: string;
	filterBy: string;
	filterType: string | null;
	label: TName;
	objectFieldBusinessType?: string;
	objectFieldName: string;
	value?: string;
	valueList?: LabelValueObject[];
};
declare type TWorkflowStatus = {
	label: string;
	value: string;
};
declare type TName = {
	[key: string]: string;
};
export {};
