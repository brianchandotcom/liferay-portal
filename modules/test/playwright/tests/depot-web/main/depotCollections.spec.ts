/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';
import {createReadStream} from 'fs';
import path from 'path';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {CollectionsPage} from '../../../pages/asset-list-web/CollectionsPage';
import {AssetPublisherPage} from '../../../pages/asset-publisher-web/AssetPublisherPage';
import {AssetPublisherWidgetPage} from '../../../pages/asset-publisher-web/AssetPublisherWidgetPage';
import {WidgetPagePage} from '../../../pages/layout-admin-web/WidgetPagePage';
import getRandomString from '../../../utils/getRandomString';
import getBasicWebContentStructureId from '../../../utils/structured-content/getBasicWebContentStructureId';
import getDataStructureDefinition from '../../journal-web/main/utils/getDataStructureDefinition';
import addConnectedDepot from './utils/addConnectedDepot';
import gotoDepotInItemSelector from './utils/gotoDepotInItemSelector';

const test = mergeTests(dataApiHelpersTest, isolatedSiteTest, loginTest());

async function selectCollectionInAssetPublisher(
	page: Page,
	collectionName: string,
	depotName?: string
) {
	const assetPublisherWidgetPage = new AssetPublisherWidgetPage(page);

	await new WidgetPagePage(page).clickOnAction(
		'Asset Publisher',
		'Configuration'
	);

	const assetPublisherPage = new AssetPublisherPage(page);

	await assetPublisherPage.openAssetSelectionTab();

	await assetPublisherWidgetPage.configurationIframe
		.getByRole('button', {name: /^(Change|Select) Collection$/})
		.click();

	const collectionSelector =
		assetPublisherWidgetPage.collectionSelectorIframe;

	if (depotName) {
		await gotoDepotInItemSelector(collectionSelector, depotName);
	}

	await collectionSelector.getByText(collectionName, {exact: true}).click();

	await expect(assetPublisherWidgetPage.collectionInput).toHaveValue(
		collectionName
	);

	await assetPublisherPage.saveConfiguration();

	await assetPublisherPage.closeConfiguration();
}

test(
	'A depot manual collection selects depot assets by type and removes them',
	{tag: '@LPD-109574'},
	async ({apiHelpers, page}) => {
		const assetLibrary =
			await apiHelpers.headlessAssetLibrary.createAssetLibrary({
				name: getRandomString(),
				type: 'AssetLibrary',
			});

		const depotURL = `/asset-library-${assetLibrary.id}`;

		const basicWebContentStructureId =
			await getBasicWebContentStructureId(apiHelpers);

		const basicWebContentTitles = [];

		for (let i = 0; i < 3; i++) {
			const title = getRandomString();

			await apiHelpers.jsonWebServicesJournal.addWebContent({
				ddmStructureId: basicWebContentStructureId,
				groupId: assetLibrary.siteId,
				titleMap: {en_US: title},
			});

			basicWebContentTitles.push(title);
		}

		const structureName = getRandomString();

		const structure = await apiHelpers.dataEngine.createStructure(
			String(assetLibrary.siteId),
			getDataStructureDefinition({
				defaultLanguageId: 'en_US',
				fields: [{name: 'Text'}],
				name: structureName,
			})
		);

		const structureWebContentTitle = getRandomString();

		await apiHelpers.headlessDelivery.postStructuredContent({
			contentFields: [
				{contentFieldValue: {data: getRandomString()}, name: 'Text'},
			],
			contentStructureId: Number(structure.id),
			datePublished: null,
			siteId: String(assetLibrary.siteId),
			title: structureWebContentTitle,
		});

		const documentTypeName = getRandomString();

		await apiHelpers.headlessDelivery.postSiteDocumentDataDefinitionType(
			String(assetLibrary.siteId),
			documentTypeName
		);

		const documentTitle = getRandomString();

		const document =
			await apiHelpers.headlessDelivery.postAssetLibraryDocument(
				assetLibrary.id,
				createReadStream(
					path.join(__dirname, 'dependencies/Document.jpg')
				),
				{
					documentType: {name: documentTypeName},
					fileName: `${documentTitle}.jpg`,
					title: documentTitle,
				}
			);

		// Delete the document before its document type

		apiHelpers.data.push({id: document.id, type: 'document'});

		const collectionsPage = new CollectionsPage(page);

		const addManualCollection = async (
			itemType: string,
			itemSubtype: string
		) => {
			await collectionsPage.goto(depotURL);

			await collectionsPage.addNewManualCollection(getRandomString());

			await collectionsPage.configureSourceItemType({
				itemSubtype,
				itemType,
			});

			await collectionsPage.save();
		};

		const getItemRow = (title: string) =>
			page.getByRole('row').filter({
				has: page.getByRole('cell', {exact: true, name: title}),
			});

		await test.step('Select depot web content and remove one', async () => {
			await addManualCollection('Web Content Article', 'All Subtypes');

			await collectionsPage.selectAssets(basicWebContentTitles);

			for (const title of basicWebContentTitles) {
				await expect(getItemRow(title)).toBeVisible();
			}

			page.once('dialog', (dialog) => dialog.accept());

			await getItemRow(basicWebContentTitles[1])
				.getByRole('link', {name: 'Remove'})
				.click();

			await expect(getItemRow(basicWebContentTitles[1])).toHaveCount(0);

			await page.reload();

			await expect(getItemRow(basicWebContentTitles[0])).toBeVisible();
			await expect(getItemRow(basicWebContentTitles[1])).toHaveCount(0);
			await expect(getItemRow(basicWebContentTitles[2])).toBeVisible();
		});

		await test.step('Select depot web content of a depot structure', async () => {
			await addManualCollection('Web Content Article', structureName);

			await collectionsPage.selectAssets([structureWebContentTitle]);

			await expect(getItemRow(structureWebContentTitle)).toBeVisible();
		});

		await test.step('Select a depot document of a depot document type', async () => {
			await addManualCollection('Document', documentTypeName);

			await collectionsPage.selectAssets([documentTitle]);

			await expect(getItemRow(documentTitle)).toBeVisible();
		});
	}
);

test(
	'A connected site Asset Publisher shows the depot collections',
	{tag: '@LPD-109574'},
	async ({apiHelpers, page, site}) => {
		const assetLibrary = await addConnectedDepot(apiHelpers, site);

		const depotURL = `/asset-library-${assetLibrary.id}`;

		const imageTitle = getRandomString();

		const image =
			await apiHelpers.headlessDelivery.postAssetLibraryDocument(
				assetLibrary.id,
				createReadStream(
					path.join(__dirname, 'dependencies/Document.jpg')
				),
				{fileName: `${imageTitle}.jpg`, title: imageTitle}
			);

		const basicWebContentStructureId =
			await getBasicWebContentStructureId(apiHelpers);

		const tag = getRandomString();

		const addWebContent = async (tags: string[]) => {
			const title = getRandomString();

			await apiHelpers.headlessDelivery.postStructuredContent({
				contentFields: [
					{
						contentFieldValue: {data: getRandomString()},
						name: 'content',
					},
				],
				contentStructureId: basicWebContentStructureId,
				datePublished: null,
				siteId: String(assetLibrary.siteId),
				tags,
				title,
			});

			return title;
		};

		const taggedWebContentTitle = await addWebContent([tag]);
		const webContentTitle = await addWebContent([]);

		const collectionsPage = new CollectionsPage(page);

		const addDynamicCollection = async (
			itemType: string,
			itemSubtype: string
		) => {
			const name = getRandomString();

			await collectionsPage.goto(depotURL);

			await collectionsPage.addNewDynamicCollection(name);

			await collectionsPage.configureSourceItemType({
				itemSubtype,
				itemType,
			});

			return name;
		};

		const imageCollectionName = await addDynamicCollection(
			'Document',
			'Basic Document'
		);

		await collectionsPage.save();

		const taggedCollectionName = await addDynamicCollection(
			'Web Content Article',
			'All Subtypes'
		);

		await collectionsPage.openFilterSection();

		await collectionsPage.fillFilterCondition(0, {
			field: 'Tags',
			operator: 'Contains',
			quantifier: 'Any of the Following',
		});

		const tagsInput = collectionsPage
			.getFilterConditionRow(0)
			.getByRole('combobox', {name: 'Tags'});

		await tagsInput.fill(tag);
		await tagsInput.press('Enter');

		// Close the suggestions, which cover the Save button

		await tagsInput.press('Escape');

		await collectionsPage.save();

		const assetPublisher = page.locator(
			'[id^="portlet_com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet"]'
		);

		const layout = await new AssetPublisherWidgetPage(
			page
		).addAssetPublisherPortlet(site);

		const layoutURL = `/web${site.friendlyUrlPath}${layout.friendlyURL}`;

		await test.step('A depot document collection keeps its image after a move', async () => {
			await selectCollectionInAssetPublisher(
				page,
				imageCollectionName,
				assetLibrary.name
			);

			await expect(assetPublisher.getByText(imageTitle)).toBeVisible();

			const folder = await apiHelpers.headlessDelivery.postDocumentFolder(
				assetLibrary.siteId
			);

			await apiHelpers.headlessDelivery.patchDocument({
				document: {documentFolderId: folder.id},
				documentId: image.id,
			});

			await page.reload();

			await expect(assetPublisher.getByText(imageTitle)).toBeVisible();
		});

		await test.step('A depot manual collection shows its image', async () => {
			await collectionsPage.goto(depotURL);

			const name = getRandomString();

			await collectionsPage.addNewManualCollection(name);

			await collectionsPage.configureSourceItemType({
				itemSubtype: 'Basic Document',
				itemType: 'Document',
			});

			await collectionsPage.save();

			await collectionsPage.selectAssets([imageTitle]);

			await page.goto(layoutURL);

			await selectCollectionInAssetPublisher(
				page,
				name,
				assetLibrary.name
			);

			await expect(assetPublisher.getByText(imageTitle)).toBeVisible();
		});

		await test.step('A site manual collection shows a depot web content', async () => {
			await collectionsPage.goto(site.friendlyUrlPath);

			const name = getRandomString();

			await collectionsPage.addNewManualCollection(name);

			await collectionsPage.configureSourceItemType({
				itemSubtype: 'Basic Web Content',
				itemType: 'Web Content Article',
			});

			await collectionsPage.save();

			await collectionsPage.selectAssets([webContentTitle]);

			await page.goto(layoutURL);

			await selectCollectionInAssetPublisher(page, name);

			await expect(
				assetPublisher.getByText(webContentTitle)
			).toBeVisible();
		});

		await test.step('A depot web content collection filtered by a tag', async () => {
			await selectCollectionInAssetPublisher(
				page,
				taggedCollectionName,
				assetLibrary.name
			);

			await expect(
				assetPublisher.getByText(taggedWebContentTitle)
			).toBeVisible();
			await expect(assetPublisher.getByText(webContentTitle)).toHaveCount(
				0
			);
		});
	}
);

test(
	'A depot dynamic collection filters by type, keyword and scope',
	{tag: '@LPD-109574'},
	async ({apiHelpers, page, site}) => {
		const assetLibrary =
			await apiHelpers.headlessAssetLibrary.createAssetLibrary({
				name: getRandomString(),
				type: 'AssetLibrary',
			});

		const basicWebContentStructureId =
			await getBasicWebContentStructureId(apiHelpers);

		const addWebContent = async (groupId: number | string) => {
			const title = getRandomString();

			await apiHelpers.jsonWebServicesJournal.addWebContent({
				ddmStructureId: basicWebContentStructureId,
				groupId,
				titleMap: {en_US: title},
			});

			return title;
		};

		const depotWebContentTitle = await addWebContent(assetLibrary.siteId);
		const siteWebContentTitle = await addWebContent(site.id);

		const addDocument = async (fileName: string, title: string) => {
			await apiHelpers.headlessDelivery.postAssetLibraryDocument(
				assetLibrary.id,
				createReadStream(
					path.join(__dirname, 'dependencies', fileName)
				),
				{fileName: `${title}${path.extname(fileName)}`, title}
			);

			return title;
		};

		const keyword = 'Excluded';

		const imageTitle = await addDocument(
			'Document.jpg',
			`${getRandomString()} ${keyword}`
		);
		const pdfTitle = await addDocument('Document.pdf', getRandomString());

		const collectionsPage = new CollectionsPage(page);

		const depotURL = `/asset-library-${assetLibrary.id}`;

		const addDynamicCollection = async (itemType: string) => {
			const name = getRandomString();

			await collectionsPage.goto(depotURL);

			await collectionsPage.addNewDynamicCollection(name);

			await collectionsPage.configureSourceItemType({
				itemSubtype: 'All Subtypes',
				itemType,
			});

			return name;
		};

		const assertItems = async (
			name: string,
			titles: string[],
			absentTitles: string[]
		) => {

			// The items of a dynamic collection come from the index

			await expect(async () => {
				await collectionsPage.goto(depotURL);

				await collectionsPage.openCollection(name);

				const viewItems = await collectionsPage.openViewItems();

				for (const title of titles) {
					await expect(viewItems.getByText(title)).toBeVisible({
						timeout: 2000,
					});
				}

				for (const title of absentTitles) {
					await expect(viewItems.getByText(title)).toHaveCount(0, {
						timeout: 2000,
					});
				}
			}).toPass();
		};

		await test.step('Only web content of the chosen type', async () => {
			const name = await addDynamicCollection('Web Content Article');

			await collectionsPage.save();

			await assertItems(
				name,
				[depotWebContentTitle],
				[imageTitle, pdfTitle, siteWebContentTitle]
			);
		});

		await test.step('Documents that do not contain a keyword', async () => {
			const name = await addDynamicCollection('Document');

			await collectionsPage.addFilterConditions([
				{
					field: 'Keywords',
					operator: 'Does Not Contain',
					quantifier: 'Any of the Following',
					value: keyword,
				},
			]);

			await collectionsPage.save();

			await assertItems(name, [pdfTitle], [imageTitle]);
		});

		await test.step('Web content of another scope only', async () => {
			const name = await addDynamicCollection('Web Content Article');

			await collectionsPage.save();

			await collectionsPage.goto(depotURL);

			await collectionsPage.openCollection(name);

			await collectionsPage.scopeToSite(site.name);

			const getScopeRow = (scopeName: string) =>
				page.getByRole('row').filter({hasText: scopeName});

			await expect(getScopeRow(site.name)).toBeVisible();

			await assertItems(
				name,
				[depotWebContentTitle, siteWebContentTitle],
				[]
			);

			const removeScope = async (scopeName: string) => {
				await collectionsPage.goto(depotURL);

				await collectionsPage.openCollection(name);

				await page
					.getByRole('button', {exact: true, name: 'Scope'})
					.click();

				await getScopeRow(scopeName)
					.getByRole('button', {name: 'Remove'})
					.click();

				await expect(getScopeRow(scopeName)).toHaveCount(0);
			};

			await removeScope('Current Asset Library');

			await assertItems(
				name,
				[siteWebContentTitle],
				[depotWebContentTitle]
			);

			await collectionsPage.goto(depotURL);

			await collectionsPage.openCollection(name);

			await collectionsPage.scopeToCurrentGroup();

			await expect(getScopeRow('Current Asset Library')).toBeVisible();

			await removeScope(site.name);

			await assertItems(
				name,
				[depotWebContentTitle],
				[siteWebContentTitle]
			);
		});
	}
);
