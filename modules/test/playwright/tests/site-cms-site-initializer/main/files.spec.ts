/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import fs from 'fs/promises';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {PORTLET_URLS} from '../../../utils/portletUrls';
import {cmsPagesTest} from './fixtures/cmsPagesTest';

const test = mergeTests(
	cmsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-17564': {enabled: true},
		'LPS-179669': {enabled: true},
	}),
	loginTest()
);

test(
	'Document can be downloaded from the Files section and saved correctly',
	{tag: '@LPD-54566'},
	async ({apiHelpers, assetsPage, page}) => {
		const applicationName = 'cms/basic-documents';
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
			'Default'
		);

		try {
			apiHelpers.data.push({
				id: objectEntry.file.id,
				type: 'document',
			});

			await assetsPage.gotoFiles();
			await assetsPage.changeVisualizationMode('Table');

			const downloadPromise = page.waitForEvent('download');
			await assetsPage.execItemAction({
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
	'The Space selector dialog is not shown when creating a Basic Document when only the default Space exists',
	{tag: '@LPD-57827'},
	async ({apiHelpers, assetsPage, page}) => {
		await test.step('Check number of existing Spaces', async () => {
			const assetLibraries =
				await apiHelpers.headlessAssetLibrary.getAssetLibrariesPage(
					"type eq 'Space'"
				);

			expect(
				assetLibraries.length,
				'Only the default Space should exist'
			).toBe(1);
		});

		await test.step('Create a Basic Document', async () => {
			await assetsPage.gotoFiles();

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

			await expect(spaceSpan).toContainText('Default');
		});
	}
);

test(
	'The Space selector dialog is shown when creating a Basic Document when multiple Spaces exist',
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

		await test.step('Create a Basic Document', async () => {
			await assetsPage.gotoFiles();

			await assetsPage.createContent('Single File');
		});

		await test.step('Check the Space selector dialog', async () => {
			await page.getByRole('dialog').waitFor();

			await page.getByLabel('SpaceRequired').click();

			await page.getByRole('option', {name: assetLibraryName}).click();

			await page.getByRole('button', {name: 'Save'}).click();
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
					'com.liferay.object.model.ObjectEntryFolder'
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
					fileBase64: 'R0lGODlhAQABAAAAACw=',
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

			await assetsPage.execItemAction({
				action: 'View',
				filter: objectEntry.title,
			});

			await expect(page.getByRole('dialog')).toBeVisible();

			await expect(page.getByText(imageName)).toBeVisible();
			await expect(
				page.getByRole('link', {name: 'Download'})
			).toBeVisible();

			await expect(page.getByText('No preview available')).toBeVisible();
		}
		finally {
			await apiHelpers.objectEntry.deleteObjectEntry(
				applicationName,
				String(objectEntry.id)
			);
		}
	}
);
