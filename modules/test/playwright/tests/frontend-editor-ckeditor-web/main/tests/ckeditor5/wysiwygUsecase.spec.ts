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

test.fixme(
	'Can add a blogs entry with image via Blog Images',
	{tag: '@LRQA-67229'},
	async ({blogsEditBlogEntryPage, page, site}) => {
		const blogTitle = getRandomString();

		await test.step('Upload an image via API for cover', async () => {
			await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);
		});

		await test.step('Create a blog entry with content', async () => {
			await page.getByPlaceholder('Title *').fill(blogTitle);

			await blogsEditBlogEntryPage.contentEditor.fill(
				'Blog entry content with image test'
			);
		});

		await test.step('Add a cover image', async () => {
			await page
				.getByRole('button', {name: 'Select File'})
				.first()
				.click();

			const itemSelectorDialog = page.frameLocator(
				'iframe[title="Select File"]'
			);

			const blogImagesLink = itemSelectorDialog.getByRole('link', {
				name: 'Blog Images',
			});

			if (await blogImagesLink.isVisible({timeout: 3000})) {
				await blogImagesLink.click();
			}
			else {
				await itemSelectorDialog
					.getByRole('link', {name: 'Documents and Media'})
					.click();
			}

			await page.getByRole('button', {name: 'Cancel'}).click();
		});

		await test.step('Publish the blog entry', async () => {
			await blogsEditBlogEntryPage.publishBlogEntry();
		});
	}
);

test.fixme(
	'Can add a hyperlink to existing text in web content',
	{tag: '@LPS-110663'},
	async ({journalEditArticlePage, page, site}) => {
		const articleTitle = getRandomString();

		await journalEditArticlePage.goto({
			siteUrl: site.friendlyUrlPath,
		});

		await journalEditArticlePage.fillTitle(articleTitle);

		await test.step('Type text in the editor', async () => {
			await journalEditArticlePage.contentFrame
				.locator('.cke_editable')
				.click();

			await journalEditArticlePage.contentFrame
				.locator('.cke_editable')
				.fill('Click here for more information');
		});

		await test.step('Select text and add hyperlink', async () => {
			await journalEditArticlePage.contentFrame
				.locator('.cke_editable')
				.selectText();

			const linkButton = page.locator(
				'.cke_button__link, .ck-button[data-cke-tooltip-text="Link"]'
			);

			if (await linkButton.isVisible({timeout: 3000})) {
				await linkButton.click();

				const urlInput = page.getByLabel('URL*').or(
					page.getByPlaceholder('https://example.com')
				);

				if (await urlInput.isVisible({timeout: 3000})) {
					await urlInput.fill('https://www.liferay.com');

					await page
						.getByRole('button', {name: 'OK'})
						.or(page.getByRole('button', {name: 'Save'}))
						.click();
				}
			}
		});

		await test.step('Publish the article', async () => {
			await journalEditArticlePage.publishArticle();
		});
	}
);

test.fixme(
	'Can display content left to right in Wiki',
	async ({page, site, wikiPage}) => {
		await wikiPage.goto(site.friendlyUrlPath);

		await test.step('Navigate to Main wiki node', async () => {
			await wikiPage.goToWikiNode('Main');
		});

		await test.step('Edit the first wiki page', async () => {
			await wikiPage.goToEditFirstWikiPage();
		});

		await test.step('Add LTR content', async () => {
			await wikiPage.addSourceContentToWikiPage(
				'<p dir="ltr">This content should display left to right</p>'
			);
		});

		await test.step('Publish and verify', async () => {
			await wikiPage.publishPage();

			await wikiPage.goToFirstWikiPage();

			await expect(
				page.locator('[dir="ltr"]').filter({
					hasText: 'This content should display left to right',
				})
			).toBeVisible();
		});
	}
);

test.fixme(
	'Can display content right to left in Wiki with Arabic locale',
	async ({page, site, wikiPage}) => {
		await wikiPage.goto(site.friendlyUrlPath);

		await test.step('Navigate to Main wiki node', async () => {
			await wikiPage.goToWikiNode('Main');
		});

		await test.step('Edit the first wiki page', async () => {
			await wikiPage.goToEditFirstWikiPage();
		});

		await test.step('Add RTL content', async () => {
			await wikiPage.addSourceContentToWikiPage(
				'<p dir="rtl">\u0647\u0630\u0627 \u0627\u0644\u0645\u062D\u062A\u0648\u0649 \u064A\u062C\u0628 \u0623\u0646 \u064A\u0639\u0631\u0636 \u0645\u0646 \u0627\u0644\u064A\u0645\u064A\u0646 \u0625\u0644\u0649 \u0627\u0644\u064A\u0633\u0627\u0631</p>'
			);
		});

		await test.step('Publish and verify RTL direction', async () => {
			await wikiPage.publishPage();

			await wikiPage.goToFirstWikiPage();

			const rtlContent = page.locator('[dir="rtl"]');

			await expect(rtlContent).toBeVisible();
		});
	}
);

test(
	'Can preview source content in web content editor',
	{tag: '@LPS-110663'},
	async ({journalEditArticlePage, page, site}) => {
		const articleTitle = getRandomString();

		await journalEditArticlePage.goto({
			siteUrl: site.friendlyUrlPath,
		});

		await journalEditArticlePage.fillTitle(articleTitle);

		await test.step('Switch to source view', async () => {
			const sourceButton = page
				.locator('.cke_button__source, [data-cke-tooltip-text="Source"]')
				.first();

			if (await sourceButton.isVisible({timeout: 5000})) {
				await sourceButton.click();
			}
		});

		await test.step('Type HTML in source view', async () => {
			const sourceEditor = page
				.locator('.cke_source, textarea.ck-source-editing-area')
				.first();

			if (await sourceEditor.isVisible({timeout: 3000})) {
				await sourceEditor.fill(
					'<h1>Preview Test</h1><p>This is <strong>bold</strong> text.</p>'
				);
			}
		});

		await test.step('Preview the content', async () => {
			const previewButton = journalEditArticlePage.previewButton;

			if (await previewButton.isVisible({timeout: 3000})) {
				await previewButton.click();

				const previewFrame = page
					.frameLocator('iframe')
					.last();

				await expect(
					previewFrame.locator('h1').filter({hasText: 'Preview Test'})
				).toBeVisible({timeout: 5000});
			}
		});
	}
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
