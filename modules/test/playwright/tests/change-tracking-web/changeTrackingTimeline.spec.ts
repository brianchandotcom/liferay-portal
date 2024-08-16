/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';
import {documentLibraryPagesTest} from '../../fixtures/documentLibraryPages.fixtures';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';
import performLogin, {performLogout, userData} from '../../utils/performLogin';
import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';

export const test = mergeTests(
	documentLibraryPagesTest,
	isolatedSiteTest,
	apiHelpersTest,
	featureFlagsTest({
		'LPD-20556': true,
		'LPS-171364': true,
	}),
	changeTrackingPagesTest
);

let title1;
let title2;

test.beforeEach(
	async ({
		changeTrackingPage,
		ctCollection,
		documentLibraryEditFilePage,
		documentLibraryPage,
		page,
		site,
	}) => {
		await changeTrackingPage.workOnProduction();

		await documentLibraryPage.goto(site.friendlyUrlPath);
		await documentLibraryPage.goToCreateNewFile();

		title1 = getRandomString();

		await documentLibraryEditFilePage.publishNewBasicFileEntry(
			title1,
			site.friendlyUrlPath
		);

		await changeTrackingPage.workOnPublication(ctCollection);

		title2 = getRandomString();

		await documentLibraryPage.editFileEntry(title1);

		await documentLibraryEditFilePage.titleSelector.fill(title2);

		await documentLibraryEditFilePage.publishButton.click();

		await waitForSuccessAlert(
			page,
			'Success:Your request completed successfully.'
		);
	}
);

test.afterEach(async ({apiHelpers, ctCollection}) => {
	await apiHelpers.headlessChangeTracking.deleteCTCollection(ctCollection.id);
});

test('LPD-25853 Edit in x publication is added in the timeline dropdown actions', async ({
	changeTrackingPage,
	ctCollection,
	documentLibraryPage,
	page,
	site,
}) => {
	await changeTrackingPage.workOnProduction();

	await documentLibraryPage.goto(site.friendlyUrlPath);

	await documentLibraryPage.editFileEntry(title1);

	await page.getByLabel('timeline-button').click();

	const timelineActionsButton = page.locator('.publication-timeline button');

	await timelineActionsButton.waitFor();

	await timelineActionsButton.click();

	const editButton = page.getByRole('button', {
		name: `Edit in ${ctCollection.name}`,
	});

	await expect(editButton).toBeVisible();

	await editButton.click();

	await expect(
		page
			.locator('.change-tracking-indicator-title')
			.filter({hasText: ctCollection.name})
	).toBeVisible();
});

test('LPD-25853 Review Change is added in the timeline dropdown actions', async ({
	ctCollection,
	documentLibraryPage,
	page,
	site,
}) => {
	await documentLibraryPage.goto(site.friendlyUrlPath);

	await documentLibraryPage.editFileEntry(title2);

	await page.getByLabel('timeline-button').click();

	const timelineActionsButton = page.locator('.publication-timeline button');

	await timelineActionsButton.waitFor();

	await timelineActionsButton.click();

	const reviewButton = page.getByRole('button', {name: 'Review Change'});

	await expect(reviewButton).toBeVisible();

	await reviewButton.click();

	await expect(
		page.locator('.publication-name').filter({hasText: ctCollection.name})
	).toBeVisible();

	await expect(page.getByText(title2)).toBeVisible();
});

test('LPD-25853 Discard Change is added in the timeline dropdown actions', async ({
	documentLibraryPage,
	page,
	site,
}) => {
	await documentLibraryPage.goto(site.friendlyUrlPath);

	await documentLibraryPage.editFileEntry(title2);

	await page.getByLabel('timeline-button').click();

	const timelineActionsButton = page.locator('.publication-timeline button');

	await timelineActionsButton.waitFor();

	await timelineActionsButton.click();

	const discardButton = page.getByRole('button', {name: 'Discard'});

	await expect(discardButton).toBeVisible();

	await discardButton.click();

	await expect(page.getByText('Discard Changes')).toBeVisible();
});

test('LPD-25853 Move Change is added in the timeline dropdown actions', async ({
	documentLibraryPage,
	page,
	site,
}) => {
	await documentLibraryPage.goto(site.friendlyUrlPath);

	await documentLibraryPage.editFileEntry(title2);

	await page.getByLabel('timeline-button').click();

	const timelineActionsButton = page.locator('.publication-timeline button');

	await timelineActionsButton.waitFor();

	await timelineActionsButton.click();

	const moveButton = page.getByRole('button', {name: 'Move'});

	await expect(moveButton).toBeVisible();

	await moveButton.click();

	await expect(page.getByText('Move Changes')).toBeVisible();
});

test('LPD-25853 Timeline actions are not visible to user without permissions', async ({
	apiHelpers,
	ctCollection,
	documentLibraryPage,
	page,
	site,
}) => {
	const user = await apiHelpers.headlessAdminUser.postUserAccount();

	userData[user.alternateName] = {
		name: user.givenName,
		password: 'test',
		surname: user.familyName,
	};

	const companyId = await page.evaluate(() => {
		return Liferay.ThemeDisplay.getCompanyId();
	});

	const role = await apiHelpers.headlessAdminUser.postRole({
		name: 'role' + getRandomInt(),
		rolePermissions: [
			{
				actionIds: ['VIEW_CONTROL_PANEL'],
				primaryKey: companyId,
				resourceName: '90',
				scope: 1,
			},
			{
				actionIds: ['ACCESS_IN_CONTROL_PANEL', 'VIEW'],
				primaryKey: companyId,
				resourceName:
					'com_liferay_change_tracking_web_portlet_PublicationsPortlet',
				scope: 1,
			},
			{
				actionIds: ['VIEW_SITE_ADMINISTRATION'],
				primaryKey: companyId,
				resourceName: 'com.liferay.portal.kernel.model.Group',
				scope: 1,
			},
			{
				actionIds: ['ACCESS_IN_CONTROL_PANEL', 'VIEW'],
				primaryKey: companyId,
				resourceName:
					'com_liferay_document_library_web_portlet_DLAdminPortlet',
				scope: 1,
			},
			{
				actionIds: ['VIEW'],
				primaryKey: companyId,
				resourceName: 'com.liferay.document.library',
				scope: 1,
			},
		],
	});

	await apiHelpers.headlessAdminUser.assignUserToRole(
		role.externalReferenceCode,
		user.id
	);

	await performLogout(page);

	await performLogin(page, user.alternateName);

	await documentLibraryPage.goto(site.friendlyUrlPath);

	await page.getByRole('link', {exact: true, name: title1}).click();

	await page.getByLabel('timeline-button').click();

	await page.getByText(ctCollection.name).waitFor();

	const timelineActionsButton = page.locator('.publication-timeline button');

	await expect(timelineActionsButton).toBeVisible({visible: false});
});
