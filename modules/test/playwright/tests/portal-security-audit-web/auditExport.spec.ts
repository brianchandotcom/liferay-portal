/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {loginTest} from '../../fixtures/loginTest';

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

test('LPD-40224: Check if the export audit events request body has all the search parameters', async ({
	applicationsMenuPage,
	page,
}) => {
	page.on('dialog', (dialog) => dialog.accept());

	await applicationsMenuPage.goToAudit();

	await page.locator('#toggle_id_audit_event_searchtoggleAdvanced').click();

	await page.locator('.lexicon-icon-search').click();

	await page.waitForTimeout(500);

	// Populate map with all the date parameters

	const dateValues = {};

	for (const field of dateFields) {
		const inputElement = page.locator(`#${field}`);
		const inputValue = await inputElement.inputValue();

		dateValues[field] = inputValue;
	}

	// On the export request, check if the body has all parameters

	page.on('request', async (request) => {
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

	const options = page.getByLabel('Options');

	await options.click();

	const menuItem = page.getByRole('menuitem', {
		name: 'Export Audit Events',
	});

	await menuItem.click();
});
