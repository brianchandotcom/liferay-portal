/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {APIResponse, expect as baseExpect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import fillAndClickOutside from '../../utils/fillAndClickOutside';
import getRandomString from '../../utils/getRandomString';
import {journalPagesTest} from './fixtures/journalPagesTest';
import getDataStructureDefinition from './utils/getDataStructureDefinition';

const expect = baseExpect.extend({
	toBeSuccessful: (response: APIResponse) => ({
		message: () =>
			response.ok()
				? 'Response is successful'
				: 'Response is not successful',
		pass: response.ok(),
	}),
});

const autoSaveTest = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	featureFlagsTest({
		'LPD-11228': true,
		'LPD-15596': true,
	}),
	isolatedSiteTest,
	journalPagesTest,
	loginTest(),
	pagesAdminPagesTest
);

const autosaveWithoutPermissionsTest = mergeTests(
	featureFlagsTest({
		'LPD-11228': true,
		'LPD-15596': false,
	}),
	isolatedSiteTest,
	journalPagesTest,
	loginTest()
);

const autoSaveUndoRedoTest = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	featureFlagsTest({
		'LPD-11228': true,
		'LPD-15596': true,
		'LPD-36053': true,
	}),
	isolatedSiteTest,
	journalPagesTest,
	loginTest()
);

autoSaveTest(
	'UndoRedo Should not appear when editing default values',
	{
		tag: '@LPD-36442',
	},
	async ({
		apiHelpers,
		journalEditArticlePage,
		journalEditStructureDefaultValuesPage,
		site,
	}) => {
		const fieldName = 'Text1';
		const structureName = 'Structure1';

		const dataDefinition = getDataStructureDefinition({
			defaultLanguageId: 'en_US',
			fields: [{name: fieldName, repeatable: true}],
			name: structureName,
		});

		await apiHelpers.dataEngine.createStructure(site.id, dataDefinition);

		await journalEditStructureDefaultValuesPage.goto({
			siteUrl: site.friendlyUrlPath,
			structureName,
		});

		expect(journalEditArticlePage.undoButton).not.toBeVisible();
		expect(journalEditArticlePage.redoButton).not.toBeVisible();
	}
);

autoSaveTest(
	'LockIndicator should have an errorState',
	{
		tag: '@LPD-26854',
	},
	async ({apiHelpers, journalEditArticlePage, page, site}) => {
		const localizableFieldName = 'Text56789';
		const structureName = 'Structure 2';

		const dataDefinition = getDataStructureDefinition({
			defaultLanguageId: 'en_US',
			fields: [{name: localizableFieldName, repeatable: false}],
			name: structureName,
		});

		await apiHelpers.dataEngine.createStructure(site.id, dataDefinition);

		await journalEditArticlePage.goto({
			siteUrl: site.friendlyUrlPath,
			structureName,
		});

		const localizableField = page.getByRole('textbox', {
			name: localizableFieldName,
		});

		await fillAndClickOutside(page, localizableField);

		const errorIndicator = await page.locator(
			'#_com_liferay_journal_web_portlet_JournalPortlet_lockErrorIndicator'
		);

		await expect(errorIndicator).toBeVisible();
	}
);

autoSaveTest(
	'Web content should be saved as draft after changing the title and the content',
	{
		tag: '@LPD-26856',
	},
	async ({apiHelpers, journalEditArticlePage, page, site}) => {
		const localizableFieldName = 'Text56789';
		const structureName = 'Structure 2';
		const title = getRandomString();
		const content = getRandomString();

		const dataDefinition = getDataStructureDefinition({
			defaultLanguageId: 'en_US',
			fields: [{name: localizableFieldName, repeatable: false}],
			name: structureName,
		});

		await apiHelpers.dataEngine.createStructure(site.id, dataDefinition);

		await journalEditArticlePage.goto({
			siteUrl: site.friendlyUrlPath,
			structureName,
		});

		await journalEditArticlePage.fillTitle(title);

		const localizableField = page.getByRole('textbox', {
			name: localizableFieldName,
		});

		await fillAndClickOutside(page, localizableField, content);

		await expect(
			journalEditArticlePage.changesSavedIndicator
		).toBeVisible();

		await page.getByTitle('Go to Web Content').click();

		await expect(
			page.getByRole('heading', {name: 'Web Content'})
		).toBeVisible();

		const article = page.getByRole('link', {name: title});

		article.waitFor();

		article.click();

		await expect(page.getByRole('heading', {name: title})).toBeVisible();

		await expect(page.getByLabel(localizableFieldName)).toHaveValue(
			content,
			{timeout: 1000}
		);
	}
);

autoSaveUndoRedoTest(
	'Translation is removed when using Undo and restored when using Redo',
	{
		tag: '@LPD-31072',
	},
	async ({journalEditArticlePage, page, site}) => {
		await journalEditArticlePage.goto({siteUrl: site.friendlyUrlPath});

		await journalEditArticlePage.fillTitle(getRandomString());

		const translationButton = page.getByLabel('Select a language, current');

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('option', {
				name: 'Catalan Language: Not',
			}),
			trigger: translationButton,
		});

		await journalEditArticlePage.fillTitle(getRandomString());

		await clickAndExpectToBeVisible({
			autoClick: false,
			target: page.getByRole('option', {
				name: 'Catalan Language: Translating 1/2',
			}),
			trigger: translationButton,
		});

		await journalEditArticlePage.undoButton.click();

		await clickAndExpectToBeVisible({
			autoClick: false,
			target: page.getByRole('option', {
				name: 'Catalan Language: Not',
			}),
			trigger: translationButton,
		});

		await journalEditArticlePage.redoButton.click();

		await clickAndExpectToBeVisible({
			autoClick: false,
			target: page.getByRole('option', {
				name: 'Catalan Language: Translating 1/2',
			}),
			trigger: translationButton,
		});
	}
);

autoSaveUndoRedoTest(
	'Undo/Redo buttons work with metadata fields',
	{
		tag: '@LPD-26863',
	},
	async ({journalEditArticlePage, site}) => {
		const title = getRandomString();

		await journalEditArticlePage.goto({siteUrl: site.friendlyUrlPath});

		await expect(async () => {
			await journalEditArticlePage.fillTitle(title);

			await expect(journalEditArticlePage.undoButton).toBeEnabled();
		}).toPass();

		await expect(
			journalEditArticlePage.changesSavedIndicator
		).toBeVisible();

		await journalEditArticlePage.undoButton.click();

		await expect(journalEditArticlePage.undoButton).toBeDisabled();

		await expect(journalEditArticlePage.titleInput).toHaveValue('');

		await journalEditArticlePage.redoButton.click();

		await expect(journalEditArticlePage.redoButton).toBeDisabled();

		await expect(journalEditArticlePage.titleInput).toHaveValue(title);
	}
);

autoSaveUndoRedoTest(
	'Undo/Redo buttons work with content field',
	{
		tag: '@LPD-26863',
	},
	async ({apiHelpers, journalEditArticlePage, page, site}) => {
		const localizableFieldName = 'Text56789';
		const structureName = 'Structure undo/redo';

		const dataDefinition = getDataStructureDefinition({
			defaultLanguageId: 'en_US',
			fields: [{name: localizableFieldName, repeatable: false}],
			name: structureName,
		});

		await apiHelpers.dataEngine.createStructure(site.id, dataDefinition);

		await journalEditArticlePage.goto({
			siteUrl: site.friendlyUrlPath,
			structureName,
		});

		const title = getRandomString();

		const localizableField = await page.getByRole('textbox', {
			name: localizableFieldName,
		});

		await expect(async () => {
			await journalEditArticlePage.fillTitle(title);

			await expect(journalEditArticlePage.undoButton).toBeEnabled();
		}).toPass();

		await expect(
			journalEditArticlePage.changesSavedIndicator
		).toBeVisible();

		await fillAndClickOutside(page, localizableField, title);

		await expect(
			journalEditArticlePage.changesSavedIndicator
		).toBeVisible();

		await journalEditArticlePage.undoButton.click();

		await expect(localizableField).toHaveValue('');

		await journalEditArticlePage.redoButton.click();

		await expect(journalEditArticlePage.redoButton).toBeDisabled();

		await expect(localizableField).toHaveValue(title);
	}
);

autoSaveUndoRedoTest(
	'History button test',
	{
		tag: '@LPD-31063',
	},
	async ({apiHelpers, journalEditArticlePage, page, site}) => {
		const textFieldName = 'Text56789';
		const structureName = 'Structure undo/redo';

		const dataDefinition = getDataStructureDefinition({
			defaultLanguageId: 'en_US',
			fields: [{name: textFieldName, repeatable: false}],
			name: structureName,
		});

		await apiHelpers.dataEngine.createStructure(site.id, dataDefinition);

		await journalEditArticlePage.goto({
			siteUrl: site.friendlyUrlPath,
			structureName,
		});

		const historyButton = journalEditArticlePage.historyButton;
		await expect(historyButton).toBeDisabled();

		const friendlyURL = 'web-content-title';
		const text = 'Web Content Content';
		const title = 'Web Content Title';

		await expect(async () => {
			await journalEditArticlePage.fillTitle(title);
			await expect(historyButton).toBeEnabled();
		}).toPass();

		await historyButton.click();

		await expect(
			page.getByRole('menuitem', {name: 'Undo All'})
		).toBeEnabled();

		await expect(
			page.getByRole('menuitem', {name: 'Edit Title'})
		).toBeDisabled();

		await expect(async () => {
			await journalEditArticlePage.fillFriendlyURL(friendlyURL);

			await historyButton.click();

			await expect(
				page.getByRole('menuitem', {name: 'Edit Title'})
			).toBeEnabled();
		}).toPass();

		await expect(
			page.getByRole('menuitem', {name: 'Edit Friendly URL'})
		).toBeDisabled();

		const textField = page.getByLabel(textFieldName);

		await fillAndClickOutside(page, textField, text);

		await historyButton.click();

		await page.getByRole('menuitem', {name: 'Undo All'}).click();

		await expect(journalEditArticlePage.friendlyURLInput).toBeEmpty();

		await expect(journalEditArticlePage.titleInput).toBeEmpty();

		await expect(textField).toBeEmpty();

		await historyButton.click();

		await page
			.getByRole('menuitem', {name: 'Edit ' + textFieldName})
			.click();

		await expect(journalEditArticlePage.friendlyURLInput).toHaveValue(
			friendlyURL
		);

		await expect(journalEditArticlePage.titleInput).toHaveValue(title);

		await expect(textField).toHaveValue(text);
	}
);

autoSaveTest(
	'Create a web content selecting permissions in the modal',
	{
		tag: '@LPD-32949',
	},
	async ({journalEditArticlePage, journalPage, page, site}) => {
		await journalEditArticlePage.goto({siteUrl: site.friendlyUrlPath});

		const title = getRandomString();

		await journalEditArticlePage.fillTitle(title);

		await expect(
			journalEditArticlePage.changesSavedIndicator
		).toBeVisible();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {
				name: 'Publish With Permissions',
			}),
			trigger: page.getByRole('button', {
				name: 'Select and Confirm Publish Settings',
			}),
		});

		await page.getByLabel('Viewable by').selectOption('Site Members');

		await page.getByRole('button', {exact: true, name: 'Publish'}).click();

		await expect(page.getByTitle(title)).toBeVisible();

		await journalPage.assertJournalArticlePermissions(title, [
			{enabled: false, locator: '#guest_ACTION_VIEW'},
		]);
	}
);

autoSaveTest(
	'Web Content version, status and ID are shown and updated after auto save',
	{
		tag: '@LPD-32874',
	},
	async ({journalEditArticlePage, page, site}) => {
		await journalEditArticlePage.goto({siteUrl: site.friendlyUrlPath});

		const title = getRandomString();

		await journalEditArticlePage.fillTitle(title);

		await expect(
			journalEditArticlePage.changesSavedIndicator
		).toBeVisible();

		await page.getByRole('link', {name: 'Basic Information'}).click();

		await expect(page.getByText('1.0')).toBeVisible();

		await expect(page.getByText('Draft', {exact: true})).toBeVisible();

		await expect(page.getByText('ID', {exact: true})).toBeVisible();

		await journalEditArticlePage.publishArticle();

		await page.getByLabel(`Actions for ${title}`).waitFor();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {
				exact: true,
				name: 'Edit',
			}),
			trigger: page.getByLabel(`Actions for ${title}`, {
				exact: true,
			}),
		});

		await expect(async () => {
			await journalEditArticlePage.fillTitle(getRandomString());

			await expect(
				journalEditArticlePage.changesSavedIndicator
			).toBeVisible();
		}).toPass();

		await page.getByRole('link', {name: 'Basic Information'}).click();

		await expect(page.getByText('1.1')).toBeVisible();

		await expect(page.getByText('Draft', {exact: true})).toBeVisible();
	}
);

autoSaveTest(
	'Autosave is not enabled until all required fields are completed',
	{
		tag: '@LPD-34923',
	},
	async ({apiHelpers, journalEditArticlePage, page, site}) => {
		const requiredFieldName = 'RequiredTextField';
		const structureName = 'Structure';

		const dataDefinition = getDataStructureDefinition({
			defaultLanguageId: 'en_US',
			fields: [{name: requiredFieldName, required: true}],
			name: structureName,
		});

		await apiHelpers.dataEngine.createStructure(site.id, dataDefinition);

		await journalEditArticlePage.goto({
			siteUrl: site.friendlyUrlPath,
			structureName,
		});

		await journalEditArticlePage.fillFriendlyURL(getRandomString());

		await page.locator('body').click();

		const errorIndicator = await page.locator(
			'#_com_liferay_journal_web_portlet_JournalPortlet_lockErrorIndicator'
		);

		await expect(errorIndicator).toBeVisible();

		await journalEditArticlePage.fillTitle(getRandomString());

		await expect(errorIndicator).toBeVisible();

		const requiredField = page.getByRole('textbox', {
			name: requiredFieldName,
		});

		await fillAndClickOutside(page, requiredField);

		await expect(
			journalEditArticlePage.changesSavedIndicator
		).toBeVisible();
	}
);

autoSaveTest(
	'Empty option restores in Select from List when using undo/redo',
	{
		tag: '@LPD-35631',
	},
	async ({apiHelpers, journalEditArticlePage, page, site}) => {
		const fieldName = 'SelectFromList';
		const structureName = 'Structure 1';

		const dataDefinition = getDataStructureDefinition({
			defaultLanguageId: 'en_US',
			fields: [
				{
					fieldType: 'select',
					name: fieldName,
					options: {
						en_US: [
							{
								label: 'option1',
								reference: 'option1',
								value: 'option1',
							},
							{
								label: 'option2',
								reference: 'option2',
								value: 'option2',
							},
						],
					},
				},
			],
			name: structureName,
		});

		await apiHelpers.dataEngine.createStructure(site.id, dataDefinition);

		await journalEditArticlePage.goto({
			siteUrl: site.friendlyUrlPath,
			structureName,
		});

		await page.getByLabel(fieldName).click();

		await page.getByRole('option', {name: 'option1'}).click();

		await journalEditArticlePage.undoButton.click();

		await expect(page.getByLabel(fieldName)).toHaveText('Choose an Option');
	}
);

autosaveWithoutPermissionsTest(
	'Web Content is published when Feature Flag LPD-11228 is enabled but LPD-15596 is disabled',
	{
		tag: '@LPD-37606',
	},
	async ({journalEditArticlePage, page, site}) => {
		await journalEditArticlePage.goto({siteUrl: site.friendlyUrlPath});

		const articleTitle = 'Web Content Title';

		journalEditArticlePage.createWCWithBasicPublishButton(articleTitle);

		await expect(page.getByTitle(articleTitle)).toBeVisible();
	}
);
