/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TreeView} from '@clayui/core';
import {ComponentProps} from 'react';
import {FDSViewType} from '../../../../FDSViews';
import {IFDSField} from '../../../../utils/types';
declare const AddFieldsModalContent: ({
	closeModal,
	fdsView,
	namespace,
	onSave,
	saveFDSFieldsURL,
	savedFDSFields,
	selectionMode,
}: {
	closeModal: Function;
	fdsView: FDSViewType;
	namespace: string;
	onSave: ({
		createdFDSFields,
		deletedFDSFieldsIds,
	}: {
		createdFDSFields: Array<IFDSField>;
		deletedFDSFieldsIds: Array<number>;
	}) => void;
	saveFDSFieldsURL: string;
	savedFDSFields: Array<IFDSField>;
	selectionMode?: ComponentProps<typeof TreeView>['selectionMode'];
}) => JSX.Element;
export default AddFieldsModalContent;
