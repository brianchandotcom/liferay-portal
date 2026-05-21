/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {AuthServerLocalMetadatasPage} from '../pages/oauth-client-administration-web/AuthServerLocalMetadatasPage';
import {OAuthClientClientsPage} from '../pages/oauth-client-administration-web/OAuthClientClientsPage';
import {ProtectedResourceLocalMetadatasPage} from '../pages/oauth-client-administration-web/ProtectedResourceLocalMetadatasPage';

const oauthClientAdminPagesTest = test.extend<{
	authServerLocalMetadatasPage: AuthServerLocalMetadatasPage;
	oauthClientClientsPage: OAuthClientClientsPage;
	protectedResourceLocalMetadatasPage: ProtectedResourceLocalMetadatasPage;
}>({
	authServerLocalMetadatasPage: async ({page}, use) => {
		await use(new AuthServerLocalMetadatasPage(page));
	},
	oauthClientClientsPage: async ({page}, use) => {
		await use(new OAuthClientClientsPage(page));
	},
	protectedResourceLocalMetadatasPage: async ({page}, use) => {
		await use(new ProtectedResourceLocalMetadatasPage(page));
	},
});

export {oauthClientAdminPagesTest};
