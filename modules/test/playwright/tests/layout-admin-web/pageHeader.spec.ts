/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import {pagesPagesTest} from './fixtures/pagesPagesTest';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pagesAdminPagesTest,
	pagesPagesTest,
	pageEditorPagesTest
);

test('Checks the correct label for restricted page in the page heading', async ({
	apiHelpers,
	page,
	site,
}) => {

	// Create a content page with only one permission

	const pageName = getRandomString();

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pagePermissions: [
			{
				actionKeys: ['VIEW'],
				roleKey: 'Owner',
			},
		],
		siteId: site.id,
		title: pageName,
	});

	// Go to the view mode and check the restricted page label

	await page.goto(
		`${liferayConfig.environment.baseUrl}/en/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
	);

	const header = page.getByRole('heading', {name: pageName});

	await header.waitFor({state: 'visible'});

	await expect(header.getByText('Restricted Page')).toBeVisible();
});

test('Checks page title in view mode and in edit mode', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {

	// Create a content page

	const pageName = getRandomString();

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		siteId: site.id,
		title: pageName,
	});

	// Check the page title in the view mode

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

	expect(await page.title()).toBe(`${pageName} - ${site.name} - Liferay DXP`);

	// Check the page title in the edit mode

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	expect(await page.title()).toBe(
		`${pageName} - ${site.name} - Liferay DXP (Editing)`
	);
});

test('Checks page SEO HTML title is not shown in edit mode', async ({
	apiHelpers,
	page,
	pageConfigurationPage,
	pageEditorPage,
	pagesAdminPage,
	site,
}) => {

	// Create page

	const pageName = getRandomString();

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		siteId: site.id,
		title: pageName,
	});

	// Change SEO HTML title

	await pagesAdminPage.goto(site.friendlyUrlPath);
	await pageConfigurationPage.goToSection(pageName, 'SEO');

	const HTMLTitle = getRandomString();

	await pageConfigurationPage.setHTMLTitle(HTMLTitle);

	// Check SEO HTML title is shown in view mode

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

	expect(await page.title()).toBe(
		`${HTMLTitle} - ${site.name} - Liferay DXP`
	);

	// Check SEO HTML title is not shown in view mode

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	expect(await page.title()).toBe(
		`${pageName} - ${site.name} - Liferay DXP (Editing)`
	);
});
