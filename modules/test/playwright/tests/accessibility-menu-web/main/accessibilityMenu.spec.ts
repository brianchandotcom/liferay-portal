/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {accessibilityMenuPagesTest} from '../../../fixtures/accessibilityMenuPagesTest';
import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {AccessibilityMenuPage} from '../../../pages/accessibility-menu-web/AccessibilityMenuPage';
import {doAndGoBack} from '../../../utils/doAndGoBack';
import {
	performLoginViaApi,
	performLogout,
	userData,
} from '../../../utils/performLogin';

async function assertUnderlinedLinksValue(page: Page, enabled: boolean) {
	const body = page.locator('body');
	const underlinedLinksClass = /c-prefers-link-underline/;

	if (enabled) {
		await expect(body).toHaveClass(underlinedLinksClass);
	}
	else {
		await expect(body).not.toHaveClass(underlinedLinksClass);
	}
}

async function setUnderlinedLinks(
	accessibilityMenuPage: AccessibilityMenuPage,
	enabled: boolean
) {
	await accessibilityMenuPage.openAccessibilityMenu();

	await accessibilityMenuPage.toggleUnderlinedLinks(enabled);

	await assertUnderlinedLinksValue(accessibilityMenuPage.page, enabled);
}

interface UsersContext {
	loginAsUserA(): Promise<void>;
	loginAsUserB(): Promise<void>;
}

async function withUsers(
	apiHelpers: ApiHelpers,
	handler: (users: UsersContext) => Promise<void>
) {
	await performLoginViaApi({
		page: apiHelpers.page,
		screenName: 'test',
	});

	const userA = await apiHelpers.headlessAdminUser.postUserAccount();
	const userB = await apiHelpers.headlessAdminUser.postUserAccount();

	for (const userAccount of [userA, userB]) {
		userData[userAccount.alternateName] = {
			name: userAccount.givenName,
			password: 'test',
			surname: userAccount.familyName,
		};
	}

	try {
		await performLogout(apiHelpers.page);

		await handler({
			loginAsUserA: async () => {
				await performLoginViaApi({
					page: apiHelpers.page,
					screenName: userA.alternateName,
				});
			},
			loginAsUserB: async () => {
				await performLoginViaApi({
					page: apiHelpers.page,
					screenName: userB.alternateName,
				});
			},
		});
	}
	finally {
		await performLoginViaApi({
			page: apiHelpers.page,
			screenName: 'test',
		});

		await apiHelpers.headlessAdminUser.deleteUserAccount(Number(userA.id));
		await apiHelpers.headlessAdminUser.deleteUserAccount(Number(userB.id));
	}
}

const test = mergeTests(
	apiHelpersTest,
	accessibilityMenuPagesTest,
	instanceSettingsPagesTest,
	loginTest()
);

test.beforeEach(async ({accessibilityMenuPage, instanceSettingsPage, page}) => {
	await doAndGoBack(page, async () => {
		await instanceSettingsPage.goToInstanceSetting(
			'Accessibility',
			'Accessibility Menu'
		);

		await accessibilityMenuPage.enableAccessibilityMenu();
	});

	await performLogout(page);
});

test.afterEach(async ({instanceSettingsPage, page}) => {
	await performLoginViaApi({page, screenName: 'test'});

	await instanceSettingsPage.goToInstanceSetting(
		'Accessibility',
		'Accessibility Menu'
	);

	await instanceSettingsPage.resetInstanceSetting();
});

test(
	'Accessibility menu is accessible using the keyboard',
	{tag: '@LPD-74263'},
	async ({accessibilityMenuPage, page}) => {
		const {closeButton, menuTitle, openAccessibilityMenuButton} =
			accessibilityMenuPage;

		await test.step('Open menu with keyboard', async () => {
			await expect(menuTitle).toBeHidden();
			await expect(openAccessibilityMenuButton).not.toBeFocused();

			await page.keyboard.press('Tab');
			await page.keyboard.press('Tab');

			await expect(openAccessibilityMenuButton).toBeFocused();
			await expect(openAccessibilityMenuButton).toBeVisible();

			await page.keyboard.press('Enter');

			await expect(menuTitle).toBeVisible();
		});

		await test.step('Close menu with keyboard', async () => {
			await expect(closeButton).toBeVisible();

			await page.keyboard.press('Tab');

			await expect(closeButton).toBeFocused();

			await page.keyboard.press('Enter');

			await expect(menuTitle).toBeHidden();
		});
	}
);

test(
	'Accessibility settings for authenticated users persist in the database',
	{tag: '@LPD-74263'},
	async ({accessibilityMenuPage, apiHelpers, page}) => {
		const valuesToTest = [
			{valueAfterToggle: true, valueBeforeToggle: false},
			{valueAfterToggle: false, valueBeforeToggle: true},
		];

		await withUsers(apiHelpers, async (usersContext) => {
			for (const {valueAfterToggle, valueBeforeToggle} of valuesToTest) {
				await test.step(`Set underlined links to ${valueAfterToggle} for User A`, async () => {
					await usersContext.loginAsUserA();

					await assertUnderlinedLinksValue(page, valueBeforeToggle);

					await setUnderlinedLinks(
						accessibilityMenuPage,
						valueAfterToggle
					);
				});

				await test.step(`Toggle underlined links to ${valueBeforeToggle} as a guest`, async () => {
					await performLogout(page);

					await setUnderlinedLinks(
						accessibilityMenuPage,
						valueBeforeToggle
					);
				});

				await test.step(`Verify underlined links is ${valueAfterToggle} for User A after re-login`, async () => {
					await page.reload();

					await usersContext.loginAsUserA();

					await assertUnderlinedLinksValue(page, valueAfterToggle);
				});
			}
		});
	}
);

test(
	'Guest accessibility settings persist in local storage',
	{tag: '@LPD-74263'},
	async ({accessibilityMenuPage, page}) => {
		const valuesToTest = [
			{valueAfterToggle: true, valueBeforeToggle: false},
			{valueAfterToggle: false, valueBeforeToggle: true},
		];

		for (const {valueAfterToggle, valueBeforeToggle} of valuesToTest) {
			await test.step(`Set underlined links to ${valueAfterToggle} as a guest and verify it persists after reload`, async () => {
				await assertUnderlinedLinksValue(page, valueBeforeToggle);

				await setUnderlinedLinks(
					accessibilityMenuPage,
					valueAfterToggle
				);

				await page.reload();

				await assertUnderlinedLinksValue(page, valueAfterToggle);
			});
		}
	}
);

test(
	'Guest accessibility settings persist in local storage if unchanged during a signed‑in session',
	{tag: '@LPD-74263'},
	async ({accessibilityMenuPage, apiHelpers, page}) => {
		await withUsers(apiHelpers, async (usersContext) => {
			await test.step('Turn on underlined links as a guest', async () => {
				await assertUnderlinedLinksValue(page, false);

				await setUnderlinedLinks(accessibilityMenuPage, true);
			});

			await test.step('Verify underlined links is enabled for User A (first login)', async () => {
				await page.reload();

				await usersContext.loginAsUserA();

				await assertUnderlinedLinksValue(page, true);
			});

			await test.step('Disable underlined links as a guest', async () => {
				await performLogout(page);

				await assertUnderlinedLinksValue(page, true);

				await setUnderlinedLinks(accessibilityMenuPage, false);
			});

			await test.step('Verify underlined links is disabled for User B (first login)', async () => {
				await page.reload();

				await usersContext.loginAsUserB();

				await assertUnderlinedLinksValue(page, false);
			});
		});
	}
);

test(
	'Guest accessibility settings extend and persist to other users',
	{tag: '@LPD-74263'},
	async ({accessibilityMenuPage, apiHelpers, page}) => {
		await withUsers(apiHelpers, async (usersContext) => {
			await test.step('Turn on underlined links as a guest', async () => {
				await assertUnderlinedLinksValue(page, false);

				await setUnderlinedLinks(accessibilityMenuPage, true);
			});

			await test.step('First login as User A', async () => {
				await page.reload();

				await usersContext.loginAsUserA();

				await assertUnderlinedLinksValue(page, true);
			});

			await test.step('Disable underlined links as a User A', async () => {
				await page.reload();

				await assertUnderlinedLinksValue(page, true);

				await setUnderlinedLinks(accessibilityMenuPage, false);
			});

			await test.step('First login as User B', async () => {
				await performLogout(page);

				await page.reload();

				await assertUnderlinedLinksValue(page, false);

				await usersContext.loginAsUserB();

				await assertUnderlinedLinksValue(page, true);
			});
		});
	}
);
