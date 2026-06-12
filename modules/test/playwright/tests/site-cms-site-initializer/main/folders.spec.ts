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
import {performUserSwitch, userData} from '../../../utils/performLogin';
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

		const nameInput = page.getByLabel('Name');

		await expect(nameInput).toHaveValue(folderTitle);

		await nameInput.fill(newFolderTitle);
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
	{tag: ['@LPD-78615', '@LPD-92355']},
	async ({apiHelpers, assetsPage, page}) => {
		const folderTitle = getRandomString();

		await apiHelpers.objectFolder.createObjectEntryFolder({
			parentObjectEntryFolderExternalReferenceCode: 'L_CONTENTS',
			scopeKey: 'Default',
			title: folderTitle,
		});

		await assetsPage.gotoContents();

		const row = page
			.getByRole('row')
			.filter({has: page.getByRole('link', {name: folderTitle})});

		await expect(row.locator('.cell-embedded-status')).toHaveText('--');
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

test(
	'Duplicating a folder creates a copy in the same parent',
	{tag: '@LPD-88657'},
	async ({apiHelpers, assetsPage, page}) => {
		const folderName = `Folder ${getRandomString()}`;
		const spaceName = 'Default';

		await test.step('Create a folder in the Space', async () => {
			await apiHelpers.objectFolder.createObjectEntryFolder({
				parentObjectEntryFolderExternalReferenceCode: 'L_CONTENTS',
				scopeKey: spaceName,
				title: folderName,
			});
		});

		await test.step('Duplicate the folder', async () => {
			await assetsPage.gotoSpaceContents(spaceName);

			await assetsPage.execItemAction({
				action: 'Duplicate',
				filter: folderName,
				parentAction: 'Copy',
			});

			await expect(
				page.getByRole('link', {
					exact: true,
					name: `${folderName} (Copy)`,
				})
			).toBeVisible();
		});

		await test.step('Duplicate the original again and check the suffix increments', async () => {
			await assetsPage.execItemAction({
				action: 'Duplicate',
				filter: folderName,
				parentAction: 'Copy',
			});

			await expect(
				page.getByRole('link', {
					exact: true,
					name: `${folderName} (Copy 1)`,
				})
			).toBeVisible();
		});
	}
);

test(
	'Shared folder shows a shared icon in the Files section only for the recipient',
	{tag: '@LPD-66045'},
	async ({apiHelpers, assetsPage, page}) => {
		const folderTitle1 = `Folder ${getRandomString()}`;
		const folderTitle2 = `Folder ${getRandomString()}`;
		const spaceName = `Space ${getRandomString()}`;

		const space = await apiHelpers.headlessAssetLibrary.createAssetLibrary({
			name: spaceName,
			settings: {},
			type: 'Space',
		});

		const objectEntryFolder1 =
			await apiHelpers.objectFolder.createObjectEntryFolder({
				parentObjectEntryFolderExternalReferenceCode: 'L_FILES',
				scopeKey: space.assetLibraryKey,
				title: folderTitle1,
			});

		const objectEntryFolder2 =
			await apiHelpers.objectFolder.createObjectEntryFolder({
				parentObjectEntryFolderExternalReferenceCode: 'L_FILES',
				scopeKey: space.assetLibraryKey,
				title: folderTitle2,
			});

		try {
			const user = await apiHelpers.headlessAdminUser.postUserAccount();

			userData[user.alternateName] = {
				name: user.givenName,
				password: 'test',
				surname: user.familyName,
			};

			const cmsAdminRole =
				await apiHelpers.headlessAdminUser.getRoleByName(
					'CMS Administrator'
				);

			await apiHelpers.headlessAdminUser.postRoleUserAccountAssociation(
				cmsAdminRole.id,
				Number(user.id)
			);

			await apiHelpers.objectFolder.postObjectEntryFolderCollaborators(
				[
					{
						actionIds: ['VIEW'],
						id: user.id,
						share: true,
						type: 'User',
					},
				],
				objectEntryFolder1.id
			);

			await performUserSwitch(page, user.alternateName);

			await assetsPage.gotoFiles();

			await assetsPage.changeVisualizationMode('Table');

			const folderRow1 = page
				.getByRole('row')
				.filter({has: page.getByRole('link', {name: folderTitle1})});

			await expect(folderRow1).toBeVisible();

			await expect(
				folderRow1.locator('.lexicon-icon-users').first()
			).toBeVisible();

			const folderRow2 = page
				.getByRole('row')
				.filter({has: page.getByRole('link', {name: folderTitle2})});

			await expect(folderRow2).toBeVisible();

			await expect(folderRow2.locator('.lexicon-icon-users')).toHaveCount(
				0
			);
		}
		finally {
			await apiHelpers.objectFolder.deleteObjectEntryFolder(
				objectEntryFolder1.id
			);

			await apiHelpers.objectFolder.deleteObjectEntryFolder(
				objectEntryFolder2.id
			);
		}
	}
);
