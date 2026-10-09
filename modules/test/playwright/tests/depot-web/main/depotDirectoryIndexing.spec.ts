/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';
import {createReadStream} from 'fs';
import path from 'path';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {DataApiHelpers} from '../../../helpers/ApiHelpers';
import getRandomString from '../../../utils/getRandomString';
import {PORTLET_URLS} from '../../../utils/portletUrls';
import {waitForAlert} from '../../../utils/waitForAlert';

const test = mergeTests(dataApiHelpersTest, loginTest()).extend<{
	depot: {companyId: number; depotEntryId: string; groupId: number};
}>({
	depot: async ({apiHelpers}, use) => {

		// A depot added through the headless API stores a Recycle Bin max age
		// of 0, which fails validation when its General settings are saved

		const depot =
			await apiHelpers.jsonWebServicesDepot.addDepotEntry(
				getRandomString()
			);

		await use(depot);

		await apiHelpers.jsonWebServicesDepot.deleteDepotEntry(
			depot.depotEntryId
		);
	},
});

const DEPOT_ADMIN_NAMESPACE =
	'_com_liferay_depot_web_portlet_DepotAdminPortlet_';

const DL_FILE_ENTRY_CLASS_NAME =
	'com.liferay.document.library.kernel.model.DLFileEntry';

const DL_FOLDER_CLASS_NAME =
	'com.liferay.document.library.kernel.model.DLFolder';

const DOCUMENT_PATH = path.join(__dirname, 'dependencies/Document.jpg');

async function addDocument({
	apiHelpers,
	assetLibraryId,
	documentFolderId,
	title = getRandomString(),
	viewableBy = 'Anyone',
}: {
	apiHelpers: DataApiHelpers;
	assetLibraryId: string;
	documentFolderId?: number;
	title?: string;
	viewableBy?: string;
}) {
	const document = {
		fileName: `${getRandomString()}.jpg`,
		title,
		viewableBy,
	};

	if (documentFolderId) {
		return apiHelpers.headlessDelivery.postDocumentFolderDocument(
			documentFolderId,
			createReadStream(DOCUMENT_PATH),
			document
		);
	}

	return apiHelpers.headlessDelivery.postAssetLibraryDocument(
		assetLibraryId,
		createReadStream(DOCUMENT_PATH),
		document
	);
}

async function enableDirectoryIndexing(page: Page, assetLibraryId: string) {
	await page.goto(
		`/group/guest${PORTLET_URLS.depotAdmin}&${DEPOT_ADMIN_NAMESPACE}mvcRenderCommandName=%2Fdepot%2Fedit_depot_entry&${DEPOT_ADMIN_NAMESPACE}depotEntryId=${assetLibraryId}`
	);

	await page.getByLabel('Enable Directory Indexing').check();

	await page.getByRole('button', {exact: true, name: 'Save'}).click();

	await waitForAlert(page);
}

function getEntryLink(page: Page, title: string) {
	return page.getByRole('link', {exact: true, name: title});
}

async function gotoDirectory(page: Page, assetLibraryId: string) {
	await page.goto(`/documents/asset-library-${assetLibraryId}`);
}

test(
	'The depot directory is not found while directory indexing is disabled',
	{tag: '@LPD-109097'},
	async ({depot, page}) => {
		await page.goto(
			`/group/guest${PORTLET_URLS.depotAdmin}&${DEPOT_ADMIN_NAMESPACE}mvcRenderCommandName=%2Fdepot%2Fedit_depot_entry&${DEPOT_ADMIN_NAMESPACE}depotEntryId=${depot.depotEntryId}`
		);

		await expect(
			page.getByLabel('Enable Directory Indexing')
		).not.toBeChecked();

		await gotoDirectory(page, depot.depotEntryId);

		await expect(
			page.getByText('The requested resource could not be found.')
		).toBeVisible();
	}
);

test(
	'The depot directory lists documents across their lifecycle and hides those guests cannot view',
	{tag: '@LPD-109097'},
	async ({apiHelpers, baseURL, browser, depot, page}) => {
		await enableDirectoryIndexing(page, depot.depotEntryId);

		const document = await addDocument({
			apiHelpers,
			assetLibraryId: depot.depotEntryId,
		});

		const escapedDocumentTitle = `& < > ${getRandomString()}`;

		await addDocument({
			apiHelpers,
			assetLibraryId: depot.depotEntryId,
			title: escapedDocumentTitle,
		});

		const ownerDocumentTitle = getRandomString();

		await addDocument({
			apiHelpers,
			assetLibraryId: depot.depotEntryId,
			title: ownerDocumentTitle,
			viewableBy: 'Owner',
		});

		const folder = await apiHelpers.headlessDelivery.postDocumentFolder(
			depot.groupId
		);

		const folderDocumentTitle = getRandomString();

		await addDocument({
			apiHelpers,
			assetLibraryId: depot.depotEntryId,
			documentFolderId: folder.id,
			title: folderDocumentTitle,
		});

		await test.step('The directory lists the documents and the folder', async () => {
			await gotoDirectory(page, depot.depotEntryId);

			for (const title of [
				document.title,
				escapedDocumentTitle,
				ownerDocumentTitle,
				`${folder.name}/`,
			]) {
				await expect(getEntryLink(page, title)).toBeVisible();
			}

			await getEntryLink(page, `${folder.name}/`).click();

			await expect(getEntryLink(page, folderDocumentTitle)).toBeVisible();
		});

		await test.step('The directory follows a renamed and a deleted document', async () => {
			const editedDocumentTitle = getRandomString();

			await apiHelpers.headlessDelivery.patchDocument({
				document: {title: editedDocumentTitle},
				documentId: document.id,
			});

			await gotoDirectory(page, depot.depotEntryId);

			await expect(getEntryLink(page, editedDocumentTitle)).toBeVisible();
			await expect(getEntryLink(page, document.title)).toHaveCount(0);

			await apiHelpers.headlessDelivery.deleteDocument(document.id);

			await gotoDirectory(page, depot.depotEntryId);

			await expect(getEntryLink(page, editedDocumentTitle)).toHaveCount(
				0
			);
		});

		await test.step('A guest does not see the owner-only document', async () => {
			const guestContext = await browser.newContext({baseURL});

			try {
				const guestPage = await guestContext.newPage();

				await gotoDirectory(guestPage, depot.depotEntryId);

				await expect(
					getEntryLink(guestPage, escapedDocumentTitle)
				).toBeVisible();
				await expect(
					getEntryLink(guestPage, ownerDocumentTitle)
				).toHaveCount(0);
			}
			finally {
				await guestContext.close();
			}
		});
	}
);

test(
	'An approved depot document shows in the directory, a pending one does not',
	{tag: '@LPD-109097'},
	async ({apiHelpers, depot, page}) => {
		const workflowDefinition =
			await apiHelpers.headlessAdminWorkflow.getWorkflowDefinitionByName(
				'Single Approver'
			);

		const userAccount =
			await apiHelpers.headlessAdminUser.getMyUserAccount();

		// Documents and Media resolves the workflow from a link on the folder,
		// the root folder here, for all document types

		await apiHelpers.jsonWebServicesWorkflowDefinitionLink.addWorkflowDefinitionLink(
			{
				className: DL_FOLDER_CLASS_NAME,
				classPK: 0,
				companyId: depot.companyId,
				groupId: depot.groupId,
				typePK: -1,
				userId: userAccount.id,
				workflowDefinitionName: workflowDefinition.name,
				workflowDefinitionVersion: workflowDefinition.version,
			}
		);

		await enableDirectoryIndexing(page, depot.depotEntryId);

		const document = await addDocument({
			apiHelpers,
			assetLibraryId: depot.depotEntryId,
		});

		await gotoDirectory(page, depot.depotEntryId);

		await expect(getEntryLink(page, document.title)).toHaveCount(0);

		await test.step('Approve the document', async () => {

			// The workflow of a document runs on its file version

			const fileVersion =
				await apiHelpers.jsonWebServicesDocumentLibrary.getLastestFileVersion(
					String(document.id)
				);

			let workflowTask;

			await expect(async () => {
				workflowTask =
					await apiHelpers.headlessAdminWorkflow.getWorkflowTaskByAsset(
						DL_FILE_ENTRY_CLASS_NAME,
						fileVersion.fileVersionId
					);

				expect(workflowTask).toBeTruthy();
			}).toPass();

			await apiHelpers.headlessAdminWorkflow.postAssignTaskToUser(
				workflowTask.id,
				userAccount.id
			);

			await apiHelpers.headlessAdminWorkflow.postWorkflowTaskChangeTransition(
				workflowTask.id,
				'approve'
			);
		});

		await expect(async () => {
			await gotoDirectory(page, depot.depotEntryId);

			await expect(getEntryLink(page, document.title)).toBeVisible({
				timeout: 2000,
			});
		}).toPass({timeout: 30000});
	}
);
