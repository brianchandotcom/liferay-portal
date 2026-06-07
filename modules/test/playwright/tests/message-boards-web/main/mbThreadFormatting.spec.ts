/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {messageBoardsPagesTest} from '../../../fixtures/messageBoardsTest';
import getRandomString from '../../../utils/getRandomString';

const test = mergeTests(
	isolatedSiteTest,
	featureFlagsTest({'LPD-11235': {enabled: true}}),
	loginTest(),
	messageBoardsPagesTest
);

test(
	'Can add bold text to a thread body',
	{tag: '@LPS-136910'},
	async ({messageBoardsEditThreadPage, page, site}) => {
		const subject = getRandomString();
		const body = getRandomString();

		await messageBoardsEditThreadPage.goto(site.friendlyUrlPath);

		await messageBoardsEditThreadPage.subjectSelector.fill(subject);
		await messageBoardsEditThreadPage.bodyTextBox.fill(body);

		// Select the body text and toggle bold

		await messageBoardsEditThreadPage.bodyTextBox.press('Control+a');

		await page.locator('a.cke_button__bold').click();

		await messageBoardsEditThreadPage.publishButton.click();

		await page.waitForLoadState('networkidle');

		// The published body renders in bold

		await expect(
			page.locator('.message-content strong').getByText(body)
		).toBeVisible();
	}
);
