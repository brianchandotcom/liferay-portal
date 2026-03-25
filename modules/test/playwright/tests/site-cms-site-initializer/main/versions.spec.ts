/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import path from 'path';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import performLogin, {
	performLogout,
	userData,
} from '../../../utils/performLogin';
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
	'Can view a content version',
	{tag: '@LPD-64984'},
	async ({apiHelpers, assetsPage, page}) => {
		const applicationName = 'cms/basic-web-contents';
		const spaceName = 'Default';

		const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
			{
				objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
				title: `title ${getRandomString()}`,
			},
			applicationName,
			spaceName
		);

		await assetsPage.gotoContents();

		await testCanViewVersion(assetsPage, page, objectEntry.title, 'Table');

		await apiHelpers.objectEntry.deleteObjectEntry(
			applicationName,
			String(objectEntry.id)
		);
	}
);

test(
	'Can view a file version',
	{tag: '@LPD-64984'},
	async ({apiHelpers, assetsPage, page}) => {
		const applicationName = 'cms/basic-documents';
		const spaceName = 'Default';

		const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
			{
				file: {
					fileBase64: 'R0lGODlhAQABAAAAACw=',
					name: `file_${getRandomString()}.png`,
				},
				objectEntryFolderExternalReferenceCode: 'L_FILES',
				title: `title ${getRandomString()}`,
			},
			applicationName,
			spaceName
		);

		await assetsPage.gotoFiles();

		await assetsPage.changeVisualizationMode('Gallery');

		await testCanViewVersion(
			assetsPage,
			page,
			objectEntry.title,
			'Gallery'
		);

		await apiHelpers.objectEntry.deleteObjectEntry(
			applicationName,
			String(objectEntry.id)
		);
	}
);

test(
	'File version history shows View action only once for space admin',
	{tag: '@LPD-83845'},
	async ({apiHelpers, assetsPage, contentsPage, page, spaceSummaryPage}) => {
		const fileTitle = `title ${getRandomString()}`;
		const spaceName = `Space ${getRandomString()}`;

		await test.step('Create a new Space', async () => {
			await apiHelpers.headlessAssetLibrary.createAssetLibrary({
				name: spaceName,
				settings: {},
				type: 'Space',
			});
		});

		await test.step('Create a file entry via UI', async () => {
			await spaceSummaryPage.goto(spaceName);

			await assetsPage.gotoFiles();

			await contentsPage.createContent('Single File', spaceName);

			await contentsPage.fillData([{label: 'Title', value: fileTitle}]);

			const fileChooserPromise = page.waitForEvent('filechooser');

			await page
				.getByRole('button', {exact: true, name: 'Select File'})
				.click();

			const fileChooser = await fileChooserPromise;

			await fileChooser.setFiles(
				path.join(__dirname, '/dependencies/file_upload_image_1.jpg')
			);

			await expect(
				page.getByText('file_upload_image_1.jpg')
			).toBeVisible();

			await contentsPage.saveContent();
		});

		await test.step('Add new user as space member and space admin', async () => {
			const user = await apiHelpers.headlessAdminUser.postUserAccount();

			userData[user.alternateName] = {
				name: user.givenName,
				password: 'test',
				surname: user.familyName,
			};

			await spaceSummaryPage.goto(spaceName);

			await spaceSummaryPage.addUserOrUserGroup(user.name, 'users');

			await spaceSummaryPage.addRoleToSpaceMember(
				'Space Administrator',
				user.name
			);

			await performLogout(page);

			await performLogin(page, user.alternateName);
		});

		await test.step('Go to file version history and check View action appears only once', async () => {
			await assetsPage.gotoFiles();

			await assetsPage.execCardItemAction({
				action: 'View History',
				filter: fileTitle,
			});

			const versionRow = assetsPage.getItem(fileTitle);

			await versionRow
				.getByRole('button', {
					name: `${fileTitle} Actions`,
				})
				.first()
				.click();

			await expect(
				page.getByRole('menuitem', {
					exact: true,
					name: 'View',
				})
			).toHaveCount(1);
		});
	}
);

async function testCanViewVersion(
	assetsPage,
	page,
	title: string,
	view: 'Table' | 'Gallery'
) {
	expect(page.getByRole('heading', {name: title})).toBeVisible();

	if (view === 'Table') {
		assetsPage.execItemAction({action: 'View History', filter: title});
	}
	else {
		assetsPage.execCardItemAction({action: 'View History', filter: title});
	}

	await expect(
		page.getByRole('heading', {name: `"${title}" History`})
	).toBeVisible();

	await page.getByRole('button', {exact: true, name: title}).click();

	expect(
		page.getByRole('heading', {name: `${title} (Version 1)`})
	).toBeVisible();

	await page.getByRole('button', {name: 'Close'}).click();
}
