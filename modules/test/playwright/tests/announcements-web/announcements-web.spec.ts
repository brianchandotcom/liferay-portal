/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {announcementsPagesTest} from './fixtures/announcementsPagesTest';

export const test = mergeTests(announcementsPagesTest, loginTest());

test(
	'Do not have a blank option for distribution scope',
	{tag: '@LPD-18804'},
	async ({announcementsPage, page}) => {
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
	}
);

test(
	'Content field is required',
	{tag: '@LPD-27067'},
	async ({announcementsPage, page}) => {
		await announcementsPage.goToCreateNewAnnouncement();

		const requiredField = page.locator(
			'#_com_liferay_announcements_web_portlet_AnnouncementsAdminPortlet_contentEditorContainer svg.lexicon-icon-asterisk'
		);

		await expect(requiredField).toBeVisible();
	}
);
