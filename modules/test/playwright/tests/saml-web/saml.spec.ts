/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {virtualInstancesPagesTest} from '../../fixtures/virtualInstancesPagesTest';
import getRandomString from '../../utils/getRandomString';

export const test = mergeTests(
	loginTest(),
	virtualInstancesPagesTest
);

test('Create, edit, and delete a new virtual instance', async ({
	editVirtualInstancePage,
	virtualInstancesPage,
}) => {
	const name = getRandomString();

	await virtualInstancesPage.addNewVirtualInstance(
		undefined,
		undefined,
		name,
		undefined
	);

	const newName = getRandomString();

	await editVirtualInstancePage.editVirtualInstance(
		false,
		name,
		newName + '.com',
		'100',
		newName
	);

	await expect(
		await virtualInstancesPage.page
			.getByRole('row')
			.getByText(name + ' ' + newName + ' ' + newName + '.com 0 100 No')
	).toBeVisible();

	await virtualInstancesPage.deleteVirtualInstance(name);
});

test('testing', async ({page}) => {
});
