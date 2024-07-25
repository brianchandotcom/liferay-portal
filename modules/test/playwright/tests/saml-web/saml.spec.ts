/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {samlAdminPagesTest} from '../../fixtures/samlAdminPagesTest';
import {virtualInstancesPagesTest} from '../../fixtures/virtualInstancesPagesTest';
import getRandomString from '../../utils/getRandomString';
import {performLogout} from '../../utils/performLogin';
import {
	DEFAULT_IDP_NAME,
	DEFAULT_IDP_URL,
	DEFAULT_SP_NAME,
	DEFAULT_SP_URL,
	createSpAndIdpUser,
	deleteVirtualInstance,
	resetSamlKeystoreManagerTarget,
	setupSamlInstances,
	updateSamlKeystoreManagerTarget,
} from './utils/samlVirtualInstanceUtil';

export const test = mergeTests(
	loginTest(),
	samlAdminPagesTest,
	virtualInstancesPagesTest
);

test('Create two virtual instances, one IdP and one SP, connect them, perform SP initiated SSO, perform SP initiated SLO', async ({
	browser,
	page,
}) => {

	// Set the Keystore Manager Target to Doc Lib, so we can store multiple
	// certificates in one instance

	await updateSamlKeystoreManagerTarget(
		page,
		'Document Library Keystore Manager'
	);

	await setupSamlInstances(page, undefined, undefined);

	// Create a user with identical credentials on each instance

	const userAccount = await createSpAndIdpUser(
		browser,
		DEFAULT_IDP_NAME,
		DEFAULT_SP_NAME
	);

	// Create new page on SP virtual instance

	const spInstancePage = await browser.newPage({
		baseURL: DEFAULT_SP_URL,
	});

	// Login as the new user from SP

	await spInstancePage.goto('/');

	const signInButton = await spInstancePage.getByRole('button', {
		name: 'Sign In',
	});

	await signInButton.click();

	// Verify user is redirected to the IdP instance

	expect(
		await spInstancePage.getByText(
			'Redirecting to your identity provider...'
		)
	).toBeVisible();

	// Wait a few seconds for redirection, otherwise the expect clause will fail

	await spInstancePage.waitForTimeout(4000);

	// Verify user has been successfully redirected

	expect(await spInstancePage.url()).toContain(DEFAULT_IDP_URL);

	// Sign in

	await spInstancePage
		.getByLabel('Email Address')
		.fill(userAccount.emailAddress);
	await spInstancePage.getByLabel('Password').fill('test');
	await spInstancePage.getByLabel('Remember Me').check();
	await spInstancePage.getByRole('button', {name: 'Sign In'}).click();

	// Wait for authentication to complete, verify user is redirected back to SP

	await spInstancePage.waitForTimeout(4000);

	expect(await spInstancePage.url()).toContain(DEFAULT_SP_URL);

	// Verify user is logged in

	await expect(await page.getByTitle('User Profile Menu')).toBeVisible();

	// Logout, verify user is also logged out of IdP

	await performLogout(spInstancePage);

	expect(
		await spInstancePage.getByRole('button', {name: 'Sign In'})
	).toBeVisible();

	await spInstancePage.goto(DEFAULT_IDP_URL);

	expect(
		await spInstancePage.getByRole('button', {name: 'Sign In'})
	).toBeVisible();

	// Lastly, delete both virtual instances and reset the keystore target

	await deleteVirtualInstance(DEFAULT_IDP_NAME, page);

	await deleteVirtualInstance(DEFAULT_SP_NAME, page);

	await resetSamlKeystoreManagerTarget(page);
});

test('Create, edit, and delete a new virtual instance', async ({
	editVirtualInstancePage,
	virtualInstancesPage,
}) => {
	const name = getRandomString();

	await virtualInstancesPage.addNewVirtualInstance(name);

	const newName = getRandomString();

	await editVirtualInstancePage.editVirtualInstance(
		name,
		false,
		newName + '.com',
		'100',
		newName
	);

	expect(
		await virtualInstancesPage.page
			.getByRole('row')
			.getByText(name + ' ' + newName + ' ' + newName + '.com 1 100 No')
	).toBeVisible();

	await virtualInstancesPage.deleteVirtualInstance(name);
});
