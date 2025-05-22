/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {isolatedLayoutTest} from '../../../fixtures/isolatedLayoutTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import {clientExtensionsPageTest} from './fixtures/clientExtensionsPageTest';
import {editCustomElementPageTest} from './fixtures/editCustomElementPageTest';
import {ClientExtensionsPage} from './pages/ClientExtensionsPage';
import {WaitAction} from './pages/EditClientExtensionsPage';
import {EditCustomElementPage} from './pages/EditCustomElementPage';
import {ViewClientExtensionPage} from './pages/ViewClientExtensionPage';

export const test = mergeTests(
	clientExtensionsPageTest,
	editCustomElementPageTest,
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
	test(`${sample.name} is registered and can be used`, async ({
		clientExtensionsPage,
		layout,
		page,
		pageEditorPage,
	}) => {
		await test.step(`${sample.name} is visible and configured from Workspace`, async () => {
			await clientExtensionsPage.goto();

			await page.getByPlaceholder('Search').fill(sample.name);

			await page.getByRole('button', {name: 'Search'}).click();

			await clientExtensionsPage.assertName(sample.name);

			await clientExtensionsPage.assertIsConfiguredFrom(
				sample.name,
				'Workspace'
			);
		});

		await test.step(`${sample.name} can be viewed and information is read-only`, async () => {
			await clientExtensionsPage.viewClientExtension(sample.name);

			const viewClientExtensionPage = new ViewClientExtensionPage(
				page,
				sample.erc
			);

			await viewClientExtensionPage.assertReadOnlyLocator(
				viewClientExtensionPage.nameLocator,
				sample.name
			);

			await viewClientExtensionPage.assertReadOnlyField(
				'HTML Element Name',
				sample.htmlElementName
			);
		});

		await test.step(`${sample.name} can be added to a page and is rendered`, async () => {
			await page.goto(`/web/guest${layout.friendlyURL}?p_l_mode=edit`);

			await pageEditorPage.addWidget('Client Extensions', sample.name);
			await pageEditorPage.publishPage();

			await page.goto(`/web/guest${layout.friendlyURL}`);

			await expect(page.locator(sample.htmlElementName)).toBeVisible();
			await expect(sample.renderTestLocator(page)).toBeVisible();
		});
	});
}

test(
	'Title field does not allow XSS injections',
	{tag: '@LPD-39400'},
	async ({clientExtensionsPage, editCustomElementPage}) => {
		const NAME = '<svg onload="document.write(\'\')">';

		await editCustomElementPage.goto();

		await editCustomElementPage.nameInput.fill(NAME);
		await editCustomElementPage.htmlElementNameInput.fill('test-element');
		await editCustomElementPage.javaScriptURLInput.fill(
			'http://localhost:8080'
		);

		await editCustomElementPage.publish(WaitAction.SUCCESS);

		await clientExtensionsPage.goto();
		const editCustomElementPage2 =
			await clientExtensionsPage.editClientExtension(
				NAME,
				EditCustomElementPage
			);

		await expect(editCustomElementPage2.nameHeader).toHaveText(NAME);
	}
);

test.describe('Client Extension admin', () => {
	test('Can cancel the creation of a Custom Element', async ({
		clientExtensionsPage,
		page,
	}) => {
		const clientExtensionName = getRandomString();

		await clientExtensionsPage.goto();

		await clientExtensionsPage.addNewClientExtensionButton.waitFor();

		await clientExtensionsPage.addNewClientExtensionButton.click();
		await page.getByRole('menuitem', {name: 'Add Custom Element'}).click();

		await clientExtensionsPage.fillNewCustomElementFormModal({
			cssUrl: getRandomString(),
			description: getRandomString(),
			friendlyUrlMapping: getRandomString(),
			htmlElementName: 'html' + getRandomString(),
			instanceable: true,
			javaScriptUrl: getRandomString(),
			name: clientExtensionName,
			sourceCodeUrl: getRandomString(),
			useEsModulesInstanceable: true,
		});

		await clientExtensionsPage.newCustomElementFormModal.cancelButton.click();

		await expect(
			clientExtensionsPage.getRowByText(clientExtensionName)
		).not.toBeVisible();
	});

	test('Can check that Name field is required and can be translated for Custom Elements', async ({
		clientExtensionsPage,
		page,
	}) => {
		await clientExtensionsPage.goto();

		await clientExtensionsPage.addNewClientExtensionButton.waitFor();

		await clientExtensionsPage.addNewClientExtensionButton.click();
		await page.getByRole('menuitem', {name: 'Add Custom Element'}).click();

		await test.step('Check that Name field is required', async () => {
			await clientExtensionsPage.newCustomElementFormModal.publishButton.waitFor();

			await clientExtensionsPage.fillNewCustomElementFormModal({
				htmlElementName: `html-${getRandomString()}`,
				javaScriptUrl: getRandomString(),
			});

			await clientExtensionsPage.newCustomElementFormModal.publishButton.click();

			await expect(
				page.getByText('Error:Client extension name is required.')
			).toBeVisible();
		});

		await test.step('Use pt_BR translation for name', async () => {
			const defaultTranslationName = getRandomString();
			const ptTranslationName = getRandomString();

			await clientExtensionsPage.fillNewCustomElementFormModal({
				name: defaultTranslationName,
			});

			await expect(
				clientExtensionsPage.newCustomElementFormModal.nameInput
			).toHaveValue(defaultTranslationName);

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: page.locator('.input-localized-palette-container'),
				trigger:
					clientExtensionsPage.newCustomElementFormModal
						.localizedNameButton,
			});

			await page
				.getByRole('button', {name: 'Current translation is'})
				.click();

			await page
				.getByRole('menuitem', {
					name: /Not translated into Portuguese \(Brazil\)/,
				})
				.click();

			await clientExtensionsPage.fillNewCustomElementFormModal({
				name: ptTranslationName,
			});

			await expect(
				clientExtensionsPage.newCustomElementFormModal.nameInput
			).toHaveValue(ptTranslationName);

			await page
				.getByRole('button', {name: 'Current translation is'})
				.click();
			await page
				.getByRole('menuitem', {name: 'Default translation is'})
				.click();

			await expect(
				clientExtensionsPage.newCustomElementFormModal.nameInput
			).toHaveValue(defaultTranslationName);
		});
	});

	test('Can check that JavaScript URL field is required for Custom Elements', async ({
		clientExtensionsPage,
		page,
	}) => {
		await clientExtensionsPage.goto();

		await clientExtensionsPage.addNewClientExtensionButton.waitFor();

		await clientExtensionsPage.addNewClientExtensionButton.click();
		await page.getByRole('menuitem', {name: 'Add Custom Element'}).click();

		await test.step('Check that JavaScript URL field is required', async () => {
			await clientExtensionsPage.newCustomElementFormModal.publishButton.waitFor();

			await clientExtensionsPage.fillNewCustomElementFormModal({
				htmlElementName: `html-${getRandomString()}`,
				name: getRandomString(),
			});

			await clientExtensionsPage.newCustomElementFormModal.publishButton.click();

			await expect(
				page.getByText('The JavaScript URL field is')
			).toBeVisible();
		});
	});

	test('Can create, edit and delete a Custom Element', async ({
		clientExtensionsPage,
		page,
	}) => {
		const clientExtensionName = getRandomString();
		const newClientExtensionName = getRandomString();

		await clientExtensionsPage.goto();

		await clientExtensionsPage.addNewClientExtensionButton.waitFor();

		await test.step('Create a new Custom Element', async () => {
			await clientExtensionsPage.addClientExtension({
				name: clientExtensionName,
				type: 'Add Custom Element',
			});

			await expect(
				clientExtensionsPage.getRowByText(clientExtensionName)
			).toBeVisible();
		});

		await test.step('Edit the Custom Element', async () => {
			const newCssUrl = getRandomString();
			const newDescription = getRandomString();
			const newFriendlyUrlMapping = getRandomString();
			const newHtmlElementName = 'html-element-' + getRandomString();
			const newJavaScriptUrl = getRandomString();
			const newSourceCodeUrl = getRandomString();

			await clientExtensionsPage.editClientExtension(
				clientExtensionName,
				EditCustomElementPage
			);

			await clientExtensionsPage.fillNewCustomElementFormModal({
				cssUrl: newCssUrl,
				description: newDescription,
				friendlyUrlMapping: newFriendlyUrlMapping,
				htmlElementName: newHtmlElementName,
				javaScriptUrl: newJavaScriptUrl,
				name: newClientExtensionName,
				sourceCodeUrl: newSourceCodeUrl,
			});

			await clientExtensionsPage.newCustomElementFormModal.publishButton.click();

			await clientExtensionsPage.openItemActionsDropdown(
				newClientExtensionName
			);

			await page
				.getByText(newClientExtensionName, {exact: true})
				.first()
				.click();

			await clientExtensionsPage.newCustomElementFormModal.publishButton.waitFor();

			await expect(
				clientExtensionsPage.newCustomElementFormModal.cssUrlInput
			).toHaveValue(`/${newCssUrl}`);
			await expect(
				clientExtensionsPage.newCustomElementFormModal
					.descriptionTextbox
			).toHaveText(newDescription);
			await expect(
				clientExtensionsPage.newCustomElementFormModal
					.friendlyUrlMappingInput
			).toHaveValue(newFriendlyUrlMapping);
			await expect(
				clientExtensionsPage.newCustomElementFormModal
					.htmlElementNameInput
			).toHaveValue(newHtmlElementName);
			await expect(
				clientExtensionsPage.newCustomElementFormModal
					.javaScriptUrlInput
			).toHaveValue(`/${newJavaScriptUrl}`);
			await expect(
				clientExtensionsPage.newCustomElementFormModal.nameInput
			).toHaveValue(newClientExtensionName);
			await expect(
				clientExtensionsPage.newCustomElementFormModal
					.sourceCodeUrlInput
			).toHaveValue(newSourceCodeUrl);

			await clientExtensionsPage.newCustomElementFormModal.cancelButton.click();
		});

		await test.step('Delete the Custom Element', async () => {
			await clientExtensionsPage.deleteClientExtension(
				newClientExtensionName
			);

			await expect(
				clientExtensionsPage.getRowByText(newClientExtensionName)
			).not.toBeVisible();
		});
	});

	test(
		`Verify that JavaScript URL repeatable field can be assigned multiple values`,
		{tag: '@LPS-158545'},
		async ({page}) => {
			await test.step('Create a custom element with two JavaScript URLs', async () => {
				const editCustomElementPage = new EditCustomElementPage(page);

				await editCustomElementPage.goto();

				await editCustomElementPage.nameInput.fill(
					'Test Custom Element'
				);
				await editCustomElementPage.htmlElementNameInput.fill(
					'test-custom-element'
				);

				await editCustomElementPage.javaScriptURLInput
					.nth(0)
					.fill('https://www.liferay.com/');
				await editCustomElementPage.addJavaScriptURLButton
					.nth(0)
					.click();
				await editCustomElementPage.javaScriptURLInput
					.nth(1)
					.fill('https://www.liferay.com/company/our-story');

				await editCustomElementPage.publish(WaitAction.SUCCESS);
			});

			const editCustomElementPage =
				await test.step('Edit the custom element again', async () => {
					const clientExtensionsPage = new ClientExtensionsPage(page);
					await clientExtensionsPage.goto();

					return await clientExtensionsPage.editClientExtension(
						'Test Custom Element',
						EditCustomElementPage
					);
				});

			await test.step('Check expectations', async () => {
				await expect(
					editCustomElementPage.javaScriptURLInput.nth(0)
				).toHaveValue('https://www.liferay.com/');

				await expect(
					editCustomElementPage.javaScriptURLInput.nth(1)
				).toHaveValue('https://www.liferay.com/company/our-story');
			});

			await test.step('Clean up', async () => {
				const clientExtensionsPage = new ClientExtensionsPage(page);

				await clientExtensionsPage.goto();
				await clientExtensionsPage.deleteClientExtension(
					'Test Custom Element'
				);
			});
		}
	);

	test(
		`Verify deletion of one of JavaScript URL multiple values`,
		{tag: '@LPS-152023'},
		async ({page}) => {
			const editCustomElementPage = new EditCustomElementPage(page);

			await test.step('Create a custom element with two JavaScript URLs', async () => {
				await editCustomElementPage.goto();

				await editCustomElementPage.nameInput.fill(
					'Test Custom Element'
				);
				await editCustomElementPage.htmlElementNameInput.fill(
					'test-custom-element'
				);

				await editCustomElementPage.javaScriptURLInput
					.nth(0)
					.fill('https://www.liferay.com/');
				await editCustomElementPage.addJavaScriptURLButton
					.nth(0)
					.click();
				await editCustomElementPage.javaScriptURLInput
					.nth(1)
					.fill('https://www.liferay.com/company/our-story');
			});

			await test.step('Remove the first JavaScript URL', async () => {
				await editCustomElementPage.deleteJavaScriptURLButton
					.nth(0)
					.click();
			});

			await test.step('Check expectations', async () => {
				await expect(
					editCustomElementPage.javaScriptURLInput
				).toHaveCount(2);
				await expect(
					editCustomElementPage.javaScriptURLInput.nth(0)
				).toBeHidden();
				await expect(
					editCustomElementPage.javaScriptURLInput.nth(1)
				).toHaveValue('https://www.liferay.com/company/our-story');

				await expect(
					editCustomElementPage.deleteJavaScriptURLButton
				).toHaveCount(2);
				await expect(
					editCustomElementPage.deleteJavaScriptURLButton.nth(0)
				).toBeHidden();
				await expect(
					editCustomElementPage.deleteJavaScriptURLButton.nth(1)
				).toBeHidden();
			});
		}
	);

	test(
		`Verify that a new CSS URL row can be created`,
		{tag: '@LPS-152023'},
		async ({editCustomElementPage}) => {
			await editCustomElementPage.goto();

			await editCustomElementPage.addCSSURLButton.nth(0).click();

			await expect(editCustomElementPage.cssURLInput).toHaveCount(2);

			await expect(editCustomElementPage.addCSSURLButton).toHaveCount(2);

			await expect(editCustomElementPage.deleteCSSURLButton).toHaveCount(
				2
			);
		}
	);

	test(
		`Verify that a new JavaScript URL row can be created`,
		{tag: '@LPS-152023'},
		async ({editCustomElementPage}) => {
			await editCustomElementPage.goto();

			await editCustomElementPage.addJavaScriptURLButton.nth(0).click();

			await expect(editCustomElementPage.javaScriptURLInput).toHaveCount(
				2
			);

			await expect(
				editCustomElementPage.addJavaScriptURLButton
			).toHaveCount(2);

			await expect(
				editCustomElementPage.deleteJavaScriptURLButton
			).toHaveCount(2);
		}
	);
});
