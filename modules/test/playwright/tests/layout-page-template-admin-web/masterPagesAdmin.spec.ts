/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, expect, mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {masterPagesPagesTest} from '../../fixtures/masterPagesPagesTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import getRandomString from '../../utils/getRandomString';

export const test = mergeTests(
	pagesAdminPagesTest,
	isolatedSiteTest,
	loginTest(),
	masterPagesPagesTest,
	pageEditorPagesTest
);

test('Validate if the Blank page template can not be edited and deleted', async ({
	masterPagesPage,
	site,
}) => {

	// Go to master pages administration

	await masterPagesPage.goto(site.friendlyUrlPath);

	// Check Blank can not be edited or deleted

	const templateCard = masterPagesPage.getMasterCard('Blank');

	await expect(templateCard).toBeVisible();

	await expect(templateCard.getByLabel('More actions')).not.toBeVisible();

	await expect(
		templateCard.locator('.custom-control.custom-checkbox')
	).not.toBeVisible();
});

test('Add a page based on custom master', async ({
	masterPagesPage,
	pageEditorPage,
	pagesAdminPage,
	site,
}) => {
	const masterName = getRandomString();

	let buttonId: string;

	await test.step('Create and publish new custom master page and edit it', async () => {
		await masterPagesPage.goto(site.friendlyUrlPath);

		await masterPagesPage.createNewMaster(masterName);

		await masterPagesPage.editMaster(masterName);
	});

	await test.step('Add and configure a Button fragment on master page', async () => {
		await pageEditorPage.addFragment('Basic Components', 'Button');

		buttonId = await pageEditorPage.getFragmentId('Button');

		expect(
			await pageEditorPage.getFragmentStyle({
				fragmentId: buttonId,
				style: 'backgroundColor',
			})
		).toBe('rgba(0, 0, 0, 0)');

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Background Color',
			fragmentId: buttonId,
			tab: 'Styles',
			value: 'Gray 300',
			valueFromStylebook: true,
		});

		expect(
			await pageEditorPage.getFragmentStyle({
				fragmentId: buttonId,
				style: 'backgroundColor',
			})
		).toBe('rgb(231, 231, 237)');

		await pageEditorPage.publishPage();
	});

	const pageName = getRandomString();

	await test.step('Assert custom masters as an option when add a new page', async () => {
		await pagesAdminPage.goto(site.friendlyUrlPath);

		await pagesAdminPage.createNewPage({
			name: pageName,
			template: masterName,
		});
	});

	await test.step('Assert the new page inherits elements from custom masters', async () => {
		await pagesAdminPage.goto(site.friendlyUrlPath);

		await pagesAdminPage.editPage(pageName);

		expect(
			await pageEditorPage.getFragmentStyle({
				fragmentId: buttonId,
				style: 'backgroundColor',
			})
		).toBe('rgb(231, 231, 237)');
	});
});

test('Fragments hidden in master pages are hidden in pages that use it and visibility can not be changed', async ({
	masterPagesPage,
	pageEditorPage,
	pagesAdminPage,
	site,
}) => {
	const masterName = getRandomString();

	let buttonId: string;
	let buttonFragment: Locator;
	let headingId: string;
	let headingFragment: Locator;

	await test.step('Create and publish new custom master page with one fragment hidden', async () => {
		await masterPagesPage.goto(site.friendlyUrlPath);

		await masterPagesPage.createNewMaster(masterName);
		await masterPagesPage.editMaster(masterName);

		await pageEditorPage.addFragment('Basic Components', 'Button');
		await pageEditorPage.addFragment('Basic Components', 'Heading');

		buttonId = await pageEditorPage.getFragmentId('Button');
		buttonFragment = pageEditorPage.getFragment(buttonId);
		headingId = await pageEditorPage.getFragmentId('Heading');
		headingFragment = pageEditorPage.getFragment(headingId);

		await pageEditorPage.hideFragment(headingId);

		expect(headingFragment).not.toBeVisible();

		await pageEditorPage.publishPage();
	});

	await test.step('Create and publish new page based on master page and check that the fragment is still hidden', async () => {
		await pagesAdminPage.goto(site.friendlyUrlPath);

		const pageName = getRandomString();

		await pagesAdminPage.createNewPage({
			name: pageName,
			template: masterName,
		});

		await pagesAdminPage.goto(site.friendlyUrlPath);

		await pagesAdminPage.editPage(pageName);

		expect(buttonFragment).toBeVisible();
		expect(buttonFragment.getAttribute('inert')).toBeDefined();

		expect(headingFragment).not.toBeVisible();
		expect(headingFragment.getAttribute('inert')).toBeDefined();
	});
});
