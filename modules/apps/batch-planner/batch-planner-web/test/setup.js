/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import 'frontend-js-web/liferay/global.es';

window.Liferay.Language = {
	get: (key) => {
		let counter = 0;

		return key.replace(new RegExp('(^x-)|(-x-)|(-x$)', 'gm'), (match) =>
			match.replace('x', `{${counter++}}`)
		);
	},
};

window.Liferay.fire = (name, payload) => {
	const event = document.createEvent('CustomEvent');

	event.initCustomEvent(name);

	if (payload) {
		Object.keys(payload).forEach((key) => {
			event[key] = payload[key];
		});
	}

	window.dispatchEvent(event);
};

window.Liferay.on = (name, fn) => {
	window.addEventListener(name, fn);
};
