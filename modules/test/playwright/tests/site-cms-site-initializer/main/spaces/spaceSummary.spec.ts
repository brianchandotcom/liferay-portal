/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import getRandomString from '../../../../utils/getRandomString';
import {cmsPagesTest} from '../fixtures/cmsPagesTest';

const test = mergeTests(
	cmsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-17564': {enabled: true},
	}),
	loginTest()
);

const validImageFileBase64 =
	'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';

test(
	'Opens in Gallery View by default for files',
	{tag: '@LPD-72056'},
	async ({apiHelpers, page, spaceSummaryPage}) => {
		const spaceName = 'Default';

		const applicationName = 'cms/basic-documents';
		const fileName = getRandomString();

		const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
			{
				file: {
					fileBase64: validImageFileBase64,
					name: `file_${fileName}.png`,
				},
				objectEntryFolderExternalReferenceCode: 'L_FILES',
				title: `title ${fileName}`,
			},
			applicationName,
			'Default'
		);

		try {
			apiHelpers.data.push({
				id: objectEntry.file.id,
				type: 'document',
			});

			await spaceSummaryPage.goto(spaceName);
			await spaceSummaryPage.viewAllFilesLink.click();

			await expect(
				page.getByRole('combobox', {name: 'Gallery View Selected'})
			).toBeVisible();

			await expect(
				spaceSummaryPage.galleryPreview.getByRole('img', {
					name: `file_${fileName}.png`,
				})
			).toBeVisible();
		}
		finally {
			await apiHelpers.objectEntry.deleteObjectEntry(
				applicationName,
				String(objectEntry.id)
			);
		}
	}
);

test(
	'Can access to View All Files page',
	{tag: '@LPD-62706'},
	async ({page, spaceSummaryPage}) => {
		const spaceName = 'Default';

		await spaceSummaryPage.goto(spaceName);
		await spaceSummaryPage.viewAllFilesLink.click();

		await expect(page.getByRole('link', {name: spaceName})).toBeVisible();
		expect(page.getByRole('link', {name: 'Files'})).toBeVisible();
	}
);

test(
	'Can view added files in the space summary page',
	{tag: '@LPD-62706'},
	async ({apiHelpers, page, spaceSummaryPage}) => {
		const applicationName = 'cms/basic-documents';
		const spaceName = 'Default';

		const file1Title = `title ${getRandomString()}`;

		const objectEntry1 = await apiHelpers.objectEntry.postObjectEntry(
			{
				file: {
					fileBase64: 'R0lGODlhAQABAAAAACw=',
					name: `file_${getRandomString()}.png`,
				},
				objectEntryFolderExternalReferenceCode: 'L_FILES',
				title: file1Title,
			},
			applicationName,
			spaceName
		);

		await spaceSummaryPage.goto(spaceName);

		expect(page.getByText(file1Title)).toBeVisible();

		await apiHelpers.objectEntry.deleteObjectEntry(
			applicationName,
			String(objectEntry1.id)
		);
	}
);

test(
	'Can access to View All Content page',
	{tag: '@LPD-62706'},
	async ({page, spaceSummaryPage}) => {
		const spaceName = 'Default';

		await spaceSummaryPage.goto(spaceName);
		await spaceSummaryPage.viewAllContentLink.click();

		await expect(page.getByRole('link', {name: spaceName})).toBeVisible();
		expect(page.getByRole('link', {name: 'Contents'})).toBeVisible();
	}
);

test(
	'Can add and delete a user group as a member of the space',
	{tag: '@LPD-61617'},
	async ({apiHelpers, page, spaceSummaryPage}) => {
		const spaceName = 'Default';

		await spaceSummaryPage.goto(spaceName);

		const userGroup = await apiHelpers.headlessAdminUser.postUserGroup();

		await spaceSummaryPage.addUserOrUserGroup(userGroup.name, 'groups');

		await spaceSummaryPage.userGroupsTab.click();

		await expect(page.getByText(userGroup.name)).toBeVisible();

		await spaceSummaryPage.removeUserOrUserGroup(userGroup.name, 'groups');

		await spaceSummaryPage.userGroupsTab.click();

		await expect(page.getByText(userGroup.name)).not.toBeVisible();
	}
);

test(
	'Can view added content in the space summary page',
	{tag: '@LPD-62706'},
	async ({apiHelpers, page, spaceSummaryPage}) => {
		const applicationName = 'cms/basic-web-contents';
		const spaceName = 'Default';

		const file1Title = `title ${getRandomString()}`;

		const objectEntry1 = await apiHelpers.objectEntry.postObjectEntry(
			{
				objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
				title: file1Title,
			},
			applicationName,
			spaceName
		);

		await spaceSummaryPage.goto(spaceName);

		expect(page.getByText(file1Title)).toBeVisible();

		await apiHelpers.objectEntry.deleteObjectEntry(
			applicationName,
			String(objectEntry1.id)
		);
	}
);

test(
	'Can connect and disconnect a site for the Default space',
	{tag: '@LPD-39906'},
	async ({page, spaceSummaryPage}) => {
		const spaceName = 'Default';
		const siteName = 'Global';

		const globalSiteLocator = page
			.getByTestId('space-summary-connected-sites')
			.getByText(siteName, {exact: true});

		await spaceSummaryPage.goto(spaceName);

		expect(globalSiteLocator).not.toBeVisible();

		await spaceSummaryPage.connectSite(siteName);

		await expect(
			page.getByRole('heading', {name: 'Sites (1)'})
		).toBeVisible();
		await expect(globalSiteLocator).toBeVisible();

		await page
			.getByRole('row', {name: `${siteName} ${siteName} Actions`})
			.getByRole('button')
			.click();
		await page.getByRole('menuitem', {name: 'Disconnect'}).click();

		expect(globalSiteLocator).not.toBeVisible();
	}
);

test(
	'Can view Share modal for added content',
	{tag: '@LPD-62554'},
	async ({apiHelpers, assetsPage, page, spaceSummaryPage}) => {
		const applicationName = 'cms/basic-web-contents';
		const file1Title = `Title ${getRandomString()}`;
		const spaceName = `Space ${getRandomString()}`;
		let objectEntry1;

		await apiHelpers.headlessAssetLibrary.createAssetLibrary({
			name: spaceName,
			settings: {
				logoColor: 'outline-3',
				sharingEnabled: true,
			},
			type: 'Space',
		});

		try {
			objectEntry1 = await apiHelpers.objectEntry.postObjectEntry(
				{
					objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
					title: file1Title,
				},
				applicationName,
				spaceName
			);

			await spaceSummaryPage.goto(spaceName);

			await assetsPage.execItemAction({
				action: 'Share',
				filter: file1Title,
			});

			await expect(page.locator('.modal-title')).toContainText(
				file1Title
			);
		}
		finally {
			await apiHelpers.objectEntry.deleteObjectEntry(
				applicationName,
				String(objectEntry1.id)
			);
		}
	}
);
