/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';

export const test = mergeTests(apiHelpersTest, loginTest(), objectPagesTest);

test('created object folders are on the left side bar', async ({
	apiHelpers,
	viewObjectDefinitionsPage,
}) => {
	await viewObjectDefinitionsPage.goto();

	const objectFolderExternalReferenceCode = 'objectFolder' + getRandomInt();

	const objectFolder = await viewObjectDefinitionsPage.createObjectFolder(
		objectFolderExternalReferenceCode
	);

	await expect(
		viewObjectDefinitionsPage.page
			.locator('li')
			.filter({hasText: objectFolderExternalReferenceCode})
	).toBeVisible();

	// Clean up

	await apiHelpers.objectAdmin.deleteObjectFolder(objectFolder.id);
});

test('default folder does not contains delete and edit options', async ({
	viewObjectDefinitionsPage,
}) => {
	await viewObjectDefinitionsPage.goto();

	await viewObjectDefinitionsPage.clickDefaultObjectFolder();

	await viewObjectDefinitionsPage.openObjectFolderActions();

	await expect(
		viewObjectDefinitionsPage.objectFolderDeleteFolderOption
	).toBeHidden();

	await expect(
		viewObjectDefinitionsPage.objectFolderEditLabelAndERCOption
	).toBeHidden();
});
