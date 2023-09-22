/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {IPickList} from '../../types';
declare function Header(): JSX.Element;
interface IBodyProps {
	includeMode: string;
	multiple: boolean;
	namespace: string;
	onIncludeModeChange: (val: string) => void;
	onMultipleChange: (val: boolean) => void;
	onPreselectedValuesChange: (val: any[]) => void;
	onSelectedPicklistChange: (val?: IPickList) => void;
	picklists: IPickList[];
	preselectedValues?: any[];
	selectedPicklist?: IPickList;
}
declare function Body({
	includeMode,
	multiple,
	namespace,
	onIncludeModeChange,
	onMultipleChange,
	onPreselectedValuesChange,
	onSelectedPicklistChange,
	picklists,
	preselectedValues,
	selectedPicklist,
}: IBodyProps): JSX.Element;
declare const _default: {
	Body: typeof Body;
	Header: typeof Header;
};
export default _default;
