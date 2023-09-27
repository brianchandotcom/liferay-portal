/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
