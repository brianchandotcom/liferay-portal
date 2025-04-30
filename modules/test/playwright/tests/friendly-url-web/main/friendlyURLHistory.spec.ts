/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import {pagesPagesTest} from '../../layout-admin-web/main/fixtures/pagesPagesTest';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	loginTest(),
	pagesAdminPagesTest,
	pagesPagesTest
);

test('Ensure that the old friendlyURL can be restored from the history', async ({
	apiHelpers,
	page,
	pageConfigurationPage,
	pagesAdminPage,
	site,
}) => {
	const pageName = 'Test Page';

	await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: pageName,
	});

	const oldFriendlyURL = '/test-page';

	const newFriendlyURL = '/new-test-page';

	await pagesAdminPage.goto(site.friendlyUrlPath);

	await pageConfigurationPage.goToSection(pageName, 'General');

	await pageConfigurationPage.setFriendlyURL(newFriendlyURL, 'english');

	await page.getByLabel('History').click();

	await expect(page.locator('p.active-url-text')).toHaveText(newFriendlyURL);

	await expect(page.locator('li.list-group-item')).toHaveText(oldFriendlyURL);

	await page.locator('li').filter({hasText: oldFriendlyURL}).hover();

	await page
		.getByRole('dialog', {name: 'History'})
		.getByRole('button')
		.nth(2)
		.click();

	await expect(page.locator('p.active-url-text')).toHaveText(oldFriendlyURL);

	await expect(page.locator('li.list-group-item')).toHaveText(newFriendlyURL);

	const response = await page.goto(
		'/web' + site.friendlyUrlPath + oldFriendlyURL
	);

	expect(response.status()).toBe(200);
});

test('Ensure that both the active URL and old friendlyURL can be viewed from the history', async ({
	apiHelpers,
	page,
	pageConfigurationPage,
	pagesAdminPage,
	site,
}) => {
	const pageName = 'Test Page';

	await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: pageName,
	});

	const newFriendlyURL = '/new-test-page';

	await pagesAdminPage.goto(site.friendlyUrlPath);

	await pageConfigurationPage.goToSection(pageName, 'General');

	await pageConfigurationPage.setFriendlyURL(newFriendlyURL, 'english');

	await page.getByLabel('History').click();

	await expect(page.locator('p.active-url-text')).toHaveText(newFriendlyURL);

	await expect(page.locator('li.list-group-item')).toHaveText('/test-page');
});

test('Ensure that both the active URL and old friendlyURL can be viewed from the history of a default page', async ({
	page,
	pageConfigurationPage,
	pagesAdminPage,
}) => {
	const newFriendlyURL = '/new-search';

	await pagesAdminPage.goto();

	await pageConfigurationPage.goToSection('Search', 'General');

	await pageConfigurationPage.setFriendlyURL(newFriendlyURL, 'english');

	await page.getByLabel('History').click();

	await expect(page.locator('p.active-url-text')).toHaveText(newFriendlyURL);

	await expect(page.locator('li.list-group-item')).toHaveText('/search');
});

test('Ensure that both the localized active URL and old friendlyURL can be viewed from the history', async ({
	apiHelpers,
	page,
	pageConfigurationPage,
	pagesAdminPage,
	site,
}) => {
	const pageName = 'Test Page';

	await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: pageName,
	});

	const newEnglishFriendlyURL = '/new-test-page';

	const newSpanishFriendlyURL = '/new-test-page-es';

	await pagesAdminPage.goto(site.friendlyUrlPath);

	await pageConfigurationPage.goToSection(pageName, 'General');

	await pageConfigurationPage.setFriendlyURL(
		newEnglishFriendlyURL,
		'english'
	);

	await page.getByRole('button', {name: 'English'}).nth(0).click();

	await page.getByRole('menuitem', {name: 'Spanish'}).click();

	await pageConfigurationPage.fillName('Test Page ES');

	await pageConfigurationPage.setFriendlyURL('/test-page-es', 'spanish');

	await page.getByRole('button', {name: 'English'}).nth(0).click();

	await page.getByRole('menuitem', {name: 'Spanish'}).click();

	await pageConfigurationPage.setFriendlyURL(
		newSpanishFriendlyURL,
		'spanish'
	);

	await page.getByLabel('History').click();

	await expect(page.locator('p.active-url-text')).toHaveText(
		newEnglishFriendlyURL
	);

	await expect(page.locator('li.list-group-item')).toHaveText('/test-page');

	await page.getByRole('button', {name: 'en-US'}).click();

	await page.getByRole('menuitem', {name: 'es-ES'}).click();

	await expect(page.locator('p.active-url-text')).toHaveText(
		newSpanishFriendlyURL
	);

	await expect(page.locator('li.list-group-item')).toHaveText(
		'/test-page-es'
	);
});

test('Ensure that the multiple old friendlyURLs can be viewed from the history and that the oldest friendlyURL is the last one', async ({
	apiHelpers,
	page,
	pageConfigurationPage,
	pagesAdminPage,
	site,
}) => {
	const pageName = 'Test Page';

	await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: pageName,
	});

	const newFriendlyURL = '/new-test-page-1';

	const oldFriendlyURL = '/new-test-page';

	await pagesAdminPage.goto(site.friendlyUrlPath);

	await pageConfigurationPage.goToSection(pageName, 'General');

	await pageConfigurationPage.setFriendlyURL(oldFriendlyURL, 'english');

	await pageConfigurationPage.setFriendlyURL(newFriendlyURL, 'english');

	await page.getByLabel('History').click();

	await expect(page.locator('p.active-url-text')).toHaveText(newFriendlyURL);

	await expect(page.locator('li.list-group-item').nth(0)).toHaveText(
		oldFriendlyURL
	);

	await expect(page.locator('li.list-group-item').nth(1)).toHaveText(
		'/test-page'
	);
});

test('Ensure that only the active URL can be viewed by default language from the history after localization without friendlyURL', async ({
	apiHelpers,
	page,
	pageConfigurationPage,
	pagesAdminPage,
	site,
}) => {
	const pageName = 'Test Page';

	await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: pageName,
	});

	const newFriendlyURL = '/new-test-page';

	await pagesAdminPage.goto(site.friendlyUrlPath);

	await pageConfigurationPage.goToSection(pageName, 'General');

	await pageConfigurationPage.setFriendlyURL(newFriendlyURL, 'english');

	await page.getByRole('button', {name: 'English'}).nth(0).click();

	await page.getByRole('menuitem', {name: 'Spanish'}).click();

	await pageConfigurationPage.fillName('Test Page ES');

	await pageConfigurationPage.save();

	await page.getByRole('button', {name: 'English'}).nth(0).click();

	await page.getByRole('menuitem', {name: 'Spanish'}).click();

	await page.getByLabel('History').click();

	await expect(page.locator('p.active-url-text')).toHaveText(newFriendlyURL);

	await expect(
		page.locator('li.list-group-item').filter({hasText: '/test-page'})
	).not.toBeVisible();
});
