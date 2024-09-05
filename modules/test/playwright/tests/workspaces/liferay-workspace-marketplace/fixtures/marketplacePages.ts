/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {BecomePublisherPage} from '../pages/become-publisher/becomePublisherPage';
import {CustomerDashboardAppDetailsPage} from '../pages/customer-dashboard/customerDashboardAppDetailsPage';
import {CustomerDashboardPage} from '../pages/customer-dashboard/customerDashboardPage';
import {PublisherAppPage} from '../pages/publisher-dashboard/publisherAppPage';
import {PublisherDashboardPage} from '../pages/publisher-dashboard/publisherDashboardPage';
import {PublisherDashboardSolutionDetailsPage} from '../pages/publisher-dashboard/publisherDashboardSolutionDetailsPage';
import {PublisherSolutionPage} from '../pages/publisher-dashboard/publisherSolutionPage';

const marketplacePagesTest = test.extend<{
	becomePublisherPage: BecomePublisherPage;
	customerDashboardAppDetailsPage: CustomerDashboardAppDetailsPage;
	customerDashboardPage: CustomerDashboardPage;
	publisherAppPage: PublisherAppPage;
	publisherDashboardPage: PublisherDashboardPage;
	publisherDashboardSolutionDetailsPage: PublisherDashboardSolutionDetailsPage;
	publisherSolutionPage: PublisherSolutionPage;
}>({
	becomePublisherPage: async ({page}, use) => {
		await use(new BecomePublisherPage(page));
	},
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
	publisherDashboardSolutionDetailsPage: async ({page}, use) => {
		await use(new PublisherDashboardSolutionDetailsPage(page));
	},
	publisherSolutionPage: async ({page}, use) => {
		await use(new PublisherSolutionPage(page));
	},
});

export {marketplacePagesTest};
