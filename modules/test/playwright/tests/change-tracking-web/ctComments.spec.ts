/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import getRandomString from '../../utils/getRandomString';
import performLogin, {performLogout} from '../../utils/performLogin';
import getBasicWebContentStructureId from '../../utils/structured-content/getBasicWebContentStructureId';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	changeTrackingPagesTest,
	isolatedSiteTest
);

let user;

test.afterEach(async ({apiHelpers, ctCollection}) => {
	await apiHelpers.headlessChangeTracking.deleteCTCollection(
		ctCollection.body.id
	);

	const role = await apiHelpers.headlessAdminUser.getRoles('Administrator');

	await apiHelpers.headlessAdminUser.deleteRoleUserAccountAssociation(
		role.items[0].id,
		user.id
	);
});

test.beforeEach(async ({apiHelpers, ctCollection}) => {
	await apiHelpers.headlessChangeTracking.checkoutCTCollection(0);

	user = await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
		'demo.unprivileged@liferay.com'
	);

	const role =
		await apiHelpers.headlessAdminUser.getRoleByName('Administrator');

	await apiHelpers.headlessAdminUser.assignUserToRole(
		role.externalReferenceCode,
		user.id
	);

	await apiHelpers.headlessChangeTracking.checkoutCTCollection(
		ctCollection.body.id
	);
});

test('LPD-17130 Only comment owners are allowed to perform actions on the comment', async ({
	apiHelpers,
	changeTrackingPage,
	ctCollection,
	page,
	site,
}) => {
	const journalName = getRandomString();

	const basicWebContentStructureId =
		await getBasicWebContentStructureId(apiHelpers);

	await apiHelpers.jsonWebServicesJournal.addWebContent({
		ddmStructureId: basicWebContentStructureId,
		groupId: site.id,
		titleMap: {en_US: journalName},
	});

	await changeTrackingPage.goToReviewChanges(ctCollection.body.name);

	await changeTrackingPage.addComment();

	const dropdownMenu = page.locator('.comment-row button');

	await expect(dropdownMenu).toBeVisible();

	await performLogout(page);

	await performLogin(page, user.alternateName);

	await changeTrackingPage.goToReviewChanges(ctCollection.body.name);

	await changeTrackingPage.openComments();

	await expect(dropdownMenu).toBeVisible({visible: false});
});
