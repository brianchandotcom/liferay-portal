/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import {createReadStream} from 'fs';
import path from 'path';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {documentLibraryPagesTest} from '../../fixtures/documentLibraryPages.fixtures';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';

const test = mergeTests(
	apiHelpersTest,
	documentLibraryPagesTest,
	isolatedSiteTest,
	loginTest()
);

test(
	'Subscription and unsubscription of a Document',
	{tag: '@LPD-42444'},
	async ({apiHelpers, documentLibraryPage, page, site}) => {
		const documentTitle =
			await test.step('Create a new document', async () => {
				const document = await apiHelpers.headlessDelivery.postDocument(
					site.id,
					createReadStream(
						path.join(__dirname, '/dependencies/image1.jpeg')
					)
				);

				return document.title;
			});

		await test.step(`Subscription and unsubscription through Document Actions`, async () => {
			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.goToFileEntryAction(
				'Subscribe',
				documentTitle
			);
			await documentLibraryPage.goToFileEntryAction(
				'Unsubscribe',
				documentTitle
			);
			await documentLibraryPage.assertFileEntryAction(
				'Subscribe',
				documentTitle
			);
		});

		await test.step(`Subscription and unsubscription through Document Info Panel`, async () => {
			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.selectFileEntry(documentTitle);
			await documentLibraryPage.openInfoPanel(documentTitle, 'Details');
			await expect(page.getByLabel('Subscribe')).toBeVisible();
			await page.getByLabel('Subscribe').click();

			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.selectFileEntry(documentTitle);
			await documentLibraryPage.openInfoPanel(documentTitle, 'Details');
			await expect(page.getByLabel('Unsubscribe')).toBeVisible();
			await page.getByLabel('Unsubscribe').click();

			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.selectFileEntry(documentTitle);
			await documentLibraryPage.openInfoPanel(documentTitle, 'Details');
			await expect(page.getByLabel('Subscribe')).toBeVisible();
		});

		await test.step(`Subscribed to a Parent Folder`, async () => {
			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.openInfoPanel('Home', 'Details');
			await page.getByLabel('Subscribe').click();

			await documentLibraryPage.assertFileEntryAction(
				'Subscribed to a Parent Folder',
				documentTitle
			);

			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.selectFileEntry(documentTitle);
			await documentLibraryPage.openInfoPanel(documentTitle, 'Details');
			await expect(
				page.getByLabel('Subscribed to a Parent Folder')
			).toBeVisible();

			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.openInfoPanel('Home', 'Details');
			await page.getByLabel('Unsubscribe').click();

			await documentLibraryPage.assertFileEntryAction(
				'Subscribe',
				documentTitle
			);
		});
	}
);

test(
	'Subscription and unsubscription of a DocumentFolder',
	{tag: '@LPD-42444'},
	async ({apiHelpers, documentLibraryPage, page, site}) => {
		const documentFolderName =
			await test.step('Create a new DocumentFolder', async () => {
				const documentFolder =
					await apiHelpers.headlessDelivery.postDocumentFolder(
						site.id
					);

				return documentFolder.name;
			});

		await test.step(`Subscription and unsubscription through DocumentFolder Actions`, async () => {
			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.goToFileEntryAction(
				'Subscribe',
				documentFolderName
			);
			await documentLibraryPage.goToFileEntryAction(
				'Unsubscribe',
				documentFolderName
			);
			await documentLibraryPage.assertFileEntryAction(
				'Subscribe',
				documentFolderName
			);
		});

		await test.step(`Subscription and unsubscription through DocumentFolder Info Panel`, async () => {
			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.selectFolder(documentFolderName);
			await documentLibraryPage.openInfoPanel(
				documentFolderName,
				'Details'
			);
			await expect(page.getByLabel('Subscribe')).toBeVisible();
			await page.getByLabel('Subscribe').click();

			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.selectFolder(documentFolderName);
			await documentLibraryPage.openInfoPanel(
				documentFolderName,
				'Details'
			);
			await expect(page.getByLabel('Unsubscribe')).toBeVisible();
			await page.getByLabel('Unsubscribe').click();

			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.selectFolder(documentFolderName);
			await documentLibraryPage.openInfoPanel(
				documentFolderName,
				'Details'
			);
			await expect(page.getByLabel('Subscribe')).toBeVisible();
		});

		await test.step(`Subscribed to a Parent Folder`, async () => {
			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.openInfoPanel('Home', 'Details');
			await page.getByLabel('Subscribe').click();

			await documentLibraryPage.assertFileEntryAction(
				'Subscribed to a Parent Folder',
				documentFolderName
			);

			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.selectFolder(documentFolderName);
			await documentLibraryPage.openInfoPanel(
				documentFolderName,
				'Details'
			);
			await expect(
				page.getByLabel('Subscribed to a Parent Folder')
			).toBeVisible();

			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.openInfoPanel('Home', 'Details');
			await page.getByLabel('Unsubscribe').click();

			await documentLibraryPage.assertFileEntryAction(
				'Subscribe',
				documentFolderName
			);
		});
	}
);
