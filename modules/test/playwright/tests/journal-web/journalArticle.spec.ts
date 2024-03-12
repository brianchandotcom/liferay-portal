/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import fillAndClickOutside from '../../utils/fillAndClickOutside';
import getRandomString from '../../utils/getRandomString';
import {setSiteUrl} from '../../utils/siteUrl';
import {journalPagesTest} from './fixtures/journalPagesTest';
import getDataStructureDefinition from './utils/getDataStructureDefinition';

const translateNameAndMetadataFields = async (
	page,
	structureName = 'Basic Web Content'
) => {
	await fillAndClickOutside(
		page,
		page.getByLabel('Friendly URL', {exact: true})
	);
	await fillAndClickOutside(
		page,
		page.getByPlaceholder(`Untitled ${structureName}`)
	);
	await fillAndClickOutside(
		page,
		page
			.frameLocator(':text("Description")+div iframe')
			.getByRole('textbox')
	);
};

const baseTest = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	isolatedSiteTest,
	journalPagesTest
);

const bulkTest = mergeTests(
	baseTest,
	featureFlagsTest({
		'LPD-16469': true,
	})
);

const scheduleTest = mergeTests(
	baseTest,
	featureFlagsTest({
		'LPD-15596': true,
	})
);

const translationTest = mergeTests(
	baseTest,
	featureFlagsTest({
		'LPD-11253': true,
		'LPS-114700': true,
	})
);

translationTest(
	'LPD-17245: Add error message in Translation for concurrent users',
	async ({
		journalEditArticlePage,
		journalEditArticleTranslationsPage,
		journalPage,
		page,
	}) => {
		await journalPage.goto();

		const title = getRandomString();

		await journalEditArticlePage.publishNewBasicArticle(title);

		const article = page
			.locator(
				'#_com_liferay_journal_web_portlet_JournalPortlet_articlesSearchContainer .list-group-item'
			)
			.filter({hasText: title});

		await article.waitFor();

		const editBasicArticleTranslationUrl =
			await journalEditArticleTranslationsPage.editBasicArticleTranslations(
				title,
				''
			);

		await journalEditArticlePage.editAndPublishExistingBasicArticle(title);

		await journalEditArticleTranslationsPage.assertErrorInEditBasicArticleTranslations(
			editBasicArticleTranslationUrl
		);

		await journalPage.deleteJournalArticle(title);
	}
);

bulkTest(
	'LPD-17782: This is a test for bulk permissions of web content',
	async ({journalEditArticlePage, journalPage, page}) => {
		const PERMISSIONS_LOCATORS = [
			{enabled: true, locator: '#guest_ACTION_DELETE'},
			{enabled: true, locator: '#guest_ACTION_PERMISSIONS'},
		];

		await journalPage.goto();

		const title1 = getRandomString();
		const title2 = getRandomString();

		await journalEditArticlePage.publishNewBasicArticle(title1);

		const article1 = page
			.locator(
				'#_com_liferay_journal_web_portlet_JournalPortlet_articlesSearchContainer .list-group-item'
			)
			.filter({hasText: title1});

		await article1.waitFor();

		await journalEditArticlePage.publishNewBasicArticle(title2);

		const article2 = page
			.locator(
				'#_com_liferay_journal_web_portlet_JournalPortlet_articlesSearchContainer .list-group-item'
			)
			.filter({hasText: title2});

		await article2.waitFor();

		await journalPage.setJournalArticlePermissions(
			[article1, article2],
			['#guest_ACTION_DELETE', '#guest_ACTION_PERMISSIONS']
		);

		await journalPage.assertJournalArticlePermissions(
			title1,
			PERMISSIONS_LOCATORS
		);
		await journalPage.assertJournalArticlePermissions(
			title2,
			PERMISSIONS_LOCATORS
		);

		await journalPage.deleteJournalArticle(title1);
		await journalPage.deleteJournalArticle(title2);
	}
);

translationTest(
	'LPD-19627: Translate several fields in a Basic Web Content and check how many fields have been translated',
	async ({journalEditArticlePage, journalPage, page}) => {
		await journalPage.goto();

		const title = getRandomString();

		await journalEditArticlePage.goToCreateNewBasicArticle(title);

		const translationButton = page.getByRole('combobox', {
			name: 'Select a language',
		});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('option', {
				name: 'Catalan Language: Not Translated',
			}),
			trigger: translationButton,
		});

		await translateNameAndMetadataFields(page);

		await translationButton.click();

		await expect(
			page.getByRole('option', {
				name: 'Catalan Language: Translating 3/4',
			})
		).toBeVisible({timeout: 1000});
	}
);

translationTest(
	'LPD-19627: Translate all fields of a Web Content based on a custom structure with repeatable fields',
	async ({apiHelpers, journalEditArticlePage, journalPage, page, site}) => {
		await setSiteUrl(page, site.friendlyUrlPath);

		const localizableFieldName = 'Text5678';
		const structureName = 'Structure 1';

		const dataDefinition = getDataStructureDefinition({
			defaultLanguageId: 'en_US',
			fields: [{name: localizableFieldName, repeatable: true}],
			name: structureName,
		});

		await apiHelpers.dataEngine.createStructure(site.id, dataDefinition);

		await journalPage.goto();

		await journalEditArticlePage.goto(structureName);

		const translationButton = page.getByRole('combobox', {
			name: 'Select a language',
		});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('option', {
				name: 'Catalan Language: Not Translated',
			}),
			trigger: translationButton,
		});

		await translateNameAndMetadataFields(page, structureName);

		const localizableField = page.getByRole('textbox', {
			name: localizableFieldName,
		});

		await fillAndClickOutside(page, localizableField);

		await translationButton.click();

		await expect(
			page.getByRole('option', {name: 'Catalan Language: Translated'})
		).toBeVisible({timeout: 1000});

		await page.getByLabel('Add Duplicate Field Text').click();

		await translationButton.click();

		await expect(
			page.getByRole('option', {
				name: 'Catalan Language: Translating 4/5',
			})
		).toBeVisible({timeout: 1000});

		await fillAndClickOutside(
			page,
			page.locator('input.ddm-field-text').nth(1)
		);

		translationButton.click();

		await expect(
			page.getByRole('option', {name: 'Catalan Language: Translated'})
		).toBeVisible({timeout: 1000});
	}
);

translationTest(
	'LPD-19627: A non-localizabled field is disabled when another translation language is selected',
	async ({apiHelpers, journalEditArticlePage, journalPage, page, site}) => {
		await setSiteUrl(page, site.friendlyUrlPath);

		const nonLocalizableFieldName = 'Text1234';
		const structureName = 'Structure 1';

		const dataDefinition = getDataStructureDefinition({
			defaultLanguageId: 'en_US',
			fields: [{localizable: false, name: nonLocalizableFieldName}],
			name: structureName,
		});

		await apiHelpers.dataEngine.createStructure(site.id, dataDefinition);

		await journalPage.goto();

		await journalEditArticlePage.goto(structureName);

		const translationButton = page.getByRole('combobox', {
			name: 'Select a language',
		});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('option', {
				name: 'Catalan Language: Not Translated',
			}),
			trigger: translationButton,
		});

		await expect(
			page.getByRole('textbox', {
				name: nonLocalizableFieldName,
			})
		).toBeDisabled();
	}
);

scheduleTest(
	'Create a web content selecting permissions in the modal',
	async ({journalEditArticlePage, journalPage, page}) => {
		await journalPage.goto();

		await journalEditArticlePage.goToCreateNewBasicArticle();

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

		await journalEditArticlePage.titlePlaceholder.fill(title);

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

scheduleTest(
	'Change permission of a web content in edition mode',
	async ({journalEditArticlePage, journalPage, page}) => {
		await journalPage.goto();

		const title = getRandomString();

		await journalEditArticlePage.goToCreateNewBasicArticle(title);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {
				name: 'Publish With Permissions',
			}),
			trigger: page.getByRole('button', {
				name: 'Select and Confirm Publish Settings',
			}),
		});

		await page.getByRole('button', {exact: true, name: 'Publish'}).click();

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

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {
				name: 'Permissions',
			}),
			trigger: page.getByRole('button', {
				name: 'Options',
			}),
		});

		await journalPage.setPermissions(['#power-user_ACTION_DELETE']);

		await journalPage.assertJournalArticlePermissions(title, [
			{enabled: true, locator: '#power-user_ACTION_DELETE'},
		]);
	}
);
