/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Page, expect, mergeTests} from '@playwright/test';
import {createReadStream} from 'fs';
import path from 'path';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {documentLibraryPagesTest} from '../../../fixtures/documentLibraryPages.fixtures';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {workflowPagesTest} from '../../../fixtures/workflowPagesTest';
import {DocumentLibraryEditFolderPage} from '../../../pages/document-library-web/DocumentLibraryEditFolderPage';
import {WorkflowTasksPage} from '../../../pages/portal-workflow-task-web/WorkflowTasksPage';
import getRandomString from '../../../utils/getRandomString';
import getBasicWebContentStructureId from '../../../utils/structured-content/getBasicWebContentStructureId';
import {waitForAlert} from '../../../utils/waitForAlert';
import {blogsPagesTest} from '../../blogs-web/main/fixtures/blogsPagesTest';
import {journalPagesTest} from '../../journal-web/main/fixtures/journalPagesTest';
import addConnectedDepot from './utils/addConnectedDepot';
import gotoDepotInItemSelector from './utils/gotoDepotInItemSelector';

const test = mergeTests(
	blogsPagesTest,
	dataApiHelpersTest,
	documentLibraryPagesTest,
	isolatedSiteTest,
	journalPagesTest,
	loginTest(),
	workflowPagesTest
);

async function goToAssignedToMyRolesTask(
	page: Page,
	title: string,
	workflowTasksPage: WorkflowTasksPage
) {

	// The tasks are listed through the search index

	await expect(async () => {
		await workflowTasksPage.goToAssignedToMyRoles();

		await expect(
			page.getByRole('row').filter({hasText: title})
		).toBeVisible({timeout: 2000});
	}).toPass();
}

async function saveFolderWorkflow(page: Page, option: RegExp) {
	await page.getByLabel(option).check();

	await page.getByRole('button', {exact: true, name: 'Save'}).click();

	await waitForAlert(page);
}

test(
	'Depot web content in a folder with workflow can be rejected, resubmitted and approved until the folder inherits the workflow again',
	{tag: '@LPD-109574'},
	async ({
		apiHelpers,
		journalEditFolderPage,
		journalPage,
		page,
		workflowTasksPage,
	}) => {
		const assetLibrary =
			await apiHelpers.headlessAssetLibrary.createAssetLibrary({
				name: getRandomString(),
				type: 'AssetLibrary',
			});

		const depotURL = `/asset-library-${assetLibrary.id}`;

		const folder = await apiHelpers.jsonWebServicesJournal.addFolder({
			groupId: assetLibrary.siteId,
		});

		const addWebContent = async () => {
			const title = getRandomString();

			await apiHelpers.jsonWebServicesJournal.addWebContent({
				ddmStructureId: await getBasicWebContentStructureId(apiHelpers),
				folderId: folder.folderId,
				groupId: assetLibrary.siteId,
				titleMap: {en_US: title},
			});

			return title;
		};

		await test.step('Set the default workflow of the folder', async () => {
			await journalPage.goto(depotURL);

			await journalEditFolderPage.editFolder(folder.name);

			await page
				.getByRole('button', {
					name: /Structure Restrictions and Workflow/,
				})
				.click();

			await page.getByLabel(/Set the default workflow for/).check();

			await page
				.getByRole('combobox')
				.filter({hasText: 'No Workflow'})
				.selectOption({label: 'Single Approver'});

			await page.getByRole('button', {exact: true, name: 'Save'}).click();

			await waitForAlert(page);
		});

		const title = await addWebContent();

		await test.step('Reject, resubmit and approve the web content', async () => {
			await goToAssignedToMyRolesTask(page, title, workflowTasksPage);

			await workflowTasksPage.assignToMe(title);

			await workflowTasksPage.reject(title);

			await workflowTasksPage.resubmit(title);

			await goToAssignedToMyRolesTask(page, title, workflowTasksPage);

			await workflowTasksPage.assignToMe(title);

			await workflowTasksPage.approve(title);
		});

		await test.step('Inherit the workflow of the parent folder', async () => {
			await journalPage.goto(depotURL);

			await journalEditFolderPage.editFolder(folder.name);

			await page
				.getByRole('button', {
					name: /Structure Restrictions and Workflow/,
				})
				.click();

			await saveFolderWorkflow(
				page,
				/Inherit allowed structures and workflows/
			);
		});

		const inheritedTitle = await addWebContent();

		await page.goto(
			`/group${depotURL}/~/control_panel/manage?p_p_id=com_liferay_journal_web_portlet_JournalPortlet&_com_liferay_journal_web_portlet_JournalPortlet_folderId=${folder.folderId}`
		);

		await journalPage.changeView('list');

		for (const approvedTitle of [inheritedTitle, title]) {
			await expect(
				page
					.locator('[data-qa-id="row"]')
					.filter({hasText: approvedTitle})
			).toContainText('Approved');
		}
	}
);

test(
	'A depot image in a folder with workflow is offered to a connected site once approved or when the folder inherits the workflow again',
	{tag: '@LPD-109574'},
	async ({
		apiHelpers,
		blogsEditBlogEntryPage,
		documentLibraryPage,
		page,
		site,
		workflowTasksPage,
	}) => {
		const assetLibrary = await addConnectedDepot(apiHelpers, site);

		const depotURL = `/asset-library-${assetLibrary.id}`;

		const folder = await apiHelpers.headlessDelivery.postDocumentFolder(
			assetLibrary.siteId
		);

		const addImage = async () => {
			const title = getRandomString();

			await apiHelpers.headlessDelivery.postDocumentFolderDocument(
				folder.id,
				createReadStream(
					path.join(__dirname, 'dependencies/Document.jpg')
				),
				{fileName: `${title}.jpg`, title}
			);

			return title;
		};

		const openFolderInItemSelector = async (): Promise<FrameLocator> => {
			await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

			await page.getByRole('button', {name: 'Select File'}).click();

			const itemSelector = page.frameLocator(
				'iframe[title="Select File"]'
			);

			await itemSelector
				.getByRole('link', {exact: true, name: 'Documents and Media'})
				.click();

			await gotoDepotInItemSelector(itemSelector, assetLibrary.name);

			await itemSelector.getByText(folder.name, {exact: true}).click();

			return itemSelector;
		};

		const documentLibraryEditFolderPage = new DocumentLibraryEditFolderPage(
			page
		);

		await test.step('Set the default workflow of the folder', async () => {
			await documentLibraryPage.goto(depotURL);

			await documentLibraryPage.goToEditFolder(folder.name);

			await documentLibraryEditFolderPage.setWorkflow('Single Approver');

			await waitForAlert(page);
		});

		const title = await addImage();

		await test.step('A pending image is not offered', async () => {
			const itemSelector = await openFolderInItemSelector();

			await expect(
				itemSelector.getByText(
					'There are no documents or media files in this folder.'
				)
			).toBeVisible();
		});

		await test.step('Approve the image', async () => {
			await goToAssignedToMyRolesTask(page, title, workflowTasksPage);

			await workflowTasksPage.assignToMe(title);

			await workflowTasksPage.approve(title);

			const itemSelector = await openFolderInItemSelector();

			await expect(
				itemSelector.getByText(title, {exact: true})
			).toBeVisible();
		});

		await test.step('Inherit the workflow of the parent folder', async () => {
			await documentLibraryPage.goto(depotURL);

			await documentLibraryPage.goToEditFolder(folder.name);

			await page
				.getByRole('button', {name: 'Document Type Restrictions'})
				.click();

			await saveFolderWorkflow(
				page,
				/Use Document Type Restrictions and Workflow of the Parent Folder/
			);

			const inheritedTitle = await addImage();

			const itemSelector = await openFolderInItemSelector();

			await expect(
				itemSelector.getByText(inheritedTitle, {exact: true})
			).toBeVisible();
		});
	}
);
