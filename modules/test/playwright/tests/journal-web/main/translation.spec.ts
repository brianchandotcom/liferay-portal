/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {siteSettingsPagesTest} from '../../../fixtures/siteSettingsPagesTest';
import {changeManagementToolbarView} from '../../../utils/changeManagementToolbarView';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getGlobalSiteId from '../../../utils/getGlobalSiteId';
import getRandomString from '../../../utils/getRandomString';
import {PORTLET_URLS} from '../../../utils/portletUrls';
import getBasicWebContentStructureId from '../../../utils/structured-content/getBasicWebContentStructureId';
import {waitForAlert} from '../../../utils/waitForAlert';
import {watchForDialog} from '../../../utils/watchForDialog';
import {journalPagesTest} from './fixtures/journalPagesTest';
import getDataStructureDefinition from './utils/getDataStructureDefinition';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	journalPagesTest,
	loginTest(),
	siteSettingsPagesTest
);

test(
	'Stored XSS payload in a Web Content is sanitized in the translation editor and processes list',
	{
		tag: '@LPD-77709',
	},
	async ({
		apiHelpers,
		journalEditArticleTranslationsPage,
		journalPage,
		page,
		site,
	}) => {
		const watcher = watchForDialog(page);

		try {
			const xssPayload = '<script>alert(123)</script>';
			const contentStructureId =
				await getBasicWebContentStructureId(apiHelpers);

			await apiHelpers.jsonWebServicesJournal.addWebContent({
				content: xssPayload,
				ddmStructureId: contentStructureId,
				descriptionMap: {en_US: xssPayload},
				groupId: site.id,
				titleMap: {en_US: xssPayload},
			});

			await journalPage.goto(site.friendlyUrlPath);

			await changeManagementToolbarView(page, 'List');

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: page.getByRole('menuitem', {
					exact: true,
					name: 'Translate',
				}),
				trigger: page.locator('[aria-label^="Actions for"]').first(),
			});

			await journalEditArticleTranslationsPage.titleInput.fill(
				xssPayload
			);

			await journalEditArticleTranslationsPage.publishButton.click();

			await waitForAlert(page);

			await page.goto(
				`/group${site.friendlyUrlPath}${PORTLET_URLS.translation}`
			);

			watcher.assertNoDialog();
		}
		finally {
			watcher.dispose();
		}
	}
);

test(
	'Language selector remains when editing Global web content from a site with limited languages',
	{
		tag: '@LPD-94459',
	},
	async ({
		apiHelpers,
		journalEditArticlePage,
		page,
		site,
		siteSettingsLocalizationPage,
	}) => {
		const globalSiteId = await getGlobalSiteId(apiHelpers);

		const webContent =
			await apiHelpers.jsonWebServicesJournal.addWebContent({
				ddmStructureId: await getBasicWebContentStructureId(apiHelpers),
				groupId: globalSiteId,
			});

		try {
			await siteSettingsLocalizationPage.setCustomDefaultLanguage(
				'Spanish (Spain)',
				site.friendlyUrlPath
			);

			await siteSettingsLocalizationPage.disableAllLanguagesExceptSp(
				site.friendlyUrlPath
			);

			await page.goto(
				`/group${site.friendlyUrlPath}${PORTLET_URLS.journal}&_com_liferay_journal_web_portlet_JournalPortlet_mvcRenderCommandName=%2Fjournal%2Fedit_article&_com_liferay_journal_web_portlet_JournalPortlet_groupId=${globalSiteId}&_com_liferay_journal_web_portlet_JournalPortlet_articleId=${webContent.articleId}`
			);

			const languageSelector = page.getByRole('combobox', {
				name: 'Select a language',
			});

			await expect(languageSelector).toBeVisible();

			await journalEditArticlePage.changeLanguage('de_DE');

			await expect(languageSelector).toBeVisible();

			await expect(languageSelector).toContainText('de-DE');
		}
		finally {
			await apiHelpers.jsonWebServicesJournal.deleteArticle(
				globalSiteId,
				webContent.articleId
			);
		}
	}
);

test(
	'Structure default values do not translate a new web content into the structure language',
	{
		tag: '@LPD-116782',
	},
	async ({
		apiHelpers,
		journalEditArticlePage,
		journalEditStructureDefaultValuesPage,
		page,
		site,
		siteSettingsLocalizationPage,
	}) => {
		const structureName = getRandomString();

		await apiHelpers.dataEngine.createStructure(
			site.id,
			getDataStructureDefinition({
				defaultLanguageId: 'en_US',
				fields: [
					{name: 'Text'},
					{
						fieldType: 'checkbox',
						localizable: false,
						name: 'Boolean',
					},
					{
						fieldType: 'checkbox',
						name: 'LocalizedBoolean',
					},
				],
				name: structureName,
			})
		);

		await journalEditStructureDefaultValuesPage.goto({
			siteUrl: site.friendlyUrlPath,
			structureName,
		});

		await journalEditStructureDefaultValuesPage.checkCheckboxField(
			'Boolean'
		);

		const text = getRandomString();

		const textField = page.getByRole('textbox', {
			exact: true,
			name: 'Text',
		});

		await textField.fill(text);

		const defaultTitle = getRandomString();

		await journalEditArticlePage.titleInput.fill(defaultTitle);

		await journalEditStructureDefaultValuesPage.save();

		await siteSettingsLocalizationPage.setCustomDefaultLanguage(
			'ja_JP',
			site.friendlyUrlPath
		);

		await journalEditArticlePage.goto({
			siteUrl: site.friendlyUrlPath,
			structureName,
		});

		const booleanField = page.getByRole('checkbox', {
			exact: true,
			name: 'Boolean',
		});

		await expect(booleanField).toBeChecked();
		await expect(journalEditArticlePage.titleInput).toHaveValue(
			defaultTitle
		);
		await expect(textField).toHaveValue(text);

		const englishNotTranslatedOption = page.getByRole('option', {
			name: 'English Language: Not Translated',
		});
		const languageSelector = page.getByLabel('Select a language, current');

		await clickAndExpectToBeVisible({
			autoClick: false,
			target: englishNotTranslatedOption,
			trigger: languageSelector,
		});

		await page.keyboard.press('Escape');

		const title = getRandomString();

		await journalEditArticlePage.fillTitle(title);

		await journalEditArticlePage.changeLanguage('en_US');

		await expect(textField).toHaveValue(text);

		await journalEditArticlePage.changeLanguage('ja_JP');

		await journalEditArticlePage.publishArticle();

		await waitForAlert(page, `Success:${title} was created successfully.`);

		await page.getByRole('link', {exact: true, name: title}).click();

		await journalEditArticlePage.expandFieldsPanel();

		await expect(booleanField).toBeChecked();
		await expect(textField).toHaveValue(text);

		await clickAndExpectToBeVisible({
			autoClick: false,
			target: englishNotTranslatedOption,
			trigger: languageSelector,
		});
	}
);
