/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pageManagementSiteTest} from '../../fixtures/pageManagementSiteTest';
import getRandomString from '../../utils/getRandomString';
import {LEMON_OBJECT_ERC} from '../setup/page-management-site/constants';
import getFormContainerDefinition from './utils/getFormContainerDefinition';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPD-10727': true,
		'LPS-178052': true,
	}),
	loginTest(),
	pageEditorPagesTest,
	pageManagementSiteTest
);

test.describe('Picklist input field', () => {
	test('Shows correct options in picklist field selected as title in related object', async ({
		apiHelpers,
		page,
		pageEditorPage,
		pageManagementSite,
	}) => {

		// Create a page with a Form fragment

		const formId = getRandomString();

		const formDefinition = getFormContainerDefinition({
			id: formId,
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([formDefinition]),
			siteId: pageManagementSite.id,
			title: getRandomString(),
		});

		// Go to edit mode

		await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

		// Map the form to Lemon Basket object, select fields and publish

		await pageEditorPage.mapFormFragment(formId, 'Lemon', [
			'Lemon Size',
			'Lemon Basket to Lemons',
		]);

		await pageEditorPage.publishPage();

		// Go to view mode and check picklist values

		await page.goto(
			`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
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
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Create a page with a Form fragment

			const formId = getRandomString();

			const formDefinition = getFormContainerDefinition({
				id: formId,
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([formDefinition]),
				siteId: pageManagementSite.id,
				title: getRandomString(),
			});

			// Go to edit mode and map it to the object

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			await pageEditorPage.mapFormFragment(formId, 'Lemon Basket', [
				'Lemon Dimensions',
				'Material',
			]);

			// Publish and go to view mode

			await pageEditorPage.publishPage();

			await page.goto(
				`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
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
		pageManagementSite,
	}) => {

		// Create a page with a Form fragment

		const formId = getRandomString();

		const formDefinition = getFormContainerDefinition({
			id: formId,
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([formDefinition]),
			siteId: pageManagementSite.id,
			title: getRandomString(),
		});

		// Go to edit mode

		await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

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

test.describe('Multistep', {tag: '@LPD-10727'}, () => {
	test('Can add and configure a Stepper fragment', async ({
		apiHelpers,
		page,
		pageEditorPage,
		pageManagementSite,
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
			siteId: pageManagementSite.id,
			title: getRandomString(),
		});

		// Go to edit mode

		await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

		// Check steps titles and bullets numbers are displayed

		await page.locator('.multi-step-nav').getByText('Step 1').waitFor();

		await page
			.locator('.multi-step-icon[data-multi-step-icon="1"]')
			.waitFor();

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
			page.locator('.multi-step-nav').getByText('Step 1')
		).not.toBeVisible();

		await expect(
			page.locator('.multi-step-icon[data-multi-step-icon="1"]')
		).not.toBeVisible();
	});

	test('Can configure multistep options for a form container', async ({
		apiHelpers,
		page,
		pageEditorPage,
		pageManagementSite,
	}) => {

		// Get the id of Lemon object from the site initializer

		const {id: objectDefinitionId} =
			await apiHelpers.objectAdmin.getObjectDefinitionByExternalReferenceCode(
				LEMON_OBJECT_ERC
			);

		// Create a page with a form container

		const formId = getRandomString();

		const formDefinition = getFormContainerDefinition({
			id: formId,
			objectDefinitionId,
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([formDefinition]),
			siteId: pageManagementSite.id,
			title: getRandomString(),
		});

		// Go to edit mode

		await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

		// Check steps are not displayed

		await expect(page.locator('.page-editor__form-step')).toHaveCount(0);

		// Change to Multi Step and check first step is displayed

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Form Type',
			fragmentId: formId,
			tab: 'General',
			value: 'Multi Step',
		});

		await expect(page.locator('.page-editor__form-step')).toHaveCount(2);

		await expect(
			page.locator('.page-editor__form-step').nth(0)
		).toBeVisible();

		await expect(
			page.locator('.page-editor__form-step').nth(1)
		).not.toBeVisible();

		// Check option to display all steps and check it works

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Display All Steps in Edit Mode',
			fragmentId: formId,
			tab: 'General',
			value: true,
		});

		await expect(
			page.locator('.page-editor__form-step').nth(0)
		).toBeVisible();

		await expect(
			page.locator('.page-editor__form-step').nth(1)
		).toBeVisible();

		// Change number of steps and check it works

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Number of Steps',
			fragmentId: formId,
			tab: 'General',
			value: '3',
		});

		await expect(page.locator('.page-editor__form-step')).toHaveCount(3);
	});

	test('Can change step with the stepper fragment', async ({
		apiHelpers,
		page,
		pageEditorPage,
		pageManagementSite,
	}) => {

		// Get the id of Lemon object from the site initializer

		const {id: objectDefinitionId} =
			await apiHelpers.objectAdmin.getObjectDefinitionByExternalReferenceCode(
				LEMON_OBJECT_ERC
			);

		// Definition for the Stepper fragment

		const stepperFragment = getFragmentDefinition({
			id: getRandomString(),
			key: 'INPUTS-stepper',
		});

		// Create a form with two steps and the Stepper

		const headingDefinition = getFragmentDefinition({
			id: getRandomString(),
			key: 'BASIC_COMPONENT-heading',
		});

		const buttonDefinition = getFragmentDefinition({
			id: getRandomString(),
			key: 'BASIC_COMPONENT-button',
		});

		const formDefinition = getFormContainerDefinition({
			id: getRandomString(),
			objectDefinitionId,
			pageElements: [stepperFragment],
			steps: [[headingDefinition], [buttonDefinition]],
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([formDefinition]),
			siteId: pageManagementSite.id,
			title: getRandomString(),
		});

		// Go to edit mode

		await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

		// Check step change works properly

		const button = page.locator(
			'.lfr-layout-structure-item-basic-component-button'
		);

		const heading = page.locator(
			'.lfr-layout-structure-item-basic-component-heading'
		);

		await expect(button).not.toBeVisible();
		await expect(heading).toBeVisible();

		const stepButtons = await page.locator('.multi-step-icon').all();
		await stepButtons[1].click();

		await expect(button).toBeVisible();
		await expect(heading).not.toBeVisible();
	});

	test('Can change step with the form button fragment', async ({
		apiHelpers,
		page,
		pageEditorPage,
		pageManagementSite,
	}) => {

		// Get the id of Lemon object from the site initializer

		const {id: objectDefinitionId} =
			await apiHelpers.objectAdmin.getObjectDefinitionByExternalReferenceCode(
				LEMON_OBJECT_ERC
			);

		// Create a form with two steps and two form buttons

		const headingDefinition = getFragmentDefinition({
			id: getRandomString(),
			key: 'BASIC_COMPONENT-heading',
		});

		const formButtonNext = getFragmentDefinition({
			fragmentConfig: {
				type: 'next',
			},
			id: getRandomString(),
			key: 'INPUTS-submit-button',
		});

		const buttonDefinition = getFragmentDefinition({
			id: getRandomString(),
			key: 'BASIC_COMPONENT-button',
		});

		const formButtonPrevious = getFragmentDefinition({
			fragmentConfig: {
				type: 'previous',
			},
			id: getRandomString(),
			key: 'INPUTS-submit-button',
		});

		const formDefinition = getFormContainerDefinition({
			id: getRandomString(),
			objectDefinitionId,
			steps: [
				[headingDefinition, formButtonNext],
				[buttonDefinition, formButtonPrevious],
			],
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([formDefinition]),
			siteId: pageManagementSite.id,
			title: getRandomString(),
		});

		// Create function that check form buttons behavior

		const checkFormButtonsBehavior = async () => {

			// Check initial state

			const button = page.locator(
				'.lfr-layout-structure-item-basic-component-button'
			);

			const heading = page.locator(
				'.lfr-layout-structure-item-basic-component-heading'
			);

			await expect(heading).toBeVisible();
			await expect(button).not.toBeVisible();

			// Check Next button works

			await page.locator('.btn', {hasText: 'Next'}).click();

			await expect(heading).not.toBeVisible();
			await expect(button).toBeVisible();

			// Check Previous button works

			await page.locator('.btn', {hasText: 'Previous'}).click();

			await expect(heading).toBeVisible();
			await expect(button).not.toBeVisible();
		};

		// Check in view mode

		await page.goto(
			`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
		);

		await checkFormButtonsBehavior();

		// Check in edit mode

		await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

		await checkFormButtonsBehavior();
	});

	test('Step change affects only desired form', async ({
		apiHelpers,
		page,
		pageManagementSite,
	}) => {

		// Get the id of Lemon object from the site initializer

		const {id: objectDefinitionId} =
			await apiHelpers.objectAdmin.getObjectDefinitionByExternalReferenceCode(
				LEMON_OBJECT_ERC
			);

		// Definition for the Steppers fragment

		const stepperFragment1 = getFragmentDefinition({
			id: getRandomString(),
			key: 'INPUTS-stepper',
		});

		const stepperFragment2 = getFragmentDefinition({
			id: getRandomString(),
			key: 'INPUTS-stepper',
		});

		// Create two forms with two steps and the Stepper

		const headingDefinition = getFragmentDefinition({
			id: getRandomString(),
			key: 'BASIC_COMPONENT-heading',
		});

		const buttonDefinition = getFragmentDefinition({
			id: getRandomString(),
			key: 'BASIC_COMPONENT-button',
		});

		const form1Definition = getFormContainerDefinition({
			id: getRandomString(),
			objectDefinitionId,
			pageElements: [stepperFragment1],
			steps: [[headingDefinition], []],
		});

		const form2Definition = getFormContainerDefinition({
			id: getRandomString(),
			objectDefinitionId,
			pageElements: [stepperFragment2],
			steps: [[buttonDefinition], []],
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				form1Definition,
				form2Definition,
			]),
			siteId: pageManagementSite.id,
			title: getRandomString(),
		});

		// Go to view mode of page

		await page.goto(
			`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
		);

		// Check step change affects only desired form

		const button = page.locator(
			'.lfr-layout-structure-item-basic-component-button'
		);

		const heading = page.locator(
			'.lfr-layout-structure-item-basic-component-heading'
		);

		await expect(button).toBeVisible();
		await expect(heading).toBeVisible();

		const firstForm = page
			.locator('.lfr-layout-structure-item-form')
			.first();

		const firstFormSteps = await firstForm
			.locator('.multi-step-icon')
			.all();
		await firstFormSteps[1].click();

		await expect(heading).not.toBeVisible();
		await expect(button).toBeVisible();
	});

	test('Stepper gets number of steps from parent form', async ({
		apiHelpers,
		page,
		pageEditorPage,
		pageManagementSite,
	}) => {

		// Get the id of Lemon object from the site initializer

		const {id: objectDefinitionId} =
			await apiHelpers.objectAdmin.getObjectDefinitionByExternalReferenceCode(
				LEMON_OBJECT_ERC
			);

		// Create a form with a Stepper

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
			steps: [[]],
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([formDefinition]),
			siteId: pageManagementSite.id,
			title: getRandomString(),
		});

		// Go to edit mode of page

		await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

		// Check changing number of stepps in Form affects the Stepper

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Number of Steps',
			fragmentId: formId,
			tab: 'General',
			value: '4',
		});

		await expect(page.locator('.multi-step-indicator')).toHaveCount(4);

		// Delete the Stepper and check that when adding it again, it takes the correct number of steps

		await pageEditorPage.deleteFragment(stepperId);

		await pageEditorPage.addFragment('Form Components', 'Stepper');

		await expect(page.locator('.multi-step-indicator')).toHaveCount(4);
	});
});
