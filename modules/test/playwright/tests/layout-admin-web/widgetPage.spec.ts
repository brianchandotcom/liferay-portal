/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {widgetPagesTest} from '../../fixtures/widgetPagesTest';
import getRandomString from '../../utils/getRandomString';
import addApprovedStructuredContent from '../../utils/structured-content/addApprovedStructuredContent';
import addDraftStructuredContent from '../../utils/structured-content/addDraftStructuredContent';
import addExpiredStructuredContent from '../../utils/structured-content/addExpiredStructuredContent';
import addInTrashStructuredContent from '../../utils/structured-content/addInTrashStructuredContent';
import addScheduledStructuredContent from '../../utils/structured-content/addScheduledStructuredContent';
import getBasicWebContentStructureId from '../../utils/structured-content/getBasicWebContentStructureId';

export const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	widgetPagesTest
);

test('LPD-15256 Approved and scheduled web contents should be displayed in the "Content" tab of the "Add" panel of a widget page, whereas draft, expired and in-trash web contents should not', async ({
	apiHelpers,
	page,
	site,
	widgetPage,
}) => {
	const approvedWebContentTitle = 'Approved Web Content';
	const draftWebContentTitle = 'Draft Web Content';
	const expiredWebContentTitle = 'Expired Web Content';
	const inTrashWebContentTitle = 'In Trash Web Content';
	const scheduledWebContentTitle = 'Scheduled Web Content';

	async function _addBasicWebContents(site: Site) {
		const contentStructureId = await getBasicWebContentStructureId(
			apiHelpers
		);

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

	const layout = await apiHelpers.jsonWebServicesLayout.addLayout(
		site.id,
		getRandomString()
	);

	await widgetPage.goToSitePage(site, layout.friendlyURL);
	await widgetPage.clickControlMenuAddButton();
	await widgetPage.goToControlMenuAddPanelContentTab();
	await _verifyVisibleWebContents();

	await page.getByLabel('Select Label').selectOption('8');
	await _verifyVisibleWebContents();

	await page.getByRole('button', {name: 'Display Style'}).click();
	await _verifyVisibleWebContents();
});
