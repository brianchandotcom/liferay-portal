/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {TEmptyState} from '../table/StateRenderer';
import {TColumn, TTableRequestParams} from '../table/types';
import {TProperty} from './Properties';
export declare type TRawItem = {
	channelName?: string;
	friendlyURL?: string;
	id: string;
	name: string;
	siteName: string;
};
interface ITabProps {
	columns: Array<keyof TRawItem>;
	description?: string;
	emptyState: TEmptyState;
	enableCheckboxs?: boolean;
	header: TColumn[];
	initialIds: number[];
	onItemsChange: (ids: number[]) => void;
	property: TProperty;
	requestFn: (params: TTableRequestParams) => Promise<any>;
}
declare const Tab: React.FC<ITabProps>;
export default Tab;
