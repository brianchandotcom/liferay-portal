/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare function getCurrentURLParamValue({
	paramSufix,
	portletId,
}: {
	paramSufix: string;
	portletId: string;
}): string | null;
export declare function getFilterRelatedItemURL({
	apiURLPath,
	filterQuery,
}: {
	apiURLPath: string;
	filterQuery: string;
}): string;
export declare function getCurrentNavFromURL({
	portletId,
}: {
	portletId: string;
}): ActiveNav;
export declare function openEditURL({
	editURL,
	id,
	portletId,
}: {
	editURL: string;
	id: number;
	portletId: string;
}): void;
export declare function updateHistory({
	navState,
	portletId,
}: {
	navState: string;
	portletId: string;
}): void;
