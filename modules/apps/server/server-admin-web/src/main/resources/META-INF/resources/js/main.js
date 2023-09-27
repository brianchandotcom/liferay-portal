/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {delegate} from 'frontend-js-web';

const MAP_DATA_PARAMS = {
	classname: 'className',
};

export default function ({namespace: portletNamespace, redirectURL, url}) {
	const form = document.getElementById(`${portletNamespace}fm`);

	const addInputsFromData = (data) => {
		Object.entries(data).forEach(([key, value]) => {
			key = MAP_DATA_PARAMS[key] || key;

			const nsKey = `${portletNamespace}${key}`;

			const input = document.createElement('input');
			input.name = nsKey;
			input.id = nsKey;
			input.value = value;
			input.type = 'hidden';

			form.append(input);
		});
	};

	const onSubmit = (event) => {
		const data = event.delegateTarget.dataset;

		const redirect = document.getElementById(`${portletNamespace}redirect`);

		if (redirect) {
			redirect.value = redirectURL;
		}

		addInputsFromData(data);

		submitForm(form, url);
	};

	const clickDelegate = delegate(
		form,
		'click',
		'.save-server-button',
		onSubmit
	);

	return {
		dispose() {
			clickDelegate.dispose();
		},
	};
}
