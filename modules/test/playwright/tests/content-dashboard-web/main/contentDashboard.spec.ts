/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import getRandomString from '../../../utils/getRandomString';
import getBasicWebContentStructureId from '../../../utils/structured-content/getBasicWebContentStructureId';
import {contentDashboardPagesTest} from './fixtures/contentDashboardPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	contentDashboardPagesTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	usersAndOrganizationsPagesTest
);

test(
	'Review date in Content Dashboard matches the review date set in Web Content',
	{
		tag: '@LPD-74997',
	},
	async ({
		apiHelpers,
		contentDashboardPage,
		page,
		site,
		usersAndOrganizationsPage,
	}) => {
		await usersAndOrganizationsPage.goToUsersWithLimitedAccess();

		await (
			await usersAndOrganizationsPage.usersTableRowLink('test')
		).click();

		await usersAndOrganizationsPage.userPreferencesButton.click();

		await usersAndOrganizationsPage.displaySettingsButton.click();

		await usersAndOrganizationsPage.timeZoneSelect.selectOption(
			'Europe/Lisbon'
		);

		await usersAndOrganizationsPage.saveTimeZoneButton.click();

		const title = getRandomString();

		const reviewDate = new Date(
			2026,
			Math.floor(Math.random() * 12),
			Math.floor(Math.random() * 28) + 1,
			Math.floor(Math.random() * 12) + 1,
			0
		);

		const basicWebContentStructureId =
			await getBasicWebContentStructureId(apiHelpers);

		await apiHelpers.jsonWebServicesJournal.addWebContentDetailed({
			ddmStructureId: basicWebContentStructureId,
			groupId: site.id,
			neverReview: false,
			reviewDateDay: reviewDate.getDate(),
			reviewDateHour: reviewDate.getHours(),
			reviewDateMinute: reviewDate.getMinutes(),
			reviewDateMonth: reviewDate.getMonth(),
			reviewDateYear: reviewDate.getFullYear(),
			titleMap: {en_US: title},
		});

		await contentDashboardPage.goto(site.friendlyUrlPath);

		const row = page.getByRole('row', {name: title});

		const expectedDateText = reviewDate.toLocaleString('en-US', {
			day: 'numeric',
			hour: 'numeric',
			hour12: true,
			minute: '2-digit',
			month: 'numeric',
			year: '2-digit',
		});

		await expect(row).toBeVisible();
		await expect(row.getByText(expectedDateText)).toBeVisible();
	}
);
