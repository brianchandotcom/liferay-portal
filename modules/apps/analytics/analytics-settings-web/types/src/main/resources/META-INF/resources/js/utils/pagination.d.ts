/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare const DELTAS: readonly [5, 10, 20, 30, 50];
export declare type TPagination = {
	maxCount: number;
	page: number;
	pageSize: typeof DELTAS[number];
	totalCount: number;
};
export declare const DEFAULT_PAGINATION: TPagination;
