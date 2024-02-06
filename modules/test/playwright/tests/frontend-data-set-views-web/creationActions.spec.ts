/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {liferayConfig} from '../../liferay.config';
import {actionsPageTest} from './fixtures/actionsPageTest';
import {dataSetsPageTest} from './fixtures/dataSetsPageTest';
import {viewsPageTest} from './fixtures/viewsPageTest';

export const test = mergeTests(
	actionsPageTest,
	dataSetsPageTest,
	loginTest,
	viewsPageTest
);

test('Create a Creation Action', async ({
	actionsPage,
	dataSetsPage,
	page,
	viewsPage,
}) => {
	await dataSetsPage.goto();
	await dataSetsPage.createSampleDataSetUI();

	await viewsPage.goto();
	await viewsPage.createSampleDataSetViewUI();

	await actionsPage.goto();
	await actionsPage.createCreationAction({
		icon: 'arrow-right-full',
		name: 'Link action',
		type: 'link',
		url: liferayConfig.environment.baseUrl,
	});

	await expect(
		page
			.getByRole('cell', {exact: true, name: 'Link action'})
			.locator('span')
			.first()
	).toBeVisible();
});
