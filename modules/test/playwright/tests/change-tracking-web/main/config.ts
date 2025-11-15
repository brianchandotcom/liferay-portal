/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const config = {
	dependencies: ['site-cms-site.main'],
	name: 'change-tracking-web.main',
	testDir: 'tests/change-tracking-web/main',
	use: {
		testIdAttribute: 'data-qa-id',
	},
};
