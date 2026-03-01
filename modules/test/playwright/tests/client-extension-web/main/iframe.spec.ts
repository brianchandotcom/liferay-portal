/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedLayoutTest} from '../../../fixtures/isolatedLayoutTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import getRandomString from '../../../utils/getRandomString';
import {clientExtensionsPageTest} from './fixtures/clientExtensionsPageTest';
import {editIFramePageTest} from './fixtures/editIFramePageTest';
import {WaitAction} from './pages/EditClientExtensionsPage';
import {ViewClientExtensionPage} from './pages/ViewClientExtensionPage';

const test = mergeTests(
	clientExtensionsPageTest,
	editIFramePageTest,
	loginTest()
);

const testSample = mergeTests(
	isolatedLayoutTest({publish: false}),
	pageEditorPagesTest,
	loginTest()
);

testSample.describe('Samples', () => {
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
		testSample(`${sample.name} is registered`, async ({page}) => {
			const viewClientExtensionPage = new ViewClientExtensionPage(
				page,
				sample.erc
			);

			await viewClientExtensionPage.goto();

			await expect(viewClientExtensionPage.nameInput).toHaveValue(
				sample.name
			);
			await expect(
				viewClientExtensionPage.getInputByLabel('URL')
			).toHaveValue(sample.url);
		});

		testSample(
			`${sample.name} can be added to a page and is rendered`,
			async ({layout, page, pageEditorPage}) => {
				await pageEditorPage.goto(layout);
				await pageEditorPage.addWidget(
					'Client Extensions',
					sample.name
				);
				await pageEditorPage.publishPage();

				await expect(
					page.locator(`iframe[src="${sample.url}"]`)
				).toBeVisible();
			}
		);
	}
});

test(
	'IFrame client extension can be created',
	async ({clientExtensionsPage, editIFramePage}) => {
		const iframeName = getRandomString();

		await editIFramePage.goto();

		await editIFramePage.nameInput.fill(iframeName);
		await editIFramePage.urlInput.fill('https://www.example.com');

		await editIFramePage.publish(WaitAction.SUCCESS);

		await test.step('Verify IFrame was created', async () => {
			await clientExtensionsPage.goto();

			await clientExtensionsPage.search(iframeName);

			await expect(
				clientExtensionsPage.getRowByText(iframeName)
			).toBeVisible();
		});

		await test.step('Clean up', async () => {
			await clientExtensionsPage.deleteClientExtension(iframeName);
		});
	}
);

test(
	'IFrame has mandatory field error messages when empty',
	async ({editIFramePage, page}) => {
		await editIFramePage.goto();

		await editIFramePage.nameInput.fill(getRandomString());

		await editIFramePage.publish(WaitAction.NONE);

		await expect(
			page.getByText(/the url field is required/i)
		).toBeVisible();
	}
);
