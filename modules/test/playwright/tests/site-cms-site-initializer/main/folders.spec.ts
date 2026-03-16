/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
import {cmsPagesTest} from './fixtures/cmsPagesTest';

const test = mergeTests(
	cmsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-17564': {enabled: true},
	}),
	loginTest()
);

test(
	'Can edit a folder',
	{tag: '@LPD-42841'},
	async ({apiHelpers, assetsPage, page}) => {
		const folderTitle = getRandomString();

		const folderData =
			await apiHelpers.objectFolder.createObjectEntryFolder({
				scopeKey: 'Default',
				title: folderTitle,
			});

		await assetsPage.gotoFiles();

		await assetsPage.execCardItemAction({
			action: 'Edit',
			filter: folderTitle,
		});

		const newFolderTitle = getRandomString();

		await page.getByLabel('Name').fill(newFolderTitle);
		await page.getByLabel('Description').fill('folder description');
		await page.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(
			page,
			`Success:${newFolderTitle} was updated successfully.`
		);

		await expect(
			page.getByLabel(newFolderTitle, {exact: true})
		).toBeVisible();

		await apiHelpers.objectFolder.deleteObjectEntryFolder(folderData.id);
	}
);

test(
	'Folders should not show status',
	{tag: '@LPD-78615'},
	async ({apiHelpers, assetsPage, page}) => {
		const folderTitle = getRandomString();

		await apiHelpers.objectFolder.createObjectEntryFolder({
			scopeKey: 'Default',
			title: folderTitle,
		});

		await assetsPage.gotoFiles();

		await assetsPage.changeVisualizationMode('Table');

		const row = page
			.getByRole('row')
			.filter({has: page.getByRole('link', {name: folderTitle})});

		await expect(
			row.getByRole('cell', {exact: true, name: '--'})
		).toBeVisible();
	}
);

test(
	'Folders have View Folder action, but not View',
	{tag: '@LPD-58720'},
	async ({apiHelpers, assetsPage, page}) => {
		const folderTitle = getRandomString();

		const folderData =
			await apiHelpers.objectFolder.createObjectEntryFolder({
				scopeKey: 'Default',
				title: folderTitle,
			});

		await assetsPage.gotoFiles();

		assetsPage
			.getCardItem(folderTitle)
			.getByLabel(`${folderTitle} Actions`)
			.click();

		expect(
			page.getByRole('menuitem', {exact: true, name: 'View'})
		).toBeHidden();
		expect(
			page.getByRole('menuitem', {exact: true, name: 'View Folder'})
		).toBeVisible();

		await apiHelpers.objectFolder.deleteObjectEntryFolder(folderData.id);
	}
);

test(
	'Info panel shows correct number of assets in folder',
	{tag: '@LPD-69166'},
	async ({apiHelpers, assetsPage, page}) => {
		const folderName = `Folder ${getRandomInt()}`;

		const folder = await apiHelpers.objectFolder.createObjectEntryFolder({
			parentObjectEntryFolderExternalReferenceCode: 'L_FILES',
			scopeKey: 'Default',
			title: folderName,
		});

		try {
			await apiHelpers.objectEntry.postObjectEntry(
				{
					file: {
						fileBase64: 'R0lGODlhAQABAAAAACw=',
						name: `file_${getRandomString()}.png`,
					},
					objectEntryFolderExternalReferenceCode:
						folder.externalReferenceCode,
					title: `Content ${getRandomInt()}`,
				},
				'cms/basic-documents',
				'Default'
			);

			await apiHelpers.objectFolder.createObjectEntryFolder({
				parentObjectEntryFolderExternalReferenceCode:
					folder.externalReferenceCode,
				scopeKey: 'Default',
				title: `Subfolder ${getRandomInt()}`,
			});

			await assetsPage.gotoFiles();

			await page.getByLabel(/View Selected/i).click();
			await page.getByRole('option', {name: 'Table'}).click();

			await page
				.getByRole('row', {name: folderName})
				.getByRole('checkbox')
				.check();

			await page.getByRole('button', {name: 'Show Info Panel'}).click();

			expect(
				await page.getByTestId('number-of-assets').textContent()
			).toContain('2');
		}
		finally {
			await apiHelpers.objectFolder.deleteObjectEntryFolder(folder.id);
		}
	}
);
