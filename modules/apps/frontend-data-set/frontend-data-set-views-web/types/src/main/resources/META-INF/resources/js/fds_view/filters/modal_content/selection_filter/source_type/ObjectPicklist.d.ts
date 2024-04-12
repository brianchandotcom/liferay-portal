/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {IPickList} from '../../../../../utils/types';
interface IObjectPicklistProps {
	includeMode: string;
	multiple: boolean;
	namespace: string;
	onIncludeModeChange: (val: string) => void;
	onMultipleChange: (val: boolean) => void;
	onPreselectedValuesChange: (val: any[]) => void;
	onSelectedPicklistChange: (val?: IPickList) => void;
	picklists: IPickList[];
	preselectedValues: any[];
	selectedPicklist?: IPickList;
}
declare function ObjectPicklist({
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
}: IObjectPicklistProps): JSX.Element;
export default ObjectPicklist;
