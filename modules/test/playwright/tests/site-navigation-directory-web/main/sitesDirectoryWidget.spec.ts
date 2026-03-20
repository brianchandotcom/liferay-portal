/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageViewModePagesTest} from '../../../fixtures/pageViewModePagesTest';
import getRandomString from '../../../utils/getRandomString';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	pageViewModePagesTest
);

test('Ensure Sites Directory widget can display child site', async ({
	apiHelpers,
	page,
	site,
	widgetPagePage,
}) => {
	const childSite = await apiHelpers.headlessAdminSite.postSite({
		name: getRandomString(),
		parentSiteExternalReferenceCode: site.externalReferenceCode,
	});

	const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: getRandomString(),
	});

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

	await widgetPagePage.addPortlet('Sites Directory');

	await expect(page.locator('[id$="groupsSearchContainer_1"]')).toHaveText(
		site.name
	);

	await widgetPagePage.clickOnAction('Sites Directory', 'Configuration');

	const configurationIFrame = page.frameLocator(
		'iframe[title*="Sites Directory"]'
	);

	await configurationIFrame.getByLabel('Sites').selectOption('Children');

	await widgetPagePage.saveAndClose('Sites Directory');

	await expect(page.locator('[id$="groupsSearchContainer_1"]')).toHaveText(
		childSite.name
	);
});

test('Ensure Sites Directory widget can display parent site', async ({
	apiHelpers,
	page,
	site,
	widgetPagePage,
}) => {
	const childSite = await apiHelpers.headlessAdminSite.postSite({
		name: getRandomString(),
		parentSiteExternalReferenceCode: site.externalReferenceCode,
	});

	const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: childSite.id,
		title: getRandomString(),
	});

	await page.goto(`/web${childSite.friendlyUrlPath}${layout.friendlyURL}`);

	await widgetPagePage.addPortlet('Sites Directory');

	await widgetPagePage.clickOnAction('Sites Directory', 'Configuration');

	const configurationIFrame = page.frameLocator(
		'iframe[title*="Sites Directory"]'
	);

	await configurationIFrame.getByLabel('Sites').selectOption('Parent Level');

	await widgetPagePage.saveAndClose('Sites Directory');

	const breadcrumbEntries = await page
		.locator('[id*="groupsSearchContainer_"]')
		.allInnerTexts();

	await expect(breadcrumbEntries).toContainEqual(site.name);
});

test('Ensure Sites Directory widget can display sibling site', async ({
	apiHelpers,
	page,
	site,
	widgetPagePage,
}) => {
	const childSite1 = await apiHelpers.headlessAdminSite.postSite({
		name: getRandomString(),
		parentSiteExternalReferenceCode: site.externalReferenceCode,
	});

	const childSite2 = await apiHelpers.headlessAdminSite.postSite({
		name: getRandomString(),
		parentSiteExternalReferenceCode: site.externalReferenceCode,
	});

	const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: childSite1.id,
		title: getRandomString(),
	});

	await page.goto(`/web${childSite1.friendlyUrlPath}${layout.friendlyURL}`);

	await widgetPagePage.addPortlet('Sites Directory');

	await widgetPagePage.clickOnAction('Sites Directory', 'Configuration');

	const configurationIFrame = page.frameLocator(
		'iframe[title*="Sites Directory"]'
	);

	await configurationIFrame.getByLabel('Sites').selectOption('Siblings');

	await widgetPagePage.saveAndClose('Sites Directory');

	const breadcrumbEntries = await page
		.locator('[id*="groupsSearchContainer_"]')
		.allInnerTexts();

	await expect(breadcrumbEntries.length).toBe(2);

	await expect(breadcrumbEntries).toEqual(
		expect.arrayContaining([childSite1.name, childSite2.name])
	);
});
