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
	'Accept': 'application/json',
	'Content-Type': 'application/json',
});

const DIAGRAMS_ENDPOINT = '/o/headless-commerce-admin-catalog/v1.0/diagrams/';

export default function ({diagramId, namespace}) {
	const type = document.getElementById(`${namespace}type`);

	const handleSelectChange = () => {
		const url = new URL(
			DIAGRAMS_ENDPOINT + diagramId,
			themeDisplay.getPortalURL()
		);

		fetch(url, {
			body: JSON.stringify({
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

export function updateDiagramColor(diagramId, color) {
	const url = new URL(
		DIAGRAMS_ENDPOINT + diagramId,
		themeDisplay.getPortalURL()
	);

	fetch(url, {
		body: JSON.stringify({
			color,
		}),
		headers: HEADERS,
		method: 'PATCH',
	});
}

export function updateDiagramRadius(diagramId, radius) {
	const url = new URL(
		DIAGRAMS_ENDPOINT + diagramId,
		themeDisplay.getPortalURL()
	);

	fetch(url, {
		body: JSON.stringify({
			radius,
		}),
		headers: HEADERS,
		method: 'PATCH',
	});
}
