/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import fs from 'fs/promises';

import {OBJECT_ENTRY_FOLDER_CLASS_NAME} from '../../../../../apps/site/site-cms-site-initializer/src/main/resources/META-INF/resources/js/common/utils/constants';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import performLogin, {
	performLoginViaApi,
	performLogout,
	performUserSwitch,
	userData,
} from '../../../utils/performLogin';
import {PORTLET_URLS} from '../../../utils/portletUrls';
import {cmsPagesTest} from './fixtures/cmsPagesTest';

const validImageFileBase64 =
	'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';

const test = mergeTests(
	cmsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-17564': {enabled: true},
	}),
	loginTest()
);

test(
	'Renders video preview for External Video entries',
	{tag: '@LPD-78868'},
	async ({apiHelpers, assetsPage, page}) => {
		const applicationName = 'cms/external-videos';
		const fileName = getRandomString();

		const VIDEO_ID = 'IqCSx3omX4o';

		const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
			{
				objectEntryFolderExternalReferenceCode: 'L_FILES',
				title: `title ${fileName}`,
				videoURL: `https://www.youtube.com/watch?v=${VIDEO_ID}`,
			},
			applicationName,
			'Default'
		);

		apiHelpers.data.push({
			id: objectEntry.id,
			type: 'document',
		});

		await assetsPage.gotoFiles();

		await expect(
			page.getByRole('combobox', {name: 'Gallery View Selected'})
		).toBeVisible();

		const videoPreview = page.locator(
			`div.video-preview:has(iframe[src*="${VIDEO_ID}"])`
		);

		await expect(videoPreview).toBeVisible();
	}
);

test(
	'Opens in Gallery View by default',
	{tag: '@LPD-68467'},
	async ({apiHelpers, assetsPage, page}) => {
		const applicationName = 'cms/basic-documents';
		const fileName = getRandomString();
		const parentFolderTitle = getRandomString();

		const parentFolder =
			await apiHelpers.objectFolder.createObjectEntryFolder({
				scopeKey: 'Default',
				title: parentFolderTitle,
			});

		const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
			{
				file: {
					fileBase64: validImageFileBase64,
					name: `file_${fileName}.png`,
				},
				objectEntryFolderExternalReferenceCode:
					parentFolder.externalReferenceCode,
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

			await assetsPage.gotoFiles();

			await page
				.getByRole('link', {name: parentFolderTitle})
				.click();

			await expect(
				page.getByRole('combobox', {name: 'Gallery View Selected'})
			).toBeVisible();

			await expect(
				assetsPage.galleryPreview.getByRole('img', {
					name: `file_${fileName}.png`,
				})
			).toBeVisible();

			await expect(
				assetsPage.galleryNavigation.getByRole('button', {
					name: 'Previous',
				})
			).toBeDisabled();

			await expect(
				assetsPage.galleryNavigation.getByRole('button', {name: 'Next'})
			).toBeDisabled();

			await expect(
				assetsPage.galleryThumbnails.locator('.card')
			).toHaveCount(1);
		}
		finally {
			await apiHelpers.objectEntry.deleteObjectEntry(
				applicationName,
				String(objectEntry.id)
			);
			await apiHelpers.objectFolder.deleteObjectEntryFolder(
				parentFolder.id
			);
		}
	}
);

test(
	'Navigates between items in Gallery View',
	{tag: '@LPD-68467'},
	async ({apiHelpers, assetsPage}) => {
		const applicationName = 'cms/basic-documents';

		const image1 = `image_${getRandomString()}`;
		const image2 = `image_${getRandomString()}`;
		const folder = `folder_${getRandomString()}`;

		const objectEntry1 = await apiHelpers.objectEntry.postObjectEntry(
			{
				file: {
					fileBase64: validImageFileBase64,
					name: `${image1}.png`,
				},
				objectEntryFolderExternalReferenceCode: 'L_FILES',
				title: `title ${image1}`,
			},
			applicationName,
			'Default'
		);

		const objectEntry2 = await apiHelpers.objectEntry.postObjectEntry(
			{
				file: {
					fileBase64: validImageFileBase64,
					name: `${image2}.png`,
				},
				objectEntryFolderExternalReferenceCode: 'L_FILES',
				title: `title ${image1}`,
			},
			applicationName,
			'Default'
		);

		const folderData =
			await apiHelpers.objectFolder.createObjectEntryFolder({
				scopeKey: 'Default',
				title: folder,
			});

		try {
			apiHelpers.data.push({
				id: objectEntry1.file.id,
				type: 'document',
			});

			apiHelpers.data.push({
				id: objectEntry2.file.id,
				type: 'document',
			});

			await assetsPage.gotoFiles();

			await expect(
				assetsPage.galleryPreview.getByRole('img', {
					name: `${image1}.png`,
				})
			).toBeVisible();

			await assetsPage.navigateByGalleryArrows('Next');

			await expect(
				assetsPage.galleryPreview.getByRole('img', {
					name: `${image2}.png`,
				})
			).toBeVisible();

			await assetsPage.navigateByGalleryArrows('Next');

			await expect(
				assetsPage.galleryPreview.getByText(folder)
			).toBeVisible();

			await assetsPage.navigateByGalleryArrows('Next');

			await expect(
				assetsPage.galleryPreview.getByRole('img', {
					name: `${image1}.png`,
				})
			).toBeVisible();

			await assetsPage.navigateByGalleryArrows('Previous');

			await expect(
				assetsPage.galleryPreview.getByText(folder)
			).toBeVisible();
		}
		finally {
			await apiHelpers.objectEntry.deleteObjectEntry(
				applicationName,
				String(objectEntry1.id)
			);
			await apiHelpers.objectEntry.deleteObjectEntry(
				applicationName,
				String(objectEntry2.id)
			);
			await apiHelpers.objectFolder.deleteObjectEntryFolder(
				folderData.id
			);
		}
	}
);

test(
	'Document can be downloaded from the Files section and saved correctly',
	{tag: '@LPD-54566'},
	async ({apiHelpers, assetsPage, page}) => {
		const applicationName = 'cms/basic-documents';
		const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
			{
				file: {
					fileBase64:
						'iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAC0lEQVR42mNkqAcAAIUAgUW0RjgAAAAASUVORK5CYII=',
					name: `file_${getRandomString()}.png`,
				},
				objectEntryFolderExternalReferenceCode: 'L_FILES',
				title: `title ${getRandomString()}`,
			},
			applicationName,
			'Default'
		);

		try {
			apiHelpers.data.push({
				id: objectEntry.file.id,
				type: 'document',
			});

			await assetsPage.gotoFiles();

			const downloadPromise = page.waitForEvent('download');
			await assetsPage.execCardItemAction({
				action: 'Download',
				filter: objectEntry.title,
			});

			const download = await downloadPromise;
			expect(download.suggestedFilename()).toBe(objectEntry.file.name);

			const downloadStat = await fs.stat(await download.path());
			expect(downloadStat.size).toBeGreaterThan(10);
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
	'The Space selector dialog is not shown when creating a Basic Document inside a folder when multiple Spaces exist',
	{tag: '@LPD-57827'},
	async ({apiHelpers, assetsPage, page}) => {
		const assetLibraryName = getRandomString();

		await test.step('Create a new Space', async () => {
			await apiHelpers.headlessAssetLibrary.createAssetLibrary({
				name: assetLibraryName,
				settings: {},
				type: 'Space',
			});
		});

		await test.step('Check number of existing Spaces', async () => {
			const assetLibraries =
				await apiHelpers.headlessAssetLibrary.getAssetLibrariesPage(
					"type eq 'Space'"
				);

			expect(
				assetLibraries.length,
				'At least 2 Spaces should exist'
			).toBeGreaterThan(1);
		});

		const folderData = await test.step('Create a folder', async () => {
			return await apiHelpers.objectFolder.createObjectEntryFolder({
				scopeKey: assetLibraryName,
				title: getRandomString(),
			});
		});

		await test.step('Navigate into the folder', async () => {
			const className =
				await apiHelpers.jsonWebServicesClassName.fetchClassName(
					OBJECT_ENTRY_FOLDER_CLASS_NAME
				);

			await page.goto(
				`${PORTLET_URLS.cmsViewFolder}/${className.classNameId}/${folderData.id}`
			);
		});

		await test.step('Create a Basic Document', async () => {
			await assetsPage.createContent('Single File');
		});

		await test.step('Check the Space selector dialog', async () => {
			await expect(page.getByRole('dialog')).not.toBeVisible();
		});

		await test.step('Check the Space name in the Basic Document creation page', async () => {
			await page
				.getByRole('heading', {name: 'Edit Basic Document'})
				.waitFor();

			const spaceSpan = page.locator(
				'//span[contains(@class,"sticker")]//following-sibling::span[1]'
			);

			await expect(spaceSpan).toContainText(assetLibraryName);
		});
	}
);

test(
	'There is a View action for items the Files section',
	{tag: '@LPD-58720'},
	async ({apiHelpers, assetsPage, page}) => {
		const applicationName = 'cms/basic-documents';
		const imageName = `Image ${getRandomString()}`;

		const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
			{
				file: {
					fileBase64:
						'iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAC0lEQVR42mNkqAcAAIUAgUW0RjgAAAAASUVORK5CYII=',
					name: `file_${getRandomString()}.png`,
				},
				objectEntryFolderExternalReferenceCode: 'L_FILES',
				title: imageName,
			},
			applicationName,
			'Default'
		);

		try {
			apiHelpers.data.push({
				id: objectEntry.file.id,
				type: 'document',
			});

			await assetsPage.gotoFiles();

			await assetsPage.execCardItemAction({
				action: 'View',
				filter: objectEntry.title,
			});

			await expect(page.getByRole('dialog')).toBeVisible();

			await expect(page.getByText(imageName)).toBeVisible();
			await expect(
				page.getByRole('link', {name: 'Download'})
			).toBeVisible();

			await expect(
				assetsPage.modal.body.getByText('No preview available')
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
	'Can navigate through items in the Files section',
	{tag: '@LPD-59866'},
	async ({apiHelpers, assetsPage, page}) => {
		const applicationName = 'cms/basic-documents';

		const image1 = `Image ${getRandomString()}`;
		const image2 = `Image ${getRandomString()}`;
		const folder = `Folder ${getRandomString()}`;

		const objectEntry1 = await apiHelpers.objectEntry.postObjectEntry(
			{
				file: {
					fileBase64:
						'iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAC0lEQVR42mNkqAcAAIUAgUW0RjgAAAAASUVORK5CYII=',
					name: `file_${getRandomString()}.png`,
				},
				objectEntryFolderExternalReferenceCode: 'L_FILES',
				title: image1,
			},
			applicationName,
			'Default'
		);

		const objectEntry2 = await apiHelpers.objectEntry.postObjectEntry(
			{
				file: {
					fileBase64:
						'iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAC0lEQVR42mNkqAcAAIUAgUW0RjgAAAAASUVORK5CYII=',
					name: `file_${getRandomString()}.png`,
				},
				objectEntryFolderExternalReferenceCode: 'L_FILES',
				title: image2,
			},
			applicationName,
			'Default'
		);

		const folderData =
			await apiHelpers.objectFolder.createObjectEntryFolder({
				scopeKey: 'Default',
				title: folder,
			});

		try {
			apiHelpers.data.push({
				id: objectEntry1.file.id,
				type: 'document',
			});

			apiHelpers.data.push({
				id: objectEntry2.file.id,
				type: 'document',
			});

			await assetsPage.gotoFiles();

			await assetsPage.execCardItemAction({
				action: 'View',
				filter: image2,
			});

			await test.step('folders are excluded from the navigation list', async () => {
				await expect(page.getByText('2 of 2')).toBeVisible();
			});

			await test.step('Can navigate to the next item', async () => {
				await expect(
					page.locator('.modal-title').getByText(image2)
				).toBeVisible();
				await assetsPage.modal.body.getByLabel('Next').click();
				await expect(
					page.locator('.modal-title').getByText(image1)
				).toBeVisible();
				await expect(page.getByText('1 of 2')).toBeVisible();
			});

			await test.step('Can open the info panel', async () => {
				await page.getByLabel('Show Details').click();
				await expect(
					page.getByRole('tab', {name: 'More'})
				).toBeInViewport();
				await expect(page.getByText('Metadata')).toBeVisible();
				await expect(page.getByLabel('Show Details')).toHaveClass(
					/active/
				);
			});

			await test.step('Can add a comment', async () => {
				await page.getByLabel('Show Comments').click();

				await expect(
					page.getByRole('tab', {name: 'Details'})
				).not.toBeVisible();

				await expect(page.getByLabel('Show Details')).not.toHaveClass(
					/active/
				);

				await expect(page.getByLabel('Show Comments')).toHaveClass(
					/active/
				);

				const commentText = getRandomString();
				await assetsPage.modal.body
					.getByRole('paragraph')
					.fill(commentText);
				await page.getByRole('button', {name: 'Save'}).click();

				await expect(page.getByText(commentText)).toBeVisible();
			});
		}
		finally {
			await apiHelpers.objectEntry.deleteObjectEntry(
				applicationName,
				String(objectEntry1.id)
			);
			await apiHelpers.objectEntry.deleteObjectEntry(
				applicationName,
				String(objectEntry2.id)
			);
			await apiHelpers.objectFolder.deleteObjectEntryFolder(
				folderData.id
			);
		}
	}
);

test(
	'Can click on title in Cards View',
	{tag: '@LPD-67612'},
	async ({apiHelpers, assetsPage, page}) => {
		const applicationName = 'cms/basic-documents';
		const fileTitle = `title ${getRandomString()}`;

		const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
			{
				file: {
					fileBase64:
						'iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAC0lEQVR42mNkqAcAAIUAgUW0RjgAAAAASUVORK5CYII=',
					name: `file_${getRandomString()}.png`,
				},
				objectEntryFolderExternalReferenceCode: 'L_FILES',
				title: fileTitle,
			},
			applicationName,
			'Default'
		);

		try {
			apiHelpers.data.push({
				id: objectEntry.file.id,
				type: 'document',
			});

			await assetsPage.gotoFiles();
			await assetsPage.changeVisualizationMode('Cards');

			await page.getByRole('link', {name: fileTitle}).click();

			await expect(
				page.getByRole('heading', {name: `Edit ${fileTitle}`})
			).toBeVisible();
		}
		finally {
			await apiHelpers.objectEntry.deleteObjectEntry(
				applicationName,
				String(objectEntry.id)
			);

			await page.getByLabel('Back').click();
			await assetsPage.changeVisualizationMode('Gallery');
		}
	}
);

test(
	'Space Member can preview image files',
	{tag: '@LPD-70422'},
	async ({apiHelpers, assetsPage, page, spaceSummaryPage}) => {
		const applicationName = 'cms/basic-documents';
		const fileTitle = getRandomString();
		const spaceName = 'Default';

		const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
			{
				file: {
					fileBase64: validImageFileBase64,
					name: `file_${fileTitle}.png`,
				},
				objectEntryFolderExternalReferenceCode: 'L_FILES',
				title: fileTitle,
			},
			applicationName,
			spaceName
		);

		try {
			apiHelpers.data.push({
				id: objectEntry.file.id,
				type: 'document',
			});

			let user;

			await test.step('Create an user and add to the Space', async () => {
				user = await apiHelpers.headlessAdminUser.postUserAccount();

				userData[user.alternateName] = {
					name: user.givenName,
					password: 'test',
					surname: user.familyName,
				};

				await spaceSummaryPage.goto(spaceName);

				await spaceSummaryPage.addUserOrUserGroup(user.name, 'users');
			});

			await test.step('Login as a space member and go to Files tab', async () => {
				await performLogout(page);

				await performLogin(page, user.alternateName);

				await assetsPage.gotoFiles();
			});

			await test.step('Check that a space member can preview image files', async () => {
				await assetsPage.changeVisualizationMode('Table');

				await assetsPage.execItemAction({
					action: 'View',
					filter: objectEntry.title,
				});

				await expect(page.getByRole('dialog')).toBeVisible();

				await expect(
					page.getByRole('link', {name: fileTitle})
				).toBeVisible();

				const imgageSrc = await page
					.getByRole('img', {name: fileTitle})
					.getAttribute('src');

				expect(imgageSrc.includes(fileTitle)).toBeTruthy();
			});
		}
		finally {
			await performLogout(page);

			await performLoginViaApi({page, screenName: 'test'});

			await apiHelpers.objectEntry.deleteObjectEntry(
				applicationName,
				String(objectEntry.id)
			);
		}
	}
);

test(
	'Shared file shows a shared icon in the Files section only for the recipient',
	{tag: '@LPD-66045'},
	async ({apiHelpers, assetsPage, page}) => {
		const applicationName = 'cms/basic-documents';
		const fileTitle1 = `File ${getRandomString()}`;
		const fileTitle2 = `File ${getRandomString()}`;
		const spaceName = `Space ${getRandomString()}`;

		await apiHelpers.headlessAssetLibrary.createAssetLibrary({
			name: spaceName,
			settings: {},
			type: 'Space',
		});

		const objectEntry1 = await apiHelpers.objectEntry.postObjectEntry(
			{
				file: {
					fileBase64:
						'iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAC0lEQVR42mNkqAcAAIUAgUW0RjgAAAAASUVORK5CYII=',
					name: `file_${getRandomString()}.png`,
				},
				objectEntryFolderExternalReferenceCode: 'L_FILES',
				title: fileTitle1,
			},
			applicationName,
			spaceName
		);

		const objectEntry2 = await apiHelpers.objectEntry.postObjectEntry(
			{
				file: {
					fileBase64:
						'iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAC0lEQVR42mNkqAcAAIUAgUW0RjgAAAAASUVORK5CYII=',
					name: `file_${getRandomString()}.png`,
				},
				objectEntryFolderExternalReferenceCode: 'L_FILES',
				title: fileTitle2,
			},
			applicationName,
			spaceName
		);

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

			await apiHelpers.objectEntry.postObjectEntryCollaborators(
				[
					{
						actionIds: ['VIEW'],
						id: user.id,
						share: true,
						type: 'User',
					},
				],
				applicationName,
				objectEntry1.id
			);

			await performUserSwitch(page, user.alternateName);

			await assetsPage.gotoFiles();

			await assetsPage.changeVisualizationMode('Table');

			const fileRow1 = page
				.getByRole('row')
				.filter({has: page.getByRole('link', {name: fileTitle1})});

			await expect(fileRow1).toBeVisible();

			await expect(
				fileRow1.locator('.lexicon-icon-users').first()
			).toBeVisible();

			const fileRow2 = page
				.getByRole('row')
				.filter({has: page.getByRole('link', {name: fileTitle2})});

			await expect(fileRow2).toBeVisible();

			await expect(fileRow2.locator('.lexicon-icon-users')).toHaveCount(
				0
			);
		}
		finally {
			await apiHelpers.objectEntry.deleteObjectEntry(
				applicationName,
				String(objectEntry1.id)
			);

			await apiHelpers.objectEntry.deleteObjectEntry(
				applicationName,
				String(objectEntry2.id)
			);
		}
	}
);
