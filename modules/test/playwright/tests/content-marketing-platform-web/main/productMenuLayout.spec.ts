/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect} from '@playwright/test';

import {cmpPagesTest as test} from './fixtures/cmpPagesTest';

test(
	'CMS product menu layout is not clobbered by data-engine styles on CMP pages',
	{tag: '@LPD-86015'},
	async ({page, projectsPage}) => {
		await projectsPage.goto();

		const productMenu = page.getByRole('navigation', {
			name: 'CMS Product Menu',
		});

		await expect(productMenu).toBeVisible();

		await expect(productMenu).toHaveCSS('transform', 'none');
		await expect(productMenu).toHaveCSS('position', 'fixed');

		await expect(page.locator('.lfr-de__form-report-sidebar')).toHaveCount(
			0
		);
	}
);
