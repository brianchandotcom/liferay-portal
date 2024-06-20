/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {EmailNotificationTemplatePage} from '../pages/notification-web/EmailNotificationTemplatePage';
import {NotificationTemplatesPage} from '../pages/notification-web/NotificationTemplatesPage';
import {QueuePage} from '../pages/notification-web/QueuePage';
import {UserPersonalBarPage} from '../pages/product-navigation-user-personal-bar-web/UserPersonalBarPage';

const notificationPagesTest = test.extend<{
	emailNotificationTemplatePage: EmailNotificationTemplatePage;
	notificationTemplatesPage: NotificationTemplatesPage;
	queuePage: QueuePage;
	userPersonalBarPage: UserPersonalBarPage;
}>({
	emailNotificationTemplatePage: async ({page}, use) => {
		await use(new EmailNotificationTemplatePage(page));
	},
	notificationTemplatesPage: async ({page}, use) => {
		await use(new NotificationTemplatesPage(page));
	},
	queuePage: async ({page}, use) => {
		await use(new QueuePage(page));
	},
	userPersonalBarPage: async ({page}, use) => {
		await use(new UserPersonalBarPage(page));
	},
});

export {notificationPagesTest};
