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

const autoSaveAsDraftTest = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	featureFlagsTest({
		'LPD-11228': true,
		'LPD-15596': true,
	}),
	isolatedSiteTest,
	journalPagesTest,
	loginTest(),
);

autoSaveAsDraftTest(
	'LPD-26854: LockIndicator should have an errorState',
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

autoSaveAsDraftTest(
	'LPD-26856: Web content should be saved as darft after changing the title and the content',
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
		).toBeVisible({timeout: 1000});

		const article = page.getByRole('link', {name: title});

		article.waitFor();

		article.click();

		await expect(page.getByRole('heading', {name: title})).toBeVisible({
			timeout: 1000,
		});

		await expect(page.getByLabel(localizableFieldName)).toHaveValue(
			content,
			{timeout: 1000}
		);
	}
);
autoSaveAsDraftTest(
	'LPD-31072: Translation is removed when using Undo and restored when using Redo',
	async ({journalEditArticlePage, page, site}) => {
		await journalEditArticlePage.goto({siteUrl: site.friendlyUrlPath});

		await journalEditArticlePage.fillTitle(getRandomString());

		const translationButton = page.locator(
			'[id="_com_liferay_journal_web_portlet_JournalPortlet__com_liferay_journal_web_portlet_JournalPortlet_titleMapAsXMLMenu"]'
		);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {
				name: 'Not translated into Catalan.',
			}),
			trigger: translationButton,
		});

		await journalEditArticlePage.fillTitle(getRandomString());

		await clickAndExpectToBeVisible({
			autoClick: false,
			target: page.getByRole('menuitem', {
				name: 'Translated into Catalan.',
			}),
			trigger: translationButton,
		});

		await journalEditArticlePage.undoButton.click();

		await clickAndExpectToBeVisible({
			autoClick: false,
			target: page.getByRole('menuitem', {
				name: 'Not translated into Catalan.',
			}),
			trigger: translationButton,
		});

		await journalEditArticlePage.redoButton.click();

		await clickAndExpectToBeVisible({
			autoClick: false,
			target: page.getByRole('menuitem', {
				name: 'Translated into Catalan.',
			}),
			trigger: translationButton,
		});
	}
);
autoSaveAsDraftTest(
	'LPD-26863: Undo/Redo buttons work with metadata fields',
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

autoSaveAsDraftTest(
	'LPD-26863: Undo/Redo buttons work with content field',
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

autoSaveAsDraftTest(
	'Create a web content selecting permissions in the modal',
	{
		tag: '@LPD-32949',
	},
	async ({journalEditArticlePage, journalPage, page, site}) => {
		await journalEditArticlePage.goto({siteUrl: site.friendlyUrlPath});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {
				name: 'Publish With Permissions',
			}),
			trigger: page.getByRole('button', {
				name: 'Select and Confirm Publish Settings',
			}),
		});

		await expect(
			page.getByText(
				'Please enter a valid title for the default language'
			)
		).toBeVisible({timeout: 1000});

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

		await page.getByText(title, {exact: true}).waitFor();

		await journalPage.assertJournalArticlePermissions(title, [
			{enabled: false, locator: '#guest_ACTION_VIEW'},
		]);
	}
);

autoSaveAsDraftTest(
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

		await expect(page.getByText('1.1')).toBeVisible();

		await expect(page.getByText('Draft', {exact: true})).toBeVisible();
	}
);