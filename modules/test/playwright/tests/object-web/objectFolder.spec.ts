/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpers.fixture';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPages.fixture';
import {loginTest} from '../../fixtures/login.fixture';
import {objectPagesTest} from '../../fixtures/objectPages.fixture';
import getRandomInt from '../../utils/getRandomInt';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	loginTest,
	objectPagesTest
);

test('created object folders are on the left side bar', async ({
	_apiHelpers,
	_login,
	_objectDefinitionsPage,
}) => {
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-148856', true);

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

test('default folder does not contains delete and edit options', async ({
	_apiHelpers,
	_login,
	_objectDefinitionsPage,
}) => {
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-148856', true);

	await _objectDefinitionsPage.goto();

	await _objectDefinitionsPage.clickDefaultObjectFolder();

	await _objectDefinitionsPage.openObjectFolderActions();

	await expect(
		_objectDefinitionsPage.objectFolderDeleteFolderOption
	).toBeHidden();

	await expect(
		_objectDefinitionsPage.objectFolderEditLabelAndERCOption
	).toBeHidden();
});
