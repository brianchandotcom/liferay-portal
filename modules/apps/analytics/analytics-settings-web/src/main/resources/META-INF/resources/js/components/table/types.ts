/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {TFilter} from '../../utils/filter';

export enum EColumnAlign {
	Left = 'start',
	Right = 'end',
}

export type TColumn = {
	align?: EColumnAlign;
	expanded?: boolean;
	id: string;
	label: string;
	show?: boolean;
	sortable?: boolean;
};

export type TColumnItem = {
	cellRenderer?: (item: TItem) => JSX.Element;
	id: string;
	value: boolean | string | number;
};

export type TItem = {
	checked?: boolean;
	columns: TColumnItem[];
	disabled?: boolean;
	id: string;
};

export type TFormattedItems = {[key: string]: TItem};

export type TTableRequestParams = {
	filter: TFilter;
	keywords: string;
	pagination: {
		page: number;
		pageSize: number;
	};
};
