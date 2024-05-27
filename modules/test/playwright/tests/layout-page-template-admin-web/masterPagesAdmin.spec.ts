/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {masterPagesTest} from './fixtures/masterPagesTest';

export const test = mergeTests(isolatedSiteTest, loginTest(), masterPagesTest);

test('This is for LPS-102202. Validate if the Blank page template can not be edited and deleted.', async ({
	masterPagesPage,
	site,
}) => {
	await masterPagesPage.goto(site.friendlyUrlPath);

	const templateCard = masterPagesPage.getTemplateCard('Blank');

	await expect(templateCard).toBeVisible();

	await expect(templateCard.getByLabel('More actions')).not.toBeVisible();

	await expect(
		templateCard.locator('.custom-control.custom-checkbox')
	).not.toBeVisible();
});
