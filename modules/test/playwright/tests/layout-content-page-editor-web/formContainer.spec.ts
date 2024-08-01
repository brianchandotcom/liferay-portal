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
import {LEMON_OBJECT_ERC} from '../../setup/wem-site/constants';
import getRandomString from '../../utils/getRandomString';
import getFormContainerDefinition from './utils/getFormContainerDefinition';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPD-10727': true,
		'LPD-20213': true,
		'LPS-178052': true,
	}),
	loginTest(),
	pageEditorPagesTest,
	wemSiteTest
);

test.describe('Picklist input field', () => {
	test('Shows correct options in picklist field selected as title in related object', async ({
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

		await page.goto(
			`/web${wemSite.friendlyUrlPath}${layout.friendlyUrlPath}`
		);

		await page.getByLabel('Lemon Basket to Lemons').click();

		await expect(page.getByText('Plastic', {exact: true})).toBeVisible();
		await expect(page.getByText('Carton', {exact: true})).toBeVisible();
	});

	test(
		'Checks changing the option when one default is selected set the value correctly',
		{
			tag: '@LPD-31856',
		},
		async ({apiHelpers, page, pageEditorPage, wemSite}) => {

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

			// Go to edit mode and map it to the object

			await pageEditorPage.goto(layout, wemSite.friendlyUrlPath);

			await pageEditorPage.mapFormFragment(formId, 'Lemon Basket', [
				'Lemon Dimensions',
				'Material',
			]);

			// Publish and go to view mode

			await pageEditorPage.publishPage();

			await page.goto(
				`/web${wemSite.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			// Check that the value after selecting the item is correct

			await page.getByLabel('Material').click();

			await page.getByText('carton').click();

			expect(page.getByLabel('Material')).toHaveValue('Carton');
		}
	);
});

test.describe('Relationships', () => {
	test('Allow selecting fields from main object and relationships in fields modal', async ({
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
});

test.describe('Multistep', () => {
	test('Can add and configure a Stepper fragment', async ({
		apiHelpers,
		page,
		pageEditorPage,
		wemSite,
	}) => {

		// Get the id of Lemon object from the site initializer

		const {id: objectDefinitionId} =
			await apiHelpers.objectAdmin.getObjectDefinitionByExternalReferenceCode(
				LEMON_OBJECT_ERC
			);

		// Create a page with a Form fragment with a Stepper fragment

		const stepperId = getRandomString();

		const stepperFragment = getFragmentDefinition({
			id: stepperId,
			key: 'INPUTS-stepper',
		});

		const formId = getRandomString();

		const formDefinition = getFormContainerDefinition({
			id: formId,
			objectDefinitionId,
			pageElements: [stepperFragment],
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([formDefinition]),
			siteId: wemSite.id,
			title: getRandomString(),
		});

		// Go to edit mode

		await pageEditorPage.goto(layout, wemSite.friendlyUrlPath);

		// Check steps titles and bullets numbers are displayed

		page.locator('.multi-step-nav').getByText('Step 01').waitFor();

		page.locator('.multi-step-icon[data-multi-step-icon="1"]').waitFor();

		// Hide both and check they are not displayed

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Show Bullets Numbers',
			fragmentId: stepperId,
			tab: 'General',
			value: false,
		});

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Show Steps Titles',
			fragmentId: stepperId,
			tab: 'General',
			value: false,
		});

		await expect(
			page.locator('.multi-step-nav').getByText('Step 01')
		).not.toBeVisible();

		await expect(
			page.locator('.multi-step-icon[data-multi-step-icon="1"]')
		).not.toBeVisible();
	});
});
