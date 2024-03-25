/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		fetch(DIAGRAMS_ENDPOINT + diagramId, {
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
