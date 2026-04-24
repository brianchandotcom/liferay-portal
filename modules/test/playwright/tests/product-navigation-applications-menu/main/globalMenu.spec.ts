/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {globalMenuPagesTest} from '../../../fixtures/globalMenuPagesTest';
import {loginTest} from '../../../fixtures/loginTest';

const test = mergeTests(globalMenuPagesTest, loginTest());

test(
	'Each global menu category becomes active when clicked and deactivates others',
	{tag: '@LPD-87314'},
	async ({globalMenuPage, page}) => {
		const names = ['Applications', 'Commerce', 'Control Panel'];

		for (const name of names) {
			await test.step(`"${name}" is active after clicking`, async () => {
				await globalMenuPage.openGlobalMenu();

				await globalMenuPage.categoriesList
					.getByRole('menuitem', {exact: true, name})
					.click();

				await expect(page.getByLabel(`${name} Menu`)).toBeVisible();
				await expect(globalMenuPage.categoriesList).toBeHidden();

				await globalMenuPage.openGlobalMenu();

				const selectedCategory = globalMenuPage.categoriesList
					.getByRole('menuitem')
					.and(page.locator('.active'));

				await expect(selectedCategory).toHaveAccessibleName(name);
				await expect(selectedCategory).toHaveCount(1);
			});
		}
	}
);
