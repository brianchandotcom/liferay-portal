/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {pagesAdminPageTest} from '../../fixtures/PagesAdminPageTest';
import {styleBookPageTest} from '../../fixtures/StyleBookPageTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {clientExtensionsPageTest} from './fixtures/clientExtensionsPageTest';
import {themeCSSClientExtensionsPageTest} from './fixtures/themeCSSClientExtensionsPageTest';
import {ThemeCSSClientExtensionsPage} from './pages/ThemeCSSClientExtensionsPage';

export const test = mergeTests(
	clientExtensionsPageTest,
	featureFlagsTest({
		'LPD-10773': true,
	}),
	loginTest(),
	pagesAdminPageTest,
	styleBookPageTest,
	themeCSSClientExtensionsPageTest
);

const uploadAndValidateFile = async (
	fileName: string,
	message: string,
	page: Page,
	themeCSSClientExtensionsPage: ThemeCSSClientExtensionsPage
) => {
	await themeCSSClientExtensionsPage.uploadFrontendTokenDefinitionFile(
		__dirname,
		fileName
	);

	await expect(page.getByText(message)).toBeVisible();
};

test('ThemeCSS client extension supports frontend token definition JSON file upload', async ({
	page,
	themeCSSClientExtensionsPage,
}) => {
	await themeCSSClientExtensionsPage.goto();

	await uploadAndValidateFile(
		'empty-json-file.json',
		'The frontend token definition JSON file was uploaded and contributed 0 token categories, 0 token sets, and 0 tokens.',
		page,
		themeCSSClientExtensionsPage
	);

	await uploadAndValidateFile(
		'frontend-token-definition.json',
		'The frontend token definition JSON file was uploaded and contributed 1 token categories, 1 token sets, and 2 tokens.',
		page,
		themeCSSClientExtensionsPage
	);

	await uploadAndValidateFile(
		'frontend-token-definition-empty-object.json',
		'The frontend token definition JSON file was uploaded and contributed 0 token categories, 0 token sets, and 0 tokens.',
		page,
		themeCSSClientExtensionsPage
	);

	await uploadAndValidateFile(
		'frontend-token-definition-invalid-schema.json',
		'The format is invalid. Please upload a valid Frontend Token Definition JSON file.',
		page,
		themeCSSClientExtensionsPage
	);
});

test('ThemeCSS client extension frontend token definition tokens appears stylebooks', async ({
	clientExtensionsPage,
	page,
	pagesAdminPage,
	styleBooksPage,
	themeCSSClientExtensionsPage,
}) => {

	// Create Theme CSS client extension.

	await themeCSSClientExtensionsPage.goto();

	const clientExtensionName = getRandomString();

	await themeCSSClientExtensionsPage.nameInput.fill(clientExtensionName);

	await uploadAndValidateFile(
		'frontend-token-definition.json',
		'The frontend token definition JSON file was uploaded and contributed 1 token categories, 1 token sets, and 2 tokens.',
		page,
		themeCSSClientExtensionsPage
	);

	await themeCSSClientExtensionsPage.editClientExtensionSubmitButton.click();

	// Apply Theme CSS client extension to all pages.

	await pagesAdminPage.selectThemeCSSClientExtension(clientExtensionName);

	const styleBookName = getRandomString();

	await styleBooksPage.createStyleBook(styleBookName);

	// Assert that the frontend token set defined in the frontendTokenDefinition.json file is available in the style book.

	const frontendTokenSetLabel = page.getByText('primary-buttons');

	await expect(frontendTokenSetLabel).toBeVisible();

	// Clean up

	await styleBooksPage.deleteStyleBook(styleBookName);

	await clientExtensionsPage.goto();

	await clientExtensionsPage.deleteClientExtension(clientExtensionName);
});
