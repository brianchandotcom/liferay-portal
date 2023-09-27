/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
export declare function formattingItems(items: TItem[]): TFormattedItems;
export declare function selectFormattedItems(
	formattedItems: TFormattedItems,
	rows: string[]
): TFormattedItems;
export declare function getIds(
	items: TFormattedItems,
	initialIds: number[]
): number[];
