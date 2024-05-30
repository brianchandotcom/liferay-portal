/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import moment from 'moment';

import {documentLibraryPagesTest} from '../../fixtures/documentLibraryPages.fixtures';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';

export const testFeatureFlagsEnabled = mergeTests(
	loginTest(),
	featureFlagsTest({
		'LPD-10701': true,
		'LPD-16311': true,
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
				moment(new Date(scheduleDate)).format('M/D/YY h:mm A') +
				'.'
		);
		await documentLibraryPage.deleteFileEntry(title);
	}
);

testFeatureFlagsEnabled(
	'LPD-16313 Identify at a glance if a Document is visible for guests',
	async ({documentLibraryEditFilePage, documentLibraryPage}) => {
		const title = getRandomString();

		await documentLibraryEditFilePage.publishNewFileWithoutGuestViewPermission(
			title
		);

		await documentLibraryPage.changeView('cards');

		await documentLibraryPage.assertPrivateFileIcon();

		await documentLibraryPage.changeView('table');

		await documentLibraryPage.assertPrivateFileIcon();

		await documentLibraryPage.changeView('list');

		await documentLibraryPage.assertPrivateFileIcon();

		await documentLibraryPage.deleteFileEntry(title);
	}
);

testFeatureFlagsEnabled(
	'LPD-16313 Show icon in the content admin and content editor',
	async ({documentLibraryEditFilePage, documentLibraryPage, page}) => {
		const title = getRandomString();

		await documentLibraryEditFilePage.publishNewFileWithoutGuestViewPermission(
			title
		);

		await documentLibraryPage.changeView('cards');

		await documentLibraryPage.editFileEntry(title);

		await documentLibraryPage.assertPrivateFileIcon();

		await documentLibraryEditFilePage.goBack();

		await page.getByRole('link', {name: title}).click();

		await documentLibraryPage.assertPrivateFileIcon();

		await documentLibraryPage.deleteFileEntry(title);
	}
);

testFeatureFlagsEnabled(
	'LPD-16313 Show icon in the DL item selector',
	async ({
		documentLibraryEditDocumentTypesPage,
		documentLibraryEditFilePage,
		documentLibraryPage,
	}) => {
		const dTypeTitle = getRandomString();
		const title = getRandomString();

		await documentLibraryEditDocumentTypesPage.createNewDLTypeWithUploadField(
			dTypeTitle
		);

		await documentLibraryEditFilePage.publishNewFileWithoutGuestViewPermission(
			title
		);

		await documentLibraryEditFilePage.goToNewFileDifferentType(dTypeTitle);

		await documentLibraryEditFilePage.selectForUpdateButton.click();

		await documentLibraryEditFilePage.assertPrivateFileIconInSelectPopUp(
			'Document'
		);

		await documentLibraryEditFilePage.changeViewInItemSelctor(
			'Document',
			'List'
		);

		await documentLibraryEditFilePage.assertPrivateFileIconInSelectPopUp(
			'Document'
		);

		await documentLibraryEditFilePage.changeViewInItemSelctor(
			'Document',
			'Table'
		);

		await documentLibraryEditFilePage.assertPrivateFileIconInSelectPopUp(
			'Document'
		);

		await documentLibraryPage.deleteFileEntry(title);

		await documentLibraryPage.deleteDocumentType(dTypeTitle);
	}
);
