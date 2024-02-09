/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TreeView} from '@clayui/core';
import {ComponentProps} from 'react';
import {FDSViewType} from '../FDSViews';
import {IField} from '../utils/types';
interface IFieldTreeItem extends IField {
	children?: IFieldTreeItem[];
	query?: string;
	selected?: boolean;
}
declare const FieldSelectModalContent: ({
	closeModal,
	fdsView,
	onSaveButtonClick,
	saveButtonDisabled,
	selectedFields,
	selectionMode,
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
}) => JSX.Element;
export default FieldSelectModalContent;
