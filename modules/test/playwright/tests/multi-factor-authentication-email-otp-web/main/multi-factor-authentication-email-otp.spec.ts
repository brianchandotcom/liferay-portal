/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {multiFactorAuthenticationPagesTest} from '../../../fixtures/multiFactorAuthenticationPagesTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';

export const test = mergeTests(
	dataApiHelpersTest,
	loginTest(),
	multiFactorAuthenticationPagesTest
);

test(
	'LPD-95294 keeps the resend countdown without flipping back to Send when an incorrect OTP is submitted',
	{tag: '@LPD-95294'},
	async ({
		apiHelpers,
		browser,
		multiFactorAuthenticationConfigurationPage,
	}) => {

		// Enable the email OTP checker for the instance. The admin browser
		// session stays authenticated, so the configuration can be reset later.

		await multiFactorAuthenticationConfigurationPage.goto();

		await multiFactorAuthenticationConfigurationPage.enable();

		// Create a user that will be challenged with email OTP when signing in

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		// Reach the MFA stage in a clean context, leaving the admin signed in

		const userContext = await browser.newContext();
		const userPage = await userContext.newPage();

		try {
			await userPage.goto('/');

			await userPage
				.getByRole('button', {name: 'Sign In'})
				.last()
				.click();

			await userPage.getByLabel('Email Address').fill(user.emailAddress);
			await userPage.getByLabel('Password').fill('test');

			await userPage
				.getByRole('button', {name: 'Sign In'})
				.last()
				.click();

			// Request a one-time password so the send button starts its cooldown

			const sendEmailButton = userPage.locator('[id$="sendEmailButton"]');

			await expect(sendEmailButton).toHaveText('Send');

			await clickAndExpectToBeVisible({
				target: userPage.getByText(
					'Your one-time password has been sent by email.'
				),
				trigger: sendEmailButton,
			});

			// Submit an incorrect OTP. The send button is wired with type="submit"
			// inside a data-senna-off form, so this is a full-page server
			// re-render of the challenge (not an AJAX update).

			await userPage
				.getByLabel('Enter the one-time password from the email')
				.fill(getRandomString());

			await userPage.locator('[id$="submitEmailButton"]').click();

			await expect(
				userPage.getByText('Multi-factor authentication has failed.')
			).toBeVisible();

			// LPD-95294: on the re-render the cooldown must already be in place,
			// never reverting to "Send". The button is rendered server-side as a
			// disabled countdown (its label is the remaining seconds).

			await expect(sendEmailButton).toBeDisabled();

			await expect(sendEmailButton).not.toHaveText('Send');

			await expect(sendEmailButton).toHaveText(/^\s*\d+\s*$/);
		}
		finally {
			await userContext.close();

			await multiFactorAuthenticationConfigurationPage.goto();

			await multiFactorAuthenticationConfigurationPage.resetConfiguration();
		}
	}
);
