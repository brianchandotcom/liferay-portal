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

export const COLUMN_SIZE_MODULE_PER_ROW_SIZES = {
	1: {
		1: [12],
	},
	2: {
		1: [12, 12],
		2: [6, 6],
	},
	3: {
		1: [12, 12, 12],
		3: [4, 4, 4],
	},
	4: {
		1: [12, 12, 12, 12],
		2: [6, 6, 6, 6],
		4: [3, 3, 3, 3],
	},
	5: {
		1: [12, 12, 12, 12, 12],
		2: [6, 6, 4, 4, 4],
		5: [2, 2, 4, 2, 2],
	},
	6: {
		1: [12, 12, 12, 12, 12, 12],
		2: [6, 6, 6, 6, 6, 6],
		3: [4, 4, 4, 4, 4, 4],
		6: [2, 2, 2, 2, 2, 2],
	},
	12: {
		1: [12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12],
		2: [6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6],
		3: [4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4],
		6: [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2],
		12: [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
	},
};
