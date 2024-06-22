/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {EditServiceAccessPolicyPage} from '../pages/portal-security-service-access-policy-service/EditServiceAccessPolicyPage';
import {ServiceAccessPolicyPage} from '../pages/portal-security-service-access-policy-service/ServiceAccessPolicyPage';

const serviceAccessPolicyPageTest = test.extend<{
	editServiceAccessPolicyPage: EditServiceAccessPolicyPage;
	serviceAccessPolicyPage: ServiceAccessPolicyPage;
}>({
	editServiceAccessPolicyPage: async ({page}, use) => {
		await use(new EditServiceAccessPolicyPage(page));
	},
	serviceAccessPolicyPage: async ({page}, use) => {
		await use(new ServiceAccessPolicyPage(page));
	},
});

export {serviceAccessPolicyPageTest};
