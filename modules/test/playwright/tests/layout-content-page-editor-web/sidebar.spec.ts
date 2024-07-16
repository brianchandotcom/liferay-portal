/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {checkAccessibility} from '../../utils/checkAccessibility';
import getRandomString from '../../utils/getRandomString';

const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	featureFlagsTest({
		'LPS-169837': true,
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

const PANELS: SidebarTab[] = [
	'Fragments and Widgets',
	'Browser',
	'Page Design Options',
	'Page Rules',
	'Page Content',
	'Comments',
];

test('Renders all panel buttons in the vertical bar', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	await page.goto('/');

	// Create page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Check all panel buttons are rendered

	for (const panel of PANELS) {
		const panelButton = page.getByLabel(panel, {exact: true});

		await expect(panelButton).toBeVisible();
		await expect(panelButton).toHaveAttribute(
			'aria-selected',
			panel === PANELS[0] ? 'true' : 'false'
		);
	}
});

test('Renders sidebars visible at desktop size and sidebars not visible at small resolutions', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	await page.goto('/');

	// Create content page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	const panel = page.getByLabel('Fragments and Widgets Panel');
	const configurationPanel = page.getByLabel('Configuration Panel', {
		exact: true,
	});

	await expect(panel).toBeVisible();

	await expect(configurationPanel).toBeVisible();

	// Set small resolution and check panels are not visible

	await page.setViewportSize({height: 600, width: 600});

	await panel.waitFor({state: 'hidden'});

	await expect(panel).not.toBeVisible();

	await expect(configurationPanel).not.toBeVisible();
});

test('Checks if sidebars are open or closed depending on Product Menu', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	await page.goto('/');

	// Create content page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Check panels are visible

	const panel = page.getByLabel('Fragments and Widgets Panel');
	const configurationPanel = page.getByLabel('Configuration Panel', {
		exact: true,
	});

	// Check if sidebars are not visible when Product Menu is open

	await page.getByLabel('Open Product Menu').click();

	await expect(panel).not.toBeVisible();

	await expect(configurationPanel).not.toBeVisible();

	// Check if sidebars are visible when Product Menu is closed

	await page
		.getByLabel('Product Menu', {exact: true})
		.getByLabel('Close')
		.click();

	await expect(panel).toBeVisible();

	await expect(configurationPanel).toBeVisible();
});

test('Checks sidebar accessibility', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {

	// Create page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Check where the focus goes when the sidebar is closed

	await page.getByRole('button', {name: 'Close'}).press('Enter');

	await expect(
		page.getByLabel('Fragments and Widgets', {exact: true})
	).toBeFocused();

	// Check with axe

	await checkAccessibility({
		page,
		selectors: ['.page-editor__sidebar'],
	});
});
