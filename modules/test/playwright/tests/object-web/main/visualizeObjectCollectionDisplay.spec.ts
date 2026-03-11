/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import {generateObjectFields} from './utils/generateObjectFields';

const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest,
	pageEditorPagesTest,
	pagesAdminPagesTest
);

test(
	'LPD-78504 Can display entries on table format in collection display',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, pageEditorPage, site}) => {
		// Corresponds to Poshi test: CanDisplayEntriesOnTableFormat

		// Create object with Text fields

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

		const field1Name = objectFields[0].name;
		const field2Name = objectFields[1].name;
		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		// Create object entries

		const entries = [];

		for (let i = 0; i < 3; i++) {
			const entry = await apiHelpers.objectEntry.postObjectEntry(
				{
					[field1Name]: `Field1_Entry${i}_${getRandomInt()}`,
					[field2Name]: `Field2_Entry${i}_${getRandomInt()}`,
				},
				applicationName
			);

			entries.push(entry);
		}

		// Create a content page

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition(),
			siteId: site.id,
			title: getRandomString(),
		});

		// Go to edit mode and add Collection Display

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await pageEditorPage.addFragment(
			'Content Display',
			'Collection Display'
		);

		// Select Collection Display fragment and choose the object as provider

		await pageEditorPage.selectFragment(
			await pageEditorPage.getFragmentId('Collection Display')
		);

		await pageEditorPage.chooseCollectionDisplayCollection(
			'Collection Providers',
			objectDefinition.label['en_US'],
			{search: true}
		);

		await pageEditorPage.waitForChangesSaved();

		// Change Style Display to Table

		const collectionId =
			await pageEditorPage.getFragmentId('Collection Display');

		await pageEditorPage.selectFragment(collectionId);

		await pageEditorPage.changeConfiguration({
			fieldLabel: 'Style Display',
			tab: 'General',
			value: 'Table',
		});

		await pageEditorPage.waitForChangesSaved();

		// Verify that entries are displayed in table format

		await expect(
			page.locator(
				'.lfr-layout-structure-item-collection table, .table'
			).first()
		).toBeVisible();

		// Publish the page

		await pageEditorPage.publishPage();

		// Navigate to view mode and verify table is displayed

		await page.goto(
			`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
		);

		await expect(
			page.locator(
				'.lfr-layout-structure-item-collection table, .table'
			).first()
		).toBeVisible();
	}
);

test(
	'LPD-78504 Object is displayed to be selected as collection provider on collection display fragment',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, pageEditorPage, site}) => {
		// Corresponds to Poshi test: ObjectDisplayedToCollectionProdiver

		// Create a published object definition

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

		// Create a content page

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition(),
			siteId: site.id,
			title: getRandomString(),
		});

		// Go to edit mode and add Collection Display

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await pageEditorPage.addFragment(
			'Content Display',
			'Collection Display'
		);

		// Select Collection Display fragment

		await pageEditorPage.selectFragment(
			await pageEditorPage.getFragmentId('Collection Display')
		);

		// Open the collection selection modal

		await page
			.getByLabel('Select Collection', {exact: true})
			.click();

		const iframe = page.frameLocator('iframe[title="Select"]');

		// Navigate to Collection Providers tab

		await iframe.getByRole('link', {name: 'Collection Providers'}).click();

		// Search for the object by name

		await expect(async () => {
			await iframe
				.getByPlaceholder('Search for')
				.fill(objectDefinition.label['en_US']);

			await expect(
				iframe.getByPlaceholder('Search for')
			).toHaveValue(objectDefinition.label['en_US']);
		}).toPass();

		await iframe
			.getByLabel('Search for', {exact: true})
			.click();

		// Verify the object appears in the list of collection providers

		await expect(
			iframe.getByRole('button', {
				name: `Select ${objectDefinition.label['en_US']}`,
			})
		).toBeVisible();

		// Close the modal

		await page.keyboard.press('Escape');
	}
);
