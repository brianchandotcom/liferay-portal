/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {PasswordPoliciesAdminPage} from '../pages/password-policies-admin-web/PasswordPoliciesAdminPage';

const passwordPoliciesAdminPageTest = test.extend<{
	passwordPoliciesAdminConfigPage: PasswordPoliciesAdminPage;
}>({
	passwordPoliciesAdminConfigPage: async ({page}, use) => {
		await use(new PasswordPoliciesAdminPage(page));
	},
});

export {passwordPoliciesAdminPageTest};
