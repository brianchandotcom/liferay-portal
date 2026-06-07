/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {messageBoardsPagesTest} from '../../../fixtures/messageBoardsTest';

const test = mergeTests(isolatedSiteTest, loginTest(), messageBoardsPagesTest);

const TERMS = [
	{
		description: 'The message body of the parent message',
		token: '[$MESSAGE_PARENT$]',
	},
	{
		description: 'The message thread of messages at the same level',
		token: '[$MESSAGE_SIBLINGS$]',
	},
	{
		description: 'The message body of the original message',
		token: '[$ROOT_MESSAGE_BODY$]',
	},
];

test('Message parent, sibling, and root body terms are defined in the default email templates', async ({
	messageBoardsPage,
	page,
	site,
}) => {
	for (const tabName of ['Message Added Email', 'Message Updated Email']) {
		await messageBoardsPage.goToConfigurationTab(
			tabName,
			site.friendlyUrlPath
		);

		// The default template body references the new terms

		for (const {token} of TERMS) {
			await expect(page.getByText(token).first()).toBeVisible();
		}

		// The definition of terms documents each new term

		for (const {description} of TERMS) {
			await expect(page.getByText(description)).toBeAttached();
		}
	}
});
