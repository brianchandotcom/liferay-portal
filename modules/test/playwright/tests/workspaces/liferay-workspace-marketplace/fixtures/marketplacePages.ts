/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {CustomerDashboardAppDetailsPage} from '../pages/customerDashboardAppDetailsPage';
import {CustomerDashboardPage} from '../pages/customerDashboardPage';
import {PublisherAppPage} from '../pages/publisherAppPage';
import {PublisherDashboardPage} from '../pages/publisherDashboardPage';
import {PublisherSolutionPage} from '../pages/publisherSolutionPage';

const marketplacePagesTest = test.extend<{
	customerDashboardAppDetailsPage: CustomerDashboardAppDetailsPage;
	customerDashboardPage: CustomerDashboardPage;
	publisherAppPage: PublisherAppPage;
	publisherDashboardPage: PublisherDashboardPage;
	publisherSolutionPage: PublisherSolutionPage;
}>({
	customerDashboardAppDetailsPage: async ({page}, use) => {
		await use(new CustomerDashboardAppDetailsPage(page));
	},
	customerDashboardPage: async ({page}, use) => {
		await use(new CustomerDashboardPage(page));
	},
	publisherAppPage: async ({page}, use) => {
		await use(new PublisherAppPage(page));
	},
	publisherDashboardPage: async ({page}, use) => {
		await use(new PublisherDashboardPage(page));
	},
	publisherSolutionPage: async ({page}, use) => {
		await use(new PublisherSolutionPage(page));
	},
});

export {marketplacePagesTest};
