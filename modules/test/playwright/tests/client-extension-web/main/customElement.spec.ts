/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {isolatedLayoutTest} from '../../../fixtures/isolatedLayoutTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import getRandomString from '../../../utils/getRandomString';
import {clientExtensionsPageTest} from './fixtures/clientExtensionsPageTest';
import {editCustomElementPageTest} from './fixtures/editCustomElementPageTest';
import {Column} from './pages/ClientExtensionsPage';
import {WaitAction} from './pages/EditClientExtensionsPage';
import {EditCustomElementPage} from './pages/EditCustomElementPage';

const test = mergeTests(
	clientExtensionsPageTest,
	editCustomElementPageTest,
	loginTest()
);

const testSample = mergeTests(
	clientExtensionsPageTest,
	editCustomElementPageTest,
	isolatedLayoutTest({publish: false}),
	pageEditorPagesTest,
	loginTest()
);

testSample.describe('Samples', () => {
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
				page.getByText(
					'liferay-sample-custom-element-3 app is running!'
				),
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
		testSample(
			`${sample.name} is registered and can be used`,
			async ({clientExtensionsPage, layout, page, pageEditorPage}) => {
				await test.step(`${sample.name} is visible and configured from Workspace`, async () => {
					await clientExtensionsPage.goto();

					await clientExtensionsPage.search(sample.name);

					await expect(
						clientExtensionsPage.getRowByText(sample.name)
					).toBeVisible();

					await expect(
						clientExtensionsPage.getCellByText(
							Column.CONFIGURED_FROM,
							sample.name
						)
					).toHaveText('Workspace');
				});

				await test.step(`${sample.name} can be viewed and information is read-only`, async () => {
					const viewClientExtensionPage =
						await clientExtensionsPage.viewClientExtension(
							sample.name
						);

					await expect(
						viewClientExtensionPage.nameInput
					).toBeVisible();
					await expect(
						viewClientExtensionPage.nameInput
					).toBeDisabled();
					await expect(viewClientExtensionPage.nameInput).toHaveValue(
						sample.name
					);

					const htmlElementNameInput =
						viewClientExtensionPage.getInputByLabel(
							'HTML Element Name'
						);

					await expect(htmlElementNameInput).toBeVisible();
					await expect(htmlElementNameInput).toBeDisabled();
					await expect(htmlElementNameInput).toHaveValue(
						sample.htmlElementName
					);
				});

				await test.step(`${sample.name} can be added to a page and is rendered`, async () => {
					await page.goto(
						`/web/guest${layout.friendlyURL}?p_l_mode=edit`
					);

					await pageEditorPage.addWidget(
						'Client Extensions',
						sample.name
					);
					await pageEditorPage.publishPage();

					await page.goto(`/web/guest${layout.friendlyURL}`);

					await expect(
						page.locator(sample.htmlElementName)
					).toBeVisible();
					await expect(sample.renderTestLocator(page)).toBeVisible();
				});
			}
		);
	}
});

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

		await test.step('Clean up', async () => {
			await clientExtensionsPage.goto();
			await clientExtensionsPage.deleteClientExtension(NAME);
		});
	}
);

test('Can cancel the creation of a Custom Element', async ({
	editCustomElementPage,
}) => {
	const clientExtensionName = getRandomString();

	await editCustomElementPage.goto();

	await editCustomElementPage.cssURLInput.fill(getRandomString());
	await editCustomElementPage.descriptionContentEditable.fill(
		getRandomString()
	);
	await editCustomElementPage.friendlyURLMappingInput.fill(getRandomString());
	await editCustomElementPage.htmlElementNameInput.fill(
		`html-${getRandomString()}`
	);
	await editCustomElementPage.instanceableCheckbox.check();
	await editCustomElementPage.javaScriptURLInput.fill(getRandomString());
	await editCustomElementPage.nameInput.fill(clientExtensionName);
	await editCustomElementPage.sourceCodeURLInput.fill(getRandomString());
	await editCustomElementPage.useESModulesCheckbox.check();

	const clientExtensionsPage = await editCustomElementPage.cancel();

	await expect(
		clientExtensionsPage.getRowByText(clientExtensionName)
	).not.toBeVisible();
});

test(
	'Publishing with invalid field values results in error',
	{tag: '@LPD-75288'},
	async ({editCustomElementPage, page}) => {
		await test.step('Go to "Add Custom Element" page', async () => {
			await editCustomElementPage.goto();
		});

		await test.step('Name cannot be empty', async () => {
			await editCustomElementPage.fillRequiredFields();

			await editCustomElementPage.nameInput.clear();

			await editCustomElementPage.publish(WaitAction.ERROR);
		});

		await test.step('HTML Element Name cannot be empty', async () => {
			await editCustomElementPage.fillRequiredFields();

			await editCustomElementPage.htmlElementNameInput.clear();

			await editCustomElementPage.publish(WaitAction.NONE);

			await expect(
				page.getByText('The HTML Element Name field is required')
			).toBeVisible();
		});

		await test.step('HTML Element Name cannot contain a space character', async () => {
			await editCustomElementPage.fillRequiredFields();

			await editCustomElementPage.htmlElementNameInput.fill('foo bar');

			await editCustomElementPage.publish(WaitAction.INVALID_CHARACTER);
		});

		await test.step('HTML Element Name must contain a hyphen', async () => {
			await editCustomElementPage.fillRequiredFields();

			await editCustomElementPage.htmlElementNameInput.fill('foo');

			await editCustomElementPage.publish(WaitAction.MISSING_HYPHEN);
		});

		await test.step('HTML Element Name must start with a lowercase letter', async () => {
			await editCustomElementPage.fillRequiredFields();

			await editCustomElementPage.htmlElementNameInput.fill('Foo-bar');

			await editCustomElementPage.publish(
				WaitAction.UPPERCASE_STARTING_LETTER
			);
		});

		await test.step('JavaScript URL field cannot be empty', async () => {
			await editCustomElementPage.fillRequiredFields();

			await editCustomElementPage.javaScriptURLInput.clear();

			await editCustomElementPage.publish(WaitAction.NONE);

			await expect(
				page.getByText('The JavaScript URL field is required')
			).toBeVisible();
		});
	}
);

test('Check that Name field can be translated', async ({
	editCustomElementPage,
}) => {
	await editCustomElementPage.goto();
	await editCustomElementPage.fillRequiredFields();

	const defaultTranslationName = getRandomString();
	const ptTranslationName = getRandomString();

	await editCustomElementPage.nameInput.fill(defaultTranslationName);
	await editCustomElementPage.changeNameLanguage('pt_BR');
	await editCustomElementPage.nameInput.fill(ptTranslationName);

	await test.step('Check expectations', async () => {
		await editCustomElementPage.changeNameLanguage('en_US');
		await expect(editCustomElementPage.nameInput).toHaveValue(
			defaultTranslationName
		);

		await editCustomElementPage.changeNameLanguage('pt_BR');
		await expect(editCustomElementPage.nameInput).toHaveValue(
			ptTranslationName
		);
	});
});

test('Check that JavaScript URL field is required', async ({
	editCustomElementPage,
	page,
}) => {
	await editCustomElementPage.goto();
	await editCustomElementPage.fillRequiredFields();

	await test.step('Check expectations', async () => {
		await editCustomElementPage.javaScriptURLInput.clear();
		await editCustomElementPage.publish(WaitAction.NONE);

		await expect(
			page.getByText('The JavaScript URL field is required.')
		).toBeVisible();
	});
});

test('Client extension can be created, edited and deleted', async ({
	clientExtensionsPage,
	editCustomElementPage,
}) => {
	const clientExtensionName = getRandomString();
	const newClientExtensionName = getRandomString();

	await editCustomElementPage.goto();

	await test.step('Create a new client extension', async () => {
		await editCustomElementPage.cssURLInput.fill(getRandomString());
		await editCustomElementPage.descriptionContentEditable.fill(
			getRandomString()
		);
		await editCustomElementPage.friendlyURLMappingInput.fill(
			getRandomString()
		);
		await editCustomElementPage.htmlElementNameInput.fill(
			`html-${getRandomString()}`
		);
		await editCustomElementPage.instanceableCheckbox.check();
		await editCustomElementPage.javaScriptURLInput.fill(getRandomString());
		await editCustomElementPage.nameInput.fill(clientExtensionName);
		await editCustomElementPage.sourceCodeURLInput.fill(getRandomString());
		await editCustomElementPage.useESModulesCheckbox.check();

		await editCustomElementPage.publish(WaitAction.SUCCESS);

		await clientExtensionsPage.goto();

		await expect(
			clientExtensionsPage.getRowByText(clientExtensionName)
		).toBeVisible();
	});

	await test.step('Edit the client extension', async () => {
		await clientExtensionsPage.editClientExtension(
			clientExtensionName,
			EditCustomElementPage
		);

		const newCSSURL = `/${getRandomString()}`;
		const newDescription = getRandomString();
		const newFriendlyURLMapping = getRandomString();
		const newHtmlElementName = 'html-element-' + getRandomString();
		const newJavaScriptURL = `/${getRandomString()}`;
		const newSourceCodeUrl = getRandomString();

		await editCustomElementPage.cssURLInput.fill(newCSSURL);
		await editCustomElementPage.descriptionContentEditable.fill(
			newDescription
		);
		await editCustomElementPage.friendlyURLMappingInput.fill(
			newFriendlyURLMapping
		);
		await editCustomElementPage.htmlElementNameInput.fill(
			newHtmlElementName
		);
		await editCustomElementPage.javaScriptURLInput.fill(newJavaScriptURL);
		await editCustomElementPage.nameInput.fill(newClientExtensionName);
		await editCustomElementPage.sourceCodeURLInput.fill(newSourceCodeUrl);

		await editCustomElementPage.publish(WaitAction.SUCCESS);

		await clientExtensionsPage.goto();

		await clientExtensionsPage.editClientExtension(
			newClientExtensionName,
			EditCustomElementPage
		);

		await expect(editCustomElementPage.cssURLInput).toHaveValue(newCSSURL);
		await expect(
			editCustomElementPage.descriptionContentEditable.getByText(
				newDescription
			)
		).toBeVisible();
		await expect(editCustomElementPage.friendlyURLMappingInput).toHaveValue(
			newFriendlyURLMapping
		);
		await expect(editCustomElementPage.htmlElementNameInput).toHaveValue(
			newHtmlElementName
		);
		await expect(editCustomElementPage.javaScriptURLInput).toHaveValue(
			newJavaScriptURL
		);
		await expect(editCustomElementPage.nameInput).toHaveValue(
			newClientExtensionName
		);
		await expect(editCustomElementPage.sourceCodeURLInput).toHaveValue(
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

test(
	`Verify that JavaScript URL repeatable field can be assigned multiple values`,
	{tag: '@LPS-158545'},
	async ({clientExtensionsPage, editCustomElementPage}) => {
		await test.step('Create a custom element with two JavaScript URLs', async () => {
			await editCustomElementPage.goto();

			await editCustomElementPage.nameInput.fill('Test Custom Element');
			await editCustomElementPage.htmlElementNameInput.fill(
				'test-custom-element'
			);

			await editCustomElementPage.javaScriptURLInput
				.nth(0)
				.fill('https://www.liferay.com/');
			await editCustomElementPage.addJavaScriptURLButton.nth(0).click();
			await editCustomElementPage.javaScriptURLInput
				.nth(1)
				.fill('https://www.liferay.com/company/our-story');

			await editCustomElementPage.publish(WaitAction.SUCCESS);
		});

		const editCustomElementPage2 =
			await test.step('Edit the custom element again', async () => {
				await clientExtensionsPage.goto();

				return await clientExtensionsPage.editClientExtension(
					'Test Custom Element',
					EditCustomElementPage
				);
			});

		await test.step('Check expectations', async () => {
			await expect(
				editCustomElementPage2.javaScriptURLInput.nth(0)
			).toHaveValue('https://www.liferay.com/');

			await expect(
				editCustomElementPage2.javaScriptURLInput.nth(1)
			).toHaveValue('https://www.liferay.com/company/our-story');
		});

		await test.step('Clean up', async () => {
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
	async ({editCustomElementPage}) => {
		await test.step('Create a custom element with two JavaScript URLs', async () => {
			await editCustomElementPage.goto();

			await editCustomElementPage.nameInput.fill('Test Custom Element');
			await editCustomElementPage.htmlElementNameInput.fill(
				'test-custom-element'
			);

			await editCustomElementPage.javaScriptURLInput
				.nth(0)
				.fill('https://www.liferay.com/');
			await editCustomElementPage.addJavaScriptURLButton.nth(0).click();
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
			await expect(editCustomElementPage.javaScriptURLInput).toHaveCount(
				2
			);
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

		await expect(editCustomElementPage.deleteCSSURLButton).toHaveCount(2);
	}
);

test(
	`Verify that a new JavaScript URL row can be created`,
	{tag: '@LPS-152023'},
	async ({editCustomElementPage}) => {
		await editCustomElementPage.goto();

		await editCustomElementPage.addJavaScriptURLButton.nth(0).click();

		await expect(editCustomElementPage.javaScriptURLInput).toHaveCount(2);

		await expect(editCustomElementPage.addJavaScriptURLButton).toHaveCount(
			2
		);

		await expect(
			editCustomElementPage.deleteJavaScriptURLButton
		).toHaveCount(2);
	}
);
