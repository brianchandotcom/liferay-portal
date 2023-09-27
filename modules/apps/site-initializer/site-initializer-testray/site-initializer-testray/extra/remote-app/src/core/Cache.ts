/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default class Cache {
	private static instance: Cache;
	private cache = new Map<string, any>();

	private constructor() {}

	public static getInstance(): Cache {
		if (!Cache.instance) {
			Cache.instance = new Cache();
		}

		return Cache.instance;
	}

	public set(key: string, value: unknown) {
		this.cache.set(key, value);
	}

	public get(key: string) {
		return this.cache.get(key);
	}
}
