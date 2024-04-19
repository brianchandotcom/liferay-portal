/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {pagesAdminPageTest} from '../../fixtures/PagesAdminPageTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {PagesAdminPage} from '../../pages/layout-admin-web/PagesAdminPage';
import getRandomString from '../../utils/getRandomString';
import {clientExtensionsPageTest} from './fixtures/clientExtensionsPageTest';
import {editJSClientExtensionsPageTest} from './fixtures/editJSClientExtensionsPageTest';
import {ClientExtensionsPage} from './pages/ClientExtensionsPage';
import {EditJSClientExtensionsPage} from './pages/EditJSClientExtensionsPage';

export const test = mergeTests(
	clientExtensionsPageTest,
	featureFlagsTest({
		'LPD-10981': true,
	}),
	loginTest(),
	pagesAdminPageTest,
	editJSClientExtensionsPageTest
);

test('Create a new JS client extension with a script element attribute', async ({
	clientExtensionsPage,
	editJSClientExtensionsPage,
	page,
	pagesAdminPage,
}) => {

	// Create a new JS client extension with a script element attribute.

	await editJSClientExtensionsPage.goto();

	const clientExtensionName = getRandomString();
	const clientExtensionValue = getRandomString();

	await editJSClientExtensionsPage.nameInput.fill(clientExtensionName);

	await editJSClientExtensionsPage.javaScriptURLInput.fill(
		'https://www.example.com/script.js'
	);

	await page
		.getByRole('textbox', {
			name: 'Attribute',
		})
		.fill('id');

	await page.getByLabel('Value', {exact: true}).fill(clientExtensionValue);

	await editJSClientExtensionsPage.publish();

	// Apply JS client extension to all pages.

	await pagesAdminPage.selectJavaScriptClientExtension(clientExtensionName);

	await page.goto('/');

	await expect(
		page.locator(`script[id="${clientExtensionValue}"]`)
	).toBeAttached();

	// Clean up

	await clientExtensionsPage.goto();

	await clientExtensionsPage.deleteClientExtension(clientExtensionName);
});

test('JS client extension does not allow "src" as a script element attribute', async ({
	editJSClientExtensionsPage,
	page,
}) => {
	await editJSClientExtensionsPage.goto();

	await page
		.getByRole('textbox', {
			name: 'Attribute',
		})
		.fill('src');

	expect(page.getByText('Use the "JavaScript URL" field.')).toBeVisible();
});

const assertDefaultSelectedLoadType = async (
	clientExtensionName: string,
	page: Page,
	loadType: string
) => {
	const loadTypeSelector = page
		.locator('tr', {hasText: clientExtensionName})
		.locator('.load-type-select');

	await expect(loadTypeSelector).toBeDisabled();

	await expect(loadTypeSelector).toHaveValue(loadType);
};

type TScriptAttribute = {
	name: string;
	type: 'boolean' | 'string';
	value: string;
	valueWhenInPage: string | null;
};

type TGlobalJSClientExtensionWithAttributes = {
	clientExtensionName: string;
	clientExtensionsPage: ClientExtensionsPage;
	defaultSelectedLoadType?: string;
	editJSClientExtensionsPage: EditJSClientExtensionsPage;
	page: Page;
	pagesAdminPage: PagesAdminPage;
	scriptAttributes: TScriptAttribute[];
};

const testGlobalJSClientExtensionWithAttributes = async ({
	clientExtensionName,
	clientExtensionsPage,
	defaultSelectedLoadType,
	editJSClientExtensionsPage,
	page,
	pagesAdminPage,
	scriptAttributes,
}: TGlobalJSClientExtensionWithAttributes) => {

	// Create the Global JS Client Extension

	await editJSClientExtensionsPage.goto();

	await editJSClientExtensionsPage.nameInput.fill(clientExtensionName);

	await editJSClientExtensionsPage.javaScriptURLInput.fill(
		'https://www.example.com/script.js'
	);

	for (const {name, type, value} of scriptAttributes) {
		await editJSClientExtensionsPage.addScriptAttribute(name, type, value);
	}

	await editJSClientExtensionsPage.publish();

	// Apply the Global JS client extension and assert its attributes

	await pagesAdminPage.selectJavaScriptClientExtension(clientExtensionName);

	await pagesAdminPage.javaScriptClientExtensionsTab.click();

	if (defaultSelectedLoadType) {
		await assertDefaultSelectedLoadType(
			clientExtensionName,
			page,
			defaultSelectedLoadType
		);
	}

	await page.goto('/');

	const scriptElement = page.locator(`script[id="${clientExtensionName}"]`);

	await expect(scriptElement).toBeAttached();

	for (const {name, valueWhenInPage} of scriptAttributes) {
		expect(await scriptElement.getAttribute(name)).toBe(valueWhenInPage);
	}

	// Clean up

	await clientExtensionsPage.goto();

	await clientExtensionsPage.deleteClientExtension(clientExtensionName);
};

test('GlobalJS client extension with async and defer attributes set to true', async ({
	clientExtensionsPage,
	editJSClientExtensionsPage,
	page,
	pagesAdminPage,
}) => {
	const clientExtensionName = getRandomString();

	await testGlobalJSClientExtensionWithAttributes({
		clientExtensionName,
		clientExtensionsPage,
		defaultSelectedLoadType: 'async',
		editJSClientExtensionsPage,
		page,
		pagesAdminPage,
		scriptAttributes: [
			{
				name: 'async',
				type: 'boolean',
				value: 'true',
				valueWhenInPage: '',
			},
			{
				name: 'defer',
				type: 'boolean',
				value: 'true',
				valueWhenInPage: null,
			},
			{
				name: 'id',
				type: 'string',
				value: clientExtensionName,
				valueWhenInPage: clientExtensionName,
			},
		],
	});
});

test('GlobalJS client extension with async attribute set to true', async ({
	clientExtensionsPage,
	editJSClientExtensionsPage,
	page,
	pagesAdminPage,
}) => {
	const clientExtensionName = getRandomString();

	await testGlobalJSClientExtensionWithAttributes({
		clientExtensionName,
		clientExtensionsPage,
		defaultSelectedLoadType: 'async',
		editJSClientExtensionsPage,
		page,
		pagesAdminPage,
		scriptAttributes: [
			{
				name: 'async',
				type: 'boolean',
				value: 'true',
				valueWhenInPage: '',
			},
			{
				name: 'id',
				type: 'string',
				value: clientExtensionName,
				valueWhenInPage: clientExtensionName,
			},
		],
	});
});

test('GlobalJS client extension with defer attribute set to true', async ({
	clientExtensionsPage,
	editJSClientExtensionsPage,
	page,
	pagesAdminPage,
}) => {
	const clientExtensionName = getRandomString();

	await testGlobalJSClientExtensionWithAttributes({
		clientExtensionName,
		clientExtensionsPage,
		defaultSelectedLoadType: 'defer',
		editJSClientExtensionsPage,
		page,
		pagesAdminPage,
		scriptAttributes: [
			{
				name: 'defer',
				type: 'boolean',
				value: 'true',
				valueWhenInPage: '',
			},
			{
				name: 'id',
				type: 'string',
				value: clientExtensionName,
				valueWhenInPage: clientExtensionName,
			},
		],
	});
});

test('GlobalJS client extension with async and defer attributes set to false and data-senna-track and type are overridden', async ({
	clientExtensionsPage,
	editJSClientExtensionsPage,
	page,
	pagesAdminPage,
}) => {
	const clientExtensionName = getRandomString();

	await testGlobalJSClientExtensionWithAttributes({
		clientExtensionName,
		clientExtensionsPage,
		editJSClientExtensionsPage,
		page,
		pagesAdminPage,
		scriptAttributes: [
			{
				name: 'async',
				type: 'boolean',
				value: 'false',
				valueWhenInPage: null,
			},
			{
				name: 'defer',
				type: 'boolean',
				value: 'false',
				valueWhenInPage: null,
			},
			{
				name: 'data-senna-track',
				type: 'string',
				value: 'permanent',
				valueWhenInPage: 'permanent',
			},
			{
				name: 'id',
				type: 'string',
				value: clientExtensionName,
				valueWhenInPage: clientExtensionName,
			},
			{
				name: 'type',
				type: 'string',
				value: 'module',
				valueWhenInPage: 'module',
			},
		],
	});
});
