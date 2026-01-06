/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accessibilityMenuPagesTest} from '../../../fixtures/accessibilityMenuPagesTest';
import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';
import {webContentDisplayPageTest} from '../../../fixtures/webContentDisplayPageTest';
import getRandomString from '../../../utils/getRandomString';
import getBasicWebContentStructureId from '../../../utils/structured-content/getBasicWebContentStructureId';
import {waitForAlert} from '../../../utils/waitForAlert';

export const test = mergeTests(
	accessibilityMenuPagesTest,
	loginTest(),
	systemSettingsPageTest,
	instanceSettingsPagesTest,
	webContentDisplayPageTest,
	apiHelpersTest,
	isolatedSiteTest,
	pageEditorPagesTest
);

test('Should persist and apply system configuration changes made via the User Interface', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
	systemSettingsPage,
	webContentDisplayPage,
}) => {
	const WEB_CONTENT_LABEL = 'Web Content';
	const NEW_COMMENT = 'New Comment';

	async function addComment() {
		const editor = page
			.frameLocator('iframe[title*="editor"]')
			.locator('body');

		await expect(editor).toBeVisible();

		await editor.scrollIntoViewIfNeeded();

		await editor.click();

		await page.keyboard.type(NEW_COMMENT);

		const replyButton = page
			.getByRole('button', {
				exact: true,
				name: 'Reply',
			})
			.first();

		await expect(replyButton).toBeEnabled();

		await replyButton.click();

		await expect(replyButton).toBeDisabled();

		await waitForAlert(page);

		// Check that the comment has been added

		const comment = page.locator('article');

		await expect(comment.filter({hasText: NEW_COMMENT})).toBeAttached();
	}

	async function disableArticleComments() {
		if (await page.getByLabel('Article Comments Enabled').isChecked()) {
			await page.getByLabel('Article Comments Enabled').uncheck();

			await systemSettingsPage.saveButton.click();

			await waitForAlert(page);
		}
	}

	await test.step('Check that Article Comments Enabled is checked for web content in System Settings', async () => {
		await systemSettingsPage.goToSystemSetting(
			WEB_CONTENT_LABEL,
			WEB_CONTENT_LABEL
		);

		await expect(page.getByLabel('Article Comments Enabled')).toBeChecked();
	});

	const basicWebContentTitle = getRandomString();

	const {layout} =
		await test.step('Create Web Content and Page via API', async () => {
			await apiHelpers.jsonWebServicesJournal.addWebContent({
				ddmStructureId: await getBasicWebContentStructureId(apiHelpers),
				groupId: site.id,
				titleMap: {en_US: basicWebContentTitle},
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				siteId: site.id,
				title: getRandomString(),
			});

			return {layout};
		});

	await test.step('Add Web Content Display widget to page', async () => {
		await pageEditorPage.goto(layout, site.friendlyUrlPath);
		await pageEditorPage.addWidget(
			'Content Management',
			'Web Content Display'
		);

		await webContentDisplayPage.addWebContentWithDisplay({
			pageType: 'content',
			webContentName: basicWebContentTitle,
		});
	});

	await test.step('Enable ViewCountIncrement and check Comments option', async () => {
		await webContentDisplayPage.goToConfigurationWithDisplay();

		await webContentDisplayPage.configurationFrame
			.getByLabel('View Count Increment')
			.click();

		await webContentDisplayPage.configurationFrame
			.getByLabel('Comments', {exact: true})
			.check();

		await webContentDisplayPage.saveConfigurationFrameOptions();
	});

	await test.step('Publish the page and add comment for Web Content', async () => {
		await pageEditorPage.publishPage();

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

		await addComment();
	});

	await test.step('Navigate to System Settings and disable Accessibility Menu configuration', async () => {
		await systemSettingsPage.goToSystemSetting(
			WEB_CONTENT_LABEL,
			WEB_CONTENT_LABEL
		);

		await disableArticleComments();
	});

	await test.step('Check that the comment for Web Content not displays on UI', async () => {
		await expect(
			page.locator('article').filter({hasText: NEW_COMMENT})
		).not.toBeAttached();
	});

	await test.step('Check that the comment for Web Content not displays on UI', async () => {
		await systemSettingsPage.goToSystemSetting(
			WEB_CONTENT_LABEL,
			WEB_CONTENT_LABEL
		);

		expect(await systemSettingsPage.getExportedConfiguration()).toContain(
			`articleCommentsEnabled=B"false"`
		);
	});
});
