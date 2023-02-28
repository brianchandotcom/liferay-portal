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

const KEY = '@testray';

const KEYS = {
	EXPORT_CASE_IDS: KEY + '/export-case-ids',
	LIST_VIEW: KEY + '/listview-',
	SIDEBAR: KEY + '/sidebar',
};

class TestrayStorage {
	static KEYS = KEYS;
	static STORAGE = localStorage;

	static getItem(key: keyof typeof KEYS): string | null {
		return TestrayStorage.STORAGE.getItem(key);
	}

	static key(index: number): string | null {
		return TestrayStorage.STORAGE.key(index);
	}

	static removeItem(key: string): void {
		return TestrayStorage.STORAGE.removeItem(key);
	}

	static setItem(key: string, value: string): void {
		return TestrayStorage.STORAGE.setItem(key, value);
	}
}

export default TestrayStorage;
