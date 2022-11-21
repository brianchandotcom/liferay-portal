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

import {TFormattedItems} from '../components/table/Table';

export function getIds(items: TFormattedItems, initialIds: number[]): number[] {
	const ids = [...initialIds];

	Object.values(items).forEach((item) => {
		if (ids.length) {
			ids.forEach((id, index) => {
				if (id === Number(item.id) && !item.checked) {
					ids.splice(index, 1);
				}
				else if (id !== Number(item.id) && item.checked) {
					ids.push(Number(item.id));
				}
			});
		}
		else if (item.checked) {
			ids.push(Number(item.id));
		}
	});

	return [...new Set(ids)];
}
