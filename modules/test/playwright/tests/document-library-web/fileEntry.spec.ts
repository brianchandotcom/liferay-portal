/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import moment from 'moment';
import path from 'path';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {documentLibraryPagesTest} from '../../fixtures/documentLibraryPages.fixtures';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../utils/getRandomString';
import {performLogout} from '../../utils/performLogin';
import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../layout-content-page-editor-web/utils/getWidgetDefinition';

const baseTest = mergeTests(
	documentLibraryPagesTest,
	isolatedSiteTest,
	loginTest()
);

export const testSearchInDlPortlet = mergeTests(
	apiHelpersTest,
	baseTest,
	featureFlagsTest({
		'LPS-178052': true,
	})
);
export const testFeatureFlagsEnabled = mergeTests(
	baseTest,
	featureFlagsTest({
		'LPD-10701': true,
	})
);

export const testUploadMultipleFieldsWithCustomDocumentType =
	mergeTests(baseTest);

baseTest(
	'Check order by Relevance in Search of DL',
	{
		tag: '@LPD-32481',
	},
	async ({documentLibraryEditFilePage, documentLibraryPage, page, site}) => {
		await documentLibraryEditFilePage.publishNewBasicFileEntry(
			'test',
			site.friendlyUrlPath
		);
		await documentLibraryEditFilePage.publishNewBasicFileEntry(
			getRandomString(),
			site.friendlyUrlPath
		);
		await documentLibraryEditFilePage.publishNewBasicFileEntry(
			getRandomString(),
			site.friendlyUrlPath
		);
		await documentLibraryPage.orderMenu.click();
		await expect(
			page.getByRole('menuitem', {name: 'Relevance'})
		).not.toBeVisible();

		await page.reload();

		await documentLibraryPage.searchInDL('test');

		await documentLibraryPage.orderBy('relevance');

		await expect(
			page.locator('dd.list-group-item[data-title="test"]')
		).toHaveAttribute('id', /_entries_1$/);
	}
);

baseTest(
	'Check if Ordering by Modified Date working, after editing a document',
	{
		tag: '@LPD-32483',
	},
	async ({documentLibraryEditFilePage, documentLibraryPage, page, site}) => {
		const title = getRandomString();
		await documentLibraryEditFilePage.publishNewBasicFileEntry(
			title,
			site.friendlyUrlPath
		);

		const title2 = getRandomString();
		await documentLibraryEditFilePage.publishNewBasicFileEntry(
			title2,
			site.friendlyUrlPath
		);

		await documentLibraryPage.editFileEntry(title);
		await documentLibraryEditFilePage.descriptionInput.fill(
			getRandomString()
		);
		await documentLibraryEditFilePage.publishButton.click();
		await waitForSuccessAlert(
			page,
			'Success:Your request completed successfully.'
		);
		await page.getByRole('link', {name: 'Back'}).click();

		await documentLibraryPage.orderBy('Modified Date');
		await documentLibraryPage.orderBy('Descending');

		await expect(
			page
				.locator(`dd.card-page-item[data-title="${title}"]`)
				.getAttribute('id')
		).resolves.toMatch(/_entries_1$/);
	}
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

		await documentLibraryEditFilePage.changeViewInItemSelector(
			'Document',
			'List'
		);

		await documentLibraryEditFilePage.assertPrivateFileIconInSelectPopUp(
			'Document'
		);

		await documentLibraryEditFilePage.changeViewInItemSelector(
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

testUploadMultipleFieldsWithCustomDocumentType(
	'Error uploading multiples files with custom document type',
	{
		tag: '@LPD-29609',
	},

	async ({
		documentLibraryEditDocumentTypesPage,
		documentLibraryEditFilePage,
		page,
	}) => {
		const dTypeTitle = getRandomString();

		await documentLibraryEditDocumentTypesPage.createNewDLTypeWithNumericField(
			dTypeTitle
		);
		await documentLibraryEditFilePage.goToNewFileDifferentType(
			'Multiple Files Upload'
		);

		await documentLibraryEditFilePage.publishMultipleFiles(dTypeTitle, [
			path.join(__dirname, '/dependencies/image1.jpeg'),
		]);

		await expect(
			page.getByRole('link', {exact: true, name: 'image1'})
		).toBeVisible();
	}
);

testSearchInDlPortlet(
	'LPD-31694 Search in DL portlet does not show results in card view for LPS-202909',
	async ({
		apiHelpers,
		documentLibraryEditFilePage,
		documentLibraryPage,
		page,
		site,
	}) => {
		const title = getRandomString();
		await documentLibraryPage.goto(site.friendlyUrlPath);
		await documentLibraryPage.goToCreateNewFile();
		await documentLibraryEditFilePage.publishNewBasicFileEntryWithoutGoTo(
			title
		);

		const portletId = getRandomString();
		const widgetDefinition = getWidgetDefinition({
			id: portletId,
			widgetName: 'com_liferay_document_library_web_portlet_DLPortlet',
		});
		await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([widgetDefinition]),
			siteId: site.id,
			title: getRandomString(),
		});

		await performLogout(page);

		await page.goto('/web' + site.friendlyUrlPath);

		await documentLibraryPage.searchFor(title);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {name: 'Cards'}),
			trigger: page.getByLabel('Select View, Currently Selected: '),
		});
		await expect(
			page
				.locator('.portlet-document-library')
				.getByRole('link', {name: title})
		).toBeVisible();
	}
);
