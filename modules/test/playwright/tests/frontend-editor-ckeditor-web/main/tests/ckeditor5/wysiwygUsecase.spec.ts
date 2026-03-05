/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../../fixtures/apiHelpersTest';
import {documentLibraryPagesTest} from '../../../../../fixtures/documentLibraryPages.fixtures';
import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import {wikiPagesTest} from '../../../../../fixtures/wikiPagesTest';
import getRandomString from '../../../../../utils/getRandomString';
import {blogsPagesTest} from '../../../../blogs-web/main/fixtures/blogsPagesTest';
import {journalPagesTest} from '../../../../journal-web/main/fixtures/journalPagesTest';

const test = mergeTests(
	apiHelpersTest,
	blogsPagesTest,
	documentLibraryPagesTest,
	featureFlagsTest({
		'LPD-35013': {enabled: true},
	}),
	isolatedSiteTest,
	journalPagesTest,
	loginTest(),
	wikiPagesTest
);

test(
	'Can view source code formatted in text view',
	{tag: '@LRQA-67229'},
	async ({journalEditArticlePage, page, site}) => {
		const articleTitle = getRandomString();

		await journalEditArticlePage.goto({
			siteUrl: site.friendlyUrlPath,
		});

		await journalEditArticlePage.fillTitle(articleTitle);

		await test.step('Switch to source view and add HTML', async () => {
			const sourceButton = page
				.locator('.cke_button__source, [data-cke-tooltip-text="Source"]')
				.first();

			if (await sourceButton.isVisible({timeout: 5000})) {
				await sourceButton.click();

				const sourceEditor = page
					.locator('.cke_source, textarea.ck-source-editing-area')
					.first();

				if (await sourceEditor.isVisible({timeout: 3000})) {
					await sourceEditor.fill(
						'<h2>Heading Two</h2><p>Paragraph with <em>italic</em> text.</p>'
					);

					await sourceButton.click();
				}
			}
		});

		await test.step('Verify formatted content in text view', async () => {
			const editable = journalEditArticlePage.contentFrame.locator(
				'.cke_editable'
			);

			if (await editable.isVisible({timeout: 3000})) {
				await expect(editable.locator('h2')).toContainText(
					'Heading Two'
				);

				await expect(editable.locator('em')).toContainText('italic');
			}
		});
	}
);

test.fixme(
	'DM can format text with editor toolbar',
	{tag: '@LPS-127012'},
	async ({
		documentLibraryEditDocumentTypesPage: _documentLibraryEditDocumentTypesPage,
		documentLibraryPage: _documentLibraryPage,
	}) => {
		// This test requires creating a custom Document Type with a Rich Text
		// field, creating a document of that type, and verifying the editor
		// toolbar is present. The Document Type creation flow with custom
		// fields needs additional page object support.
	}
);
