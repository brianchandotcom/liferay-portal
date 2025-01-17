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
import {
	disableSystemFeatureFlag,
	enableSystemFeatureFlag,
} from '../../utils/systemFeatureFlag';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
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

test(
	'Preview StyleBook when edit StyleBook',
	{tag: '@LPD-35561'},
	async ({
		page,
		pageEditorPage,
		pagesAdminPage,
		productMenuPage,
		site,
		styleBooksPage,
	}) => {
		await test.step('Enable feature flag', async () => {
			await enableSystemFeatureFlag({
				page,
				title: 'Featured Content Fragment Set',
				type: 'Deprecation',
			});
		});

		await test.step('Add a content page', async () => {
			await page.goto('/');

			await productMenuPage.goToPages();

			await pagesAdminPage.createNewPage({
				draft: true,
				name: 'Test Page Name',
				template: 'Blank',
			});
		});

		await test.step('Add a Banner Center to page', async () => {
			await pageEditorPage.goToSidebarTab('Components');

			await pageEditorPage.addFragment(
				'Featured Content Deprecated',
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
			await styleBooksPage.selectTokenCategory('Buttons');

			await styleBooksPage.updateTokenInputColor(
				'Background Color',
				'#FF0000',
				'Button Primary'
			);

			await styleBooksPage.waitForAutoSave();
		});

		await test.step('Select Typography in sidebar', async () => {
			await styleBooksPage.selectTokenCategory('Typography');
		});

		await test.step('Edit Heading 1 Font Size in Headings section', async () => {
			await styleBooksPage.updateTokenInput(
				'Heading 1 Font Size',
				'2',
				'Headings'
			);

			await styleBooksPage.waitForAutoSave();
		});

		await test.step('Select Color System in sidebar', async () => {
			await styleBooksPage.selectTokenCategory('Color System');
		});

		await test.step('Edit the Danger in Theme Colors section', async () => {
			await styleBooksPage.updateTokenInputColor(
				'Brand Color 1',
				'danger',
				'Brand Colors'
			);

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
					.locator(
						'.lfr-layout-structure-item-basic-component-heading'
					)
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

		await test.step('Disable feature flag', async () => {
			await disableSystemFeatureFlag({
				page,
				title: 'Featured Content Fragment Set',
				type: 'Deprecation',
			});
		});
	}
);

test(
	'Preview style book on pages',
	{tag: '@LPD-35560'},
	async ({
		page,
		pageEditorPage,
		pagesAdminPage,
		productMenuPage,
		site,
		styleBooksPage,
	}) => {
		const firstPageName = getRandomString();
		const secondPageName = getRandomString();
		const thirdPageName = getRandomString();

		const styleBookName = getRandomString();

		const previewIframe = page.frameLocator(
			'iframe.style-book-editor__page-preview-frame'
		);

		await test.step('Add the first page with a Blogs widget', async () => {
			await productMenuPage.goToPages();

			await pagesAdminPage.createNewPage({
				draft: true,
				name: firstPageName,
				template: 'Blank',
			});

			await pageEditorPage.addWidget('Collaboration', 'Blogs');
		});

		await test.step('Add the second page with a My Sites widget', async () => {
			await productMenuPage.goToPages();

			await pagesAdminPage.createNewPage({
				draft: true,
				name: secondPageName,
				template: 'Blank',
			});

			await pageEditorPage.addWidget('Community', 'My Sites');
		});

		await test.step('Add and publish the third page with a paragraph fragment', async () => {
			await productMenuPage.goToPages();

			await pagesAdminPage.createNewPage({
				draft: true,
				name: thirdPageName,
				template: 'Blank',
			});

			await pageEditorPage.addFragment('Basic Components', 'Paragraph');

			await pageEditorPage.publishPage();
		});

		await test.step('Add and publish the first page with a heading fragment', async () => {
			await page.getByRole('link', {name: firstPageName}).click();

			await pageEditorPage.addFragment('Basic Components', 'Heading');

			await pageEditorPage.publishPage();
		});

		await test.step('Add and publish the second page with a button fragment', async () => {
			await page.getByRole('link', {name: secondPageName}).click();

			await pageEditorPage.addFragment('Basic Components', 'Button');

			await pageEditorPage.publishPage();
		});

		await test.step('Add a style book', async () => {
			await styleBooksPage.goto(site['friendlyUrl']);

			await styleBooksPage.create(styleBookName);

			await styleBooksPage.publish();
		});

		await test.step('Dropdown trigger with text Pages is shown in Management Bar preview type selector', async () => {
			await page
				.locator('.form-check-card')
				.filter({
					hasText: styleBookName,
				})
				.getByRole('button', {name: 'More actions'})
				.click();

			await page.getByRole('menuitem', {name: 'Edit'}).click();

			await expect(
				page.getByRole('button', {name: 'Pages'})
			).toBeVisible();

			await expect(
				page.getByRole('button', {name: secondPageName})
			).toBeVisible();
		});

		await test.step('Third content page name is shown in management bar preview item selector', async () => {
			await styleBooksPage.changePreviewPage(
				secondPageName,
				thirdPageName
			);

			await expect(
				page.getByRole('button', {name: thirdPageName})
			).toBeVisible();
		});

		await test.step('Change Body Color in the General frontend token category', async () => {
			await styleBooksPage.selectTokenCategory('General');

			await styleBooksPage.updateTokenInputColor(
				'Body Color',
				'#227777',
				'Body'
			);

			await styleBooksPage.waitForAutoSave();
		});

		await test.step('Preview color effects on the third content page', async () => {
			await expect(previewIframe.locator('body')).toHaveCSS(
				'color',
				'rgb(34, 119, 119)'
			);
		});

		await test.step('Change the preview item to the second content page', async () => {
			await styleBooksPage.changePreviewPage(
				thirdPageName,
				secondPageName
			);
		});

		await test.step('Change color of Button Primary in the Buttons frontend token category', async () => {
			await styleBooksPage.selectTokenCategory('Buttons');

			await styleBooksPage.updateTokenInputColor(
				'Color',
				'#880022',
				'Button Primary'
			);
		});

		await test.step('Preview color effects on the second content page', async () => {
			await expect(previewIframe.locator('.btn-primary')).toHaveCSS(
				'color',
				'rgb(136, 0, 34)'
			);
		});

		await test.step('Change the preview item to the first content page', async () => {
			await styleBooksPage.changePreviewPage(
				secondPageName,
				firstPageName
			);
		});

		await test.step('Change Body Color in the General frontend token category', async () => {
			await styleBooksPage.selectTokenCategory('General');

			await styleBooksPage.updateTokenInputColor(
				'Body Color',
				'#995511',
				'Body'
			);

			await styleBooksPage.waitForAutoSave();
		});

		await test.step('Preview color effects on the first content page', async () => {
			await expect(previewIframe.locator('body')).toHaveCSS(
				'color',
				'rgb(153, 85, 17)'
			);
		});

		await test.step('Change the preview item to the second content page', async () => {
			await styleBooksPage.changePreviewPage(
				firstPageName,
				secondPageName
			);
		});

		await test.step('Change Font Family Base in the Typography frontend token category', async () => {
			await styleBooksPage.selectTokenCategory('Typography');

			await styleBooksPage.updateTokenInput(
				'Font Family Base',
				'times',
				'Font Family'
			);

			await styleBooksPage.waitForAutoSave();
		});

		await test.step('Preview typography effects on the second content page', async () => {
			await expect(
				previewIframe.getByRole('link', {name: 'My Sites'})
			).toHaveCSS('font-family', 'times');
		});

		await test.step('View the Showing X of Y Items shown in dropdown menu of preview item selector then change the preview item to the first content page', async () => {
			await page.getByRole('button', {name: secondPageName}).click();

			await expect(
				page.getByText(/Showing\s[0-9]+\sof\s[0-9]+\sItems/)
			).toBeVisible();

			await page.getByRole('menuitem', {name: firstPageName}).click();
		});

		await test.step('Change Font Family Base in the Typography frontend token category', async () => {
			await styleBooksPage.updateTokenInput(
				'Font Family Base',
				'courier',
				'Font Family'
			);

			await styleBooksPage.waitForAutoSave();
		});

		await test.step('Preview typography effects on the first content page', async () => {
			await expect(
				previewIframe.getByRole('link', {name: 'New Entry'})
			).toHaveCSS('font-family', 'courier');
		});
	}
);
