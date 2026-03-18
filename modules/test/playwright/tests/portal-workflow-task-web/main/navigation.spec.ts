/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {workflowPagesTest} from '../../../fixtures/workflowPagesTest';

export const test = mergeTests(
	isolatedSiteTest,
	loginTest(),
	workflowPagesTest
);

test(
	'Back button is preserved when switching tabs from Home',
	{tag: '@LPD-78912'},
	async ({page, workflowTasksPage}) => {
		await page.getByTitle('User Profile Menu').click();

		await page.getByRole('menuitem', {name: 'My Workflow Tasks'}).click();

		const backLink = page.getByRole('link', {
			name: 'Return to Full Page',
		});

		await expect(backLink).toBeVisible();

		await workflowTasksPage.assignedToMeLink.click();

		await page.waitForLoadState();

		await expect(backLink).toBeVisible();

		await workflowTasksPage.assignedToMyRolesLink.click();

		await page.waitForLoadState();

		await expect(backLink).toBeVisible();
	}
);

test(
	'Back button is preserved when switching tabs from Administration',
	{tag: '@LPD-78912'},
	async ({page, workflowTasksPage}) => {
		workflowTasksPage.goto();

		await page.getByTitle('User Profile Menu').click();

		await page.getByRole('menuitem', {name: 'My Workflow Tasks'}).click();

		const backLink = page.getByRole('link', {
			name: 'Back',
		});

		await expect(backLink).toBeVisible();

		await workflowTasksPage.assignedToMeLink.click();

		await page.waitForLoadState();

		await expect(backLink).toBeVisible();

		await workflowTasksPage.assignedToMyRolesLink.click();

		await page.waitForLoadState();

		await expect(backLink).toBeVisible();
	}
);
