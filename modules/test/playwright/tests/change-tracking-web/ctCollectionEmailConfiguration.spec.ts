/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';

export const test = mergeTests(
	featureFlagsTest({
		'LPD-11212': true,
	}),
	loginTest()
);

test('LPD-28802 Verify email notification checkbox is displayed', async ({
	page,
}) => {
	await page.goto('/');

	await page.getByTestId('userPersonalMenu').click();

	await page.getByRole('menuitem', {name: 'Notifications'}).click();

	await page
		.locator(
			'#portlet-topper-toolbar_com_liferay_notifications_web_portlet_NotificationsPortlet'
		)
		.getByLabel('Options')
		.click();

	await page
		.getByRole('menuitem', {exact: true, name: 'Configuration'})
		.click();

	const dialogIFrame = page.frameLocator('iframe');

	await dialogIFrame.getByRole('link', {name: 'Publications'}).click();

	const invitePublication = dialogIFrame.getByRole('cell', {
		name: 'Receive a notification when someone: Invites you to work on a publication.',
	});

	await expect(invitePublication.getByRole('checkbox').nth(0)).toBeChecked();
	await expect(invitePublication.getByRole('checkbox').nth(1)).toBeChecked();
});
