/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {test} from '@playwright/test';

import {QueuePage} from '../pages/notification-web/QueuePage';

const notificationPagesTest = test.extend<{
	queuePage: QueuePage;
}>({
	queuePage: async ({page}, use) => {
		await use(new QueuePage(page));
	},
});

export {notificationPagesTest};
