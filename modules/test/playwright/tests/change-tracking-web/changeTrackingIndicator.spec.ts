/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import performLogin, {performLogout, userData} from '../../utils/performLogin';
import {featureFlagPagesTest} from '../feature-flag-web/fixtures/featureFlagPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagPagesTest,
	loginTest()
);

test('LPD-31710 Publication bar disappears when trying to select a publication', async ({
	apiHelpers,
	page,
}) => {
	const user = await apiHelpers.headlessAdminUser.postUserAccount();

	userData[user.alternateName] = {
		name: user.givenName,
		password: 'test',
		surname: user.familyName,
	};

	const role =
		await apiHelpers.headlessAdminUser.getRoleByName('Publications User');

	await apiHelpers.headlessAdminUser.assignUserToRole(
		role.externalReferenceCode,
		user.id
	);

	await performLogout(page);

	await performLogin(page, user.alternateName);

	const ctCollection =
		await apiHelpers.headlessChangeTracking.createCTCollection(
			getRandomString()
		);

	await performLogout(page);

	await performLogin(page, 'test');

	await apiHelpers.headlessAdminUser.deleteUserAccount(Number(user.id));

	const changeTrackingIndicatorButton = page.locator(
		'.change-tracking-indicator-button'
	);

	await changeTrackingIndicatorButton.click();

	const selectPublicationMenuItem = page.getByRole('menuitem', {
		name: 'Select a Publication',
	});

	await expect(selectPublicationMenuItem).toBeVisible();

	await selectPublicationMenuItem.click();

	await expect(
		page.locator('li').filter({hasText: ctCollection.name})
	).toBeVisible();

	await apiHelpers.headlessChangeTracking.deleteCTCollection(ctCollection.id);
});

test('LPD-36221 Publications bar breaks when enabling the FF for LPD-20131', async ({
	apiHelpers,
	featureFlagsInstanceSettingsPage,
	page,
}) => {
	const ctCollection =
		await apiHelpers.headlessChangeTracking.createCTCollection(
			getRandomString()
		);

	await apiHelpers.headlessChangeTracking.checkoutCTCollection(
		ctCollection.id
	);

	await featureFlagsInstanceSettingsPage.goto('Release');

	await featureFlagsInstanceSettingsPage.search('LPD-20131');

	await featureFlagsInstanceSettingsPage.updateFeatureFlag('LPD-20131', true);

	const changeTrackingIndicatorButton = page.locator(
		'.change-tracking-indicator-button'
	);

	await changeTrackingIndicatorButton.click();

	const selectPublicationMenuItem = page.getByRole('menuitem', {
		name: 'Select a Publication',
	});

	await expect(selectPublicationMenuItem).toBeVisible();

	await selectPublicationMenuItem.click();

	await expect(
		page.locator('li').filter({hasText: ctCollection.name})
	).toBeVisible();

	await featureFlagsInstanceSettingsPage.goto('Release');

	await featureFlagsInstanceSettingsPage.search('LPD-20131');

	await featureFlagsInstanceSettingsPage.updateFeatureFlag(
		'LPD-20131',
		false
	);

	await apiHelpers.headlessChangeTracking.deleteCTCollection(ctCollection.id);
});
