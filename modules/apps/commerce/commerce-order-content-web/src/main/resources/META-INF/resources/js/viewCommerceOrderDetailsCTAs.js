/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-web';

export default function ({namespace, viewReturnableCommerceOrderItemsURL}) {
	const formElement = document[`${namespace}fm`];
	const cmdInputElement = formElement[`${namespace}cmd`];

	Liferay.provide(window, `${namespace}handleCTA`, (cmdValue) => {
		if (cmdValue === 'makeReturn') {
			openModal({
				containerProps: {
					center: true,
				},
				height: '32rem',
				iframeBodyCssClass: 'w-100',
				size: 'lg',
				title: Liferay.Language.get('select-returnable-items'),
				url: viewReturnableCommerceOrderItemsURL,
			});
		}
		else {
			cmdInputElement.value = cmdValue;

			submitForm(formElement);
		}
	});
}
