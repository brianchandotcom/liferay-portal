/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {IDataSet} from '../../../DataSets';
import {FDSViewType} from '../../../FDSViews';
import {IAction} from '../Actions';
declare const ActionForm: ({
	activeTab,
	dataSet,
	editing,
	initialValues,
	namespace,
	onCancel,
	onSave,
	spritemap,
}: {
	activeTab: number;
	dataSet: IDataSet | FDSViewType;
	editing?: boolean | undefined;
	initialValues?: IAction | undefined;
	namespace: string;
	onCancel: () => void;
	onSave: () => void;
	spritemap: string;
}) => JSX.Element;
export default ActionForm;
