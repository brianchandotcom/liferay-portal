/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {displayPageTemplatesPagesTest} from '../../fixtures/displayPageTemplatesPagesTest';
import {documentLibraryPagesTest} from '../../fixtures/documentLibraryPages.fixtures';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {ApiHelpers} from '../../helpers/ApiHelpers';
import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';
import getBasicWebContentStructureId from '../../utils/structured-content/getBasicWebContentStructureId';
import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';
import {blogsPagesTest} from '../blogs-web/fixtures/blogsPagesTest';
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';
import {JournalEditArticlePage} from '../journal-web/pages/JournalEditArticlePage';
import {JournalPage} from '../journal-web/pages/JournalPage';

const test = mergeTests(
	apiHelpersTest,
	blogsPagesTest,
	displayPageTemplatesPagesTest,
	documentLibraryPagesTest,
	isolatedSiteTest,
	journalPagesTest,
	loginTest()
);

const testInfoPanel = mergeTests(
	test,
	featureFlagsTest({
		'LPS-189856': true,
	})
);

async function addBasicJournalArticleWithSpecificDisplayPageTemplate(
	apiHelpers: ApiHelpers,
	displayPageTemplateName: string,
	journalArticleTitle: string,
	journalEditArticlePage: JournalEditArticlePage,
	journalPage: JournalPage,
	page: Page,
	site: Site
) {
	const contentStructureId = await getBasicWebContentStructureId(apiHelpers);

	await apiHelpers.jsonWebServicesJournal.addWebContent({
		ddmStructureId: contentStructureId,
		groupId: site.id,
		titleMap: {en_US: journalArticleTitle},
	});

	await journalPage.goto(site.friendlyUrlPath);

	await journalEditArticlePage.editArticle(journalArticleTitle);

	await journalEditArticlePage.selectSpecificDisplayPage(
		displayPageTemplateName
	);

	await page.getByRole('button', {name: 'Publish'}).click();

	await waitForSuccessAlert(
		page,
		`Success:${journalArticleTitle} was updated successfully.`
	);
}

test.describe('Usages', () => {
	test(
		'Can assign usage to default even if the default display page template does not exist',
		{
			tag: '@LPS-121199',
		},
		async ({
			apiHelpers,
			displayPageTemplatesPage,
			journalEditArticlePage,
			journalPage,
			page,
			site,
		}) => {

			// Create a display page template for Basic Web Content

			await displayPageTemplatesPage.goto(site.friendlyUrlPath);

			const displayPageTemplateName =
				'basicWebContentDpt' + getRandomInt();

			await displayPageTemplatesPage.createTemplate({
				contentSubtype: 'Basic Web Content',
				contentType: 'Web Content Article',
				name: displayPageTemplateName,
			});

			// Create a Basic Web Content and set the template as its specific display page template

			const journalArticleTitle = getRandomString();

			await addBasicJournalArticleWithSpecificDisplayPageTemplate(
				apiHelpers,
				displayPageTemplateName,
				journalArticleTitle,
				journalEditArticlePage,
				journalPage,
				page,
				site
			);

			// View display page template usages and check that option 'Assign to Default' is displayed

			await displayPageTemplatesPage.goto(site.friendlyUrlPath);

			await displayPageTemplatesPage.viewUsages(displayPageTemplateName);

			await expect(page.getByText(journalArticleTitle)).toBeVisible();

			const firstRowCheckbox = page
				.getByRole('row')
				.getByRole('checkbox')
				.first();

			await firstRowCheckbox.click();

			await page.getByRole('button', {name: 'Actions'}).click();

			await expect(
				page.getByRole('menuitem', {
					exact: true,
					name: 'Assign to Default',
				})
			).toBeVisible();
		}
	);

	test(
		'Can assign usage to default even if the default display page template exists',
		{
			tag: '@LPS-121199',
		},
		async ({
			apiHelpers,
			displayPageTemplatesPage,
			journalEditArticlePage,
			journalPage,
			page,
			site,
		}) => {

			// Create a display page template for Basic Web Content and mark as default

			await displayPageTemplatesPage.goto(site.friendlyUrlPath);

			const defaultDisplayPageTemplateName = getRandomString();

			await displayPageTemplatesPage.createTemplate({
				contentSubtype: 'Basic Web Content',
				contentType: 'Web Content Article',
				name: defaultDisplayPageTemplateName,
			});

			await displayPageTemplatesPage.markAsDefault(
				defaultDisplayPageTemplateName
			);

			// Create a display page template for Basic Web Content

			const displayPageTemplateName =
				'basicWebContentDpt' + getRandomInt();

			await displayPageTemplatesPage.createTemplate({
				contentSubtype: 'Basic Web Content',
				contentType: 'Web Content Article',
				name: displayPageTemplateName,
			});

			// Create a Basic Web Content and set the template as its specific display page template

			const journalArticleTitle = getRandomString();

			await addBasicJournalArticleWithSpecificDisplayPageTemplate(
				apiHelpers,
				displayPageTemplateName,
				journalArticleTitle,
				journalEditArticlePage,
				journalPage,
				page,
				site
			);

			// View display page template usages and check that option 'Assign to Default (defaultTemplate)' is displayed

			await displayPageTemplatesPage.goto(site.friendlyUrlPath);

			await displayPageTemplatesPage.viewUsages(displayPageTemplateName);

			await expect(page.getByText(journalArticleTitle)).toBeVisible();

			const firstRowCheckbox = page
				.getByRole('row')
				.getByRole('checkbox')
				.first();

			await firstRowCheckbox.click();

			await page.getByRole('button', {name: 'Actions'}).click();

			await expect(
				page.getByRole('menuitem', {
					exact: true,
					name: `Assign to Default (${defaultDisplayPageTemplateName})`,
				})
			).toBeVisible();
		}
	);

	test(
		'Can assign multiple usages to default',
		{
			tag: '@LPS-121199',
		},
		async ({
			apiHelpers,
			displayPageTemplatesPage,
			journalEditArticlePage,
			journalPage,
			page,
			site,
		}) => {

			// Create a display page template for Basic Web Content

			await displayPageTemplatesPage.goto(site.friendlyUrlPath);

			const displayPageTemplateName = getRandomString();

			await displayPageTemplatesPage.createTemplate({
				contentSubtype: 'Basic Web Content',
				contentType: 'Web Content Article',
				name: displayPageTemplateName,
			});

			// Create three basic web contents and set its specific display page templates

			for (let i = 1; i < 4; i++) {
				const journalArticleTitle = getRandomString();

				await addBasicJournalArticleWithSpecificDisplayPageTemplate(
					apiHelpers,
					displayPageTemplateName,
					journalArticleTitle,
					journalEditArticlePage,
					journalPage,
					page,
					site
				);
			}

			// View usages of display page template

			await displayPageTemplatesPage.goto(site.friendlyUrlPath);

			await displayPageTemplatesPage.viewUsages(displayPageTemplateName);

			// Assign to default all the usages leaving the usages empty

			for (const rowCheckbox of await page
				.getByRole('row')
				.getByRole('checkbox')
				.all()) {
				await expect(rowCheckbox).toBeVisible();
				await rowCheckbox.click();
			}

			await page.getByRole('button', {name: 'Actions'}).click();

			const assignToDefaultMenuItem = page.getByRole('menuitem', {
				exact: true,
				name: `Assign to Default`,
			});

			await expect(assignToDefaultMenuItem).toBeVisible();

			page.on('dialog', async (dialog) => {
				dialog.accept();
			});

			await assignToDefaultMenuItem.click();

			await expect(
				page.getByText('There are no display page template usages.')
			).toBeVisible();
		}
	);

	test(
		'View usages for blogs entry',
		{
			tag: '@LPS-123480',
		},
		async ({
			blogsEditBlogEntryPage,
			displayPageTemplatesPage,
			page,
			site,
		}) => {

			// Create a display page template for Blogs Entry

			await displayPageTemplatesPage.goto(site.friendlyUrlPath);

			const displayPageTemplateName = getRandomString();

			await displayPageTemplatesPage.createTemplate({
				contentType: 'Blogs Entry',
				name: displayPageTemplateName,
			});

			// Create a Blogs Entry and set the template as its specific display page template

			await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

			const title = getRandomString();
			const content = getRandomString();

			await blogsEditBlogEntryPage.editBlogEntry({
				content,
				publish: false,
				title,
			});

			await blogsEditBlogEntryPage.selectSpecificDisplayPage(
				displayPageTemplateName
			);

			await blogsEditBlogEntryPage.publishBlogEntry();

			// View usages of display page template

			await displayPageTemplatesPage.goto(site.friendlyUrlPath);

			await displayPageTemplatesPage.viewUsages(displayPageTemplateName);

			const firstRowCheckbox = page
				.getByRole('row')
				.getByRole('checkbox')
				.first();
			await expect(firstRowCheckbox).toBeVisible();
		}
	);

	test(
		'View usages for basic document',
		{
			tag: '@LPS-123480',
		},
		async ({
			displayPageTemplatesPage,
			documentLibraryEditFilePage,
			page,
			site,
		}) => {

			// Create a display page template for Basic Document

			await displayPageTemplatesPage.goto(site.friendlyUrlPath);

			const displayPageTemplateName = getRandomString();

			await displayPageTemplatesPage.createTemplate({
				contentSubtype: 'Basic Document',
				contentType: 'Document',
				name: displayPageTemplateName,
			});

			// Create a Basic Document and set the template as its specific display page template

			await documentLibraryEditFilePage.goto(site.friendlyUrlPath);

			const title = getRandomString();

			await page.getByLabel('Title').fill(title);

			await documentLibraryEditFilePage.selectSpecificDisplayPage(
				displayPageTemplateName
			);

			await documentLibraryEditFilePage.publishFileEntry();

			// View usages of display page template

			await displayPageTemplatesPage.goto(site.friendlyUrlPath);

			await displayPageTemplatesPage.viewUsages(displayPageTemplateName);

			const firstRowCheckbox = page
				.getByRole('row')
				.getByRole('checkbox')
				.first();
			await expect(firstRowCheckbox).toBeVisible();
		}
	);
});

test('Checks that the card checkbox has the correct aria label', async ({
	displayPageTemplatesPage,
	page,
	site,
}) => {

	// Go to display pages administration

	await displayPageTemplatesPage.goto(site.friendlyUrlPath);

	// Create new DPT and check checkbox aria-label

	const displayPageTemplateName = getRandomString();

	await displayPageTemplatesPage.createTemplate({
		contentSubtype: 'Basic Web Content',
		contentType: 'Web Content Article',
		name: displayPageTemplateName,
	});

	await expect(
		page.getByLabel(`Select ${displayPageTemplateName}`)
	).toBeVisible();
});

test('User can delete default display page template', async ({
	displayPageTemplatesPage,
	page,
	site,
}) => {

	// Create a display page template for Basic Web Content and mark as default

	await displayPageTemplatesPage.goto(site.friendlyUrlPath);

	const displayPageTemplateName = getRandomString();

	await displayPageTemplatesPage.createTemplate({
		contentSubtype: 'Basic Web Content',
		contentType: 'Web Content Article',
		name: displayPageTemplateName,
	});

	await displayPageTemplatesPage.markAsDefault(displayPageTemplateName);

	// Delete default display page template

	await displayPageTemplatesPage.deleteTemplate(displayPageTemplateName);

	await expect(
		page.getByText(displayPageTemplateName, {exact: true})
	).not.toBeVisible();
});

test('User can rename a display page', async ({
	displayPageTemplatesPage,
	page,
	site,
}) => {

	// Create a display page template for Blogs Entry

	await displayPageTemplatesPage.goto(site.friendlyUrlPath);

	const displayPageTemplateName = getRandomString();

	await displayPageTemplatesPage.createTemplate({
		contentType: 'Blogs Entry',
		name: displayPageTemplateName,
	});

	// Rename display page template

	const newDisplayPageTemplateName = getRandomString();

	await displayPageTemplatesPage.renameTemplate(
		newDisplayPageTemplateName,
		displayPageTemplateName
	);

	await expect(
		page.getByText(newDisplayPageTemplateName, {exact: true})
	).toBeVisible();
});

testInfoPanel(
	'View the info panel for a display page and for a folder',
	{
		tag: ['@LPD-34205', '@LPS-189857'],
	},
	async ({displayPageTemplatesPage, page, site}) => {

		// Create a folder

		await displayPageTemplatesPage.goto(site.friendlyUrlPath);

		const displayPageTemplateFolderName = getRandomString();

		await displayPageTemplatesPage.createFolder(
			displayPageTemplateFolderName
		);

		// Create a display page template for Blogs Entry

		const displayPageTemplateName = getRandomString();

		await displayPageTemplatesPage.createTemplate({
			contentType: 'Blogs Entry',
			folderName: displayPageTemplateFolderName,
			name: displayPageTemplateName,
		});

		// Check folder info panel

		await page.getByTitle('Toggle Info Panel', {exact: true}).click();

		const infoPanel = page.getByLabel('Info Panel', {exact: true});

		await expect(
			infoPanel.locator('.sidebar-header .component-title')
		).toContainText(displayPageTemplateFolderName);

		await expect(
			infoPanel.locator('.sidebar-header .component-subtitle')
		).toContainText('Folder');

		await expect(
			infoPanel.locator('.sidebar-body .mb-4').nth(0)
		).toContainText('Location');

		await expect(
			infoPanel.locator('.sidebar-body .mb-4').nth(0)
		).toContainText(`Home > ${displayPageTemplateFolderName}`);

		await expect(
			infoPanel.locator('.sidebar-body .mb-4').nth(1)
		).toContainText('Number of Items');

		await expect(
			infoPanel.locator('.sidebar-body .mb-4').nth(1)
		).toContainText('1');

		await expect(
			infoPanel.locator('.sidebar-body .mb-4').nth(2)
		).toContainText('Created');

		await expect(
			infoPanel.locator('.sidebar-body .mb-4').nth(3)
		).toContainText('Modified');

		// Check display page info panel

		await page
			.getByLabel(`Select ${displayPageTemplateName}`, {exact: true})
			.check();

		await expect(
			infoPanel.locator('.sidebar-header .component-title')
		).toContainText(displayPageTemplateName);

		await expect(
			infoPanel.locator('.sidebar-header .component-subtitle')
		).toContainText('Display Page Template');

		await expect(
			infoPanel.locator('.sidebar-header .label-item')
		).toContainText('Approved');

		await expect(
			infoPanel.locator('.sidebar-body .mb-4').nth(0)
		).toContainText('Location');

		await expect(
			infoPanel.locator('.sidebar-body .mb-4').nth(0)
		).toContainText(`Home > ${displayPageTemplateFolderName}`);

		await expect(
			infoPanel.locator('.sidebar-body .mb-4').nth(1)
		).toContainText('Content Type');

		await expect(
			infoPanel.locator('.sidebar-body .mb-4').nth(1)
		).toContainText('Blogs Entry');

		await expect(
			infoPanel.locator('.sidebar-body .mb-4').nth(2)
		).toContainText('Created');

		await expect(
			infoPanel.locator('.sidebar-body .mb-4').nth(3)
		).toContainText('Modified');
	}
);
