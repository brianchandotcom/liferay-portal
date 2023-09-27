/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {delegate} from 'frontend-js-web';

export default function () {
	const delegateHandler = delegate(
		document.body,
		'change',
		'input[type="checkbox"]',
		(event) => {
			if (event.delegateTarget.checked) {
				document
					.querySelector(
						`input[type='hidden'][name='${event.delegateTarget.id}']`
					)
					.setAttribute('disabled', '');
			}
			else {
				document
					.querySelector(
						`input[type='hidden'][name='${event.delegateTarget.id}']`
					)
					.removeAttribute('disabled');
			}
		}
	);

	return {
		dispose() {
			delegateHandler.dispose();
		},
	};
}
