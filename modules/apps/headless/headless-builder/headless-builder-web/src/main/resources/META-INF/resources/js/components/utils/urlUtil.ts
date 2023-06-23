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
