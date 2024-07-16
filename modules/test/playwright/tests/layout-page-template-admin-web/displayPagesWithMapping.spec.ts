/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {wemSiteTest} from '../../fixtures/wemSiteTest';
import {clickAndExpectToBeHidden} from '../../utils/clickAndExpectToBeHidden';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../utils/getRandomString';
import {displayPageTemplatesTest} from './fixtures/displayTemplatePagesTest';

const test = mergeTests(
	displayPageTemplatesTest,
	featureFlagsTest({
		'LPD-11377': true,
	}),
	pageEditorPagesTest,
	loginTest(),
	wemSiteTest
);

test('Allow mapping repeatable fields collection provider', async ({
	displayPageTemplatesPage,
	page,
	pageEditorPage,
	wemSite,
}) => {

	// Create DPT for Animal

	await displayPageTemplatesPage.goto(wemSite.friendlyUrlPath);

	const displayPageTemplateName = getRandomString();

	await displayPageTemplatesPage.publishNewTemplate({
		contentSubtype: 'Animal',
		contentType: 'Web Content Article',
		name: displayPageTemplateName,
	});

	await displayPageTemplatesPage.editTemplate(displayPageTemplateName);

	// Add collection and image fragment

	await pageEditorPage.addFragment('Content Display', 'Collection Display');

	await page.locator('.lfr-layout-structure-item-collection').click();

	await pageEditorPage.chooseCollectionDisplayOption(
		'Repeatable Fields Collection Providers',
		'Species'
	);

	await pageEditorPage.waitForChangesSaved();

	await pageEditorPage.addFragment(
		'Basic Components',
		'Image',
		page.locator('.page-editor__collection-item.empty').last()
	);

	// Map editable to image field

	const imageFragment = page.locator('.component-image').last();

	await imageFragment.click();
	await imageFragment.click();

	await page.getByLabel('Source Selection').selectOption('Mapping');

	await page.getByLabel('Field').selectOption('Species Image');

	await pageEditorPage.waitForChangesSaved();

	// Change preview with item

	await clickAndExpectToBeVisible({
		autoClick: true,
		target: page.getByRole('menuitem', {
			name: 'Select Other Item',
		}),
		trigger: page.getByLabel('Preview With'),
	});

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

	// Check src of images

	const imageFragments = page.locator('.component-image img');

	expect(await imageFragments.first().getAttribute('src')).toContain(
		'poodle.jpg'
	);
	expect(await imageFragments.last().getAttribute('src')).toContain(
		'pug.jpg'
	);
});
