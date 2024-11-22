/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {ObjectDefinitionApi} from '../../../../../apps/object/object-admin-rest-client-js';
import {backendPageTest} from '../../../fixtures/backendPageTest';
import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {
	ALL_FIELDS_OBJECT_ERC,
	LEMON_BASKET_OBJECT_ERC,
	LEMON_OBJECT_ERC,
	PAGE_MANAGEMENT_SITE_ERC,
	POTATO_OBJECT_ERC,
} from './constants';

export const test = mergeTests(backendPageTest);

test('Teardown: Delete site and data for Page Management tests', async ({
	backendPage,
}) => {
	const apiHelpers = new ApiHelpers(backendPage);

	const {id: siteId} = await apiHelpers.headlessSite.getSiteByERC(
		PAGE_MANAGEMENT_SITE_ERC
	);

	// Return if site does not exist, this is for cases in which this test is ran independently

	if (!siteId) {
		return;
	}

	// Delete object definitions

	for (const ERC of [
		ALL_FIELDS_OBJECT_ERC,
		LEMON_OBJECT_ERC,
		LEMON_BASKET_OBJECT_ERC,
		POTATO_OBJECT_ERC,
	]) {
		const objectDefinitionApiClient =
			await apiHelpers.buildRestClient(ObjectDefinitionApi);

		const {id: objectDefinitionId} = (
			await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
				ERC
			)
		).body;

		if (objectDefinitionId) {
			await objectDefinitionApiClient.deleteObjectDefinition(
				objectDefinitionId
			);
		}
	}

	// Delete site

	await expect(
		await apiHelpers.headlessSite.deleteSiteByERC(PAGE_MANAGEMENT_SITE_ERC)
	).toBeOK();
});
