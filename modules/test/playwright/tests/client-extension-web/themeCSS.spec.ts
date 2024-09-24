/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import {styleBookPageTest} from '../../fixtures/styleBookPageTest';
import getRandomString from '../../utils/getRandomString';
import {clientExtensionsPageTest} from './fixtures/clientExtensionsPageTest';
import {editThemeCSSClientExtensionsPageTest} from './fixtures/editThemeCSSClientExtensionsPageTest';
import {ViewClientExtensionPage} from './pages/ViewClientExtensionPage';
import uploadAndValidateFile from './utils/uploadAndValidateFile';

export const test = mergeTests(
	clientExtensionsPageTest,
	loginTest(),
	pagesAdminPagesTest,
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
	const clientExtensionName = getRandomString();

	await test.step('Create Theme CSS client extension', async () => {
		await editThemeCSSClientExtensionsPage.goto();

		await editThemeCSSClientExtensionsPage.nameInput.fill(
			clientExtensionName
		);

		await uploadAndValidateFile(
			'frontend-token-definition.json',
			'The frontend token definition JSON file was uploaded and contributed 1 token categories, 1 token sets, and 2 tokens.',
			page,
			editThemeCSSClientExtensionsPage
		);

		await editThemeCSSClientExtensionsPage.publish();
	});

	await test.step('Apply Theme CSS client extension to all pages', async () => {
		await pagesAdminPage.selectThemeCSSClientExtension(clientExtensionName);
	});

	const styleBookName = getRandomString();

	await test.step('Create style book', async () => {
		await styleBooksPage.goto();

		await styleBooksPage.create(styleBookName);
	});

	await test.step('Assert that the frontend token set defined in the frontendTokenDefinition.json file is available in the style book', async () => {
		const frontendTokenSetLabel = page.getByText('primary-buttons');

		await expect(frontendTokenSetLabel).toBeVisible();
	});

	await test.step('Clean up', async () => {
		await styleBooksPage.goto();

		await styleBooksPage.delete(styleBookName);

		await clientExtensionsPage.goto();

		await clientExtensionsPage.deleteClientExtension(clientExtensionName);
	});
});
