/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {fragmentsPagesTest} from '../../fixtures/fragmentPagesTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {serviceAccessPolicyPageTest} from '../../fixtures/serviceAccessPolicyPageTest';
import getRandomString from '../../utils/getRandomString';
import performLogin, {performLogout} from '../../utils/performLogin';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	fragmentsPagesTest,
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest,
	serviceAccessPolicyPageTest
);

test('LPD-26931 Verify the new System REST Client Template Object SAP entry allows access for headless API accessed locally', async ({
	apiHelpers,
	editServiceAccessPolicyPage,
	fragmentEditorPage,
	fragmentsPage,
	page,
	pageEditorPage,
	serviceAccessPolicyPage,
	site,
}) => {

	// Go to fragment editor

	await fragmentsPage.goto(site.friendlyUrlPath);

	// Add a new fragment set and a fragment inside it

	const setName = getRandomString();

	await fragmentsPage.createFragmentSet(setName);

	await fragmentsPage.goto(site.friendlyUrlPath);

	await fragmentsPage.createFragment(setName, 'My Fragment');

	// Add a valid restClient call to the fragment

	await fragmentEditorPage.addHTML(
		'LPD-26931 ${restClient.get("/headless-delivery/v1.0/message-board-threads/ranked").totalCount}'
	);

	await fragmentEditorPage.publish();

	// Create a blank content page.  Adding fragmentDefinition during layout creation does not seem to work with restClient, so we'll drag and drop later

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		siteId: site.id,
		title: getRandomString(),
	});

	// Go to edit layout, drag and drop fragment onto page editor, and publish page

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	await page.getByRole('menuitem', {name: setName}).click();

	await page
		.getByText('My Fragment')
		.first()
		.dragTo(page.locator('#page-editor div').nth(1));

	await pageEditorPage.publishPage();

	// View created layout, logout, and verify guest user can view layout without errors

	await page.goto(`/web${site.friendlyUrlPath}/${layout.friendlyUrlPath}`);

	await expect(await page.getByText('LPD-26931 ')).toBeVisible();

	await performLogout(page);

	await page.goto(`/web${site.friendlyUrlPath}/${layout.friendlyUrlPath}`);

	await expect(await page.getByText('LPD-26931 ')).toBeVisible();

	// Log back in, disable System REST Client Template Object SAP entry

	await performLogin(page, 'test');

	await serviceAccessPolicyPage.goto(site.friendlyUrlPath);

	await (
		await page.getByRole('link', {
			name: 'SYSTEM_REST_CLIENT_TEMPLATE_OBJECT',
		})
	).click();

	await editServiceAccessPolicyPage.enabledButton.setChecked(false);

	await editServiceAccessPolicyPage.saveButton.click();

	await expect(
		await editServiceAccessPolicyPage.successMessage
	).toBeVisible();

	// Verify admin can still view layout

	await page.goto(`/web${site.friendlyUrlPath}/${layout.friendlyUrlPath}`);

	await expect(await page.getByText('LPD-26931 ')).toBeVisible();

	// Verify guest user receives freemarker error

	await performLogout(page);

	await page.goto(`/web${site.friendlyUrlPath}/${layout.friendlyUrlPath}`);

	await expect(
		await page.getByText('FreeMarker syntax is invalid.')
	).toBeVisible();

	// Restore System REST Client Template Object SAP entry

	await performLogin(page, 'test');

	await serviceAccessPolicyPage.goto(site.friendlyUrlPath);

	await (
		await page.getByRole('link', {
			name: 'SYSTEM_REST_CLIENT_TEMPLATE_OBJECT',
		})
	).click();

	await editServiceAccessPolicyPage.enabledButton.setChecked(true);

	await editServiceAccessPolicyPage.saveButton.click();

	await expect(
		await editServiceAccessPolicyPage.successMessage
	).toBeVisible();
});
