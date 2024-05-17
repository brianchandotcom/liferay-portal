/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedLayoutTest} from '../../fixtures/isolatedLayoutTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {ViewClientExtensionPage} from './pages/ViewClientExtensionPage';

export const test = mergeTests(
	isolatedLayoutTest({publish: false}),
	pageEditorPagesTest,
	loginTest()
);

const SAMPLES = [
	{
		erc: 'LXC:liferay-sample-iframe-2-baseball',
		name: 'Baseball',
		url: 'https://en.wikipedia.org/wiki/Baseball',
	},
	{
		erc: 'LXC:liferay-sample-iframe-1-counter-app',
		name: 'Counter App',
		url: 'https://arnab-datta.github.io/counter-app',
	},
	{
		erc: 'LXC:liferay-sample-iframe-2-football',
		name: 'Football',
		url: 'https://en.wikipedia.org/wiki/Football',
	},
	{
		erc: 'LXC:liferay-sample-iframe-2-hockey',
		name: 'Hockey',
		url: 'https://en.wikipedia.org/wiki/Hockey',
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
		expect(viewClientExtensionPage.fieldLocator('URL')).toHaveValue(
			sample.url
		);
	});

	test(`${sample.name} can be added to a page and is rendered`, async ({
		layout,
		page,
		pageEditorPage,
	}) => {
		await pageEditorPage.goto(layout);
		await pageEditorPage.addWidget('Client Extensions', sample.name);
		await pageEditorPage.publishPage();

		expect(page.locator(`iframe[src="${sample.url}"]`)).toBeVisible();
	});
}
