/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {IClientExtensionRenderer} from '@liferay/frontend-data-set-web';
import {IDataSet} from '../DataSets';
import {FDSViewType} from '../FDSViews';
import {IFieldTreeItem} from '../utils/types';
export interface IDataSetSectionProps {
	backURL: string;
	dataSet: IDataSet | FDSViewType;
	fdsClientExtensionCellRenderers: IClientExtensionRenderer[];
	fdsFilterClientExtensions: IClientExtensionRenderer[];
	fieldTreeItems: Array<IFieldTreeItem>;
	namespace: string;
	onActiveSectionChange: (section: number) => void;
	onDataSetUpdate: (data: FDSViewType) => void;
	saveFDSFieldsURL: string;
	spritemap: string;
}
declare const DataSet: ({
	backURL,
	dataSetERC,
	fdsClientExtensionCellRenderers,
	fdsFilterClientExtensions,
	fdsViewId,
	namespace,
	saveFDSFieldsURL,
	spritemap,
}: {
	backURL: string;
	dataSetERC: string;
	fdsClientExtensionCellRenderers: IClientExtensionRenderer[];
	fdsFilterClientExtensions: IClientExtensionRenderer[];
	fdsViewId: string;
	namespace: string;
	saveFDSFieldsURL: string;
	spritemap: string;
}) => JSX.Element;
export default DataSet;
