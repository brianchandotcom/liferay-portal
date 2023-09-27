/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
