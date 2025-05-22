/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {ClientExtensionsPage} from './pages/ClientExtensionsPage';
import {WaitAction} from './pages/EditClientExtensionsPage';
import {EditFDSCellRendererPage} from './pages/EditFDSCellRendererPage';

const test = mergeTests(loginTest());
const editFDSCellRendererTest = test.extend<{
	editFDSCellRendererPage: EditFDSCellRendererPage;
}>({
	editFDSCellRendererPage: [
		async ({page}, use) => {
			const clientExtensionsPage = new ClientExtensionsPage(page);

			await clientExtensionsPage.goto();
			await clientExtensionsPage.gotoNewClientExtension(
				'Add Frontend Data Set Cell Renderer'
			);

			const editFDSCellRendererPage = new EditFDSCellRendererPage(page);

			await editFDSCellRendererPage.waitFor();

			await use(editFDSCellRendererPage);
		},
		{auto: true},
	],
});

test(
	`Verify that changes are not saved when clicks on Cancel button`,
	{tag: '@LPS-175155'},
	async ({page}) => {
		const clientExtensionsPage = new ClientExtensionsPage(page);

		await clientExtensionsPage.goto();
		await clientExtensionsPage.gotoNewClientExtension(
			'Add Frontend Data Set Cell Renderer'
		);

		const editFDSCellRendererPage = new EditFDSCellRendererPage(page);

		await editFDSCellRendererPage.waitFor();
		await editFDSCellRendererPage.nameInput.fill('Change Date Format');
		await editFDSCellRendererPage.javaScriptURLInput.fill(
			'http://www.myplace.com/mycellrenderer.js'
		);
		await editFDSCellRendererPage.cancel();

		await clientExtensionsPage.waitFor();

		await expect(
			clientExtensionsPage.getRowByText('Change Date Format')
		).toHaveCount(0);
	}
);

editFDSCellRendererTest(
	`Verify that it is possible to change the language`,
	{tag: '@LPS-175155'},
	async ({editFDSCellRendererPage}) => {
		await editFDSCellRendererPage.changeNameLanguage('es_ES');
		await editFDSCellRendererPage.nameInput.fill(
			'Cambiar formato de fecha'
		);
		await editFDSCellRendererPage.changeNameLanguage('en_US');
		await editFDSCellRendererPage.changeNameLanguage('es_ES');

		await expect(editFDSCellRendererPage.nameInput).toHaveValue(
			'Cambiar formato de fecha'
		);
	}
);

editFDSCellRendererTest(
	`Verify that it is possible to create a cell renderer`,
	{tag: '@LPS-175155'},
	async ({editFDSCellRendererPage, page}) => {
		await editFDSCellRendererPage.nameInput.fill('Change Date Format');
		await editFDSCellRendererPage.javaScriptURLInput.fill(
			'http://www.myplace.com/mycellrenderer.js'
		);
		await editFDSCellRendererPage.publish(WaitAction.SUCCESS);

		const clientExtensionsPage = new ClientExtensionsPage(page);

		await clientExtensionsPage.goto();

		await expect(
			clientExtensionsPage.getRowByText('Change Date Format')
		).toBeVisible();

		await test.step('Cleanup', async () => {
			await clientExtensionsPage.deleteClientExtension(
				'Change Date Format'
			);
		});
	}
);

editFDSCellRendererTest(
	`Verify that the Additional Resources group can be hidden`,
	{tag: '@LPS-175155'},
	async ({page}) => {
		await page.getByRole('button', {name: 'Additional Resources'}).click();

		await page
			.getByText('Source Code URL')
			.waitFor({state: 'hidden', timeout: 1000});
	}
);

editFDSCellRendererTest(
	`Verify that the Content group can be hidden`,
	{tag: '@LPS-175155'},
	async ({page}) => {
		await page.getByRole('button', {name: 'Content'}).click();

		await page
			.getByText('JavaScript URL')
			.waitFor({state: 'hidden', timeout: 1000});
	}
);

editFDSCellRendererTest(
	`Verify that the Identity group can be hidden`,
	{tag: '@LPS-175155'},
	async ({page}) => {
		await page.getByRole('button', {name: 'Identity'}).click();

		await page.getByText('Name').waitFor({state: 'hidden', timeout: 1000});
		await page
			.getByText('Description')
			.waitFor({state: 'hidden', timeout: 1000});
	}
);

editFDSCellRendererTest(
	`Verify that it is not possible to publish when the Name field is empty`,
	{tag: '@LPS-175155'},
	async ({editFDSCellRendererPage}) => {
		await editFDSCellRendererPage.javaScriptURLInput.fill(
			'http://www.myplace.com/mycellrenderer.js'
		);

		await editFDSCellRendererPage.publish(WaitAction.ERROR);
	}
);

editFDSCellRendererTest(
	`Verify that it is not possible to publish when the JavaScript URL field is empty`,
	{tag: '@LPS-175155'},
	async ({editFDSCellRendererPage, page}) => {
		await editFDSCellRendererPage.nameInput.fill('Change Date Format');
		await editFDSCellRendererPage.publish(WaitAction.NONE);

		await expect(
			page.getByText('The JavaScript URL field is required.')
		).toBeVisible();
	}
);

editFDSCellRendererTest(
	`Verify that publication cannot be done when an incorrect value is entered in the Source Code field`,
	{tag: '@LPS-175155'},
	async ({editFDSCellRendererPage}) => {

		// TODO: this one is really failing

		await editFDSCellRendererPage.nameInput.fill('Change Date Format');
		await editFDSCellRendererPage.javaScriptURLInput.fill(
			'http://www.myplace.com/mycellrenderer.js'
		);
		await editFDSCellRendererPage.sourceCodeURLInput.fill(
			'This Cell Renderer will show the Date in a different format to the default shown by the FDS View.'
		);

		await editFDSCellRendererPage.publish(WaitAction.ERROR);
	}
);
