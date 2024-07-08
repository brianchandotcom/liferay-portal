/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageViewModePagesTest} from '../../fixtures/pageViewModePagesTest';
import {UIElementsPage} from '../../pages/uielements/UIElementsPage';
import getRandomString from '../../utils/getRandomString';
import addApprovedStructuredContent from '../../utils/structured-content/addApprovedStructuredContent';
import addDraftStructuredContent from '../../utils/structured-content/addDraftStructuredContent';
import addExpiredStructuredContent from '../../utils/structured-content/addExpiredStructuredContent';
import addInTrashStructuredContent from '../../utils/structured-content/addInTrashStructuredContent';
import addScheduledStructuredContent from '../../utils/structured-content/addScheduledStructuredContent';
import getBasicWebContentStructureId from '../../utils/structured-content/getBasicWebContentStructureId';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	pageViewModePagesTest
);

test('LPD-15256 Approved and scheduled web contents should be displayed in the "Content" tab of the "Add" panel of a widget page, whereas draft, expired and in-trash web contents should not', async ({
	apiHelpers,
	page,
	site,
	widgetPagePage,
}) => {
	const approvedWebContentTitle = 'Approved Web Content';
	const draftWebContentTitle = 'Draft Web Content';
	const expiredWebContentTitle = 'Expired Web Content';
	const inTrashWebContentTitle = 'In Trash Web Content';
	const scheduledWebContentTitle = 'Scheduled Web Content';

	async function _addBasicWebContents(site: Site) {
		const contentStructureId =
			await getBasicWebContentStructureId(apiHelpers);

		await addApprovedStructuredContent({
			apiHelpers,
			contentStructureId,
			siteId: site.id,
			title: approvedWebContentTitle,
		});

		await addDraftStructuredContent({
			apiHelpers,
			contentStructureId,
			siteId: site.id,
			title: draftWebContentTitle,
		});
		await addExpiredStructuredContent(
			apiHelpers,
			site.id,
			contentStructureId,
			expiredWebContentTitle
		);
		await addInTrashStructuredContent(
			apiHelpers,
			site.id,
			contentStructureId,
			inTrashWebContentTitle
		);
		await addScheduledStructuredContent(
			apiHelpers,
			site.id,
			contentStructureId,
			scheduledWebContentTitle
		);
	}

	async function _verifyVisibleWebContents() {
		await expect(page.getByText(approvedWebContentTitle)).toBeVisible();
		await expect(page.getByText(draftWebContentTitle)).not.toBeVisible();
		await expect(page.getByText(expiredWebContentTitle)).not.toBeVisible();
		await expect(page.getByText(inTrashWebContentTitle)).not.toBeVisible();
		await expect(page.getByText(scheduledWebContentTitle)).toBeVisible();
	}

	await _addBasicWebContents(site);

	const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: getRandomString(),
	});

	await widgetPagePage.goToSitePage(site, layout.friendlyURL);
	await widgetPagePage.clickControlMenuAddButton();
	await widgetPagePage.goToControlMenuAddPanelContentTab();
	await _verifyVisibleWebContents();

	await page.getByLabel('Select Label').selectOption('8');
	await _verifyVisibleWebContents();

	await page.getByRole('button', {name: 'Display Style'}).click();
	await _verifyVisibleWebContents();
});

test('View web content is shown in Web Content Display after be added via content panel', async ({
	apiHelpers,
	page,
	site,
	widgetPagePage,
}) => {
	const longWebContentTitle =
		'Inside the life coaching cult that takes over lives';
	const webContentTitle = getRandomString();
	const uiElementsPage = new UIElementsPage(page);

	const contentStructureId = await getBasicWebContentStructureId(apiHelpers);

	await addApprovedStructuredContent({
		apiHelpers,
		contentStructureId,
		siteId: site.id,
		title: longWebContentTitle,
	});

	await addApprovedStructuredContent({
		apiHelpers,
		contentStructureId,
		siteId: site.id,
		title: webContentTitle,
	});

	const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: getRandomString(),
	});

	await widgetPagePage.goToSitePage(site, layout.friendlyURL);
	await widgetPagePage.clickControlMenuAddButton();
	await widgetPagePage.goToControlMenuAddPanelContentTab();

	await expect(
		page.getByTitle(longWebContentTitle, {exact: true})
	).toBeVisible();
	await expect(page.getByText(longWebContentTitle)).toBeVisible();

	await widgetPagePage.addContent(longWebContentTitle);

	await uiElementsPage.pageCreatedAlert.waitFor({state: 'hidden'});

	await expect(page.getByText(longWebContentTitle)).toBeVisible();

	await widgetPagePage.addContent(webContentTitle);

	await uiElementsPage.pageCreatedAlert.waitFor({state: 'hidden'});

	await expect(page.getByText(webContentTitle)).toBeVisible();
});
