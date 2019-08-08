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

export default class Headers {
	constructor(init) {
		if (init instanceof Headers) {
			return init;
		}

		const headerList = {};

		let entries = [];

		if (Array.isArray(init)) {
			entries = init;
		} else if (init && typeof init === 'object') {
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
}
