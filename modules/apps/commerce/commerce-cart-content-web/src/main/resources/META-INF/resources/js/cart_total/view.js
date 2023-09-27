/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {delegate} from 'frontend-js-web';

export default function ({namespace}) {
	const requestQuote = document.getElementById(`${namespace}requestQuote`);

	let delegateHandler = null;

	if (requestQuote) {
		delegateHandler = delegate(
			requestQuote,
			'click',
			'.request-quote',
			(event) => {
				window[`${namespace}requestQuote`](event);
			}
		);
	}

	Liferay.after('current-order-updated', () => {
		Liferay.Portlet.refresh(`#p_p_id${namespace}`);
	});

	return {
		dispose() {
			if (delegateHandler) {
				delegateHandler.dispose();
			}
		},
	};
}
