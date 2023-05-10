/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
