/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import {createReadStream} from 'fs';
import path from 'path';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {documentLibraryPagesTest} from '../../../fixtures/documentLibraryPages.fixtures';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';

const test = mergeTests(
	apiHelpersTest,
	documentLibraryPagesTest,
	isolatedSiteTest,
	loginTest()
);

test(
	'Subscription and unsubscription of a File Entry',
	{tag: '@LPD-42444'},
	async ({apiHelpers, documentLibraryPage, page, site}) => {
		const fileEntryTitle =
			await test.step('Create a new File Entry', async () => {
				const fileEntry =
					await apiHelpers.headlessDelivery.postDocument(
						site.id,
						createReadStream(
							path.join(__dirname, '/dependencies/image1.jpeg')
						)
					);

				return fileEntry.title;
			});

		await test.step(`Subscription and unsubscription through File Entry Actions`, async () => {
			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.goToFileEntryAction(
				'Subscribe',
				fileEntryTitle
			);
			await documentLibraryPage.goToFileEntryAction(
				'Unsubscribe',
				fileEntryTitle
			);
			await documentLibraryPage.assertFileEntryAction(
				'Subscribe',
				fileEntryTitle
			);
		});

		await test.step(`Subscription and unsubscription through File Entry Info Panel`, async () => {
			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.selectFileEntry(fileEntryTitle);
			await documentLibraryPage.openInfoPanel(fileEntryTitle, 'Details');
			await expect(page.getByLabel('Subscribe')).toBeVisible();
			await page.getByLabel('Subscribe').click();

			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.selectFileEntry(fileEntryTitle);
			await documentLibraryPage.openInfoPanel(fileEntryTitle, 'Details');
			await expect(page.getByLabel('Unsubscribe')).toBeVisible();
			await page.getByLabel('Unsubscribe').click();

			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.selectFileEntry(fileEntryTitle);
			await documentLibraryPage.openInfoPanel(fileEntryTitle, 'Details');
			await expect(page.getByLabel('Subscribe')).toBeVisible();
		});

		await test.step(`Subscribed to a Parent Folder`, async () => {
			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.openInfoPanel('Home', 'Details');
			await page.getByLabel('Subscribe').click();

			await documentLibraryPage.assertFileEntryAction(
				'Subscribed to a Parent Folder',
				fileEntryTitle
			);

			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.selectFileEntry(fileEntryTitle);
			await documentLibraryPage.openInfoPanel(fileEntryTitle, 'Details');
			await expect(
				page.getByLabel('Subscribed to a Parent Folder')
			).toBeVisible();

			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.openInfoPanel('Home', 'Details');
			await page.getByLabel('Unsubscribe').click();

			await documentLibraryPage.assertFileEntryAction(
				'Subscribe',
				fileEntryTitle
			);
		});
	}
);

test(
	'Subscription and unsubscription of a Folder',
	{tag: '@LPD-42444'},
	async ({apiHelpers, documentLibraryPage, page, site}) => {
		const folderName = await test.step('Create a new Folder', async () => {
			const folder = await apiHelpers.headlessDelivery.postDocumentFolder(
				site.id
			);

			return folder.name;
		});

		await test.step(`Subscription and unsubscription through Folder Actions`, async () => {
			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.goToFileEntryAction(
				'Subscribe',
				folderName
			);
			await documentLibraryPage.goToFileEntryAction(
				'Unsubscribe',
				folderName
			);
			await documentLibraryPage.assertFileEntryAction(
				'Subscribe',
				folderName
			);
		});

		await test.step(`Subscription and unsubscription through Folder Info Panel`, async () => {
			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.selectFolder(folderName);
			await documentLibraryPage.openInfoPanel(folderName, 'Details');
			await expect(page.getByLabel('Subscribe')).toBeVisible();
			await page.getByLabel('Subscribe').click();

			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.selectFolder(folderName);
			await documentLibraryPage.openInfoPanel(folderName, 'Details');
			await expect(page.getByLabel('Unsubscribe')).toBeVisible();
			await page.getByLabel('Unsubscribe').click();

			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.selectFolder(folderName);
			await documentLibraryPage.openInfoPanel(folderName, 'Details');
			await expect(page.getByLabel('Subscribe')).toBeVisible();
		});

		await test.step(`Subscribed to a Parent Folder`, async () => {
			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.openInfoPanel('Home', 'Details');
			await page.getByLabel('Subscribe').click();

			await documentLibraryPage.assertFileEntryAction(
				'Subscribed to a Parent Folder',
				folderName
			);

			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.selectFolder(folderName);
			await documentLibraryPage.openInfoPanel(folderName, 'Details');
			await expect(
				page.getByLabel('Subscribed to a Parent Folder')
			).toBeVisible();

			await documentLibraryPage.goto(site.friendlyUrlPath);
			await documentLibraryPage.openInfoPanel('Home', 'Details');
			await page.getByLabel('Unsubscribe').click();

			await documentLibraryPage.assertFileEntryAction(
				'Subscribe',
				folderName
			);
		});
	}
);
