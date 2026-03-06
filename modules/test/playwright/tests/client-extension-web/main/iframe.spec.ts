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
import {editIframePageTest} from './fixtures/editIframePageTest';
import {WaitAction} from './pages/EditClientExtensionsPage';
import {EditIframePage} from './pages/EditIframePage';
import {ViewClientExtensionPage} from './pages/ViewClientExtensionPage';

const test = mergeTests(
	clientExtensionsPageTest,
	editIframePageTest,
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
	'Publishing with invalid field values results in error',
	{tag: '@LPD-75288'},
	async ({editIframePage, page}) => {
		await test.step('Go to "Add Iframe" page', async () => {
			await editIframePage.goto();
		});

		await test.step('Name cannot be empty', async () => {
			await editIframePage.fillRequiredFields();

			await editIframePage.nameInput.clear();

			await editIframePage.publish(WaitAction.ERROR);
		});

		await test.step('URL cannot be empty', async () => {
			await editIframePage.fillRequiredFields();

			await editIframePage.urlInput.clear();

			await editIframePage.publish(WaitAction.NONE);

			await expect(
				page.getByText('The URL field is required')
			).toBeVisible();
		});
	}
);

test('Client extension can be created, edited and deleted', async ({
	clientExtensionsPage,
	editIframePage,
}) => {
	const clientExtensionName = getRandomString();
	const newClientExtensionName = getRandomString();

	await editIframePage.goto();

	await test.step('Create a new client extension', async () => {
		await editIframePage.nameInput.fill(clientExtensionName);
		await editIframePage.descriptionContentEditable.fill(getRandomString());
		await editIframePage.urlInput.fill(`https://${getRandomString()}`);
		await editIframePage.friendlyURLMappingInput.fill(getRandomString());
		await editIframePage.sourceCodeURLInput.fill(getRandomString());

		await editIframePage.publish(WaitAction.SUCCESS);

		await clientExtensionsPage.goto();

		await expect(
			clientExtensionsPage.getRowByText(clientExtensionName)
		).toBeVisible();
	});

	await test.step('Edit the client extension', async () => {
		await clientExtensionsPage.editClientExtension(
			clientExtensionName,
			EditIframePage
		);

		const newDescription = getRandomString();
		const newURL = `https://${getRandomString()}`;
		const newFriendlyURLMapping = getRandomString();
		const newSourceCodeUrl = getRandomString();

		await editIframePage.nameInput.fill(newClientExtensionName);
		await editIframePage.descriptionContentEditable.fill(newDescription);
		await editIframePage.urlInput.fill(newURL);
		await editIframePage.friendlyURLMappingInput.fill(
			newFriendlyURLMapping
		);
		await editIframePage.sourceCodeURLInput.fill(newSourceCodeUrl);

		await editIframePage.publish(WaitAction.SUCCESS);

		await clientExtensionsPage.goto();

		await clientExtensionsPage.editClientExtension(
			newClientExtensionName,
			EditIframePage
		);

		await expect(editIframePage.nameInput).toHaveValue(
			newClientExtensionName
		);
		await expect(
			editIframePage.descriptionContentEditable.getByText(newDescription)
		).toBeVisible();
		await expect(editIframePage.urlInput).toHaveValue(newURL);
		await expect(editIframePage.friendlyURLMappingInput).toHaveValue(
			newFriendlyURLMapping
		);
		await expect(editIframePage.sourceCodeURLInput).toHaveValue(
			newSourceCodeUrl
		);
	});

	await test.step('Delete the client extension', async () => {
		await clientExtensionsPage.goto();

		await clientExtensionsPage.deleteClientExtension(
			newClientExtensionName
		);

		await expect(
			clientExtensionsPage.getRowByText(newClientExtensionName)
		).not.toBeVisible();
	});
});
