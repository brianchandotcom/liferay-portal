/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {AuthServerLocalMetadatasPage} from '../pages/oauth-client-administration-web/AuthServerLocalMetadatasPage';

const authServerLocalMetadatasPageTest = test.extend<{
	authServerLocalMetadatasPage: AuthServerLocalMetadatasPage;
}>({
	authServerLocalMetadatasPage: async ({page}, use) => {
		await use(new AuthServerLocalMetadatasPage(page));
	},
});

export {authServerLocalMetadatasPageTest};
