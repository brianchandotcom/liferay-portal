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

export function withEnv(env: 'production' | 'development', callback: Function) {
	const originalEnv = process.env.NODE_ENV;

	try {
		process.env.NODE_ENV = env;

		callback();
	}
	finally {
		if (typeof originalEnv === 'string') {
			process.env.NODE_ENV = originalEnv;
		}
		else if (typeof originalEnv === 'undefined') {
			delete process.env.NODE_ENV;
		}
	}
}
