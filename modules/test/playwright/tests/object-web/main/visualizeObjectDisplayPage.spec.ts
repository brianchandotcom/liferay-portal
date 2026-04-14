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
import { getRandomInt } from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import { generateObjectEntryValues } from '../utils/generateObjectEntry';
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
	pageEditorPagesTest
);

test(
	'Can define fixed filter for picklist type on display page',
	{tag: '@LPS-135004'},
	async ({apiHelpers, page, pageEditorPage, site}) => {
		// Corresponds to Poshi test: CanDefineFixedFilterForPicklistType

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
			objectFieldBusinessTypes: [{businessType: 'Picklist', indexed: true}],
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

			await pageEditorPage.addFragment(
				'Basic Components',
				'Heading',
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

			await expect(page.getByRole('navigation', { name: 'Pagination' })).toBeVisible();

			await expect(page.getByText('Showing 1 to 2 of 2 entries.')).toBeVisible();
		});
	}
);

test(
	'Can set pagination as simple on display page',
	{tag: '@LPS-135004'},
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

			await pageEditorPage.addFragment(
				'Basic Components',
				'Heading',
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

			await expect(
				page.getByRole('button', { name: 'previous' })
			).toBeVisible();

			await expect(
				page.getByRole('button', { name: 'Next' })
			).toBeVisible();
		});
	}
);

test(
	'Can view image user profile from specific entry on display page',
	{tag: '@LPD-86436'},
	async ({
		apiHelpers,
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
				page.locator('.component-image img')
			).toBeVisible();
		});
	}
);