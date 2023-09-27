/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default class Observer {
	constructor() {
		this.handlers = [];
	}

	subscribe(fn) {
		this.handlers.push(fn);
	}

	unsubscribe(fn) {
		this.handlers = this.handlers.filter((handler) => handler !== fn);
	}

	dispatch(value) {
		this.handlers.forEach((handler) => handler(value));
	}
}
