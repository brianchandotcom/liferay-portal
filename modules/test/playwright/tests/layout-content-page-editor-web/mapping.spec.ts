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
import {clickAndExpectToBeHidden} from '../../utils/clickAndExpectToBeHidden';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../utils/getRandomString';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPD-11377': true,
	}),
	loginTest(),
	pageEditorPagesTest,
	wemSiteTest
);

test('allows selecting specific repeatable field when mapping', async ({
	apiHelpers,
	page,
	pageEditorPage,
	wemSite,
}) => {
	const headingId = getRandomString();

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getFragmentDefinition({
				id: headingId,
				key: 'BASIC_COMPONENT-heading',
			}),
		]),
		siteId: wemSite.id,
		title: 'Test Page Name',
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

	// publish and check the published page

	await pageEditorPage.publishPage();

	await page.goto(`/web${wemSite.friendlyUrlPath}${layout.friendlyUrlPath}`);

	expect(fragment).toHaveText('France');
});
