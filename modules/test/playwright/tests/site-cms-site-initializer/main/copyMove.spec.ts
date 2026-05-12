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
import {waitForAlert} from '../../../utils/waitForAlert';
import {cmsPagesTest} from './fixtures/cmsPagesTest';

const test = mergeTests(
	cmsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-11235': {enabled: false},
		'LPD-17564': {enabled: true},
		'LPD-34594': {enabled: true},
	}),
	loginTest()
);

test.beforeEach(async ({apiHelpers, page}) => {
	const user = await apiHelpers.headlessAdminUser.postUserAccount();

	userData[user.alternateName] = {
		name: user.givenName,
		password: 'test',
		surname: user.familyName,
	};

	const cmsAdminRole =
		await apiHelpers.headlessAdminUser.getRoleByName('CMS Administrator');

	await apiHelpers.headlessAdminUser.postRoleUserAccountAssociation(
		cmsAdminRole.id,
		Number(user.id)
	);

	await performUserSwitch(page, user.alternateName);
});

test(
	'Can move a content to a folder in the same Space',
	{tag: '@LPD-89762'},
	async ({apiHelpers, assetsPage, page}) => {
		const applicationName = 'cms/basic-web-contents';
		const spaceName = `Space ${getRandomString()}`;
		const destinationFolderName = `Destination ${getRandomString()}`;
		const contentTitle = `Content ${getRandomString()}`;

		await test.step('Create a new Space', async () => {
			await apiHelpers.headlessAssetLibrary.createAssetLibrary({
				name: spaceName,
				settings: {},
				type: 'Space',
			});
		});

		let destinationFolderId: number;

		await test.step('Create a destination folder in the Space', async () => {
			const folder =
				await apiHelpers.objectFolder.createObjectEntryFolder({
					parentObjectEntryFolderExternalReferenceCode: 'L_CONTENTS',
					scopeKey: spaceName,
					title: destinationFolderName,
				});

			destinationFolderId = folder.id;
		});

		await test.step('Create a content at the root of the Space', async () => {
			await apiHelpers.objectEntry.postObjectEntry(
				{
					objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
					title: contentTitle,
				},
				applicationName,
				spaceName
			);
		});

		await test.step('Move the content to the destination folder', async () => {
			await assetsPage.gotoAll();

			await assetsPage.moveTo({
				destinationFolder: destinationFolderName,
				destinationSpace: spaceName,
				itemTitle: contentTitle,
			});
		});

		await test.step('Info alert for the move is displayed', async () => {
			await waitForAlert(
				page,
				`Info:Moving ${contentTitle} to ${destinationFolderName}.`,
				{type: 'info'}
			);
		});

		await test.step('Success alert for the move is displayed', async () => {
			await waitForAlert(
				page,
				`Success:${contentTitle} was successfully moved to ${destinationFolderName}.`,
				{first: true}
			);
		});

		await test.step('The content is in the destination folder', async () => {
			const response =
				await apiHelpers.objectEntry.getObjectDefinitionObjectEntriesByScope(
					applicationName,
					encodeURIComponent(spaceName),
					new URLSearchParams({pageSize: '100'})
				);

			const movedItems = response.items.filter(
				(item: {objectEntryFolderId: number}) =>
					item.objectEntryFolderId === destinationFolderId
			);

			expect(movedItems).toHaveLength(1);
			expect(movedItems[0].title).toBe(contentTitle);
		});
	}
);
