/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';
import {webContentDisplayPageTest} from '../../../fixtures/webContentDisplayPageTest';
import getRandomString from '../../../utils/getRandomString';
import getBasicWebContentStructureId from '../../../utils/structured-content/getBasicWebContentStructureId';

export const test = mergeTests(
	apiHelpersTest,
	loginTest(),
	systemSettingsPageTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	pageEditorPagesTest,
	isolatedSiteTest,
	webContentDisplayPageTest
);

test('Should not allow enabling comments in Web Content Display when disabled by Portal Properties', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
	systemSettingsPage,
	webContentDisplayPage,
}) => {
	await test.step('Verify global comments restriction in System Settings', async () => {
		await systemSettingsPage.goToSystemSetting(
			'Web Content',
			'Web Content'
		);

		await expect(
			page.getByLabel('Article Comments Enabled')
		).not.toBeChecked();
		await expect(
			page
				.getByText(
					'Set this to true to enable comments for journal articles. This field has been set by a portal property and cannot be changed here.'
				)
				.first()
		).toBeVisible();
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

	await test.step('Verify that "Comments" option is hidden in widget configuration', async () => {
		await page
			.locator('#wrapper')
			.getByText('Web Content Display')
			.locator('..')
			.getByRole('button', {name: 'Options'})
			.click();

		await webContentDisplayPage.configurationOption.click();

		await expect(page.locator('input[id$="enableComments"]')).toBeHidden();
	});
});
