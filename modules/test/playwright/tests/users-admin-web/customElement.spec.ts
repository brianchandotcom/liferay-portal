/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {loginTest} from '../../fixtures/loginTest';
import {editCustomElementPageTest} from '../client-extension-web/fixtures/editCustomElementPageTest';
import {componentsPageTest} from '../configuration-admin-web/fixtures/ComponentsPageTest';

export const test = mergeTests(
	applicationsMenuPageTest,
	componentsPageTest,
	editCustomElementPageTest,
	loginTest()
);

test('LPD-39537 - Check that the name field of custom elements does not allow stored XSS injections', async ({
	applicationsMenuPage,
	componentsPage,
	editCustomElementPage,
	page,
}) => {
	const NAME = '<svg onload=alert(XSS injection)>';

	await editCustomElementPage.goto();

	await editCustomElementPage.nameInput.fill(NAME);
	await editCustomElementPage.htmlElementNameInput.fill('test-element');
	await editCustomElementPage.javaScriptURLInput.fill(
		'http://localhost:8080'
	);

	await editCustomElementPage.publish();

	await applicationsMenuPage.goToComponents();

	await expect(componentsPage.helpLink).toBeVisible();

	page.on('dialog', async () => {
		throw new Error('XSS detected');
	});

	await page.reload();

	await expect(page.getByText(NAME)).toBeVisible();
});
