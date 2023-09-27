/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {TFilter} from '../../utils/filter';
import {TPagination} from '../../utils/pagination';
import {TFormattedItems} from './types';
export declare enum Events {
	ChangeFilter = 'CHANGE_FILTER',
	ChangeItem = 'CHANGE_ITEM',
	ChangeItems = 'CHANGE_ITEMS',
	ChangeKeywords = 'CHANGE_KEYWORDS',
	ChangePagination = 'CHANGE_PAGINATION',
	FormatData = 'FORMAT_DATA',
	Reload = 'RELOAD',
	ToggleGlobalCheckbox = 'TOGGLE_CHECKBOX',
}
declare type TState = {
	filter: TFilter;
	formattedItems: TFormattedItems;
	globalChecked: boolean;
	internalKeywords: string;
	keywords: string;
	pagination: TPagination;
	reload: () => void;
	rows: string[];
};
declare const useData: () => TState;
declare const useDispatch: () => any;
interface ITableContextProps {
	initialFilter?: TFilter;
	initialKeywords?: string;
	initialPagination?: TPagination;
}
declare const TableContext: React.FC<ITableContextProps>;
export {useData, useDispatch};
export default TableContext;
