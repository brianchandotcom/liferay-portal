/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare type TFilter = {
	type: OrderBy;
	value: string;
};
export declare enum OrderBy {
	Asc = 'asc',
	Desc = 'desc',
}
export declare const DEFAULT_FILTER: {
	type: OrderBy;
	value: string;
};
