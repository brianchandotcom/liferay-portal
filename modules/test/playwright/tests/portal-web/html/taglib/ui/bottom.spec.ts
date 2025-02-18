/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import getRandomString from '../../../../../utils/getRandomString';
import getBasicWebContentStructureId from '../../../../../utils/structured-content/getBasicWebContentStructureId';

const test = mergeTests(dataApiHelpersTest, loginTest());

test(
	'Check fixed permission header is visible',
	{tag: ['@LPD-39339']},
	async ({apiHelpers, page}) => {
		const contentStructureId =
			await getBasicWebContentStructureId(apiHelpers);
		const randomTitle = getRandomString();
		const siteId = await page.evaluate(() => {
			return String(Liferay.ThemeDisplay.getSiteGroupId());
		});

		const webContent =
			await apiHelpers.jsonWebServicesJournal.addWebContent({
				ddmStructureId: contentStructureId,
				groupId: siteId,
				titleMap: {en_US: randomTitle},
			});

		apiHelpers.data.push({
			id: `${siteId}_${webContent.articleId}`,
			type: 'webContent',
		});

		await page.goto('/');

		const openProductButton = page.getByLabel('Open Product Menu');

		if (await openProductButton.isVisible()) {
			await openProductButton.click();
		}

		const contentAndDataTab = page.getByRole('menuitem', {
			name: 'Content & Data',
		});

		await contentAndDataTab.waitFor({state: 'visible'});

		await contentAndDataTab.click();

		const webContentButton = page.getByRole('menuitem', {
			name: 'Web Content',
		});

		await webContentButton.waitFor({state: 'visible'});

		await webContentButton.click();

		const webContentPage = page.getByRole('heading', {name: 'Web Content'});

		await webContentPage.waitFor({state: 'visible'});

		const editWebContentButton = page.locator(
			`button[aria-label="Actions for ${randomTitle}"]`
		);

		await editWebContentButton.waitFor({state: 'visible'});

		await editWebContentButton.click();

		const permissionButton = page.getByRole('menuitem', {
			name: 'Permissions',
		});

		await permissionButton.waitFor({state: 'visible'});

		await permissionButton.click();

		const permissionHeading = page.getByRole('heading', {
			name: 'Permissions',
		});

		await permissionHeading.waitFor({state: 'visible'});

		const fixedHeaderRow = page
			.frameLocator('iframe[title="Permissions"]')
			.locator(
				'[id="_com_liferay_portlet_configuration_web_portlet_PortletConfigurationPortlet_rolesSearchContainerfixedHeader"]'
			);

		await expect(fixedHeaderRow).toHaveCSS('display', 'none');

		await page
			.frameLocator('iframe[title="Permissions"]')
			.getByText('No roles were found. Role')
			.click();

		await page.keyboard.down('PageDown');

		await expect(fixedHeaderRow).not.toHaveCSS('display', 'none');
	}
);
