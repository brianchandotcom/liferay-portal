/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {wemSiteTest} from '../../fixtures/wemSiteTest';
import getRandomString from '../../utils/getRandomString';
import getFormContainerDefinition from './utils/getFormContainerDefinition';
import getPageDefinition from './utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPD-20213': true,
		'LPS-178052': true,
	}),
	loginTest(),
	pageEditorPagesTest,
	wemSiteTest
);

test('allow selecting fields from main object and relationships in fields modal', async ({
	apiHelpers,
	pageEditorPage,
	wemSite,
}) => {

	// Create a page with a Form fragment

	const formId = getRandomString();

	const formDefinition = getFormContainerDefinition({
		id: formId,
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([formDefinition]),
		siteId: wemSite.id,
		title: getRandomString(),
	});

	// Go to edit mode

	await pageEditorPage.goto(layout, wemSite.friendlyUrlPath);

	// Map the form to Lemon object and select fields

	await pageEditorPage.mapFormFragment(formId, 'Lemon', [
		'Lemon Size',
		'Lemon Basket Color',
	]);

	const form = pageEditorPage.getFragment(formId);

	await expect(form.getByText('Lemon Size')).toBeVisible();
	await expect(form.getByText('Lemon Basket Color')).toBeVisible();
});

test('shows correct options in picklist field selected as title in related object', async ({
	apiHelpers,
	page,
	pageEditorPage,
	wemSite,
}) => {

	// Create a page with a Form fragment

	const formId = getRandomString();

	const formDefinition = getFormContainerDefinition({
		id: formId,
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([formDefinition]),
		siteId: wemSite.id,
		title: getRandomString(),
	});

	// Go to edit mode

	await pageEditorPage.goto(layout, wemSite.friendlyUrlPath);

	// Map the form to Lemon Basket object, select fields and publish

	await pageEditorPage.mapFormFragment(formId, 'Lemon', [
		'Lemon Size',
		'Lemon Basket to Lemons',
	]);

	await pageEditorPage.publishPage();

	// Go to view mode and check picklist values

	await page.goto(`/web${wemSite.friendlyUrlPath}${layout.friendlyUrlPath}`);

	await page.getByLabel('Lemon Basket to Lemons').click();

	await expect(page.getByText('Plastic', {exact: true})).toBeVisible();
	await expect(page.getByText('Carton', {exact: true})).toBeVisible();
});
