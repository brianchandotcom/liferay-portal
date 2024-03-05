/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {announcementsPagesTest} from '../../fixtures/announcementsPagesTest';
import {loginTest} from '../../fixtures/loginTest';

export const test = mergeTests(announcementsPagesTest, loginTest());

test('LPD-18804: Do not have a blank option for distribution scope', async ({
	announcementsPage,
	page,
}) => {
	await announcementsPage.goToCreateNewAnnouncement();

	await page.getByRole('button', {name: 'Configuration'}).click();
	await page.getByLabel('Distribution Scope').selectOption({index: 0});

	expect(
		await page
			.getByLabel('Distribution Scope')
			.evaluate(
				(select: HTMLSelectElement) =>
					select.options[select.selectedIndex].label
			)
	).toBe('General');
});
