/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {displayPageTemplatesPagesTest} from '../../../fixtures/displayPageTemplatesPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import {generateObjectFields} from './utils/generateObjectFields';
import {postListTypeDefinitionListTypeEntries} from './utils/postListTypeDefinitionListTypeEntries';

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
	pageEditorPagesTest
);

// Skip: The "Filter Collection" menu in the page editor Collection Display
// configuration is accessed via the sidebar's collection item selector ellipsis
// menu. The Poshi test (CanDefineFixedFilterForPicklistType) creates a content
// page with Collection Display, sets a fixed filter on a Picklist field so only
// entries matching "Item Test 1" are shown, and verifies "Item Test 2" is hidden.
// The sidebar interaction for "Filter Collection" needs a dedicated page object
// method to be implemented reliably.
test.skip(
	'LPD-78504 Can define fixed filter for picklist type on display page',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, pageEditorPage, site}) => {
		// Corresponds to Poshi test: CanDefineFixedFilterForPicklistType

		const {listTypeDefinition, listTypeEntries} =
			await postListTypeDefinitionListTypeEntries({
				apiHelpers,
				listTypeEntriesLength: 2,
			});

		const picklistEntryNames = listTypeEntries.map(
			(entry) => entry.name_i18n.en_US
		);

		const objectFields = generateObjectFields({
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			objectFieldBusinessTypes: ['Picklist', 'Text'],
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
		const textFieldName = objectFields[1].name;
		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		for (let i = 0; i < picklistEntryNames.length; i++) {
			await apiHelpers.objectEntry.postObjectEntry(
				{
					[picklistFieldName]: {key: picklistEntryNames[i]},
					[textFieldName]: `Entry${i}_${getRandomInt()}`,
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

			await page.getByLabel('Item Type', {exact: true}).click();

			await page
				.getByLabel(objectDefinition.label['en_US'])
				.check();

			await page.locator('body').click();

			await page.getByLabel('Filter', {exact: true}).click();

			await page
				.getByRole('option', {name: objectFields[0].label['en_US']})
				.click();

			await page.getByLabel('Value', {exact: true}).click();

			await page
				.getByRole('option', {name: picklistEntryNames[0]})
				.click();

			await page.getByRole('button', {name: 'Save'}).click();

			await pageEditorPage.waitForChangesSaved();
		});

		await test.step('Publish and verify only filtered entry is visible', async () => {
			await pageEditorPage.publishPage();

			await page.goto(
				`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			const collectionTable = page
				.locator('.lfr-layout-structure-item-collection')
				.first();

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
	'LPD-78504 Can set pagination as numeric on display page',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, pageEditorPage, site}) => {
		// Corresponds to Poshi test: CanSetPaginationNumeric

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

		const textFieldName = objectFields[0].name;
		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		for (let i = 0; i < 2; i++) {
			await apiHelpers.objectEntry.postObjectEntry(
				{[textFieldName]: `Entry_${i}_${getRandomInt()}`},
				applicationName
			);
		}

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

			await pageEditorPage.addFragment(
				'Basic Components',
				'Heading',
				page.locator('.page-editor__collection-item.empty').first()
			);
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

			const collectionStyle = page.getByLabel('CollectionStyle');

			await expect(collectionStyle.getByLabel('Pagination')).toHaveValue(
				'numeric'
			);

			await expect(page.locator('.pagination-bar')).toBeVisible();

			await expect(page.getByLabel('Go to page, 1')).toBeVisible();
		});
	}
);

test(
	'LPD-78504 Can set pagination as simple on display page',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, pageEditorPage, site}) => {
		// Corresponds to Poshi test: CanSetPaginationSimple

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

		const textFieldName = objectFields[0].name;
		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		for (let i = 0; i < 2; i++) {
			await apiHelpers.objectEntry.postObjectEntry(
				{[textFieldName]: `Entry_${i}_${getRandomInt()}`},
				applicationName
			);
		}

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

			await pageEditorPage.addFragment(
				'Basic Components',
				'Heading',
				page.locator('.page-editor__collection-item.empty').first()
			);
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

			const collectionStyle = page.getByLabel('CollectionStyle');

			await expect(collectionStyle.getByLabel('Pagination')).toHaveValue(
				'simple'
			);

			await expect(
				page.getByText('Previous').or(page.getByText('Prev')).first()
			).toBeVisible();

			await expect(page.getByText('Next').first()).toBeVisible();
		});
	}
);

test(
	'LPD-78504 Can view image user profile from specific entry on display page',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		displayPageTemplatesPage,
		page,
		pageEditorPage,
		site,
	}) => {
		// Corresponds to Poshi test: ViewImageUserProfileFromSpecificEntry

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

		const textFieldName = objectFields[0].name;
		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

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

			await page.getByLabel('Source Selection').selectOption('Mapping');

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
				page.locator(
					'[data-lfr-editable-id="image-square"] img, img[data-lfr-editable-id="image-square"]'
				)
			).toBeVisible();
		});
	}
);
