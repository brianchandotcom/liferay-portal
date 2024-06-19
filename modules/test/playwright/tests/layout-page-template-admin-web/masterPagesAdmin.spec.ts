/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import {masterPagesTest} from './fixtures/masterPagesTest';

export const test = mergeTests(
	pagesAdminPagesTest,
	isolatedSiteTest,
	loginTest(),
	masterPagesTest,
	pageEditorPagesTest
);

test('Validate if the Blank page template can not be edited and deleted', async ({
	masterPagesPage,
	site,
}) => {
	await masterPagesPage.goto(site.friendlyUrlPath);

	const templateCard = masterPagesPage.getMasterCard('Blank');

	await expect(templateCard).toBeVisible();

	await expect(templateCard.getByLabel('More actions')).not.toBeVisible();

	await expect(
		templateCard.locator('.custom-control.custom-checkbox')
	).not.toBeVisible();
});

test('Add a page based on custom master', async ({
	masterPagesPage,
	page,
	pageEditorPage,
	pagesAdminPage,
	site,
}) => {
	const masterName = 'New Master Page';

	await test.step('Create and publish new custom master page and edit it', async () => {
		await masterPagesPage.goto(site.friendlyUrlPath);

		await masterPagesPage.createNewMaster(masterName);

		await masterPagesPage.editMaster(masterName);
	});

	let buttonId;

	await test.step('Add and configure a Button fragment on master page', async () => {
		await pageEditorPage.addFragment('Basic Components', 'Button');

		const topper = await page.locator(
			'.page-editor__topper[data-name="Button"]'
		);

		buttonId = await topper.evaluate((element) =>
			Array.from(element.classList)
				.find((cssClass) =>
					cssClass.includes('lfr-layout-structure-item')
				)
				.replace('lfr-layout-structure-item-topper-', '')
		);

		const fragmentButton = await pageEditorPage.getFragment(buttonId);

		expect(
			await pageEditorPage.getElementStyle(
				fragmentButton,
				'background-color'
			)
		).toBe('rgba(0, 0, 0, 0)');

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Background Color',
			fragmentId: buttonId,
			tab: 'Styles',
			value: 'Gray 300',
			valueFromStylebook: true,
		});

		expect(
			await pageEditorPage.getElementStyle(
				fragmentButton,
				'background-color'
			)
		).toBe('rgb(231, 231, 237)');

		await pageEditorPage.publishPage();
	});

	const pageName = `Page ${masterName}`;

	await test.step('Assert custom masters as an option when add a new page', async () => {
		await pagesAdminPage.goto(site.friendlyUrlPath);

		await pagesAdminPage.createNewPage(pageName, masterName);
	});

	await test.step('Assert the new page inherits elements from custom masters', async () => {
		await pagesAdminPage.goto(site.friendlyUrlPath);

		await pagesAdminPage.editPage(pageName);

		const fragmentButton = await pageEditorPage.getFragment(buttonId);

		expect(
			await pageEditorPage.getElementStyle(
				fragmentButton,
				'background-color'
			)
		).toBe('rgb(231, 231, 237)');
	});
});

test('Fragments hidden in master pages are hidden in pages that use it and visibility can not be changed', async ({
	masterPagesPage,
	page,
	pageEditorPage,
	pagesAdminPage,
	site,
}) => {
	const masterName = 'New Master Page';
	let buttonId;
	let headerId;

	await test.step('Create and publish new custom master page with one fragment hidden', async () => {
		await masterPagesPage.goto(site.friendlyUrlPath);

		await masterPagesPage.createNewMaster(masterName);

		await masterPagesPage.editMaster(masterName);

		await pageEditorPage.addFragment('Basic Components', 'Button');

		const topperButton = await page.locator(
			'.page-editor__topper[data-name="Button"]'
		);

		buttonId = await topperButton.evaluate((element) =>
			Array.from(element.classList)
				.find((cssClass) =>
					cssClass.includes('lfr-layout-structure-item')
				)
				.replace('lfr-layout-structure-item-topper-', '')
		);

		await pageEditorPage.addFragment('Basic Components', 'Heading');

		const topperHeader = await page.locator(
			'.page-editor__topper[data-name="Heading"]'
		);

		headerId = await topperHeader.evaluate((element) =>
			Array.from(element.classList)
				.find((cssClass) =>
					cssClass.includes('lfr-layout-structure-item')
				)
				.replace('lfr-layout-structure-item-topper-', '')
		);

		await pageEditorPage.hideFragment(headerId);

		expect(await pageEditorPage.getFragmentStyle(headerId, 'display')).toBe(
			'none'
		);

		await pageEditorPage.publishPage();
	});

	await test.step('Create and publish new page based on master page and check that the fragment is still hidden', async () => {
		await pagesAdminPage.goto(site.friendlyUrlPath);

		const pageName = `Page ${masterName}`;

		await pagesAdminPage.createNewPage(pageName, masterName);

		await pagesAdminPage.goto(site.friendlyUrlPath);

		await pagesAdminPage.editPage(pageName);

		const buttonFragment = await pageEditorPage.getFragment(buttonId);

		expect(
			await pageEditorPage.getElementStyle(buttonFragment, 'display')
		).toBe('block');

		await expect(buttonFragment.getAttribute('inert')).toBeDefined();

		const headerFragment = await pageEditorPage.getFragment(headerId);

		expect(
			await pageEditorPage.getElementStyle(headerFragment, 'display')
		).toBe('none');

		await expect(headerFragment.getAttribute('inert')).toBeDefined();
	});
});
