/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {displayPageTemplatesTest} from './fixtures/displayTemplatePagesTest';

const test = mergeTests(
	displayPageTemplatesTest,
	isolatedSiteTest,
	loginTest()
);

test('Checks that the card checkbox has the correct aria label', async ({
	displayPageTemplatesPage,
	page,
	site,
}) => {

	// Go to display pages administration

	await displayPageTemplatesPage.goto(site.friendlyUrlPath);

	// Create new DPT and check checkbox aria-label

	const displayPageTemplateName = getRandomString();

	await displayPageTemplatesPage.publishNewTemplate({
		contentSubtype: 'Basic Web Content',
		contentType: 'Web Content Article',
		name: displayPageTemplateName,
	});

	await expect(
		page.getByLabel(`Select ${displayPageTemplateName}`)
	).toBeVisible();
});
