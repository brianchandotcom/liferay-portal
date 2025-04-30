/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

export default async function clickRowAction({
	actionLabel,
	page,
	row,
}: {
	actionLabel: string;
	page: Page;
	row: Locator;
}) {
	const actionsButton = row.locator('.actions-cell button');

	await expect(actionsButton).toBeInViewport();

	await actionsButton.click();

	const actionMenuItem = page.getByRole('menuitem', {
		name: actionLabel,
	});

	await expect(actionMenuItem).toBeInViewport();

	await actionMenuItem.click();
}
