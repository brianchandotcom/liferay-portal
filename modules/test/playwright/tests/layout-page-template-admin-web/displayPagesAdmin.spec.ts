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

	const displayPageTemplateName = 'dpt' + getRandomInt();

	await displayPageTemplatesPage.publishNewTemplate({
		contentSubtype: 'Basic Web Content',
		contentType: 'Web Content Article',
		name: displayPageTemplateName,
	});

	const contentStructureId = await getBasicWebContentStructureId(apiHelpers);

	const webContentTitle = 'specificDPT' + getRandomInt();

	await apiHelpers.jsonWebServicesJournal.addWebContent({
		ddmStructureId: contentStructureId,
		groupId: site.id,
		titleMap: {en_US: webContentTitle},
	});

	await journalPage.goto(site.friendlyUrlPath);

	await journalEditArticlePage.editArticle(webContentTitle);

	await journalEditArticlePage.selectSpecificDisplayPage(
		displayPageTemplateName
	);

	await page.getByRole('button', {name: 'Publish'}).click();

	await waitForSuccessAlert(
		page,
		`Success:${webContentTitle} was updated successfully.`
	);

	await displayPageTemplatesPage.goto(site.friendlyUrlPath);

	await displayPageTemplatesPage.goToDisplayPageTemplateAction(
		'View Usages',
		'1'
	);

	await expect(page.getByText(webContentTitle)).toBeVisible();

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
