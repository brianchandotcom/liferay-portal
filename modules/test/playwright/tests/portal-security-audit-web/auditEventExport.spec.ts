/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import {readFileSync, statSync} from 'fs';

import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {loginTest} from '../../fixtures/loginTest';
import fillAndClickOutside from '../../utils/fillAndClickOutside';
import {getTempDir} from '../../utils/temp';

export const test = mergeTests(loginTest(), applicationsMenuPageTest);

const AUDIT_PORTLET_NAMESPACE =
	'_com_liferay_portal_security_audit_web_portlet_AuditPortlet_';

const dateFields = [
	'endDateAmPm',
	'endDateDay',
	'endDateHour',
	'endDateMinute',
	'endDateMonth',
	'endDateYear',
	'startDateAmPm',
	'startDateDay',
	'startDateHour',
	'startDateMinute',
	'startDateMonth',
	'startDateYear',
];

const fields = [
	'className',
	'classPK',
	'clientHost',
	'clientIP',
	'eventType',
	'serverName',
	'userName',
	'groupId',
	'serverPort',
	'userId',
];

test('LPD-40224: Check if the export audit events .csv is being filtered by the search fields', async ({
	applicationsMenuPage,
	page,
}) => {
	page.on('dialog', (dialog) => dialog.accept());

	await applicationsMenuPage.goToAudit();

	await page.locator('#toggle_id_audit_event_searchtoggleAdvanced').click();

	// Filter the events by the first element, use the information to make assertions in the .csv

	const firstEventResourceId = await page
		.locator('td.lfr-resource-id-column')
		.first();

	const resourceId = (await firstEventResourceId.textContent()).trim();

	const firstEventResourceAction = await page
		.locator('td.lfr-resource-action-column')
		.first();

	const resourceAction = (
		await firstEventResourceAction.textContent()
	).trim();

	await page
		.locator(`#${AUDIT_PORTLET_NAMESPACE}classPK:visible`)
		.fill(resourceId);

	await page
		.locator(`#${AUDIT_PORTLET_NAMESPACE}eventType:visible`)
		.fill(resourceAction);

	await expect(page.locator('.lexicon-icon-search')).toBeVisible();

	await page.locator('.lexicon-icon-search').click();

	await page.waitForTimeout(500);

	// Populate map with all the date parameters

	const dateValues = {};

	for (const field of dateFields) {
		const inputElement = await page.locator(`#${field}`);
		const inputValue = await inputElement.inputValue();

		dateValues[field] = inputValue;
	}

	// On the export request, check if the body has all parameters

	await page.on('request', async (request) => {
		if (request.url().includes('export_audit_events')) {
			const requestBody = request.postData();

			for (const field of dateFields) {
				expect(requestBody).toContain(
					`${AUDIT_PORTLET_NAMESPACE + field}=${dateValues[field]}`
				);
			}

			for (const field of fields) {
				expect(requestBody).toContain(AUDIT_PORTLET_NAMESPACE + field);
			}
		}
	});

	const options = await page.getByLabel('Options');

	await options.click();

	const menuItem = await page.getByRole('menuitem', {
		name: 'Export Audit Events',
	});

	await menuItem.click();

	const downloadPromise = await page.waitForEvent('download');

	const download = await downloadPromise;

	const filePath = getTempDir() + download.suggestedFilename();

	await download.saveAs(filePath);

	const content = await readFileSync(filePath, 'utf8');

	expect(content).toContain(resourceId);

	const regex = new RegExp(resourceAction, 'g');

	const matches = await content.match(regex);

	const eventCount = await page
		.locator('table.table > tbody tr:not(.d-none)')
		.count();

	expect(matches).toHaveLength(eventCount);
});

test('LPD-40224: Check if the audit events filtered by date are being exported', async ({
	applicationsMenuPage,
	page,
}) => {
	page.on('dialog', (dialog) => dialog.accept());

	await applicationsMenuPage.goToAudit();

	await page.locator('#toggle_id_audit_event_searchtoggleAdvanced').click();

	await page.locator('#startDate').fill('01/01/2001');

	await fillAndClickOutside(page, page.locator('#endDate'), '01/01/2001');

	await page.locator('.lexicon-icon-search').click();

	await page.waitForTimeout(500);

	await expect(page.getByText('There are no events.')).toBeVisible();

	const options = await page.getByLabel('Options');

	await options.click();

	const menuItem = await page.getByRole('menuitem', {
		name: 'Export Audit Events',
	});

	await menuItem.click();

	const downloadPromise = await page.waitForEvent('download');

	const download = await downloadPromise;

	const filePath = getTempDir() + download.suggestedFilename();

	await download.saveAs(filePath);

	expect(statSync(filePath).size).toBe(0);
});

test("LPS-192555: Assert that the page's URL with advanced search doesn't get over 2048 characters", async ({
	applicationsMenuPage,
	page,
}) => {
	await applicationsMenuPage.goToAudit();

	await page.locator('#toggle_id_audit_event_searchtoggleAdvanced').click();

	await page
		.locator(`#${AUDIT_PORTLET_NAMESPACE}userName:visible`)
		.fill('test test');

	await page
		.locator(`#${AUDIT_PORTLET_NAMESPACE}eventType:visible`)
		.fill('LOGIN');

	await page.locator('.lexicon-icon-search').click();

	await page.waitForTimeout(500);

	const pageURL = page.url();

	expect(pageURL.length).toBeLessThan(2048);
});
