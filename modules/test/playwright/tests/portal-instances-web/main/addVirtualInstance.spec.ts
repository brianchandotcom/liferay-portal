/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {notificationPagesTest} from '../../../fixtures/notificationPagesTest';
import {virtualInstancesPagesTest} from '../../../fixtures/virtualInstancesPagesTest';
import {
	assertDuplicateWebIdIsRejected,
	assertVirtualInstanceIsAddedAndNotified,
} from '../utils/addVirtualInstanceUtil';

const test = mergeTests(
	loginTest(),
	notificationPagesTest,
	virtualInstancesPagesTest
);

test(
	'Reports a duplicate web ID without starting the operation',
	{tag: '@LPD-93373'},
	async ({page, virtualInstancesPage}) =>
		assertDuplicateWebIdIsRejected(page, virtualInstancesPage)
);

test(
	'Acknowledges the start and notifies the completion',
	{tag: '@LPD-93373'},
	async ({page, userPersonalBarPage, virtualInstancesPage}) =>
		assertVirtualInstanceIsAddedAndNotified(
			page,
			userPersonalBarPage,
			virtualInstancesPage
		)
);
