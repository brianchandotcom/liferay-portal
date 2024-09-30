/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';

export const test = mergeTests(
	changeTrackingPagesTest,
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

	await dialogIFrame.getByRole('button', {name: 'Publications'}).click();

	const invitePublication = dialogIFrame.getByRole('cell', {
		name: 'Receive a notification when someone: Invites you to work on a publication.',
	});

	await expect(invitePublication.getByRole('checkbox').nth(0)).toBeChecked();
	await expect(invitePublication.getByRole('checkbox').nth(1)).toBeChecked();
});

test('LPD-28956 Verify the configuration fields are displayed', async ({
	ChangeTrackingInstanceSettingsPage,
	page,
}) => {
	await ChangeTrackingInstanceSettingsPage.goto(
		'Publications Email Notifications'
	);

	await expect(
		page.getByRole('heading', {name: 'Publications Email Notifications'})
	).toBeVisible();

	const displayData = [
		'Email from Address',
		'Email from Name',
		'Invitation Email Subject',
		'Invitation Email Body',
	];

	for (const data of displayData) {
		await expect(page.getByText(data, {exact: true})).toBeVisible();
	}

	const subject =
		'[$PORTAL_URL$]: You Have Been Invited to Work on a Publication';
	const body =
		'Dear [$TO_NAME$],<br /><br /> You have been invited to work on a publication. ' +
		'For further information, please visit:<br /><br /> <a href="' +
		'[$PORTAL_PUBLICATION_REVIEW_CHANGES_URL$]">[$PUBLICATION_NAME$]</a><br />' +
		'<br /> Sincerely,<br /> [$FROM_NAME$]<br /> [$FROM_ADDRESS$]<br /> [$PORTAL_URL$]<br />';

	await expect(page.getByText(subject)).toBeVisible();

	await expect(page.getByText(body)).toBeVisible();
});
