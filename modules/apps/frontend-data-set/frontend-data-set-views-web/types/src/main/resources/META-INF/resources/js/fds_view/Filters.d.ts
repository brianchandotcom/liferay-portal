/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {FDSViewType} from '../FDSViews';
import {IFDSFilterClientExtension} from '../types';
import '../../css/Filters.scss';
interface IProps {
	fdsFilterClientExtensions: IFDSFilterClientExtension[];
	fdsView: FDSViewType;
	fdsViewsURL: string;
	namespace: string;
}
declare function Filters({
	fdsFilterClientExtensions,
	fdsView,
	fdsViewsURL,
	namespace,
}: IProps): JSX.Element;
export default Filters;
