/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import getBasicWebContentStructureId from '../../../utils/structured-content/getBasicWebContentStructureId';
import {journalPagesTest} from '../../journal-web/main/fixtures/journalPagesTest';
import {translationPagesTest} from './fixtures/translationPagesTest';

const test = mergeTests(
	apiHelpersTest,
	journalPagesTest,
	loginTest(),
	translationPagesTest
);

const SPANISH = {
	content: 'WC WebContent Contenido',
	description: 'WC WebContent Descripción',
	title: 'WC WebContent Título',
};

// The Translations admin app redirects to the home page when reached on a
// freshly created site right after publishing, so these tests run against the
// Guest site (where the Poshi suite also ran them). Guest is shared, so each
// test uses a unique web content and removes it afterward.

let guestSite: Site;

const createdArticleIds: string[] = [];

test.beforeEach(async ({apiHelpers}) => {
	const company =
		await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
			'liferay.com'
		);

	const group = await apiHelpers.jsonWebServicesGroup.getGroupByKey(
		company.companyId,
		'Guest'
	);

	guestSite = {friendlyUrlPath: '/guest', id: String(group.groupId)} as Site;
});

test.afterEach(async ({apiHelpers}) => {
	for (const articleId of createdArticleIds.splice(0)) {
		await apiHelpers.jsonWebServicesJournal
			.moveArticleToTrash(guestSite.id, articleId)
			.catch(() => {});
	}
});

async function addWebContent(apiHelpers, title: string) {
	const article = await apiHelpers.jsonWebServicesJournal.addWebContent({
		content: 'WC WebContent Content',
		ddmStructureId: await getBasicWebContentStructureId(apiHelpers),
		descriptionMap: {en_US: 'WC WebContent Description'},
		groupId: guestSite.id,
		titleMap: {en_US: title},
	});

	createdArticleIds.push(article.articleId);
}

test('Deleting an approved translation entry keeps the web content translation', async ({
	apiHelpers,
	translationsAdminPage,
	webContentTranslationPage,
}) => {
	const title = getRandomString();

	const entry = `Translation of ${title} to es-ES`;

	await addWebContent(apiHelpers, title);

	// Translate the web content into Spanish and publish

	await webContentTranslationPage.open(guestSite, title);

	await webContentTranslationPage.changeTargetLocale('es-ES');

	await webContentTranslationPage.translateFields(SPANISH);

	await webContentTranslationPage.publish();

	// Delete the approved translation entry from the Translations app

	await translationsAdminPage.goto(guestSite);

	await translationsAdminPage.assertEntry({
		language: 'es-ES',
		status: 'Approved',
		title: entry,
	});

	await translationsAdminPage.deleteEntry(entry);

	await translationsAdminPage.assertNoEntry(entry);

	// The web content keeps its Spanish translation

	await webContentTranslationPage.open(guestSite, title);

	await webContentTranslationPage.changeTargetLocale('es-ES');

	await webContentTranslationPage.assertTargetFields(SPANISH);
});

test('Deleting a draft translation entry removes the web content translation', async ({
	apiHelpers,
	translationsAdminPage,
	webContentTranslationPage,
}) => {
	const title = getRandomString();

	const entry = `Translation of ${title} to es-ES`;

	await addWebContent(apiHelpers, title);

	// Translate the web content into Spanish and save as draft

	await webContentTranslationPage.open(guestSite, title);

	await webContentTranslationPage.changeTargetLocale('es-ES');

	await webContentTranslationPage.translateFields(SPANISH);

	await webContentTranslationPage.saveAsDraft();

	// Delete the draft translation entry from the Translations app

	await translationsAdminPage.goto(guestSite);

	await translationsAdminPage.assertEntry({
		language: 'es-ES',
		status: 'Draft',
		title: entry,
	});

	await translationsAdminPage.deleteEntry(entry);

	await translationsAdminPage.assertNoEntry(entry);

	// The web content no longer has a Spanish translation: the target side
	// falls back to the untranslated source values

	await webContentTranslationPage.open(guestSite, title);

	await webContentTranslationPage.changeTargetLocale('es-ES');

	await webContentTranslationPage.assertTargetFields({
		content: 'WC WebContent Content',
		description: 'WC WebContent Description',
		title,
	});
});
