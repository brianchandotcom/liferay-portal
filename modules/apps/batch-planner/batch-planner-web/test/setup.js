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
