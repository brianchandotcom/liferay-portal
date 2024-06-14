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

test('Validate if the Blank page template can not be edited and deleted.', async ({
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

test('Add a page based on custom master.', async ({
	masterPagesPage,
	page,
	pageEditorPage,
	pagesAdminPage,
	site,
}) => {
	const masterName = 'New Master Page';

	await test.step('Create and publish new custom master page', async () => {
		await masterPagesPage.goto(site.friendlyUrlPath);

		await masterPagesPage.createNewMaster(masterName);

		const templateCard = masterPagesPage.getMasterCard(masterName);

		await expect(templateCard).toBeVisible();

		await expect(templateCard.getByLabel('More actions')).toBeVisible();

		await expect(
			templateCard.locator('.custom-control.custom-checkbox')
		).toBeVisible();
	});

	await test.step('Assert header of Drop Zone is inside body by default', async () => {
		await masterPagesPage.editMaster(masterName);

		await expect(page.locator('.page-editor__drop-zone')).toBeVisible();

		await expect(
			page.getByText(
				'Fragments and widgets for pages based on this master will be placed here.'
			)
		).toBeVisible();
		await expect(
			page.getByText('Configure Allowed Fragments')
		).toBeVisible();
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

		await expect(topper.locator('a.btn')).toHaveClass(/btn-primary/);
		await expect(topper.locator('a.btn')).toHaveClass(/btn-nm/);
		expect(
			await pageEditorPage.getElementStyle(
				topper.locator(`.lfr-layout-structure-item-${buttonId}`),
				'background-color'
			)
		).toBe('rgba(0, 0, 0, 0)');

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Style',
			fragmentId: buttonId,
			tab: 'General',
			value: 'Link',
		});

		await expect(topper.locator('a.btn')).toHaveClass(/btn-link/);

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Size',
			fragmentId: buttonId,
			tab: 'General',
			value: 'Large',
		});

		await expect(topper.locator('a.btn')).toHaveClass(/btn-lg/);

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Align Center',
			fragmentId: buttonId,
			tab: 'Styles',
		});

		expect(
			await pageEditorPage.getElementStyle(
				topper.locator(`.lfr-layout-structure-item-${buttonId}`),
				'text-align'
			)
		).toBe('center');

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Background Color',
			fragmentId: buttonId,
			tab: 'Styles',
			value: 'Gray 300',
			valueFromStylebook: true,
		});

		expect(
			await pageEditorPage.getElementStyle(
				topper.locator(`.lfr-layout-structure-item-${buttonId}`),
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

		const buttonItem = await page.locator(
			`.page-editor__fragment-content--master.lfr-layout-structure-item-${buttonId}`
		);

		await expect(buttonItem.locator('a.btn')).toHaveClass(/btn-link/);
		await expect(buttonItem.locator('a.btn')).toHaveClass(/btn-lg/);
		expect(
			await pageEditorPage.getElementStyle(buttonItem, 'text-align')
		).toBe('center');
		expect(
			await pageEditorPage.getElementStyle(buttonItem, 'background-color')
		).toBe('rgb(231, 231, 237)');
	});
});
