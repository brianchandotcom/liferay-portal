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

import {OrderBy, TFilter} from '../../utils/filter';
import {TFormattedItems, TItem, TTableRequestParams} from './types';
export declare function serializeTableRequestParams({
	filter: {type, value},
	keywords,
	pagination: {page, pageSize},
}: TTableRequestParams): string;
export declare function getOrderBy({type}: TFilter): OrderBy;
export declare function getOrderBySymbol({type}: TFilter): string;
export declare function getResultsLanguage(totalCount: number): string;
export declare function getGlobalChecked(
	formattedItems: TFormattedItems
): boolean;
export declare function updateFormattedItems(
	formattedItems: TFormattedItems,
	checked: boolean
): TFormattedItems;
export declare function getFormattedItems(items: TItem[]): TFormattedItems;
export declare function getIds(
	items: TFormattedItems,
	initialIds: number[]
): number[];
