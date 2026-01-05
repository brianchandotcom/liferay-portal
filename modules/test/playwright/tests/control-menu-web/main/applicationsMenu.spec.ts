/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';

const test = mergeTests(apiHelpersTest, applicationsMenuPageTest, loginTest());

test(
	'It shows "View All" when total amount of sites of "recently visited" and "my sites" exceeds 7',
	{tag: '@LPD-66980'},
	async ({apiHelpers, applicationsMenuPage, page}) => {
		const sites: Array<Site> = [];

		try {
			for (let index = 1; index < 7; index++) {
				await test.step(`Assert "View All" link visibility after extra site ${index}`, async () => {
					sites.push(
						await apiHelpers.headlessSite.createSite({
							name: getRandomString(),
						})
					);

					await applicationsMenuPage.goto();

					await expect(applicationsMenuPage.viewAllLink).toBeVisible({
						visible: index + 1 >= 7,
					});
				});
			}

			const randomSite = sites[Math.floor(Math.random() * sites.length)];

			await test.step(`Use the "View All" link to navigate to "${randomSite.name}" site`, async () => {
				await applicationsMenuPage.viewAllLink.click();

				const frame = page.frameLocator('iframe[title="Select Site"]');

				await frame.getByRole('link', {name: 'All Sites'}).click();

				await frame
					.getByRole('link', {exact: true, name: randomSite.name})
					.click();

				await page.waitForURL(
					new RegExp(`/group${randomSite.friendlyUrlPath}`)
				);
			});
		}
		finally {
			await test.step('Cleanup sites', async () => {
				await Promise.all(
					sites.map((site) =>
						apiHelpers.headlessSite.deleteSite(site.id)
					)
				);
			});
		}
	}
);
