/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
