/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {documentLibraryPagesTest} from '../../../fixtures/documentLibraryPages.fixtures';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {DataApiHelpers} from '../../../helpers/ApiHelpers';
import {DocumentLibraryEditFilePage} from '../../../pages/document-library-web/DocumentLibraryEditFilePage';
import {DocumentLibraryPage} from '../../../pages/document-library-web/DocumentLibraryPage';
import {KnowledgeBaseEditArticlePage} from '../../../pages/knowledge-base-web/KnowledgeBaseEditArticlePage';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
import {blogsPagesTest} from '../../blogs-web/main/fixtures/blogsPagesTest';
import {journalPagesTest} from '../../journal-web/main/fixtures/journalPagesTest';
import getFragmentDefinition from '../../layout-content-page-editor-web/main/utils/getFragmentDefinition';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import getWidgetDefinition from '../../layout-content-page-editor-web/main/utils/getWidgetDefinition';
import addConnectedDepot from './utils/addConnectedDepot';
import gotoDepotInItemSelector from './utils/gotoDepotInItemSelector';

const test = mergeTests(
	blogsPagesTest,
	dataApiHelpersTest,
	documentLibraryPagesTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	journalPagesTest,
	loginTest(),
	pageEditorPagesTest
);

const VIDEO_EMBED_URL = 'https://www.youtube.com/embed/HOdbzGCI5ME';

const VIDEO_TITLE = 'Liferay Digital Experience Platform';

const WEB_CONTENT_DISPLAY_WIDGET =
	'com_liferay_journal_content_web_portlet_JournalContentPortlet';

async function addExternalVideoShortcut(
	documentLibraryEditFilePage: DocumentLibraryEditFilePage,
	documentLibraryPage: DocumentLibraryPage,
	page: Page,
	assetLibraryId: number
) {
	await documentLibraryPage.goto(`/asset-library-${assetLibraryId}`);

	await clickAndExpectToBeVisible({
		autoClick: true,
		target: page.getByRole('menuitem', {
			exact: true,
			name: 'External Video Shortcut',
		}),
		trigger: page.getByRole('button', {exact: true, name: 'New'}),
	});

	await page
		.getByLabel('Video URL')
		.fill('https://www.youtube.com/watch?v=HOdbzGCI5ME');

	// The title comes from the video provider

	await expect(page.getByLabel('Title')).toHaveValue(VIDEO_TITLE);

	await documentLibraryEditFilePage.publishFileEntry();
}

async function expectEmbeddedVideo(
	apiHelpers: DataApiHelpers,
	collectionPath: string,
	title: string,
	getContent: (item: any) => string
) {
	const item = await getItemByTitle(apiHelpers, collectionPath, title);

	expect(getContent(item)).toContain(VIDEO_EMBED_URL);
}

async function getItemByTitle(
	apiHelpers: DataApiHelpers,
	collectionPath: string,
	title: string
) {
	let item;

	// The collections are searched through the index

	await expect(async () => {
		const {items} = await apiHelpers.get(
			`${apiHelpers.baseUrl}headless-delivery/v1.0/${collectionPath}?search=${title}`
		);

		expect(items).toHaveLength(1);

		item = items[0];
	}).toPass();

	return item;
}

async function insertDepotVideo(
	page: Page,
	videoButton: Locator,
	depotName: string
) {
	await videoButton.click();

	const itemSelector = page.frameLocator('iframe[title="Select Item"]');

	await itemSelector
		.getByRole('link', {exact: true, name: 'Documents and Media'})
		.click();

	await gotoDepotInItemSelector(itemSelector, depotName);

	await itemSelector.getByText(VIDEO_TITLE, {exact: true}).click();
}

test(
	'A depot external video can be embedded in the content of a connected site until it is disconnected',
	{tag: ['@LPD-109574', '@LPS-123588']},
	async ({
		apiHelpers,
		blogsEditBlogEntryPage,
		documentLibraryEditFilePage,
		documentLibraryPage,
		journalEditArticlePage,
		page,
		pageEditorPage,
		site,
	}) => {
		const assetLibrary = await addConnectedDepot(apiHelpers, site);

		await addExternalVideoShortcut(
			documentLibraryEditFilePage,
			documentLibraryPage,
			page,
			assetLibrary.id
		);

		await test.step('Embed the video in a blog entry', async () => {
			await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

			const title = getRandomString();

			await blogsEditBlogEntryPage.titleInput.fill(title);

			await blogsEditBlogEntryPage.contentEditor.click();

			await page
				.getByRole('toolbar', {name: 'Add'})
				.getByRole('button', {name: 'Add'})
				.click();

			await insertDepotVideo(
				page,
				page.getByRole('button', {name: 'Insert Video'}),
				assetLibrary.name
			);

			await blogsEditBlogEntryPage.publishButton.click();

			await waitForAlert(page);

			await expectEmbeddedVideo(
				apiHelpers,
				`sites/${site.id}/blog-postings`,
				title,
				(blogPosting) => blogPosting.articleBody
			);
		});

		await test.step('Embed the video in a knowledge base article', async () => {
			const knowledgeBaseEditArticlePage =
				new KnowledgeBaseEditArticlePage(page);

			await knowledgeBaseEditArticlePage.goto(site.friendlyUrlPath);

			const title = getRandomString();

			await knowledgeBaseEditArticlePage.titlePlaceholder.fill(title);

			await insertDepotVideo(
				page,
				page.getByRole('button', {exact: true, name: 'Video'}),
				assetLibrary.name
			);

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: knowledgeBaseEditArticlePage.publishMenuItem,
				trigger: knowledgeBaseEditArticlePage.publishButton,
			});

			await waitForAlert(
				page,
				`Success:${title} was successfully published.`
			);

			await expectEmbeddedVideo(
				apiHelpers,
				`sites/${site.id}/knowledge-base-articles`,
				title,
				(knowledgeBaseArticle) => knowledgeBaseArticle.articleBody
			);
		});

		await test.step('Embed the video in a web content', async () => {
			await journalEditArticlePage.goto({siteUrl: site.friendlyUrlPath});

			const title = getRandomString();

			await journalEditArticlePage.fillTitle(title);

			await insertDepotVideo(
				page,
				page.getByRole('button', {exact: true, name: 'Video'}),
				assetLibrary.name
			);

			await journalEditArticlePage.publishArticle();

			await expectEmbeddedVideo(
				apiHelpers,
				`sites/${site.id}/structured-contents`,
				title,
				(structuredContent) =>
					structuredContent.contentFields[0].contentFieldValue.data
			);
		});

		await test.step('Display the video in an external video fragment', async () => {
			const fragmentId = getRandomString();

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([
					getFragmentDefinition({
						id: fragmentId,
						key: 'BASIC_COMPONENT-external-video',
					}),
				]),
				siteId: site.id,
				title: getRandomString(),
			});

			await pageEditorPage.goto(layout, site.friendlyUrlPath);

			await pageEditorPage.selectFragment(fragmentId);

			await page.getByTitle('Select Video', {exact: true}).click();

			const itemSelector = page.frameLocator('iframe[title="Select"]');

			await itemSelector
				.getByRole('link', {exact: true, name: 'Documents and Media'})
				.click();

			await gotoDepotInItemSelector(itemSelector, assetLibrary.name);

			await itemSelector.getByTitle(VIDEO_TITLE, {exact: true}).click();

			await pageEditorPage.publishPage();

			await expect(async () => {
				await page.goto(
					`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
				);

				await expect(
					page
						.locator('.video-container')
						.frameLocator('iframe')
						.getByText(VIDEO_TITLE)
				).toBeVisible({timeout: 4000});
			}).toPass();
		});

		await test.step('A disconnected site does not offer the depot video', async () => {
			await apiHelpers.headlessAssetLibrary.disconnectSite(
				assetLibrary.externalReferenceCode,
				site.externalReferenceCode
			);

			await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

			await blogsEditBlogEntryPage.contentEditor.click();

			await page
				.getByRole('toolbar', {name: 'Add'})
				.getByRole('button', {name: 'Add'})
				.click();

			await page.getByRole('button', {name: 'Insert Video'}).click();

			const itemSelector = page.frameLocator(
				'iframe[title="Select Item"]'
			);

			await itemSelector
				.getByRole('link', {exact: true, name: 'Documents and Media'})
				.click();

			await itemSelector
				.getByRole('link', {exact: true, name: 'Sites and Libraries'})
				.click();

			await itemSelector
				.getByText('Asset Library', {exact: true})
				.click();

			await expect(
				itemSelector.getByText('No asset libraries were found.')
			).toBeVisible();
			await expect(
				itemSelector.getByText(assetLibrary.name, {exact: true})
			).toHaveCount(0);
		});
	}
);

test(
	'A depot external video can be a related asset of a connected site web content',
	{tag: '@LPD-109574'},
	async ({
		apiHelpers,
		documentLibraryEditFilePage,
		documentLibraryPage,
		journalEditArticlePage,
		page,
		site,
	}) => {
		const assetLibrary = await addConnectedDepot(apiHelpers, site);

		await addExternalVideoShortcut(
			documentLibraryEditFilePage,
			documentLibraryPage,
			page,
			assetLibrary.id
		);

		// Relate the depot video to a site web content

		await journalEditArticlePage.goto({siteUrl: site.friendlyUrlPath});

		const title = getRandomString();

		await journalEditArticlePage.fillTitle(title);

		const content = getRandomString();

		await journalEditArticlePage.fillContent(content);

		await journalEditArticlePage.openRelatedAsset();

		const selectAssetDialog = page.getByRole('dialog', {
			name: 'Select Asset',
		});

		await selectAssetDialog
			.getByRole('checkbox', {name: `Select ${VIDEO_TITLE}`})
			.check();

		await selectAssetDialog
			.getByRole('button', {exact: true, name: 'Select'})
			.click();

		await journalEditArticlePage.publishArticle();

		// Display the web content and its related assets

		const structuredContent = await getItemByTitle(
			apiHelpers,
			`sites/${site.id}/structured-contents`,
			title
		);

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getWidgetDefinition({
					id: getRandomString(),
					widgetConfig: {
						articleId: structuredContent.key,
						contentMetadataAssetAddonEntryKeys:
							'enableRelatedAssets',
						groupId: String(site.id),
					},
					widgetName: WEB_CONTENT_DISPLAY_WIDGET,
				}),
			]),
			siteId: site.id,
			title: getRandomString(),
		});

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

		await expect(page.getByText(content)).toBeVisible();

		await page
			.locator('.asset-links')
			.getByRole('link', {name: VIDEO_TITLE})
			.click();

		await expect(
			page.locator(`iframe[src*="${VIDEO_EMBED_URL}"]`)
		).toBeVisible();
	}
);
