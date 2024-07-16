/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';
import getBasicWebContentStructureId from '../../utils/structured-content/getBasicWebContentStructureId';
import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';
import {displayPageTemplatesTest} from './fixtures/displayTemplatePagesTest';

const test = mergeTests(
	apiHelpersTest,
	displayPageTemplatesTest,
	isolatedSiteTest,
	loginTest()
);

async function addBasicJournalArticleWithSpecificDisplayPageTemplate(
	apiHelpers,
	displayPageTemplateName,
	journalArticleTitle,
	journalEditArticlePage,
	journalPage,
	page,
	site
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

test('Checks that the card checkbox has the correct aria label', async ({
	displayPageTemplatesPage,
	page,
	site,
}) => {

	// Go to display pages administration

	await displayPageTemplatesPage.goto(site.friendlyUrlPath);

	// Create new DPT and check checkbox aria-label

	const displayPageTemplateName = getRandomString();

	await displayPageTemplatesPage.publishNewTemplate({
		contentSubtype: 'Basic Web Content',
		contentType: 'Web Content Article',
		name: displayPageTemplateName,
	});

	await expect(
		page.getByLabel(`Select ${displayPageTemplateName}`)
	).toBeVisible();
});

test('LPS-121199 can assign usage to default even if the default display page template does not exist', async ({
	apiHelpers,
	displayPageTemplatesPage,
	journalEditArticlePage,
	journalPage,
	page,
	site,
}) => {
	await displayPageTemplatesPage.goto(site.friendlyUrlPath);

	const displayPageTemplateName = 'basicWebContentDpt' + getRandomInt();

	await displayPageTemplatesPage.publishNewTemplate({
		contentSubtype: 'Basic Web Content',
		contentType: 'Web Content Article',
		name: displayPageTemplateName,
	});

	const journalArticleTitle = 'specificDPT' + getRandomInt();

	await addBasicJournalArticleWithSpecificDisplayPageTemplate(
		apiHelpers,
		displayPageTemplateName,
		journalArticleTitle,
		journalEditArticlePage,
		journalPage,
		page,
		site
	);

	await displayPageTemplatesPage.goto(site.friendlyUrlPath);

	await displayPageTemplatesPage.goToDisplayPageTemplateAction(
		'View Usages',
		'1'
	);

	await expect(page.getByText(journalArticleTitle)).toBeVisible();

	const firstRowCheckbox = page.locator(
		'[aria-labelledby="_com_liferay_layout_page_template_admin_web_portlet_LayoutPageTemplatesPortlet_assetDisplayPageEntries_1"]'
	);

	await firstRowCheckbox.click();

	await page.getByRole('button', {name: 'Actions'}).click();

	await expect(
		page.getByRole('menuitem', {
			exact: true,
			name: 'Assign to Default',
		})
	).toBeVisible();
});

test('LPS-121199 can assign usage to default even if the default display page template exists', async ({
	apiHelpers,
	displayPageTemplatesPage,
	journalEditArticlePage,
	journalPage,
	page,
	site,
}) => {
	await displayPageTemplatesPage.goto(site.friendlyUrlPath);

	const defaultDisplayPageTemplateName = 'defaultDpt' + getRandomInt();

	await displayPageTemplatesPage.publishNewTemplate({
		contentSubtype: 'Basic Web Content',
		contentType: 'Web Content Article',
		name: defaultDisplayPageTemplateName,
	});

	await displayPageTemplatesPage.markAsDefault(
		defaultDisplayPageTemplateName
	);

	const displayPageTemplateName = 'basicWebContentDpt' + getRandomInt();

	await displayPageTemplatesPage.publishNewTemplate({
		contentSubtype: 'Basic Web Content',
		contentType: 'Web Content Article',
		name: displayPageTemplateName,
	});

	const journalArticleTitle = 'specificDPT' + getRandomInt();

	await addBasicJournalArticleWithSpecificDisplayPageTemplate(
		apiHelpers,
		displayPageTemplateName,
		journalArticleTitle,
		journalEditArticlePage,
		journalPage,
		page,
		site
	);

	await displayPageTemplatesPage.goto(site.friendlyUrlPath);

	await displayPageTemplatesPage.goToDisplayPageTemplateAction(
		'View Usages',
		'2'
	);

	await expect(page.getByText(journalArticleTitle)).toBeVisible();

	const firstRowCheckbox = page.locator(
		'[aria-labelledby="_com_liferay_layout_page_template_admin_web_portlet_LayoutPageTemplatesPortlet_assetDisplayPageEntries_1"]'
	);

	await firstRowCheckbox.click();

	await page.getByRole('button', {name: 'Actions'}).click();

	await expect(
		page.getByRole('menuitem', {
			exact: true,
			name: `Assign to Default (${defaultDisplayPageTemplateName})`,
		})
	).toBeVisible();
});

test('LPS-121199 can assign multiple usages to default', async ({
	apiHelpers,
	displayPageTemplatesPage,
	journalEditArticlePage,
	journalPage,
	page,
	site,
}) => {
	await displayPageTemplatesPage.goto(site.friendlyUrlPath);

	const displayPageTemplateName = 'basicWebContentDpt' + getRandomInt();

	await displayPageTemplatesPage.publishNewTemplate({
		contentSubtype: 'Basic Web Content',
		contentType: 'Web Content Article',
		name: displayPageTemplateName,
	});

	for (let i = 1; i < 4; i++) {
		const journalArticleTitle = 'specificDPT' + i + getRandomInt();

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

	await displayPageTemplatesPage.goto(site.friendlyUrlPath);

	await displayPageTemplatesPage.goToDisplayPageTemplateAction(
		'View Usages',
		'1'
	);

	for (let i = 1; i < 4; i++) {
		const rowCheckbox = page.locator(
			`[aria-labelledby="_com_liferay_layout_page_template_admin_web_portlet_LayoutPageTemplatesPortlet_assetDisplayPageEntries_${i}"]`
		);
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
});

test('LPS-123480 view usages for blogs entry', async ({
	blogsEditBlogEntryPage,
	displayPageTemplatesPage,
	page,
	site,
}) => {
	await displayPageTemplatesPage.goto(site.friendlyUrlPath);

	const displayPageTemplateName = 'blogsEntryDpt' + getRandomInt();

	await displayPageTemplatesPage.publishNewTemplate({
		contentType: 'Blogs Entry',
		name: displayPageTemplateName,
	});

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

	await displayPageTemplatesPage.goto(site.friendlyUrlPath);

	await displayPageTemplatesPage.goToDisplayPageTemplateAction(
		'View Usages',
		'1'
	);

	const rowCheckbox = page.locator(
		`[aria-labelledby="_com_liferay_layout_page_template_admin_web_portlet_LayoutPageTemplatesPortlet_assetDisplayPageEntries_1"]`
	);
	await expect(rowCheckbox).toBeVisible();
});

test('LPS-123480 view usages for basic document', async ({
	displayPageTemplatesPage,
	documentLibraryEditFilePage,
	page,
	site,
}) => {
	await displayPageTemplatesPage.goto(site.friendlyUrlPath);

	const displayPageTemplateName = 'basicDocumentDpt' + getRandomInt();

	await displayPageTemplatesPage.publishNewTemplate({
		contentSubtype: 'Basic Document',
		contentType: 'Document',
		name: displayPageTemplateName,
	});

	await documentLibraryEditFilePage.goto(site.friendlyUrlPath);

	const title = getRandomString();

	await page.getByLabel('Title').fill(title);

	await documentLibraryEditFilePage.selectSpecificDisplayPage(
		displayPageTemplateName
	);

	await documentLibraryEditFilePage.publishFileEntry();

	await displayPageTemplatesPage.goto(site.friendlyUrlPath);

	await displayPageTemplatesPage.goToDisplayPageTemplateAction(
		'View Usages',
		'1'
	);

	const rowCheckbox = page.locator(
		`[aria-labelledby="_com_liferay_layout_page_template_admin_web_portlet_LayoutPageTemplatesPortlet_assetDisplayPageEntries_1"]`
	);
	await expect(rowCheckbox).toBeVisible();
});
