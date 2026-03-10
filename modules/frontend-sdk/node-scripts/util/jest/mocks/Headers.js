/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

module.exports = class Headers {
	constructor(init) {
		if (init instanceof Headers) {
			return init;
		}

		const headerList = {};

		let entries = [];

		if (Array.isArray(init)) {
			entries = init;
		}
		else if (init && typeof init === 'object') {
			entries = Object.entries(init);
		}

		entries.forEach(([key, value]) => {
			key = key.toLowerCase();

			const headerValues = headerList[key] || [];

			headerValues.push(value);

			headerList[key] = headerValues;
		});

		this.headerList = headerList;
	}

	forEach(callback) {
		const entries = Object.entries(this.headerList);

		entries.forEach(([key, value]) => {
			callback(value.toString(), key);
		});
	}

	set(key, value) {
		this.headerList[key.toLowerCase()] = [value];
	}
};
