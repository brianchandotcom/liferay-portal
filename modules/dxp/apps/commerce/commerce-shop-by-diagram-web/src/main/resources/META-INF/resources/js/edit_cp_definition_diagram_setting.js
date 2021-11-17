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
import {fetch} from 'frontend-js-web';
import {imageSelectorImageAtom} from 'item-selector-taglib';

export const HEADERS = new Headers({
	'Accept': 'application/json',
	'Content-Type': 'application/json',
});

const DIAGRAMS_ENDPOINT = '/o/headless-commerce-admin-catalog/v1.0/diagrams/';

export default function ({diagramId, namespace}) {
	const inputType = document.getElementById(`${namespace}type`);

	const handleSelectChange = () => {
		const url = new URL(
			DIAGRAMS_ENDPOINT + diagramId,
			themeDisplay.getPortalURL()
		);

		fetch(url, {
			body: JSON.stringify({
				type: inputType.value,
			}),
			headers: HEADERS,
			method: 'PATCH',
		});
	};

	inputType.addEventListener('change', handleSelectChange);

	function handleDiagramImageChanged({fileEntryId, src}) {
		// eslint-disable-next-line no-console
		console.log(fileEntryId, src);

		// TODO:fetch then window.location.reload();

	}

	const {dispose: unsubscribeImageSelector} = State.subscribe(
		imageSelectorImageAtom,
		handleDiagramImageChanged
	);

	return {
		dispose() {
			inputType.removeEventListener('change', handleSelectChange);

			unsubscribeImageSelector();
		},
	};
}
