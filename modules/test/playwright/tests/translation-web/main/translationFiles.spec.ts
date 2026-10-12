/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import createTempFile from '../../../utils/createTempFile';
import getRandomString from '../../../utils/getRandomString';
import getBasicWebContentStructureId from '../../../utils/structured-content/getBasicWebContentStructureId';
import {readFileFromZip} from '../../../utils/zip';
import {journalPagesTest} from '../../journal-web/main/fixtures/journalPagesTest';
import {translationPagesTest} from './fixtures/translationPagesTest';
import {TranslationFilesPage} from './pages/TranslationFilesPage';

const test = mergeTests(
	dataApiHelpersTest,
	isolatedSiteTest,
	journalPagesTest,
	loginTest(),
	translationPagesTest
);

const SPANISH = {
	content: 'WC WebContent Contenido',
	description: 'WC WebContent Descripción',
	title: 'WC WebContent Título',
};

for (const [format, extension, version] of [
	['XLIFF 1.2', 'xlf', '1.2'],
	['XLIFF 2.0', 'xliff', '2.0'],
] as const) {
	test(
		`Import the translation of a web content exported as ${format}`,
		{tag: '@LPD-109576'},
		async ({
			apiHelpers,
			journalPage,
			page,
			site,
			translationFilesPage,
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

			// Export the web content for Spanish

			await journalPage.goto(site.friendlyUrlPath);

			await journalPage.goToJournalArticleAction(
				'Export for Translation',
				title
			);

			const {fileName, filePath} =
				await translationFilesPage.exportTranslation({
					format,
					languages: ['Spanish (Spain)'],
				});

			expect(fileName).toBe(`${title}-en_US.zip`);

			const xliff = await readFileFromZip(
				`${title}-en_US-es_ES.xlf`,
				filePath
			);

			expect(xliff).toContain(`version="${version}"`);

			// Translate the exported file and import it

			const translatedFilePath = createTempFile(
				`${title}-en_US-es_ES.${extension}`,
				TranslationFilesPage.translateXliff(xliff, {
					DDMStructure_content: `<p>${SPANISH.content}</p>`,
					JournalArticle_description: SPANISH.description,
					JournalArticle_title: SPANISH.title,
				})
			);

			await journalPage.goto(site.friendlyUrlPath);

			await journalPage.goToJournalArticleAction(
				'Import Translation',
				title
			);

			await translationFilesPage.importTranslation([translatedFilePath]);

			await expect(page.getByText('1 File Published')).toBeVisible();

			// The web content has the imported translation

			await webContentTranslationPage.open(site, title);

			await webContentTranslationPage.changeTargetLocale('es-ES');

			await webContentTranslationPage.assertTargetFields(SPANISH);
		}
	);
}
