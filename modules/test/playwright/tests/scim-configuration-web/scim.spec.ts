/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {SCIMConfigurationPage} from '../../pages/scim-configuraiton-web/SCIMConfigurationPage';

export const test = mergeTests(loginTest());

test('smoke: test SCIM configuration options', async ({page}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();

	await scimConfigurationPage.configureSCIM('test', 'email');

	await scimConfigurationPage.generateToken();

	await scimConfigurationPage.revokeToken();

	await scimConfigurationPage.resetClientData();
});
