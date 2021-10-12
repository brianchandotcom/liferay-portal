/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {fetch} from 'frontend-js-web';

export const HEADERS = new Headers({
	Accept: 'application/json',
	'Content-Type': 'application/json',
});

const ORDER_RULES_ENDPOINT = '/o/headless-commerce-admin-order/v1.0/order-rules/';

export default function ({corEntryId, currentURL, namespace}) {
	const name = document.getElementById(`${namespace}name`);
	const type = document.getElementById(`${namespace}type`);
	const handleSelectChange = () => {
		const url = new URL(
			ORDER_RULES_ENDPOINT + corEntryId,
			currentURL
		);
		fetch(url, {
			body: JSON.stringify({
				name: name.value,
				type: type.value,
			}),
			headers: HEADERS,
			method: 'PATCH',
		});
	};

	type.addEventListener('change', handleSelectChange);

	return {
		dispose() {
			type.removeEventListener('change', handleSelectChange);
		},
	};
}
