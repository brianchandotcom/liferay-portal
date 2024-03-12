/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import {format} from 'date-fns';

import {documentLibraryPagesTest} from '../../fixtures/documentLibraryPages.fixtures';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';

export const testFeatureFlagsEnabled = mergeTests(
	loginTest(),
	featureFlagsTest({
		'LPD-10701': true,
	}),
	documentLibraryPagesTest
);

testFeatureFlagsEnabled(
	'LPD-16658 Show a success message after scheduling a new file',
	async ({documentLibraryEditFilePage, documentLibraryPage, page}) => {
		const scheduleDate = `01/01/${new Date().getFullYear() + 1}`;
		const title = getRandomString();

		await documentLibraryEditFilePage.publishNewFileWithScheduleDate(
			scheduleDate,
			title
		);

		await expect(page.getByRole('link', {name: title})).toBeVisible();

		const toastAlertContainer = page.locator('[id="ToastAlertContainer"]');

		await expect(toastAlertContainer).toBeVisible();

		await expect(toastAlertContainer).toHaveText(
			'Success:' +
				title +
				' will be published on ' +
				format(new Date(scheduleDate), 'M/d/yy h:mm a') +
				'.'
		);

		await documentLibraryPage.deleteAllFileEntries();
	}
);
