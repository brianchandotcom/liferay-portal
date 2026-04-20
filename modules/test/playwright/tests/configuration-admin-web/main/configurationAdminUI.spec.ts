/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';

export const test = mergeTests(
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	loginTest(),
	systemSettingsPageTest
);

test(
	'Configuration admin UI can autogenerate a doc link',
	{tag: ['@LPS-194725']},
	async ({page, systemSettingsPage}) => {
		await test.step('Navigate to Bundle Blacklist configuration page', async () => {
			await systemSettingsPage.goToSystemSetting(
				'Module Container',
				'Bundle Blacklist'
			);
		});

		await test.step('Assert hyperlink is available within an anchor tag, and it says “How does bundle blacklisting work?"', async () => {
			const link = page.getByRole('link', {
				name: 'How does bundle blacklisting work?',
			});

			await expect(link).toBeVisible();
			await expect(link).toHaveAttribute(
				'href',
				/^https:\/\/learn\.liferay\.com\//
			);
		});
	}
);

test(
	'Configuration submit button stays disabled when form has no inputs',
	{tag: ['@LPD-86166']},
	async ({page, systemSettingsPage}) => {
		await page.addInitScript(() => {
			const OriginalMutationObserver = window.MutationObserver;

			window.MutationObserver = function (callback) {
				return new OriginalMutationObserver(function (
					mutations,
					observer
				) {
					document.querySelectorAll('form').forEach((form) => {
						if (
							form.querySelector('.configuration-submit-button')
						) {
							form.querySelectorAll(
								'input:not([type="hidden"]), select, textarea'
							).forEach((input) => input.remove());
						}
					});

					callback.call(this, mutations, observer);
				});
			} as unknown as typeof MutationObserver;
		});

		await systemSettingsPage.goToSystemSetting(
			'Users',
			'Password Policies'
		);

		await expect(
			page.locator('.configuration-submit-button')
		).toBeDisabled();
	}
);

test(
	'Configuration submit button is enabled once form inputs are loaded',
	{tag: ['@LPD-86166']},
	async ({page, systemSettingsPage}) => {
		await systemSettingsPage.goToSystemSetting(
			'Users',
			'Password Policies'
		);

		await expect(
			page.locator('.configuration-submit-button')
		).toBeEnabled();
	}
);
