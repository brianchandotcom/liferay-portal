/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import {createReadStream} from 'fs';
import path from 'path';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {documentLibraryPagesTest} from '../../../fixtures/documentLibraryPages.fixtures';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {DocumentLibraryViewFileEntryPage} from '../../../pages/document-library-web/DocumentLibraryViewFileEntryPage';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
import getDataStructureDefinition from '../../journal-web/main/utils/getDataStructureDefinition';

const test = mergeTests(
	dataApiHelpersTest,
	documentLibraryPagesTest,
	isolatedSiteTest,
	loginTest()
);

test(
	'Documents and folders can be edited, moved, copied and deleted in a depot',
	{tag: '@LPD-109574'},
	async ({
		apiHelpers,
		documentLibraryEditFilePage,
		documentLibraryEditFolderPage,
		documentLibraryPage,
		page,
	}) => {
		const assetLibrary =
			await apiHelpers.headlessAssetLibrary.createAssetLibrary({
				name: getRandomString(),
				type: 'AssetLibrary',
			});

		const depotURL = `/asset-library-${assetLibrary.id}`;

		const addDocument = async () => {
			const title = getRandomString();

			await apiHelpers.headlessDelivery.postAssetLibraryDocument(
				assetLibrary.id,
				createReadStream(
					path.join(__dirname, 'dependencies/Document.jpg')
				),
				{fileName: `${title}.jpg`, title}
			);

			return title;
		};

		const documentTitle = await addDocument();
		const otherDocumentTitle = await addDocument();

		const folder = await apiHelpers.headlessDelivery.postDocumentFolder(
			assetLibrary.siteId
		);
		const targetFolder =
			await apiHelpers.headlessDelivery.postDocumentFolder(
				assetLibrary.siteId
			);

		const editedFolderName = getRandomString();

		await test.step('Edit a folder', async () => {
			await documentLibraryPage.goto(depotURL);

			await documentLibraryPage.changeView('Cards');

			await documentLibraryPage.goToFolderAction('Edit', folder.name);

			await documentLibraryEditFolderPage.fillTitle(editedFolderName);

			await documentLibraryEditFolderPage.saveButton.click();

			await waitForAlert(page);

			await expect(
				page.getByRole('link', {exact: true, name: editedFolderName})
			).toBeVisible();
		});

		const editedDocumentTitle = getRandomString();

		await test.step('Edit a document and its friendly URL', async () => {
			await documentLibraryPage.goToFileEntryAction(
				'Edit',
				documentTitle
			);

			await documentLibraryEditFilePage.titleSelector.fill(
				editedDocumentTitle
			);

			await page
				.locator('input[type="file"]')
				.first()
				.setInputFiles(
					path.join(__dirname, 'dependencies/Document_Small.jpeg')
				);

			await page.getByRole('button', {name: 'Friendly URL'}).click();

			await page
				.getByRole('textbox', {name: /Friendly URL/})
				.fill(otherDocumentTitle);

			await documentLibraryEditFilePage.publishButton.click();

			// The friendly URL of another depot document is not reused

			await waitForAlert(
				page,
				`The friendly URL ${otherDocumentTitle} was changed to ${otherDocumentTitle}-1 to ensure uniqueness.`,
				{type: 'warning'}
			);

			await documentLibraryPage.goToViewFileEntry(editedDocumentTitle);

			const documentLibraryViewFileEntryPage =
				new DocumentLibraryViewFileEntryPage(page);

			await documentLibraryViewFileEntryPage.openInfoPanel(
				editedDocumentTitle,
				'Details'
			);

			await expect(
				documentLibraryViewFileEntryPage.infoPanel
			).toContainText(/Version\s*2\.0/);
		});

		await test.step('Move a document to a folder', async () => {
			await documentLibraryPage.goto(depotURL);

			await documentLibraryPage.goToFileEntryAction(
				'Move',
				editedDocumentTitle
			);

			await page
				.frameLocator(
					'iframe[title="Select Destination Folder for 1 Item"]'
				)
				.getByRole('row')
				.filter({hasText: editedFolderName})
				.getByRole('button', {name: 'Select'})
				.click();

			await waitForAlert(page);

			await expect(
				page.getByRole('link', {exact: true, name: editedDocumentTitle})
			).toHaveCount(0);

			await page
				.getByRole('link', {exact: true, name: editedFolderName})
				.click();

			await expect(
				page.getByRole('link', {exact: true, name: editedDocumentTitle})
			).toBeVisible();
		});

		await test.step('Copy a folder and a document to another folder', async () => {
			const copyTo = async (goToCopyTo: () => Promise<void>) => {
				await documentLibraryPage.goto(depotURL);

				await goToCopyTo();

				await page
					.getByRole('button', {exact: true, name: 'Select'})
					.click();

				await page
					.frameLocator('iframe[title="Select"]')
					.getByRole('row')
					.filter({hasText: targetFolder.name})
					.getByRole('button', {name: 'Select'})
					.click();

				await page
					.getByRole('button', {exact: true, name: 'Copy'})
					.click();

				await waitForAlert(page, '1 item was copied successfully.');
			};

			await copyTo(() =>
				documentLibraryPage.goToFolderAction(
					'Copy To',
					editedFolderName
				)
			);
			await copyTo(() =>
				documentLibraryPage.goToFileEntryAction(
					'Copy To',
					otherDocumentTitle
				)
			);

			// The originals stay where they were

			await documentLibraryPage.goto(depotURL);

			await expect(
				page.getByRole('link', {exact: true, name: editedFolderName})
			).toBeVisible();
			await expect(
				page.getByRole('link', {exact: true, name: otherDocumentTitle})
			).toBeVisible();

			await page
				.getByRole('link', {exact: true, name: targetFolder.name})
				.click();

			// The copies have the same names as the originals in the root

			await expect(page).toHaveURL(
				new RegExp(`folderId=${targetFolder.id}`)
			);

			await expect(
				page.getByRole('link', {exact: true, name: editedFolderName})
			).toBeVisible();
			await expect(
				page.getByRole('link', {exact: true, name: otherDocumentTitle})
			).toBeVisible();

			await page
				.getByRole('link', {exact: true, name: editedFolderName})
				.click();

			await expect(
				page.getByRole('link', {exact: true, name: editedDocumentTitle})
			).toBeVisible();
		});

		await test.step('Delete a folder and then everything in bulk', async () => {
			await documentLibraryPage.goto(depotURL);

			await documentLibraryPage.moveFolderToRecycleBin(targetFolder.name);

			await expect(
				page.getByRole('link', {exact: true, name: targetFolder.name})
			).toHaveCount(0);

			await page.getByLabel('Select All Items on the Page').check();

			await page.getByRole('button', {name: 'Delete'}).click();

			// The edited folder and the other document are left in the root

			await waitForAlert(page, '2 items were moved to the Recycle Bin.');

			await expect(
				page.getByText(
					'There are no documents or media files in this folder.'
				)
			).toBeVisible();
		});
	}
);

test(
	'Depot documents can be filtered by category, extension and tag',
	{tag: '@LPD-109574'},
	async ({apiHelpers, documentLibraryPage, page}) => {
		const assetLibrary =
			await apiHelpers.headlessAssetLibrary.createAssetLibrary({
				name: getRandomString(),
				type: 'AssetLibrary',
			});

		const vocabulary =
			await apiHelpers.headlessAdminTaxonomy.postAssetLibraryTaxonomyVocabulary(
				{assetLibraryId: assetLibrary.id, name: getRandomString()}
			);

		const addCategory = (name: string) =>
			apiHelpers.headlessAdminTaxonomy.postTaxonomyVocabularyTaxonomyCategory(
				{name, vocabularyId: vocabulary.id}
			);

		const categoryName = getRandomString();

		const category = await addCategory(categoryName);
		const sharedCategory = await addCategory(getRandomString());

		const sharedTag = getRandomString();
		const tag = getRandomString();

		const imageTitle = getRandomString();

		await apiHelpers.headlessDelivery.postAssetLibraryDocument(
			assetLibrary.id,
			createReadStream(path.join(__dirname, 'dependencies/Document.jpg')),
			{
				fileName: `${imageTitle}.jpg`,
				keywords: [sharedTag],
				taxonomyCategoryIds: [sharedCategory.id],
				title: imageTitle,
			}
		);

		const pdfTitle = getRandomString();

		await apiHelpers.headlessDelivery.postAssetLibraryDocument(
			assetLibrary.id,
			createReadStream(path.join(__dirname, 'dependencies/Document.pdf')),
			{
				fileName: `${pdfTitle}.pdf`,
				keywords: [sharedTag, tag],
				taxonomyCategoryIds: [category.id, sharedCategory.id],
				title: pdfTitle,
			}
		);

		const depotURL = `/asset-library-${assetLibrary.id}`;

		const filterDialog = page.getByRole('dialog');

		const filterFrameLocator = filterDialog.frameLocator('iframe');

		const assertFilteredTo = async (title: string, otherTitle: string) => {

			// Only the filtered results page shows the count

			await expect(
				page.getByText('1 Result Found With Filters')
			).toBeVisible();

			await expect(
				page.getByRole('link', {exact: true, name: title})
			).toBeVisible();
			await expect(
				page.getByRole('link', {exact: true, name: otherTitle})
			).toHaveCount(0);
		};

		await test.step('Filter by category', async () => {
			await documentLibraryPage.goto(depotURL);

			await documentLibraryPage.openFilter('Categories');

			await filterFrameLocator
				.getByRole('treeitem', {name: new RegExp(vocabulary.name)})
				.getByRole('button')
				.click();

			await filterFrameLocator
				.getByRole('treeitem')
				.filter({hasText: categoryName})
				.getByRole('checkbox')
				.check();

			await filterDialog.getByRole('button', {name: 'Select'}).click();

			await assertFilteredTo(pdfTitle, imageTitle);
		});

		await test.step('Filter by extension', async () => {
			await documentLibraryPage.goto(depotURL);

			await documentLibraryPage.openFilter('Extension');

			await expect(
				filterFrameLocator.getByRole('treeitem', {
					name: 'Vectorial (1 Item)',
				})
			).toBeVisible();

			await filterFrameLocator
				.getByRole('checkbox', {name: 'Image (1 Item)'})
				.check();

			await filterDialog.getByRole('button', {name: 'Apply'}).click();

			await assertFilteredTo(imageTitle, pdfTitle);
		});

		await test.step('Filter by tag', async () => {
			await documentLibraryPage.goto(depotURL);

			await documentLibraryPage.openFilter('Tags');

			await filterFrameLocator
				.getByRole('row')
				.filter({hasText: tag})
				.getByRole('checkbox')
				.check();

			await filterDialog.getByRole('button', {name: 'Select'}).click();

			await assertFilteredTo(pdfTitle, imageTitle);
		});
	}
);

test(
	'A depot document keeps the values of its document type and metadata set fields',
	{tag: '@LPD-109574'},
	async ({
		apiHelpers,
		documentLibraryEditFilePage,
		documentLibraryPage,
		page,
	}) => {
		const assetLibrary =
			await apiHelpers.headlessAssetLibrary.createAssetLibrary({
				name: getRandomString(),
				type: 'AssetLibrary',
			});

		const metadataSetName = getRandomString();

		const metadataSet = await apiHelpers.dataEngine.createStructure(
			String(assetLibrary.siteId),
			getDataStructureDefinition({
				defaultLanguageId: 'en_US',
				fields: [{name: 'MetadataText'}],
				name: metadataSetName,
			}),
			'document-library'
		);

		const documentTypeDefinition = getDataStructureDefinition({
			defaultLanguageId: 'en_US',
			fields: [{fieldType: 'numeric', name: 'TypeNumber'}],
			name: getRandomString(),
		});

		const documentTypeName = getRandomString();

		await apiHelpers.headlessDelivery.postSiteDocumentDataDefinitionType(
			String(assetLibrary.siteId),
			documentTypeName,
			{
				dataDefinitionFields:
					documentTypeDefinition.dataDefinitionFields,
				dataLayout: documentTypeDefinition.defaultDataLayout,
				documentMetadataSetIds: [Number(metadataSet.id)],
			}
		);

		// Add a document of the type

		await documentLibraryPage.goto(`/asset-library-${assetLibrary.id}`);

		await documentLibraryPage.openNewButton();

		await page.getByRole('menuitem', {name: documentTypeName}).click();

		await page
			.locator('input[type="file"]')
			.first()
			.setInputFiles(path.join(__dirname, 'dependencies/Document.pdf'));

		const title = getRandomString();

		await documentLibraryEditFilePage.titleSelector.fill(title);

		await page
			.getByRole('textbox', {exact: true, name: 'TypeNumber'})
			.fill('1234');

		const metadataSetFieldValue = getRandomString();

		await page
			.getByRole('textbox', {exact: true, name: 'MetadataText'})
			.fill(metadataSetFieldValue);

		await documentLibraryEditFilePage.publishButton.click();

		await waitForAlert(page);

		// Both values are shown with the document

		await documentLibraryPage.goToViewFileEntry(title);

		// Delete the document before its document type

		apiHelpers.data.push({
			id: Number(
				new URL(page.url()).searchParams.get(
					'_com_liferay_document_library_web_portlet_DLAdminPortlet_fileEntryId'
				)
			),
			type: 'document',
		});

		const documentLibraryViewFileEntryPage =
			new DocumentLibraryViewFileEntryPage(page);

		await documentLibraryViewFileEntryPage.openInfoPanel(title, 'Details');

		for (const [panelName, fieldName, value] of [
			[documentTypeName, 'TypeNumber', '1234'],
			[metadataSetName, 'MetadataText', metadataSetFieldValue],
		]) {
			await documentLibraryViewFileEntryPage.infoPanel
				.getByRole('button', {name: panelName})
				.click();

			await expect(
				documentLibraryViewFileEntryPage.infoPanel.getByRole(
					'textbox',
					{exact: true, name: fieldName}
				)
			).toHaveValue(value);
		}
	}
);

test(
	'A depot shortcut to a site document opens the document',
	{tag: '@LPD-109574'},
	async ({apiHelpers, documentLibraryPage, page, site}) => {
		const assetLibrary =
			await apiHelpers.headlessAssetLibrary.createAssetLibrary({
				name: getRandomString(),
				type: 'AssetLibrary',
			});

		const title = getRandomString();

		await apiHelpers.headlessDelivery.postDocument(
			site.id,
			createReadStream(path.join(__dirname, 'dependencies/Document.jpg')),
			{fileName: `${title}.jpg`, title}
		);

		await documentLibraryPage.goto(`/asset-library-${assetLibrary.id}`);

		await documentLibraryPage.openNewButton();

		await page
			.getByRole('menuitem', {exact: true, name: 'Shortcut'})
			.click();

		await page.getByRole('button', {exact: true, name: 'Select'}).click();

		const itemSelector = page
			.getByRole('dialog', {name: 'Select File'})
			.frameLocator('iframe');

		// The item selector opens in the depot

		await itemSelector
			.getByRole('link', {exact: true, name: 'Sites and Libraries'})
			.click();

		await itemSelector
			.getByRole('link', {exact: true, name: site.name})
			.click();

		await itemSelector.getByText(title, {exact: true}).click();

		await expect(
			page.getByRole('textbox', {exact: true, name: 'Document'})
		).toHaveValue(title);

		await page.getByRole('button', {exact: true, name: 'Save'}).click();

		await waitForAlert(page);

		// The shortcut opens the site document

		await page.getByRole('link', {exact: true, name: title}).click();

		await expect(
			page.getByRole('heading', {exact: true, name: title}).first()
		).toBeVisible();
		await expect(page).toHaveURL(/view_file_entry/);
	}
);
