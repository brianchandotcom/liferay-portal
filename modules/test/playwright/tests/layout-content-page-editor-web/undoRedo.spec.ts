/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomId from '../../utils/getRandomId';
import {pageEditorPagesTest} from './fixtures/pageEditorPages';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest,
	pageEditorPagesTest
);

test('View Undo interaction state is cleared after refreshing the page', async ({
	apiHelpers,
	page,
	pageEditorPage,
}) => {
	await page.goto('/');

	// Create a site

	const site = await apiHelpers.headlessSite.createSite(getRandomId());

	// Create a page with a Heading fragment

	const headingId = getRandomId();

	const headingFragment = getFragmentDefinition(
		headingId,
		'BASIC_COMPONENT-heading'
	);

	const layout = await apiHelpers.headlessDelivery.createSitePage(
		site.id,
		getRandomId(),
		getPageDefinition([headingFragment])
	);

	// Go to edit mode of page

	await pageEditorPage.goToEditMode(site, layout);

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

	// Delete the site

	await apiHelpers.headlessSite.deleteSite(site.id);
});

test('Undo and Redo buttons work as expected', async ({
	apiHelpers,
	page,
	pageEditorPage,
}) => {
	await page.goto('/');

	// Create a site

	const site = await apiHelpers.headlessSite.createSite(getRandomId());

	// Create a page with a Tabs fragment

	const tabsId = getRandomId();

	const fragmentDefinition = getFragmentDefinition(
		tabsId,
		'BASIC_COMPONENT-tabs'
	);

	const layout = await apiHelpers.headlessDelivery.createSitePage(
		site.id,
		getRandomId(),
		getPageDefinition([fragmentDefinition])
	);

	// Go to edit mode of page

	await pageEditorPage.goToEditMode(site, layout);

	// Change number of tabs to 5

	await pageEditorPage.changeFragmentConfiguration(
		tabsId,
		'General',
		'Number of Tabs',
		'5'
	);

	// Delete tabs fragment

	await pageEditorPage.deleteFragment(tabsId);

	// Assert undo button is enabled and redo button is disabled

	await expect(pageEditorPage.undoButton).toBeEnabled();
	await expect(pageEditorPage.redoButton).toBeDisabled();

	// Undo deleting the fragment

	await pageEditorPage.undoButton.click();

	// Assert tabsfragment its present and configuration is not lost

	const tabsFragment = pageEditorPage.getFragment(tabsId);

	await expect(tabsFragment).toBeAttached();
	await expect(tabsFragment.getByText('Tab 5')).toBeVisible();

	// Undo changing number of tabs

	await pageEditorPage.undoButton.click();

	// Check tab 5 is not present

	await expect(tabsFragment.getByText('Tab 5')).not.toBeVisible();

	// Assert Undo button is disabled and Redo button is enabled

	await expect(pageEditorPage.undoButton).toBeDisabled();
	await expect(pageEditorPage.redoButton).toBeEnabled();

	// Delete the site

	await apiHelpers.headlessSite.deleteSite(site.id);
});

test('Undo history works as expected', async ({
	apiHelpers,
	page,
	pageEditorPage,
}) => {
	await page.goto('/');

	// Create a site

	const site = await apiHelpers.headlessSite.createSite(getRandomId());

	// Create a page with a Heading fragment

	const headingId = getRandomId();

	const fragmentDefinition = getFragmentDefinition(
		headingId,
		'BASIC_COMPONENT-heading'
	);

	const layout = await apiHelpers.headlessDelivery.createSitePage(
		site.id,
		getRandomId(),
		getPageDefinition([fragmentDefinition])
	);

	// Go to edit mode of page

	await pageEditorPage.goToEditMode(site, layout);

	// Assert History button is visible

	await expect(page.getByTitle('History')).toBeVisible();

	// Go to General Panel and change the Heading level 3 times

	await pageEditorPage.changeFragmentConfiguration(
		headingId,
		'General',
		'Heading Level',
		'h2'
	);

	await pageEditorPage.changeFragmentConfiguration(
		headingId,
		'General',
		'Heading Level',
		'h3'
	);

	await pageEditorPage.changeFragmentConfiguration(
		headingId,
		'General',
		'Heading Level',
		'h4'
	);

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

	await expect(
		pageEditorPage.getFragment(headingId).locator('h3')
	).toBeAttached();

	// Assert the new History position is updated

	await expect(
		pageEditorPage.undoHistory.getByRole('menuitem').nth(0)
	).toBeEnabled();

	await expect(
		pageEditorPage.undoHistory.getByRole('menuitem').nth(1)
	).toBeDisabled();

	// Delete the site

	await apiHelpers.headlessSite.deleteSite(site.id);
});
