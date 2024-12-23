/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {collectionsPagesTest} from '../../fixtures/collectionsPagesTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../utils/getRandomString';
import {waitForAlert} from '../../utils/waitForAlert';
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';

const test = mergeTests(
	apiHelpersTest,
	collectionsPagesTest,
	isolatedSiteTest,
	journalPagesTest,
	loginTest(),
	pagesAdminPagesTest
);

test(
	'Add collection page',
	{
		tag: ['@LPS-107775', '@LPS-107776', '@LPS-110183'],
	},
	async ({
		collectionsPage,
		journalEditArticlePage,
		page,
		pagesAdminPage,
		site,
	}) => {

		// Go to collection page creation

		await pagesAdminPage.goto(site.friendlyUrlPath);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {
				exact: true,
				name: 'Collection Page',
			}),
			trigger: pagesAdminPage.newButton,
		});

		// Create a dynamic collection for web content

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {name: 'Dynamic Collection'}),
			trigger: page.getByRole('button', {name: 'New'}),
		});

		const dynamicCollectionName = getRandomString();

		await page.getByPlaceholder('Title').fill(dynamicCollectionName);

		await page.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(page);

		await collectionsPage.configureCollectionWithWebContents();

		await page.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(page);

		await page.getByTitle('Go to Select Collection').click();

		// Create a collection page based on created collection

		await page
			.locator('.select-collection-action-option', {
				hasText: dynamicCollectionName,
			})
			.click();

		await page.getByRole('button', {name: 'Blank'}).click();

		const collectionPageName = getRandomString();

		const addCollectionPageIframe = page.frameLocator(
			'iframe[title="Add Collection Page"]'
		);

		await addCollectionPageIframe
			.getByPlaceholder('Add Page Name', {exact: true})
			.fill(collectionPageName);

		await addCollectionPageIframe
			.getByRole('button', {name: 'Add'})
			.click();

		await waitForAlert(
			page,
			'Success:The collection page was created successfully. Next, customize how the collection is displayed by dropping fragments to the collection display already added to the page.'
		);

		// Assert collection items

		await expect(
			page.getByRole('button', {name: '(0 Items)'})
		).toBeVisible();

		// Create new basic web content

		await page.getByLabel('New Basic Web Content').click();

		const articleTitle = getRandomString();

		await journalEditArticlePage.createAndPublishBasicArticle(articleTitle);

		await expect(
			page.getByRole('button', {name: '(1 Items)'})
		).toBeVisible();

		// Go to page admin and assert view collection items

		await pagesAdminPage.goto(site.friendlyUrlPath);

		await pagesAdminPage.clickOnAction(
			'View Collection Items',
			collectionPageName
		);

		const collectionItemsIframe = page.frameLocator(
			'iframe[title="Collection Items"]'
		);

		await expect(
			collectionItemsIframe.getByText(articleTitle)
		).toBeVisible();
	}
);

test(
	'No alert shown when view the collection page based on collection with XSS name',
	{
		tag: '@LPS-129109',
	},
	async ({apiHelpers, page, pagesAdminPage, site}) => {

		// Create a collection

		await apiHelpers.jsonWebServicesAssetListEntry.addDynamicAssetListEntry(
			{
				groupId: site.id,
				title: '<script>alert(123);</script>',
			}
		);

		// Add listener with expect, so it fails when a browser dialog is shown

		page.on('dialog', async (dialog) => {
			dialog.accept();

			expect(
				dialog.message(),
				'This alert should not be shown'
			).toBeNull();
		});

		// Create collection page and go to view mode to check dialog is not shown

		await pagesAdminPage.goto(site.friendlyUrlPath);

		const layoutName = getRandomString();

		await pagesAdminPage.addCollectionPage({
			collectionName: '<script>alert(123);</script>',
			name: layoutName,
		});

		await pagesAdminPage.clickOnAction('View', layoutName);
	}
);
