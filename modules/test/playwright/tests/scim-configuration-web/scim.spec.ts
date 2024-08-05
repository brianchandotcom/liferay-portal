/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {SCIMConfigurationPage} from '../../pages/scim-configuraiton-web/SCIMConfigurationPage';

export const test = mergeTests(loginTest());

const RESET_SCIM_HELP_TEXT =
	'All the current SCIM Client related data and the generated OAuth 2 tokens is removed. This is necessary for being able to configure a new SCIM Client.';

test('smoke: test SCIM configuration options', async ({page}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();

	await scimConfigurationPage.configureSCIM('test', 'email');

	await scimConfigurationPage.generateToken();

	await scimConfigurationPage.revokeToken();

	await scimConfigurationPage.resetClientData();
});

test('LPD-23255 AC1 TC1: Reset SCIM Client provisioning data button is present', async ({
	page,
}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();

	await scimConfigurationPage.configureSCIM('Test SCIM Client', 'email');

	await scimConfigurationPage.resetClientData();
});

test('LPD-23255 AC2 TC2: Reset SCIM Client provisioning data button description is present', async ({
	page,
}) => {
	const scimConfigurationPage = new SCIMConfigurationPage(page);

	await scimConfigurationPage.goTo();

	await scimConfigurationPage.configureSCIM('Test SCIM Client', 'email');

	const tooltip = page.getByLabel(RESET_SCIM_HELP_TEXT).locator('use');

	expect(await tooltip).toBeDefined();

	await scimConfigurationPage.resetClientData();
});
