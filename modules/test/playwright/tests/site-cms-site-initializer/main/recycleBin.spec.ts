/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {checkAccessibility} from '../../../utils/checkAccessibility';
import getRandomString from '../../../utils/getRandomString';
import performLoginViaApi from '../../../utils/performLogin';
import {waitForAlert} from '../../../utils/waitForAlert';
import {cmsPagesTest} from './fixtures/cmsPagesTest';
import {RecycleBinPage} from './pages/RecycleBinPage';

const test = mergeTests(
	cmsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-17564': {enabled: true},
		'LPD-34594': {enabled: true},
	}),
	loginTest()
);

test.beforeAll(async ({browser}) => {
	const newPage = await browser.newPage();

	const recycleBinPage = new RecycleBinPage(newPage);

	await performLoginViaApi(newPage, 'test');

	await recycleBinPage.goto();

	const emptyRecycleBinMessage = newPage.getByText(
		'The Recycle Bin is empty.'
	);

	await Promise.race([
		emptyRecycleBinMessage.waitFor({state: 'visible'}),
		recycleBinPage.selectAllItemsCheckbox.waitFor({state: 'visible'}),
	]);

	if (await emptyRecycleBinMessage.isVisible()) {
		await newPage.close();

		return;
	}

	await recycleBinPage.selectAllItemsCheckbox.check();

	await recycleBinPage.tableActions.click();

	await newPage.getByRole('menuitem', {name: 'Delete'}).click();

	await recycleBinPage.modalDeleteEntriesButton.click();

	expect.poll(
		async () => {
			await newPage.reload();

			await expect(emptyRecycleBinMessage).toBeVisible();
		},
		{
			intervals: [1000],
			timeout: 10_000,
		}
	);

	await newPage.close();
});

test(
	'Can delete a single content from Recycle Bin',
	{tag: '@LPD-55831'},
	async ({apiHelpers, contentsPage, page, recycleBinPage}) => {
		const applicationName = 'cms/basic-web-contents';
		const contentName = `Content ${getRandomString()}`;
		const spaceName = `Space ${getRandomString()}`;

		await test.step('Create a new Space', async () => {
			await apiHelpers.headlessAssetLibrary.createAssetLibrary({
				name: spaceName,
				settings: {},
				type: 'Space',
			});
		});

		await apiHelpers.objectEntry.postObjectEntry(
			{
				objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
				title: contentName,
			},
			applicationName,
			spaceName
		);

		await test.step('Delete the created content so it goes into the Recycle Bin', async () => {
			await contentsPage.goto();

			await contentsPage.deleteContent(contentName);
		});

		await test.step('Go to the Recycle Bin and delete the content permanently', async () => {
			await recycleBinPage.goto();

			await page
				.getByRole('row', {name: contentName})
				.getByRole('button')
				.click();

			await page.getByRole('menuitem', {name: 'Delete'}).click();

			await page.getByLabel('Delete').waitFor({state: 'visible'});

			await checkAccessibility({
				page,
				selectors: ['.modal-content'],
			});

			await expect(
				recycleBinPage.deleteItemConfirmationText
			).toBeVisible();

			await recycleBinPage.deleteButton.last().click();

			await waitForAlert(
				page,
				`Success:${contentName} has been permanently deleted.`
			);
		});
	}
);

test(
	'Can delete permanently multiple contents from Recycle Bin',
	{tag: '@LPD-62787'},
	async ({apiHelpers, contentsPage, page, recycleBinPage}) => {
		const applicationName = 'cms/basic-web-contents';
		const contentName1 = `First Content ${getRandomString()}`;
		const contentName2 = `Second Content ${getRandomString()}`;
		const spaceName = `Space ${getRandomString()}`;

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
				title: contentName1,
			},
			applicationName,
			spaceName
		);

		await apiHelpers.objectEntry.postObjectEntry(
			{
				objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
				title: contentName2,
			},
			applicationName,
			spaceName
		);

		await test.step('Delete the contents so they can go into the Recycle Bin', async () => {
			await contentsPage.goto();

			await contentsPage.deleteContent(contentName1);

			await contentsPage.deleteContent(contentName2);
		});

		await test.step('Go to the Recycle Bin and delete the contents permanently', async () => {
			await recycleBinPage.goto();

			await recycleBinPage.selectItems([contentName1, contentName2]);

			await page
				.getByTestId('visualization-mode-table')
				.getByLabel('Actions')
				.click();

			await page.getByRole('menuitem', {name: 'Delete'}).click();

			await expect(
				page.getByText('You are about to permanently delete 2 entries.')
			).toBeVisible();

			await checkAccessibility({
				page,
				selectors: ['.modal-content'],
			});

			await page.getByRole('button', {name: 'Delete'}).click();

			await page.reload();

			await expect(
				page.getByRole('cell', {name: contentName1})
			).toBeHidden();
			await expect(
				page.getByRole('cell', {name: contentName2})
			).toBeHidden();
		});
	}
);

test(
	'Can empty the Recycle Bin',
	{tag: '@LPD-62787'},
	async ({apiHelpers, contentsPage, page, recycleBinPage}) => {
		const applicationName = 'cms/basic-web-contents';
		const contentName1 = `First Content ${getRandomString()}`;
		const contentName2 = `Second Content ${getRandomString()}`;
		const spaceName = `Space ${getRandomString()}`;

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
				title: contentName1,
			},
			applicationName,
			spaceName
		);

		await apiHelpers.objectEntry.postObjectEntry(
			{
				objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
				title: contentName2,
			},
			applicationName,
			spaceName
		);

		await test.step('Delete the contents so they can go into the Recycle Bin', async () => {
			await contentsPage.goto();

			await contentsPage.deleteContent(contentName1);

			await contentsPage.deleteContent(contentName2);
		});

		await test.step('Go to the Recycle Bin and empty it', async () => {
			await recycleBinPage.goto();

			await page.getByLabel('More Actions').click();

			await recycleBinPage.emptyRecycleBinButton.waitFor({
				state: 'visible',
			});

			await recycleBinPage.emptyRecycleBinButton.click();

			await expect(
				page.getByText(
					'This will permanently delete all items in the recycle bin.'
				)
			).toBeVisible();

			await checkAccessibility({
				page,
				selectors: ['.modal-content'],
			});

			await page.getByRole('button', {name: 'Empty Bin'}).click();

			await waitForAlert(
				page,
				'Info:Delete action started for all assets.',
				{type: 'info'}
			);

			await page.reload();

			await expect(
				page.getByRole('cell', {name: contentName1})
			).toBeHidden();
			await expect(
				page.getByRole('cell', {name: contentName2})
			).toBeHidden();
		});
	}
);

test(
	'Can restore a single content from Recycle Bin',
	{tag: '@LPD-55830'},
	async ({apiHelpers, contentsPage, page, recycleBinPage}) => {
		const applicationName = 'cms/basic-web-contents';
		const contentName = `Content ${getRandomString()}`;
		const spaceName = `Space ${getRandomString()}`;

		await test.step('Create a new Space', async () => {
			await apiHelpers.headlessAssetLibrary.createAssetLibrary({
				name: spaceName,
				settings: {},
				type: 'Space',
			});
		});

		await apiHelpers.objectEntry.postObjectEntry(
			{
				objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
				title: contentName,
			},
			applicationName,
			spaceName
		);

		await test.step('Delete the created content so it goes into the Recycle Bin', async () => {
			await contentsPage.goto();

			await contentsPage.deleteContent(contentName);
		});

		await test.step('Go to the Recycle Bin and restore the item ', async () => {
			await recycleBinPage.goto();

			await page
				.getByRole('row', {name: contentName})
				.getByRole('button')
				.click();

			await page.getByRole('menuitem', {name: 'Restore'}).click();

			await waitForAlert(
				page,
				`Success:${contentName} was restored to Contents.`,
				{autoClose: false}
			);

			await checkAccessibility({
				page,
				selectors: ['.alert-success'],
			});
		});

		await test.step('Assert item is restored', async () => {
			await page.getByRole('link', {name: 'Contents'}).click();

			await expect(
				page.getByRole('row', {name: contentName})
			).toBeVisible();
		});

		await test.step('Clean up', async () => {
			await contentsPage.deleteContent(contentName);

			await recycleBinPage.goto();

			await page
				.getByRole('row', {name: contentName})
				.getByRole('button')
				.click();

			await page.getByRole('menuitem', {name: 'Delete'}).click();

			await expect(
				recycleBinPage.deleteItemConfirmationText
			).toBeVisible();

			await recycleBinPage.deleteButton.last().click();

			await waitForAlert(
				page,
				`Success:${contentName} has been permanently deleted.`
			);
		});
	}
);

test(
	'Can restore a folder and its contents from Recycle Bin',
	{tag: '@LPD-59716'},
	async ({apiHelpers, contentsPage, page, recycleBinPage}) => {
		test.slow();

		const contentName = `Content ${getRandomString()}`;
		const folderName = getRandomString();
		const nestedFolderName = `nested-${getRandomString()}`;
		const spaceName = `Space ${getRandomString()}`;

		await test.step('Create a new Space', async () => {
			await apiHelpers.headlessAssetLibrary.createAssetLibrary({
				name: spaceName,
				settings: {},
				type: 'Space',
			});
		});

		const folderData =
			await apiHelpers.objectFolder.createObjectEntryFolder({
				parentObjectEntryFolderExternalReferenceCode: 'L_CONTENTS',
				scopeKey: spaceName,
				title: folderName,
			});

		const nestedFolderData =
			await apiHelpers.objectFolder.createObjectEntryFolder({
				parentObjectEntryFolderExternalReferenceCode:
					folderData.externalReferenceCode,
				scopeKey: spaceName,
				title: nestedFolderName,
			});

		const applicationName = 'cms/basic-web-contents';
		await apiHelpers.objectEntry.postObjectEntry(
			{
				objectEntryFolderExternalReferenceCode:
					nestedFolderData.externalReferenceCode,
				title: contentName,
			},
			applicationName,
			spaceName
		);

		await test.step('Move the folder to Recycle Bin', async () => {
			await contentsPage.goto();

			await contentsPage.deleteFolder(folderName);
		});

		await test.step('Assert navigation within Recycle Bin', async () => {
			await recycleBinPage.goto();

			await recycleBinPage.navigateTo(folderName);

			await recycleBinPage.navigateTo(nestedFolderName);

			await recycleBinPage
				.getItem(contentName)
				.waitFor({state: 'visible'});

			await expect(
				page.getByRole('row', {name: contentName})
			).toBeVisible();
		});

		await test.step('Restore the folder from Recycle Bin', async () => {
			await recycleBinPage.goto();

			await recycleBinPage.execItemAction({
				action: 'Restore',
				filter: folderName,
			});

			await waitForAlert(
				page,
				`Success:${folderName} was restored to Contents.`,
				{autoClose: false}
			);
		});

		await test.step('Assert folder and its contents are restored', async () => {
			await contentsPage.goto();

			await contentsPage.navigateTo(folderName);

			await contentsPage.navigateTo(nestedFolderName);

			await recycleBinPage
				.getItem(contentName)
				.waitFor({state: 'visible'});

			await expect(
				page.getByRole('row', {name: contentName})
			).toBeVisible();
		});

		await test.step('Clean up', async () => {
			await apiHelpers.objectFolder.deleteObjectEntryFolder(
				folderData.id
			);

			await recycleBinPage.goto();

			await recycleBinPage.execItemAction({
				action: 'Delete',
				filter: folderName,
			});

			await expect(
				recycleBinPage.deleteItemConfirmationText
			).toBeVisible();

			await recycleBinPage.deleteButton.last().click();

			await waitForAlert(
				page,
				`Success:${folderName} has been permanently deleted.`
			);
		});
	}
);

test(
	'Can use the success toast options of undo and redirect to Recycle Bin after deleting content',
	{tag: '@LPD-53983'},
	async ({apiHelpers, contentsPage, page, recycleBinPage}) => {
		const applicationName = 'cms/basic-web-contents';
		const contentName = `Content ${getRandomString()}`;
		const spaceName = `Space ${getRandomString()}`;

		await test.step('Create a new Space', async () => {
			await apiHelpers.headlessAssetLibrary.createAssetLibrary({
				name: spaceName,
				settings: {},
				type: 'Space',
			});
		});

		await apiHelpers.objectEntry.postObjectEntry(
			{
				objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
				title: contentName,
			},
			applicationName,
			spaceName
		);

		await test.step('Delete the created content so it goes into the Recycle Bin', async () => {
			await contentsPage.goto();

			await contentsPage.deleteContent(contentName);

			await checkAccessibility({
				page,
				selectors: ['.alert-success'],
			});
		});

		await test.step('Restore the content using the undo button from the success toast', async () => {
			await page.getByRole('button', {name: 'Undo'}).click();

			await expect(
				page.getByText(`Success:${contentName} was restored`)
			).toBeVisible();
		});

		await test.step('Delete the content again and go to the Recycle Bin using the toast link', async () => {
			await contentsPage.deleteContent(contentName);

			await page.getByRole('link', {name: 'Recycle Bin'}).click();
		});

		await test.step('Delete the content from the Recycle Bin', async () => {
			await expect(
				page.getByRole('row', {name: contentName})
			).toBeVisible();

			await recycleBinPage.execItemAction({
				action: 'Delete',
				filter: contentName,
			});

			await expect(
				recycleBinPage.deleteItemConfirmationText
			).toBeVisible();

			await recycleBinPage.deleteButton.last().click();

			await waitForAlert(
				page,
				`Success:${contentName} has been permanently deleted.`
			);

			await expect(
				page.getByText(`Success:${contentName} was moved to the`)
			).toBeHidden();
		});
	}
);
