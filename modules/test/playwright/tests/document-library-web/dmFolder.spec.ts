/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {PORTLET_URLS} from '../../utils/portletUrls';

const test = mergeTests(loginTest(), isolatedSiteTest);

test(
	'Can create DM folder in French language',
	{tag: '@LPD-27271'},
	async ({page, site}) => {
		const folderTitle = 'DM Folder FR';

		await page.goto(
			`/fr/group${site.friendlyUrlPath}${PORTLET_URLS.documentLibrary}`
		);
		await page.getByRole('button', {name: 'Nouveau'}).click();

		await page
			.getByRole('menuitem', {
				name: 'Répertoire',
			})
			.click();

		await page.getByRole('textbox').first().fill(folderTitle);
		await page.getByRole('button', {name: 'Enregistrer'}).click();

		await expect(page.getByRole('link', {name: folderTitle})).toBeVisible();

		// change back to english language

		await page.goto('/en');
	}
);
