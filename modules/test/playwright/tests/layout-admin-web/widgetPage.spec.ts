/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageViewModePagesTest} from '../../fixtures/pageViewModePagesTest';
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

	// Add required basic web contents

	const approvedWebContentTitle = getRandomString();
	const draftWebContentTitle = getRandomString();
	const expiredWebContentTitle = getRandomString();
	const inTrashWebContentTitle = getRandomString();
	const scheduledWebContentTitle = getRandomString();

	const contentStructureId = await getBasicWebContentStructureId(apiHelpers);

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

	async function verifyVisibleWebContents() {
		await expect(page.getByText(approvedWebContentTitle)).toBeVisible();
		await expect(page.getByText(draftWebContentTitle)).not.toBeVisible();
		await expect(page.getByText(expiredWebContentTitle)).not.toBeVisible();
		await expect(page.getByText(inTrashWebContentTitle)).not.toBeVisible();
		await expect(page.getByText(scheduledWebContentTitle)).toBeVisible();
	}

	// Create page, go to view mode and open Contents panel

	const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: getRandomString(),
	});

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

	await widgetPagePage.openAddPanel();

	await widgetPagePage.contentTab.click();

	await verifyVisibleWebContents();

	await page.getByLabel('Select Label').selectOption('8');

	await verifyVisibleWebContents();

	await page.getByRole('button', {name: 'Display Style'}).click();

	await verifyVisibleWebContents();
});

test('LPS-108216 Can hide and show portlet header of existing visible portlets on widget page via switch Toggle Controls', async ({
	apiHelpers,
	page,
	site,
	widgetPagePage,
}) => {

	// Create page and go to view mode

	const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: getRandomString(),
	});

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

	await widgetPagePage.addPortlet('Blogs Aggregator');

	const blogsWidget = page.locator('.portlet-blogs');

	// Make sure controls are visible and check topper is shown

	await widgetPagePage.toggleControls('visible');

	const topper = page
		.locator('.portlet-topper')
		.getByText('Blogs Aggregator');

	await blogsWidget.hover();

	await expect(topper).toBeVisible();

	// Toggle controls and check topper is not shown

	await widgetPagePage.toggleControls('hidden');

	await blogsWidget.hover();

	await expect(topper).not.toBeVisible();

	// Recover original state

	await widgetPagePage.toggleControls('visible');

	// Delete Web Content Display and check it's not displayed

	await widgetPagePage.deletePortlet('Blogs Aggregator');

	await expect(
		page.locator('.portlet-topper', {hasText: 'Blogs Aggregator'})
	).not.toBeVisible();
});

test('View web content is shown in Web Content Display after be added via content panel', async ({
	apiHelpers,
	page,
	site,
	widgetPagePage,
}) => {

	// Add required web content

	const webContentTitle = getRandomString();

	const contentStructureId = await getBasicWebContentStructureId(apiHelpers);

	await addApprovedStructuredContent({
		apiHelpers,
		contentStructureId,
		siteId: site.id,
		title: webContentTitle,
	});

	// Create page and go to view mode

	const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: getRandomString(),
	});

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

	// Check item has correct title

	await widgetPagePage.openAddPanel();

	await widgetPagePage.contentTab.click();

	await expect(page.getByTitle(webContentTitle)).toBeVisible();

	// Add content and check it's displayed inside a Web Content Display

	await widgetPagePage.addContent(webContentTitle);

	await expect(
		page.locator('.portlet-journal-content').getByText(webContentTitle)
	).toBeVisible();
});

test('LPS-178476 View the XSS is escaped when store it in widget page name.', async ({
	apiHelpers,
	page,
	site,
}) => {

	// Add listener with expect so it fails when a browser dialog is shown

	page.on('dialog', async (dialog) => {
		dialog.accept();

		expect(dialog.message(), 'This alert should not be shown').toBeNull();
	});

	const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: '<script>alert(123);</script>',
	});

	// Go to view mode of page

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

	// Open the Product Menu

	await page
		.getByRole('tab', {
			name: 'Product Menu',
		})
		.click({timeout: 3000});
});
