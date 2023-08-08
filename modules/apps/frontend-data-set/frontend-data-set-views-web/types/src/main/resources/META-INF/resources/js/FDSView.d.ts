/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {IClientExtensionRenderer} from 'frontend-js-web';
import {FDSViewType} from './FDSViews';
interface IFDSViewSectionInterface {
	fdsClientExtensionCellRenderers: IClientExtensionRenderer[];
	fdsView: FDSViewType;
	fdsViewsURL: string;
	namespace: string;
	onFDSViewUpdate: (data: FDSViewType) => void;
	saveFDSFieldsURL: string;
}
interface IFDSViewInterface {
	fdsClientExtensionCellRenderers: IClientExtensionRenderer[];
	fdsViewId: string;
	fdsViewsURL: string;
	namespace: string;
	saveFDSFieldsURL: string;
}
declare const FDSView: ({
	fdsClientExtensionCellRenderers,
	fdsViewId,
	fdsViewsURL,
	namespace,
	saveFDSFieldsURL,
}: IFDSViewInterface) => JSX.Element;
export {IFDSViewSectionInterface};
export default FDSView;
