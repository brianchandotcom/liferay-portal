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

async function getSpaceGroupId(page: Page, spaceName: string): Promise<string> {
	await page.goto('/web/cms');
	await page.getByRole('menuitem', {name: spaceName}).click();
	await expect(page.getByRole('button', {name: 'Add Content'})).toBeVisible();

	const currentUrl = page.url();
	const urlMatch = currentUrl.match(/\/space\/(\d+)\/(\d+)/);
	const groupId = urlMatch?.[2];

	return groupId;
}

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
	const applicationName = 'cms/basic-web-contents';
	const space1Name = `Space ${getRandomString()}`;
	const space2Name = `Space ${getRandomString()}`;
	const contentTitle = `Content ${getRandomString()}`;

	await test.step('Create two CMS spaces', async () => {
		await apiHelpers.headlessAssetLibrary.createAssetLibrary({
			name: space1Name,
			type: 'Space',
			settings: {},
		});

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
			applicationName,
			space1Name
		);
	});

	const groupId =
		await test.step('Navigate to space 1 to get its groupId', async () => {
			return await getSpaceGroupId(page, space1Name);
		});

	const exportFilePath = await test.step('Export the space 1', async () => {
		await exportImportPage.goToExport(`/asset-library-${groupId}`);

		const exportFilePath = await exportImportPage.export({
			taskName: `CMS-Export-${getRandomString()}`,
		});
		expect(exportFilePath).toBeTruthy();

		return exportFilePath;
	});

	const groupId2 =
		await test.step('Navigate to space 2 to get its groupId', async () => {
			return await getSpaceGroupId(page, space2Name);
		});

	await test.step('Verify Basic Web Content checkbox appears in import UI', async () => {
		await exportImportPage.goToImport(`/asset-library-${groupId2}`);
		await exportImportPage.selectImportFile(exportFilePath);

		const basicWebContentCheckbox = page.getByText('Basic Web Contents1');
		await expect(basicWebContentCheckbox).toBeVisible();
	});
});
