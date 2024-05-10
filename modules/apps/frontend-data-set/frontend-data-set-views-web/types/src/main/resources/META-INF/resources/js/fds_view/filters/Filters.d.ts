/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {IClientExtensionRenderer} from '@liferay/frontend-data-set-web';
import {FDSViewType} from '../../FDSViews';
import {IFieldTreeItem} from '../../utils/types';
import '../../../css/Filters.scss';
interface IProps {
	fdsFilterClientExtensions: IClientExtensionRenderer[];
	fdsView: FDSViewType;
	fdsViewsURL: string;
	fieldTreeItems: Array<IFieldTreeItem>;
	namespace: string;
}
declare function Filters({
	fdsFilterClientExtensions,
	fdsView,
	fieldTreeItems: fields,
	namespace,
}: IProps): JSX.Element;
export default Filters;
