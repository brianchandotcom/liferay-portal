/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const config = {
	expect: {
		toMatchAriaSnapshot: {
			pathTemplate:
				'tests/frontend-taglib-clay/main/snapshots/{testFilePath}_{arg}{ext}',
		},
	},
	name: 'frontend-taglib-clay.main',
	testDir: 'tests/frontend-taglib-clay/main',
};
