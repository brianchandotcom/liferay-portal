/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {journalPages} from '../../fixtures/JournalPages';
import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {loginTest} from '../../fixtures/loginTest';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	journalPages,
	loginTest
);

test('This is a test for LPS-177690. The tooltip of the back button should be Go to Web Content in the editor of Templates.', async ({
	journalEditTemplatePage,
	page,
}) => {
	await journalEditTemplatePage.goto();

	await expect(page.getByTitle('Go to Web Content')).toBeVisible();
});
