/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import getRandomString from '../../../utils/getRandomString';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import { generateObjectFields } from '../utils/generateObjectFields';

const test = mergeTests(
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
	'Can search for object entry on search experience in collection providers',
	{tag: '@LPS-135388'},
	async ({apiHelpers, page, pageEditorPage, site}) => {
		// Corresponds to Poshi test: CanSearchForObjectEntryOnSearchExperience

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

			await page
				.getByLabel(objectDefinition.label['en_US'])
				.check();

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

			await expect(collectionTable.getByText(entryValueA)).toBeVisible();
			await expect(collectionTable.getByText(entryValueB)).toBeVisible();
		});

		await test.step('Search for entry A and verify entry B is filtered out', async () => {
			const collectionTable = page.locator(
				'.lfr-layout-structure-item-collection'
			);

			await expect(async () => {
				const searchInput = page
					.getByPlaceholder('Search', {exact: true});

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
