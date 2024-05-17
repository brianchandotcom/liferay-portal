/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {isolatedLayoutTest} from '../../fixtures/isolatedLayoutTest';
import {loginTest} from '../../fixtures/loginTest';
import {PageEditorPage} from './pages/PageEditorPage';
import {ViewClientExtensionPage} from './pages/ViewClientExtensionPage';

export const test = mergeTests(
	isolatedLayoutTest({publish: false}),
	pageEditorPagesTest,
	loginTest()
);

const SAMPLES = [
	{
		erc: 'LXC:liferay-sample-custom-element-1',
		htmlElementName: 'vanilla-counter',
		name: 'Liferay Sample Custom Element 1',
		renderTestLocator: (page: Page) =>
			page.getByText('Portlet internal route'),
	},
	{
		erc: 'LXC:liferay-sample-custom-element-3',
		htmlElementName: 'liferay-sample-custom-element-3',
		name: 'Liferay Sample Custom Element 3',
		renderTestLocator: (page: Page) =>
			page.getByText('liferay-sample-custom-element-3 app is running!'),
	},
	{
		erc: 'LXC:liferay-sample-custom-element-4',
		htmlElementName: 'liferay-sample-custom-element-4',
		name: 'Liferay Sample Custom Element 4',
		renderTestLocator: (page: Page) =>
			page.getByRole('heading', {name: 'Hello Test. Welcome!'}),
	},
	{
		erc: 'LXC:liferay-sample-custom-element-5',
		htmlElementName: 'liferay-sample-custom-element-5',
		name: 'Liferay Sample Custom Element 5',
		renderTestLocator: (page: Page) => page.getByText('Success!'),
	},
	{
		erc: 'LXC:liferay-sample-etc-frontend-custom-element',
		htmlElementName: 'liferay-sample-etc-frontend-custom-element',
		name: 'Liferay Sample Etc Frontend Custom Element',
		renderTestLocator: (page: Page) => page.getByText('Greetings in:'),
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
		expect(
			viewClientExtensionPage.fieldLocator('HTML Element Name')
		).toHaveValue(sample.htmlElementName);
	});

	test(`${sample.name} can be added to a page and is rendered`, async ({
		layout,
		page,
		pageEditorPage,
	}) => {
		await pageEditorPage.goto(layout);
		await pageEditorPage.addWidget('Client Extensions', sample.name);
		await pageEditorPage.publishPage();

		expect(page.locator(sample.htmlElementName)).toBeVisible();
		expect(sample.renderTestLocator(page)).toBeVisible();
	});
}
