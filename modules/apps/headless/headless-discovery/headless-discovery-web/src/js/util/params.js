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

const setSearchParamsWithoutPageReload = (qs) => {
	const newurl = `${window.location.protocol}//${window.location.host}${window.location.pathname}?${qs}`;

	window.history.pushState({path: newurl}, '', newurl);
};

export const setSearchParam = (key, value) => {
	const searchParams = new URLSearchParams(window.location.search);

	if (value && value.length > 0) {
		searchParams.set(key, value);
	}
	else {
		searchParams.delete(key);
	}

	setSearchParamsWithoutPageReload(searchParams.toString());
};

export const getSearchParam = (key) => {
	const searchParams = new URLSearchParams(window.location.search);

	return searchParams.get(key) || null;
};
