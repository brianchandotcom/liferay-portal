/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {pagesAdminPageTest} from '../../fixtures/PagesAdminPageTest';
import {styleBookPageTest} from '../../fixtures/StyleBookPageTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {clientExtensionsPageTest} from './fixtures/clientExtensionsPageTest';
import {editThemeCSSClientExtensionsPageTest} from './fixtures/editThemeCSSClientExtensionsPageTest';
import {ViewClientExtensionPage} from './pages/ViewClientExtensionPage';
import uploadAndValidateFile from './utils/uploadAndValidateFile';

export const test = mergeTests(
	clientExtensionsPageTest,
	loginTest(),
	pagesAdminPageTest,
	styleBookPageTest,
	editThemeCSSClientExtensionsPageTest
);

const SAMPLES = [
	{
		erc: 'LXC:liferay-sample-theme-css-1',
		mainURL: '/o/liferay-sample-theme-css-1/css/main.css',
		name: 'Liferay Sample Theme CSS 1',
	},
	{
		erc: 'LXC:liferay-sample-theme-css-2',
		mainURL: '/o/liferay-sample-theme-css-2/css/main.css',
		name: 'Liferay Sample Theme CSS 2',
	},
];

for (const sample of SAMPLES) {
	test(`${sample.name} is registered`, async ({page}) => {
		const viewClientExtensionPage = new ViewClientExtensionPage(
			page,
			sample.erc
		);

		await viewClientExtensionPage.goto();

		expect(viewClientExtensionPage.nameLocator).toHaveValue(sample.name);
		expect(viewClientExtensionPage.fieldLocator('Main URL')).toHaveValue(
			sample.mainURL
		);
	});

	test(`${sample.name}'s .css file can be downloaded`, async ({page}) => {
		const response = await page.goto(sample.mainURL);

		expect(response.status()).toBe(200);
	});
}

test('ThemeCSS client extension supports frontend token definition JSON file upload', async ({
	editThemeCSSClientExtensionsPage,
	page,
}) => {
	await editThemeCSSClientExtensionsPage.goto();

	await uploadAndValidateFile(
		'empty-json-file.json',
		'The frontend token definition JSON file was uploaded and contributed 0 token categories, 0 token sets, and 0 tokens.',
		page,
		editThemeCSSClientExtensionsPage
	);

	await uploadAndValidateFile(
		'frontend-token-definition.json',
		'The frontend token definition JSON file was uploaded and contributed 1 token categories, 1 token sets, and 2 tokens.',
		page,
		editThemeCSSClientExtensionsPage
	);

	await uploadAndValidateFile(
		'frontend-token-definition-empty-object.json',
		'The frontend token definition JSON file was uploaded and contributed 0 token categories, 0 token sets, and 0 tokens.',
		page,
		editThemeCSSClientExtensionsPage
	);

	await uploadAndValidateFile(
		'frontend-token-definition-invalid-schema.json',
		'The format is invalid. Please upload a valid Frontend Token Definition JSON file.',
		page,
		editThemeCSSClientExtensionsPage
	);
});

test('ThemeCSS client extension frontend token definition tokens appears stylebooks', async ({
	clientExtensionsPage,
	editThemeCSSClientExtensionsPage,
	page,
	pagesAdminPage,
	styleBooksPage,
}) => {

	// Create Theme CSS client extension.

	await editThemeCSSClientExtensionsPage.goto();

	const clientExtensionName = getRandomString();

	await editThemeCSSClientExtensionsPage.nameInput.fill(clientExtensionName);

	await uploadAndValidateFile(
		'frontend-token-definition.json',
		'The frontend token definition JSON file was uploaded and contributed 1 token categories, 1 token sets, and 2 tokens.',
		page,
		editThemeCSSClientExtensionsPage
	);

	await editThemeCSSClientExtensionsPage.publish();

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
