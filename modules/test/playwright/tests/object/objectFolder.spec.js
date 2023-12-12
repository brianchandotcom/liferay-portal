/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {test as apiHelpersTest} from '../../fixtures/apiHelpers.fixture';
import {test as applicationsMenuPageTest} from '../../fixtures/applicationsMenuPage.fixture';
import {test as objectPagesTest} from '../../fixtures/objectPages.fixture';
import {getRandomInt} from '../../utils/util';

export const test = mergeTests(apiHelpersTest, applicationsMenuPageTest, objectPagesTest);

test('created object folders are on the left side bar', async ({
	_apiHelpers,
	_objectDefinitionsPage,
}) => {

	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-148856', 'true');

	await _objectDefinitionsPage.goto();

	const objectFolderExternalReferenceCode = 'objectFolder' + getRandomInt();

	const objectFolder = await _objectDefinitionsPage.createObjectFolder(
		objectFolderExternalReferenceCode
	);

	await expect(
		_objectDefinitionsPage.page
			.locator('li')
			.filter({hasText: objectFolderExternalReferenceCode})
	).toBeVisible();

	// Clean up

	await _apiHelpers.objectAdmin.deleteObjectFolder(objectFolder.id);
});

test('uncategorized folder does not contains delete and edit options', async ({
	_apiHelpers,
	_objectDefinitionsPage,
}) => {

	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-148856', 'true');

	await _objectDefinitionsPage.goto();
	await _objectDefinitionsPage.clickUncategorizedObjectFolder();
	await _objectDefinitionsPage.openObjectFolderActions();

	await expect(
		_objectDefinitionsPage.objectFolderDeleteFolderOption
	).toBeHidden();
	await expect(
		_objectDefinitionsPage.objectFolderEditLabelAndERCOption
	).toBeHidden();
});
