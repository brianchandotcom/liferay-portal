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

test(
	'LPD-78504 Can define fixed filter for picklist type on display page',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		displayPageTemplatesPage,
		page,
		pageEditorPage,
		site,
	}) => {
		// Corresponds to Poshi test: CanDefineFixedFilterForPicklistType

		// Create picklist with entries

		const {listTypeDefinition, listTypeEntries} =
			await postListTypeDefinitionListTypeEntries({
				apiHelpers,
				listTypeEntriesLength: 3,
			});

		const picklistEntryNames = listTypeEntries.map(
			(entry) => entry.name_i18n.en_US
		);

		// Create object with Picklist and Text fields

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

		// Create entries with different picklist values

		const targetPicklistValue = picklistEntryNames[0];

		for (let i = 0; i < picklistEntryNames.length; i++) {
			await apiHelpers.objectEntry.postObjectEntry(
				{
					[picklistFieldName]: {key: picklistEntryNames[i]},
					[textFieldName]: `Entry${i}_${getRandomInt()}`,
				},
				applicationName
			);
		}

		// Create a display page template for the object

		const objectClassName = objectDefinition.className;
		const className =
			await apiHelpers.jsonWebServicesClassName.fetchClassName(
				objectClassName
			);

		const displayPageTemplateName = 'DPT_' + getRandomString();

		await apiHelpers.jsonWebServicesLayoutPageTemplateEntry.addDisplayPageLayoutPageTemplateEntry(
			{
				classNameId: className.classNameId,
				groupId: site.id,
				name: displayPageTemplateName,
			}
		);

		// Edit display page and add Collection Display fragment

		await displayPageTemplatesPage.goto(site.friendlyUrlPath);

		await displayPageTemplatesPage.editTemplate(displayPageTemplateName);

		await pageEditorPage.addFragment(
			'Content Display',
			'Collection Display'
		);

		// Select the Collection Display and choose the object as collection provider

		await pageEditorPage.selectFragment(
			await pageEditorPage.getFragmentId('Collection Display')
		);

		await pageEditorPage.chooseCollectionDisplayCollection(
			'Collection Providers',
			objectDefinition.label['en_US'],
			{search: true}
		);

		await pageEditorPage.waitForChangesSaved();

		// Add Heading fragment inside collection display

		await pageEditorPage.addFragment(
			'Basic Components',
			'Heading',
			page.locator('.page-editor__collection-item.empty').first()
		);

		// Configure fixed filter on the picklist field via Filter Collection

		const collectionId =
			await pageEditorPage.getFragmentId('Collection Display');

		await pageEditorPage.selectFragment(collectionId);

		// Open filter collection options

		await page.getByTitle('View Collection Options').click();

		await page
			.getByRole('menuitem', {name: 'Filter Collection'})
			.click();

		// Select the picklist field filter and set value

		await page.getByLabel('Item Type', {exact: true}).click();

		await page
			.getByLabel(objectDefinition.label['en_US'])
			.check();

		await page.locator('body').click();

		// Set the filter for the picklist field with the target value

		await page.getByLabel('Filter', {exact: true}).click();

		await page
			.getByRole('option', {name: objectFields[0].label['en_US']})
			.click();

		await page.getByLabel('Value', {exact: true}).click();

		await page
			.getByRole('option', {name: targetPicklistValue})
			.click();

		await page.getByRole('button', {name: 'Save'}).click();

		await pageEditorPage.waitForChangesSaved();

		// Publish the display page template

		await displayPageTemplatesPage.publishTemplate();

		// Verify that the collection display has entries (at least the filtered one)

		await displayPageTemplatesPage.goto(site.friendlyUrlPath);

		await displayPageTemplatesPage.editTemplate(displayPageTemplateName);

		await expect(
			page.locator('.page-editor__collection-item')
		).toHaveCount(1);
	}
);

test(
	'LPD-78504 Can set pagination as numeric on display page',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		displayPageTemplatesPage,
		page,
		pageEditorPage,
		site,
	}) => {
		// Corresponds to Poshi test: CanSetPaginationNumeric

		// Create object with Text field

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

		// Create enough entries to trigger pagination

		for (let i = 0; i < 25; i++) {
			await apiHelpers.objectEntry.postObjectEntry(
				{[textFieldName]: `Entry_${i}_${getRandomInt()}`},
				applicationName
			);
		}

		// Create a display page template for the object

		const objectClassName = objectDefinition.className;
		const className =
			await apiHelpers.jsonWebServicesClassName.fetchClassName(
				objectClassName
			);

		const displayPageTemplateName = 'DPT_' + getRandomString();

		await apiHelpers.jsonWebServicesLayoutPageTemplateEntry.addDisplayPageLayoutPageTemplateEntry(
			{
				classNameId: className.classNameId,
				groupId: site.id,
				name: displayPageTemplateName,
			}
		);

		// Edit display page and add Collection Display

		await displayPageTemplatesPage.goto(site.friendlyUrlPath);

		await displayPageTemplatesPage.editTemplate(displayPageTemplateName);

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

		// Add Heading fragment inside collection display

		await pageEditorPage.addFragment(
			'Basic Components',
			'Heading',
			page.locator('.page-editor__collection-item.empty').first()
		);

		// Select collection display and change pagination to Numeric

		const collectionId =
			await pageEditorPage.getFragmentId('Collection Display');

		await pageEditorPage.selectFragment(collectionId);

		// Set max items per page to a low number so pagination appears

		await pageEditorPage.changeConfiguration({
			fieldLabel: 'Maximum Number of Items per Page',
			tab: 'General',
			value: '5',
		});

		await pageEditorPage.changeConfiguration({
			fieldLabel: 'Pagination',
			tab: 'General',
			value: 'numeric',
		});

		// Verify that numeric pagination is displayed

		const collectionStyle = page.getByLabel('CollectionStyle');

		await expect(collectionStyle.getByLabel('Pagination')).toHaveValue(
			'numeric'
		);

		await expect(page.locator('.pagination-bar')).toBeVisible();

		await expect(page.getByLabel('Go to page, 1')).toBeVisible();
	}
);

test(
	'LPD-78504 Can set pagination as simple on display page',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		displayPageTemplatesPage,
		page,
		pageEditorPage,
		site,
	}) => {
		// Corresponds to Poshi test: CanSetPaginationSimple

		// Create object with Text field

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

		// Create enough entries to trigger pagination

		for (let i = 0; i < 25; i++) {
			await apiHelpers.objectEntry.postObjectEntry(
				{[textFieldName]: `Entry_${i}_${getRandomInt()}`},
				applicationName
			);
		}

		// Create a display page template for the object

		const objectClassName = objectDefinition.className;
		const className =
			await apiHelpers.jsonWebServicesClassName.fetchClassName(
				objectClassName
			);

		const displayPageTemplateName = 'DPT_' + getRandomString();

		await apiHelpers.jsonWebServicesLayoutPageTemplateEntry.addDisplayPageLayoutPageTemplateEntry(
			{
				classNameId: className.classNameId,
				groupId: site.id,
				name: displayPageTemplateName,
			}
		);

		// Edit display page and add Collection Display

		await displayPageTemplatesPage.goto(site.friendlyUrlPath);

		await displayPageTemplatesPage.editTemplate(displayPageTemplateName);

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

		// Add Heading fragment inside collection display

		await pageEditorPage.addFragment(
			'Basic Components',
			'Heading',
			page.locator('.page-editor__collection-item.empty').first()
		);

		// Select collection display and change pagination to Simple

		const collectionId =
			await pageEditorPage.getFragmentId('Collection Display');

		await pageEditorPage.selectFragment(collectionId);

		// Set max items per page to a low number so pagination appears

		await pageEditorPage.changeConfiguration({
			fieldLabel: 'Maximum Number of Items per Page',
			tab: 'General',
			value: '5',
		});

		await pageEditorPage.changeConfiguration({
			fieldLabel: 'Pagination',
			tab: 'General',
			value: 'simple',
		});

		// Verify that simple pagination is displayed

		const collectionStyle = page.getByLabel('CollectionStyle');

		await expect(collectionStyle.getByLabel('Pagination')).toHaveValue(
			'simple'
		);

		await expect(page.locator('.pagination-bar')).toBeVisible();

		// Simple pagination shows Previous/Next instead of page numbers

		await expect(
			page.getByRole('link', {name: 'Next'}).or(
				page.getByLabel('Next Page')
			)
		).toBeVisible();
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

		// Create object with Text field

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

		// Create an object entry

		const entryValue = 'TestEntry_' + getRandomInt();

		const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
			{[textFieldName]: entryValue},
			applicationName
		);

		// Create a display page template for the object

		const objectClassName = objectDefinition.className;
		const className =
			await apiHelpers.jsonWebServicesClassName.fetchClassName(
				objectClassName
			);

		const displayPageTemplateName = 'DPT_' + getRandomString();

		const displayPage =
			await apiHelpers.jsonWebServicesLayoutPageTemplateEntry.addDisplayPageLayoutPageTemplateEntry(
				{
					classNameId: className.classNameId,
					groupId: site.id,
					name: displayPageTemplateName,
				}
			);

		// Mark as default display page

		await apiHelpers.jsonWebServicesLayoutPageTemplateEntry.markAsDefaultDisplayPageLayoutPageTemplateEntry(
			{
				layoutPageTemplateEntryId:
					displayPage.layoutPageTemplateEntryId,
			}
		);

		// Edit display page and add Heading fragment mapped to text field

		await displayPageTemplatesPage.goto(site.friendlyUrlPath);

		await displayPageTemplatesPage.editTemplate(displayPageTemplateName);

		await pageEditorPage.addFragment('Basic Components', 'Heading');

		await page.getByText('Heading Example', {exact: true}).click();

		await pageEditorPage.setMappingConfiguration({
			mapping: {
				field: objectFields[0].label['en_US'],
			},
			source: 'structure',
		});

		await displayPageTemplatesPage.publishTemplate();

		// Navigate to the display page for the object entry

		await page.goto(
			`/web${site.friendlyUrlPath}/e/${displayPageTemplateName}/${className.classNameId}/${objectEntry.id}`,
			{waitUntil: 'networkidle'}
		);

		// Verify the entry text is visible

		await expect(page.getByText(entryValue)).toBeVisible();

		// Verify the user profile image (avatar) is visible on the page

		await expect(
			page.locator(
				'img.user-icon-initials, img[alt*="User"], .user-icon, .sticker-user-icon, .lexicon-icon-user'
			).first()
		).toBeVisible();
	}
);
