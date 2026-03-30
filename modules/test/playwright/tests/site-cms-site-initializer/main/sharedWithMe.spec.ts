/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {performUserSwitch, userData} from '../../../utils/performLogin';
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
	'Shared entries show status "Not Visible" when assets are sent to the Recycle Bin',
	{tag: '@LPD-83882'},
	async ({apiHelpers, page, sharedWithMePage}) => {
		const spaceName = `Space ${getRandomString()}`;
		let space = null;

		await test.step('Create a new space with recycle bin enabled', async () => {
			space = await apiHelpers.headlessAssetLibrary.createAssetLibrary({
				name: spaceName,
				settings: {
					trashEnabled: true,
				},
				type: 'Space',
			});
		});

		const objectEntryTitle = `Content ${getRandomString()}`;
		let objectEntry = null;

		await test.step('Create a content', async () => {
			objectEntry = await apiHelpers.objectEntry.postObjectEntry(
				{
					objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
					title: objectEntryTitle,
				},
				'cms/basic-web-contents',
				spaceName
			);
		});

		const objectEntryFolderTitle = `Folder ${getRandomString()}`;
		let objectEntryFolder = null;

		await test.step('Create a folder', async () => {
			objectEntryFolder = await apiHelpers.objectFolder.createObjectEntryFolder(
				{
					parentObjectEntryFolderExternalReferenceCode: 'L_CONTENTS',
					scopeKey: spaceName,
					title: objectEntryFolderTitle,
				}
			);
		});

		let user = null;

		await test.step('Create a user', async () => {
			user = await apiHelpers.headlessAdminUser.postUserAccount();

			userData[user.alternateName] = {
				name: user.givenName,
				password: 'test',
				surname: user.familyName,
			};

			await apiHelpers.jsonWebServicesUser.addGroupUsers(space.siteId, [
				user.id,
			]);
		});

		await test.step('Share the content to the user', async () => {
			await apiHelpers.objectEntry.postObjectEntryCollaborators(
				[
						{
							actionIds: ['DOWNLOAD', 'VIEW'],
							id: user.id,
							share: false,
							type: 'User',
						},
					],
					'cms/basic-web-contents',
					objectEntry.id
			);
		});

		await test.step('Share the folder to the user', async () => {
			await apiHelpers.objectFolder.postObjectEntryFolderCollaborators(
				[
						{
							actionIds: ['VIEW'],
							id: user.id,
							share: false,
							type: 'User',
						},
					],
					objectEntryFolder.id
			);
		});

		await test.step('Verify that the user can see the shared assets', async () => {
			await performUserSwitch(page, user.alternateName);

			await sharedWithMePage.expectAssetEntryToBeVisible(objectEntryTitle);
			await sharedWithMePage.expectAssetEntryToBeVisible(objectEntryFolderTitle);
		});

		await test.step('Delete the content and the folder so they go into the Recycle Bin', async () => {
			await performUserSwitch(page, 'test');

			await expect(
				await apiHelpers.objectEntry.deleteObjectEntry(
					'cms/basic-web-contents',
					objectEntry.id
				)
			).toBeOK();

			await expect(
				await apiHelpers.objectFolder.deleteObjectEntryFolder(
					objectEntryFolder.id
				)
			).toBeOK();
		});

		await test.step('Verify that the user can see deleted shared assets as "Not Visible"', async () => {
			await performUserSwitch(page, user.alternateName);

			await sharedWithMePage.expectAssetEntryNotToBeVisible(objectEntryTitle);
			await sharedWithMePage.expectAssetEntryNotToBeVisible(objectEntryFolderTitle);
		});
	}
);
