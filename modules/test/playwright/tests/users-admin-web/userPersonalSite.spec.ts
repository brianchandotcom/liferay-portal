/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';

export const test = mergeTests(loginTest(), usersAndOrganizationsPagesTest);

test('LPD-26175 check that language selector works on private pages', async ({
	page,
	userPersonalSitePage,
}) => {
	await userPersonalSitePage.goToMyDashboard();

	await userPersonalSitePage.addLanguageSelectorToPage();
	await userPersonalSitePage.switchLanguages(
		'deutsch-Deutschland',
		'Select a Language'
	);

	await expect(page).toHaveURL(new RegExp(`.+/user/.+`));

	await userPersonalSitePage.switchLanguages(
		'english-United States',
		'Sie eine Sprache aus'
	);
});
