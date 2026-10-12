/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import * as fs from 'fs';
import * as path from 'path';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import createTempFile, {zipFolder} from '../../../utils/createTempFile';
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

test(
	'Import the translations of several web contents at once',
	{tag: '@LPD-109576'},
	async ({
		apiHelpers,
		journalPage,
		page,
		site,
		translationFilesPage,
		webContentTranslationPage,
	}) => {
		const titles = [getRandomString(), getRandomString()];

		for (const title of titles) {
			await apiHelpers.jsonWebServicesJournal.addWebContent({
				ddmStructureId: await getBasicWebContentStructureId(apiHelpers),
				groupId: site.id,
				titleMap: {en_US: title},
			});
		}

		// Export both web contents for Japanese and Spanish

		await journalPage.goto(site.friendlyUrlPath);

		await journalPage.selectItem(0);
		await journalPage.selectItem(1);

		await page
			.getByRole('button', {exact: true, name: 'Export for Translations'})
			.click();

		const {fileName, filePath} =
			await translationFilesPage.exportTranslation({
				languages: ['Japanese (Japan)', 'Spanish (Spain)'],
			});

		expect(fileName).toBe('Web Content Article Translations-en_US.zip');

		const getTranslatedXliff = async (title: string, languageId: string) =>
			TranslationFilesPage.translateXliff(
				await readFileFromZip(
					`${title}-en_US-${languageId}.xlf`,
					filePath
				),
				{JournalArticle_title: `${title} ${languageId}`}
			);

		// Pack the Spanish translation of the first web content and its
		// Japanese one, pointing to a web content that does not exist, in a
		// zip, and keep the Japanese one of the second web content apart

		const zipName = `${getRandomString()}.zip`;

		const zipFolderPath = path.join('tmp', `${process.pid}-${zipName}`);

		fs.mkdirSync(zipFolderPath, {recursive: true});

		fs.writeFileSync(
			path.join(zipFolderPath, `${titles[0]}-en_US-es_ES.xlf`),
			await getTranslatedXliff(titles[0], 'es_ES')
		);

		const invalidFileName = `${titles[0]}-en_US-ja_JP.xlf`;

		fs.writeFileSync(
			path.join(zipFolderPath, invalidFileName),
			(await getTranslatedXliff(titles[0], 'ja_JP')).replace(
				/JournalArticle:\d+/,
				'JournalArticle:0'
			)
		);

		const zipFilePath = await zipFolder(zipFolderPath);

		// Import the zip and the separate file from the web content admin

		await journalPage.goto(site.friendlyUrlPath);

		await page.getByRole('button', {name: 'Options'}).first().click();

		await page.getByRole('menuitem', {name: 'Import Translations'}).click();

		await translationFilesPage.importTranslation([
			zipFilePath,
			createTempFile(
				`${titles[1]}-en_US-ja_JP.xlf`,
				await getTranslatedXliff(titles[1], 'ja_JP')
			),
		]);

		await expect(page.getByText('2 Files Published')).toBeVisible();

		await expect(
			page.getByText('1 file could not be published.')
		).toBeVisible();

		await expect(page.getByText(invalidFileName)).toBeVisible();

		await expect(
			page.getByText(
				'The XLIFF file contains a translation for an invalid model.'
			)
		).toBeVisible();

		// The error report lists only the file that could not be published

		const report = await translationFilesPage.download(
			page.getByRole('link', {name: 'Download CSV Error Report'})
		);

		const reportContent = fs.readFileSync(report.filePath, 'utf-8');

		expect(reportContent).toContain(
			`${invalidFileName},The XLIFF file contains a translation for an invalid model.,${path.basename(zipFilePath)}`
		);
		expect(reportContent).not.toContain(`${titles[0]}-en_US-es_ES.xlf`);

		// Upload the Spanish translation of the second web content afterwards

		await translationFilesPage.uploadAnotherFileButton.click();

		await translationFilesPage.importTranslation([
			createTempFile(
				`${titles[1]}-en_US-es_ES.xlf`,
				await getTranslatedXliff(titles[1], 'es_ES')
			),
		]);

		await expect(page.getByText('1 File Published')).toBeVisible();

		// Every published translation is applied, the invalid one is not

		for (const [title, locale, translatedTitle] of [
			[titles[0], 'es-ES', `${titles[0]} es_ES`],
			[titles[0], 'ja-JP', titles[0]],
			[titles[1], 'es-ES', `${titles[1]} es_ES`],
			[titles[1], 'ja-JP', `${titles[1]} ja_JP`],
		]) {
			await webContentTranslationPage.open(site, title);

			await webContentTranslationPage.changeTargetLocale(locale);

			await webContentTranslationPage.assertTargetFields({
				title: translatedTitle,
			});
		}
	}
);
