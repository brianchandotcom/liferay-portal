/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests, Page} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {exportImportPagesTest} from './fixtures/exportImportPagesTest';

const test = mergeTests(
	dataApiHelpersTest,
	exportImportPagesTest,
	featureFlagsTest({
		'LPD-34594': {enabled: true},
		'LPD-11235': {enabled: true},
		'LPD-17564': {enabled: true},
		'LPD-35443': {enabled: true},
		'LPD-35914': {enabled: true},
	}),
	loginTest()
);

test('Basic Web Content checkbox is displayed when importing LAR with Basic Web Content', async ({
	apiHelpers,
	exportImportPage,
	page,
}) => {
	const space1Name = `Space ${getRandomString()}`;
	const space2Name = `Space ${getRandomString()}`;
	const contentTitle = `Content ${getRandomString()}`;

	const {assetLibraryId1, assetLibraryId2} =
		await test.step('Create two CMS spaces', async () => {
			const assetLibrary1 =
				await apiHelpers.headlessAssetLibrary.createAssetLibrary({
					name: space1Name,
					type: 'Space',
					settings: {},
				});

			const assetLibrary2 =
				await apiHelpers.headlessAssetLibrary.createAssetLibrary({
					name: space2Name,
					type: 'Space',
					settings: {},
				});

			await apiHelpers.objectEntry.postObjectEntry(
				{
					objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
					title: contentTitle,
				},
				'cms/basic-web-contents',
				space1Name
			);

			return {
				assetLibraryId1: assetLibrary1.id,
				assetLibraryId2: assetLibrary2.id,
			};
		});

	const exportFilePath = await test.step('Export the space 1', async () => {
		await exportImportPage.goToExport(`/asset-library-${assetLibraryId1}`);

		const exportFilePath = await exportImportPage.export({
			taskName: `CMS-Export-${getRandomString()}`,
		});
		expect(exportFilePath).toBeTruthy();

		return exportFilePath;
	});

	await test.step('Verify Basic Web Content checkbox appears in import UI', async () => {
		await exportImportPage.goToImport(`/asset-library-${assetLibraryId2}`);
		await exportImportPage.selectImportFile({filePath: exportFilePath});

		const basicWebContentCheckbox = page.getByText('Basic Web Contents1');
		await expect(basicWebContentCheckbox).toBeVisible();
	});
});
