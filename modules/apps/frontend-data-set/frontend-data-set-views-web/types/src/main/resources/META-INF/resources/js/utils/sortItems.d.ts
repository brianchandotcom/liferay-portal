/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IOrderable} from './types';

/**
 * sorts the provided items array according to the itemsOrder comma-separated list of ids.
 * If array contains items not included in the list of ids, then those are appended after
 * Example:
 * 		items = [ {id: 1}, {id: 4}, {id: 2}, {id: 3} ]
 * 		itemsOrder = "2, 3, 1"
 * 		output is [ {id: 2}, {id: 3}, {id: 1}, {id: 4} ]
 * Optionally, not included items can be sorted by creation date
 *
 * @param items {IOrderable[]}
 * @param itemsOrder {string}
 * @param useCreationDate {boolean}
 * @returns {Array}
 */
export default function sortItems(
	items: IOrderable[],
	itemsOrder: string,
	useCreationDate?: boolean
): IOrderable[];
