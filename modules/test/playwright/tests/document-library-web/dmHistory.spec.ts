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
import {setItemsPerPage} from '../../utils/pagination';

const test = mergeTests(
	apiHelpersTest,
	documentLibraryPagesTest,
	isolatedSiteTest,
	loginTest()
);

test(
	'File Entry History is ordered correctly',
	{tag: '@LPD-52628'},
	async ({apiHelpers, documentLibraryPage, page, site}) => {
		const fileEntryTitle =
			await test.step('Create a new File Entry with multiple versions', async () => {
				const fileEntry =
					await apiHelpers.headlessDelivery.postDocument(
						site.id,
						createReadStream(
							path.join(__dirname, '/dependencies/image1.jpeg')
						)
					);

				for (let i = 0; i < 11; i++) {
					await apiHelpers.headlessDelivery.patchDocument({
						document: {
							description: '' + i,
						},
						documentId: fileEntry.id,
					});
				}

				return fileEntry.title;
			});

		await documentLibraryPage.goto(site.friendlyUrlPath);
		await documentLibraryPage.goToViewHistoryFileEntry(fileEntryTitle);

		await setItemsPerPage(page, 8);

		await expect(
			page.getByText('Showing 1 to 8 of 12 entries.', {exact: true})
		).toBeVisible();

		await expect(page.locator('td.lfr-version-column').nth(0)).toHaveText(
			'1.11'
		);
	}
);
