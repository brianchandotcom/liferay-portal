/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {FDSViewType} from '../../../../FDSViews';
import {IFDSField} from '../Table';
declare const AddFieldsModalContent: ({
	closeModal,
	fdsView,
	namespace,
	onSave,
	saveFDSFieldsURL,
	savedFDSFields,
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
}) => JSX.Element;
export default AddFieldsModalContent;
