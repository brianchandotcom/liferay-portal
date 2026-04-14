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
import getRandomString from '../../../utils/getRandomString';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import { generateObjectEntryValues } from '../utils/generateObjectEntry';
import {generateObjectFields} from '../utils/generateObjectFields';

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
	'Can display entries on table format in collection display',
	{tag: '@LPS-135386'},
	async ({apiHelpers, page, pageEditorPage, site}) => {
		// Corresponds to Poshi test: CanDisplayEntriesOnTableFormat

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
					collectionTable.getByText(objectEntry[objectFields[0].name])
				).toBeVisible();
			}
		});
	}
);

test(
	'Object is displayed to be selected as collection provider on collection display fragment',
	{tag: '@LPS-133865'},
	async ({apiHelpers, page, pageEditorPage, site}) => {
		// Corresponds to Poshi test: ObjectDisplayedToCollectionProdiver

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
