/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {ApiHelpers} from '../../../helpers/ApiHelpers';
import getRandomString from '../../../utils/getRandomString';
import {performLoginViaApi} from '../../../utils/performLogin';
import {DataSetPage} from '../main/pages/DataSetPage';
import {cmsPagesTest} from './fixtures/cmsPagesTest';

const test = mergeTests(cmsPagesTest, dataApiHelpersTest, loginTest());

const APPLICATION_NAME = 'cms/basic-documents';

const SPACE_NAME = 'Default';

const VALID_IMAGE_FILE_BASE64 =
	'iVBORw0KGgoAAAANSUhEUgAAAAEAAAABAQMAAAAl21bKAAAAA1BMVEUAAACnej3aAAAAAXRSTlMAQObYZgAAAApJREFUCNdjYAAAAAIAAeIhvDMAAAAASUVORK5CYII=';

let createdEntryId: number;
let title: string;

test.describe(
	'Edit Categories and Edit Tags bulk actions are visible for entries the user can update',
	{tag: '@LPD-89774'},
	() => {
		test.beforeAll(async ({browser}) => {
			const page = await browser.newPage();

			await performLoginViaApi({page, screenName: 'test'});

			const setupApiHelpers = new ApiHelpers(page);

			title = `editbulk_${getRandomString().replace(/-/g, '')}`;

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

			createdEntryId = entry.id;

			await page.close();
		});

		test.afterAll(async ({browser}) => {
			const page = await browser.newPage();

			await performLoginViaApi({page, screenName: 'test'});

			const teardownApiHelpers = new ApiHelpers(page);

			if (createdEntryId) {
				await teardownApiHelpers.objectEntry
					.deleteObjectEntry(APPLICATION_NAME, String(createdEntryId))
					.catch(() => {});
			}

			await page.close();
		});

		test('shows Edit Categories and Edit Tags in the bulk menu', async ({
			assetsPage,
			page,
		}) => {
			await assetsPage.gotoFiles();
			await assetsPage.changeVisualizationMode('Table');

			const dataSetPage = new DataSetPage(page);

			await dataSetPage.search(title);

			await page.getByLabel(`Select ${title}`, {exact: true}).check();

			await expect(async () => {
				await page
					.getByTestId('visualization-mode-table')
					.getByLabel('Actions')
					.click();

				await expect(
					page.getByRole('menuitem', {
						exact: true,
						name: 'Edit Categories',
					})
				).toBeVisible({timeout: 1000});
				await expect(
					page.getByRole('menuitem', {
						exact: true,
						name: 'Edit Tags',
					})
				).toBeVisible({timeout: 1000});

				await page.keyboard.press('Escape');
			}).toPass({timeout: 5000});
		});
	}
);
