/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

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
