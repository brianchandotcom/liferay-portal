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
import getRandomString from '../../utils/getRandomString';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

test('View Undo interaction state is cleared after refreshing the page', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	await page.goto('/');

	// Create a page with a Heading fragment

	const headingId = getRandomString();

	const headingFragment = getFragmentDefinition({
		id: headingId,
		key: 'BASIC_COMPONENT-heading',
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([headingFragment]),
		siteId: site.id,
		title: getRandomString(),
	});

	// Go to edit mode of page

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Assert undo button is disabled

	await expect(pageEditorPage.undoButton).toBeDisabled();

	// Select the fragment

	await pageEditorPage.selectFragment(headingId);

	// Go to Styles panel and set text to Align Center

	await pageEditorPage.goToConfigurationTab('Styles');
	await page.getByLabel('Align Center').click();

	// Assert undo button is enabled

	await expect(pageEditorPage.undoButton).toBeEnabled();

	// Refresh the page

	await page.reload();

	// Assert Undo button is disabled

	await expect(pageEditorPage.undoButton).toBeDisabled();
});

test('Undo and Redo buttons work as expected', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	await page.goto('/');

	// Create a page with a Tabs fragment

	const tabsId = getRandomString();

	const fragmentDefinition = getFragmentDefinition({
		id: tabsId,
		key: 'BASIC_COMPONENT-tabs',
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([fragmentDefinition]),
		siteId: site.id,
		title: getRandomString(),
	});

	const tabsFragment = pageEditorPage.getFragment(tabsId);

	// Go to edit mode of page

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Change number of tabs to 5

	await pageEditorPage.changeFragmentConfiguration({
		fieldLabel: 'Number of Tabs',
		fragmentId: tabsId,
		tab: 'General',
		value: '5',
	});

	await expect(tabsFragment.getByText('Tab 5')).toBeVisible();

	// Delete tabs fragment

	await pageEditorPage.deleteFragment(tabsId);

	await expect(tabsFragment).not.toBeAttached();

	// Assert undo button is enabled and redo button is disabled

	await expect(pageEditorPage.undoButton).toBeEnabled();
	await expect(pageEditorPage.redoButton).toBeDisabled();

	// Undo deleting the fragment

	await pageEditorPage.undoButton.click();

	await expect(tabsFragment).toBeAttached();

	// Assert tabsfragment its present and configuration is not lost

	await expect(tabsFragment.getByText('Tab 5')).toBeVisible();

	// Undo changing number of tabs

	await pageEditorPage.undoButton.click();

	// Check tab 5 is not present

	await expect(tabsFragment.getByText('Tab 5')).not.toBeVisible();

	// Assert Undo button is disabled and Redo button is enabled

	await expect(pageEditorPage.undoButton).toBeDisabled();
	await expect(pageEditorPage.redoButton).toBeEnabled();
});

test('Undo history works as expected', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	await page.goto('/');

	// Create a page with a Heading fragment

	const headingId = getRandomString();

	const fragmentDefinition = getFragmentDefinition({
		id: headingId,
		key: 'BASIC_COMPONENT-heading',
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([fragmentDefinition]),
		siteId: site.id,
		title: getRandomString(),
	});

	// Go to edit mode of page

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Assert History button is visible

	await expect(page.getByTitle('History')).toBeVisible();

	// Go to General Panel and change the Heading level 3 times

	await pageEditorPage.changeFragmentConfiguration({
		fieldLabel: 'Heading Level',
		fragmentId: headingId,
		tab: 'General',
		value: 'h2',
	});

	const headingFragment = pageEditorPage.getFragment(headingId);

	await expect(headingFragment.locator('h2')).toBeAttached();

	await pageEditorPage.changeFragmentConfiguration({
		fieldLabel: 'Heading Level',
		fragmentId: headingId,
		tab: 'General',
		value: 'h3',
	});

	await expect(headingFragment.locator('h3')).toBeAttached();

	await pageEditorPage.changeFragmentConfiguration({
		fieldLabel: 'Heading Level',
		fragmentId: headingId,
		tab: 'General',
		value: 'h4',
	});

	await expect(headingFragment.locator('h4')).toBeAttached();

	// Open the History dropdown and assert we have 3 + 1 Action including Undo All

	await page.getByTitle('History').click();

	await expect(
		pageEditorPage.undoHistory.locator('ul > li > button')
	).toHaveCount(4);

	// Assert the current (first) History position is disabled

	await expect(
		pageEditorPage.undoHistory.getByRole('menuitem').nth(0)
	).toBeDisabled();

	// Assert the heading fragment has the correct heading level after changing history

	await pageEditorPage.undoHistory.getByRole('menuitem').nth(1).click();

	await expect(headingFragment.locator('h3')).toBeAttached();

	// Assert the new History position is updated

	await expect(
		pageEditorPage.undoHistory.getByRole('menuitem').nth(0)
	).toBeEnabled();

	await expect(
		pageEditorPage.undoHistory.getByRole('menuitem').nth(1)
	).toBeDisabled();
});
