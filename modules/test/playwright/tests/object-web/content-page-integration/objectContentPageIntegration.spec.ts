/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import {createReadStream} from 'fs';
import path from 'path';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {displayPageTemplatesPagesTest} from '../../../fixtures/displayPageTemplatesPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pageViewModePagesTest} from '../../../fixtures/pageViewModePagesTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import createTempFile from '../../../utils/createTempFile';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
import getFragmentDefinition from '../../layout-content-page-editor-web/main/utils/getFragmentDefinition';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import {generateObjectEntryValues} from '../utils/generateObjectEntry';
import {generateObjectFields} from '../utils/generateObjectFields';
import {postListTypeDefinitionListTypeEntries} from '../utils/postListTypeDefinitionListTypeEntries';

const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	displayPageTemplatesPagesTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest,
	pageEditorPagesTest,
	pageViewModePagesTest,
	pagesAdminPagesTest
);

test.describe('Object Widget', () => {
	test(
		'Can add object portlet as a widget on a page',
		{tag: '@LPS-143122'},
		async ({
			apiHelpers,
			editObjectDetailsPage,
			page,
			site,
			widgetPagePage,
		}) => {
			const objectFields = generateObjectFields({
				objectFieldBusinessTypes: ['Text'],
			});

			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields,
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await test.step('Enable "Show Widget in Page Builder" and save', async () => {
				await editObjectDetailsPage.goto(
					objectDefinition.label['en_US']
				);

				await editObjectDetailsPage.goToDetailsTab();

				await expect(
					editObjectDetailsPage.showWidgetToggle
				).toBeChecked();

				await editObjectDetailsPage.saveObjectDefinition();

				await waitForAlert(page, 'Success:');
			});

			await test.step('Create a widget page and add the object portlet', async () => {
				const layout = await apiHelpers.jsonWebServicesLayout.addLayout(
					{
						groupId: site.id,
						title: getRandomString(),
					}
				);

				await page.goto(
					`/web${site.friendlyUrlPath}${layout.friendlyURL}`
				);

				await widgetPagePage.addPortlet(
					objectDefinition.pluralLabel['en_US']
				);
			});

			await test.step('Verify the portlet is displayed with empty state', async () => {
				await expect(
					page
						.getByText(objectDefinition.pluralLabel['en_US'])
						.first()
				).toBeVisible();

				await expect(page.getByText('No Results Found')).toBeVisible();
			});
		}
	);

	test(
		'Cannot add object portlet as widget when widget button is disabled',
		{tag: '@LPS-143122'},
		async ({
			apiHelpers,
			editObjectDetailsPage,
			page,
			site,
			widgetPagePage,
		}) => {
			const objectFields = generateObjectFields({
				objectFieldBusinessTypes: ['Text'],
			});

			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields,
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await test.step('Navigate to object details and untoggle show widget', async () => {
				await editObjectDetailsPage.goto(
					objectDefinition.label['en_US']
				);

				await editObjectDetailsPage.goToDetailsTab();

				await editObjectDetailsPage.showWidgetToggle.uncheck();

				await editObjectDetailsPage.saveObjectDefinition();

				await waitForAlert(page, 'Success:');
			});

			await test.step('Search for the object portlet on a widget page and verify it is not available', async () => {
				const layout = await apiHelpers.jsonWebServicesLayout.addLayout(
					{
						groupId: site.id,
						title: getRandomString(),
					}
				);

				await page.goto(
					`/web${site.friendlyUrlPath}${layout.friendlyURL}`
				);

				await widgetPagePage.openAddPanel();

				await page.getByLabel('Widgets', {exact: true}).click();

				await page
					.getByRole('textbox', {name: 'Search Form'})
					.fill(objectDefinition.pluralLabel['en_US']);

				await expect(
					page
						.getByRole('alert')
						.getByText('There are no widgets on this page')
				).toBeVisible();
			});
		}
	);

	test(
		'Object portlet widget disappears from page when widget button is disabled',
		{tag: '@LPS-143122'},
		async ({
			apiHelpers,
			editObjectDetailsPage,
			page,
			site,
			widgetPagePage,
		}) => {
			const objectFields = generateObjectFields({
				objectFieldBusinessTypes: ['Text'],
			});

			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields,
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			let layout: Layout;

			await test.step('Create a widget page and add the object portlet', async () => {
				layout = await apiHelpers.jsonWebServicesLayout.addLayout({
					groupId: site.id,
					title: getRandomString(),
				});

				await page.goto(
					`/web${site.friendlyUrlPath}${layout.friendlyURL}`
				);

				await widgetPagePage.addPortlet(
					objectDefinition.pluralLabel['en_US']
				);
			});

			await test.step('Disable "Show Widget in Page Builder" and save', async () => {
				await editObjectDetailsPage.goto(
					objectDefinition.label['en_US']
				);

				await editObjectDetailsPage.goToDetailsTab();

				await editObjectDetailsPage.showWidgetToggle.uncheck();

				await editObjectDetailsPage.saveObjectDefinition();

				await waitForAlert(page, 'Success:');
			});

			await test.step('Navigate back to widget page and verify warning message', async () => {
				await page.goto(
					`/web${site.friendlyUrlPath}${layout.friendlyURL}`
				);

				await expect(
					page.getByText('This object is not available.')
				).toBeVisible();
			});
		}
	);
});

test.describe('Content Pages Mapping', () => {
	test(
		'Can map preview URL of image attachment to fragment on content page',
		{tag: '@LPS-182999'},
		async ({apiHelpers, page, pageEditorPage, site}) => {
			const objectFields = generateObjectFields({
				objectFieldBusinessTypes: ['Attachment'],
			});

			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields,
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			const document = await apiHelpers.headlessDelivery.postDocument(
				site.id,
				createReadStream(
					path.join(__dirname, '..', 'dependencies', 'astronaut.png')
				),
				{fileName: 'astronaut.png', title: getRandomString()}
			);

			const attachmentFieldName = objectFields[0].name!;
			const applicationName =
				'c/' + objectDefinition.name!.toLowerCase() + 's';

			const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
				{[attachmentFieldName]: document.id},
				applicationName
			);

			const imageId = getRandomString();

			const imageFragment = getFragmentDefinition({
				id: imageId,
				key: 'BASIC_COMPONENT-image',
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([imageFragment]),
				siteId: site.id,
				title: getRandomString(),
			});

			await test.step('Map Image fragment to object entry Preview URL', async () => {
				await pageEditorPage.goto(layout, site.friendlyUrlPath);

				await pageEditorPage.selectEditable(imageId, 'image-square');

				await page
					.getByLabel('Source Selection')
					.selectOption('Mapping');

				await pageEditorPage.setMappedItem({
					entity: objectDefinition.label['en_US'],
					entry: objectEntry.id.toString(),
					entryLocator: page
						.frameLocator('iframe[title="Select"]')
						.getByText(objectEntry.id.toString())
						.first(),
					field: 'Preview URL',
				});

				await pageEditorPage.waitForChangesSaved();
			});

			await test.step('Verify mapped image is shown in editor', async () => {
				await expect(
					page.locator('.component-image img')
				).toBeVisible();
			});

			await test.step('Publish and verify image in view mode', async () => {
				await pageEditorPage.publishPage();

				await page.goto(
					`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
				);

				await expect(
					page.locator('.component-image img')
				).toBeVisible();
			});
		}
	);

	test(
		'Can map preview URL of non-image attachment to fragment showing blank space',
		{tag: '@LPS-182999'},
		async ({apiHelpers, page, pageEditorPage, site}) => {
			const objectFields = generateObjectFields({
				objectFieldBusinessTypes: ['Attachment'],
			});

			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields,
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			const pdfPath = createTempFile(
				'test-document.pdf',
				'%PDF-1.0 dummy content'
			);

			const document = await apiHelpers.headlessDelivery.postDocument(
				site.id,
				createReadStream(pdfPath),
				{fileName: 'test-document.pdf', title: getRandomString()}
			);

			const attachmentFieldName = objectFields[0].name!;
			const applicationName =
				'c/' + objectDefinition.name!.toLowerCase() + 's';

			const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
				{[attachmentFieldName]: document.id},
				applicationName
			);

			const imageId = getRandomString();

			const imageFragment = getFragmentDefinition({
				id: imageId,
				key: 'BASIC_COMPONENT-image',
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([imageFragment]),
				siteId: site.id,
				title: getRandomString(),
			});

			await test.step('Map Image fragment to object entry Preview URL', async () => {
				await pageEditorPage.goto(layout, site.friendlyUrlPath);

				await pageEditorPage.selectEditable(imageId, 'image-square');

				await page
					.getByLabel('Source Selection')
					.selectOption('Mapping');

				await pageEditorPage.setMappedItem({
					entity: objectDefinition.label['en_US'],
					entry: objectEntry.id.toString(),
					entryLocator: page
						.frameLocator('iframe[title="Select"]')
						.getByText(objectEntry.id.toString())
						.first(),
					field: 'Preview URL',
				});

				await pageEditorPage.waitForChangesSaved();
			});

			await test.step('Publish and verify non-image shows blank space in view mode', async () => {
				await pageEditorPage.publishPage();

				await page.goto(
					`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
				);

				await expect
					.poll(async () =>
						page
							.locator('.component-image img')
							.evaluate(
								(image: HTMLImageElement) => image.naturalWidth
							)
					)
					.toBe(0);
			});
		}
	);

	test(
		'Can view image user profile from specific entry on display page',
		{tag: '@LPD-86436'},
		async ({apiHelpers, page, pageEditorPage, site}) => {
			const objectFields = generateObjectFields({
				objectFieldBusinessTypes: ['Text'],
			});

			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields,
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			const applicationName =
				'c/' + objectDefinition.name!.toLowerCase() + 's';
			const textFieldName = objectFields[0].name;

			const entryValue = 'TestEntry_' + getRandomInt();

			const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
				{[textFieldName]: entryValue},
				applicationName
			);

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition(),
				siteId: site.id,
				title: getRandomString(),
			});

			await test.step('Add Image fragment and map to User Profile Image', async () => {
				await pageEditorPage.goto(layout, site.friendlyUrlPath);

				await pageEditorPage.addFragment('Basic Components', 'Image');

				const imageId = await pageEditorPage.getFragmentId('Image');

				await pageEditorPage.selectEditable(imageId, 'image-square');

				await page
					.getByLabel('Source Selection')
					.selectOption('Mapping');

				await pageEditorPage.setMappedItem({
					entity: objectDefinition.label['en_US'],
					entry: objectEntry.id.toString(),
					entryLocator: page
						.frameLocator('iframe[title="Select"]')
						.getByText(objectEntry.id.toString())
						.first(),
					field: 'User Profile Image',
				});

				await pageEditorPage.waitForChangesSaved();
			});

			await test.step('Publish and verify user profile image is visible', async () => {
				await pageEditorPage.publishPage();

				await page.goto(
					`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
				);

				await expect(
					page.locator('.component-image img')
				).toBeVisible();
			});
		}
	);
});

test.describe('Collection Display', () => {
	test(
		'Can display entries on table format in collection display',
		{tag: '@LPS-135386'},
		async ({apiHelpers, page, pageEditorPage, site}) => {
			const objectFields = generateObjectFields({
				objectFieldBusinessTypes: ['Text', 'Text'],
			});

			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields,
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			const objectEntries = [];

			for (let i = 0; i < 2; i++) {
				const {objectEntry} = await generateObjectEntryValues({
					objectEntryFormat: 'API',
					objectFields,
				});
				objectEntries.push(objectEntry);
			}

			await apiHelpers.objectEntry.postObjectEntriesBatch(
				'c/' + objectDefinition.name.toLowerCase() + 's',
				objectEntries
			);

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition(),
				siteId: site.id,
				title: getRandomString(),
			});

			await test.step('Add Collection Display with object as provider and Table style', async () => {
				await pageEditorPage.goto(layout, site.friendlyUrlPath);

				await pageEditorPage.addFragment(
					'Content Display',
					'Collection Display'
				);

				await pageEditorPage.selectFragment(
					await pageEditorPage.getFragmentId('Collection Display')
				);

				await pageEditorPage.chooseCollectionDisplayCollection(
					'Collection Providers',
					objectDefinition.label['en_US'],
					{search: true}
				);

				await pageEditorPage.waitForChangesSaved();

				const collectionId =
					await pageEditorPage.getFragmentId('Collection Display');

				await pageEditorPage.selectFragment(collectionId);

				await pageEditorPage.changeConfiguration({
					fieldLabel: 'Style Display',
					tab: 'General',
					value: 'Table',
				});

				await pageEditorPage.waitForChangesSaved();
			});

			await test.step('Publish and verify entries in view mode', async () => {
				await pageEditorPage.publishPage();

				await page.goto(
					`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
				);

				const collectionTable = page.getByRole('table');

				for (const objectEntry of objectEntries) {
					await expect(
						collectionTable.getByText(
							objectEntry[objectFields[0].name]
						)
					).toBeVisible();
				}
			});
		}
	);

	test(
		'Can search for object entry on search experience in collection providers',
		{tag: '@LPS-135388'},
		async ({apiHelpers, page, pageEditorPage, site}) => {
			const objectFields = generateObjectFields({
				objectFieldBusinessTypes: [
					{businessType: 'Text', indexed: true},
				],
			});

			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields,
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			const applicationName =
				'c/' + objectDefinition.name!.toLowerCase() + 's';
			const fieldName = objectFields[0].name!;

			const entryValueA = 'Alpha' + getRandomString();
			const entryValueB = 'Beta' + getRandomString();

			for (const value of [entryValueA, entryValueB]) {
				await apiHelpers.objectEntry.postObjectEntry(
					{[fieldName]: value},
					applicationName
				);
			}

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition(),
				siteId: site.id,
				title: getRandomString(),
			});

			await test.step('Add Collection Display with object as provider and Table style', async () => {
				await pageEditorPage.goto(layout, site.friendlyUrlPath);

				await pageEditorPage.addFragment(
					'Content Display',
					'Collection Display'
				);

				await pageEditorPage.selectFragment(
					await pageEditorPage.getFragmentId('Collection Display')
				);

				await pageEditorPage.chooseCollectionDisplayCollection(
					'Collection Providers',
					objectDefinition.label['en_US'],
					{search: true}
				);

				await pageEditorPage.waitForChangesSaved();

				const collectionId =
					await pageEditorPage.getFragmentId('Collection Display');

				await pageEditorPage.selectFragment(collectionId);

				await pageEditorPage.changeConfiguration({
					fieldLabel: 'Style Display',
					tab: 'General',
					value: 'Table',
				});

				await pageEditorPage.waitForChangesSaved();
			});

			await test.step('Add Collection Filter with Keywords filter', async () => {
				await pageEditorPage.addFragment(
					'Content Display',
					'Collection Filter'
				);

				const collectionFilterId =
					await pageEditorPage.getFragmentId('Collection Filter');

				await pageEditorPage.selectFragment(collectionFilterId);

				await page.getByLabel('Select', {exact: true}).click();

				await page.getByLabel(objectDefinition.label['en_US']).check();

				await page
					.getByLabel('Filter', {exact: true})
					.selectOption('keywords');

				await pageEditorPage.waitForChangesSaved();
			});

			await test.step('Publish page and verify both entries are visible', async () => {
				await pageEditorPage.publishPage();

				await page.goto(
					`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
				);

				const collectionTable = page.locator(
					'.lfr-layout-structure-item-collection'
				);

				await expect(
					collectionTable.getByText(entryValueA)
				).toBeVisible();
				await expect(
					collectionTable.getByText(entryValueB)
				).toBeVisible();
			});

			await test.step('Search for entry A and verify entry B is filtered out', async () => {
				const collectionTable = page.locator(
					'.lfr-layout-structure-item-collection'
				);

				await expect(async () => {
					const searchInput = page.getByPlaceholder('Search', {
						exact: true,
					});

					await searchInput.fill(entryValueA, {timeout: 1000});
					await searchInput.press('Enter', {timeout: 1000});

					await page.waitForURL(/keywords/, {timeout: 3000});

					await expect(
						collectionTable.getByText(entryValueA)
					).toBeVisible({timeout: 3000});

					await expect(
						collectionTable.getByText(entryValueB)
					).not.toBeVisible({timeout: 3000});
				}).toPass();
			});
		}
	);

	test(
		'Object is displayed to be selected as collection provider on collection display fragment',
		{tag: '@LPS-133865'},
		async ({apiHelpers, page, pageEditorPage, site}) => {
			const objectFields = generateObjectFields({
				objectFieldBusinessTypes: ['Text'],
			});

			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields,
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition(),
				siteId: site.id,
				title: getRandomString(),
			});

			await test.step('Add Collection Display and select the object as provider', async () => {
				await pageEditorPage.goto(layout, site.friendlyUrlPath);

				await pageEditorPage.addFragment(
					'Content Display',
					'Collection Display'
				);

				await pageEditorPage.selectFragment(
					await pageEditorPage.getFragmentId('Collection Display')
				);

				await pageEditorPage.chooseCollectionDisplayCollection(
					'Collection Providers',
					objectDefinition.label['en_US'],
					{search: true}
				);

				await expect(
					page.getByLabel('Collection', {exact: true})
				).toHaveValue(objectDefinition.label['en_US']);
			});
		}
	);
});

test.describe('Display Page', () => {
	test(
		'Can define fixed filter for picklist type on display page',
		{tag: '@LPS-135004'},
		async ({apiHelpers, page, pageEditorPage, site}) => {
			const {listTypeDefinition, listTypeEntries} =
				await postListTypeDefinitionListTypeEntries({
					apiHelpers,
				});

			const picklistEntryNames = listTypeEntries.map(
				(entry) => entry.name
			);

			const objectFields = generateObjectFields({
				listTypeDefinitionExternalReferenceCode:
					listTypeDefinition.externalReferenceCode,
				objectFieldBusinessTypes: [
					{businessType: 'Picklist', indexed: true},
				],
			});

			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields,
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			const picklistFieldName = objectFields[0].name;
			const applicationName =
				'c/' + objectDefinition.name!.toLowerCase() + 's';

			for (let i = 0; i < picklistEntryNames.length; i++) {
				await apiHelpers.objectEntry.postObjectEntry(
					{
						[picklistFieldName]: {key: picklistEntryNames[i]},
					},
					applicationName
				);
			}

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition(),
				siteId: site.id,
				title: getRandomString(),
			});

			await test.step('Add Collection Display with object as provider in Table style', async () => {
				await pageEditorPage.goto(layout, site.friendlyUrlPath);

				await pageEditorPage.addFragment(
					'Content Display',
					'Collection Display'
				);

				await pageEditorPage.selectFragment(
					await pageEditorPage.getFragmentId('Collection Display')
				);

				await pageEditorPage.chooseCollectionDisplayCollection(
					'Collection Providers',
					objectDefinition.label['en_US'],
					{search: true}
				);

				await pageEditorPage.waitForChangesSaved();

				const collectionId =
					await pageEditorPage.getFragmentId('Collection Display');

				await pageEditorPage.selectFragment(collectionId);

				await pageEditorPage.changeConfiguration({
					fieldLabel: 'Style Display',
					tab: 'General',
					value: 'Table',
				});

				await pageEditorPage.waitForChangesSaved();
			});

			await test.step('Configure fixed filter on the picklist field', async () => {
				const collectionId =
					await pageEditorPage.getFragmentId('Collection Display');

				await pageEditorPage.selectFragment(collectionId);

				await page.getByTitle('View Collection Options').click();

				await page
					.getByRole('menuitem', {name: 'Filter Collection'})
					.click();

				await page
					.getByLabel(objectFields[0].label['en_US'], {exact: true})
					.selectOption(picklistEntryNames[0]);

				await page.getByRole('button', {name: 'Save'}).click();
			});

			await test.step('Publish and verify only filtered entry is visible', async () => {
				await pageEditorPage.publishPage();

				await page.goto(
					`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
				);

				const collectionTable = page.getByRole('table');

				await expect(
					collectionTable.getByText(picklistEntryNames[0])
				).toBeVisible();

				await expect(
					collectionTable.getByText(picklistEntryNames[1])
				).not.toBeVisible();
			});
		}
	);

	test(
		'Can set pagination as numeric on display page',
		{tag: '@LPS-135004'},
		async ({apiHelpers, page, pageEditorPage, site}) => {
			const objectFields = generateObjectFields({
				objectFieldBusinessTypes: ['Text'],
			});

			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields,
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			const objectEntries = [];

			for (let i = 0; i < 2; i++) {
				const {objectEntry} = await generateObjectEntryValues({
					objectEntryFormat: 'API',
					objectFields,
				});
				objectEntries.push(objectEntry);
			}

			await apiHelpers.objectEntry.postObjectEntriesBatch(
				'c/' + objectDefinition.name.toLowerCase() + 's',
				objectEntries
			);

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition(),
				siteId: site.id,
				title: getRandomString(),
			});

			await test.step('Add Collection Display with object as provider', async () => {
				await pageEditorPage.goto(layout, site.friendlyUrlPath);

				await pageEditorPage.addFragment(
					'Content Display',
					'Collection Display'
				);

				await pageEditorPage.selectFragment(
					await pageEditorPage.getFragmentId('Collection Display')
				);

				await pageEditorPage.chooseCollectionDisplayCollection(
					'Collection Providers',
					objectDefinition.label['en_US'],
					{search: true}
				);

				await pageEditorPage.waitForChangesSaved();

				await pageEditorPage.addFragment('Basic Components', 'Heading');
			});

			await test.step('Set pagination to Numeric and verify', async () => {
				const collectionId =
					await pageEditorPage.getFragmentId('Collection Display');

				await pageEditorPage.selectFragment(collectionId);

				await pageEditorPage.changeConfiguration({
					fieldLabel: 'Pagination',
					tab: 'General',
					value: 'numeric',
				});

				await pageEditorPage.waitForChangesSaved();

				await expect(
					page.getByRole('navigation', {name: 'Pagination'})
				).toBeVisible();

				await expect(
					page.getByText('Showing 1 to 2 of 2 entries.')
				).toBeVisible();
			});
		}
	);

	test(
		'Can set pagination as simple on display page',
		{tag: '@LPS-135004'},
		async ({apiHelpers, page, pageEditorPage, site}) => {
			const objectFields = generateObjectFields({
				objectFieldBusinessTypes: ['Text'],
			});

			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields,
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			const objectEntries = [];

			for (let i = 0; i < 2; i++) {
				const {objectEntry} = await generateObjectEntryValues({
					objectEntryFormat: 'API',
					objectFields,
				});
				objectEntries.push(objectEntry);
			}

			await apiHelpers.objectEntry.postObjectEntriesBatch(
				'c/' + objectDefinition.name.toLowerCase() + 's',
				objectEntries
			);

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition(),
				siteId: site.id,
				title: getRandomString(),
			});

			await test.step('Add Collection Display with object as provider', async () => {
				await pageEditorPage.goto(layout, site.friendlyUrlPath);

				await pageEditorPage.addFragment(
					'Content Display',
					'Collection Display'
				);

				await pageEditorPage.selectFragment(
					await pageEditorPage.getFragmentId('Collection Display')
				);

				await pageEditorPage.chooseCollectionDisplayCollection(
					'Collection Providers',
					objectDefinition.label['en_US'],
					{search: true}
				);

				await pageEditorPage.waitForChangesSaved();

				await pageEditorPage.addFragment('Basic Components', 'Heading');
			});

			await test.step('Set pagination to Simple and verify', async () => {
				const collectionId =
					await pageEditorPage.getFragmentId('Collection Display');

				await pageEditorPage.selectFragment(collectionId);

				await pageEditorPage.changeConfiguration({
					fieldLabel: 'Pagination',
					tab: 'General',
					value: 'simple',
				});

				await pageEditorPage.waitForChangesSaved();

				await expect(
					page.getByRole('button', {name: 'previous'})
				).toBeVisible();

				await expect(
					page.getByRole('button', {name: 'Next'})
				).toBeVisible();
			});
		}
	);
});
