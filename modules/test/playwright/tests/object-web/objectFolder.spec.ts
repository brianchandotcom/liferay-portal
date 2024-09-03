/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accountSettingsPagesTest} from '../../fixtures/accountSettingsPagesTest';
import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';

export const test = mergeTests(
	accountSettingsPagesTest,
	apiHelpersTest,
	loginTest(),
	objectPagesTest
);

test.describe('manage object definitions through model builder', () => {
	test('navigate between object folders on model builder page', async ({
		apiHelpers,
		modelBuilderLeftSidebarPage,
		modelBuilderPage,
	}) => {
		const objectFolders: ObjectFolder[] = await Promise.all(
			Array.apply(null, Array(5)).map(async () => {
				return await apiHelpers.objectAdmin.postRandomObjectFolder();
			})
		);

		await modelBuilderPage.goto({objectFolderName: 'Default'});

		for (const objectFolder of objectFolders) {
			await expect(
				modelBuilderLeftSidebarPage.otherObjectFolders
			).toBeVisible();

			const otherObjectFolderLocator =
				modelBuilderLeftSidebarPage.getOtherObjectFolderLocator(
					objectFolder.label['en_US']
				);

			await otherObjectFolderLocator.hover();

			await otherObjectFolderLocator
				.getByRole('button', {name: 'Go to Folder'})
				.click();

			await expect(otherObjectFolderLocator).toBeHidden();

			await expect(
				modelBuilderPage.getObjectFolderLabelHeaderLocator(
					objectFolder.label['en_US']
				)
			).toBeVisible();
		}

		// Clean up

		for (const objectFolder of objectFolders) {
			await apiHelpers.objectAdmin.deleteObjectFolder(objectFolder.id);
		}
	});

	test('can edit object folder label and ERC by Model Builder', async ({
		apiHelpers,
		modalEditObjectFolderPage,
		modelBuilderLeftSidebarPage,
		modelBuilderPage,
	}) => {
		const objectFolder =
			await apiHelpers.objectAdmin.postRandomObjectFolder();

		await modelBuilderPage.goto({objectFolderName: objectFolder.name});

		await modelBuilderPage.editObjectFolderDetailsButton.click();

		const newObjectFolderLabel = 'objectFolderLabel' + getRandomInt();
		const newObjectFolderERC = 'objectFolderERC' + getRandomInt();

		await modalEditObjectFolderPage.editObjectFolderDetails(
			newObjectFolderERC,
			newObjectFolderLabel
		);

		expect(
			modelBuilderPage.getObjectFolderLabelHeaderLocator(
				newObjectFolderLabel
			)
		).toBeVisible();

		expect(modelBuilderLeftSidebarPage.selectedObjectFolder).toBeVisible();

		expect(
			modelBuilderPage.getObjectFolderERCHeaderLocator(newObjectFolderERC)
		).toBeVisible();

		// Clean up

		await apiHelpers.objectAdmin.deleteObjectFolder(objectFolder.id);
	});

	test('can navigate from Model Builder to Account Settings', async ({
		accountSettingsPage,
		modelBuilderPage,
		page,
	}) => {
		await modelBuilderPage.goto({objectFolderName: 'Default'});

		await modelBuilderPage.toggleSidebarsButton.click();

		await page.getByTitle('User Profile Menu').click();

		await accountSettingsPage.accountSettingsMenuItem.click();

		await expect(accountSettingsPage.userDisplayData).toBeVisible();
	});
});

test.describe('manage object definitions through view object definitions', () => {
	test('can edit object folder label and ERC in the view object definitions page', async ({
		apiHelpers,
		modalEditObjectFolderPage,
		viewObjectDefinitionsPage,
	}) => {
		const objectFolder =
			await apiHelpers.objectAdmin.postRandomObjectFolder();

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.openObjectFolder(
			objectFolder.label['en_US']
		);

		await viewObjectDefinitionsPage.objectFolderActions.click();

		await viewObjectDefinitionsPage.objectFolderEditLabelAndERCOption.click();

		const newObjectFolderLabel = 'objectFolderLabel' + getRandomInt();
		const newObjectFolderERC = 'objectFolderERC' + getRandomInt();

		await modalEditObjectFolderPage.editObjectFolderDetails(
			newObjectFolderERC,
			newObjectFolderLabel
		);

		expect(
			viewObjectDefinitionsPage.objectFolders.getByText(
				newObjectFolderLabel
			)
		).toBeVisible();

		expect(
			viewObjectDefinitionsPage.getObjectFolderCardHeaderLabel(
				newObjectFolderLabel
			)
		).toBeVisible();

		expect(
			viewObjectDefinitionsPage.getObjectFolderCardHeaderERC(
				newObjectFolderERC
			)
		).toBeVisible();

		// Clean up

		await apiHelpers.objectAdmin.deleteObjectFolder(objectFolder.id);
	});

	test('created object folders are on the left side bar', async ({
		apiHelpers,
		viewObjectDefinitionsPage,
	}) => {
		await viewObjectDefinitionsPage.goto();

		const objectFolderExternalReferenceCode =
			'objectFolder' + getRandomInt();

		const objectFolder = await viewObjectDefinitionsPage.createObjectFolder(
			objectFolderExternalReferenceCode
		);

		await expect(
			viewObjectDefinitionsPage.page
				.locator('li')
				.filter({hasText: objectFolder.label['en_US']})
		).toBeVisible();

		// Clean up

		await apiHelpers.objectAdmin.deleteObjectFolder(objectFolder.id);
	});

	test('default folder does not contains delete and edit options', async ({
		viewObjectDefinitionsPage,
	}) => {
		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.defaultObjectFolder.click();

		await viewObjectDefinitionsPage.objectFolderActions.click();

		await expect(
			viewObjectDefinitionsPage.objectFolderDeleteFolderOption
		).toBeHidden();

		await expect(
			viewObjectDefinitionsPage.objectFolderEditLabelAndERCOption
		).toBeHidden();
	});

	test('navigate between object folders on view object definitions page', async ({
		apiHelpers,
		viewObjectDefinitionsPage,
	}) => {
		const objectFolders: ObjectFolder[] = await Promise.all(
			Array.apply(null, Array(5)).map(async () => {
				return await apiHelpers.objectAdmin.postRandomObjectFolder();
			})
		);

		await viewObjectDefinitionsPage.goto();

		for (const objectFolder of objectFolders) {
			await expect(viewObjectDefinitionsPage.objectFolders).toBeVisible();

			viewObjectDefinitionsPage.openObjectFolder(
				objectFolder.label['en_US']
			);

			expect(
				viewObjectDefinitionsPage.getObjectFolderCardHeaderLabel(
					objectFolder.label['en']
				)
			).toBeVisible();
		}

		// Clean up

		for (const objectFolder of objectFolders) {
			await apiHelpers.objectAdmin.deleteObjectFolder(objectFolder.id);
		}
	});

	test('object definitions from a deleted folder are moved to the default folder', async ({
		apiHelpers,
		viewObjectDefinitionsPage,
	}) => {
		const objectFolder =
			await apiHelpers.objectAdmin.postRandomObjectFolder();

		const objectDefinition1 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode:
					objectFolder.externalReferenceCode,
				status: {code: 0},
			});
		const objectDefinition2 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode:
					objectFolder.externalReferenceCode,
				status: {code: 0},
			});

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.openObjectFolder(
			objectFolder.externalReferenceCode
		);

		await viewObjectDefinitionsPage.objectFolderActions.click();

		await viewObjectDefinitionsPage.deleteObjectFolder(objectFolder.name);

		await viewObjectDefinitionsPage.defaultObjectFolder.click();

		await expect(
			viewObjectDefinitionsPage.frontendDataSetEntries.filter({
				hasText: objectDefinition1.label['en_US'],
			})
		).toBeVisible();

		await expect(
			viewObjectDefinitionsPage.frontendDataSetEntries.filter({
				hasText: objectDefinition2.label['en_US'],
			})
		).toBeVisible();

		// Clean up

		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition1.id
		);
		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition2.id
		);
	});
});
