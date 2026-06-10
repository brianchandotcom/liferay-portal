/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import getBasicWebContentStructureId from '../../../utils/structured-content/getBasicWebContentStructureId';
import {journalPagesTest} from '../../journal-web/main/fixtures/journalPagesTest';
import {translationPagesTest} from './fixtures/translationPagesTest';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	journalPagesTest,
	loginTest(),
	translationPagesTest
);

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
