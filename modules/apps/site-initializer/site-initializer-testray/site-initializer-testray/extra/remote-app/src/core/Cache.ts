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
