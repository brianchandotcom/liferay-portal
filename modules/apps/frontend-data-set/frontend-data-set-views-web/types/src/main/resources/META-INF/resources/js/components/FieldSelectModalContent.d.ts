/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TreeView} from '@clayui/core';
import {ComponentProps} from 'react';
import {FDSViewType} from '../FDSViews';
import {IField, IFieldTreeItem} from '../utils/types';
import '../../css/components/FieldSelectModalContent.scss';
export declare function visit(
	fields: Array<IFieldTreeItem>,
	callback: Function
): void;
declare const FieldSelectModalContent: ({
	closeModal,
	onSaveButtonClick,
	saveButtonDisabled,
	selectedFields,
	selectionMode,
	treeItems,
}: {
	closeModal: Function;
	fdsView: FDSViewType;
	onSaveButtonClick: ({
		selectedFields,
	}: {
		selectedFields: Array<IFieldTreeItem>;
	}) => void;
	saveButtonDisabled: boolean;
	selectedFields: Array<IField>;
	selectionMode?: ComponentProps<typeof TreeView>['selectionMode'];
	treeItems: Array<IFieldTreeItem>;
}) => JSX.Element;
export default FieldSelectModalContent;
