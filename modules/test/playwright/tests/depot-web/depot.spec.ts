/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import getGlobalSiteId from '../../utils/getGlobalSiteId';
import getRandomString from '../../utils/getRandomString';
import {depotsPagesTest} from './fixtures/depotsPagesTest';

const test = mergeTests(
	apiHelpersTest,
	depotsPagesTest,
	isolatedSiteTest,
	loginTest()
);

test(
	'Asset library can only see tags from its own scope',
	{tag: '@LPD-1628'},
	async ({apiHelpers, documentLibraryEditFilePage, page}) => {

		// Create one tag on Global

		const globalSiteId = await getGlobalSiteId(apiHelpers);

		const globalTagName = getRandomString();

		const globalTag =
			await apiHelpers.headlessAdminTaxonomy.postSiteKeyword({
				name: globalTagName,
				siteId: globalSiteId,
			});

		const depotName = getRandomString();

		const depot =
			await apiHelpers.jsonWebServicesDepot.addDepotEntry(depotName);

		const depotTagName = getRandomString();

		await apiHelpers.headlessAdminTaxonomy.postAssetLibraryKeyword({
			depotEntryId: depot.depotEntryId,
			name: depotTagName,
		});

		const anotherDepotName = getRandomString();

		const anotherDepot =
			await apiHelpers.jsonWebServicesDepot.addDepotEntry(
				anotherDepotName
			);

		const anotherDepotTagName = getRandomString();

		await apiHelpers.headlessAdminTaxonomy.postAssetLibraryKeyword({
			depotEntryId: anotherDepot.depotEntryId,
			name: anotherDepotTagName,
		});

		await documentLibraryEditFilePage.goto(
			`/asset-library-${depot.depotEntryId}`
		);

		const fieldset = await page.locator(
			'#_com_liferay_document_library_web_portlet_DLAdminPortlet_categorization'
		);

		if (await fieldset.locator('.panel-body').isHidden()) {
			await fieldset
				.getByRole('button', {name: 'Categorization'})
				.click();
		}

		await page.getByRole('button', {name: 'Select Tags'}).click();

		const modalTag = page.frameLocator('iframe[title="Tags"]');

		await expect(
			modalTag.locator(`tr:has-text('${depotTagName}')`)
		).toBeVisible();

		await expect(
			modalTag.locator(`tr:has-text('${anotherDepotTagName}')`)
		).toBeHidden();

		await expect(
			modalTag.locator(`tr:has-text('${globalTagName}')`)
		).toBeVisible();

		// Remove the tag created on Global

		await apiHelpers.headlessAdminTaxonomy.deleteKeyword({
			id: globalTag.id,
		});

		await apiHelpers.jsonWebServicesDepot.deleteDepotEntry(
			depot.depotEntryId
		);

		await apiHelpers.jsonWebServicesDepot.deleteDepotEntry(
			anotherDepot.depotEntryId
		);
	}
);
