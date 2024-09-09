/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';

export const test = mergeTests(changeTrackingPagesTest);

test('LPD-29823 Provide a shortcut to Data Removal scripts from Publications Admin', async ({
	changeTrackingPage,
	page,
}) => {
	await changeTrackingPage.goToPublicationHistory();

	const optionsMenuItem = page.getByLabel('Options');

	await expect(optionsMenuItem).toBeVisible();

	await optionsMenuItem.click();

	const dataRemovalMenuItem = page.getByText('Data Removal');

	await expect(dataRemovalMenuItem).toBeVisible();
});
