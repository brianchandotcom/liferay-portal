/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {ApiHelpers} from '../../../helpers/ApiHelpers';
import getRandomString from '../../../utils/getRandomString';
import {performLoginViaApi} from '../../../utils/performLogin';
import {DataSetPage} from '../main/pages/DataSetPage';
import {cmsPagesTest} from './fixtures/cmsPagesTest';

const test = mergeTests(cmsPagesTest, dataApiHelpersTest, loginTest());

const APPLICATION_NAME = 'cms/basic-documents';
const FILE_COUNT = 12;

const SPACE_NAME = 'Default';

const VALID_IMAGE_FILE_BASE64 =
	'iVBORw0KGgoAAAANSUhEUgAAAAEAAAABAQMAAAAl21bKAAAAA1BMVEUAAACnej3aAAAAAXRSTlMAQObYZgAAAApJREFUCNdjYAAAAAIAAeIhvDMAAAAASUVORK5CYII=';

let createdEntryIds: number[];
let titlePrefix: string;

async function openBulkActionMenu(page: Page) {
	await page
		.getByTestId('visualization-mode-table')
		.getByLabel('Actions')
		.click();
}

async function expectBulkActionVisible(page: Page, action: string) {
	await expect(async () => {
		await openBulkActionMenu(page);

		await expect(
			page.getByRole('menuitem', {exact: true, name: action})
		).toBeVisible({timeout: 1000});

		await page.keyboard.press('Escape');
	}).toPass({timeout: 5000});
}

async function selectRow(page: Page, title: string, checked: boolean) {
	const checkbox = page.getByLabel(`Select ${title}`, {exact: true});

	if (checked) {
		await checkbox.check();
	}
	else {
		await checkbox.uncheck();
	}
}

test.describe(
	'transformFDSBulkActions wires the bulk action menu in the Files view',
	{tag: '@LPD-86106'},
	() => {
		test.beforeAll(async ({browser}) => {
			const page = await browser.newPage();

			await performLoginViaApi({page, screenName: 'test'});

			const setupApiHelpers = new ApiHelpers(page);

			titlePrefix = `bulkfilter_${getRandomString().replace(/-/g, '')}`;

			const createdEntries: ObjectEntry[] = [];

			for (let i = 0; i < FILE_COUNT; i++) {
				const title = `${titlePrefix} ${i}`;
				const entry = await setupApiHelpers.objectEntry.postObjectEntry(
					{
						file: {
							fileBase64: VALID_IMAGE_FILE_BASE64,
							name: `${title}.png`,
						},
						objectEntryFolderExternalReferenceCode: 'L_FILES',
						title,
					},
					APPLICATION_NAME,
					SPACE_NAME
				);

				createdEntries.push(entry);
			}

			createdEntryIds = createdEntries.map((entry) => entry.id);

			await page.close();
		});

		test.afterAll(async ({browser}) => {
			const page = await browser.newPage();

			await performLoginViaApi({page, screenName: 'test'});

			const teardownApiHelpers = new ApiHelpers(page);

			if (createdEntryIds?.length) {
				for (const entryId of createdEntryIds) {
					await teardownApiHelpers.objectEntry
						.deleteObjectEntry(APPLICATION_NAME, String(entryId))
						.catch(() => {});
				}
			}

			await page.close();
		});

		test('shows Delete in the bulk menu when a single item is selected', async ({
			assetsPage,
			page,
		}) => {
			await assetsPage.gotoFiles();
			await assetsPage.changeVisualizationMode('Table');

			const dataSetPage = new DataSetPage(page);

			await dataSetPage.search(`${titlePrefix} 0`);
			await selectRow(page, `${titlePrefix} 0`, true);

			await expectBulkActionVisible(page, 'Delete');
		});

		test('shows Delete in the bulk menu when multiple items are selected', async ({
			assetsPage,
			page,
		}) => {
			await assetsPage.gotoFiles();
			await assetsPage.changeVisualizationMode('Table');

			const dataSetPage = new DataSetPage(page);

			await dataSetPage.search(titlePrefix);
			await selectRow(page, `${titlePrefix} 0`, true);
			await selectRow(page, `${titlePrefix} 1`, true);

			await expectBulkActionVisible(page, 'Delete');
		});
	}
);
