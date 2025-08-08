/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export async function safeTeardown(fn: () => Promise<void>, timeout = 10000) {
	try {
		await Promise.race([
			fn(),
			new Promise((_, reject) =>
				setTimeout(
					() => reject(new Error('Timeout in teardown')),
					timeout
				)
			),
		]);
	}
	catch (error) {
		console.warn(error);
	}
}
