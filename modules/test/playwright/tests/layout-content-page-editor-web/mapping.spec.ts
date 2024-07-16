/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {collectionsPagesTest} from '../../fixtures/CollectionsPageTest';
import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {wemSiteTest} from '../../fixtures/wemSiteTest';
import {ANIMALS_COLLECTION_NAME} from '../../setup/wem-site/constants';
import {clickAndExpectToBeHidden} from '../../utils/clickAndExpectToBeHidden';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../utils/getRandomString';
import getCollectionDefinition from './utils/getCollectionDefinition';
import getCollectionItemDefinition from './utils/getCollectionItemDefinition';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	collectionsPagesTest,
	featureFlagsTest({
		'LPD-11377': true,
		'LPS-178052': true,
	}),
	loginTest(),
	pageEditorPagesTest,
	wemSiteTest
);

test('Allows selecting specific repeatable field when mapping', async ({
	apiHelpers,
	page,
	pageEditorPage,
	wemSite,
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
		siteId: wemSite.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, wemSite.friendlyUrlPath);

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

	expect(fragment).toHaveText('Spain');

	await page.getByLabel('Iteration to Display').selectOption('Last');

	await pageEditorPage.waitForChangesSaved();

	expect(fragment).toHaveText('United Kingdom');

	await page
		.getByLabel('Iteration to Display')
		.selectOption('Specific Number');

	await page.getByLabel('Iteration Number').fill('2');

	await page.getByLabel('Iteration Number').blur();

	await pageEditorPage.waitForChangesSaved();

	expect(fragment).toHaveText('France');

	await pageEditorPage.switchLanguage('es-ES');

	expect(fragment).toHaveText('Francia');

	// Publish and check the published page

	await pageEditorPage.publishPage();

	await page.goto(`/web${wemSite.friendlyUrlPath}${layout.friendlyUrlPath}`);

	expect(fragment).toHaveText('France');
});

test('Allows selecting specific repeatable collection provider', async ({
	apiHelpers,
	collectionsPage,
	page,
	pageEditorPage,
	wemSite,
}) => {

	// Create definition for a collection mapped to Animals collection

	const animalsClassPK = await collectionsPage.getCollectionClassPK(
		ANIMALS_COLLECTION_NAME,
		wemSite.friendlyUrlPath
	);

	const animalsCollection = getCollectionItemDefinition(
		getRandomString(),
		[]
	);

	const collectionId = getRandomString();

	const collectionDefinition = getCollectionDefinition({
		classPK: animalsClassPK,
		id: collectionId,
		pageElements: [animalsCollection],
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([collectionDefinition]),
		siteId: wemSite.id,
		title: getRandomString(),
	});

	// Go to edit mode of page

	await pageEditorPage.goto(layout, wemSite.friendlyUrlPath);

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

	const headingFragment = page.locator('.component-heading').last();

	await headingFragment.click();
	await headingFragment.click();

	await page.getByLabel('Field').selectOption('Species Name');

	await pageEditorPage.waitForChangesSaved();

	expect(page.getByText('Balinese')).toBeAttached();
	expect(page.getByText('Poodle')).toBeAttached();
	expect(page.getByText('Pug')).toBeAttached();
	expect(page.getByText('Sphynx')).toBeAttached();

	await pageEditorPage.publishPage();

	await page.goto(`/web${wemSite.friendlyUrlPath}${layout.friendlyUrlPath}`);

	expect(page.getByText('Balinese')).toBeAttached();
	expect(page.getByText('Poodle')).toBeAttached();
	expect(page.getByText('Pug')).toBeAttached();
	expect(page.getByText('Sphynx')).toBeAttached();
});
