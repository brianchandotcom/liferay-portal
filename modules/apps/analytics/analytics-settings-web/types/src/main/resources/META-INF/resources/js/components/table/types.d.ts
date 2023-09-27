/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {TFilter} from '../../utils/filter';
export declare enum EColumnAlign {
	Left = 'start',
	Right = 'end',
}
export declare type TColumn = {
	align?: EColumnAlign;
	expanded?: boolean;
	id: string;
	label: string;
	show?: boolean;
	sortable?: boolean;
};
export declare type TColumnItem = {
	cellRenderer?: (item: TItem) => JSX.Element;
	id: string;
	value: boolean | string | number;
};
export declare type TItem = {
	checked?: boolean;
	columns: TColumnItem[];
	disabled?: boolean;
	id: string;
};
export declare type TFormattedItems = {
	[key: string]: TItem;
};
export declare type TTableRequestParams = {
	filter: TFilter;
	keywords: string;
	pagination: {
		page: number;
		pageSize: number;
	};
};
