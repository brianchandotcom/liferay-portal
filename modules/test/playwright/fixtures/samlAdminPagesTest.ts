/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {IdentityProviderConnectionsPage} from '../pages/saml-web/IdentityProviderConnectionsPage';
import {IdentityProviderPage} from '../pages/saml-web/IdentityProviderPage';
import {SamlAdminPage} from '../pages/saml-web/SamlAdminPage';
import {ServiceProviderConnectionsPage} from '../pages/saml-web/ServiceProviderConnectionsPage';

const samlAdminPagesTest = test.extend<{
	identityProviderConnectionsPage: IdentityProviderConnectionsPage;
	identityProviderPage: IdentityProviderPage;
	samlAdminPage: SamlAdminPage;
	serviceProviderConnectionsPage: ServiceProviderConnectionsPage;
}>({
	identityProviderConnectionsPage: async ({page}, use) => {
		await use(new IdentityProviderConnectionsPage(page));
	},
	identityProviderPage: async ({page}, use) => {
		await use(new IdentityProviderPage(page));
	},
	samlAdminPage: async ({page}, use) => {
		await use(new SamlAdminPage(page));
	},
	serviceProviderConnectionsPage: async ({page}, use) => {
		await use(new ServiceProviderConnectionsPage(page));
	},
});

export {samlAdminPagesTest};
