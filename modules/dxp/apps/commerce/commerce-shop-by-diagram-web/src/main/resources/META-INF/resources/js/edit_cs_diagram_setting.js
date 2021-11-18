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

import {State} from '@liferay/frontend-js-state-web';
import {fetch, navigate, openToast} from 'frontend-js-web';
import {imageSelectorImageAtom} from 'item-selector-taglib';

export const HEADERS = new Headers({
	'Accept': 'application/json',
	'Content-Type': 'application/json',
});

const DIAGRAMS_ENDPOINT = '/o/headless-commerce-admin-catalog/v1.0/diagrams/';

export default function ({diagramId, namespace}) {
	const typeInput = document.getElementById(`${namespace}type`);

	const handleSelectChange = () => {
		const url = new URL(
			DIAGRAMS_ENDPOINT + diagramId,
			themeDisplay.getPortalURL()
		);

		fetch(url, {
			body: JSON.stringify({
				type: typeInput.value,
			}),
			headers: HEADERS,
			method: 'PATCH',
		});
	};

	typeInput.addEventListener('change', handleSelectChange);

	function handleDiagramImageChanged({fileEntryId}) {
		if (fileEntryId === '0') {
			return;
		}

		const publishInput = document.getElementById(`${namespace}publish`);

		publishInput.value = false;

		const fileEntryIdInput = document.getElementById(
			`${namespace}fileEntryId`
		);

		fileEntryIdInput.value = fileEntryId;

		const form = document.getElementById(`${namespace}fm`);

		fetch(form.action, {
			body: new FormData(form),
			method: 'POST',
		}).then((response) => {
			if (response.status === 200) {
				navigate(location.href);
			}
			else {
				response.json().then((error) => {
					openToast({
						title: error.message,
						type: 'danger',
					});
				});
			}
		});
	}

	const {dispose: unsubscribeImageSelector} = State.subscribe(
		imageSelectorImageAtom,
		handleDiagramImageChanged
	);

	return {
		dispose() {
			typeInput.removeEventListener('change', handleSelectChange);

			unsubscribeImageSelector();
		},
	};
}
