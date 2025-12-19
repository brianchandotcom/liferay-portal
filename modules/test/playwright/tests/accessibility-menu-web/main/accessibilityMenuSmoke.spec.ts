/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {accessibilityMenuPagesTest} from '../../../fixtures/accessibilityMenuPagesTest';
import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {siteSettingsPagesTest} from '../../../fixtures/siteSettingsPagesTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';
import {virtualInstancesPagesTest} from '../../../fixtures/virtualInstancesPagesTest';
import {AccessibilityMenuPage} from '../../../pages/accessibility-menu-web/AccessibilityMenuPage';
import {getRandomInt} from '../../../utils/getRandomInt';
import performLogin from '../../../utils/performLogin';

const test = mergeTests(
	accessibilityMenuPagesTest,
	apiHelpersTest,
	isolatedSiteTest,
	siteSettingsPagesTest,
	systemSettingsPageTest,
	instanceSettingsPagesTest,
	virtualInstancesPagesTest,
	loginTest()
);

const DEFAULT_VIRTUAL_INSTANCE_NAME = 'www.able.com';

const FIRST_SITENAME = `First Site ${getRandomInt()}`;
const SECOND_SITENAME = `Second Site ${getRandomInt()}`;

const SETTING_CATEGORY_KEY = 'Accessibility';
const SETTING_CONFIGURATION_NAME = 'Accessibility Menu';

async function expectAccessibilityMenuToBeAvailableFromUserProfileMenu(
	page: Page,
	expectedStatus: 'visible' | 'hidden' = 'visible'
) {
	const userProfileTrigger = page.locator(
		'button[data-qa-id=userPersonalMenu]'
	);
	const accessibilityMenuItem = page.getByRole('menuitem', {
		name: SETTING_CONFIGURATION_NAME,
	});
	const accessibilityModal = page
		.locator('.modal')
		.getByLabel(SETTING_CONFIGURATION_NAME);

	await userProfileTrigger.click();

	if (expectedStatus === 'visible') {
		await expect(accessibilityMenuItem).toBeVisible();

		await accessibilityMenuItem.click();

		await expect(accessibilityModal).toBeVisible();

		await accessibilityModal.getByLabel('Close').click();
	}
	else if (expectedStatus === 'hidden') {
		await expect(accessibilityMenuItem).toBeHidden();

		await userProfileTrigger.click();
	}
}

async function expectAccessibilityMenuToBeAvailableFromKeyboard(
	accessibilityMenuPage: AccessibilityMenuPage
) {
	await accessibilityMenuPage.openAccessibilityMenu();

	await accessibilityMenuPage.closeButton.click();
}

test(
	'Verifies that the user can enable it by Instance Settings',
	{tag: '@LPS-178192'},
	async ({accessibilityMenuPage, instanceSettingsPage, page}) => {
		await test.step('When Accessibility Menu is enabled at Instance Settings level', async () => {
			await instanceSettingsPage.goToInstanceSetting(
				SETTING_CATEGORY_KEY,
				SETTING_CONFIGURATION_NAME
			);

			await accessibilityMenuPage.enableAccessibilityMenu();
		});

		await test.step('Then Accessibility Menu can be reached by keyboard', async () => {
			await expectAccessibilityMenuToBeAvailableFromKeyboard(
				accessibilityMenuPage
			);
		});

		await test.step('And Accessibility Menu can be reached through the user profile menu', async () => {
			await expectAccessibilityMenuToBeAvailableFromUserProfileMenu(page);
		});
	}
);

test(
	'Verifies that the user can enable it by Site Settings',
	{tag: '@LPS-178192'},
	async ({accessibilityMenuPage, site, siteSettingsPage}) => {
		await test.step('When navigate to Site Settings > Accessibility And turn on Enable Accessibility Menu', async () => {
			await siteSettingsPage.goToSiteSetting(
				SETTING_CATEGORY_KEY,
				SETTING_CONFIGURATION_NAME,
				site.friendlyUrlPath
			);

			await accessibilityMenuPage.enableAccessibilityMenu();
		});

		await test.step('Then Accessibility Menu can be reached by keyboard', async () => {
			await expectAccessibilityMenuToBeAvailableFromKeyboard(
				accessibilityMenuPage
			);
		});
	}
);

test(
	'Verifies that the user can enable it by System Settings',
	{tag: '@LPS-178192'},
	async ({accessibilityMenuPage, systemSettingsPage}) => {
		await test.step('When navigate to Site Settings > Accessibility And turn on Enable Accessibility Menu', async () => {
			await systemSettingsPage.goToSystemSetting(
				SETTING_CATEGORY_KEY,
				SETTING_CONFIGURATION_NAME
			);

			await accessibilityMenuPage.enableAccessibilityMenu();
		});

		await test.step('Then Accessibility Menu can be reached by keyboard', async () => {
			await expectAccessibilityMenuToBeAvailableFromKeyboard(
				accessibilityMenuPage
			);
		});
	}
);

test(
	'Verifies that settings can be overridden',
	{tag: '@LPS-178192'},
	async ({
		accessibilityMenuPage,
		apiHelpers,
		browser,
		instanceSettingsPage,
		page,
		siteSettingsPage,
		systemSettingsPage,
		virtualInstancesPage,
	}) => {
		let virtualInstancePage: Page;
		let firstSite: Site;
		let secondSite: Site;

		try {
			await test.step('Create new virtual instance, new first site and new second site', async () => {
				test.slow();

				await virtualInstancesPage.addNewVirtualInstance(
					DEFAULT_VIRTUAL_INSTANCE_NAME
				);

				virtualInstancePage = await browser.newPage({
					baseURL: `http://${DEFAULT_VIRTUAL_INSTANCE_NAME}:8080`,
				});

				await performLogin(
					virtualInstancePage,
					'test',
					'',
					`@${DEFAULT_VIRTUAL_INSTANCE_NAME}.com`
				);

				[firstSite, secondSite] = await Promise.all(
					[FIRST_SITENAME, SECOND_SITENAME].map((name) =>
						apiHelpers.headlessSite.createSite({name})
					)
				);
			});

			await test.step('When enable Accessibility Menu on first site settings', async () => {
				await siteSettingsPage.goToSiteSetting(
					SETTING_CATEGORY_KEY,
					SETTING_CONFIGURATION_NAME,
					firstSite.friendlyUrlPath
				);

				await accessibilityMenuPage.enableAccessibilityMenu();
			});

			await test.step('Then Accessibility Menu is available on first site', async () => {
				await expectAccessibilityMenuToBeAvailableFromUserProfileMenu(
					page
				);
			});

			await test.step('And Accessibility Menu is not available on second site', async () => {
				await siteSettingsPage.goto(secondSite.friendlyUrlPath);

				await expectAccessibilityMenuToBeAvailableFromUserProfileMenu(
					page,
					'hidden'
				);
			});

			await test.step('When enable Accessibility Menu on instance ', async () => {
				await instanceSettingsPage.goToInstanceSetting(
					SETTING_CATEGORY_KEY,
					SETTING_CONFIGURATION_NAME
				);

				await accessibilityMenuPage.enableAccessibilityMenu();
			});

			await test.step('Then Accessibility Menu is available on first and second site', async () => {
				for (const site of [firstSite, secondSite]) {
					await siteSettingsPage.goto(site.friendlyUrlPath);

					await expectAccessibilityMenuToBeAvailableFromUserProfileMenu(
						page
					);
				}
			});

			await test.step('And Accessibility Menu is not available in the new virtual instance', async () => {
				await expectAccessibilityMenuToBeAvailableFromUserProfileMenu(
					virtualInstancePage,
					'hidden'
				);
			});

			await test.step('When disable Accessibility Menu on first site settings', async () => {
				await siteSettingsPage.goToSiteSetting(
					SETTING_CATEGORY_KEY,
					SETTING_CONFIGURATION_NAME,
					firstSite.friendlyUrlPath
				);

				await accessibilityMenuPage.disableAccessibilityMenu();
			});

			await test.step('Then Accessibility Menu is not available on first site', async () => {
				await expectAccessibilityMenuToBeAvailableFromUserProfileMenu(
					page,
					'hidden'
				);
			});

			await test.step('And Accessibility Menu is available on second site', async () => {
				await siteSettingsPage.goto(secondSite.friendlyUrlPath);

				await expectAccessibilityMenuToBeAvailableFromUserProfileMenu(
					page
				);
			});

			await test.step('When enable Accessibility Menu on System Settings', async () => {
				await systemSettingsPage.goToSystemSetting(
					SETTING_CATEGORY_KEY,
					SETTING_CONFIGURATION_NAME
				);

				await accessibilityMenuPage.enableAccessibilityMenu();
			});

			await test.step('And disable Accessibility Menu on Instance Settings', async () => {
				await instanceSettingsPage.goToInstanceSetting(
					SETTING_CATEGORY_KEY,
					SETTING_CONFIGURATION_NAME
				);

				await accessibilityMenuPage.disableAccessibilityMenu();
			});

			await test.step('Then Accessibility Menu is not available on first site', async () => {
				await siteSettingsPage.goto(firstSite.friendlyUrlPath);

				await expectAccessibilityMenuToBeAvailableFromUserProfileMenu(
					page,
					'hidden'
				);
			});

			await test.step('And Accessibility Menu is available in the new virtual instance', async () => {
				await expectAccessibilityMenuToBeAvailableFromUserProfileMenu(
					virtualInstancePage
				);
			});

			await test.step('When enable Accessibility Menu on Instance Settings', async () => {
				await instanceSettingsPage.goToInstanceSetting(
					SETTING_CATEGORY_KEY,
					SETTING_CONFIGURATION_NAME
				);

				await accessibilityMenuPage.enableAccessibilityMenu();
			});

			await test.step('Then Accessibility Menu is not available on first site', async () => {
				await siteSettingsPage.goto(firstSite.friendlyUrlPath);

				await expectAccessibilityMenuToBeAvailableFromUserProfileMenu(
					page,
					'hidden'
				);
			});
		}
		finally {
			await Promise.all(
				[firstSite, secondSite].map((site) => {
					apiHelpers.headlessSite.deleteSite(site.id);
				})
			);

			if (virtualInstancePage) {
				await virtualInstancePage.close();
			}

			await virtualInstancesPage.deleteVirtualInstance(
				DEFAULT_VIRTUAL_INSTANCE_NAME
			);
		}
	}
);
