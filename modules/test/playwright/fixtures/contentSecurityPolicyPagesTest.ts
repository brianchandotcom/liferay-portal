/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {ContentSecurityPolicyPage} from '../pages/portal-security-content-security-policy/ContentSecurityPolicyPage';

const contentSecurityPolicyPagesTest = test.extend<{
	contentSecurityPolicyPage: ContentSecurityPolicyPage;
}>({
	contentSecurityPolicyPage: async ({page}, use) => {
		await use(new ContentSecurityPolicyPage(page));
	},
});

export {contentSecurityPolicyPagesTest};
