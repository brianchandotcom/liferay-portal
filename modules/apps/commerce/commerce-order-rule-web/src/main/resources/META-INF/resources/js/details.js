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

import {fetch} from 'frontend-js-web';

export const HEADERS = new Headers({
	'Accept': 'application/json',
	'Content-Type': 'application/json',
});

const ORDER_RULES_ENDPOINT =
	'/o/headless-commerce-admin-order/v1.0/order-rules/';

export default function ({corEntryId, currentURL, namespace}) {
	const typeSelect = document.getElementById(`${namespace}type`);

	const handleSelectChange = () => {
		const url = new URL(ORDER_RULES_ENDPOINT + corEntryId, currentURL);

		fetch(url, {
			body: JSON.stringify({
				name: document.getElementById(`${namespace}name`).value,
				type: typeSelect.value,
			}),
			headers: HEADERS,
			method: 'PATCH',
		});
	};

	typeSelect.addEventListener('change', handleSelectChange);

	return {
		dispose() {
			typeSelect.removeEventListener('change', handleSelectChange);
		},
	};
}
