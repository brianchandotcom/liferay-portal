/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';
import {SystemSettingsPage} from '../../../pages/configuration-admin-web/SystemSettingsPage';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import getBasicWebContentStructureId from '../../../utils/structured-content/getBasicWebContentStructureId';
import {journalPagesTest} from '../../journal-web/main/fixtures/journalPagesTest';
import {translationPagesTest} from './fixtures/translationPagesTest';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	journalPagesTest,
	loginTest(),
	systemSettingsPageTest,
	translationPagesTest
);

const GOOGLE_CLOUD_TRANSLATOR_CONFIGURATION_PID =
	'com.liferay.translation.translator.google.cloud.internal.configuration.GoogleCloudTranslatorConfiguration';

async function enableGoogleCloudTranslator(
	systemSettingsPage: SystemSettingsPage
) {
	await systemSettingsPage.goToConfiguration(
		GOOGLE_CLOUD_TRANSLATOR_CONFIGURATION_PID
	);

	await systemSettingsPage.page.getByLabel('Enabled').check();

	await systemSettingsPage.page
		.getByLabel('Service Account Private Key')
		.fill('{"type": "service_account"}');

	await systemSettingsPage.saveAndWaitForAlert();
}

async function resetGoogleCloudTranslator(
	systemSettingsPage: SystemSettingsPage
) {
	await systemSettingsPage.goToConfiguration(
		GOOGLE_CLOUD_TRANSLATOR_CONFIGURATION_PID
	);

	await systemSettingsPage.resetToDefaultValues();
}

test('Can translate the fields of a web content into another language', async ({
	apiHelpers,
	site,
	webContentTranslationPage,
}) => {
	const title = 'WC WebContent Title';

	// Create a basic web content through the API

	await apiHelpers.jsonWebServicesJournal.addWebContent({
		content: 'WC WebContent Content',
		ddmStructureId: await getBasicWebContentStructureId(apiHelpers),
		descriptionMap: {en_US: 'WC WebContent Description'},
		groupId: site.id,
		titleMap: {en_US: title},
	});

	// Translate it to Spanish

	await webContentTranslationPage.open(site, title);

	await webContentTranslationPage.changeTargetLocale('es-ES');

	await webContentTranslationPage.translateFields({
		content: 'WC WebContent Contenido',
		description: 'WC WebContent Descripción',
		title: 'WC WebContent Título',
	});

	await webContentTranslationPage.publish();

	// The translation persists when the editor is reopened

	await webContentTranslationPage.open(site, title);

	await webContentTranslationPage.changeTargetLocale('es-ES');

	await webContentTranslationPage.assertTargetFields({
		content: 'WC WebContent Contenido',
		description: 'WC WebContent Descripción',
		title: 'WC WebContent Título',
	});
});

test('Editing a web content shows its latest values in the side-by-side translation', async ({
	apiHelpers,
	journalEditArticlePage,
	site,
	webContentTranslationPage,
}) => {
	const title = 'WC WebContent Title';

	// Create a basic web content and translate it to Spanish

	await apiHelpers.jsonWebServicesJournal.addWebContent({
		content: 'WC WebContent Content',
		ddmStructureId: await getBasicWebContentStructureId(apiHelpers),
		descriptionMap: {en_US: 'WC WebContent Description'},
		groupId: site.id,
		titleMap: {en_US: title},
	});

	await webContentTranslationPage.open(site, title);

	await webContentTranslationPage.changeTargetLocale('es-ES');

	await webContentTranslationPage.translateFields({
		content: 'WC WebContent Contenido',
		description: 'WC WebContent Descripción',
		title: 'WC WebContent Título',
	});

	await webContentTranslationPage.publish();

	// Edit the Spanish title and content from the web content form

	await journalEditArticlePage.editArticle(title);

	await journalEditArticlePage.changeLanguage('es_ES');

	await journalEditArticlePage.fillTitle('WC WebContent Título editar');

	await journalEditArticlePage.fillContent('WC WebContent Contenido editar');

	await journalEditArticlePage.publishArticle(true);

	await expect(journalEditArticlePage.publishButton).toBeHidden();

	// The side-by-side editor reflects the edited values

	await webContentTranslationPage.open(site, title);

	await webContentTranslationPage.changeTargetLocale('es-ES');

	await webContentTranslationPage.assertTargetFields({
		content: 'WC WebContent Contenido editar',
		description: 'WC WebContent Descripción',
		title: 'WC WebContent Título editar',
	});
});

test('Translating from a translated base language adds a second translation', async ({
	apiHelpers,
	site,
	webContentTranslationPage,
}) => {
	const title = 'WC WebContent Title';

	await apiHelpers.jsonWebServicesJournal.addWebContent({
		content: 'WC WebContent Content',
		ddmStructureId: await getBasicWebContentStructureId(apiHelpers),
		descriptionMap: {en_US: 'WC WebContent Description'},
		groupId: site.id,
		titleMap: {en_US: title},
	});

	// Translate from English into Spanish

	await webContentTranslationPage.open(site, title);

	await webContentTranslationPage.changeTargetLocale('es-ES');

	await webContentTranslationPage.translateFields({
		content: 'WC WebContent Contenido',
		description: 'WC WebContent Descripción',
		title: 'WC WebContent Título',
	});

	await webContentTranslationPage.publish();

	// Using Spanish as the base language, translate into Japanese

	await webContentTranslationPage.open(site, title);

	await webContentTranslationPage.changeBaseLocale('es-ES');

	await webContentTranslationPage.changeTargetLocale('ja-JP');

	await webContentTranslationPage.translateFields({
		content: 'wc webcontentコンテンツ',
		description: 'wc webcontent記述',
		title: 'wc webcontentタイトル',
	});

	await webContentTranslationPage.publish();

	// Both translations persist and switching the target locale shows each one

	await webContentTranslationPage.open(site, title);

	await webContentTranslationPage.changeTargetLocale('es-ES');

	await webContentTranslationPage.assertTargetFields({
		content: 'WC WebContent Contenido',
		description: 'WC WebContent Descripción',
		title: 'WC WebContent Título',
	});

	await webContentTranslationPage.changeTargetLocale('ja-JP');

	await webContentTranslationPage.assertTargetFields({
		content: 'wc webcontentコンテンツ',
		description: 'wc webcontent記述',
		title: 'wc webcontentタイトル',
	});
});

test(
	'Auto translate fills the fields of a web content while a translator is enabled',
	{tag: '@LPD-109576'},
	async ({
		apiHelpers,
		page,
		site,
		systemSettingsPage,
		webContentTranslationPage,
	}) => {
		const title = getRandomString();

		await apiHelpers.jsonWebServicesJournal.addWebContent({
			content: 'WC WebContent Content',
			ddmStructureId: await getBasicWebContentStructureId(apiHelpers),
			descriptionMap: {en_US: 'WC WebContent Description'},
			groupId: site.id,
			titleMap: {en_US: title},
		});

		try {
			await enableGoogleCloudTranslator(systemSettingsPage);

			// The provider is stubbed: the response translates each requested
			// field by its name and keeps the HTML flags of the request

			const translations: {[key: string]: string} = {
				content: '<p>WC WebContent Contenido</p>',
				description: '<p>WC WebContent Descripción</p>',
				title: 'WC WebContent Título',
			};

			await page.route(
				'**/o/translation/auto_translate',
				async (route) => {
					const {fields, html} = route.request().postDataJSON();

					await route.fulfill({
						body: JSON.stringify({
							fields: Object.fromEntries(
								Object.keys(fields).map((name) => [
									name,
									translations[
										Object.keys(translations).find((key) =>
											name.includes(`_${key}--`)
										)!
									],
								])
							),
							html,
						}),
						contentType: 'application/json',
						status: 200,
					});
				}
			);

			// Auto translate the web content into Spanish and publish

			await webContentTranslationPage.open(site, title);

			await webContentTranslationPage.changeTargetLocale('es-ES');

			await webContentTranslationPage.autoTranslate();

			await webContentTranslationPage.assertTargetFields({
				content: 'WC WebContent Contenido',
				description: 'WC WebContent Descripción',
				title: 'WC WebContent Título',
			});

			await webContentTranslationPage.publish();

			await page.unroute('**/o/translation/auto_translate');
		}
		finally {
			await resetGoogleCloudTranslator(systemSettingsPage);
		}

		// Once the translator is disabled, the translation persists and Auto
		// Translate is no longer offered

		await webContentTranslationPage.open(site, title);

		await webContentTranslationPage.changeTargetLocale('es-ES');

		await webContentTranslationPage.assertTargetFields({
			content: 'WC WebContent Contenido',
			description: 'WC WebContent Descripción',
			title: 'WC WebContent Título',
		});

		await expect(
			webContentTranslationPage.autoTranslateButton
		).toBeHidden();
	}
);

test(
	'The translation actions of a web content are only offered on a site with more than one language',
	{tag: '@LPD-109576'},
	async ({apiHelpers, journalPage, page, site}) => {
		const title = getRandomString();

		await apiHelpers.jsonWebServicesJournal.addWebContent({
			ddmStructureId: await getBasicWebContentStructureId(apiHelpers),
			groupId: site.id,
			titleMap: {en_US: title},
		});

		const assertTranslationActions = async (count: number) => {
			await journalPage.goto(site.friendlyUrlPath);

			await clickAndExpectToBeVisible({
				target: page.getByRole('menuitem', {exact: true, name: 'Edit'}),
				trigger: page.getByLabel(`Actions for ${title}`, {exact: true}),
			});

			for (const action of [
				'Export for Translation',
				'Import Translation',
				'Translate',
			]) {
				await expect(
					page.getByRole('menuitem', {exact: true, name: action})
				).toHaveCount(count);
			}
		};

		// A site with a single language has nothing to translate into

		await apiHelpers.headlessAdminSite.putSite(site.externalReferenceCode, {
			defaultLanguageId: 'en_US',
			inheritLocales: false,
			locales: ['en_US'],
		});

		await assertTranslationActions(0);

		// One more language makes the translation actions available

		await apiHelpers.headlessAdminSite.putSite(site.externalReferenceCode, {
			locales: ['en_US', 'es_ES'],
		});

		await assertTranslationActions(1);
	}
);
