/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {collectionsPagesTest} from '../../fixtures/collectionsPagesTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pageManagementSiteTest} from '../../fixtures/pageManagementSiteTest';
import {clickAndExpectToBeHidden} from '../../utils/clickAndExpectToBeHidden';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../utils/getRandomString';
import {ANIMALS_COLLECTION_NAME} from '../setup/page-management-site/constants/animals';
import getCollectionDefinition from './utils/getCollectionDefinition';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	collectionsPagesTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest(),
	pageEditorPagesTest,
	pageManagementSiteTest
);

test('Allows selecting specific repeatable field when mapping', async ({
	apiHelpers,
	page,
	pageEditorPage,
	pageManagementSite,
}) => {

	// Create page with a Heading fragment and go to edit mode

	const headingId = getRandomString();

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getFragmentDefinition({
				id: headingId,
				key: 'BASIC_COMPONENT-heading',
			}),
		]),
		siteId: pageManagementSite.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

	// Map editable to repeatable field Country

	await pageEditorPage.selectEditable(headingId, 'element-text');

	await pageEditorPage.selectItemMappingButton.click();

	const webContentOption = page
		.frameLocator('iframe[title="Select"]')
		.getByRole('menuitem', {name: 'Web Content'});

	if (
		await webContentOption.evaluate(
			(element) => !element.classList.contains('active')
		)
	) {
		await webContentOption.click();
	}

	const folderCard = page
		.frameLocator('iframe[title="Select"]')
		.getByRole('link', {name: 'Animals'});

	const articleCard = page
		.frameLocator('iframe[title="Select"]')
		.getByText('Animal 01', {exact: false});

	await clickAndExpectToBeVisible({target: articleCard, trigger: folderCard});

	await clickAndExpectToBeHidden({
		target: page.locator('.modal-dialog'),
		trigger: articleCard,
	});

	await page
		.getByLabel('Field')
		.selectOption({value: 'DDMStructure_Country'});

	await pageEditorPage.waitForChangesSaved();

	// Check that all iteration to display option works

	const fragment = page.locator('.component-heading');

	await expect(fragment).toHaveText('Spain');

	await page.getByLabel('Iteration to Display').selectOption('Last');

	await pageEditorPage.waitForChangesSaved();

	await expect(fragment).toHaveText('United Kingdom');

	await page
		.getByLabel('Iteration to Display')
		.selectOption('Specific Number');

	await page.getByLabel('Iteration Number').fill('2');

	await page.getByLabel('Iteration Number').blur();

	await pageEditorPage.waitForChangesSaved();

	await expect(fragment).toHaveText('France');

	await pageEditorPage.switchLanguage('es-ES');

	await expect(fragment).toHaveText('Francia');

	// Publish and check the published page

	await pageEditorPage.publishPage();

	await page.goto(
		`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
	);

	await expect(fragment).toHaveText('France');
});

test('Allows selecting specific repeatable collection provider', async ({
	apiHelpers,
	collectionsPage,
	page,
	pageEditorPage,
	pageManagementSite,
}) => {

	// Create definition for a collection mapped to Animals collection

	const animalsClassPK = await collectionsPage.getCollectionClassPK(
		ANIMALS_COLLECTION_NAME,
		pageManagementSite.friendlyUrlPath
	);

	const collectionId = getRandomString();

	const collectionDefinition = getCollectionDefinition({
		classPK: animalsClassPK,
		id: collectionId,
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([collectionDefinition]),
		siteId: pageManagementSite.id,
		title: getRandomString(),
	});

	// Go to edit mode of page

	await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

	// Add a repeatable field collection with heading fragment

	await pageEditorPage.addFragment(
		'Content Display',
		'Collection Display',
		page.locator('.page-editor__collection-item').first()
	);

	await page.locator('.lfr-layout-structure-item-collection').last().click();

	await pageEditorPage.chooseCollectionDisplayOption(
		'Repeatable Fields Collection Providers',
		'Species'
	);

	await pageEditorPage.waitForChangesSaved();

	await pageEditorPage.addFragment(
		'Basic Components',
		'Heading',
		page.locator('.page-editor__collection-item.empty').last()
	);

	// Select editable and map it

	await pageEditorPage.goToSidebarTab('Browser');

	await page.getByLabel('Select element-text').click();

	await page.getByLabel('Field').selectOption('Species Name');

	await pageEditorPage.waitForChangesSaved();

	await expect(page.getByText('Balinese')).toBeAttached();
	await expect(page.getByText('Poodle')).toBeAttached();
	await expect(page.getByText('Pug')).toBeAttached();
	await expect(page.getByText('Sphynx')).toBeAttached();

	await pageEditorPage.publishPage();

	await page.goto(
		`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
	);

	await expect(page.getByText('Balinese')).toBeAttached();
	await expect(page.getByText('Poodle')).toBeAttached();
	await expect(page.getByText('Pug')).toBeAttached();
	await expect(page.getByText('Sphynx')).toBeAttached();
});
