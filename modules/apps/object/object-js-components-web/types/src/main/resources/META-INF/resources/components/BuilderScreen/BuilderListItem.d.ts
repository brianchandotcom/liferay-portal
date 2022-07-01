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

import React from 'react';
import './BuilderListItem.scss';
interface IProps {
	disableEdit?: boolean;
	hasDragAndDrop?: boolean;
	index: number;
	label?: string;
	objectFieldName: string;
	onChangeColumnOrder?: (draggedIndex: number, targetIndex: number) => void;
	onDeleteColumn: (objectFieldName: string) => void;
	onEditing?: (boolean: boolean) => void;
	onEditingObjectFieldName?: (objectFieldName: string) => void;
	onVisibleEditModal?: (boolean: boolean) => void;
	secondColumnValue?: string;
	thirdColumnValues?: TThirdColumnValues[] | string;
}
declare type TThirdColumnValues = {
	label: string;
	value: string;
};
declare const BuilderListItem: React.FC<IProps>;
export default BuilderListItem;
