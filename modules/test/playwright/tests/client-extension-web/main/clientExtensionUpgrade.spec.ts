/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';

const test = mergeTests(loginTest());

test.skip(
	'Can view Remote App Custom Element portlet after upgrade from 7.4.13',
	async ({page: _page}) => {
		// Requires pre-seeded database archive from 7.4.13 and specific portal
		// version. Not compatible with standard Playwright execution.
	}
);

test.skip(
	'Can view Remote App fields after upgrade from 7.3.10.1',
	async ({page: _page}) => {
		// Requires pre-seeded database archive from 7.3.10.1 and specific
		// portal version. Not compatible with standard Playwright execution.
	}
);
