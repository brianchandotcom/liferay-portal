/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {partnerSiteFixture} from './fixtures/partnerSite';

export const test = mergeTests(partnerSiteFixture);

test('Open Partner Homepage', async ({page}) => {
	const partnerTitle = await page.getByRole('heading', {
		name: 'Welcome to Partner Portal',
	});
	const videoTourButton = await page.getByRole('button', {
		name: 'Watch a Video Tour',
	});
	const partnerGuideLink = await page.getByRole('link', {
		name: 'View Solution Partner Guide',
	});

	expect(partnerTitle).toBeTruthy();
	expect(videoTourButton).toBeVisible();
	expect(partnerGuideLink).toBeVisible();
});
