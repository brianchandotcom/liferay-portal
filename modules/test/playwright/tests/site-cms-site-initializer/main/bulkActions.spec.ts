/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {performUserSwitch, userData} from '../../../utils/performLogin';
import {waitForModal} from '../../../utils/waitFor';
import {waitForAlert} from '../../../utils/waitForAlert';
import {checkInZip} from '../../../utils/zip';
import {structureBuilderPagesTest} from '../structure-builder/fixtures/structureBuilderPagesTest';
import {cmsPagesTest} from './fixtures/cmsPagesTest';

const test = mergeTests(
	cmsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-11235': {enabled: false},
		'LPD-17564': {enabled: true},
		'LPD-34594': {enabled: true},
	}),
	loginTest(),
	structureBuilderPagesTest
);

test(
	'Can delete multiple contents across spaces with and without recycle bin enabled',
	{tag: '@LPD-62787'},
	async ({apiHelpers, assetsPage, page, recycleBinPage}) => {
		const applicationName = 'cms/basic-web-contents';
		const spaceNameWithRecycleBin = `Space ${getRandomString()}`;
		const spaceNameWithoutRecycleBin = `Space ${getRandomString()}`;
		const file1Title = `title ${getRandomString()}`;
		const file2Title = `title ${getRandomString()}`;

		await apiHelpers.headlessAssetLibrary.createAssetLibrary({
			name: spaceNameWithRecycleBin,
			settings: {
				logoColor: 'outline-3',
				sharingEnabled: true,
				trashEnabled: true,
			},
			type: 'Space',
		});

		await apiHelpers.headlessAssetLibrary.createAssetLibrary({
			name: spaceNameWithoutRecycleBin,
			settings: {
				logoColor: 'outline-3',
				sharingEnabled: true,
				trashEnabled: false,
			},
			type: 'Space',
		});

		await apiHelpers.objectEntry.postObjectEntry(
			{
				objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
				title: file1Title,
			},
			applicationName,
			spaceNameWithRecycleBin
		);

		await apiHelpers.objectEntry.postObjectEntry(
			{
				objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
				title: file2Title,
			},
			applicationName,
			spaceNameWithoutRecycleBin
		);

		await assetsPage.gotoAll();

		await assetsPage.selectItems([file1Title, file2Title]);

		await page
			.getByTestId(/visualization-mode/)
			.getByLabel('Actions')
			.click();

		await page.getByRole('menuitem', {name: 'Delete'}).click();

		await expect(
			page.getByText('Some of the selected files')
		).toBeVisible();

		await page.getByRole('button', {name: 'Delete'}).click();

		await waitForAlert(page, 'Info:Delete action started for 2 assets.', {
			type: 'info',
		});

		await expect(
			page.getByRole('cell', {exact: true, name: file1Title})
		).not.toBeVisible();
		await expect(
			page.getByRole('cell', {exact: true, name: file2Title})
		).not.toBeVisible();

		await recycleBinPage.goto();

		await expect(
			page.getByRole('cell', {exact: true, name: file1Title})
		).toBeVisible();
	}
);

test(
	'Can delete multiple contents in a space with recycle bin disabled',
	{tag: '@LPD-62787'},
	async ({apiHelpers, assetsPage, page}) => {
		const applicationName = 'cms/basic-web-contents';
		const spaceName = `Space ${getRandomString()}`;
		const file1Title = `title ${getRandomString()}`;
		const file2Title = `title ${getRandomString()}`;

		await apiHelpers.headlessAssetLibrary.createAssetLibrary({
			name: spaceName,
			settings: {
				logoColor: 'outline-3',
				sharingEnabled: true,
				trashEnabled: false,
			},
			type: 'Space',
		});

		await apiHelpers.objectEntry.postObjectEntry(
			{
				objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
				title: file1Title,
			},
			applicationName,
			spaceName
		);

		await apiHelpers.objectEntry.postObjectEntry(
			{
				objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
				title: file2Title,
			},
			applicationName,
			spaceName
		);

		await assetsPage.gotoAll();

		await assetsPage.selectItems([file1Title, file2Title]);

		await page
			.getByTestId(/visualization-mode/)
			.getByLabel('Actions')
			.click();

		await page.getByRole('menuitem', {name: 'Delete'}).click();

		await expect(
			page.getByText('You are about to permanently')
		).toBeVisible();

		await page.getByRole('button', {name: 'Delete'}).click();

		await waitForAlert(page, 'Info:Delete action started for 2 assets.', {
			type: 'info',
		});

		await expect(
			page.getByRole('cell', {exact: true, name: file1Title})
		).not.toBeVisible();
		await expect(
			page.getByRole('cell', {exact: true, name: file2Title})
		).not.toBeVisible();
	}
);

test(
	'Can delete multiple contents in a space with recycle bin enabled',
	{tag: '@LPD-62787'},
	async ({apiHelpers, assetsPage, page, recycleBinPage}) => {
		const applicationName = 'cms/basic-web-contents';
		const spaceName = `Space ${getRandomString()}`;
		const file1Title = `title ${getRandomString()}`;
		const file2Title = `title ${getRandomString()}`;

		await apiHelpers.headlessAssetLibrary.createAssetLibrary({
			name: spaceName,
			settings: {
				logoColor: 'outline-3',
				sharingEnabled: true,
				trashEnabled: true,
			},
			type: 'Space',
		});

		await apiHelpers.objectEntry.postObjectEntry(
			{
				objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
				title: file1Title,
			},
			applicationName,
			spaceName
		);

		await apiHelpers.objectEntry.postObjectEntry(
			{
				objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
				title: file2Title,
			},
			applicationName,
			spaceName
		);

		await assetsPage.gotoAll();

		await assetsPage.selectItems([file1Title, file2Title]);

		await page
			.getByTestId('visualization-mode-table')
			.getByLabel('Actions')
			.click();

		await page.getByRole('menuitem', {name: 'Delete'}).click();

		await waitForAlert(page, 'Info:Delete action started for 2 assets.', {
			type: 'info',
		});

		await expect(
			page.getByRole('cell', {exact: true, name: file1Title})
		).not.toBeVisible();
		await expect(
			page.getByRole('cell', {exact: true, name: file2Title})
		).not.toBeVisible();

		await recycleBinPage.goto();

		await expect(
			page.getByRole('cell', {exact: true, name: file1Title})
		).toBeVisible();
		await expect(
			page.getByRole('cell', {exact: true, name: file2Title})
		).toBeVisible();
	}
);

test(
	'Bulk Actions Monitor component',
	{tag: ['@LPD-57835', '@LPD-74095', '@LPD-74096']},
	async ({apiHelpers, assetsPage, page}) => {
		const basicWebContent = 'cms/basic-web-contents';
		const bulkActionTasks = 'cms/bulk-action-tasks';
		const spaceName = 'Default';

		const createdFiles = [];
		const filesNames = [
			getRandomString(),
			getRandomString(),
			getRandomString(),
		];
		let tasks;

		for (const fileName of filesNames) {
			const file = await apiHelpers.objectEntry.postObjectEntry(
				{
					objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
					title: fileName,
				},
				basicWebContent,
				spaceName
			);
			createdFiles.push(file);
		}
		try {
			await test.step('Select 1 asset and delete it using the Bulk Action', async () => {
				await assetsPage.gotoAll();

				await assetsPage
					.getItem(filesNames[0])
					.locator('input[title="Select Item"]')
					.check();
				await assetsPage.execBulkItemAction('Delete');

				await waitForAlert(
					page,
					`Info:Delete action started for ${filesNames[0]} asset.`,
					{
						autoClose: true,
						type: 'info',
					}
				);
			});

			await test.step('Check that the processingTask button appear and click on it', async () => {
				await expect(assetsPage.processingTasksButton).toBeVisible();

				await assetsPage.processingTasksButton.click();
			});

			await test.step('After the click, the dropdown component is shown and 1 task with details is visible', async () => {
				await expect(
					assetsPage
						.taskStatusDropdownItemButton('Assets Deletion')
						.nth(0)
				).toBeVisible();

				await expect(assetsPage.taskStatusDropdownList).toContainText(
					'1 Items'
				);
				await expect(assetsPage.taskStatusDropdownList).toContainText(
					'a few seconds ago'
				);
				await expect(assetsPage.taskStatusDropdownList).toContainText(
					'Completed'
				);

				await expect(assetsPage.viewAllTasksLink).toBeVisible();
			});

			await test.step('Go to View All Task redirect to the Task Report page and check that Result column show the correct results', async () => {
				tasks =
					await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
						bulkActionTasks
					);

				await assetsPage.viewAllTasksLink.click();

				await expect(
					page.getByRole('heading', {
						exact: true,
						name: 'Task Report',
					})
				).toBeVisible();

				const row = page.getByRole('row').filter({
					hasText: tasks.items[0].id,
				});

				await expect(row).toBeVisible();
				await expect(
					row.getByRole('cell', {
						name: 'All Successful',
					})
				).toBeVisible();
				await expect(
					row
						.getByRole('cell', {
							name: 'All Successful',
						})
						.locator('.lexicon-icon-check-circle-full')
				).toBeVisible();

				await apiHelpers.objectEntry.patchObjectEntry(
					{
						executionStatus: {
							key: 'failed',
							name: 'Failed',
						},
						numberOfFailedItems: 3,
						numberOfSuccessfulItems: 3,
					},
					bulkActionTasks,
					tasks.items[0].id
				);

				await page.reload();

				await expect(
					page.getByRole('cell', {
						exact: true,
						name: '3 Successful 3 Failed',
					})
				).toBeVisible();
				await expect(
					page
						.getByRole('cell', {
							exact: true,
							name: '3 Successful 3 Failed',
						})
						.locator('.lexicon-icon-check-circle-full')
				).toBeVisible();
				await expect(
					page
						.getByRole('cell', {
							exact: true,
							name: '3 Successful 3 Failed',
						})
						.locator('.lexicon-icon-times-circle-full')
				).toBeVisible();

				await apiHelpers.objectEntry.patchObjectEntry(
					{
						executionStatus: {
							key: 'started',
							name: 'Started',
						},
						numberOfFailedItems: 0,
						numberOfSuccessfulItems: 0,
					},
					bulkActionTasks,
					tasks.items[0].id
				);

				await page.reload();

				await expect(
					page.getByRole('cell', {exact: true, name: 'Processing'})
				).toBeVisible();
				await expect(
					page
						.getByRole('cell', {exact: true, name: 'Processing'})
						.locator('.lexicon-icon-time')
				).toBeVisible();

				await apiHelpers.objectEntry.patchObjectEntry(
					{
						executionStatus: {
							key: 'failed',
							name: 'Failed',
						},
						numberOfFailedItems: 3,
						numberOfSuccessfulItems: 0,
					},
					bulkActionTasks,
					tasks.items[0].id
				);

				await expect(
					page.getByRole('cell', {exact: true, name: 'All Failed'})
				).toBeVisible();
				await expect(
					page
						.getByRole('cell', {exact: true, name: 'All Failed'})
						.locator('.lexicon-icon-times-circle-full')
				).toBeVisible();

				await assetsPage.gotoAll();
			});

			await test.step('Update the task status to Started', async () => {
				await apiHelpers.objectEntry.patchObjectEntry(
					{
						executionStatus: {
							key: 'started',
							name: 'Started',
						},
						numberOfFailedItems: 0,
						numberOfSuccessfulItems: 1,
					},
					bulkActionTasks,
					tasks.items[0].id
				);

				expect.poll(
					async () => {
						await expect(
							assetsPage.taskStatusFormsButton
						).toBeVisible();
					},
					{
						timeout: 5000,
					}
				);

				await assetsPage.taskStatusFormsButton.click();

				await expect(assetsPage.taskStatusDropdownList).toContainText(
					'Processing'
				);
			});

			await test.step('Select 2 assets and delete them using the Bulk Action', async () => {
				await expect(
					assetsPage
						.getItem(filesNames[0])
						.locator('input[title="Select Item"]')
				).not.toBeVisible();

				await assetsPage
					.getItem(filesNames[1])
					.locator('input[title="Select Item"]')
					.check();
				await assetsPage
					.getItem(filesNames[2])
					.locator('input[title="Select Item"]')
					.check();
				await assetsPage.execBulkItemAction('Delete');

				await waitForAlert(
					page,
					'Info:Delete action started for 2 assets.',
					{
						autoClose: true,
						type: 'info',
					}
				);

				await expect(assetsPage.processingTasksButton).toBeVisible();

				await assetsPage.processingTasksButton.click();

				await expect(assetsPage.taskStatusDropdownList).toContainText(
					'2 Items'
				);
				await expect(assetsPage.taskStatusDropdownList).toContainText(
					'a few seconds ago'
				);

				await expect(
					assetsPage
						.taskStatusDropdownItemButton('Assets Deletion')
						.nth(0)
				).toBeVisible();
				await expect(
					assetsPage
						.taskStatusDropdownItemButton('Assets Deletion')
						.nth(1)
				).toBeVisible();
			});

			await test.step('Update the status of the task to Failed', async () => {
				const processingTasks =
					await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
						bulkActionTasks,
						new URLSearchParams({
							filter: `executionStatus eq 'started'`,
						})
					);

				await apiHelpers.objectEntry.patchObjectEntry(
					{
						executionStatus: {
							key: 'failed',
							name: 'Failed',
						},
					},
					bulkActionTasks,
					processingTasks.items[0].id
				);

				expect.poll(
					async () => {
						await expect(
							assetsPage.taskStatusDropdownList
						).toContainText('Failed');
					},
					{
						timeout: 5000,
					}
				);
			});
		}
		finally {
			tasks =
				await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
					bulkActionTasks
				);

			for (let i = 0; i < tasks.totalCount; i++) {
				await apiHelpers.objectEntry.deleteObjectEntry(
					bulkActionTasks,
					tasks.items[i].id
				);
			}

			if (createdFiles) {
				for (const file of createdFiles) {
					await apiHelpers.objectEntry.deleteObjectEntry(
						basicWebContent,
						file.id
					);
				}
			}
		}
	}
);

test(
	'Edit Categories in bulk',
	{tag: '@LPD-57835'},
	async ({apiHelpers, assetsPage, infoPanelPage, page}) => {
		const vocabularyName = getRandomString();

		const siteId = await apiHelpers.headlessAdminUser
			.getSiteByFriendlyUrlPath('cms')
			.then((response) => response.id);

		const vocabularyId = await apiHelpers.headlessAdminTaxonomy
			.postSiteTaxonomyVocabulary({
				assetLibraries: [{id: -1}],
				assetTypes: [
					{
						required: false,
						subtype: 'AllAssetSubtypes',
						type: 'AllAssetTypes',
					},
				],
				name: vocabularyName,
				siteId,
				visibilityType: 'PUBLIC',
			})
			.then((response) => response.id);

		const categoryName = getRandomString();

		await apiHelpers.headlessAdminTaxonomy
			.postTaxonomyVocabularyTaxonomyCategory({
				name: categoryName,
				vocabularyId,
			})
			.then((response) => response.id);

		const basicWebContent = 'cms/basic-web-contents';
		const bulkActionTasks = 'cms/bulk-action-tasks';

		const createdFiles = [];

		const fileNames = [
			getRandomString(),
			getRandomString(),
			getRandomString(),
		];

		let tasks;

		for (const fileName of fileNames) {
			const file = await apiHelpers.objectEntry.postObjectEntry(
				{
					objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
					title: fileName,
				},
				basicWebContent,
				'Default'
			);

			createdFiles.push(file);
		}
		try {
			await test.step('Select 3 assets and bulk edit their categories', async () => {
				await assetsPage.gotoAll();

				await assetsPage
					.getItem(fileNames[0])
					.locator('input[title="Select Item"]')
					.check();
				await assetsPage
					.getItem(fileNames[1])
					.locator('input[title="Select Item"]')
					.check();
				await assetsPage
					.getItem(fileNames[2])
					.locator('input[title="Select Item"]')
					.check();

				await assetsPage.execBulkItemAction('Edit Categories');

				await waitForModal({
					page,
				});
			});

			await test.step('Add a new category to the selected assets', async () => {
				const categoriesAutocomplete =
					page.getByPlaceholder('Add category');

				await categoriesAutocomplete.fill(categoryName);

				const option = page.getByRole('option', {name: categoryName});

				await option.waitFor();
				await option.click();

				const categoryLabel = page.locator('.label-item', {
					hasText: categoryName,
				});

				await expect(categoryLabel).toBeAttached();

				await page.getByRole('button', {name: 'Save'}).click();

				await waitForAlert(
					page,
					'Info:Categories update action started for 3 assets.',
					{
						autoClose: true,
						type: 'info',
					}
				);
			});

			await test.step('Check that the "Processing Task" button appears and click on it', async () => {
				await expect(assetsPage.processingTasksButton).toBeVisible();

				await assetsPage.processingTasksButton.click();

				expect.poll(
					async () => {
						await expect(
							assetsPage.processingTasksButton
						).toBeHidden();
					},
					{
						timeout: 5000,
					}
				);

				await expect(assetsPage.taskStatusDropdownList).toContainText(
					'Completed'
				);
			});

			await test.step('After the click, the dropdown component is shown and 1 task with details is visible', async () => {
				await expect(
					assetsPage
						.taskStatusDropdownItemButton('Assets Categorization')
						.nth(0)
				).toBeVisible();

				await expect(assetsPage.taskStatusDropdownList).toContainText(
					'3 Items'
				);
			});

			await test.step('Verify the category has been correctly applied.', async () => {
				await page.reload();

				await assetsPage.execItemAction({
					action: 'Show Details',
					filter: fileNames[0],
				});

				await expect(
					page.getByRole('heading', {name: fileNames[0]})
				).toBeVisible();

				await infoPanelPage.selectTab('Categorization').click();

				await expect(
					page.getByText(categoryName, {exact: true})
				).toBeVisible();
			});
		}
		finally {
			tasks =
				await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
					bulkActionTasks
				);

			for (let i = 0; i < tasks.totalCount; i++) {
				await apiHelpers.objectEntry.deleteObjectEntry(
					bulkActionTasks,
					tasks.items[i].id
				);
			}

			if (createdFiles.length) {
				for (const file of createdFiles) {
					await apiHelpers.objectEntry.deleteObjectEntry(
						basicWebContent,
						file.id
					);
				}
			}

			await apiHelpers.headlessAdminTaxonomy.deleteTaxonomyVocabulary(
				vocabularyId
			);
		}
	}
);

test(
	'Edit Categories in bulk for different assets',
	{tag: '@LPD-76507'},
	async ({apiHelpers, assetsPage, infoPanelPage, page}) => {
		const vocabularyName = getRandomString();
		const fileNameImg = `file_${getRandomString()}.png`;
		const titleImg = `title ${getRandomString()}`;

		const siteId = await apiHelpers.headlessAdminUser
			.getSiteByFriendlyUrlPath('cms')
			.then((response) => response.id);

		const vocabularyId = await apiHelpers.headlessAdminTaxonomy
			.postSiteTaxonomyVocabulary({
				assetLibraries: [{id: -1}],
				assetTypes: [
					{
						required: false,
						subtype: 'AllAssetSubtypes',
						type: 'AllAssetTypes',
					},
				],
				name: vocabularyName,
				siteId,
				visibilityType: 'PUBLIC',
			})
			.then((response) => response.id);

		const categories = [
			getRandomString(),
			getRandomString(),
			getRandomString(),
		];

		for (const category of categories) {
			await apiHelpers.headlessAdminTaxonomy
				.postTaxonomyVocabularyTaxonomyCategory({
					name: category,
					vocabularyId,
				})
				.then((response) => response.id);
		}

		const basicWebContent = 'cms/basic-web-contents';
		const bulkActionTasks = 'cms/bulk-action-tasks';

		const createdFiles = [];

		const webContentNames = [getRandomString(), getRandomString()];

		let tasks;

		for (const fileName of webContentNames) {
			const file = await apiHelpers.objectEntry.postObjectEntry(
				{
					objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
					title: fileName,
				},
				basicWebContent,
				'Default'
			);

			createdFiles.push(file);
		}

		const folderName = `Folder ${getRandomInt()}`;

		const folder = await apiHelpers.objectFolder.createObjectEntryFolder({
			parentObjectEntryFolderExternalReferenceCode: 'L_CONTENTS',
			scopeKey: 'Default',
			title: folderName,
		});

		await apiHelpers.objectEntry.postObjectEntry(
			{
				file: {
					fileBase64: 'R0lGODlhAQABAAAAACw=',
					name: fileNameImg,
				},
				objectEntryFolderExternalReferenceCode: 'L_FILES',
				title: titleImg,
			},
			'cms/basic-documents',
			'Default'
		);

		await apiHelpers.objectEntry.postObjectEntry(
			{
				file: {
					objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
					title: getRandomString(),
				},
				objectEntryFolderExternalReferenceCode:
					folder.externalReferenceCode,
				title: `Content ${getRandomInt()}`,
			},
			basicWebContent,
			'Default'
		);

		try {
			await test.step('Select 3 assets including a folder and bulk edit their categories', async () => {
				await assetsPage.gotoContents();

				await assetsPage
					.getItem(webContentNames[0])
					.locator('input[title="Select Item"]')
					.check();
				await assetsPage
					.getItem(webContentNames[1])
					.locator('input[title="Select Item"]')
					.check();
				await assetsPage
					.getItem(folderName)
					.locator('input[title="Select Item"]')
					.check();

				await assetsPage.execBulkItemAction('Edit Categories');

				await waitForModal({
					page,
				});
			});

			await test.step('Add categories to the selected assets', async () => {
				const categoriesAutocomplete =
					page.getByPlaceholder('Add category');

				for (const category of categories) {
					await categoriesAutocomplete.fill(category);

					const option = page.getByRole('option', {name: category});

					await option.waitFor();
					await option.click();

					const categoryLabel = page.locator('.label-item', {
						hasText: category,
					});

					await expect(categoryLabel).toBeAttached();
				}

				await page.getByRole('button', {name: 'Save'}).click();

				await waitForAlert(
					page,
					'Info:Categories update action started for 2 assets.',
					{
						autoClose: true,
						type: 'info',
					}
				);
			});

			await test.step('Check that the "Processing Task" button appears and click on it', async () => {
				await expect(assetsPage.processingTasksButton).toBeVisible();

				await assetsPage.processingTasksButton.click();

				expect.poll(
					async () => {
						await expect(
							assetsPage.processingTasksButton
						).toBeHidden();
					},
					{
						timeout: 5000,
					}
				);

				await expect(assetsPage.taskStatusDropdownList).toContainText(
					'Completed'
				);
			});

			await test.step('After the click, the dropdown component is shown and 1 task with details is visible', async () => {
				await expect(
					assetsPage
						.taskStatusDropdownItemButton('Assets Categorization')
						.nth(0)
				).toBeVisible();

				await expect(assetsPage.taskStatusDropdownList).toContainText(
					'2 Items'
				);
			});

			await test.step('Verify the categories has been correctly applied.', async () => {
				for (const webContentName of webContentNames) {
					await page.reload();

					await assetsPage.execItemAction({
						action: 'Show Details',
						filter: webContentName,
					});

					await expect(
						page.getByRole('heading', {name: webContentName})
					).toBeVisible();

					await infoPanelPage.selectTab('Categorization').click();

					for (const category of categories) {
						await expect(
							page.getByText(category, {exact: true})
						).toBeVisible();
					}
				}
			});

			await test.step('Delete more than one category', async () => {
				await page.reload();

				await assetsPage
					.getItem(webContentNames[1])
					.locator('input[title="Select Item"]')
					.check();

				await assetsPage.execBulkItemAction('Edit Categories');

				await waitForModal({
					page,
				});

				await page
					.locator('span', {hasText: categories[0]})
					.getByRole('button', {name: 'Close'})
					.click();
				await page
					.locator('span', {hasText: categories[1]})
					.getByRole('button', {name: 'Close'})
					.click();

				await page.getByRole('button', {name: 'Save'}).click();

				await waitForAlert(
					page,
					'Info:Categories update action started for one asset.',
					{
						autoClose: true,
						type: 'info',
					}
				);
			});

			await test.step('Verify the categories has been correctly removed.', async () => {
				await page.reload();

				await assetsPage.execItemAction({
					action: 'Show Details',
					filter: webContentNames[1],
				});

				await expect(
					page.getByRole('heading', {name: webContentNames[1]})
				).toBeVisible();

				await infoPanelPage.selectTab('Categorization').click();

				await expect(
					page.getByText(categories[2], {exact: true})
				).toBeVisible();
			});

			await test.step('Select 3 mixed assets and bulk edit their categories', async () => {
				await assetsPage.gotoAll();

				await assetsPage
					.getItem(webContentNames[0])
					.locator('input[title="Select Item"]')
					.check();
				await assetsPage
					.getItem(webContentNames[1])
					.locator('input[title="Select Item"]')
					.check();
				await assetsPage
					.getItem(titleImg)
					.locator('input[title="Select Item"]')
					.check();

				await assetsPage.execBulkItemAction('Edit Categories');

				await waitForModal({
					page,
				});
			});

			await test.step('Add and remove categories to the selected assets', async () => {
				const categoriesAutocomplete =
					page.getByPlaceholder('Add category');

				for (const category of categories) {
					await categoriesAutocomplete.fill(category);

					const option = page.getByRole('option', {name: category});

					await option.waitFor();
					await option.click();

					const categoryLabel = page.locator('.label-item', {
						hasText: category,
					});

					await expect(categoryLabel).toBeAttached();
				}

				await page
					.locator('span', {hasText: categories[1]})
					.getByRole('button', {name: 'Close'})
					.click();
				await page
					.locator('span', {hasText: categories[2]})
					.getByRole('button', {name: 'Close'})
					.click();

				await page.getByRole('button', {name: 'Save'}).click();

				await waitForAlert(
					page,
					'Info:Categories update action started for 3 assets.',
					{
						autoClose: true,
						type: 'info',
					}
				);
			});

			await test.step('Verify the categories has been correctly edited.', async () => {
				await page.reload();

				for (const webContentName of webContentNames) {
					await assetsPage.execItemAction({
						action: 'Show Details',
						filter: webContentName,
					});

					await expect(
						page.getByRole('heading', {name: webContentName})
					).toBeVisible();

					await infoPanelPage.selectTab('Categorization').click();

					await expect(
						page.getByText(categories[0], {exact: true})
					).toBeVisible();

					await assetsPage
						.getItem(webContentName)
						.locator('input[title="Select Item"]')
						.uncheck();
				}

				await assetsPage.execItemAction({
					action: 'Show Details',
					filter: titleImg,
				});

				await expect(
					page.getByRole('heading', {name: titleImg})
				).toBeVisible();

				await infoPanelPage.selectTab('Categorization').click();

				await expect(
					page.getByText(categories[0], {exact: true})
				).toBeVisible();
			});
		}
		finally {
			tasks =
				await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
					bulkActionTasks
				);

			await apiHelpers.objectFolder.deleteObjectEntryFolder(folder.id);

			for (let i = 0; i < tasks.totalCount; i++) {
				await apiHelpers.objectEntry.deleteObjectEntry(
					bulkActionTasks,
					tasks.items[i].id
				);
			}

			if (createdFiles.length) {
				for (const file of createdFiles) {
					await apiHelpers.objectEntry.deleteObjectEntry(
						basicWebContent,
						file.id
					);
				}
			}

			await apiHelpers.headlessAdminTaxonomy.deleteTaxonomyVocabulary(
				vocabularyId
			);
		}
	}
);

test(
	'Edit Tags in bulk for different assets',
	{tag: '@LPD-76507'},
	async ({apiHelpers, assetsPage, infoPanelPage, page}) => {
		const fileNameImg = `file_${getRandomString()}.png`;
		const titleImg = `title ${getRandomString()}`;

		const siteId = await apiHelpers.headlessAdminUser
			.getSiteByFriendlyUrlPath('cms')
			.then((response) => response.id);

		const basicWebContent = 'cms/basic-web-contents';
		const bulkActionTasks = 'cms/bulk-action-tasks';

		const createdFiles = [];

		const webContentNames = [getRandomString(), getRandomString()];

		let tasks;

		for (const fileName of webContentNames) {
			const file = await apiHelpers.objectEntry.postObjectEntry(
				{
					objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
					title: fileName,
				},
				basicWebContent,
				'Default'
			);

			createdFiles.push(file);
		}

		const folderName = `Folder ${getRandomInt()}`;

		const folder = await apiHelpers.objectFolder.createObjectEntryFolder({
			parentObjectEntryFolderExternalReferenceCode: 'L_CONTENTS',
			scopeKey: 'Default',
			title: folderName,
		});

		await apiHelpers.objectEntry.postObjectEntry(
			{
				file: {
					fileBase64: 'R0lGODlhAQABAAAAACw=',
					name: fileNameImg,
				},
				objectEntryFolderExternalReferenceCode: 'L_FILES',
				title: titleImg,
			},
			'cms/basic-documents',
			'Default'
		);

		await apiHelpers.objectEntry.postObjectEntry(
			{
				file: {
					objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
					title: getRandomString(),
				},
				objectEntryFolderExternalReferenceCode:
					folder.externalReferenceCode,
				title: `Content ${getRandomInt()}`,
			},
			basicWebContent,
			'Default'
		);

		const createdTags = [];

		const tagNames = [
			'Tag' + getRandomInt(),
			'Tag' + getRandomInt(),
			'Tag' + getRandomInt(),
		];

		for (const tagName of tagNames) {
			const tag = await apiHelpers.headlessAdminTaxonomy.postSiteKeyword({
				name: tagName,
				siteId,
			});

			createdTags.push(tag);
		}

		try {
			await test.step('Select 3 assets including a folder and bulk edit their tags', async () => {
				await assetsPage.gotoContents();

				await assetsPage
					.getItem(webContentNames[0])
					.locator('input[title="Select Item"]')
					.check();
				await assetsPage
					.getItem(webContentNames[1])
					.locator('input[title="Select Item"]')
					.check();
				await assetsPage
					.getItem(folderName)
					.locator('input[title="Select Item"]')
					.check();

				await assetsPage.execBulkItemAction('Edit Tags');

				await waitForModal({
					page,
				});
			});

			await test.step('Add tags to the selected assets', async () => {
				const categoriesAutocomplete = page.getByPlaceholder('Add tag');

				for (const tagName of tagNames) {
					await categoriesAutocomplete.fill(tagName);

					const option = page.getByRole('option', {
						exact: true,
						name: tagName,
					});

					await option.waitFor();
					await option.click();

					const categoryLabel = page.locator('.label-item', {
						hasText: tagName,
					});

					await expect(categoryLabel).toBeAttached();
				}

				await page.getByRole('button', {name: 'Save'}).click();

				await waitForAlert(
					page,
					'Info:Tags update action started for 2 assets.',
					{
						autoClose: true,
						type: 'info',
					}
				);
			});

			await test.step('Check that the "Processing Task" button appears and click on it', async () => {
				await expect(assetsPage.processingTasksButton).toBeVisible();

				await assetsPage.processingTasksButton.click();

				expect.poll(
					async () => {
						await expect(
							assetsPage.processingTasksButton
						).toBeHidden();
					},
					{
						timeout: 5000,
					}
				);

				await expect(assetsPage.taskStatusDropdownList).toContainText(
					'Completed'
				);
			});

			await test.step('After the click, the dropdown component is shown and 1 task with details is visible', async () => {
				await expect(
					assetsPage
						.taskStatusDropdownItemButton('Assets Tagging')
						.nth(0)
				).toBeVisible();

				await expect(assetsPage.taskStatusDropdownList).toContainText(
					'2 Items'
				);
			});

			await test.step('Verify the tags has been correctly applied.', async () => {
				for (const webContentName of webContentNames) {
					await page.reload();

					await assetsPage.execItemAction({
						action: 'Show Details',
						filter: webContentName,
					});

					await expect(
						page.getByRole('heading', {name: webContentName})
					).toBeVisible();

					await infoPanelPage.selectTab('Categorization').click();

					for (const tagName of tagNames) {
						await expect(
							page.getByText(tagName, {exact: true})
						).toBeVisible();
					}
				}
			});

			await test.step('Delete more than one tag', async () => {
				await page.reload();

				await assetsPage
					.getItem(webContentNames[1])
					.locator('input[title="Select Item"]')
					.check();

				await assetsPage.execBulkItemAction('Edit Tags');

				await waitForModal({
					page,
				});

				await page
					.locator('span', {hasText: tagNames[0]})
					.getByRole('button', {name: 'Close'})
					.click();
				await page
					.locator('span', {hasText: tagNames[1]})
					.getByRole('button', {name: 'Close'})
					.click();

				await page.getByRole('button', {name: 'Save'}).click();

				await waitForAlert(
					page,
					'Info:Tags update action started for one asset.',
					{
						autoClose: true,
						type: 'info',
					}
				);
			});

			await test.step('Verify the tags has been correctly removed.', async () => {
				await page.reload();

				await assetsPage.execItemAction({
					action: 'Show Details',
					filter: webContentNames[1],
				});

				await expect(
					page.getByRole('heading', {name: webContentNames[1]})
				).toBeVisible();

				await infoPanelPage.selectTab('Categorization').click();

				await expect(
					page.getByText(tagNames[2], {exact: true})
				).toBeVisible();
			});

			await test.step('Select 3 mixed assets and bulk edit their tags', async () => {
				await assetsPage.gotoAll();

				await assetsPage
					.getItem(webContentNames[0])
					.locator('input[title="Select Item"]')
					.check();
				await assetsPage
					.getItem(webContentNames[1])
					.locator('input[title="Select Item"]')
					.check();
				await assetsPage
					.getItem(titleImg)
					.locator('input[title="Select Item"]')
					.check();

				await assetsPage.execBulkItemAction('Edit Tags');

				await waitForModal({
					page,
				});
			});

			await test.step('Add and remove tags to the selected assets', async () => {
				const categoriesAutocomplete = page.getByPlaceholder('Add tag');

				for (const tagName of tagNames) {
					await categoriesAutocomplete.fill(tagName);

					const option = page.getByRole('option', {
						exact: true,
						name: tagName,
					});

					await option.waitFor();
					await option.click();

					const categoryLabel = page.locator('.label-item', {
						hasText: tagName,
					});

					await expect(categoryLabel).toBeAttached();
				}

				await page
					.locator('span', {hasText: tagNames[1]})
					.getByRole('button', {name: 'Close'})
					.click();
				await page
					.locator('span', {hasText: tagNames[2]})
					.getByRole('button', {name: 'Close'})
					.click();

				await page.getByRole('button', {name: 'Save'}).click();

				await waitForAlert(
					page,
					'Info:Tags update action started for 3 assets.',
					{
						autoClose: true,
						type: 'info',
					}
				);
			});

			await test.step('Verify the tags has been correctly edited.', async () => {
				await page.reload();

				for (const webContentName of webContentNames) {
					await assetsPage.execItemAction({
						action: 'Show Details',
						filter: webContentName,
					});

					await expect(
						page.getByRole('heading', {name: webContentName})
					).toBeVisible();

					await infoPanelPage.selectTab('Categorization').click();

					await expect(
						page.getByText(tagNames[0], {exact: true})
					).toBeVisible();

					await assetsPage
						.getItem(webContentName)
						.locator('input[title="Select Item"]')
						.uncheck();
				}

				await assetsPage.execItemAction({
					action: 'Show Details',
					filter: titleImg,
				});

				await expect(
					page.getByRole('heading', {name: titleImg})
				).toBeVisible();

				await infoPanelPage.selectTab('Categorization').click();

				await expect(
					page.getByText(tagNames[0], {exact: true})
				).toBeVisible();
			});
		}
		finally {
			tasks =
				await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
					bulkActionTasks
				);

			await apiHelpers.objectFolder.deleteObjectEntryFolder(folder.id);

			for (let i = 0; i < tasks.totalCount; i++) {
				await apiHelpers.objectEntry.deleteObjectEntry(
					bulkActionTasks,
					tasks.items[i].id
				);
			}

			if (createdFiles.length) {
				for (const file of createdFiles) {
					await apiHelpers.objectEntry.deleteObjectEntry(
						basicWebContent,
						file.id
					);
				}
			}

			if (createdTags.length) {
				for (const tag of createdTags) {
					await apiHelpers.headlessAdminTaxonomy.deleteKeyword({
						id: tag.id,
					});
				}
			}
		}
	}
);

test(
	'Bulk action download assets',
	{tag: '@LPD-62554'},
	async ({apiHelpers, assetsPage, page}) => {
		const contentApplicationName = 'cms/basic-web-contents';
		const fileApplicationName = 'cms/basic-documents';
		const spaceName = 'Default';

		const content1 = `title ${getRandomString()}`;
		const fileAssetTitle1 = `title ${getRandomString()}`;
		const fileAssetTitle2 = `title ${getRandomString()}`;
		const fileNameImg = `file_${getRandomString()}.png`;

		const contentObjectEntry1 =
			await apiHelpers.objectEntry.postObjectEntry(
				{
					objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
					title: content1,
				},
				contentApplicationName,
				spaceName
			);

		const fileObjectEntry1 = await apiHelpers.objectEntry.postObjectEntry(
			{
				file: {
					fileBase64: 'R0lGODlhAQABAAAAACw=',
					name: fileNameImg,
				},
				objectEntryFolderExternalReferenceCode: 'L_FILES',
				title: fileAssetTitle1,
			},
			fileApplicationName,
			'Default'
		);

		const fileObjectEntry2 = await apiHelpers.objectEntry.postObjectEntry(
			{
				file: {
					fileBase64: 'R0lGODlhAQABAAAAACw=',
					name: fileNameImg,
				},
				objectEntryFolderExternalReferenceCode: 'L_FILES',
				title: fileAssetTitle2,
			},
			fileApplicationName,
			'Default'
		);

		await test.step('Go to All section and try to download a content asset from the bulk action, un unexpected error occurred', async () => {
			await assetsPage.gotoAll();
			await assetsPage.selectItems([content1]);
			await assetsPage.execBulkItemAction('Download');

			await waitForAlert(
				page,
				'Error:Unable to process the bulk download. Please check your selection and try again.',
				{
					type: 'danger',
				}
			);
		});

		await test.step('Download a file asset from the bulk action', async () => {
			await assetsPage
				.getItem(content1)
				.locator('input[title="Select Item"]')
				.uncheck();
			await assetsPage.selectItems([fileAssetTitle1]);

			const downloadPromise = page.waitForEvent('download');

			await assetsPage.execBulkItemAction('Download');

			await waitForAlert(
				page,
				'Warning:The download of 1 file is being prepared. Please do not close this window or navigate to another section.',
				{
					type: 'warning',
				}
			);

			await waitForAlert(page, 'Success:The download will begin shortly');

			const download = await downloadPromise;

			expect(download.suggestedFilename()).toBeDefined();
		});

		await test.step('Download both content and files assets from the bulk action, a message will inform the user that content assets will be skipped from the download', async () => {
			await assetsPage.selectItems([
				content1,
				fileAssetTitle1,
				fileAssetTitle2,
			]);

			const downloadPromise = page.waitForEvent('download');

			await assetsPage.execBulkItemAction('Download');

			await waitForAlert(
				page,
				'Warning:You have selected both content and file assets. Only file assets can be downloaded. Content assets will be skipped.',
				{
					type: 'warning',
				}
			);
			await waitForAlert(
				page,
				'Warning:The download of 2 files is being prepared. Please do not close this window or navigate to another section.',
				{
					type: 'warning',
				}
			);
			await waitForAlert(page, 'Success:The download will begin shortly');

			const download = await downloadPromise;

			expect(download.suggestedFilename()).toBeDefined();
		});

		await test.step('Remove generated files', async () => {
			await apiHelpers.objectEntry.deleteObjectEntry(
				contentApplicationName,
				String(contentObjectEntry1.id)
			);
			await apiHelpers.objectEntry.deleteObjectEntry(
				fileApplicationName,
				String(fileObjectEntry1.id)
			);
			await apiHelpers.objectEntry.deleteObjectEntry(
				fileApplicationName,
				String(fileObjectEntry2.id)
			);
		});
	}
);

test(
	'Delete Asset Versions in bulk',
	{tag: '@LPD-67234'},
	async ({apiHelpers, assetsPage, page}) => {
		const bulkActionTasksApplicationName = 'cms/bulk-action-tasks';
		const contentApplicationName = 'cms/basic-web-contents';
		const spaceName = 'Default';

		const webContentNames = [
			getRandomString(),
			getRandomString(),
			getRandomString(),
		];

		let contentObjectEntry = await apiHelpers.objectEntry.postObjectEntry(
			{
				objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
				title: webContentNames[0],
			},
			contentApplicationName,
			spaceName
		);

		await test.step('Edit object entry to generate more versions', async () => {
			await apiHelpers.objectEntry.patchObjectEntry(
				{
					title_i18n: {
						en_US: webContentNames[1],
					},
				},
				contentApplicationName,
				contentObjectEntry.id
			);

			contentObjectEntry = await apiHelpers.objectEntry.patchObjectEntry(
				{
					title_i18n: {
						en_US: webContentNames[2],
					},
				},
				contentApplicationName,
				contentObjectEntry.id
			);
		});

		await test.step('Log in as an CMS Administrator', async () => {
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

			await performUserSwitch(page, user.alternateName);
		});

		try {
			await test.step('Navigate to history page and bulk delete all versions', async () => {
				await assetsPage.gotoAll();

				await assetsPage.execItemAction({
					action: 'View History',
					filter: webContentNames[2],
				});

				for (const webContentName of webContentNames) {
					await assetsPage
						.getItem(webContentName)
						.locator('input[title="Select Item"]')
						.check();
				}

				await assetsPage.execBulkItemAction('Delete');

				await waitForModal({
					page,
				});

				await page
					.locator('.modal')
					.getByRole('button', {name: 'Delete'})
					.click();

				await waitForAlert(
					page,
					'Info:Delete asset versions action started for 3 versions.',
					{
						autoClose: true,
						type: 'info',
					}
				);
			});
		}
		finally {
			const tasks =
				await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
					bulkActionTasksApplicationName
				);

			for (let i = 0; i < tasks.totalCount; i++) {
				await apiHelpers.objectEntry.deleteObjectEntry(
					bulkActionTasksApplicationName,
					tasks.items[i].id
				);
			}
		}

		await test.step('All versions are removed excluding the current one', async () => {
			await page.reload();

			await expect(
				assetsPage.getItem(webContentNames[0])
			).not.toBeVisible();

			await expect(
				assetsPage.getItem(webContentNames[1])
			).not.toBeVisible();

			await expect(assetsPage.getItem(webContentNames[2])).toBeVisible();
		});

		await test.step('Assert that current version cannot be deleted', async () => {
			await assetsPage
				.getItem(webContentNames[2])
				.locator('input[title="Select Item"]')
				.check();

			await assetsPage.execBulkItemAction('Delete');

			await waitForModal({
				page,
			});

			await page
				.locator('.modal')
				.getByRole('button', {name: 'Ok'})
				.click();

			await expect(assetsPage.getItem(webContentNames[2])).toBeVisible();
		});
	}
);

test(
	'Expire CMS assets in bulk',
	{tag: '@LPD-85361'},
	async ({apiHelpers, assetsPage, page}) => {
		const basicDocumentTitle = `Basic Document ${getRandomString()}`;
		const basicWebContentTitle = `Basic Web Content ${getRandomString()}`;
		const spaceName = 'Default';

		await test.step('Create CMS assets', async () => {
			await apiHelpers.objectEntry.postObjectEntry(
				{
					file: {
						fileBase64: 'R0lGODlhAQABAAAAACw=',
						name: `file_${getRandomString()}.png`,
					},
					objectEntryFolderExternalReferenceCode: 'L_FILES',
					title: basicDocumentTitle,
				},
				'cms/basic-documents',
				spaceName
			);

			await apiHelpers.objectEntry.postObjectEntry(
				{
					objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
					title: basicWebContentTitle,
				},
				'cms/basic-web-contents',
				spaceName
			);
		});

		await test.step('Log in as an CMS Administrator', async () => {
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

			await performUserSwitch(page, user.alternateName);
		});

		try {
			await test.step('Expire one asset in bulk', async () => {
				await assetsPage.gotoAll();

				const row = page.getByRole('row', {name: basicDocumentTitle});

				await expect(
					row.getByRole('cell', {name: 'Approved'})
				).toBeVisible();

				await assetsPage.selectItems([basicDocumentTitle]);

				await assetsPage.execBulkItemAction('Expire');

				await waitForAlert(
					page,
					`Info:Expire action started for ${basicDocumentTitle} asset.`,
					{
						type: 'info',
					}
				);

				await waitForAlert(
					page,
					`Success:${basicDocumentTitle} was successfully expired.`
				);

				await expect(
					row.getByRole('cell', {name: 'Expired'})
				).toBeVisible();
			});

			await test.step('Expire two assets in bulk', async () => {
				const row = page.getByRole('row', {name: basicWebContentTitle});

				await expect(
					row.getByRole('cell', {name: 'Approved'})
				).toBeVisible();

				await assetsPage.selectItems([basicWebContentTitle]);

				await assetsPage.execBulkItemAction('Expire');

				await waitForAlert(
					page,
					'Info:Expire action started for 2 assets.',
					{
						type: 'info',
					}
				);

				await waitForAlert(
					page,
					'Success:2 assets were successfully expired.'
				);

				await expect(
					row.getByRole('cell', {name: 'Expired'})
				).toBeVisible();
			});
		}
		finally {
			const tasks =
				await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
					'cms/bulk-action-tasks'
				);

			for (let i = 0; i < tasks.totalCount; i++) {
				await apiHelpers.objectEntry.deleteObjectEntry(
					'cms/bulk-action-tasks',
					tasks.items[i].id
				);
			}
		}
	}
);

test(
	'Export for Translation CMS assets in bulk',
	{tag: '@LPD-85361'},
	async ({apiHelpers, assetsPage, page}) => {
		const basicDocumentTitle = `Basic Document ${getRandomString()}`;
		const basicWebContentTitle1 = `Basic Web Content ${getRandomString()}`;
		const basicWebContentTitle2 = `Basic Web Content ${getRandomString()}`;
		const blogTitle = `Blog ${getRandomString()}`;
		const spaceName = 'Default';

		await test.step('Create CMS assets', async () => {
			await apiHelpers.objectEntry.postObjectEntry(
				{
					file: {
						fileBase64: 'R0lGODlhAQABAAAAACw=',
						name: `file_${getRandomString()}.png`,
					},
					objectEntryFolderExternalReferenceCode: 'L_FILES',
					title: basicDocumentTitle,
				},
				'cms/basic-documents',
				spaceName
			);

			await apiHelpers.objectEntry.postObjectEntry(
				{
					objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
					title: basicWebContentTitle1,
				},
				'cms/basic-web-contents',
				spaceName
			);

			await apiHelpers.objectEntry.postObjectEntry(
				{
					objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
					title: basicWebContentTitle2,
				},
				'cms/basic-web-contents',
				spaceName
			);

			await apiHelpers.objectEntry.postObjectEntry(
				{
					objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
					title: blogTitle,
				},
				'cms/blogs',
				spaceName
			);
		});

		await test.step('Log in as an CMS Administrator', async () => {
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

			await performUserSwitch(page, user.alternateName);
		});

		await test.step('Exporting for Translation a single file type object entry is not allowed', async () => {
			await assetsPage.gotoAll();

			await assetsPage.selectItems([basicDocumentTitle]);

			await assetsPage.execBulkItemAction('Export for Translation');

			await waitForModal({
				page,
			});

			await expect(
				page.getByText(
					'This action is not available for the item you have selected.'
				)
			).toBeVisible();

			await page
				.locator('.modal')
				.getByRole('button', {name: 'OK'})
				.click();
		});

		await test.step('Exporting for Translation file and content type object entries together is not allowed', async () => {
			await assetsPage.gotoAll();

			await assetsPage.selectItems([
				basicDocumentTitle,
				basicWebContentTitle1,
			]);

			await assetsPage.execBulkItemAction('Export for Translation');

			await waitForModal({
				page,
			});

			await expect(
				page.getByText(
					'This action is not available for the item you have selected.'
				)
			).toBeVisible();

			await page
				.locator('.modal')
				.getByRole('button', {name: 'OK'})
				.click();
		});

		try {
			await test.step('Exporting for Translation a single content type object entry', async () => {
				await assetsPage.gotoAll();

				await assetsPage.selectItems([basicWebContentTitle1]);

				await assetsPage.execBulkItemAction('Export for Translation');

				await waitForModal({
					page,
				});

				await expect(
					page
						.locator('.modal-header')
						.getByText('Export for Translation')
				).toBeVisible();

				const filePath = await assetsPage.exportForTranslation(
					'Spanish (Spain)',
					true
				);

				await expect(
					checkInZip(
						filePath,
						`${basicWebContentTitle1}-en_US-es_ES.xlf`
					)
				).resolves.toBe(true);
			});

			await test.step('Exporting for Translation two equal content type object entries', async () => {
				await assetsPage.gotoAll();

				await assetsPage.selectItems([
					basicWebContentTitle1,
					basicWebContentTitle2,
				]);

				await assetsPage.execBulkItemAction('Export for Translation');

				await waitForModal({
					page,
				});

				await expect(
					page
						.locator('.modal-header')
						.getByText('Export for Translation')
				).toBeVisible();

				const filePath = await assetsPage.exportForTranslation(
					'Spanish (Spain)',
					true
				);

				await expect(
					checkInZip(
						filePath,
						`${basicWebContentTitle1}-en_US-es_ES.xlf`
					)
				).resolves.toBe(true);

				await expect(
					checkInZip(
						filePath,
						`${basicWebContentTitle2}-en_US-es_ES.xlf`
					)
				).resolves.toBe(true);
			});

			await test.step('Exporting for Translation two different content type object entries', async () => {
				await assetsPage.gotoAll();

				await assetsPage.selectItems([
					basicWebContentTitle1,
					blogTitle,
				]);

				await assetsPage.execBulkItemAction('Export for Translation');

				await waitForModal({
					page,
				});

				await expect(
					page
						.locator('.modal-header')
						.getByText('Export for Translation')
				).toBeVisible();

				const filePath = await assetsPage.exportForTranslation(
					'Spanish (Spain)',
					true
				);

				await expect(
					checkInZip(filePath, `${basicWebContentTitle1}-en_US.zip`)
				).resolves.toBe(true);

				await expect(
					checkInZip(filePath, `${blogTitle}-en_US.zip`)
				).resolves.toBe(true);
			});

			await test.step('Exporting for Translation multiple object entries of two different content types', async () => {
				await assetsPage.gotoAll();

				await assetsPage.selectItems([
					basicWebContentTitle1,
					basicWebContentTitle2,
					blogTitle,
				]);

				await assetsPage.execBulkItemAction('Export for Translation');

				await waitForModal({
					page,
				});

				await expect(
					page
						.locator('.modal-header')
						.getByText('Export for Translation')
				).toBeVisible();

				const filePath = await assetsPage.exportForTranslation(
					'Spanish (Spain)',
					true
				);

				await expect(
					checkInZip(
						filePath,
						`Basic Web Content Translations-en_US.zip`
					)
				).resolves.toBe(true);

				await expect(
					checkInZip(filePath, `${blogTitle}-en_US.zip`)
				).resolves.toBe(true);
			});
		}
		finally {
			const tasks =
				await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
					'cms/bulk-action-tasks'
				);

			for (let i = 0; i < tasks.totalCount; i++) {
				await apiHelpers.objectEntry.deleteObjectEntry(
					'cms/bulk-action-tasks',
					tasks.items[i].id
				);
			}
		}
	}
);
