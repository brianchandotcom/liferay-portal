/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import {styleBookPageTest} from '../../../fixtures/styleBookPageTest';
import getRandomString from '../../../utils/getRandomString';

export const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	pagesAdminPagesTest,
	loginTest(),
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	pageEditorPagesTest,
	styleBookPageTest,
	pagesAdminPagesTest
);

test(
	'The user can preview the effects on current site fragments in styles book editor.',
	{tag: '@LPS-140774'},
	async ({
		apiHelpers,
		page,
		pageEditorPage,
		pagesAdminPage,
		site,
		styleBooksPage,
	}) => {
		const fragmentCollectionName = getRandomString();

		const styleBookName = getRandomString();

		const previewFragmentExample = page
			.frameLocator('iframe.style-book-editor__page-preview-frame')
			.locator('.fragment-example');

		await test.step('Create custom fragment', async () => {
			const PAGE_NAME = getRandomString();

			// Create a custom fragment

			const {fragmentCollectionId} =
				await apiHelpers.jsonWebServicesFragmentCollection.addFragmentCollection(
					{
						groupId: site.id,
						name: fragmentCollectionName,
					}
				);

			const fragmentEntryName = getRandomString();

			await apiHelpers.jsonWebServicesFragmentEntry.addFragmentEntry({
				fragmentCollectionId,
				groupId: site.id,
				html: `<div class="fragment-example">
				  <h1>Example fragment</h1>
				</div>`,
				name: fragmentEntryName,
				type: 'component',
			});

			// Add a page and add custom fragment

			await pagesAdminPage.goto(site.friendlyUrlPath);

			await pagesAdminPage.createNewPage({
				draft: true,
				name: PAGE_NAME,
			});

			await pageEditorPage.addFragment(
				fragmentCollectionName,
				fragmentEntryName
			);

			await pageEditorPage.waitForChangesSaved();

			await pageEditorPage.publishPage();
		});

		await test.step('Create a style book', async () => {
			await styleBooksPage.goto(site.friendlyUrlPath);

			await styleBooksPage.create(styleBookName);
		});

		await test.step('View the custom fragment set is shown in dropdown list of preview item selector', async () => {
			await page
				.getByRole('button', {name: 'Fragments'})
				.or(page.getByRole('button', {name: 'Pages'}))
				.click();

			await page.getByRole('menuitem', {name: 'Fragments'}).click();

			await expect(
				page.getByRole('button', {name: fragmentCollectionName})
			).toBeVisible();

			await page
				.getByRole('button', {name: fragmentCollectionName})
				.click();

			await expect(page.getByText('Showing 4 of 9 Items')).toBeVisible();
		});

		await test.step('Assert that the custom fragment is shown in preview page of the custom set fragment collection', async () => {
			await expect(previewFragmentExample).toHaveText('Example fragment');
		});

		await test.step('Define the Heading 1 Font Size of Headings', async () => {
			await styleBooksPage.selectTokenCategory('Typography');

			await styleBooksPage.updateTokenInput(
				'Heading 1 Font Size',
				'2',
				'Headings'
			);

			await styleBooksPage.waitForAutoSave();
		});

		await test.step('View the defined Heading 1 Font Size applied on Heading fragment', async () => {
			await expect(previewFragmentExample.locator('h1')).toHaveCSS(
				'font-size',
				'32px'
			);
		});
	}
);
