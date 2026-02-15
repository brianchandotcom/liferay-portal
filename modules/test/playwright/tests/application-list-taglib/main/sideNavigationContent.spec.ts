/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {globalMenuPagesTest} from '../../../fixtures/globalMenuPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';

const test = mergeTests(
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	globalMenuPagesTest,
	loginTest()
);

async function setAllCategoriesExpanded(page: Page, expanded: boolean) {
	const buttons = page
		.getByTestId('sideNavigation')
		.getByRole('menuitem', {expanded: !expanded});

	const initialCount = await buttons.count();

	for (let position = 1; position <= initialCount; position++) {
		await buttons.first().click();

		await expect(buttons).toHaveCount(initialCount - position);
	}

	await expect(buttons).toHaveCount(0);
}

test.beforeEach(async ({globalMenuPage, page}) => {
	await globalMenuPage.goToControlPanel();

	const sideNavigation = page.getByTestId('sideNavigation');

	await expect(sideNavigation).toBeVisible();
	await expect(
		sideNavigation.getByRole('menuitem', {expanded: true, name: 'Users'})
	).toBeVisible();

	await setAllCategoriesExpanded(page, false);
});

test(
	'The leaf item links have active state based on the current page',
	{tag: '@LPD-73706'},
	async ({page}) => {
		await test.step('Click on a leaf item and check if it navigates to the correct page', async () => {
			const usersAndOrganizationsItem = page.getByRole('menuitem', {
				name: 'Users and Organizations',
			});

			await expect(usersAndOrganizationsItem).toHaveClass(/active/);

			const userGroupsItem = page.getByRole('menuitem', {
				name: 'User Groups',
			});

			await expect(userGroupsItem).not.toHaveClass(/active/);

			await clickAndExpectToBeVisible({
				target: page.getByRole('heading', {name: 'User Groups'}),
				trigger: userGroupsItem,
			});

			await expect(usersAndOrganizationsItem).not.toHaveClass(/active/);
			await expect(userGroupsItem).toHaveClass(/active/);
		});
	}
);
