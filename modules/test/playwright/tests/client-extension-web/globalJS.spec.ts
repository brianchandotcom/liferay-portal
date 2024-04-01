/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {pagesAdminPageTest} from '../../fixtures/PagesAdminPageTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {clientExtensionsPageTest} from './fixtures/clientExtensionsPageTest';
import {editJSClientExtensionsPageTest} from './fixtures/editJSClientExtensionsPageTest';

export const test = mergeTests(
	clientExtensionsPageTest,
	featureFlagsTest({
		'LPD-10981': true,
	}),
	loginTest(),
	pagesAdminPageTest,
	editJSClientExtensionsPageTest
);

test('Create a new JS with an attribute field', async ({
	clientExtensionsPage,
	editJSClientExtensionsPage,
	page,
	pagesAdminPage,
}) => {

	// Create new JS with a attribute

	await editJSClientExtensionsPage.goto();

	const clientExtensionName = getRandomString();
	const clientExtensionValue = getRandomString();

	await editJSClientExtensionsPage.nameInput.fill(clientExtensionName);

	await page
		.getByRole('textbox', {name: 'JavaScript URL'})
		.fill('https://www.google.com/script.js');

	await page
		.getByRole('textbox', {
			name: 'Attribute',
		})
		.fill('id');

	await page.getByLabel('Value', {exact: true}).fill(clientExtensionValue);

	await editJSClientExtensionsPage.editClientExtensionSubmitButton.click();

	await page.waitForLoadState();

	// Apply JS client extension to all pages

	await pagesAdminPage.selectJavaScriptClientExtension(clientExtensionName);

	await page.goto('/');

	await expect(
		page.locator(`script[id="${clientExtensionValue}"]`)
	).toBeAttached();

	// Clean up

	await clientExtensionsPage.goto();

	await clientExtensionsPage.deleteClientExtension(clientExtensionName);
});

test('The "src" attribute cannot be specified', async ({
	editJSClientExtensionsPage,
	page,
}) => {
	await editJSClientExtensionsPage.goto();

	await page
		.getByRole('textbox', {
			name: 'Attribute',
		})
		.fill('src');

	expect(page.getByText('Use the "JavaScript URL" field.')).toBeVisible();
});
