/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import test from './index.mjs';

/**
 * Synchronously runs tests across multiple projects.
 * This is useful when running test at `./modules` level
 */
export default function syncTest() {
	test(true);
}
