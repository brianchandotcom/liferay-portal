/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import getFragmentDefinition from '../layout-content-page-editor-web/utils/getFragmentDefinition';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';
import {pagesPagesTest} from './fixtures/pagesPagesTest';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pagesAdminPagesTest,
	pagesPagesTest
);

test(
	'Some page names result in 404 friendly URLs',
	{
		tag: '@LPD-21554',
	},
	async ({apiHelpers, page}) => {
		const company =
			await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
				'liferay.com'
			);

		const group = await apiHelpers.jsonWebServicesGroup.getGroupByKey(
			company.companyId,
			'Guest'
		);

		// Create a page in Guest site with name matching a supported locale which
		// is not an available locale for the site

		const pageName = 'th';

		const sitePage = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getFragmentDefinition({
					id: getRandomString(),
					key: 'BASIC_COMPONENT-heading',
				}),
			]),
			siteId: group.groupId,
			title: pageName,
		});

		await page.goto(liferayConfig.environment.baseUrl);

		if (await page.getByText(pageName, {exact: true}).isVisible()) {
			await page.getByText(pageName, {exact: true}).click();
		}

		await expect.soft(page.getByText('Heading Example')).toBeVisible();

		await apiHelpers.jsonWebServicesLayout.deleteLayout(
			String(sitePage.id)
		);
	}
);

test('Navigating to the URL of an uncreated page does not throw errors', async ({
	apiHelpers,
	page,
	site,
}) => {

	// Create a page because the site needs a page to redirect to an Utility Page

	await apiHelpers.headlessDelivery.createSitePage({
		siteId: site.id,
		title: getRandomString(),
	});

	// Try to access a page that doesn't exist

	await page.goto(
		`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}/test-page-name`
	);

	// Doesn't show an alert but the default 404 Utility Page

	await expect(page.getByRole('alert')).toHaveCount(0);
	await expect(page.getByText('Error Code: 404')).toBeVisible();
});

test('Canonical URL doesnt change with localized Friendly URL', async ({
	apiHelpers,
	browser,
	pageConfigurationPage,
	pagesAdminPage,
	site,
}) => {
	await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getFragmentDefinition({
				id: getRandomString(),
				key: 'BASIC_COMPONENT-heading',
			}),
		]),
		siteId: site.id,
		title: 'Test Page Name',
	});

	// The configuration action must be available from the card
	// The configuration view should only allow setting the canonicalURL SEO field

	await pagesAdminPage.goto(site.friendlyUrlPath);
	await pageConfigurationPage.goToSection('Test Page Name', 'SEO');
	await pageConfigurationPage.setCanonicalURL(
		liferayConfig.environment.baseUrl + '/' + site.name + '/test-page-name'
	);

	// Create a new incognito browser context

	const context = await browser.newContext();

	// Create a new page inside context.

	const newPage = await context.newPage();

	await newPage.goto('/es/web/' + site.name + '/test-page-name');

	expect(
		await newPage.locator('link[rel="canonical"]').getAttribute('href')
	).toBe(
		liferayConfig.environment.baseUrl + '/' + site.name + '/test-page-name'
	);
});

test('Friendly URLs Only Display Locale Once', async ({
	apiHelpers,
	page,
	pageConfigurationPage,
	pagesAdminPage,
	site,
}) => {

	// Create page with a Heading fragment

	await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getFragmentDefinition({
				id: getRandomString(),
				key: 'BASIC_COMPONENT-heading',
			}),
		]),
		siteId: site.id,
		title: 'Test Page Name',
	});

	// The configuration action must be available from the card
	// The configuration view should only allow setting the FriendlyURL and the associated language

	await pagesAdminPage.goto(site.friendlyUrlPath);
	await pageConfigurationPage.goToSection('Test Page Name', 'General');
	await pageConfigurationPage.setFriendlyURL('/test-pagina', 'spanish');

	await page.goto('/es/web/' + site.name + '/test-page-name');

	const newURL = new URL(page.url());

	expect(newURL.pathname).toBe('/es/web/' + site.name + '/test-pagina');
});
