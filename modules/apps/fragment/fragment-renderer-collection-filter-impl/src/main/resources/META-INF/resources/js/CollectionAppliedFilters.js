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

import {delegate} from 'frontend-js-web';

export default function CollectionAppliedFilters({
	filterPrefix,
	removeButtonSelector,
}) {
	const clickHandler = delegate(
		document.body,
		'click',
		removeButtonSelector,
		(event) => {
			const {
				filterFragmentEntryLinkId,
				filterType,
				filterValue,
			} = event.delegateTarget.dataset;

			const paramName = `${filterPrefix}${filterType}_${filterFragmentEntryLinkId}`;
			const searchParams = [];
			const url = new URL(window.location.href);

			// Here we need to loop all parameters twice if we want to preserve
			// them sorted. First we eliminate unwanted parameters, then we
			// remove all of them and last we re-add preserved parameters
			// following same order.

			url.searchParams.forEach((value, key) => {
				if (value !== filterValue || key !== paramName) {
					searchParams.push([key, value]);
				}
			});

			url.search = '';

			searchParams.forEach(([key, value]) => {
				url.searchParams.append(key, value);
			});

			window.location.href = url.toString();
		}
	);

	return {
		dispose() {
			clickHandler.dispose();
		},
	};
}
