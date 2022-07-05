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

import './BuilderScreen.scss';
export declare function BuilderScreen({
	defaultSort,
	disableEdit,
	emptyState,
	filter,
	firstColumnHeader,
	hasDragAndDrop,
	objectColumns,
	onChangeColumnOrder,
	onDeleteColumn,
	onEditing,
	onEditingObjectFieldName,
	onVisibleEditModal,
	openModal,
	secondColumnHeader,
	thirdColumnHeader,
	title,
}: IProps): JSX.Element;
declare type TName = {
	[key: string]: string;
};
declare type TLabelValueObject = {
	label: string;
	value: string;
};
declare type TBuilderScreenColumn = {
	defaultSort?: boolean;
	disableEdit?: boolean;
	fieldLabel?: string;
	filterBy?: string;
	label: TName;
	objectFieldBusinessType?: string;
	objectFieldName: string;
	priority?: number;
	sortOrder?: string;
	type?: string;
	value?: string;
	valueList?: TLabelValueObject[];
};
interface IProps {
	defaultSort?: boolean;
	disableEdit?: boolean;
	emptyState: {
		buttonText: string;
		description: string;
		title: string;
	};
	filter?: boolean;
	firstColumnHeader: string;
	hasDragAndDrop?: boolean;
	objectColumns: TBuilderScreenColumn[];
	onChangeColumnOrder?: (draggedIndex: number, targetIndex: number) => void;
	onDeleteColumn: (objectFieldName: string) => void;
	onEditing?: (boolean: boolean) => void;
	onEditingObjectFieldName?: (objectFieldName: string) => void;
	onVisibleEditModal: (boolean: boolean) => void;
	openModal: () => void;
	secondColumnHeader: string;
	thirdColumnHeader?: string;
	title: string;
}
export {};
