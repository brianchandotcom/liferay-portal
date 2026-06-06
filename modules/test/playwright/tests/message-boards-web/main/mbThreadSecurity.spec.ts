/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {messageBoardsPagesTest} from '../../../fixtures/messageBoardsTest';
import getRandomString from '../../../utils/getRandomString';

const test = mergeTests(isolatedSiteTest, loginTest(), messageBoardsPagesTest);

test(
	'JavaScript in a thread subject and body is not executed',
	{tag: '@LPS-137634'},
	async ({messageBoardsEditThreadPage, page, site}) => {
		const dialogs: string[] = [];

		page.on('dialog', async (dialog) => {
			dialogs.push(dialog.message());

			await dialog.dismiss();
		});

		const script = "<script>alert('XSS');</script>";
		const subject = `${getRandomString()} ${script}`;

		await messageBoardsEditThreadPage.gotoAndPublishNewBasicThread(
			subject,
			script,
			site.friendlyUrlPath
		);

		// The script is rendered as text, not executed

		await expect(page.getByTestId('headerTitle')).toHaveText(subject);

		expect(dialogs).toHaveLength(0);
	}
);
