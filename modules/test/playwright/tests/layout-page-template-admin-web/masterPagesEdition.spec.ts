/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {masterPagesPagesTest} from '../../fixtures/masterPagesPagesTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import getRandomString from '../../utils/getRandomString';

export const test = mergeTests(
	pagesAdminPagesTest,
	isolatedSiteTest,
	loginTest(),
	masterPagesPagesTest,
	pageEditorPagesTest
);

test('Items containing the drop zone cannot be duplicated or copied', async ({
	masterPagesPage,
	page,
	pageEditorPage,
	site,
}) => {

	// Create new master page

	const name = getRandomString();

	await masterPagesPage.goto(site.friendlyUrlPath);

	await masterPagesPage.createNewMaster(name);

	// Edit it, and place the drop zone inside a container

	await masterPagesPage.editMaster(name);

	await pageEditorPage.addFragment('Layout Elements', 'Container');

	await pageEditorPage.dragTreeNode({
		position: 'middle',
		source: {label: 'Drop Zone'},
		target: {label: 'Container'},
	});

	// Check the container only have Rename action and not Duplicate or Copy

	await page.getByLabel('Select Container').click();

	await page
		.locator('.treeview-link', {hasText: 'Container'})
		.getByLabel('Options')
		.click();

	await expect(page.getByText('Rename')).toBeVisible();

	await expect(page.getByText('Duplicate')).not.toBeVisible();
	await expect(page.getByText('Copy')).not.toBeVisible();
});
