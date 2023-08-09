/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getCurrentURLParamValue({
	paramSufix,
	portletId,
}: {
	paramSufix: string;
	portletId: string;
}) {
	const newURLSearchParams = new URLSearchParams(window.location.search);

	return newURLSearchParams.get(`_${portletId}_${paramSufix}`);
}

export function getFilterRelatedItemURL({
	apiURLPath,
	filterQuery,
}: {
	apiURLPath: string;
	filterQuery: string;
}) {
	return `${apiURLPath}/?filter=${filterQuery}`;
}

export function getCurrentNavFromURL({
	portletId,
}: {
	portletId: string;
}): ActiveNav {
	const currentURLParamValue = getCurrentURLParamValue({
		paramSufix: 'editAPIApplicationNav',
		portletId,
	});

	if (
		currentURLParamValue === 'endpoints' ||
		currentURLParamValue === 'schemas'
	) {
		return currentURLParamValue;
	}

	return 'details';
}

export function openEditURL({
	editURL,
	id,
	portletId,
}: {
	editURL: string;
	id: number;
	portletId: string;
}) {
	const newURL = new URL(editURL);

	const newURLSearchParams = new URLSearchParams(newURL.search);

	newURLSearchParams.set(`_${portletId}_apiApplicationId`, id.toString());
	window.location.search = newURLSearchParams.toString();
}

export function updateHistory({
	navState,
	portletId,
}: {
	navState: string;
	portletId: string;
}) {
	const newURL = new URL(window.location.href);

	newURL.searchParams.set(`_${portletId}_editAPIApplicationNav`, navState);
	window.history.replaceState({}, '', newURL);
}
