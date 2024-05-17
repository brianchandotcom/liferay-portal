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
import {expectElementToHaveClass} from '../../utils/expectElementToHaveClass';
import getRandomString from '../../utils/getRandomString';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

test('allows moving through layout content with keyboard', async ({
	apiHelpers,
	pageEditorPage,
	site,
}) => {

	// Create a page with a Heading and a Card fragment

	const headingId = getRandomString();
	const headingDefinition = getFragmentDefinition(
		headingId,
		'BASIC_COMPONENT-heading'
	);

	const cardId = getRandomString();
	const cardDefinition = getFragmentDefinition(
		cardId,
		'BASIC_COMPONENT-card'
	);

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([cardDefinition, headingDefinition]),
		siteId: site.id,
		title: getRandomString(),
	});

	// Go to edit mode of page

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Check we can move with arrows

	const card = pageEditorPage.getTopper(cardId);
	const heading = pageEditorPage.getTopper(headingId);

	await card.focus();

	await card.press('ArrowDown');

	await expect(heading).toBeFocused();

	await heading.press('ArrowUp');

	await expect(card).toBeFocused();

	// Check we can select the fragment with Enter

	await card.press('Enter');

	await expect(card).toBeFocused();
	await expectElementToHaveClass(card, 'active');

	// Check we can move to editables with Tab

	const firstEditable = card.locator('img');
	const secondEditable = card.getByText('Card Title example');

	await card.press('Tab');

	await expect(firstEditable).toBeFocused();

	await firstEditable.press('Tab');

	await expect(secondEditable).toBeFocused();

	// Check we can also select the editable with Enter

	await secondEditable.press('Enter');

	await expect(secondEditable).toBeFocused();
	await expectElementToHaveClass(
		secondEditable,
		'page-editor__editable--active'
	);
});

test('focus order is correct', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {

	// Create a page with a Heading fragment

	const headingId = getRandomString();
	const headingDefinition = getFragmentDefinition(
		headingId,
		'BASIC_COMPONENT-heading'
	);

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([headingDefinition]),
		siteId: site.id,
		title: getRandomString(),
	});

	// Go to edit mode of page

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Check focus order is correct:
	// Sidebar > Fragment > Editable > Breadcrumb > Config sidebar

	const resizer = page.getByLabel('Resize Sidebar');
	const heading = pageEditorPage.getTopper(headingId);
	const editable = heading.getByText('Heading Example');
	const breadcrumbItem = page.getByRole('link', {name: 'Heading'});
	const generalTab = page.getByTitle('General', {exact: true});

	// Sidebar to fragment

	await resizer.focus();
	await resizer.press('Tab');

	await expect(heading).toBeFocused();

	// Fragment to editable

	await heading.press('Enter');
	await heading.press('Tab');

	await expect(editable).toBeFocused();

	// Editable to breadcrumb item

	await editable.press('Tab');

	await expect(breadcrumbItem).toBeFocused();

	// Breadcrumb item to config sidebar

	await breadcrumbItem.press('Tab');

	await expect(generalTab).toBeFocused();
});
