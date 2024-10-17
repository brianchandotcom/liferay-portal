/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import {productMenuPageTest} from '../../fixtures/productMenuPageTest';
import {styleBookPageTest} from '../../fixtures/styleBookPageTest';
import getRandomString from '../../utils/getRandomString';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest,
	pagesAdminPagesTest,
	productMenuPageTest,
	styleBookPageTest
);

test('Checks the correct label for restricted pages in the preview selector', async ({
	apiHelpers,
	page,
	site,
	styleBooksPage,
}) => {

	// Create a content page with only one permission

	const pageName = getRandomString();

	await apiHelpers.headlessDelivery.createSitePage({
		pagePermissions: [
			{
				actionKeys: ['VIEW'],
				roleKey: 'Owner',
			},
		],
		siteId: site.id,
		title: pageName,
	});

	// Create a stylebook and edit it

	const styleBookName = getRandomString();

	await styleBooksPage.goto(site.friendlyUrlPath);

	await styleBooksPage.create(styleBookName);

	// Check the restricted page label in the preview selector

	await page.getByRole('button', {name: pageName}).click();

	await expect(
		page.getByRole('menuitem', {name: `${pageName} Restricted Page`})
	).toBeVisible();
});

test('LPD-35561 Preview StyleBook when edit StyleBook', async ({
	page,
	pageEditorPage,
	pagesAdminPage,
	productMenuPage,
	site,
	styleBooksPage,
}) => {
	await test.step('Add a content page', async () => {
		await productMenuPage.openProductMenuIfClosed();

		await productMenuPage.goToPages();

		await pagesAdminPage.createNewPage({
			addButtonLabel: 'Page',
			draft: true,
			name: 'Test Page Name',
			template: 'Blank',
		});
	});

	await test.step('Add a Banner Center to page', async () => {
		await pageEditorPage.goToSidebarTab('Fragments and Widgets');

		await pageEditorPage.addFragment(
			'Featured Content',
			'Banner Center',
			page.locator('div.page-editor__root')
		);
	});

	await test.step('Change the background color of Paragraph and publish the page', async () => {
		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Background Color',
			fragmentId: await pageEditorPage.getFragmentId('Paragraph'),
			tab: 'Styles',
			value: 'Danger',
			valueFromStylebook: true,
		});

		await pageEditorPage.publishPage();
	});

	const styleBookName = getRandomString();

	await test.step('Add a style book', async () => {
		await styleBooksPage.goto(site['friendlyUrl']);

		await styleBooksPage.create(styleBookName);
	});

	await test.step('Assert the content page is shown in preview iframe', async () => {
		const previewIframe = page.frameLocator(
			'iframe.style-book-editor__page-preview-frame'
		);

		expect(
			await previewIframe.getByRole('heading', {
				name: 'Banner Title Example',
			})
		).toBeVisible();
	});

	await test.step('Edit Background Color in Button Primary section', async () => {
		await styleBooksPage.selectFrontendTokenCategory(
			'Color System',
			'Buttons'
		);

		await styleBooksPage.updateTokenInputColor(
			'Background Color',
			'#FF0000',
			'Button Primary'
		);

		await styleBooksPage.waitForAutoSave();
	});

	await test.step('Select Typography in sidebar', async () => {
		await styleBooksPage.selectFrontendTokenCategory(
			'Buttons',
			'Typography'
		);
	});

	await test.step('Edit Heading 1 Font Size in Headings section', async () => {
		await page.getByRole('button', {name: 'Headings'}).click();

		const heading1FontSizeInput = page
			.getByLabel('Heading 1 Font Size')
			.locator('[aria-label="Heading 1 Font Size"]');

		await heading1FontSizeInput.fill('2');

		await heading1FontSizeInput.blur();

		await styleBooksPage.waitForAutoSave();
	});

	await test.step('Select Color System in sidebar', async () => {
		await styleBooksPage.selectFrontendTokenCategory(
			'Typography',
			'Color System'
		);
	});

	await test.step('Edit the Danger in Theme Colors section', async () => {
		await page.getByRole('button', {name: 'Brand Colors'}).click();

		await styleBooksPage.updateTokenInputColor('Brand Color 1', 'danger');

		await styleBooksPage.waitForAutoSave();
	});

	await test.step('Preview the effect in page preivew iframe', async () => {
		const previewIframe = page.frameLocator(
			'iframe.style-book-editor__page-preview-frame'
		);

		await expect(
			previewIframe.locator(
				'.lfr-layout-structure-item-basic-component-button .btn-primary'
			)
		).toHaveCSS('background-color', 'rgb(255, 0, 0)');

		await expect(
			previewIframe
				.locator('.lfr-layout-structure-item-basic-component-heading')
				.getByText('Banner Title Example')
		).toHaveCSS('font-size', '32px');

		await expect(
			previewIframe.locator(
				'.lfr-layout-structure-item-basic-component-paragraph'
			)
		).toHaveCSS('background-color', 'rgb(218, 20, 20)');

		await styleBooksPage.publish();
	});

	await test.step('Assert the new style book in Style Books admin', async () => {
		await expect(
			page.getByRole('link', {name: styleBookName})
		).toBeVisible();
	});
});
